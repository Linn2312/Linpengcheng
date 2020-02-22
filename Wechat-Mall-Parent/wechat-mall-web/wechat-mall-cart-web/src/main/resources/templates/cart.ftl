<!doctype html>
<html>
    <head>
    <meta charset="UTF-8">
    <!-- 屏幕宽度 && 放大比例 && minimal-ui Safari 网页加载时隐藏地址栏与导航栏-->
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=0,minimal-ui">
    <!-- safari私有meta标签，允许全屏模式浏览 -->
    <meta content="yes" name="apple-mobile-web-app-capable" />
    <!-- ios系统的私有标签，它指定在web app状态下，ios设备中顶端的状态条的颜色 -->
    <meta content="black" name="apple-mobile-web-app-status-bar-style" />
    <!-- 设备浏览网页时对数字不启用电话功能 -->
    <meta content="telephone=no,email=no" name="format-detection" />
<title>微信商城</title>
<link rel="stylesheet" type="text/css" href="css/shoppingcart.css"/>
<link rel="stylesheet" type="text/css" href="css/usercenter.css"/>
<script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
<script src="js/shoppingcart.js" type="text/javascript" charset="utf-8"></script>
        <script type="text/javascript">
            function delCart(itemId){
                if(confirm("您确定要删除当前商品？")){
                    window.location.href = 'delCart?itemId=' + itemId;
                }
            }
            $(function () {
                $("#toSettlement").click(function () {
                    var itemId = $(".product-xz .itemId");  //定义选中之后隐藏域的商品id
                    var itemIds = [];

                    var shoppingnum = $(".product-num"); //定义商品数量
                    var shoppingnums = [];

                    for (var i = 0; i < itemId.length; i++) {
                        itemIds[i] = itemId[i].value;
                        shoppingnums[i] = shoppingnum[i].value;
                    }
                    var totalPrice = document.getElementById("totalPrice").innerText;
                    // //转为json字符串
                    var J_itemIds = JSON.stringify(itemIds);
                    var J_shoppingnums = JSON.stringify(shoppingnums);
                    //货币格式转为数字格式
                    var J_totalPrice = moneyToNumValue(totalPrice);

                    //购物车中选中的商品id集合 总金额 商品数量集合 传给后台
                    //自定义表单并设置参数
                    var body = $("body");
                    var cartForm = $("<form></form>");
                    cartForm.attr("action","http://28ii870524.zicp.vip/order-web/generateOrder");
                    cartForm.attr("method","post");
                    var input_J_itemIds = $("<input name='J_itemIds' />");
                    var input_J_totalPrice = $("<input name='J_totalPrice' />");
                    var input_J_shoppingnums = $("<input name='J_shoppingnums' />");
                    input_J_itemIds.attr("value",J_itemIds);
                    input_J_totalPrice.attr("value",J_totalPrice);
                    input_J_shoppingnums.attr("value",J_shoppingnums);
                    cartForm.append(input_J_itemIds);
                    cartForm.append(input_J_totalPrice);
                    cartForm.append(input_J_shoppingnums);
                    cartForm.hide();
                    body.append(cartForm);
                    cartForm.submit();
                });
            });
        </script>
</head>

<body>
<div class="body">
    <div id="user-b">
        <nav class="j-nav navbar">
            <div class="logo fl">
                <a href="javascript:history.go(-1)"></a>
            </div>
            <span class="user-title">我的购物车</span>
        </nav>
    </div>
    <div class="product">
        <#if dataMap?exists>
        <#list dataMap?keys as key>
        <div class="product-box">
            <div class="product-ckb">
                <em class="product-em product-xz">
                    <#--隐藏域 选择性下单需要传商品的id-->
                    <input type="hidden" class="itemId" value="${dataMap[key].id}">
                </em>
            </div>
            <div class="product-sx">
                <a href="http://28ii870524.zicp.vip/index-web/itemDesc?id=${dataMap[key].id}">
                    <img src="${dataMap[key].image}" class="product-img" />
                    <span class="product-name">${dataMap[key].title}</span>
                </a>
                <div class="product-price">&yen;<span class="price">${dataMap[key].price}</span></div>
                <div class="product-amount">
                    <div class="product_gw">
                        <em class="product-jian">-</em>
                        <input type="text" value="1" class="product-num"/>
                        <em class="product-add">+</em>
                    </div>
                </div>
                <div class="product-del"><a onclick="delCart(${dataMap[key].id})"><img src="images/deleteico.png"/></a></div>
            </div>
        </div>
        </#list>
        </#if>
    </div>
    <div class="product-js">
        <div class="product-al">
            <div class="product-all">
                <em class=""></em>
            </div>
            <div class="all-xz"><span class="product-all-qx">全选</span><div class="all-sl" style="display: none;">(<span class="product-all-sl">0</span>)</div></div>
        </div>
        <a id="toSettlement" class="product-sett product-sett-a">下单</a>
        <div class="all-product"><span class="all-product-a">¥&thinsp;<span id="totalPrice" class="all-price">0</span></span></div>
    </div>
</div>
<!--购物车空-->
<div class="kon-cat">
    <div class="catkon">
        <div class="kon-box">
            <div class="kon-hz">
                <img src="images/cart-air.png" />
                <span class="kon-wz">购物车什么都没有</span>
                <a href="http://28ii870524.zicp.vip/index-web/index" class="kon-lj">去逛逛</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>

