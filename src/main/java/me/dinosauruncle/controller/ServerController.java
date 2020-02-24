package me.dinosauruncle.controller;

import me.dinosauruncle.common.IOStreamUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class ServerController{
    private static final Logger logger = LogManager.getLogger(ServerController.class);
    @Autowired
    private IOStreamUtils ioStreamUtils;

    @Value("${server.ip}")
    String ip;

    @Value("${server.port}")
    int port;

    public void execute(){
        ServerSocket serverSocket = null;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "박종훈");
        jsonObject.put("email", "m05214");
        jsonObject.put("age", "33");
        while(true){
            try {
                serverSocket = new ServerSocket();
                String hostAddress = InetAddress.getLocalHost().getHostAddress();
                logger.info(hostAddress);

                serverSocket.bind(new InetSocketAddress(ip, port));

                logger.info("연결 기다림: " + hostAddress + " : " + port);
                Socket socket = serverSocket.accept();
                ioStreamUtils.setSocket(socket);
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

}
