<!DOCTYPE html>
<html class="no-js">
<head>
    <meta content="yes" name="apple-mobile-web-app-capable" />
    <!-- ios系统的私有标签，它指定在web app状态下，ios设备中顶端的状态条的颜色 -->
    <meta content="black" name="apple-mobile-web-app-status-bar-style" />
    <!-- 设备浏览网页时对数字不启用电话功能 -->
    <meta content="telephone=no,email=no" name="format-detection" />
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,minimum-scale=1.0,user-scalable=no" />
    <title>微信商城</title>
    <link rel="stylesheet" href="css/usercenter.css"/>
    <script type="text/javascript">
        function goBack() {
            var footer = document.getElementById("footer");
            if (footer!=null) {
                var x = window.confirm("订单有效期为15分钟，是否立即支付？");
                if (x){
                    void(0);
                }
                else {
                    window.history.go(-1);
                }
            }
        }
    </script>
</head>
<body>
<div id="user-b" class="od-box">
    <!--html5 nav-->
    <nav class="j-nav navbar">
        <div class="logo fl">
            <a onclick="goBack()" id="goBack"></a>
        </div>
        <span class="user-title">订单信息</span>
    </nav>

    <!-- 订单信息 -->
    <div class="od-infor">
        <table cellpadding="1" cellspacing="0">
            <tr>
                <td>总金额:</td>
                <td class="od-red">&yen;${amount}</td>
            </tr>
            <tr>
                <td>订单编号:</td>
                <td>${orderId}</td>
            </tr>
            <tr>
                <td>下单时间:</td>
                <td>${createdTime}</td>
            </tr>
        </table>
    </div>
    <!-- 购买详情 -->
    <h4>购买详情</h4>
    <#if listOrder?exists>
    <#list listOrder as list>
    <div class="mc-sum-box">
        <div class="myorder-sum fl"><img src="${list.getImage()}"></div>
        <div class="myorder-text">
            <h1>${list.getTitle()}</h1><br />
            <div class="myorder-cost">
                <span>数量:${list.getShoppingnum()}</span>
                <span class="mc-t">&yen;${list.getPrice()}/部</span>
            </div>
        </div>
    </div>
    </#list>
    </#if>
</div>

<#if listOrder?exists>
    <#list listOrder as list>
        <#if list.getOrderStatus()==0>
            <footer class="footer" id="footer">
                <div class="pay-m">
                    <a href="orderToPay?orderId=${orderId}&amount=${amount}"><button class="btn">付款</button></a>
                    <a href="cancelOrder?orderId=${orderId}"><button class="btn">取消订单</button></a>
                </div>
            </footer>
        </#if>
        <#break>
    </#list>
</#if>
</body>
</html>