package com.ebusbar.bean;

/**
 * 预约订单
 * Created by Jelly on 2016/3/9.
 */
public class Appoint {

    /**
     * interfaceName : evc_planorder_change
     * returnStatus : 100
     * isSuccess : Y
     * data : {"OrderNo":"O-20160401-00395"}
     */

    private String interfaceName;
    private String returnStatus;
    private String isSuccess;
    /**
     * OrderNo : O-20160401-00395
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

        public String getOrderNo() {
            return OrderNo;
        }

        public void setOrderNo(String OrderNo) {
            this.OrderNo = OrderNo;
        }
    }
}
