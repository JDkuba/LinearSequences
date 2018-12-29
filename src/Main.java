import java.util.stream.IntStream;
import java.util.stream.Stream;


public class Main {
    public static void main(String[] args) {
        int[] coefs = new int[]{1, 1};
        int[] initial = new int[]{1, 0};
        IntStream fib = SequenceSupplier.RecurrenceStream(coefs, initial, 10);

        fib.limit(10).forEach(System.out::println);

        System.out.println();

        IntStream primes = SequenceSupplier.PrimeStream();
        primes.limit(10).forEach(System.out::println);

        System.out.println();

        IntStream tm = SequenceSupplier.ThueMorse();
        tm.limit(100).forEach(System.out::print);
        System.out.println();
        IntStream tmc = SequenceSupplier.ThueMorseClosed();
        tmc.limit(100).forEach(System.out::print);

        System.out.println();

        IntStream catalan = SequenceSupplier.Catalan();
        catalan.limit(10).forEach(System.out::println);

        System.out.println();


        Stream<String> las = SequenceSupplier.LookandSay();
        las.limit(10).forEach(System.out::println);

        System.out.println();
    }
}
