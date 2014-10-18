package Chapter2;

import java.net.*;
import java.util.*;


/**
 * Allows input of a Web name - return IP of that address - e.g fockers.moonfruit.com
 * @author mikieJ
 *
 */

public class IPFinder 
{

	public static void main (String[] args)
	{
		String host;
		Scanner input = new Scanner (System.in);
		
		//handles Internet addresses both as host names and as IP addresses
		InetAddress address;
		
		
		System.out.print("\n\nEnter host/web Name: ");
		host = input.next();
		
		try 
		{
			//return the Internet address of a specified host name as an InetAddress object.
			// therefore address = object
			//uses static method getByName from InetAddress class
			address = InetAddress.getByName(host);
			System.out.println("IP address: " + address.toString());
		}
		catch (UnknownHostException uhEx)
		{
			System.out.println("Could not find " + host);
		}
	}
	
}
