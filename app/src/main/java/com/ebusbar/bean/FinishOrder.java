package com.ebusbar.bean;

/**
 * 结束订单
 * Created by Jelly on 2016/3/22.
 */
public class FinishOrder {

    /**
     * returnStatus : 100
     * isSuccess : Y
     * OrderNo : EV_20160322_00000123
     * OrderStatus : 16
     * OrderType : 1
     * CompanyID : C01
     * CustID : EV_20160321_00000077
     * OrgID : C00000001
     * FacilityID : F000001
     * PlanBeginDateTime : 2016-03-22 14:26:33
     * PlanEndDateTime : 2016-03-22 14:56:33
     */

    private EvcOrderCancelEntity evc_order_cancel;

    public EvcOrderCancelEntity getEvc_order_cancel() {
        return evc_order_cancel;
    }

    public void setEvc_order_cancel(EvcOrderCancelEntity evc_order_cancel) {
        this.evc_order_cancel = evc_order_cancel;
    }

    public static class EvcOrderCancelEntity {
        private String returnStatus;
        private String isSuccess;
        private String OrderNo;
        private String OrderStatus;
        private String OrderType;
        private String CompanyID;
        private String CustID;
        private String OrgID;
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
