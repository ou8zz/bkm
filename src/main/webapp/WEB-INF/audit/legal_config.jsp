<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/common/header.jsp" flush="true"><jsp:param name="pageTitle" value="法规维护"/></jsp:include>
<link rel="stylesheet" type="text/css" href="../global/css/zTreeStyle.css"/>
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
			<h4 class="page-title">法规维护<small></small></h4>
			<ol class="breadcrumb"><li><i class="fa fa-home"></i> <a href="index">首页</a></li><li class="active">法规维护</li></ol>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<form id="searchForm" class="form-inline hidden-xs" action=""> 
					<div class="col-md-2">
				    	
					</div>
			        <div class="col-md-10 text-right">
		              <input type="text" name="title" class="form-control" placeholder="标题" >
			          <div class="input-group">
			          	<input type="text" name="remark" class="form-control" placeholder="内容" >
				          <span class="input-group-btn">
				            <button class="btn btn-success" type="button" onclick="javascript:LegalConfig.search(this);"><i class="fa fa-search"></i></button>
				          </span>
			          </div>
			      	</div>
				</form>
			</div>
	 		<hr/>
			<div class="row">
				<div class="col-sm-3" style="padding-left: 5px; padding-right: 5px;">
					<div class="portlet grey-cascade box">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-cogs"></i><small>法规类别<abbr title="可以对节点进行任意位置拖拽排序" class="initialism"></abbr></small>
							</div>
							<div class="tools">
								<i onclick="LegalConfig.expandAll(this);" class="fa fa-expand" title="展开或关闭所有节点"></i>
								<i onclick="LegalConfig.addDlg(this, 'addLegalTree');" class="fa fa-plus" title="新增根目录"></i>
								<i onclick="LegalConfig.drag(this);" class="fa fa-sort" title="是否拖拽节点"></i>
								<i onclick="LegalConfig.reload(this, 'tree');" class="fa fa-refresh" title="刷新"></i>
								<i onclick="LegalConfig.fullscreen(this);" class="fa fa-arrows-alt" title="全屏"></i>
							</div>
						</div>
						<div class="portlet-body">
							<div class="table-responsive">
								<ul id="legalTree" class="ztree"></ul>
							</div>
						</div>
					</div>
				</div>
				<div id="legalBody" class="col-sm-9" style="padding-left: 5px; padding-right: 5px;">
					<div class="portlet grey-cascade box">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-cogs"></i><small>法规条目</small>
							</div>
							<div class="tools">
								<i onclick="LegalConfig.addLegal(this, 'addLegal');" class="fa fa-plus" title="新增"></i>
								<i onclick="LegalConfig.reload(this, 'legal');" class="fa fa-refresh" title="刷新"></i>
								<i onclick="LegalConfig.fullscreen(this);" class="fa fa-arrows-alt" title="全屏"></i>
							</div>
						</div>
						<div class="portlet-body">
							<div class="table-responsive">
							  <!-- Table -->
							  <table class="table table-paper table-striped table-hover">
								<thead>
					            <tr>
					               <th>序号</th><th>颁布时间</th><th>发文号</th><th>标题</th><th>操作</th>
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
	</div>
</div>

<!-- LegalTree弹出框 -->
<div id="addDlg" class="collapse">
	<div class="panel-body">
		<form class="" id="addTreeForm" action="addLegalTree">
		<input type="hidden" name="id">
		<input type="hidden" name="pId">
		<input type="hidden" name="node">
		  <div class="form-group col-sm-12">
            <label class="col-sm-2 control-label text-right">名称</label>
            <div class="col-sm-10"><input type="text" name="name" class="form-control" required></div>
            <input style='display:none' />
          </div>
      </form>
	</div>
</div>

<!-- 新增Legal弹出框 -->
<div id="addLegal" class="collapse">
	<div class="panel-body">
		<form class="" id="addForm" action="addLegal">
		<input type="hidden" name="id">
		<input type="hidden" name="tid">
		  <div class="form-group col-sm-12">
            <label class="col-sm-2 control-label text-right">法规类别</label>
            <div class="col-sm-10"><input type="text" name="tid_" class="form-control" placeholder="请选择对应的法规类别" required readonly></div>
          </div>
		  <div class="form-group col-sm-12">
            <label class="col-sm-2 control-label text-right">颁布时间</label>
            <div class="col-sm-10"><input type="date" name="cdate" class="form-control" required></div>
          </div>
          <div class="form-group col-sm-12">
            <label class="col-sm-2 control-label text-right">发文号</label>
            <div class="col-sm-10"><input type="text" name="issued" class="form-control" required></div>
          </div>
          <div class="form-group col-sm-12">
            <label class="col-sm-2 control-label text-right">标题</label>
            <div class="col-sm-10"><textarea name="title" class="form-control" required></textarea></div>
          </div>
          <div class="form-group col-sm-12">
	      	<textarea name="remark" class="form-control content" required></textarea>
	      </div>
      </form>
	</div>
</div>

<!-- 显示法规内容弹出框 -->
<div id="showLegal" class="collapse">
<div class="panel-body">
</div>
</div>

<%@include file="/common/footer.html" %>
<script type="text/javascript" src="../global/plugins/bootstrap-summernote/summernote.min.js"></script>
<script type="text/javascript" src="../global/plugins/bootstrap-summernote/lang/summernote-zh-CN.js"></script>
<script type="text/javascript" src="../global/plugins/jquery.blockui.min.js"></script>
<script type="text/javascript" src="../global/plugins/jquery.validation.min.js"></script>
<script type="text/javascript" src="../global/plugins/jquery.ztree.exedit.min.js"></script>
<script type="text/javascript" src="../global/scripts/audit/legal_config.js"></script>
</body>
</html>
