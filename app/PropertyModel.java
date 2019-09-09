package com.dbs.easyhomeloan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PropertyModel implements Serializable {

    @SerializedName("propertiesList")
    @Expose
    private List<PropertiesList> propertiesList = null;
    @SerializedName("recommended")
    @Expose
    private Boolean recommended;

    public List<PropertiesList> getPropertiesList() {
        return propertiesList;
    }

    public void setPropertiesList(List<PropertiesList> propertiesList) {
        this.propertiesList = propertiesList;
    }

    public Boolean getRecommended() {
        return recommended;
    }

    public void setRecommended(Boolean recommended) {
        this.recommended = recommended;
    }

    public class PropertiesList implements Serializable{
        @SerializedName("propertyId")
        @Expose
        private String propertyId;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("price")
        @Expose
        private Integer price;
        @SerializedName("propertyName")
        @Expose
        private String propertyName;
        @SerializedName("area")
        @Expose
        private Integer area;
        @SerializedName("emi10yrs")
        @Expose
        private String emi10yrs;
        @SerializedName("emi15yrs")
        @Expose
        private String emi15yrs;
        @SerializedName("emi20yrs")
        @Expose
        private String emi20yrs;
        @SerializedName("addr1")
        @Expose
        private String addr1;
        @SerializedName("addr2")
        @Expose
        private String addr2;
        @SerializedName("recommended")
        @Expose
        private Object recommended;

        public String getPropertyId() {
            return propertyId;
        }

        public void setPropertyId(String propertyId) {
            this.propertyId = propertyId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public String getPropertyName() {
            return propertyName;
        }

        public void setPropertyName(String propertyName) {
            this.propertyName = propertyName;
        }

        public Integer getArea() {
            return area;
        }

        public void setArea(Integer area) {
            this.area = area;
        }

        public String getEmi10yrs() {
            return emi10yrs;
        }

        public void setEmi10yrs(String emi10yrs) {
            this.emi10yrs = emi10yrs;
        }

        public String getEmi15yrs() {
            return emi15yrs;
        }

        public void setEmi15yrs(String emi15yrs) {
            this.emi15yrs = emi15yrs;
        }

        public String getEmi20yrs() {
            return emi20yrs;
        }

        public void setEmi20yrs(String emi20yrs) {
            this.emi20yrs = emi20yrs;
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

        public Object getRecommended() {
            return recommended;
        }

        public void setRecommended(Object recommended) {
            this.recommended = recommended;
        }

    }
}

