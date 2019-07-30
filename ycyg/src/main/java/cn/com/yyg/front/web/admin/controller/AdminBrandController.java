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
import cn.com.yyg.base.entity.BrandEntity;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.front.dao.BrandDao;
import cn.com.yyg.front.utils.RequestUtils;

/**
 * 系统品牌管理
 * 
 * @author linwk 2016年8月26日
 * 
 */
@Controller
@RequestMapping("/admin/brand")
public class AdminBrandController {

	private Logger logger = LoggerFactory.getLogger(AdminBrandController.class);

	/** 品牌dao */
	@Autowired
	private BrandDao brandDao;

	/**
	 * 品牌管理首页
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author linwk 2016年8月26日
	 */
	@RequestMapping
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		return "/admin/brand/brand";
	}

	/**
	 * 分页获取品牌信息
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param pageReqeustParam
	 * @param type
	 * @author linwk 2016年8月26日
	 */
	@RequestMapping("/all.json")
	public void all(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, PageRequestParamDTO pageReqeustParam) throws BusinessException {
		try {
			Page<BrandEntity> page = new Page<BrandEntity>();
			List<BrandEntity> list = brandDao.findAll();
			page.setResult(list);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, page));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "分页获取品牌信息异常"));
		}
	}

	/**
	 * 异步删除品牌
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @author linwk 2016年8月26日
	 */
	@RequestMapping("/delete.json")
	public void delete(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, Long id) {
		try {
			if (id == null) {
				throw new BusinessException("id不能为空");
			}
			BrandEntity brandEntity = brandDao.findOne(id);
			if (brandEntity == null) {
				throw new BusinessException("找不到该对象");
			}
			// 如果有子品牌不能删除
			if (brandDao.countByParentId(id) > 0) {
				throw new BusinessException("当前品牌下有子品牌，不能删除");
			}
			UserEntity userEntity = RequestUtils.getCurrentAdminUser(req);
			brandEntity.setLastModifyBy(userEntity.getUserName());
			brandEntity.setLastModifyById(userEntity.getId());
			brandEntity.setDeleteStatus(true);
			brandDao.save(brandEntity);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, ""));
		} catch (BusinessException ex) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, ex.getLocalizedMessage()));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "删除品牌异常"));
		}
	}

	/**
	 * 异步保存品牌
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param brandEntity
	 * @author linwk 2016年8月26日
	 */
	@RequestMapping("/save.json")
	public void save(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, BrandEntity brandEntity) {
		try {
			UserEntity userEntity = RequestUtils.getCurrentAdminUser(req);
			BrandEntity brandEntityTemp = null;
			if (brandEntity.getId() == null) {
				// 新增
				brandEntityTemp = brandEntity;
				brandEntityTemp.setLastModifyBy(userEntity.getUserName());
				brandEntityTemp.setLastModifyById(userEntity.getId());
			} else {
				// 更新
				brandEntityTemp = brandDao.findOne(brandEntity.getId());
				brandEntityTemp.setParentId(brandEntity.getParentId());
				brandEntityTemp.setSeqNo(brandEntity.getSeqNo());
				brandEntityTemp.setLastModifyBy(userEntity.getUserName());
				brandEntityTemp.setLastModifyById(userEntity.getId());
				brandEntityTemp.setBrandName(brandEntity.getBrandName());
				brandEntityTemp.setIconFrontCode(brandEntity.getIconFrontCode());
			}
			brandDao.save(brandEntityTemp);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, ""));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "异步保存品牌异常"));
		}
	}

}
