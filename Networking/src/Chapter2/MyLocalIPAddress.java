package Chapter2;

import java.net.*;

/**
 * returns the IP address of the current local machine 
 * - local network IP
 * @author mikieJ
 *
 */


public class MyLocalIPAddress 
{
	public static void main(String[] aegs)
	{
		
		try
		{
			//address object = local machine
			InetAddress address = InetAddress.getLocalHost();
			System.out.println(address);
		}
		catch (UnknownHostException uhEx)
		{
			System.out.println("Could not find local address!");
		}
		
		
		
	}
	
	
}
