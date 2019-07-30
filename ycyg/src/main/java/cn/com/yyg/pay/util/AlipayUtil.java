package cn.com.yyg.pay.util;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.easy.exception.BusinessException;
 
/**
 * 支付宝接口
 * @author lvzf
 * 2016年2月22日 上午10:51:38
 */
public class AlipayUtil {
	private static Logger logger = LoggerFactory.getLogger(AlipayUtil.class);
	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	// 签名方式 不需修改
	public static String sign_type = "MD5";
/*	public static String sign_type_MD5 = "MD5";*/
	public static String sign_type_RSA= "RSA";
	public static final String  SIGN_ALGORITHMS = "SHA1WithRSA";
	/**商户的私钥*/
	public static String pay_key = "p3dbfdk0mcj9v2oazhmij6v6akj2m5jg";

	public static String pay_key_rsa = "MIICWwIBAAKBgQCud21m/VhBJxWOn1r1HsCF8pCfqdj/xQUevbld+M1AdzE5lI7239nevmVP8DKpXaKzMMyQR69k02OE6fe93udjoqGMEnX0sP7qOrpFgm8zTGfLGsFZj6YNJ49rbuA7N2vZYWvm6tkC17DKxcFjnoF7sxiipxn2bWiAYhsd8jS2YQIDAQABAoGABfPVK7phFHf86EzPkaVm9Cr+fDQTT0atkgrvFCG0/woSB+Dlr86bZFoiosOTNuwW7P4xPIr1lP8RWiuRZE0Cn/kyoMoC85hKQG/OOn8SZWOBAb2VHIfPSHhgnnJFqIJ2FMuvZvNEnCLxQ84w50v5uPymyNwScHAjajEp6gf0u0ECQQDajfBt4YvvJQ4pT/SA41QiXMZevKQ7crcGLxc0htO46qACkCR7Gnpo/OQwMhh0QjsJg47o2VrngyLvnanDL2cdAkEAzFu9ADOYeNLoMoqRbfsae0koneynBtOXMxyvfTMTNzloy9SWn+Fn9+40f/RVI7gLRGYxLv2v1g5Qjt8XdiV1FQJARbpVQ+layOCMpReSTXC5zgDFdUJAL4tI41swzxxY/j008bxlNzVaapaMgufor2bWCRIFDTsTapuHEOW/XLuJ7QJAPBQgTWXdxm+iyfEI7j9rOqF4am0/cXdVNTaStQkF9i9PitUV7yzyRuZl0tQ/D4A098ffX8Q/JHwS3N3jZN2APQJACHvvRdhPUJV++9DThFcXKYyz49dQR9KuXJ6uFuAbZX9pcAowtE0Rv13A7MsDXI5gqsfBHX5xACK9U03M7mjdBw==";
	// 支付宝的公钥，无需修改该值
	public static String ali_public_key ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	/**合作身份者ID，以2088开头由16位纯数字组成的字符串*/
	public static String pay_partner = "2088521328093997";
	
    /**
     * 支付宝提供给商户的服务接入网关URL(WEB)
     */
    private static final String ALIPAY_GATEWAY_WEB = "https://mapi.alipay.com/gateway.do?";
    /**
     * 支付宝消息验证地址(WEB)
     */
    private static final String HTTPS_VERIFY_WEB = "https://mapi.alipay.com/gateway.do?service=notify_verify&";
    /**
     * 支付宝：pc web端在线即时支付
     * @param sPara
     * @return
     * @throws BusinessException
     */
	 public static String buildRequest4web(Map<String, String> sParaTemp)  throws BusinessException{
	        //除去数组中的空值和签名参数
	        Map<String, String> sPara = paraFilter(sParaTemp);
	        //生成签名结果
	        String mysign = buildRequestMysign(sPara);

	        //签名结果与签名方式加入请求提交参数组中
	        sPara.put("sign", mysign);
	        sPara.put("sign_type",sign_type);

	        List<String> keys = new ArrayList<String>(sPara.keySet());

	        StringBuffer sbHtml = new StringBuffer();

	        sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + ALIPAY_GATEWAY_WEB
	                      + "_input_charset=" + input_charset+ "\" method=\"get\">");

	        for (int i = 0; i < keys.size(); i++) {
	            String name = (String) keys.get(i);
	            String value = (String) sPara.get(name);

	            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
	        }
	        sbHtml.append("<input type=\"submit\" value=\"确认\" style=\"display:none;\"></form>");
	        sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");

