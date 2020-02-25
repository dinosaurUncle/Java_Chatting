package me.dinosauruncle.core;

import me.dinosauruncle.common.IOStreamUtils;
import me.dinosauruncle.controller.ServerController;
import me.dinosauruncle.server.ServerProcessThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.*;

@Component
public class RequestAccepter {
    private static final Logger logger = LogManager.getLogger(RequestAccepter.class);


    @Value("${server.ip}")
    String ip;

    @Value("${server.port}")
    int port;

    public void execute(){
        ServerSocket serverSocket = null;
        InetSocketAddress inetSocketAddress = new InetSocketAddress(ip, port);
        while(true){
            try {
                serverSocket = new ServerSocket();
                String hostAddress = InetAddress.getLocalHost().getHostAddress();

                try {
                    logger.info(serverSocket.isClosed());
                    serverSocket.bind(inetSocketAddress);

                }catch (BindException e){
                    logger.error(e.getMessage());
                    serverSocket.close();
                    continue;
                }
                logger.info(hostAddress);

                logger.info("연결 기다림: " + hostAddress + " : " + port);
                Socket socket = serverSocket.accept();
                new ServerProcessThread(socket).start();
            } catch (IOException e){
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
