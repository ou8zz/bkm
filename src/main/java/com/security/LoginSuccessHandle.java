/**
 * OLE
 */
package com.security;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.constant.OperationMode;
import com.google.gson.reflect.TypeToken;
import com.model.Guser;
import com.model.Logs;
import com.util.JsonUtils;
import com.util.RegexUtil;

/**
 * @description 登录成功后处理
 * @author OLE
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date Apr 6, 2013
 * @version 3.0
 */
public class LoginSuccessHandle extends SavedRequestAwareAuthenticationSuccessHandler {
	private Log log = LogFactory.getLog(LoginSuccessHandle.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	/**
	 * 登录成功后在session中添加用户数 登陆成功后如果是ajax登录返回成功状态 如果是不是ajax就设置登陆成功后跳转页面 交给父类
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		PrintWriter writer = null;
		Map<String, Object> m = new HashMap<String, Object>();
		try {
			writer = response.getWriter();
			RequestCache requestCache = new HttpSessionRequestCache();
			SavedRequest savedRequest = requestCache.getRequest(request, response);
			if(RegexUtil.isEmpty(savedRequest)) {
				m.put("url", request.getContextPath() + getDefaultTargetUrl());
			} else {
				m.put("url", savedRequest.getRedirectUrl());
			}
			m.put("result", "SUCCESS");
			writer.write(JsonUtils.toJson(m, new TypeToken<Map<String, Object>>() {}.getType()));
		} catch (Exception ex) {
			log.error("LoginSuccessHandle onAuthenticationSuccess have error !", ex);
			m.put("result", "ERROR");
			writer.write(JsonUtils.toJson(m, new TypeToken<Map<String, Object>>() {}.getType()));
		} finally {
			handelLogin(request, authentication);
			if(RegexUtil.notEmpty(writer)) {
				writer.flush();
				writer.close();
			}
			// 如果请求跳转直接调用父类super.onAuthenticationSuccess即可实现自动跳转
			// super.onAuthenticationSuccess(request, response, authentication);
		}
	}
	
	/**
	 * 
	 * 处理登录后续操作
	 * 使用线程方式处理日志记录，避免影响登录性能
	 * 为避免登录出异常情况，这个方法会把异常先捕获
	 * @param authentication
	 */
	private void handelLogin(final HttpServletRequest request, final Authentication authentication) {
		try {
			new Thread() {
				public void run() {
					String ip = getRemortIP(request);
					GuserDetails principal = (GuserDetails) authentication.getPrincipal();
					Guser gu = principal.getGuser();
					Logs l = new Logs();
					l.setCmode(OperationMode.LOGIN);
					l.setUserid(gu.getId());
					l.setUname(gu.getCname());
					l.setCfunc("系统");
					l.setTitle("登录成功");
					l.setCid(gu.getId());
					l.setContent("{\"ip\":\""+ip+"\"}");
					sqlSession.insert("logs.addLogs", l);
				}
			}.start();
		} catch (Exception e) {
			log.error("handelLogin error", e);
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
