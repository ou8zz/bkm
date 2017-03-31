var params;
var funds;
var cfunds;
/**
 * 基金经理维护
 */
var FundManagers = function() {
	var lay;
	var trs = new Object();		// 在点击修改和预览数据时保存的对象
	
	var search = function (obj) {
		var searchForm = null;
		if(obj==undefined) searchForm = $('#fm').find("#searchForm");
		else searchForm = $(obj).closest("#searchForm");
		var json = searchForm.serializeJson();
		getDataTable(obj, json);
	};
	
	var getDataTable = function (obj, pas) {
		var fm = $('#fm');
		var tbody = fm.find("#dataBody");
		var tfoot = fm.find("#ajaxpage");
		var mask = layer.load(0, {shade: [0.2,'#000']});	// 遮罩
		params = $.extend({}, params, pas);
		$.ajaxSettings.traditional=true;					// 去除参数中数组的[]
		$.ajax({
			url:'../audit/getFundManagers',
			data:params,
			type:'POST',
			dataType:'json',
			success:function(data) {
				trs = data.ob;								// 保存好实体对象后再修改和预览时可以直接取值
				var arr = new Array();
				$.each(data.ob, function(i,e) {
					Utils.push(arr, '<tr>');
					Utils.addtd(arr, i+1);
					Utils.addtd(arr, e.cname);
					Utils.addtd(arr, e.ctypeDes);
					Utils.addtd(arr, e.itime);
					Utils.addtd(arr, e.otime);
					Utils.push(arr, '<td>');
					Utils.push(arr, '<div class="btn-group btn-group-xs btn-group-solid">');
					Utils.push(arr, '<button class="btn btn-fit-height grey-salt dropdown-toggle" type="button" data-toggle="dropdown">');
					Utils.push(arr, ' <i class="fa fa-cog"></i> 编辑</button>');
					Utils.push(arr, '<ul class="dropdown-menu pull-right">');
					Utils.push(arr, '<li><a class="btn btn-xs" href="javascript:void(0);" onclick="javascript:FundManagers.addDlg(this, \'editFundManagers\', '+e.id+', '+i+')"><i class="fa fa-pencil"></i> 编辑</a></li>');
					Utils.push(arr, '<li><a class="btn btn-xs" href="javascript:void(0);" onclick="javascript:FundManagers.del('+e.id+')"><i class="fa fa-times"></i> 删除</a></li>');
					Utils.push(arr, '</ul>');
					Utils.push(arr, '</div>');
					Utils.push(arr, '</td>');
					Utils.push(arr, '</tr>');
				});

				// Ajax分页
				laypage({
				    cont: tfoot, 				//容器。值支持id名、原生dom对象，jquery对象,
				    pages: data.pages, 			//总页数
				    curr: data.pageNum || 1, 	//当前页
				    total: data.total,			//总条数
				    skip: true, 				//是否开启跳页
				    skin: 'molv', 				//加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
				    groups: 10, 				//连续显示分页数
				    jump: function(ob, first){ 	//触发分页后的回调
		                if(!first){ 			//点击跳页触发函数自身，并传递当前页：ob.curr
		                	// 分页后回调，设置下次分页参数和调用函数
		                	params = $.extend({}, params, {"pageNum":ob.curr});
		                	getDataTable(obj, params);
		                }
		            }
				});
				
				// 初始化列表和summernote编辑器插件
				tbody.html(arr.join(''));		// 填充页面内容
				layer.close(mask);				// 关闭遮罩
			}
		});
	};
	
	var addDlg = function (obj, action, uid, i) {
		var dlg = $('#addDlg');
		
		// 如果只是新增就取消基金信息的标签页
		if(uid) dlg.find('li a[href="#fundInfo"]').show();
		else dlg.find('li a[href="#fundInfo"]').hide();
		dlg.find('li a[href="#fundManager"]').click();
		
		lay = layer.open({
			title: '基金管理信息',
			type:1,
			btn:['<i class="fa fa-pencil"></i> 提交保存', '<i class="fa fa-ban"></i> 取消'],
			yes:submitAdd,
			maxmin: true,
			area: '800px',
			content: dlg
		});
		
		// 获取用户对象赋值表单,如果是新增，直接new一个Object赋空值即可
		var att = trs[i];
		var e = att ? att : new Object();
		var addForm = dlg.find('#addForm');
		addForm.validate().resetForm();
		addForm.attr('action', action);
		addForm.find('input[name="id"]').val(e.id);
		addForm.find('input[name="ename"]').val(e.ename);
		addForm.find('input[name="cname"]').val(e.cname);
		addForm.find('input[name="ctype"]').val(e.ctype);
		addForm.find('input[name="itime"]').val(e.itime);
		addForm.find('input[name="otime"]').val(e.otime);
		addForm.find('textarea[name="resume"]').summernote('code', Utils.isEmpty(e.resume)?'':e.resume);
		
		// 设置基金管理人ID
		var fundCheck = dlg.find('#fundCheck');
		fundCheck.find('input[name="userid"]').val(uid);
		
		// 初始化基金管理人员对应组合的查询条件和查询事件
		var searchForm = dlg.find('#searchForm');
		searchForm.find('select[name="userid"]').find('#suid').val(uid);
		FundInfo.searchFundInfo();
	};
	
	var submitAdd = function (obj) {
		var addDlg = $('#addDlg');
		var addForm = addDlg.find('#addForm');
		// 新增或修改基金管理人信息 对应参数
		if(addForm.is(':visible')) {
			if(!addForm.valid()) {
				return false;
			}
		} 
		// 修改基金管理人对应组合 对应参数
		else {
			var ids = new Array();
			addDlg.find("#dataBody").find("input[name=ids]:checked").each(function(i, e){
				ids[i] = $(this).val();
			});
			id = ids.toString();
			if(Utils.isEmpty(id)) {
				layer.msg('请选择要操作的项目！');
				return false;
			}
			
			// 赋值当前标签页的form表单和提交的action
			addForm = addDlg.find('#fundCheck');
			addForm.attr('action', 'editFundInfo');
		}
		layer.confirm('您是否确定提交？', {btn : ['确定', '取消']}, function() {
			var mask = layer.load(0, {shade: [0.2,'#000']});
			$.post(addForm.attr('action'), addForm.serialize(), function(data) {
				if (data.res == "success") {
					layer.msg('操作成功!');
					
					// 如果是修改基金管理人信息，修改后设置刷新基金管理人界面关闭窗口，否则是修改基金管理人对应组合，修改后刷新对应组合的界面即可
					if(addDlg.find('#addForm').is(':visible')) {
						search();
						layer.close(lay);
					} else {
						FundInfo.searchFundInfo();
					}
				} else {
					layer.msg('操作失败:' + data.msg, {time: 20000, btn: ['关闭']});
				}
				layer.close(mask);
			});
		});
	};
	
	var del = function (id) {
		var addDlg = $('#addDlg');
		if(id == undefined) {
			var ids = new Array();
			addDlg.find("#dataBody").find("input[name=ids]:checked").each(function(i, e){
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
			$.post("delFundManagers", {"id":id}, function(data) {
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
	
	var summernote = function (obj) {
		if(typeof obj === 'string') {
			obj = $('#'+obj);
		}
		var content = obj.find('textarea.content');
		// 如果没有textarea.note-codable表示没有创建编辑器，如果有就不用创建了
		if(obj.find('textarea.note-codable').length == 0) {
			content.summernote({
				lang:'zh-CN',
				height:$(document).height()-580,
				placeholder:'简历',
				toolbar: [
		          ['style', ['style', 'bold', 'italic', 'underline', 'clear']],
		          ['font', ['strikethrough', 'superscript', 'subscript', 'hr']],
		          ['fontsize', ['fontsize', 'color']],
		          ['para', ['ul', 'ol', 'paragraph', 'height', 'table']],
		          ['codeview', ['undo', 'redo', 'codeview']],
		          ['fullscreen', ['fullscreen']]
		          ]
			});
		}
	};
	
	return {
		search:search,				// 查询按钮事件
		getDataTable:getDataTable,	// 渲染table数据
		addDlg:addDlg,				// 新增弹出窗
		submitAdd:submitAdd,		// 提交新增事件
		del:del,					// 删除事件
		summernote:summernote		// 新增弹出框init编辑器(该插件中的hint和jquery validate冲突)
	};
}();

/**
 * 基金基本信息维护
 */
var FundInfo = function() {
	var lay;
	var search = function (obj) {
		var searchForm = null;
		if(obj==undefined) searchForm = $('#fi').find("#searchForm");
		else searchForm = $(obj).closest("#searchForm");
		var json = searchForm.serializeJson();
		getDataTable(obj, json);
	};
	
	var getDataTable = function (obj, pas) {
		var fi = $('#fi');
		var tbody = fi.find("#dataBody");
		var tfoot = fi.find("#ajaxpage");
		funds = $.extend({}, funds, pas);
		var mask = layer.load(0, {shade: [0.2,'#000']});	// 遮罩
		$.ajaxSettings.traditional=true;					// 去除参数中数组的[]
		$.ajax({
			url:'../audit/getFundInfo',
			data:funds,
			type:'POST',
			dataType:'json',
			success:function(data) {
				var arr = new Array();
				$.each(data.ob, function(i,e) {
					Utils.push(arr, '<tr att=\''+JSON.stringify(e)+'\'>');
					Utils.addtd(arr, i+1);
					Utils.addtd(arr, e.fcode);
					Utils.addtd(arr, e.fname);
					Utils.addtd(arr, e.uname);
					Utils.push(arr, '<td>');
					Utils.push(arr, '<div class="btn-group btn-group-xs btn-group-solid">');
					Utils.push(arr, '<button class="btn btn-fit-height grey-salt dropdown-toggle" type="button" data-toggle="dropdown">');
					Utils.push(arr, ' <i class="fa fa-cog"></i> 编辑</button>');
					Utils.push(arr, '<ul class="dropdown-menu pull-right">');
					Utils.push(arr, '<li><a class="btn btn-xs" href="javascript:void(0);" onclick="javascript:FundInfo.addDlg(this, \'editFundInfo\')"><i class="fa fa-pencil"></i> 编辑</a></li>');
					Utils.push(arr, '<li><a class="btn btn-xs" href="javascript:void(0);" onclick="javascript:FundInfo.del('+e.id+')"><i class="fa fa-times"></i> 删除</a></li>');
					Utils.push(arr, '</ul>');
					Utils.push(arr, '</div>');
					Utils.push(arr, '</td>');
					Utils.push(arr, '</tr>');
				});

				// Ajax分页
				laypage({
				    cont: tfoot, 				//容器。值支持id名、原生dom对象，jquery对象,
				    pages: data.pages, 			//总页数
				    curr: data.pageNum || 1, 	//当前页
				    total: data.total,			//总条数
				    skip: true, 				//是否开启跳页
				    skin: 'molv', 				//加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
				    groups: 10, 				//连续显示分页数
				    jump: function(ob, first){ 	//触发分页后的回调
		                if(!first){ 			//点击跳页触发函数自身，并传递当前页：ob.curr
		                	// 分页后回调，设置下次分页参数和调用函数
		                	funds = $.extend({}, funds, {"pageNum":ob.curr});
		                	getDataTable(obj, funds);
		                }
		            }
				});
				
				// 初始化列表和summernote编辑器插件
				tbody.html(arr.join(''));		// 填充页面内容
				layer.close(mask);				// 关闭遮罩
			}
		});
	};
	
	var searchFundInfo = function (obj) {
		var searchForm = null;
		if(obj==undefined) searchForm = $('#fundInfo').find("#searchForm");
		else searchForm = $(obj).closest("#searchForm");
		var json = searchForm.serializeJson();
		getFundInfoTable(obj, json);
	};
	
	var getFundInfoTable = function (obj, pas) {
		var fundInfo = $('#fundInfo');
		var tbody = fundInfo.find("#dataBody");
		var tfoot = fundInfo.find("#ajaxpage");
		cfunds = $.extend({}, cfunds, pas);
		var mask = layer.load(0, {shade: [0.2,'#000']});	// 遮罩
		$.ajaxSettings.traditional=true;					// 去除参数中数组的[]
		$.ajax({
			url:'../audit/getFundInfo',
			data:cfunds,
			type:'POST',
			dataType:'json',
			success:function(data) {
				var arr = new Array();
				// 根据弹出框在弹出时赋值的用户ID获取到当前行的基金经理ID进行比较，如果有属于该基金经理的组合则默认checked
				var uid = fundInfo.find('input[name="userid"]').val();
				$.each(data.ob, function(i,e) {
					var checked = (uid==e.userid)?'checked':'';
					Utils.push(arr, '<tr att=\''+JSON.stringify(e)+'\'>');
					Utils.addtd(arr, '<input type="checkbox" name="ids" value='+e.id+' '+checked+'>');
					Utils.addtd(arr, i+1);
					Utils.addtd(arr, e.fcode);
					Utils.addtd(arr, e.fname);
					Utils.addtd(arr, e.uname);
					Utils.push(arr, '</tr>');
				});

				// Ajax分页
				laypage({
				    cont: tfoot, 						//容器。值支持id名、原生dom对象，jquery对象,
				    pages: data.pages, 					//总页数
				    curr: data.pageNum || 1, 			//当前页
				    total: data.total,					//总条数
				    skip: true, 						//是否开启跳页
				    skin: 'molv', 						//加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
				    groups: 10, 						//连续显示分页数
				    jump: function(ob, first){ 			//触发分页后的回调
		                if(!first){ 					//点击跳页触发函数自身，并传递当前页：ob.curr
		                	// 分页后回调，设置下次分页参数和调用函数
		                	cfunds = $.extend({}, cfunds, {"pageNum":ob.curr});
		                	getFundInfoTable(obj, cfunds);
		                }
		            }
				});
				
				tbody.html(arr.join(''));					// 填充页面内容
				fundInfo.find('#tog').onTableChecked();		// 点击全选
				layer.close(mask);							// 关闭遮罩
			}
		});
	};
	
	var addDlg = function (obj, action) {
		lay = layer.open({
			title: '基金信息',
			type:1,
			btn:['<i class="fa fa-pencil"></i> 提交保存', '<i class="fa fa-ban"></i> 取消'],
			yes:submitAdd,
			maxmin: true,
			area: '500px',
			content: $("#addDlgFi")
		});
		
		// 获取用户对象赋值表单,如果是新增，直接new一个Object赋空值即可
		var att = $(obj).closest('tr').attr('att');
		var e = att ? JSON.parse(att) : new Object();
		
		var addFundForm = $('#addFundForm');
		addFundForm.validate().resetForm();
		addFundForm.attr('action', action);
		addFundForm.find('input[name="id"]').val(e.id);
		addFundForm.find('input[name="fcode"]').val(e.fcode);
		addFundForm.find('input[name="fname"]').val(e.fname);
	};
	
	var submitAdd = function (obj) {
		var addForm = $('#addFundForm');
		if(!addForm.valid()) {
			return false;
		}
		layer.confirm('您是否确定提交？', {btn : ['确定', '取消']}, function() {
			var mask = layer.load(0, {shade: [0.2,'#000']});
			$.post(addForm.attr('action'), addForm.serialize(), function(data) {
				if (data.res == "success") {
					layer.msg('操作成功!');
					layer.close(lay);
					search();
				} else {
					layer.msg('操作失败:' + data.msg, {time: 20000, btn: ['关闭']});
				}
				layer.close(mask);
			});
		});
	};
	
	var del = function (id) {
		if(id == undefined) {
			var ids = new Array();
			$("#dataBodyFi").find("input[name=che]:checked").each(function(i, e){
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
			$.post("delFundInfo", {"id":id}, function(data) {
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
		search:search,						// 查询按钮事件
		getDataTable:getDataTable,			// 渲染table数据
		searchFundInfo:searchFundInfo,		// 基金经理选择基金查询按钮
		getFundInfoTable:getFundInfoTable,	// 基金经理选择基金列表
		addDlg:addDlg,						// 新增弹出窗
		submitAdd:submitAdd,				// 提交新增事件
		del:del								// 删除事件
	};
}();

$(function() {
	// 默认查询事件
	params = {'func':'FundManagers.getDataTable', 'pageNum':1, 'pageSize':10};
	funds = {'func':'FundInfo.getDataTable', 'pageNum':1, 'pageSize':10};
	cfunds = {'func':'FundInfo.getFundInfoTable', 'pageNum':1, 'pageSize':10};
	
	// 基金管理人查询
	FundManagers.search();
	FundManagers.summernote($('#addDlg'));		// 初始化弹出框中的编辑器
	
	// 基金信息查询
	FundInfo.search();
});

