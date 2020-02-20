package server;

import common.IOStreamUtils;
import common.Log4jConfig;

import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    public static final int PORT = 5000;

    public static void main(String[] args) {
        Logger logger =  new Log4jConfig(ChatServer.class).getLogger();
        ServerSocket serverSocket = null;
        IOStreamUtils ioStreamUtils = null;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "박종훈");
        jsonObject.put("email", "m05214");
        jsonObject.put("age", "33");
        try {
            File file = new File("./log4j.xml");
            file.createNewFile();
            serverSocket = new ServerSocket();
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            logger.info(hostAddress);
            serverSocket.bind(new InetSocketAddress("127.0.0.1", PORT));
            logger.info("연결 기다림: " + hostAddress + " : " + PORT);
            Socket socket = serverSocket.accept();
            ioStreamUtils = new IOStreamUtils(socket);
            JSONObject inputJsonObject = ioStreamUtils.inputStreamExecute();
            logger.info(inputJsonObject);
            ioStreamUtils.outputStreamExecute(jsonObject);
        } catch (IOException e){
            logger.error(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                if (serverSocket != null && !serverSocket.isClosed()){
                    serverSocket.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
