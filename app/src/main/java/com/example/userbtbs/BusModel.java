package com.example.userbtbs;

public class BusModel {
    public String arrivalLoc;
    public String arrivalTime;
    public String busNo;
    public String date;
    public String departureLoc;
    public String departureTime;
    public String typeSit;
    public String ticketPrice;

    public BusModel() {
    }

    public BusModel(String arrivalLoc, String arrivalTime, String busNo, String date, String departureLoc, String departureTime, String typeSit, String ticketPrice) {
        this.arrivalLoc = arrivalLoc;
        this.arrivalTime = arrivalTime;
        this.busNo = busNo;
        this.date = date;
        this.departureLoc = departureLoc;
        this.departureTime = departureTime;
        this.typeSit = typeSit;
        this.ticketPrice = ticketPrice;
    }

    public String getArrivalLoc() {
        return arrivalLoc;
    }

    public void setArrivalLoc(String arrivalLoc) {
        this.arrivalLoc = arrivalLoc;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDepartureLoc() {
        return departureLoc;
    }

    public void setDepartureLoc(String departureLoc) {
        this.departureLoc = departureLoc;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getTypeSit() {
        return typeSit;
    }

    public void setTypeSit(String typeSit) {
        this.typeSit = typeSit;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}
