var params;
$(function() {
	// 默认查询事件
	params = {'func':'getUsers', 'pageNum':1, 'pageSize':10};
	searchUser();
	
});

/**
 * 查询用户按钮
 * @param obj
 */
function searchUser(obj) {
	var searchForm;
	if(obj==undefined) searchForm = $("#searchForm");
	else searchForm = $(obj).closest("#searchForm");
	getUsers(obj, searchForm.serializeJson());
}

function getUsers(obj, pas) {
	params = $.extend({}, params, pas);
	var mask = layer.load(0, {shade: [0.2,'#000']});
	$.ajax({
		url:'users',
		data:params,
		type:'POST',
		dataType:'json',
		success:function(data) {
			var arr = new Array();
			var dataBody = $("#dataBody").empty();
			$.each(data.ob, function(i,e) {
				arr.push('<tr>');
				add_td(arr, '<input type="checkbox" name="che" value='+e.id+' att=\''+JSON.stringify(e)+'\'/>');
				add_td(arr, ++i);
				add_td(arr, e.ename);
				add_td(arr, e.cname);
				add_td(arr, e.cdepartment);
				add_td(arr, e.crole);
				var li = '<li><a href="javascript:void(0)" onclick="javascript:addDlg(this, \'editUser\')">1. 编辑</a></li>';
				li += '<li><a href="javascript:void(0)" onclick="javascript:delUser('+e.id+')">2. 删除</a></li>';
				add_td(arr, add_dropdown(li));
				arr.push('</tr>');
			});

			// Ajax分页
			laypage({
			    cont: $("#ajaxpage"), 		//容器。值支持id名、原生dom对象，jquery对象,
			    pages: data.pages, 			//总页数
			    curr: data.pageNum || 1, 	//当前页
			    total: data.total,			//总条数
			    skip: true, 				//是否开启跳页
			    skin: 'molv', 				//加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    groups: 7, 					//连续显示分页数
			    jump: function(ob, first){ 	//触发分页后的回调
	                if(!first){ //点击跳页触发函数自身，并传递当前页：ob.curr
	                	params = $.extend({}, params, {"pageNum":ob.curr});
	                	getUsers(obj);
	                }
	            }
			});
			
			$("#dataBody").html(arr.join(''));
			$(".am-dropdown").dropdown({justify: '#doc-dropdown-justify-js'}); // 激活下拉功能；
			layer.close(mask);
		}
	});
}

/**
 * 弹出框
 */
var addLayer;
function addDlg(obj, action) {
	addLayer = layer.open({
		title: '用户信息',
		type:1,
		maxmin: true,
		area: '600px', //宽高
		content: $("#addUser")
	});
	
	// 获取用户对象赋值表单,如果是新增，直接new一个Object赋空值即可
	var att = $(obj).closest('tr').find('input[name=che]').attr('att');
	var e = att ? JSON.parse(att) : new Object();
	
	// 清空form提示，设置提交action
	var addForm = $('#addForm');
	addForm.validate().resetForm();
	addForm.attr('action', action);
	
	var dptForm = $('#dptForm');
	dptForm.validate().resetForm();
	dptForm.attr('action', action);
	dptForm.find('input[name="id"]').val(e.id);
	dptForm.find('select[name="dptId"]').val(e.dptId);
	
	var roleForm = $('#roleForm');
	roleForm.validate().resetForm();
	roleForm.attr('action', action);
	roleForm.find('input[name="id"]').val(e.id);
	roleForm.find('select[name="roleId"]').val(e.roleId);
	
	addForm.find('input[name="id"]').val(e.id);
	addForm.find('input[name="ename"]').val(e.ename);
	addForm.find('input[name="cname"]').val(e.cname);
	addForm.find('input[name="email"]').val(e.email);
	addForm.find('input[type="radio"][value='+e.gender+']').uCheck('check');
	
	// 激活select插件
	$('#type').selected('destroy'); //: 销毁实例
	$('#type').selected({
//	    btnWidth: '180px',
	    btnSize: 'sm',
	    btnStyle: 'primary',
	    maxHeight: '120px'
	});
}

function submitAdd(obj) {
	var addUser = $(obj).closest("#addUser");
	var form = addUser.find("div.am-active").find("form");
	if(!form.valid()) return false;
	layer.confirm('您是否确定提交？', {btn : ['确定', '取消']}, function() {
		var mask = layer.load(0, {shade: [0.2,'#000']});
		$.post(form.attr('action'), form.serialize(), function(data) {
			if (data.res == "success") {
				layer.msg('操作成功!');
				searchUser();
//				layer.close(addLayer);
			} else {
				layer.msg('操作失败:' + data.msg, {time: 20000, btn: ['关闭']});
			}
			layer.close(mask);
		});
	});
};


function delUser(id) {
	layer.confirm('您是否确定删除？', {btn : [ '确定', '取消' ]}, function() {
		var mask = layer.load(0, {shade: [0.2,'#000']});
		$.post("delUgroup/"+id, function(data) {
			if (data.res == "success") {
				layer.msg('删除成功!');
				searchUgroup();
			} else {
				layer.msg('操作失败:' + data.msg, {time: 20000, btn: ['关闭']});
			}
			layer.close(mask);
		});
	});
};