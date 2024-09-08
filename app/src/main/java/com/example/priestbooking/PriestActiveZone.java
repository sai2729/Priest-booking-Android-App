package com.example.priestbooking;

public class PriestActiveZone {

    private String uname,umobile,ulocation,timings,reqservice,price;

    public PriestActiveZone(String uname, String umobile, String ulocation, String timings, String reqservice, String price){
        this.uname=uname;
        this.umobile=umobile;
        this.ulocation=ulocation;
        this.timings=timings;
        this.reqservice=reqservice;
        this.price=price;
    }

    public PriestActiveZone(){

    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUmobile() {
        return umobile;
    }

    public void setUmobile(String umobile) {
        this.umobile = umobile;
    }

    public String getUlocation() {
        return ulocation;
    }

    public void setUlocation(String ulocation) {
        this.ulocation = ulocation;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public String getReqservice() {
        return reqservice;
    }

    public void setReqservice(String reqservice) {
        this.reqservice = reqservice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
