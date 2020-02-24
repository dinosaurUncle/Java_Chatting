package me.dinosauruncle;

import me.dinosauruncle.controller.ServerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChatServer implements ApplicationRunner {

    @Autowired
    ServerController serverController;
    public static void main(String[] args) {
        SpringApplication.run(ChatServer.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        serverController.execute();
    }
}
