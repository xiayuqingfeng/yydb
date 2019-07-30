package cn.com.yyg.front.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.com.yyg.base.entity.PayRecordEntity;

/**
 * 支付记录dao
 * 
 * @author lvzf
 * 
 */
public interface PayRecordDao extends JpaRepository<PayRecordEntity, Long>, JpaSpecificationExecutor<PayRecordEntity> { 
	/**
	 * 根据支付流水号获取支付记录
	 *@param payNo
	 *@return
	 * @author lvzf 2016年9月22日
	 */
	public PayRecordEntity findByPayNo(String payNo);
}
