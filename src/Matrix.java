/**
 * Matrix class is a class that represents 2D array of BigIntegers as mathematical matrix.
 * It is used for calculating linear recursive sequences.
 * Sequence with coefficients a_0, ..., a_k is represented by matrix:
 * [[a_0,a_1,a_2,...,a_k],
 *  [1,  0,  0,  ...,  0],
 *  [0,  1,  0,  ...,  0],
 *  [...................],
 *  [...................],
 *  [...................],
 *  [0,  0,  0,  ...,  1]]
 *
 * To find n-th element of sequence it is enough to take n_th power of this matrix and multiply it by
 * initial values vector. The result is vertical vector [f(n), f(n-1),..., f(n-k)]
 **/

class Matrix {
    private final int rows;
    private final int cols;
    private final int[][] data;

    private Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        data = new int[rows][cols];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                data[i][j] = 0;
            }
        }
    }

    Matrix(int[][] data) {
        rows = data.length;
        cols = data[0].length;
        this.data = data;
    }

    Matrix(int[] top_row) {
        this(top_row.length, top_row.length);
        data[0] = top_row;
        for (int i = 1; i < rows; i++) {
            data[i][i - 1] = 1;
        }
    }


    static Matrix identity(int n) {
        Matrix X = new Matrix(n, n);
        for (int i = 0; i < n; i++) X.data[i][i] = 1;
        return X;
    }


    Integer getNum() {
        return data[0][0];
    }

    int intVal() {
        return data[0][0];
    }

    int dim() {
        return rows;
    }

    Matrix mul(Matrix B) {
        Matrix A = this;
        if (A.cols != B.rows) throw new RuntimeException("Illegal matrix dimensions");
        Matrix C = new Matrix(A.rows, B.cols);
        for (int i = 0; i < C.rows; i++) {
            for (int j = 0; j < C.cols; j++) {
                for (int k = 0; k < A.cols; k++) {
                    C.data[i][j] += (A.data[i][k] * B.data[k][j]);
                }
            }
        }
        return C;
    }

    Matrix pow(int n) {
        if (n < 0) throw new RuntimeException("Illegal power");
        if (n == 0) return identity(rows);
        if (n == 1) return this;
        Matrix ret = pow(n / 2);
        if (n % 2 == 1) {
            return this.mul(ret).mul(ret);
        } else {
            return ret.mul(ret);
        }
    }

    @Override
    public String toString() {
        return getNum().toString();
    }
}
