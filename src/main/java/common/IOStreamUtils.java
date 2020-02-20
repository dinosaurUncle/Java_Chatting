package common;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.Socket;
import java.util.Base64;

public class IOStreamUtils {
    private Socket socket = null;
    private static Logger logger = LogManager.getLogger(IOStreamUtils.class);
    public IOStreamUtils(Socket socket){
        this.socket = socket;
    }

    public void outputStreamExecute(JSONObject jsonObject){
        String serializeJsonObject = jsonObject.toJSONString();
        byte[] serializedObject;
        try {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                    oos.writeObject(serializeJsonObject);

                    serializedObject = baos.toByteArray();
                }
            }
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(Base64.getEncoder().encodeToString(serializedObject));
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    public JSONObject inputStreamExecute(){
        JSONObject result = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            String base64Member = objectInputStream.readObject().toString();
            byte[] inputSerialzedMember = Base64.getDecoder().decode(base64Member);
            try (ByteArrayInputStream bais = new ByteArrayInputStream(inputSerialzedMember)) {
                try (ObjectInputStream ois = new ObjectInputStream(bais)) {
                    JSONParser jsonParser = new JSONParser();
                    result = (JSONObject)jsonParser.parse(String.valueOf(ois.readObject()));
                    System.out.println("inputStreamExecute: " + result);
                }
            }
        } catch (IOException e){
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }finally {

        }
        return result;
    }
}
