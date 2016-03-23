package com.ebusbar.dao;

/**
 * Created by Jelly on 2016/3/23.
 */
public class DeleteOrderDao{


    /**
     * returnStatus : 100
     * isSuccess : Y
     * OrderNo : EV_20160322_00000125
     * OrderStatus : 8
     * OrderType : EVR
     * CompanyID : C01
     * CustID : EV_20160321_00000077
     * OrgID : C00000001
     * OrgName : 科兴科学园
     * FacilityID : F000001
     * PlanBeginDateTime : 2016-03-22 14:38:22
     * PlanEndDateTime : 2016-03-22 15:08:22
     */

    private EvcOrderChangeEntity evc_order_change;

    public EvcOrderChangeEntity getEvc_order_change() {
        return evc_order_change;
    }

    public void setEvc_order_change(EvcOrderChangeEntity evc_order_change) {
        this.evc_order_change = evc_order_change;
    }

    public static class EvcOrderChangeEntity {
        private String returnStatus;
        private String isSuccess;
        private String OrderNo;
        private String OrderStatus;
        private String OrderType;
        private String CompanyID;
        private String CustID;
        private String OrgID;
        private String OrgName;
        private String FacilityID;
        private String PlanBeginDateTime;
        private String PlanEndDateTime;

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

        public String getOrderNo() {
            return OrderNo;
        }

        public void setOrderNo(String OrderNo) {
            this.OrderNo = OrderNo;
        }

        public String getOrderStatus() {
            return OrderStatus;
        }

        public void setOrderStatus(String OrderStatus) {
            this.OrderStatus = OrderStatus;
        }

        public String getOrderType() {
            return OrderType;
        }

        public void setOrderType(String OrderType) {
            this.OrderType = OrderType;
        }

        public String getCompanyID() {
            return CompanyID;
        }

        public void setCompanyID(String CompanyID) {
            this.CompanyID = CompanyID;
        }

        public String getCustID() {
            return CustID;
        }

        public void setCustID(String CustID) {
            this.CustID = CustID;
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

        public String getFacilityID() {
            return FacilityID;
        }

        public void setFacilityID(String FacilityID) {
            this.FacilityID = FacilityID;
        }

        public String getPlanBeginDateTime() {
            return PlanBeginDateTime;
        }

        public void setPlanBeginDateTime(String PlanBeginDateTime) {
            this.PlanBeginDateTime = PlanBeginDateTime;
        }

        public String getPlanEndDateTime() {
            return PlanEndDateTime;
        }

        public void setPlanEndDateTime(String PlanEndDateTime) {
            this.PlanEndDateTime = PlanEndDateTime;
        }
    }
}
