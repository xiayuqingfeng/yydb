package cn.com.yyg.base.utils;

import java.awt.Container;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.com.easy.exception.BusinessException;
import cn.com.easy.utils.SpringContextHolder;
import cn.com.yyg.base.entity.AccessoryEntity;
import cn.com.yyg.front.dao.AccessoryDao;

/**
 * 文件工具类
 * 
 * @author lvzf 2016年1月5日 下午4:18:03
 */

public class UploadFileUtil {
	private static Logger logger = LoggerFactory.getLogger(UploadFileUtil.class);
	/** 文件表dao */
	private static AccessoryDao accessoryDao = SpringContextHolder.getBean(AccessoryDao.class);
	/** * 文件根路径 */
	private static String rootPath = SpringContextHolder.getBean(UploadFileUtilHelper.class).getRootPathTemp();

	/**
	 * 上传文件：已经保存到硬盘，等待把返回的对象保存到数据库
	 * 
	 * @param file
	 *            文件对象
	 * @param storageFilePath
	 *            存储文件的中间块的相对路径，如：/home/data/statis/upload/{storageFilePath}/{
	 *            storageFileName}
	 * @param storageFileName
	 *            存储文件的文件名，不包括后缀名，如：/home/data/statis/upload/{storageFilePath}/{
	 *            storageFileName}
	 * @param userId
	 *            用户id
	 * @return
	 * @throws BusinessException
	 * @author nibili 2016年1月5日
	 */
	public static AccessoryEntity uploadFile(MultipartFile file, String storageFilePath, String storageFileName, Long userId) throws BusinessException {
		return UploadFileUtil.uploadFile(new FileUploadVO(file, storageFilePath, storageFileName, userId));
	}

