package cn.com.yyg.front.web.admin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import cn.com.easy.dto.MessageDTO;
import cn.com.easy.dto.PageRequestParamDTO;
import cn.com.easy.exception.BusinessException;
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.yyg.base.em.UserScoreTypeEnum;
import cn.com.yyg.base.entity.AccessoryEntity;
import cn.com.yyg.base.entity.BigWheelInfoEntity;
import cn.com.yyg.base.entity.BigWheelPrizeEntity;
import cn.com.yyg.base.entity.BigWheelUserPrizeEntity;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.base.utils.PageUtils;
import cn.com.yyg.base.utils.UploadFileUtil;
import cn.com.yyg.front.dao.BigWheelInfoDao;
import cn.com.yyg.front.dao.BigWheelPrizeDao;
import cn.com.yyg.front.dao.BigWheelUserPrizeDao;
import cn.com.yyg.front.service.WhdService;
import cn.com.yyg.front.utils.RequestUtils;

import com.google.common.collect.Lists;

/**
 * 微活动控制器
 * 
 * @author nibili 2016年5月23日
 * 
 */
@Controller
@RequestMapping("/admin/whd")
public class AdminBigWheelController {

	private Logger logger = LoggerFactory.getLogger(AdminBigWheelController.class);

	/** 大转盘dao */
	@Autowired
	private BigWheelInfoDao bigWheelInfoDao;
	/** 大转盘奖项dao */
	@Autowired
	private BigWheelPrizeDao bigWheelPrizeDao;
	/** 微活动服务 */
	@Autowired
	private WhdService whdService;
	/** 大转盘参与用户 */
	@Autowired
	private BigWheelUserPrizeDao bigWheelUserPrizeDao;

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
	 * 幸运大转盘首页
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author nibili 2016年5月23日
	 */
	@RequestMapping("/bigwheel")
	public String bigwheel(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		return "/admin/wei_huo_dong/wei_huo_dong_bigwheel";
	}

