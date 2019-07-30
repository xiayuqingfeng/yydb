package cn.com.yyg.front.web.pub.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.easy.utils.PageRequest;
import cn.com.easy.utils.PageRequestUtils;
import cn.com.yyg.base.entity.ProductEntity;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.base.entity.YgProductEntity;
import cn.com.yyg.base.entity.YgShareEntity;
import cn.com.yyg.base.utils.PageUtils;
import cn.com.yyg.front.dao.ProductDao;
import cn.com.yyg.front.dao.UserDao;
import cn.com.yyg.front.dao.YgProductDao;
import cn.com.yyg.front.dao.YgShareDao;
import cn.com.yyg.front.service.common.CommonService;
import cn.com.yyg.front.utils.RequestUtils;
/**
 * 晒单分享
 * @author lvzf	2016年9月18日
 *
 */
@Controller
@RequestMapping("/share")
public class PubShareController {

	/** 用户 */
	@Autowired
	private UserDao userDao;
	/** 前端公共服务 */
	@Autowired
	private CommonService frontCommonService;
	/** 用户晒单 */
	@Autowired
	private YgShareDao ygShareDao;
	/** 云购 */
	@Autowired
	private YgProductDao ygProductDao;
	/** 商品 */
	@Autowired
	private ProductDao productDao;
	@RequestMapping
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, String keyword) {
		long total=ygShareDao.countByAudit(true);
		modelMap.addAttribute("total",total);
		return "/pub/share/share";
	}
	@RequestMapping("/share_ajax/{pageNo}")
	public String shareAjax(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res,@PathVariable Integer pageNo){
		
		PageRequest pageRequest = new PageRequest(pageNo, 8);
		pageRequest.setOrderBy("createTime");
		pageRequest.setOrderDir("desc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		Page<YgShareEntity> springPage=ygShareDao.findByAudit(true, pb);
		cn.com.easy.utils.Page<YgShareEntity> page = PageUtils.getPage(springPage);
		for(YgShareEntity s:page.getResult()){
			YgProductEntity ygProduct=ygProductDao.findOne(s.getYgProductId());
			s.setYgProduct(ygProduct);
		}
		modelMap.addAttribute("page",page);
		return "/pub/share/share_ajax";
	}
	/**
	 * 分享详情
	 *@param modelMap
	 *@param req
	 *@param res
	 *@param userId
	 *@param id
	 *@return
	 * @author lvzf 2016年9月28日
	 */
	@RequestMapping("/{id}")
	public String shareDetail(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res,@PathVariable Long id){

		YgShareEntity share=ygShareDao.findOne(id);
		if(share==null){
			return "redirect:/";
		}
		//为审核通过的，只能自己看
		if(share.isAudit()==false){
			UserEntity user=RequestUtils.getCurrentUser(req);
			if(user==null || !share.getUserId().equals(user.getId())){
				return "redirect:/";
			}
		}
		YgProductEntity ygProduct=ygProductDao.findOne(share.getYgProductId());
		share.setYgProduct(ygProduct);
		UserEntity userInfo=userDao.findOne(share.getUserId());	 
		modelMap.addAttribute("userInfo",userInfo);
		if(!share.getUserId().equals(userInfo.getId())){
			return "redirect:/";
		}
		modelMap.addAttribute("share",share);
		//商品
		ProductEntity op=productDao.findOne(share.getProductId());
		if(op==null){
			return "redirect:/";
		}
		//获取最新一期商品
		modelMap.addAttribute("newYgProduct",ygProductDao.findByProductIdAndPeriod(op.getId(), op.getPeriod()));
		return "/pub/member/member_share_detail";
	}
}
