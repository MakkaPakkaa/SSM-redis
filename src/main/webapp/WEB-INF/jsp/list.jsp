<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css"/>
<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<title>商品信息</title>
</head>
<body>
	<div class="container">
		<h2>商品信息</h2>
		<p>
		<form action="list" method="post">
			<label for="name"> 名称：<input type="text" value="${name}"
				name="name" id="name1" />
			</label> <label for="outline"> 描述：<input type="text" value="${outline}"
				name="outline" id="outline1" />
			</label>
			<button type="submit" class="btn btn-primary">查询</button>
			<a data-toggle="modal" onclick="add(${wares.id})" data-target="#addModal" class="btn btn-primary">添加</a>
			
		</form>
		</p>
		<table class="table table-bordered table-hover">
			<thead>
				<tr>
					<td>编号</td>
					<td>名称</td>
					<td>价格</td>
					<td>描述</td>
					<td>产地</td>
					<td>生产日期</td>
					<td>保质期</td>
					<td>库存</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${waresList}" var="wares">
				<tr>
					<td>${wares.id}</td>
					<td>${wares.name}</td>
					<td>${wares.price}</td>
					<td>${wares.outline}</td>
					<td>${wares.place}</td>
					<td>${wares.date}</td>
					<td>${wares.expire_date}</td>
					<td>${wares.stock}</td>
					<td>
						<a href="javascript:void(0)" onclick="del(${wares.id})" class="btn btn-danger">删除</a>
<%-- 						<a href="edit/${wares.id }" class="btn btn-warning">修改</a> --%>
						<a data-toggle="modal" onclick="upd(${wares.id})" data-target="#myModal" class="btn btn-warning">修改</a>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<p class="text-center">
			<!-- 
				如果不是第一页，就显示上一页
				pageNum 表示 当前页       pageNum != 1 不是第一页，显示上一页
			 -->
			<c:if test="${pageNum != 1}">
				<a href="list?pageNum=${pageNum-1}&name=${name}&outline=${outline}">上一页</a>
			</c:if>
			
			<!-- 
				totalPage : 存放的是所有页面的页码数   [1,2,3,4]
			 -->
			<c:forEach items="${totalPage}" var="num">
				<c:if test="${pageNum == num}">
					<a href="list?pageNum=${num}&name=${name}&outline=${outline}" style="color: red">${num}</a>&nbsp;&nbsp;
				</c:if>
				<c:if test="${pageNum != num}">
					<a href="list?pageNum=${num}&name=${name}&outline=${outline}" style="color: blue">${num}</a>&nbsp;&nbsp;
				</c:if>
			</c:forEach>
			
			<!-- 
				如果不是最后一页，就显示下一页
			 -->
			<c:if test="${pageNum != lastPage}">
				<a href="list?pageNum=${pageNum+1}&name=${name}&outline=${outline}">下一页</a>
			</c:if>
		</p>
		
	</div>
	
	<!-- 模态框（Modal） -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">
						更新商品信息
					</h4>
				</div>
				<div class="modal-body">
					<form id="frm">
						<input type="hidden" name="id" id="id"/>
						<label for="name">
							名称：
							<input type="text" name="name" id="name"/>
						</label>
						<br />
						<label for="price">
							价格：<input type="text"  name="price" id="price"/>
						</label>
						<br />
						<label for="outline">
							描述：<input type="text" name="outline"  id="outline"/>
						</label>
						<br />
						<label for="place">
							产地：<input type="text" name="place" id="place"/>
						</label>
						<br />
						<label for="date">
							日期：<input type="text" name="date" id="date"/>
						</label>
						<br />
						<label for="expire_date">
							保质期：<input type="text" name="expire_date" id="expire_date" />
						</label>
						<br />
						<label for="stock">
							库存：<input type="text" name="stock" id="stock"/>
						</label>
						<br />
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
					<button type="button" id="btnComm" class="btn btn-primary">
						提交更改
					</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	
	<!-- 模态框（Modal） -->
	<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">
						添加商品信息
					</h4>
				</div>
				<div class="modal-body">
					<form id="addfrm">
						<input type="hidden" name="id" id="id"/>
						<label for="name">
							名称：
							<input type="text" name="name" id="name" />
						</label>
						<br />
						<label for="price">
							价格：<input type="text"  name="price" id="price"/>
						</label>
						<br />
						<label for="outline">
							描述：<input type="text" name="outline"  id="outline" value="食品"/>
						</label>
						<br />
						<label for="place">
							产地：<input type="text" name="place" id="place" value="中国"/>
						</label>
						<br />
						<label for="date">
							日期：<input type="text" name="date" id="date" value="2020-5-20"/>
						</label>
						<br />
						<label for="expire_date">
							保质期：<input type="text" name="expire_date" id="expire_date" value="2020-10-30"/>
						</label>
						<br />
						<label for="stock">
							库存：<input type="text" name="stock" id="stock" value="100"/>
						</label>
						<br />
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
					<button type="button" id="addBtn" class="btn btn-primary">
						添加
					</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	
	
	<!-- <script type="text/javascript">
		function del(id) {
			if (confirm("确定要删除嘛？")) {
				//发送请求
				window.location="del/"+id;
			}
		}
	</script>
	 -->
	 <script type="text/javascript">
		$(function(){
			$("#btnComm").click(function(){
				//对表单进行序列化     参数名=参数值&参数名=参数值
				var data = $("#frm").serialize();
				$.ajax({
					url:"upd" ,
					type:"get",
					data:data ,
					success:function(data){
						alert("更新成功");
						//重新加载
						window.location.reload();
					}
				});
			});
		});
		
	
	
		function upd(id){
// 			alert(id);
			$.ajax({
				url:"waresId/"+id ,
				type:"get",
				success:function(data){
					var wares = data.wares ; 
					$("#id").val(id);
					$("#name").val(wares.name);
					$("#price").val(wares.price);
					$("#outline").val(wares.outline);
					$("#place").val(wares.place);
					$("#date").val(wares.date);
					$("#expire_date").val(wares.expire_date);
					$("#stock").val(wares.stock);
				}
			});
		}
	
		function del(id){
			if(confirm("确定要删除嘛?")){
				$.ajax({
					url:"del/"+id ,
					type:"get",
					success:function(data){
						if(data.msg == "ok"){
							//删除成功
							alert("删除成功");
							//重新加载页面
							window.location.reload();
						}else{
							alert(data.msg);
						}
					}
				});
			}
		}
		
		//id选择器  class选择器
		$(function(){
			$("#addBtn").click(function(){
				//表单内容序列化 
				var data = $("#addfrm").serialize();
				console.log(data);
				$.ajax({
					url:"addwares",
					type:"post",
					data:data,
					success:function(data){
						if(data.msg == "ok"){
							//添加成功，提示成功，跳转页面
							//重新加载页面
							window.location = "list";
						}else{
							alert(data.msg);
						}
					}
				});
			});
		});	
		
	</script>
	 
	 
</body>
</html>