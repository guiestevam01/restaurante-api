package com.example.guifood.restaurante.Repository;

import com.example.guifood.restaurante.Entity.Prato;
import org.springframework.data.jpa.repository.JpaRepository;
//Um Repository é uma camada que lida diretamente com o banco de dados.
public interface PratoRepository extends JpaRepository<Prato, Integer> {
}
