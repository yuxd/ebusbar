package com.ebusbar.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jelly on 2016/3/23.
 */
public class AddChargeCard implements Parcelable {

    /**
     * returnStatus : 100
     * isSuccess : Y
     * CustId : EV_20160319_00000057
     * AccountID : 86201603240000000259
     * AccountNo : 464646
     * AccountType : ChargeType
     */

    private CrmAccountsInsertEntity crm_accounts_insert;

    public CrmAccountsInsertEntity getCrm_accounts_insert() {
        return crm_accounts_insert;
    }

    public void setCrm_accounts_insert(CrmAccountsInsertEntity crm_accounts_insert) {
        this.crm_accounts_insert = crm_accounts_insert;
    }

    public static class CrmAccountsInsertEntity implements Parcelable {
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

        public CrmAccountsInsertEntity() {
        }

        protected CrmAccountsInsertEntity(Parcel in) {
            this.returnStatus = in.readString();
            this.isSuccess = in.readString();
            this.CustId = in.readString();
            this.AccountID = in.readString();
            this.AccountNo = in.readString();
            this.AccountType = in.readString();
        }

        public static final Creator<CrmAccountsInsertEntity> CREATOR = new Creator<CrmAccountsInsertEntity>() {
            @Override
            public CrmAccountsInsertEntity createFromParcel(Parcel source) {
                return new CrmAccountsInsertEntity(source);
            }

            @Override
            public CrmAccountsInsertEntity[] newArray(int size) {
                return new CrmAccountsInsertEntity[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.crm_accounts_insert, flags);
    }

    public AddChargeCard() {
    }

    protected AddChargeCard(Parcel in) {
        this.crm_accounts_insert = in.readParcelable(CrmAccountsInsertEntity.class.getClassLoader());
    }

    public static final Parcelable.Creator<AddChargeCard> CREATOR = new Parcelable.Creator<AddChargeCard>() {
        @Override
        public AddChargeCard createFromParcel(Parcel source) {
            return new AddChargeCard(source);
        }

        @Override
        public AddChargeCard[] newArray(int size) {
            return new AddChargeCard[size];
        }
    };
}
