package com.ebusbar.dao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jelly on 2016/3/30.
 */
public class AllStationDao extends SearchDao implements Parcelable {

    /**
     * returnStatus : 100
     * isSuccess : Y
     * OrgId : C00000001
     * OrgName : 深大站
     * City : 深圳市
     * CityCode : 440300
     * Longitude : 113.938000
     * Latitude : 22.539223
     * OrgStatus : 1
     * Addr : 深圳市南山区科园路南山科技园
     * isAvailable : 0
     * availableNum : 0
     * unavailableNum : 0
     */

    private EvcStationsGetallEntity evc_stations_getall;

    public EvcStationsGetallEntity getEvc_stations_getall() {
        return evc_stations_getall;
    }

    public void setEvc_stations_getall(EvcStationsGetallEntity evc_stations_getall) {
        this.evc_stations_getall = evc_stations_getall;
    }

    public static class EvcStationsGetallEntity implements Parcelable {
        private String returnStatus;
        private String isSuccess;
        private String OrgId;
        private String OrgName;
        private String City;
        private String CityCode;
        private String Longitude;
        private String Latitude;
        private String OrgStatus;
        private String Addr;
        private String isAvailable;
        private String availableNum;
        private String unavailableNum;

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

        public String getCity() {
            return City;
        }

        public void setCity(String City) {
            this.City = City;
        }

        public String getCityCode() {
            return CityCode;
        }

        public void setCityCode(String CityCode) {
            this.CityCode = CityCode;
        }

        public String getLongitude() {
            return Longitude;
        }

        public void setLongitude(String Longitude) {
            this.Longitude = Longitude;
        }

        public String getLatitude() {
            return Latitude;
        }

        public void setLatitude(String Latitude) {
            this.Latitude = Latitude;
        }

        public String getOrgStatus() {
            return OrgStatus;
        }

        public void setOrgStatus(String OrgStatus) {
            this.OrgStatus = OrgStatus;
        }

        public String getAddr() {
            return Addr;
        }

        public void setAddr(String Addr) {
            this.Addr = Addr;
        }

        public String getIsAvailable() {
            return isAvailable;
        }

        public void setIsAvailable(String isAvailable) {
            this.isAvailable = isAvailable;
        }

        public String getAvailableNum() {
            return availableNum;
        }

        public void setAvailableNum(String availableNum) {
            this.availableNum = availableNum;
        }

        public String getUnavailableNum() {
            return unavailableNum;
        }

        public void setUnavailableNum(String unavailableNum) {
            this.unavailableNum = unavailableNum;
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
            dest.writeString(this.City);
            dest.writeString(this.CityCode);
            dest.writeString(this.Longitude);
            dest.writeString(this.Latitude);
            dest.writeString(this.OrgStatus);
            dest.writeString(this.Addr);
            dest.writeString(this.isAvailable);
            dest.writeString(this.availableNum);
            dest.writeString(this.unavailableNum);
        }

        public EvcStationsGetallEntity() {
        }

        protected EvcStationsGetallEntity(Parcel in) {
            this.returnStatus = in.readString();
            this.isSuccess = in.readString();
            this.OrgId = in.readString();
            this.OrgName = in.readString();
            this.City = in.readString();
            this.CityCode = in.readString();
            this.Longitude = in.readString();
            this.Latitude = in.readString();
            this.OrgStatus = in.readString();
            this.Addr = in.readString();
            this.isAvailable = in.readString();
            this.availableNum = in.readString();
            this.unavailableNum = in.readString();
        }

        public static final Creator<EvcStationsGetallEntity> CREATOR = new Creator<EvcStationsGetallEntity>() {
            @Override
            public EvcStationsGetallEntity createFromParcel(Parcel source) {
                return new EvcStationsGetallEntity(source);
            }

            @Override
            public EvcStationsGetallEntity[] newArray(int size) {
                return new EvcStationsGetallEntity[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.evc_stations_getall, flags);
    }

    public AllStationDao() {
    }

    protected AllStationDao(Parcel in) {
        this.evc_stations_getall = in.readParcelable(EvcStationsGetallEntity.class.getClassLoader());
    }

    public static final Parcelable.Creator<AllStationDao> CREATOR = new Parcelable.Creator<AllStationDao>() {
        @Override
        public AllStationDao createFromParcel(Parcel source) {
            return new AllStationDao(source);
        }

        @Override
        public AllStationDao[] newArray(int size) {
            return new AllStationDao[size];
        }
    };
}
