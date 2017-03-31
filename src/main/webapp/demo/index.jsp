<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>login</title>
<script type="text/javascript" src="script/common/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
$(function() {
	
});

function login(obj) {
	var f = $("#f");
	var param = {
		'username':f.find("#username").val(),
		'password':f.find("#password").val()
	}
	$.ajax({
		url:'ajax_login',
		type:'POST',
		data:param,
		dataType:'json',
		success:function(data) {
			if(data.result=="SUCCESS") {
				alert("登录OK");
// 				window.location=data.url;				
			} else {
				alert("登录错误");
			}
			
		}
	});
}

</script>
</head>
<body>
<h2>login</h2>
<form id='f' action='/bkm/ajax_login' method='POST'>
 <table>
    <tr><td>User:</td><td><input type='text' name='username' id='username' value=''></td></tr>
    <tr><td>Password:</td><td><input type='password' name='password' id='password'/></td></tr>
    <tr><td><input type='checkbox' name='remember_me'/></td><td>Remember me on this computer.</td></tr>
    <tr><td colspan='2'><input name="submit1" type="button" value="Login" onclick="javascript:login(this)"/><br/>
    <input name="submit2" type="submit" value="LoginForSubmit"/></td></tr>
  </table>
</form>
</body>
</html>
