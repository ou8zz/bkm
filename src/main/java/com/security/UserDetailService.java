package com.security;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.model.Guser;
import com.util.RegexUtil;

/**
 * @description 
 * Implementing your own UserService is necessary if 
 * 1. you want to return customized UserDetails objects (which you can access later via the SecurityContextHolder)
 * 2. the receival of the User objects and authorities is too complex and/or cannot be defined with easy queries at the jdbc-user-service
 * 
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date Mar 17, 2013  10:58:01 PM
 * @version 3.0
 */
public class UserDetailService implements UserDetailsService {
	private static final Logger logger = Logger.getLogger(UserDetailService.class);
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public UserDetails loadUserByUsername(String uname) {
		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		Guser u = new Guser();
		u.setEname(uname);
		try {
			u = sqlSession.selectOne("guser.getGusers", u);
			if(u ==null){
				logger.error("user \"" + uname + "\" not exist, please check again!");
			} else {
				u = sqlSession.selectOne("guser.getGuserDetil", new Guser(u.getId()));
				if(RegexUtil.notEmpty(u.getRole())) {
					GrantedAuthority granted = new SimpleGrantedAuthority("ROLE_"+u.getRole());
					auths.add(granted);
				}
				if(RegexUtil.notEmpty(u.getPosition())) {
					GrantedAuthority granted = new SimpleGrantedAuthority("ROLE_"+u.getPosition());
					auths.add(granted);
				}
			}
		} catch (Exception e) {
			logger.error("get user role is faild", e);
		}
		// 获得用户的权限，设置权限
		GuserDetails gud = new GuserDetails(u, auths);
		return gud;
	}

}
