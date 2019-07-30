package cn.com.yyg.front.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qq.connect.utils.json.JSONObject;

public class WeixinAccessToken {
	private static Logger logger = LoggerFactory.getLogger(WeixinAccessToken.class);
	public static String getAccessToken() {
		String access_token = ""; // 有效期为7200秒
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + WeixinUtils.app_ID + "&secret=" + WeixinUtils.app_Secret;
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
			access_token = demoJson.getString("access_token");
			logger.info("access_token"+access_token);
			System.out.println("access_token"+access_token);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return access_token;
	}

}