<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/common/header.jsp" flush="true"><jsp:param name="pageTitle" value="${gtypeDes}管理"/></jsp:include>
<link rel="stylesheet" type="text/css" href="../global/css/zTreeStyle.css"/>

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
			<h4 class="page-title">${gtypeDes}管理 <small></small></h4>
			<ol class="breadcrumb"><li><i class="fa fa-home"></i> <a href="index">首页</a></li><li class="active">${gtypeDes}管理</li></ol>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
			  <form id="searchForm" class="form-inline hidden-xs" action="getUsers">
				<div class="col-md-2">
			    	<button type="button" class="btn default btn-sm" onclick="javascript:Ugroup.addDlg(this, 'addUgroup');"><i class="fa fa-plus"></i> 新增</button>
				</div>
				<div class="col-md-10 text-right">
					 <input type="text" name="ename" class="form-control" placeholder="英文名称" >
					 <div class="input-group">
				     <input type="text" name="cname" class="form-control" placeholder="中文名称" >
				      <span class="input-group-btn">
				      	<input type="hidden" name="gtype" value="${gtype}">
				        <button class="btn btn-success" type="button" onclick="javascript:Ugroup.search(this);"><i class="fa fa-search"></i></button>
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
               <th>英文简称</th>
               <th>中文简称</th>
<!--                <th>类型</th> -->
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

<!-- 配置菜单弹出框 -->
<div id="confUgroup" class="collapse" style="max-height:450px;">
	<div class="panel-body">
		<ul id="ztreeConfig" class="ztree"></ul>
	</div>
</div>


<!-- 配置权限弹出框 -->
<div id="addUgroup" class="collapse">
	<div class="panel-body">
		<form class="form-horizontal" id="addForm" action="addUgroup">
			<input type="hidden" name="id">
			<input type="hidden" name="gtype" value="${gtype}" />
		    <div class="form-group col-sm-12">
		      <label class="col-sm-3 control-label">英文简称</label>
		      <div class="col-sm-8">
		      	<input type="text" name="ename" class="form-control" required placeholder="至少3位大小写字母">
		      </div>
		    </div>
		    <div class="form-group col-sm-12">
		      <label class="col-sm-3 control-label">中文简称</label>
		      <div class="col-sm-8">
		      	<input type="text" name="cname" class="form-control" required placeholder="须为中文汉字">
		      </div>
		    </div>
		</form>   
	</div>
</div>

<%@include file="/common/footer.html" %>
<script type="text/javascript" src="../global/plugins/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript" src="../global/plugins/jquery.validation.min.js"></script>
<script type="text/javascript" src="../global/scripts/admin/ugroup.js"></script>
</body>
</html>