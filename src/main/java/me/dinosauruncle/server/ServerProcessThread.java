package me.dinosauruncle.server;


import me.dinosauruncle.common.HttpConnector;
import me.dinosauruncle.common.IOStreamUtils;
import me.dinosauruncle.controller.ServerController;
import me.dinosauruncle.service.ResultDelivery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

@Component
public class ServerProcessThread {
    private static final Logger logger = LogManager.getLogger(ServerProcessThread.class);

    @Autowired
    private IOStreamUtils ioStreamUtils;

    @Autowired
    HttpConnector httpConnector;

    @Autowired
    ServerController serverController;

    @Autowired
    ResultDelivery resultDelivery;

    private Socket socket;

    private Map<String, String> map;

    @Async("threadPoolTaskExecutor")
    public void run(Socket socket, Map<String, Socket> sockerMap) {
        map = new HashMap<String, String>();
        ioStreamUtils.setSocket(socket);
        while(!socket.isClosed()){
            logger.info("연결되어 있는 소켓: " + sockerMap);
            JSONObject inputJsonObject = ioStreamUtils.inputStreamExecute();
            logger.info(inputJsonObject);
            String apiKey = ioStreamUtils.getKey(inputJsonObject);
            serverController.classification(apiKey, inputJsonObject, map);
            resultDelivery.resultDeliery(apiKey, map, ioStreamUtils, socket, sockerMap);
        }
    }



}
