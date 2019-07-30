package cn.com.yyg.pay.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.codec.digest.DigestUtils;

import cn.com.yyg.front.utils.WeixinSign;
import cn.com.yyg.front.utils.WeixinUtils;
import cn.com.yyg.pay.wxpay.sdk.HttpUtil;
import cn.com.yyg.pay.wxpay.sdk.MD5Util;
import cn.com.yyg.pay.wxpay.sdk.XMLUtil;

/**
 * 微信支付
 * 
 * @author lvzf 2016年9月28日
 * 
 */
public class WxPayUtil {
	static String UFDODER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	/**
	 * 生成h5支付连接
	 * 
	 * @param appId
	 * @param appKey
	 * @param merId
	 * @param orderId
	 * @param amount
	 * @param notifyUrl
	 * @param ip
	 * @return
	 * @throws Exception
	 * @author linwk 2016年10月10日
	 * @param openId
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getWeixinPayMap(String appId, String appKey, String merId, String orderId, String amount, String notifyUrl, String ip, String openId, String timeStamp)
			throws Exception {
		// String appsecret = PayConfigUtil.APP_SECRET; // appsecret
		String currTime = getCurrTime();
		String strTime = currTime.substring(8, currTime.length());
		String strRandom = buildRandom(4) + "";
		String nonce_str = strTime + strRandom;
		// 价格 注意：价格的单位是分
		amount = String.valueOf((long) (Double.parseDouble(amount) * 100));
		String body = "商品名称"; // 商品名称
		String trade_type = "JSAPI";
		SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
		packageParams.put("appid", appId);
		packageParams.put("mch_id", merId);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("body", body);
		packageParams.put("device_info", "WEB");
		if (openId != null) {
			packageParams.put("openid", openId);
		}
		packageParams.put("product_id", orderId);
		// 订单号
		packageParams.put("out_trade_no", orderId);
		packageParams.put("total_fee", amount);
		// 获取发起电脑 ip
		packageParams.put("spbill_create_ip", ip);
		// 回调接口
		packageParams.put("notify_url", notifyUrl);
		packageParams.put("trade_type", trade_type);
		String sign = createSign("UTF-8", packageParams, appKey);
		packageParams.put("sign", sign);
		String requestXML = getRequestXml(packageParams);
		System.out.println("请求：" + requestXML);
		String resXml = HttpUtil.postData(UFDODER_URL, requestXML);
		System.out.println("响应：" + resXml);
		Map map = XMLUtil.doXMLParse(resXml);

		SortedMap<Object, Object> packagePayParams = new TreeMap<Object, Object>();
		packagePayParams.put("appId", appId);
		packagePayParams.put("timeStamp", timeStamp);
		packagePayParams.put("nonceStr", (String) map.get("nonce_str"));
		packagePayParams.put("package", "prepay_id=" + (String) map.get("prepay_id"));
		packagePayParams.put("signType", "MD5");
		// 再次签名
		String paySign = createSign("UTF-8", packagePayParams, appKey);
		map.put("paySign", paySign);
		//map.put("nonceStr", (String) map.get("nonce_str"));
		return map;
	}

	/**
	 * 生成支付二维码
	 * 
	 * @param appId
	 * @param appKey
	 * @param merId
	 *            商户号
	 * @param orderId
	 *            订单号
	 * @param amount
	 *            金额（元）
	 * @param notifyUrl
	 * @param ip
	 * @return
	 * @throws Exception
	 * @author lvzf 2016年9月26日
	 */
	public static String createQrcode(String appId, String appKey, String merId, String orderId, String amount, String notifyUrl, String ip) throws Exception {
		// String appsecret = PayConfigUtil.APP_SECRET; // appsecret

		String currTime = getCurrTime();
		String strTime = currTime.substring(8, currTime.length());
		String strRandom = buildRandom(4) + "";
		String nonce_str = strTime + strRandom;
		// 价格 注意：价格的单位是分
		amount = String.valueOf((long) (Double.parseDouble(amount) * 100));
		String body = "扫描付款"; // 商品名称

		String trade_type = "NATIVE";

		SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
		packageParams.put("appid", appId);
		packageParams.put("mch_id", merId);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("body", body);
		packageParams.put("device_info", "web");
		packageParams.put("product_id", orderId);

		// 订单号
		packageParams.put("out_trade_no", orderId);
		packageParams.put("total_fee", amount);
		// 获取发起电脑 ip
		packageParams.put("spbill_create_ip", ip);
		// 回调接口
		packageParams.put("notify_url", notifyUrl);
		packageParams.put("trade_type", trade_type);

		String sign = createSign("UTF-8", packageParams, appKey);
		packageParams.put("sign", sign);

		String requestXML = getRequestXml(packageParams);
		System.out.println("请求：" + requestXML);

		String resXml = HttpUtil.postData(UFDODER_URL, requestXML);
		System.out.println("响应：" + resXml);
		@SuppressWarnings("rawtypes")
		Map map = XMLUtil.doXMLParse(resXml);
		@SuppressWarnings("unused")
		String return_code = (String) map.get("return_code");
		String prepay_id = (String) map.get("prepay_id");
		@SuppressWarnings("unused")
		String urlCode = (String) map.get("code_url");
		sign = (String) map.get("sign");
		return urlCode;

	}

