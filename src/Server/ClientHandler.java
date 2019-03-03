////////////////////////////////////////////////////////////////////////////////
// File: ClientHandler.java 
// Author(s): Benjamin Gordon 
// Last Edited 3/2/2019
// Purpose: Connects new clients and creates threads to handle each user.
////////////////////////////////////////////////////////////////////////////////

// Package
package Server;

//Imports
import java.net.*;
import java.io.*;
import java.util.*;

public class ClientHandler extends Thread
            /* Connects new clients and creates threads to handle each user. */
			{
	
	        // The server socket.
			private ServerSocket serversocket;
			// ID of client in array.
			private int clientid = 0;	
			// Determines weather to continue the thread or not.
			private Boolean threadstop = true;	
			
			public ClientHandler(ServerSocket serversocket)
                  /* Constructor. */
					{
				
				     // Server socket.
					 this.serversocket = serversocket; 
					 
					 // Current ID of client in array.
					 this.clientid = clientid;
					 
					 // Determines whether to continue the thread or not.
					 this.threadstop = threadstop;
					}
			
			public void threadStop()
                        /* Shuts down thread safely upon call. */
						{
				
				         // Stops main loop.
						 this.threadstop = false;
						}
			
			
			
			 public void run()
                         /* This thread connects new clients and creates threads to handle each user. */
					    {
				
						try 
						
							{
                                // Loops runs to continue accepting clients
								while(this.threadstop)
									
									{	
									
									System.out.print("\nWaiting for clients to connect...");
									Socket current = serversocket.accept();
									    
										
									// Gives input to client.
									BufferedReader instream = new BufferedReader(new InputStreamReader(current.getInputStream()));
									// Takes output from user.
									PrintWriter outstream = new PrintWriter(new OutputStreamWriter(current.getOutputStream())); 
									
									// Creates new user handler thread.
									UserHandling newuserhandler = new UserHandling(current, instream, outstream, clientid);
										
							        // Gets username from client and adds the user.
							        String username = instream.readLine();
							        Server.addUser(newuserhandler, username);
							        
							        // Increments the current client ID.
							        this.clientid += 1;
							        
							        
							        System.out.print("\nClient Found:" + " INFO - " + current  + " USERNAME - " + username);
							        
							        // Global welcome message.
									Server.globalPush("Welcome " + username + " to this server!", -1);
									outstream.flush(); 
									
									//Starts new thread.
									newuserhandler.start(); 
									
						
						
									}
								
							    }
								
								
								catch(Exception e)
						             /* This handles errors with connecting the client. */
									{
									// Prints user and disconnects upon error.
									System.out.print("\nAn  issue with connecting client " +  this.clientid + " occured.");
									Server.removeUser(this.clientid);	;	
									}
							 
							}
				
				
			}
