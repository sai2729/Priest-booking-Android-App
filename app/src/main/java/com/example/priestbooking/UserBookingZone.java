package com.example.priestbooking;

public class UserBookingZone {

    private String service,timings,status;

    public UserBookingZone(String service,String timings,String status){
        this.service=service;
        this.timings=timings;
        this.status=status;
    }

public UserBookingZone(){

}

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
