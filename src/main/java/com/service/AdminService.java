package com.service;

import com.model.Ugroup;
import com.util.Paramer;

/**
 * @description 后台部门，角色等管理
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/05/29
 * @version 1.0
 */
public interface AdminService {
	void addUgroup(Ugroup u);
	void editUgroup(Ugroup u);
	void delUgroup(Integer id);
	Paramer getUgroups(Paramer p);
	Object getUgroup(Ugroup u);
	void saveRoleZtree(Ugroup ug);
}
