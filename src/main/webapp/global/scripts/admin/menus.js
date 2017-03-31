var params;
var Menus = function() {
	var lay;			// 弹出层临时变量
	var trs;			// 在点击修改和预览数据时保存的对象
	var treeMenu;		// ztreeDOM元素
	var zTree_Menu;		// ztree Object
	var open = false;	// 默认tree.open=false
	var setting = {
			async : {
				enable : true,
				url : "../admin/getMenus",
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
				showLine:true,
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
						var p = layer.confirm('您确定要在<b>"'+treeNode.name+'"</b>下新增子元素吗？', {btn : ['确定', '取消']}, function() {
							var treeDetail = $('#treeDetail');
							var addForm = treeDetail.find('#addForm');
							treeDetail.find('.caption small').html('在<b>"'+treeNode.name+'"</b>下新增子元素');
							addForm.validate().resetForm();
							addForm.attr('action', 'addZtree');
							addForm.find('input[name="id"]').val('');
							addForm.find('input[name="pId"]').val(treeNode.id);
							addForm.find('input[name="node"]').val(treeNode.node);
							addForm.find('input[name="name"]').val(treeNode.name);
							addForm.find('input[name="title"]').val(treeNode.title);
							addForm.find('input[name="url"]').val(treeNode.url);
							addForm.find("#show-ico").html('<i class="fa '+treeNode.ico+'"></i> <input type="hidden" name="ico" value="'+treeNode.ico+'">');
							layer.close(p);
						});
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
				showRenameBtn: false,
				drag:{
					isCopy:false,
					isMove:false
				}
			},
			callback : {
				beforeRemove: function(treeId, treeNode) {
					// 删除树节点
					zTree_Menu.selectNode(treeNode);
					layer.confirm('您是否确定删除<b>"'+treeNode.name+'"</b>？', {btn : ['确定', '取消']}, function() {
						var mask = layer.load(0, {shade: [0.2,'#000']});
						$.post("delZtree", {"id":treeNode.id}, function(data) {
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
					// 情况form提示，设置提交action
					var treeDetail = $('#treeDetail');
					var addForm = treeDetail.find('#addForm');
					treeDetail.find('.caption small').html('修改<b>"'+treeNode.name+'"</b>菜单');
					addForm.validate().resetForm();
					addForm.attr('action', 'editZtree');
					addForm.find('input[name="id"]').val(treeNode.id);
					addForm.find('input[name="pId"]').val(treeNode.pId);
					addForm.find('input[name="node"]').val(treeNode.node);
					addForm.find('input[name="name"]').val(treeNode.name);
					addForm.find('input[name="title"]').val(treeNode.title);
					addForm.find('input[name="url"]').val(treeNode.url);
					addForm.find("#show-ico").html('<i class="fa '+treeNode.ico+'"> '+treeNode.ico+' </i> <input type="hidden" name="ico" value="'+treeNode.ico+'">');
				},
				onDrop : function(event, treeId, treeNodes, targetNode, moveType) {
//				    console.log(treeNodes[0].name + "," + (targetNode ? (targetNode.tId + ", " + targetNode.name) : "isRoot" ) + " moveType : " + moveType);
					targetNode = targetNode||new Object();
					var param = {
				    	'id':treeNodes[0].id,				// 当前节点id
				    	'tid':targetNode.id,				// 拖拽到该节点ID
				    	'pId':targetNode.pId,				// 拖拽到该节点PID
				    	'node':targetNode.node,				// 拖拽到该节点node
				    	'mtype':moveType||'inner'			// 拖拽到某个节点的上面 下面 里面 prev next inner
				    };
				    var mask = layer.load(0, {shade: [0.2,'#000']});	// 遮罩
				    $.post('editZtree', param, function(data) {
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
	
	/**
	 * 根据查询条件查询
	 */
	var search = function (obj) {
		var searchForm = null;
		if(obj==undefined) searchForm = $("#searchForm");
		else searchForm = $(obj).closest("#searchForm");
		setting.async.otherParam = searchForm.serializeJson();
		getTrees();
	};
	
	var getTrees = function() {
		//初始化ztree菜单
		treeMenu = $("#ztreeTree");
		zTree_Menu = $.fn.zTree.init(treeMenu, setting);
	};
	
	/**
	 * 选择菜单图标事件
	 * @param ob
	 * @param ico
	 */
	var choiceIco = function (ob, ico) {
		var cho = $(ob).closest("#choico");
		cho.find("#show-ico").html('<i class="fa '+ico+'"> '+ico+' </span> <input type="hidden" name="ico" value="'+ico+'">');
		cho.find("button").click(); // 隐藏下拉菜单；
	};
	
	/**
	 * 点击菜单link到菜单按钮
	 */
	var link = function(obj) {
		var ob = $(obj);
		var url = ob.parent('span').siblings('input').val();
		if(Utils.isEmpty(url)) {
			layer.msg('URL为空,或者该节点为父节点');
		} else {
			window.open(url);
		}
	};
	
	/**
	 * 展开或者收起ztree菜单
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
	
	var addDlg = function() {
		// 情况form提示，设置提交action
		var treeDetail = $('#treeDetail');
		var addForm = treeDetail.find('#addForm');
		treeDetail.find('.caption small').html('新增根节点菜单');
		addForm.validate().resetForm();
		addForm.attr('action', 'addZtree');
		addForm.find('input').val('');
	};
	
	var submitAdd = function (layero) {
		var addForm = $(layero).closest('#addForm');
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
	
	return {
		search:search,				// 查询按钮事件
		getTrees:getTrees,			// 查询法规类别树
		choiceIco:choiceIco,		// 选择菜单图标事件
		link:link,					// 点击菜单link到菜单按钮
		expandAll:expandAll,		// 展开全部树节点
		drag:drag,					// 是否拖拽节点ztree
		addDlg:addDlg,				// 新增根目录菜单元素
		submitAdd:submitAdd,		// 提交新增事件
		fullscreen:fullscreen,		// box全屏事件
		reload:reload				// box刷新事件
	};
}();

$(function() {
	// 默认查询事件
	Menus.search();
	
	// title tip 显示效果
	$('.tools i, .dropdown-menu > li > i, .portlet-title > .caption > small > abbr').tooltip();
});

