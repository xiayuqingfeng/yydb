package cn.com.yyg.front.dao;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.com.yyg.base.entity.UserAccountDetailEntity;

/**
 * 用户资金明细
 * 
 * @author lvzf 2016年10月26日
 * 
 */
public interface UserAccountDetailDao extends JpaRepository<UserAccountDetailEntity, Long>, JpaSpecificationExecutor<UserAccountDetailEntity> {
	/**
	 * 统计用户余额
	 * 
	 * @param userId
	 * @return
	 * @author lvzf 2016年10月26日
	 */
	@Query("select sum(amount) from UserAccountDetailEntity t where t.userId=?1")
	public BigDecimal totalAmountByUserId(Long userId);

	/**
	 * 用户余额明细
	 * 
	 * @param userId
	 * @param pageable
	 * @return
	 * @author linwk 2016年10月26日
	 */
	@Query("select t from UserAccountDetailEntity t where t.userId=?1 order by  id desc")
	public Page<UserAccountDetailEntity> findListByUserId(Long userId, Pageable pageable);
}
