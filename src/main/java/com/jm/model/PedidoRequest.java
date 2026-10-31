package com.jm.model;

import java.io.Serializable;
import java.util.List;

public class PedidoRequest implements Serializable {

    private Integer clienteId;
    private List<Item> cart;

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

        private Integer productoId;
        private String tamañoSeleccionado;
        private Integer quantity;
        private List<Integer> ingredientesIds;

        public Integer getProductoId() {
            return productoId;
        }

        public void setProductoId(Integer productoId) {
            this.productoId = productoId;
        }

        public String getTamanoSeleccionado() {
            return tamañoSeleccionado;
        }

        public void setTamañoSeleccionado(String tamañoSeleccionado) {
            this.tamañoSeleccionado = tamañoSeleccionado;
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