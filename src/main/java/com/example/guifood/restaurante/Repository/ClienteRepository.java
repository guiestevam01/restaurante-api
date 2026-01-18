package com.example.guifood.restaurante.Repository;

import com.example.guifood.restaurante.Entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    boolean existsByEmail(String email);

}
