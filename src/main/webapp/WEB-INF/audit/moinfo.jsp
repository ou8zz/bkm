<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/common/header.jsp" flush="true"><jsp:param name="pageTitle" value="${ctypeDes}管理"/></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
			<h4 class="page-title">${ctypeDes}管理 <small></small></h4>
			<ol class="breadcrumb"><li><i class="fa fa-home"></i> <a href="index">首页</a></li><li class="active">${ctypeDes}管理</li></ol>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
			  <form id="searchForm" class="form-inline hidden-xs" action="getUsers">
				<div class="col-md-2">
			    	<button type="button" class="btn default btn-sm" onclick="javascript:Moinfo.addDlg(this, 'addMoinfo');"><i class="fa fa-plus"></i> 新增</button>
				</div>
				<div class="col-md-10 text-right">
					<c:if test="${ctype=='BROKERAGE'}">
					 <input type="text" name="ename" class="form-control" placeholder="类别" >
					</c:if> 
					 <div class="input-group">
				     <input type="text" name="cname" class="form-control" placeholder="名称" >
				      <span class="input-group-btn">
				      	<input type="hidden" name="ctype" value="${ctype}">
				        <button class="btn btn-success" type="button" onclick="javascript:Moinfo.search(this);"><i class="fa fa-search"></i></button>
				      </span>
				    </div>
				  </div>
				</form>
			</div>
			<hr/>
		  <!-- Table -->
		  <table class="table table-paper table-striped table-hover">
            <thead>
            <tr>
               <th>序号</th>
               <th>简称</th>
               <c:if test="${ctype=='BROKERAGE'}"><th>类别</th></c:if>
               <th>地址</th>
               <th>联系人</th>
               <th>联系电话</th>
               <th>操作</th>
            </tr>
            </thead>
            <tbody id="dataBody"></tbody>
            <tfoot>
            	<tr><td colspan="10" id="ajaxpage"></td>
            	</tr>
            </tfoot>
          </table>
			<!-- END PAGE CONTENT-->
		</div>
	</div>
	<!-- END CONTENT -->
</div>

<!-- 配置权限弹出框 -->
<div id="addDlg" class="collapse">
	<div class="panel-body">
		<form class="form-horizontal" id="addForm" action="addMoinfo">
			<input type="hidden" name="id">
			<input type="hidden" name="ctype" value="${ctype}" />
		    <div class="form-group col-sm-12">
		      <label class="col-sm-3 control-label">简称</label>
		      <div class="col-sm-8">
		      	<input type="text" name="cname" class="form-control" required placeholder="不能为空">
		      </div>
		    </div>
		    <c:if test="${ctype=='BROKERAGE'}">
		    <div class="form-group col-sm-12">
		      <label class="col-sm-3 control-label">类别</label>
		      <div class="col-sm-8">
		      	<input type="text" name="orgtype" class="form-control" placeholder="">
		      </div>
		    </div>
		    </c:if>
		    <div class="form-group col-sm-12">
		      <label class="col-sm-3 control-label">地址</label>
		      <div class="col-sm-8">
		      	<textarea name="addr" class="form-control" required placeholder="请填写完整的地址信息"></textarea>
		      </div>
		    </div>
		    <div class="form-group col-sm-12">
		      <label class="col-sm-3 control-label">联系人</label>
		      <div class="col-sm-8">
		      	<input type="text" name="contacts" class="form-control" required placeholder="须为中文汉字">
		      </div>
		    </div>
		    <div class="form-group col-sm-12">
		      <label class="col-sm-3 control-label">联系电话</label>
		      <div class="col-sm-8">
		      	<input type="text" name="cnumber" class="form-control" required placeholder="010-12345678">
		      </div>
		    </div>
		</form>   
	</div>
</div>

<%@include file="/common/footer.html" %>
<script type="text/javascript" src="../global/plugins/jquery.validation.min.js"></script>
<script type="text/javascript" src="../global/scripts/audit/moinfo.js"></script>
</body>
</html>