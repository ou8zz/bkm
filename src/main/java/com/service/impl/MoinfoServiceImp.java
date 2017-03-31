package com.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.model.Moinfo;
import com.service.MoinfoService;
import com.util.Paramer;

/**
 * @description 机构信息,托管人信息等接口
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/08/21
 * @version 1.0
 */
@Repository(value="moinfoService")
@Scope(value="prototype")
@Transactional(propagation=Propagation.REQUIRED)
public class MoinfoServiceImp implements MoinfoService {

	@Autowired
	private SqlSession sqlSession;

	/**
	 * 新增配置
	 */
	@Override
	public void addMoinfo(Moinfo m) {
		sqlSession.insert("moinfo.addMoinfo", m);
	}

	/**
	 * 编辑配置 
	 */
	@Override
	public void editMoinfo(Moinfo m) {
		sqlSession.update("moinfo.editMoinfo", m);
	}

	/**
	 * 删除配置
	 */
	@Override
	public void delMoinfo(Moinfo m) {
		sqlSession.delete("moinfo.delMoinfo", m);
	}

	/**
	 * 查询配置
	 */
	@Override
	public Paramer getMoinfo(Paramer p) {
		Page<Object> startPage = PageHelper.startPage(p.getPageNum(), p.getPageSize());
		startPage = (Page<Object>) sqlSession.selectList("moinfo.getMoinfo", p.getOb());
		p.setPage(startPage);
		return p;
	}

	/**
	 * 查询配置
	 */
	@Override
	public Moinfo getMoinfo(Moinfo m) {
		return sqlSession.selectOne("moinfo.getMoinfo", m);
	}
	
}
