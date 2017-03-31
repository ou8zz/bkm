<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js fixed-layout">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>${typeDes}管理</title>
  <meta name="description" content="${typeDes}管理">
  <meta name="keywords" content="ugroup">
  <meta name="viewport" content="${typeDes}管理">
  <meta name="renderer" content="${typeDes}管理">
  <meta http-equiv="Cache-Control" content="${typeDes}管理" />
  <meta name="apple-mobile-web-app-title" content="${typeDes}管理" />
  <link rel="icon" type="image/png" href="../assets/img/favicon.png">
  <link rel="apple-touch-icon-precomposed" href="../assets/img/app-icon72x72@2x.png">
  <link media="screen" rel="stylesheet" type="text/css" href="../assets/css/amazeui.min.css"/>
  <link media="screen" rel="stylesheet" type="text/css" href="../assets/css/admin.css">
  <link media="screen" rel="stylesheet" type="text/css" href="../assets/css/zTreeStyle.css">
</head>
<body>
<%@include file="/common/app_header.html" %>

<div class="am-cf admin-main">
  <!-- sidebar start -->
  <div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
    <!-- ztree菜单 -->
    <ul id="treeMenu" class="am-list admin-sidebar-list"></ul>
  </div>
  <!-- sidebar end -->

  <!-- content start -->
  <div class="admin-content">
    <div class="admin-content-body">
      <div class="am-cf am-padding">
        <div class="am-fl am-cf"><a href="index"><strong class="am-text-primary am-text-lg">系统管理</strong></a> / <small>${typeDes}管理</small></div>
      </div>

	<form id="searchForm" action="">
      <div class="am-g">
        <div class="am-u-sm-12 am-u-md-6">
          <div class="am-btn-toolbar">
            <div class="am-btn-group am-btn-group-xs">
              <button type="button" class="am-btn am-btn-default" onclick="javascript:addDlg(this, 'addUgroup');"><span class="am-icon-plus"></span> 新增</button>
            </div>
          </div>
        </div>
        <div class="am-u-sm-12 am-u-md-3">
          <div class="am-input-group am-input-group-sm">
            <input type="text" name="ename" class="am-form-field" placeholder="英文名称" >
          </div>
        </div>
        <div class="am-u-sm-12 am-u-md-3">
          <div class="am-input-group am-input-group-sm">
            <input type="text" name="cname" class="am-form-field" placeholder="中文名称" >
          <span class="am-input-group-btn">
          	<input type="hidden" name="type" value="${type}">
            <button class="am-btn am-btn-default" type="button" onclick="javascript:searchUgroup(this);">查询</button>
          </span>
          </div>
        </div>
      </div>
	</form>
	
      <div class="am-g">
        <div class="am-u-sm-12">
          <table class="am-table am-table-bd am-table-striped am-table-hover admin-content-table">
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
        </div>
      </div>
      
    </div>

<!-- 配置菜单弹出框 -->
<div id="confUgroup" class="am-collapse">
<div class="am-margin">
  <button type="button" onclick="javascript:saveRoleZtree(this);" class="am-btn am-btn-secondary" >提交保存</button>
</div>
<div class="am-panel am-panel-default">
    <div class="am-panel-bd">
		<ul id="ztreeConfig" class="ztree"></ul>
	</div>
</div>
</div>


<!-- 配置权限弹出框 -->
<div id="addUgroup" class="am-collapse">
<form class="am-form" id="addForm" action="addUgroup" data-am-validator>
<input type="hidden" name="id">
   <div class="am-tabs am-margin" data-am-tabs>
      <ul class="am-tabs-nav am-nav am-nav-tabs">
        <li class="am-active"><a href="#tab1">基本信息</a></li>
      </ul>
      <div class="am-tabs-bd">
        <div class="am-tab-panel am-fade am-in am-active" id="tab1">
        	
            <div class="am-g am-margin-top-sm">
              <div class="am-u-sm-6 am-u-md-3 am-text-right">英文简称</div>
              <div class="am-u-sm-10 am-u-md-6 am-u-end">
              	<input type="text" name="ename" class="am-input-sm" required placeholder="至少3位大小写字母">
              </div>
            </div>
            <div class="am-g am-margin-top-sm">
              <div class="am-u-sm-6 am-u-md-3 am-text-right">中文简称</div>
              <div class="am-u-sm-10 am-u-md-6 am-u-end">
              	<input type="text" name="cname" class="am-input-sm" required placeholder="须为中文汉字">
              </div>
            </div>
			<!-- <div class="am-g am-margin-top-sm">
              <div class="am-u-sm-6 am-u-md-3 am-text-right">类型</div>
              <div class="am-u-sm-10 am-u-md-6 am-u-end" >
	              <select id="type" name="type">
	                <option value="ROLE">角色</option>
	                <option value="DEPARTMENT">部门</option>
	                <option value="POSITION">职务</option>
	              </select>
	            </div>
            </div> -->
            <div class="am-g am-margin-top-sm">
              <div class="am-u-sm-6 am-u-md-3 am-text-right">
				<input type="hidden" name="type" value="${type}">
      			<button type="button" onclick="javascript:submitAdd(this);" class="am-btn am-btn-secondary" >提交保存</button>
		      </div>
              <div class="am-u-sm-10 am-u-md-6 am-u-end">
              	
              </div>
            </div>
        </div>
      </div>
    </div>
</form>    
</div>


  <%@include file="/common/app_footer.html" %>
  </div>
  <!-- content end -->
</div>


<a href="#" class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu" data-am-offcanvas="{target: '#admin-offcanvas'}"></a>

<%@include file="/common/app_script.html" %>
<script type="text/javascript" src="../assets/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript" src="../assets/js/jquery.validation.min.js"></script>
<script type="text/javascript" src="../assets/js/admin/ugroup.js"></script>

</body>
</html>
