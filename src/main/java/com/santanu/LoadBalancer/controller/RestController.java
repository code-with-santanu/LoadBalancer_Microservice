package com.santanu.LoadBalancer.controller;


import com.santanu.LoadBalancer.model.Server;
import com.santanu.LoadBalancer.service.ServiceProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    private final ServiceProviderImpl serviceProviderImpl;

    @Autowired
    public RestController(ServiceProviderImpl serviceProviderImpl) {
        this.serviceProviderImpl = serviceProviderImpl;
    }

    // expose endpoint to return list of employees
    @GetMapping("/servers")
    public List<Server> getAllUsers() {
        return serviceProviderImpl.getAllServers();
    }

    // expose endpoint to add an employee
    @PostMapping("/servers")
    public String addUser(@RequestBody Server server) {
        return serviceProviderImpl.addServer(server);
    }

    // expose endpoint to update an employee
    @PutMapping("/servers/{serverId}")
    public String updateUser(@PathVariable int serverId, @RequestBody Server server) {
        return serviceProviderImpl.updateServer(serverId, server);
    }

    //expose endpoint to delete an employee
    @DeleteMapping("/servers/{serverId}")
    public String deleteServer(@PathVariable int serverId) {
        return serviceProviderImpl.deleteServer(serverId);
    }
}