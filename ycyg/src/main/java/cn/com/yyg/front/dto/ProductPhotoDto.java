package cn.com.yyg.front.dto;

import cn.com.easy.utils.SpringContextHolder;
import cn.com.yyg.front.utils.BaseConfig;

/**
 * 商品图片对象
 * @author lvzf	2016年8月23日
 *
 */
public class ProductPhotoDto {
	 private String photoPath;
	 private String remark;
	/**
	 * get photoPath  
	 * @return   
	 * @author lvzf 2016年8月23日
	 */
	public String getPhotoPath() {		
		if(photoPath!=null && photoPath.startsWith("http")){
			return photoPath;
		}
		BaseConfig baseConfig = SpringContextHolder.getBean(BaseConfig.class);
		return baseConfig.getImgServerUrl()+photoPath;
	}
	/** 
	 *set photoPath
	 * @param photoPath 
	 * @author lvzf 2016年8月23日
	 */
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	/**
	 * get remark  
	 * @return   
	 * @author lvzf 2016年8月23日
	 */
	public String getRemark() {
		return remark;
	}
	/** 
	 *set remark
	 * @param remark 
	 * @author lvzf 2016年8月23日
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	 
}
