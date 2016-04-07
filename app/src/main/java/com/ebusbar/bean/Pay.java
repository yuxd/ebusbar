package com.ebusbar.bean;

/**
 * 支付
 * Created by Jelly on 2016/3/31.
 */
public class Pay {

    /**
     * interfaceName : evc_order_pay
     * returnStatus : 100
     * isSuccess : Y
     * data : {"OrderNo":"O-20160329-00345"}
     */

    private String interfaceName;
    private String returnStatus;
    private String isSuccess;
    /**
     * OrderNo : O-20160329-00345
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
