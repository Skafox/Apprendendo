package com.fatecerss.tcc.apprendendo.model;

/**
 * Created by Sandro on 23/03/2018.
 */

public class Learner extends User{

    private String schoolingLevel;

    //CONSTRUCTORS

    public Learner (String username, String email, String password, String name, String phone, String birthdate, String bio){
        super(username,email,password,name,phone,birthdate,bio);
    }

    public Learner(){

    }


    //GETTERS AND SETTERS
    public String getSchoolingLevel() {
        return schoolingLevel;
    }

    public void setSchoolingLevel(String schoolingLevel) {
        this.schoolingLevel = schoolingLevel;
    }

    public String getUsername() {
        return super.getUsername();
    }

    public void setUsername(String username) {
        super.setUsername(username);
    }

    public String getEmail() {
        return super.getEmail();
    }

    public void setEmail(String email) {
        super.setEmail(email);
    }

    public String getPassword() {
        return super.getPassword();
    }

    public void setPassword(String password) {
        super.setPassword(password);
    }

    public String getName() {
        return super.getName();
    }

    public void setName(String name) {
        super.setName(name);
    }

    public String getPhone() {
        return super.getPhone();
    }

    public void setPhone(String phone) {
        super.setPhone(phone);
    }

    public String getBirthdate() {
        return super.getBirthdate();
    }

    public void setBirthdate(String birthdate) {
        super.setBirthdate(birthdate);
    }

    public String getBio() {
        return super.getBio();
    }

    public void setBio(String bio) {
        super.setBio(bio);
    }



}
