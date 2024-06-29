package com.santanu.LoadBalancer.model;

import lombok.Getter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ServerPool {

    // A list that holds the information about all servers
    private ArrayList<Server> serverGroup = new ArrayList<Server>();

    @Getter
    private Integer totalServers;

    public ServerPool() {
        this.totalServers = 0;
    }

    // To add single server to serverPool
    public void addServer(Server server)
    {
        serverGroup.add(server);
        totalServers++;
    }

    // To add multiple server to serverPool
    public void addServer(ArrayList<Server> serversList)
    {
        serverGroup.addAll(serversList);
    }

    // Return all the servers
    public List<Server> getAllServers()
    {
        return serverGroup;
    }

    // Update a server information
    public String updateServer(int id, Server server) {
        serverGroup.set(id,server);
        return "Updated server : "+ serverGroup.get(id);
    }

    // Delete a server
    public String deleteServer(int id) {
        Server server = serverGroup.get(id);
        serverGroup.remove(id);

        return server + " is deleted";
    }

    // Return list of active servers
    public ArrayList<Server> getUpServers()
    {
        ArrayList<Server> serverCache = new ArrayList<>();

        for (Server server : serverGroup)
        {
            if(server.getStatus())
            {
                serverCache.add(server);
            }
        }

        return serverCache;
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
