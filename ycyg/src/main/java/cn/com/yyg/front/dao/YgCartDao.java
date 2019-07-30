package cn.com.yyg.front.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.com.yyg.base.entity.YgCartEntity;

/**
 * 云购购物车dao
 * 
 * @author lvzf
 * 
 */
public interface YgCartDao extends JpaRepository<YgCartEntity, Long>, JpaSpecificationExecutor<YgCartEntity> {
	/**
	 * 获取用户的购物车列表
	 *@return
	 * @author lvzf 2016年8月26日
	 */
    public List<YgCartEntity> findByUserId(Long userId);
    /**
     * 获取购车明细
     *@return
     * @author lvzf 2016年8月26日
     */
    public List<YgCartEntity> findByUserIdAndYgProductId(Long userId,Long ygProductId);
    /**
     * 获取购物车
     *@param userId
     *@param ygProductIds
     *@return
     * @author lvzf 2016年8月26日
     */
    public List<YgCartEntity> findByUserIdAndYgProductIdIn(Long userId,Collection<Long> ygProductIds);
    /**
     * 删除购物车
     *@param userId
     *@param ygProductIds
     *@return
     * @author lvzf 2016年8月26日
     */
	@Modifying
	@Query("delete from YgCartEntity a where a.userId=?1 and a.ygProductId in ?2")
	public int deleteByUserIdAndYgProductIds(Long userId,List<Long> ygProductIds);
}
