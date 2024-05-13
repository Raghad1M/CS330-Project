import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPClient {
    public static void main(String[] args) throws Exception {
        // Create a socket to connect to the server
        Socket socket = null ;
        try {
            socket  = new Socket("10.100.21.222", 1025);
        } catch (Exception e) {
            System.out.println("\n\nServer is down, please try later");
        }
        
        
        if(socket != null){
        // Create input and output streams to communicate with the server
        DataInputStream inputFromServer = new DataInputStream(socket.getInputStream());
        DataOutputStream outputToServer = new DataOutputStream(socket.getOutputStream());
        // Create a scanner to read user input
        Scanner scanner = new Scanner(System.in);
        // Loop until the user quits
        while (true) {
            // Display the menu and prompt the user to enter a choice
            System.out.println("\nPlease enter one of the following choices:");
            System.out.println("B: to convert to binary");
            System.out.println("H: to convert to hexadecimal");
            System.out.println("Q: to quit the client program");
            System.out.print("Your choice: ");
            String choice = scanner.nextLine().toUpperCase();
            // If the choice is Q, break the loop and close the socket
            if (choice.equals("Q")) {
                break;
            }
            
            // If the choice is B or H, prompt the user to enter a number
            else 
                if (choice.equals("B") || choice.equals("H") || choice.length()==0) {
                System.out.print("Please enter a number: ");
                String number = scanner.nextLine();
                // Try to parse the number
                try {
                    if(number.length()!=0)
                        Integer.valueOf(number);
                    // Send the choice and the number to the server
                    
                    outputToServer.writeBytes(choice + " " + number + "\n");
                    // Receive the response from the server
                    String response = inputFromServer.readLine();
                    // Split the response by space
                    String[] parts = response.split(" ");
                    // If the first part is 200, display the converted number
                    if (parts[0].equals("200")) {
                        System.out.println("The converted number is: " + parts[1]);
                    }
                    // Otherwise, display the error message
                    else {
                        System.out.print("Error: ");
                        for (int i = 0; i < parts.length; i++) {
                            System.out.print(parts[i] + " ");
                        }
                        System.out.println("");
                    }
                }
                // If the number is not an integer, display an error message
                catch (NumberFormatException e) {
                    System.out.println("Invalid number. Please try again.");
                }
            }
//             If the choice is not B, H, or Q, display an error message
            else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
        // Close the socket and the scanner
        socket.close();
        scanner.close();
    }
    }
}
