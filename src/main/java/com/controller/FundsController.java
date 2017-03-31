package com.controller;

import java.util.Map;
import java.util.TreeMap;

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

import com.constant.PositionType;
import com.model.FundInfo;
import com.model.FundManagers;
import com.service.FundsService;
import com.util.Paramer;
import com.util.RespsonData;

/**
 * @description 基金
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/08/09
 * @version 1.0
 */
@Controller
@Scope(value="request")
@PreAuthorize("hasAnyRole('Admin', 'Audit')")
public class FundsController {
	private Log log = LogFactory.getLog(FundsController.class);
	private RespsonData rd = new RespsonData("success");			// 通用返回JSON对象
	
	@Resource(name="fundsService")
	private FundsService fundsService;
	
	/**
	 * 进入基金经理配置页面
	 * @param model
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET, value={"/audit/fundManagers"})
	public Object fundManagers(Model model) {
		PositionType[] values = PositionType.values();
		Map<PositionType, Object> mo = new TreeMap<PositionType, Object>();
		for(PositionType om : values) {
			mo.put(om, om.getDes());
		}
		model.addAttribute("ctype", mo);
		return "audit/fund_managers";
	}
	
	/**
	 * 获取基金经理配置列表
	 * @param paramer 分页对象
	 * @param fundManagers
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, value="/audit/getFundManagers")
	@ResponseBody
	public Object getFundManagers(@ModelAttribute Paramer p, @ModelAttribute FundManagers fundManagers) {
		try {
			p.setOb(fundManagers);
			Paramer pt = fundsService.getFundManagers(p);
			return pt;
		} catch (Exception e) {
			log.error("getFundManagers error", e);
			rd.result("faild", e.getMessage());
		}
		return rd;
	}
	
	/**
	 * 添加基金经理
	 * @param Section
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, value="/audit/addFundManagers")
	@ResponseBody
	public Object addFundManagers(@ModelAttribute FundManagers fm) {
		try {
			fundsService.addFundManagers(fm);
			return rd;
		} catch (Exception e) {
			log.error("addFundManagers error", e);
			rd.result("faild", e.getMessage());
		}
		return rd;
	}
	
	/**
	 * 编辑基金经理
	 * @param Section
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, value="/audit/editFundManagers")
	@ResponseBody
	public Object editFundManagers(@ModelAttribute FundManagers fm) {
		try {
			fundsService.editFundManagers(fm);
			return rd;
		} catch (Exception e) {
			log.error("editFundManagers error", e);
			rd.result("faild", e.getMessage());
		}
		return rd;
	}
	
	/**
	 * 删除基金经理
	 * @param Section
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, value="/audit/delFundManagers")
	@ResponseBody
	public Object delFundManagers(@ModelAttribute FundManagers fm) {
		try {
			fundsService.delFundManagers(fm);
			return rd;
		} catch (Exception e) {
			log.error("delFundManagers error", e);
			rd.result("faild", e.getMessage());
		}
		return rd;
	}
	
	/**
	 * 获取基金产品信息列表
	 * @param paramer 分页对象
	 * @param fundInfo
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, value="/audit/getFundInfo")
	@ResponseBody
	public Object getFundInfo(@ModelAttribute Paramer p, @ModelAttribute FundInfo fi) {
		try {
			p.setOb(fi);
			Paramer pt = fundsService.getFundInfo(p);
			return pt;
		} catch (Exception e) {
			log.error("getFundInfo error", e);
			rd.result("faild", e.getMessage());
		}
		return rd;
	}
	
	/**
	 * 添加基金经理
	 * @param Section
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, value="/audit/addFundInfo")
	@ResponseBody
	public Object addFundInfo(@ModelAttribute FundInfo fi) {
		try {
			fundsService.addFundInfo(fi);
			return rd;
		} catch (Exception e) {
			log.error("addFundInfo error", e);
			rd.result("faild", e.getMessage());
		}
		return rd;
	}
	
	/**
	 * 编辑基金经理
	 * @param Section
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, value="/audit/editFundInfo")
	@ResponseBody
	public Object editFundInfo(@ModelAttribute FundInfo fi) {
		try {
			fundsService.editFundInfo(fi);
			return rd;
		} catch (Exception e) {
			log.error("editFundInfo error", e);
			rd.result("faild", e.getMessage());
		}
		return rd;
	}
	
	/**
	 * 删除基金经理
	 * @param Section
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, value="/audit/delFundInfo")
	@ResponseBody
	public Object delFundInfo(@ModelAttribute FundInfo fi) {
		try {
			fundsService.delFundInfo(fi);
			return rd;
		} catch (Exception e) {
			log.error("delFundInfo error", e);
			rd.result("faild", e.getMessage());
		}
		return rd;
	}
}
