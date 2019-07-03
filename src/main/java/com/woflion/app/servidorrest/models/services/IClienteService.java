package com.woflion.app.servidorrest.models.services;

import java.util.List;

import com.woflion.app.servidorrest.models.entity.Cliente;

public interface IClienteService {
    public List<Cliente> findAll();
}