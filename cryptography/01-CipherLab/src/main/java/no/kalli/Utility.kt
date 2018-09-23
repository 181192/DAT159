/**
 *
 */
package no.kalli

/**
 * @author Kristoffer-Andre Kalliainen
 */

data class Utility(val msg: String, val cipher: Cipher)

data class Affine(val a: Int, val b: Int) : Cipher {

    override infix fun encrypt(msg: String): String {
        return encryptMessage(msg.toByteArray()).toString()
    }

    override infix fun decrypt(msg: String): String {
        return decryptMessage(msg.toByteArray()).toString()
    }

    override fun encryptMessage(plaintext: ByteArray): ByteArray {
        var res = ""
        plaintext.toString().forEach {
            res += ('A' + (a * it.toInt() + b) % 26)
        }
        return res.toByteArray()
    }

    override fun decryptMessage(ciphertext: ByteArray): ByteArray {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

data class Hill(val a: Int, val b: Int) : Cipher {
    override fun encrypt(msg: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun decrypt(msg: String): String {
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
    infix fun encrypt(msg: String): String
    infix fun decrypt(msg: String): String
}

@DslMarker
annotation class EncryptDsl

@EncryptDsl
class UtilityBuilder(initialMsg: String, initialCipher: Cipher) {
    var msg: String = initialMsg
    var cipher = initialCipher

    fun build(): Utility {
        return Utility(msg, cipher)
    }

    fun affine(a: Int = 0, b: Int = 0, setup: AffineBuilder.() -> Unit) {
        val builder = AffineBuilder(a, b)
        builder.setup()
        cipher = builder.build()
    }

    fun hill(a: Int = 0, b: Int = 0, setup: HillBuilder.() -> Unit) {
        val builder = HillBuilder(a, b)
        builder.setup()
        cipher = builder.build()
    }

}

@EncryptDsl
class AffineBuilder(initialA: Int, initialB: Int) {
    var a: Int = initialA
    var b: Int = initialB

    fun build(): Affine {
        return Affine(a, b)
    }
}

@EncryptDsl
class HillBuilder(initialA: Int, initialB: Int) {
    var a: Int = initialA
    var b: Int = initialB

    fun build(): Hill {
        return Hill(a, b)
    }
}

@EncryptDsl
fun utility(msg: String = "Test", c: Cipher = Affine(5, 8), setup: UtilityBuilder.() -> Unit = {}): Cipher {
    val utilityBuilder = UtilityBuilder(msg, c)
    utilityBuilder.setup()
    return utilityBuilder.build().cipher
}

fun main(args: Array<String>) {

    val affine = utility {
        msg = "Test"
        affine {
            a = 5
            b = 8
        }
    }

    val hill = utility {
        msg = "Test"
        hill {
            a = 3
            b = 6
        }
    }

    affine encrypt "Test"
    affine decrypt "SSDFs"

    hill encrypt "Test"
    hill decrypt "SSDFA"


}
