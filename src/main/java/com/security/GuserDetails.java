package com.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.model.Guser;
import com.util.RegexUtil;

/**
 * @description Implementing your own UserService is necessary if
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date Mar 17, 2013  11:47:03 PM
 * @version 3.0
 */
public class GuserDetails implements UserDetails {
	private static final long serialVersionUID = -5226602131176862620L;
	private Guser guser;

	private Collection<GrantedAuthority> roles;
	
	public GuserDetails(Guser guser, Collection<GrantedAuthority> roles) {
		this.guser = guser;
		this.roles = roles;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		if(RegexUtil.isEmpty(guser)) {
			return null;
		}
		return guser.getPwd();
	}

	@Override
	public String getUsername() {
		if(RegexUtil.isEmpty(guser)) {
			return null;
		}
		return guser.getEname();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		if(RegexUtil.isEmpty(guser)) {
			return true;
		}
		return !guser.getLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		if(RegexUtil.isEmpty(guser)) {
			return true;
		}
		return guser.getGactive();
	}
	
	public void setUser(Guser guser) {
		this.guser = guser;
	}

	public Guser getGuser() {
		return guser;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object instanceof GuserDetails) {
			if (this.guser.getId().equals(((GuserDetails) object).guser.getId()))
				return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.guser.getId().hashCode();
	}
}
