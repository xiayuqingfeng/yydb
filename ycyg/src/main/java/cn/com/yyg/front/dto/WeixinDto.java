package cn.com.yyg.front.dto;

public class WeixinDto {
	private String openid;
	private String nickname;
	private String sex;
	private String headimgurl;

	public WeixinDto(String openid, String nickname, String sex, String headimgurl) {
		this.openid = openid;
		this.nickname = nickname;
		this.sex = sex;
		this.headimgurl = headimgurl;
	}

	/**
	 * get openid
	 * 
	 * @return
	 * @author linwk 2016年10月13日
	 */
	public String getOpenid() {
		return openid;
	}

	/**
	 * set openid
	 * 
	 * @param openid
	 * @author linwk 2016年10月13日
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	/**
	 * get nickname
	 * 
	 * @return
	 * @author linwk 2016年10月13日
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * set nickname
	 * 
	 * @param nickname
	 * @author linwk 2016年10月13日
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * get sex
	 * 
	 * @return
	 * @author linwk 2016年10月13日
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * set sex
	 * 
	 * @param sex
	 * @author linwk 2016年10月13日
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * get headimgurl
	 * 
	 * @return
	 * @author linwk 2016年10月13日
	 */
	public String getHeadimgurl() {
		return headimgurl;
	}

	/**
	 * set headimgurl
	 * 
	 * @param headimgurl
	 * @author linwk 2016年10月13日
	 */
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
}