	/**
	 * 删除硬盘文件和数据记录
	 * 
	 * @param accessoryId
	 * @author nibili 2016年5月26日
	 */
	public static void deleteFile(long accessoryId) {
		AccessoryEntity accessory = accessoryDao.findOne(accessoryId);
		// 在硬盘删除原文件
		if (accessory != null) {
			String root = rootPath;
			try {
				String fullPath = root + File.separator + accessory.getPath() + File.separator + accessory.getName();
				File f = new File(fullPath);
				if (f.exists()) {
					logger.info("删除硬盘文件:" + fullPath);
					FileUtils.forceDelete(f);
				}
			} catch (Throwable e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 删除硬盘文件和数据记录
	 * 
	 * @param accessoryId
	 * @author nibili 2016年5月26日
	 */
	public static void deleteFileAndAccessory(long accessoryId) {
		AccessoryEntity accessory = accessoryDao.findOne(accessoryId);
		// 在硬盘删除原文件
		if (accessory != null) {
			String root = rootPath;
			try {
				String fullPath = root + File.separator + accessory.getPath() + File.separator + accessory.getName();
				File f = new File(fullPath);
				if (f.exists()) {
					logger.info("删除硬盘文件:" + fullPath);
					FileUtils.forceDelete(f);
				}
				// 删除数据库记录
				if (accessory.getId() != null) {
					accessoryDao.delete(accessoryId);
				}
			} catch (Throwable e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 删除硬盘文件
	 * 
	 * @param accessory
	 */
	public static void deleteFileAndAccessory(AccessoryEntity accessory) {

		String root = rootPath;
		// 在硬盘删除原文件
		if (accessory != null) {
			try {
				String fullPath = root + File.separator + accessory.getPath() + File.separator + accessory.getName();
				File f = new File(fullPath);
				if (f.exists()) {
					logger.info("删除硬盘文件:" + fullPath);
					FileUtils.forceDelete(f);
				}
				// 删除数据库记录
				if (accessory.getId() != null) {
					accessoryDao.delete(accessory.getId());
				}
			} catch (Throwable e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 删除硬盘文件
	 * 
	 * @param accessory
	 */
	public static void deleteFile(AccessoryEntity accessory) {

		String root = rootPath;
		// 在硬盘删除原文件
		if (accessory != null) {
			try {
				String fullPath = root + File.separator + accessory.getPath() + File.separator + accessory.getName();
				File f = new File(fullPath);
				if (f.exists()) {
					logger.info("删除硬盘文件:" + fullPath);
					FileUtils.forceDelete(f);
				}
			} catch (Throwable e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	/**
	 * 删除硬盘文件
	 *@param path
	 * @author lvzf 2016年8月19日
	 */
	public static void deleteFileByPath(String path) {
		String root = rootPath;
		// 在硬盘删除原文件
		if (path != null) {
			try {
				String fullPath = root + File.separator + path;
				File f = new File(fullPath);
				if (f.exists()) {
					logger.info("删除硬盘文件:" + fullPath);
					FileUtils.forceDelete(f);
				}
			} catch (Throwable e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	/**
	 * 上传文件：已经保存到硬盘，等待把返回的对象保存到数据库
	 * 
	 * @param fileUploadVO
	 * @param accessory
	 *            原文件
	 * @return
	 * @throws BusinessException
	 */
	public static AccessoryEntity uploadFile(FileUploadVO fileUploadVO) throws BusinessException {

		if (fileUploadVO == null) {
			throw new BusinessException("上传对象为空");
		}
		if (fileUploadVO.getStorageFilePath() == null) {
			throw new BusinessException("存储文件路径为空");
		}
		if (fileUploadVO.getStorageFileName() == null) {
			throw new BusinessException("存储文件名为空");
		}
		if (fileUploadVO.getFile() == null) {
			throw new BusinessException("文件为空");
		}
		try {
			String root = rootPath;

			// 文件大小1024*1024*10 => 10M
			byte[] data = fileUploadVO.getFile().getBytes();
			if (data == null || data.length == 0) {
				throw new BusinessException("文件为空");
			}
			if (data.length > 1024 * 1024 * 20) {
				throw new BusinessException("文件大小不能超过20M");
			}
			// 获取文件扩展名
			String origName = fileUploadVO.getFile().getOriginalFilename();
			String extend = origName.substring(origName.lastIndexOf(".") + 1).toLowerCase();
			// 文件存储路径格式：/ROOT/module/submodule/yyyy-mm-dd/
			String filePath = "upload/" + fileUploadVO.getStorageFilePath();
			String filePathWithRoot = root + "/" + filePath;
			File directory = new File(filePathWithRoot);
			if (!directory.exists()) {
				FileUtils.forceMkdir(directory);
			}
			String fileName = fileUploadVO.getStorageFileName() + "." + extend;
			File uploadedFile = new File(directory, fileName);
			// 返回保存数据库对象
			AccessoryEntity accessory = new AccessoryEntity();
			accessory.setPath(filePath);
			accessory.setName(fileName);
			accessory.setSize(data.length);
			accessory.setUserId(fileUploadVO.getUserId());
			accessory.setExt(extend);
			try {
				// 图片处理
				BufferedImage bis = ImageIO.read(uploadedFile);
				int w = bis.getWidth();
				int h = bis.getHeight();
				accessory.setWidth(w);
				accessory.setHeight(h);
			} catch (Exception ex) {
				try { /*
					 * ThumbnailConvert tc = new ThumbnailConvert();
					 * tc.setCMYK_COMMAND(uploadedFile.getPath());
					 */
					Image image = null;
					image = Toolkit.getDefaultToolkit().getImage(uploadedFile.getPath());
					MediaTracker mediaTracker = new MediaTracker(new Container());
					mediaTracker.addImage(image, 0);
					mediaTracker.waitForID(0);
					accessory.setWidth(image.getWidth(null));
					accessory.setHeight(image.getHeight(null));
				} catch (Exception e1) {
					logger.error("获取图片宽和高异常：" + fileName, e1);
				}
			}
			if (fileUploadVO.getLimitMinWidth() != null) {
				if (accessory.getWidth() < fileUploadVO.getLimitMinWidth().intValue()) {
					if (fileUploadVO.getLimitMaxWidth() != null) {
						throw new BusinessException("当前图片宽度为" + accessory.getWidth() + "px,必须在[" + fileUploadVO.getLimitMinWidth() + "到" + fileUploadVO.getLimitMaxWidth() + "之间]");
					}
					throw new BusinessException("当前图片宽度为" + accessory.getWidth() + ",必须大于[" + fileUploadVO.getLimitMaxWidth() + "px]");
				}
			}
			if (fileUploadVO.getLimitMaxWidth() != null) {
				if (accessory.getWidth() > fileUploadVO.getLimitMaxWidth().intValue()) {
					if (fileUploadVO.getLimitMinWidth() != null) {
						throw new BusinessException("当前图片宽度为" + accessory.getWidth() + "px,要求在[" + fileUploadVO.getLimitMinWidth() + "到" + fileUploadVO.getLimitMaxWidth() + "之间]");
					}
					throw new BusinessException("当前图片宽度为" + accessory.getWidth() + ",必须小于[" + fileUploadVO.getLimitMaxWidth() + "px]");
				}
			}
			// 写入硬盘
			FileUtils.writeByteArrayToFile(uploadedFile, data);
			//accessory = accessoryDao.save(accessory);
			return accessory;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Service
	public static class UploadFileUtilHelper {

		/** properties文件中定义的静态文件存放路径 */
		@Value("${file.rootpath}")
		private String rootPathTemp;

		/**
		 * get properties文件中定义的静态文件存放路径
		 * 
		 * @return
		 * @author nibili 2016年5月30日
		 */
		public String getRootPathTemp() {
			return rootPathTemp;
		}

		/**
		 * set properties文件中定义的静态文件存放路径
		 * 
		 * @param rootPathTemp
		 * @author nibili 2016年5月30日
		 */
		public void setRootPathTemp(String rootPathTemp) {
			this.rootPathTemp = rootPathTemp;
		}

	}

}
