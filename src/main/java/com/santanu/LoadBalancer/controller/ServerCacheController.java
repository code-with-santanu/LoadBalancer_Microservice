package com.santanu.LoadBalancer.controller;


import com.santanu.LoadBalancer.model.Server;
import com.santanu.LoadBalancer.service.ServiceProviderImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class ServerCacheController {

    private final ServiceProviderImpl serviceProviderImpl;

    @Autowired
    public ServerCacheController(ServiceProviderImpl serviceProviderImpl) {
        this.serviceProviderImpl = serviceProviderImpl;
    }

    // expose endpoint to return list of employees
    @GetMapping("/servers")
    public List<Server> getAllUsers() {
        return serviceProviderImpl.getAllServers();
    }

    // expose endpoint to add an employee
    @PostMapping("/servers")
    public String addUser(@Valid @RequestBody Server server) {
        server.setCurrentLoad(0L);
        server.setStatus(true);
        return serviceProviderImpl.addServer(server);
    }

    // expose endpoint to update an employee
    @PutMapping("/servers/{serverId}")
    public String updateUser(@Valid @PathVariable int serverId, @RequestBody Server server) {
        return serviceProviderImpl.updateServer(serverId, server);
    }

    //expose endpoint to delete an employee
    @DeleteMapping("/servers/{serverId}")
    public String deleteServer(@PathVariable int serverId) {
        return serviceProviderImpl.deleteServer(serverId);
    }
}