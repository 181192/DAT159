package no.kalli;

import java.util.ArrayList;
import java.util.Arrays;

public class Matrix {
    private double[][] data;
    private int rows, cols;


    /**
     * A emtpy matrix
     *
     * @param rows Number of rows
     * @param cols Number of col
     */
    public Matrix(int rows, int cols) {
        data = new double[rows][cols];
        this.rows = rows;
        this.cols = cols;
    }

    /**
     * A matrix from a double array of type Double
     *
     * @param arr The array
     */
    public Matrix(double[][] arr) {
        this.data = arr.clone();
        this.rows = arr.length;
        this.cols = arr[0].length;
    }

    /**
     * Check if a Matrix is squared
     *
     * @return True if the matrix is squared
     */
    public boolean isSquare() {
        return rows == cols;
    }

    /**
     * Calculating the determinant of a 2x2 matrix
     *
     * @return The determinant
     */
    public double determinant2x2() {
        return data[0][0] * data[1][1] - data[0][1] * data[1][0];
    }

    /**
     * Calculating the inverse of a 2x2 matrix
     *
     * @return The inverse matrix
     */
    public Matrix inverse2x2() {
        var negatives = data.clone();
        double tmp = data[0][0];
        double detA = 1 / determinant2x2();

        negatives[0][0] = data[1][1] / detA;
        negatives[0][1] = -data[0][1] / detA;
        negatives[1][0] = -data[1][0] / detA;
        negatives[1][1] = tmp / detA;
        return new Matrix(negatives);
    }

    /**
     * Multiplying the matrix with modulo of the given param
     *
     * @param m The modulo
     */
    public void modulo(int m) {
        for (int row = 0; row < data.length; row++)
            for (int col = 0; col < data[row].length; col++)
                data[row][col] = data[row][col] % m;
    }

    /**
     * Printing out a presentation of the matrix
     */
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

    /**
     * Fixing negatives values for letter representation
     */
    public void fixNegatives() {
        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data[i].length; j++) {
                double x = data[i][j];
                data[i][j] = x < 0 ? x + 26 : x + 1;
            }
    }

    /**
     * Check if a array is even
     *
     * @param b The array
     * @return True if the array is even
     */
    private static boolean isEven(byte[] b) {
        return b.length % 2 == 0;
    }

    /**
     * Creating tuplets of the byte array
     *
     * @param b The array
     * @return A 2xn matrix
     */
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

    /**
     * Creating a 2x2 matrix of a given byte array with lenght 4
     *
     * @param b A byte array with length 4
     * @return A 2x2 matrix
     */
    public static Matrix createKeyMatrix(byte[] b) {
        if (b.length != 4) return null;

        double arr[][] = {{b[0] - 'A', b[1] - 'A'}, {b[2] - 'A', b[3] - 'A'}};
        return new Matrix(arr);
    }

    /**
     * Multiply a 2x2 matrix with an 2xn matrix
     *
     * @param a The 2x2 matrix
     * @param b The 2xn matrix
     * @return A 2xn matrix
     */
    public static Matrix multiplyMatrix(Matrix a, Matrix b) {
        var rowa = a.data.length;
        var cola = a.data[0].length;
        var colb = b.data[0].length;

        var c = new Matrix(b.data.length, b.data[0].length);

        for (int i = 0; i < rowa; i++)
            for (int j = 0; j < colb; j++) {
                c.data[i][j] = 0;
                for (int k = 0; k < cola; k++)
                    c.data[i][j] += a.data[i][k] * b.data[k][j];
            }
        return c;
    }

    /**
     * Print out the letters in the matrix
     */
    public void printLetters() {
        for (double[] row : data)
            for (double col : row) System.out.print((char) (col + 'A' + 1));

        System.out.println();
    }

    /**
     * Create a string of the matrix
     *
     * @return String representation of the matrix
     */
    public String toString() {
        var s = new StringBuilder();
        for (double[] row : data)
            for (double col : row) s.append((char) (col + 'A' + 1));

        return s.toString();
    }

    /**
     * Creating a one dimentional array of the matrix
     *
     * @return A one dimentional byte array of the matrix
     */
    public byte[] toBytes() {
        var b = new ArrayList<Byte>();

        for (double[] row : data)
            for (double col : row) b.add((byte) (col + 'A'));

        var res = new byte[b.size()];
        for (int i = 0; i < b.size(); i++)
            res[i] = b.get(i);

        return res;
    }
}
