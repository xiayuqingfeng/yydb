package cn.com.yyg.front.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.yyg.front.web.wap.controller.WapWxPayController;

import com.qq.connect.utils.json.JSONObject;

public class WeixinTicket {
	private static Logger logger = LoggerFactory.getLogger(WeixinTicket.class);
	public static String getTicket() {
		String ticket = null;
		String access_token = WeixinUtils.getAccessToken(); // 有效期为7200秒
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi";
		try {
			URL urlGet = new URL(url);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
			http.setRequestMethod("GET"); // 必须是get方式请求
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			http.connect();
			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message = new String(jsonBytes, "UTF-8");
			JSONObject demoJson = new JSONObject(message);
			ticket = demoJson.getString("ticket");
			String error=demoJson.getString("errcode");
			logger.info("ticket="+ticket);
			System.out.println("ticket="+ticket);
			is.close();
			//TODO
			//判断 会循环 注意判断
			if (StringUtils.isNotBlank(error)&&StringUtils.isBlank(ticket)) {
				System.setProperty("accessToken", "");
				return getTicket();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticket;
	}
}