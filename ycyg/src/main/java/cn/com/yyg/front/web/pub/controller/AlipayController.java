package cn.com.yyg.front.web.pub.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.com.easy.exception.BusinessException;
import cn.com.easy.utils.FastJSONUtils;
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.yyg.base.entity.PayRecordEntity;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.base.utils.DateUtil;
import cn.com.yyg.front.dao.UserDao;
import cn.com.yyg.front.service.PayService;
import cn.com.yyg.front.service.YgBuyService;
import cn.com.yyg.pay.util.AlipayUtil;

/**
 * 支付宝支付
 * @author lvzf	2016年9月22日
 *
 */
@Controller
@RequestMapping("/alipay")
public class AlipayController {
	private Logger logger = LoggerFactory.getLogger(AlipayController.class);
	@Autowired
    private UserDao userDao;
	@Autowired
    private PayService payService;
	@Autowired
	private YgBuyService ygBuyService;
	/**
	 * 支付宝支付
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param orderId
	 * @param storeId
	 * @throws Exception
	 */
	@RequestMapping
	public String alipay(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, String payNo, RedirectAttributes redirectAttributes) throws Exception {
		PayRecordEntity pay=payService.getPayByPayNo(payNo); 
		if(pay==null || pay.getPayStatus()!=PayRecordEntity.PAY_STATUS_INIT){
			redirectAttributes.addFlashAttribute("errorString", "该订单已经支付或取消");
			return "redirect:/error/custom";
		} 
		Map<String, String> sParaTemp=new HashMap<String, String>();
		sParaTemp.put("service", "create_direct_pay_by_user");
		//合作身份者ID，以2088开头由16位纯数字组成的字符串
		String partner=AlipayUtil.pay_partner;
        sParaTemp.put("partner",partner);
        //直接转给商户
        sParaTemp.put("seller_id", partner);
        //直接转给卖家 
        //sParaTemp.put("seller_email", "2251991538@qq.com");
        sParaTemp.put("_input_charset", AlipayUtil.input_charset);
		//支付类型 :1-购买商品
		String payment_type = "1";
		sParaTemp.put("payment_type", payment_type);
		 String serverPort="";
         if(request.getServerPort()!=80){
         	serverPort=":"+request.getServerPort();
         }
         String contextPath=request.getContextPath();
         if(contextPath=="/"){
         	contextPath="";
         }
         String path=request.getScheme()+"://"+request.getServerName()+serverPort+contextPath;  		
 		//页面跳转同步通知页面路径
		sParaTemp.put("return_url", path+"/alipay/frontReturn");
		//服务器异步通知页面路径 
		sParaTemp.put("notify_url", path+"/alipay/backReturn"); 
		sParaTemp.put("out_trade_no",pay.getPayNo());//商户网站唯一订单号
		sParaTemp.put("subject", "一元云购商品");
		sParaTemp.put("total_fee", pay.getAmount().toString());//交易金额
		//订单描述
		//sParaTemp.put("body", body);
		//商品展示地址
		//sParaTemp.put("show_url", show_url);
		//防钓鱼时间戳
		//sParaTemp.put("anti_phishing_key","");
		//sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
		String html=AlipayUtil.buildRequest4web(sParaTemp);	 
		ResponseOutputUtils.renderHtml(response, html,"encoding:UTF-8");
		return null;
	}