	        return sbHtml.toString();
	    }
	 /**
	     * 验证消息是否是支付宝发出的合法消息
	     * @param params 通知返回来的参数数组
	     * @return 验证结果
	     */
	    public static boolean verify4web(Map<String, String> params) {

	        //判断responsetTxt是否为true，isSign是否为true
	        //responsetTxt的结果不是true，与服务器设置问题、合作身份者ID、notify_id一分钟失效有关
	        //isSign不是true，与安全校验码、请求时的参数格式（如：带自定义参数等）、编码格式有关
	    	String responseTxt = "false";
			if(params.get("notify_id") != null) {
				String notify_id = params.get("notify_id");
				responseTxt = verifyResponse(notify_id);
			}
		    String sign = "";
		    if(params.get("sign") != null) {sign = params.get("sign");}
		    boolean isSign = getSignVeryfy(params, sign);

	        //写日志记录（若要调试，请取消下面两行注释）
	        String sWord = "responseTxt=" + responseTxt + ",isSign=" + isSign + "\n 返回回来的参数：" + createLinkString(params);
		    //AlipayCore.logResult(sWord);
		    logger.info(sWord);
	        if (isSign && responseTxt.equals("true")) {
	            return true;
	        } else {
	            return false;
	        }
	    }
	    /**
	     * app支付回调
	     * @param params
	     * @return
	     */
	    public static boolean verify4App(Map<String, String> params) {
	    	String responseTxt = "false";
			if(params.get("notify_id") != null) {
				String notify_id = params.get("notify_id");
				responseTxt = verifyResponse(notify_id);
			}
			//回调，先不校验sign，因为ios的RSA签名规则不清楚
			 if (responseTxt.equals("true")) {
	            return true;
	        } else {
	            return false;
	        }
	    }
	    /**
	     * 获取远程服务器ATN结果,验证返回URL
	     * @param notify_id 通知校验ID
	     * @return 服务器ATN结果
	     * 验证结果集：
	     * invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 
	     * true 返回正确信息
	     * false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	     */
	     private static String verifyResponse(String notify_id) {
	         //获取远程服务器ATN结果，验证是否是支付宝服务器发来的请求

	         String partner = pay_partner;
	         String veryfy_url = HTTPS_VERIFY_WEB + "partner=" + partner + "&notify_id=" + notify_id;

	         return checkUrl(veryfy_url);
	     }
	    /**
	     * 根据反馈回来的信息，生成签名结果
	     * @param Params 通知返回来的参数数组
	     * @param sign 比对的签名结果
	     * @return 生成的签名结果
	     */
		private static boolean getSignVeryfy(Map<String, String> Params, String sign) {
	    	//过滤空值、sign与sign_type参数
	    	Map<String, String> sParaNew =paraFilter(Params);
	        //获取待签名字符串
	        String preSignStr =createLinkString(sParaNew);
	        //获得签名验证结果
	        boolean isSign = false;
	        logger.info("支付类型："+sign_type);
	     	isSign = verify(preSignStr, sign,pay_key,input_charset);
	        /*if(sign_type.equals(sign_type_MD5) ) {
	        	isSign = verify(preSignStr, sign,pay_key,input_charset);
	        	
	        }
	        else if(sign_type.equals(sign_type_RSA)){
	        	isSign = verifyRSA(preSignStr, sign, ali_public_key, input_charset);
	        }*/
	        return isSign;
	    }
		/**
		* RSA验签名检查
		* @param content 待签名数据
		* @param sign 签名值
		* @param ali_public_key 支付宝公钥
		* @param input_charset 编码格式
		* @return 布尔值
		*/
		public static boolean verifyRSA(String content, String sign, String ali_public_key, String input_charset)
		{
			try 
			{
				logger.info("RSA回调参数content："+content);
				logger.info("RSA回调参数sign："+sign);
				logger.info("RSA回调参数ali_public_key："+ali_public_key);
				logger.info("RSA回调参数input_charset："+input_charset);
				KeyFactory keyFactory = KeyFactory.getInstance(sign_type_RSA);
		        byte[] encodedKey = Base64.decode(ali_public_key);
		        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

			
				java.security.Signature signature = java.security.Signature
				.getInstance(SIGN_ALGORITHMS);
			
				signature.initVerify(pubKey);
				signature.update( content.getBytes(input_charset) );
			
				boolean bverify = signature.verify( Base64.decode(sign) );
				return bverify;
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			return false;
		}
	   /**
	     * 生成签名结果
	     * @param sPara 要签名的数组
	     * @return 签名结果字符串
	     */
		private static String buildRequestMysign(Map<String, String> sPara) throws BusinessException{
	    	String prestr = createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
	        String mysign = "";
	    	mysign =sign(prestr,pay_key, input_charset);
	   /*     if(sign_type.equals(sign_type_MD5) ) {
	        	mysign =sign(prestr,pay_key, input_charset);
	        }else  if(sign_type.equals(sign_type_RSA) ) {
	        	mysign =signRSA(prestr,pay_key_rsa, input_charset);
	        }*/
	        return mysign;
	    }
		public static String signRSA(String content, String privateKey, String input_charset)
		{
	        try 
	        {
	        	PKCS8EncodedKeySpec priPKCS8 	= new PKCS8EncodedKeySpec( Base64.decode(privateKey) ); 
	        	KeyFactory keyf 				= KeyFactory.getInstance(sign_type_RSA);
	        	PrivateKey priKey 				= keyf.generatePrivate(priPKCS8);
	            java.security.Signature signature = java.security.Signature
	                .getInstance(SIGN_ALGORITHMS);

	            signature.initSign(priKey);
	            signature.update( content.getBytes(input_charset) );

	            byte[] signed = signature.sign();
	            
	            return Base64.encode(signed);
	        }
	        catch (Exception e) 
	        {
	        	e.printStackTrace();
	        }
	        
	        return null;
	    }
		
	    /**
	     * 签名字符串
	     * @param text 需要签名的字符串
	     * @param key 密钥
	     * @param input_charset 编码格式
	     * @return 签名结果
	     */
		private static String sign(String text, String key, String input_charset) {
	    	text = text + key;
	        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
/*	 
	        return DigestUtils.md5DigestAsHex(getContentBytes(text, input_charset));*/
	    }
	    
	    /**
	     * 签名字符串
	     * @param text 需要签名的字符串
	     * @param sign 签名结果
	     * @param key 密钥
	     * @param input_charset 编码格式
	     * @return 签名结果
	     */
		 private static boolean verify(String text, String sign, String key, String input_charset) {
				logger.info("MD5回调参数content："+text);
				logger.info("MD5回调参数sign："+sign);
				logger.info("MD5回调参数key："+key);
		    	text = text + key;
		    	String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
		    	if(mysign.equals(sign)) {
		    		return true;
		    	}
		    	else {
		    		return false;
		    	}
		    }
		/** 
	     * 除去数组中的空值和签名参数
	     * @param sArray 签名参数组
	     * @return 去掉空值与签名参数后的新签名参数组
	     */
		private static Map<String, String> paraFilter(Map<String, String> sArray) {

	        Map<String, String> result = new HashMap<String, String>();

	        if (sArray == null || sArray.size() <= 0) {
	            return result;
	        }

	        for (String key : sArray.keySet()) {
	            String value = sArray.get(key);
	            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
	                || key.equalsIgnoreCase("sign_type")) {
	                continue;
	            }
	            result.put(key, value);
	        }

	        return result;
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
	    private static String createLinkString(Map<String, String> params) {

	        List<String> keys = new ArrayList<String>(params.keySet());
	        Collections.sort(keys);

	        String prestr = "";

	        for (int i = 0; i < keys.size(); i++) {
	            String key = keys.get(i);
	            String value = params.get(key);

	            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
	                prestr = prestr + key + "=" + value;
	            } else {
	                prestr = prestr + key + "=" + value + "&";
	            }
	        }

	        return prestr;
	    }
	    /**
	     * 获取远程服务器ATN结果
	     * @param urlvalue 指定URL路径地址
	     * @return 服务器ATN结果
	     * 验证结果集：
	     * invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 
	     * true 返回正确信息
	     * false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	     */
	     private static String checkUrl(String urlvalue) {
	         String inputLine = "";

	         try {
	             URL url = new URL(urlvalue);
	             HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
	             BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection
	                 .getInputStream()));
	             inputLine = in.readLine().toString();
	         } catch (Exception e) {
	             e.printStackTrace();
	             inputLine = "";
	         }

	         return inputLine;
	     }
		public static void main(String[] args) throws BusinessException {
		Map<String, String> sParaTemp=new HashMap<String, String>();
			sParaTemp.put("service", "create_direct_pay_by_user");
			//合作身份者ID，以2088开头由16位纯数字组成的字符串
			String partner="2088801980879745";
	        sParaTemp.put("partner",partner);
	        //直接转给商户（卖家）
	        sParaTemp.put("seller_id", partner);
	        
	        //sParaTemp.put("seller_email", "lvzhongfu@wo.com.cn");
	        sParaTemp.put("_input_charset", AlipayUtil.input_charset);
			//支付类型 :1-购买商品
			String payment_type = "1";
			sParaTemp.put("payment_type", payment_type);
			//服务器异步通知页面路径
			sParaTemp.put("notify_url", "http://127.0.0.1/create_direct_pay_by_user-JAVA-UTF-8/notify_url.jsp");
			//页面跳转同步通知页面路径
			sParaTemp.put("return_url", "http://127.0.0.1/create_direct_pay_by_user-JAVA-UTF-8/return_url.jsp");
			sParaTemp.put("out_trade_no", "A0001");//商户网站唯一订单号
			sParaTemp.put("subject", "建材保障网商品");
			sParaTemp.put("total_fee", "0.01");//交易金额
			//订单描述
			//sParaTemp.put("body", body);
			//商品展示地址
			//sParaTemp.put("show_url", show_url);
			//防钓鱼时间戳
			//sParaTemp.put("anti_phishing_key","");
    		System.out.println("form:"+AlipayUtil.buildRequest4web(sParaTemp));
//			String sign="B3FOFBZGTBoizdWGvpEYmzw9Kjow0HFSWEENTBMgPt6r3JeXX266xwqXu7rV/EyuADRzGxhzXb+TQNfq4mUmlfbyNedhKAWLTDMjItLCXoNgDr668jRLIeCoV6XOKu+3wT036THvZxOqQBr6BkmL1jdh36z3o8O5anneVwochls=";
//			 boolean res=verifyRSA("", sign, ali_public_key, input_charset);
//			System.out.println("res:"+res);

		}
}