	/**
	 * 异步获取大转盘信息
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @author nibili 2016年6月2日
	 */
	@RequestMapping("/bigwheel/all.json")
	public void bigwheelAll(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, PageRequestParamDTO pageRequestParamDTO) {
		try {
			UserEntity user = RequestUtils.getCurrentAdminUser(req);
			Page<BigWheelInfoEntity> springPage = bigWheelInfoDao.findByUserIdAndDeleteStatus(user.getId(), false, pageRequestParamDTO.buildSpringDataPageRequest());
			cn.com.easy.utils.Page<BigWheelInfoEntity> page = PageUtils.getPage(springPage);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, page));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "异步获取大转盘信息 ，异常，稍后再试"));
		}
	}

	/**
	 * 幸运大转盘--添加--页面
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author nibili 2016年5月27日
	 */
	@RequestMapping("/bigwheel/add")
	public String bigwheelAdd(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		return "/admin/wei_huo_dong/wei_huo_dong_bigwheel_add";
	}

	/**
	 * 幸运大转盘--保存
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param imageFile
	 * @param bigWheelInfoEntity
	 *            收集用户的哪些信息
	 * @return
	 * @author nibili 2016年6月1日
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/bigwheel/save", method = RequestMethod.POST)
	public String bigwheelSave(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, MultipartFile imageFile, BigWheelInfoEntity bigWheelInfoEntity,
			String[] collectInfoTypesArray) throws BusinessException {
		UserEntity userEntity = RequestUtils.getCurrentAdminUser(req);
		// 进行字段验证
		try {
			this.validateBigWheelInfo(bigWheelInfoEntity, userEntity.getId());
		} catch (BusinessException ex) {
			// 前端判断是否有错误要显示
			modelMap.addAttribute("error", ex.getMessage());
			// 回显提交的数据
			modelMap.addAttribute("bigWheelInfo", bigWheelInfoEntity);
			// 出错返回错误页面，重新编辑
			return "/admin/wei_huo_dong/wei_huo_dong_bigwheel_add";
		}
		// 三、如果图片不为空，保存图片
		if (imageFile != null && imageFile.getSize() > 0) {
			// 上传图片到硬盘
			AccessoryEntity acc = UploadFileUtil.uploadFile(imageFile, "user/whd", userEntity.getId() + "_" + UUID.randomUUID().toString(), userEntity.getId());
			bigWheelInfoEntity.setPhotoId(acc.getId());
			bigWheelInfoEntity.setPhotoPath(acc.getPath() + "/" + acc.getId());
		}
		// 最终保存的实体
		BigWheelInfoEntity bigWheelInfoEntityTemp = null;
		// 判断添加还是更新
		if (bigWheelInfoEntity.getId() == null) {
			// 添加
			bigWheelInfoEntity.setUserId(userEntity.getId());
			bigWheelInfoEntityTemp = bigWheelInfoEntity;
		} else {
			// 更新
			bigWheelInfoEntityTemp = bigWheelInfoDao.findByIdAndUserIdAndDeleteStatus(bigWheelInfoEntity.getId(), userEntity.getId(), false);
			if (bigWheelInfoEntityTemp == null) {
				// 前端判断是否有错误要显示
				modelMap.addAttribute("error", "不合法的请求");
				// 回显提交的数据
				modelMap.addAttribute("bigWheelInfo", bigWheelInfoEntity);
				// 出错返回错误页面，重新编辑
				return "/admin/wei_huo_dong/wei_huo_dong_bigwheel_add";
			}
			bigWheelInfoEntityTemp.setTitle(bigWheelInfoEntity.getTitle());
			bigWheelInfoEntityTemp.setDescription(bigWheelInfoEntity.getDescription());
			if (bigWheelInfoEntity.getPhotoId() != null) {
				bigWheelInfoEntityTemp.setPhotoId(bigWheelInfoEntity.getPhotoId());
				bigWheelInfoEntityTemp.setPhotoPath(bigWheelInfoEntity.getPhotoPath());
			}
			bigWheelInfoEntityTemp.setActiveRule(bigWheelInfoEntity.getActiveRule());
		}
		bigWheelInfoDao.save(bigWheelInfoEntityTemp);
		// 保存成功跳到 奖品管理页面
		return "redirect:/admin/whd/bigwheel/" + bigWheelInfoEntityTemp.getId() + "/prize";
	}

	/**
	 * 验证大转盘数据
	 * 
	 * @param bigWheelInfoEntity
	 * @param userId
	 * @author nibili 2016年6月1日
	 * @throws BusinessException 
	 */
	private void validateBigWheelInfo(BigWheelInfoEntity bigWheelInfoEntity, Long userId) throws BusinessException {
		if (StringUtils.isBlank(bigWheelInfoEntity.getTitle()) == true) {
			throw new BusinessException("活动标题不能为空!");
		}
		if (StringUtils.isBlank(bigWheelInfoEntity.getDescription()) == true) {
			throw new BusinessException("活动说明不能为空!");
		}
		if (StringUtils.isBlank(bigWheelInfoEntity.getActiveRule()) == true) {
			throw new BusinessException("活动规则不能为空!");
		}
	}

	/**
	 * 删除大转盘
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author nibili 2016年6月3日
	 */
	@RequestMapping("/bigwheel/{id}/delete.json")
	public void bigwheelDelete(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long id) {
		try {
			UserEntity user = RequestUtils.getCurrentAdminUser(req);
			BigWheelInfoEntity bigWheelInfoEntity = bigWheelInfoDao.findByIdAndUserIdAndDeleteStatus(id, user.getId(), false);
			bigWheelInfoEntity.setDeleteStatus(true);
			bigWheelInfoDao.save(bigWheelInfoEntity);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, ""));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "删除大转盘，异常，稍后再试"));
		}
	}

	/**
	 * 幸运大转盘--编辑--页面
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author nibili 2016年5月27日
	 */
	@RequestMapping("/bigwheel/{id}/edit")
	public String bigwheelEdit(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long id) {
		BigWheelInfoEntity bigWheelInfoEntity = bigWheelInfoDao.findOne(id);
		if (bigWheelInfoEntity != null) {
			modelMap.addAttribute("bigWheelInfo", bigWheelInfoEntity);
		}
		return "/admin/wei_huo_dong/wei_huo_dong_bigwheel_add";
	}

	/**
	 * 幸运大转盘--奖品管理页面
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 *            BigWheelInfoEntity表id
	 * @return
	 * @author nibili 2016年6月1日
	 */
	@RequestMapping("/bigwheel/{id}/prize")
	public String bigwheelPrize(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long id) {
		modelMap.addAttribute("id", id);
		return "/admin/wei_huo_dong/wei_huo_dong_bigwheel_prize";
	}

	/**
	 * 异步获取所有奖项列表
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @author nibili 2016年6月2日
	 */
	@RequestMapping("/bigwheel/{bigWheelId}/prize/all.json")
	public void bigwheelPrizeAll(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long bigWheelId, PageRequestParamDTO pageRequestParamDTO) {
		try {
			UserEntity user = RequestUtils.getCurrentAdminUser(req);
			Page<BigWheelPrizeEntity> springPage = bigWheelPrizeDao.findByBigWheelIdAndUserId(bigWheelId, user.getId(), pageRequestParamDTO.buildSpringDataPageRequest());
			//
			cn.com.easy.utils.Page<BigWheelPrizeEntity> page = PageUtils.getPage(springPage);
			// 总的中奖概率
			Integer totalPrizeRate = bigWheelPrizeDao.sumPrizeRateByBigWheelId(bigWheelId.longValue());
			if (totalPrizeRate == null) {
				totalPrizeRate = 0;
			}
			int prizeRate = 100 - totalPrizeRate;
			// 添加一个谢谢参与
			BigWheelPrizeEntity bigWheelPrizeEntity = new BigWheelPrizeEntity();
			bigWheelPrizeEntity.setPrizeName("谢谢参与");
			bigWheelPrizeEntity.setPrizeContent("");
			bigWheelPrizeEntity.setPrizeCount(-1);
			bigWheelPrizeEntity.setPrizeRate(prizeRate);
			List<BigWheelPrizeEntity> list = Lists.newArrayList();
			if (CollectionUtils.isEmpty(page.getResult()) == true) {
				list.add(bigWheelPrizeEntity);
				page.setTotalItems(1);
			} else {
				list.add(bigWheelPrizeEntity);
				list.addAll(page.getResult());
			}
			page.setResult(list);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, page));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "异步获取大转盘奖项信息 ，异常，稍后再试"));
		}
	}

	/**
	 * 幸运大转盘--添加奖品页
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 *            BigWheelInfoEntity表id
	 * @return
	 * @author nibili 2016年6月1日
	 */
	@RequestMapping("/bigwheel/{id}/prize/add")
	public String bigwheelPrizeAdd(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long id) {
		modelMap.addAttribute("bigWheelId", id);
		return "/admin/wei_huo_dong/wei_huo_dong_bigwheel_prize_add";
	}

	/**
	 * 幸运大转盘--保存奖品
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param imageFile
	 * @param bigWheelId
	 *            大转盘id
	 * @param bigWheelPrizeEntity
	 * @return
	 * @author nibili 2016年6月2日
	 */
	@RequestMapping("/bigwheel/{bigWheelId}/prize/save")
	public String bigwheelPrizeSave(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, MultipartFile imageFile, @PathVariable Long bigWheelId,
			BigWheelPrizeEntity bigWheelPrizeEntity) throws BusinessException {

		this.validateBigWheelPrize(bigWheelPrizeEntity, bigWheelId);
		// 奖品图片，也不能为空
		if (bigWheelPrizeEntity.getId() == null) {
			if (imageFile == null || imageFile.getSize() <= 0) {
				//throw new BusinessException("奖品图片不能为空!");
			}
		}
		// 二、如果图片不为空，保存图片
		if (imageFile != null && imageFile.getSize() > 0) {
			UserEntity userEntity = RequestUtils.getCurrentAdminUser(req);
			// 上传图片到硬盘
			AccessoryEntity acc = UploadFileUtil.uploadFile(imageFile, "user/whd", userEntity.getId() + "_" + UUID.randomUUID().toString(), userEntity.getId());
			bigWheelPrizeEntity.setPhotoId(acc.getId());
			bigWheelPrizeEntity.setPhotoPath(acc.getPath() + "/" + acc.getName());
		}

		// 最终保存的实体
		BigWheelPrizeEntity bigWheelPrizeEntityTemp = null;
		UserEntity userEntity = RequestUtils.getCurrentAdminUser(req);
		// 判断是添加还是更新
		if (bigWheelPrizeEntity.getId() == null) {
			// 添加
			bigWheelPrizeEntity.setUserId(userEntity.getId());
			bigWheelPrizeEntity.setBigWheelId(bigWheelId);
			bigWheelPrizeEntityTemp = bigWheelPrizeEntity;
		} else {
			// 更新
			// 大转盘实体
			BigWheelInfoEntity bigWheelInfoEntityTemp = bigWheelInfoDao.findByIdAndUserIdAndDeleteStatus(bigWheelId, userEntity.getId(), false);
			// 奖项实体
			bigWheelPrizeEntityTemp = bigWheelPrizeDao.findByIdAndUserId(bigWheelPrizeEntity.getId(), userEntity.getId());
			if (bigWheelPrizeEntityTemp == null) {
				// 前端判断是否有错误要显示
				modelMap.addAttribute("error", "不合法的请求");
				// 回显提交的数据
				modelMap.addAttribute("bigWheelPrize", bigWheelPrizeEntity);
				// 出错返回错误页面，重新编辑
				return "/admin/wei_huo_dong/wei_huo_dong_bigwheel_prize_add";
			}
			bigWheelPrizeEntityTemp.setPrizeLevel(bigWheelPrizeEntity.getPrizeLevel());
			bigWheelPrizeEntityTemp.setPrizeName(bigWheelPrizeEntity.getPrizeName());

			bigWheelPrizeEntityTemp.setPrizeType(bigWheelPrizeEntity.getPrizeType());
			bigWheelPrizeEntityTemp.setPrizeScore(bigWheelPrizeEntity.getPrizeScore());
			

			bigWheelPrizeEntityTemp.setPrizeContent(bigWheelPrizeEntity.getPrizeContent());
			if (bigWheelPrizeEntity.getPhotoId() != null) {
				bigWheelPrizeEntityTemp.setPhotoId(bigWheelPrizeEntity.getPhotoId());
				bigWheelPrizeEntityTemp.setPhotoPath(bigWheelPrizeEntity.getPhotoPath());
			}
			bigWheelPrizeEntityTemp.setPrizeCount(bigWheelPrizeEntity.getPrizeCount());
			bigWheelPrizeEntityTemp.setPrizeRate(bigWheelPrizeEntity.getPrizeRate());
			bigWheelPrizeEntityTemp.setAcceptPrizeType(bigWheelPrizeEntity.getAcceptPrizeType());
			bigWheelPrizeEntityTemp.setCoupons(bigWheelPrizeEntity.getCoupons());
			bigWheelPrizeEntityTemp.setOnLineUrl(bigWheelPrizeEntity.getOnLineUrl());
			bigWheelPrizeEntityTemp.setTakeSelfAddress(bigWheelPrizeEntity.getTakeSelfAddress());
		}
		bigWheelPrizeDao.save(bigWheelPrizeEntityTemp);
		// 保存完奖品跳到 奖品 管理页
		return "redirect:/admin/whd/bigwheel/" + bigWheelId + "/prize";
	}

	/**
	 * 校验大转盘奖项
	 * 
	 * @param bigWheelPrizeEntity
	 * @param bigWheelId
	 * @author nibili 2016年6月2日
	 * @throws BusinessException 
	 */
	private void validateBigWheelPrize(BigWheelPrizeEntity bigWheelPrizeEntity, Long bigWheelId) throws BusinessException {
		if (StringUtils.isBlank(bigWheelPrizeEntity.getPrizeName()) == true) {
			throw new BusinessException("自定义奖项名称不能为空!");
		}
		if (bigWheelPrizeEntity.getPrizeType()==0) {
			if (bigWheelPrizeEntity.getPrizeScore()==null) {
				throw new BusinessException("获奖积分不能为空!");
			}
		}else {
			if (StringUtils.isBlank(bigWheelPrizeEntity.getPrizeContent()) == true) {
				throw new BusinessException("奖品内容不能为空!");
			}
		}
		if (bigWheelPrizeEntity.getPrizeCount() == null || bigWheelPrizeEntity.getPrizeCount() <= 0) {
			throw new BusinessException("请正确填写奖品数量!!");
		}
		if (bigWheelPrizeEntity.getPrizeRate() == null || bigWheelPrizeEntity.getPrizeRate() < 0) {
			throw new BusinessException("请正确填写中奖概率!!");
		}
		{
			Integer sumPrizeRate = bigWheelPrizeDao.sumPrizeRateByBigWheelId(bigWheelId.longValue());
			if (sumPrizeRate == null) {
				sumPrizeRate = 0;
			}
			// 判断是添加还是更新
			if (bigWheelPrizeEntity.getId() == null) {
				// 添加
				if (sumPrizeRate + bigWheelPrizeEntity.getPrizeRate().longValue() > 100) {
					throw new BusinessException("所有奖项的中奖概率总和不能大于100!!");
				}
			} else {
				// 更新
				BigWheelPrizeEntity bigWheelPrizeEntityTemp = bigWheelPrizeDao.findOne(bigWheelPrizeEntity.getId());
				if (sumPrizeRate.longValue() - bigWheelPrizeEntityTemp.getPrizeRate().longValue() + bigWheelPrizeEntity.getPrizeRate().longValue() > 100) {
					throw new BusinessException("所有奖项的中奖概率总和不能大于100!!");
				}
			}
		}
	}

	/**
	 * 删除奖项
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param imageFile
	 * @param prizeId
	 * @author nibili 2016年6月3日
	 */
	@RequestMapping("/bigwheel/{bigWheelId}/prize/{prizeId}/delete.json")
	public void bigwheelPrizeDelete(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long bigWheelId, @PathVariable Long prizeId) {
		try {
			UserEntity user = RequestUtils.getCurrentAdminUser(req);
			whdService.deleteBigwheelPrize(bigWheelId, prizeId, user.getId());
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, ""));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "删除奖项，异常，稍后再试"));
		}
	}

	/**
	 * 幸运大转盘--编辑奖品页
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @return
	 * @author nibili 2016年6月1日
	 */
	@RequestMapping("/bigwheel/{id}/prize/{prizeId}/edit")
	public String bigwheelPrizeEdit(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long id, @PathVariable Long prizeId) {
		UserEntity userEntity = RequestUtils.getCurrentAdminUser(req);
		BigWheelPrizeEntity bigWheelPrizeEntity = bigWheelPrizeDao.findByIdAndUserId(prizeId, userEntity.getId());
		modelMap.addAttribute("bigWheelId", bigWheelPrizeEntity.getBigWheelId());
		// 回显提交的数据
		modelMap.addAttribute("bigWheelPrize", bigWheelPrizeEntity);
		return "/admin/wei_huo_dong/wei_huo_dong_bigwheel_prize_add";
	}

	/**
	 * 幸运大转盘 --转盘刻度维护
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @param prizeId
	 * @return
	 * @author nibili 2016年6月1日
	 */
	@RequestMapping("/bigwheel/{bigWheelId}/calibrations")
	public String bigwheelCalibrations(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long bigWheelId) {
		// 大转盘id
		modelMap.addAttribute("bigWheelId", bigWheelId);
		UserEntity userEntity = RequestUtils.getCurrentAdminUser(req);
		// 奖项列表
		List<BigWheelPrizeEntity> list = bigWheelPrizeDao.findByBigWheelIdAndUserId(bigWheelId, userEntity.getId());
		modelMap.addAttribute("bigWheelPrizes", list);
		BigWheelInfoEntity bigWheelInfoEntity = bigWheelInfoDao.findOne(bigWheelId);
		if (bigWheelInfoEntity != null) {
			// 大转盘，刻度信息
			modelMap.addAttribute("wheelCalibrations", StringUtils.split(bigWheelInfoEntity.getWheelCalibrations(), ","));
		}
		//
		return "/admin/wei_huo_dong/wei_huo_dong_bigwheel_calibrations";
	}

	/**
	 * 保存转盘刻度
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @param ids
	 *            转盘刻度列表
	 * @return
	 * @author nibili 2016年6月3日
	 */
	@RequestMapping("/bigwheel/{id}/calibrations/save")
	public String bigwheelCalibrationsSave(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long id, String[] ids) {
		UserEntity userEntity = RequestUtils.getCurrentAdminUser(req);
		BigWheelInfoEntity bigWheelInfoEntity = bigWheelInfoDao.findByIdAndUserIdAndDeleteStatus(id, userEntity.getId(), false);
		bigWheelInfoEntity.setWheelCalibrations(StringUtils.join(ids, ","));
		bigWheelInfoDao.save(bigWheelInfoEntity);
		return "redirect:/admin/whd/bigwheel#4001";
	}

	/**
	 * 参与用户信息
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @return
	 * @author nibili 2016年6月3日
	 */
	@RequestMapping("/bigwheel/{bigwheelId}/joinuser")
	public String joinUserInfo(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long bigwheelId) {
		modelMap.addAttribute("bigWheelId", bigwheelId);
		return "/admin/wei_huo_dong/wei_huo_dong_bigwheel_joinuser";
	}

	/**
	 * 参与用户分页列表
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param bigwheelId
	 * @param pageRequestParamDTO
	 * @param isWinning
	 *            是否查询中奖信息
	 * @author nibili 2016年6月3日
	 */
	@RequestMapping("/bigwheel/{bigwheelId}/joinuser/all.json")
	public void joinUserInfoAll(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long bigwheelId, PageRequestParamDTO pageRequestParamDTO,
			Boolean isWinning) {
		try {
			if (isWinning != null && isWinning == true) {
				Page<BigWheelUserPrizeEntity> page = bigWheelUserPrizeDao.findWinnings(bigwheelId, pageRequestParamDTO.buildSpringDataPageRequest());
				ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, PageUtils.getPage(page)));
			} else {
				Page<BigWheelUserPrizeEntity> page = bigWheelUserPrizeDao.findByBigWheelId(bigwheelId, pageRequestParamDTO.buildSpringDataPageRequest());
				ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, PageUtils.getPage(page)));
			}
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "异步获取大转盘奖项信息 ，异常，稍后再试"));
		}
	}
}
