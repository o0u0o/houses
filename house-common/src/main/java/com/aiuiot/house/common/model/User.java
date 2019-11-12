package com.aiuiot.house.common.model;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @Title: User.java
 * @Package com.aiuiot.house.common.model
 * @Description: （Plain Ordinary Java Object）简单的Java对象，实际就是普通JavaBeans，
 *               是为了避免和EJB混淆所创造的简称。 其中有一些属性及其getter、setter方法的类，没有业务逻辑，
 *               有时可以作为VO（value-object）或DTO（Data Transfer Object）来使用。不允许有业务方法，
 *               也不能携带connection之类的方法，实际就是普通JavaBeans。POJO类中有属性和get、set方法，
 *               但是没有业务逻辑
 * 
 * @author: ZerOneth
 * @date: 2019年6月21日 上午9:02:17
 * @version V1.0
 */
public class User {

	private Long id; // 用户ID
	private String name; // 姓名
	private String email; // 邮箱
	private String phone; // 手机
	private String passwd; // 密码
	private String confirmPasswd; // 确认密码
	private Integer type; // 1-普通用户 2-经纪人
	private Date createTime; // 创建时间
	private Integer enable; // 是否激活
	private String avatar; // 头像 url地址
	private MultipartFile avatarFile; // 接收用户上传的文件(用户头像)
	private String newPassword; // 新密码 用于修改密码使用
	private String key; // 激活码
	private Long agencyId; // 经纪机构的ID
	private String aboutme; // 关于我
	private String agencyName; // 经纪人姓名

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getConfirmPasswd() {
		return confirmPasswd;
	}

	public void setConfirmPasswd(String confirmPasswd) {
		this.confirmPasswd = confirmPasswd;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public MultipartFile getAvatarFile() {
		return avatarFile;
	}

	public void setAvatarFile(MultipartFile avatarFile) {
		this.avatarFile = avatarFile;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Long getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	public String getAboutme() {
		return aboutme;
	}

	public void setAboutme(String aboutme) {
		this.aboutme = aboutme;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone + ", passwd=" + passwd
				+ ", confirmPasswd=" + confirmPasswd + ", type=" + type + ", createTime=" + createTime + ", enable="
				+ enable + ", avatar=" + avatar + ", avatarFile=" + avatarFile + ", newPassword=" + newPassword
				+ ", key=" + key + ", agencyId=" + agencyId + ", aboutme=" + aboutme + ", agencyName=" + agencyName
				+ "]";
	}
	
}
