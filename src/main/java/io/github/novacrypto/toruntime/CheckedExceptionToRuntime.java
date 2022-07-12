package io.github.novacrypto.toruntime;

public final class CheckedExceptionToRuntime {
    public static <T> T toRuntime(final CheckedExceptionToRuntime.Func<T> function) {
        try {
            return function.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void toRuntime(final CheckedExceptionToRuntime.Action function) {
        try {
            function.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public interface Func<T> {
        T run() throws Exception;
    }

    public interface Action {
        void run() throws Exception;
    }
}
