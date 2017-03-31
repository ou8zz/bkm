<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/common/header.jsp" flush="true"><jsp:param name="pageTitle" value="章节配置"/></jsp:include>
<link rel="stylesheet" type="text/css" href="../global/plugins/bootstrap-summernote/summernote.css">
<style>
.cp {cursor: pointer;}
.cp:HOVER {cursor: pointer; color:#886f6f;}
body.dragging, body.dragging * {cursor: move !important; }
.dragged {position: absolute;top: 0; opacity: 0.5;z-index: 2000; }

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
			<h4 class="page-title">章节配置<small></small></h4>
			<ol class="breadcrumb"><li><i class="fa fa-home"></i> <a href="index">首页</a></li><li class="active">章节配置</li></ol>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<form id="searchForm" class="form-inline hidden-xs" action="">
					<div class="col-md-2">
				    	<button type="button" class="btn default btn-sm" onclick="javascript:SectionConfig.addDlg(this, 'getSectionConfig');"><i class="fa fa-plus"></i> 新增</button>
					</div>
			        <div class="col-md-10 text-right">
			          <select title="每页条数" data-toggle="tooltip" class="form-control select2" onchange="javascript:SectionConfig.getDataTable(this, {'pageSize':this.value})"><option value="10">10</option><option value="20">20</option><option value="40">40</option></select>
		              <input type="text" name="title" class="form-control" placeholder="标题" >
			          <div class="input-group">
			          	<input type="text" name="content" class="form-control" placeholder="内容" >
				          <span class="input-group-btn">
				            <button class="btn btn-success" type="button" onclick="javascript:SectionConfig.search(this);" data-title="查询"><i class="fa fa-search"></i></button>
				            <button class="btn btn-success" type="button" onclick="javascript:SectionConfig.showDlg(this);" data-title="预览"><i class="fa fa-file-text-o"></i></button>
				            <button class="btn btn-success" type="button" onclick="javascript:SectionConfig.exportFile(this);" data-title="导出"><i class="fa fa-file-word-o"></i></button>
				          </span>
			          </div>
			      	</div>
				</form>
			</div>
	 		
	 		<hr/>
              
			<!-- 章节配置树 -->
			<div class="portlet-body">
				<div class="panel-group accordion" id="accordion">
					<!-- <div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
							<a class="accordion-toggle accordion-toggle-styled" data-toggle="collapse" data-parent="#accordion3" href="#collapse_3_1">
							Collapsible Group Item #1 </a>
							</h4>
						</div>
						<div id="collapse_3_1" class="panel-collapse in">
							<div class="panel-body">
								<p>
									 Duis autem vel eum iriure dolor in hendrerit in vulputate. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut.
								</p>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
							<a class="accordion-toggle accordion-toggle-styled collapsed" data-toggle="collapse" data-parent="#accordion3" href="#collapse_3_2">
							Collapsible Group Item #2 </a>
							</h4>
						</div>
						<div id="collapse_3_2" class="panel-collapse collapse">
							<div class="panel-body" style="height:200px; overflow-y:auto;">
								<p>
									<a class="btn blue" href="ui_tabs_accordions_navs.html#collapse_3_2" target="_blank">
									Activate this section via URL </a>
								</p>
							</div>
						</div>
					</div> -->
				</div>
				<div id="ajaxpage"></div>
			</div>		
    	</div>
	</div>
</div>

<!-- 新增弹出框 -->
<div id="addDlg" class="collapse">
	<div class="panel-body">
		<form class="" id="addForm" action="addSectionConfig">
			<input type="hidden" name="id">
		    <div class="form-group col-sm-12">
		      	<input type="text" name="title" class="form-control" required="required" placeholder="章节目录">
		    </div>
		    <div class="form-group col-sm-12">
		      	<textarea name="content" class="form-control content" required="required" ></textarea>
		      	<span class="text-success">使用 @ 符可以拉出对应管理人信息(使用前后用空格进行拉取和完成)</span>
		    </div>
		</form>   
	</div>
</div>

<!-- 预览配置内容弹出框 -->
<div id="showDlg" class="collapse">
	<div class="panel-body">
	</div>
</div>

<%@include file="/common/footer.html" %>
<script type="text/javascript" src="../global/plugins/jquery-sortable/js/jquery-sortable.min.js"></script>
<script type="text/javascript" src="../global/plugins/bootstrap-summernote/summernote.min.js"></script>
<script type="text/javascript" src="../global/plugins/bootstrap-summernote/lang/summernote-zh-CN.js"></script>
<script type="text/javascript" src="../global/scripts/audit/section_config.js"></script>
</body>
</html>
