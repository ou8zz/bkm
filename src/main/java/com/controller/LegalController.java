package com.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.Legal;
import com.service.LegalService;
import com.util.Paramer;
import com.util.RespsonData;

/**
 * @description 法律法规管理
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/08/23
 * @version 1.0
 */
@Controller
@Scope(value="request")
public class LegalController {
	private Log log = LogFactory.getLog(LegalController.class);
	private RespsonData rd = new RespsonData("success");			// 通用返回JSON对象
	
	@Resource(name="legalService")
	private LegalService legalService;
	
	/**
	 * 后台配置页面
	 */
	@PreAuthorize("hasRole('Admin')")
	@RequestMapping(method=RequestMethod.GET, value="/audit/legalConfig")
	public String getLegalTreePage(Model m) {
		return "audit/legal_config";
	}
	
	/**
	 * ztree树result
	 * @return json menu
	 */
	@RequestMapping(method=RequestMethod.POST, value={"/audit/getLegalTrees"})
	@ResponseBody
	public Object getLegalTrees(@ModelAttribute Legal l) {
		try {
			List<Legal> legals = legalService.getLegalTrees(l);
			return legals;
		} catch (Exception e) {
			log.error("getLegalTrees error", e);
		}
		return null;
	}
	
	/**
	 * add LegalTree
	 * @return
	 */
	@PreAuthorize("hasRole('Admin')")
	@RequestMapping(method=RequestMethod.POST, value="/audit/addLegalTree")
	@ResponseBody
	public Object addLegalTree(@ModelAttribute Legal l) {
		try {
			legalService.addLegalTree(l);
		} catch(Exception e) {
			log.error("addLegalTree error", e);
			rd.result("faild", e.getMessage());
		}
		return rd;
	}
	
	/**
	 * edit LegalTree
	 * @return
	 */
	@PreAuthorize("hasRole('Admin')")
	@RequestMapping(method=RequestMethod.POST, value="/audit/editLegalTree")
	@ResponseBody
	public Object editLegalTree(@ModelAttribute Legal l) {
		try {
			legalService.editLegalTree(l);
		} catch(Exception e) {
			log.error("editLegalTree error", e);
			rd.result("faild", e.getMessage());
		}
		return rd;
	}
	
	/**
	 * delete LegalTree
	 * @return
	 */
	@PreAuthorize("hasRole('Admin')")
	@RequestMapping(method=RequestMethod.POST, value="/audit/delLegalTree")
	@ResponseBody
	public Object delLegalTree(@ModelAttribute Legal l) {
		try {
			legalService.delLegalTree(l);
		} catch(Exception e) {
			log.error("delLegalTree error", e);
			rd.result("faild", e.getMessage());
		}
		return rd;
	}
	
	/**
	 * get Legals
	 * @param p
	 * @param l
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, value="/audit/getLegals")
	@ResponseBody
	public Object getLegals(@ModelAttribute Paramer p, @ModelAttribute Legal l) {
		try {
			p.setOb(l);
			Paramer pt = legalService.getLegal(p);
			return pt;
		} catch (Exception e) {
			log.error("getLegals error", e);
			rd.result("faild", e.getMessage());
		}
		return rd;
	}
	
	/**
	 * add LegalTree
	 * @return
	 */
	@PreAuthorize("hasRole('Admin')")
	@RequestMapping(method=RequestMethod.POST, value="/audit/addLegal")
	@ResponseBody
	public Object addLegal(@ModelAttribute Legal l) {
		try {
			legalService.addLegal(l);
		} catch(Exception e) {
			log.error("addLegal error", e);
			rd.result("faild", e.getMessage());
		}
		return rd;
	}
	
	/**
	 * edit Legal
	 * @return
	 */
	@PreAuthorize("hasRole('Admin')")
	@RequestMapping(method=RequestMethod.POST, value="/audit/editLegal")
	@ResponseBody
	public Object editLegal(@ModelAttribute Legal l) {
		try {
			legalService.editLegal(l);
		} catch(Exception e) {
			log.error("editLegal error", e);
			rd.result("faild", e.getMessage());
		}
		return rd;
	}
	
	/**
	 * delete Legal
	 * @return
	 */
	@PreAuthorize("hasRole('Admin')")
	@RequestMapping(method=RequestMethod.POST, value="/audit/delLegal")
	@ResponseBody
	public Object delLegal(@ModelAttribute Legal l) {
		try {
			legalService.delLegal(l);
		} catch(Exception e) {
			log.error("delLegal error", e);
			rd.result("faild", e.getMessage());
		}
		return rd;
	}
}
