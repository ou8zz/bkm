package com.constant;

public enum DocumentsType {
	PIDCard("身份证"),PASSPORT("护照"),MTPs("港澳台胞证");
	
	private String des;

	DocumentsType(String des) {
		this.des = des;
	}

	public String getDes() {
		return des;
	}
}
