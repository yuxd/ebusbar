package com.ebusbar.bean;

/**
 * 获取订单详情
 * Created by Jelly on 2016/3/23.
 */
public class OrderInfo {

    /**
     * returnStatus : 100
     * isSuccess : Y
     * OrderNo : O-20160409-00564
     * OrderStatus : 8
     * OrderType : EVCR
     * CompanyID : C01
     * CustID : 86201604070000000784
     * OrgID : C00000001
     * OrgName : 深大站
     * FacilityID : 0755300000000091
     * FacilityName : 91号桩
     * Longitude : 113.938000
     * ChargingAmt : 0.00
     * ServiceAmt : 0.00
     * ChargingQty : 0.00
     * ChargingTime : 0
     * PlanCost : 1.50
     * PlanCostPayed : 1
     * PlanBeginDateTime : 2016-04-09 12:42:24
     * PlanEndDateTime : 2016-04-09 12:57:24
     * RealBeginDateTime : 2016-04-09 12:42:48
     * RealEndDateTime : 2016-04-09 12:42:54
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
        private String FacilityName;
        private String Longitude;
        private String ChargingAmt;
        private String ServiceAmt;
        private String ChargingQty;
        private String ChargingTime;
        private String PlanCost;
        private String PlanCostPayed;
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

        public String getFacilityName() {
            return FacilityName;
        }

        public void setFacilityName(String FacilityName) {
            this.FacilityName = FacilityName;
        }

        public String getLongitude() {
            return Longitude;
        }

        public void setLongitude(String Longitude) {
            this.Longitude = Longitude;
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

        public String getPlanCost() {
            return PlanCost;
        }

        public void setPlanCost(String PlanCost) {
            this.PlanCost = PlanCost;
        }

        public String getPlanCostPayed() {
            return PlanCostPayed;
        }

        public void setPlanCostPayed(String PlanCostPayed) {
            this.PlanCostPayed = PlanCostPayed;
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
