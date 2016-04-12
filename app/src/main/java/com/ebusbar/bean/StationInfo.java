package com.ebusbar.bean;

/**
 * Created by Jelly on 2016/4/11.
 */
public class StationInfo{
    /**
     * interfaceName : evc_station_get
     * returnStatus : 100
     * isSuccess : Y
     * data : {"OrgId":"C00000001","OrgName":"深大站","Longitude":"113.938000","Latitude":"22.539223","OrgStatus":"1","Addr":"深圳市南山区科园路南山科技园","isAvailable":"1","availableNum":"1","unavailableNum":"0","CompanyName":"巴斯巴网络","Tel":"15817220196"}
     */
    private String interfaceName;
    private String returnStatus;
    private String isSuccess;
    /**
     * OrgId : C00000001
     * OrgName : 深大站
     * Longitude : 113.938000
     * Latitude : 22.539223
     * OrgStatus : 1
     * Addr : 深圳市南山区科园路南山科技园
     * isAvailable : 1
     * availableNum : 1
     * unavailableNum : 0
     * CompanyName : 巴斯巴网络
     * Tel : 15817220196
     */

    private DataEntity data;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

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

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        private String OrgId;
        private String OrgName;
        private String Longitude;
        private String Latitude;
        private String OrgStatus;
        private String Addr;
        private String isAvailable;
        private String availableNum;
        private String unavailableNum;
        private String CompanyName;
        private String Tel;

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

        public String getCompanyName() {
            return CompanyName;
        }

        public void setCompanyName(String CompanyName) {
            this.CompanyName = CompanyName;
        }

        public String getTel() {
            return Tel;
        }

        public void setTel(String Tel) {
            this.Tel = Tel;
        }
    }
}
