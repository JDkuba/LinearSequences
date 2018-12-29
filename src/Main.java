import java.util.stream.Stream;


public class Main {
    public static void main(String[] args) {
        int[] coefs = new int[]{1, 1};
        int[] initial = new int[]{1, 0};
        Stream<Integer> fib = SequenceSupplier.RecurrenceStream(coefs, initial, 10);

        fib.limit(10).forEach(System.out::println);

        System.out.println();

        Stream<Integer> primes = SequenceSupplier.PrimeStream();
        primes.limit(10).forEach(System.out::println);

        System.out.println();

        Stream<Integer> tm = SequenceSupplier.ThueMorse();
        tm.limit(100).forEach(System.out::print);
        System.out.println();
        Stream<Integer> tmc = SequenceSupplier.ThueMorseClosed();
        tmc.limit(100).forEach(System.out::print);

        System.out.println();

        Stream<Integer> catalan = SequenceSupplier.Catalan();
        catalan.limit(10).forEach(System.out::println);

        System.out.println();

        Stream<String> las = SequenceSupplier.LookandSay();
        las.limit(10).forEach(System.out::println);

        System.out.println();
    }
}
