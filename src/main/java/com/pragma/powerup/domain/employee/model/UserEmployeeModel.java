package com.pragma.powerup.domain.employee.model;

public class UserEmployeeModel {
    private String name;
    private String lastName;
    private Long identityDocument;
    private String phone;
    private String email;
    private String password;
    private Long idRol;

    public UserEmployeeModel(String name, String lastName, Long identityDocument, String phone, String email, String password, Long idRol) {
        this.name = name;
        this.lastName = lastName;
        this.identityDocument = identityDocument;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.idRol = idRol;
    }

    public UserEmployeeModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getIdentityDocument() {
        return identityDocument;
    }

    public void setIdentityDocument(Long identityDocument) {
        this.identityDocument = identityDocument;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }
}
