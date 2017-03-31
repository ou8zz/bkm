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
import com.model.Section;
import com.service.SectionService;
import com.util.Paramer;
import com.util.RegexUtil;

/**
 * @description 章节接口
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/08/06
 * @version 1.0
 */
@Repository(value="sectionService")
@Scope(value="prototype")
@Transactional(propagation=Propagation.REQUIRED)
public class SectionServiceImp implements SectionService {

	@Autowired
	private SqlSession sqlSession;

	/**
	 * 新增配置
	 */
	@Override
	public void addSectionConfig(Section sc) {
		sqlSession.insert("section.addSectionConfig", sc);
	}

	/**
	 * 编辑配置 
	 */
	@Override
	public void editSectionConfig(Section sc) {
		// 如果node不为空则操作为拖拽排序
		if(RegexUtil.notEmpty(sc) && RegexUtil.notEmpty(sc.getNode())) {
			Integer prev = sc.getPrev();
			Integer next = sc.getNext();
			if(RegexUtil.isEmpty(prev)) {
				prev = sqlSession.selectOne("section.getPrevNode", next);
				if(RegexUtil.isEmpty(prev)) {
					prev = 0;
				}
			} else if(RegexUtil.isEmpty(next)) {
				next = sqlSession.selectOne("section.getNextNode", prev);
				if(RegexUtil.isEmpty(next)) {
					next = prev+500;
				}
			}
			
			// 计算在拖动俩个节点之间的数值
			sc.setNode((next-prev)/2+prev);
		}
		sqlSession.update("section.editSectionConfig", sc);
	}

	/**
	 * 删除配置
	 */
	@Override
	public void delSectionConfig(Section sc) {
		sqlSession.delete("section.delSectionConfig", sc);
	}

	/**
	 * 查询配置
	 */
	@Override
	public Paramer getSectionConfig(Paramer p) {
		Page<Object> startPage = PageHelper.startPage(p.getPageNum(), p.getPageSize());
		startPage = (Page<Object>) sqlSession.selectList("section.getSectionConfig", p.getOb());
		p.setPage(startPage);
		return p;
	}
	
	/**
	 * 导出配置
	 */
	@Override
	public Object expSectionConfig(Section sc) {
		List<Section> list = sqlSession.selectList("section.getSectionConfig", sc);
		StringBuilder ts = new StringBuilder("<html><body>");
		StringBuilder cs = new StringBuilder();
		for(Section s : list) {
			ts.append("<h4>").append(s.getTitle()).append("</h4>");
			cs.append("<h4>").append(s.getTitle()).append("</h4>");
			cs.append("<div>").append(s.getContent()).append("</div>");
			cs.append("<br/>");
		}
		ts.append("<br/>").append(cs).append("</body></html>");
		return ts.toString();
	}

	/**
	 * 查询配置
	 */
	@Override
	public Section getSectionConfig(Section sc) {
		return sqlSession.selectOne("section.getSectionConfig", sc);
	}
}
