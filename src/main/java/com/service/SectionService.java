package com.service;

import com.model.Section;
import com.util.Paramer;

/**
 * @description 章节接口
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/08/06
 * @version 1.0
 */
public interface SectionService {
	
	/**
	 * 新增配置
	 * @param sc
	 */
	void addSectionConfig(Section sc);
	
	/**
	 * 修改配置
	 * 包括拖拽配置进行排序功能
	 * @param sc
	 */
	void editSectionConfig(Section sc);
	
	/**
	 * 删除配置（备用接口。目前没有删除配置功能）
	 * @param sc
	 */
	void delSectionConfig(Section sc);
	
	/**
	 * 界面分页查询配置
	 * @param p
	 * @return p
	 */
	Paramer getSectionConfig(Paramer p);
	
	/**
	 * 单个查询配置
	 * @param sc
	 * @return sc
	 */
	Section getSectionConfig(Section sc);
	
	/**
	 * 导出配置
	 * @param sc
	 * @return html
	 */
	Object expSectionConfig(Section sc);
}
