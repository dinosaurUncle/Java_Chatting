package me.dinosauruncle;

import me.dinosauruncle.controller.ServerController;
import me.dinosauruncle.core.RequestAccepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ChatServer implements ApplicationRunner {

    @Autowired
    RequestAccepter requestAccepter;
    public static void main(String[] args) {
        SpringApplication.run(ChatServer.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        requestAccepter.execute();
    }
}
