package com.dbs.easyhomeloan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompareModel {

    @SerializedName("property_id")
    @Expose
    private String propertyId;
    @SerializedName("property_name")
    @Expose
    private String propertyName;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("Addr1")
    @Expose
    private String addr1;
    @SerializedName("Addr2")
    @Expose
    private String addr2;
    @SerializedName("Area")
    @Expose
    private Integer area;

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }
}
