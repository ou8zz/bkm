<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/common/header.jsp" flush="true"><jsp:param name="pageTitle" value="菜单配置"/></jsp:include>
<link rel="stylesheet" type="text/css" href="../global/css/zTreeStyle.css"/>
<style type="text/css">
/* 选择图标自定义css */
.ulliico {width:100%;}
.ulliico:HOVER {width:100%;background-color:#e8e8e8;}
.dropdown-menu li i{cursor:pointer;}
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
			<h4 class="page-title">菜单配置<small></small></h4>
			<ol class="breadcrumb"><li><i class="fa fa-home"></i> <a href="index">首页</a></li><li class="active">菜单配置</li></ol>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<form id="searchForm" class="form-inline hidden-xs" action=""> 
			        <div class="col-md-12 text-right">
		              <input type="text" name="name" class="form-control" placeholder="菜单名称" >
			          <div class="input-group">
			          	<input type="text" name="url" class="form-control" placeholder="菜单URL" >
				          <span class="input-group-btn">
				            <button class="btn btn-success" type="button" onclick="javascript:Menus.search(this);"><i class="fa fa-search"></i></button>
				          </span>
			          </div>
			      	</div>
				</form>
			</div>
	 		<hr/>
			<div class="row">
				<div class="col-sm-4" style="padding-left: 5px; padding-right: 5px;">
					<div class="portlet grey-cascade box">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-cogs"></i><small>菜单结构<abbr title="可以对节点进行任意位置拖拽排序" class="initialism"></abbr></small>
							</div>
							<div class="tools">
								<i onclick="Menus.expandAll(this);" class="fa fa-expand" title="展开或关闭所有节点"></i>
								<i onclick="Menus.addDlg(this, 'addZtreeTree');" class="fa fa-plus" title="新增根目录"></i>
								<i onclick="Menus.drag(this);" class="fa fa-sort" title="是否拖拽节点"></i>
								<i onclick="Menus.reload(this, 'tree');" class="fa fa-refresh" title="刷新"></i>
								<i onclick="Menus.fullscreen(this);" class="fa fa-arrows-alt" title="全屏"></i>
							</div>
						</div>
						<div class="portlet-body">
							<div class="table-responsive">
								<ul id="ztreeTree" class="ztree"></ul>
							</div>
						</div>
					</div>
				</div>
				<div id="treeDetail" class="col-sm-8" style="padding-left: 5px; padding-right: 5px;">
					<div class="portlet grey-cascade box">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-cogs"></i><small>菜单字段</small>
							</div>
							<div class="tools">
								<i title="全屏" onclick="javascript:Menus.fullscreen(this)" class="fa fa-arrows-alt"></i>
							</div>
						</div>
						<div class="portlet-body">
							<div class="table-responsive">
								<form class="form-horizontal" id="addForm" action="addZtree">
									<div class="panel-body">
							       		<input type="hidden" name="id" class="form-control" min="1" readonly="readonly" placeholder="ID自动生成">
							       		<input type="hidden" name="pId" class="form-control" min="1" readonly="readonly" placeholder="顶级父节点为空">
								           <div id="choico" class="form-group col-sm-12">
								             <label class="col-sm-3 control-label">图标样式选择</label>
								             <div class="col-sm-9">
								              <div class="btn-group btn-group-solid ulliico">
											    <button class="btn btn-fit-height dropdown-toggle ulliico" type="button" data-toggle="dropdown"><span id="show-ico"></span> &nbsp;&nbsp;&nbsp;&nbsp;选择 <i class="fa fa-caret-down"></i></button>
											    <ul class="dropdown-menu">
													<li>
														<c:forEach items="${paramer.ob}" var="b" varStatus="i">
									   	 					<i class="fa ${b.ico}" title="${b.ico}" onclick="javascript:Menus.choiceIco(this,'${b.ico}');"></i>
									  					</c:forEach>
													</li>
												</ul>
											  </div>
											</div>
								           </div>
								           <div class="form-group col-sm-12">
								             <label class="col-sm-3 control-label">菜单名称</label>
								             <div class="col-sm-9">
								             	<input type="text" name="name" class="form-control" required pattern="[A-z]{3}" placeholder="须为中文汉字">
								             </div>
								           </div>
								           <div class="form-group col-sm-12">
								             <label class="col-sm-3 control-label">显示标题</label>
								             <div class="col-sm-9">
								             	<input type="text" name="title" class="form-control" required pattern="[\u4e00-\u9fa5]" placeholder="须为中文汉字">
								             </div>
								           </div>
										   <div class="form-group col-sm-12">
								             <label class="col-sm-3 control-label">URL</label>
								             <div class="col-sm-9">
									             <div class="input-group">
										          	<input type="text" name="url" class="form-control" placeholder="请求的相对路径" >
											        <span class="input-group-btn">
											          <button type="button" class="btn btn-info" onclick="javascript:Menus.link(this)"><i class="fa fa-link"></i></button>
											        </span>
										         </div>
									         </div>
								          </div>
								    </div>
								    <div class="form-group col-sm-12 text-right">
						              <button type="button" onclick="javascript:Menus.submitAdd(this);" class="btn btn-primary btn-sm" ><i class="fa fa-pencil"></i> 提交保存</button>
						            </div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
    	</div>
	</div>
</div>

<!-- 显示法规内容弹出框 -->
<div id="showLegal" class="collapse">
<div class="panel-body">
</div>
</div>

<%@include file="/common/footer.html" %>
<script type="text/javascript" src="../global/plugins/jquery.blockui.min.js"></script>
<script type="text/javascript" src="../global/plugins/jquery.validation.min.js"></script>
<script type="text/javascript" src="../global/plugins/jquery.ztree.exedit.min.js"></script>
<script type="text/javascript" src="../global/scripts/admin/menus.js"></script>
</body>
</html>
