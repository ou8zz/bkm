package com.controller;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.model.Section;
import com.service.SectionService;
import com.util.Paramer;
import com.util.RegexUtil;
import com.util.RespsonData;

/**
 * @description 章节配置
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/08/06
 * @version 1.0
 */
@Controller
@Scope(value="request")
@PreAuthorize("hasAnyRole('Admin', 'Audit')")
public class SectionController {
	private Log log = LogFactory.getLog(SectionController.class);
	private RespsonData rd = new RespsonData("success");			// 通用返回JSON对象
	
	@Resource(name="sectionService")
	private SectionService sectionService;
	
	/**
	 * 进入章节配置页面
	 * @param model
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET, value={"/audit/sectionConfig"})
	public Object sectionConfig(Model model) {
		
		return "audit/section_config";
	}
	
	/**
	 * 获取章节配置列表
	 * @param paramer 分页对象
	 * @param Section
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, value="/audit/getSectionConfig")
	@ResponseBody
	public Object getSectionConfig(@ModelAttribute Paramer p, @ModelAttribute Section sc) {
		try {
			p.setOb(sc);
			Paramer pt = sectionService.getSectionConfig(p);
			return pt;
		} catch (Exception e) {
			log.error("getSectionConfig error", e);
			rd.result("faild", e.getMessage());
		}
		return rd;
	}
	
	/**
	 * 导出章节配置
	 * @param Section
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/audit/expSectionConfig")
	public Object expSectionConfig(@ModelAttribute Section sc, HttpServletRequest req, HttpServletResponse res) {
		PrintWriter writer = null;
		try {
			res.setHeader("Content-Disposition", "attachment; filename="+RegexUtil.encodingFileName("导出.doc", req));
	        res.setContentType("application/vnd.ms-word; charset=utf-8");
			writer = res.getWriter();
			String pt = (String)sectionService.expSectionConfig(sc);
			writer.write(pt);
		} catch (Exception e) {
			log.error("expSectionConfig error", e);
			rd.result("faild", e.getMessage());
		} finally {
			if(RegexUtil.notEmpty(writer)) {
				writer.flush();
				writer.close();
			}
		}
		return rd;
	}
	
	/**
	 * 添加章节配置
	 * @param Section
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, value="/audit/addSectionConfig")
	@ResponseBody
	public Object addSectionConfig(@ModelAttribute Section sc) {
		try {
			sectionService.addSectionConfig(sc);
			return rd;
		} catch (Exception e) {
			log.error("addSectionConfig error", e);
			rd.result("faild", e.getMessage());
		}
		return rd;
	}
	
	/**
	 * 编辑章节配置
	 * @param Section
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, value="/audit/editSectionConfig")
	@ResponseBody
	public Object editSectionConfig(@ModelAttribute Section sc) {
		try {
			sectionService.editSectionConfig(sc);
			return rd;
		} catch (Exception e) {
			log.error("editSectionConfig error", e);
			rd.result("faild", e.getMessage());
		}
		return rd;
	}
	
	/**
	 * 删除章节配置
	 * @param Section
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, value="/audit/delSectionConfig")
	@ResponseBody
	public Object delSectionConfig(@ModelAttribute Section sc) {
		try {
			sectionService.delSectionConfig(sc);
			return rd;
		} catch (Exception e) {
			log.error("delSectionConfig error", e);
			rd.result("faild", e.getMessage());
		}
		return rd;
	}
}
