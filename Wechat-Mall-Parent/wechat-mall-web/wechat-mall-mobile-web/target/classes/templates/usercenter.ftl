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
    <link rel="stylesheet" type="text/css" href="css/usercenter.css"/>
	<script type="text/javascript">
		function gogo() {
			alert("您还未登录");
		}
	</script>
</head>
<body>
<div id="user-b">
	<!--html5 nav-->
	<nav class="j-nav navbar">
		<div class="logo fl">
			<a href="javascript:history.go(-1)"></a>
		</div>
		<span class="user-title">个人中心</span>
	</nav>
	<section class="m-component-user" id="m-user">
		<div class="m-user-avatar text-center">
			<span class="avatarPic">
				<img style="display: inline;" class="lazy img-circle" src="images/tx.jpg"></span>
			<#if username?exists>
				${username}
			<#else>
				<div class="user_btn">
					<a href="toLogin" class="this">登录</a>
					<a href="toRegister">注册</a>
				</div>
			</#if>
		</div>

		<div class="head_list">
			<ul class="m-user-list">
				<#if username?exists>
					<li><a href="selectCart"><span>${cartSize}</span><br>购物车</a></li>
					<li><a href="selectUserAllOrder"><span>${orderSize}</span><br>我的订单</a></li>
				<#else>
					<li><a href="javascript:void(0)" onclick="gogo()"><span>0</span><br>购物车</a></li>
					<li><a href="javascript:void(0)" onclick="gogo()"><span>0</span><br>我的订单</a></li>
				</#if>
			</ul>
		</div>
		<ul class="m-user-content">
			<li>
				<div class="m-user-item">
					<a href="javascript:void(0)" class="user-set">设置</a>
				</div>
				<div class="m-user-item">
					<div class="user-score"><span class="pull-right">400-110110</span>联系客服</div>
				</div>
			</li>
		</ul>
	</section>

	<footer class="footer">
		<nav>
			<ul>
				<li><a href="index" class="nav-controller"><div class="fb-home"></div>首页</a></li>
				<#if username?exists>
					<li><a href="exit" class="nav-controller"><div class="fb-list"></div>退出登录</a></li>
				<#else >
					<li><a href="javascript:void(0)" onclick="gogo()" class="nav-controller"><div class="fb-list"></div>退出登录</a></li>
				</#if>
			</ul>
		</nav>
	</footer>
</div>
</body>
</html>