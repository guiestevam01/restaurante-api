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
import java.util.Map;
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

        // Chamada da API externa de validação
        String url = "https://api.ValidEmail.net/?email=" + cliente.getEmail() + "&token=";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();

        // Interpretar JSON corretamente usando Gson
        Gson gson = new Gson();
        Map<String, Object> map = gson.fromJson(json, Map.class);

        Object isValidObj = map.get("IsValid");
        if (!(isValidObj instanceof Boolean)) {
            throw new RuntimeException("Erro na validação do email: resposta da API inesperada");
        }
        if (!((Boolean) isValidObj)) {
            throw new RuntimeException("Email inválido");
        }

        // Checa duplicidade no banco
        if (repository.existsByEmail(cliente.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        // Salva no banco
        return repository.save(cliente);
    }

    public void deletarCliente(Integer id) {
        repository.deleteById(id);
    }
}