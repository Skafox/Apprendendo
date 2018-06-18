package com.fatecerss.tcc.apprendendo.model;

/**
 * Created by Sandro on 23/05/2018.
 */

public class Interest {

    private String ownerId;
    private String ownerName;
    private String interestedId;
    private String interestedName;
    private String adId;
    private String adTitle;
    private String adSpecialty;
    private String interestDate;
    private String interestId;
    private String type;

    public Interest() {
    }

    public Interest(String ownerId, String ownerName, String interestedId, String interestedName, String adId, String adTitle, String adSpecialty, String interestDate) {
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.interestedId = interestedId;
        this.interestedName = interestedName;
        this.adId = adId;
        this.adTitle = adTitle;
        this.adSpecialty = adSpecialty;
        this.interestDate = interestDate;
        this.interestId = null;
        this.type = null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getInterestedId() {
        return interestedId;
    }

    public void setInterestedId(String interestedId) {
        this.interestedId = interestedId;
    }

    public String getInterestedName() {
        return interestedName;
    }

    public void setInterestedName(String interestedName) {
        this.interestedName = interestedName;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getAdTitle() {
        return adTitle;
    }

    public void setAdTitle(String adTitle) {
        this.adTitle = adTitle;
    }

    public String getAdSpecialty() {
        return adSpecialty;
    }

    public void setAdSpecialty(String adSpecialty) {
        this.adSpecialty = adSpecialty;
    }

    public String getInterestDate() {
        return interestDate;
    }

    public void setInterestDate(String interestDate) {
        this.interestDate = interestDate;
    }

    public String getInterestId() {
        return interestId;
    }

    public void setInterestId(String interestId) {
        this.interestId = interestId;
    }
}
