package com.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.constant.DocumentsType;
import com.constant.GenderType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.util.RegexUtil;

/**
 * @description Guser 
 * @author <a href="mailto:ou8zz@sina.com">OLE</a>
 * @date 2016/05/29
 * @version 1.0
 */
public class Guser implements Serializable {
	private static final long serialVersionUID = -9137179245056475448L;
	
	private Integer id;
	private String jobNo;
	private String ename;
	private String pwd;
	private String cname;
	private String email;
	private GenderType gender;  			//性别
	private DocumentsType documents; 		//证件类型
	private String idcard; 					//身份证
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date birthday; 					// e.g. 出生年月1987-08-23
	private String phone; 					//固话
	private String telExt; 					//分机号
	private Boolean gactive;
	private Boolean locked;
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date entryDate;  				//入职日期
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date exitDate;  				//离职日期
	private String cdepartment;
	private String department;
	private Integer dptId;
	private Integer[] dptIds;
	private String cposition;
	private String position;
	private Integer pstId;
	private String crole;
	private String role;
	private Integer roleId;
	
	public Guser() {
	}
	
	public Guser(Integer id) {
		this.id = id;
	}
	
	public Guser(String ename) {
		this.ename = ename;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getJobNo() {
		return jobNo;
	}

	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTelExt() {
		return telExt;
	}

	public void setTelExt(String telExt) {
		this.telExt = telExt;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public Date getExitDate() {
		return exitDate;
	}

	public void setExitDate(Date exitDate) {
		this.exitDate = exitDate;
	}

	public String getCdepartment() {
		return cdepartment;
	}

	public void setCdepartment(String cdepartment) {
		this.cdepartment = cdepartment;
	}
	
	public Integer getDptId() {
		return dptId;
	}

	public void setDptId(Integer dptId) {
		this.dptId = dptId;
	}

	public Integer getPstId() {
		return pstId;
	}

	public void setPstId(Integer pstId) {
		this.pstId = pstId;
	}

	public String getCposition() {
		return cposition;
	}

	public void setCposition(String cposition) {
		this.cposition = cposition;
	}

	public GenderType getGender() {
		return gender;
	}
	
	public void setGender(GenderType gender) {
		this.gender = gender;
	}

	public DocumentsType getDocuments() {
		return documents;
	}
	
	public String getDocumentsDes() {
		return RegexUtil.notEmpty(documents) ? documents.getDes():"";
	}

	public void setDocuments(DocumentsType documents) {
		this.documents = documents;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Boolean getLocked() {
		return locked;
	}
	
	public String getCrole() {
		return crole;
	}

	public void setCrole(String crole) {
		this.crole = crole;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Boolean getGactive() {
		return gactive;
	}

	public void setGactive(Boolean gactive) {
		this.gactive = gactive;
	}

	public Integer[] getDptIds() {
		return dptIds;
	}

	public void setDptIds(Integer[] dptIds) {
		this.dptIds = dptIds;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getEmail()).append(getJobNo()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Guser != true)
			return false;
		Guser other = (Guser) obj;
		return new EqualsBuilder().append(getJobNo(), other.getJobNo()).append(getEmail(), other.getEmail()).isEquals();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append(this.id);
		sb.append(",").append(this.jobNo);
		sb.append(",").append(this.ename);
		sb.append(",").append(this.cname);
		sb.append(",").append(this.pwd);
		sb.append(",").append(this.email);
		sb.append("}");
		return sb.toString();
	}
}