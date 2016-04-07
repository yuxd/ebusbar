package com.ebusbar.bean;

/**
 * Created by Jelly on 2016/3/22.
 */
public class PileInfo {

    /**
     * returnStatus : 100
     * isSuccess : Y
     * FacilityID : F000004
     * FacilityName : 12号充电桩
     * FacilityModel : 快充200V
     * FacilityStatus : 1
     * Price : 0
     * FacilityNo : 00011
     * OrgID : C00000002
     * OrgName : 深大站
     */

    private EvcFacilityGetEntity evc_facility_get;

    public EvcFacilityGetEntity getEvc_facility_get() {
        return evc_facility_get;
    }

    public void setEvc_facility_get(EvcFacilityGetEntity evc_facility_get) {
        this.evc_facility_get = evc_facility_get;
    }

    public static class EvcFacilityGetEntity {
        private String returnStatus;
        private String isSuccess;
        private String FacilityID;
        private String FacilityName;
        private String FacilityModel;
        private String FacilityStatus;
        private String Price;
        private String FacilityNo;
        private String OrgID;
        private String OrgName;

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

        public String getFacilityNo() {
            return FacilityNo;
        }

        public void setFacilityNo(String FacilityNo) {
            this.FacilityNo = FacilityNo;
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
    }
}
