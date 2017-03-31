package com.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.model.Ugroup;
import com.service.AdminService;
import com.util.Paramer;

/**
 * @description 后台部门，角色等管理
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/05/29
 * @version 1.0
 */
@Repository(value="adminService")
@Scope(value="prototype")
@Transactional(propagation=Propagation.REQUIRED)
public class AdminServiceImp implements AdminService {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void addUgroup(Ugroup u) {
		sqlSession.insert("ugroup.addUgroup", u);
	}

	@Override
	public void editUgroup(Ugroup u) {
		sqlSession.selectOne("ugroup.editUgroup", u);
	}

	@Override
	public void delUgroup(Integer id) {
		sqlSession.selectOne("ugroup.delUgroup", id);
	}

	@Override
	public Paramer getUgroups(Paramer p) {
		Page<Object> startPage = PageHelper.startPage(p.getPageNum(), p.getPageSize());
		startPage = (Page<Object>) sqlSession.selectList("ugroup.getUgroups", p.getOb());
		p.setPage(startPage);
		return p;
	}

	@Override
	public Object getUgroup(Ugroup u) {
		return sqlSession.selectList("ugroup.getUgroups", u);
	}
	
	/**
	 * 后台菜单配置
	 */
	@Override
	public void saveRoleZtree(Ugroup ug) {
		// 先删除所有菜单配置，在insert所配置的新菜单
		sqlSession.delete("ztree.delRoleZtree", ug.getId());
		sqlSession.insert("ztree.saveRoleZtree", ug);
	}
}
