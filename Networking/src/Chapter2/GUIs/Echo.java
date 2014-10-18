/**
 * use the Chapter2.TCP_Sockets - TCPEchoServer.java to act as the server
 */


package Chapter2.GUIs;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;

import javax.swing.*;

import java.net.*;
import java.io.*;
import java.util.*;
public class Echo extends JFrame implements ActionListener
{
	//JFRAME VARIABLES
	private JTextField hostInput,lineToSend;
	private JLabel hostPrompt,messagePrompt;
	private JTextArea received;
	private JButton closeConnection;
	private JPanel hostPanel,entryPanel;
	private String messageToSend;

	//SOCKET VARIABLES
	private final int ECHO = 7;
	private static Socket socket = null;
	private Scanner input;
	private PrintWriter output;
	
	
	public static void main(String[] args)
	{
		Echo frame = new Echo();
		frame.setSize(600,400);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				if (socket != null)
				{
					 try
						{
						socket.close();
						}
					 	catch (IOException ioEx)
					 		{
								System.out.println("\n* Unable to close link! *\n");
								System.exit(1);
							}
					System.exit(0);
				}
			}
		  }
		);
	}
	
	//constructor
	public Echo()
	{
		hostPanel = new JPanel();
		hostPrompt = new JLabel("Enter host name:");
		hostInput = new JTextField(20);
		hostInput.addActionListener(this);
		hostPanel.add(hostPrompt);
		hostPanel.add(hostInput);
		
		add(hostPanel, BorderLayout.NORTH);
		
		entryPanel = new JPanel();
		messagePrompt = new JLabel("Enter text:");
		lineToSend = new JTextField(15);
		entryPanel.add(messagePrompt);
		entryPanel.add(lineToSend);
		
		lineToSend.addActionListener(this);
		//Change field to editable when
		//host name entered�
		lineToSend.setEditable(false);
	
		add(entryPanel, BorderLayout.SOUTH);
		
		closeConnection = new JButton("Disconnect");
		closeConnection.addActionListener(this);
		//now add button to JPanel
		hostPanel.add(closeConnection);
		
		
		received = new JTextArea(10,15);
		//Following two lines ensure that word-wrapping
		//occurs within the JTextArea�
		received.setWrapStyleWord(true);
		received.setLineWrap(true);
		add(new JScrollPane(received),BorderLayout.CENTER);
		
		System.out.println("Testing now");
		lineToSend.setText("");	
		
	}
	
	
	public void actionPerformed(ActionEvent event)
	{
		if (event.getSource() == closeConnection)
			{
			  System.out.println("Exit button pressed");
			  
			   if (socket != null)
			      {
						try
						{
						 socket.close();
						}
						catch(IOException ioEx)
						{
							System.out.println("\n* Unable to close link!*\n");
							System.exit(1);
						}
						
					//block message to sent field and allow user to re-enter new ip address	
					hostInput.setEditable(true);						
					lineToSend.setEditable(false);
					hostInput.grabFocus();
			     }
			 return;
			}
		
	
		
		//anything in the line to send - move to host field
			if (event.getSource() == lineToSend)
			{
			   			
						//get the text you just typed and move it to the main field - set it to "" otherwise it will copy it twice
					   messageToSend = lineToSend.getText();								
					   received.append("You: " + messageToSend + "\n");
					   System.out.println("You: " + messageToSend + "\n");
					
						
						 try
							{
							 	//now send the message to the sever and wait for the call back to write to the text area the echo
							    output = new PrintWriter(socket.getOutputStream(),true);					  				    
							    output.println(messageToSend);							    	 
								
							    
							    input =  new Scanner(socket.getInputStream()); 
								String response = input.nextLine();
								received.append("\nSERVER> " + response + "\n");							
								
							
							
							}
							catch (UnknownHostException uhEx)
							{
								received.append("\n*** No such host! ***\n");
								hostInput.setText("");
							}
							catch (IOException ioEx)
							{
								received.append("\n*** " + ioEx.toString()	+ " ***\n");
							}
						 	finally
						 	{						 		
						 		lineToSend.setText("");		
						 		messageToSend = "";
						 	}
			
				
			}
			//Must have been entry into host field�
			
	   
			if (event.getSource() == hostInput)
			{			
				System.out.println("HostInput called");  //DEBUG
				
			    String host = hostInput.getText();
			    String response;
			    hostInput.setText("");
			    
			   try
				{
				   //connect here to server - add options for host port
					socket = new Socket(host,ECHO);
					
					//wait for the welcome message from the server
				    input =  new Scanner(socket.getInputStream()); 
					response = input.nextLine();
					received.append("\nSERVER> " + response + "\n");	
					//stop any more attempts to join the server once connected - 
					//allow the text box for messages to be used 
					hostInput.setEditable(false);
					lineToSend.setEditable(true);
				
				}
				catch (UnknownHostException uhEx)
				{
					received.append("\n*** No such host! ***\n");
					hostInput.setText("");
				}
				catch (IOException ioEx)
				{
				received.append("\n*** " + ioEx.toString()	+ " ***\n");
				}
		}
		
	}
	
}
