package com.cs495.phototk.ui.management;

import java.util.Date;

public class Gears {
    String gearID;
    String gearName;
    String gearOwner;
    String insurance;
    String warranty;
    Double price;
    String detail;

    public Gears(){
    }

    public Gears(String gearID, String gearName, String gearOwner, String insurance, String warranty, Double price, String detail) {
        this.gearID = gearID;
        this.gearName = gearName;
        this.gearOwner = gearOwner;
        this.insurance = insurance;
        this.warranty = warranty;
        this.price = price;
        this.detail = detail;
    }

    public String getGearID() {
        return gearID;
    }

    public String getGearName() {
        return gearName;
    }

    public String getGearOwner() {
        return gearOwner;
    }

    public String getInsurance() {
        return insurance;
    }

    public String getWarranty() {
        return warranty;
    }

    public Double getPrice() {
        return price;
    }

    public String getDetail() {
        return detail;
    }
}
