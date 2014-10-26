package Chapter3.Threads;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MultiEchoServer
{
private static ServerSocket serverSocket;
private static final int PORT = 4444;
private static int connections;

	public static void main(String[] args) throws IOException
	{
		try
		{
			//open the port first to see if it can be used
			serverSocket = new ServerSocket(PORT);
			System.out.println("Waiting for client to connect...");
			
			
		}
		catch(IOException ioEx)
		{
			System.out.println("\nUnable to set up port");
			System.exit(1);
		}
		
		//assuming try works...
		do
		{
			//Wait for a client to connect...
			Socket client = serverSocket.accept();
			
			System.out.println("\n New Client has connected. IP:" + client.getInetAddress() + " "+ client.getLocalPort());
			increaseConnectionNum();
			//Now client has connected - allocate a thread to that client to handle communication
			ClientHandler handle = new ClientHandler(client);
			Thread handler = new Thread(handle);
			handler.start();
			
		}while (true);
		
	}
	
	public static void increaseConnectionNum()
	{
		connections = connections + 1;
		System.out.println("Current users Connected: " + connections);
	}
	
	
	public static void decreaseConnectionNum()
	{
		connections = connections- 1;
		System.out.println("Current users Connected: " + connections);
	}
	
	public static int getConnectionNum()
	{
		return connections;
	}
	
}

/**
 * Class to handle client connects and communication with the main thread
 * @author MikieJ Study
 *
 */
class ClientHandler implements Runnable
{
	private Socket client;
	private Scanner input;
	private PrintWriter output;
	private MultiEchoServer connectionsHolder;
	
	
	public ClientHandler(Socket socket)
	{
		this.client = socket;
		try
		{
			input = new Scanner(client.getInputStream());
			output = new PrintWriter(client.getOutputStream(), true);
			connectionsHolder = new MultiEchoServer();
		}
		catch (IOException ioEx)
		{
			ioEx.printStackTrace();
		}
		
	}
	
	
	public void run()
	{
		
		String received;
		System.out.println("Thread active");
		do
		{	
			//Accept message from client on the socket's input stream
			received = input.nextLine();
			
			//Echo message back to client on the socket's output stream
			output.println("ECHO: " + received);
		}
		while(!received.equals("Quit"));
		
		try
		{
			if (client != null)
			{
				System.out.println("Closing down Connection...");
				client.close();
				connectionsHolder.decreaseConnectionNum();
				
			}
		}
		catch(IOException ioEx)
		{
			System.out.println("Unable to disconnect");
		}
		
	}
}
