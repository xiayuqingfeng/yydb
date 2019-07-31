package cn.com.yyg.front.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class BaseConfig {
	/**图片服务器地址*/
	@Value("${img.serverpath}")
	private String imgServerPath;
	public String getImgServerPath(){
		return imgServerPath;
	}
	/**图片服务器地址*/
	@Value("${img.serverpath}")
	private  String imgServerUrl;
	/**
	 * get imgServerUrl
	 * @return
	 * @author lvzf 2016年8月22日
	 */
	public  String getImgServerUrl() {
		return imgServerUrl;
	}
	
}
