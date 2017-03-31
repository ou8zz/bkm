<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/common/header.jsp" flush="true"><jsp:param name="pageTitle" value="日志管理"/></jsp:include>
<style>
input::-webkit-calendar-picker-indicator{
    display: none;
}
input[type="date"]::-webkit-input-placeholder{ 
    visibility: hidden !important;
}
</style>
<!-- BEGIN CONTAINER -->
<div class="page-container">
	<!-- BEGIN SIDEBAR -->
	<div class="page-sidebar-wrapper">
		<div class="page-sidebar navbar-collapse collapse">
			<!-- BEGIN SIDEBAR MENU -->
			<ul class="page-sidebar-menu page-sidebar-menu-light" data-keep-expanded="true" data-auto-scroll="true" data-slide-speed="200" id="tree"></ul>
			<!-- END SIDEBAR MENU -->
		</div>
	</div>
	<!-- END SIDEBAR -->
	<!-- BEGIN CONTENT -->
	<div class="page-content-wrapper">
		<div class="page-content">
			<!-- BEGIN PAGE HEADER-->
			<h4 class="page-title">日志管理 <small></small></h4>
			<ol class="breadcrumb"><li><i class="fa fa-home"></i> <a href="index">首页</a></li><li class="active">日志管理</li></ol>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<form id="searchForm" class="form-inline hidden-xs" action="">
			        <div class="col-md-12 text-right">
			          <select title="每页条数" class="form-control select2" onchange="javascript:Logs.getDataTable(this, {'pageSize':this.value})">
			          <option value="10">10</option><option value="20">20</option><option value="40">40</option></select>
			          <input type="date" name="startTimeShort" class="form-control input-small" placeholder="开始时间" >
			          <input type="date" name="endTimeShort" class="form-control input-small" placeholder="结束时间" >
			          <select name="cmodes" class="form-control select2" multiple="multiple" style="width:25%;" data-placeholder="操作类型" data-allow-clear="true">
					 	<option value="">全部</option>
		                <c:forEach items="${oms}" var="b" varStatus="i">
		   	 				<option value="${b.key }">${b.value }</option>
		  				</c:forEach>
		              </select>
		              <input type="text" name="cfunc" class="form-control" placeholder="功能" >
			          <div class="input-group">
			          	<input type="text" name="title" class="form-control" placeholder="描述" >
				          <span class="input-group-btn">
				            <button class="btn btn-success" type="button" onclick="javascript:Logs.search();"><i class="fa fa-search"></i></button>
				          </span>
			          </div>
			      	</div>
				</form>
			</div>
          <hr/>
	      <div class="error-log">
	        <div class="u-sm-12 u-sm-centered">
<!-- 	        	<pre class="pre-scrollable" id="dataBody"></pre> -->
	        	<pre class="" id="dataBody"></pre>
	        </div>
	        <div id="ajaxpage"></div>
	      </div>
	    </div>
	</div>
</div>

<!-- 查看日志明细弹出框 -->
<div id="showLogs" class="collapse">
	<div class="panel-body">
		<!-- Table -->
		  <table class="table table-paper table-striped table-hover">
            <thead>
            <tr>
               <th>列</th>
               <th>新记录</th>
               <th>上次记录</th>
            </tr>
            </thead>
            <tbody id="dataLogs"></tbody>
           </table> 
	</div>
</div>

<%@include file="/common/footer.html" %>
<script type="text/javascript" src="../global/plugins/diff.js"></script>
<script type="text/javascript" src="../global/scripts/admin/logs.js"></script>
</body>
</html>
