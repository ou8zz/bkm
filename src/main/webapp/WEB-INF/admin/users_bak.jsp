<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html class="no-js fixed-layout">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>用户管理</title>
  <meta name="description" content="用户管理">
  <meta name="keywords" content="users">
  <meta name="viewport" content="用户管理">
  <meta name="renderer" content="用户管理">
  <meta http-equiv="Cache-Control" content="用户管理" />
  <meta name="apple-mobile-web-app-title" content="用户管理" />
  <link rel="icon" type="image/png" href="../assets/i/favicon.png">
  <link rel="apple-touch-icon-precomposed" href="../assets/i/app-icon72x72@2x.png">
  <link media="screen" rel="stylesheet" type="text/css" href="../assets/css/amazeui.min.css"/>
  <link media="screen" rel="stylesheet" type="text/css" href="../assets/css/admin.css">
  
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
        <div class="am-fl am-cf"><a href="index"><strong class="am-text-primary am-text-lg">系统管理</strong></a> / <small>用户管理</small></div>
      </div>

	<form id="searchForm" action="">
      <div class="am-g">
        <div class="am-u-sm-12 am-u-md-6">
          <div class="am-btn-toolbar">
            <div class="am-btn-group am-btn-group-xs">
              <button type="button" class="am-btn am-btn-default" onclick="javascript:addDlg('addUser');"><span class="am-icon-plus"></span> 新增</button>
              <button type="button" class="am-btn am-btn-default"><span class="am-icon-trash-o"></span> 删除</button>
            </div>
          </div>
        </div>
        <div class="am-u-sm-12 am-u-md-3">
          <div class="am-form-group">
            <select id="dpt" data-am-selected="{btnSize: 'sm'}" multiple="multiple">
              <option value="option1">部门</option>
              <option value="option2">IT业界</option>
              <option value="option3">数码产品</option>
              <option value="option3">笔记本电脑</option>
              <option value="option3">平板电脑</option>
              <option value="option3">只能手机</option>
              <option value="option3">超极本</option>
            </select>
          </div>
        </div>
        <div class="am-u-sm-12 am-u-md-3">
          <div class="am-input-group am-input-group-sm">
            <input type="text" id="cname" class="am-form-field" placeholder="用户名">
          <span class="am-input-group-btn">
            <button class="am-btn am-btn-default" type="button" onclick="javascript:searchUser(this);">查询</button>
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
	               <th class="table-check"><input type="checkbox" /></th>
	               <th>序号</th>
	               <th>用户账号</th>
	               <th>用户名</th>
	               <th>部门</th>
	               <th>角色</th>
	               <th>管理</th>
	            </tr>
            </thead>
            <tbody id="dataBody"></tbody>
            <tfoot><tr><td colspan="10" id="ajaxpage"></td></tr></tfoot>
          </table>
        </div>
      </div>
    </div>

