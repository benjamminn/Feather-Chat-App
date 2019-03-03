////////////////////////////////////////////////////////////////////////////////
// File: Recieve.java 
// Author(s): Benjamin Gordon 
// Purpose: Handles strings coming from the server.
////////////////////////////////////////////////////////////////////////////////

package Client;

//Imports
import java.net.*;
import java.io.*;
import java.util.*;

public class Receive extends Thread
             /* Handles info coming from the server. */
		   	{
	            // Sends string to the server.
				private BufferedReader instream;
		        // The client Socket 
				private Socket clientsocket;
				// Controls main loop
				private Boolean threadStop = true;
				
				public Receive(Socket clientsocket, BufferedReader instream)
	                   /* Constructor. */		
				        {
					     // Corresponds to global variables.
						 this.clientsocket = clientsocket;
						 this.instream = instream;
						 this.threadStop = threadStop;
						 
						}
				
				
				
				public void threadStop()
		        /* Safely stops thread when requested. */
					{
					 // Stops main loop.
					 this.threadStop = false;
					}
				 
				 public void run()
	             /* This thread receives info coming from the server. */					
						    {
							 
							try 
							    {
					            
								// This loop takes incoming strings from the server. 
								while(this.threadStop)
							        {
									
							       // Gets string from the server.
							        String s = instream.readLine();
							        
							        // Checks for server code.
									if(Client.serverInfoHandler(s))
										{
										System.out.print("\n" + s);			 	
										}
						
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
