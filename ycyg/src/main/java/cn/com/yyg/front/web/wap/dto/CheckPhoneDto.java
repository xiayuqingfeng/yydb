package cn.com.yyg.front.web.wap.dto;


/**
 * 大转盘，检查手机 抽奖次数，已中奖礼品等信息
 * 
 * @author nibili 2016年6月4日
 * 
 */
public class CheckPhoneDto {
	/** 剩余抽奖次数 */
	private int lastTimes;
	/** 是否完善了用户信息 */
	private boolean isUserInfoCommit;
//	/** 用户奖项 */
//	private List<BigWheelUserPrizeEntity> userPrizes = Lists.newArrayList();

	/**
	 * get 剩余抽奖次数
	 * 
	 * @return
	 * @author nibili 2016年6月4日
	 */
	public int getLastTimes() {
		return lastTimes;
	}

	/**
	 * set 剩余抽奖次数
	 * 
	 * @param lastTimes
	 * @author nibili 2016年6月4日
	 */
	public void setLastTimes(int lastTimes) {
		this.lastTimes = lastTimes;
	}

	/**
	 * get 是否完善了用户信息
	 * 
	 * @return
	 * @author nibili 2016年6月4日
	 */
	public boolean getIsUserInfoCommit() {
		return isUserInfoCommit;
	}

	/**
	 * set 是否完善了用户信息
	 * 
	 * @param isUserInfoCommit
	 * @author nibili 2016年6月4日
	 */
	public void setIsUserInfoCommit(boolean isUserInfoCommit) {
		this.isUserInfoCommit = isUserInfoCommit;
	}

	// /**
	// * get 用户奖项
	// *
	// * @return
	// * @author nibili 2016年6月4日
	// */
	// public List<BigWheelUserPrizeEntity> getUserPrizes() {
	// return userPrizes;
	// }
	//
	// /**
	// * set 用户奖项
	// *
	// * @param userPrizes
	// * @author nibili 2016年6月4日
	// */
	// public void setUserPrizes(List<BigWheelUserPrizeEntity> userPrizes) {
	// this.userPrizes = userPrizes;
	// }

}
