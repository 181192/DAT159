package no.kalli

import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.PublicKey
import javax.xml.bind.DatatypeConverter

object HashUtil {

    fun addressFromPublicKey(pk: PublicKey): String {
        val pkBytes = pk.encoded
        val hash = sha256Hash(pkBytes)
        return DatatypeConverter.printBase64Binary(hash)
    }

    fun sha256Hash(s: String): ByteArray {
        try {
            return sha256Hash(s.toByteArray(charset("UTF-8")))
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException(e)
        }

    }

    fun sha256Hash(input: ByteArray): ByteArray {
        try {
            val digest = MessageDigest.getInstance("SHA-256")
            return digest.digest(input)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        }

    }

    fun base64Encode(bytes: ByteArray): String {
        return DatatypeConverter.printBase64Binary(bytes)
    }

    fun base64Decode(s: String): ByteArray {
        return DatatypeConverter.parseBase64Binary(s)
    }

}
