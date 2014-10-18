/**
 * ivy.shu.ac.uk - use as the server
 */


package Chapter2.GUIs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GetRemoteTime extends JFrame implements ActionListener
{

	//Variables - JFrame
	private JTextField hostInput;
	private JTextArea display;
	private JButton timeButton;
	private JButton exitButton;
	private JPanel buttonPanel;
	
	//Variables - Sockets
	private static Socket socket = null;
	
	public static void main(String[] args) 
	{
		GetRemoteTime frame = new GetRemoteTime();
		//inherits jframe - thus setSize etc is used
		frame.setSize(400,300);
		frame.setVisible(true);
		
		frame.addWindowListener( new WindowAdapter()
		{
			public void WindowClosing(WindowEvent event)
			{
				//Check Whether a socket is open
				if(socket !=null)
				{
					try
					{
						socket.close();
					}
					catch(IOException ioEx)
					{
						System.out.println(	"\nUnable to close link!\n");
								System.exit(1);
					}
				}
				System.exit(0);
			}
		}
	  ); //new WindowAdapter()
	}

	//constructor
	public GetRemoteTime()
	{
		
		//adding listeners to the text fields to take that information to make string data
		hostInput = new JTextField(20);
		add(hostInput, BorderLayout.NORTH);
		display = new JTextArea(10,15);
		//Following two lines ensure that word-wrapping
		//occurs within the JTextArea�
		display.setWrapStyleWord(true);
		display.setLineWrap(true);
		
		add(new JScrollPane(display),
		BorderLayout.CENTER);
		buttonPanel = new JPanel();
		timeButton = new JButton("Get date and time ");
		timeButton.addActionListener(this);
		buttonPanel.add(timeButton);
		exitButton = new JButton("Exit");
		exitButton.addActionListener(this);
		buttonPanel.add(exitButton);
		add(buttonPanel,BorderLayout.SOUTH);
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent event) 
	{
		if(event.getSource() == exitButton)
		{
			System.exit(0);;
		}
		String theTime;
		
		//Accept host name from the user...
		String host = hostInput.getText();
		final int DAYTIME_PORT = 13;
		
		
		try 
		{
			//Create a Socket object to connect to the
			//specified host on the relevant port�
			socket = new Socket(host, DAYTIME_PORT);
			
			//Create an input stream for the above Socket
			//and add string-reading functionality�
			Scanner input =  new Scanner(socket.getInputStream());
			
			//Accept the host�s response via the
			//above stream�
			theTime = input.nextLine();
			
			//Add the host�s response to the text in the JTextArea�
			display.append("The date/time at " + host + " is " + theTime + "\n");
			hostInput.setText("");
			
		}
		catch (UnknownHostException uhEx)
		{
			display.append("No Such Host\n");
			//set text area to null/""
			hostInput.setText("");
		}
		catch (IOException ioEx)
		{
			display.append(ioEx.toString() + "\n");
		}
		finally
		{
			try
			  {
				if (socket!=null)
				socket.close(); //Close link to host.
			  }
			  catch(IOException ioEx)
				   {
					 System.out.println("Unable to disconnect!");
					 System.exit(1);
				   }
		}
		
		
	}

}
