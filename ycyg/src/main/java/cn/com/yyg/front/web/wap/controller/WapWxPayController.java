package cn.com.yyg.front.web.wap.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.easy.exception.BusinessException;
import cn.com.easy.utils.FastJSONUtils;
import cn.com.easy.utils.IPUtils;
import cn.com.yyg.base.entity.PayRecordEntity;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.front.dao.UserDao;
import cn.com.yyg.front.service.PayService;
import cn.com.yyg.front.service.YgBuyService;
import cn.com.yyg.front.utils.RequestUtils;
import cn.com.yyg.front.utils.WeixinSign;
import cn.com.yyg.pay.util.WxPayUtil;
import cn.com.yyg.pay.wxpay.sdk.XMLUtil;

/**
 * 微信扫描支付
 * 
 * @author lvzf 2016年8月30日
 * 
 */
@Controller
@RequestMapping("/wap/wxPay")
public class WapWxPayController {

	private Logger logger = LoggerFactory.getLogger(WapWxPayController.class);
	@Autowired
	private UserDao userDao;
	@Autowired
	private YgBuyService ygBuyService;
	@Autowired
	private PayService payService;
	// 商户号
	String merId = "1421466502";
	// 应用id
	String appId = "wx160ded85d3101b00";
	// 应用秘钥
	String appKey = "fsadfdsafasdfsadfdsae43ffrreferr";

	/**
	 * 微信支付页面
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param payNo
	 * @return
	 * @author linwk 2016年10月17日
	 */
	@RequestMapping
	public String pay(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, String payNo) {
		// 参数设置
		try {
			req.getSession().setAttribute("isWeixinBrowerisWeixinBrower", false);
			req.getSession().setAttribute("isWeixinBrower", false);

			PayRecordEntity pay = payService.getPayByPayNo(payNo);
			if (pay == null || pay.getPayStatus() != PayRecordEntity.PAY_STATUS_INIT) {
				throw new BusinessException("该订单已经支付或取消");
			}
			String payAmount = pay.getAmount().toString();
			if (NumberUtils.isNumber(payAmount) == false) {
				throw new BusinessException("请输入数字");
			}
			if (Double.parseDouble(payAmount) < 0.01) {
				throw new BusinessException("金额大于0.01元");
			}
			String orderId = payNo;

			String serverPort = "";
			if (req.getServerPort() != 80) {
				serverPort = ":" + req.getServerPort();
			}
			String contextPath = req.getContextPath();
			if (contextPath == "/") {
				contextPath = "";
			}
			String path = req.getScheme() + "://" + req.getServerName() + serverPort + contextPath;
			String backUrl = path + "wap/wxPay/backResponse/";
			String ip = IPUtils.getRealIP(req);

			// 用户openId
			UserEntity user = userDao.findOne(RequestUtils.getCurrentUser(req).getId());
			if (user.getWeixinOpenId() == null) {
				// 可以不需要
				throw new BusinessException("请使用微信账号登录");
			}
			String timeStamp = WeixinSign.create_timestamp();
			modelMap.addAttribute("appId", appId);
			modelMap.addAttribute("timeStamp", timeStamp);
			@SuppressWarnings("unchecked")
			Map<String, String> map = WxPayUtil.getWeixinPayMap(appId, appKey, merId, orderId, payAmount, backUrl, ip, user.getWeixinOpenId(),timeStamp);
			for (@SuppressWarnings("rawtypes")
			Map.Entry entry : map.entrySet()) {
				modelMap.addAttribute(entry.getKey().toString(), entry.getValue());
				logger.info(entry.getKey().toString(), entry.getValue());
			}
			if (StringUtils.contains((String) map.get("return_code"), "FAIL")) {
				throw new BusinessException((String) map.get("return_msg"));
			}

			return "/wap/buy/weixinpay_result";
		} catch (BusinessException ex) {
			modelMap.addAttribute("message", ex.getMessage());
		} catch (Exception ex) {
			modelMap.addAttribute("message", "系统错误");
		}
		return "/wap/buy/pay_error";
	}

	/**
	 * 支付结果
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author lvzf 2016年10月8日
	 */
	@RequestMapping("/qrcodePaySuccess")
	public String qrcodePaySuccess(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, String payNo) {
		PayRecordEntity pay = payService.getPayByPayNo(payNo);
		if (pay != null && pay.getPayStatus() == PayRecordEntity.PAY_STATUS_SUCCESS) {
			return "/wap/buy/pay_result";
		}
		return "/wap/buy/pay_error";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/backResponse")
	public void weixin_notify(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 读取参数
		InputStream inputStream;
		StringBuffer sb = new StringBuffer();
		inputStream = request.getInputStream();
		String s;
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		while ((s = in.readLine()) != null) {
			sb.append(s);
		}
		in.close();
		inputStream.close();

		// 解析xml成map
		Map<String, String> m = new HashMap<String, String>();
		m = XMLUtil.doXMLParse(sb.toString());

		// 过滤空 设置 TreeMap
		SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
		Iterator it = m.keySet().iterator();
		while (it.hasNext()) {
			String parameter = (String) it.next();
			String parameterValue = m.get(parameter);

			String v = "";
			if (null != parameterValue) {
				v = parameterValue.trim();
			}
			packageParams.put(parameter, v);
		}

		logger.info(FastJSONUtils.toJsonString(packageParams));

		// 判断签名是否正确
		if (WxPayUtil.isTenpaySign("UTF-8", packageParams, appKey)) {
			// ------------------------------
			// 处理业务开始
			// ------------------------------
			String resXml = "";
			if ("SUCCESS".equals((String) packageParams.get("result_code"))) {
				// 这里是支付成功
				// ////////执行自己的业务逻辑////////////////
				String mch_id = (String) packageParams.get("mch_id");
				String openid = (String) packageParams.get("openid");
				String is_subscribe = (String) packageParams.get("is_subscribe");
				String out_trade_no = (String) packageParams.get("out_trade_no");
				String trade_no = request.getParameter("trade_no");
				String trade_status = request.getParameter("result_code");

				String total_fee = (String) packageParams.get("total_fee");

				logger.info("mch_id:" + mch_id);
				logger.info("openid:" + openid);
				logger.info("is_subscribe:" + is_subscribe);
				logger.info("out_trade_no:" + out_trade_no);
				logger.info("total_fee:" + total_fee);

				// ////////执行自己的业务逻辑////////////////

				logger.info("支付成功");
				// 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
				resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
				PayRecordEntity pay = payService.getPayByPayNo(out_trade_no);
				if (pay != null && pay.getPayStatus()== PayRecordEntity.PAY_STATUS_INIT) {
					pay.setPayStatus(PayRecordEntity.PAY_STATUS_SUCCESS);// 付款
					// pay.setThirdPayTime(DateUtil.getStringToDate(notify_time,
					// DateUtil.YMDhhmmss));
					pay.setThirdPayTime(new Date());
					pay.setThirdTradeNo(trade_no);
					pay.setThirdTradeStatus(trade_status);
					// 生成中奖号码
					UserEntity user = userDao.findOne(pay.getUserId());
					ygBuyService.createWinNo(user, pay);
				}
			} else {
				logger.info("支付失败,错误信息：" + packageParams.get("err_code"));
				resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
			}
			// ------------------------------
			// 处理业务完毕
			// ------------------------------
			BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
			out.write(resXml.getBytes());
			out.flush();
			out.close();
		} else {
			logger.info("通知签名验证失败");
		}

	}
}
