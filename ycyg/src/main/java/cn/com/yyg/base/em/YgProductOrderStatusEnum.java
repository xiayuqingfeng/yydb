package cn.com.yyg.base.em;

/**
 * 云购商品订单状态枚举
 * 
 * @author lvzf 2016年8月19日
 * 
 */
public enum YgProductOrderStatusEnum {
	PAYED("已付款_待发货", 1), SENDED("已发货_待收货", 2), ACCEPTED("交易完成", 3);
	// 成员变量
	private String name;
	private int value;

	// 构造方法
	private YgProductOrderStatusEnum(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public static YgProductOrderStatusEnum getInstance(int value) {
		for (YgProductOrderStatusEnum c : YgProductOrderStatusEnum.values()) {
			if (c.getValue() == value) {
				return c;
			}
		}
		return null;
	}

	// 普通方法
	public static String getName(int value) {
		for (YgProductOrderStatusEnum c : YgProductOrderStatusEnum.values()) {
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
