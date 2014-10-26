package Chapter3.Threads;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

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
	//private MultiEchoServer connectionsHolder;
	
	
	public ClientHandler(Socket socket)
	{
		this.client = socket;
		try
		{
			input = new Scanner(client.getInputStream());
			output = new PrintWriter(client.getOutputStream(), true);
			//connectionsHolder = new MultiEchoServer();
		}
		catch (IOException ioEx)
		{
			ioEx.printStackTrace();
		}
		
	}
	
	
	public void run()
	{
		
		String received;
		System.out.println("Thread active - " + Thread.currentThread().getName());
		
		do
		{	
			//Accept message from client on the socket's input stream
			received = input.nextLine();
			System.out.println(Thread.currentThread().getName() + ": " + received);
			//Echo message back to client on the socket's output stream
			output.println("ECHO: " + received);
	
		}
		while(!received.equals("Quit"));
		
		System.out.println(Thread.currentThread().getName() + " has Disconnected");
		
		try
		{
			if (client != null)
			{
				System.out.println("Closing down Connection...");
				client.close();
				MultiEchoServer.decreaseConnectionNum();
				
			}
		}
		catch(IOException ioEx)
		{
			System.out.println("Unable to disconnect");
		}
		
	}
}
