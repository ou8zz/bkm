package com.util;

import java.io.Serializable;

/**
 * @description 返回JSON通用对象
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/07/01
 * @version 1.0
 */
public class RespsonData implements Serializable {
	private static final long serialVersionUID = 7260070567969839919L;
	private String res;		// 操作状态
	private String msg;		// 返回信息
	
	public RespsonData(String res) {
		this.res = res;
	}
	
	public RespsonData(String res, String msg) {
		this.res = res;
		this.msg = msg;
	}

	public void result(String res, String msg) {
		this.res = res;
		this.msg = msg;
	}
	
	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
