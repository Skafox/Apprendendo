package com.fatecerss.tcc.apprendendo.model;

/**
 * Created by Sandro on 13/06/2018.
 */

public class Negotiation {

    private String negotiationId;
    private String interestId;
    private String adTitle;
    private String adOwnerId;
    private String adOwnerName;
    private String interestedId;
    private String interestedName;
    private String lastDate;


    public Negotiation(){

    }

    public Negotiation(String negotiationId, String interestId, String adTitle, String adOwnerId, String adOwnerName, String interestedId, String interestedName) {
        this.negotiationId = negotiationId;
        this.interestId = interestId;
        this.adTitle = adTitle;
        this.adOwnerId = adOwnerId;
        this.adOwnerName = adOwnerName;
        this.interestedId = interestedId;
        this.interestedName = interestedName;
        this.lastDate = null;

    }

    public String getInterestId() {
        return interestId;
    }

    public String getNegotiationId() {
        return negotiationId;
    }

    public void setNegotiationId(String negotiationId) {
        this.negotiationId = negotiationId;
    }

    public void setInterestId(String adId) {
        this.interestId = adId;
    }

    public String getAdTitle() {
        return adTitle;
    }

    public void setAdTitle(String adTitle) {
        this.adTitle = adTitle;
    }

    public String getAdOwnerId() {
        return adOwnerId;
    }

    public void setAdOwnerId(String adOwnerId) {
        this.adOwnerId = adOwnerId;
    }

    public String getAdOwnerName() {
        return adOwnerName;
    }

    public void setAdOwnerName(String adOwnerName) {
        this.adOwnerName = adOwnerName;
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

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }
}
