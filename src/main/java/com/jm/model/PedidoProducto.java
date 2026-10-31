package com.jm.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class PedidoProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    private Integer cantidad;

    private String tamaño;

    @OneToMany(mappedBy = "pedidoProducto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoProductoIngrediente> ingredientes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getTamaño() {
        return tamaño;
    }

    public void setTamano(String tamaño) {
        this.tamaño = tamaño;
    }

    public List<PedidoProductoIngrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<PedidoProductoIngrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }
}