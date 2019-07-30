package cn.com.yyg.front.web.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.easy.dto.MessageDTO;
import cn.com.easy.dto.PageRequestParamDTO;
import cn.com.easy.exception.BusinessException;
import cn.com.easy.utils.Page;
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.yyg.base.entity.AreaEntity;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.front.dao.AreaDao;
import cn.com.yyg.front.utils.RequestUtils;

/**
 * 系统地区字典管理
 * 
 * @author linwk 2016年8月9日
 * 
 */
@Controller
@RequestMapping("/admin/area")
public class AdminAreaController {

	private Logger logger = LoggerFactory.getLogger(AdminAreaController.class);

	/** 地区dao */
	@Autowired
	private AreaDao areaDao;

	/**
	 * 地区管理首页
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author linwk 2016年8月9日
	 */
	@RequestMapping
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		return "/admin/area/area";
	}

	/**
	 * 分页获取地区信息
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param pageReqeustParam
	 * @param type
	 * @author linwk 2016年8月9日
	 */
	@RequestMapping("/all.json")
	public void all(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, PageRequestParamDTO pageReqeustParam) {
		try {
			Page<AreaEntity> page = new Page<AreaEntity>();
			List<AreaEntity> list = areaDao.findAll();
			page.setResult(list);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, page));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "分页获取地区信息异常"));
		}
	}

	/**
	 * 异步删除地区
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @author linwk 2016年8月9日
	 */
	@RequestMapping("/delete.json")
	public void delete(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, Long id) {
		try {
			if (id == null) {
				throw new BusinessException("id不能为空");
			}
			AreaEntity areaEntity = areaDao.findOne(id);
			if (areaEntity == null) {
				throw new BusinessException("找不到该对象");
			}
			// 如果有子地区不能删除
			if (areaDao.countByParentId(id) > 0) {
				throw new BusinessException("当前地区下有子地区，不能删除");
			}
			UserEntity userEntity = RequestUtils.getCurrentAdminUser(req);
			areaEntity.setLastModifyBy(userEntity.getUserName());
			areaEntity.setLastModifyById(userEntity.getId());
			areaEntity.setDeleteStatus(true);
			areaDao.save(areaEntity);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, ""));
		} catch (BusinessException ex) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, ex.getLocalizedMessage()));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "删除地区异常"));
		}
	}

	/**
	 * 异步保存地区
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param areaEntity
	 * @author linwk 2016年8月9日
	 */
	@RequestMapping("/save.json")
	public void save(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, AreaEntity areaEntity) {
		try {
			UserEntity userEntity = RequestUtils.getCurrentAdminUser(req);
			AreaEntity areaEntityTemp = null;
			if (areaEntity.getId() == null) {
				// 新增
				areaEntityTemp = areaEntity;
				areaEntityTemp.setLastModifyBy(userEntity.getUserName());
				areaEntityTemp.setLastModifyById(userEntity.getId());
			} else {
				// 更新
				areaEntityTemp = areaDao.findOne(areaEntity.getId());
				areaEntityTemp.setParentId(areaEntity.getParentId());
				areaEntityTemp.setSeqNo(areaEntity.getSeqNo());
				areaEntityTemp.setLastModifyBy(userEntity.getUserName());
				areaEntityTemp.setLastModifyById(userEntity.getId());
			}
			areaDao.save(areaEntityTemp);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, ""));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "异步保存地区异常"));
		}
	}

}
