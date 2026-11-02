package com.jm.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    private java.sql.Timestamp fecha;
    private String estado;

    @Column(precision = 10, scale = 2) // Para ajustarlo a DECIMAL(10,2) en la BD
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<PedidoProducto> productos;

    @Column(name = "stripe_session_id")
    private String stripeSessionId;

    @Column(name = "stripe_session_url", length = 500)
    private String stripeSessionUrl;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public java.sql.Timestamp getFecha() { return fecha; }
    public void setFecha(java.sql.Timestamp fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public Empleado getEmpleado() { return empleado; }
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }

    public List<PedidoProducto> getProductos() { return productos; }

    public void setProductos(List<PedidoProducto> productos) { this.productos = productos; }

    public String getStripeSessionId() {
        return stripeSessionId;
    }

    public void setStripeSessionId(String stripeSessionId) {
        this.stripeSessionId = stripeSessionId;
    }

    public String getStripeSessionUrl() {
        return stripeSessionUrl;
    }

    public void setStripeSessionUrl(String stripeSessionUrl) {
        this.stripeSessionUrl = stripeSessionUrl;
    }
}
