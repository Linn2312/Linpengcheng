package com.lpc.constants;

/**
 * @author Lin
 * @Date 2019/12/7
 *
 * 数据库中的表名 及 列名
 */
public interface DBTable {
    //会员表
    String TABLE_MB_USER = "mb_user";
    //收货地址表
    String ADDRESS = "address";
    //购物车表
    String TABLE_CART = "cart";

    //商品表中的价格字段
    String ITEM_PRICE = "price";

    //订单详情表
    String ORDERDETAIL = "order_detail";

    //支付信息表
    String PAYMENTINFO = "payment_info";
}
