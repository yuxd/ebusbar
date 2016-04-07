package com.ebusbar.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jelly on 2016/3/7.
 */
public class NearbyStation implements Parcelable {
    /**
     * returnStatus : 100
     * isSuccess : Y
     * OrgId : C00000001
     * OrgName : 科兴科学园
     * Longitude : 113.930250
     * Latitude : 22.540693
     * OrgStatus : 1
     * Addr : 深圳市南山区科园路南山科技园
     * isAvailable : 1
     * availableNum : 2
     * unavailableNum : 0
     */

    private EvcStationsGetEntity evc_stations_get;

    public EvcStationsGetEntity getEvc_stations_get() {
        return evc_stations_get;
    }

    public void setEvc_stations_get(EvcStationsGetEntity evc_stations_get) {
        this.evc_stations_get = evc_stations_get;
    }

    public static class EvcStationsGetEntity implements Parcelable {
        private String returnStatus;
        private String isSuccess;
        private String OrgId;
        private String OrgName;
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
            dest.writeString(this.Longitude);
            dest.writeString(this.Latitude);
            dest.writeString(this.OrgStatus);
            dest.writeString(this.Addr);
            dest.writeString(this.isAvailable);
            dest.writeString(this.availableNum);
            dest.writeString(this.unavailableNum);
        }

        public EvcStationsGetEntity() {
        }

        protected EvcStationsGetEntity(Parcel in) {
            this.returnStatus = in.readString();
            this.isSuccess = in.readString();
            this.OrgId = in.readString();
            this.OrgName = in.readString();
            this.Longitude = in.readString();
            this.Latitude = in.readString();
            this.OrgStatus = in.readString();
            this.Addr = in.readString();
            this.isAvailable = in.readString();
            this.availableNum = in.readString();
            this.unavailableNum = in.readString();
        }

        public static final Creator<EvcStationsGetEntity> CREATOR = new Creator<EvcStationsGetEntity>() {
            @Override
            public EvcStationsGetEntity createFromParcel(Parcel source) {
                return new EvcStationsGetEntity(source);
            }

            @Override
            public EvcStationsGetEntity[] newArray(int size) {
                return new EvcStationsGetEntity[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.evc_stations_get, flags);
    }

    public NearbyStation() {
    }

    protected NearbyStation(Parcel in) {
        this.evc_stations_get = in.readParcelable(EvcStationsGetEntity.class.getClassLoader());
    }

    public static final Parcelable.Creator<NearbyStation> CREATOR = new Parcelable.Creator<NearbyStation>() {
        @Override
        public NearbyStation createFromParcel(Parcel source) {
            return new NearbyStation(source);
        }

        @Override
        public NearbyStation[] newArray(int size) {
            return new NearbyStation[size];
        }
    };
}
