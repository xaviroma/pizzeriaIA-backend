package com.jm.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Usando Integer en lugar de Long

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @Column(nullable = false)
    private BigDecimal precio_pequena; // Usando BigDecimal para representar el precio

    private BigDecimal precio_mediana;

    private BigDecimal precio_familiar; 
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
    
    private String imagen;
    
    private Integer orden;

    @Column(nullable = false)
    private boolean disponible = true;

    // Getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecioPequena() {
        return precio_pequena;
    }

    public void setPrecioPequena(BigDecimal precio_pequena) {
        this.precio_pequena = precio_pequena;
    }
    
    public BigDecimal getPrecioMediana() {
        return precio_mediana;
    }

    public void setPrecioMediana(BigDecimal precio_mediana) {
        this.precio_mediana = precio_mediana;
    }
    
    public BigDecimal getPrecioFamiliar() {
        return precio_familiar;
    }

    public void setPrecioFamiliar(BigDecimal precio_familiar) {
        this.precio_familiar = precio_familiar;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public boolean isDisponible() { return disponible; }

    public void setDisponible(boolean disponible) { this.disponible = disponible; }

}
