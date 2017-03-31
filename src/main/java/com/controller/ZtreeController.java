package com.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.Ztree;
import com.service.ZtreeService;
import com.util.Paramer;
import com.util.RespsonData;
import com.util.SessionUtil;

/**
 * @description 后台菜单管理
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/05/29
 * @version 1.0
 */
@Controller
@Scope(value="request")
public class ZtreeController {
	private Log log = LogFactory.getLog(ZtreeController.class);
	private RespsonData rd = new RespsonData("success");			// 通用返回JSON对象
	
	@Resource(name="ztreeService")
	private ZtreeService ztreeService;
	
	/**
	 * 用户登录后左侧菜单加载菜单树（mybatis有缓存）
	 * @param session uid
	 * @return json menu
	 */
	@RequestMapping(method=RequestMethod.POST, value={"/admin/ztree", "/app/ztree"})
	@ResponseBody
	public Object getZtreeByUserId(HttpServletRequest req, HttpSession session) {
		try {
			List<Ztree> ztrees = ztreeService.getZtreeByUserId(SessionUtil.getUserId(session));
			return ztrees;
		} catch (Exception e) {
			log.error("getZtreeByUserId error", e);
		}
		return null;
	}
	
	/**
	 * 菜单后台管理页面
	 * @return json menu
	 */
	@PreAuthorize("hasRole('Admin')")
	@RequestMapping(method=RequestMethod.GET, value="/admin/ztree")
	public String getZtreePage(Model m) {
		//默认把图标加载到页面,暂时没有分页处理,默认500条数据
		Paramer p = new Paramer();
		p.setPageNum(1);
		p.setPageSize(1500);
		p = ztreeService.getIco(p);
		m.addAttribute("paramer", p);
		return "admin/ztree";
	}
	
	/**
	 * 菜单后台管理页面（新版）
	 * @return json menu
	 */
	@PreAuthorize("hasRole('Admin')")
	@RequestMapping(method=RequestMethod.GET, value="/admin/menus")
	public String getMenusPage(Model m) {
		//默认把图标加载到页面,暂时没有分页处理,默认500条数据
		Paramer p = new Paramer();
		p.setPageNum(1);
		p.setPageSize(1500);
		p = ztreeService.getIco(p);
		m.addAttribute("paramer", p);
		return "admin/menus";
	}
	
	/**
	 * 菜单树加载
	 * @return json menu
	 */
	@RequestMapping(method=RequestMethod.POST, value="/admin/getZtrees")
	@ResponseBody
	public Object getZtrees(@ModelAttribute Paramer p, @ModelAttribute Ztree z) {
		try {
			p.setOb(z);
			p = ztreeService.getZtrees(p);
		} catch (Exception e) {
			log.error("getZtrees error", e);
		}
		return p;
	}
	
	/**
	 * 菜单树加载(新版本)
	 * @return json menu
	 */
	@RequestMapping(method=RequestMethod.POST, value="/admin/getMenus")
	@ResponseBody
	public Object getMenus(@ModelAttribute Ztree z) {
		try {
			return ztreeService.getZtrees(z);
		} catch (Exception e) {
			log.error("getZtrees error", e);
		}
		return null;
	}
	
	/**
	 * ztree角色配置菜单
	 * @return json menu
	 */
	@PreAuthorize("hasRole('Admin')")
	@RequestMapping(method=RequestMethod.POST, value="/admin/getRoleZtrees")
	@ResponseBody
	public Object getRoleZtrees(@RequestParam Integer id, @RequestParam boolean b) {
		try {
			return ztreeService.getRoleZtrees(id, b);
		} catch (Exception e) {
			log.error("getRoleZtrees error", e);
		}
		return rd;
	}
	
	/**
	 * add ztree
	 * @return
	 */
	@PreAuthorize("hasRole('Admin')")
	@RequestMapping(method=RequestMethod.POST, value="/admin/addZtree")
	@ResponseBody
	public Object addZtree(@ModelAttribute Ztree z) {
		try {
			ztreeService.addZtree(z);
		} catch(Exception e) {
			log.error("addZtree error", e);
			rd.result("faild", e.getMessage());
		}
		return rd;
	}
	
	/**
	 * edit ztree
	 * @return
	 */
	@PreAuthorize("hasRole('Admin')")
	@RequestMapping(method=RequestMethod.POST, value="/admin/editZtree")
	@ResponseBody
	public Object editZtree(@ModelAttribute Ztree z) {
		try {
			ztreeService.editZtree(z);
		} catch(Exception e) {
			log.error("editZtree error", e);
			rd.result("faild", e.getMessage());
		}
		return rd;
	}
	
	/**
	 * delete ztree
	 * @return
	 */
	@PreAuthorize("hasRole('Admin')")
	@RequestMapping(method=RequestMethod.POST, value="/admin/delZtree")
	@ResponseBody
	public Object delZtree(@RequestParam Integer id) {
		try {
			ztreeService.delZtree(id);
		} catch(Exception e) {
			log.error("delZtree error", e);
			rd.result("faild", e.getMessage());
		}
		return rd;
	}
}
