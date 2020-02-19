package common;

import domain.Member;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.Base64;

public class IOStreamUtils {
    private Socket socket = null;
    private static Logger logger = LogManager.getLogger(IOStreamUtils.class);
    public IOStreamUtils(Socket socket){
        this.socket = socket;
    }

    public void outputStreamExecute(Object object){
        byte[] serializedObject;
        try {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                    oos.writeObject(object);

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

    public Object inputStreamExecute(){
        Object result = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            String base64Member = objectInputStream.readObject().toString();
            byte[] inputSerialzedMember = Base64.getDecoder().decode(base64Member);
            try (ByteArrayInputStream bais = new ByteArrayInputStream(inputSerialzedMember)) {
                try (ObjectInputStream ois = new ObjectInputStream(bais)) {
                    result = ois.readObject();
                }
            }
        } catch (IOException e){
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {

        }
        return result;
    }
}
