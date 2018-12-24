import java.math.BigInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *  RecurrenceStream is a class that uses Matrix class to perform fast calculation of linear recursive sequences, i.e:
 *  f(n+k) = a_0 * f(n + k - 1) + a_1 * f(n+k-2) + ... + a_(k-1) * f(n), where a_0, a_1 ... a_k-1 are coefficients
 *  with starting values f(k-1) = b_0, f(k-2) = b_1, ... , f(0) = b_(k-1)
 *  RecurrenceStream(int[], int[]) constructor takes two int arrays:
 *  array of coefficients: [a_0,a_1,...,a_(k-1)]
 *  and array of starting values: [b_0, b_1, ... b_(k-1)]
 *
 *  Methods next() and jump(int) move Recurrence stream no appropriate position,
 *  but RecurrenceStream class is lazy evaluated so calculations are made only when it is necessary.
 *  Lazy evaluation makes this class effective, because evaluate() function has logarithmic complexity
 *  Function toStream() return Stream<Matrix> that is stream of matrices of recursive sequence elements
 *
 **/

class RecurrenceStream {
    private Matrix base;
    private final Matrix M;
    private Matrix power;
    private Supplier<Stream<Matrix>> s;
    private Stream<Matrix> curr;
    private int to_skip;

    RecurrenceStream(int[] coefs, int[] initial) {
        BigInteger[] big_coefs = new BigInteger[coefs.length];
        for (int i = 0; i < coefs.length; ++i) big_coefs[i] = BigInteger.valueOf(coefs[i]);
        M = new Matrix(big_coefs);
        BigInteger[][] tmp = new BigInteger[initial.length][1];
        for (int i = 0; i < initial.length; ++i) {
            tmp[i][0] = BigInteger.valueOf(initial[i]);
        }
        base = new Matrix(tmp);
        power = Matrix.identity(M.dim());
        s = () -> Stream.iterate(power, matrix -> matrix.mul(M));
        curr = s.get();
        to_skip = 0;
    }


    RecurrenceStream jump(int n) {
        to_skip += n;
        return this;
    }

    RecurrenceStream next() {
        return jump(1);
    }

    Stream<Matrix> toStream() {
        return evaluate().map(matrix -> matrix.mul(base));
    }

    private Stream<Matrix> evaluate() {
        power = power.mul(M.pow(to_skip));
        to_skip = 0;
        curr = s.get();
        return curr;
    }


    @Override
    public String toString() {
        return evaluate().findFirst().get().mul(base).toString();
    }
}