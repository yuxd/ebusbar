package com.ebusbar.dao;

/**
 * Created by Jelly on 2016/3/28.
 */
public class TestDao {

    /**
     * result : {"err_code":"0","model":"101009513977^1101479736999","success":true}
     * request_id : z2amben5spsi
     */

    private AlibabaAliqinFcSmsNumSendResponseEntity alibaba_aliqin_fc_sms_num_send_response;

    public AlibabaAliqinFcSmsNumSendResponseEntity getAlibaba_aliqin_fc_sms_num_send_response() {
        return alibaba_aliqin_fc_sms_num_send_response;
    }

    public void setAlibaba_aliqin_fc_sms_num_send_response(AlibabaAliqinFcSmsNumSendResponseEntity alibaba_aliqin_fc_sms_num_send_response) {
        this.alibaba_aliqin_fc_sms_num_send_response = alibaba_aliqin_fc_sms_num_send_response;
    }

    public static class AlibabaAliqinFcSmsNumSendResponseEntity {
        /**
         * err_code : 0
         * model : 101009513977^1101479736999
         * success : true
         */

        private ResultEntity result;
        private String request_id;

        public ResultEntity getResult() {
            return result;
        }

        public void setResult(ResultEntity result) {
            this.result = result;
        }

        public String getRequest_id() {
            return request_id;
        }

        public void setRequest_id(String request_id) {
            this.request_id = request_id;
        }

        public static class ResultEntity {
            private String err_code;
            private String model;
            private boolean success;

            public String getErr_code() {
                return err_code;
            }

            public void setErr_code(String err_code) {
                this.err_code = err_code;
            }

            public String getModel() {
                return model;
            }

            public void setModel(String model) {
                this.model = model;
            }

            public boolean isSuccess() {
                return success;
            }

            public void setSuccess(boolean success) {
                this.success = success;
            }
        }
    }
}
