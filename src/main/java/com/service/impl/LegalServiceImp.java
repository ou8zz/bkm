package com.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.model.Legal;
import com.service.LegalService;
import com.util.Paramer;
import com.util.RegexUtil;

/**
 * @description 法律法规管理
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/08/23
 * @version 1.0
 */
@Repository(value="legalService")
@Scope(value="prototype")
@Transactional(propagation=Propagation.REQUIRED)
public class LegalServiceImp implements LegalService {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void addLegalTree(Legal l) {
		sqlSession.insert("legal.addLegalTree", l);
	}

	@Override
	public void editLegalTree(Legal l) {
		// 如果node有传值表示是拖拽节点，拖拽分别为 拖拽到某个节点的上面 下面 里面  对应传值对象mtype=prev, next, inner
		if(RegexUtil.notEmpty(l) && RegexUtil.notEmpty(l.getMtype())) {
			String type = l.getMtype();
			Integer next=0, prev=0;
			if("next".equals(type)) {
				// 如果next表示拖拽到当前节点的下面，找到当前节点的下一个节点数做计算中间值即可，如果当前节点是最后一个节点则当前节点+5000
				prev = l.getNode();
				next = sqlSession.selectOne("legal.getNextNode", l);
				next = RegexUtil.isEmpty(next) ? prev+5000 : next;
 				l.setNode((next-prev)/2+prev);
			} else if("prev".equals(type)) {
				// 如果prev表示拖拽到当前节点的上面，找到当前节点的上一个节点数做计算中间值即可，如果当前节点是第一个节点则当前节点设置0
				next = l.getNode();
				prev = sqlSession.selectOne("legal.getPrevNode", l);
				prev = RegexUtil.isEmpty(prev) ? 0 : prev;
				l.setNode((next-prev)/2+prev);
			} else if("inner".equals(type) && RegexUtil.notEmpty(l.getTid())) {
				// 如果inner表示拖拽到当前节点的里面，找到当前节点ID设置为pid即可，设置node值为当前节点*1000（尽量大于当前节点即可）
				Legal lg = new Legal(l.getTid());
				lg = sqlSession.selectOne("legal.getLegalTrees", lg);
				l.setpId(lg.getId());
				l.setNode(l.getTid()*1000);
			}
		}
		sqlSession.update("legal.editLegalTree", l);
	}
	
	@Override
	public void delLegalTree(Legal l) {
		sqlSession.delete("legal.delLegalTree", l);
	}

	@Override
	public List<Legal> getLegalTrees(Legal p) {
		return sqlSession.selectList("legal.getLegalTrees", p);
	}
	
	@Override
	public void addLegal(Legal l) {
		sqlSession.insert("legal.addLegal", l);
	}

	@Override
	public void editLegal(Legal l) {
		sqlSession.update("legal.editLegal", l);
	}
	
	@Override
	public void delLegal(Legal l) {
		sqlSession.delete("legal.delLegal", l);
	}

	@Override
	public Paramer getLegal(Paramer p) {
		Page<Object> startPage = PageHelper.startPage(p.getPageNum(), p.getPageSize());
		startPage = (Page<Object>) sqlSession.selectList("legal.getLegal", p.getOb());
		p.setPage(startPage);
		return p;
	}
}
