package no.kalli


import java.nio.charset.StandardCharsets

import no.kalli.Matrix.*

object Utility2 {

    private val a = 5
    private val b = 7
    private val m = 26

    fun affineEncrypt(input: ByteArray): ByteArray {

        val tmp = ByteArray(input.size)

        for (i in input.indices)
            tmp[i] = encryptThis(input[i].toInt()).toByte()

        return tmp
    }

    fun affineDecrypt(input: ByteArray): ByteArray {

        val tmp = ByteArray(input.size)

        for (i in input.indices)
            tmp[i] = decryptThis(input[i].toInt()).toByte()

        return tmp
    }

    private fun encryptThis(i: Int): Int {
        return 'A'.toInt() + (a * (i + 'A'.toInt()) + b) % m
    }

    private fun decryptThis(i: Int): Int {
        val x = modInverse(a, m) * (i - 'A'.toInt() - b) % m
        return if (x < 0) (x + m) % m + 'A'.toInt() else x + 'A'.toInt()
    }

    private fun modInverse(a: Int, m: Int): Int {
        var a = a
        a = a % m
        for (x in 1 until m)
            if (a * x % m == 1)
                return x
        return 1
    }

    fun gcd(a: Int, b: Int): Int {
        // Everything divides 0
        if (a == 0 || b == 0)
            return 0

        // base case
        if (a == b)
            return a

        // a is greater
        return if (a > b) gcd(a - b, b) else gcd(a, b - a)
    }


    fun hillEncrypt(msg: ByteArray, key: ByteArray): ByteArray {
        val keyMatrix = createKeyMatrix(key)
        val tuplets = createTuples(msg)

        val m_res = multiplyMatrix(keyMatrix!!, tuplets)
        m_res.modulo(m)

        return m_res.toBytes()
    }

    fun hillDencrypt(msg: ByteArray, key: ByteArray): ByteArray {
        val keyMatrix = createKeyMatrix(key)
        val key_inv = keyMatrix!!.inverse2x2()
        key_inv.modulo(m)

        val m_res = createTuples(msg)

        val c = multiplyMatrix(key_inv, m_res)
        c.modulo(m)
        c.fixNegatives()

        return c.toBytes()
    }


    @JvmStatic
    fun main(args: Array<String>) {
        val test = "ILOVEPIZZA".toByteArray()
        var encrypted = affineEncrypt(test)
        var decrypted = affineDecrypt(encrypted)

        println(String(encrypted))
        println(String(decrypted))


        val key = "PATH".toByteArray()
        val msg = "CIPHER".toByteArray()

        // Encrypt
        encrypted = hillEncrypt(msg, key)
        println(String(encrypted, StandardCharsets.UTF_8))


        // Decrypt
        decrypted = hillDencrypt(encrypted, key)
        println(String(decrypted, StandardCharsets.UTF_8))

    }
}
