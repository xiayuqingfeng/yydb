package cn.com.yyg.front.web.pub.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.easy.dto.MessageDTO;
import cn.com.easy.dto.PageRequestParamDTO;
import cn.com.easy.exception.BusinessException;
import cn.com.easy.utils.FastJSONUtils;
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.yyg.base.em.UserScoreTypeEnum;
import cn.com.yyg.base.entity.BigWheelInfoEntity;
import cn.com.yyg.base.entity.BigWheelJoinUserEntity;
import cn.com.yyg.base.entity.BigWheelPrizeEntity;
import cn.com.yyg.base.entity.BigWheelUserPrizeEntity;
import cn.com.yyg.base.entity.SysConfigEntity;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.front.dao.BigWheelInfoDao;
import cn.com.yyg.front.dao.BigWheelJoinUserDao;
import cn.com.yyg.front.dao.BigWheelPrizeDao;
import cn.com.yyg.front.dao.BigWheelUserPrizeDao;
import cn.com.yyg.front.dao.UserScoreDao;
import cn.com.yyg.front.dto.ScoreConfigDto;
import cn.com.yyg.front.service.ScoreService;
import cn.com.yyg.front.service.SysConfigService;
import cn.com.yyg.front.service.WhdService;
import cn.com.yyg.front.utils.RequestUtils;
import cn.com.yyg.front.web.wap.dto.CheckPhoneDto;
import cn.com.yyg.front.web.wap.dto.PrizeResultDto;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 前端大转盘控制器
 * 
 * @author linwk 2016年10月20日
 * 
 */
@Controller
@RequestMapping("/member/bigwheel")
public class PubBigWheelFrontController {

	private Logger logger = LoggerFactory.getLogger(PubBigWheelFrontController.class);

	/** 抽奖用户的sessionkey */
	private final static String SESSION_KEY = "PUB_USER_TEL";

	/** 大转盘dao */
	@Autowired
	private BigWheelInfoDao bigWheelInfoDao;
	/** 大转盘奖项dao */
	@Autowired
	private BigWheelPrizeDao bigWheelPrizeDao;
	/** 大转盘用户奖项dao */
	@Autowired
	private BigWheelUserPrizeDao bigWheelUserPrizeDao;
	/** 参与用户dao */
	@Autowired
	private BigWheelJoinUserDao bigWheelJoinUserDao;
	/** 微活动服务类 */
	@Autowired
	private WhdService whdService;

	/** 用户积分 */
	@Autowired
	private UserScoreDao userScoreDao;

	@Autowired
	private SysConfigService sysConfigService;

	@Autowired
	private ScoreService scoreService;

	@RequestMapping("")
	public String indexNoUserIdNoBigWheelId(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) throws Exception {
		return index(modelMap, req, res, 1l, RequestUtils.getCurrentUser(req).getId());
	}

	@RequestMapping("/{bigWheelId}")
	public String indexNoUserId(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long bigWheelId) throws Exception {
		return index(modelMap, req, res, bigWheelId, RequestUtils.getCurrentUser(req).getId());
	}

