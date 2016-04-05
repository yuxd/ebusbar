package com.ebusbar.dao;

/**
 * Created by Jelly on 2016/3/31.
 */
public class PayingAppointOrderDao {
    /**
     * interfaceName : evc_planorder_set
     * returnStatus : 100
     * isSuccess : Y
     * data : {"OrderNo":"O-20160331-00382","OrderStatus":"0","OrderType":"EVCP","CompanyID":"C01","CustID":"86201603290000000665","OrgID":"GZ0000001","OrgName":"红花岗1站","FacilityID":"0755300000000033","Longitude":"106.910680","Latitude":"27.602907","ChargingAmt":"0.00","ServiceAmt":"0.00","ChargingQty":"0.00","ChargingTime":"0","PlanCost":"3.00","PlanBeginDateTime":"2016-03-31 17:23:23","PlanEndDateTime":"2016-03-31 17:53:23","Tel":"0755-82891087"}
     */

    private String interfaceName;
    private String returnStatus;
    private String isSuccess;
    /**
     * OrderNo : O-20160331-00382
     * OrderStatus : 0
     * OrderType : EVCP
     * CompanyID : C01
     * CustID : 86201603290000000665
     * OrgID : GZ0000001
     * OrgName : 红花岗1站
     * FacilityID : 0755300000000033
     * Longitude : 106.910680
     * Latitude : 27.602907
     * ChargingAmt : 0.00
     * ServiceAmt : 0.00
     * ChargingQty : 0.00
     * ChargingTime : 0
     * PlanCost : 3.00
     * PlanBeginDateTime : 2016-03-31 17:23:23
     * PlanEndDateTime : 2016-03-31 17:53:23
     * Tel : 0755-82891087
     */

    private DataEntity data;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

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

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        private String OrderNo;
        private String OrderStatus;
        private String OrderType;
        private String CompanyID;
        private String CustID;
        private String OrgID;
        private String OrgName;
        private String FacilityID;
        private String Longitude;
        private String Latitude;
        private String ChargingAmt;
        private String ServiceAmt;
        private String ChargingQty;
        private String ChargingTime;
        private String PlanCost;
        private String PlanBeginDateTime;
        private String PlanEndDateTime;
        private String Tel;

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

        public String getLongitude() {
            return Longitude;
        }

        public void setLongitude(String Longitude) {
            this.Longitude = Longitude;
        }

        public String getLatitude() {
            return Latitude;
        }

        public void setLatitude(String Latitude) {
            this.Latitude = Latitude;
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
