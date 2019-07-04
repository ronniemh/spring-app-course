package com.woflion.app.servidorrest.controllers;

import java.util.List;

import com.woflion.app.servidorrest.models.entity.Cliente;
import com.woflion.app.servidorrest.models.services.IClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController{

    @Autowired
    private IClienteService clienteService;

    @GetMapping("/clientes")
    public List<Cliente> index()
    {
        return clienteService.findAll();
    }
}