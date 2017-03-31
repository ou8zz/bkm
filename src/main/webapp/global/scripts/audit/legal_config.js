var params;
var LegalConfig = function() {
	var lay;
	var trs;			// 在点击修改和预览数据时保存的对象
	var treeMenu;		// ztreeDOM元素
	var zTree_Menu;		// ztree Object
	var open = false;	// 默认tree.open=false
	var setting = {
			async : {
				enable : true,
				url : "../audit/getLegalTrees",
				type : "post",
				dataFilter : function(treeId, parentNode, responseData) {
					// 设置默认ztree父节点是否展开
					$.each(responseData, function(i, e) {
						responseData[i].open = open;
					});
					return responseData;
				}
			},
			data : {
				simpleData : {enable : true}
			},
			view: {
				dblClickExpand: false,
				showIcon: true,
				addHoverDom: function(treeId, treeNode) {
					// 新增树节点
					var sObj = $("#" + treeNode.tId + "_span");
					if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
					var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='添加新节点' onfocus='this.blur();'></span>";
					sObj.after(addStr);
					var btn = $("#addBtn_"+treeNode.tId);
					if (btn) btn.bind("click", function(){
						tn = treeNode;
						addDlg(treeNode, 'addLegalTree', treeNode.id, treeNode.name);
					});
				},
				removeHoverDom: function(treeId, treeNode) {
					// 鼠标移开同时也移除add图标
					$("#addBtn_"+treeNode.tId).unbind().remove();
				}
			},
			edit: {
				enable: true,
				removeTitle:'删除节点',
				renameTitle:'修改节点',
				showRemoveBtn: true,
				showRenameBtn: true,
				drag:{
					isCopy:false,
					isMove:false
				}
			},
			callback : {
				beforeEditName: function(treeId, treeNode) {
					// 修改树节点名称
					tn = treeNode;
					addDlg(treeNode, 'editLegalTree', treeNode.id, treeNode.name, treeNode.pId);
				},
				beforeRemove: function(treeId, treeNode) {
					// 删除树节点
					var zTree = $.fn.zTree.getZTreeObj("legalTree");
					zTree.selectNode(treeNode);
					layer.confirm('您是否确定提交？', {btn : ['确定', '取消']}, function() {
						var mask = layer.load(0, {shade: [0.2,'#000']});
						$.post("delLegalTree", {"id":treeNode.id}, function(data) {
							layer.close(mask);
							if (data.res == "success") {
								getTrees();
								layer.msg('删除成功!');
								return true;
							} else {
								layer.msg('操作失败:' + data.msg, {time: 20000, btn: ['关闭']});
								return false;
							}
						});
					});
					return false;
				},
				beforeClick : function(treeId, treeNode) {
					var addLegal = $('#addLegal');
					addLegal.find('input[name="tid"]').val(treeNode.id);
					addLegal.find('input[name="tid_"]').val(treeNode.name);
					
					var searchForm = $("#searchForm");
					var json = searchForm.serializeJson();
					params = $.extend({}, params, json, {'tid':treeNode.id});
					getDataTable(null, params);
				},
				onDrop : function(event, treeId, treeNodes, targetNode, moveType) {
//				    console.log(treeNodes[0].name + "," + (targetNode ? (targetNode.tId + ", " + targetNode.name) : "isRoot" ) + " moveType : " + moveType);
					targetNode = targetNode||new Object();
					var param = {
				    	'id':treeNodes[0].id,		// 当前节点id
				    	'tid':targetNode.id,		// 拖拽到该节点ID
				    	'pId':targetNode.pId,		// 拖拽到该节点PID
				    	'node':targetNode.node,		// 拖拽到该节点node
				    	'mtype':moveType||'inner'	// 拖拽到某个节点的上面 下面 里面 prev next inner
				    };
				    var mask = layer.load(0, {shade: [0.2,'#000']});	// 遮罩
				    $.post('editLegalTree', param, function(data) {
						if (data.res == "success") {
							search();
							layer.msg('拖拽操作成功!');
						} else {
							layer.msg('拖拽操作失败:' + data.msg, {time: 20000, btn: ['关闭']});
						}
						layer.close(mask);
					});
				}
			}
		};
	
	var search = function (obj) {
		var searchForm = null;
		if(obj==undefined) searchForm = $("#searchForm");
		else searchForm = $(obj).closest("#searchForm");
		var json = searchForm.serializeJson();
		getDataTable(obj, json);
		getTrees();
	};
	
	var getTrees = function() {
		//初始化ztree菜单
		treeMenu = $("#legalTree");
		zTree_Menu = $.fn.zTree.init(treeMenu, setting);
	};
	
	var getDataTable = function (obj, pas) {
		var fm = $('#legalBody');
		var tbody = fm.find("#dataBody");
		var tfoot = fm.find("#ajaxpage");
		var mask = layer.load(0, {shade: [0.2,'#000']});	// 遮罩
		params = $.extend({}, params, pas);
		$.ajaxSettings.traditional=true;					// 去除参数中数组的[]
		$.ajax({
			url:'../audit/getLegals',
			data:params,
			type:'POST',
			dataType:'json',
			success:function(data) {
				trs = data.ob;								// 保存好实体对象后再修改和预览时可以直接取值
				var arr = new Array();
				$.each(data.ob, function(i,e) {
					Utils.push(arr, '<tr>');
					Utils.addtd(arr, i+1);
					Utils.addtd(arr, e.cdate);
					Utils.addtd(arr, e.issued);
					Utils.addtd(arr, '<a href="javascript:void(0)" onclick="LegalConfig.showLegal(this, '+i+')">'+e.title+'</a>');
					Utils.push(arr, '<td>');
					Utils.push(arr, '<div class="btn-group btn-group-xs btn-group-solid">');
					Utils.push(arr, '<button class="btn btn-fit-height grey-salt dropdown-toggle" type="button" data-toggle="dropdown">');
					Utils.push(arr, ' <i class="fa fa-cog"></i> 编辑</button>');
					Utils.push(arr, '<ul class="dropdown-menu pull-right">');
					Utils.push(arr, '<li><a class="btn btn-xs" href="javascript:void(0);" onclick="javascript:LegalConfig.addLegal(this, \'editLegal\', '+e.id+', '+i+')"><i class="fa fa-pencil"></i> 编辑</a></li>');
					Utils.push(arr, '<li><a class="btn btn-xs" href="javascript:void(0);" onclick="javascript:LegalConfig.del('+e.id+')"><i class="fa fa-times"></i> 删除</a></li>');
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
	
	/**
	 * 展开或者收起tree菜单
	 */
	var expandAll = function() {
		open = open ? false : true;
		zTree_Menu.expandAll(open);
	};
	
	/**
	 * 是否拖拽节点
	 */
	var drag = function(obj) {
		var ismove = setting.edit.drag.isMove;		// 获取当前drag设置是否为拖拽true or fasle
		var move, abbr;
		if(ismove) {
			move = false;
			abbr = '';
		} else {
			move = true;
			abbr = '(允许拖拽)';
		}
		setting.edit.drag.isMove = move;			// 设置isMove后重新刷新ztree
		getTrees();
		$(obj).closest('.portlet-title').find('.caption > small > abbr').html(abbr);
	};
	
	var showLegal = function (obj, i) {
		var dlg = $('#showLegal');
		var att = trs[i];
		var e = att ? att : new Object();
		dlg.find('.panel-body').html(e.remark);
		lay = layer.open({
			title: '信息',
			type:1,
			btn:['<i class="fa fa-ban"></i> 确定', '<i class="fa fa-ban"></i> 取消'],
			maxmin: true,
			area: '700px',
			content: dlg
		});
	};
	
	var addDlg = function (obj, action, id, name, pId) {
		var dlg = $('#addDlg');
		lay = layer.open({
			title: '信息',
			type:1,
			btn:['<i class="fa fa-pencil"></i> 提交保存', '<i class="fa fa-ban"></i> 取消'],
			yes:submitAdd,
			maxmin: true,
			area: '600px',
			content: dlg
		});
		
		// 获取用户对象赋值表单,如果是新增，直接new一个Object赋空值即可
		var addForm = dlg.find('#addTreeForm');
		addForm.validate().resetForm();
		addForm.attr('action', action);
		
		// 根据action判断新增和修改
		if(action == 'addLegalTree') {
			addForm.find('input[name="id"], input[name="name"]').val('');
			addForm.find('input[name="pId"]').val(id);
		} else {
			addForm.find('input[name="id"]').val(id);
			addForm.find('input[name="pId"]').val(pId);
			addForm.find('input[name="name"]').val(name);
		}
	};
	
	var addLegal = function (obj, action, id, i) {
		var dlg = $('#addLegal');
		var addForm = dlg.find('#addForm');
		var tid = addForm.find('input[name="tid"]');
		if(Utils.isEmpty(tid.val())) {
			layer.msg('没有选择对应的法规类别');
			return false;
		}
		
		summernote('addLegal');
		lay = layer.open({
			title: '信息',
			type:1,
			btn:['<i class="fa fa-pencil"></i> 提交保存', '<i class="fa fa-ban"></i> 取消'],
			yes:submitAdd,
			maxmin: true,
			area: '700px',
			content: dlg
		});
		layer.full(lay);
		
		// 获取用户对象赋值表单,如果是新增，直接new一个Object赋空值即可
		var att = trs[i];
		var e = att ? att : new Object();
		addForm.validate().resetForm();
		addForm.attr('action', action);
		addForm.find('input[name="id"]').val(id);
		addForm.find('input[name="cdate"]').val(e.cdate);
		addForm.find('input[name="issued"]').val(e.issued);
		addForm.find('textarea[name="title"]').val(e.title);
		addForm.find('textarea[name="remark"]').val(e.remark);
		addForm.find('textarea[name="remark"]').summernote('code', !e.remark?'':e.remark);
	};
	
	var submitAdd = function (i, layero) {
		var addForm = $(layero).find('form:visible');
		var action = addForm.attr('action');
		if(!addForm.valid()) return false;
		layer.confirm('您是否确定提交？', {btn : ['确定', '取消']}, function() {
			var mask = layer.load(0, {shade: [0.2,'#000']});
			$.post(action, addForm.serialize(), function(data) {
				if (data.res == "success") {
					search();
					layer.msg('操作成功!');
					layer.close(lay);
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
			$.post("delLegal", {"id":id}, function(data) {
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
	

	var fullscreen = function(obj) {
		var portlet = $(obj).closest(".portlet");
		if (portlet.hasClass('portlet-fullscreen')) {
			$(obj).removeClass('on');
			portlet.removeClass('portlet-fullscreen');
			$('body').removeClass('page-portlet-fullscreen');
			portlet.children('.portlet-body').css('height', 'auto');
		} else {
			var height = Metronic.getViewPort().height
				    - portlet.children('.portlet-title').outerHeight()
					- parseInt(portlet.children('.portlet-body').css('padding-top'))
					- parseInt(portlet.children('.portlet-body').css('padding-bottom'));

			$(obj).addClass('on');
			portlet.addClass('portlet-fullscreen');
			$('body').addClass('page-portlet-fullscreen');
			portlet.children('.portlet-body').css('height', height);
		}
	};
	
	var reload = function(ob, tag) {
		var el = $(ob).closest(".portlet").find(".portlet-body");
		// for demo purpose
		Metronic.blockUI({
			target : el,
			animate : true,
			overlayColor : 'none'
		});
		window.setTimeout(function() {
			if(tag == 'tree') {
				getTrees();
			} else {
            	getDataTable(null, params);
			}
			Metronic.unblockUI(el);
		}, 500);
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
				height:$(window).height()-450,
				placeholder:'内容',
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
		getTrees:getTrees,			// 查询法规类别树
		getDataTable:getDataTable,	// 渲染table数据
		expandAll:expandAll,		// 展开或者收起tree菜单
		drag:drag,					// 是否拖拽tree
		showLegal:showLegal,		// 显示法规内容弹出框
		addDlg:addDlg,				// 新增弹出窗
		addLegal:addLegal,			// 新增法规弹出窗
		submitAdd:submitAdd,		// 提交新增事件
		del:del,					// 删除事件
		fullscreen:fullscreen,		// box全屏事件
		reload:reload				// box刷新事件
	};
}();

$(function() {
	// 默认查询事件
	params = {'func':'LegalConfig.getDataTable', 'pageNum':1, 'pageSize':10};
	LegalConfig.search();
	
	// title tip 显示效果
	$('.tools i, .portlet-title > .caption > small > abbr').tooltip();
});

