package com.controller;

import java.util.Calendar;
import java.util.Date;
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

import com.constant.OperationMode;
import com.model.Logs;
import com.service.AdminService;
import com.service.GuserService;
import com.service.LogsService;
import com.util.Paramer;
import com.util.RegexUtil;
import com.util.RespsonData;

/**
 * @description 后台日志管理
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/07/28
 * @version 1.0
 */
@Controller
@Scope(value="request")
@PreAuthorize("hasAnyRole('Admin', 'Audit')")
public class LogsController {
	private Log log = LogFactory.getLog(LogsController.class);
	private RespsonData rd = new RespsonData("success");			// 通用返回JSON对象
	
	@Resource(name="guserService")
	private GuserService guserService;
	
	@Resource(name="adminService")
	private AdminService adminService;
	
	@Resource(name="logsService")
	private LogsService logsService;
	
	/**
	 * 进入日志管理界面
	 * 在进入页面时日志操作类型的枚举
	 * @param model
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET, value={"/app/logs", "/admin/logs"})
	public Object logs(Model model) {
		OperationMode[] values = OperationMode.values();
		Map<OperationMode, Object> mo = new TreeMap<OperationMode, Object>();
		for(OperationMode om : values) {
			mo.put(om, om.getDes());
		}
		model.addAttribute("oms", mo);
		return "admin/logs";
	}
	
	/**
	 * 获取日志列表
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, value="/admin/getLogs")
	@ResponseBody
	public Object getLogs(@ModelAttribute Paramer p, @ModelAttribute Logs l) {
		try {
			// 如果是前台查询，必须使用的日期是短字段格式，而且如果是结束日期，需要包含当天的数据，所以需要设置结束时间的时分秒为23:59:59
			Date endTimeShort = l.getEndTimeShort();
			if(RegexUtil.notEmpty(endTimeShort)) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(endTimeShort);
				cal.set(Calendar.HOUR, 23);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);
				l.setEndTime(cal.getTime());
				l.setStartTime(l.getStartTimeShort());
			}
			p.setOb(l);
			Paramer pt = logsService.getLogs(p);
			return pt;
		} catch (Exception e) {
			log.error("getLogs error", e);
			rd.result("faild", e.getMessage());
		}
		return rd;
	}
}
