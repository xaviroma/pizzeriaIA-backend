package com.jm.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;
import java.util.List;

public class PedidoRequest implements Serializable {

    private Integer clienteId;

    @NotEmpty(message = "El carrito no puede estar vacío")
    private List<@Valid Item> cart;

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public List<Item> getCart() {
        return cart;
    }

    public void setCart(List<Item> cart) {
        this.cart = cart;
    }

    public static class Item {

        @NotNull(message = "El ID del producto es obligatorio")
        @Positive(message = "El ID del producto debe ser positivo")
        private Integer productoId;

        @NotBlank(message = "El tamaño es obligatorio")
        @Pattern(regexp = "Pequena|Mediana|Familiar",
                message = "El tamaño seleccionado no es válido")
        private String tamanoSeleccionado;

        @NotNull(message = "La cantidad es obligatoria")
        @Positive(message = "La cantidad debe ser mayor que cero")
        private Integer quantity;

        private List<@Positive(message = "Los IDs de ingredientes deben ser positivos") Integer> ingredientesIds;

        public Integer getProductoId() {
            return productoId;
        }

        public void setProductoId(Integer productoId) {
            this.productoId = productoId;
        }

        public String getTamanoSeleccionado() {
            return tamanoSeleccionado;
        }

        public void setTamanoSeleccionado(String tamanoSeleccionado) {
            this.tamanoSeleccionado = tamanoSeleccionado;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public List<Integer> getIngredientesIds() {
            return ingredientesIds;
        }

        public void setIngredientesIds(List<Integer> ingredientesIds) {
            this.ingredientesIds = ingredientesIds;
        }
    }
}