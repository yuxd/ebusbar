package com.ebusbar.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 开始充电
 * Created by Jelly on 2016/3/22.
 */
public class StartCharge {

    /**
     * returnStatus : 100
     * isSuccess : Y
     * OrderNo : 86201603250000000347
     * OrderStatus : 2
     * OrderType : EVCR
     * CompanyID : C01
     * CustID : 18806002800000000019
     * OrgID : C00000001
     * OrgName : 科兴科学园
     * FacilityID : 0755300000000033
     * PlanBeginDateTime : 2016-03-25 13:55:38
     * PlanEndDateTime : 2016-03-25 14:10:38
     * RealBeginDateTime : 2016-03-25 13:56:06
     * Tel : 0755-82891087
     */

    private EvcOrderChangeEntity evc_order_change;

    public EvcOrderChangeEntity getEvc_order_change() {
        return evc_order_change;
    }

    public void setEvc_order_change(EvcOrderChangeEntity evc_order_change) {
        this.evc_order_change = evc_order_change;
    }

    public static class EvcOrderChangeEntity implements Parcelable {

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
        private String RealBeginDateTime;
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
            dest.writeString(this.PlanBeginDateTime);
            dest.writeString(this.PlanEndDateTime);
            dest.writeString(this.RealBeginDateTime);
            dest.writeString(this.Tel);
        }

        public EvcOrderChangeEntity() {
        }

        protected EvcOrderChangeEntity(Parcel in) {
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
            this.PlanBeginDateTime = in.readString();
            this.PlanEndDateTime = in.readString();
            this.RealBeginDateTime = in.readString();
            this.Tel = in.readString();
        }

        public static final Parcelable.Creator<EvcOrderChangeEntity> CREATOR = new Parcelable.Creator<EvcOrderChangeEntity>() {
            @Override
            public EvcOrderChangeEntity createFromParcel(Parcel source) {
                return new EvcOrderChangeEntity(source);
            }

            @Override
            public EvcOrderChangeEntity[] newArray(int size) {
                return new EvcOrderChangeEntity[size];
            }
        };
    }
}