	/**
	 * 大转盘首页
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @author nibili 2016年6月3日
	 */
	@RequestMapping("/{bigWheelId}/{userId}")
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long bigWheelId, @PathVariable Long userId) throws Exception {
		// 过滤器 会判断是微信 才会加载微信脚本
		UserEntity user = RequestUtils.getCurrentUser(req);
		// 一、大转盘实体
		BigWheelInfoEntity bigWheelInfoEntity = bigWheelInfoDao.findOne(bigWheelId);
		modelMap.addAttribute("bigWheelInfo", bigWheelInfoEntity);
		// 二、解板转盘刻度
		List<BigWheelPrizeEntity> bigWheelPrizeList = Lists.newArrayList();
		String[] calibrationIdArray = StringUtils.split(bigWheelInfoEntity.getWheelCalibrations(), ",");
		BigWheelPrizeEntity bigWheelPrizeEntity;
		Long countNoPrice = 0l;
		for (String temp : calibrationIdArray) {
			Long id = Long.valueOf(temp);
			if (id == -1) {
				bigWheelPrizeEntity = new BigWheelPrizeEntity();
				countNoPrice = countNoPrice - 1l;
				bigWheelPrizeEntity.setId(countNoPrice);
				bigWheelPrizeEntity.setPrizeName("谢谢参与");
				bigWheelPrizeList.add(bigWheelPrizeEntity);
			} else {
				bigWheelPrizeList.add(bigWheelPrizeDao.findOne(id));
			}
		}
		modelMap.addAttribute("bigWheelPrizes", bigWheelPrizeList);

		modelMap.addAttribute("user", user);

		List<BigWheelPrizeEntity> bigWheelPrizes = bigWheelPrizeDao.findByBigWheelId(bigWheelId);
		modelMap.addAttribute("bigWheelPrizePage", bigWheelPrizes);

		// 添加中奖信息
		PageRequestParamDTO pageRequestParamDTO = new PageRequestParamDTO();
		pageRequestParamDTO.setPageSize(6);
		Page<BigWheelUserPrizeEntity> prizesPage = bigWheelUserPrizeDao.findWinnings(bigWheelId, pageRequestParamDTO.buildSpringDataPageRequest());
		if (CollectionUtils.isNotEmpty(prizesPage.getContent())) {
			modelMap.addAttribute("prizesPage", prizesPage.getContent());
		}

		
		// 三、是否已登录，已登录返回用户需要显示的信息; 以及是否
		try {
			String tel = user.getMobile();
			if (StringUtils.isBlank(tel)) {
				throw new BusinessException("请设置手机号");
			}
			// 还没完盖个人信息,保存一个
			CheckPhoneDto checkPhoneDto = this.getCheckPhoneDto(req, bigWheelId, tel);
			modelMap.addAttribute("checkPhoneDto", checkPhoneDto);
		} catch (BusinessException ex) {
			// 参与人数超限
			modelMap.addAttribute("error", ex.getLocalizedMessage());
		}

		// 四、奖项列表
		bigWheelPrizeList = bigWheelPrizeDao.findByBigWheelId(bigWheelId);
		modelMap.addAttribute("bigWheelPrizesList", bigWheelPrizeList);

		// 用户总积分
		Long scores = userScoreDao.totalScoreByUserId(user.getId());
		if (scores == null) {
			scores = 0L;
		}
		modelMap.addAttribute("scores", scores);

		// 五 抽奖所需积分
		SysConfigEntity config = sysConfigService.getById(SysConfigEntity.ID_SCORE);
		if (config != null) {
			ScoreConfigDto scoreRule = FastJSONUtils.toObject(config.getData(), ScoreConfigDto.class);
			modelMap.addAttribute("bigWheelJoinScore", scoreRule.getBigWheelJoinScore());
		}
		return "/pub/big_wheel/big_wheel";
	}

	@RequestMapping("/{bigWheelId}/loadNewInfo.html")
	public String loadNewInfo(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long bigWheelId){
		try {
			indexNoUserIdNoBigWheelId(modelMap, req, res);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/pub/big_wheel/big_wheel_info";
	}
	
	/**
	 * 我获得的奖品
	 * 
	 * @param req
	 * @param res
	 * @param bigWheelId
	 * @author nibili 2016年6月4日
	 */
	@RequestMapping("/{bigWheelId}/myprize.json")
	public void getMyPrize(HttpServletRequest req, HttpServletResponse res, @PathVariable Long bigWheelId) {
		try {
			UserEntity user = RequestUtils.getCurrentUser(req);
			// 获取我的奖品
			PageRequestParamDTO pageRequestParamDTO = new PageRequestParamDTO();
			pageRequestParamDTO.setPageSize(50);
			String tel = user.getMobile();
			Page<BigWheelUserPrizeEntity> page = bigWheelUserPrizeDao.findUserWinnings(bigWheelId, tel, pageRequestParamDTO.buildSpringDataPageRequest());
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, page.getContent()));
		} catch (Exception ex) {
			logger.error("", ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "校验个人信息异常，请到个人信息页面确认您的手机号码已填写"));
		}
	}

	/**
	 * 抽奖<br>
	 * 
	 * 
	 * @param req
	 * @param res
	 * @param bigWheelId
	 * @param tel
	 * @author nibili 2016年6月4日
	 */
	@RequestMapping("/{bigWheelId}/prize.json")
	public void prize(HttpServletRequest req, HttpServletResponse res, @PathVariable Long bigWheelId) {
		UserEntity user = RequestUtils.getCurrentUser(req);

		try {
			// 已登录
			// 大转盘实体
			BigWheelInfoEntity bigWheelInfoEntity = bigWheelInfoDao.findOne(bigWheelId);
			if (bigWheelInfoEntity == null) {
				ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "抽奖异常！!"));
				return;
			}
			// tel
			String tel = user.getMobile();

			if (StringUtils.isBlank(tel)) {
				throw new BusinessException("请设置手机号");
			}

			// 一、参与人数上限
			BigWheelJoinUserEntity bigWheelJoinUserEntity = bigWheelJoinUserDao.findByBigWheelIdAndTel(bigWheelId, tel);
			if (bigWheelJoinUserEntity == null) {
				// 未参加本次活动
				// ResponseOutputUtils.renderJson(res,
				// MessageDTO.newInstance("", false, "亲们太热情了！您的积分未达到抽奖要求!"));
			}
			// 二、手机号剩余抽奖次数
			int lastTimes = 0;
			lastTimes = getLastTimes(user, lastTimes);

			// 三、有抽奖机会，才能抽奖
			if (lastTimes >= -1 && lastTimes != 0) {
				// 当前大转盘的奖项
				List<BigWheelPrizeEntity> bigWheelPrizeList = bigWheelPrizeDao.findByBigWheelId(bigWheelId);
				// 抽奖结果dto
				PrizeResultDto prizeResultDto = new PrizeResultDto();
				// 用户奖项，不管有没有中奖，没有中奖也要把“谢谢参与”信息保存到表中
				BigWheelUserPrizeEntity bigWheelUserPrizeEntity = new BigWheelUserPrizeEntity();
				// 抽奖算法，获得中奖奖项实体
				BigWheelPrizeEntity resultPrizeEntity = this.getGameWinningPrize(bigWheelPrizeList);

				// 1、还剩余抽奖次数
				if (lastTimes == -1) {
					prizeResultDto.setLastTimes(lastTimes);
				} else {
					prizeResultDto.setLastTimes(lastTimes - 1);
				}
				// 2、保存抽奖信息到表中
				{
					if (StringUtils.isNotBlank(user.getTrueName())) {
						bigWheelUserPrizeEntity.setName(user.getTrueName());
					} else if (StringUtils.isNotBlank(user.getNickName())) {
						bigWheelUserPrizeEntity.setName(user.getNickName());
					} else {
						bigWheelUserPrizeEntity.setName(user.getMobile());
					}
					// 不管有没有中奖，都要把“谢谢参与”信息保存到表中
					bigWheelUserPrizeEntity.setBigWheelId(bigWheelId);
					if (resultPrizeEntity != null && StringUtils.isNotBlank(resultPrizeEntity.getPrizeName())) {
						// 中奖id
						bigWheelUserPrizeEntity.setPrizeId(resultPrizeEntity.getId());
						bigWheelUserPrizeEntity.setPrizeName(resultPrizeEntity.getPrizeName());
						bigWheelUserPrizeEntity.setTel(tel);
						// 保存用户中奖（或者 谢谢参与）信息
						bigWheelUserPrizeEntity = bigWheelUserPrizeDao.save(bigWheelUserPrizeEntity);
					} else {
						// 没中奖
						// 解板转盘刻度
						String[] calibrationIdArray = StringUtils.split(bigWheelInfoEntity.getWheelCalibrations(), ",");
						int countNoPrice = 0;
						for (String temp : calibrationIdArray) {
							Long id = Long.valueOf(temp);
							if (id == -1) {
								countNoPrice++;
							}
						}
						int peizeNum = RandomUtils.nextInt(0, countNoPrice);
						String prizeNumString = "-" + peizeNum + "l";
						bigWheelUserPrizeEntity.setPrizeId(NumberUtils.toLong(prizeNumString, 0));
					}
				}
				// 3、返回抽奖结果dto
				if (resultPrizeEntity == null) {
					// 未中奖
					prizeResultDto.setIsPrized(false);
					prizeResultDto.setPrizedId(-1l);
				} else {
					// 中奖了
					// 设置抽奖结果dto数据
					prizeResultDto.setPrizeName(resultPrizeEntity.getPrizeName());
					// 如果是积分
					if (resultPrizeEntity.getPrizeType() == 0) {
						int num = NumberUtils.toInt(resultPrizeEntity.getPrizeScore().toString());
						// scoreService.addScore(user,
						// UserScoreTypeEnum.BigWheelWin, "大转盘中奖");
						scoreService.addScore4BigWheel(user, num, "大转盘中奖");
					}
					prizeResultDto.setIsPrized(true);
					prizeResultDto.setPrizedId(resultPrizeEntity.getId());
				}
				// 抽奖成功 --重新计算积分
				// 抽奖所需积分
				scoreService.reduceScore(user, UserScoreTypeEnum.BigWheelJoin, "大转盘抽奖");

				// 用户总积分
				Long scores = userScoreDao.totalScoreByUserId(user.getId());
				if (scores == null) {
					scores = 0L;
				}
				prizeResultDto.setAllScore(scores);
				// 再次计算抽奖次数
				lastTimes = getLastTimes(user, lastTimes);
				prizeResultDto.setLastTimes(lastTimes);
				ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, prizeResultDto));
			} else {
				ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "您的抽奖次数已用光!"));
			}
		} catch (Exception ex) {
			logger.error("", ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "抽奖异常，请稍后再试"));
		}
	}

	/**
	 * 收集用户信息
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param bigWheelId
	 * @return
	 * @throws Exception
	 * @author nibili 2016年6月4日
	 */
	@RequestMapping("/{bigWheelId}/collect_user_info.json")
	public void collectUserInfo(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long bigWheelId, BigWheelJoinUserEntity bigWheelJoinUserEntity)
			throws Exception {
		UserEntity user = RequestUtils.getCurrentUser(req);
		try {
			// 已登录
			whdService.collectUserInfo(bigWheelId, user.getMobile(), bigWheelJoinUserEntity);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, ""));
		} catch (BusinessException ex) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, ex.getMessage()));
		} catch (Exception ex) {
			logger.error("", ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "提交个人信息异常，请稍后再试"));
		}

	}

	/**
	 * 校验，获取当前用户信息
	 * 
	 * @param bigWheelId
	 * @param tel
	 * @return
	 * @author nibili 2016年6月4日
	 */
	private CheckPhoneDto getCheckPhoneDto(HttpServletRequest req, Long bigWheelId, String tel) throws BusinessException {
		UserEntity user = RequestUtils.getCurrentUser(req);
		// 返回给客户端的dto
		CheckPhoneDto checkPhoneDto = new CheckPhoneDto();
		// 一、手机号剩余抽奖次数
		int lastTimes = 0;
		// 判断参与频率类型
		// 可参与次数
		// 积分 剩余 除以所需积分
		// 用户总积分
		lastTimes = getLastTimes(user, lastTimes);
		//
		checkPhoneDto.setLastTimes(lastTimes);
		return checkPhoneDto;
	}

	private int getLastTimes(UserEntity user, int lastTimes) {
		Long scores = userScoreDao.totalScoreByUserId(user.getId());
		if (scores == null) {
			scores = 0L;
		}
		SysConfigEntity config = sysConfigService.getById(SysConfigEntity.ID_SCORE);
		if (config != null) {
			ScoreConfigDto scoreRule = FastJSONUtils.toObject(config.getData(), ScoreConfigDto.class);
			lastTimes = (int) (scores / scoreRule.getBigWheelJoinScore());
		}
		return lastTimes;
	}

	/** 用于取日期ms随机数 */
	private Calendar calendar = Calendar.getInstance();

	/**
	 * 获取中奖奖项
	 * 
	 * @param list
	 * @return
	 * @auth nibili 2016年6月6日 上午12:19:55
	 */
	private BigWheelPrizeEntity getGameWinningPrize(List<BigWheelPrizeEntity> list) {

		// 查询奖项还剩下多额，没有名额了要移除掉
		if (CollectionUtils.isEmpty(list) == false) {
			for (BigWheelPrizeEntity bigWheelPrizeEntity : list) {
				// 奖品数量
				int prizeCount = bigWheelPrizeEntity.getPrizeCount();
				List<BigWheelUserPrizeEntity> prizeList = bigWheelUserPrizeDao.findByPrizeId(bigWheelPrizeEntity.getId());
				if (CollectionUtils.isNotEmpty(prizeList) == true) {
					// 剩余奖品数量
					prizeCount = prizeCount - prizeList.size();
				}
				// 新的奖品数量
				bigWheelPrizeEntity.setPrizeCount(prizeCount);
			}
		}
		if (CollectionUtils.isEmpty(list) == true) {
			return null;
		}
		// 号码池
		List<Integer> numberList = Lists.newArrayList();
		for (int i = 0; i < 100; i++) {
			numberList.add(i, i);
		}
		// 中奖数组
		Map<Integer, BigWheelPrizeEntity> map = Maps.newHashMap();
		for (BigWheelPrizeEntity bigWheelPrizeEntity : list) {
			// 奖品必须大于0
			if (bigWheelPrizeEntity.getPrizeCount() > 0) {
				// 中奖概率
				int prizeRate = bigWheelPrizeEntity.getPrizeRate();
				for (int i = 0; i < prizeRate; i++) {
					// 号码池的序号
					int numberIndex = RandomUtils.nextInt(0, numberList.size() - 1);
					// 会中奖号码
					int winningNumTemp = numberList.get(numberIndex);
					// 号码池中移除
					numberList.remove(numberIndex);
					// 放入map中
					map.put(winningNumTemp, bigWheelPrizeEntity);
				}
			}
		}
		// 中奖号码
		int winningNum = RandomUtils.nextInt(0, calendar.get(Calendar.MILLISECOND) / 10);
		// 中奖的奖项
		BigWheelPrizeEntity bigWheelPrizeEntity = map.get(winningNum);
		return bigWheelPrizeEntity;
	}
}
