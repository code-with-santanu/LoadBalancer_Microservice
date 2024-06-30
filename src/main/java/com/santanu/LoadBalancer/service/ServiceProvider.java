package com.santanu.LoadBalancer.service;

import com.santanu.LoadBalancer.model.Server;

import java.util.ArrayList;
import java.util.List;

public interface ServiceProvider {

    List<Server> getAllServers();

    String addServers(ArrayList<Server> serverList);

    String addServer(Server server);

    String updateServer(int serverId,Server server);

    String deleteServer(Integer serverId);


    Server getTargetServer();
}
