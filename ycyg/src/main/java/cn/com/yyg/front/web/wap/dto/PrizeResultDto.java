package cn.com.yyg.front.web.wap.dto;

/**
 * 抽奖结果dto
 * 
 * @author nibili 2016年6月4日
 * 
 */
public class PrizeResultDto {
	/** 剩余抽奖次数 */
	private int lastTimes = 0;
	/** 有没有中奖 */
	private boolean isPrized = false;
	/** 是否需要收集用户信息 */
	private boolean isNeedCollectUserInfo = false;
	/** 领奖方式 */
	private int acceptPrizeType;
	/** 奖项名称 */
	private String prizeName;
	/** 奖项，优惠券链接等 */
	private String prizeString;
	/** 奖项id */
	private Long prizedId;
	/** 总积分*/
	private Long allScore;

	/**
	 * get 剩余抽奖次数
	 * 
	 * @return
	 * @author nibili 2016年6月6日
	 */
	public int getLastTimes() {
		return lastTimes;
	}

	/**
	 * set 剩余抽奖次数
	 * 
	 * @param lastTimes
	 * @author nibili 2016年6月6日
	 */
	public void setLastTimes(int lastTimes) {
		this.lastTimes = lastTimes;
	}

	/**
	 * get 有没有中奖
	 * 
	 * @return
	 * @author nibili 2016年6月6日
	 */
	public boolean getIsPrized() {
		return isPrized;
	}

	/**
	 * set 有没有中奖
	 * 
	 * @param isPrized
	 * @author nibili 2016年6月6日
	 */
	public void setIsPrized(boolean isPrized) {
		this.isPrized = isPrized;
	}

	/**
	 * get 是否需要收集用户信息
	 * 
	 * @return
	 * @author nibili 2016年6月6日
	 */
	public boolean getIsNeedCollectUserInfo() {
		return isNeedCollectUserInfo;
	}

	/**
	 * set 是否需要收集用户信息
	 * 
	 * @param isNeedCollectUserInfo
	 * @author nibili 2016年6月6日
	 */
	public void setIsNeedCollectUserInfo(boolean isNeedCollectUserInfo) {
		this.isNeedCollectUserInfo = isNeedCollectUserInfo;
	}

	/**
	 * get 领奖方式
	 * 
	 * @return
	 * @author nibili 2016年6月6日
	 */
	public int getAcceptPrizeType() {
		return acceptPrizeType;
	}

	/**
	 * set 领奖方式
	 * 
	 * @param acceptPrizeType
	 * @author nibili 2016年6月6日
	 */
	public void setAcceptPrizeType(int acceptPrizeType) {
		this.acceptPrizeType = acceptPrizeType;
	}

	/**
	 * get 奖项id
	 * 
	 * @return
	 * @author nibili 2016年6月6日
	 */
	public Long getPrizedId() {
		return prizedId;
	}

	/**
	 * set 奖项id
	 * 
	 * @param prizedId
	 * @author nibili 2016年6月6日
	 */
	public void setPrizedId(Long prizedId) {
		this.prizedId = prizedId;
	}

	/**
	 * get 奖项，优惠券链接等
	 * 
	 * @return
	 * @author nibili 2016年6月6日
	 */
	public String getPrizeString() {
		return prizeString;
	}

	/**
	 * set 奖项，优惠券链接等
	 * 
	 * @param prizeString
	 * @author nibili 2016年6月6日
	 */
	public void setPrizeString(String prizeString) {
		this.prizeString = prizeString;
	}

	/**
	 * get 奖项名称
	 * 
	 * @return
	 * @author nibili 2016年6月6日
	 */
	public String getPrizeName() {
		return prizeName;
	}

	/**
	 * set 奖项名称
	 * 
	 * @param prizeName
	 * @author nibili 2016年6月6日
	 */
	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	/**
	 * get allScore  
	 * @return   
	 * @author linwk 2016年10月17日
	 */
	public Long getAllScore() {
		return allScore;
	}

	/** 
	 *set allScore
	 * @param allScore 
	 * @author linwk 2016年10月17日
	 */
	public void setAllScore(Long allScore) {
		this.allScore = allScore;
	}

}
