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
    <script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript">
        function goBack() {
            var x = window.confirm("订单有效期为15分钟，是否需要立即支付？");
            if (x){
                void(0);
            }
            else {
                window.history.go(-1);
            }
        }
        function backToIndex() {
            window.location.href = 'index';
        }
    </script>
</head>
<body>
<div id="user-b">
    <!--html5 nav-->
    <nav class="j-nav navbar">
        <div class="logo fl">
            <a onclick="javascript:goBack()" id="goBack"></a>
        </div>
        <span class="user-title">付款</span>
    </nav>
    <div class="pay-box">
        <!-- 收货地址信息 -->
        <a href="javascript:void(0)">
            <div class="pay-m pay-site">
                <div class="pay-site-left fl"><img src="images/address.png" height="52px"></div>
                <div class="pay-site-right" id="addAddress">
                    <p>${username}&nbsp;&nbsp;${phone}</p>
                    <p>${address}</p>
                </div>
            </div>
        </a>
        <!-- 商品详情 -->
        <div class="pay-m mc-sum-box">
            <#if itemList?exists>
            <#list itemList as list>
            <div class="myorder-sum fl">
                <img src="${list.getImage()}"></div>
            <div class="myorder-text">
                <h1>${list.getTitle()}</h1>
                <br />
                <div class="myorder-cost">
                    <span>数量:${list.getShoppingnum()}</span>
                    <span class="mc-t">&yen;${list.getPrice()}/部</span>
                </div>
            </div>
            </#list>
            </#if>
        </div>
        <!-- 运费以及总价 -->
        <div class="pay-freight text-right">
            <p>快递:&nbsp;<span>&yen;0.00</span>&nbsp;总价:&nbsp;&nbsp;<b>&yen;${totalPrice}</b></p>
        </div>
        <!-- 支付方式 -->
        <div class="pay-m">
            <form class="am-form" action="addPaymentInfo">
                <input type="hidden" name="orderId" value="${orderId}">
                <input type="hidden" name="totalPrice" value="${totalPrice}">
                <div class="am-g">
                    <div class="pay-mu">
                        <div class="pay-input-group pay-input-group-success">
                <span class="pay-input-group-label">
                  <input type="radio" checked="checked">
                </span>
                            <div class="pay-manner">银联支付</div>
                        </div>
                    </div>
                </div>

                <button class="btn" type="submit">立即支付</button>
                <button class="btn" type="button" onclick="backToIndex()">返回首页</button>
            </form>
        </div>

    </div>
</div>
</body>
</html>