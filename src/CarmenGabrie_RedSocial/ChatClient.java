package CarmenGabrie_RedSocial;

import java.io.*;
import java.net.*;

public class ChatClient {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ChatClient(String username) throws Exception{

        socket = new Socket("localhost",5000);

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(),true);

        out.println(username);
    }

    public void send(String to,String msg){

        out.println(to+";msg;"+msg);
    }

    public String receive() throws Exception{

        return in.readLine();
    }
}