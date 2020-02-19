package client;

import common.IOStreamUtils;
import domain.Member;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

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
            Member member = new Member("아이유", "IU@naver.com", 26);
            ioStreamUtils = new IOStreamUtils(socket);
            ioStreamUtils.outputStreamExecute(member);
            Member inputMember = (Member)ioStreamUtils.inputStreamExecute();
            System.out.println(inputMember);
        } catch (IOException e){

        }
    }
}
