package cn.com.yyg.front.web.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.easy.dto.MessageDTO;
import cn.com.easy.exception.BusinessException;
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.easy.utils.SecurityUtils;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.front.dao.UserDao;
import cn.com.yyg.front.utils.RequestUtils;

/**
 * 用户基本信息
 * 
 * @author nibili 2016年5月23日
 * 
 */
@Controller
@RequestMapping("/admin/setting")
public class AdminSettingController {

	private Logger logger = LoggerFactory.getLogger(AdminSettingController.class);
	/** 用户dao */
	@Autowired
	private UserDao userDao;

	/**
	 * 
	 * 基本信息首页
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author nibili 2016年5月23日
	 */
	@RequestMapping
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		return "/admin/setting/setting";
	}

	/**
	 * 异步修改管理员信息
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param userDto
	 * @param oldPassword
	 * @author nibili 2016年6月7日
	 */
	@RequestMapping("/save.json")
	public void save(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, UserEntity userDto, String oldPassword, String confirmPassword) {

		try {
			UserEntity userEntity = userDao.findOne(RequestUtils.getCurrentAdminUser(req).getId());
			UserEntity user1 = userDao.findUserByMobile(userDto.getMobile());
			if (user1 != null && user1.getId().longValue() != userEntity.getId()) {
				throw new BusinessException("手机号已存在");
			}
			user1 = userDao.findUserByEmail(userDto.getEmail());
			if (user1 != null && user1.getId().longValue() != userEntity.getId()) {
				throw new BusinessException("邮箱已存在");
			}
			userEntity.setEmail(userDto.getEmail());
			userEntity.setTrueName(userDto.getTrueName());
			userEntity.setMobile(userDto.getMobile());
			if (StringUtils.isNotBlank(userDto.getPassword()) == true) {
				// 要修改密码的节奏
				// 判断旧密码，不能为空，然后再判断旧密码是否正确
				if (StringUtils.isBlank(oldPassword) == true || userEntity.getPassword().equals(SecurityUtils.SHA256(oldPassword)) == false) {
					// 旧密码不正确
					throw new BusinessException("旧密码不正确");
				}
				// 判断两个新密码是否一致
				if (StringUtils.equals(userDto.getPassword(), confirmPassword) == false) {
					throw new BusinessException("两次密码不一致");
				}
				userEntity.setPassword(SecurityUtils.SHA256(userDto.getPassword()));
			}
			userDao.save(userEntity);
			RequestUtils.setCurrentAdminUser(req, userEntity);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, ""));
		} catch (BusinessException ex) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, ex.getLocalizedMessage()));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "异步修改管理员信息异常"));
		}
	}
}
