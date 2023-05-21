package voting;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
	
	public static void main(String args[]) {
		int port = 3000;
		
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			serverSocket.setReuseAddress(true);
            System.out.println("Server started on port " + port + ". " + "Waiting for a client to connect...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");
            
            ClientHandler clientHandler = new ClientHandler(clientSocket, serverSocket);
            
            new Thread(clientHandler).start();
		} catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static class ClientHandler implements Runnable {
		Socket clientSocket;
		ServerSocket serverSocket;

		List<Integer> setup_num = new ArrayList<>();
		List<Integer> numbersReceived = new ArrayList<>();
		int[] electionResults;
		
		public ClientHandler(Socket clientSocket, ServerSocket serverSocket) {
			this.clientSocket = clientSocket;
			this.serverSocket = serverSocket;
		}
		
		@Override
		public void run() {
			try {
				DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
		        int eligible_voters = dataInputStream.readInt();
		        int party_num = dataInputStream.readInt();
		        electionResults = new int[party_num + 1];
		        System.out.println("Received number of eligible voters and number of parties from the client: " + eligible_voters + " and " + party_num + " respectively.");

		        dataInputStream.close();
		        clientSocket.close();
		        
		        while (numbersReceived.size() < eligible_voters) {
		            Socket socket = serverSocket.accept();
		            System.out.println("Client connected.");

		            InputStream inputStream = socket.getInputStream();
		            int number = inputStream.read();
		            numbersReceived.add(number);

		            System.out.println("Received number: " + number);

		            socket.close();
		        }
		        System.out.println("All done voting. Server is stopping.");
		        serverSocket.close();
		        
		        electionResults = new int[party_num + 1];
		        for (int n : numbersReceived) {
		        	electionResults[n]++;
		        }
		        
		        int most_voted = 0;
		        for (int i = 1; i < electionResults.length; i++) {
		        	if (electionResults[i] > most_voted) {
		        		most_voted = i;
		        	}
		        }
		        System.out.println("Winning party: " + most_voted);
		        new PieChart(electionResults, most_voted);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
