package cn.com.yyg.front.web.member.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.com.easy.dto.MessageDTO;
import cn.com.easy.exception.BusinessException;
import cn.com.easy.utils.FastJSONUtils;
import cn.com.easy.utils.PageRequest;
import cn.com.easy.utils.PageRequestUtils;
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.easy.utils.SecurityUtils;
import cn.com.easy.utils.ValidUtils;
import cn.com.yyg.base.entity.AccessoryEntity;
import cn.com.yyg.base.entity.AddressEntity;
import cn.com.yyg.base.entity.ProductEntity;
import cn.com.yyg.base.entity.UserAccountDetailEntity;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.base.entity.UserScoreEntity;
import cn.com.yyg.base.entity.UserYgEntity;
import cn.com.yyg.base.entity.YgProductEntity;
import cn.com.yyg.base.entity.YgProductOrderEntity;
import cn.com.yyg.base.entity.YgShareEntity;
import cn.com.yyg.base.utils.DateUtil;
import cn.com.yyg.base.utils.FileUploadVO;
import cn.com.yyg.base.utils.PageUtils;
import cn.com.yyg.base.utils.UploadFileUtil;
import cn.com.yyg.front.dao.AddressDao;
import cn.com.yyg.front.dao.AreaDao;
import cn.com.yyg.front.dao.ProductDao;
import cn.com.yyg.front.dao.UserAccountDetailDao;
import cn.com.yyg.front.dao.UserDao;
import cn.com.yyg.front.dao.UserScoreDao;
import cn.com.yyg.front.dao.UserYgDao;
import cn.com.yyg.front.dao.YgProductDao;
import cn.com.yyg.front.dao.YgProductOrderDao;
import cn.com.yyg.front.dao.YgShareDao;
import cn.com.yyg.front.service.ScoreService;
import cn.com.yyg.front.utils.RequestUtils;

import com.google.common.collect.Lists;

/**
 * 用户基本信息
 * 
 * @author lvzf 2016年9月25日
 * 
 */
@Controller
@RequestMapping("/member")
public class MemberController {
	private Logger logger = LoggerFactory.getLogger(MemberController.class);
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
	/** 商品 */
	@Autowired
	private ProductDao productDao;
	/** 收货地址dao */
	@Autowired
	private AddressDao addressDao;
	/** 地区dao */
	@Autowired
	private AreaDao areaDao;
	/** 用户云购订单 */
	@Autowired
	private YgProductOrderDao ygProductOrderDao;
	/** 用户积分 */
	@Autowired
	private UserScoreDao userScoreDao;
	@Autowired
	private UserAccountDetailDao userAccountDetailDao;
	/** 积分 */
	@Autowired
	private ScoreService scoreService;
	
	@Value("#{configProperties['www.domain']}")
	private String domain;

	/**
	 * 基本信息首页
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author lvzf 2016年9月25日
	 */
	@RequestMapping
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		UserEntity user = userDao.findOne(RequestUtils.getCurrentUser(req).getId());
		PageRequest pageRequest = new PageRequest(1, 10);
		modelMap.addAttribute("userInfo", user);
		pageRequest.setOrderBy("createTime");
		pageRequest.setOrderDir("desc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		Page<UserYgEntity> springPage = userYgDao.findByBuyUserId(user.getId(), pb);
		cn.com.easy.utils.Page<UserYgEntity> page = PageUtils.getPage(springPage);
		for (UserYgEntity s : page.getResult()) {
			YgProductEntity ygProduct = ygProductDao.findOne(s.getYgProductId());
			s.setYgProduct(ygProduct);
		}
		modelMap.addAttribute("result", page.getResult());
		// 用户总积分
		Long scores = userScoreDao.totalScoreByUserId(user.getId());
		if (scores == null) {
			scores = 0L;
		}
		modelMap.addAttribute("scores", scores);
		// 连续签到次数
		int qiandaos = scoreService.getLianxuQiandaoDays(user);
		modelMap.addAttribute("qiandaos", qiandaos);
		// 今天是否签到
		modelMap.addAttribute("todayQian", scoreService.todayQiandao(user));
		return "/member/member_index";
	}

