package cn.com.yyg.front.dto;

import java.util.List;

import cn.com.yyg.base.entity.SysDictEntity;
import cn.com.yyg.base.entity.YgProductEntity;
/**
 * 首页楼层
 * @author lvzf	2016年8月22日
 *
 */
public class IndexFloorDto extends SysDictEntity{
  /** */
	private static final long serialVersionUID = 152950547815602133L;
/**楼层商品数据*/
  private List<YgProductEntity> products;

/**
 * get products  
 * @return   
 * @author lvzf 2016年8月22日
 */
public List<YgProductEntity> getProducts() {
	return products;
}

/** 
 *set products
 * @param products 
 * @author lvzf 2016年8月22日
 */
public void setProducts(List<YgProductEntity> products) {
	this.products = products;
}
  
}
