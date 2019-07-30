package cn.com.yyg.base.em;

/**
 * 用户积分枚举
 * 
 * @author lvzf 2016年8月19日
 * 
 */
public enum UserScoreTypeEnum {
	Regist("注册会员",1),QianDao("每日签到", 2), Link("发布链接",3), Yaoqing("邀请好友", 4), 
	Share("分享", 5), Buy("参与夺宝", 6), BigWheelJoin("参与抽奖（大转盘）", 7), BigWheelWin("中奖（大转盘）", 8);
	// 成员变量
	private String name;
	private int value;

	// 构造方法
	private UserScoreTypeEnum(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public static UserScoreTypeEnum getInstance(int value) {
		for (UserScoreTypeEnum c : UserScoreTypeEnum.values()) {
			if (c.getValue() == value) {
				return c;
			}
		}
		return null;
	}

	// 普通方法
	public static String getName(int value) {
		for (UserScoreTypeEnum c : UserScoreTypeEnum.values()) {
			if (c.getValue() == value) {
				return c.name;
			}
		}
		return null;
	}
	
	// get set 方法
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
