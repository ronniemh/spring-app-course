package com.woflion.app.servidorrest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.woflion.app.servidorrest.models.entity.Cliente;
import com.woflion.app.servidorrest.models.services.IClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        System.out.println("entrando en la consulta!");
        Map<String, Object> response = new HashMap<>();
        Cliente cliente = null;
        try {
            cliente = clienteService.findById(id);

        } catch (DataAccessException e) {
            System.out.println("Entrando en la excepción");
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cliente == null) {
            response.put("mensaje",
                    "El cliente con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        response.put("mensaje: ", "El cliente con "+id+" se ha encontrado en la base de datos.");
        response.put("cliente", cliente);
        //TODO: retornar el response.
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @PostMapping(value = "/clientes")
    public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result) {
        Cliente clienteNuevo = null;
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors())
        {
            response.put("errors: ", result.getFieldErrors().stream().map(c -> "El campo ".concat(c.getField()).concat(" ").concat(c.getDefaultMessage())).collect(Collectors.toList()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            clienteNuevo = clienteService.save(cliente);
        } catch (Exception e) {
            response.put("mensaje", "Error al insertar el objeto en la base de datos en la base de datos");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El cliente ha sido creado con éxito");
        response.put("cliente", clienteNuevo);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id) {
        System.out.println("Entrando al update...");
        Map<String, Object> response = new HashMap<>();
        if(result.hasErrors())
        {
            System.out.println("tiene errores...");
            response.put("errores: ", result.getFieldErrors().stream().map(c -> "El campo ".concat(c.getField()).concat(" "+c.getDefaultMessage())).collect(Collectors.toList()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        Cliente clienteActual = null;
        Cliente clienteUpdate = null;
        try {
            clienteActual = clienteService.findById(id);
            if (clienteActual != null) {
                clienteActual.setApellido(cliente.getApellido());
                clienteActual.setEmail(cliente.getEmail());
                clienteActual.setNombre(cliente.getNombre());
                clienteActual.setCreateAt(cliente.getCreateAt());
                clienteUpdate = clienteService.save(clienteActual);
            } else {
                response.put("mensaje", "Error al actualizar el objeto en la base de datos en la base de datos, no existe el "+id);
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            response.put("mensaje", "Error al actualizar el objeto en la base de datos en la base de datos");
            response.put("error:", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El cliente ha sido actualizado con éxito");
        response.put("cliente", clienteUpdate);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            clienteService.delete(id);            
        } catch (Exception e) {
            response.put("mensaje", "Error al eliminar el objeto en la base de datos en la base de datos");
            response.put("error:", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El cliente ha sido eliminado con éxito");
        return new ResponseEntity<>(response, HttpStatus.OK);       
    }

}