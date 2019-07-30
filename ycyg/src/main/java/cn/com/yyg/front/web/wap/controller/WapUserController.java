package cn.com.yyg.front.web.wap.controller;

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
import cn.com.yyg.front.dao.UserScoreDao;
import cn.com.yyg.front.dao.UserYgDao;
import cn.com.yyg.front.dao.YgProductDao;
import cn.com.yyg.front.dao.YgShareDao;
import cn.com.yyg.front.service.ScoreService;

/**
 * 查看用户中奖等相关记录
 * 
 * @author lvzf 2016年9月25日
 * 
 */
@Controller
@RequestMapping("/wap/user")
public class WapUserController {
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
	
	/** 用户积分 */
	@Autowired
	private UserScoreDao userScoreDao;

	/**
	 * 首页
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param userId
	 * @return
	 * @author linwk 2016年10月8日
	 */
	@RequestMapping("/{userId}")
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long userId) {
		UserEntity userInfo = userDao.findOne(userId);
		if (userInfo == null) {
			return "redirect:/";
		}
		modelMap.addAttribute("userInfo", userInfo);
		modelMap.addAttribute("userId", userId);
		// 用户总积分
		Long scores = userScoreDao.totalScoreByUserId(userInfo.getId());
		if (scores == null) {
			scores = 0L;
		}
		modelMap.addAttribute("scores", scores);
		return "/wap/user/member_index";
	}

	/**
	 * 夺宝记录
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param userId
	 * @param pageRequest
	 * @return
	 * @author linwk 2016年10月8日
	 */
	@RequestMapping("/load_db/{userId}")
	public String duobao(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long userId, PageRequest pageRequest, Long vid) {
		UserEntity userInfo = userDao.findOne(userId);
		if (userInfo == null) {
			return "redirect:/";
		}
		modelMap.addAttribute("userInfo", userInfo);

		if (vid != null) {
			UserYgEntity userYgEntity = userYgDao.findOne(vid);
			if (userYgEntity != null) {
				YgProductEntity ygProduct = ygProductDao.findOne(userYgEntity.getYgProductId());
				userYgEntity.setYgProduct(ygProduct);
				modelMap.addAttribute("item", userYgEntity);
				return "/wap/user/load_db_vid";
			}
		}
		pageRequest.setPageSize(10);
		pageRequest.setOrderBy("createTime");
		pageRequest.setOrderDir("desc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		Page<UserYgEntity> springPage = userYgDao.findByBuyUserId(userId, pb);
		cn.com.easy.utils.Page<UserYgEntity> page = PageUtils.getPage(springPage);
		for (UserYgEntity s : page.getResult()) {
			YgProductEntity ygProduct = ygProductDao.findOne(s.getYgProductId());
			s.setYgProduct(ygProduct);
		}
		modelMap.addAttribute("page", page);
		modelMap.addAttribute("userId", userId);
		return "/wap/user/load_db";
	}

	/**
	 * 夺宝记录
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param userId
	 * @param pageNo
	 * @return
	 * @author linwk 2016年10月8日
	 */
	@RequestMapping("/load_db/{userId}/{pageNo}")
	public String duobaoPage(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long userId, @PathVariable int pageNo) {
		return duobao(modelMap, req, res, userId, new PageRequest(pageNo, 10), null);
	}

	/**
	 * 中奖记录
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param userId
	 * @param pageRequest
	 * @return
	 * @author linwk 2016年10月8日
	 */
	@RequestMapping("/load_dbCod/{userId}")
	public String load_dbCod(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long userId, PageRequest pageRequest) {
		UserEntity userInfo = userDao.findOne(userId);
		if (userInfo == null) {
			return "redirect:/";
		}
		modelMap.addAttribute("userInfo", userInfo);
		pageRequest.setPageSize(10);
		pageRequest.setOrderBy("createTime");
		pageRequest.setOrderDir("desc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		Page<UserYgEntity> springPage = userYgDao.findByBuyUserIdAndWin(userId, true, pb);
		cn.com.easy.utils.Page<UserYgEntity> page = PageUtils.getPage(springPage);
		for (UserYgEntity s : page.getResult()) {
			YgProductEntity ygProduct = ygProductDao.findOne(s.getYgProductId());
			s.setYgProduct(ygProduct);
		}
		modelMap.addAttribute("page", page);
		modelMap.addAttribute("userId", userId);
		return "/wap/user/load_dbCod";
	}

	/**
	 * 中奖记录
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param userId
	 * @param pageNo
	 * @return
	 * @author linwk 2016年10月8日
	 */
	@RequestMapping("/load_dbCod/{userId}/{pageNo}")
	public String load_dbCodPage(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long userId, @PathVariable int pageNo) {
		return load_dbCod(modelMap, req, res, userId, new PageRequest(pageNo, 10));
	}

	/**
	 * 
	 * Ta的晒单分享
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param userId
	 * @return
	 * @author linwk 2016年10月8日
	 */
	@RequestMapping("/load_share/{userId}")
	public String share(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long userId, PageRequest pageRequest) {
		UserEntity userInfo = userDao.findOne(userId);
		if (userInfo == null) {
			return "redirect:/";
		}
		modelMap.addAttribute("userInfo", userInfo);
		pageRequest.setPageSize(10);
		pageRequest.setOrderBy("createTime");
		pageRequest.setOrderDir("desc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		Page<YgShareEntity> springPage = ygShareDao.findByUserIdAndAudit(userId, true, pb);
		cn.com.easy.utils.Page<YgShareEntity> page = PageUtils.getPage(springPage);
		for (YgShareEntity s : page.getResult()) {
			YgProductEntity ygProduct = ygProductDao.findOne(s.getYgProductId());
			s.setYgProduct(ygProduct);
		}
		modelMap.addAttribute("page", page);
		modelMap.addAttribute("userId", userId);
		return "/wap/user/load_share";
	}

	/**
	 * Ta的晒单分享
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param userId
	 * @param pageNo
	 * @return
	 * @author linwk 2016年10月8日
	 */
	@RequestMapping("/load_share/{userId}/{pageNo}")
	public String sharePage(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long userId, @PathVariable int pageNo) {
		return share(modelMap, req, res, userId, new PageRequest(pageNo, 10));
	}

	/**
	 * 加好友
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param userId
	 * @return
	 * @author linwk 2016年9月30日
	 */
	@RequestMapping("/load_fri/{userId}")
	public String load_fri(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long userId) {
		UserEntity userInfo = userDao.findOne(userId);
		if (userInfo == null) {
			return "redirect:/";
		}
		modelMap.addAttribute("userInfo", userInfo);
		long total = ygShareDao.countByUserIdAndAudit(userId, true);
		modelMap.addAttribute("total", total);
		modelMap.addAttribute("userId", userId);
		return "/wap/user/load_fri";
	}

	/**
	 * 访客
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param userId
	 * @return
	 * @author linwk 2016年9月30日
	 */
	@RequestMapping("/load_visit/{userId}")
	public String load_visit(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long userId) {
		UserEntity userInfo = userDao.findOne(userId);
		if (userInfo == null) {
			return "redirect:/";
		}
		modelMap.addAttribute("userInfo", userInfo);
		long total = ygShareDao.countByUserIdAndAudit(userId, true);
		modelMap.addAttribute("total", total);
		modelMap.addAttribute("userId", userId);
		return "/wap/user/load_visit";
	}

	/**
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param userId
	 * @return
	 * @author linwk 2016年9月30日
	 */
	@RequestMapping("/load_db_vid/{userId}")
	public String load_db_vid(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long userId) {
		UserEntity userInfo = userDao.findOne(userId);
		if (userInfo == null) {
			return "redirect:/";
		}
		modelMap.addAttribute("userInfo", userInfo);
		long total = ygShareDao.countByUserIdAndAudit(userId, true);
		modelMap.addAttribute("total", total);
		modelMap.addAttribute("userId", userId);
		return "/wap/user/load_db_vid";
	}

	/**
	 * 查看购买记录
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param userId
	 * @param ygProductId
	 * @return
	 * @author linwk 2016年10月8日
	 */
	@RequestMapping("/{userId}/viewBuyRecord")
	public String viewBuyRecord(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long userId, Long ygProductId) {
		List<UserYgEntity> myBuyRecords = userYgDao.findByYgProductIdAndBuyUserId(ygProductId, userId);
		long myTotalBuyNums = 0;
		for (UserYgEntity re : myBuyRecords) {
			myTotalBuyNums += re.getBuyNum();
		}
		List<String> mBuyNos = Lists.newArrayList();
		for (UserYgEntity re : myBuyRecords) {
			mBuyNos.addAll(Lists.newArrayList(re.getBuyNos().split(",")));
		}
		if (mBuyNos.size() >= 9) {
			modelMap.addAttribute("mBuyNos", mBuyNos.subList(0, 9));
		} else {
			modelMap.addAttribute("mBuyNos", mBuyNos);
		}
		modelMap.addAttribute("myTotalBuyNums", myTotalBuyNums);
		modelMap.addAttribute("myBuyRecords", myBuyRecords);
		modelMap.addAttribute("userId", userId);
		return "/wap/user/member_view_buy_record";
	}

}
