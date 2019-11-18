package com.cs495.phototk.ui.management;


import android.os.Parcel;
import android.os.Parcelable;

public class Gears implements Parcelable {
    String key;
    String gearName;
    String gearOwner;
    String insurance;
    String warranty;
    Double price;
    String detail;
    String pic;

    public Gears(){
    }

    public Gears(String key, String gearName, String gearOwner, String insurance, String warranty, Double price, String detail, String pic) {
        this.key = key;
        this.gearName = gearName;
        this.gearOwner = gearOwner;
        this.insurance = insurance;
        this.warranty = warranty;
        this.price = price;
        this.detail = detail;
        this.pic = pic;
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

    @Override
    public boolean equals(Object object){
        if(object == null)
            return false;
        if(!Gears.class.isAssignableFrom(object.getClass()))
            return false;
        final Gears gears = (Gears)object;
        return gears.getKey().equals(key);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.gearName);
        dest.writeString(this.gearOwner);
        dest.writeString(this.insurance);
        dest.writeString(this.warranty);
        dest.writeString(this.detail);
        dest.writeString(this.pic);
        dest.writeDouble(this.price);
    }

    public Gears(Parcel parcel){
        key = parcel.readString();
        gearName = parcel.readString();
        gearOwner = parcel.readString();
        insurance = parcel.readString();
        warranty = parcel.readString();
        detail = parcel.readString();
        pic = parcel.readString();
        price = parcel.readDouble();

    }

    public static final Parcelable.Creator<Gears> CREATOR = new Parcelable.Creator<Gears>(){

        @Override
        public Gears createFromParcel(Parcel parcel) {
            return new Gears(parcel);
        }

        @Override
        public Gears[] newArray(int size) {
            return new Gears[0];
        }
    };
}

