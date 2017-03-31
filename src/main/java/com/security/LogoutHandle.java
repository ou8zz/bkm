/**
 * OLE
 */
package com.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import com.constant.OperationMode;
import com.model.Guser;
import com.model.Logs;

/**
 * @description	登出注销操作
 * @author OLE
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016-08-04
 * @version 1.0
 */
public class LogoutHandle extends SimpleUrlLogoutSuccessHandler {
	private Log log = LogFactory.getLog(LogoutHandle.class);
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		handelLogout(request, authentication);
		super.onLogoutSuccess(request, response, authentication);
	}
	
	/**
	 * 
	 * 处理注销后续操作
	 * 使用线程方式处理日志记录，避免影响注销性能
	 * 为避免注销出异常情况，这个方法会把异常先捕获
	 * @param authentication
	 */
	private void handelLogout(final HttpServletRequest request, final Authentication authentication) {
		try {
			new Thread() {
				public void run() {
					GuserDetails principal = (GuserDetails) authentication.getPrincipal();
					Guser gu = principal.getGuser();
					Logs l = new Logs();
					l.setCmode(OperationMode.LOGOUT);
					l.setUserid(gu.getId());
					l.setUname(gu.getCname());
					l.setCfunc("系统");
					l.setTitle("注销成功");
					l.setCid(gu.getId());
					l.setContent("{\"ip\":\""+getRemortIP(request)+"\"}");
					sqlSession.insert("logs.addLogs", l);
				}
			}.start();
		} catch (Exception e) {
			log.error("handelLogout error", e);
		}
	}
	
	// 获得IP
	private String getRemortIP(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}
}
