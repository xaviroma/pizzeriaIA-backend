package com.jm.dto;

public class LoginResponse {

    private Integer id;
    private String nombre;
    private String correo;
    private String token;

    public LoginResponse(Integer id, String nombre, String correo, String token) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.token = token;
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

    public String getToken() {
        return token;
    }
}