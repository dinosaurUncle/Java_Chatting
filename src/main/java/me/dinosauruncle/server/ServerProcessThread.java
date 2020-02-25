package me.dinosauruncle.server;


import me.dinosauruncle.common.IOStreamUtils;
import me.dinosauruncle.core.RequestAccepter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;

public class ServerProcessThread extends Thread {
    private static final Logger logger = LogManager.getLogger(ServerProcessThread.class);

    private IOStreamUtils ioStreamUtils;

    private Socket socket;
    public ServerProcessThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        ioStreamUtils = new IOStreamUtils();
        ioStreamUtils.setSocket(socket);
        JSONObject inputJsonObject = ioStreamUtils.inputStreamExecute();
        logger.info(inputJsonObject);
        ioStreamUtils.outputStreamExecute(new JSONObject());
        try {
            socket.close();
        } catch (IOException e){
            logger.error(e.getMessage());
        }
    }



}
