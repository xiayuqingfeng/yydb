package cn.com.yyg.front.web.pub.controller;

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

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.easy.dto.MessageDTO;
import cn.com.easy.exception.BusinessException;
import cn.com.easy.utils.FastJSONUtils;
import cn.com.easy.utils.IPUtils;
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.yyg.base.entity.PayRecordEntity;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.front.dao.UserDao;
import cn.com.yyg.front.service.PayService;
import cn.com.yyg.front.service.YgBuyService;
import cn.com.yyg.pay.util.WxPayUtil;
import cn.com.yyg.pay.wxpay.sdk.XMLUtil;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

/**
 * 微信扫描支付
 * 
 * @author lvzf 2016年8月30日
 * 
 */
@Controller
@RequestMapping("/wxPay")
public class WxPayController {

	private Logger logger = LoggerFactory.getLogger(WxPayController.class);
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
	 * 微信二维码扫码
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param payAmount
	 *            元
	 * @param payRemark
	 * @return
	 * @author lvzf 2016年8月30日
	 */
	@RequestMapping("/createQrcode")
	public String createQrcode(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, String payNo) {
		try {
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
			String backUrl = path + "/wxPay/backResponse/";
			String ip =IPUtils.getRealIP(req);
			String url = WxPayUtil.createQrcode(appId, appKey, merId, orderId, payAmount, backUrl, ip);
			modelMap.addAttribute("qrcodeUrl", url);
			modelMap.addAttribute("payAmount", payAmount);
			return "/pub/buy/pay_wx_qrcode";
		} catch (BusinessException ex) {
			modelMap.addAttribute("message", ex.getMessage());
		} catch (Exception ex) {
			modelMap.addAttribute("message", "系统错误");
		}
		return "/pub/buy/pay_error";
	}

	/**
	 * 扫码支付监听：如果已支付跳转的成功页面
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param payNo
	 * @return
	 * @author lvzf 2016年10月8日
	 */
	@RequestMapping("/qrcodePayCheck")
	public void qrcodePayCheck(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, String payNo) {
		PayRecordEntity pay = payService.getPayByPayNo(payNo);
		if (pay != null && pay.getPayStatus() != PayRecordEntity.PAY_STATUS_INIT) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, pay.getPayStatusName()));
		} else {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "还未支付"));
		}

	}

	/**
	 * 支付成功
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
			return "/pub/buy/pay_result";
		}
		return "/pub/buy/pay_error";
	}

	/**
	 * 根据url生成二维码图片
	 * 
	 * @param content
	 * @param res
	 * @author lvzf 2016年10月9日
	 */
	@RequestMapping("/resQrcode")
	public void encoderQRCode(String content, HttpServletResponse res) {
		try {
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			// 用于设置QR二维码参数
			Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 300, 300, hints);
			MatrixToImageWriter.writeToStream(bitMatrix, "jpg", res.getOutputStream());
		} catch (Exception e) {
			logger.error("生成二维码错误", e);
		}
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
				if (pay != null && pay.getPayStatus() == PayRecordEntity.PAY_STATUS_INIT) {
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
