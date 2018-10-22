package no.kalli

import java.security.*
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.xml.bind.DatatypeConverter

object DSAUtil {

    fun generateRandomDSAKeyPair(): KeyPair {
        try {
            val keyGen = KeyPairGenerator.getInstance("DSA")
            keyGen.initialize(512)
            return keyGen.generateKeyPair()

        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    fun signWithDSA(privateKey: PrivateKey, message: String): ByteArray {

        try {
            val dsa = Signature.getInstance("DSA")
            dsa.initSign(privateKey)
            dsa.update(message.toByteArray())
            return dsa.sign()

        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    fun verifyWithDSA(publicKey: PublicKey, message: String, signature: ByteArray): Boolean {
        try {
            val dsa = Signature.getInstance("DSA")
            dsa.initVerify(publicKey)
            dsa.update(message.toByteArray())
            return dsa.verify(signature)

        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    fun base64EncodeKey(key: Key): String {
        return DatatypeConverter.printBase64Binary(key.encoded)
    }

    fun base64DecodePublicKey(base64Key: String): PublicKey {
        val bytes = DatatypeConverter.parseBase64Binary(base64Key)
        try {
            return KeyFactory.getInstance("DSA")
                    .generatePublic(X509EncodedKeySpec(bytes))

        } catch (e: InvalidKeySpecException) {
            throw RuntimeException(e)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        }

    }

    fun base64DecodePrivateKey(base64Key: String): PrivateKey {
        val bytes = DatatypeConverter.parseBase64Binary(base64Key)
        try {
            return KeyFactory.getInstance("DSA")
                    .generatePrivate(PKCS8EncodedKeySpec(bytes))

        } catch (e: InvalidKeySpecException) {
            throw RuntimeException(e)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        }

    }


}
