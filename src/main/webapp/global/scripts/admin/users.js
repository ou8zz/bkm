var params;
var Users = function() {
	var addLayer;
	var trs;
	/**
	 * 查询用户按钮
	 * @param obj
	 */
	var search = function (obj) {
		var searchForm;
		if(obj==undefined) searchForm = $("#searchForm");
		else searchForm = $(obj).closest("#searchForm");
		getDataTable(obj, searchForm.serializeJson());
	}
	
	var getDataTable = function (obj, pas) {
		params = $.extend({}, params, pas);
		var mask = layer.load(0, {shade: [0.2,'#000']});
		$.ajaxSettings.traditional=true;	// 去除参数中数组的[]
		$.ajax({
			url:'users',
			data:params,
			type:'POST',
			dataType:'json',
			success:function(data) {
				trs = data.ob;
				var arr = new Array();
				$.each(data.ob, function(i,e) {
					Utils.push(arr, '<tr>');
					Utils.addtd(arr, '<input type="checkbox" name="che" value='+e.id+'/>');
					Utils.addtd(arr, i+1);
					Utils.addtd(arr, e.ename);
					Utils.addtd(arr, e.cname);
					Utils.addtd(arr, e.cdepartment);
					Utils.addtd(arr, e.crole);
					Utils.push(arr, '<td><div class="btn-group btn-group-xs btn-group-solid">');
					Utils.push(arr, '<button type="button" class="btn btn-xs" onclick="javascript:Users.addDlg(this, \'editUser\', '+i+')"><i class="fa fa-pencil"></i></button>');
					// Utils.push(arr, '<button type="button" class="btn btn-xs" onclick="javascript:Users.delUser('+e.id+')"><i class="fa fa-times"></i></button></div>');
					Utils.push(arr, '</td></tr>');
				});
	
				// Ajax分页
				laypage({
				    cont: $("#ajaxpage"), 		//容器。值支持id名、原生dom对象，jquery对象,
				    pages: data.pages, 			//总页数
				    curr: data.pageNum || 1, 	//当前页
				    total: data.total,			//总条数
				    skip: true, 				//是否开启跳页
				    skin: 'molv', 				//加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
				    groups: 17, 				//连续显示分页数
				    jump: function(ob, first){ 	//触发分页后的回调
		                if(!first){ //点击跳页触发函数自身，并传递当前页：ob.curr
		                	params = $.extend({}, params, {"pageNum":ob.curr});
		                	getDataTable(obj);
		                }
		            }
				});
				$("#dataBody").html(arr.join(''));		// 填充table>tbody
				$('#tog').onTableChecked();				// checkbox全选
				layer.close(mask);						// 关闭遮罩
			}
		});
	}
	
	/**
	 * 弹出框
	 */
	var addDlg = function (obj, action, i) {
		addLayer = layer.open({
			title: '用户信息',
			type:1,
			btn:['<i class="fa fa-pencil"></i> 提交保存', '<i class="fa fa-ban"></i> 取消'],
			yes:submitAdd,
			maxmin: true,
			area: '550px', //宽高
			content: $("#addUser")
		});

		// 获取用户对象赋值表单,如果是新增，直接new一个Object赋空值即可
		var att = trs[i];
		var e = att ? att : new Object();
		
		// 清空form提示，设置提交action
		var addForm = $('#addForm');
		addForm.validate().resetForm();
		addForm.attr('action', action);
		addForm.find('input[name="id"]').val(e.id);
		addForm.find('input[name="ename"]').val(e.ename);
		addForm.find('input[name="cname"]').val(e.cname);
		addForm.find('input[name="email"]').val(e.email);
		addForm.find('input[name="gender"][value='+e.gender+']').closest('.btn').button('toggle');
		addForm.find('input[name="gactive"][value='+e.gactive+']').closest('.btn').button('toggle');
		addForm.find('input[name="locked"][value='+e.locked+']').closest('.btn').button('toggle');
		
		var ugForm = $('#ugForm');
		ugForm.validate().resetForm();
		ugForm.find('input[name="id"]').val(e.id);
		ugForm.find('select[name="dptId"]').val(e.dptId);
		ugForm.find('select[name="roleId"]').val(e.roleId);
		if(e.roleId) {
			confDlg(obj, e.roleId);
		}
		
		// 如果为新增则不显示密码重置表单
		if(action == 'addUser') {
			$('#addUser').find('li a[href="#tab3"]').hide();
		} else {
			$('#addUser').find('li a[href="#tab3"]').show();
			var pwdForm = $('#pwdForm');
			pwdForm.validate().resetForm();
			pwdForm.find('input[name="id"]').val(e.id);
			pwdForm.find('input[name="npwd"]').val('');
			pwdForm.find('input[name="pwd"]').val('');
		}
	}
	
	var submitAdd = function (i, layero) {
		var form = $(layero).find('form:visible');
		if(!form.valid()) return false;
		layer.confirm('您是否确定提交？', {btn : ['确定', '取消']}, function() {
			var mask = layer.load(0, {shade: [0.2,'#000']});
			var url = form.attr('action');
			var param = form.serialize();
			var pwd = form.find("#pwd");
			if(!Utils.isEmpty(pwd.val())) {
				param = {
					'id':form.find('input[name="id"]').val(),
					'pwd': $.md5(pwd.val())
				};
			}
			$.post(url, param, function(data) {
				if (data.res == "success") {
					layer.msg('操作成功!');
					search();
	//				layer.close(addLayer);
				} else {
					layer.msg('操作失败:' + data.msg, {time: 20000, btn: ['关闭']});
				}
				layer.close(mask);
			});
		});
	};
	
	/**
	 * 删除用户操作
	 * @param id
	 */
	var delUser = function (id) {
		if(id == undefined) {
			var ids = new Array();
			$("#dataBody").find("input[name=che]:checked").each(function(i, e){ 
				ids[i] = $(this).val();  
			});
			id = ids.toString();
		}
		if(Utils.isEmpty(id)) {
			layer.msg('请选中要删除的数据!');
			return;
		}
		layer.confirm('您是否确定删除？', {btn : [ '确定', '取消' ]}, function() {
			var mask = layer.load(0, {shade: [0.2,'#000']});
			$.post("delUser", {"id":id}, function(data) {
				if (data.res == "success") {
					layer.msg('删除成功!');
					search();
				} else {
					layer.msg('操作失败:' + data.msg, {time: 20000, btn: ['关闭']});
				}
				layer.close(mask);
			});
		});
	};
	
	/**
	 * 权限预览
	 */
	var confDlg = function (obj, rid) {
		var zconf = {
			async : {
				enable : true,
				url : "getRoleZtrees",
				otherParam: ["id", rid, "b", false],
				type : "post",
				dataFilter : function(treeId, parentNode, responseData) {
					// 设置在预览角色权限时点击菜单后从新窗口打开
					$.each(responseData, function(i, e) {
						responseData[i].target = '_blank';
					});
					return responseData;
				}
			},
			data : {simpleData : {enable : true}},
			callback : {
				onAsyncSuccess : function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
					if(msg=='' || msg == null || msg == '[]') {
						$('#'+treeId).html('<label class="error">该角色没有对应权限</label>');
					}
				}
			}
		};
		$.fn.zTree.init($("#ztreeConfig"), zconf);
	}

	return{
		search:search,				// 查询按钮
		getDataTable:getDataTable,	// 查询用户信息渲染页面table
		addDlg:addDlg,				// 弹出框
		submitAdd:submitAdd,		// 提交新增或者修改用户信息事件
		delUser:delUser,			// 删除用户信息
		confDlg:confDlg				// 权限预览弹出框
	};
}();

$(function() {
	// 默认查询事件
	params = {'func':'Users.getDataTable', 'pageNum':1, 'pageSize':10};
	Users.search();

	// title tip 显示效果
	$('.select2-selection').tooltip();
});
