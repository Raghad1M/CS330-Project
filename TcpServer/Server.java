import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        // Create a server socket and bind it to a port number
        ServerSocket serverSocket = new ServerSocket(6789);


        // Accept a connection from a client
        Socket clientSocket = serverSocket.accept();


        // Get the input and output streams from the socket
        BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

        // Loop until the client sends Q
        while (true) {
            // Read a request from the client
            String request = input.readLine();

            // Check if the request is empty
            if (request == null || request.length()==1) {
                // Send a 500 Request is empty: Missing both the letter and number
                output.println("500 Request is empty");
                continue;
            }

            // Split the request into letter and number
            String[] parts = request.split(" ");

            
            // Get the letter 
            String letter = parts[0];
           
            
            // Check if the letter is B or H
            if (!letter.equals("B") && !letter.equals("H")) {
                // Send a 300 bad request: Missing B or H
                output.println("300 Bad request");
                continue;
            }

            
            // Check if the number is valid
            try {
                
            if (parts.length != 2) {
                // Send a 400 The number is missing: response Missing the number
                output.println("400 The number is missing");
                continue;
            }
                
                // Get the number
                 String number = parts[1];
                
                // Parse the number as an integer
                
                int num = Integer.parseInt(number);

                // Convert the number to binary or hexadecimal
                String result ;
                if (letter.equals("B")) {
                    // Convert to binary
                    result = Integer.toBinaryString(num);
                } else {
                    // Convert to hexadecimal
                    result = Integer.toHexString(num).toUpperCase();
                }

                // Send a 200 response with the result
                output.println("200 " + result);
            } catch (NumberFormatException e) {
                // Send a 400 response
                output.println("400 The number is missing");
            }
        }
    }
}
