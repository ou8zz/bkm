var Utils = function() {
	/**
	 * 辅助拼table格式
	 * @param arr 数组
	 * @param va  字段
	 * @param att 赋值变量名标记
	 */
	var addtd = function (arr, va, att) {
		if(!va) va = '';
		if(att)att='att='+att;else att='';
		arr.push('<td '+att+'>'+va+'</td>');
	};

	/**
	 * 辅助arr.push
	 * @param arr 数组
	 * @param va  html
	 */
	var push = function (arr, va) {
		if(!va) va = "";
		arr.push(va);
	};
	
	var isEmpty = function (str){
		if (str == "") return true;
		if (str == undefined) return true;
		var reg = /^[ ]+$/; 
		return reg.test(str);
	};
	
	var ignoreNull = function (str){
		if (str == undefined || str == null) return '';
		else return str;
	};
	
	var strToJson = function(str) {
		var json = (new Function("return " + str))();
		return json;
	};
	
	return {
		addtd : addtd,			// 辅助拼table格式
		push : push,			// 辅助Array.push
		isEmpty : isEmpty,		// 判断字符是否为空
		ignoreNull:ignoreNull,	// 忽略空的类型，返回空的字符串“”
		strToJson:strToJson		// toJson方法
	};
}();

$(function() {
	// 处理全局ajax请求如果返回403异常提醒
	/*$.ajaxSetup({
		beforeSend : function(xhr) {
			xhr.setRequestHeader('Authorization', 'Token 123')
		}
	});*/
	//	在接受到数据后做统一处理
	/*$(document).ajaxSuccess(function(event, request, settings) {
		console.log(request.status);
	});*/
	$(document).ajaxError(function(event, request, settings) {
		if(request.status == 500) {
			alert("服务器异常，请重新登录或刷新页面重试，如果仍存在问题请联系管理员！");
		}
		else if (request.status == 403) {
			alert("请求URL"+settings.url+"系统检测您没有权限！");
		} 
		else if (request.status == 302) {
			alert("没有权限，请重试！");
		}
		else if (request.status == 200) {
			alert("用户请求错误，可能是登录超时，请刷新页面重新登录后重试！");
		} 
		else {
			alert("异常状态："+request.status+"，请重新登录或刷新页面重试！");
		}
	});
		
	// 定义全局Jquery表单ToJosn方法
	$.fn.serializeJson = function() {
	    var o = {};
	    var a = this.serializeArray();
	    $.each(a, function() {
	        if (o[this.name]) {
	            if (!o[this.name].push) {
	                o[this.name] = [ o[this.name] ];
	            }
	            o[this.name].push(this.value || '');
	        } else {
	            o[this.name] = this.value || '';
	        }
	    });
	    return o;
	};
	
	// 定义table下checked点击全选
	$.fn.onTableChecked = function() {
		var obj = this;
		var tbody = obj.closest('thead').siblings('tbody');

		// 点击全选
		obj.click(function() {tbody.find('tr input[type="checkbox"]').prop('checked', this.checked);});
		
		// 未来事件，点击checked时全选checked
		tbody.on('click', 'tr input[type="checkbox"]', function(e) { setChecked(); });
		
		// 加载时触发一次是否全选
		setChecked();
		
		// 判断所有checkbox是否选中，如果没有选中取消全选checkbox否则选中全选checkbox
		function setChecked() {
			var count = tbody.find('tr input[type="checkbox"]').size();
			var checked_count = tbody.find('tr input[type="checkbox"]:checked').size();
			if(count == checked_count) {
				obj.prop('checked', true);
			} else {
				obj.prop('checked', false);
			}
		}
	};
	
	Layout.init(); // init current layout
	Themes.init();	// init themes
	
	//Ztree参数,由于左侧菜单是由Ztree进行处理的，以下代码不要随便动
	var zTree_Menu, treeMenu, active;
	var ztree_temp_url = window.location.toString();
//	var ztree_temp_url_i = ztree_temp_url.lastIndexOf("/");
//	ztree_temp_url = ztree_temp_url.substring(++ztree_temp_url_i, ztree_temp_url.length);
	var setting = {
		async : {
			enable : true,
			url : "../app/ztree",
			type : "post",
			dataFilter : function(treeId, parentNode, responseData) {
				// 数据在初始json时默认所有节点是关闭的，以下处理只是为当前url的界面进行节点的展开
				var nids = $.cookie('treeOpen_');
				$.each(responseData, function(i, e) {
					if(ztree_temp_url.indexOf(e.url) > 8) {
						$.each(responseData, function(j, w) {
							if(e.pId==w.id) {
								responseData[j].open=true;
								return;
							}
						});
						return;
					}
					
					// 从cookie中找是否有点开过的父节点，如果有，默认也要将这些节点恢复打开状态
					if(!Utils.isEmpty(nids)) {
						var ids = nids.split(',');
						for(var k in ids) {
							if(e.id==ids[k]) {
								responseData[i].open=true;
								return;
							}
						}
					}
				});
				return responseData;
			}
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		view: {
			dblClickExpand: false,
			showIcon: false,
			addDiyDom : function(treeId, treeNode) {
				// 初始化菜单后进行bootstrap的左侧菜单css和菜单图片的覆盖
				var aObj = treeMenu.find("#" + treeNode.tId + "_a");
				if (treeNode.isParent) {
					aObj.append('<span class="arrow"></span>');
					aObj.siblings("ul").addClass("sub-menu");
					
					// 如果默认是open的节点,加上选中效果
					if(treeNode.open) {
						if(active) {
							active.removeClass().addClass("active");
						}
						aObj.find("span.arrow").addClass("open");
						aObj.append('<span class="selected"></span>');
					}
				} else {
					if(ztree_temp_url.indexOf(treeNode.url) > 8) {
						active = aObj.closest("li").parent("ul").parent("li");		// 设置当前菜单为活动状态，即指示的小三角形
						aObj.closest("li").removeClass().addClass("active");		// 设置当前菜单为选中状态
					}
				}
				aObj.prepend('<i class="fa '+treeNode.ico+' fa-fw"></i>');	// 图标
				aObj.find('.node_name').addClass('title');					// 菜单名称css
			}
		},
		callback : {
			beforeClick : function(treeId, treeNode) {
				var aObj = treeMenu.find("#" + treeNode.tId + "_a");
				if (treeNode.isParent) {
					// ztree如果是父节点就可以点击菜单名称进行收缩
					zTree_Menu.expandNode(treeNode);
					
					// 如果点击也需要更新菜单图标样式，毕竟不是原生的，需要刷新css
					if(treeNode.open) {
						aObj.find("span.arrow").addClass("open");
					} else {
						aObj.find("span.arrow").removeClass('open');
					}
					aObj.siblings("ul").removeClass().addClass("sub-menu").show();
					
					// 如果点击了父节点，记录cookie
					var nodes = zTree_Menu.getNodes();
					var arr = new Array();
					for(var n in nodes) {
						if(nodes[n].open) {
							nid = nodes[n].id;
							arr.push(nid);
						}
					}
					$.cookie('treeOpen_', arr);
				}
				return !treeNode.isParent;
			}
		}
	};
	
	//初始化ztree菜单
	treeMenu = $("#tree");
	zTree_Menu = $.fn.zTree.init(treeMenu, setting);
	
	// 添加菜单切换合并功能按钮
//	treeMenu.prepend('<li class="sidebar-toggler-wrapper"><div class="sidebar-toggler"></div></li>');
	
	// 初始化select2多选插件
	$('select.select2').each(function(i, e) {
		var el = $(e);
		el.select2({theme:'bootstrap', placeholder: {id:null, text:el.attr('data-placeholder')}});
	});
	
	// 设置全局date插件为bootstrap-datapicker
//	if( $('[type="date"]').prop('type') != 'date' ) {
//	}
	$('[type="date"]').datepicker({
		language:  'zh-CN',
	    format: 'yyyy-mm-dd',
	    autoclose: true,
        todayBtn: 'linked'	//设置linked表示today直接就是选择今日
	});
	
	// 全局按按钮ESC关闭layer事件
	$(document).keyup(function (e) {
		var code = e.keyCode || e.which;
		if (code == 27) {
//			layer.closeAll(); 这里为什么不用closeAll是因为如果在弹出框中有confirm确认的第二层弹出层会全部都关闭，正确的方式是each当前所有弹出层关闭最大的那个层（越后面弹出框times数越大）。
			var id = 0;
			$('.layui-layer').each(function(i, e) {
				id = $(e).attr('times');
			});
			layer.close(id);
		}
	});
});

