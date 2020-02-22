<!DOCTYPE html>
<html class="no-js">
<head>
    <title>微信商城</title>
    <meta content="yes" name="apple-mobile-web-app-capable" />
    <!-- ios系统的私有标签，它指定在web app状态下，ios设备中顶端的状态条的颜色 -->
    <meta content="black" name="apple-mobile-web-app-status-bar-style" />
    <!-- 设备浏览网页时对数字不启用电话功能 -->
    <meta content="telephone=no,email=no" name="format-detection" />
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,minimum-scale=1.0,user-scalable=no" />
    <link rel="stylesheet" href="css/usercenter.css"/>
</head>
<body>
<div id="user-b">
    <!--html5 nav-->
    <nav class="j-nav navbar">
        <div class="logo fl">
            <a href="javascript:history.go(-1)"></a>
        </div>
        <span class="user-title">我的订单</span>
    </nav>
    <div class="myorder-tab">
        <div class="myorder-nav">
            <ul id="tab_btn" class="coupon-list">
                <li class="pick">待付款</li>
                <li><span class="bar"></span>待收货</li>
                <li><span class="bar"></span>已失效</li>
            </ul>
            <div class="cl"></div>
        </div>
        <div class="myorder-content">
            <ul>

                <!-- 待付款订单 -->
                <li class="mc-all cc-one tab_content show">
                    <ul>

                        <#if (itemNoPay?exists)&&(itemNoPay?size>0)>
                            <#list itemNoPay as list>
                                <li>
                                    <a href="orderToPay?orderId=${list.getOrderId()}&amount=${list.getShoppingnum()*list.getPrice()}">
                                        <div class="mc-sum-box">
                                            <div class="myorder-sum fl"><img src="${list.getImage()}"></div>
                                            <div class="myorder-text">
                                                <h1>${list.getTitle()}</h1><br />
                                                <div class="myorder-cost">
                                                    <span></span>
                                                    <span class="mc-t">&yen;${list.getPrice()}/部</span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="mc-sum-Am">
                                            共${list.getShoppingnum()}件商品，免运费<span>实付：<span class="mc-t">&yen;${list.getShoppingnum()*list.getPrice()}</span></span>
                                        </div>
                                        <h3>未付款</h3>
                                    </a>
                                </li>
                            </#list>
                        <#else >
                            <h2 style="color: red">您没有未付款订单!</h2>
                        </#if>

                    </ul>
                </li>

                <!-- 已付款订单 -->
                <li class="mc-all mc-two tab_content">
                    <ul>

                        <#if (itemPay?exists)&&(itemPay?size>0)>
                            <#list itemPay as list>
                                <li>
                                    <a href="toOrderDetail?orderId=${list.getOrderId()}&amount=${list.getShoppingnum()*list.getPrice()}&createdTime=${list.getCreatedTime()}">
                                        <div class="mc-sum-box">
                                            <div class="myorder-sum fl"><img src="${list.getImage()}"></div>
                                            <div class="myorder-text">
                                                <h1>${list.getTitle()}</h1><br />
                                                <div class="myorder-cost">
                                                    <span></span>
                                                    <span class="mc-t">&yen;${list.getPrice()}/部</span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="mc-sum-Am">
                                            共${list.getShoppingnum()}件商品，免运费<span>实付：<span class="mc-t">&yen;${list.getShoppingnum()*list.getPrice()}</span></span>
                                        </div>
                                        <h3>已付款，待收货</h3>
                                    </a>
                                </li>
                            </#list>
                        <#else >
                            <h2 style="color: red">您没有已付款订单!</h2>
                        </#if>

                    </ul>
                </li>

                <!-- 已失效订单 -->
                <li class="mc-all mc-three tab_content">
                    <ul>

                        <#if (itemOverTimePay?exists)&&(itemOverTimePay?size>0)>
                            <#list itemOverTimePay as list>
                                <li>
                                    <div class="mc-sum-box">
                                        <div class="myorder-sum fl"><img src="${list.getImage()}"></div>
                                        <div class="myorder-text">
                                            <h1>${list.getTitle()}</h1><br />
                                            <div class="myorder-cost">
                                                <span></span>
                                                <span class="mc-t">&yen;${list.getPrice()}/部</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="mc-sum-Am">
                                        共${list.getShoppingnum()}件商品，免运费<span>实付：<span class="mc-t">&yen;${list.getShoppingnum()*list.getPrice()}</span></span>
                                    </div>
                                    <h3>订单超时！已失效</h3>
                                </li>
                            </#list>
                        <#else >
                            <h2 style="color: red">您没有失效订单!</h2>
                        </#if>

                    </ul>
                </li>

            </ul>
        </div>
    </div>
</div>
<!--footer begin-->
<footer class="footer">
    <nav>
        <ul>
            <li><a href="http://28ii870524.zicp.vip/index-web/index" class="nav-controller"><div class="fb-home"></div>首页</a></li>
            <li><a href="http://28ii870524.zicp.vip/usercenter-web/toUserCenter" class="nav-controller"><div class="fb-user"></div>个人中心</a></li>
        </ul>
    </nav>
</footer>
<!--footer end-->
<script type="text/javascript">
    //TAB切换
    var tab_Btns_box=document.getElementById('tab_btn');
    var tab_Btns=tab_Btns_box.getElementsByTagName('li');
    var tab_contents=document.getElementsByClassName('tab_content');
    for(var i=0;i<tab_Btns.length;i++){
        tab_Btns[i].index=i;

        tab_Btns[i].onclick=function(){
            for(var i=0;i<tab_Btns.length;i++){
                if(i!==this.index){
                    tab_Btns[i].classList.remove('pick')
                }
            }
            tab_Btns[this.index].className="pick";

            for(var j=0;j<tab_contents.length;j++){
                if(j!==this.index){
                    tab_contents[j].classList.remove('show');
                }
                tab_contents[this.index].classList.add('show');
            }
        }
    }
</script>
</body>
</html>