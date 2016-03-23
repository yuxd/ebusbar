package com.ebusbar.dao;

/**
 * Created by Jelly on 2016/3/23.
 */
public class P3PayDao {

    /**
     * returnStatus : 100
     * isSuccess : Y
     * OrderNo : EV_20160322_00000137
     * OrderStatus : 8
     * OrderType : EVCR
     * CompanyID : C01
     * CustID : EV_20160319_00000057
     * OrgID : C00000002
     * OrgName : 深大站
     * FacilityID : F000004
     * ChargingAmt : 0.01
     * ServiceAmt : 0.00
     * ChargingQty : 0.00
     * PlanBeginDateTime : 2016-03-22 18:26:17
     * PlanEndDateTime : 2016-03-22 18:41:17
     * RealBeginDateTime : 2016-03-22 18:26:22
     * RealEndDateTime : 2016-03-23 17:12:54
     * Tel : 0755-82891087
     */

    private EvcOrderPayEntity evc_order_pay;

    public EvcOrderPayEntity getEvc_order_pay() {
        return evc_order_pay;
    }

    public void setEvc_order_pay(EvcOrderPayEntity evc_order_pay) {
        this.evc_order_pay = evc_order_pay;
    }

    public static class EvcOrderPayEntity {
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
        private String RealBeginDateTime;
        private String RealEndDateTime;
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

        public String getRealBeginDateTime() {
            return RealBeginDateTime;
        }

        public void setRealBeginDateTime(String RealBeginDateTime) {
            this.RealBeginDateTime = RealBeginDateTime;
        }

        public String getRealEndDateTime() {
            return RealEndDateTime;
        }

        public void setRealEndDateTime(String RealEndDateTime) {
            this.RealEndDateTime = RealEndDateTime;
        }

        public String getTel() {
            return Tel;
        }

        public void setTel(String Tel) {
            this.Tel = Tel;
        }
    }
}
