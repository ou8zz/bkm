<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/common/header.jsp" flush="true"><jsp:param name="pageTitle" value="用户管理"/></jsp:include>
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
			<h4 class="page-title">用户管理 <small></small></h4>
			<ol class="breadcrumb"><li><i class="fa fa-home"></i> <a href="index">首页</a></li><li class="active">用户管理</li></ol>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
			  <form id="searchForm" class="form-inline hidden-xs" action="getUsers">
				<div class="col-md-3">
					<div class="btn-group">
			    		<button type="button" class="btn default btn-sm" onclick="javascript:Users.addDlg(this, 'addUser');"><i class="fa fa-plus"></i> 新增</button>
<!-- 	   					<button type="button" class="btn default btn-sm" onclick="javascript:Users.delUser();"><i class="fa fa-times"></i> 删除</button> -->
	   				</div>
				</div>
				<div class="col-md-9 text-right">
					 <select name="gactive" class="form-control select2" title="账号状态">
					 	<option value="">全部</option>
					 	<option value="true">启用</option>
					 	<option value="false">禁用</option>
		             </select>
					 <select name="dptIds" class="form-control select2" multiple="multiple" style="width:35%" data-placeholder="部门选择" data-allow-clear="true">
					 	<option value="">全部</option>
		                <c:forEach items="${userDepartment}" var="b" varStatus="i">
		   	 				<option value="${b.id }">${b.cname }</option>
		  				</c:forEach>
		             </select>
				 	 <input type="text" name="ename" class="form-control" placeholder="用户账号">
					 <div class="input-group">
				     <input type="text" name="cname" class="form-control" placeholder="用户名">
				      <span class="input-group-btn">
				        <button class="btn btn-success" type="button" onclick="javascript:Users.search(this);"><i class="fa fa-search"></i></button>
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
	               <th class="table-check"><input id="tog" type="checkbox" /></th>
	               <th>序号</th><th>用户账号</th><th>用户名</th><th>部门</th><th>角色</th><th>操作</th>
	            </tr>
	            </thead>
	            <tbody id="dataBody"></tbody>
	            <tfoot><tr><td colspan="10" id="ajaxpage"></td></tr></tfoot>
			</table>
			<!-- END PAGE CONTENT-->
		</div>
	</div>
	<!-- END CONTENT -->
</div>

<!-- 用户信息弹出框-->
<div id="addUser" class="collapse" >
   <div class="tabbable tabbable-custom boxless">
      <ul class="nav nav-tabs">
        <li class="active"><a href="#tab1" data-toggle="tab">基本信息</a></li>
        <li><a href="#tab2" data-toggle="tab">部门/角色</a></li>
        <li><a href="#tab3" data-toggle="tab">密码重置</a></li>
      </ul>
      <div class="tab-content">
        <div class="tab-pane fade in active" id="tab1">
        	<br/>
           <form id="addForm" class="form-horizontal" action="editUser">
        	<input type="hidden" name="id">
        	<div class="form-group col-sm-12">
				<label class="col-sm-3 control-label">用户账号</label>
				<div class="col-sm-8"><input type="text" name="ename" class="form-control" placeholder="输入用户账号" required></div>
		  	</div>
            <div class="form-group col-sm-12">
              <label class="col-sm-3 control-label">用户名称</label>
              <div class="col-sm-8"><input type="text" name="cname" class="form-control" placeholder="输入用户名称" required></div>
            </div>
			<div class="form-group col-sm-12">
              <label class="col-sm-3 control-label">邮箱</label>
              <div class="col-sm-8"><input type="email" name="email" class="form-control" placeholder="输入用户邮箱" required></div>
            </div>
            <div class="form-group col-sm-12">
              <label class="col-md-3 control-label">性别</label>
              <div class="btn-group col-sm-8" data-toggle="buttons">
                <label class="btn btn-default">
                  <input type="radio" name="gender" value="M"> 男
                </label>
                <label class="btn btn-default">
                  <input type="radio" name="gender" value="F"> 女
                </label>
              </div>
            </div>
            <div class="form-group col-sm-12">
              <label class="col-md-3 control-label">账号状态</label>
              <div class="btn-group col-sm-8" data-toggle="buttons">
                <label class="btn btn-default">
                  <input type="radio" name="gactive" value="true"> 启用
                </label>
                <label class="btn btn-default">
                  <input type="radio" name="gactive" value="false"> 禁用
                </label>
              </div>
            </div>
            <div class="form-group col-sm-12">
              <label class="col-md-3 control-label">是否锁定</label>
              <div class="btn-group col-sm-8" data-toggle="buttons">
                <label class="btn btn-default">
                  <input type="radio" name="locked" value="false"> 未锁定
                </label>
                <label class="btn btn-default">
                  <input type="radio" name="locked" value="true"> 已锁定
                </label>
              </div>
            </div>
          </form>
        </div>

        <div class="tab-pane fade" id="tab2">
        <br/>
        <form id="ugForm" class="form-horizontal" action="editUserUgroup">
        <input type="hidden" name="id">
          <div class="form-group col-sm-12">
            <label class="col-sm-3 control-label">所属部门</label>
            <div class="col-sm-8">
              <select name="dptId" class="form-control" required>
                <c:forEach items="${userDepartment}" var="b" varStatus="i">
   	 				<option value="${b.id }">${b.cname }</option>
  				</c:forEach>
              </select>
            </div>
           </div>
           <div class="form-group col-sm-12">
            <label class="col-sm-3 control-label">用户角色</label>
            <div class="col-sm-8">
              <select name="roleId" class="form-control" required onchange="javascript:Users.confDlg(this, this.value);">
                <c:forEach items="${userRole}" var="b" varStatus="i">
   	 				<option value="${b.id }">${b.cname }</option>
  				</c:forEach>
              </select>
            </div>
          </div>
          <div class="form-group col-sm-12">
            <label class="col-sm-3 control-label">权限预览</label>
          	<div class="col-sm-8">
          		<div class="panel panel-default" style="max-height:300px;overflow:auto">
				    <div class="panel-bd">
						<ul id="ztreeConfig" class="ztree"></ul>
					</div>
				</div>
			</div>
          </div>
          </form>
        </div>

        <div class="tab-pane fade" id="tab3">
        <br/>
        <form id="pwdForm" class="form-horizontal" action="editUser">
        <input type="hidden" name="id">
          <div class="form-group col-sm-12">
            <label class="col-sm-3 control-label">新密码</label>
            <div class="col-sm-8"><input type="password" name="npwd" id="npwd" class="form-control" required minlength="6"></div>
          </div>
          <div class="form-group col-sm-12">
            <label class="col-sm-3 control-label">重复密码</label>
            <div class="col-sm-8"><input type="password" name="pwd" id="pwd" class="form-control" required equalTo="#npwd"></div>
          </div>
        </form>
        </div>
      </div>
    </div>
</div>

<%@include file="/common/footer.html" %>
<script type="text/javascript" src="../global/plugins/jquery.validation.min.js"></script>
<script type="text/javascript" src="../global/scripts/md5.js"></script>
<script type="text/javascript" src="../global/scripts/admin/users.js"></script>
</body>
</html>