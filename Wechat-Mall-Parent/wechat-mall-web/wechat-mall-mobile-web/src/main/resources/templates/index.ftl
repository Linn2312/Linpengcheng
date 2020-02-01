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
	<!-- 移动端滑动&轮播框架 -->
	<script src="js/ready.js"></script>
	<script src="js/swipe.js"></script>
	<script type="text/javascript">
		/* loading ready */
		document.ready(function() {
			document.getElementById("loading").style.display = "visible";
			onload=function () {
				setTimeout(go,500);
			};
			function go() {
				document.getElementById("loading").style.display = "none";
			}
		});
	</script>
</head>
<body>
<!-- loading -->
<div id="loading" style="height: 100%">
	<div class="spinner">
		<div class="spinner-container container1">
			<div class="circle1"></div>
			<div class="circle2"></div>
			<div class="circle3"></div>
			<div class="circle4"></div>
		</div>
		<div class="spinner-container container2">
			<div class="circle1"></div>
			<div class="circle2"></div>
			<div class="circle3"></div>
			<div class="circle4"></div>
		</div>
		<div class="spinner-container container3">
			<div class="circle1"></div>
			<div class="circle2"></div>
			<div class="circle3"></div>
			<div class="circle4"></div>
		</div>
	</div>
</div>
<!-- all content  -->
<div class="wrapper" id="content">
	<!-- header start -->
	<div class="header">
		<!-- 绝对定位的logo -->
		<div class="logo">
			<a href="index" title="微信商城" class="home"> <span>微信商城</span></a>
		</div>
		<!-- 占据高度DIV -->
		<div class="tit"></div>
		<!-- 绝对定位的右侧个人中心 && 购物车-->
		<div class="right">
			<ul>
				<li class="user"><a href="toUserCenter" title="个人中心"> <span
								class="icon icon-gerenzhongxin"></span>
					</a></li>
				<li class="cart"><a href="selectCart" title="购物车"> <span
								class="icon icon-gouwuche"></span>
					</a></li>
			</ul>
		</div>
	</div>
	<!-- header end -->

	<!-- banner start -->
	<div id="slider" class="slider card card-nomb" style="visibility:visible">
		<div class="swipe-wrap">
			<div data-index="0">
				<div>
					<span class="imgurl"><img src="http://i3.mifile.cn/a4/T1EiWvBXZv1RXrhCrK.jpg"></span>
				</div>
			</div>

			<div data-index="1">
				<div>
					<span class="imgurl"><img src="https://img10.360buyimg.com/cms/jfs/t1/91459/14/8790/100104/5e0723abEde16968f/8bdd52bf99d146a5.jpg"></span>
				</div>
			</div>

			<div data-index="2">
				<div>
					<span class="imgurl"><img src="https://img11.360buyimg.com/cms/jfs/t1/103095/6/8625/74599/5e0723abEd5b695ee/42d91778d6b1ac1f.jpg"></span>
				</div>
			</div>

			<div data-index="3">
				<div>
					<span class="imgurl"><img src="https://img10.360buyimg.com/cms/jfs/t1/94763/39/8690/113893/5e0723abEb9525055/c540950d094a351d.jpg"></span>
				</div>
			</div>
		</div>

		<div class="swipe-nav" id="bottomNav">
			<span class="on">&nbsp;</span> <span class="">&nbsp;</span>
			<span class="on">&nbsp;</span> <span class="">&nbsp;</span>
		</div>
	</div>
	<!-- banner end -->

	<!-- menu start -->
	<div class="nav-wrap nav-index">
		<div id="slider_nav" class="swipe" style="visibility: visiable;">
			<div class="swipe-wrap">
				<div data-index="0">
					<ul>
						<li><a href="index?more=1">
								<span class="icon icon-quanbushangpin"></span><span class="t"><span>全部商品</span></span></a></li>
						<li><a href="toSearch">
								<span class="icon icon-sousuo"></span><span class="t"><span>搜索</span></span></a></li>
						<li><a href="https://developers.weixin.qq.com/community/servicemarket">
								<span class="icon icon-shequ"></span><span class="t"><span>微信社区</span></span></a></li>
						<li><a href="javascript:void(0)" class="red">
								<span class="icon icon-kehuduan"></span><span class="t"><span>客户端</span></span></a></li>
					</ul>
				</div>

				<div data-index="1">
					<ul>
						<li><a href="javascript:void(0)">
								<span class="icon icon-heyueji"></span><span class="t"><span>合约机</span></span></a></li>
						<li><a href="javascript:void(0)">
								<span class="icon icon-fcode"></span><span class="t"><span>F码频道</span></span></a></li>
						<li><a href="javascript:void(0)">
								<span class="icon icon-huafeichongzhi"></span><span class="t"><span>话费充值</span></span></a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<!-- list one start -->
	<div class="list">
		<#if dataMap?exists>
			<#list dataMap?keys as key>
				<div class="list-cell">
					<div class="head">
						<span>${key}</span>
					</div>
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

		<!-- more start -->
		<div class="col1 more">
			<a href="moreParts">
				<span>点击查看配件&nbsp;&gt;</span>
			</a>
		</div>
		<!-- more end -->

		<!-- footer start -->
		<div class="footer">
			<div class="tip">
				<a onclick="goTop()" class="goDesktop"><span>返回顶层</span></a>
			</div>
		</div>
		<!-- footer end -->

		<script type="text/javascript">
			var bottom = document.getElementById("bottomNav").getElementsByTagName("span");
			// 轮播&滑动事件
			var slider = Swipe(document.getElementById('slider_nav'), {
				continuous : true, //无限循环的图片切换效果
				disableScroll : true, //阻止由于触摸而滚动屏幕
				stopPropagation : false, //停止滑动事件
			});

			var sliderBanner = Swipe(document.getElementById('slider'), {
				auto : 2500,
				continuous : true,
				disableScroll : false,
				stopPropagation : false,
				callback : function(index) {
					if (index % 2) {
						bottom[0].className = "";
						bottom[1].className = "on";
					} else {
						bottom[0].className = "on";
						bottom[1].className = "";
					}
				}, //回调函数，切换时触发
			});

			//返回顶部
			function goTop() {
				document.documentElement.scrollTop = 0;
			}
		</script>
	</div>
</body>
</html>