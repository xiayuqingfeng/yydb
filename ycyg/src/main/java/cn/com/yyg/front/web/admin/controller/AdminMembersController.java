package cn.com.yyg.front.web.admin.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.easy.dto.MessageDTO;
import cn.com.easy.dto.PageRequestParamDTO;
import cn.com.easy.exception.BusinessException;
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.easy.utils.SecurityUtils;
import cn.com.yyg.base.em.UserScoreTypeEnum;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.base.utils.PageUtils;
import cn.com.yyg.front.dao.AccessoryDao;
import cn.com.yyg.front.dao.UserDao;
import cn.com.yyg.front.service.ScoreService;

/**
 * 会员管理控制器
 * 
 * @author nibili 2016年5月23日
 * 
 */
@Controller
@RequestMapping("/admin/members")
public class AdminMembersController {

	private Logger logger = LoggerFactory.getLogger(AdminMembersController.class);
	/** 用户dao */
	@Autowired
	private UserDao userDao;
	/** 文件dao */
	@Autowired
	private AccessoryDao accessoryDao;
	/** 积分 */
	@Autowired
	private ScoreService scoreService;

	/**
	 * 解决客户端上传时间参数转换的问题
	 * 
	 * @param binder
	 * @author nibili 2016年6月1日
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * 会员管理首页
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author nibili 2016年5月23日
	 */
	@RequestMapping
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		return "/admin/members/members";
	}

	/**
	 * 异步删除记录
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @author nibili 2016年6月7日
	 */
	@RequestMapping("/delete.json")
	public void delete(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, Long id) {
		try {
			if (id == null) {
				throw new BusinessException("id不能为空");
			}
			UserEntity userEntity = userDao.findOne(id);
			if (userEntity == null) {
				throw new BusinessException("找不到该对象");
			}
			userEntity.setDeleteStatus(true);
			userDao.save(userEntity);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, ""));
		} catch (BusinessException ex) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, ex.getLocalizedMessage()));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "分页获取会员信息异常"));
		}
	}

	/**
	 * 分页获取会员信息
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param pageReqeustParam
	 * @author nibili 2016年6月7日
	 */
	@RequestMapping("/all.json")
	public void all(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, PageRequestParamDTO pageReqeustParam) {
		try {
			Page<UserEntity> springPage = null;
			if (StringUtils.isNotBlank(pageReqeustParam.getSearchText())) {
				springPage = userDao.findBySearchText("%" + pageReqeustParam.getSearchText() + "%", pageReqeustParam.buildSpringDataPageRequest());
			} else {
				springPage = userDao.findAllNotDelete(pageReqeustParam.buildSpringDataPageRequest());
			}
			cn.com.easy.utils.Page<UserEntity> page = PageUtils.getPage(springPage);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, page));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "分页获取会员信息异常"));
		}
	}

	/**
	 * 添加用户页
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param type
	 * @return
	 * @author linwk 2016年9月14日
	 */
	@RequestMapping("/add")
	public String addArticle(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		return "/admin/members/members_add";
	}

	/**
	 * 编辑用户
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @return
	 * @author linwk 2016年9月14日
	 */
	@RequestMapping("/edit")
	public String editArticle(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, Long id) {
		try {
			if (id == null) {
				throw new BusinessException("id不能为空");
			}
			UserEntity user = userDao.findOne(id);
			if (user == null) {
				throw new BusinessException("找不到该对象");
			}

			modelMap.addAttribute("user", user);

		} catch (BusinessException ex) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, ex.getLocalizedMessage()));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "获取会员信息异常"));
		}
		return "/admin/members/members_add";
	}

	/**
	 * 保存用户
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param user
	 * @param headPhoto
	 * @param weixinCode
	 * @param weixinLogo
	 * @author nibili 2016年6月12日
	 */
	@RequestMapping("/save")
	public void save(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, UserEntity user) {
		try {

			if (StringUtils.isBlank(user.getUserName()) == true) {
				throw new BusinessException("用户名不能为空");
			}
			if (StringUtils.isBlank(user.getTrueName()) == true) {
				throw new BusinessException("真实姓名不能为空");
			}
			if (StringUtils.isBlank(user.getMobile()) == true) {
				throw new BusinessException("手机号不能为空");
			}

			boolean isAdd = true;
			UserEntity userEntity = null;
			if (user.getId() == null) {
				// 新增
				UserEntity user1 = userDao.findUserByUserName(user.getUserName());
				if (user1 != null) {
					throw new BusinessException("用户名已存在");
				}
				user1 = userDao.findUserByMobile(user.getMobile());
				if (user1 != null) {
					throw new BusinessException("手机号已存在");
				}
				if (StringUtils.isNoneBlank(user.getEmail())) {
					user1 = userDao.findUserByEmail(user.getEmail());
					if (user1 != null && user1.getId().longValue() != user.getId().longValue()) {
						throw new BusinessException("邮箱已存在");
					}
				}else {
					user.setEmail(null);
				}
				userEntity = new UserEntity();
				userEntity.setUserType(0);
				
				//金额为0
				userEntity.setAccountBalance(BigDecimal.ZERO);
			} else {
				isAdd = false;
				// 更新
				UserEntity user1 = userDao.findUserByUserName(user.getUserName());
				if (user1 != null && user1.getId().longValue() != user.getId()) {
					throw new BusinessException("用户名已存在");
				}
				user1 = userDao.findUserByMobile(user.getMobile());
				if (user1 != null && user1.getId().longValue() != user.getId()) {
					throw new BusinessException("手机号已存在");
				}

				if (StringUtils.isNoneBlank(user.getEmail())) {
					user1 = userDao.findUserByEmail(user.getEmail());
					if (user1 != null && user1.getId().longValue() != user.getId().longValue()) {
						throw new BusinessException("邮箱已存在");
					}
				}
				userEntity = userDao.findOne(user.getId());
			}

			if (StringUtils.isBlank(user.getPassword()) == false) {
				// 设置密码
				userEntity.setPassword(SecurityUtils.SHA256(user.getPassword()));
			}
			userEntity.setUserName(user.getUserName());
			userEntity.setNickName(user.getNickName());
			userEntity.setTrueName(user.getTrueName());
			userEntity.setMobile(user.getMobile());
			userEntity.setAddress(user.getAddress());
			userEntity.setEmail(user.getEmail());
			userEntity.setQq(user.getQq());
			userEntity.setLastModifyBy(user.getUserName());
			//userEntity.setAccountBalance(user.getAccountBalance());
			userEntity.setInnerUser(user.isInnerUser());

			userEntity.setCityId(user.getCityId());
			userEntity.setAreaId(user.getAreaId());
			userEntity.setProvinceId(user.getCityId());

			userDao.save(userEntity);
			// 新会员添加积分
			if (isAdd) {
				scoreService.addScore(userEntity, UserScoreTypeEnum.Regist, "注册成为新会员：" + userEntity.getUserName());
			}
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, "保存成功"));
		} catch (BusinessException ex) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, ex.getMessage()));
		} catch (Exception ex) {
			logger.error("保存用户错误：", ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "保存错误"));
		}
	}
}
