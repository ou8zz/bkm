package com.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.model.FundInfo;
import com.model.FundManagers;
import com.service.FundsService;
import com.util.Paramer;
import com.util.RegexUtil;

/**
 * @description 基金经理接口
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/08/09
 * @version 1.0
 */
@Repository(value="fundsService")
@Scope(value="prototype")
@Transactional(propagation=Propagation.REQUIRED)
public class FundsServiceImp implements FundsService {

	@Autowired
	private SqlSession sqlSession;

	/**
	 * 新增配置
	 */
	@Override
	public void addFundManagers(FundManagers fm) {
		sqlSession.insert("funds.addFundManagers", fm);
	}

	/**
	 * 编辑配置 
	 */
	@Override
	public void editFundManagers(FundManagers fm) {
		sqlSession.update("funds.editFundManagers", fm);
	}

	/**
	 * 删除配置
	 */
	@Override
	public void delFundManagers(FundManagers fm) {
		sqlSession.delete("funds.delFundManagers", fm);
	}

	/**
	 * 查询配置
	 */
	@Override
	public Paramer getFundManagers(Paramer p) {
		Page<Object> startPage = PageHelper.startPage(p.getPageNum(), p.getPageSize());
		startPage = (Page<Object>) sqlSession.selectList("funds.getFundManagers", p.getOb());
		p.setPage(startPage);
		return p;
	}

	/**
	 * 查询配置
	 */
	@Override
	public FundManagers getFundManagers(FundManagers fm) {
		return sqlSession.selectOne("funds.getFundManagers", fm);
	}
	
	
	/**
	 * 新增基金组合
	 */
	@Override
	public void addFundInfo(FundInfo fi) {
		sqlSession.insert("funds.addFundInfo", fi);
	}

	/**
	 * 编辑基金组合
	 */
	@Override
	public void editFundInfo(FundInfo fi) {
		// 如果ids不等于空，是在修改基金经理对应组合的关系，设置关系需要删除原有的关系
		if(RegexUtil.notEmpty(fi.getIds()) && RegexUtil.notEmpty(fi.getUserid())) {
			sqlSession.update("funds.editFundInfoByUid", fi.getUserid());
		}
		sqlSession.update("funds.editFundInfo", fi);
	}

	/**
	 * 删除基金组合
	 */
	@Override
	public void delFundInfo(FundInfo fi) {
		sqlSession.delete("funds.delFundInfo", fi);
	}

	/**
	 * 查询基金组合列表
	 */
	@Override
	public Paramer getFundInfo(Paramer p) {
		Page<Object> startPage = PageHelper.startPage(p.getPageNum(), p.getPageSize());
		startPage = (Page<Object>) sqlSession.selectList("funds.getFundInfo", p.getOb());
		p.setPage(startPage);
		return p;
	}

	/**
	 * 查询基金组合
	 */
	@Override
	public FundInfo getFundInfo(FundInfo fi) {
		return sqlSession.selectOne("funds.getFundInfo", fi);
	}
}
