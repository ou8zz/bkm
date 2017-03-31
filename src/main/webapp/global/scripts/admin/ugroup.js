var params;
var Ugroup = function() {
	var nowLayer;
	var roleId;
	
	/**
	 * 查询按钮
	 * @param obj
	 */
	var search = function (obj) {
		var searchForm = null;
		if(obj==undefined) searchForm = $("#searchForm");
		else searchForm = $(obj).closest("#searchForm");
		getDataTable(obj, searchForm.serializeJson());
	}
	
	var getDataTable = function (obj, pas) {
		params = $.extend({}, params, pas);
		var mask = layer.load(0, {shade: [0.2,'#000']});
		$.ajax({
			url:'../admin/getUgroups',
			data:params,
			type:'POST',
			dataType:'json',
			success:function(data) {
				var arr = new Array();
				$.each(data.ob, function(i,e) {
					Utils.push(arr, '<tr>');
					Utils.addtd(arr, ++i);
					Utils.addtd(arr, e.ename);
					Utils.addtd(arr, e.cname);
	//				Utils.addtd(arr, e.type);
					Utils.push(arr, '<td><div class="btn-group btn-group-xs btn-group-solid">');
					Utils.push(arr, '<button type="button" class="btn btn-xs" onclick="javascript:Ugroup.addDlg(this, \'editUgroup\', \''+e.id+'\')"><i class="fa fa-pencil"></i></button>');
					if(params.gtype=='ROLE') Utils.push(arr, '<button type="button" class="btn btn-xs" onclick="javascript:Ugroup.confDlg(this, '+e.id+')"><i class="fa fa-cog"></i></button>');
					Utils.push(arr, '<button type="button" class="btn btn-xs" onclick="javascript:Ugroup.delUgroup('+e.id+')"><i class="fa fa-times"></i></button>');
					Utils.push(arr, '</div></td></tr>');
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
		                	getDataTable(obj);
		                }
		            }
				});
				
				$("#dataBody").html(arr.join(''));
				layer.close(mask);
			}
		});
	}
	
	/**
	 * 权限配置弹出框
	 */
	var confDlg = function (obj, rid) {
		nowLayer = layer.open({
			title: '权限配置',
			type:1,
			btn:['<i class="fa fa-pencil"></i> 提交保存', '<i class="fa fa-ban"></i> 取消'],
			yes:saveRoleZtree,
			area: '400px', //宽高
			offset: '100px',
			content: $("#confUgroup")
		});
		
		// ztree权限配置
		var zconf = {
			async : {
				enable : true,
				url : "getRoleZtrees",
				otherParam: ["id", rid, "b", true],
				type : "post",
				dataFilter : function(treeId, parentNode, responseData) {
					// 设置在预览角色权限时点击菜单后从新窗口打开
					$.each(responseData, function(i, e) {
						responseData[i].target = '_blank';
					});
					return responseData;
				}
			},
			check : {enable : true},
			data : {simpleData : {enable : true}},
			callback : {}
		};
		$.fn.zTree.init($("#ztreeConfig"), zconf);
		roleId = rid;
	}
	
	var saveRoleZtree = function () {
		var treeObj = $.fn.zTree.getZTreeObj("ztreeConfig");
		var nodes = treeObj.getCheckedNodes(true);
		var arr = new Array();
		$.each(nodes, function(i, e) {
			arr[i] = e.id;
		});
		
		layer.confirm('您是否确定提交？', {btn : ['确定', '取消']}, function() {
			var mask = layer.load(0, {shade: [0.2,'#000']});
			$.ajaxSettings.traditional=true;
			$.post("saveRoleZtree", {"id":roleId, "zids":arr}, function(data) {
				if (data.res == "success") {
					layer.msg('操作成功!');
					layer.close(nowLayer);
				} else {
					layer.msg('操作失败:' + data.msg, {time: 20000, btn: ['关闭']});
				}
				layer.close(mask);
			});
		});
	}
	
	/**
	 * 弹出框
	 */
	var addDlg = function (obj, action, id) {
		nowLayer = layer.open({
			title: '信息',
			type:1,
			btn:['<i class="fa fa-pencil"></i> 提交保存', '<i class="fa fa-ban"></i> 取消'],
			yes:submitAdd,
			area: '500px',
			content: $("#addUgroup")
		});
		
		// 清空form提示，设置提交action
		var addForm = $('#addForm');
		addForm.validate().resetForm();
		addForm.attr('action', action);
		
		// 如果是编辑操作需要对字段赋值
		var tr = $(obj).closest('tr');
		var ename = tr.find('td').eq(1).text();
		var cname = tr.find('td').eq(2).text();
	//	var type = tr.find('td').eq(3).text();
		addForm.find('input[name="id"]').val(id);
		addForm.find('input[name="ename"]').val(ename);
		addForm.find('input[name="cname"]').val(cname);
	//	addForm.find('input[name="type"]').val(type);
	}
	
	/**
	 * 提交操作
	 * @param obj
	 */
	var submitAdd = function (obj) {
		var addForm = $("#addForm");
		if(!addForm.valid()) return false;
		layer.confirm('您是否确定提交？', {btn : ['确定', '取消']}, function() {
			var mask = layer.load(0, {shade: [0.2,'#000']});
			$.post(addForm.attr('action'), addForm.serialize(), function(data) {
				if (data.res == "success") {
					layer.msg('操作成功!');
					search();
					layer.close(nowLayer);
				} else {
					layer.msg('操作失败:' + data.msg, {time: 20000, btn: ['关闭']});
				}
				layer.close(mask);
			});
		});
	};
	
	var delUgroup = function (id) {
		layer.confirm('您是否确定删除？', {btn : [ '确定', '取消' ]}, function() {
			var mask = layer.load(0, {shade: [0.2,'#000']});
			$.post("delUgroup", {id : id}, function(data) {
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

	return {
		search:search,
		getDataTable:getDataTable,
		confDlg:confDlg,
		saveRoleZtree:saveRoleZtree,
		addDlg:addDlg,
		submitAdd:submitAdd,
		delUgroup:delUgroup
	}
}();

$(function() {
	// 默认查询事件
	params = {'func':'Ugroup.getDataTable', 'pageNum':1, 'pageSize':10};
	Ugroup.search();
});