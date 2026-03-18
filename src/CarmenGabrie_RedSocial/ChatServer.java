package CarmenGabrie_RedSocial;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {

    private static Map<String,PrintWriter> clients = new HashMap<>();

    public static void main(String[] args) throws Exception {

        ServerSocket server = new ServerSocket(5000);

        System.out.println("Server running...");

        while(true){

            Socket socket = server.accept();

            new ClientHandler(socket).start();
        }
    }

    public void startServer() {

    try {

        ServerSocket server = new ServerSocket(5000);

        System.out.println("ChatServer iniciado en puerto 5000");

        while (true) {

            Socket socket = server.accept();

            new ClientHandler(socket).start();

        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    static class ClientHandler extends Thread{

        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private String username;

        public ClientHandler(Socket socket) throws Exception{

            this.socket = socket;

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);

            username = in.readLine();

            clients.put(username,out);
        }

        public void run(){

            try{

                String msg;

                while((msg=in.readLine())!=null){

                    String[] parts = msg.split(";",3);

                    String to = parts[0];
                    String message = parts[2];

                    PrintWriter receiver = clients.get(to);

                    if(receiver!=null){

                        receiver.println(username+";"+message);

                    }

                }

            }catch(Exception e){
            }
        }
        
    }
}