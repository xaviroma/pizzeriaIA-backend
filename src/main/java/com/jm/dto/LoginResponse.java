package com.jm.dto;

public class LoginResponse {

    private Integer id;
    private String nombre;
    private String correo;
    private String token;
    private String rol;

    public LoginResponse(Integer id, String nombre, String correo, String token, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.token = token;
        this.rol = rol;
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

    public String getRol() {
        return rol;
    }
}