package cn.com.yyg.front.web.admin.controller;

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
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.easy.dto.MessageDTO;
import cn.com.easy.dto.PageRequestParamDTO;
import cn.com.easy.exception.BusinessException;
import cn.com.easy.utils.PageUtils;
import cn.com.easy.utils.RequestUtils;
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.yyg.base.em.UserScoreTypeEnum;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.base.entity.YgShareEntity;
import cn.com.yyg.front.dao.YgShareDao;
import cn.com.yyg.front.service.ScoreService;
import cn.com.yyg.front.service.UserService;

/**
 * 云购晒单管理
 * 
 * @author linwk 2016年9月12日
 * 
 */
@Controller
@RequestMapping("/admin/ygshare")
public class AdminYgShareController {

	private Logger logger = LoggerFactory.getLogger(AdminYgShareController.class);

	/** 晒单dao */
	@Autowired
	private YgShareDao ygShareDao;
	@Autowired
    private UserService userService;
	/**积分 */
	@Autowired
	private ScoreService scoreService;
	/**
	 * 晒单管理首页
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author linwk 2016年9月12日
	 */
	// @RequestMapping()
	@RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		return "/admin/ygshare/share_list";
	}

	@RequestMapping("/edit")
	public String zengyuan(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		return "/admin/share/share_edit";
	}

	/**
	 * 分页获取晒单信息
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param pageReqeustParam
	 * @author linwk 2016年9月12日
	 */
	@RequestMapping("/all.json")
	public void all(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, PageRequestParamDTO pageReqeustParam) {
		try {
			Page<YgShareEntity> springPage = null;
			// 关键词的
			if (StringUtils.isBlank(pageReqeustParam.getSearchText())) {
				// 所有晒单
				pageReqeustParam.setSortName("id");
				pageReqeustParam.setSortOrder("desc");
				springPage = ygShareDao.findAll(pageReqeustParam.buildSpringDataPageRequest());
			} else {
				// 加搜索条件
				springPage = ygShareDao.findByTitle("%" + pageReqeustParam.getSearchText() + "%", pageReqeustParam.buildSpringDataPageRequest());
			}
			cn.com.easy.utils.Page<YgShareEntity> page = PageUtils.getPage(springPage);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, page));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "分页获取晒单信息异常"));
		}
	}

	/**
	 * 审核
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param shareEntity
	 * @author linwk 2016年9月12日
	 */
	@RequestMapping("/save.json")
	public void save(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, YgShareEntity shareEntity) {
		try {
			if (shareEntity.getId() != null) {
				// 修改
				YgShareEntity tempEntity = ygShareDao.findOne(shareEntity.getId());
				tempEntity.setAudit(!tempEntity.isAudit());
				if (StringUtils.isNoneBlank(shareEntity.getTitle())) {
					tempEntity.setTitle(shareEntity.getTitle());
				}
				if (StringUtils.isNoneBlank(shareEntity.getContent())) {
					tempEntity.setContent(shareEntity.getContent());
				}
				ygShareDao.save(tempEntity);
				UserEntity user=userService.findById(tempEntity.getUserId());
				//添加积分
				scoreService.addScore(user, UserScoreTypeEnum.Share, "晒单id"+tempEntity.getId());
				ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, ""));
			} else {
				// 保存
				ygShareDao.save(shareEntity);
				ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, ""));
			}
		} catch (BusinessException ex) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, ex.getLocalizedMessage()));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "审核晒单信息异常"));
		}
	}

	/**
	 * 推荐晒单
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @author linwk 2016年9月12日
	 */
	@RequestMapping("/recomment.json")
	public void recomment(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, Long id) {
		try {
			UserEntity user = RequestUtils.getCurrentUser(req);
			YgShareEntity tEntity = ygShareDao.findOne(id);
			tEntity.setRecommend(!tEntity.isRecommend());
			tEntity.setLastModifyBy(user.getUserName());
			ygShareDao.save(tEntity);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, ""));
		} catch (Exception ex) {
			logger.error("推荐晒单错误", ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "推荐晒单错误"));
		}
	}

}
