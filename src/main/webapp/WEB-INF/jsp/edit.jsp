<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css"/>
<title>修改商品信息</title>
</head>
<body>
	<div class="container">
		<h2>编辑商品信息</h2>
		<form id="frm" action="../comm" method="post">
			<input type="hidden" name="id"  value="${wares.id}" />
			<label for="name">
				名称：<input type="text" name="name" id="name" value="${wares.name}" />
				<span id="uname" style="color:red ; font-size:11px"></span>
			</label>
			<br />
			<label for="price">
				价格：<input type="text" name="price" id="price" value="${wares.price}" />
				<span id="uprice" style="color:red ; font-size:11px"></span>
			</label>
			<br />
			<label for="outline">
				描述：<input type="text" name="outline" id="outline" value="${wares.outline}" />
				<span id="uoutline" style="color:red ; font-size:11px"></span>
			</label>
			<br />
			<label for="place">
				产地：<input type="text" name="place" id="place" value="${wares.place}" />
				<span id="uplace" style="color:red ; font-size:11px"></span>
			</label>
			<br />
			<label for="date">
				生产日期：<input type="text" name="date" id="date" value="${wares.date}" />
				<span id="udate" style="color:red ; font-size:11px"></span>
			</label>
			<br />
			<label for="expire_date">
				保质期：<input type="text" name="expire_date" id="expire_date" value="${wares.expire_date}" />
				<span id="uexpire_date" style="color:red ; font-size:11px"></span>
			</label>
			<br />
			<label for="stock">
				库存：<input type="text" name="stock" id="stock" value="${wares.stock}" />
				<span id="ustock" style="color:red ; font-size:11px"></span>
			</label>
			<button type="button" id="btn" class="btn btn-primary">提交</button>
<!-- 			<button type="button" id="btn" class="btn btn-primary">添加</button> -->
		</form>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.8.2.min.js"></script>
	<script type="text/javascript">
			//id选择器  class选择器
			$(function(){
				$("#btn").click(function(){
					//先获取表单里面的参数值
					var name = $("#name").val();
					var price = $("#price").val();
					var outline = $("#outline").val() ;
					var place = $("#place").val();
					var date = $("#date").val();
					var expire_date = $("#expire_date").val();
					var stock = $("#stock").val();
					//弹窗验证获取值
// 					alert(name + "---" + price + "---"+outline + "---"+place + "---"+date + "---"+expire_date + "---"+stock);
					
					if(name == "" ){
						//请输入完整
						$("#uname").html("请输入名称");
						return ; 
					}
					if(price =="" || parseInt(price) < 0 ){
						$("#uprice").html("价格有误");
						return  ; 
					}
					if(outline == "" ){
						$("#uoutline").html("描述不能为空");
						return ; 
					}
					if(place == ""){
						$("#uplace").html("产地不能为空");
						return ;
					}
					if(date ==""){
						$("#udate").html("生产日期不能为空");
						return ;
					}
					if(expire_date ==""){
						$("#uexpire_date").html("保质期不能为空");
						return ;
					}
					if(stock ==""){
						$("#ustock").html("库存不能为空");
						return ;
					}
					//修改form表单的action的地址attribute()
// 					$("#frm").attr("action","../comm");
					$("#frm").submit();
				});
			});			
	</script>
</body>
</html>