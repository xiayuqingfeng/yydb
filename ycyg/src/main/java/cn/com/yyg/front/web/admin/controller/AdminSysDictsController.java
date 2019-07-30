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
import cn.com.yyg.base.entity.SysDictEntity;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.front.dao.SysDictDao;
import cn.com.yyg.front.utils.RequestUtils;

/**
 * 系统数据字典管理
 * 
 * @author linwk 2016年8月9日
 * 
 */
@Controller
@RequestMapping("/admin/sysdicts")
public class AdminSysDictsController {

	private Logger logger = LoggerFactory.getLogger(AdminSysDictsController.class);

	/** 数据dao */
	@Autowired
	private SysDictDao sysDictDao;

	/**
	 * 数据管理首页
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author linwk 2016年8月9日
	 */
	@RequestMapping
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		return "/admin/sysdicts/sysdicts";
	}

	/**
	 * 分页获取数据信息
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
			Page<SysDictEntity> page = new Page<SysDictEntity>();
			List<SysDictEntity> list = sysDictDao.findAll();
			page.setResult(list);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, page));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "分页获取数据信息异常"));
		}
	}

	/**
	 * 异步删除数据
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
			SysDictEntity sysDictEntity = sysDictDao.findOne(id);
			if (sysDictEntity == null) {
				throw new BusinessException("找不到该对象");
			}
			// 如果有子数据不能删除
			if (sysDictDao.countByDeleteStatusAndParentId(false,id) > 0) {
				throw new BusinessException("当前数据下有子数据，不能删除");
			}
			UserEntity userEntity = RequestUtils.getCurrentAdminUser(req);
			sysDictEntity.setLastModifyBy(userEntity.getUserName());
			sysDictEntity.setLastModifyById(userEntity.getId());
			sysDictEntity.setDeleteStatus(true);
			sysDictDao.save(sysDictEntity);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, ""));
		} catch (BusinessException ex) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, ex.getLocalizedMessage()));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "删除数据异常"));
		}
	}

	/**
	 * 异步保存数据
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param sysDictEntity
	 * @author linwk 2016年8月9日
	 */
	@RequestMapping("/save.json")
	public void save(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, SysDictEntity sysDictEntity) {
		try {
			UserEntity userEntity = RequestUtils.getCurrentAdminUser(req);
			SysDictEntity sysDictEntityTemp = null;
			if (sysDictEntity.getId() == null) {
				// 新增
				sysDictEntityTemp = sysDictEntity;
				sysDictEntityTemp.setLastModifyBy(userEntity.getUserName());
				sysDictEntityTemp.setLastModifyById(userEntity.getId());
				if(sysDictEntity.getParentId()==null){
					sysDictEntityTemp.setParentId(SysDictEntity.ROOT_ID);
				}else{
					sysDictEntityTemp.setParentId(sysDictEntity.getParentId());
				}
				sysDictEntityTemp.setDisplay(true);
			} else {
				// 更新
				sysDictEntityTemp = sysDictDao.findOne(sysDictEntity.getId());
				sysDictEntityTemp.setName(sysDictEntity.getName());
				sysDictEntityTemp.setParentId(sysDictEntity.getParentId());
				sysDictEntityTemp.setSeqNo(sysDictEntity.getSeqNo());
				sysDictEntityTemp.setLastModifyBy(userEntity.getUserName());
				sysDictEntityTemp.setLastModifyById(userEntity.getId());
				sysDictEntityTemp.setIcons(sysDictEntity.getIcons());
			}
			sysDictDao.save(sysDictEntityTemp);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, ""));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "异步保存数据异常"));
		}
	}

}
