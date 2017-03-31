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
import com.model.Ztree;
import com.service.ZtreeService;
import com.util.Paramer;
import com.util.RegexUtil;

/**
 * @description 后台菜单管理
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/05/29
 * @version 1.0
 */
@Repository(value="ztreeService")
@Scope(value="prototype")
@Transactional(propagation=Propagation.REQUIRED)
public class ZtreeServiceImp implements ZtreeService {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void addZtree(Ztree ztree) {
		sqlSession.insert("ztree.addZtree", ztree);
	}

	@Override
	public void editZtree(Ztree ztree) {
		// 如果node有传值表示是拖拽节点，拖拽分别为 拖拽到某个节点的上面 下面 里面  对应传值对象mtype=prev, next, inner
		if(RegexUtil.notEmpty(ztree) && RegexUtil.notEmpty(ztree.getMtype())) {
			String type = ztree.getMtype();
			Integer next=0, prev=0;
			if("next".equals(type)) {
				// 如果next表示拖拽到当前节点的下面，找到当前节点的下一个节点数做计算中间值即可，如果当前节点是最后一个节点则当前节点+5000
				prev = ztree.getNode();
				next = sqlSession.selectOne("ztree.getNextNode", ztree);
				next = RegexUtil.isEmpty(next) ? prev+5000 : next;
 				ztree.setNode((next-prev)/2+prev);
			} else if("prev".equals(type)) {
				// 如果prev表示拖拽到当前节点的上面，找到当前节点的上一个节点数做计算中间值即可，如果当前节点是第一个节点则当前节点设置0
				next = ztree.getNode();
				prev = sqlSession.selectOne("ztree.getPrevNode", ztree);
				prev = RegexUtil.isEmpty(prev) ? 0 : prev;
				ztree.setNode((next-prev)/2+prev);
			} else if("inner".equals(type) && RegexUtil.notEmpty(ztree.getTid())) {
				// 如果inner表示拖拽到当前节点的里面，找到当前节点ID设置为pid即可，设置node值为当前节点*1000（尽量大于当前节点即可）
				Ztree lg = new Ztree(ztree.getTid());
				lg = sqlSession.selectOne("ztree.getZtrees", lg);
				ztree.setpId(lg.getId());
				ztree.setNode(ztree.getTid()*1000);
			}
			sqlSession.update("ztree.editZtreeSequence", ztree);
		} else {
			sqlSession.update("ztree.editZtree", ztree);
		}
	}
	
	@Override
	public void delZtree(Integer id) {
		sqlSession.delete("ztree.delZtree", id);
	}

	@Override
	public Paramer getZtrees(Paramer p) {
		Page<Object> startPage = PageHelper.startPage(p.getPageNum(), p.getPageSize());
		startPage = (Page<Object>) sqlSession.selectList("ztree.getZtrees", p.getOb());
		p.setPage(startPage);
		return p;
	}
	
	@Override
	public List<Ztree> getZtrees(Ztree z) {
		List<Ztree> zs = sqlSession.selectList("ztree.getZtrees", z);
		return zs;
	}
	
	@Override
	public List<Ztree> getRoleZtrees(Integer id, boolean b) {
		if(b) {
			List<Ztree> ztrees = sqlSession.selectList("ztree.getRoleZtrees", id);
			return ztrees;
		}
		List<Ztree> ztrees = sqlSession.selectList("ztree.getRoleUserZtrees", id);
		return ztrees;
	}
	
	@Override
	public Paramer getIco(Paramer p) {
		Page<Object> startPage = PageHelper.startPage(p.getPageNum(), p.getPageSize());
		startPage = (Page<Object>) sqlSession.selectList("ztree.getIco");
		p.getPageTag(startPage);
		return p;
	}

	@Override
	public List<Ztree> getZtreeByUserId(Integer uid) {
		List<Ztree> ztrees = sqlSession.selectList("ztree.getZtreeByUserId", uid);
		return ztrees;
	}

}
