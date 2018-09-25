package no.kalli;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class Matrix {
    private double[][] data;
    private int rows, cols;


    public Matrix(int rows, int cols) {
        data = new double[rows][cols];
        this.rows = rows;
        this.cols = cols;
    }

    public Matrix(double[][] arr) {
        this.data = arr.clone();
        this.rows = arr.length;
        this.cols = arr[0].length;
    }

    public boolean isSquare() {
        return rows == cols;
    }

    public Matrix transpose() {
        Matrix res = new Matrix(cols, rows);

        for (int row = 0; row < rows; rows++)
            for (int col = 0; col < cols; cols++)
                res.data[col][row] = data[row][col];

        return res;
    }

    public double determinant2x2() {
        return data[0][0] * data[1][1] - data[0][1] * data[1][0];
    }

    public Matrix inverse2x2() {
        double[][] negatives = data.clone();
        double tmp = data[0][0];
        double detA = 1 / determinant2x2();

        negatives[0][0] = data[1][1] / detA;
        negatives[0][1] = -data[0][1] / detA;
        negatives[1][0] = -data[1][0] / detA;
        negatives[1][1] = tmp / detA;
        return new Matrix(negatives);
    }

    public void modulo(int m) {
        for (int row = 0; row < data.length; row++)
            for (int col = 0; col < data[row].length; col++)
                data[row][col] = data[row][col] % m;
    }

    public void print() {
        System.out.print("[");
        for (int row = 0; row < rows; ++row) {
            if (row != 0) System.out.print(" ");

            System.out.print("[");

            for (int col = 0; col < cols; ++col) {
                System.out.printf("%8.3f", data[row][col]);
                if (col != cols - 1) System.out.print(" ");
            }

            System.out.print("]");
            if (row == rows - 1) System.out.print("]");
            System.out.println();
        }

    }

    public void fixNegatives() {
        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data[i].length; j++) {
                double x = data[i][j];
                data[i][j] = x < 0 ? 26 + x : x;
            }
    }

    private static boolean isEven(byte[] b) {
        return b.length % 2 == 0;
    }

    public static Matrix createTuples(byte[] b) {
        assert (isEven(b));

        var subLeft = Arrays.copyOfRange(b, 0, b.length / 2);
        var subRight = Arrays.copyOfRange(b, b.length / 2, b.length);

        double[][] arr = new double[2][subLeft.length];
        for (int i = 0; i < subLeft.length; i++) {
            arr[0][i] = subLeft[i] - 'A';
            arr[1][i] = subRight[i] - 'A';
        }

        return new Matrix(arr);
    }

    public static Matrix createKeyMatrix(byte[] b) {
        if (b.length != 4) return null;

        double arr[][] = {{b[0] - 'A', b[1] - 'A'}, {b[2] - 'A', b[3] - 'A'}};
        return new Matrix(arr);
    }

    private static Matrix multiplyMatrix(Matrix a, Matrix b) {
        var rowa = b.data.length;
        var cola = b.data[0].length;
        var colb = a.data[0].length;

        var c = new Matrix(a.data.length, a.data[0].length);

        for (int i = 0; i < rowa; i++)
            for (int j = 0; j < colb; j++)
                for (int k = 0; k < cola; k++)
                    c.data[i][j] += b.data[i][k] * a.data[k][j];

        return c;
    }

    public static void main(String[] args) {

        // Encrypt

        // Key matrix
        byte[] keyArr = "PATH".getBytes();
        Matrix key = createKeyMatrix(keyArr);

        assert (key != null);
        key.print();

        System.out.println();

        // From string to Matrix tuplets
        byte[] msg = "CIPHER".getBytes();
        Matrix tuplets = createTuples(msg);
//        for (int i = 0; i < tuplets.length; i++) {
//            tuplets[i].print();
//            System.out.println();
//        }

        Matrix m_res = multiplyMatrix(tuplets, key);

//        double[][] res = new double[2][tuplets.length];
//        for (int i = 0; i < tuplets.length; i++) {
//            res[0][i] = key.data[0][0] * tuplets[i].data[0][0] + key.data[0][1] * tuplets[i].data[1][0];
//            res[1][i] = key.data[1][0] * tuplets[i].data[0][0] + key.data[1][1] * tuplets[i].data[1][0];
//        }
        System.out.println("lolo");

//        Matrix m_res = new Matrix(res);
        m_res.print();

        System.out.println();
        m_res.modulo(26);
        m_res.print();

        // Decrypt
        Matrix key_inv = key.inverse2x2();


        Matrix c = multiplyMatrix(m_res, key_inv);

        c.print();

        c.modulo(26);
        c.print();
        c.fixNegatives();
        c.print();

    }
}
