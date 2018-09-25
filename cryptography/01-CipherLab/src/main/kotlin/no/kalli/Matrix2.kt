package no.kalli

import java.util.ArrayList
import java.util.Arrays

class Matrix2 {
    private var data: Array<DoubleArray>? = null
    private var rows: Int = 0
    private var cols: Int = 0

    val isSquare: Boolean
        get() = rows == cols


    constructor(rows: Int, cols: Int) {
        data = Array(rows) { DoubleArray(cols) }
        this.rows = rows
        this.cols = cols
    }

    constructor(arr: Array<DoubleArray>) {
        this.data = arr.clone()
        this.rows = arr.size
        this.cols = arr[0].size
    }

    fun transpose(): Matrix2 {
        val res = Matrix2(cols, rows)

        val row = 0
        while (row < rows) {
            run {
                val col = 0
                while (col < cols) {
                    res.data!![col][row] = data!![row][col]
                    cols++
                }
            }
            rows++
        }

        return res
    }

    fun determinant2x2(): Double {
        return data!![0][0] * data!![1][1] - data!![0][1] * data!![1][0]
    }

    fun inverse2x2(): Matrix2 {
        val negatives = data!!.clone()
        val tmp = data!![0][0]
        val detA = 1 / determinant2x2()

        negatives[0][0] = data!![1][1] / detA
        negatives[0][1] = -data!![0][1] / detA
        negatives[1][0] = -data!![1][0] / detA
        negatives[1][1] = tmp / detA
        return Matrix2(negatives)
    }

    fun modulo(m: Int) {
        for (row in data!!.indices)
            for (col in 0 until data!![row].size)
                data!![row][col] = data!![row][col] % m
    }

    fun print() {
        print("[")
        for (row in 0 until rows) {
            if (row != 0) print(" ")

            print("[")

            for (col in 0 until cols) {
                System.out.printf("%8.3f", data!![row][col])
                if (col != cols - 1) print(" ")
            }

            print("]")
            if (row == rows - 1) print("]")
            println()
        }

    }

    fun fixNegatives() {
        for (i in data!!.indices)
            for (j in 0 until data!![i].size) {
                val x = data!![i][j]
                data!![i][j] = if (x < 0) x + 26 else x + 1
            }
    }

    fun printLetters() {
        for (i in data!!.indices)
            for (j in 0 until data!![i].size)
                print((data!![i][j] + 'A'.toDouble() + 1.0).toChar())

        println()
    }

    override fun toString(): String {
        val s = StringBuilder()
        for (i in data!!.indices)
            for (j in 0 until data!![i].size)
                s.append((data!![i][j] + 'A'.toDouble() + 1.0).toChar())

        return s.toString()
    }

    fun toBytes(): ByteArray {

        val b = ArrayList<Byte>()

        for (i in data!!.indices)
            for (j in 0 until data!![i].size)
                b.add((data!![i][j] + 'A'.toDouble()).toByte())

        val res = ByteArray(b.size)
        for (i in b.indices)
            res[i] = b[i]

        return res
    }

    companion object {

        private fun isEven(b: ByteArray): Boolean {
            return b.size % 2 == 0
        }

        fun createTuples(b: ByteArray): Matrix2 {
            assert(isEven(b))

            val subLeft = Arrays.copyOfRange(b, 0, b.size / 2)
            val subRight = Arrays.copyOfRange(b, b.size / 2, b.size)

            val arr = Array(2) { DoubleArray(subLeft.size) }
            for (i in subLeft.indices) {
                arr[0][i] = (subLeft[i] - 'A'.toByte()).toDouble()
                arr[1][i] = (subRight[i] - 'A'.toByte()).toDouble()
            }

            return Matrix2(arr)
        }

        fun createKeyMatrix(b: ByteArray): Matrix2? {
            if (b.size != 4) return null

            val arr = arrayOf(doubleArrayOf((b[0] - 'A'.toByte()).toDouble(), (b[1] - 'A'.toByte()).toDouble()), doubleArrayOf((b[2] - 'A'.toByte()).toDouble(), (b[3] - 'A'.toByte()).toDouble()))
            return Matrix2(arr)
        }

        fun multiplyMatrix(a: Matrix2, b: Matrix2): Matrix2 {
            val rowa = a.data!!.size
            val cola = a.data!![0].size
            val colb = b.data!![0].size

            val c = Matrix2(b.data!!.size, b.data!![0].size)

            for (i in 0 until rowa)
                for (j in 0 until colb) {
                    c.data!![i][j] = 0.0
                    for (k in 0 until cola)
                        c.data!![i][j] += a.data!![i][k] * b.data!![k][j]
                }
            return c
        }
    }
}
