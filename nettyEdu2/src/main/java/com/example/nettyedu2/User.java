package com.example.nettyedu2;

import io.netty.channel.ChannelHandlerContext;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;

import java.util.HashMap;

public class User {
    HashMap<String, Connection> clientList = new HashMap<String, Connection>();

    public synchronized void addClient(String userName, Connection connection) {
        clientList.put(userName, connection);
        System.out.println("[Server]" + userName + "등록됨");
        System.out.println("[Server]" + clientList);
//        broadcastMessage("[Server]: " + userName + "님이 입장하였습니다.");
    }

//    public synchronized void removeClient(String userName) {
//        clientList.remove(userName);
//        broadcastMessage("[Server]: " + userName + "님이 나갔습니다.");
//    }

    public synchronized void broadcastMessage(String message) {
        for (Connection connection : clientList.values()) {
            connection.outbound().sendString(Mono.just("[Server]: " + message + "\r\n")).then().subscribe();
        }
    }
}
