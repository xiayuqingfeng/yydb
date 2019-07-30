package base;

import cn.com.easy.utils.SecurityUtils;

/**
 * 生成指定位数随机字符
 * 
 * @author nibili 2016年5月25日
 * 
 */
public class SecurityUtilsTest {

	public static void main(String[] args) {
		String temp = SecurityUtils.createPasswd(32);
		System.out.println(temp + "  " + temp.length());
		temp = SecurityUtils.createPasswd(43);
		System.out.println(temp + "  " + temp.length());
	}

}
