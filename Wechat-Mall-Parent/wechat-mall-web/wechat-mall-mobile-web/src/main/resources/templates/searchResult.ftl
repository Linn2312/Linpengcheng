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
    <link rel="stylesheet" href="css/common.css">
    <!-- 自适应样式单 -->
    <link rel="stylesheet" href="css/adaptive.css">
    <!-- 加载前动画样式 -->
    <link rel="stylesheet" href="css/loading.css">
    <link rel="stylesheet" href="css/index.css">
</head>
<body>
<!-- all content  -->
<div class="wrapper" id="content">
    <!-- list start -->
    <div class="list">
        <div class="list-cell">
            <#if (searchList?exists)&&(searchList?size>0)>
                <#list searchList as list>
                    <div class="row">
                        <div class="cell">
							<span class="imgurl">
								<a href="itemDesc?id=${list.getId()}"><img src="${list.getImage()}" alt=""></a>
							</span>
                            <span class="sellpoint">
								${list.getSellpoint()}
							</span>
                            <span class="p">
								<span style="color: red;font-size: 18px">&yen;${list.getPrice()}</span>
							</span>
                        </div>
                    </div>
                </#list>
            <#else >
                <span style="font-size: 34px">
                        没有您要查找的商品
                    </span>
            </#if>
        </div>
        <!-- list end -->
    </div>
</div>
</body>
</html>