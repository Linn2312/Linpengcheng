<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <!-- 屏幕宽度 && 放大比例 && minimal-ui Safari 网页加载时隐藏地址栏与导航栏-->
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=0,minimal-ui">
    <!-- safari私有meta标签，允许全屏模式浏览 -->
    <meta content="yes" name="apple-mobile-web-app-capable" />
    <!-- ios系统的私有标签，它指定在web app状态下，ios设备中顶端的状态条的颜色 -->
    <meta content="black" name="apple-mobile-web-app-status-bar-style" />
    <!-- 设备浏览网页时对数字不启用电话功能 -->
    <meta content="telephone=no,email=no" name="format-detection" />
    <title>微信商城</title>
    <link rel="stylesheet" href="css/sm.css">
    <script src="js/zepto.js"></script>
    <script src="js/sm.js"></script>
</head>
<body>
<!-- search start -->
<div class="page-group">
    <div class="page page-current">
        <div class="bar bar-header-secondary">
            <div class="searchbar">
                <a class="searchbar-cancel">取消</a>
                <div class="search-input">
                    <label class="icon icon-search" for="search"></label>
                    <input type="search" id='search' placeholder='输入关键字...'/>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- search end -->
</body>
</html>