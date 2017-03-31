package com.logging;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.constant.OperationMode;
import com.model.Guser;
import com.model.Logs;
import com.model.Moinfo;
import com.util.JsonUtils;
import com.util.SessionUtil;

/**
 * @description 机构信息,托管人信息等维护日志
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/08/21
 * @version 1.0
 */
@Aspect
@Repository("moinfoLogs")
public class MoinfoLogs {
	private Log log = LogFactory.getLog(MoinfoLogs.class);
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private SqlSession sqlSession;
	
	/**
	 * 在方法结束后记录新增日志
	 * @param jp
	 */
	@After("execution(public * com.service.impl.MoinfoServiceImp.add*(..))")
	public void afterAddLogs(JoinPoint jp) {
		try {
			Guser user = SessionUtil.getUser(session);
			Object[] args = jp.getArgs(); // 获得参数列表
			if (args.length <= 0) {
				log.info(jp.getSignature().getName() + "方法没有参数");
			} else {
				Moinfo moinfo = (Moinfo) args[0];
				List<Moinfo> moinfos = sqlSession.selectList("moinfo.getMoinfo", moinfo);
				moinfo = moinfos.get(0);
				Logs logs = new Logs();
				logs.setCmode(OperationMode.ADD);
				logs.setCfunc(moinfo.getCtype().getDes()+"管理");
				logs.setTitle("新增了\""+moinfo.getCtype().getDes()+"\" \""+moinfo.getCname()+"\"");
				logs.setUserid(user.getId());
				logs.setUname(user.getCname());
				logs.setCid(moinfo.getId());
				logs.setContent(JsonUtils.toJson(moinfo));
				sqlSession.insert("logs.addLogs", logs);
			}
		} catch (Exception e) {
			log.error("记录日志错误", e);
		}
	}
	
	/**
	 * 在方法结束后记录修改日志
	 * @param jp
	 */
	@After("execution(public * com.service.impl.MoinfoServiceImp.edit*(..))")
	public void afterEditLogs(JoinPoint jp) {
		try {
			Guser user = SessionUtil.getUser(session);
			Object[] args = jp.getArgs(); // 获得参数列表
			if (args.length <= 0) {
				log.info(jp.getSignature().getName() + "方法没有参数");
			} else {
				Moinfo object = (Moinfo) args[0];
				Logs logs = new Logs();
				logs.setCmode(OperationMode.EDIT);
				logs.setCfunc(object.getCtype().getDes()+"管理");
				logs.setTitle("修改了\""+object.getCtype().getDes()+"\" \""+object.getCname()+"\"");
				logs.setUserid(user.getId());
				logs.setUname(user.getCname());
				logs.setCid(object.getId());
				logs.setContent(JsonUtils.toJson(object));
				sqlSession.insert("logs.addLogs", logs);
			}
		} catch (Exception e) {
			log.error("记录日志错误", e);
		}
	}
	
	/**
	 * 在方法之前记录删除日志
	 * @param jp
	 */
	@Before("execution(public * com.service.impl.MoinfoServiceImp.del*(..))")
	public void afterDeleteLogs(JoinPoint jp) {
		try {
			Guser user = SessionUtil.getUser(session);
			Object[] args = jp.getArgs(); // 获得参数列表
			if (args.length <= 0) {
				log.info(jp.getSignature().getName() + "方法没有参数");
			} else {
				Moinfo moinfo = (Moinfo) args[0];
				moinfo = sqlSession.selectOne("moinfo.getMoinfo", moinfo);
				Logs logs = new Logs();
				logs.setCmode(OperationMode.DELETE);
				logs.setCfunc(moinfo.getCtype().getDes()+"管理");
				logs.setTitle("删除了\""+moinfo.getCtype().getDes()+"\" \""+moinfo.getCname()+"\"");
				logs.setUserid(user.getId());
				logs.setUname(user.getCname());
				logs.setCid(moinfo.getId());
				logs.setContent(JsonUtils.toJson(moinfo));
				sqlSession.insert("logs.addLogs", logs);
			}
		} catch (Exception e) {
			log.error("记录日志错误", e);
		}
	}
}
