package com.fatecerss.tcc.apprendendo.model;

/**
 * Created by Sandro on 23/05/2018.
 */

public class Interest {

    private String ownerId;
    private String interestedId;
    private String interestedName;
    private String adId;
    private String adTitle;
    private String adSpecialty;
    private String interestDate;
    private String interestId;

    public Interest() {
    }

    public Interest(String ownerId, String interestedId, String interestedName, String adId, String adTitle, String adSpecialty, String interestDate) {
        this.ownerId = ownerId;
        this.interestedId = interestedId;
        this.interestedName = interestedName;
        this.adId = adId;
        this.adTitle = adTitle;
        this.adSpecialty = adSpecialty;
        this.interestDate = interestDate;
        this.interestId = null;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
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
