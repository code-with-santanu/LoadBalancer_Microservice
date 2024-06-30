package com.santanu.LoadBalancer.model;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Component
public class ServerPool {

    // A list that holds the information about all servers
    private ArrayList<Server> serverGroup = new ArrayList<Server>();

    @Getter
    @Value("0")
    private Integer totalServers; // serverId starts with 0

    public ServerPool() {
    }

    // To add single server to serverPool
    public void addServer(Server server)
    {
        // set the serverId
        server.setId(totalServers);
        serverGroup.add(server);
        totalServers++;
    }

    // To add multiple server to serverPool
    public void addServer(ArrayList<Server> serversList)
    {
        serverGroup.addAll(serversList);
    }

    // Return all the servers
    public ArrayList<Server> getAllServers()
    {
        return serverGroup;
    }

    public Server getServerById(int serveId) {
        return serverGroup.get(serveId);
    }

    // Update a server information
    public Server updateServer(int id, Server server) {
        serverGroup.set(id,server);
        return serverGroup.get(id);
    }

    // Delete a server
    public Server deleteServer(int id) {
        Server server = serverGroup.get(id);
        serverGroup.remove(id);

        return server;
    }


    // Check and update the servers health after every 30s
    @Scheduled(initialDelay = 2,fixedDelay = 30,timeUnit = TimeUnit.SECONDS)
    public void checkServerHealth()
    {
        String serverIp;
        int serverPort;


        if(!serverGroup.isEmpty()){
            for (Server s : serverGroup)
            {
                serverIp = s.getServerIp();
                serverPort = s.getPort();

                // try to connect the socket
                try (Socket socket = new Socket(serverIp, serverPort)) {
                    System.out.println(LocalDateTime.now() +" " + socket + " Server is active!");

                    // update the server status
                    s.setStatus(true);

                } catch (Exception e) {
                    System.out.println(LocalDateTime.now()+ " "+ serverIp +" Server is not active or not responding.");

                    // update the server status
                    s.setStatus(false);
                }
            }
        }
        else {
            System.out.println(LocalDateTime.now() +" There is no server");
        }



    }
}
