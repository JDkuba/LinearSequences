import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;

class SequenceSupplier {
    public static class RecurrenceStreamSupplier implements IntSupplier {
        Matrix M;
        Matrix base;
        Matrix power;

        RecurrenceStreamSupplier(int[] coefs, int[] initial, int jump) {
            M = new Matrix(coefs);
            int[][] tmp = new int[initial.length][1];
            for (int i = 0; i < initial.length; ++i) {
                tmp[i][0] = initial[i];
            }
            base = new Matrix(tmp);
            power = Matrix.identity(M.dim());
            power = power.mul(M.pow(jump));
        }

        RecurrenceStreamSupplier(int[] coefs, int[] initial) {
            this(coefs, initial, 0);
        }


        @Override
        public int getAsInt() {
            int res = power.mul(base).getNum();
            power = power.mul(M);
            return res;
        }
    }

    static IntStream RecurrenceStream(int[] coefs, int[] initial, int jump) {
        return IntStream.generate(new RecurrenceStreamSupplier(coefs, initial, jump));
    }

    static IntStream RecurrenceStream(int[] coefs, int[] initial) {
        return IntStream.generate(new RecurrenceStreamSupplier(coefs, initial));
    }

    private static IntPredicate isPrime = x -> true;

    static IntStream PrimeStream() {
        return IntStream
                .iterate(2, k -> k + 1)
                .filter(k -> isPrime.test(k))
                .peek(k -> isPrime = isPrime.and(n -> n % k != 0));
    }
}
