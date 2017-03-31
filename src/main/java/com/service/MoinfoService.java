package com.service;

import com.model.Moinfo;
import com.util.Paramer;

/**
 * @description 机构信息,托管人信息等维护
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/08/21
 * @version 1.0
 */
public interface MoinfoService {
	void addMoinfo(Moinfo m);
	void editMoinfo(Moinfo m);
	void delMoinfo(Moinfo m);
	Paramer getMoinfo(Paramer p);
	Moinfo getMoinfo(Moinfo m);
}
