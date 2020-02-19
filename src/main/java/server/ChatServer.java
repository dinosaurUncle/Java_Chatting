package server;

import common.IOStreamUtils;
import domain.Member;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ChatServer {
    public static final int PORT = 5000;


    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        IOStreamUtils ioStreamUtils = null;
        Member member = new Member("박종훈", "m05214@naver.com", 33);

        try {
            serverSocket = new ServerSocket();
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            System.out.println(hostAddress);
            serverSocket.bind(new InetSocketAddress("127.0.0.1", PORT));
            System.out.println("연결 기다림: " + hostAddress + " : " + PORT);
            Socket socket = serverSocket.accept();
            ioStreamUtils = new IOStreamUtils(socket);
            Member inputMember = (Member)ioStreamUtils.inputStreamExecute();
            System.out.println(inputMember);
            ioStreamUtils.outputStreamExecute(member);

        } catch (IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null && !serverSocket.isClosed()){
                    serverSocket.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
