////////////////////////////////////////////////////////////////////////////////
// File: Server.java 
// Author(s): Benjamin Gordon 
// Last Edited 3/2/2019
// Purpose: Handles console commands and server functions.
////////////////////////////////////////////////////////////////////////////////


// Package
package Server;

//Imports
import java.net.*;
import java.io.*;
import java.util.*;




public class Server
             /* Handles console commands and server functions. */ 
			{ 
			
				// Global Array that holds pointer to each clients objects.
				private static List<ClientObject> clientinfo = new ArrayList<ClientObject>();
				
			    
				
				
				public Server(int port, int ip)
				       /* Constructor */
				       {
					   // Global Array that holds pointer to each clients objects.
					    this.clientinfo = clientinfo;
					    
			
			
				       }
				
				public static void addUser(UserHandling threadid, String name)
							         /* Adds a new user object. */
							        {
								    // Creates a new object for each user.
								    clientinfo.add(new ClientObject(threadid, name));
								    		
							        }
				
				public static void removeUser(int index)
							       /* Removes a user cleanly. */
						            {
						             
								     // Kills user thread and object
								     UserHandling thread = clientinfo.get(index).getThread();
								     thread.threadStop();
							         clientinfo.remove(index);
						            }
							
				private static void safeOff(ClientHandler clienthandler)
			            			/* Safely kills all the threads and terminates the program. */
								    {
					
					
								    System.out.print("Shutting Down...");	
										
								    
								     // Runs through the client object list and deletes all the
								     // threads and objects.
								     Iterator<ClientObject> traverse = clientinfo.iterator();
								     
								     while(traverse.hasNext())
								          {
								    	 
								    	 // Ends threads then the object.
								    	 UserHandling thread = traverse.next().getThread();
								    	 thread.threadStop();
								    	 clientinfo.remove(traverse.next());
								    
								    	  
								          }
								          
							    	 // Ends the client handler.								     
								     clienthandler.threadStop();
								    	 
								     // Closes main program.
								     System.exit(0);
								    }
				
			
				
			
				
				public static void globalPush(String message, int senderclientid)
    			                   /* Sends messages to all users. */
				
						           	{
					                 // Iterator for clientinfo arrau.
								     Iterator<ClientObject> traverse = clientinfo.iterator();
								    
								     // Loop runs through all users.
								     while(traverse.hasNext())
								          {
								    	 UserHandling thread = traverse.next().getThread();
								    	 
								    	 // Added to the beginning of the outgoing message.
								    	 String username;
								    	 
								    	 
								    	 
								    	 if (senderclientid == -1)
									    	 // For server messages.
									         {
									      	 username = "SERVER";
									         }
								    	 else if (senderclientid == -2)
										     // For nameless messages.
									    	 {
									    	 username = "";	 
								    	 }
								    	 else 
									    	 {
									    	 username = clientinfo.get(senderclientid).getUserName();
										     // For messages from a specific user.
									    	 }
								    	 
								    	 // Sends a message to every user object that is iterated.
								    	 String output = username+ ": " + message;
						                 thread.Send(output);
								         }
								    		 
						           	}
				
				private static void serverCommands(String command, ClientHandler clienthandler)
                                    /* Handles commands input into the server.*/
								    {
					
					               // The /stop command shuts down the server.
								    if (command.equals("/stop"))
								       {
								    	// The stop code allows the clients to react appropriately. 
								    	globalPush("[serverstopcode]", -2);
								    	
								    	// Trigger safe off.
								    	safeOff(clienthandler);
								    	
								       }
								    
								    
								    // Handles incorrect commands.
								    if (command.substring(0).equals("/"))
									    {   	
									    	System.out.print("\nCLIENT: Command not Recognized.");   	
									    }
								    
								    // Handles incorrect commands.
								    if (command.equals("/about"))
									    {   	
								    	System.out.print("\nFeather Chat App Server: A classic console group messaging application.\r\n" + 
								    			"Copyright (C) 2019 Benjamin Gordon\r\n" + 
								    			"\r\n" + 
								    			"The Feather Chat App Server   is free software: you can redistribute it and/or\r\n" + 
								    			"modify it under the terms of the GNU General Public License as published\r\n" + 
								    			"by the Free Software Foundation, either version 3 of the License, or\r\n" + 
								    			"(at your option) any later version.\r\n" + 
								    			"\r\n" + 
								    			"The Feather Chat App Server is distributed in the hope that it will be useful,\r\n" + 
								    			"but WITHOUT ANY WARRANTY; without even the implied warranty of\r\n" + 
								    			"MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General\r\n" + 
								    			"Public License for more details.\r\n" + 
								    			"\r\n" + 
								    			"You should have received a copy of the GNU General Public License\r\n" + 
								    			"along with the Feather Chat App Server.  If not, see <https://www.gnu.org/licenses/>.\""); 	
									    }
								      
								    }
						
						public static boolean clientCommands(String command, int index)
                                             /* Makes the server reacts appropriately to a local client commands. The
                                                boolean returns ensure the command is not printed globally. */
										    {
							
							                // Handles client disconnects.
										    if (command.equals("/disconnect") || command.equals("/stop"))
										       {
										    	
										    	// Both local and global disconnects notices and removes user.
											    System.out.print("Client " + clientinfo.get(index).getUserName() + "has disconnected.");
										    	globalPush(clientinfo.get(index).getUserName() + " has disconnected.", -1);
										        removeUser(index);
										
										
										     
										        
										        return false;
										    	
										       }
										    
							                // About client command. 
										    if (command.equals("/about"))
										       {
										        return false;
										       }
										    
										    
											return true;
										    }
									
									
			   public static void main(String[] args)
			                           /* Starts program and listens for local commands.*/
							       {
									try
										{
											
									
									    // Sets up socket and makes client handler.
									    System.out.print("Java Server");	
										System.out.print("\nFeather Chat App Server Copyright (C) 2019  Benjamin Gordon\r\n" + 
												"This program comes with ABSOLUTELY NO WARRANTY; for details type '/about'.\r\n" + 
												"This is free software, and you are welcome to redistribute it\r\n" + 
												"under certain conditions; one again type `'/about' for details.");
										ServerSocket serversocket = new ServerSocket(2019);
										ClientHandler clienthandler = new ClientHandler(serversocket);
										clienthandler.start(); 
										
										
										// Listens for local commands 
										while(true)
											
											{
								
											// Feeds local input into serverCommands to check for commands.
											BufferedReader scanner =  new BufferedReader(new InputStreamReader(System.in));
											String s;	
											s = scanner.readLine();
											serverCommands(s, clienthandler);
	
											}
											
										
									    }
										
								
								   catch(IOException error)
									  /* Prints I/O Errors.*/
									   {
										error.printStackTrace();
									   }		
						 	       }
			       
			
			
			}
