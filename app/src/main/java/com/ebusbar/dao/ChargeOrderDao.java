package com.ebusbar.dao;

/**
 * Created by Jelly on 2016/3/22.
 */
public class ChargeOrderDao {

    /**
     * returnStatus : 100
     * isSuccess : Y
     * OrderNo : EV_20160322_00000084
     * OrderStatus : 1
     * OrderType : 2
     * CompanyID : C01
     * CustID : EV_20160321_00000077
     * OrgID : C00000001
     * FacitityID : F000001
     * PlanBeginDateTime : 2016-03-22 10:32:50
     * PlanEndDateTime : 2016-03-22 11:02:50
     */

    private EvcOrderSetEntity evc_order_set;

    public EvcOrderSetEntity getEvc_order_set() {
        return evc_order_set;
    }

    public void setEvc_order_set(EvcOrderSetEntity evc_order_set) {
        this.evc_order_set = evc_order_set;
    }

    public static class EvcOrderSetEntity {
        private String returnStatus;
        private String isSuccess;
        private String OrderNo;
        private String OrderStatus;
        private String OrderType;
        private String CompanyID;
        private String CustID;
        private String OrgID;
        private String FacitityID;
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

        public String getFacitityID() {
            return FacitityID;
        }

        public void setFacitityID(String FacitityID) {
            this.FacitityID = FacitityID;
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
