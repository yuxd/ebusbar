package com.ebusbar.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jelly on 2016/3/21.
 */
public class PileListItem {

    /**
     * returnStatus : 100
     * isSuccess : Y
     * OrgId : C00000001
     * OrgName : 深大站
     * FacilityID : 0755300000000033
     * FacilityName : 99号充电桩
     * FacilityType : 0
     * FacilityModel : 1
     * FacilityStatus : 0
     * Price : 0
     * ApplicableCar : 特斯拉-宝马
     */

    private EvcFacilitiesGetEntity evc_facilities_get;

    public EvcFacilitiesGetEntity getEvc_facilities_get() {
        return evc_facilities_get;
    }

    public void setEvc_facilities_get(EvcFacilitiesGetEntity evc_facilities_get) {
        this.evc_facilities_get = evc_facilities_get;
    }

    public static class EvcFacilitiesGetEntity implements Parcelable {
        private String returnStatus;
        private String isSuccess;
        private String OrgId;
        private String OrgName;
        private String FacilityID;
        private String FacilityName;
        private String FacilityType;
        private String FacilityModel;
        private String FacilityStatus;
        private String Price;
        private String ApplicableCar;

        public String getReturnStatus() {
            return returnStatus;
        }

        public void setReturnStatus(String returnStatus) {
            this.returnStatus = returnStatus;
        }

        public String getIsSuccess() {
            return isSuccess;
        }

        public void setIsSuccess(String isSuccess) {
            this.isSuccess = isSuccess;
        }

        public String getOrgId() {
            return OrgId;
        }

        public void setOrgId(String OrgId) {
            this.OrgId = OrgId;
        }

        public String getOrgName() {
            return OrgName;
        }

        public void setOrgName(String OrgName) {
            this.OrgName = OrgName;
        }

        public String getFacilityID() {
            return FacilityID;
        }

        public void setFacilityID(String FacilityID) {
            this.FacilityID = FacilityID;
        }

        public String getFacilityName() {
            return FacilityName;
        }

        public void setFacilityName(String FacilityName) {
            this.FacilityName = FacilityName;
        }

        public String getFacilityType() {
            return FacilityType;
        }

        public void setFacilityType(String FacilityType) {
            this.FacilityType = FacilityType;
        }

        public String getFacilityModel() {
            return FacilityModel;
        }

        public void setFacilityModel(String FacilityModel) {
            this.FacilityModel = FacilityModel;
        }

        public String getFacilityStatus() {
            return FacilityStatus;
        }

        public void setFacilityStatus(String FacilityStatus) {
            this.FacilityStatus = FacilityStatus;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String Price) {
            this.Price = Price;
        }

        public String getApplicableCar() {
            return ApplicableCar;
        }

        public void setApplicableCar(String ApplicableCar) {
            this.ApplicableCar = ApplicableCar;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.returnStatus);
            dest.writeString(this.isSuccess);
            dest.writeString(this.OrgId);
            dest.writeString(this.OrgName);
            dest.writeString(this.FacilityID);
            dest.writeString(this.FacilityName);
            dest.writeString(this.FacilityType);
            dest.writeString(this.FacilityModel);
            dest.writeString(this.FacilityStatus);
            dest.writeString(this.Price);
            dest.writeString(this.ApplicableCar);
        }

        public EvcFacilitiesGetEntity() {
        }

        protected EvcFacilitiesGetEntity(Parcel in) {
            this.returnStatus = in.readString();
            this.isSuccess = in.readString();
            this.OrgId = in.readString();
            this.OrgName = in.readString();
            this.FacilityID = in.readString();
            this.FacilityName = in.readString();
            this.FacilityType = in.readString();
            this.FacilityModel = in.readString();
            this.FacilityStatus = in.readString();
            this.Price = in.readString();
            this.ApplicableCar = in.readString();
        }

        public static final Parcelable.Creator<EvcFacilitiesGetEntity> CREATOR = new Parcelable.Creator<EvcFacilitiesGetEntity>() {
            @Override
            public EvcFacilitiesGetEntity createFromParcel(Parcel source) {
                return new EvcFacilitiesGetEntity(source);
            }

            @Override
            public EvcFacilitiesGetEntity[] newArray(int size) {
                return new EvcFacilitiesGetEntity[size];
            }
        };
    }
}
