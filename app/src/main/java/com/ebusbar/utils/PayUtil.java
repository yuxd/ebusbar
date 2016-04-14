package com.ebusbar.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Jelly on 2016/3/21.
 */
public class PayUtil {
    // 商户PID
    public static final String PARTNER = "2088021278222169";
    // 商户收款账号
    public static final String SELLER = "774819744@qq.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMhHEZtlShWXBQeorNMoItE0smdnvReTpncw9EZD4wNcwPs4R0Yv+cvEROaHIa/ICnsG8iTJTw8WC1Qc3nYURWOSMJVwPPB02YW7NVaeISrsxAq39RJqo//Y4wbignZWcGfri5OcaHWEz9Myag+udNTySxYU+jMbyasWtt7S+JRfAgMBAAECgYAFVScZ3SOQ2ZmPr6USZkIvEdkiObfmNc874zjN2ibkQwb8aW40ZziefVZcUQLLnGbzCadO5XFOdIbOsYoW2MlUJkvF5DEXAHfdEZgo8sg6271QeHHcBrn175h7FQ6wZLfE59PengTrKDVfqJuP+rQlYt98svrZQtc+uOu8vKtGyQJBAPQNfpLj0ne4XQ0zkQXsKFVZ1JvFwTDRxTIJaiOxdwthRzy42tstiVqS70y2nA7zImCHrPzjXADuXdd8yA7fsgUCQQDSFPpouHJorxcnl3pX7Jc1PnZm9LaDUn+rCxldlamO0BmNs2iGB4zJxl9HX75nTN7fGGpYjxpXXxtXzk+gvUYTAkBLLoEIhTBH+z6HG5SYa3i9Wk/5sMDj5itGT8ontrrCgbQtS5D4Jh4DDfK0AikvHc1ASWuL+ApWqxezk1gZ5OV1AkA1eZWMFnc44mi4cTx5KQYMTYwCBQR4RQWuWjLouXHhomL+yM4wX7ecnNwf5OUokk6g5ywawsJnvxPMs/1c4xD1AkEA8IUz0fBMzH/QDc+cGi9GKF8uNvrVNVi4VbYDFCLx8k2ni3moZnFYJnENBcxjAvikmvy6fJIISl9a+/Tl3YkSRw==";

    public static final int SDK_PAY_FLAG = 1;
    /**
     * create the order info. 创建订单信息
     *
     */
    public static String getOrderInfo(String subject, String body, String price , String tradeNo) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + tradeNo + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://192.168.0.151:8081/create_direct_pay_by_user-JAVA-UTF-8/notify_url.jsp" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"1m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     *
     */
    public static String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }


    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content
     *            待签名订单信息
     */
    public static String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     *
     */
    public static String getSignType() {
        return "sign_type=\"RSA\"";
    }
}
