package server;

import common.IOStreamUtils;
import org.json.simple.JSONObject;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    public static final int PORT = 5000;


    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        IOStreamUtils ioStreamUtils = null;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "박종훈");
        jsonObject.put("email", "m05214");
        jsonObject.put("age", "33");
        try {

            serverSocket = new ServerSocket();
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            System.out.println(hostAddress);
            serverSocket.bind(new InetSocketAddress("127.0.0.1", PORT));
            System.out.println("연결 기다림: " + hostAddress + " : " + PORT);
            Socket socket = serverSocket.accept();
            ioStreamUtils = new IOStreamUtils(socket);
            JSONObject inputJsonObject = ioStreamUtils.inputStreamExecute();
            System.out.println(inputJsonObject);
            ioStreamUtils.outputStreamExecute(jsonObject);

        } catch (IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                if (serverSocket != null && !serverSocket.isClosed()){
                    serverSocket.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
