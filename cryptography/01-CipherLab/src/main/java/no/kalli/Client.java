/**
 * 
 */
package no.kalli;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

/**
 * @author tosindo
 *
 */
public class Client implements IParent {
	
	public static void main(String[] args){
		var client = new Client();
		client.sendAndReceice();
	}
	
	private void sendAndReceice() {
		Socket client;
	    ObjectOutputStream oos;
	    ObjectInputStream ois;
	    
	    try {
	    	client = new Socket("localhost",PORT);
			
	    	System.out.println("Connected to Server on "+ client.getInetAddress());

	    	oos = new ObjectOutputStream(client.getOutputStream());
	    	ois = new ObjectInputStream(client.getInputStream());
	    	
	    	// send a plaintext message to server
	    	String plaintxt = "Hello from client";
	    	
	    	// send message to server
	    	oos.writeObject(plaintxt.getBytes());
	    	oos.flush();
	    	
	    	// receive response from server
	    	byte[] response = (byte[]) ois.readObject();
	    	
	    	System.out.println("Response from server: "+ new String(response, StandardCharsets.UTF_8));
	    	
	    	// close cliet socket
	    	client.close();
	    	ois.close();
	    	oos.close();
	    	
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public byte[] encryptMessage(byte[] plaintext) {
		
		return null;
	}

	@Override
	public byte[] decryptMessage(byte[] ciphertext) {
		
		return null;
	}

}
