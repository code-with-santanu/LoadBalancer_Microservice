package com.santanu.LoadBalancer.serverHandler;

import com.santanu.LoadBalancer.model.Server;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ServerSelectorImpl implements ServerSelector {

    // To select server, using Weighted Least Connection algo which select the server with minimum load factor
    // The time complexity: O(n) and Space Complexity: O(1)
    @Override
    public Server getServer(ArrayList<Server> cacheServer) {

        float minLoadFactor= Long.MAX_VALUE;
        Server selectedServer = null;

        // Select the server with minimum LoadFactor(activeConnection/maxWeight)
        for(Server server: cacheServer)
        {
            float curLoadFactor = server.getLoadFactor();
            if(curLoadFactor < minLoadFactor)
            {
                minLoadFactor = curLoadFactor;
                selectedServer = server;
            }
        }

        return selectedServer;
    }

}