<div id="addUser" class="am-collapse">
   <div class="am-tabs am-margin" data-am-tabs="{noSwipe: 1}">
      <ul class="am-tabs-nav am-nav am-nav-tabs">
        <li class="am-active"><a href="#tab1">基本信息</a></li>
        <li><a href="#tab2">部门</a></li>
        <li><a href="#tab3">角色</a></li>
        <li><a href="#tab4">密码修改</a></li>
      </ul>
      <div class="am-tabs-bd">
        <div class="am-tab-panel am-fade am-in am-active" id="tab1">
        	<form class="am-form" id="addForm" action="editUser">
        	<input type="hidden" name="id">
            <div class="am-g am-margin-top-sm">
              <div class="am-u-sm-6 am-u-md-3 am-text-right">用户账号</div>
              <div class="am-u-sm-10 am-u-md-6 am-u-end"><input type="text" name="ename" class="am-input-sm" placeholder="输入用户账号" required></div>
            </div>
            <div class="am-g am-margin-top-sm">
              <div class="am-u-sm-6 am-u-md-3 am-text-right">用户名称</div>
              <div class="am-u-sm-10 am-u-md-6 am-u-end"><input type="text" name="cname" class="am-input-sm" placeholder="输入用户名称" required></div>
            </div>
			<div class="am-g am-margin-top-sm">
              <div class="am-u-sm-6 am-u-md-3 am-text-right">邮箱</div>
              <div class="am-u-sm-10 am-u-md-6 am-u-end"><input type="email" name="email" class="am-input-sm" placeholder="输入用户邮箱" required></div>
            </div>
            <div class="am-g am-margin-top">
              <div class="am-u-sm-6 am-u-md-3 am-text-right">性别</div>
              <div class="am-u-sm-10 am-u-md-6 am-u-end">
                <label class="am-radio-inline">
                  <input type="radio" name="gender" value="M" data-am-ucheck> 男
                </label>
                <label class="am-radio-inline">
                  <input type="radio" name="gender" value="F" data-am-ucheck> 女
                </label>
              </div>
            </div>
          </form>
        </div>

        <div class="am-tab-panel am-fade" id="tab2">
        <form class="am-form" id="dptForm" action="editUser">
        <input type="hidden" name="id">
          <div class="am-g am-margin-top">
            <div class="am-u-sm-6 am-u-md-3 am-text-right">所属部门</div>
            <div class="am-u-sm-10 am-u-md-6 am-u-end">
              <select name="dptId" data-am-selected="{btnSize: 'sm', maxHeight: 100}" required>
                <c:forEach items="${userDepartment}" var="b" varStatus="i">
   	 				<option value="${b.id }">${b.cname }</option>
  				</c:forEach>
              </select>
            </div>
           </div>

          <div class="am-g am-margin-top">
            <div class="am-u-sm-6 am-u-md-3 am-text-right">显示状态</div>
            <div class="am-u-sm-10 am-u-md-6 am-u-end">
              <div class="am-btn-group" data-am-button>
                <label class="am-btn am-btn-default am-btn-xs">
                  <input type="radio" name="options" id="option1"> 正常
                </label>
                <label class="am-btn am-btn-default am-btn-xs">
                  <input type="radio" name="options" id="option3"> 不显示
                </label>
              </div>
            </div>
          </div>
          </form>
        </div>

        <div class="am-tab-panel am-fade" id="tab3">
         <form class="am-form" id="roleForm" action="editUser">
         <input type="hidden" name="id">
          <div class="am-g am-margin-top">
            <div class="am-u-sm-6 am-u-md-3 am-text-right">用户角色</div>
            <div class="am-u-sm-10 am-u-md-6 am-u-end">
              <select name="roleId" data-am-selected="{btnSize: 'sm'}" required>
                <c:forEach items="${userRole}" var="b" varStatus="i">
   	 				<option value="${b.id }">${b.cname }</option>
  				</c:forEach>
              </select>
            </div>
          </div>
          <div class="am-g am-margin-top">
            <div class="am-u-sm-6 am-u-md-3 am-text-right">显示状态</div>
            <div class="am-u-sm-10 am-u-md-6 am-u-end">
              <div class="am-btn-group" data-am-button>
                <label class="am-btn am-btn-default am-btn-xs">
                  <input type="radio" name="options" id="option1"> 正常
                </label>
                <label class="am-btn am-btn-default am-btn-xs">
                  <input type="radio" name="options" id="option3"> 不显示
                </label>
              </div>
            </div>
          </div>
		 </form>
        </div>
        
        <div class="am-tab-panel am-fade" id="tab4">
        <form class="am-form" id="updatePwdForm" action="editUser">
        <input type="hidden" name="id">
          <div class="am-g am-margin-top-sm">
            <div class="am-u-sm-6 am-u-md-3 am-text-right">原密码</div>
            <div class="am-u-sm-10 am-u-md-6 am-u-end"><input type="text" name="pwd" class="am-input-sm" required></div>
          </div>
          <div class="am-g am-margin-top-sm">
            <div class="am-u-sm-6 am-u-md-3 am-text-right">新密码</div>
            <div class="am-u-sm-10 am-u-md-6 am-u-end"><input type="text" name="npwd" id="npwd" class="am-input-sm" required minlength="6"></div>
          </div>
          <div class="am-g am-margin-top-sm">
            <div class="am-u-sm-6 am-u-md-3 am-text-right">重复密码</div>
            <div class="am-u-sm-10 am-u-md-6 am-u-end"><input type="text" name="cpwd" class="am-input-sm" required equalTo="#npwd"></div>
          </div>
        </form>
        </div>

      </div>
    </div>
	<div class="am-margin">
    	<button type="button" onclick="javascript:submitAdd(this);" class="am-btn am-btn-secondary" >提交保存</button>
    </div>
</div>

    <%@include file="/common/app_footer.html" %>
  </div>
  <!-- content end -->

</div>

<a href="#" class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu" data-am-offcanvas="{target: '#admin-offcanvas'}"></a>

<%@include file="/common/app_script.html" %>
<script type="text/javascript" src="../assets/js/jquery.validation.min.js"></script>
<script type="text/javascript" src="../assets/js/admin/user.js"></script>

</body>
</html>
