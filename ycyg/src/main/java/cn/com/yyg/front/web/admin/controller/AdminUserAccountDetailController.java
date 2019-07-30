package cn.com.yyg.front.web.admin.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.easy.dto.MessageDTO;
import cn.com.easy.dto.PageRequestParamDTO;
import cn.com.easy.exception.BusinessException;
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.yyg.base.entity.UserAccountDetailEntity;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.base.utils.PageUtils;
import cn.com.yyg.front.dao.UserAccountDetailDao;
import cn.com.yyg.front.dao.UserDao;
import cn.com.yyg.front.service.UserService;

/**
 * 用户余额明细
 * 
 * @author linwk 2016年10月26日
 * 
 */
@Controller
@RequestMapping("/admin/userAccountDetail")
public class AdminUserAccountDetailController {
	private Logger logger = LoggerFactory.getLogger(AdminUserAccountDetailController.class);

	@Autowired
	private UserAccountDetailDao userAccountDetailDao;

	@Autowired
	private UserService userService;

	@Autowired
	private UserDao userDao;

	@RequestMapping
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, Long userId) throws BusinessException {
		if (userId == null) {
			throw new BusinessException("用户Id不能为空");
		}
		UserEntity user = userDao.findOne(userId);
		if (user == null) {
			throw new BusinessException("用户不存在");
		}
		modelMap.addAttribute("user", user);
		return "/admin/userAccountDetail/userAccountDetail";
	}

	@RequestMapping("/all.json")
	public void all(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, PageRequestParamDTO pageReqeustParam, Long userId) {
		try {
			if (userId == null) {
				throw new BusinessException("用户Id不能为空");
			}
			Page<UserAccountDetailEntity> springPage = null;
			springPage = userAccountDetailDao.findListByUserId(userId, pageReqeustParam.buildSpringDataPageRequest());
			cn.com.easy.utils.Page<UserAccountDetailEntity> page = PageUtils.getPage(springPage);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, page));
		} catch (BusinessException ex) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, ex.getLocalizedMessage()));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "分页获取会员余额信息异常"));
		}
	}

	@RequestMapping("/amount.json")
	public void addAmount(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, Long userId, BigDecimal amount, String remark) {
		try {
			if (userId == null) {
				throw new BusinessException("用户Id不能为空");
			}
			if (amount == null) {
				throw new BusinessException("充值金额不能为空");
			}
			if (StringUtils.isBlank(remark)) {
				remark = "充值";
			}
			UserEntity user = userDao.findOne(userId);
			if (user == null) {
				throw new BusinessException("用户不存在");
			}
			// 执行充值操作
			userService.addAmount(user, amount, remark);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, "充值成功"));
		} catch (BusinessException ex) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, ex.getLocalizedMessage()));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "分页获取会员余额信息异常"));
		}
	}
}
