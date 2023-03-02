package com.pragma.powerup.infrastructure.datarol;

public enum TypeRol {

    ADMINISTRATOR("Administrator"),
    OWNER("Propietario"),
    EMPLOYEE("Empleado"),
    CUSTOMER("Customer");

    private final String nameRol;
    TypeRol(String nameRol){
        this.nameRol = nameRol;
    }

    public String getNameRol(){
        return nameRol;
    }
}
