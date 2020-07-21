import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class App {
    public static void main(String[] args) throws Exception {

        // Consumer<T> T を受け取り、戻り値なし。
        Consumer<String> write = (message) -> System.out.println(message);
        write.accept("test, test, test!");

        // UnaryOperator<T> T を受け取り、T を返却する。
        UnaryOperator<String> unary = (value) -> value + "aaa";
        write.accept(unary.apply("eeeee"));

        // Runnable 引数なしかつ、戻り値なし。
        Runnable writeConsole = () -> write.accept("writeConsole");
        writeConsole.run();

        // Supplier<T> 引数なしで T を返却する。
        Supplier<String> getString = () -> "test string.";
        write.accept("getString.get: " + getString.get());

        // Predicate<T> T を受け取り、boolean を返却する。
        Predicate<Integer> isBiggerThanFive = num -> num > 5;
        Predicate<Integer> isLowerThanSix = num -> num < 6;
        write.accept("isBiggerThanFive.and(isLowerThanSix)(5): " + isBiggerThanFive.and(isLowerThanSix).test(5));

        BiPredicate<Integer, Integer> isBiggerThan = (val1, val2) -> val1 > val2;
        write.accept("isBiggerThan(2, 1): " + isBiggerThan.test(2, 1));

        // Function<T, R> T を受け取り、R を返却する。
        Function<Integer, Integer> multiply = (value) -> value * 2;
        Function<Integer, Integer> add = (value) -> value + 3;
        Function<Integer, Integer> addThenMultiply = multiply.compose(add);
        write.accept("multiply.andThen(add)(5): "+multiply.andThen(add).apply(5));
        write.accept("addThenMultiply(5): "+addThenMultiply.apply(5));

        // @FunctionalInterface
        exec(() -> testMethodB());
        exec(() -> testMethodA());
    }

    public static void exec(Funcs<Exception> f) throws Exception {
        f.execute();
    }

    public static void testMethodA() throws Exception {
        Consumer<String> write = (message) -> System.out.println(message);
        write.accept("testMethodA");
    }
    public static void testMethodB() throws Exception {
        Consumer<String> write = (message) -> System.out.println(message);
        write.accept("testMethodB");
    }

    @FunctionalInterface
    public interface Funcs<E extends Exception> {
        void execute() throws E;
    }

}
