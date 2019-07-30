package cn.com.yyg.front.web.pub.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.easy.dto.MessageDTO;
import cn.com.easy.exception.BusinessException;
import cn.com.easy.utils.IPUtils;
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.yyg.pay.unionpay.sdk.AcpService;
import cn.com.yyg.pay.unionpay.sdk.LogUtil;
import cn.com.yyg.pay.unionpay.sdk.SDKConstants;
import cn.com.yyg.pay.util.UnionPayUtil;
/**
 * 银联在线支付
 * @author lvzf	2016年8月30日
 *
 */
@Controller
@RequestMapping("/unionPay")
public class UnionPayController {
/*	@Autowired
	private PayRecordDao payRecordDao;*/

	//商户号 
	@Value("#{configProperties['pay.union.merId']}")
	private  String merId;

	/**
	 * 银联在线支付
	 *@param modelMap
	 *@param req
	 *@param res
	 *@param payAmount 元
	 *@param payRemark
	 *@return
	 * @author lvzf 2016年8月30日
	 */
	@RequestMapping
	public String unionPay(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res,String payAmount,String payRemark) { 
		try{
			if(NumberUtils.isNumber(payAmount)==false){
				throw new BusinessException("请输入数字");
			}
			if(Double.parseDouble(payAmount)<0.01){
				throw new BusinessException("金额大于0.01元");			
			}
			if(StringUtils.isBlank(merId)){
				throw new BusinessException("商户号为空");
			}
			 String serverPort="";
	         if(req.getServerPort()!=80){
	         	serverPort=":"+req.getServerPort();
	         }
	         String contextPath=req.getContextPath();
	         if(contextPath=="/"){
	         	contextPath="";
	         }
	        String path=req.getScheme()+"://"+req.getServerName()+serverPort+contextPath;  	
			String frontUrl=path+"/pay/frontResponse";
			String backUrl=path+"/pay/backResponse";//="http://222.222.222.222:8080/ACPSample_B2C/BackRcvResponse";
			String d=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			String html=UnionPayUtil.submit(merId,payAmount, d, d, frontUrl, backUrl, payRemark);
			ResponseOutputUtils.renderHtml(res, html,"encoding:UTF-8");
			return null;
		}catch(BusinessException ex){
			req.setAttribute("msg", ex.getMessage());
		}catch(Exception ex){
			req.setAttribute("msg", "系统错误");
		}
		return null;
	}
	@RequestMapping("/frontResponse")
	public String union_frontResponse(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) throws Exception{
		try{
			LogUtil.writeLog("FrontRcvResponse前台接收报文返回开始");
			String encoding = req.getParameter(SDKConstants.param_encoding);
			LogUtil.writeLog("返回报文中encoding=[" + encoding + "]");	 
			Map<String, String> respParam = getAllRequestParam(req);	
			// 打印请求报文
			LogUtil.printRequestLog(respParam);	
			Map<String, String> valideData = null;
			StringBuffer page = new StringBuffer();
			if (null != respParam && !respParam.isEmpty()) {
				Iterator<Entry<String, String>> it = respParam.entrySet()
						.iterator();
				valideData = new HashMap<String, String>(respParam.size());
				while (it.hasNext()) {
					Entry<String, String> e = it.next();
					String key = (String) e.getKey();
					String value = (String) e.getValue();
					value = new String(value.getBytes(encoding), encoding);
					page.append("<tr><td width=\"30%\" align=\"right\">" + key
							+ "(" + key + ")</td><td>" + value + "</td></tr>");
					valideData.put(key, value);
				}
			}
			if (!AcpService.validate(valideData, encoding)) {
				page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>失败</td></tr>");
				LogUtil.writeLog("验证签名结果[失败].");
			} else {
				page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>成功</td></tr>");
				LogUtil.writeLog("验证签名结果[成功].");
				System.out.println(valideData.get("orderId")); //其他字段也可用类似方式获取
			}
			req.setAttribute("result", page.toString()); 	
			LogUtil.writeLog("FrontRcvResponse前台接收报文返回结束");
			String ip=IPUtils.getRealIP(req);
			//本地测试环境填写交易记录
			if(ip.indexOf("127")>=0 || ip.indexOf("192")>=0 || ip.indexOf("localhost")>=0 ){
/*				PayRecordEntity record=new PayRecordEntity();
				record.setClientIp(ip);
				record.setDataJson(FastJSONUtils.toJsonString(respParam));
				record.setPayType(0);
				payRecordDao.save(record);*/
			}
		}catch(Exception ex){
			req.setAttribute("msg", "系统错误");
			ResponseOutputUtils.renderJson(res,MessageDTO.newInstance("", false, "系统错误"));
		}
		return "redirect:/";
	} 
	@RequestMapping("/backResponse")
	public void union_backResponse(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) throws Exception{
		try{
			LogUtil.writeLog("BackRcvResponse接收后台通知开始");
			
			String encoding = req.getParameter(SDKConstants.param_encoding);
			// 获取银联通知服务器发送的后台通知参数
			Map<String, String> reqParam = getAllRequestParam(req);
		
			LogUtil.printRequestLog(reqParam);
		
			Map<String, String> valideData = null;
			if (null != reqParam && !reqParam.isEmpty()) {
				Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
				valideData = new HashMap<String, String>(reqParam.size());
				while (it.hasNext()) {
					Entry<String, String> e = it.next();
					String key = (String) e.getKey();
					String value = (String) e.getValue();
					value = new String(value.getBytes(encoding), encoding);
					valideData.put(key, value);
				}
			}
		
			//重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
			if (!AcpService.validate(valideData, encoding)) {
				LogUtil.writeLog("验证签名结果[失败].");
				//验签失败，需解决验签问题
				
			} else {
				LogUtil.writeLog("验证签名结果[成功].");
				//【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态
				
				//String orderId =valideData.get("orderId"); //获取后台通知的数据，其他字段也可用类似方式获取
				//String respCode =valideData.get("respCode"); //获取应答码，收到后台通知了respCode的值一般是00，可以不需要根据这个应答码判断。
				
			}
			LogUtil.writeLog("BackRcvResponse接收后台通知结束"); 
			ResponseOutputUtils.renderJson(res,MessageDTO.newInstance("", true,"成功"));
/*			PayRecordEntity record=new PayRecordEntity();
			String ip=IPUtils.getRealIP(req);
			record.setClientIp(ip);
			record.setDataJson(FastJSONUtils.toJsonString(reqParam));
			record.setPayType(0);
			payRecordDao.save(record);*/
		}catch(Exception ex){
			req.setAttribute("msg", "系统错误");
			ResponseOutputUtils.renderJson(res,MessageDTO.newInstance("", false, "系统错误"));
		}
	}
	/**
	 * 获取请求参数中所有的信息
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				//在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				//System.out.println("ServletUtil类247行  temp数据的键=="+en+"     值==="+value);
				if (null == res.get(en) || "".equals(res.get(en))) {
					res.remove(en);
				}
			}
		}
		return res;
	}
}
