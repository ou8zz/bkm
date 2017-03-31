/**
 * OLE
 */
package com.constant;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @description 机构信息,托管人
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2011-7-30,2011 上午11:37:49
 * @version 3.0
 */
public enum MoType {
	CUSTODIAN("托管人"), BROKERAGE("机构信息");

	private String des;

	MoType(String des) {
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
