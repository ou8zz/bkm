package com.controller;

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

import com.constant.MoType;
import com.model.Moinfo;
import com.service.MoinfoService;
import com.util.Paramer;
import com.util.RespsonData;

/**
 * @description 机构信息,托管人信息等维护
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/08/21
 * @version 1.0
 */
@Controller
@Scope(value="request")
@PreAuthorize("hasAnyRole('Admin', 'Audit')")
public class MoinfoController {
	private Log log = LogFactory.getLog(MoinfoController.class);
	private RespsonData rd = new RespsonData("success");			// 通用返回JSON对象
	
	@Resource(name="moinfoService")
	private MoinfoService moinfoService;
	
	/**
	 * 进入托管人信息维护
	 * @param model
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET, value={"/audit/custodian"})
	public Object custodian(Model model) {
		model.addAttribute("ctype", MoType.CUSTODIAN);
		model.addAttribute("ctypeDes", MoType.CUSTODIAN.getDes());
		return "audit/moinfo";
	}
	
	/**
	 * 进入机构信息维护
	 * @param model
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET, value={"/audit/brokerage"})
	public Object brokerage(Model model) {
		model.addAttribute("ctype", MoType.BROKERAGE);
		model.addAttribute("ctypeDes", MoType.BROKERAGE.getDes());
		return "audit/moinfo";
	}
	
	/**
	 * 获取机构信息,托管人信息列表
	 * @param paramer 分页对象
	 * @param fundManagers
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, value="/audit/getMoinfo")
	@ResponseBody
	public Object getMoinfo(@ModelAttribute Paramer p, @ModelAttribute Moinfo m) {
		try {
			p.setOb(m);
			Paramer pt = moinfoService.getMoinfo(p);
			return pt;
		} catch (Exception e) {
			log.error("getMoinfo error", e);
			rd.result("faild", e.getMessage());
		}
		return rd;
	}
	
	/**
	 * 添加机构信息,托管人信息
	 * @param Section
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, value="/audit/addMoinfo")
	@ResponseBody
	public Object addMoinfo(@ModelAttribute Moinfo m) {
		try {
			moinfoService.addMoinfo(m);
			return rd;
		} catch (Exception e) {
			log.error("addMoinfo error", e);
			rd.result("faild", e.getMessage());
		}
		return rd;
	}
	
	/**
	 * 编辑机构信息,托管人信息
	 * @param Section
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, value="/audit/editMoinfo")
	@ResponseBody
	public Object editMoinfo(@ModelAttribute Moinfo m) {
		try {
			moinfoService.editMoinfo(m);
			return rd;
		} catch (Exception e) {
			log.error("editMoinfo error", e);
			rd.result("faild", e.getMessage());
		}
		return rd;
	}
	
	/**
	 * 删除机构信息,托管人信息
	 * @param Section
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, value="/audit/delMoinfo")
	@ResponseBody
	public Object delMoinfo(@ModelAttribute Moinfo m) {
		try {
			moinfoService.delMoinfo(m);
			return rd;
		} catch (Exception e) {
			log.error("delMoinfo error", e);
			rd.result("faild", e.getMessage());
		}
		return rd;
	}
}
