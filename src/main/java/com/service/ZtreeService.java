package com.service;

import java.util.List;

import com.model.Ztree;
import com.util.Paramer;

/**
 * @description 后台菜单管理
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/05/29
 * @version 1.0
 */
public interface ZtreeService {
	/**
	 * 用于菜单配置界面
	 * 添加菜单新节点
	 * @param ztree
	 */
	void addZtree(Ztree ztree);
	
	/**
	 * 用于菜单配置界面
	 * 修改菜单，包括拖拽节点位置和顺序
	 * @param ztree
	 */
	void editZtree(Ztree ztree);
	
	/**
	 * 用于菜单配置界面
	 * 删除单个菜单根据ztree.id
	 * @param id
	 */
	void delZtree(Integer id);
	
	/**
	 * 用户菜单配置界面
	 * 获取所有菜单列表，分页实现
	 * @param p
	 * @return
	 */
	Paramer getZtrees(Paramer p);
	
	/**
	 * 用于菜单配置界面(新版本 菜单配置)
	 * 获取所有菜单列表
	 * @param z
	 * @return
	 */
	List<Ztree> getZtrees(Ztree z);
	
	/**
	 * 用户菜单配置界面
	 * 获取所有菜单ico图片列表(图片来源于fa的图片资源库)，分页实现
	 * @param p
	 * @return
	 */
	Paramer getIco(Paramer p);
	
	/**
	 * 用户角色配置界面
	 * 根据角色ID或者当前角色对应的菜单树，b为true表示前台页面支持checked(用户角色配置页面)，false只为用户预览(用户管理配置页面)
	 * @param p
	 * @return
	 */
	List<Ztree> getRoleZtrees(Integer id, boolean b);
	
	/**
	 * 全局左侧菜单获取
	 * 根据当前session中userid获取每一个登录用户对应的菜单树
	 * @param userid
	 * @return
	 */
	List<Ztree> getZtreeByUserId(Integer uid);
}
