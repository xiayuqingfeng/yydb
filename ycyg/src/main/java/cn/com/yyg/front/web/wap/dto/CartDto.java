package cn.com.yyg.front.web.wap.dto;

import cn.com.yyg.base.entity.YgProductEntity;
/**
 * 购物车对象
 * @author lvzf	2016年8月26日
 *
 */
public class CartDto { 
	/**云购商品id*/ 
	private Long id;
	/**购买数量*/ 
	private long buyNum;	 
	/**更新时间用于排序*/
	private long updateDate;
	private YgProductEntity product;
	private boolean success;
	private String remark;
	public CartDto(){
		
	}
	public CartDto(Long id,long buyNum){
		this.setId(id);
		this.setBuyNum(buyNum);
		this.setUpdateDate(System.currentTimeMillis());
	}
	/**
	 * get id  
	 * @return   
	 * @author lvzf 2016年8月26日
	 */
	public Long getId() {
		return id;
	}
	/** 
	 *set id
	 * @param id 
	 * @author lvzf 2016年8月26日
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * get buyNum  
	 * @return   
	 * @author lvzf 2016年8月26日
	 */
	public long getBuyNum() {
		return buyNum;
	}
	/** 
	 *set buyNum
	 * @param buyNum 
	 * @author lvzf 2016年8月26日
	 */
	public void setBuyNum(long buyNum) {
		this.buyNum = buyNum;
	}
	/**
	 * get updateDate  
	 * @return   
	 * @author lvzf 2016年8月26日
	 */
	public long getUpdateDate() {
		return updateDate;
	}
	/** 
	 *set updateDate
	 * @param updateDate 
	 * @author lvzf 2016年8月26日
	 */
	public void setUpdateDate(long updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * get product  
	 * @return   
	 * @author lvzf 2016年8月26日
	 */
	public YgProductEntity getProduct() {
		return product;
	}
	/** 
	 *set product
	 * @param product 
	 * @author lvzf 2016年8月26日
	 */
	public void setProduct(YgProductEntity product) {
		this.product = product;
	}
	/**
	 * get success  
	 * @return   
	 * @author lvzf 2016年8月26日
	 */
	public boolean isSuccess() {
		return success;
	}
	/** 
	 *set success
	 * @param success 
	 * @author lvzf 2016年8月26日
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	/**
	 * get remark  
	 * @return   
	 * @author lvzf 2016年8月26日
	 */
	public String getRemark() {
		return remark;
	}
	/** 
	 *set remark
	 * @param remark 
	 * @author lvzf 2016年8月26日
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	 
 
	
	 
}
