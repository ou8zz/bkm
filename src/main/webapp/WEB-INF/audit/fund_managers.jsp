<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/common/header.jsp" flush="true"><jsp:param name="pageTitle" value="基金经理配置"/></jsp:include>
<link rel="stylesheet" type="text/css" href="../global/plugins/bootstrap-summernote/summernote.css">

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
			<h4 class="page-title">基金经理配置<small></small></h4>
			<ol class="breadcrumb"><li><i class="fa fa-home"></i> <a href="index">首页</a></li><li class="active">基金经理配置</li></ol>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="tabbable tabbable-custom boxless">
		      <ul class="nav nav-tabs">
		        <li class="active"><a href="#fm" data-toggle="tab">基金经理信息</a></li>
		        <li><a href="#fi" data-toggle="tab">基金产品信息</a></li>
		      </ul>
		      <div class="tab-content">
				<!-- 基金经理信息 -->
		        <div class="tab-pane fade panel-body in active" id="fm">
					<div class="row">
						<form id="searchForm" class="form-inline hidden-xs" action="">
							<div class="col-md-2">
						    	<button type="button" class="btn default btn-sm" onclick="javascript:FundManagers.addDlg(this, 'addFundManagers');"><i class="fa fa-plus"></i> 新增</button>
							</div>
					        <div class="col-md-10 text-right">
					          <select name="ctypes" class="form-control select2" multiple="multiple" style="width: 40%" data-placeholder="人员类型" data-allow-clear="true">
							 	<option value="">全部</option>
				                <c:forEach items="${ctype}" var="b" varStatus="i">
				   	 				<option value="${b.key }">${b.value }</option>
				  				</c:forEach>
				              </select>
				              <input type="text" name="ename" class="form-control" placeholder="英文简称" >
					          <div class="input-group">
					          	<input type="text" name="cname" class="form-control" placeholder="中文名称" >
						          <span class="input-group-btn">
						            <button class="btn btn-success" type="button" onclick="javascript:FundManagers.search();"><i class="fa fa-search"></i></button>
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
			               <th>序号</th><th>名称</th><th>类型</th><th>入职日期</th><th>离职日期</th><th>操作</th>
			            </tr>
			            </thead>
			            <tbody id="dataBody"></tbody>
			            <tfoot><tr><td colspan="10" id="ajaxpage"></td></tr></tfoot>
					</table>
				</div>
				
				<!-- 基金组合信息 -->
				<div class="tab-pane fade panel-body" id="fi">
					<div class="row">
						<form id="searchForm" class="form-inline hidden-xs" action="">
							<div class="col-md-2">
						    	<button type="button" class="btn default btn-sm" onclick="javascript:FundInfo.addDlg(this, 'addFundInfo');"><i class="fa fa-plus"></i> 新增</button>
							</div>
					        <div class="col-md-10 text-right">
					          <input type="text" name="uname" class="form-control" placeholder="管理人" >
				              <input type="text" name="fcode" class="form-control" placeholder="基金代码" >
					          <div class="input-group">
					          	<input type="text" name="fname" class="form-control" placeholder="基金名称" >
						          <span class="input-group-btn">
						            <button class="btn btn-success" type="button" onclick="javascript:FundInfo.search();"><i class="fa fa-search"></i></button>
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
			               <th>序号</th><th>基金代码</th><th>基金名称</th><th>管理人</th><th>操作</th>
			            </tr>
			            </thead>
			            <tbody id="dataBody"></tbody>
			            <tfoot><tr><td colspan="10" id="ajaxpage"></td></tr></tfoot>
					</table>
				</div>
			</div>	
			</div>
    	</div>
	</div>
</div>

