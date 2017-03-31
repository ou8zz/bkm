package com.model;


import java.io.Serializable;

import com.constant.MoType;

/**
 * @description 机构信息,托管人信息等
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016-08-21
 * @version 1.0
 */
public class Moinfo implements Serializable {
	private static final long serialVersionUID = -5908146114765563938L;
	private Integer id;				// 主键
	private String cname;			// 名称
	private String orgtype;			// 机构类型
	private String addr;			// 地址
	private String contacts;		// 联系人
	private String cnumber;			// 联系电话
	private MoType ctype;			// 类型
	
	public Moinfo() {}
	
	public Moinfo(Integer id) {
		this.id = id;
	}
	
	public Moinfo(String cname) {
		this.cname = cname;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getOrgtype() {
		return orgtype;
	}
	public void setOrgtype(String orgtype) {
		this.orgtype = orgtype;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getCnumber() {
		return cnumber;
	}
	public void setCnumber(String cnumber) {
		this.cnumber = cnumber;
	}
	public MoType getCtype() {
		return ctype;
	}
	public void setCtype(MoType ctype) {
		this.ctype = ctype;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addr == null) ? 0 : addr.hashCode());
		result = prime * result + ((cname == null) ? 0 : cname.hashCode());
		result = prime * result + ((cnumber == null) ? 0 : cnumber.hashCode());
		result = prime * result + ((contacts == null) ? 0 : contacts.hashCode());
		result = prime * result + ((ctype == null) ? 0 : ctype.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((orgtype == null) ? 0 : orgtype.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Moinfo other = (Moinfo) obj;
		if (addr == null) {
			if (other.addr != null)
				return false;
		} else if (!addr.equals(other.addr))
			return false;
		if (cname == null) {
			if (other.cname != null)
				return false;
		} else if (!cname.equals(other.cname))
			return false;
		if (cnumber == null) {
			if (other.cnumber != null)
				return false;
		} else if (!cnumber.equals(other.cnumber))
			return false;
		if (contacts == null) {
			if (other.contacts != null)
				return false;
		} else if (!contacts.equals(other.contacts))
			return false;
		if (ctype != other.ctype)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (orgtype == null) {
			if (other.orgtype != null)
				return false;
		} else if (!orgtype.equals(other.orgtype))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Moinfo [id=" + id + ", cname=" + cname + ", orgtype=" + orgtype + ", addr=" + addr + ", contacts="
				+ contacts + ", cnumber=" + cnumber + ", ctype=" + ctype + "]";
	}
}
