package cn.com.yyg.front.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.com.yyg.base.entity.UserScoreEntity;

/**
 * 会员积分dao
 * 
 * @author lvzf
 * 
 */
public interface UserScoreDao extends JpaRepository<UserScoreEntity, Long>, JpaSpecificationExecutor<UserScoreEntity> {
	/**
	 * 用户积分明细
	 *@param userId
	 *@param pageable
	 *@return
	 * @author lvzf 2016年10月14日
	 */
    public Page<UserScoreEntity> findByUserId(Long userId,Pageable pageable);
    /**
     * 用户总积分
     *@param userId
     *@return
     * @author lvzf 2016年10月14日
     */
	@Query("select sum(score) from UserScoreEntity t where t.userId=?1")
    public Long totalScoreByUserId(Long userId);
	/**
	 * 获取当前的积分记录
	 *@param scoreType
	 *@param yyyymmdd
	 *@return
	 * @author lvzf 2016年10月14日
	 */
	public List<UserScoreEntity> findByUserIdAndScoreTypeAndYyyymmdd(Long userId,int scoreType,String yyyymmdd);
}
