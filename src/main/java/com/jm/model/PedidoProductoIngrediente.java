package com.jm.model;

import jakarta.persistence.*;

@Entity
public class PedidoProductoIngrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pedido_producto_id", nullable = false)
    private PedidoProducto pedidoProducto;

    @ManyToOne
    @JoinColumn(name = "ingrediente_id", nullable = false)
    private Ingrediente ingrediente;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PedidoProducto getPedidoProducto() {
        return pedidoProducto;
    }

    public void setPedidoProducto(PedidoProducto pedidoProducto) {
        this.pedidoProducto = pedidoProducto;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }
}
