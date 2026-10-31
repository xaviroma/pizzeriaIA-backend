package com.jm.dto;

public class LoginResponse {

    private Integer id;
    private String nombre;
    private String correo;

    public LoginResponse(Integer id, String nombre, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }
}