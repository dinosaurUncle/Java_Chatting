package me.dinosauruncle.service;

import me.dinosauruncle.common.IOStreamUtils;
import me.dinosauruncle.server.ServerProcessThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

@Component
public class ResultDelivery {
    private static final Logger logger = LogManager.getLogger(ResultDelivery.class);

    public void resultDeliery(String key, Map<String, String> map, IOStreamUtils ioStreamUtils,
                              Socket socket, Map<String, Socket> sockerMap){

        String result = map.get("result");

        JSONParser jsonParser = new JSONParser();
        JSONObject resultJsonObject = null;
        try {

            if (StringUtils.isEmpty(result)) throw new NullPointerException();

                resultJsonObject = (JSONObject) jsonParser.parse(result);
        switch (key) {
            case "login":
                String loginState = resultJsonObject.get(key).toString();
                if (loginState.equals("false")) {
                    String errorMessage = String.valueOf(resultJsonObject.get("message"));
                    logger.error(errorMessage);
                    JSONObject errorJsonObject = new JSONObject();
                    errorJsonObject.put("message", errorMessage);
                    ioStreamUtils.outputStreamExecute(errorJsonObject);
                    socket.close();
                    break;
                }
                JSONObject innerJsonObject = (JSONObject) resultJsonObject.get("account");

                String loginId = innerJsonObject.get("id").toString();
                if (loginState.equals("true")) {
                    resultJsonObject.put("connecting", "true");
                    resultJsonObject.put("message", "로그인 되었습니다");
                    logger.info("로그인 성공");
                    sockerMap.put(loginId, socket);
                    ioStreamUtils.outputStreamExecute(resultJsonObject);
                } else {
                    innerJsonObject.put("message", "계정 또는 비밀번호를 다시 입력해주세요");
                    logger.info("로그인 실패");
                    ioStreamUtils.outputStreamExecute(resultJsonObject);
                    socket.close();
                }


                break;
            case "checkId":
                resultJsonObject.clear();
                resultJsonObject.put("message", "이미 존재하는 아이디 입니다");
                resultJsonObject.put("isUse", false);
                ioStreamUtils.outputStreamExecute(resultJsonObject);
                socket.close();
                break;
            case "signup":
                JSONObject accountJsonObject = (JSONObject) resultJsonObject.get("account");
                resultJsonObject.put("message", "id: " +accountJsonObject.get("id") + " 회원 가입 되었습니다");
                ioStreamUtils.outputStreamExecute(resultJsonObject);
                socket.close();
                break;
            default:
                break;
            }
        } catch (IOException e){
            String errorMessage = "";
            switch (key) {
                case "login":
                    errorMessage = "계정 또는 비밀번호를 다시 입력해주세요";
                    break;
            }

            logger.error(e.getMessage());
            JSONObject errorJsonObject = new JSONObject();
            errorJsonObject.put("message", errorMessage);
            ioStreamUtils.outputStreamExecute(errorJsonObject);

        } catch (ParseException e){
            String errorMessage = "";
            switch (key) {
                case "login":
                    errorMessage = "계정 또는 비밀번호를 다시 입력해주세요";
                    break;
            }

            logger.error(e.getMessage());
            JSONObject errorJsonObject = new JSONObject();
            errorJsonObject.put("message", errorMessage);
            ioStreamUtils.outputStreamExecute(errorJsonObject);
        } catch (NullPointerException e) {
            String message = "";
            switch (key) {
                case "login":
                    message = "계정 또는 비밀번호를 다시 입력해주세요";
                    break;
                case "checkId" :
                    message = "사용 가능한 아이디 입니다.";
            }
            JSONObject errorJsonObject = new JSONObject();
            if (key.equals("checkId")) errorJsonObject.put("isUse", true);

            errorJsonObject.put("message", message);
            ioStreamUtils.outputStreamExecute(errorJsonObject);
        }

    }
}
