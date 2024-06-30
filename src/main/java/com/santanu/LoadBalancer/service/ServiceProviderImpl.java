package com.santanu.LoadBalancer.service;

import com.santanu.LoadBalancer.model.Server;
import com.santanu.LoadBalancer.model.ServerPool;
import com.santanu.LoadBalancer.serverHandler.ServerSelectorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ServiceProviderImpl implements ServiceProvider {

    private final ServerPool serverPool;
    private final ServerSelectorImpl serverSelector;

    @Autowired
    public ServiceProviderImpl(ServerPool serverPool, ServerSelectorImpl serverSelector) {
        this.serverPool = serverPool;
        this.serverSelector = serverSelector;
    }

    @Override
    public List<Server> getAllServers() {
        return serverPool.getAllServers();
    }

    @Override
    public String addServers(ArrayList<Server> serverList) {
        serverPool.addServer(serverList);
        return serverList + "\n are added";
    }

    @Override
    public String addServer(Server server) {
        serverPool.addServer(server);
        return server.getServerURL() + " is added";
    }


    @Override
    public String updateServer(int serverId,Server server) {
        Server s = serverPool.updateServer(serverId,server);
        return s.getServerIp() + " server info is updated";
    }

    @Override
    public String deleteServer(Integer serverId) {
        Server s = serverPool.deleteServer(serverId);
        return s.getServerURL() + " is removed";
    }

    @Override
    public Server getTargetServer() {
        // Get the list of all active servers
        ArrayList<Server> activeServers = serverPool.getUpServers();

        // return the selected server among active servers
        return serverSelector.getServer(activeServers);
    }
}
