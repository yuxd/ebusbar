package com.ebusbar.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jelly on 2016/3/24.
 */
public class Bill implements Parcelable {

    /**
     * returnStatus : 100
     * isSuccess : Y
     * LogId : 86201603240000000282
     * CustId : EV_20160319_00000057
     * Amt : -0.01
     * LogType : 3
     * OrderNo : EV_20160322_00000144
     * CreateDate : 2016-03-24 15:58:50
     */

    private CrmBalancelogGetEntity crm_balancelog_get;

    public CrmBalancelogGetEntity getCrm_balancelog_get() {
        return crm_balancelog_get;
    }

    public void setCrm_balancelog_get(CrmBalancelogGetEntity crm_balancelog_get) {
        this.crm_balancelog_get = crm_balancelog_get;
    }

    public static class CrmBalancelogGetEntity implements Parcelable {
        private String returnStatus;
        private String isSuccess;
        private String LogId;
        private String CustId;
        private String Amt;
        private String LogType;
        private String OrderNo;
        private String CreateDate;

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

        public String getLogId() {
            return LogId;
        }

        public void setLogId(String LogId) {
            this.LogId = LogId;
        }

        public String getCustId() {
            return CustId;
        }

        public void setCustId(String CustId) {
            this.CustId = CustId;
        }

        public String getAmt() {
            return Amt;
        }

        public void setAmt(String Amt) {
            this.Amt = Amt;
        }

        public String getLogType() {
            return LogType;
        }

        public void setLogType(String LogType) {
            this.LogType = LogType;
        }

        public String getOrderNo() {
            return OrderNo;
        }

        public void setOrderNo(String OrderNo) {
            this.OrderNo = OrderNo;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String CreateDate) {
            this.CreateDate = CreateDate;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.returnStatus);
            dest.writeString(this.isSuccess);
            dest.writeString(this.LogId);
            dest.writeString(this.CustId);
            dest.writeString(this.Amt);
            dest.writeString(this.LogType);
            dest.writeString(this.OrderNo);
            dest.writeString(this.CreateDate);
        }

        public CrmBalancelogGetEntity() {
        }

        protected CrmBalancelogGetEntity(Parcel in) {
            this.returnStatus = in.readString();
            this.isSuccess = in.readString();
            this.LogId = in.readString();
            this.CustId = in.readString();
            this.Amt = in.readString();
            this.LogType = in.readString();
            this.OrderNo = in.readString();
            this.CreateDate = in.readString();
        }

        public static final Creator<CrmBalancelogGetEntity> CREATOR = new Creator<CrmBalancelogGetEntity>() {
            @Override
            public CrmBalancelogGetEntity createFromParcel(Parcel source) {
                return new CrmBalancelogGetEntity(source);
            }

            @Override
            public CrmBalancelogGetEntity[] newArray(int size) {
                return new CrmBalancelogGetEntity[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.crm_balancelog_get, flags);
    }

    public Bill() {
    }

    protected Bill(Parcel in) {
        this.crm_balancelog_get = in.readParcelable(CrmBalancelogGetEntity.class.getClassLoader());
    }

    public static final Parcelable.Creator<Bill> CREATOR = new Parcelable.Creator<Bill>() {
        @Override
        public Bill createFromParcel(Parcel source) {
            return new Bill(source);
        }

        @Override
        public Bill[] newArray(int size) {
            return new Bill[size];
        }
    };
}
