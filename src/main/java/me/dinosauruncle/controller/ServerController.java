package me.dinosauruncle.controller;

import me.dinosauruncle.common.HttpConnector;
import me.dinosauruncle.common.IOStreamUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class ServerController{
    private static final Logger logger = LogManager.getLogger(ServerController.class);

    @Autowired
    HttpConnector httpConnector;

    public void classification(JSONObject inputJsonObject, Map<String, String> map){
        String result = "";
        Set<String> keySet = inputJsonObject.keySet();
        String key = "";
        for (String eachKey : keySet){
            key = eachKey;
        }
        map.put("type", key);
        switch (key){
            case "login" :
                JSONObject innerJsonObject = (JSONObject)inputJsonObject.get(key);
                result = httpConnector.post("/login", innerJsonObject.toJSONString());
                break;
            default:
                break;
        }
        map.put("result", result);
    }

}
