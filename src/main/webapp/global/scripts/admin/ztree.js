var params;
var Ztree = function() {
	var lay;
	
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
			url:'getZtrees',
			data:params,
			type:'POST',
			dataType:'json',
			success:function(data) {
				var arr = new Array();
				$.each(data.ob, function(i,e) {
					Utils.push(arr, '<tr>');
					Utils.addtd(arr, e.id);
					Utils.addtd(arr, e.pId);
					Utils.addtd(arr, e.node);
					Utils.addtd(arr, '<i class="fa '+e.ico+'"></i> ' + e.ico);
					Utils.addtd(arr, e.name);
					Utils.addtd(arr, e.title);
					Utils.addtd(arr, e.url);
					Utils.push(arr, '<td>');
					Utils.push(arr, '<div class="btn-group btn-group-xs btn-group-solid">');
					Utils.push(arr, '<button class="btn btn-fit-height grey-salt dropdown-toggle" type="button" data-toggle="dropdown">');
					Utils.push(arr, ' <i class="fa fa-cog"></i> 编辑</button>');
					Utils.push(arr, '<ul class="dropdown-menu pull-right">');
					Utils.push(arr, '<li><a class="btn btn-xs" href="javascript:void(0);" onclick="javascript:Ztree.addDlg(this, \'addZtree\')"><i class="fa fa-plus"></i> 添加</a></li>');
					Utils.push(arr, '<li><a class="btn btn-xs" href="javascript:void(0);" onclick="javascript:Ztree.addDlg(this, \'editZtree\')"><i class="fa fa-pencil"></i> 编辑</a></li>');
					Utils.push(arr, '<li><a class="btn btn-xs" href="javascript:void(0);" onclick="javascript:Ztree.delZtree('+e.id+')"><i class="fa fa-times"></i> 删除</a></li>');
					Utils.push(arr, '</ul>');
					Utils.push(arr, '</div>');
					Utils.push(arr, '</td>');
					Utils.push(arr, '</tr>');
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
		                	getDataTable(obj, params);
		                }
		            }
				});
				
				$("#dataBody").html(arr.join(''));
				layer.close(mask);
			}
		});
	}
	
	/**
	 * 选择菜单图标事件
	 * @param ob
	 * @param ico
	 */
	var choiceIco = function (ob, ico) {
		var cho = $(ob).closest("#choico");
		cho.find("#show-ico").html('<i class="fa '+ico+'"></span> <input type="hidden" name="ico" value="'+ico+'">');
		cho.find("button").click(); // 隐藏下拉菜单；
	}
	
	/**
	 * 弹出框
	 */
	var addDlg = function (obj, action) {
		lay = layer.open({
			title: '菜单信息',
			type:1,
			btn:['<i class="fa fa-pencil"></i> 提交保存', '<i class="fa fa-ban"></i> 取消'],
			yes:submitAdd,
			maxmin: true,
			area: '700px',
			content: $("#addZtree")
		});
		
		// 情况form提示，设置提交action
		var addForm = $("#addForm");
		addForm.validate().resetForm();
		addForm.attr('action', action);
		
		// 如果是编辑操作需要对字段赋值
		var tr = $(obj).closest('tr');
		var id = tr.find('td').eq(0).text();
		var pid = tr.find('td').eq(1).text();
		var node = tr.find('td').eq(2).text();
		var ico = tr.find('td').eq(3).text();
		var name = tr.find('td').eq(4).text();
		var title = tr.find('td').eq(5).text();
		var url = tr.find('td').eq(6).text();
		addForm.find('input[name="id"]').val(id);
		addForm.find('input[name="pId"]').val(pid);
		addForm.find('input[name="node"]').val(node);
		addForm.find('input[name="name"]').val(name);
		addForm.find('input[name="title"]').val(title);
		addForm.find('input[name="url"]').val(url);
		addForm.find("#show-ico").html('<i class="fa '+ico+'"></i> <input type="hidden" name="ico" value="'+ico+'">');
		
		if(action == 'addZtree') {
			addForm.find('input[name="id"]').val('');
			addForm.find('input[name="pId"]').val(id);
		}
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
					layer.close(lay);
				} else {
					layer.msg('操作失败:' + data.msg, {time: 20000, btn: ['关闭']});
				}
				layer.close(mask);
			});
		});
	};
	
	var delZtree = function (id) {
		layer.confirm('您是否确定删除？', {btn : ['确定', '取消']}, function() {
			var mask = layer.load(0, {shade: [0.2,'#000']});
			$.post("delZtree", {id : id}, function(data) {
				if (data.res == "success") {
					layer.msg('删除成功!');
					search();
					layer.close(lay);
				} else {
					layer.msg('操作失败:' + data.msg, {time: 20000, btn: ['关闭']});
				}
				layer.close(mask);
			});
		});
	};

	return {
		search:search,					// 查询按钮事件
		getDataTable:getDataTable,		// 渲染table数据
		choiceIco:choiceIco,			// 选择文字图标弹出框
		addDlg:addDlg,					// 新增或者编辑数据
		submitAdd:submitAdd,			// 提交或者新增数据
		delZtree:delZtree				// 删除数据
	}
}();

$(function() {
	// 默认查询事件
	params = {'func':'Ztree.getDataTable', 'pageNum':1, 'pageSize':10};
	Ztree.search();
	
	// title tip 显示效果
	$('.select2-selection').tooltip();
});