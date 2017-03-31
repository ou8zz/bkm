package com.security;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * @description	如果请求没有权限在这儿处理
 * @author Ole
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date Apr 11, 2013
 * @version 3.0
 */
public class AjaxAccessDeniedHandlerImpl implements AccessDeniedHandler  {
	private String errorPage;
	
	/**
	 * 如果是ajax请求遇到没有权限的时候处理返回403的excption 前台ajax全局处理返回403的请求
	 * 如果为普通请求，直接跳转到错误界面  
	 */
	@Override
	public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		boolean isAjax = "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));  
		System.out.println("----------------------------------" + req.getRequestURL());
		if (isAjax) {
			res.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
        } else {
        	if (!res.isCommitted()) {
                if (errorPage != null) {
                    // Put exception into request scope (perhaps of use to a view)
                    req.setAttribute(WebAttributes.ACCESS_DENIED_403, accessDeniedException);

                    // Set the 403 status code.
                    res.setStatus(HttpServletResponse.SC_FORBIDDEN);

                    // forward to error page.
                    RequestDispatcher dispatcher = req.getRequestDispatcher(errorPage);
                    dispatcher.forward(req, res);
                } else {
                    res.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
                }
            }
        }
	}
	
	public void setErrorPage(String errorPage) {
		if ((errorPage != null) && !errorPage.startsWith("/")) {
			throw new IllegalArgumentException("errorPage must begin with '/'");
		}

		this.errorPage = errorPage;
	}
}
