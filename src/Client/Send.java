////////////////////////////////////////////////////////////////////////////////
// File: Send.java 
// Author(s): Benjamin Gordon 
// Purpose: Handles info going out to the server.
////////////////////////////////////////////////////////////////////////////////

package Client;

//Imports
import java.net.*;
import java.io.*;
import java.util.*;
;

public class Send extends Thread
             /* Handles info going out to the server. */
			 {
	            // Sends info to server.
				private PrintWriter outstream;
				// The client socket.
				private Socket clientsocket;
				// Stops main loop.
				private Boolean threadStop = true;
				
				
				public Send(Socket clientsocket, PrintWriter outstream)
	                   /* Constructor.*/				        
						{
						 // Corresponds to global variables.
						 this.clientsocket = clientsocket;
						 this.outstream = outstream;
						 this.threadStop = threadStop;
						}
				
				public void threadStop()
		        /* Safely stops thread when requested. */
				
							{
							 // Stops main loop.
							 this.threadStop = false;
							 
						
							}
				
				 public void run()
			     /* Thread that handles info going out to the server.. */	
						    {
							 
							try 
							   {
					      
								while(this.threadStop)
							        {
						
								    // Makes scanner to accept user input.
								    BufferedReader scanner;	
									scanner =  new BufferedReader(new InputStreamReader(System.in));
									
									// Gets user input.
									String s;
									s = scanner.readLine();
									

									// Sends string to the server.
									outstream.println(s);
									outstream.flush(); 	
									
									// Checks if the input is local commands
									Client.localCommands(s);
						
							        
						
							        }
								
								
							    }
								
								
								catch(Exception e)
						        /* Reconnects user if error occurs. */	
								{
								// Message and reconnect.
								System.out.print("\n An error occured while getting a message.");
								Client.reconnect();
								}
						    }
			}
