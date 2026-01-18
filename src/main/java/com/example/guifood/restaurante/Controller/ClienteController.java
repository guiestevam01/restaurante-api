package com.example.guifood.restaurante.Controller;

import com.example.guifood.restaurante.Entity.Cliente;
import com.example.guifood.restaurante.Repository.ClienteRepository;
import com.example.guifood.restaurante.Service.ClienteService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    public List<Cliente> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Cliente buscar(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public Cliente criar(@RequestBody Cliente cliente) throws IOException, InterruptedException {
        return service.criarCliente(cliente);
    }


    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        service.deletarCliente(id);
    }

}
