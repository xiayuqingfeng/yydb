package cn.com.yyg.front.web.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.easy.dto.MessageDTO;
import cn.com.easy.exception.BusinessException;
import cn.com.easy.utils.PageRequest;
import cn.com.easy.utils.PageRequestUtils;
import cn.com.easy.utils.PageUtils;
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.yyg.base.entity.AdvItemEntity;
import cn.com.yyg.base.entity.SysDictEntity;
import cn.com.yyg.front.dao.AccessoryDao;
import cn.com.yyg.front.dao.AdvItemDao;
import cn.com.yyg.front.service.common.CommonService;

import com.google.common.collect.Table;

/**
 * advItem
 * 
 * @author linwk 2016年8月19日
 * 
 */
@Controller
@RequestMapping("/admin/adv")
public class AdminAdvController {
	private Logger logger = LoggerFactory.getLogger(AdminAdvController.class);
	   /**公共字典*/
	  @Autowired
	  private CommonService commonService;
	/** 广告dao */
	@Autowired
	private AdvItemDao advItemDao;
	/** 文件 */
	@Autowired
	private AccessoryDao accessoryDao;
	
	@RequestMapping
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		Table<Long, Long, SysDictEntity> dicts=commonService.getAllDicts();
		//广告位列表
		modelMap.addAttribute("advs", dicts.column(SysDictEntity.ROOT_ADV).values());
		return "/admin/adv/adv";
	}
	/**
	 * 广告管理列表
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param pageRequest
	 * @return
	 * @author lvzf 2016年6月1日
	 */
	@RequestMapping("/{advId}")
	public String list(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long advId, PageRequest pageRequest) {
		Table<Long, Long, SysDictEntity> dicts=commonService.getAllDicts();
		modelMap.addAttribute("adv",dicts.get(advId, SysDictEntity.ROOT_ADV));
		pageRequest.setOrderBy("seqNo");
		pageRequest.setOrderDir("desc");
		org.springframework.data.domain.PageRequest pg = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		org.springframework.data.domain.Page<AdvItemEntity> list = advItemDao.findByAdvId(advId,pg);
		cn.com.easy.utils.Page<AdvItemEntity> page = PageUtils.getPage(list);
		modelMap.addAttribute("page", page);
		return "/admin/adv/advItem";
	}

	/**
	 * 添加广告
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author lvzf 2016年6月1日
	 */
	@RequestMapping("/addItem/{advId}")
	public String itemContentEdit(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long advId) {
		Table<Long, Long, SysDictEntity> dicts=commonService.getAllDicts();
		modelMap.addAttribute("adv",dicts.get(advId, SysDictEntity.ROOT_ADV));
		AdvItemEntity advItem =new AdvItemEntity();
		advItem.setSeqNo(0);
		modelMap.addAttribute("advItem", advItem);
		return "/admin/adv/advItem_edit";
	}

	/**
	 * 编辑广告
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @return
	 * @author linwk 2016年8月19日
	 */
	@RequestMapping("/editItem/{advId}/{id}")
	public String itemContentEdit(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long advId, @PathVariable Long id) {
		Table<Long, Long, SysDictEntity> dicts=commonService.getAllDicts();
		modelMap.addAttribute("adv",dicts.get(advId, SysDictEntity.ROOT_ADV));
		AdvItemEntity advItem = advItemDao.findOne(id);
		modelMap.addAttribute("advItem", advItem);
		return "/admin/adv/advItem_edit";
	}

	/**
	 * 保存advItem
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param photoFile
	 * @param advItem
	 * @author linwk 2016年8月19日
	 */
	@RequestMapping("/saveItem")
	public void itemContentEdit(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res,AdvItemEntity advItem) {
		try {
			AdvItemEntity advItemEntity = null;
			if (advItem.getId() != null) {
				advItemEntity = advItemDao.findOne(advItem.getId());
			} else {
				advItemEntity = new AdvItemEntity();
			}
			advItemEntity.setLinkUrl(advItem.getLinkUrl());
			advItemEntity.setTitle(advItem.getTitle());
			advItemEntity.setSeqNo(advItem.getSeqNo());
			advItemEntity.setAdvId(advItem.getAdvId());
			advItemEntity.setPhotoPath(advItem.getPhotoPath());
/*			// 上传图片
			if (photoFile != null) {
				String storageFilePath = "admin/advItem/index";
				FileUploadVO fileUpload = new FileUploadVO();
				fileUpload.setFile(photoFile);
				fileUpload.setStorageFilePath(storageFilePath);
				fileUpload.setStorageFileName(UUID.randomUUID().toString());
				// 上传图片到硬盘
				AccessoryEntity acc = UploadFileUtil.uploadFile(fileUpload);
				 
				advItemEntity.setPhotoPath(acc.getPath() + "/" + acc.getName());
			}*/
			advItemDao.save(advItemEntity);

			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, "保存成功"));
		} catch (Exception ex) {
			logger.error("保存advItem错误：", ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "保存advItem错误"));
		}
	}

	/**
	 * 删除advItem
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @author linwk 2016年8月19日
	 */
	@RequestMapping("/deleteItem/{id}")
	public void itemDelete(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long id) {
		try {
			AdvItemEntity advItemEntity = advItemDao.findOne(id);
			if (advItemEntity == null) {
				throw new BusinessException("ID无效");
			}
			// 删除
			advItemDao.delete(id);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, "删除成功"));
		} catch (BusinessException ex) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, ex.getMessage()));
		} catch (Exception ex) {
			logger.error("advItem删除错误：", ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "advItem删除错误"));
		}
	}
}
