/**
 * OLE
 */
package com.constant;

/**
 * @description user sex
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date Sep 4, 2011 9:48:21 PM
 * @version 3.0
 */
public enum GenderType {
	M("男"), F("女");

	GenderType(String des) {
		this.des = des;
	}

	private String des;

//	@JsonValue	//该注解表示默认toJson为des中文
	public String getDes() {
		return des;
	}
}
