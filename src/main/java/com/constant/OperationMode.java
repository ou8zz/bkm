/**
 * OLE
 */
package com.constant;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @description 日志操作模式
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016-07-28
 * @version 3.0
 */
public enum OperationMode {
	ADD("新增"), EDIT("修改"), DELETE("删除"), LOGIN("登录"), LOGOUT("注销");

	OperationMode(String des) {
		this.des = des;
	}

	private String des;

	@JsonValue	//该注解表示默认toJson为des中文
	public String getDes() {
		return des;
	}
}