	/**
	 * 夺宝记录
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param userId
	 * @return
	 * @author lvzf 2016年9月25日
	 */
	@RequestMapping("/duobao")
	public String duobao(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, PageRequest pageRequest) {
		UserEntity user = userDao.findOne(RequestUtils.getCurrentUser(req).getId());
		modelMap.addAttribute("userInfo", user);
		pageRequest.setPageSize(10);
		pageRequest.setOrderBy("createTime");
		pageRequest.setOrderDir("desc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		Page<UserYgEntity> springPage = userYgDao.findByBuyUserId(user.getId(), pb);
		cn.com.easy.utils.Page<UserYgEntity> page = PageUtils.getPage(springPage);
		for (UserYgEntity s : page.getResult()) {
			YgProductEntity ygProduct = ygProductDao.findOne(s.getYgProductId());
			s.setYgProduct(ygProduct);
		}
		modelMap.addAttribute("page", page);
		return "/member/member_duobao";
	}

	/**
	 * 中奖记录
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param userId
	 * @return
	 * @author lvzf 2016年9月25日
	 */
	@RequestMapping("/win")
	public String win(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, PageRequest pageRequest) {
		UserEntity user = userDao.findOne(RequestUtils.getCurrentUser(req).getId());
		modelMap.addAttribute("userInfo", user);
		pageRequest.setPageSize(12);
		pageRequest.setOrderBy("createTime");
		pageRequest.setOrderDir("desc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		Page<UserYgEntity> springPage = userYgDao.findByBuyUserIdAndWin(user.getId(), true, pb);
		cn.com.easy.utils.Page<UserYgEntity> page = PageUtils.getPage(springPage);
		for (UserYgEntity s : page.getResult()) {
			YgProductEntity ygProduct = ygProductDao.findOne(s.getYgProductId());
			s.setYgProduct(ygProduct);
		}
		modelMap.addAttribute("page", page);
		return "/member/member_win";
	}

	/**
	 * 晒单分享
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param userId
	 * @return
	 * @author lvzf 2016年9月25日
	 */
	@RequestMapping("/share")
	public String share(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		UserEntity user = userDao.findOne(RequestUtils.getCurrentUser(req).getId());
		modelMap.addAttribute("userInfo", user);
		long total = ygShareDao.countByUserId(user.getId());
		modelMap.addAttribute("total", total);
		return "/member/member_share";
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
	 * @author lvzf 2016年9月25日
	 */
	@RequestMapping("/viewBuyRecord")
	public String viewBuyRecord(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, Long ygProductId) {
		UserEntity user = userDao.findOne(RequestUtils.getCurrentUser(req).getId());
		List<UserYgEntity> myBuyRecords = userYgDao.findByYgProductIdAndBuyUserId(ygProductId, user.getId());
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
		return "/member/member_view_buy_record";
	}

	/**
	 * 晒单分享分页
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param pageNo
	 * @return
	 * @author lvzf 2016年9月25日
	 */
	@RequestMapping("/share_ajax/{pageNo}")
	public String shareAjax(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Integer pageNo) {
		UserEntity user = userDao.findOne(RequestUtils.getCurrentUser(req).getId());
		PageRequest pageRequest = new PageRequest(pageNo, 9);
		pageRequest.setOrderBy("createTime");
		pageRequest.setOrderDir("desc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		Page<YgShareEntity> springPage = ygShareDao.findByUserId(user.getId(),pb);
		cn.com.easy.utils.Page<YgShareEntity> page = PageUtils.getPage(springPage);
		for (YgShareEntity s : page.getResult()) {
			YgProductEntity ygProduct = ygProductDao.findOne(s.getYgProductId());
			s.setYgProduct(ygProduct);
		}
		modelMap.addAttribute("page", page);
		return "/member/member_share_ajax";
	}

	/**
	 * 分享详情
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param userId
	 * @param id
	 * @return
	 * @author lvzf 2016年9月28日
	 */
	@RequestMapping("/share/{id}")
	public String shareDetail(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long id) {

		YgShareEntity share = ygShareDao.findOne(id);
		if (share == null) {
			return "redirect:/";
		}
		YgProductEntity ygProduct = ygProductDao.findOne(share.getYgProductId());
		share.setYgProduct(ygProduct);
		UserEntity userInfo = userDao.findOne(share.getUserId());
		modelMap.addAttribute("userInfo", userInfo);
		if (!share.getUserId().equals(userInfo.getId())) {
			return "redirect:/";
		}
		modelMap.addAttribute("share", share);
		// 商品
		ProductEntity op = productDao.findOne(share.getProductId());
		if (op == null) {
			return "redirect:/";
		}
		// 获取最新一期商品
		modelMap.addAttribute("newYgProduct", ygProductDao.findByProductIdAndPeriod(op.getId(), op.getPeriod()));
		return "/member/member_share_detail";
	}

	/**
	 * 发布晒单
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param orderId
	 * @return
	 * @author lvzf 2016年10月11日
	 */
	@RequestMapping("/share/pre_save/{orderId}")
	public String sharePreSave(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long orderId) {
		YgProductOrderEntity order = ygProductOrderDao.findOne(orderId);
		if (order == null) {
			return "redirect:/member/";
		}
		modelMap.addAttribute("order", order);
		return "/member/member_share_save";
	}

	/**
	 * 保存晒单
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param orderId
	 * @param share
	 * @return
	 * @author lvzf 2016年10月11日
	 * @throws BusinessException 
	 */
	@RequestMapping("/share/save")
	public String shareSave(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, Long orderId, YgShareEntity share,
			@RequestParam(value = "file") MultipartFile[] file) throws BusinessException {
		UserEntity user = userDao.findOne(RequestUtils.getCurrentUser(req).getId());
		YgProductOrderEntity order = ygProductOrderDao.findOne(orderId);
		if (order == null || !order.getUserId().equals(user.getId())) {
			return "redirect:/member/";
		}
		share.setAudit(false);
		share.setCreateBy(user.getUserName());
		YgProductEntity ygProduct = order.getYgProduct();
		share.setPeriod(ygProduct.getPeriod());
		share.setProductId(ygProduct.getProductId());
		share.setRecommend(false);
		share.setUserId(user.getId());
		share.setUserLogoPath(user.getHeadPhotoPath());
		share.setUserNickName(user.getNickName());
		share.setYgProductId(ygProduct.getId());
		// 晒单图片存放路径
		String storageFilePath = "member/share/" + user.getId() + "/" + DateUtil.getNowDateToString("yyyy") + "/" + DateUtil.getNowDateToString("MM") + "/"
				+ DateUtil.getNowDateToString("dd");
		List<String> photos = Lists.newArrayList();
		for (int i = 0; i < file.length; i++) {
			if (file[i].getSize() == 0) {
				continue;
			}
			FileUploadVO fileUpload = new FileUploadVO();
			fileUpload.setFile(file[i]);
			fileUpload.setStorageFilePath(storageFilePath);
			fileUpload.setStorageFileName(UUID.randomUUID().toString());
			fileUpload.setUserId(user.getId());
			// 上传图片到硬盘
			AccessoryEntity acc = UploadFileUtil.uploadFile(fileUpload);
			photos.add(acc.getPath() + "/" + acc.getName());
		}
		share.setPhotos(FastJSONUtils.toJsonString(photos));
		ygShareDao.save(share);
		order.setShareId(share.getId());
		order.setShareTime(new Date());
		ygProductOrderDao.save(order);
		modelMap.addAttribute("order", order);
		return this.share(modelMap, req, res);
	}

	/**
	 * 我的邀请
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author lvzf 2016年9月29日
	 */
	@RequestMapping("/yq")
	public String yaoqing(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		UserEntity user = userDao.findOne(RequestUtils.getCurrentUser(req).getId());
		modelMap.addAttribute("userInfo", user);
		// 我邀请的人员
		modelMap.addAttribute("yaoqingUsers", userDao.findByYaoqingUserId(user.getId()));
		return "/member/member_yaoqing";
	}

	/**
	 * 我的积分
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author lvzf 2016年10月4日
	 */
	@RequestMapping("/jifen")
	public String jifen(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, Integer pageNo) {
		UserEntity user = userDao.findOne(RequestUtils.getCurrentUser(req).getId());
		if (pageNo == null) {
			pageNo = 1;
		}
		PageRequest pageRequest = new PageRequest(pageNo, 10);

		pageRequest.setOrderBy("createTime");
		pageRequest.setOrderDir("desc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		Page<UserScoreEntity> springPage = userScoreDao.findByUserId(user.getId(), pb);
		cn.com.easy.utils.Page<UserScoreEntity> page = PageUtils.getPage(springPage);
		modelMap.addAttribute("page", page);
		modelMap.addAttribute("userInfo", user);
		// 用户总积分
		Long scores = userScoreDao.totalScoreByUserId(user.getId());
		if (scores == null) {
			scores = 0L;
		}
		modelMap.addAttribute("scores", scores);
		// 连续签到次数
		int qiandaos = scoreService.getLianxuQiandaoDays(user);
		modelMap.addAttribute("qiandaos", qiandaos);
		// 今天是否签到
		modelMap.addAttribute("todayQian", scoreService.todayQiandao(user));
		return "/member/member_jifen";
	}

	/**
	 * 我的夺宝金币
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param pageNo
	 * @return
	 * @author linwk 2016年10月26日
	 */
	@RequestMapping("/amountDetail")
	public String amountDetail(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, Integer pageNo) {
		UserEntity user = userDao.findOne(RequestUtils.getCurrentUser(req).getId());
		if (pageNo == null) {
			pageNo = 1;
		}
		PageRequest pageRequest = new PageRequest(pageNo, 10);
		pageRequest.setOrderBy("createTime");
		pageRequest.setOrderDir("desc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		Page<UserAccountDetailEntity> springPage = userAccountDetailDao.findListByUserId(user.getId(), pb);
		cn.com.easy.utils.Page<UserAccountDetailEntity> page = PageUtils.getPage(springPage);
		modelMap.addAttribute("page", page);
		modelMap.addAttribute("userInfo", user);
		// 用户总积分
		Long scores = userScoreDao.totalScoreByUserId(user.getId());
		if (scores == null) {
			scores = 0L;
		}
		modelMap.addAttribute("scores", scores);
		// 连续签到次数
		int qiandaos = scoreService.getLianxuQiandaoDays(user);
		modelMap.addAttribute("qiandaos", qiandaos);
		// 今天是否签到
		modelMap.addAttribute("todayQian", scoreService.todayQiandao(user));
		return "/member/member_amountDetail";
	}

	/**
	 * 收货地址
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author lvzf 2016年10月4日
	 */
	@RequestMapping("/addr")
	public String addr(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		UserEntity user = userDao.findOne(RequestUtils.getCurrentUser(req).getId());
		modelMap.addAttribute("userInfo", user);
		List<AddressEntity> addressList = addressDao.findByUserId(user.getId());
		modelMap.addAttribute("addressList", addressList);
		return "/member/member_addr";
	}

	@RequestMapping("/addr/save")
	public String addr_save(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, AddressEntity addressSave) {
		UserEntity user = userDao.findOne(RequestUtils.getCurrentUser(req).getId());
		try {
			if (addressSave.getId() == null) {
				// 新增
				if (addressDao.countByUserId(user.getId()) > 4) {
					throw new BusinessException("最多保存5个有效地址");
				}
				// 检查字段
				this.checkVlaues(addressSave);
				addressSave.setUserId(user.getId());
				addressDao.save(addressSave);
			} else {
				// 检查字段
				this.checkVlaues(addressSave);
				// 修改
				AddressEntity tAddressEntity = addressDao.findOne(addressSave.getId());
				// 不存在
				if (tAddressEntity == null) {
					modelMap.addAttribute("error", "地址id不存在");
					throw new BusinessException("配送区域未选择");
				}
				// 不是自己的地址
				if (tAddressEntity.getUserId().longValue() != user.getId().longValue()) {
					modelMap.addAttribute("error", "地址id不存在");
					throw new BusinessException("配送区域未选择");
				}
				tAddressEntity.setTrueName(addressSave.getTrueName());

				tAddressEntity.setProvinceId(addressSave.getProvinceId());
				tAddressEntity.setProvinceName(addressSave.getProvinceName());
				tAddressEntity.setCityId(addressSave.getCityId());
				tAddressEntity.setCityName(addressSave.getCityName());
				tAddressEntity.setAreaName(addressSave.getAreaName());
				tAddressEntity.setAreaId(addressSave.getAreaId());

				tAddressEntity.setAreaInfo(addressSave.getAreaInfo());
				tAddressEntity.setZip(addressSave.getZip());
				tAddressEntity.setTelephone(addressSave.getTelephone());
				tAddressEntity.setMobile(addressSave.getMobile());

				tAddressEntity.setIsDefaultAddress(addressSave.getIsDefaultAddress());
				addressDao.save(tAddressEntity);
			}
			modelMap.addAttribute("msg", "保存成功");
		} catch (BusinessException exception) {
			modelMap.addAttribute("msg", exception.getMessage());
			return "/member/member_iframe_msg";
		} catch (Exception e) {
			modelMap.addAttribute("msg", "异常");
			return "/member/member_iframe_msg";
		}
		return "/member/member_addr_save_success";
	}

	@RequestMapping("/addr/{id}")
	public String address(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long id) {
		UserEntity user = userDao.findOne(RequestUtils.getCurrentUser(req).getId());
		// 编辑
		AddressEntity address = addressDao.findOne(id);
		// 不存在
		if (address == null) {
			return "redirect:/member/";
		}
		// 不是自己的地址
		if (address != null && address.getUserId().longValue() != user.getId().longValue()) {
			return "redirect:/member/";
		}
		modelMap.addAttribute("address", address);
		List<AddressEntity> addressList = addressDao.findByUserId(user.getId());
		modelMap.addAttribute("addressList", addressList);
		return "/member/member_addr";
	}

	@RequestMapping("/addr_del/{id}")
	public String address_del(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long id) {
		UserEntity user = userDao.findOne(RequestUtils.getCurrentUser(req).getId());
		// 删除
		AddressEntity address = addressDao.findOne(id);
		// 不存在
		if (address == null) {
			modelMap.addAttribute("msg", "地址id不存在");
			return "/member/member_iframe_msg";
		}
		// 不是自己的地址
		if (address != null && address.getUserId().longValue() != user.getId().longValue()) {
			modelMap.addAttribute("msg", "地址id不存在");
			return "/member/member_iframe_msg";
		}
		addressDao.delete(id);
		modelMap.addAttribute("msg", "删除成功");
		return "/member/member_addr_save_success";
	}

	/**
	 * 检查地址项
	 * 
	 * @param address
	 * @throws Exception
	 * @author linwk 2016年10月5日
	 */
	private void checkVlaues(AddressEntity address) throws Exception {
		if (address.getAreaId() == null) {
			throw new BusinessException("配送区域未选择");
		}
		if (StringUtils.isBlank(address.getAreaInfo()) == true) {
			throw new BusinessException("详细地址不能为空!");
		}
		if (StringUtils.isBlank(address.getTrueName()) == true) {
			throw new BusinessException("收货人不能为空!");
		}
		if (StringUtils.isBlank(address.getMobile()) == true) {
			throw new BusinessException("手机号码不能为空!");
		}
	}

	@RequestMapping("/baseinfo")
	public String baseinfo(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		UserEntity user = userDao.findOne(RequestUtils.getCurrentUser(req).getId());
		modelMap.addAttribute("userInfo", user);
		return "/member/member_baseinfo";
	}

	/**
	 * 保存用户
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param user
	 * @return
	 * @author lvzf
	 * @date 2016年5月24日 下午3:56:12
	 */
	@RequestMapping("/save")
	public void save(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, UserEntity user) {
		try {
			UserEntity currentUser = RequestUtils.getCurrentUser(req);
			UserEntity userEntity = userDao.findOne(currentUser.getId());
			if (userEntity == null) {
				throw new BusinessException("当前用户不存在");
			}
			UserEntity user1 = userDao.findUserByMobile(user.getMobile());
			if (user1 != null && user1.getId().longValue() != userEntity.getId().longValue()) {
				throw new BusinessException("手机号已存在");
			}
			if (StringUtils.isNoneBlank(user.getEmail())) {
				user1 = userDao.findUserByEmail(user.getEmail());
				if (user1 != null && user1.getId().longValue() != userEntity.getId().longValue()) {
					throw new BusinessException("邮箱已存在");
				}
			} else {
				user.setEmail(null);
			}

			userEntity.setEmail(user.getEmail());
			userEntity.setMobile(user.getMobile());
			userEntity.setTrueName(user.getTrueName());
			userEntity.setNickName(user.getNickName());
			userEntity.setCardNo(user.getCardNo());
			userEntity.setLastModifyBy(user.getUserName());
			userDao.save(userEntity);
			RequestUtils.setCurrentUser(req, userEntity);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, "保存成功"));
		} catch (BusinessException ex) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, ex.getMessage()));
		} catch (Exception ex) {
			logger.error("保存用户错误：", ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "保存错误"));
		}
	}
	@RequestMapping("/changePwd")
	public String changePwd(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) { 
		return "/member/member_change_pwd";
	}
	@RequestMapping("/changePwdSave")
	public void changePwdSave(ModelAndView model, HttpServletRequest req, HttpServletResponse res, String oldPwd, String pass1, String pass2) {
		try {
			UserEntity user = RequestUtils.getCurrentUser(req);
			if (StringUtils.isBlank(user.getPassword())
					|| StringUtils.isBlank(oldPwd)) {
				throw new BusinessException("原密码不能为空");
			}
			if (StringUtils.endsWith(pass1, oldPwd)) {
				throw new BusinessException("原密码与新密码不能一致");
			}
			if (StringUtils.endsWith(user.getPassword(),
					SecurityUtils.SHA256(oldPwd)) == false) {
				throw new BusinessException("原密码错误");
			}
			if (StringUtils.isBlank(pass1)) {
				throw new BusinessException("新密码不能为空");
			}
			if (StringUtils.isBlank(pass2)) {
				throw new BusinessException("确认密码不能为空");
			}
			if (StringUtils.endsWith(pass1, pass2) == false) {
				throw new BusinessException("新密码与确认密码不一致");
			}
			if (ValidUtils.IsPassword(pass1) == false) {
				throw new BusinessException("新密码必须是大于等于6个字符小于32个字符");
			}
			// 验证通过
			// 保存密码
			UserEntity userTEntity = userDao.findOne(user.getId());
			userTEntity.setPassword(SecurityUtils.SHA256(pass1));
			userDao.save(userTEntity);
			RequestUtils.setCurrentUser(req, userTEntity);
			ResponseOutputUtils.renderJson(res,
					MessageDTO.newInstance("", true, "保存成功"));
		} catch (BusinessException ex) {
			ResponseOutputUtils.renderJson(res,
					MessageDTO.newInstance("", false, ex.getMessage()));
		} catch (Exception ex) {
			logger.error("保存用户错误：", ex);
			ResponseOutputUtils.renderJson(res,
					MessageDTO.newInstance("", false, "保存错误"));
		}
	}

