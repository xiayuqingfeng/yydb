package cn.com.yyg.front.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.com.yyg.base.entity.YgProductOrderEntity;

/**
 * 中奖商品订单dao
 * 
 * @author lvzf
 * 
 */
public interface YgProductOrderDao extends JpaRepository<YgProductOrderEntity, Long>, JpaSpecificationExecutor<YgProductOrderEntity> {

	/**
	 * 统计数量
	 *@param value
	 *@return
	 * @author linwk 2016年10月11日
	 */
	int countByStatus(int value);

	int countByUserIdAndStatus(Long id, int value);

}
