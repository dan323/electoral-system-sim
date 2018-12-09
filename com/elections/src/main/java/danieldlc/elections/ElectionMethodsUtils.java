package danieldlc.elections;

import danieldlc.elections.systems.Test;
import danieldlc.elections.systems.div.Divisor;
import danieldlc.elections.systems.quo.Quota;
import danieldlc.elections.systems.quo.Remainder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ElectionMethodsUtils {

    private ElectionMethodsUtils(){
    }

    private static final Logger LOG = Logger.getLogger("Election methods utils");
    public static final String ILLEGAL_ACCESS_TO_METHOD = "Illegal access to method";
    public static final String INVOCATION_TARGET_ERROR = "Invocation target error";

    public static List<Divisor> getDivisorMethods(List<Class> clazzes){
        return getMethods(ElectionMethodsUtils::getDivisor,clazzes, Test.Type.DIVISOR);
    }

    public static List<Quota> getQuotaMethods(List<Class> clazzes){
        return getMethods(ElectionMethodsUtils::getQuota,clazzes, Test.Type.QUOTA);
    }

    public static List<Remainder> getRemainderMethods(List<Class> clazzes){
        return getMethods(ElectionMethodsUtils::getRemainder,clazzes, Test.Type.REMAINDER);
    }

    private static void applyMethodRemainder(Method method, Map<String,Double> remainders, Map<String,Integer> quotaResults, int n) {
        try {
            method.invoke(null,n,remainders,quotaResults);
        } catch (IllegalAccessException e) {
            LOG.log(Level.SEVERE, ILLEGAL_ACCESS_TO_METHOD);
        } catch (InvocationTargetException e) {
            LOG.log(Level.SEVERE, INVOCATION_TARGET_ERROR);
        }
    }

    private static Remainder getRemainder(Method method) {
        return (n, rem, quotas)->applyMethodRemainder(method,rem,quotas,n);
    }

    private static Stream<Method> getMethods(List<Class> clazzes, Test.Type remainder) {
        return clazzes.stream()
                .map(Class::getDeclaredMethods)
                .flatMap(Stream::of)
                .filter(m -> m.isAnnotationPresent(Test.class))
                .filter(m -> m.getAnnotation(Test.class).toBeTested())
                .filter(m -> m.getAnnotation(Test.class).type().equals(remainder));
    }

    private static <T>  List<T> getMethods(Function<Method,T> function,List<Class> clazzes, Test.Type remainder ){
                return getMethods(clazzes,remainder)
                .map(function)
                .collect(Collectors.toList());
    }

    private static double applyMethodDivisor(Method method, int n) {
        try {
            return (double) method.invoke(null, n);
        } catch (IllegalAccessException e) {
            LOG.log(Level.SEVERE, ILLEGAL_ACCESS_TO_METHOD);
        } catch (InvocationTargetException e) {
            LOG.log(Level.SEVERE, INVOCATION_TARGET_ERROR);
        }
        return 0;
    }

    private static Divisor getDivisor(Method method) {
        return n -> applyMethodDivisor(method, n);
    }

    private static double applyMethodQuota(Method method, Map<String,Integer> votes, int n) {
        try {
            return (double) method.invoke(null,votes, n);
        } catch (IllegalAccessException e) {
            LOG.log(Level.SEVERE, ILLEGAL_ACCESS_TO_METHOD);
        } catch (InvocationTargetException e) {
            LOG.log(Level.SEVERE, INVOCATION_TARGET_ERROR);
        }
        return 0;
    }

    private static Quota getQuota(Method method) {
        return (votes,n) -> applyMethodQuota(method,votes, n);
    }

}
