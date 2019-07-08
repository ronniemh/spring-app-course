package com.woflion.app.servidorrest.controllers;

import java.util.List;

import com.woflion.app.servidorrest.models.entity.Cliente;
import com.woflion.app.servidorrest.models.services.IClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class ClienteRestController {

    @Autowired
    private IClienteService clienteService;

    // TODO: Revisar el resposeStatus
    @GetMapping("/clientes")
    public List<Cliente> index() {
        return clienteService.findAll();
    }

    // TODO: Revisar el resposeStatus
    @GetMapping("/clientes/{id}")
    public Cliente show(@PathVariable Long id) {
        return clienteService.findById(id);
    }

    @PostMapping(value = "/clientes")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente create(@RequestBody Cliente cliente) {
        // cliente.setCreateAt(new Date());
        return clienteService.save(cliente);
    }

    @PutMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente update(@RequestBody Cliente cliente, @PathVariable Long id) {
        Cliente clienteActual = clienteService.findById(id);
        clienteActual.setApellido(cliente.getApellido());
        clienteActual.setEmail(cliente.getEmail());
        clienteActual.setNombre(cliente.getNombre());
        return clienteService.save(clienteActual);
    }

    @DeleteMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        clienteService.delete(id);
    }

}