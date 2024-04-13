package com.example.haircutbooking;

public class Data {

    public String name;
    public String phone;
    public String type;
    public String barber;
    public String date_and_time;
    public String email;

    @Override
    public String toString() {
        return "Data{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", type='" + date_and_time + '\'' +
                ", barber='" + type + '\'' +
                ", date_and_time='" + barber + '\'' +
                ", email ='" + email + '\'' +
                '}';
    }

    public Data(){

    }

    public Data(String name, String phone, String type, String barber, String date_and_time , String email) {
        this.name = name;
        this.phone = phone;
        this.type = type;
        this.barber = barber;
        this.date_and_time = date_and_time;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getType() {
        return type;
    }

    public String getBarber() {
        return barber;
    }

    public String getEmail() {
        return email;
    }

    public String getDate_and_time() {
        return type;
    }


}
