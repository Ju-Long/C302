package sg.edu.rp.webservices.c302_p06_sakilaclient;

import java.io.Serializable;

public class FilmLocation implements Serializable {
    private String address;
    private int postal_code;
    private int phone;
    private String city;
    private String country;

    public FilmLocation(String address, int postal_code, int phone, String city, String country) {
        this.address = address;
        this.postal_code = postal_code;
        this.phone = phone;
        this.city = city;
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public int getPostal_code() {
        return postal_code;
    }

    public int getPhone() {
        return phone;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}
