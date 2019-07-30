package cn.com.yyg.front.web.member.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sun.misc.BASE64Decoder;
import cn.com.easy.dto.MessageDTO;
import cn.com.easy.exception.BusinessException;
import cn.com.easy.utils.FastJSONUtils;
import cn.com.easy.utils.PageRequest;
import cn.com.easy.utils.PageRequestUtils;
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.easy.utils.SecurityUtils;
import cn.com.easy.utils.ValidUtils;
import cn.com.yyg.base.em.YgProductOrderStatusEnum;
import cn.com.yyg.base.entity.AccessoryEntity;
import cn.com.yyg.base.entity.AddressEntity;
import cn.com.yyg.base.entity.SysConfigEntity;
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
import cn.com.yyg.front.dao.AccessoryDao;
import cn.com.yyg.front.dao.AddressDao;
import cn.com.yyg.front.dao.UserAccountDetailDao;
import cn.com.yyg.front.dao.UserDao;
import cn.com.yyg.front.dao.UserScoreDao;
import cn.com.yyg.front.dao.UserYgDao;
import cn.com.yyg.front.dao.YgProductDao;
import cn.com.yyg.front.dao.YgProductOrderDao;
import cn.com.yyg.front.dao.YgShareDao;
import cn.com.yyg.front.dto.ContentDto;
import cn.com.yyg.front.dto.MessageDto;
import cn.com.yyg.front.dto.ScoreConfigDto;
import cn.com.yyg.front.service.ScoreService;
import cn.com.yyg.front.service.SysConfigService;
import cn.com.yyg.front.service.common.CommonService;
import cn.com.yyg.front.utils.RequestUtils;

import com.google.common.collect.Lists;

/**
 * 个人信息页面控制器
 * 
 * @author linwk 2016年9月12日
 * 
 */
@Controller
@RequestMapping("/wap/member")
public class WapMemberController {

	private Logger logger = LoggerFactory.getLogger(WapMemberController.class);

	/** 前端公共服务 */
	@Autowired
	private CommonService frontCommonService;

	/** 用户晒单 */
	@Autowired
	private YgShareDao ygShareDao;

	/** 云购 */
	@Autowired
	private YgProductDao ygProductDao;

	/** 用户云购 */
	@Autowired
	private UserYgDao userYgDao;

	/** 用户dao */
	@Autowired
	private UserDao userDao;

	/** 收货地址dao */
	@Autowired
	private AddressDao addressDao;

	/** 文件服务类 */
	@Autowired
	private AccessoryDao accessoryDao;

	/** 用户云购订单 */
	@Autowired
	private YgProductOrderDao ygProductOrderDao;

	/** 用户积分 */
	@Autowired
	private UserScoreDao userScoreDao;

	@Autowired
	private SysConfigService sysConfigService;

	@Autowired
	private ScoreService scoreService;
	
	@Autowired
	private UserAccountDetailDao userAccountDetailDao;
	
	@Value("#{configProperties['www.domain']}")
	private String domain;

	/**
	 * 个人信息首页
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author linwk 2016年9月12日
	 */
	@RequestMapping()
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		// 用户信息
		UserEntity user=userDao.findOne(RequestUtils.getCurrentUser(req).getId());
		modelMap.addAttribute("user", user);
		// 用户总积分
		if (user!=null&&user.getId()!=null) {
			Long scores = userScoreDao.totalScoreByUserId(user.getId());
			if (scores == null) {
				scores = 0L;
			}
			modelMap.addAttribute("scores", scores);
		}
		
