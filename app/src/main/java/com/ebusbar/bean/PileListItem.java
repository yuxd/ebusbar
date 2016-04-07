package com.ebusbar.bean;

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

    public static class EvcFacilitiesGetEntity {
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
    }
}
