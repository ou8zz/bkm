package com.service;

import java.util.List;

import com.model.Legal;
import com.util.Paramer;

/**
 * @description 法律法规管理
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/08/23
 * @version 1.0
 */
public interface LegalService {
	// legal_tree操作-----------------------------------------------------
	/**
	 * 添加legal数元素
	 * l.pid不为空表示添加该元素下的子元素
	 * @param l
	 */
	void addLegalTree(Legal l);
	
	/**
	 * 编辑legal数元素名称
	 * 如果l.mtype不为空，表示为拖拽节点排序，额外计算节点排序和pid更新
	 * @param l
	 */
	void editLegalTree(Legal l);
	
	/**
	 * 直接删除某个节点元素。
	 * 如果是删除的父节点，则只会删除父节点这一行数据，
	 * 其子节点数据虽不会显示，但是会存在数据库
	 * @param l
	 */
	void delLegalTree(Legal l);
	
	/**
	 * 获取页面ztree需要的legal数结构数据
	 * @param l
	 * @return
	 */
	List<Legal> getLegalTrees(Legal l);
	
	
	// legal操作-----------------------------------------------------------------
	
	/**
	 * 新增legal条目，根据tid对应tree
	 * 每一条目都需要对应的tid
	 * @param l
	 */
	void addLegal(Legal l);
	
	/**
	 * 修改对应的Legal条目内容或者标题
	 * @param l
	 */
	void editLegal(Legal l);
	
	/**
	 * 删除对应的Legal条目
	 * @param l
	 */
	void delLegal(Legal l);
	
	/**
	 * 根据tid获得对应的legal条目内容
	 * @param p
	 * @return
	 */
	Paramer getLegal(Paramer p);
}
