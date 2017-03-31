$(function() {
	
	// 初始化ztree菜单
	$.fn.zTree.init($("#treeMenu"), setting);

	
	// 默认设置ztree菜单样式风格
	zTree_Menu = $.fn.zTree.getZTreeObj("treeMenu");

});

var setting = {
	async : {
		enable : true,
		url : "../admin/ztree",
		type : "get"
	},
	check: {
		enable: false
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback: {
		onNodeCreated: function(event, treeId, treeNode) {
//			alert(treeNode.tId + ", " + treeNode.name + ", " + treeNode.ico + " , ");
			// 遍历所有节点更新菜单图标
			$("#" + treeNode.tId + "_a").find("span:first").addClass(treeNode.ico + " am-margin-left-sm spse");
		},
		beforeClick: function(treeId, node) {
			if (node.isParent) {
				// ztree如果是父节点就可以点击菜单名称进行收缩
				zTree_Menu.expandNode(node);
				
				// 如果点击也需要更新菜单图标样式，毕竟不是原生的，需要刷新css
				$("#" + node.tId + "_a").find("span:first").addClass(node.ico + " am-margin-left-sm spse");
			}
			$("#treeMenu").html();
			return !node.isParent;
		},
		onAsyncSuccess:function(event, treeId, treeNode, msg) {
			// 异步加载菜单数据成功后对菜单样式进行覆盖AmazeUI的样式
			$("#treeMenu").find("li").addClass("am-panel admin-parent").find("ul").addClass("am-list am-collapse admin-sidebar-sub am-in spse");
		}
	}
};

function getUsers() {
	$.ajax({
		url:'user',
		type:'GET',
		dataType:'json',
		complete:function(data, textStatus) {
//			alert($.toJSON(data.responseText) + " 返回消息内容：" + data.responseText);
		},
		success:function(data) {
			var dataBody = $("#dataBody").empty();
			$.each(data, function(i,e) {
				var tr = $("<tr></tr>");
				tr.append("<td>"+e.id+"</td>");
				tr.append("<td>"+e.ename+"</td>");
				tr.append("<td>"+e.cname+"</td>");
				tr.append("<td>"+e.age+"</td>");
				tr.append("<td>"+e.birthday+"</td>");
				tr.append('<td><a class="edit">编辑</a></td>');
				tr.append('<td><a class="delete" href="user/${u.id}">删除</a></td>');
				tr.appendTo(dataBody);
			});
		}
	});
}

function addUser(obj) {
	var addForm = $("#addForm");
	var url = "user";
	var param = {
		'id':addForm.find("#id").val(),
		'ename':addForm.find("#ename").val(),
		'cname':addForm.find("#cname").val(),
		'age':addForm.find("#age").val(),
		'birthday':addForm.find("#birthday").val()
//		'_method':'PUT'
	};
//	$.post(url, param, function(data){
//		alert(data);
//	},'json');
	
	$.ajax({
		url:url,
		data:param,
		type:'PUT',
		dataType:'json',
		complete:function(data) {
			alert(JSON.stringify(data) + " 返回消息内容：" + data.responseText);
		}
	});
};

function editUser(obj) {
	var addForm = $("#addForm");
	var url = "user";
	var param = {
		'id':addForm.find("#id").val(),
		'ename':addForm.find("#ename").val(),
		'cname':addForm.find("#cname").val(),
		'age':addForm.find("#age").val(),
		'birthday':addForm.find("#birthday").val()
//		'_method':'POST'
	};
//	$.post(url, param, function(data){
//		alert(data);
//	},'json');
	
	$.ajax({
		url:url,
		data:param,
		type:'POST',
		dataType:'json',
		complete:function(data) {
			if(data.status==200) {
				alert(JSON.stringify(data) + " 返回消息内容：" + data.responseText);
			}
			
		}
	});
};

function subHello2(obj) {
	var addForm = $("#addForm");
	var url = "user.htm";
	var param = {
		'id':addForm.find("#id").val(),
		'ename':addForm.find("#ename").val(),
		'cname':addForm.find("#cname").val()
	};
	$.post(url, param, function(data){
		alert(data);
	},'json');
};
