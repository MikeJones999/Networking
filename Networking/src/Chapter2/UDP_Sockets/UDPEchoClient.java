package Chapter2.UDP_Sockets;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class UDPEchoClient 
{
	private static InetAddress host;
	private static final int PORT = 1234;
	private static DatagramSocket datagramSocket;
	private static DatagramPacket inPacket;
	private static DatagramPacket outPacket;
	private static byte[] buffer;
	
	public static void main(String[] args)
	{
		try
		{
			//get local host IP
			host = InetAddress.getLocalHost();
		}
		catch (UnknownHostException uhEx)
		 	{
				System.out.println("Host ID not found!");
				System.exit(1);
			}
		
		//local IP address found - try to access server
		accessServer();
		
	}
	
	
	public static void accessServer()
	{
		try
		{
			datagramSocket = new DatagramSocket();
			
			//set up stream for Keyboard entry...
			Scanner userEntry = new Scanner(System.in);
			
			String message = "";
			String response = "";
			
			
			do
			{
				System.out.print("Enter message: ");
				//capture the message typed in String variable message
				message = userEntry.nextLine();
				
				
				//as long as the message "***closed***" is not received then continue...
				if(!message.equals("***CLOSE***"))
				{
					//convert message to bytes ready for sending
					outPacket = new DatagramPacket(message.getBytes(), message.length(), host, PORT);
					
					//send the DatagramPacket object (outPacket) to server
					datagramSocket.send(outPacket);
					
					//Create a buffer for incoming datagrams.
					buffer = new byte[256];
					//Create a DatagramPacket object for the incoming datagrams.
					inPacket = 	new DatagramPacket(buffer, buffer.length);
					//Accept an incoming datagram. -
					
					/*
					 *  receive()  method: -
					 *  Receives a datagram packet from this socket. When this method returns, the DatagramPacket's buffer is filled with the data received.
					 *  The datagram packet also contains the sender's IP address, and the port number on the sender's machine. 
					 *  This method blocks until a datagram is received. 
					 *  The length field of the datagram packet object contains the length of the received message. 
					 *  If the message is longer than the packet's length, the message is truncated. 

					 */
					datagramSocket.receive(inPacket);
					
					//string response now contains the converted byte array of characters
					response = new String(inPacket.getData(), 0, inPacket.getLength());
					
					System.out.println("\nSERVER> "+response);
				}	
			}while (!message.equals("***CLOSE***"));
						
		}		
		catch(IOException ioEx)
		{
			ioEx.printStackTrace();
		}
		finally
		{
			System.out.println("\n* Closing connectionů *");
			datagramSocket.close();			
		}
		
		
	}
	
}
