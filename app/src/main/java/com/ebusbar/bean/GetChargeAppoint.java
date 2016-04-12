package com.ebusbar.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jelly on 2016/3/10.
 */
public class GetChargeAppoint implements Parcelable {

    /**
     * returnStatus : 100
     * isSuccess : Y
     * OrderNo : O-20160331-00384
     * OrderStatus : 0
     * OrderType : EVCP
     * CompanyID : C01
     * CustID : 86201603290000000667
     * OrgID : GZ0000001
     * OrgName : 红花岗1站
     * FacilityID : 0755300000000033
     * Longitude : 106.910680
     * Latitude : 27.602907
     * ChargingAmt : 0.00
     * ServiceAmt : 0.00
     * ChargingQty : 0.00
     * ChargingTime : 0
     * PlanCost : 1.50
     * PlanCostPayed : 0
     * PlanBeginDateTime : 2016-03-31 17:52:01
     * PlanEndDateTime : 2016-03-31 18:07:01
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
        private String Longitude;
        private String Latitude;
        private String ChargingAmt;
        private String ServiceAmt;
        private String ChargingQty;
        private String ChargingTime;
        private String PlanCost;
        private String PlanCostPayed;
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
            dest.writeString(this.Longitude);
            dest.writeString(this.Latitude);
            dest.writeString(this.ChargingAmt);
            dest.writeString(this.ServiceAmt);
            dest.writeString(this.ChargingQty);
            dest.writeString(this.ChargingTime);
            dest.writeString(this.PlanCost);
            dest.writeString(this.PlanCostPayed);
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
            this.Longitude = in.readString();
            this.Latitude = in.readString();
            this.ChargingAmt = in.readString();
            this.ServiceAmt = in.readString();
            this.ChargingQty = in.readString();
            this.ChargingTime = in.readString();
            this.PlanCost = in.readString();
            this.PlanCostPayed = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.evc_orders_get, flags);
    }

    public GetChargeAppoint() {
    }

    protected GetChargeAppoint(Parcel in) {
        this.evc_orders_get = in.readParcelable(EvcOrdersGetEntity.class.getClassLoader());
    }

    public static final Parcelable.Creator<GetChargeAppoint> CREATOR = new Parcelable.Creator<GetChargeAppoint>() {
        @Override
        public GetChargeAppoint createFromParcel(Parcel source) {
            return new GetChargeAppoint(source);
        }

        @Override
        public GetChargeAppoint[] newArray(int size) {
            return new GetChargeAppoint[size];
        }
    };
}
