/**
 * OLE
 */
package com.constant;

/**
 * @description 人员类型
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016-08-09
 * @version 1.0
 */
public enum PositionType {
	FM("基金经理"), AFM("基金经理助理");

	PositionType(String des) {
		this.des = des;
	}

	private String des;

//	@JsonValue	//该注解表示默认toJson为des中文
	public String getDes() {
		return des;
	}
}
