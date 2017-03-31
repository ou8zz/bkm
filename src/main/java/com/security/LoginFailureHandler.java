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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.google.gson.reflect.TypeToken;
import com.util.JsonUtils;
import com.util.RegexUtil;

/**
 * @description 登录失败处理
 * @author Ole
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016-07-31
 * @version 1.0
 */
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	private Log log = LogFactory.getLog(LoginFailureHandler.class);
	
	/**
	 * 登陆错误如果是ajax登录返回错误状态
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception) {
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		Map<String, Object> m = new HashMap<String, Object>();
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			m.put("result", "FAILED");
			m.put("info", exception.getMessage());
			writer.write(JsonUtils.toJson(m, new TypeToken<Map<String, Object>>() {}.getType()));
		} catch (Exception ex) {
			log.error("LoginFailureHandler onAuthenticationFailure have error !", ex);
		} finally {
			if(RegexUtil.notEmpty(writer)) {
				writer.flush();
				writer.close();
			}
		}
	}

}
