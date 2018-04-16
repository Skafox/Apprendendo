package com.fatecerss.tcc.apprendendo.model;

import android.app.ProgressDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Sandro on 23/03/2018.
 */

public class User {

    private String username;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String birthdate;
    private String bio;
    private String status;

    public User(){

    }


    public User(String username, String email, String password, String name, String phone, String birthdate, String bio) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.birthdate = birthdate;
        this.bio = bio;
        this.status = "ENABLED";
    }

    public User(String username, String email, String password, String name, String phone, String birthdate, String bio, String status) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.birthdate = birthdate;
        this.bio = bio;
        this.status = status;
    }

    public User(String username){
        this.username = username;
        this.email = null;
        this.password = null;
        this.name = null;
        this.phone = null;
        this.birthdate = null;
        this.bio = null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                username + '\'' +
                email + '\'' +
                password + '\'' +
                name + '\'' +
                phone + '\'' +
                birthdate + '\'' +
                bio + '\'' +
                status + '\'' +
                '}';
    }
}
