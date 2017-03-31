package com.service;

import com.model.Guser;
import com.util.Paramer;

/**
 * @description 后台用户管理接口
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/05/29
 * @version 1.0
 */
public interface GuserService {
	void addGuser(Guser u);
	void editGuser(Guser u);
	void editUserUgroup(Guser u);
	void delGuser(Integer[] id);
	Paramer getGusers(Paramer p);
	Guser getGuser(Guser u);
}
