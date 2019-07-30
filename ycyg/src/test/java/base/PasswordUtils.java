package base;

import cn.com.easy.utils.SecurityUtils;

public class PasswordUtils {

	public static void main(String[] args) {
		String passwordTemp = SecurityUtils.SHA256("123456");
		System.out.println(passwordTemp);
	}

}
