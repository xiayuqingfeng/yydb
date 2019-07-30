package cn.com.yyg.front.web.member.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.easy.exception.BusinessException;
import cn.com.yyg.base.entity.PayRecordEntity;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.base.entity.YgCartEntity;
import cn.com.yyg.front.dao.UserDao;
import cn.com.yyg.front.dao.YgCartDao;
import cn.com.yyg.front.dao.YgProductDao;
import cn.com.yyg.front.service.PayService;
import cn.com.yyg.front.service.ScoreService;
import cn.com.yyg.front.service.UserService;
import cn.com.yyg.front.service.YgBuyService;
import cn.com.yyg.front.utils.RequestUtils;
import cn.com.yyg.front.web.pub.controller.PubCartController;
import cn.com.yyg.front.web.pub.dto.CartDto;

import com.google.common.collect.Lists;

@Controller
@RequestMapping("/member/buy")
public class MemberBuyController {
	private Logger logger = LoggerFactory.getLogger(MemberBuyController.class);
	/** 云购购物车 */
	@Autowired
	private YgCartDao ygCartDao; 
	/** 云购 */
	@Autowired
	private YgProductDao ygProductDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private YgBuyService ygBuyService;
	@Autowired
    private PayService payService;
	/**积分 */
	@Autowired
	private ScoreService scoreService;
	@Autowired
	private UserService userService;
	@Autowired
	private PubCartController pubCartController;
	@RequestMapping(value = "/paycheck", method = RequestMethod.POST)
	public String checkout(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res,Long[] ids){
		UserEntity user=userDao.findOne(RequestUtils.getCurrentUser(req).getId());
		List<YgCartEntity> list=ygCartDao.findByUserIdAndYgProductIdIn(user.getId(), Lists.newArrayList(ids));
		Map<Long,CartDto> cartMap=new LinkedHashMap<Long,CartDto>(); 
		for(YgCartEntity y:list){
			CartDto dto=new CartDto(y.getYgProductId(),y.getBuyNum());
			dto.setUpdateDate(y.getLastModifyTime().getTime());
			cartMap.put(y.getYgProductId(),dto);
		}
		List<CartDto> results= pubCartController.getCartList(modelMap, req, res,cartMap);
		long totalPrice=0;
		for(CartDto c:results){
			if(c.isSuccess()){
				totalPrice+=c.getBuyNum()*c.getProduct().getSinglePrice();
			}
		}
		modelMap.addAttribute("results", results);
		modelMap.addAttribute("totalPrice", totalPrice);
		modelMap.addAttribute("accountBalance", user.getAccountBalance());
		return "/pub/buy/pay_check";
	}
	/**
	 * 立即支付
	 *@param modelMap
	 *@param req
	 *@param res
	 *@param accountBalancePay 是否账户余额支付
	 *@param ids 云购商品id
	 *@return
	 * @author lvzf 2016年8月27日
	 */
	@RequestMapping(value = "/paySubmit", method = RequestMethod.POST)
	public String pay(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res,int payType,Long[] ids){
		try{
			if(ids==null || ids.length==0){
				throw new BusinessException("没有选择商品");
			}
			UserEntity user=userDao.findOne(RequestUtils.getCurrentUser(req).getId());			
			modelMap.addAttribute("user", user);	
			PayRecordEntity pay=ygBuyService.createPayOrder(user, payType, Lists.newArrayList(ids));
			//余额支付
			if(payType==PayRecordEntity.PAY_TYPE_YUE){
				if(user.getAccountBalance().longValue()<pay.getAmount().longValue()){
					throw new BusinessException("账号余额不足");
				}
				//生成中奖号码
				ygBuyService.createWinNo(user,pay);
				RequestUtils.setCurrentUser(req, user);
			}//支付宝支付
			else if(payType==PayRecordEntity.PAY_TYPE_ALI){
				return "redirect:/alipay.html?payNo="+pay.getPayNo();
			}//微信扫码支付
			else if(payType==PayRecordEntity.PAY_TYPE_WX){
				return "redirect:/wxPay/createQrcode.html?payNo="+pay.getPayNo();
			}

		}catch(BusinessException e){ 
			modelMap.addAttribute("message", e.getMessage());
			return "/pub/buy/pay_error";  
		}catch(Exception e){
			logger.error("支付错误：", e);
			modelMap.addAttribute("message", "未知错误");
			return "/pub/buy/pay_error"; 
		}
		return "/pub/buy/pay_result";
	}
	/**
	 * 支付
	 *@param modelMap
	 *@param req
	 *@param res
	 *@param payNo
	 *@return
	 * @author lvzf 2016年10月8日
	 */
	@RequestMapping(value = "/pay")
	public String pay(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res,String payNo){
		try{
			UserEntity user=userDao.findOne(RequestUtils.getCurrentUser(req).getId());			
			modelMap.addAttribute("user", user);	
			PayRecordEntity pay=payService.getPayByPayNo(payNo); 
			if(pay==null || pay.getPayStatus()!=PayRecordEntity.PAY_STATUS_INIT){ 
				throw new BusinessException("该订单已经支付或取消");
			} 
			int payType =pay.getPayType();
			//余额支付
			if(payType==PayRecordEntity.PAY_TYPE_YUE){
				if(user.getAccountBalance().longValue()<pay.getAmount().longValue()){
					throw new BusinessException("账号余额不足");
				} 
				//生成中奖号码
				ygBuyService.createWinNo(user,pay);
			}//支付宝支付
			else if(payType==PayRecordEntity.PAY_TYPE_ALI){
				return "redirect:/alipay.html?payNo="+pay.getPayNo();
			}//微信扫码支付
			else if(payType==PayRecordEntity.PAY_TYPE_WX){
				return "redirect:/wxPay/createQrcode.html?payNo="+pay.getPayNo();
			}

		}catch(BusinessException e){ 
			modelMap.addAttribute("message", e.getMessage());
			return "/pub/buy/pay_error";  
		}catch(Exception e){
			logger.error("支付错误：", e);
			modelMap.addAttribute("message", "未知错误");
			return "/pub/buy/pay_error"; 
		}
		return "/pub/buy/pay_result";
	}
	/**
	 * 支付成功返回页面
	 *@param modelMap
	 *@param req
	 *@param res
	 *@param payNo
	 *@return
	 * @author lvzf 2016年9月22日
	 */
	@RequestMapping(value = "/payResult")
	public String payResult(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res,String payNo){
		UserEntity user=RequestUtils.getCurrentUser(req);
		modelMap.addAttribute("user", user);
		return "/pub/buy/pay_result";
	}
}
