package cn.com.yyg.front.service;

import java.math.BigDecimal;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.easy.exception.BusinessException;
import cn.com.easy.utils.FastJSONUtils;
import cn.com.easy.utils.SecurityUtils;
import cn.com.yyg.base.em.UserScoreTypeEnum;
import cn.com.yyg.base.entity.SysConfigEntity;
import cn.com.yyg.base.entity.UserAccountDetailEntity;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.front.dao.UserAccountDetailDao;
import cn.com.yyg.front.dao.UserDao;
import cn.com.yyg.front.dto.WeixinDto;
import cn.com.yyg.front.utils.RequestUtils;

@Service
public class UserService {
	private Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private UserDao userDao;
	/** 积分 */
	@Autowired
	private ScoreService scoreService;
	/** 用户资金明细 */
	@Autowired
	private UserAccountDetailDao userAccountDetailDao;
	@Autowired
	private SysConfigService sysConfigService;
	private String ONLOAD_PASSWORD = "123456";

	/**
	 * 获取用户
	 * 
	 * @param id
	 * @return
	 * @author lvzf 2016年9月21日
	 */
	public UserEntity findById(Long id) {
		UserEntity userEntity = userDao.findOne(id);
		return userEntity;
	}

	/**
	 * 判断手机是否存在
	 * 
	 * @param username
	 * @return
	 * @author linwk 2016年9月13日
	 */
	public boolean exsitMobile(String username) {
		if (StringUtils.isNoneBlank(username)) {
			UserEntity userEntity = userDao.findUserByMobile(username);
			if (userEntity != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 先通过手机号码获取
	 * 
	 * @param username
	 * @return
	 * @author linwk 2016年9月13日
	 */
	public UserEntity getUserByMobile(String username) {
		if (StringUtils.isNoneBlank(username)) {
			UserEntity userEntity = userDao.findUserByMobile(username);
			if (userEntity != null) {
				return userEntity;
			}
		}
		return null;
	}

	/**
	 * 微信授权登录或者注册(未注册的先注册)
	 * 
	 * @return
	 * @author linwk 2016年10月13日
	 * @param res
	 * @param request
	 * @throws BusinessException 
	 */
	public UserEntity loginOrRegistByWeiXinLongin(HttpServletRequest req, HttpServletResponse res, WeixinDto weixin) throws BusinessException {
		UserEntity user = new UserEntity();
		// 判断 weixin openId 是否已经注册
		if (weixin != null && StringUtils.isNotBlank(weixin.getOpenid())) {
			// 登录操作
			UserEntity userEntity = userDao.findByWeixinOpenId(weixin.getOpenid());
			if (userEntity == null) {
				logger.error("weixin  user:"+FastJSONUtils.toJsonString(weixin));
				// 注册
				String nickName= weixin.getNickname(); 
				user.setUserName(nickName);
				//user.setUserName(UUID.randomUUID().toString().replaceAll("-", ""));
				user.setNickName(nickName);
				user.setWeixinOpenId(weixin.getOpenid());
				user.setHeadPhotoPath(weixin.getHeadimgurl());				
				String yaoqingUserId =(String)req.getSession().getAttribute("yaoqingUserId");
				if (StringUtils.isNotBlank(yaoqingUserId)) {
					if (NumberUtils.isNumber(yaoqingUserId)) {
						user.setYaoqingUserId(NumberUtils.toLong(yaoqingUserId));
					}
				}
				SysConfigEntity sysConfig = (SysConfigEntity) req.getSession().getAttribute("sysConfig");
				if (sysConfig == null) {
					sysConfig = sysConfigService.getById(SysConfigEntity.ID_COMMON);
					ONLOAD_PASSWORD = sysConfig.getDataMap().get("initPwd");
				}
				// 保存密码
				user.setPassword(SecurityUtils.SHA256(ONLOAD_PASSWORD));
				user.setAccountBalance(new BigDecimal(0));
				user = userDao.save(user);
				// 新会员添加积分
				scoreService.addScore(user, UserScoreTypeEnum.Regist, "注册成为新会员：" + user.getUserName());
				// 注册完登录
				RequestUtils.setCurrentUser(req, user);
			} else {
				RequestUtils.setCurrentUser(req, userEntity);
			}

		}
		return user;
	}

	/**
	 * 添加用户金额明细并统计到用户表
	 * 
	 * @param user
	 * @param amount
	 * @author lvzf 2016年10月26日
	 */
	@Transactional
	public void addAmount(UserEntity user, BigDecimal amount, String remark) {
		// 添加用户金额明细并统计
		UserAccountDetailEntity detail = new UserAccountDetailEntity();
		detail.setAmount(amount);
		if (amount.longValue() < 0) {
			detail.setDirection("-");
		} else {
			detail.setDirection("+");
		}
		detail.setUserId(user.getId());
		detail.setCreateBy(user.getUserName());
		detail.setRemark(remark);
		userAccountDetailDao.save(detail);
		BigDecimal yu = userAccountDetailDao.totalAmountByUserId(user.getId());
		if (yu == null) {
			yu = BigDecimal.ZERO;
		}
		user.setAccountBalance(yu);
		userDao.save(user);
	}
}
