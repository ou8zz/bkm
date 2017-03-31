package com.util;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContext;

import com.model.Guser;
import com.security.GuserDetails;

/**
 * @description 后台菜单管理
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/07/31
 * @version 1.0
 */
public class SessionUtil {
	private static SecurityContext attribute;
	
	/**
	 * 根据当前session获取当前登录用户对象
	 * @param session
	 * @return guser
	 */
	public static Guser getUser(HttpSession session) {
		try {
			attribute = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
			GuserDetails principal = (GuserDetails) attribute.getAuthentication().getPrincipal();
			return principal.getGuser();
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * 根据当前session获取当前登录用户ID
	 * @param session
	 * @return guser
	 */
	public static Integer getUserId(HttpSession session) {
		try {
			attribute = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
			GuserDetails principal = (GuserDetails) attribute.getAuthentication().getPrincipal();
			return principal.getGuser().getId();
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * 根据当前session获取当前登录用户名称
	 * @param session
	 * @return guser
	 */
	public static String getUserEname(HttpSession session) {
		try {
			attribute = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
			GuserDetails principal = (GuserDetails) attribute.getAuthentication().getPrincipal();
			return principal.getGuser().getEname();
		} catch (Exception e) {
		}
		return null;
	}
}
