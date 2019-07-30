package cn.com.yyg.base.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import cn.com.easy.persistence.BaseEntity;

/**
 * 用户表
 * 
 * @author lvzf
 * 
 */
@Entity
@Table(name = "yyg_user")
@DynamicInsert
@DynamicUpdate
public class UserEntity extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1918710199322286827L;
	/** 用户名、会员名 */
	@Column(nullable = false)
	private String userName;
	/** 微信 openId */
	private String weixinOpenId;
	/** 密码 */
	@Column(nullable = false)
	private String password;
	/** 用户类型：0-普通会员，1-管理员 */
	@Column(nullable = false, columnDefinition = " int default 0 ")
	private int userType;
	/** 是否内部用户 */
	@Column(nullable = false, columnDefinition = " bit default 0 ")
	private boolean innerUser;
	/** 昵称：显示用，可以重复 */
	private String nickName;
	/** 真实姓名 */
	private String trueName;
	/** 责任人头像 */
	private String headPhotoPath;
	/** 手机 */
	private String mobile;
	/** 性别 */
	private int sex;
	/** 生日 */
	private String birthday;
	/** 邮箱 */
	private String email;
	/** 地址 */
	private String address;
	/** qq号 */
	private String qq;
	/** 简介、备注、个性签名 */
	@Column(length = 512)
	private String remark;
	/** 省份id */
	private Long provinceId;
	/** 城市id */
	private Long cityId;
	/** 区域id */
	private Long areaId;
	/** 身份证号码 */
	private String cardNo;
	/** 账户余额 */
	@Column(nullable = false, columnDefinition = "decimal default 0 ")
	private BigDecimal accountBalance;
	/** 邀请人 */
	private Long yaoqingUserId;

	/**
	 * get userName
	 * 
	 * @return
	 * @author lvzf 2016年8月19日
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * set userName
	 * 
	 * @param userName
	 * @author lvzf 2016年8月19日
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * get password
	 * 
	 * @return
	 * @author lvzf 2016年8月19日
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * set password
	 * 
	 * @param password
	 * @author lvzf 2016年8月19日
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * get userType
	 * 
	 * @return
	 * @author lvzf 2016年8月19日
	 */
	public int getUserType() {
		return userType;
	}

	/**
	 * set userType
	 * 
	 * @param userType
	 * @author lvzf 2016年8月19日
	 */
	public void setUserType(int userType) {
		this.userType = userType;
	}

	/**
	 * get trueName
	 * 
	 * @return
	 * @author lvzf 2016年8月19日
	 */
	public String getTrueName() {
		return trueName;
	}

	/**
	 * set trueName
	 * 
	 * @param trueName
	 * @author lvzf 2016年8月19日
	 */
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	/**
	 * get headPhotoPath
	 * 
	 * @return
	 * @author lvzf 2016年8月19日
	 */
	public String getHeadPhotoPath() {
		return headPhotoPath;
	}

	/**
	 * set headPhotoPath
	 * 
	 * @param headPhotoPath
	 * @author lvzf 2016年8月19日
	 */
	public void setHeadPhotoPath(String headPhotoPath) {
		this.headPhotoPath = headPhotoPath;
	}

	/**
	 * get mobile
	 * 
	 * @return
	 * @author lvzf 2016年8月19日
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * set mobile
	 * 
	 * @param mobile
	 * @author lvzf 2016年8月19日
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * get email
	 * 
	 * @return
	 * @author lvzf 2016年8月19日
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * set email
	 * 
	 * @param email
	 * @author lvzf 2016年8月19日
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * get address
	 * 
	 * @return
	 * @author lvzf 2016年8月19日
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * set address
	 * 
	 * @param address
	 * @author lvzf 2016年8月19日
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * get qq
	 * 
	 * @return
	 * @author lvzf 2016年8月19日
	 */
	public String getQq() {
		return qq;
	}

	/**
	 * set qq
	 * 
	 * @param qq
	 * @author lvzf 2016年8月19日
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}

	/**
	 * get nickName
	 * 
	 * @return
	 * @author lvzf 2016年9月11日
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * set nickName
	 * 
	 * @param nickName
	 * @author lvzf 2016年9月11日
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * get remark
	 * 
	 * @return
	 * @author lvzf 2016年9月11日
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * set remark
	 * 
	 * @param remark
	 * @author lvzf 2016年9月11日
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * get cityId
	 * 
	 * @return
	 * @author lvzf 2016年9月11日
	 */
	public Long getCityId() {
		return cityId;
	}

	/**
	 * set cityId
	 * 
	 * @param cityId
	 * @author lvzf 2016年9月11日
	 */
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	/**
	 * get areaId
	 * 
	 * @return
	 * @author lvzf 2016年9月11日
	 */
	public Long getAreaId() {
		return areaId;
	}

	/**
	 * set areaId
	 * 
	 * @param areaId
	 * @author lvzf 2016年9月11日
	 */
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	/**
	 * get cardNo
	 * 
	 * @return
	 * @author lvzf 2016年9月11日
	 */
	public String getCardNo() {
		return cardNo;
	}

	/**
	 * set cardNo
	 * 
	 * @param cardNo
	 * @author lvzf 2016年9月11日
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	/**
	 * get accountBalance
	 * 
	 * @return
	 * @author lvzf 2016年9月11日
	 */
	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	/**
	 * set accountBalance
	 * 
	 * @param accountBalance
	 * @author lvzf 2016年9月11日
	 */
	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	/**
	 * get innerUser
	 * 
	 * @return
	 * @author lvzf 2016年9月11日
	 */
	public boolean isInnerUser() {
		return innerUser;
	}

	/**
	 * set innerUser
	 * 
	 * @param innerUser
	 * @author lvzf 2016年9月11日
	 */
	public void setInnerUser(boolean innerUser) {
		this.innerUser = innerUser;
	}

	/**
	 * get sex
	 * 
	 * @return
	 * @author linwk 2016年10月5日
	 */
	public int getSex() {
		return sex;
	}

	/**
	 * set sex
	 * 
	 * @param sex
	 * @author linwk 2016年10月5日
	 */
	public void setSex(int sex) {
		this.sex = sex;
	}

	/**
	 * get birthday
	 * 
	 * @return
	 * @author linwk 2016年10月5日
	 */
	public String getBirthday() {
		return birthday;
	}

	/**
	 * set birthday
	 * 
	 * @param birthday
	 * @author linwk 2016年10月5日
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	/**
	 * get provinceId
	 * 
	 * @return
	 * @author linwk 2016年10月5日
	 */
	public Long getProvinceId() {
		return provinceId;
	}

	/**
	 * set provinceId
	 * 
	 * @param provinceId
	 * @author linwk 2016年10月5日
	 */
	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	/**
	 * get yaoqingUserId
	 * 
	 * @return
	 * @author linwk 2016年10月9日
	 */
	public Long getYaoqingUserId() {
		return yaoqingUserId;
	}

	/**
	 * set yaoqingUserId
	 * 
	 * @param yaoqingUserId
	 * @author linwk 2016年10月9日
	 */
	public void setYaoqingUserId(Long yaoqingUserId) {
		this.yaoqingUserId = yaoqingUserId;
	}

	/**
	 * get weixinOpenId
	 * 
	 * @return
	 * @author linwk 2016年10月13日
	 */
	public String getWeixinOpenId() {
		return weixinOpenId;
	}

	/**
	 * set weixinOpenId
	 * 
	 * @param weixinOpenId
	 * @author linwk 2016年10月13日
	 */
	public void setWeixinOpenId(String weixinOpenId) {
		this.weixinOpenId = weixinOpenId;
	}

}
