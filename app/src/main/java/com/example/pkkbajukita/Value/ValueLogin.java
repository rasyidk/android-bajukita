package com.example.pkkbajukita.Value;

public class ValueLogin {

    String value;
    String message;
    String userid;
    String username;
    String nama_lengkap;
    String no_wa;
    String img_profile;



    public ValueLogin(String value, String message, String userid, String username, String nama_lengkap, String no_wa, String img_profile) {
        this.value = value;
        this.message = message;
        this.userid = userid;
        this.username = username;
        this.nama_lengkap = nama_lengkap;
        this.no_wa = no_wa;
        this.img_profile = img_profile;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public String getNo_wa() {
        return no_wa;
    }

    public void setNo_wa(String no_wa) {
        this.no_wa = no_wa;
    }

    public String getImg_profile() {
        return img_profile;
    }

    public void setImg_profile(String img_profile) {
        this.img_profile = img_profile;
    }
}
