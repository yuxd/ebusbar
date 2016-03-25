package com.ebusbar.dao;

/**
 * Created by Jelly on 2016/3/7.
 */
public class PositionListItemDao {


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

    public static class EvcStationsGetEntity {
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
    }
}
