package com.example.priestbooking;

public class ShopSignupHelper {
    public String skname;
    public String skmobile;
    public String sklocation;
    public String skopentime;
    public String skclosetime;
    public String skstatus;

    public ShopSignupHelper() {
    }

    public ShopSignupHelper(String skname, String skmobile, String sklocation, String skopentime, String skclosetime, String skstatus) {
        this.skname = skname;
        this.skmobile = skmobile;
        this.sklocation = sklocation;
        this.skopentime = skopentime;
        this.skclosetime = skclosetime;
        this.skstatus = skstatus;
    }

    public String getSkname() {
        return skname;
    }

    public void setSkname(String skname) {
        this.skname = skname;
    }

    public String getSkmobile() {
        return skmobile;
    }

    public void setSkmobile(String skmobile) {
        this.skmobile = skmobile;
    }

    public String getSklocation() {
        return sklocation;
    }

    public void setSklocation(String sklocation) {
        this.sklocation = sklocation;
    }

    public String getSkopentime() {
        return skopentime;
    }

    public void setSkopentime(String skopentime) {
        this.skopentime = skopentime;
    }

    public String getSkclosetime() {
        return skclosetime;
    }

    public void setSkclosetime(String skclosetime) {
        this.skclosetime = skclosetime;
    }

    public String getSkstatus() {
        return skstatus;
    }

    public void setSkstatus(String skstatus) {
        this.skstatus = skstatus;
    }
}
