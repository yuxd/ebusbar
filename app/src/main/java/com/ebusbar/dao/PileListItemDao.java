package com.ebusbar.dao;

/**
 * Created by Jelly on 2016/3/21.
 */
public class PileListItemDao {

    /**
     * returnStatus : 100
     * isSuccess : Y
     * OrgId : C00000001
     * OrgName : 科兴科学园
     * FacilityID : F000001
     * FacilityName : 1号充电桩
     * FacilityModel : 快充200V
     * FacilityStatus : 1
     * Price : 0
     * FacilityNo : 00001
     */

    private EvcFacilitiesGetEntity evc_facilities_get;

    public EvcFacilitiesGetEntity getEvc_facilities_get() {
        return evc_facilities_get;
    }

    public void setEvc_facilities_get(EvcFacilitiesGetEntity evc_facilities_get) {
        this.evc_facilities_get = evc_facilities_get;
    }

    public static class EvcFacilitiesGetEntity {
        private String returnStatus;
        private String isSuccess;
        private String OrgId;
        private String OrgName;
        private String FacilityID;
        private String FacilityName;
        private String FacilityModel;
        private String FacilityStatus;
        private String Price;
        private String FacilityNo;

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
    }
}
