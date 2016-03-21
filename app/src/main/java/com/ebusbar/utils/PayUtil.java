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
    public static final String PARTNER = "2088311706095565";
    // 商户收款账号
    public static final String SELLER = "liming@tsinghua.org.cn";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALVTmrxJDzh4y65vw3jtdTB657ju1qx1VvYv+FJJ8JSi0MzSgEWDB8t4iHnKnuf7v8+HtC/UfBE+RXErkvQPlFdMKUKCKqZeg5BZwYlt6GdCM01qutmdLiQNCbbkxfnNukSsC+m2k5FdnbfGAuJvpUD8PECMLTLDnSEUSlpDBF1hAgMBAAECgYATybvpME/o6cXQk4HRgJh5YfBHzki/KU8ELtf6ovcOQXZn49D7xQDkw5KhN+t/hYm5LCYN0jJ370PExeyTld+qOCs8rR+u/9ETXBLjS939QX9pVTPHNwCdyHjUv2YFWMcf9nYIx7iyfo9PHiCA/fu+tGuuOVW+ZzYuW4nzuUhTwQJBAN0DoOKVeK3NWn1E9F9LqKrR6PxZjPQKxMsbjYEcguW4nNZu4CqGBD0uyT84kVqrwJn5SfNXJjrwicOmPeuUZo8CQQDSB61Z18BSNmIPzoB79ArHm2u2EmTmtdBUBStitC0R8Lv3MEAI2aRZERdHzltx3rPr6Ya9WOv/ohU/vDDX3XUPAkEAnT8LpKp8JgxJKX957qNB6edXQNPl+WwAABImZ4oFh7QFwns4eojHU1CKVrTEliJE8kfvWXbT+HNTUSRiQc5f+QJAbSGqFGXDu7QgTgBJyJ5sv3zlgcya+zn7jrzE51uhk5HXyUAb9b1I5TK5OwlhgJBsQqer6FT9A24vxlc9tdyPRwJAOUHXzr6fwhZi21kvjbfzpxHApfOBtgMYskZPj+3mvIq6PXWkvjhuStYtyRadlardOsQRN2cyyfA3haFFi8jOIA==";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";

    public static final int SDK_PAY_FLAG = 1;
    /**
     * create the order info. 创建订单信息
     *
     */
    public static String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

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
    private static String getOutTradeNo() {
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
