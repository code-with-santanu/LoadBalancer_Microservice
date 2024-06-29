package com.santanu.LoadBalancer.serverHandler;

import com.santanu.LoadBalancer.model.Server;

import java.util.ArrayList;

public interface ServerSelector {

    Server getServer(ArrayList<Server> cacheServer);
}