<!-- 新增弹出框 -->
<div id="addDlg" class="collapse">
  <div class="tabbable tabbable-custom boxless panel-body">
      <ul class="nav nav-tabs">
        <li class="active"><a href="#fundManager" data-toggle="tab">基金经理信息</a></li>
        <li><a href="#fundInfo" data-toggle="tab">基金经理组合对应</a></li>
      </ul>
      <div class="tab-content">
		<!-- 基金经理信息 -->
        <div class="tab-pane fade panel-body in active" id="fundManager">
		<form class="" id="addForm" action="addFundManagers">
		  <input type="hidden" name="id">
		  <div class="form-group col-sm-6">
            <label class="col-sm-3 control-label text-right">英文简称</label>
            <div class="col-sm-8"><input type="text" name="ename" class="form-control" required></div>
          </div>
          <div class="form-group col-sm-6">
            <label class="col-sm-3 control-label text-right">中文名称</label>
            <div class="col-sm-8"><input type="text" name="cname" class="form-control" required></div>
          </div>
          <div class="form-group col-sm-6">
            <label class="col-sm-3 control-label text-right">入职日期</label>
            <div class="col-sm-8"><input type="date" name="itime" class="form-control" required></div>
          </div>
          <div class="form-group col-sm-6">
            <label class="col-sm-3 control-label text-right">离职日期</label>
            <div class="col-sm-8"><input type="date" name="otime" class="form-control"></div>
          </div>
          <div class="form-group col-sm-6">
            <label class="col-sm-3 control-label text-right">人员类型</label>
            <div class="col-sm-8">
			  <select name="ctype" class="form-control select2" style="width:100%" data-placeholder="人员类型">
                <c:forEach items="${ctype}" var="b" varStatus="i">
   	 				<option value="${b.key }">${b.value }</option>
  				</c:forEach>
              </select>
			</div>
          </div>
          <div class="form-group col-sm-12">
	      	<textarea name="resume" class="form-control content" required></textarea>
	      </div>
		</form>
	</div>
	
	<!-- 基金经理组合对应 -->
	<div class="tab-pane panel-body fade" id="fundInfo">
	  <div class="row">
		<form id="searchForm" class="form-inline hidden-xs" action="">
	        <div class="col-md-12 text-right">
	          <select name="userid" class="form-control select2" data-placeholder="当前管理人">
	          	<option id="suid" value="1">当前管理人</option>
	          	<option value="">所有管理人</option>
	          </select>
              <input type="text" name="fcode" class="form-control" placeholder="基金代码" >
	          <div class="input-group">
	          	<input type="text" name="fname" class="form-control" placeholder="基金名称" >
		          <span class="input-group-btn">
		            <button class="btn btn-success" type="button" onclick="javascript:FundInfo.searchFundInfo();"><i class="fa fa-search"></i></button>
		          </span>
	          </div>
	      	</div>
		</form>
	</div>
		<hr/>
	 <!-- Table -->
	 <form id="fundCheck" action="editFundInfo">
	  <input type="hidden" name="userid">
	  <table class="table table-paper table-striped table-hover">
		<thead>
           <tr>
              <th class="table-check"><input id="tog" type="checkbox" /></th><th>序号</th><th>基金代码</th><th>基金名称</th><th>管理人</th>
           </tr>
           </thead>
           <tbody id="dataBody"></tbody>
           <tfoot><tr><td colspan="10" id="ajaxpage"></td></tr></tfoot>
	</table>
	</form>
	</div>
	</div>
  </div>
</div>

<!-- 新增基金信息弹出框 -->
<div id="addDlgFi" class="collapse">
  	<div class="panel-body">
		<form class="" id="addFundForm" action="editFundInfo">
		<input type="hidden" name="id">
		  <div class="form-group col-sm-12">
            <label class="col-sm-3 control-label text-right">产品代码</label>
            <div class="col-sm-8"><input type="text" name="fcode" class="form-control" required></div>
          </div>
          <div class="form-group col-sm-12">
            <label class="col-sm-3 control-label text-right">产品名称</label>
            <div class="col-sm-8"><input type="text" name="fname" class="form-control" required></div>
          </div>
      </form>
	</div>
</div>

<%@include file="/common/footer.html" %>
<script type="text/javascript" src="../global/plugins/bootstrap-summernote/summernote.min.js"></script>
<script type="text/javascript" src="../global/plugins/bootstrap-summernote/lang/summernote-zh-CN.js"></script>
<script type="text/javascript" src="../global/plugins/jquery.validation.min.js"></script>
<script type="text/javascript" src="../global/scripts/audit/fund_managers.js"></script>
</body>
</html>
