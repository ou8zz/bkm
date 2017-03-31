package com.logging;

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
import com.util.JsonUtils;
import com.util.SessionUtil;

/**
 * @description 记录用户日志
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/08/03
 * @version 1.0
 */
@Aspect
@Repository("guserLogs")
public class GuserLogs {
	private Log log = LogFactory.getLog(GuserLogs.class);
	private final String FUNC_NAME = "用户信息";
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private SqlSession sqlSession;
	
	/**
	 * 在方法结束后记录新增日志
	 * @param jp
	 */
	@After("execution(public * com.service.impl.GuserServiceImp.add*(..))")
	public void afterAddLogs(JoinPoint jp) {
		try {
			Guser user = SessionUtil.getUser(session);
			Object[] args = jp.getArgs(); // 获得参数列表
			if (args.length <= 0) {
				log.info(jp.getSignature().getName() + "方法没有参数");
			} else {
				Guser object = (Guser) args[0];
				object = sqlSession.selectOne("guser.getGuserDetil", new Guser(object.getEname()));
				Logs logs = new Logs();
				logs.setCmode(OperationMode.ADD);
				logs.setCfunc(FUNC_NAME);
				logs.setTitle("新增了用户\""+object.getCname()+"\"");
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
	 * 在方法结束后记录修改日志
	 * @param jp
	 */
	@After("execution(public * com.service.impl.GuserServiceImp.edit*(..))")
	public void afterEditLogs(JoinPoint jp) {
		try {
			Guser user = SessionUtil.getUser(session);
			Object[] args = jp.getArgs(); // 获得参数列表
			if (args.length <= 0) {
				log.info(jp.getSignature().getName() + "方法没有参数");
			} else {
				Guser object = (Guser) args[0];
				Guser selectOne = sqlSession.selectOne("guser.getGuserDetil", new Guser(object.getId()));
				Logs logs = new Logs();
				logs.setCmode(OperationMode.EDIT);
				logs.setCfunc(FUNC_NAME);
				logs.setTitle("修改了\"" + selectOne.getCname()+"\"信息");
				logs.setUserid(user.getId());
				logs.setUname(user.getCname());
				logs.setCid(selectOne.getId());
				logs.setContent(JsonUtils.toJson(selectOne));
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
	@Before("execution(public * com.service.impl.GuserServiceImp.del*(..))")
	public void afterDeleteLogs(JoinPoint jp) {
		try {
			Guser user = SessionUtil.getUser(session);
			Object[] args = jp.getArgs(); // 获得参数列表
			if (args.length <= 0) {
				log.info(jp.getSignature().getName() + "方法没有参数");
			} else {
				Integer[] ids = (Integer[]) args[0];
				for(Integer id : ids) {
					Guser object = sqlSession.selectOne("guser.getGuserDetil", new Guser(id));
					Logs logs = new Logs();
					logs.setCmode(OperationMode.DELETE);
					logs.setCfunc(FUNC_NAME);
					logs.setTitle("删除了\"" + object.getCname()+"\"信息" );
					logs.setUserid(user.getId());
					logs.setUname(user.getCname());
					logs.setCid(id);
					logs.setContent(JsonUtils.toJson(object));
					sqlSession.insert("logs.addLogs", logs);
				}
			}
		} catch (Exception e) {
			log.error("记录日志错误", e);
		}
	}
}
