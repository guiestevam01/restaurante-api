package com.example.guifood.restaurante.Service;

import com.example.guifood.restaurante.Entity.Cliente;
import com.example.guifood.restaurante.Repository.ClienteRepository;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Gatherer;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> listarTodos() {
        return repository.findAll();
    }

    public Cliente buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    public Cliente criarCliente(Cliente cliente) throws IOException, InterruptedException {
        String email = "https://api.ValidEmail.net/?email=" + cliente.getEmail() + "&token=56a0339b1dac4ca5b87b80e6fa267611";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(email))
                .build();
        HttpResponse<String> reponse = client
                .send(request, HttpResponse.BodyHandlers.ofString());
        String json = reponse.body();
        if (!(json.contains("\"IsValid\":true"))) {
            throw new RuntimeException("Email invalido");
        }
        boolean existe = repository.existsByEmail(cliente.getEmail());
        if (existe) {
            throw new RuntimeException("Email já cadastrado");
        }
        return repository.save(cliente);
    }

    public void deletarCliente(Integer id) {
        repository.deleteById(id);
    }
}