package com.dan323.elections;

import com.dan323.elections.systems.Testing;
import com.dan323.elections.systems.div.Divisor;
import com.dan323.elections.systems.quo.Quota;
import com.dan323.elections.systems.quo.Remainder;
import com.dan323.elections.systems.stv.STVChoice;
import com.dan323.elections.systems.stv.STVTransfer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
        throw new UnsupportedOperationException();
    }

    public static List<Divisor> getDivisorMethods(List<Class> clazzes) {
        return getMethods(ElectionMethodsTestReflectionUtils::getDivisor, clazzes, Testing.Type.DIVISOR);
    }

    public static <T, N extends Number> List<Quota<T, N>> getQuotaMethods(List<Class> clazzes) {
        return getMethods(ElectionMethodsTestReflectionUtils::getQuota, clazzes, Testing.Type.QUOTA);
    }

    public static <T> List<Remainder<T>> getRemainderMethods(List<Class> clazzes) {
        return getMethods(ElectionMethodsTestReflectionUtils::getRemainder, clazzes, Testing.Type.REMAINDER);
    }

    public static <T> List<STVTransfer<T>> getTransferVotesMethods(List<Class> clazzes) {
        return getMethods(ElectionMethodsTestReflectionUtils::getTransferVotes, clazzes, Testing.Type.TRANSFER);
    }

    public static <T> List<STVChoice<T>> getChoiceVotesMethods(List<Class> clazzes) {
        return getMethods(ElectionMethodsTestReflectionUtils::getChoiceVotes, clazzes, Testing.Type.CHOICE);
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

    private static <T> void applyMethodTransferVotes(Method method, T candidate, long transfer, Map<T, Double> votes, Map<List<T>, Map<T, Long>> originalVotes, Set<T> hopefuls) {
        try {
            method.invoke(null, candidate, transfer, votes, originalVotes, hopefuls);
        } catch (IllegalAccessException e) {
            LOG.log(Level.SEVERE, ILLEGAL_ACCESS_TO_METHOD);
        } catch (InvocationTargetException e) {
            LOG.log(Level.SEVERE, INVOCATION_TARGET_ERROR, e.getCause());
        }
    }

    private static <T> Remainder<T> getRemainder(Method method) {
        return (n, rem, quotas) -> applyMethodRemainder(method, rem, quotas, n);
    }

    private static <T> STVTransfer<T> getTransferVotes(Method method) {
        return (candidate, transfer, votes, originalVotes, hopefuls) -> applyMethodTransferVotes(method, candidate, transfer, votes, originalVotes, hopefuls);
    }

    private static <T> STVChoice<T> getChoiceVotes(Method method) {
        return (votes, esc) -> applyMethodChoiceVotes(method, votes, esc);
    }

    private static <T> Optional<T> applyMethodChoiceVotes(Method method, Map<T, Double> votes, int esc) {
        Optional<T> solution = Optional.empty();
        try {
            solution = (Optional<T>) method.invoke(null, votes, esc);
        } catch (IllegalAccessException e) {
            LOG.log(Level.SEVERE, ILLEGAL_ACCESS_TO_METHOD);
        } catch (InvocationTargetException e) {
            LOG.log(Level.SEVERE, INVOCATION_TARGET_ERROR, e.getCause());
        }
        return solution;
    }

    private static Stream<Method> getMethods(List<Class> clazzes, Testing.Type type) {
        return clazzes.stream()
                .map(Class::getDeclaredMethods)
                .flatMap(Stream::of)
                .filter(m -> m.isAnnotationPresent(Testing.class))
                .filter(m -> m.getAnnotation(Testing.class).toBeTested())
                .filter(m -> m.getAnnotation(Testing.class).type().equals(type));
    }

    private static <T> List<T> getMethods(Function<Method, T> function, List<Class> clazzes, Testing.Type type) {
        return getMethods(clazzes, type)
                .map(function)
                .collect(Collectors.toList());
    }

    private static double applyMethodDivisor(Method method, int n) {
        double solution = 0;
        try {
            solution = (double) method.invoke(null, n);
        } catch (IllegalAccessException e) {
            LOG.log(Level.SEVERE, ILLEGAL_ACCESS_TO_METHOD);
        } catch (InvocationTargetException e) {
            LOG.log(Level.SEVERE, INVOCATION_TARGET_ERROR, e.getCause());
        }
        return solution;
    }

    private static Divisor getDivisor(Method method) {
        return n -> applyMethodDivisor(method, n);
    }

    private static <T, N extends Number> double applyMethodQuota(Method method, Map<T, N> votes, int n) {
        double solution = 0;
        try {
            solution = (double) method.invoke(null, votes, n);
        } catch (IllegalAccessException e) {
            LOG.log(Level.SEVERE, ILLEGAL_ACCESS_TO_METHOD);
        } catch (InvocationTargetException e) {
            LOG.log(Level.SEVERE, INVOCATION_TARGET_ERROR, e.getCause());
        }
        return solution;
    }

    private static <T, N extends Number> Quota<T, N> getQuota(Method method) {
        return (votes, n) -> applyMethodQuota(method, votes, n);
    }

}
