import java.math.BigInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {
    public static void main(String[] args) {
// Example 1: Fibonacci sequence
        System.out.println("Fibonacci sequenc:");
        int[] coefs = new int[]{1, 1};
        int[] initial = new int[]{1, 0};
        RecurrenceStream fib_rec = new RecurrenceStream(coefs, initial);
        System.out.println(fib_rec);
        System.out.println(fib_rec.next());
        System.out.println(fib_rec.next());
        System.out.println(fib_rec.next());
        System.out.println(fib_rec.jump(3));
        fib_rec.jump(5);
        fib_rec.jump(3);
        fib_rec.next();
        System.out.println("Fibonacci stream 1:");
        Stream<Matrix> fib_stream = fib_rec.toStream();
        fib_stream.limit(20).filter(p -> p.getNum().mod(BigInteger.valueOf(2))
                .equals(BigInteger.ZERO)).forEach(System.out::println);

        fib_rec.jump(100);
        System.out.println("Fibonacci stream 2:");
        fib_stream = fib_rec.toStream();
        fib_stream.filter(p -> p.getNum().isProbablePrime(1)).limit(4).collect(Collectors.toList()).forEach(System.out::println);
        System.out.println(fib_rec.jump(1000000));

// Example 2: f(0)=1, f(1)=3, f(2)=2, f(n+3)=2*f(n+2)-f(n+1)-2*f(n)
// First numbers by WolframAlpha: 2, -1, -10, -23, -34, -25, 30, 153
        System.out.println("\nExample 2:");
        coefs = new int[]{2, -1, -2};
        initial = new int[]{2, 3, 1};
        RecurrenceStream rec = new RecurrenceStream(coefs, initial);
        Stream<Matrix> rec_stream = rec.toStream();
        System.out.println(rec_stream.limit(10)
                .collect(Collectors.groupingBy(p -> p.getNum().compareTo(BigInteger.ZERO))));
        System.out.println(rec);
        System.out.println(rec.next());
        System.out.println(rec.next());
        rec.jump(30);
        rec_stream = rec.toStream();
        System.out.println(rec_stream.limit(10).count());
        rec_stream = rec.toStream();
        rec_stream.limit(10).forEach(System.out::println);

// Example 3: f(0)=f(1)=...=f(8)=0, f(9)=1, f(n+10)=f(n+9)+...+f(n)
        System.out.println("\nExample 3:");
        coefs = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        initial = new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        rec = new RecurrenceStream(coefs, initial);
        rec_stream = rec.toStream();
        rec_stream.limit(15).forEach(System.out::println);
        rec_stream = rec.jump(10).jump(500).next().jump(2).toStream();
        rec_stream.filter(p -> p.getNum().mod(BigInteger.valueOf(5)).equals(BigInteger.ZERO)).limit(5).forEach(System.out::println);
    }
}
