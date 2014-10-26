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
		if (connections == 0)
		{	
			System.out.println("Waiting for client to connect...");
		}
	}
	
	public static int getConnectionNum()
	{
		return connections;
	}
	
}

