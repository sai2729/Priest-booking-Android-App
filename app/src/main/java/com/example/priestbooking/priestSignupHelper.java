package com.example.priestbooking;

import android.net.VpnService;

public class priestSignupHelper {
    public String pname;
    public String pmobile;
    public String pservices;
    public String pstarttime;
    public String pendtime;
    public String pstatus;

    public priestSignupHelper(){

    }

    public String getPstatus() {
        return pstatus;
    }

    public void setPstatus(String pstatus) {
        this.pstatus = pstatus;
    }

    public priestSignupHelper(String pname, String pmobile, String pservices, String pstarttime, String pendtime, String pstatus){
        this.pname=pname;
        this.pmobile=pmobile;
        this.pservices=pservices;
        this.pstarttime=pstarttime;
        this.pendtime=pendtime;
        this.pstatus=pstatus;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPmobile() {
        return pmobile;
    }

    public void setPmobile(String pmobile) {
        this.pmobile = pmobile;
    }

    public String getPservices() {
        return pservices;
    }

    public void setPservices(String pservices) {
        this.pservices = pservices;
    }

    public String getPstarttime() {
        return pstarttime;
    }

    public void setPstarttime(String pstarttime) {
        this.pstarttime = pstarttime;
    }

    public String getPendtime() {
        return pendtime;
    }

    public void setPendtime(String pendtime) {
        this.pendtime = pendtime;
    }
}
