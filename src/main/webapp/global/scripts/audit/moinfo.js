var params;
var Moinfo = function() {
	var nowLayer;
	var roleId;
	
	/**
	 * 查询按钮
	 * @param obj
	 */
	var search = function (obj) {
		var searchForm = null;
		if(obj==undefined) searchForm = $('#searchForm');
		else searchForm = $(obj).closest('#searchForm');
		getDataTable(obj, searchForm.serializeJson());
	};
	
	var getDataTable = function (obj, pas) {
		params = $.extend({}, params, pas);
		var mask = layer.load(0, {shade: [0.2,'#000']});
		$.ajax({
			url:'getMoinfo',
			data:params,
			type:'POST',
			dataType:'json',
			success:function(data) {
				var arr = new Array();
				$.each(data.ob, function(i,e) {
					Utils.push(arr, '<tr>');
					Utils.addtd(arr, ++i);
					Utils.addtd(arr, e.cname, 'cname');
					if(params.ctype=='BROKERAGE') Utils.addtd(arr, e.orgtype, 'orgtype');
					Utils.addtd(arr, e.addr, 'addr');
					Utils.addtd(arr, e.contacts, 'contacts');
					Utils.addtd(arr, e.cnumber, 'cnumber');
					Utils.push(arr, '<td><div class="btn-group btn-group-xs btn-group-solid">');
					Utils.push(arr, '<button type="button" class="btn btn-xs" onclick="javascript:Moinfo.addDlg(this, \'editMoinfo\', \''+e.id+'\')"><i class="fa fa-pencil"></i></button>');
					Utils.push(arr, '<button type="button" class="btn btn-xs" onclick="javascript:Moinfo.del('+e.id+')"><i class="fa fa-times"></i></button>');
					Utils.push(arr, '</div></td></tr>');
				});
				
				// Ajax分页
				laypage({
				    cont: $('#ajaxpage'), 		//容器。值支持id名、原生dom对象，jquery对象,
				    pages: data.pages, 			//总页数
				    curr: data.pageNum || 1, 	//当前页
				    total: data.total,			//总条数
				    skip: true, 				//是否开启跳页
				    skin: 'molv', 				//加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
				    groups: 7, 					//连续显示分页数
				    jump: function(ob, first){ 	//触发分页后的回调
		                if(!first){ //点击跳页触发函数自身，并传递当前页：ob.curr
		                	params = $.extend({}, params, {'pageNum':ob.curr});
		                	getDataTable(obj);
		                }
		            }
				});
				
				$('#dataBody').html(arr.join(''));
				layer.close(mask);
			}
		});
	};
	
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
			content: $('#addDlg')
		});
		
		// 清空form提示，设置提交action
		var addForm = $('#addForm');
		addForm.validate().resetForm();
		addForm.find('input:visible, textarea:visible').val('');	// 只清空显示表单，隐藏的属性不要清空
		addForm.attr('action', action);
		
		// 如果是编辑操作需要对字段赋值
		addForm.find('input[name="id"]').val(id);
		$(obj).closest('tr').find('td').each(function(i, e) {
			var v = $(e);
			addForm.find('[name="'+v.attr('att')+'"]').val(v.text());
		});
	};
	
	/**
	 * 提交操作
	 * @param obj
	 */
	var submitAdd = function (obj) {
		var addForm = $('#addForm');
		if(!addForm.valid()) return false;
		layer.confirm('您是否确定提交？', {btn : ['确定', '取消']}, function() {
			var mask = layer.load(0, {shade: [0.2,'#000']});
			$.post(addForm.attr('action'), addForm.serialize(), function(data) {
				if (data.res == 'success') {
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
	
	var del = function (id) {
		layer.confirm('您是否确定删除？', {btn : [ '确定', '取消' ]}, function() {
			var mask = layer.load(0, {shade: [0.2,'#000']});
			$.post('delMoinfo', {id : id}, function(data) {
				if (data.res == 'success') {
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
		addDlg:addDlg,
		submitAdd:submitAdd,
		del:del
	};
}();

$(function() {
	// 默认查询事件
	params = {'func':'Moinfo.getDataTable', 'pageNum':1, 'pageSize':10};
	Moinfo.search();
});