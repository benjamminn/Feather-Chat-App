////////////////////////////////////////////////////////////////////////////////
// File: ClientObject.java 
// Author(s): Benjamin Gordon 
// Last Edited 3/2/2019
// Purpose: Object that holds all the attributes for a dedicated user.
////////////////////////////////////////////////////////////////////////////////

package Server;

//Imports
import java.net.*;
import java.io.*;
import java.util.*;;

public class ClientObject
             /* Object that holds all the attributes for a dedicated user. */

			{
		   
	        // The thread that handles with user.
			private UserHandling threadid;
			
			// The user name of the this user.
			private String username;
			
			public ClientObject(UserHandling threadid, String username)
                   /* Constructor.*/
					{
			        // Corresponds to global variable.
					this.threadid = threadid;
					this.username = username;
					}
			
			public UserHandling getThread()
                                /* Returns thread of the user.*/
								{
								return this.threadid;	
								}
			
			public String getUserName()
	                        /* Returns username of the user.*/
							{
							return this.username;	
							}
				
			}