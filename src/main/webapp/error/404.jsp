<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/common/header.jsp" flush="true"><jsp:param name="pageTitle" value="404"/></jsp:include>
<link rel="stylesheet" type="text/css" href="../global/css/error.css"/>

<!-- BEGIN CONTAINER -->
<div class="page-container">
	<!-- BEGIN SIDEBAR -->
	<div class="page-sidebar-wrapper">
		<div class="page-sidebar navbar-collapse collapse">
			<!-- BEGIN SIDEBAR MENU -->
			<ul class="page-sidebar-menu page-sidebar-menu-light" data-keep-expanded="true" data-auto-scroll="true" data-slide-speed="200"  id="tree"></ul>
			<!-- END SIDEBAR MENU -->
		</div>
	</div>
	<!-- END SIDEBAR -->
	<!-- BEGIN CONTENT -->
	<div class="page-content-wrapper">
		<div class="page-content">
			<!-- BEGIN PAGE HEADER-->
			<h4 class="page-title">404 <small></small></h4>
			<ol class="breadcrumb"><li><i class="fa fa-home"></i> <a href="../app/index">首页</a></li><li class="active">404</li></ol>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12 page-404">
					<div class="number">
						 404
					</div>
					<div class="details">
						<h3>Oops! 好像找不到页面了</h3>
						<p>
							 您访问的页面我们找不到了，<br/>
							 请稍后重新试试呢！<br/><br/>
						</p>
					</div>
				</div>
				<!-- END PAGE CONTENT-->
			</div>
    	</div>
	</div>
</div>

<%@include file="/common/footer.html" %>
</body>
</html>
