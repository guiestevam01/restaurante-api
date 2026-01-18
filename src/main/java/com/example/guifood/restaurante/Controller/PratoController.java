package com.example.guifood.restaurante.Controller;

import com.example.guifood.restaurante.Entity.Prato;
import com.example.guifood.restaurante.Repository.PratoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pratos")
public class PratoController {

    private final PratoRepository repository;

    // Construtor: Spring injeta automaticamente o Repository
    public PratoController(PratoRepository repository) {
        this.repository = repository;
    }

    // GET /pratos
    @GetMapping
    public List<Prato> listar() {
        return repository.findAll();
    }

    // GET /pratos/{id}
    @GetMapping("/{id}")
    public Prato buscarPorId(@PathVariable Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prato não encontrado"));
    }

    // POST /pratos
    @PostMapping
    public Prato criar(@RequestBody Prato prato) {
        return repository.save(prato);
    }

    // DELETE /pratos/{id}
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
