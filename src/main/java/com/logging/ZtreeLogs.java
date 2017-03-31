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
import com.model.Ztree;
import com.util.JsonUtils;
import com.util.SessionUtil;

/**
 * @description 菜单管理日志
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/08/18
 * @version 1.0
 */
@Aspect
@Repository("ztreeLogs")
public class ZtreeLogs {
	private Log log = LogFactory.getLog(ZtreeLogs.class);
	private final String FUNC_NAME = "菜单管理";
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private SqlSession sqlSession;
	
	/**
	 * 在方法结束后记录新增日志
	 * @param jp
	 */
	@After("execution(public * com.service.impl.ZtreeServiceImp.add*(..))")
	public void afterAddLogs(JoinPoint jp) {
		try {
			Guser user = SessionUtil.getUser(session);
			Object[] args = jp.getArgs(); // 获得参数列表
			if (args.length <= 0) {
				log.info(jp.getSignature().getName() + "方法没有参数");
			} else {
				Ztree ob = (Ztree) args[0];
				List<Ztree> obs = sqlSession.selectList("ztree.getZtrees", ob);
				ob = obs.get(0);
				Logs logs = new Logs();
				logs.setCmode(OperationMode.ADD);
				logs.setCfunc(FUNC_NAME);
				logs.setTitle("新增 \""+ob.getTitle()+"\"");
				logs.setUserid(user.getId());
				logs.setUname(user.getCname());
				logs.setCid(ob.getId());
				logs.setContent(JsonUtils.toJson(ob));
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
	@After("execution(public * com.service.impl.ZtreeServiceImp.edit*(..))")
	public void afterEditLogs(JoinPoint jp) {
		try {
			Guser user = SessionUtil.getUser(session);
			Object[] args = jp.getArgs(); // 获得参数列表
			if (args.length <= 0) {
				log.info(jp.getSignature().getName() + "方法没有参数");
			} else {
				Ztree object = (Ztree) args[0];
				Logs logs = new Logs();
				logs.setCmode(OperationMode.EDIT);
				logs.setCfunc(FUNC_NAME);
				logs.setTitle("修改了 \""+object.getTitle()+"\"");
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
	@Before("execution(public * com.service.impl.ZtreeServiceImp.del*(..))")
	public void afterDeleteLogs(JoinPoint jp) {
		try {
			Guser user = SessionUtil.getUser(session);
			Object[] args = jp.getArgs(); // 获得参数列表
			if (args.length <= 0) {
				log.info(jp.getSignature().getName() + "方法没有参数");
			} else {
				Integer id = (Integer) args[0];
				Ztree ob = new Ztree(id);
				ob = sqlSession.selectOne("ztree.getZtrees", ob);
				Logs logs = new Logs();
				logs.setCmode(OperationMode.DELETE);
				logs.setCfunc(FUNC_NAME);
				logs.setTitle("删除了 \""+ob.getTitle()+"\"");
				logs.setUserid(user.getId());
				logs.setUname(user.getCname());
				logs.setCid(id);
				logs.setContent(JsonUtils.toJson(ob));
				sqlSession.insert("logs.addLogs", logs);
			}
		} catch (Exception e) {
			log.error("记录日志错误", e);
		}
	}
}
