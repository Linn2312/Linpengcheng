<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="author" content="wechat">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,minimum-scale=1.0,user-scalable=no" />
<meta name="format-detection" content="telephone=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="apple-touch-icon-precomposed" href="http://img01.mifile.cn/m/app/touch-icon.png">
<link rel="shortcut icon" href="http://m.mi.com/favicon.ico" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="css/order.css">
<title>微信商城</title>
<script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
    $(document).ready(function (){
    	  $("#bt_sp").click(function (){
    		  var itemId=$("#itemId").val();
    		   $.ajax(
    		   			{url:"addCart?itemId=" + itemId,
							method:'post',
						success:function (result){alert(result.msg)}
						}
					);
    	  	})
    });
</script>
</head>
<body style="display: block; background: rgb(255, 255, 255);">
	<div id="Cheader">
   <input type="hidden" id="itemId" value="${item['id']}">
		<div id="header" class="header_03">
			<div class="back">
				<a href="index" class="arrow">首页</a>
			</div>
			<div class="tit" style="">
				<h3>商品详情</h3>
			</div>
			<div class="nav">
				<ul>
					<li class="cart">
						<a href="selectCart">购物车</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<div id="wrapper" class="xm_app">
		<div id="viewport" class="viewport">
			<div class="product_view">
				<div class="box box_01">
					<div class="product_swipe">
						<!-- 单品介绍图片 -->
						<div class="swipe" id="slider" style="visibility: visible">
							<div class="swipe-wrap" style="width: 2160px;">
								<div data-index="0"
									style="width: 720px; left: -170px; transition-duration: 0ms; transform: translate(0px, 0px) translateZ(0px);">
									<img src="${item['image']}" alt="">
								</div>
							</div>
						</div>
					</div>
					<div class="product_info">
						<div class="info info_right">
							<h3 class="name">${item['title']}</h3>
							<div class="right">
								<div id="favorite_add" class="favorite_add">
									<span>收藏</span>
								</div>
								<div id="pro_share" class="share">
									<span>分享</span>
								</div>
							</div>
						</div>
						<div class="price">&yen;${item['price']}</div>
						<div class="star comment_star">
							<div class="star_box">
								<div class="star_bar">
									<div style="width: 100%" class="star_num"></div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<input type="hidden" name="goods_id" id="goods_id">
				<div class="product_addCart">
					<a id="bt_sp" class="button active_button">加入购物车</a>
				</div>

				<div class="box box_02 tab_html">
					<div class="tab_nav">
						<ul>
							<li><a href="javascript:void(0)" class="tab_head on">商品介绍</a></li>
						</ul>
					</div>
					<div class="tab_product tab_item" id="productViewDiv">
						<div class="product_main">${itemDesc['itemDesc']}</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>