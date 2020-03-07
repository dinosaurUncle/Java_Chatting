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
import java.util.HashMap;
import java.util.Map;

@Component
public class RequestAccepter {
    private static final Logger logger = LogManager.getLogger(RequestAccepter.class);

    @Autowired
    ServerProcessThread serverProcessThread;

    @Value("${self.server.ip}")
    String ip;

    @Value("${self.server.port}")
    int port;

    public Map<String, Socket> sockerMap;

    public void execute(){
        sockerMap = new HashMap<String, Socket>();
        ServerSocket serverSocket = null;
        InetSocketAddress inetSocketAddress = new InetSocketAddress(ip, port);

            try {
                serverSocket = new ServerSocket();
                String hostAddress = InetAddress.getLocalHost().getHostAddress();

                try {
                    logger.info(serverSocket.isBound());
                    serverSocket.bind(inetSocketAddress);

                }catch (BindException e){

                    logger.error(e.getMessage());
                    serverSocket.close();
                }

                while(true){
                    logger.info("접속된 소켓리스트: " + sockerMap);
                    logger.info(hostAddress);
                    logger.info("연결 기다림: " + hostAddress + " : " + port);
                    Socket socket = serverSocket.accept();
                    serverProcessThread.run(socket, sockerMap);
                }
            } catch (IOException e){
                logger.error(e.getMessage());
                e.printStackTrace();
            }
    }
}