		SysConfigEntity config = sysConfigService.getById(SysConfigEntity.ID_SCORE);
		if (config != null) {
			ScoreConfigDto scoreRule = FastJSONUtils.toObject(config.getData(), ScoreConfigDto.class);
			modelMap.addAttribute("scoreRule", scoreRule);
		}
		return "/wap_member/member";
	}

	@RequestMapping("/address")
	public String address(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		getAddressList(modelMap, req, res);
		return "/wap_member/address";
	}

	/**
	 * 获取用户地址列表
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @author linwk 2016年10月19日
	 */
	private void getAddressList(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		UserEntity user = RequestUtils.getCurrentUser(req);
		Long userId = user.getId();
		if (userId == null) {
			return;
		}
		List<AddressEntity> addressList = addressDao.findByUserId(userId);
		modelMap.addAttribute("addressList", addressList);
	}

	/**
	 * 编辑地址
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @return
	 * @author linwk 2016年10月19日
	 */
	@RequestMapping("/address/{id}")
	public String address(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long id) {
		UserEntity user = RequestUtils.getCurrentUser(req);
		// 编辑
		AddressEntity address = addressDao.findOne(id);
		// 不存在
		if (address == null) {
			modelMap.addAttribute("error", "地址id不存在");
			return "/wap_member/address_save_error";
		}
		// 不是自己的地址
		if (address != null && address.getUserId().longValue() != user.getId().longValue()) {
			modelMap.addAttribute("error", "地址id不存在");
			return "/wap_member/address_save_error";
		}
		modelMap.addAttribute("address", address);
		// 获取地址列表
		getAddressList(modelMap, req, res);
		return "/wap_member/address_edit";
	}

	/**
	 * 删除地址
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @return
	 * @author linwk 2016年10月19日
	 */
	@RequestMapping("/address_del/{id}")
	public String address_del(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long id) {
		UserEntity user = RequestUtils.getCurrentUser(req);
		// 删除
		AddressEntity address = addressDao.findOne(id);
		// 不存在
		if (address == null) {
			modelMap.addAttribute("error", "地址id不存在");
			return "/wap_member/address_save_error";
		}
		// 不是自己的地址
		if (address != null && address.getUserId().longValue() != user.getId().longValue()) {
			modelMap.addAttribute("error", "地址id不存在");
			return "/wap_member/address_save_error";
		}
		addressDao.delete(id);
		return "/wap_member/address_del_success";
	}

	/**
	 * 保存地址
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param addressSave
	 * @return
	 * @author linwk 2016年10月19日
	 */
	@RequestMapping("/address/save")
	public String addressSave(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, AddressEntity addressSave) {
		UserEntity user = RequestUtils.getCurrentUser(req);
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
				if (StringUtils.isNoneBlank(addressSave.getProvinceName())) {
					tAddressEntity.setProvinceName(addressSave.getProvinceName());
				}

				tAddressEntity.setCityId(addressSave.getCityId());
				if (StringUtils.isNoneBlank(addressSave.getCityName())) {
					tAddressEntity.setCityName(addressSave.getCityName());
				}

				tAddressEntity.setAreaId(addressSave.getAreaId());
				if (StringUtils.isNoneBlank(addressSave.getAreaName())) {
					tAddressEntity.setAreaName(addressSave.getAreaName());
				}

				tAddressEntity.setAreaInfo(addressSave.getAreaInfo());
				tAddressEntity.setZip(addressSave.getZip());
				tAddressEntity.setTelephone(addressSave.getTelephone());
				tAddressEntity.setMobile(addressSave.getMobile());

				tAddressEntity.setIsDefaultAddress(addressSave.getIsDefaultAddress());
				addressDao.save(tAddressEntity);
			}
		} catch (BusinessException exception) {
			modelMap.addAttribute("error", exception.getMessage());
			return "/wap_member/address_save_error";
		} catch (Exception e) {
			modelMap.addAttribute("error", "异常");
			return "/wap_member/address_save_error";
		}
		return "/wap_member/address_save_success";
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
			throw new BusinessException("收货人姓名不能为空!");
		}
		if (StringUtils.isBlank(address.getMobile()) == true) {
			throw new BusinessException("手机号码不能为空!");
		}
	}

	/**
	 * 购买记录 云购记录
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author linwk 2016年10月19日
	 */
	@RequestMapping("/buyRecord")
	public String buyRecord(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		// 删除保存的分页页码信息
		return "/wap_member/buyRecord";
	}

	/**
	 * 云购记录分页
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param pageNo
	 * @author linwk 2016年10月19日
	 */
	@RequestMapping("/buyRecordAjax/{pageNo}")
	public void buyRecordAjax(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Integer pageNo) {
		UserEntity user = RequestUtils.getCurrentUser(req);
		PageRequest pageRequest = new PageRequest(pageNo, 8);
		pageRequest.setOrderBy("createTime");
		pageRequest.setOrderDir("desc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		Page<UserYgEntity> springPage = userYgDao.findByBuyUserId(user.getId(), pb);
		cn.com.easy.utils.Page<UserYgEntity> page = PageUtils.getPage(springPage);
		for (UserYgEntity s : page.getResult()) {
			YgProductEntity ygProduct = ygProductDao.findOne(s.getYgProductId());
			s.setYgProduct(ygProduct);
		}
		List<ContentDto> contents = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(page.getResult())) {
			for (UserYgEntity item : page.getResult()) {
				String html = "";
				html += "<dt class=\"ui-clr\">";
				html += "<div class=\"db-img\"><a href=\"/product/" + item.getYgProductId() + ".html\" ><img src=\"" + item.getYgProduct().getLogoPath() + "\" alt=\""
						+ item.getYgProduct().getName() + "\" /></a></div>";
				html += "<div class=\"db-txt\">";
				html += "<p class=\"p1\"> <a href=\"/product/" + item.getYgProductId() + ".html\"  class=\"color00\"><span class=\"color01\">(第" + item.getYgProduct().getPeriod()
						+ "期)</span> " + item.getYgProduct().getName() + "</a></p>";
				if (item.getYgProduct().getStatus() != 3) {
					html += "<p class=\"color03\">总需：" + item.getYgProduct().getTotalNum() + "人次</p>";
					html += "<p class=\"db-jdt\">";
					html += "<span style=\"width:" + item.getYgProduct().getUsedNum() * 100 / item.getYgProduct().getTotalNum() + "%\"></span>";
					html += "</p>";
					html += "<ul class=\"db-nums ui-clr color03\">";
					html += "<li class=\"tr\">剩余<span>" + item.getYgProduct().getLeaveNum() + "</span></li>";
					html += "<li class=\"tl\">已参与<span>" + item.getYgProduct().getUsedNum() + "</span></li>";
					html += "</ul>";
					html += "<p class=\"color03\">购买时间：" + item.getCreateTime() + "</p>";
					html += "</div>";
					html += "</dt>";
					html += "<dd>";
					html += "<table cellpadding=\"0\" cellspacing=\"0\">";
					html += "<tr>";
					html += "<th>云购状态</th>";
					html += "<td>";
					html += "<span class=\"color02\">正进行.....</span>";
					html += "</td>";
					html += "</tr>";
				} else {
					html += "<p class=\"color03\">";
					html += "获得者：<a href=\"/user/" + user.getId() + "\" >" + item.getYgProduct().getWinUserNickName() + "</a>（本期共参与" + item.getYgProduct().getWinUserBuyNum()
							+ "人次）<br/>";
					html += "幸运号码：<b class=\"color01\" style=\"font-size: 1.4rem\">" + item.getYgProduct().getWinNo() + "</b><br/>";
					html += "揭晓时间：" + item.getYgProduct().getPublishDate();
					html += "</p>";
					html += "</div>";
					html += "</dt>";
					html += "<dd>";
					html += "<table cellpadding=\"0\" cellspacing=\"0\">";
					html += "<tr>";
					html += "<th>云购状态</th>";
					html += "<td>";
					html += "<span class=\"color02\">已揭晓</span>";
					html += "</td>";
					html += "</tr>";
				}
				html += "<tr>";
				html += "<th>参与人次</th>";
				html += "<td>";
				html += "" + item.getBuyNum() + "人次             </td>";
				html += "</tr>";
				html += "<tr>";
				html += "<th>云购号码</th>";
				html += "<td>";
				int countBuyNos = 1;
				for (String buyNo : StringUtils.split(item.getBuyNos(), ",")) {
					if (countBuyNos <= 9) {
						html += buyNo + "&nbsp;&nbsp";
					}else {
						html +="<span class=\"moreCode\" style=\"display: none;\">"+buyNo+"&nbsp;&nbsp;</span>";
					}
					if (countBuyNos % 3 == 0) {
						html += "\n";
					}
					countBuyNos++;
				}
				if (countBuyNos > 10) {
					html += "<a href=\"javascript:;\" class=\"color02\" onclick=\"moreCode(this)\">查看更多&gt;&gt;</a>";
				}
				html += "</td>";
				html += "</tr>";
				html += "</table>";
				html += "</dd>";

				ContentDto contentDto = new ContentDto();
				contentDto.setContent(html);
				contents.add(contentDto);
			}
		}
		ResponseOutputUtils.renderJson(res, FastJSONUtils.toJsonString(contents));
	}

	@RequestMapping("/luck")
	public String luck(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		// 删除保存的分页页码信息
		return "/wap_member/luck";
	}

	/**
	 * 幸运中奖
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param pageNo
	 * @author linwk 2016年10月12日
	 */
	@RequestMapping("/luckAjax/{pageNo}")
	public void luckAjax(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Integer pageNo, Integer amount) {
		UserEntity user = RequestUtils.getCurrentUser(req);
		PageRequest pageRequest = new PageRequest(pageNo, amount);
		pageRequest.setOrderBy("createTime");
		pageRequest.setOrderDir("desc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		Page<UserYgEntity> springPage = userYgDao.findByBuyUserIdAndWin(user.getId(), true, pb);
		cn.com.easy.utils.Page<UserYgEntity> page = PageUtils.getPage(springPage);
		for (UserYgEntity s : page.getResult()) {
			YgProductEntity ygProduct = ygProductDao.findOne(s.getYgProductId());
			s.setYgProduct(ygProduct);
		}
		List<ContentDto> contents = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(page.getResult())) {
			for (UserYgEntity item : page.getResult()) {
				String html = "";
				html += "<dt class=\"ui-clr\">";
				html += "<a href=\"/product/" + item.getYgProductId() + ".html\" target=\"_blank\" class=\"color00\"><span class=\"color01\">(第" + item.getYgProduct().getPeriod()
						+ "期)</span> " + item.getYgProduct().getName() + "</a>";
				html += "</dt>";
				html += "<dd>";
				html += "<div class=\"db-img\"><a href=\"/product/" + item.getYgProductId() + ".html\" target=\"_blank\"><img src=\"" + item.getYgProduct().getLogoPath()
						+ "\" alt=\" " + item.getYgProduct().getName() + "\" /></a></div>";
				html += "<div class=\"db-txt color03\">";
				html += "<p>总需：" + item.getYgProduct().getTotalNum() + "人次</p>";
				html += "<p>幸运号码：<strong class=\"color01\">" + item.getYgProduct().getWinNo() + "</strong></p>";
				html += "<p>本次参与：" + item.getYgProduct().getWinUserBuyNum() + "人次</p>";
				html += "<p>揭晓时间：" + item.getYgProduct().getPublishDate() + "</p>";
				if (item.getStatus() == 0) {
					// 领奖
					html += "<div>";
					html += "<form action=\"/member/order/buy/" + item.getId() + "" + "" + "\" method=\"post\" style=\"display: inline-block\">";
					html += "<input class=\"btn-small\" style=\"margin-top:5px;\" type=\"submit\" value=\"领奖\">";
					html += "<input type=\"hidden\" name=\"id\" value=\"" + item.getId() + "\">";
					html += "<input type=\"hidden\" name=\"type\" value=\"3\">";
					html += "</form>";
					html += "</div>";
				}

				html += "</div>";
				html += "</dd>";

				ContentDto contentDto = new ContentDto();
				contentDto.setContent(html);
				contents.add(contentDto);
			}
		}
		ResponseOutputUtils.renderJson(res, FastJSONUtils.toJsonString(contents));
	}

	/**
	 * 个人信息
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author linwk 2016年10月19日
	 */
	@RequestMapping("/info")
	public String info(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		// 用户信息
		UserEntity user = RequestUtils.getCurrentUser(req);
		modelMap.addAttribute("user", user);
		return "/wap_member/info";
	}

	/**
	 * 个人信息保存
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param userTEntity
	 * @author linwk 2016年10月19日
	 */
	@RequestMapping("/infoSave")
	public void infoSave(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, UserEntity userTEntity) {
		try {
			// 用户信息
			UserEntity user = RequestUtils.getCurrentUser(req);
			UserEntity userSaveEntity = userDao.findOne(user.getId());
			if (userSaveEntity == null) {
				throw new BusinessException("当前用户不存在");
			}

			UserEntity user1 = null;
			if (StringUtils.isNoneBlank(userTEntity.getMobile())) {
				user1 = userDao.findUserByMobile(userTEntity.getMobile());
				if (user1 != null && user1.getId().longValue() != userSaveEntity.getId().longValue()) {
					throw new BusinessException("手机号已存在");
				}
			}

			if (StringUtils.isNoneBlank(userTEntity.getEmail())) {
				user1 = userDao.findUserByEmail(userTEntity.getEmail());
				if (user1 != null && user1.getId().longValue() != userSaveEntity.getId().longValue()) {
					throw new BusinessException("邮箱已存在");
				}
			}else {
				userTEntity.setEmail(null);
			}

			// 保存的时候不保存其他信息 安全
			userSaveEntity.setNickName(userTEntity.getNickName());
			userSaveEntity.setRemark(userTEntity.getRemark());
			userSaveEntity.setEmail(userTEntity.getEmail());
			userSaveEntity.setTrueName(userTEntity.getTrueName());
			userSaveEntity.setMobile(userTEntity.getMobile());
			userSaveEntity.setCardNo(userTEntity.getCardNo());
			userSaveEntity.setLastModifyBy(user.getUserName());

			userDao.save(userSaveEntity);
			modelMap.addAttribute("user", userSaveEntity);
			// 保存用户信息
			RequestUtils.setCurrentUser(req, userSaveEntity);
			ResponseOutputUtils.renderHtml(res,
					"<script type=\"text/javascript\">parent.layer.alert(\"保存成功\",1,function(){parent.location.replace('javascript:history.go(0)')});</script>");
		} catch (BusinessException e) {
			logger.error(e.getMessage());
			ResponseOutputUtils.renderHtml(res, "<script type=\"text/javascript\">parent.layer.alert(\"" + e.getMessage() + "\",0);</script>");
		} catch (Exception e) {
			logger.error(e.getMessage());
			ResponseOutputUtils.renderHtml(res, "<script type=\"text/javascript\">parent.layer.alert(\"保存失败\",0);</script>");
		}
	}

	/**
	 * 修改密码
	 * 
	 * @param model
	 * @param req
	 * @param res
	 * @param oldpass
	 * @param pass1
	 * @param pass2
	 * @author linwk 2016年10月19日
	 */
	@RequestMapping("/chpass")
	public void chpass(ModelAndView model, HttpServletRequest req, HttpServletResponse res, String oldpass, String pass1, String pass2) {
		UserEntity user = RequestUtils.getCurrentUser(req);
		if (StringUtils.isBlank(user.getPassword()) || StringUtils.isBlank(oldpass)) {
			ResponseOutputUtils.renderHtml(res, scriptErrorMessage("原密码不能为空"));
			return;
		}
		if (StringUtils.endsWith(pass1, oldpass)) {
			ResponseOutputUtils.renderHtml(res, scriptErrorMessage("原密码,新密码不能一致"));
			return;
		}
		if (StringUtils.endsWith(user.getPassword(), SecurityUtils.SHA256(oldpass)) == false) {
			ResponseOutputUtils.renderHtml(res, scriptErrorMessage("原密码错误"));
			return;
		}
		if (StringUtils.isBlank(pass1)) {
			ResponseOutputUtils.renderHtml(res, scriptErrorMessage("新密码不能为空"));
			return;
		}
		if (StringUtils.isBlank(pass2)) {
			ResponseOutputUtils.renderHtml(res, scriptErrorMessage("确认密码不能为空"));
			return;
		}
		if (StringUtils.endsWith(pass1, pass2) == false) {
			ResponseOutputUtils.renderHtml(res, scriptErrorMessage("新密码,确认密码不一致"));
			return;
		}
		if (ValidUtils.IsPassword(pass1) == false) {
			ResponseOutputUtils.renderHtml(res, scriptErrorMessage("新密码必须是大于等于6个字符小于32个字符"));
			return;
		}
		// 验证通过
		// 保存密码
		UserEntity userTEntity = userDao.findOne(user.getId());
		userTEntity.setPassword(SecurityUtils.SHA256(pass1));
		userDao.save(userTEntity);
		// 退出操作
		req.getSession().invalidate();
		ResponseOutputUtils.renderHtml(res, "<script type=\"text/javascript\">parent.layer.alert(\"重置成功\",1,function(){parent.location.href='/login'});</script>");
	}

	private String scriptErrorMessage(String message) {
		return "<script type=\"text/javascript\">parent.layer.alert(\"" + message + "\",0);" + "" + "</script>";
	}

	/**
	 * 我的邀请
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author linwk 2016年10月19日
	 */
	@RequestMapping("/myivt")
	public String myivt(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		// 用户信息
		UserEntity user = RequestUtils.getCurrentUser(req);
		modelMap.addAttribute("user", user);
		// 我邀请的人员
		modelMap.addAttribute("yaoqingUsers", userDao.findByYaoqingUserId(user.getId()));
		SysConfigEntity config = sysConfigService.getById(SysConfigEntity.ID_SCORE);
		if (config != null) {
			ScoreConfigDto scoreRule = FastJSONUtils.toObject(config.getData(), ScoreConfigDto.class);
			modelMap.addAttribute("scoreRule", scoreRule);
		}
		return "/wap_member/myivt";
	}

	/**
	 * 分享完到我的完成订单页面
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author linwk 2016年10月19日
	 */
	@RequestMapping("/share")
	public String share(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		// // 用户信息
		// UserEntity user = RequestUtils.getCurrentUser(req);
		// modelMap.addAttribute("user", user);
		// return "/wap_member/order/";
		// 删除保存的分页页码信息
		// 未发货数量
		// 已发货数量
		// 已完成数量
		modelMap.addAttribute("payed", ygProductOrderDao.countByStatus(YgProductOrderStatusEnum.PAYED.getValue()));
		modelMap.addAttribute("sended", ygProductOrderDao.countByStatus(YgProductOrderStatusEnum.SENDED.getValue()));
		modelMap.addAttribute("accepted", ygProductOrderDao.countByStatus(YgProductOrderStatusEnum.ACCEPTED.getValue()));
		modelMap.addAttribute("status", 3);
		return "/wap_member/order/order_list";
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
	 * @throws IOException
	 */
	@RequestMapping("/share/save")
	public String shareSave(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, Long orderId, YgShareEntity share, String shares[]) throws IOException {
		if (orderId == null) {
			modelMap.addAttribute("error", "订单Id为空");
			return "/wap_member/error_message";
		}
		UserEntity user = RequestUtils.getCurrentUser(req);
		YgProductOrderEntity order = ygProductOrderDao.findOne(orderId);
		
		if (order == null || !order.getUserId().equals(user.getId())) {
			modelMap.addAttribute("error", "订单不是当前用户的");
			return "/wap_member/error_message";
		}
		
		if (StringUtils.isBlank(share.getTitle())) {
			modelMap.addAttribute("error", "请输入晒单标题");
			return "/wap_member/error_message";
		}
		
		if (StringUtils.isBlank(share.getContent())) {
			modelMap.addAttribute("error", "请输入晒单内容");
			return "/wap_member/error_message";
		}
		
		if (shares==null||CollectionUtils.sizeIsEmpty(shares)) {
			modelMap.addAttribute("error", "无图无真相,上传点晒单照片吧");
			return "/wap_member/error_message";
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
		share.setPhotos(FastJSONUtils.toJsonString(shares));
		ygShareDao.save(share);
		order.setShareId(share.getId());
		order.setShareTime(new Date());
		ygProductOrderDao.save(order);
		modelMap.addAttribute("order", order);
		// 晒单成功增加积分
		//scoreService.addScore(user, UserScoreTypeEnum.Share, "晒单分享");
		ResponseOutputUtils.renderHtml(res, "<script type=\"text/javascript\">parent.layer.alert(\"晒单成功，等待系统审核并奖励！\",1,function(){parent.location.replace('/member/share')});</script>");
		return "";
	}

	/**
	 * 晒单页面
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param orderId
	 * @return
	 * @throws IOException
	 * @author linwk 2016年10月19日
	 */
	@RequestMapping("/post_share/{orderId}")
	public String post_share(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long orderId) throws IOException {
		// 用户信息
		UserEntity user = RequestUtils.getCurrentUser(req);
		YgProductOrderEntity order = ygProductOrderDao.findOne(orderId);
		if (order == null || !order.getUserId().equals(user.getId())) {
			res.sendRedirect("redirect:/member/");
			return null;
		}
		YgProductEntity ygProduct = order.getYgProduct();
		modelMap.addAttribute("user", user);
		modelMap.addAttribute("ygProduct", ygProduct);
		modelMap.addAttribute("orderId", orderId);
		return "/wap_member/post_share";
	}

	/**
	 * 上传头像
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param photo
	 * @return
	 * @author linwk 2016年10月19日
	 */
	@RequestMapping("/photo")
	public String photo(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, String photo) {
		UserEntity user = RequestUtils.getCurrentUser(req);
		if (StringUtils.isNoneBlank(photo)) {
			UserEntity sEntity = userDao.findOne(user.getId());
			sEntity.setHeadPhotoPath(photo);
			userDao.save(sEntity);
			RequestUtils.setCurrentUser(req, sEntity);
			// 用户信息
			modelMap.addAttribute("user", sEntity);
			ResponseOutputUtils.renderHtml(res, "<script type=\"text/javascript\">parent.layer.alert(\"头像上传成功\",1,function(){parent.location.href='/member'});</script>");
			return "";
		} else {
			// 用户信息
			modelMap.addAttribute("user", user);
		}
		return "/wap_member/photo";
	}

	/**
	 * 上传头像 base64方式(备选)
	 * 
	 * @param file
	 * @param req
	 * @param res
	 * @param indexImgSize
	 * @throws Exception
	 * @author linwk 2016年1月15日
	 */
	@RequestMapping(value = "/uploadUserImg.htm", method = RequestMethod.POST)
	public void uploadFront(HttpServletRequest req, HttpServletResponse res, Integer indexImgSize) throws Exception {
		String base64Code = req.getParameter("base64Code");
		try {
			if (StringUtils.isBlank(base64Code)) {
				throw new BusinessException("图片不能为空!");
			}

			// 商品图片存放路径
			String storageFileName;
			String base64Img = null;
			// 获取图片后缀
			String imgSuffix = "jpg";
			String pattern = "data:image/(.*?);base64";
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(base64Code);
			ArrayList<String> strs = Lists.newArrayList();
			if (m.find()) {
				strs.add(m.group(1));
			}
			for (String s : strs) {
				imgSuffix = s;
			}
			if (base64Code.indexOf("base64,") > 0) {
				String[] base64LiStrings = base64Code.split("base64,");
				// 获取图片内容
				base64Img = base64LiStrings[1];
			} else {
				base64Img = base64Code;
			}

			UserEntity user = RequestUtils.getCurrentUser(req);
			String storageFilePath = "user/space/" + user.getId() + "/" + DateUtil.getNowDateToString("yyyy") + "/" + DateUtil.getNowDateToString("MM") + "/"
					+ DateUtil.getNowDateToString("dd");
			storageFileName = UUID.randomUUID().toString();

			// 更新个人头像(只允许传一张)
			final String finalStorageFileName = storageFileName + "." + imgSuffix;
			FileUploadVO fileUpload = new FileUploadVO();
			BASE64Decoder decoder = new BASE64Decoder();
			final byte[] decoderBytes = decoder.decodeBuffer(base64Img);
			MultipartFile file = new MultipartFile() {
				@Override
				public void transferTo(File dest) throws IOException, IllegalStateException {

				}

				@Override
				public boolean isEmpty() {
					return false;
				}

				@Override
				public long getSize() {
					return 0;
				}

				@Override
				public String getOriginalFilename() {
					return finalStorageFileName;
				}

				@Override
				public String getName() {
					return "";
				}

				@Override
				public InputStream getInputStream() throws IOException {
					return null;
				}

				@Override
				public String getContentType() {
					return null;
				}

				@Override
				public byte[] getBytes() throws IOException {
					return decoderBytes;
				}
			};
			fileUpload.setFile(file);
			fileUpload.setStorageFilePath(storageFilePath);
			fileUpload.setStorageFileName(storageFileName);
			fileUpload.setUserId(user.getId());
			// 上传图片到硬盘
			AccessoryEntity acc = UploadFileUtil.uploadFile(fileUpload);
			// 上传图片到数据库
			// accessoryDao.save(acc);
			// 更新用户
			UserEntity sEntity = userDao.findOne(user.getId());
			sEntity.setHeadPhotoPath(domain + "/" + acc.getPath() + "/" + acc.getName());
			userDao.save(sEntity);
			RequestUtils.setCurrentUser(req, sEntity);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, null));
		} catch (BusinessException e) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance(e.getMessage(), false, null));
		} catch (Exception e) {
			logger.error("上传头像错误：", e);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance(e.getMessage(), false, null));
		}
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
	public String jifen(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, Integer pageNo, Integer amount) {
		UserEntity user = RequestUtils.getCurrentUser(req);
		if (pageNo == null) {
			pageNo = 1;
		}
		PageRequest pageRequest = new PageRequest(pageNo, amount);
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
		return "/member/member_jifen";
	}

	/**
	 * 异步签到
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @author lvzf 2016年10月16日
	 * @throws BusinessException 
	 */
	@RequestMapping("/ajax_signin")
	public void ajax_signin(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) throws BusinessException {
		UserEntity user = RequestUtils.getCurrentUser(req);
		MessageDto messageDto = new MessageDto();

		//ScoreConfigDto scoreRule = sysConfigService.getScoreRule();
		//int score = scoreRule.getQianDaoScore();
		messageDto.setError("0");
		boolean re = scoreService.addScore4qiandao(user, "每日签到");
		if (re) {
			messageDto.setMsg("签到成功");
		} else {
			messageDto.setMsg("您今天已签到过了！<br>");
		}

		ResponseOutputUtils.renderJson(res, FastJSONUtils.toJsonString(messageDto));
	}

	/**
	 * 签到
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author lvzf 2016年10月14日
	 * @throws IOException 
	 */
	@RequestMapping("/qiandao")
	public String qiaodao(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res,final RedirectAttributes redirectAttributes) throws IOException {
		UserEntity user = RequestUtils.getCurrentUser(req);
		/*boolean re = scoreService.addScore4qiandao(user, "每日签到");
		String msg = "";
		if (re) {
			ScoreConfigDto scoreRule = sysConfigService.getScoreRule();
			int score = scoreRule.getQianDaoScore();
			msg = "签到成功，获得 " + score + " 积分！";
		} else {
			msg = "您今天已签到过了！";
		}
		redirectAttributes.addFlashAttribute("msg", msg);*/
		//return this.index(modelMap, req, res);
		res.sendRedirect("/member/?qiandao=1");
		return null;
	}

	@RequestMapping("/scores")
	public String scores(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		UserEntity user = RequestUtils.getCurrentUser(req);
		// 删除保存的分页页码信息
		// 用户总积分
		Long scores = userScoreDao.totalScoreByUserId(user.getId());
		if (scores == null) {
			scores = 0L;
		}
		modelMap.addAttribute("scores", scores);
		return "/wap_member/scores";
	}

	/**
	 * 积分记录
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param pageNo
	 * @author linwk 2016年10月12日
	 */
	@RequestMapping("/scoresAjax/{pageNo}")
	public void scoresAjax(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Integer pageNo, Integer amount) {
		UserEntity user = RequestUtils.getCurrentUser(req);
		PageRequest pageRequest = new PageRequest(pageNo, amount);
		pageRequest.setOrderBy("createTime");
		pageRequest.setOrderDir("desc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		Page<UserScoreEntity> springPage = userScoreDao.findByUserId(user.getId(), pb);
		cn.com.easy.utils.Page<UserScoreEntity> page = PageUtils.getPage(springPage);
		List<ContentDto> contents = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(page.getResult())) {
			for (UserScoreEntity item : page.getResult()) {
				String html = "";
				html += "<dt><b>" + item.getCreateTime() + "</b></dt>";
				html += "<dd>";
				html += "    <table cellpadding=\"0\" cellspacing=\"0\">";
				html += "        <tr>";
				html += "            <th>积分来源</th>";
				html += "            <td>";
				html += "                " + item.getScoreTypeName() + "";
				html += "            </td>";
				html += "        </tr>";
				html += "                                                        <tr>";
				html += "            <th>积分</th>";
				html += "            <td>";
				html += "" + item.getScore() + "";
				html += "            </td>";
				html += "        </tr>";
				html += "                <tr>";
				html += "            <th>备注</th>";
				html += "            <td>";
				html += "                " + item.getRemark() + "";
				html += "            </td>";
				html += "        </tr>";
				html += "    </table>";
				html += "</dd>";
				ContentDto contentDto = new ContentDto();
				contentDto.setContent(html);
				contents.add(contentDto);
			}
		}
		ResponseOutputUtils.renderJson(res, FastJSONUtils.toJsonString(contents));
	}
	
	
	@RequestMapping("/coin")
	public String coin(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		return "/wap_member/coin";
	}
	
	/** 金币记录
	 * 
	 *@param modelMap
	 *@param req
	 *@param res
	 *@param pageNo
	 *@param amount
	 * @author linwk 2016年10月28日
	 */
	@RequestMapping("/coinAjax/{pageNo}")
	public void coinAjax(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Integer pageNo, Integer amount) {
		UserEntity user = RequestUtils.getCurrentUser(req);
		PageRequest pageRequest = new PageRequest(pageNo, amount);
		pageRequest.setOrderBy("createTime");
		pageRequest.setOrderDir("desc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		Page<UserAccountDetailEntity> springPage = userAccountDetailDao.findListByUserId(user.getId(), pb);
		cn.com.easy.utils.Page<UserAccountDetailEntity> page = PageUtils.getPage(springPage);
		List<ContentDto> contents = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(page.getResult())) {
			for (UserAccountDetailEntity item : page.getResult()) {
				String html = "";
				html += "<dt><b>" + item.getCreateTime() + "</b></dt>";
				html += "<dd>";
				html += "    <table cellpadding=\"0\" cellspacing=\"0\">";
//				html += "        <tr>";
//				html += "            <th>金币来源</th>";
//				html += "            <td>";
//				html += "                " + item.getDirection()+ "";
//				html += "            </td>";
//				html += "        </tr>";
				html += "                                                        <tr>";
				html += "            <th>金币</th>";
				html += "            <td>";
				html += "" + item.getAmount() + "";
				html += "            </td>";
				html += "        </tr>";
				html += "                <tr>";
				html += "            <th>备注</th>";
				html += "            <td>";
				html += "                " + item.getRemark() + "";
				html += "            </td>";
				html += "        </tr>";
				html += "    </table>";
				html += "</dd>";
				ContentDto contentDto = new ContentDto();
				contentDto.setContent(html);
				contents.add(contentDto);
			}
		}
		ResponseOutputUtils.renderJson(res, FastJSONUtils.toJsonString(contents));
	}

}
