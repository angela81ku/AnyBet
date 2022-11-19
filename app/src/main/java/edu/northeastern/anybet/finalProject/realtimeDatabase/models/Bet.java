package edu.northeastern.anybet.finalProject.realtimeDatabase.models;


public class Bet {

    // bet > title, bet price, bet status, participant, bet time start, bet time end, address,

    private String title;
    private String betPrice;
    private String betStatus;
    private String participant1;
    private String participant2;
    private String betStartTime;
    private String betEndTime;
    private double longitude;
    private double latitude;

    public Bet() {

    }

    public Bet(String title, String betPrice, String participant1, String participant2,
               String betStartTime, double longitude, double latitude) {
        this.title = title;
        this.betPrice = betPrice;
        this.participant1 = participant1;
        this.participant2 = participant2;
        this.betStatus = "in progress";
        this.betStartTime = betStartTime;
        this.betEndTime = "";
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBetPrice() {
        return betPrice;
    }

    public void setBetPrice(String betPrice) {
        this.betPrice = betPrice;
    }

    public String getBetStatus() {
        return betStatus;
    }

    public void setBetStatus(String betStatus) {
        this.betStatus = betStatus;
    }

    public String getParticipant1() {
        return participant1;
    }

    public void setParticipant1(String participant1) {
        this.participant1 = participant1;
    }

    public String getParticipant2() {
        return participant2;
    }

    public void setParticipant2(String participant2) {
        this.participant2 = participant2;
    }

    public String getBetStartTime() {
        return betStartTime;
    }

    public void setBetStartTime(String betStartTime) {
        this.betStartTime = betStartTime;
    }

    public String getBetEndTime() {
        return betEndTime;
    }

    public void setBetEndTime(String betEndTime) {
        this.betEndTime = betEndTime;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
