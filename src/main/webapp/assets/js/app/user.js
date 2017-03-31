$(function() {
	$(".delete").click(function() {
		var url = $(this).attr("href");
//		$("#utForm").attr("action", url).submit();
//		$.post(url, {'_method':'DELETE'}, function(data) {
//			alert(data);
//		});
		$.ajax({
			url:url,
			type:'DELETE',
			dataType:'json',
			complete:function(data) {
				alert(JSON.stringify(data) + " 返回消息内容：" + data.responseText);
			}
		});
		return false;
	});
	
	
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
});

var setting = {
	view: {
		selectedMulti: false
	},
	check: {
		enable: true
	},
	data: {
		simpleData: {
			enable: true
		}
	}
};

var zNodes =[
	{ id:1, pId:0, name:"随意勾选 1", open:true},
	{ id:11, pId:1, name:"随意勾选 1-1"},
	{ id:12, pId:1, name:"随意勾选  1-2", open:true},
	{ id:121, pId:12, name:"随意勾选 1-2-1", checked:true},
	{ id:122, pId:12, name:"随意勾选 1-2-2"},
	{ id:123, pId:12, name:"随意勾选 1-2-3"},
	{ id:13, pId:1, name:"随意勾选 1-3"},
	{ id:2, pId:0, name:"随意勾选 2", open:true},
	{ id:21, pId:2, name:"随意勾选 2-1"},
	{ id:22, pId:2, name:"随意勾选 2-2", open:true},
	{ id:221, pId:22, name:"随意勾选 2-2-1", checked:true},
	{ id:222, pId:22, name:"随意勾选 2-2-2"},
	{ id:223, pId:22, name:"随意勾选 2-2-3"},
	{ id:23, pId:2, name:"随意勾选 2-3", checked:true}
];

function getZtreeId(obj) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	var checkCount = zTree.getCheckedNodes(true).length;
	var nocheckCount = zTree.getCheckedNodes(false).length;
	var changeCount = zTree.getChangeCheckedNodes().length;
	var nodes = zTree.getCheckedNodes(true);
	$.each(nodes, function(i, e) {
		alert(e.id);
	});
//	alert(JSON.stringify(nodes));
}

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
