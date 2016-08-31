import java.io.*;
import java.net.*;

class TCPClient {
	public static void main(String argv[]) throws Exception {
		String sentence;
		String messageFromServer;
		
		BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in) );
		
		Socket clientSocket = new Socket("localhost", 6789);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		while (true) {
			sentence = inFromUser.readLine();
			System.out.println("Processing: " + sentence + "\n");
			
			if (sentence.equals("")) {	
				clientSocket.close();
      			System.exit(0);
			}
			
			outToServer.writeBytes(sentence + '\n');
			messageFromServer = inFromServer.readLine();
			System.out.println(messageFromServer);
		}
	}
}
