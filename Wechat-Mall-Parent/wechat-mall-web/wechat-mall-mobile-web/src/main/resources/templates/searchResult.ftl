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
    <!-- list one start -->
    <div class="list">
        <#if dataMap?exists>
            <#list dataMap?keys as key>
                <div class="list-cell">
                    <#list dataMap["${key}"] as u>
                        <#if u_index % 2 != 1 >
                            <div class="row">
                        </#if>
                        <div class="cell">
							<span class="imgurl">
								<a href="itemDesc?id=${u.id}"><img src="${u.image}" alt=""></a>
							</span>
                            <span class="p">
								<span>${u.title}</span>
							</span>
                            <span class="p">
								<span style="color: red">&yen;${u.price}</span>
							</span>
                        </div>
                        <#if u_index % 2 != 1 >
                            </div>
                        </#if>
                    </#list>
                </div>
            </#list>
        </#if>
        <!-- list one end -->

        <!-- footer start -->
        <div class="footer">
            <div class="tip">
                <a onclick="goTop()" class="goDesktop"><span>返回顶层</span></a>
            </div>
        </div>
        <!-- footer end -->

        <script type="text/javascript">
            //返回顶部
            function goTop() {
                document.documentElement.scrollTop = 0;
            }
        </script>
    </div>
</body>
</html>