package Chapter3.Threads;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MultiEchoClient 
{

	private static InetAddress host;
	private static final int PORT = 4444;
	
	public static void main(String[] args)
	{
		try
		{
			host = InetAddress.getLocalHost();
		}
		catch(UnknownHostException uhEx)
		{
			System.out.println("\nHost ID not found!\n");
			System.exit(1);
				
		}
		
		sendMessage();
	}
	
	
	
	
	public static void sendMessage()
	{
		Socket socket = null;
		try
		{

			socket = new Socket(host,PORT);
			Scanner networkInput = new Scanner(socket.getInputStream());
			PrintWriter networkOutput = new PrintWriter(socket.getOutputStream(), true);
			
			String message; //outgoing message
			String response; //awaiting incoming message
			//stream for keyboard enrty
			Scanner userEntry = new Scanner(System.in);
			System.out.print("Connected to Server as " + Thread.currentThread().getName() + "\n");
			do
			{
				System.out.print("Enter message ('Quit' to exit): ");
				message = userEntry.nextLine();
				
				
				//send message via socket. - this must only be a string - no front characters
				networkOutput.println(message);
				
				//wait then get server response
				response = networkInput.nextLine();
				
				
				//Display server's response to user...
				System.out.println("\nServer> " + response);
				
			
			}while (!message.equals("Quit"));

			
		}
		catch(IOException ioEx)
		{
			ioEx.printStackTrace();
		}
		finally
		{
			try
			{
				System.out.println("\nClosing connection...");
				socket.close();
			}
			catch(IOException ioEx)
			{
				System.out.println("Unable to disconnect - terminating connection");
				System.exit(1);
			}
		}
		
		
	}
}
