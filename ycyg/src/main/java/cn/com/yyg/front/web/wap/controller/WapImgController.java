/**
 * 
 */
package cn.com.yyg.front.web.wap.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.com.easy.dto.MessageDTO;
import cn.com.easy.exception.BusinessException;
import cn.com.easy.utils.FastJSONUtils;
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.yyg.base.entity.AccessoryEntity;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.base.utils.DateUtil;
import cn.com.yyg.base.utils.FileUploadVO;
import cn.com.yyg.base.utils.UploadFileUtil;
import cn.com.yyg.front.dao.AccessoryDao;
import cn.com.yyg.front.dto.DataDto;
import cn.com.yyg.front.service.common.ImgService;
import cn.com.yyg.front.utils.RequestUtils;

/**
 * 图片上传、访问控制器
 * 
 * @author linwk 2016年10月9日
 * 
 */
@Controller
@RequestMapping("/wap/img")
public class WapImgController {

	/** 图片服务 */
	@Autowired
	private ImgService imgService;

	/** 文件服务类 */
	@Autowired
	private AccessoryDao accessoryDao;

	@Value("#{configProperties['www.domain']}")
	private String domain;

	/**
	 * 读取图片
	 * 
	 * @param response
	 * @param value
	 * @throws Exception
	 */
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public void img(String name, HttpServletResponse response) throws Exception {
		// "image/pjpeg", "image/gif", "image/bmp", "image/x-png";
		response.setContentType("image/gif;charset=utf-8");
		FileCopyUtils.copy(imgService.get(name), response.getOutputStream());
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
	public void uploadFile(HttpServletRequest req, HttpServletResponse res, Long albumId) throws Exception {
		try {
			if (req instanceof MultipartHttpServletRequest) {
				MultipartFile file = ((MultipartHttpServletRequest) req).getFile("file");
				if (file == null) {
					throw new BusinessException("上传内容为空");
				}
				UserEntity user = RequestUtils.getCurrentUser(req);
				// 图片存放路径
				String storageFilePath = "user/space/" + user.getId() + "/" + DateUtil.getNowDateToString("yyyy") + "/" + DateUtil.getNowDateToString("MM") + "/"
						+ DateUtil.getNowDateToString("dd");
				FileUploadVO fileUpload = new FileUploadVO();
				fileUpload.setFile(file);
				fileUpload.setStorageFilePath(storageFilePath);
				fileUpload.setStorageFileName(UUID.randomUUID().toString());
				fileUpload.setUserId(user.getId());
				// 上传图片到硬盘
				AccessoryEntity acc = UploadFileUtil.uploadFile(fileUpload);
				acc.setAlbumId(albumId);
				// 上传图片到数据库
				// accessoryDao.save(acc);
				DataDto dataDto = new DataDto();
				dataDto.setData(domain + "/" + acc.getPath() + "/" + acc.getName());
				// {"data":"\/upload\/images\/share\/20161009054802769072034_src.jpg"}
				ResponseOutputUtils.renderJson(res, FastJSONUtils.toJsonString(dataDto));
			}
		} catch (BusinessException ex) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, ex.getMessage()));
		} catch (Exception e) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "上传图片错误"));
			// {"data":"\/upload\/images\/share\/20161009054802769072034_src.jpg"}
		}

	}
}
