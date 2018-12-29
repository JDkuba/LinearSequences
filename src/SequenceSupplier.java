import java.util.ArrayList;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class SequenceSupplier {
    static class RecurrenceStreamSupplier implements IntSupplier {
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

    static class ThueMorseSupplier implements IntSupplier {
        String s;
        int curr;

        ThueMorseSupplier() {
            s = "0";
            curr = 0;
        }

        @Override
        public int getAsInt() {
            if (curr < s.length()) {
                curr += 1;
                return Character.getNumericValue(s.charAt(curr - 1));
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length(); ++i) {
                if (s.charAt(i) == '0') sb.append('1');
                else sb.append('0');
            }
            s = s + sb.toString();
            return getAsInt();
        }
    }

    static IntStream ThueMorse() {
        return IntStream.generate(new ThueMorseSupplier());
    }

    static IntStream ThueMorseClosed() {
        return IntStream.iterate(0, i -> i + 1)
                .map(n -> (Integer.toBinaryString(n)
                        .chars()
                        .reduce(0, (x, y) -> x + y)) % 2);
    }

    private static class CatalanSupplier implements IntSupplier {
        private static List<Integer> catalans;

        CatalanSupplier() {
            catalans = new ArrayList<>();
            catalans.add(1);
        }

        @Override
        public int getAsInt() {
            int res = catalans.get(catalans.size() - 1);
            int tmp = 0;
            for (int i = 0; i < catalans.size(); ++i) {
                tmp += catalans.get(i) * catalans.get(catalans.size() - i - 1);
            }
            catalans.add(tmp);
            return res;
        }
    }

    static IntStream Catalan() {
        return IntStream.generate(new CatalanSupplier());
    }


    private static class LookandSaySupplier implements Supplier<String> {
        String s;

        LookandSaySupplier() {
            s = "1";
        }

        String lookandsay(String num) {
            StringBuilder sb = new StringBuilder();
            char r = num.charAt(0);
            num = num.substring(1) + " ";
            int k = 1;
            for (int i = 0; i < num.length(); ++i) {
                if (num.charAt(i) == r) k++;
                else {
                    sb.append(k).append(r);
                    k = 1;
                    r = num.charAt(i);
                }
            }
            return sb.toString();
        }

        @Override
        public String get() {
            String ret = s;
            s = lookandsay(s);
            return ret;
        }
    }

    static Stream<String> LookandSay() {
        return Stream.generate(new LookandSaySupplier());
    }
}
