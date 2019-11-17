package com.cs495.phototk.ui.management;



public class Gears {
    String key;
    String gearID;
    String gearName;
    String gearOwner;
    String insurance;
    String warranty;
    Double price;
    String detail;
    String pic;

    public Gears(){
    }

    public Gears(String gearID, String gearName, String gearOwner, String insurance, String warranty, Double price, String detail, String pic) {
        this.gearID = gearID;
        this.gearName = gearName;
        this.gearOwner = gearOwner;
        this.insurance = insurance;
        this.warranty = warranty;
        this.price = price;
        this.detail = detail;
        this.pic = pic;
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

    public String getPic() {return pic;}

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setGearID(String gearID) {
        this.gearID = gearID;
    }

    public void setGearName(String gearName) {
        this.gearName = gearName;
    }

    public void setGearOwner(String gearOwner) {
        this.gearOwner = gearOwner;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}

