package com.jm.repository;

import com.jm.model.ProductoIngrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoIngredienteRepository extends JpaRepository<ProductoIngrediente, Integer> {
}
