import java.io.*;
import java.net.*;

    public class TCPServer {
       public static void main(String[] args) throws IOException 
       {
      	
			// Variables for setting up connection and communication
        Socket Socket = null; // socket to connect with ServerRouter
        PrintWriter out = null; // for writing to ServerRouter
        BufferedReader in = null; // for reading form ServerRouter
		InetAddress addr = InetAddress.getLocalHost();
			
			//this line gives hostName/hostIP, which can be used for setup of
			//router name and client address (when used locally)
		String host = addr.getHostAddress(); // Server machine's IP
		String[] iNetHost = new String[2];
			
		System.out.println("Host: " + addr);

		iNetHost = addr.toString().split("/");
						
		System.out.println("Currently the serverip is " + iNetHost[1]);
		System.out.println("Currently the serverhost is " + iNetHost[0]);
			
		String routerName = iNetHost[0]; // ServerRouter host name
		int SockNum = 5555; // port number
			
			// Tries to connect to the ServerRouter
			//Have to launch the ServerRouter first in order for the connection to open
         try {
            Socket = new Socket(routerName, SockNum);
            out = new PrintWriter(Socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
         } 
             catch (UnknownHostException e) {
               System.err.println("Don't know about router: " + routerName);
               System.exit(1);
            } 
             catch (IOException e) {
               System.err.println("Couldn't get I/O for the connection to: " + routerName);
               System.exit(1);
            }
				
      	// Variables for message passing			
         String fromServer; // messages sent to ServerRouter
         String fromClient; // messages received from ServerRouter      
 			String address = iNetHost[1]; //"10.5.3.196"; // destination IP (Client)
			
			// Communication process (initial sends/receives)
			out.println(address);// initial send (IP of the destination Client)
			fromClient = in.readLine();// initial receive from router (verification of connection)
			System.out.println("ServerRouter: " + fromClient);
			         
			// Communication while loop
      	while ((fromClient = in.readLine()) != null) 
      	{
            System.out.println("Client said: " + fromClient);
            if (fromClient.equals("Bye.")) // exit statement
					break;
				fromServer = fromClient.toString().toUpperCase(); // converting received message to upper case
				System.out.println("Server said: " + fromServer);
            out.println(fromServer); // sending the converted message back to the Client via ServerRouter
         }
			
			// closing connections
         out.close();
         in.close();
         Socket.close();
      } //end of main
   }
