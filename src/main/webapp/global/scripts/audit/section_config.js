var params;
var SectionConfig = function() {
	var lay;
	var search = function (obj) {
		var searchForm = null;
		if(obj==undefined) searchForm = $("#searchForm");
		else searchForm = $(obj).closest("#searchForm");
		var json = searchForm.serializeJson();
		getDataTable(obj, json);
	};
	
	var getDataTable = function (obj, pas) {
		params = $.extend({}, params, pas);
		var mask = layer.load(0, {shade: [0.2,'#000']});	// 遮罩
		$.ajaxSettings.traditional=true;					// 去除参数中数组的[]
		$.ajax({
			url:'../audit/getSectionConfig',
			data:params,
			type:'POST',
			dataType:'json',
			success:function(data) {
				var arr = new Array();
				$.each(data.ob, function(i,e) {
					arr.push('<div class="panel panel-default item-selector" data-id="'+e.id+'" data-node="'+e.node+'">');
					arr.push('<div class="panel-heading">');
					arr.push('<h4 class="panel-title">');
					arr.push('<span onclick="javascript:SectionConfig.summernote(\'collapse_'+i+'\', 600);" class="accordion-toggle accordion-toggle-styled collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapse_'+i+'"><i class="fa fa-arrows icon-move cp">&nbsp;&nbsp;&nbsp;</i> <b class="cp">'+e.title+'</b></span>');
					arr.push('</h4>');
					arr.push('</div>');
					arr.push('<div id="collapse_'+i+'" class="panel-collapse collapse">');	// 默认使用collapse样式关闭 in样式打开
					arr.push('<div class="panel-body">');
					arr.push('<div class="form-group"><input type="text" name="title" class="form-control" value="'+e.title+'" placeholder="章节目录"></div>');
					arr.push('<textarea class="content" name="content" id="content_'+i+'">'+e.content+'</textarea>');
					arr.push('<span class="text-success">使用 @ 符可以拉出对应管理人信息(使用前后用空格进行拉取和完成)</span>');
					arr.push('</div>');
					arr.push('<div class="panel-footer text-right">');
					arr.push('<button type="button" onclick="javascript:SectionConfig.submitEidt(this, '+e.id+');" class="btn btn-primary btn-sm" ><i class="fa fa-pencil"></i> 保存修改</button>');
					arr.push('</div>');
					arr.push('</div>');
					arr.push('</div>');
				});

				// Ajax分页
				laypage({
				    cont: $("#ajaxpage"), 		//容器。值支持id名、原生dom对象，jquery对象,
				    pages: data.pages, 			//总页数
				    curr: data.pageNum || 1, 	//当前页
				    total: data.total,			//总条数
				    skip: true, 				//是否开启跳页
				    skin: 'molv', 				//加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
				    groups: 28, 				//连续显示分页数
				    jump: function(ob, first){ 	//触发分页后的回调
		                if(!first){ 			//点击跳页触发函数自身，并传递当前页：ob.curr
		                	// 分页后回调，设置下次分页参数和调用函数
		                	params = $.extend({}, params, {"pageNum":ob.curr});
		                	getDataTable(obj, params);
		                }
		            }
				});
				
				// 初始化列表和summernote编辑器插件
				var accordion = $("#accordion");
				accordion.html(arr.join(''));	// 填充页面内容
				layer.close(mask);				// 关闭遮罩
			}
		});
	};
	
	var dragDlg = function() {
		$("#accordion").sortable({
			handle:'i.icon-move',									// 只通过设置的移动图标才能拖拽
			containerSelector:'div.accordion',						// 必须指定确切的目标DIV，不然会取不到onDrop中container参数
			itemSelector:'div.item-selector',						// 移动的标签元素，唯一
			placeholder:'<i class="fa fa-hand-o-right"></i>',		// 拖拽后插入指示图标
			onDrop:function ($item, container, _super, event) {
				var param = {
					'id':$item.attr('data-id'),
					'node':$item.attr('data-node'),
					'prev':$item.prev().attr('data-node'),
					'next':$item.next().attr('data-node')
				};
				var url = '../audit/editSectionConfig';
				var mask = layer.load(0, {shade: [0.2,'#000']});
				$.post(url, param, function(data) {
					if (data.res == "success") {
						layer.msg('拖拽完成!');
						layer.close(lay);
						search();
					} else {
						layer.msg('拖拽操作失败:' + data.msg, {time: 20000, btn: ['关闭']});
					}
					layer.close(mask);
				});
				// 动态局部更新拖拽后界面和鼠标效果
				$item.removeClass(container.group.options.draggedClass).removeAttr("style");
				$("body").removeClass(container.group.options.bodyClass);
			}
		});
	};
	
	var addDlg = function (obj, action, id) {
		lay = layer.open({
			title: '新增信息',
			type:1,
			btn:['<i class="fa fa-pencil"></i> 提交保存', '<i class="fa fa-ban"></i> 取消'],
			yes:submitAdd,
			maxmin: true,
			area: '800px',
			content: $("#addDlg")
		});
		layer.full(lay);	// 弹出全屏
		SectionConfig.summernote($('#addDlg'), 280);		// 初始化弹出框中的编辑器
	};
	
	var showDlg = function (obj, action, id) {
		var dlg = $("#showDlg");
		
		// 获取内容
		var url = 'expSectionConfig';
		$.post(url, $("#searchForm").serialize(), function(data) {
			dlg.find('.panel-body').html(data);
			
			// 弹出框
			lay = layer.open({
				title: '预览',
				type:1,
				btn:['<i class="fa fa-file-word-o"></i> 导出', '<i class="fa fa-ban"></i> 取消'],
				yes:exportFile,
				maxmin: true,
				area: '800px',
				content: dlg
			});
			layer.full(lay);	// 弹出全屏
		});
	};
	
	var exportFile = function(obj, layero) {
		// 设置按钮禁用3秒后恢复，防止重复点击按钮。
		var ob = layero ?　$(layero).find('button') : $(obj);
		ob.attr('disabled', 'disabled');
		setTimeout(function() {ob.removeAttr('disabled');}, 3000);
		
		// 提交导出form
		var body = $("body");
		body.append('<form class="exp collapse" method="post" action="expSectionConfig"></form>');
		body.find('form.exp').submit().remove();
	};
	
	var submitAdd = function (obj) {
		var addForm = $("#addForm");
		if(Utils.isEmpty(addForm.find('input[name="title"]').val())) {
			layer.msg('标题不能为空!');
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
	
	var submitEidt = function (obj, id) {
		layer.confirm('您是否确定提交？', {btn : ['确定', '取消']}, function() {
			var mask = layer.load(0, {shade: [0.2,'#000']});
			var url = '../audit/editSectionConfig';
			var ob = $(obj).closest('div.panel-default');
			var param = {
				'id':id,
				'title':ob.find('input[name="title"]').val(),
				'content':ob.find('textarea[name="content"]').val()
			};
			$.post(url, param, function(data) {
				if (data.res == "success") {
					layer.msg('操作成功!');
					layer.close(lay);
				} else {
					layer.msg('操作失败:' + data.msg, {time: 20000, btn: ['关闭']});
				}
				layer.close(mask);
			});
		});
	};
	
	var summernote = function (obj, height) {
		if(typeof obj === 'string') {
			obj = $('#'+obj);
		}
		var content = obj.find('textarea.content');
		// 如果没有textarea.note-codable表示没有创建编辑器，如果有就不用创建了
		if(obj.find('textarea.note-codable').length == 0) {
			content.summernote({
				lang:'zh-CN',
				height:$(window).height()-(height||300),
				placeholder:'章节内容',
				toolbar: [
		          ['style', ['style', 'bold', 'italic', 'underline', 'clear']],
		          ['font', ['strikethrough', 'superscript', 'subscript', 'hr']],
		          ['fontsize', ['fontsize', 'color']],
		          ['para', ['ul', 'ol', 'paragraph', 'height', 'table']],
		          ['codeview', ['undo', 'redo', 'codeview']],
		          ['fullscreen', ['fullscreen']]
		          ],
				hint: {
				    mentions: ['张伟东', '王大锤', '李丽萍', '王大伟'],
				    match: /\B@(\w*)$/,
				    search: function (keyword, callback) {
				      callback($.grep(this.mentions, function (item) {
				        return item.indexOf(keyword) == 0;
				      }));
				    },
				    content: function (item) {
				      return item;
				    }
				}
			});
		}
	};
	
	return {
		search:search,				// 查询按钮事件
		getDataTable:getDataTable,	// 渲染table数据
		dragDlg:dragDlg,			// 拖动节点排序事件
		showDlg:showDlg,			// 预览配置内容
		exportFile:exportFile,		// 导出按钮
		addDlg:addDlg,				// 新增弹出窗
		submitAdd:submitAdd,		// 提交新增事件
		submitEidt:submitEidt,		// 修改
		summernote:summernote		// 新增弹出框init编辑器(该插件中hint和jquery validate冲突)
	};
}();

$(function() {
	// 默认查询事件
	params = {'func':'SectionConfig.getDataTable', 'pageNum':1, 'pageSize':10};
	SectionConfig.search();

	// 拖动节点排序事件
	SectionConfig.dragDlg();
	
	// title tip 显示效果
	$('.select2-selection, #searchForm button').tooltip({'trigger':'hover'});
});

