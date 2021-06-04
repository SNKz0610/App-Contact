package com.snkz.appcontact.model;

import java.io.Serializable;

public class Contact implements Serializable {
    private int id;
    private byte[] avatar;
    private String section;
    private String username, password, name, birthday, phone, email, address;

    public Contact() {
    }


    public Contact(int id, byte[] avatar, String username, String password, String name, String birthday, String phone, String email, String address) {
        this.id = id;
        this.avatar = avatar;
        this.username = username;
        this.password = password;
        this.name = name;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public Contact( String username, String password,byte[] avatar,String name, String birthday, String phone, String email, String address) {
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        this.name = name;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public Contact(int id) {
        this.id = id;
    }

    public Contact(String name) {
        this.name = name;
    }

    public Contact(byte[] avatar, String name) {
        this.avatar = avatar;
        this.name = name;
    }

    public Contact(int id, byte[] avatar, String name) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
    }

    public Contact(int id, String password) {
        this.id = id;
        this.password = password;
    }

    public Contact(byte[] avatar, String name, String birthday, String phone, String email, String address) {
        this.avatar = avatar;
        this.name = name;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public Contact(int id, byte[] avatar, String name, String birthday, String phone, String email, String address) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public Contact(byte[] avatar, String name, String birthday) {
        this.avatar = avatar;
        this.name = name;
        this.birthday = birthday;
    }

    public Contact(byte[] avatar) {
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
