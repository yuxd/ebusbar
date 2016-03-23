package com.ebusbar.dao;

/**
 * Created by Jelly on 2016/3/10.
 */
public class GetChargeAppointDao {

    /**
     * returnStatus : 100
     * isSuccess : Y
     * OrderNo : 86201603230000000165
     * OrderStatus : 1
     * OrderType : EVCP
     * CompanyID : C01
     * CustID : EV_20160319_00000057
     * OrgID : C00000002
     * OrgName : 深大站
     * FacilityID : F000004
     * ChargingAmt : 0.00
     * ServiceAmt : 0.00
     * ChargingQty : 0.00
     * PlanBeginDateTime : 2016-03-23 13:55:47
     * PlanEndDateTime : 2016-03-23 14:10:47
     * Tel : 0755-82891087
     */

    private EvcOrdersGetEntity evc_orders_get;

    public EvcOrdersGetEntity getEvc_orders_get() {
        return evc_orders_get;
    }

    public void setEvc_orders_get(EvcOrdersGetEntity evc_orders_get) {
        this.evc_orders_get = evc_orders_get;
    }

    public static class EvcOrdersGetEntity {
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
        private String ChargingAmt;
        private String ServiceAmt;
        private String ChargingQty;
        private String PlanBeginDateTime;
        private String PlanEndDateTime;
        private String Tel;

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

        public String getChargingAmt() {
            return ChargingAmt;
        }

        public void setChargingAmt(String ChargingAmt) {
            this.ChargingAmt = ChargingAmt;
        }

        public String getServiceAmt() {
            return ServiceAmt;
        }

        public void setServiceAmt(String ServiceAmt) {
            this.ServiceAmt = ServiceAmt;
        }

        public String getChargingQty() {
            return ChargingQty;
        }

        public void setChargingQty(String ChargingQty) {
            this.ChargingQty = ChargingQty;
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

        public String getTel() {
            return Tel;
        }

        public void setTel(String Tel) {
            this.Tel = Tel;
        }
    }
}
