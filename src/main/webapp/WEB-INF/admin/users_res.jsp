<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/common/header.jsp" flush="true"><jsp:param name="pageTitle" value="用户维护"/></jsp:include>

<!-- PAGE -->
<section style="padding-top:50px;">
	<!-- SIDEBAR -->
	<div id="sidebar" class="sidebar sidebar-fixed">
		<div class="sidebar-menu nav-collapse"><ul id="tree"></ul></div>
	</div>
	<!-- /SIDEBAR -->
	
	<div id="main-content">
		<div class="container">
			<div class="row">
			  <div class="panel panel-default">
			  <div class="panel-heading">
				<ol class="breadcrumb">
				  <li><i class="fa fa-home"></i> <a href="index">首页</a></li><li class="active">用户管理</li>
				</ol>
			  </div>
			  <!-- 查询条件body -->
			  <div class="panel-body">
			    <form id="searchForm" class="form-inline hidden-xs" action="getUsers">
			    <button type="button" class="btn btn-success" onclick="javascript:addDlg(this, 'addUser');"><i class="fa fa-plus"></i> 新增</button>
      			<button type="button" class="btn btn-success" onclick="javascript:delUser();"><i class="fa fa-times"></i> 删除</button>
					<select name="dptIds" class="form-control js-basic-multiple" multiple="multiple" style="width: 30%">
					 	<option value="">全部</option>
		                <c:forEach items="${userDepartment}" var="b" varStatus="i">
		   	 				<option value="${b.id }">${b.cname }</option>
		  				</c:forEach>
		             </select>
				 	 <input type="text" name="ename" class="form-control" placeholder="用户账号">
					 <div class="input-group">
				     <input type="text" name="cname" class="form-control" placeholder="用户名">
				      <span class="input-group-btn">
				        <button class="btn btn-success" type="button" onclick="javascript:searchUser(this);"><i class="fa fa-search"></i></button>
				      </span>
				    </div>
				  </form>
 				</div>

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
				</div>
				<div class="footer-tools"><span class="go-top"><i class="fa fa-chevron-up"></i> Top </span></div>
			</div><!-- /CONTENT-->
		</div>
	</div>

<!-- 用户信息弹出框-->
<div id="addUser" class="collapse" >
   <div>
      <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#tab1" aria-controls="profile" role="tab" data-toggle="tab">基本信息</a></li>
        <li role="presentation"><a href="#tab2" aria-controls="profile" role="tab" data-toggle="tab">部门/角色</a></li>
        <li role="presentation"><a href="#tab3" aria-controls="profile" role="tab" data-toggle="tab">密码修改</a></li>
      </ul>
      <div class="tab-content">
        <div class="tab-pane fade in active" id="tab1">
        	<br/>
           <form class="form-horizontal" role="form" id="addForm" action="editUser">
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
              <label class="col-sm-3 control-label">性别</label>
              <div class="col-sm-8">
                <label class="btn ">
                  <input type="radio" name="gender" value="M"> 男
                </label>
                <label class="btn ">
                  <input type="radio" name="gender" value="F"> 女
                </label>
              </div>
            </div>
            <div class="form-group col-sm-12 text-right">
              <button type="button" onclick="javascript:submitAdd(this);" class="btn btn-primary" ><i class="fa fa-pencil"></i> 提交保存</button>
              <button type="button" onclick="javascript:closeAdd(this);" class="btn btn-danger" ><i class="fa fa-ban"></i> 关闭</button>
            </div>
          </form>
        </div>

        <div class="tab-pane fade" id="tab2">
        <br/>
        <form class="form-horizontal" id="ugForm" action="editUserUgroup">
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
              <select name="roleId" class="form-control" required onchange="javascript:confDlg(this, this.value);">
                <c:forEach items="${userRole}" var="b" varStatus="i">
   	 				<option value="${b.id }">${b.cname }</option>
  				</c:forEach>
              </select>
            </div>
          </div>
          <div class="form-group col-sm-12">
            <label class="col-sm-3 control-label">权限预览</label>
          	<div class="col-sm-8 panel panel-default">
			    <div class="panel-bd">
					<ul id="ztreeConfig" class="ztree"></ul>
				</div>
			</div>
          </div>
          <div class="form-group col-sm-12 text-right">
              <button type="button" onclick="javascript:submitAdd(this);" class="btn btn-primary" ><i class="fa fa-pencil"></i> 提交保存</button>
              <button type="button" onclick="javascript:closeAdd(this);" class="btn btn-danger" ><i class="fa fa-ban"></i> 关闭</button>
           </div>
          </form>
        </div>

        <div class="tab-pane fade" id="tab3">
        <br/>
        <form class="form-horizontal" id="updatePwdForm" action="editUser">
        <input type="hidden" name="id">
          <div class="form-group col-sm-12">
            <label class="col-sm-3 control-label">原密码</label>
            <div class="col-sm-8"><input type="text" name="pwd" class="form-control" required></div>
          </div>
          <div class="form-group col-sm-12">
            <label class="col-sm-3 control-label">新密码</label>
            <div class="col-sm-8"><input type="text" name="npwd" id="npwd" class="form-control" required minlength="6"></div>
          </div>
          <div class="form-group col-sm-12">
            <label class="col-sm-3 control-label">重复密码</label>
            <div class="col-sm-8"><input type="text" name="cpwd" class="form-control" required equalTo="#npwd"></div>
          </div>
          <div class="form-group col-sm-12 text-right">
              <button type="button" onclick="javascript:submitAdd(this);" class="btn btn-primary" ><i class="fa fa-pencil"></i> 提交保存</button>
              <button type="button" onclick="javascript:closeAdd(this);" class="btn btn-danger" ><i class="fa fa-ban"></i> 关闭</button>
            </div>
        </form>
        </div>
      </div>
    </div>
</div>
</section>
<!--/PAGE -->
	
<%@include file="/common/script.html" %>
<script type="text/javascript" src="../res/js/jquery.validation.min.js"></script>
<script type="text/javascript" src="../res/js/admin/users.js"></script>

<script src="../res/js/script.js"></script>
	<script>
		jQuery(document).ready(function() {		
			App.setPage("fixed_header_sidebar");  //Set current page
			App.init(); //Initialise plugins and elements
		});
	</script>
</body>
</html>