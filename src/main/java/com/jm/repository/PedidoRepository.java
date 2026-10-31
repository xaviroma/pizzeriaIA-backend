package com.jm.repository;

import com.jm.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByClienteId(Integer clienteId);

    List<Pedido> findByCliente_IdAndCliente_Correo(
            Integer clienteId,
            String correo
    );
}
