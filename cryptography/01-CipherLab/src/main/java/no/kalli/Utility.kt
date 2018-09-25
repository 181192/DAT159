/**
 *
 */
package no.kalli

import java.nio.charset.Charset
import java.math.BigInteger


/**
 * @author Kristoffer-Andre Kalliainen
 */

data class Affine(val a: Int, val b: Int, val msg: String, var enc: String) : Cipher {

    override fun encrypt(): String {
        return encryptMessage(msg.toByteArray(Charsets.UTF_8)).toString(Charsets.UTF_8)
    }

    override fun decrypt(): String {
        return decryptMessage(enc.toByteArray(Charsets.UTF_8)).toString(Charsets.UTF_8)
    }

    override fun encryptMessage(plaintext: ByteArray): ByteArray {
        var res = ""
        val len = plaintext.toString(Charsets.UTF_8)
        for (c in len)
//            res += ((a * (c - 'a') + b) % 26 + 'a').toChar()

        println(res)
        enc = res
        return res.toByteArray()
    }

    override fun decryptMessage(ciphertext: ByteArray): ByteArray {
        var res = ""

        val inverse = BigInteger.valueOf(a.toLong()).modInverse(BigInteger.valueOf(26))

        val c = ciphertext.toString(Charsets.UTF_8)

        c.forEach {
            val decoded = inverse.toInt() * (it - 'a' - b + 26)
            res  += (decoded % 26 + 'a'.toInt()).toChar()
        }

        println(res)
        return res.toByteArray(Charsets.UTF_8)
    }

    private fun gcd(a: Int, b: Int): Int {
        return if (b != 0) gcd(b, a % b) else a
    }
}

data class Hill(val a: Int, val b: Int, val msg: String) : Cipher {
    override fun encrypt(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun decrypt(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun encryptMessage(plaintext: ByteArray): ByteArray {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun decryptMessage(ciphertext: ByteArray): ByteArray {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

interface Cipher : IParent {
    fun encrypt(): String
    fun decrypt(): String
}

@DslMarker
annotation class EncryptDsl

@EncryptDsl
class AffineBuilder(initialA: Int, initialB: Int, initialMsg: String) {
    var a: Int = initialA
    var b: Int = initialB
    var msg: String = initialMsg

    fun build(): Affine {
        return Affine(a, b, msg, "")
    }
}

@EncryptDsl
class HillBuilder(initialA: Int, initialB: Int, initialMsg: String) {
    var a: Int = initialA
    var b: Int = initialB
    var msg: String = initialMsg

    fun build(): Hill {
        return Hill(a, b, msg)
    }
}

@EncryptDsl
fun affine(a: Int = 0, b: Int = 0, msg: String = "", setup: AffineBuilder.() -> Unit = {}): Affine {
    val affineBuilder = AffineBuilder(a, b, msg)
    affineBuilder.setup()
    return affineBuilder.build()
}

@EncryptDsl
fun hill(a: Int = 0, b: Int = 0, msg: String = "", setup: HillBuilder.() -> Unit = {}): Hill {
    val hillBuilder = HillBuilder(a, b, msg)
    hillBuilder.setup()
    return hillBuilder.build()
}

fun main(args: Array<String>) {


    val affine = affine {
        a = 5
        b = 7
        msg = "ILOVEPIZZA"
    }

    affine.encrypt()
    affine.decrypt()


}
