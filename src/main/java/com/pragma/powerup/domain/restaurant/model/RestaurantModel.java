package com.pragma.powerup.domain.restaurant.model;

import com.pragma.powerup.domain.restaurant.exception.FormatValueException;
import com.pragma.powerup.domain.restaurant.exception.LengthValueException;
import com.pragma.powerup.domain.restaurant.exception.NumberPhoneFormatException;

public class RestaurantModel {

    private Long id;
    private String name;
    private String address;
    private Long idOwner;
    private String phone;
    private String urlLogo;
    private Long nit;

    public RestaurantModel(Long id, String name, String address, Long idOwner, String phone, String urlLogo, Long nit) {
        validationLengthPhone(phone);
        validationContainsNumber(name);
        this.id = id;
        this.name = name;
        this.address = address;
        this.idOwner = idOwner;
        this.phone = phone;
        this.urlLogo = urlLogo;
        this.nit = nit;
    }

    public RestaurantModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(Long idOwner) {
        this.idOwner = idOwner;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public Long getNit() {
        return nit;
    }

    public void setNit(Long nit) {
        this.nit = nit;
    }

    private void validationLengthPhone(String phone) {
        final int INIT_LENGTH_NUMBER = 10;
        final int END_LENGTH_NUMBER = 13;
        final String MSG_EXCEPTION = "phone " + phone;
        if ((phone.length() < INIT_LENGTH_NUMBER || phone.length() > END_LENGTH_NUMBER) ||
                (phone.length() > INIT_LENGTH_NUMBER && phone.length() < END_LENGTH_NUMBER)) {
            throw new LengthValueException(MSG_EXCEPTION);
        }

        String regexSigne = "^\\+(\\d{12})$";
        String regexNumber = "^\\d{10}$";

        if ((phone.length() == INIT_LENGTH_NUMBER && !phone.matches(regexNumber))
                || (phone.length() == END_LENGTH_NUMBER && !phone.matches(regexSigne))) {

            throw new NumberPhoneFormatException(MSG_EXCEPTION);
        }
    }

    private void validationContainsNumber(String value) {
        if(value.matches("\\d+")){
            throw new FormatValueException("name "+value);
        }
    }
}
