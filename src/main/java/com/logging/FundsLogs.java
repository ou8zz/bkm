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
import com.model.FundInfo;
import com.model.FundManagers;
import com.model.Guser;
import com.model.Logs;
import com.util.JsonUtils;
import com.util.RegexUtil;
import com.util.SessionUtil;

/**
 * @description 基金及管理者日志
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/08/17
 * @version 1.0
 */
@Aspect
@Repository("fundsLogs")
public class FundsLogs {
	private Log log = LogFactory.getLog(FundsLogs.class);
	private final String FUNC_NAME = "基金经理信息";
	private final String FUND_USER = "基金经理管理组合";
	private final String FUND_INFO = "基金信息";
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private SqlSession sqlSession;
	
	/**
	 * 在方法结束后记录新增日志
	 * 基金管理人员新增方法
	 * @param jp
	 */
	@After("execution(public * com.service.impl.FundsServiceImp.addFundManagers(..))")
	public void afterAddLogs(JoinPoint jp) {
		try {
			Guser user = SessionUtil.getUser(session);
			Object[] args = jp.getArgs(); // 获得参数列表
			if (args.length <= 0) {
				log.info(jp.getSignature().getName() + "方法没有参数");
			} else {
				FundManagers ob = (FundManagers) args[0];
				List<FundManagers> obs = sqlSession.selectList("funds.getFundManagers", ob);
				ob = obs.get(0);
				Logs logs = new Logs();
				logs.setCmode(OperationMode.ADD);
				logs.setCfunc(FUNC_NAME);
				logs.setTitle("新增 \""+ob.getCname()+"\"");
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
	 * 基金管理人员修改方法
	 * @param jp
	 */
	@After("execution(public * com.service.impl.FundsServiceImp.editFundManagers(..))")
	public void afterEditLogs(JoinPoint jp) {
		try {
			Guser user = SessionUtil.getUser(session);
			Object[] args = jp.getArgs(); // 获得参数列表
			if (args.length <= 0) {
				log.info(jp.getSignature().getName() + "方法没有参数");
			} else {
				FundManagers object = (FundManagers) args[0];
				Logs logs = new Logs();
				logs.setCmode(OperationMode.EDIT);
				logs.setCfunc(FUNC_NAME);
				logs.setTitle("修改了 \""+object.getCname()+"\"");
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
	 * 基金管理人员删除方法
	 * @param jp
	 */
	@Before("execution(public * com.service.impl.FundsServiceImp.delFundManagers(..))")
	public void afterDeleteLogs(JoinPoint jp) {
		try {
			Guser user = SessionUtil.getUser(session);
			Object[] args = jp.getArgs(); // 获得参数列表
			if (args.length <= 0) {
				log.info(jp.getSignature().getName() + "方法没有参数");
			} else {
				Integer id = (Integer) args[0];
				FundManagers ob = new FundManagers(id);
				ob = sqlSession.selectOne("funds.getFundManagers", ob);
				Logs logs = new Logs();
				logs.setCmode(OperationMode.DELETE);
				logs.setCfunc(FUNC_NAME);
				logs.setTitle("删除了章节 \""+ob.getCname()+"\"");
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
	
	
	/**
	 * 在方法结束后记录新增日志
	 * 基金信息新增方法
	 * @param jp
	 */
	@After("execution(public * com.service.impl.FundsServiceImp.addFundInfo(..))")
	public void afterAddFundInfoLogs(JoinPoint jp) {
		try {
			Guser user = SessionUtil.getUser(session);
			Object[] args = jp.getArgs(); // 获得参数列表
			if (args.length <= 0) {
				log.info(jp.getSignature().getName() + "方法没有参数");
			} else {
				FundInfo ob = (FundInfo) args[0];
				List<FundInfo> obs = sqlSession.selectList("funds.getFundInfo", ob);
				ob = obs.get(0);
				Logs logs = new Logs();
				logs.setCmode(OperationMode.ADD);
				logs.setCfunc(FUND_INFO);
				logs.setTitle("新增 \""+ob.getFname()+"\"");
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
	 * 基金信息修改方法
	 * @param jp
	 */
	@After("execution(public * com.service.impl.FundsServiceImp.editFundInfo(..))")
	public void afterEditFundInfoLogs(JoinPoint jp) {
		try {
			Guser user = SessionUtil.getUser(session);
			Object[] args = jp.getArgs(); // 获得参数列表
			if (args.length <= 0) {
				log.info(jp.getSignature().getName() + "方法没有参数");
			} else {
				FundInfo object = (FundInfo) args[0];
				Logs logs = new Logs();
				logs.setCmode(OperationMode.EDIT);
				logs.setUserid(user.getId());
				logs.setUname(user.getCname());
				// 如果是修改基金经理对应组合的功能需要单独查询基金经理信息和管理组合信息，并且对应的cfunc类型是fund_user
				if(RegexUtil.notEmpty(object.getUserid())) {
					Integer uid = Integer.valueOf(object.getUserid());
					FundManagers ob = new FundManagers(uid);
					ob = sqlSession.selectOne("funds.getFundManagers", ob);
					
					FundInfo fundinfo = new FundInfo();
					fundinfo.setIds(object.getIds());
					List<FundInfo> fundinfos = sqlSession.selectList("funds.getFundInfo", fundinfo);
					
					logs.setCfunc(FUND_USER);
					logs.setTitle("修改了 \""+ob.getCname()+"\" 管理的基金");
					logs.setCid(uid);
					
					String[] fcode = new String[fundinfos.size()];
					String[] fname = new String[fundinfos.size()];
					int i=0;
					for(FundInfo fi : fundinfos) {
						fcode[i] = fi.getFcode();
						fname[i] = fi.getFname();
						i++;
					}
					ob.setFcodes(fcode);
					ob.setFnames(fname);
					logs.setContent(JsonUtils.toJson(ob));
				} else {
					logs.setCfunc(FUND_INFO);
					logs.setTitle("修改了 \""+object.getFname()+"\"");
					logs.setCid(object.getId());
					logs.setContent(JsonUtils.toJson(object));
				}
				sqlSession.insert("logs.addLogs", logs);
			}
		} catch (Exception e) {
			log.error("记录日志错误", e);
		}
	}
	
	/**
	 * 在方法之前记录删除日志
	 * 基金信息删除方法
	 * @param jp
	 */
	@Before("execution(public * com.service.impl.FundsServiceImp.delFundInfo(..))")
	public void afterDeleteFundInfoLogs(JoinPoint jp) {
		try {
			Guser user = SessionUtil.getUser(session);
			Object[] args = jp.getArgs(); // 获得参数列表
			if (args.length <= 0) {
				log.info(jp.getSignature().getName() + "方法没有参数");
			} else {
				Integer id = (Integer) args[0];
				FundInfo ob = new FundInfo(id);
				ob = sqlSession.selectOne("funds.getFundInfo", ob);
				Logs logs = new Logs();
				logs.setCmode(OperationMode.DELETE);
				logs.setCfunc(FUND_INFO);
				logs.setTitle("删除了章节 \""+ob.getFname()+"\"");
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
