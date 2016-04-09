package com.ebusbar.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jelly on 2016/3/10.
 */
public class PendingOrder {

    /**
     * returnStatus : 100
     * isSuccess : Y
     * OrderNo : EV_20160322_00000118
     * OrderStatus : 16
     * OrderType : 1
     * CompanyID : C01
     * CustID : EV_20160321_00000077
     * OrgID : C00000001
     * OrgName : 科兴科学园
     * FacilityID : F000001
     * ChargingAmt : 0.00
     * ServiceAmt : 0.00
     * ChargingQty : 0.00
     * PlanBeginDateTime : 2016-03-22 14:12:35
     * PlanEndDateTime : 2016-03-22 14:42:35
     * Tel : 0755-82891087
     */

    private EvcOrdersGetEntity evc_orders_get;

    public EvcOrdersGetEntity getEvc_orders_get() {
        return evc_orders_get;
    }

    public void setEvc_orders_get(EvcOrdersGetEntity evc_orders_get) {
        this.evc_orders_get = evc_orders_get;
    }

    public static class EvcOrdersGetEntity implements Parcelable {
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.returnStatus);
            dest.writeString(this.isSuccess);
            dest.writeString(this.OrderNo);
            dest.writeString(this.OrderStatus);
            dest.writeString(this.OrderType);
            dest.writeString(this.CompanyID);
            dest.writeString(this.CustID);
            dest.writeString(this.OrgID);
            dest.writeString(this.OrgName);
            dest.writeString(this.FacilityID);
            dest.writeString(this.ChargingAmt);
            dest.writeString(this.ServiceAmt);
            dest.writeString(this.ChargingQty);
            dest.writeString(this.PlanBeginDateTime);
            dest.writeString(this.PlanEndDateTime);
            dest.writeString(this.Tel);
        }

        public EvcOrdersGetEntity() {
        }

        protected EvcOrdersGetEntity(Parcel in) {
            this.returnStatus = in.readString();
            this.isSuccess = in.readString();
            this.OrderNo = in.readString();
            this.OrderStatus = in.readString();
            this.OrderType = in.readString();
            this.CompanyID = in.readString();
            this.CustID = in.readString();
            this.OrgID = in.readString();
            this.OrgName = in.readString();
            this.FacilityID = in.readString();
            this.ChargingAmt = in.readString();
            this.ServiceAmt = in.readString();
            this.ChargingQty = in.readString();
            this.PlanBeginDateTime = in.readString();
            this.PlanEndDateTime = in.readString();
            this.Tel = in.readString();
        }

        public static final Parcelable.Creator<EvcOrdersGetEntity> CREATOR = new Parcelable.Creator<EvcOrdersGetEntity>() {
            @Override
            public EvcOrdersGetEntity createFromParcel(Parcel source) {
                return new EvcOrdersGetEntity(source);
            }

            @Override
            public EvcOrdersGetEntity[] newArray(int size) {
                return new EvcOrdersGetEntity[size];
            }
        };
    }
}
