package cn.com.yyg.front.web.admin.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cn.com.easy.dto.MessageDTO;
import cn.com.easy.dto.PageRequestParamDTO;
import cn.com.easy.exception.BusinessException;
import cn.com.easy.utils.Page;
import cn.com.easy.utils.PageRequest;
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.yyg.base.entity.AccessoryEntity;
import cn.com.yyg.base.entity.SysDictEntity;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.base.entity.UserPhotoAblumEntity;
import cn.com.yyg.base.utils.DateUtil;
import cn.com.yyg.base.utils.FileUploadVO;
import cn.com.yyg.base.utils.PageRequestUtils;
import cn.com.yyg.base.utils.PageUtils;
import cn.com.yyg.base.utils.UploadFileUtil;
import cn.com.yyg.front.dao.AccessoryDao;
import cn.com.yyg.front.dao.UserPhotoAblumDao;
import cn.com.yyg.front.service.ProductService;
import cn.com.yyg.front.utils.RequestUtils;

/**
 * 后台管理员进行图片与相册管理
 * 
 * @author lvzf 2016年6月14日
 * 
 */
@Controller
@RequestMapping("/admin/photo")
public class AdminPhotoController {
	private Logger logger = LoggerFactory.getLogger(AdminPhotoController.class);
	/** 文件服务类 */
	@Autowired
	private AccessoryDao accessoryDao;
	/** 相册*/
	@Autowired
	private UserPhotoAblumDao userPhotoAblumDao;
	@Autowired
	private ProductService productService;
	/**
	 * 我的图库
	 *@param modelMap
	 *@param req
	 *@param res
	 *@param pageRequest
	 *@return
	 * @author lvzf 2016年9月12日
	 */
	@RequestMapping("")
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, PageRequest pageRequest, Long albumId) {
		UserEntity user = RequestUtils.getCurrentAdminUser(req);
		//我的相册
		List<UserPhotoAblumEntity> ablums=productService.getAblumAllChilds(user.getId(),UserPhotoAblumEntity.ROOT_ID, 0);
		modelMap.addAttribute("ablums", ablums); 
		org.springframework.data.domain.PageRequest pg = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		org.springframework.data.domain.Page<AccessoryEntity> list = null;
		if (albumId == null) {
			list = accessoryDao.findListByUserId(user.getId(), pg);
		} else {
			list = accessoryDao.findListByUserIdAndAlbumId(user.getId(), albumId, pg);
		}
		cn.com.easy.utils.Page<AccessoryEntity> page = PageUtils.getPage(list);
		modelMap.addAttribute("page", page); 
		return "/admin/photo/photo_list";
	} 
	@RequestMapping("/select")
	public String select(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, PageRequest pageRequest, Long albumId) {
		this.index(modelMap, req, res, pageRequest, albumId);
		return "/admin/photo/photo_select";
	} 
	/**
	 * 上传图片
	 * 
	 * @param file
	 * @param req
	 * @param res
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public void uploadFile(@RequestParam(value = "file") MultipartFile[] file, HttpServletRequest req, HttpServletResponse res, Long albumId) throws Exception {
		try {
			UserEntity user = RequestUtils.getCurrentAdminUser(req); 
			// 商品图片存放路径
			String storageFilePath = "user/space/" + user.getId() + "/" + DateUtil.getNowDateToString("yyyy") + "/" + DateUtil.getNowDateToString("MM") + "/"
					+ DateUtil.getNowDateToString("dd");
			for (int i = 0; i < file.length; i++) {
				FileUploadVO fileUpload = new FileUploadVO();
				fileUpload.setFile(file[i]);
				fileUpload.setStorageFilePath(storageFilePath);
				fileUpload.setStorageFileName(UUID.randomUUID().toString());
				fileUpload.setUserId(user.getId());
				// 上传图片到硬盘
				AccessoryEntity acc = UploadFileUtil.uploadFile(fileUpload);
				acc.setAlbumId(albumId);
				// 上传图片到数据库
				accessoryDao.save(acc);

			}
			ResponseOutputUtils.renderHtml(res, "上传成功", "encoding:UTF-8");

			//.renderJson(res, MessageDTO.newInstance("", true, "上传成功"));
		} catch (BusinessException ex) {
			//ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, ex.getMessage()));
			ResponseOutputUtils.renderHtml(res,ex.getMessage(), "encoding:UTF-8");
		} catch (Exception e) {
			logger.error("上传图片错误：", e);
			//ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "上传图片错误"));
			ResponseOutputUtils.renderHtml(res,"上传图片错误", "encoding:UTF-8");
		}
	}
	/**
	 * 修改图片备注
	 *@param modelMap
	 *@param req
	 *@param res
	 *@param id
	 *@param remark
	 * @author lvzf 2016年10月17日
	 */
	@RequestMapping("/updateRemark.json")
	public void updateRemark(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, Long id,String remark) {
		try {
			AccessoryEntity 	photo = accessoryDao.findOne(id);
			UserEntity user = RequestUtils.getCurrentAdminUser(req); 
			if(user.getId().equals(photo.getUserId())){
				photo.setInfo(remark);
				accessoryDao.save(photo);
			}	
		ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, "修改图片成功"));
		} catch (Exception e) {
			logger.error("修改图片备注错误：", e);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "修改图片备注图片错误"));
		}
	}
	/**
	 * 删除图片
	 *@param modelMap
	 *@param req
	 *@param res
	 *@param ids
	 * @author lvzf 2016年9月19日
	 */
	@RequestMapping("/delete.json")
	public void photo_delete(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, Long ids[]) {
		try {
			for(Long id:ids){				 
				AccessoryEntity 	photo = accessoryDao.findOne(id);
				if (photo == null) {
					throw new BusinessException("找不到该对象");
				} 			 
				UserEntity userEntity = RequestUtils.getCurrentAdminUser(req);
			    if(!userEntity.getId().equals(photo.getUserId())){
					throw new BusinessException("非当前用户的相册");
			    }
			    UploadFileUtil.deleteFile(photo);
			    accessoryDao.delete(photo);
			}
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, ""));
		} catch (BusinessException ex) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, ex.getLocalizedMessage()));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "删除相册异常"));
		}
	}
	/**
	 * 我的相册
	 *@param modelMap
	 *@param req
	 *@param res
	 *@return
	 * @author lvzf 2016年9月19日
	 */
	@RequestMapping("/ablum")
	public String ablum(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		return "/admin/photo/photo_ablum";
	}
	/**
	 * 我的相册
	 *@param modelMap
	 *@param req
	 *@param res
	 *@param pageReqeustParam
	 * @author lvzf 2016年9月19日
	 */
	@RequestMapping("/ablum/list.json")
	public void ablum_list(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, PageRequestParamDTO pageReqeustParam) {
		try {
			UserEntity user = RequestUtils.getCurrentAdminUser(req);
			Page<UserPhotoAblumEntity> page = new Page<UserPhotoAblumEntity>();
			List<UserPhotoAblumEntity> list = userPhotoAblumDao.findListByUserId(user.getId());
			for(UserPhotoAblumEntity ablum:list){
				UserPhotoAblumEntity p=userPhotoAblumDao.findOne(ablum.getParentId());
				if(p!=null){
					ablum.setParentName(p.getName());
				}else{
					ablum.setParentName("默认相册");
				}
			}
			page.setResult(list);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, page));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "分页获取相册异常"));
		}
	}
	/**
	 * 删除相册
	 *@param modelMap
	 *@param req
	 *@param res
	 *@param ids
	 * @author lvzf 2016年9月19日
	 */
	@RequestMapping("/ablum/delete.json")
	public void ablum_delete(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, Long id) {
		try { 
			if (id == null) {
				throw new BusinessException("id不能为空");
			}
			UserPhotoAblumEntity 	ablum = userPhotoAblumDao.findOne(id);
			if (ablum == null) {
				throw new BusinessException("找不到该对象");
			} 
			// 如果有子数据不能删除
			if (userPhotoAblumDao.countByDeleteStatusAndParentId(false,id) > 0) {
				throw new BusinessException("当前数据下有子数据，不能删除");
			}
			UserEntity userEntity = RequestUtils.getCurrentAdminUser(req);
		    if(!userEntity.getId().equals(ablum.getUserId())){
				throw new BusinessException("非当前用户的相册");
		    }
			userPhotoAblumDao.delete(ablum); 
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, ""));
		} catch (BusinessException ex) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, ex.getLocalizedMessage()));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "删除相册异常"));
		}
	}
	/**
	 * 相册保存
	 *@param modelMap
	 *@param req
	 *@param res
	 *@param ablumEntity
	 * @author lvzf 2016年9月19日
	 */
	@RequestMapping("/ablum/save")
	public void ablum_save(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res,UserPhotoAblumEntity ablumEntity ) {
		try {
			UserEntity userEntity = RequestUtils.getCurrentAdminUser(req);
			UserPhotoAblumEntity ablum = null;
			if (ablumEntity.getId() == null) {
				// 新增
				ablum = ablumEntity;
				ablum.setLastModifyBy(userEntity.getUserName());
				ablum.setLastModifyById(userEntity.getId());
				ablum.setUserId(userEntity.getId());
				if(ablum.getParentId()==null){
					ablum.setParentId(SysDictEntity.ROOT_ID);
				}else{
					ablum.setParentId(ablumEntity.getParentId());
				} 
			} else {
				// 更新
				ablum = userPhotoAblumDao.findOne(ablumEntity.getId());
				ablum.setName(ablumEntity.getName());
				ablum.setParentId(ablumEntity.getParentId()); 
				ablum.setLastModifyBy(userEntity.getUserName());
				ablum.setLastModifyById(userEntity.getId()); 
			}
			userPhotoAblumDao.save(ablum);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, ""));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "异步保存相册异常"));
		}
	}
}
