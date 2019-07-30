package cn.com.yyg.base.utils;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件对象
 * 
 * @author lvzf 2015年11月16日 下午5:34:07
 */
public class FileUploadVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6729375261238511485L;
	private Long userId;
	/** 存储文件名 */
	private String storageFileName;
	/** 存储文件路径 */
	private String storageFilePath;
	private MultipartFile file;
	/** 限制最小宽度，null表示不限制 */
	private Integer limitMinWidth;
	/** 限制最大宽度，null表示不限制 */
	private Integer limitMaxWidth;
	/** 限制最小高度，null表示不限制 */
	private Integer limitMinHeight;
	/** 限制最大高度，null表示不限制 */
	private Integer limitMaxHeight;

	public FileUploadVO() {

	}

	/**
	 * 
	 * @param file
	 *            文件对象
	 * @param storageFilePath
	 *            存储文件的中间块的相对路径，如：/home/data/statis/upload/{storageFilePath}/{
	 *            storageFileName}
	 * @param storageFileName
	 *            存储文件的文件名，如：/home/data/statis/upload/{storageFilePath}/{
	 *            storageFileName}
	 * @param userId
	 *            用户id
	 */
	public FileUploadVO(MultipartFile file, String storageFilePath,
			String storageFileName, Long userId) {
		this.file = file;
		this.storageFilePath = storageFilePath;
		this.storageFileName = storageFileName;
		this.userId = userId;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getStorageFilePath() {
		return storageFilePath;
	}

	public void setStorageFilePath(String storageFilePath) {
		this.storageFilePath = storageFilePath;
	}

	public String getStorageFileName() {
		return storageFileName;
	}

	public void setStorageFileName(String storageFileName) {
		this.storageFileName = storageFileName;
	}

	public Integer getLimitMinWidth() {
		return limitMinWidth;
	}

	public void setLimitMinWidth(Integer limitMinWidth) {
		this.limitMinWidth = limitMinWidth;
	}

	public Integer getLimitMaxWidth() {
		return limitMaxWidth;
	}

	public void setLimitMaxWidth(Integer limitMaxWidth) {
		this.limitMaxWidth = limitMaxWidth;
	}

	public Integer getLimitMinHeight() {
		return limitMinHeight;
	}

	public void setLimitMinHeight(Integer limitMinHeight) {
		this.limitMinHeight = limitMinHeight;
	}

	public Integer getLimitMaxHeight() {
		return limitMaxHeight;
	}

	public void setLimitMaxHeight(Integer limitMaxHeight) {
		this.limitMaxHeight = limitMaxHeight;
	}

}
