package com.ebusbar.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jelly on 2016/3/23.
 */
public class ChargeCardItem implements Parcelable {


    /**
     * returnStatus : 100
     * isSuccess : Y
     * CustId : EV_20160319_00000057
     * AccountID : 86201603230000000201
     * AccountNo : 123
     * AccountType : ChargeType
     */

    private CrmAccountsGetEntity crm_accounts_get;

    public CrmAccountsGetEntity getCrm_accounts_get() {
        return crm_accounts_get;
    }

    public void setCrm_accounts_get(CrmAccountsGetEntity crm_accounts_get) {
        this.crm_accounts_get = crm_accounts_get;
    }

    public static class CrmAccountsGetEntity implements Parcelable {

        private String returnStatus;
        private String isSuccess;
        private String CustId;
        private String AccountID;
        private String AccountNo;
        private String AccountType;

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

        public String getCustId() {
            return CustId;
        }

        public void setCustId(String CustId) {
            this.CustId = CustId;
        }

        public String getAccountID() {
            return AccountID;
        }

        public void setAccountID(String AccountID) {
            this.AccountID = AccountID;
        }

        public String getAccountNo() {
            return AccountNo;
        }

        public void setAccountNo(String AccountNo) {
            this.AccountNo = AccountNo;
        }

        public String getAccountType() {
            return AccountType;
        }

        public void setAccountType(String AccountType) {
            this.AccountType = AccountType;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.returnStatus);
            dest.writeString(this.isSuccess);
            dest.writeString(this.CustId);
            dest.writeString(this.AccountID);
            dest.writeString(this.AccountNo);
            dest.writeString(this.AccountType);
        }

        public CrmAccountsGetEntity() {
        }

        protected CrmAccountsGetEntity(Parcel in) {
            this.returnStatus = in.readString();
            this.isSuccess = in.readString();
            this.CustId = in.readString();
            this.AccountID = in.readString();
            this.AccountNo = in.readString();
            this.AccountType = in.readString();
        }

        public static final Creator<CrmAccountsGetEntity> CREATOR = new Creator<CrmAccountsGetEntity>() {
            @Override
            public CrmAccountsGetEntity createFromParcel(Parcel source) {
                return new CrmAccountsGetEntity(source);
            }

            @Override
            public CrmAccountsGetEntity[] newArray(int size) {
                return new CrmAccountsGetEntity[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.crm_accounts_get, flags);
    }

    public ChargeCardItem() {
    }

    protected ChargeCardItem(Parcel in) {
        this.crm_accounts_get = in.readParcelable(CrmAccountsGetEntity.class.getClassLoader());
    }

    public static final Parcelable.Creator<ChargeCardItem> CREATOR = new Parcelable.Creator<ChargeCardItem>() {
        @Override
        public ChargeCardItem createFromParcel(Parcel source) {
            return new ChargeCardItem(source);
        }

        @Override
        public ChargeCardItem[] newArray(int size) {
            return new ChargeCardItem[size];
        }
    };
}
