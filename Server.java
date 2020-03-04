import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Server {

	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;
	private DataInputStream dataIn = null;
	private DataOutputStream dataOut = null;
	private Scanner scan = new Scanner(System.in); //To take input from terminal
	private String message = null;

	//To Store Local Date and Time
	private LocalDate date = null;
	private LocalTime time = null;
	
	//To Format Date and Time
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MMM-YYYY");
	private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");;

	public void RunServer(int port)
	{
		try {
			serverSocket = new ServerSocket(port);
			clientSocket = serverSocket.accept();
			
			dataIn = new DataInputStream(clientSocket.getInputStream());
			dataOut = new DataOutputStream(clientSocket.getOutputStream());
			
			//This loop continues until client sends "EXIT" message
			while(true)
			{	
				message = (String) dataIn.readUTF(); //reading string from client
				if(message.equals("EXIT")) break;
				else if(message.equals("Hello")) dataOut.writeUTF("Welcome");
				else if(message.equals("Date")){
					date = LocalDate.parse(LocalDate.now().toString()); //converting local date format to our given format
					dataOut.writeUTF(dateFormatter.format(date));
				}
				else if(message.equals("Time")){
					time = LocalTime.parse(LocalTime.now().toString()); //converting local time format to our given format
					dataOut.writeUTF(timeFormatter.format(time));
				}
				else if(message.equals("DateTime")){
					date = LocalDate.parse(LocalDate.now().toString()); //converting local date format to our given format
					time = LocalTime.parse(LocalTime.now().toString()); //converting local time format to our given format
					dataOut.writeUTF("Time : "+timeFormatter.format(time)+"\t"+"Date : "+dateFormatter.format(date));
				}
				else dataOut.writeUTF("Please Send Valid Message");
			}
			
			scan.close();
			dataIn.close();
			dataOut.close();
			clientSocket.close();
			serverSocket.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public static void main(String[] args)
	{
		Server server = new Server();
		server.RunServer(3333);
	}
}

