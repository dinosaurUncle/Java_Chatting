package me.dinosauruncle.server;


import java.io.*;
import java.nio.charset.StandardCharsets;

import java.net.Socket;
import java.util.List;


public class ChatServerProcessThread extends Thread {
    private String nickname = null;
    private Socket socket = null;
    List<PrintWriter> listWriters = null;

    ChatServerProcessThread(Socket socket, List<PrintWriter> listWriters){
        this.socket = socket;
        this.listWriters = listWriters;
    }

    @Override
    public void run() {
        try{
            BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)));
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));

            while(true) {
                String request = bufferedReader.readLine();

                if (request == null){
                    //logger.info("클라이언트로부터 연결 끊김");

                    break;
                }
            }
        }catch (IOException e) {
            //logger.info(this.nickname + "님이 채팅방을 나갔습니다");
            //logger.error(e.getMessage());
        }
    }

    private void doQuit(PrintWriter writer) {
        removeWriter(writer);

        String data = this.nickname + "님이 퇴장했습니다.";
        broadcast(data);
    }

    private void removeWriter(PrintWriter writer) {
        synchronized (listWriters) {
            listWriters.remove(writer);
        }
    }

    private void doMessage(String data) {
        broadcast(this.nickname + ":" + data);
    }

    private void broadcast(String data) {
        synchronized (listWriters){
            for(PrintWriter writer: listWriters) {
                writer.println(data);
                writer.flush();
            }
        }
    }


}
