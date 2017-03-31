var params;
$(function() {
	// 默认查询事件
	params = {'func':'getZtrees', 'pageNum':1, 'pageSize':10};
	searchZtree();
});

/**
 * 查询按钮
 * @param obj
 */
function searchZtree(obj) {
	var searchForm = null;
	if(obj==undefined) searchForm = $("#searchForm");
	else searchForm = $(obj).closest("#searchForm");
	getZtrees(obj, searchForm.serializeJson());
}

function getZtrees(obj, pas) {
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
				arr.push('<tr>');
				add_td(arr, e.id);
				add_td(arr, e.pId);
				add_td(arr, e.node);
				add_td(arr, '<span class='+e.ico+'></span> ' + e.ico);
				add_td(arr, e.name);
				add_td(arr, e.title);
				add_td(arr, e.url);
				add_td(arr, add_dropdown('<li><a href="javascript:void(0)" onclick="javascript:addDlg(this, \'editZtree\')">1. 编辑</a></li><li><a href="javascript:void(0)" onclick="javascript:delZtree('+e.id+')">2. 删除</a></li>'));
				arr.push('</tr>');
			});
			$("#dataBody").html(arr.join(''));
			
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
	                	getZtrees(obj, params);
	                }
	            }
			});
			
			$(".am-dropdown").dropdown({justify: '#doc-dropdown-justify-js'}); // 激活下拉功能；
			layer.close(mask);
		}
	});
}

/**
 * 选择菜单图标事件
 * @param ob
 * @param ico
 */
function choiceIco(ob, ico) {
	var cho = $(ob).closest("#choico");
	cho.find("#show-ico").html('<span class='+ico+'></span> <input type="hidden" name="ico" value="'+ico+'">');
	cho.find("button").click(); // 隐藏下拉菜单；
}

/**
 * 弹出框
 */
var lay;
function addDlg(obj, action) {
	lay = layer.open({
		title: '菜单信息',
		type:1,
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
	addForm.find("#show-ico").html('<span class='+ico+'></span> <input type="hidden" name="ico" value="'+ico+'">');
}

/**
 * 提交操作
 * @param obj
 */
function submitAdd(obj) {
	var addForm = $("#addForm");
	if(!addForm.valid()) return false;
	layer.confirm('您是否确定提交？', {btn : ['确定', '取消']}, function() {
		var mask = layer.load(0, {shade: [0.2,'#000']});
		$.post(addForm.attr('action'), addForm.serialize(), function(data) {
			if (data.res == "success") {
				layer.msg('操作成功!');
				searchZtree();
				layer.close(lay);
			} else {
				layer.msg('操作失败:' + data.msg, {time: 20000, btn: ['关闭']});
			}
			layer.close(mask);
		});
	});
};

function delZtree(id) {
	layer.confirm('您是否确定删除？', {btn : ['确定', '取消']}, function() {
		var mask = layer.load(0, {shade: [0.2,'#000']});
		$.post("delZtree", {id : id}, function(data) {
			if (data.res == "success") {
				layer.msg('删除成功!');
				searchZtree();
				layer.close(lay);
			} else {
				layer.msg('操作失败:' + data.msg, {time: 20000, btn: ['关闭']});
			}
			layer.close(mask);
		});
	});
};