	/**
	 * 修改头像
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param headPhoto
	 * @return
	 * @author lvzf 2016年10月20日
	 */
	@RequestMapping("/uploadHead")
	public String uploadHead(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, MultipartFile headPhoto) {
		try {
			UserEntity currentUser = RequestUtils.getCurrentUser(req);
			UserEntity userEntity = userDao.findOne(currentUser.getId());
			// 上传头像
			if (headPhoto != null) {
				String storageFilePath = "user/space/" + userEntity.getId() + "/" + DateUtil.getNowDateToString("yyyy") + "/" + DateUtil.getNowDateToString("MM") + "/"
						+ DateUtil.getNowDateToString("dd");
				FileUploadVO fileUpload = new FileUploadVO();
				fileUpload.setFile(headPhoto);
				fileUpload.setStorageFilePath(storageFilePath);
				fileUpload.setStorageFileName(UUID.randomUUID().toString());
				fileUpload.setUserId(userEntity.getId());
				if (userEntity.getHeadPhotoPath() != null) {
					// 从硬盘删除原文件
					UploadFileUtil.deleteFileByPath(userEntity.getHeadPhotoPath());
				}
				// 上传图片到硬盘
				AccessoryEntity acc = UploadFileUtil.uploadFile(fileUpload);
				userEntity.setHeadPhotoPath(domain + "/" + acc.getPath() + "/" + acc.getName());
				userEntity.setLastModifyBy(userEntity.getUserName());
				userDao.save(userEntity);
			}
			RequestUtils.setCurrentUser(req, userEntity);
		} catch (Exception ex) {
			logger.error("修改头像错误：", ex);
		}
		return "redirect:/member/baseinfo.html";
	}

	/**
	 * 签到
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author lvzf 2016年10月14日
	 * @throws BusinessException 
	 */
	@RequestMapping("/qiandao")
	public String qiaodao(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) throws BusinessException {
		UserEntity user = userDao.findOne(RequestUtils.getCurrentUser(req).getId());
		boolean re = scoreService.addScore4qiandao(user, "每日签到");
		String msg = "";
		if (re) {
			msg = "签到成功";
		} else {
			msg = "今日已签到";
		}
		modelMap.addAttribute("msg", msg);
		return this.index(modelMap, req, res);
	}
}
