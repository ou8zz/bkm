/**
 * OLE
 */
package com.constant;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @description type of group discrimination.
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2011-7-30,2011 上午11:37:49
 * @version 3.0
 */
public enum GroupType {
	ROLE("角色"), DEPARTMENT("部门"), POSITION("职务");

	private String des;

	GroupType(String des) {
		this.des = des;
	}

	@JsonValue
	public String getDes() {
		return des;
	}

	public String getValue() {
		return name();
	}
}
