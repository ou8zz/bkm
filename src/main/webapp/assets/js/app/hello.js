$(function() {
	var url = "helloByJson.htm";
	$.post(url, null, function(data){
		alert(data);
	},'json');
});

function subHello(obj) {
	var addForm = $("#addForm");
	var url = "toJson.htm";
	var param = {
		'id':addForm.find("#id").val(),
		'ename':addForm.find("#ename").val(),
		'cname':addForm.find("#cname").val()
	};
	$.post(url, param, function(data){
		alert(data.cname);
	},'json');
};

function subHello2(obj) {
	var addForm = $("#addForm");
	var url = "toJsonByResponseBody.htm";
	var param = {
		'id':addForm.find("#id").val(),
		'ename':addForm.find("#ename").val(),
		'cname':addForm.find("#cname").val()
	};
	$.post(url, param, function(data){
		alert(data);
	},'json');
};
