import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class client {
	public Socket client = null;
	public DataInputStream dis = null;
	public DataOutputStream dos = null;
	public FileInputStream fis = null;
	public FileOutputStream fos = null;
	public BufferedReader br = null;
	public String inputFromUser = "";
	public static void main(String[] args)
	{
		client c = new client();
		c.doConnections();	
	}
	public void doConnections() 
	{
		try{
		  InputStreamReader isr = new InputStreamReader(System.in);
		  br = new BufferedReader(isr);
		  client = new Socket("localhost",8888);
		  dis = new DataInputStream(client.getInputStream());
		  dos = new DataOutputStream(client.getOutputStream());
		  
		}
		catch(Exception e)
		{
		  	System.out.println("Unable to Connect to Server");
		}
		
		while(true)
		{
		try{
    System.out.println("Please Make a Choice : \n1.send file \n2receive file \nYour Choice: ");
		 inputFromUser = br.readLine();
		 int i = Integer.parseInt(inputFromUser);
		 switch(i)
		 {
			case 1: sendFile(); break;
			case 2: receiveFile(); break;
			default: System.out.println("Invalid Option !");
		 } 
		 }
		catch(Exception e)
		{
		  System.out.println("Some Error Occured!");
		}
		}	
	}
	public void sendFile() {
		try{
			String filename="",filedata="";		
			File file;
			byte[] data;
			System.out.println("Enter the filename: ");
			filename = br.readLine();
			file = new File(filename);
			if(file.isFile())
			{	
				fis = new FileInputStream(file);
				data = new byte[fis.available()];
				fis.read(data);
				fis.close();
				filedata = new String(data);
				dos.writeUTF("FILE_SEND_FROM_CLIENT");
				dos.writeUTF(filename);		
				dos.writeUTF(filedata);
				System.out.println("File already sent to the server " + filename );
			}
			else
			{
				System.out.println("File Not Found!");
			}
		}
		catch(Exception e)
		{

		}		
	}
	public void receiveFile(){
		try{
			String filename="",filedata="";	
			System.out.println("Enter the filename: ");
			filename = br.readLine();
			dos.writeUTF("DOWNLOAD_FILE");
			dos.writeUTF(filename);
			filedata = dis.readUTF();
		        if(filedata.equals(""))
			{
			  	System.out.println("No Such File");
			}
			else
		{
				fos = new FileOutputStream(filename);
				fos.write(filedata.getBytes());
				fos.close();
				System.out.println("File receibed successfully  " + filename + " 775  seconds");
			}			
		}
		catch(Exception e)
		{
		}
	}

}
