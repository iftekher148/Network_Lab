import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	
	private Socket clientSocket = null;
    private DataInputStream dataIn = null;
    private DataOutputStream dataOut = null;
    private Scanner scan = new Scanner(System.in);
    private String sentMessage = null;
    private String receivedMessage = null;

    public void RunClient(String serverAddress, int port)
    {
        try {
            clientSocket = new Socket(serverAddress, port);
            System.out.println("Success");

            dataIn = new DataInputStream(clientSocket.getInputStream());
            dataOut = new DataOutputStream(clientSocket.getOutputStream());
			
            while(true)
            {
                System.out.print("Type Your Message : ");
                sentMessage = scan.nextLine();
                dataOut.writeUTF(sentMessage);
                if(sentMessage.equals("EXIT")) break;
            
                receivedMessage = (String) dataIn.readUTF();
                System.out.println("Server's Reply : " + receivedMessage);
            }
            
            scan.close();
            dataIn.close();
            dataOut.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }



	public static void main(String[] args)
	{
        Client client = new Client();
        client.RunClient("127.0.0.1", 3333);
	}
}


