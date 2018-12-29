import java.util.ArrayList;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

class SequenceSupplier {
    static class RecurrenceStreamSupplier implements Supplier<Integer> {
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
        public Integer get() {
            int res = power.mul(base).getNum();
            power = power.mul(M);
            return res;
        }
    }

    static Stream<Integer> RecurrenceStream(int[] coefs, int[] initial, int jump) {
        return Stream.generate(new RecurrenceStreamSupplier(coefs, initial, jump));
    }

    static Stream<Integer> RecurrenceStream(int[] coefs, int[] initial) {
        return Stream.generate(new RecurrenceStreamSupplier(coefs, initial));
    }

    private static IntPredicate isPrime = x -> true;

    static Stream<Integer> PrimeStream() {
        return Stream
                .iterate(2, k -> k + 1)
                .filter(k -> isPrime.test(k))
                .peek(k -> isPrime = isPrime.and(n -> n % k != 0));
    }

    private static class ThueMorseSupplier implements Supplier<Integer> {
        String s;
        int curr;

        ThueMorseSupplier() {
            s = "0";
            curr = 0;
        }

        @Override
        public Integer get() {
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
            return get();
        }
    }

    static Stream<Integer> ThueMorse() {
        return Stream.generate(new ThueMorseSupplier());
    }

    static Stream<Integer> ThueMorseClosed() {
        return Stream.iterate(0, i -> i + 1)
                .map(n -> (Integer.toBinaryString(n)
                        .chars()
                        .reduce(0, (x, y) -> x + y)) % 2);
    }

    private static class CatalanSupplier implements Supplier<Integer> {
        private static List<Integer> catalans;

        CatalanSupplier() {
            catalans = new ArrayList<>();
            catalans.add(1);
        }

        @Override
        public Integer get() {
            int res = catalans.get(catalans.size() - 1);
            int tmp = 0;
            for (int i = 0; i < catalans.size(); ++i) {
                tmp += catalans.get(i) * catalans.get(catalans.size() - i - 1);
            }
            catalans.add(tmp);
            return res;
        }
    }

    static Stream<Integer> Catalan() {
        return Stream.generate(new CatalanSupplier());
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

    private static class KolakoskiSupplier implements Supplier<Integer> {
        int[] base;
        int len;
        List<Integer> memo;
        int i, k, x;

        KolakoskiSupplier(int[] b) {
            base = b;
            memo = new ArrayList<>();
            i = 0;
            k = 0;
            x = 0;
            len = base.length;
        }

        KolakoskiSupplier() {
            this(new int[]{1, 2});
        }

        @Override
        public Integer get() {
            if (x < memo.size()) {
                x++;
                return memo.get(x - 1);
            }
            while (x >= memo.size()) {
                memo.add(base[k % len]);
                if (memo.get(k) > 1) {
                    for (int j = 1; j < memo.get(k); ++j) {
                        i++;
                        memo.add(memo.get(i - 1));
                    }
                }
                i++;
                k++;
            }
            return get();
        }
    }


    static Stream<Integer> Kolakoski() {
        return Stream.generate(new KolakoskiSupplier());
    }

    static Stream<Integer> Kolakoski(int[] b) {
        return Stream.generate(new KolakoskiSupplier(b));
    }

    private static class TenThousandSupplier implements Supplier<Integer> {
        List<Integer> memo;
        int x;

        TenThousandSupplier() {
            memo = new ArrayList<>();
            memo.add(1);
            memo.add(1);
            x = 0;
        }

        @Override
        public Integer get() {
            if (x == 0 || x == 1) {
                x++;
                return memo.get(x - 1);
            }
            memo.add(memo.get(memo.get(x - 1) - 1) + memo.get(x - memo.get(x - 1)));
            x++;
            return memo.get(x - 1);
        }
    }

    static Stream<Integer> TenThousandSequence() {
        return Stream.generate(new TenThousandSupplier());
    }
}