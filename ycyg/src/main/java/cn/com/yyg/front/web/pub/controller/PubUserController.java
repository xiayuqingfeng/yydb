package cn.com.yyg.front.web.pub.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;

import cn.com.easy.utils.PageRequest;
import cn.com.easy.utils.PageRequestUtils;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.base.entity.UserYgEntity;
import cn.com.yyg.base.entity.YgProductEntity;
import cn.com.yyg.base.entity.YgShareEntity;
import cn.com.yyg.base.utils.PageUtils;
import cn.com.yyg.front.dao.UserDao;
import cn.com.yyg.front.dao.UserYgDao;
import cn.com.yyg.front.dao.YgProductDao;
import cn.com.yyg.front.dao.YgShareDao;
/**
 * 查看用户中奖等相关记录
 * @author lvzf	2016年9月25日
 *
 */
@Controller
@RequestMapping("/user")
public class PubUserController {
	/** 用户 */
	@Autowired
	private UserDao userDao;
	/** 用户晒单 */
	@Autowired
	private YgShareDao ygShareDao;
	/** 云购 */
	@Autowired
	private YgProductDao ygProductDao;
	/** 用户云购 */
	@Autowired
	private UserYgDao userYgDao;
	/**
	 * 首页
	 *@param modelMap
	 *@param req
	 *@param res
	 *@param userId
	 *@return
	 * @author lvzf 2016年9月25日
	 */
	@RequestMapping("/{userId}")
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res,@PathVariable Long userId) {  
		return this.duobao(modelMap, req, res, userId,new PageRequest(1,10));
	}
	/**
	 * 夺宝记录
	 *@param modelMap
	 *@param req
	 *@param res
	 *@param userId
	 *@return
	 * @author lvzf 2016年9月25日
	 */
	@RequestMapping("/{userId}/duobao")
	public String duobao(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res,@PathVariable Long userId,	PageRequest pageRequest) {  
		UserEntity userInfo=userDao.findOne(userId);
		if(userInfo==null){
			return "redirect:/";
		}
		modelMap.addAttribute("userInfo",userInfo);
		pageRequest.setPageSize(10);
		pageRequest.setOrderBy("createTime");
		pageRequest.setOrderDir("desc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		Page<UserYgEntity> springPage=	userYgDao.findByBuyUserId(userId,pb);
		cn.com.easy.utils.Page<UserYgEntity> page = PageUtils.getPage(springPage);
		for(UserYgEntity s:page.getResult()){
			YgProductEntity ygProduct=ygProductDao.findOne(s.getYgProductId());
			s.setYgProduct(ygProduct);
		}
		modelMap.addAttribute("userId",userId);
		modelMap.addAttribute("page",page);
		return "/pub/member/member_duobao";
	}
	/**
	 * 中奖记录
	 *@param modelMap
	 *@param req
	 *@param res
	 *@param userId
	 *@return
	 * @author lvzf 2016年9月25日
	 */
	@RequestMapping("/{userId}/win")
	public String win(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res,@PathVariable Long userId,	PageRequest pageRequest) { 
		UserEntity userInfo=userDao.findOne(userId);	
		if(userInfo==null){
			return "redirect:/";
		}
		modelMap.addAttribute("userInfo",userInfo);
		pageRequest.setPageSize(12);
		pageRequest.setOrderBy("createTime");
		pageRequest.setOrderDir("desc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		Page<UserYgEntity> springPage=	userYgDao.findByBuyUserIdAndWin(userId,true,pb);
		cn.com.easy.utils.Page<UserYgEntity> page = PageUtils.getPage(springPage);
		for(UserYgEntity s:page.getResult()){
			YgProductEntity ygProduct=ygProductDao.findOne(s.getYgProductId());
			s.setYgProduct(ygProduct);
		}
		modelMap.addAttribute("userId",userId);
		modelMap.addAttribute("page",page);
		return "/pub/member/member_win";
	}	
	/**
	 * 他的晒单分享
	 *@param modelMap
	 *@param req
	 *@param res
	 *@param userId
	 *@return
	 * @author lvzf 2016年9月25日
	 */
	@RequestMapping("/{userId}/share")
	public String share(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res,@PathVariable Long userId) {  
		UserEntity userInfo=userDao.findOne(userId);	
		if(userInfo==null){
			return "redirect:/";
		}
		modelMap.addAttribute("userInfo",userInfo);
		long total=ygShareDao.countByUserIdAndAudit(userId,true);
		modelMap.addAttribute("total",total);
		modelMap.addAttribute("userId",userId);
		return "/pub/member/member_share";
	}
	/**
	 * 查看购买记录
	 *@param modelMap
	 *@param req
	 *@param res
	 *@param userId
	 *@param ygProductId
	 *@return
	 * @author lvzf 2016年9月25日
	 */
	@RequestMapping("/{userId}/viewBuyRecord")
	public String viewBuyRecord(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res,@PathVariable Long userId,Long ygProductId){
		 List<UserYgEntity> myBuyRecords=userYgDao.findByYgProductIdAndBuyUserId(ygProductId, userId);
		 long myTotalBuyNums=0;
		 for(UserYgEntity re:myBuyRecords){
			 myTotalBuyNums+=re.getBuyNum();
		 }
		 List<String> mBuyNos=Lists.newArrayList();
		 for(UserYgEntity re:myBuyRecords){
			 mBuyNos.addAll(Lists.newArrayList(re.getBuyNos().split(",")));
		 }
		 if(mBuyNos.size()>=9){
			 modelMap.addAttribute("mBuyNos",mBuyNos.subList(0, 9));
		 }else{
			 modelMap.addAttribute("mBuyNos",mBuyNos);
		 }
		modelMap.addAttribute("myTotalBuyNums",myTotalBuyNums);
		modelMap.addAttribute("myBuyRecords",myBuyRecords);
		modelMap.addAttribute("userId",userId);
		return "/pub/member/member_view_buy_record";
	}
	/**
	 * 他的晒单分享
	 *@param modelMap
	 *@param req
	 *@param res
	 *@param pageNo
	 *@return
	 * @author lvzf 2016年9月25日
	 */
	@RequestMapping("/{userId}/share_ajax/{pageNo}")
	public String shareAjax(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res,@PathVariable Long userId,@PathVariable Integer pageNo){
		
		PageRequest pageRequest = new PageRequest(pageNo, 9);
		pageRequest.setOrderBy("createTime");
		pageRequest.setOrderDir("desc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		Page<YgShareEntity> springPage=ygShareDao.findByUserIdAndAudit(userId,true, pb);
		cn.com.easy.utils.Page<YgShareEntity> page = PageUtils.getPage(springPage);
		for(YgShareEntity s:page.getResult()){
			YgProductEntity ygProduct=ygProductDao.findOne(s.getYgProductId());
			s.setYgProduct(ygProduct);
		}
		modelMap.addAttribute("page",page);
		modelMap.addAttribute("userId",userId);
		return "/pub/member/member_share_ajax";
	}
	
}
