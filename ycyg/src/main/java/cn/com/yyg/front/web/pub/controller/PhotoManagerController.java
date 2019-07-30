package cn.com.yyg.front.web.pub.controller;

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
import cn.com.easy.exception.BusinessException;
import cn.com.easy.utils.PageRequest;
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.yyg.base.entity.AccessoryEntity;
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
 * 图片与相册管理
 * 
 * @author lvzf 2016年6月14日
 * 
 */
@Controller
@RequestMapping("/pub/photo")
public class PhotoManagerController {
	private Logger logger = LoggerFactory.getLogger(PhotoManagerController.class);
	/** 文件服务类 */
	@Autowired
	private AccessoryDao accessoryDao;
	/** 相册*/
	@Autowired
	private UserPhotoAblumDao userPhotoAblumDao;
	@Autowired
	private ProductService productService;
	@RequestMapping("")
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, PageRequest pageRequest) {
		return "/pub/photo/photo_list";
	}

	/**
	 * 编辑器选择图片
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author lvzf 2016年6月14日
	 */
	@RequestMapping("/select")
	public String selectImg4editor(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, PageRequest pageRequest, Long albumId, Integer type) {
		UserEntity user = RequestUtils.getCurrentAdminUser(req); 
		if (user == null) {
			user =RequestUtils.getCurrentUser(req);
		}
		if (user != null) {
			//我的相册
			List<UserPhotoAblumEntity> ablums=productService.getAblumAllChilds(user.getId(),UserPhotoAblumEntity.ROOT_ID, 0);
			modelMap.addAttribute("ablums", ablums); 
			pageRequest.setPageSize(12);
			org.springframework.data.domain.PageRequest pg = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
			org.springframework.data.domain.Page<AccessoryEntity> list = null;
			if (albumId == null) {
				list = accessoryDao.findListByUserId(user.getId(), pg);
			} else {
				list = accessoryDao.findListByUserIdAndAlbumId(user.getId(), albumId, pg);
			}
			cn.com.easy.utils.Page<AccessoryEntity> page = PageUtils.getPage(list);
			modelMap.addAttribute("page", page);
		}
		if (type != null && (type == 1 || type == 2)) {
			return "/pub/photo/photo_select_more";
		} else {
			return "/pub/photo/photo_select";
		}

	}

	/**
	 * 进入插入图片
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/addPhotoPre")
	public String addPhotoPre(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, Integer type) {
		modelMap.addAttribute("type", type);
		return "/pub/photo/photo_add";
	}

	/**
	 * 上传文件
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
			if (user == null) {
				user = RequestUtils.getCurrentUser(req);
			}
			if (user == null) {
				throw new BusinessException("还未登录系统");
			}
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
			//ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, "上传成功"));
			ResponseOutputUtils.renderHtml(res, "上传成功", "encoding:UTF-8");
		} catch (BusinessException ex) {
			//ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, ex.getMessage()));
			ResponseOutputUtils.renderHtml(res,ex.getMessage(), "encoding:UTF-8");
		} catch (Exception e) {
			logger.error("上传图片错误：", e);
			//ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "上传图片错误"));
			ResponseOutputUtils.renderHtml(res,"上传图片错误", "encoding:UTF-8");
		}
	} 
}