	/**
	 * 支付宝支付页面跳转
	 * @param request
	 * @param response
	 */
	@RequestMapping("/frontReturn")
	public String frontReturn(ModelMap modelMap,  HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){

			Map<String,String> params = new HashMap<String,String>();
			@SuppressWarnings("rawtypes")
			Map requestParams = request.getParameterMap();
			for (@SuppressWarnings("rawtypes")
			Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
				params.put(name, valueStr);
			}
			
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			//平台订单号
			String out_trade_no = request.getParameter("out_trade_no");
			//支付宝交易号
			String trade_no =request.getParameter("trade_no");
			//交易状态
			String trade_status =request.getParameter("trade_status");
			//交易金额
			//String total_fee =request.getParameter("total_fee");
			String notify_time =request.getParameter("notify_time");
			//String sellerEmail =request.getParameter("seller_email");
			//String sellerId =request.getParameter("seller_id");
			
			if(AlipayUtil.verify4web(params)){
				try{				
					if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
						//判断该笔订单是否在商户网站中已经做过处理
							//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
							//如果有做过处理，不执行商户的业务程序

						//String sname=request.getServerName();
						//如果是生产环境，由alipay/webNotify执行业务
						//本地测试环境填写交易记录
						String ip=request.getRemoteHost();
						if(ip.indexOf("127")>=0 || ip.indexOf("192")>=0 || ip.indexOf("localhost")>=0 ){
							PayRecordEntity pay=payService.getPayByPayNo(out_trade_no);
							if(pay!=null && pay.getPayStatus()!=PayRecordEntity.PAY_STATUS_SUCCESS){ 
								pay.setPayStatus(PayRecordEntity.PAY_STATUS_SUCCESS);//付款
								pay.setThirdPayTime(DateUtil.getStringToDate(notify_time, DateUtil.YMDhhmmss));
								pay.setThirdTradeNo(trade_no);
								pay.setThirdTradeStatus(trade_status); 
								//生成中奖号码
								UserEntity user =userDao.findOne(pay.getUserId());
								ygBuyService.createWinNo(user,pay);
							}
						}
					}else{
						throw new BusinessException("支付异常");
					}
				}catch(BusinessException ex){
					redirectAttributes.addFlashAttribute("errorString", ex.getMessage());
					return "redirect:/error/custom";
				}catch(Exception ex){
					redirectAttributes.addFlashAttribute("errorString","支付异常");
					return "redirect:/error/custom";
				}
			return "redirect:/member/buy/payResult?payNo="+out_trade_no;
		}else{
			redirectAttributes.addFlashAttribute("errorString", "支付宝支付:校验错误");
			return "redirect:/error/custom";
		}
	}

	@RequestMapping("/backReturn")
	public void backReturn( HttpServletRequest request,HttpServletResponse response){
		try{
			Map<String,String> params = new HashMap<String,String>();
			@SuppressWarnings("rawtypes")
			Map requestParams = request.getParameterMap();
			for (@SuppressWarnings("rawtypes")
			Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
				params.put(name, valueStr);
			}
			
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			//平台订单号
			String out_trade_no = request.getParameter("out_trade_no");
			//支付宝交易号
			String trade_no =request.getParameter("trade_no");
			//交易状态
			String trade_status =request.getParameter("trade_status");
			//交易金额
			//String total_fee =request.getParameter("total_fee");
			String notify_time =request.getParameter("notify_time");
			//String sellerEmail =request.getParameter("seller_email");
			//String sellerId =request.getParameter("seller_id");
			logger.info("支付宝支付回调参数："+FastJSONUtils.toJsonString(params));
			boolean ret=false;
			ret=AlipayUtil.verify4web(params);
			if(ret){
				if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
					//判断该笔订单是否在商户网站中已经做过处理
						//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
						//如果有做过处理，不执行商户的业务程序
					PayRecordEntity pay=payService.getPayByPayNo(out_trade_no);					 
					if(pay!=null && pay.getPayStatus()==PayRecordEntity.PAY_STATUS_INIT){
						pay.setPayStatus(PayRecordEntity.PAY_STATUS_SUCCESS);//付款
						pay.setThirdPayTime(DateUtil.getStringToDate(notify_time, DateUtil.YMDhhmmss));
						pay.setThirdTradeNo(trade_no);
						pay.setThirdTradeStatus(trade_status); 
						//生成中奖号码
						UserEntity user =userDao.findOne(pay.getUserId());
						ygBuyService.createWinNo(user,pay);
					}   
					logger.info("支付宝支付回调成功");
				}else{
					throw new BusinessException("支付宝支付回调状态不正确");
				} 
			}else{
				throw new BusinessException("支付宝支付回调校验错误");
			}
		}catch(BusinessException ex){
			logger.error(ex.getMessage());
		}catch(Exception ex){
			logger.error("支付宝支付回调错误",ex);
		}

	}
}
