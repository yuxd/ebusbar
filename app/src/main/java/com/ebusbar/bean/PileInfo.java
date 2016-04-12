package com.ebusbar.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jelly on 2016/3/22.
 */
public class PileInfo implements Parcelable {

    /**
     * returnStatus : 100
     * isSuccess : Y
     * FacilityID : 0755300000000091
     * FacilityName : 91号桩
     * FacilityModel : 1
     * FacilityStatus : 0
     * Price : 0
     * OrgID : C00000001
     * OrgName : 深大站
     * addr : 深圳市南山区科园路南山科技园
     */

    private EvcFacilityGetEntity evc_facility_get;

    public EvcFacilityGetEntity getEvc_facility_get() {
        return evc_facility_get;
    }

    public void setEvc_facility_get(EvcFacilityGetEntity evc_facility_get) {
        this.evc_facility_get = evc_facility_get;
    }

    public static class EvcFacilityGetEntity implements Parcelable {
        private String returnStatus;
        private String isSuccess;
        private String FacilityID;
        private String FacilityName;
        private String FacilityModel;
        private String FacilityStatus;
        private String Price;
        private String OrgID;
        private String OrgName;
        private String addr;
        private String FacilityType;

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

        public String getOrgID() {
            return OrgID;
        }

        public void setOrgID(String OrgID) {
            this.OrgID = OrgID;
        }

        public String getOrgName() {
            return OrgName;
        }

        public void setOrgName(String OrgName) {
            this.OrgName = OrgName;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getFacilityType() {
            return FacilityType;
        }

        public void setFacilityType(String facilityType) {
            FacilityType = facilityType;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.returnStatus);
            dest.writeString(this.isSuccess);
            dest.writeString(this.FacilityID);
            dest.writeString(this.FacilityName);
            dest.writeString(this.FacilityModel);
            dest.writeString(this.FacilityStatus);
            dest.writeString(this.Price);
            dest.writeString(this.OrgID);
            dest.writeString(this.OrgName);
            dest.writeString(this.addr);
            dest.writeString(this.FacilityType);
        }

        public EvcFacilityGetEntity() {
        }

        protected EvcFacilityGetEntity(Parcel in) {
            this.returnStatus = in.readString();
            this.isSuccess = in.readString();
            this.FacilityID = in.readString();
            this.FacilityName = in.readString();
            this.FacilityModel = in.readString();
            this.FacilityStatus = in.readString();
            this.Price = in.readString();
            this.OrgID = in.readString();
            this.OrgName = in.readString();
            this.addr = in.readString();
            this.FacilityType = in.readString();
        }

        public static final Parcelable.Creator<EvcFacilityGetEntity> CREATOR = new Parcelable.Creator<EvcFacilityGetEntity>() {
            @Override
            public EvcFacilityGetEntity createFromParcel(Parcel source) {
                return new EvcFacilityGetEntity(source);
            }

            @Override
            public EvcFacilityGetEntity[] newArray(int size) {
                return new EvcFacilityGetEntity[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.evc_facility_get, flags);
    }

    public PileInfo() {
    }

    protected PileInfo(Parcel in) {
        this.evc_facility_get = in.readParcelable(EvcFacilityGetEntity.class.getClassLoader());
    }

    public static final Parcelable.Creator<PileInfo> CREATOR = new Parcelable.Creator<PileInfo>() {
        @Override
        public PileInfo createFromParcel(Parcel source) {
            return new PileInfo(source);
        }

        @Override
        public PileInfo[] newArray(int size) {
            return new PileInfo[size];
        }
    };
}
