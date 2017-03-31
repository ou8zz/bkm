var params;
var Logs = function() {
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
		var mask = layer.load(0, {shade: [0.2,'#000']});
		$.ajaxSettings.traditional=true;	// 去除参数中数组的[]
		$.ajax({
			url:'../admin/getLogs',
			data:params,
			type:'POST',
			dataType:'json',
			success:function(data) {
				var arr = new Array();
				$.each(data.ob, function(i,l) {
					arr.push('<div><span class="text-success">['+l.ctime+']</span>');
					arr.push('<span class="text-danger">['+l.cmode+']</span> ');
					arr.push('<a href="javascript:Logs.logDlg('+l.cid+', \''+l.cfunc+'\', \''+l.ctime+'\');">['+l.cfunc+']</a> ');
					arr.push(l.uname+'-'+l.title+'.</div>');
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
	};
	
	var logDlg = function (cid, cfunc, ctime) {
		var mask = layer.load(0, {shade: [0.2,'#000']});
		$.ajax({
			url:'../admin/getLogs',
			data:{'pageNum':1, 'pageSize':2, 'cid':cid, 'cfunc':cfunc, 'startTime':'1970-01-01 00:00:00', 'endTime':ctime},
			type:'POST',
			dataType:'json',
			success:function(data) {
				var arr = new Array();
				var ctimetr = $('<tr><td>ctime</td></tr>');
				var cmode = $('<tr><td>cmode</td></tr>');
				$.each(data.ob, function(i,l) {
					var k=0;
					ctimetr.append('<td>'+l.ctime+'</td>');
					cmode.append('<td>'+l.cmode+'</td>');
					$.each(Utils.strToJson(l.content), function(j, p) {
						if(i==0) {
							var tr = $('<tr class="po"></tr>');
							tr.append('<td class="po">'+j+'</td> <td class="po">'+p+'</td>');
							arr[k] = tr;
						} else {
							var tr = arr[k];
							tr.append('<td class="po">'+p+'</td>');
							arr[k] = tr;
						}
						k++;
					});
				});
				
				// 数据填充
				var dataLogs = $("#dataLogs").empty();
				dataLogs.append(ctimetr);
				dataLogs.append(cmode);
				$.each(arr, function(j, p) {
					dataLogs.append(p);
				});
				
				// 对class="po"的tr和td文本对比如果不一致标示出来
				dataLogs.find("tr.po").each(function(i, e) {
					var td = $(e).find('td.po');
					var t1 = td.eq(1);
					var t2 = td.eq(2);
					var text1 = t1.text();
					var text2 = t2.text();
					if(!Utils.isEmpty(t2.html())) {
						var res = checkText(text2, text1);
						t1.html(res);
					}
					t2.html(text2);
				});
				
				// 弹出框事件
				lay = layer.open({
					title: '日志信息',
					type:1,
					btn:['<i class="fa fa-ban"></i> 确定','<i class="fa fa-ban"></i> 关闭'],
					maxmin: true,
					area: '800px',
					content: $("#showLogs")
				});
				layer.full(lay);	// 弹出全屏
				layer.close(mask);	// 关闭遮罩
			}
		});
	};
	
	var checkText = function(text1, text2) {
		var dmp = new diff_match_patch();
		dmp.Diff_Timeout = parseFloat(1);
		dmp.Diff_EditCost = parseFloat(8);

		var ms_start = (new Date()).getTime();
		var d = dmp.diff_main(text1, text2);
		var ms_end = (new Date()).getTime();

//		if (document.getElementById('semantic').checked) {
//			dmp.diff_cleanupSemantic(d);
//		}
//		if (document.getElementById('efficiency').checked) {
//			dmp.diff_cleanupEfficiency(d);
//		}
//		dmp.diff_cleanupSemantic(d);
		dmp.diff_cleanupEfficiency(d);
		var ds = dmp.diff_prettyHtml(d);
		return ds;
	};
	
	return {
		search:search,				// 查询按钮事件
		getDataTable:getDataTable,	// 渲染table数据
		logDlg:logDlg				// 日志明细弹出
	};
}();

$(function() {
	// 默认查询事件
	params = {'func':'getLogs', 'pageNum':1, 'pageSize':20};
	Logs.search();
	
	// title tip 显示效果
	$('.select2-selection').tooltip();
});

