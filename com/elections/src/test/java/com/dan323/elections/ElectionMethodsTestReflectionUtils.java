package com.dan323.elections;

import com.dan323.elections.systems.Testing;
import com.dan323.elections.systems.div.Divisor;
import com.dan323.elections.systems.quo.Quota;
import com.dan323.elections.systems.quo.Remainder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ElectionMethodsTestReflectionUtils {

    private static final String ILLEGAL_ACCESS_TO_METHOD = "Illegal access to method";
    private static final String INVOCATION_TARGET_ERROR = "Invocation target error";
    private static final Logger LOG = Logger.getLogger("Election methods utils:");

    private ElectionMethodsTestReflectionUtils() {
    }

    public static List<Divisor> getDivisorMethods(List<Class> clazzes) {
        return getMethods(ElectionMethodsTestReflectionUtils::getDivisor, clazzes, Testing.Type.DIVISOR);
    }

    public static <T> List<Quota<T>> getQuotaMethods(List<Class> clazzes) {
        return getMethods(ElectionMethodsTestReflectionUtils::getQuota, clazzes, Testing.Type.QUOTA);
    }

    public static <T> List<Remainder<T>> getRemainderMethods(List<Class> clazzes) {
        return getMethods(ElectionMethodsTestReflectionUtils::getRemainder, clazzes, Testing.Type.REMAINDER);
    }

    private static <T> void applyMethodRemainder(Method method, Map<T, Double> remainders, Map<T, Integer> quotaResults, int n) {
        try {
            method.invoke(null, n, remainders, quotaResults);
        } catch (IllegalAccessException e) {
            LOG.log(Level.SEVERE, ILLEGAL_ACCESS_TO_METHOD);
        } catch (InvocationTargetException e) {
            LOG.log(Level.SEVERE, INVOCATION_TARGET_ERROR, e.getCause());
        }
    }

    private static <T> Remainder<T> getRemainder(Method method) {
        return (n, rem, quotas) -> applyMethodRemainder(method, rem, quotas, n);
    }

    private static Stream<Method> getMethods(List<Class> clazzes, Testing.Type remainder) {
        return clazzes.stream()
                .map(Class::getDeclaredMethods)
                .flatMap(Stream::of)
                .filter(m -> m.isAnnotationPresent(Testing.class))
                .filter(m -> m.getAnnotation(Testing.class).toBeTested())
                .filter(m -> m.getAnnotation(Testing.class).type().equals(remainder));
    }

    private static <T> List<T> getMethods(Function<Method, T> function, List<Class> clazzes, Testing.Type remainder) {
        return getMethods(clazzes, remainder)
                .map(function)
                .collect(Collectors.toList());
    }

    private static double applyMethodDivisor(Method method, int n) {
        try {
            return (double) method.invoke(null, n);
        } catch (IllegalAccessException e) {
            LOG.log(Level.SEVERE, ILLEGAL_ACCESS_TO_METHOD);
        } catch (InvocationTargetException e) {
            LOG.log(Level.SEVERE, INVOCATION_TARGET_ERROR, e.getCause());
        }
        return 0;
    }

    private static Divisor getDivisor(Method method) {
        return n -> applyMethodDivisor(method, n);
    }

    private static <T> double applyMethodQuota(Method method, Map<T, Long> votes, int n) {
        try {
            return (double) method.invoke(null, votes, n);
        } catch (IllegalAccessException e) {
            LOG.log(Level.SEVERE, ILLEGAL_ACCESS_TO_METHOD);
        } catch (InvocationTargetException e) {
            LOG.log(Level.SEVERE, INVOCATION_TARGET_ERROR, e.getCause());
        }
        return 0;
    }

    private static <T> Quota<T> getQuota(Method method) {
        return (votes, n) -> applyMethodQuota(method, votes, n);
    }

}
