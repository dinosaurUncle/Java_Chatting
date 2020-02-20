package client;

import common.IOStreamUtils;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ChatClientApp {
    private static final String SERVER_IP ="127.0.0.1";
    private static final int SERVER_PORT = 5000;

    public static void main(String[] args) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
            IOStreamUtils ioStreamUtils = null;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", "아이유");
            jsonObject.put("email", "IU@naver.com");
            jsonObject.put("age", "22");
            ioStreamUtils = new IOStreamUtils(socket);
            ioStreamUtils.outputStreamExecute(jsonObject);
            JSONObject inputJsonObject = ioStreamUtils.inputStreamExecute();
            System.out.println(inputJsonObject);
        } catch (IOException e){

        }
    }
}
