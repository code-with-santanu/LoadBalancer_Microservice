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

    @Autowired
    public ServiceProviderImpl(ServerPool serverPool) {
        this.serverPool = serverPool;
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


    // Return list of active servers
    @Override
    public ArrayList<Server> getUpServers(ArrayList<Server> serverGroup) {
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

    @Override
    public Server getTargetServer() {
        // Get the list of all active servers
        ArrayList<Server> activeServers = new ServiceProviderImpl(serverPool).getUpServers(serverPool.getAllServers());

        ServerSelectorImpl serverSelector = new ServerSelectorImpl();
        // return the selected server among active servers
        return serverSelector.getServer(activeServers);
    }

    @Override
    public void updateServerLoad(int serverId) {
        // Increase the current active connections
        Server s = serverPool.getServerById(serverId);
        s.setCurrentLoad(s.getCurrentLoad()+1);

        // calculate the loadFactor and set the new value
        float lf = (float) s.getCurrentLoad() / (float) s.getMaxWeight();
        s.setLoadFactor(lf);

        // put the updated server
        serverPool.updateServer(serverId,s);
    }
}
