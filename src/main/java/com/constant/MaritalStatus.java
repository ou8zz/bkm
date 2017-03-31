/**
 * OLE
 */
package com.constant;

/**
 * @description 婚姻状况
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/05/29
 * @version 1.0
 */
public enum MaritalStatus {
	SINGLE("未婚"), MARRIED("已婚"), DIVOCED("离异"), WIDOWED("丧偶"), SEPARATED("分居");

	MaritalStatus(String des) {
		this.des = des;
	}

	private String des;

	public String getDes() {
		return des;
	}
}
