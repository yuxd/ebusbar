package com.ebusbar.dao;

/**
 * 获取订单详情
 * Created by Jelly on 2016/3/23.
 */
public class OrderInfoDao {

    /**
     * returnStatus : 100
     * isSuccess : Y
     * OrderNo : O-20160326-00107
     * OrderStatus : 4
     * OrderType : EVCR
     * CompanyID : C01
     * CustID : 86201603250000000351
     * OrgID : C00000001
     * OrgName : 科兴科学园
     * FacilityID : 0755300000000033
     * ChargingAmt : 12.00
     * ServiceAmt : 0.03
     * ChargingQty : 123.00
     * ChargingTime : 21
     * PlanBeginDateTime : 2016-03-26 11:36:47
     * PlanEndDateTime : 2016-03-26 11:51:47
     * RealBeginDateTime : 03 26 2016 11:37AM
     * RealEndDateTime : 2016-03-28 18:32:46
     * Tel : 0755-82891087
     */

    private EvcOrderGetEntity evc_order_get;

    public EvcOrderGetEntity getEvc_order_get() {
        return evc_order_get;
    }

    public void setEvc_order_get(EvcOrderGetEntity evc_order_get) {
        this.evc_order_get = evc_order_get;
    }

    public static class EvcOrderGetEntity {
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
        private String ChargingTime;
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

        public String getChargingTime() {
            return ChargingTime;
        }

        public void setChargingTime(String ChargingTime) {
            this.ChargingTime = ChargingTime;
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
