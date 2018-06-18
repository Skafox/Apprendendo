package com.fatecerss.tcc.apprendendo.model;

import java.util.ArrayList;

import static com.fatecerss.tcc.apprendendo.R.string.sunday;

/**
 * Created by Sandro on 08/05/2018.
 */

public class Advertisement {

    private String adId;
    private String uId;
    private String title;
    private String description;
    private boolean Sunday;
    private boolean Monday;
    private boolean Tuesday;
    private boolean Wednesday;
    private boolean Thursday;
    private boolean Friday;
    private boolean Saturday;
    private boolean morning;
    private boolean afternoon;
    private boolean night;
    private String specialty;
    private String registrationDate;
    private String type;

    public Advertisement(){

    }

    public Advertisement(String uId, String title, String description, String specialty,
                         boolean sunday, boolean monday, boolean tuesday, boolean wednesday,
                         boolean thursday, boolean friday, boolean saturday,
                         boolean morning, boolean afternoon, boolean night, String registrationDate, String type) {

        this.adId = null;
        this.uId = uId;
        this.title = title;
        this.description = description;
        this.specialty = specialty;
        Sunday = sunday;
        Monday = monday;
        Tuesday = tuesday;
        Wednesday = wednesday;
        Thursday = thursday;
        Friday = friday;
        Saturday = saturday;
        this.morning = morning;
        this.afternoon = afternoon;
        this.night = night;
        this.registrationDate = registrationDate;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSunday() {
        return Sunday;
    }

    public void setSunday(boolean sunday) {
        Sunday = sunday;
    }

    public boolean isMonday() {
        return Monday;
    }

    public void setMonday(boolean monday) {
        Monday = monday;
    }

    public boolean isTuesday() {
        return Tuesday;
    }

    public void setTuesday(boolean tuesday) {
        Tuesday = tuesday;
    }

    public boolean isWednesday() {
        return Wednesday;
    }

    public void setWednesday(boolean wednesday) {
        Wednesday = wednesday;
    }

    public boolean isThursday() {
        return Thursday;
    }

    public void setThursday(boolean thursday) {
        Thursday = thursday;
    }

    public boolean isFriday() {
        return Friday;
    }

    public void setFriday(boolean friday) {
        Friday = friday;
    }

    public boolean isSaturday() {
        return Saturday;
    }

    public void setSaturday(boolean saturday) {
        Saturday = saturday;
    }

    public boolean isMorning() {
        return morning;
    }

    public void setMorning(boolean morning) {
        this.morning = morning;
    }

    public boolean isAfternoon() {
        return afternoon;
    }

    public void setAfternoon(boolean afternoon) {
        this.afternoon = afternoon;
    }

    public boolean isNight() {
        return night;
    }

    public void setNight(boolean night) {
        this.night = night;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }
}
