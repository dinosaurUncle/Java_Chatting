package me.dinosauruncle.service;

import me.dinosauruncle.common.IOStreamUtils;
import me.dinosauruncle.server.ServerProcessThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

@Component
public class ResultDelivery {
    private static final Logger logger = LogManager.getLogger(ResultDelivery.class);

    public void resultDeliery(Map<String, String> map, IOStreamUtils ioStreamUtils,
                              Socket socket, Map<String, Socket> sockerMap){
        String type = map.get("type");
        String loginResult = map.get("result");
        JSONParser jsonParser = new JSONParser();
        JSONObject resultJsonObject = null;
        switch (type){
            case "login":
                try {
                    resultJsonObject = (JSONObject) jsonParser.parse(loginResult);
                    String loginState = resultJsonObject.get("login").toString();
                    if (loginState.equals("false")) {
                        String errorMessage =String.valueOf(resultJsonObject.get("message"));
                        logger.error(errorMessage);
                        JSONObject errorJsonObject = new JSONObject();
                        errorJsonObject.put("message", errorMessage);
                        ioStreamUtils.outputStreamExecute(errorJsonObject);
                        socket.close();
                        break;
                    }
                    JSONObject innerJsonObject = (JSONObject) resultJsonObject.get("account");

                    String loginId = innerJsonObject.get("id").toString();
                    if (loginState.equals("true")){
                        logger.info("로그인 성공");
                        sockerMap.put(loginId, socket);
                        ioStreamUtils.outputStreamExecute(resultJsonObject);
                    } else {
                        logger.info("로그인 실패");
                        ioStreamUtils.outputStreamExecute(resultJsonObject);
                        socket.close();
                    }

                } catch (IOException e){
                    String errorMessage = "로그인 실패 하였습니다. 아이디와 비밀번호를 확인해주세요";
                    logger.error(e.getMessage());
                    JSONObject errorJsonObject = new JSONObject();
                    errorJsonObject.put("message", errorMessage);
                    ioStreamUtils.outputStreamExecute(errorJsonObject);

                } catch (ParseException e){
                    String errorMessage = "로그인 실패 하였습니다. 아이디와 비밀번호를 확인해주세요";
                    JSONObject errorJsonObject = new JSONObject();
                    errorJsonObject.put("message", errorMessage);
                    ioStreamUtils.outputStreamExecute(errorJsonObject);
                    logger.error(errorMessage + e.getMessage());
                } catch (RuntimeException e) {

                }
                break;
            default:
                break;
        }


    }
}
