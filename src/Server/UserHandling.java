////////////////////////////////////////////////////////////////////////////////
// File: UserHandling.java 
// Author(s): Benjamin Gordon 
// Last Edited 3/2/2019
// Purpose: Handles communication between its client and the server.
////////////////////////////////////////////////////////////////////////////////

package Server;

//Imports
import java.net.*;
import java.io.*;
import java.util.*;;

public class UserHandling extends Thread
            /* Handles communication between its client and the server.*/

			{
			    // The server socket.
				private Socket mysocket;
				// Gives input to user.
				private BufferedReader instream;
				// Takes output from user.
				private PrintWriter outstream;
				// Corresponding clientid.
				private int clientid;
				// Stops mainloop.
				private Boolean threadstop = true;	
				
				public UserHandling(Socket mysocket, BufferedReader instream, PrintWriter outstream, int clientid)
					    /* Constructor. */
						{
					     // Corresponds to global variable.
						 this.clientid = clientid;
						 this.mysocket = mysocket; 
						 this.instream = instream;
						 this.outstream = outstream;
						 this.threadstop = threadstop;
						 
						}
				
				public void Send(String message)
			                /* Sends a message to client. */
							{
						
							// Sends message.	
							outstream.println(message);
							outstream.flush();
							}
				
				public void threadStop()
				            /* Shuts down thread. */
							{
					         // Kills loop.
							 this.threadstop = false;
							}
				
				
				
				 public void run()
		                    /* Thread handles communication between its client and the server.*/
						    {
							 
							 
					
							try 
								{
						            
								// Gives input to client.
								BufferedReader instream = new BufferedReader(new InputStreamReader(this.mysocket.getInputStream()));
								// Takes output from user.
								PrintWriter outstream = new PrintWriter(new OutputStreamWriter(this.mysocket.getOutputStream())); 
								
								
							    // Listens for incoming messages from client.
								while(threadstop)
						        {
							    
						        // Gets message from client.
						        String message = instream.readLine();
						        
						        // Decides whether message a command or global message.
						        if (Server.clientCommands(message, this.clientid))
					        		{
						        	Server.globalPush(message, this.clientid);
					        		}
						        
						  	    
						  	    
						  	    
						  	    
								
						        }
								
								
							    }
								
								
							    // Removes user if disconnect.
								catch(Exception e)
								{
								// Prints user and disconnects upon error.
								System.out.print("\nA communication issue with client " +  this.clientid + " occured.");
								Server.removeUser(this.clientid);	
								}
				    }
			}
