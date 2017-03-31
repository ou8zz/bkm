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
import com.model.Ugroup;
import com.util.JsonUtils;
import com.util.SessionUtil;

/**
 * @description 记录用户权限日志
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/08/05
 * @version 1.0
 */
@Aspect
@Repository("ugroupLogs")
public class UgroupLogs {
	private Log log = LogFactory.getLog(UgroupLogs.class);
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private SqlSession sqlSession;
	
	/**
	 * 在方法结束后记录新增日志
	 * @param jp
	 */
	@After("execution(public * com.service.impl.AdminServiceImp.add*(..))")
	public void afterAddLogs(JoinPoint jp) {
		try {
			Guser user = SessionUtil.getUser(session);
			Object[] args = jp.getArgs(); // 获得参数列表
			if (args.length <= 0) {
				log.info(jp.getSignature().getName() + "方法没有参数");
			} else {
				Ugroup ugroup = (Ugroup) args[0];
				List<Ugroup> ugroups = sqlSession.selectList("ugroup.getUgroups", ugroup);
				ugroup = ugroups.get(0);
				Logs logs = new Logs();
				logs.setCmode(OperationMode.ADD);
				logs.setCfunc(ugroup.getGtype().getDes()+"管理");
				logs.setTitle("新增了\""+ugroup.getGtype().getDes()+"\" \""+ugroup.getCname()+"\"");
				logs.setUserid(user.getId());
				logs.setUname(user.getCname());
				logs.setCid(ugroup.getId());
				logs.setContent(JsonUtils.toJson(ugroup));
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
	@After("execution(public * com.service.impl.AdminServiceImp.edit*(..))")
	public void afterEditLogs(JoinPoint jp) {
		try {
			Guser user = SessionUtil.getUser(session);
			Object[] args = jp.getArgs(); // 获得参数列表
			if (args.length <= 0) {
				log.info(jp.getSignature().getName() + "方法没有参数");
			} else {
				Ugroup object = (Ugroup) args[0];
				Logs logs = new Logs();
				logs.setCmode(OperationMode.EDIT);
				logs.setCfunc(object.getGtype().getDes()+"管理");
				logs.setTitle("修改了\""+object.getGtype().getDes()+"\" \""+object.getCname()+"\"");
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
	@Before("execution(public * com.service.impl.AdminServiceImp.del*(..))")
	public void afterDeleteLogs(JoinPoint jp) {
		try {
			Guser user = SessionUtil.getUser(session);
			Object[] args = jp.getArgs(); // 获得参数列表
			if (args.length <= 0) {
				log.info(jp.getSignature().getName() + "方法没有参数");
			} else {
				Integer id = (Integer) args[0];
				Ugroup ugroup = new Ugroup(id);
				ugroup = sqlSession.selectOne("ugroup.getUgroups", ugroup);
				Logs logs = new Logs();
				logs.setCmode(OperationMode.DELETE);
				logs.setCfunc(ugroup.getGtype().getDes()+"管理");
				logs.setTitle("删除了\""+ugroup.getGtype().getDes()+"\" \""+ugroup.getCname()+"\"");
				logs.setUserid(user.getId());
				logs.setUname(user.getCname());
				logs.setCid(id);
				logs.setContent(JsonUtils.toJson(ugroup));
				sqlSession.insert("logs.addLogs", logs);
			}
		} catch (Exception e) {
			log.error("记录日志错误", e);
		}
	}
	
	/**
	 * 修改用户权限日志
	 * @param jp
	 */
	@After("execution(public * com.service.impl.AdminServiceImp.saveRoleZtree(..))")
	public void afterSaveRoleZtree(JoinPoint jp) {
		try {
			Guser user = SessionUtil.getUser(session);
			Object[] args = jp.getArgs(); // 获得参数列表
			if (args.length <= 0) {
				log.info(jp.getSignature().getName() + "方法没有参数");
			} else {
				Ugroup param = (Ugroup) args[0];
				Ugroup ug = sqlSession.selectOne("ugroup.getUgroups", param);
				List<String> znames = sqlSession.selectList("ztree.getZtreesByLog", param);
				ug.setZnames(znames);
				Logs logs = new Logs();
				logs.setCmode(OperationMode.EDIT);
				logs.setCfunc(ug.getGtype().getDes()+"管理");
				logs.setTitle("修改了 \""+ug.getCname()+"\"权限");
				logs.setUserid(user.getId());
				logs.setUname(user.getCname());
				logs.setCid(ug.getId());
				logs.setContent(JsonUtils.toJson(ug));
				sqlSession.insert("logs.addLogs", logs);
			}
		} catch (Exception e) {
			log.error("记录日志错误", e);
		}
	}
}
