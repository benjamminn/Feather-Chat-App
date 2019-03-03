////////////////////////////////////////////////////////////////////////////////
// File: Client.java 
// Author(s): Benjamin Gordon 
// Purpose: Handles connections, disconnects, and commands.
////////////////////////////////////////////////////////////////////////////////

// Package
package Client;

//Imports
import java.net.*;

import Server.ClientHandler;

import java.io.*;



public class Client
			/* Handles connections, disconnects, and commands.*/
			{	
	
	        // The thread that receives strings.
			private static Receive recievethread;
			
	        // The thread that sends strings.
			private static Send sendthread;
			
			
			
			
			
			private static void safeOff()
			/* Safely shuts down the server.*/			
								{
								// Stops threads.
							   recievethread.threadStop();
							   sendthread.threadStop();
							
							   // Exits application.
							   System.exit(0);
								}
			
			
			
			public static void disconnect()
			
								{
				                // Stops threads
							    recievethread.threadStop();
							
							    sendthread.threadStop(); 
								
								}		
			
			
			private static void connect()
			/* Connects client to the server and starts communication threads.*/	
								{
								try
									{
									
									// Scanner accepts input.
									BufferedReader scanner =  new BufferedReader(new InputStreamReader(System.in));;
									
									
									// Server host, port, and username for the server the user wants to connect too.
									String host;
									int port;
									String username;
									
									
									
									// Gets the server host, port, and username from the user.
									System.out.print("\nCLIENT: Host:");
									host = scanner.readLine();	
								
									System.out.print("CLIENT: Port:");
									port = Integer.parseInt(scanner.readLine());	
	
									System.out.print("CLIENT: Username:");
									username = scanner.readLine(); 
								    
								    // Connects clientsocket to the server with the specified host and port.
									Socket clientsocket = new Socket(host, port);
									System.out.print("CLIENT: Connected to " + host + " on port " + port + ".");
									
									
									// Reads from the server.
								    BufferedReader instream = new BufferedReader(new InputStreamReader(clientsocket.getInputStream())); 
								    // Writes to the server.
								    PrintWriter outstream = new PrintWriter(new OutputStreamWriter(clientsocket.getOutputStream()));
								   
								    // Sends username to Server
								    outstream.println(username);
								    outstream.flush();
									
								    
								    
								    //Starts the receive and send thread.
									recievethread = new Receive(clientsocket, instream);
									recievethread.start(); 
								
									sendthread = new Send(clientsocket, outstream);
									sendthread.start(); 
									
									
							
								}
							   
								
								catch(Exception e)
								/* Restarts connection if issue.*/
									
									{
									System.out.print("Connection Failed Retrying...");
									connect();
									}
								}
			
			
			public static void reconnect()
			/* Reconnects the client.*/
			
								{
				                // Disconnects then connects.
							    disconnect();
								System.out.print("\nCLIENT: Restarting Connection...");    
							    connect();
								}
			
			
			public static void localCommands(String command)
		                     	/* Checks input for commands then acts on commands.*/
								{
				
				                
				                // Shuts down server.
								if (command.equals("/stop"))
								   {
									System.out.print("\nCLIENT: Shutting Down...");
									safeOff();
								   }
								
								
								// Disconnect from the current server and connects with a new one.
								if (command.equals("/disconnect"))
									{
									System.out.print("\nCLIENT: Disconnecting");  
								    reconnect();	
									}
									
							    // Detects an unrecognized command.
								if (command.substring(0).equals("/"))
									{ 
									System.out.print("\nCLIENT: Command not recognized."); 		
									}
								
								// About command.
								if (command.substring(0).equals("/about"))
									{ 	
									System.out.print("\nFeather Chat App Client: A classic console group messaging application.\r\n" + 
											"Copyright (C) 2019 Benjamin Gordon\r\n" + 
											"\r\n" + 
											"The Feather Chat App Client is free software: you can redistribute it and/or\r\n" + 
											"modify it under the terms of the GNU General Public License as published\r\n" + 
											"by the Free Software Foundation, either version 3 of the License, or\r\n" + 
											"(at your option) any later version.\r\n" + 
											"\r\n" + 
											"The Feather Chat App Client is distributed in the hope that it will be useful,\r\n" + 
											"but WITHOUT ANY WARRANTY; without even the implied warranty of\r\n" + 
											"MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General\r\n" + 
											"Public License for more details.\r\n" + 
											"\r\n" + 
											"You should have received a copy of the GNU General Public License\r\n" + 
											"along with the Feather Chat App Client.  If not, see <https://www.gnu.org/licenses/>.\""); 
									}
								
								}
			
			public static boolean serverInfoHandler(String command)
         	/* Handles server codes. The boolean returns prevent the message from being sent globally.*/
			
								{
				
				                // The server stop code signals the client that the server is shutting down.
				                // The user is alerted and the client reconnects.
								if (command.equals(": [serverstopcode]"))
								   {
									System.out.print("\nCLIENT: The Server was shut down.");
									reconnect();
									return false;
									
								   }
								   return true;
								}
								
			
			public static void main(String[] args)
         	/* Starts the server.*/
							 {
								
								// Start messages.
								System.out.print("Java Client");
								 
								
								System.out.print("\nFeather Chat App Client Copyright (C) 2019  Benjamin Gordon\r\n" + 
										"This program comes with ABSOLUTELY NO WARRANTY; for details type '/about'.\r\n" + 
										"This is free software, and you are welcome to redistribute it\r\n" + 
										"under certain conditions; one again type `'/about' for details.");
								
								// Initial connection.
							    connect();
							 }
			}
