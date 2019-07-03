package com.woflion.app.servidorrest.models.dao;

import com.woflion.app.servidorrest.models.entity.Cliente;

import org.springframework.data.repository.CrudRepository;

public interface IClienteDAO extends CrudRepository<Cliente, Long>{
    
}