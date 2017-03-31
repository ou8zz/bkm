package com.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.thoughtworks.xstream.core.util.Base64Encoder;

/**
 * @description 令牌发生器
 * @author OLE
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date Mar 13, 2013
 * @version 3.0
 */
public class TokenProcessor {
	
	/**
	 * 1.把构造方法私有 
	 * 2.自己创建一个 
	 * 3.对外暴露一个方法，允许获得创建的对象
	 */
	private TokenProcessor() {
	}

	private static final TokenProcessor instance = new TokenProcessor();

	public static TokenProcessor getInstance() {
		return instance;
	}

	// 获取唯一的表单码 Token
	public String generateToken() {
		String token = System.currentTimeMillis() + new Random().nextInt() + "";
		try {
			MessageDigest md5 = MessageDigest.getInstance("md5");
			byte[] md = md5.digest(token.getBytes());

			// base64进行编码
			Base64Encoder be = new Base64Encoder();
			return be.encode(md);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	// 验证Token是否被使用过,如果没有使用返回true并移除Session中的token
	public boolean isTokenValue(HttpServletRequest request) {
		String clinetToken = request.getParameter("token");
		if (clinetToken == null) {
			return false;
		}
		String serverToken = (String) request.getSession(false).getAttribute("token");
		if (serverToken == null) {
			return false;
		}
		if (!serverToken.equals(clinetToken)) {
			return false;
		}
		// 验证成功一次后移除server的session.token
		request.getSession(false).removeAttribute("token");
		return true;
	}
}
