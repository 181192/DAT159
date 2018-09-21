/**
 * 
 */
package no.kalli;

/**
 * @author tosindo
 *
 */
public interface IParent {
	
	int PORT = 9090;
	
	byte[] encryptMessage(byte[] plaintext);
	
	byte[] decryptMessage(byte[] ciphertext);
	
}