	/**
	 * 是否签名正确,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 * 
	 * @return boolean
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isTenpaySign(String characterEncoding, SortedMap<Object, Object> packageParams, String API_KEY) {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (!"sign".equals(k) && null != v && !"".equals(v)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + API_KEY);

		// 算出摘要
		String mysign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toLowerCase();
		String tenpaySign = ((String) packageParams.get("sign")).toLowerCase();

		// System.out.println(tenpaySign + "    " + mysign);
		return tenpaySign.equals(mysign);
	}

	/**
	 * 
	 * @param map
	 * @return
	 * @author linwk 2016年10月18日
	 */
	private static String getSign(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		@SuppressWarnings("rawtypes")
		Set es = map.entrySet();// 所有参与传参的参数按照accsii排序（升序）
		@SuppressWarnings("rawtypes")
		Iterator it = es.iterator();
		while (it.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		// 你在公众号内设置的密钥
		sb.append("key=" + WeixinUtils.app_Secret);
		// MD5加密方法，文章后面会提供工具类下载
		String sign = DigestUtils.md5Hex(getContentBytes(sb.toString(), "UTF-8")).toUpperCase();
		return sign;
	}

	/**
	 * @author
	 * @date 2016-4-22
	 * @Description：sign签名
	 * @param characterEncoding
	 *            编码格式
	 * @param parameters
	 *            请求参数
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String createSign(String characterEncoding, SortedMap<Object, Object> packageParams, String API_KEY) {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + API_KEY);
		// String sign = MD5Util.MD5Encode(sb.toString(),
		// characterEncoding).toUpperCase();
		String sign = DigestUtils.md5Hex(getContentBytes(sb.toString(), characterEncoding)).toUpperCase();
		return sign;
	}

	private static byte[] getContentBytes(String content, String charset) {
		if (charset == null || "".equals(charset)) {
			return content.getBytes();
		}
		try {
			return content.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
		}
	}

	/**
	 * @author
	 * @date 2016-4-22
	 * @Description：将请求参数转换为xml格式的string
	 * @param parameters
	 *            请求参数
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getRequestXml(SortedMap<Object, Object> parameters) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			// if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) ||
			// "sign".equalsIgnoreCase(k)) {
			if ("attach".equalsIgnoreCase(k)) {
				sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
			} else {
				sb.append("<" + k + ">" + v + "</" + k + ">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * 取出一个指定长度大小的随机正整数.
	 * 
	 * @param length
	 *            int 设定所取出随机数的长度。length小于11
	 * @return int 返回生成的随机数。
	 */
	public static int buildRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) ((random * num));
	}

	/**
	 * 获取当前时间 yyyyMMddHHmmss
	 * 
	 * @return String
	 */
	public static String getCurrTime() {
		Date now = new Date();
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String s = outFormat.format(now);
		return s;
	}

	public static void main(String[] args) {
		String merId = "1388261002";
		String appId = "wxe913ec262d781dca";
		String appKey = "c80f86302e55cdbcb6fae727991ff4c6";
		String orderId = "" + System.currentTimeMillis();
		String amount = "0.02";
		String openId = "oPgBSwL8NcYm5XdZH3rBSxClYUzY";
		try {
			// String url = WxPayUtil.createQrcode(appId, appKey, merId,
			// orderId, amount, "http://www.qibao99.com/wxPay/smBack/",
			// "123.12.12.123");
			// System.out.println("url=" + url);
			// QRCodeImgUtils.createImg(url, 300, 300, "D:/newPic.jpg");
			@SuppressWarnings("unchecked")
			Map<String, String> map = WxPayUtil.getWeixinPayMap(appId, appKey, merId, orderId, amount, "http://www.qibao99.com/wxPay/smBack/", "123.12.12.123", openId,
					WeixinSign.create_timestamp());
			for (@SuppressWarnings("rawtypes")
			Map.Entry entry : map.entrySet()) {
				System.out.println("key:" + entry.getKey().toString() + ",value:" + entry.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
