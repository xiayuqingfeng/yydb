package cn.com.yyg.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import cn.com.easy.persistence.BaseEntity;

/**
 * 用户奖项表
 * 
 * @author nibili 2016年6月3日
 * 
 */
@Entity
@Table(name = "yyg_big_wheel_join_user_prize")
@DynamicInsert
@DynamicUpdate
public class BigWheelUserPrizeEntity extends BaseEntity {

	/** */
	private static final long serialVersionUID = -1462061662115146081L;

	/** 大转盘id */
	@Column(nullable = false)
	private Long bigWheelId;
	/** 用户手机 */
	private String tel;
	/** 用户名字 */
	private String name;
	/** 奖项id */
	private Long prizeId;
	/** 奖项名称 */
	private String prizeName;
	/** 地址 */
	private String address;

	/**
	 * get 是否中奖
	 * 
	 * @return
	 * @author nibili 2016年6月1日
	 */
	@Transient
	public boolean isWinning() {
		if (this.prizeId == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * get 大转盘id
	 * 
	 * @return
	 * @author nibili 2016年6月3日
	 */
	public Long getBigWheelId() {
		return bigWheelId;
	}

	/**
	 * set 大转盘id
	 * 
	 * @param bigWheelId
	 * @author nibili 2016年6月3日
	 */
	public void setBigWheelId(Long bigWheelId) {
		this.bigWheelId = bigWheelId;
	}

	/**
	 * get 用户手机
	 * 
	 * @return
	 * @author nibili 2016年6月3日
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * set 用户手机
	 * 
	 * @param tel
	 * @author nibili 2016年6月3日
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * get 用户名字
	 * 
	 * @return
	 * @author nibili 2016年6月3日
	 */
	public String getName() {
		if (StringUtils.isBlank(name)) {
			return "匿名";
		}
		return name;
	}

	/**
	 * set 用户名字
	 * 
	 * @param name
	 * @author nibili 2016年6月3日
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * get 奖项id
	 * 
	 * @return
	 * @author nibili 2016年6月3日
	 */
	public Long getPrizeId() {
		return prizeId;
	}

	/**
	 * set 奖项id
	 * 
	 * @param prizeId
	 * @author nibili 2016年6月3日
	 */
	public void setPrizeId(Long prizeId) {
		this.prizeId = prizeId;
	}

	/**
	 * get 奖项名称
	 * 
	 * @return
	 * @author nibili 2016年6月3日
	 */
	public String getPrizeName() {
		return prizeName;
	}

	/**
	 * set 奖项名称
	 * 
	 * @param prizeName
	 * @author nibili 2016年6月3日
	 */
	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	/**
	 * get address
	 * 
	 * @return
	 * @author linwk 2016年9月21日
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * set address
	 * 
	 * @param address
	 * @author linwk 2016年9月21日
	 */
	public void setAddress(String address) {
		this.address = address;
	}

}
