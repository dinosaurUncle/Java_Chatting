package me.dinosauruncle.controller;

import me.dinosauruncle.common.HttpConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class ServerController{
    private static final Logger logger = LogManager.getLogger(ServerController.class);

    @Autowired
    HttpConnector httpConnector;

    public void classification(String key, JSONObject inputJsonObject, Map<String, String> map){
        String result = "";
        map.put("type", key);
        JSONObject innerJsonObject = (JSONObject)inputJsonObject.get(key);
        switch (key){
            case "login" :

                result = httpConnector.restApiCall("POST","login", innerJsonObject.toJSONString(),
                         true);
                break;
            case "checkId" :
                result = httpConnector.restApiCall("GET",
                        httpConnector.jsonExtractionKey(innerJsonObject.toJSONString(), "id"),
                        innerJsonObject.toJSONString(),
                        false);
                break;
            case "signup" :
                result = httpConnector.restApiCall("POST","", innerJsonObject.toJSONString(),
                        true);
                break;
            default:
                break;
        }
        map.put("result", result);
    }

}
