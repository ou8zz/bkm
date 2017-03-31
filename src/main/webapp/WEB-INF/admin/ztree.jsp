<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/common/header.jsp" flush="true"><jsp:param name="pageTitle" value="菜单管理"/></jsp:include>
<style type="text/css">
/* 选择图标自定义css */
.ulliico {width:400px;}
.dropdown-menu li i{cursor:pointer;}
</style>

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
			<h4 class="page-title">菜单管理 <small></small></h4>
			<ol class="breadcrumb"><li><i class="fa fa-home"></i> <a href="index">首页</a></li><li class="active">菜单管理</li></ol>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<form id="searchForm" class="form-inline hidden-xs" action="">
			      	<div class="col-md-3">
			            <div class="btn-group">
			              <button type="button" class="btn default btn-sm" onclick="javascript:Ztree.addDlg(this, 'addZtree');"><i class="fa fa-plus"></i> 新增</button>
			            </div>
			        </div>
			        <div class="col-md-9 text-right">
			        	<select title="每页条数" class="form-control select2" onchange="javascript:Ztree.getDataTable(this, {'pageSize':this.value})"><option value="10">10</option><option value="20">20</option><option value="40">40</option></select>
			            <input type="text" name="name" class="form-control" placeholder="菜单名称" >
			          <div class="input-group">
			          	<input type="text" name="node" class="form-control" placeholder="NODE" >
				          <span class="input-group-btn">
				            <button class="btn btn-success" type="button" onclick="javascript:Ztree.search(this);"><i class="fa fa-search"></i></button>
				          </span>
			          </div>
			      </div>
				</form>
			</div>
		<hr/>
          <table class="table table-bordered table-striped text-nowrap table-hover admin-content-table">
            <thead>
            <tr>
               <th>ID</th>
               <th>PID</th>
               <th>NODE</th>
               <th>图标样式</th>
               <th>菜单名称</th>
               <th>显示标题</th>
               <th>URL</th>
               <th>操作</th>
            </tr>
            </thead>
            <tbody id="dataBody">
            </tbody>
            <tfoot>
            	<tr><td colspan="10" id="ajaxpage"></td>
            	</tr>
            </tfoot>
          </table>
    </div>
</div>
</div>

<!-- 弹出框 -->
<div id="addZtree" class="collapse">
<form class="form-horizontal" id="addForm" action="addZtree">
	<div class="panel-body">
       	<div class="form-group col-sm-12">
             <label class="col-sm-3 control-label">ID/PID</label>
             <div class="col-sm-4">
             	<input type="number" name="id" class="form-control" min="1" readonly="readonly" placeholder="ID自动生成">
             </div>
             <div class="col-sm-4">
             	<input type="number" name="pId" class="form-control" min="1" placeholder="顶级父节点为空">
             </div>
           </div>
           <div id="choico" class="form-group col-sm-12">
             <label class="col-sm-3 control-label">图标样式选择</label>
             <div class="col-sm-8">
              <div class="btn-group btn-group-solid">
			    <button class="btn btn-fit-height dropdown-toggle ulliico" type="button" data-toggle="dropdown"><span id="show-ico"></span> &nbsp;&nbsp;&nbsp;&nbsp;选择 <i class="fa fa-caret-down"></i></button>
			    <ul class="dropdown-menu">
					<li>
						<c:forEach items="${paramer.ob}" var="b" varStatus="i">
	   	 					<i class="fa ${b.ico}" onclick="javascript:Ztree.choiceIco(this,'${b.ico}');"></i>
	  					</c:forEach>
					</li>
				</ul>
			  </div>
			</div>
           </div>
           <div class="form-group col-sm-12">
             <label class="col-sm-3 control-label">NODE</label>
             <div class="col-sm-8">
             	<input type="text" name="node" class="form-control" required placeholder="按照NODE规则进行设置">
             </div>
           </div>
           <div class="form-group col-sm-12">
             <label class="col-sm-3 control-label">菜单名称</label>
             <div class="col-sm-8">
             	<input type="text" name="name" class="form-control" required pattern="[A-z]{3}" placeholder="须为中文汉字">
             </div>
           </div>
           <div class="form-group col-sm-12">
             <label class="col-sm-3 control-label">显示标题</label>
             <div class="col-sm-8">
             	<input type="text" name="title" class="form-control" required pattern="[\u4e00-\u9fa5]" placeholder="须为中文汉字">
             </div>
           </div>
		   <div class="form-group col-sm-12">
             <label class="col-sm-3 control-label">URL</label>
             <div class="col-sm-8">
             	<input type="text" name="url" class="form-control" placeholder="请求的相对路径">
             </div>
          </div>
    </div>
</form>
</div>

<%@include file="/common/footer.html" %>
<script type="text/javascript" src="../global/plugins/jquery.validation.min.js"></script>
<script type="text/javascript" src="../global/scripts/admin/ztree.js"></script>
</body>
</html>
