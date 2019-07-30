package cn.com.yyg.base.em;

/**
 * 云购商品状态枚举
 * 
 * @author lvzf 2016年8月19日
 * 
 */
public enum YgProductStatusEnum {
	ING("进行中", 0), PRE("即将揭晓", 1), DO("开奖计算中", 2), END("已揭晓", 3), DEL("已删除", 4);
	// 成员变量
	private String name;
	private int value;

	// 构造方法
	private YgProductStatusEnum(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public static YgProductStatusEnum getInstance(int value) {
		for (YgProductStatusEnum c : YgProductStatusEnum.values()) {
			if (c.getValue() == value) {
				return c;
			}
		}
		return null;
	}

	// 普通方法
	public static String getName(int value) {
		for (YgProductStatusEnum c : YgProductStatusEnum.values()) {
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
