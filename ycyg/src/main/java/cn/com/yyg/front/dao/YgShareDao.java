package cn.com.yyg.front.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.com.yyg.base.entity.YgShareEntity;

/**
 * 晒单分享dao
 * 
 * @author lvzf
 * 
 */
public interface YgShareDao extends JpaRepository<YgShareEntity, Long>, JpaSpecificationExecutor<YgShareEntity> {
	/**
	 * 获取已审核的晒单
	 * 
	 * @param pageable
	 * @return
	 * @author lvzf 2016年8月20日
	 */
	public Page<YgShareEntity> findByAudit(boolean audit, Pageable pageable);
	/**
	 * 统计已审核的晒单
	 *@param audit
	 *@return
	 * @author lvzf 2016年9月18日
	 */
	public long countByAudit(boolean audit);
	/**
	 * 获取个人已审核的晒单
	 *@param userId
	 *@param audit
	 *@param pageable
	 *@return
	 * @author lvzf 2016年9月25日
	 */
	public Page<YgShareEntity> findByUserIdAndAudit(Long userId,boolean audit, Pageable pageable);
	/**
	 * 统计个人已审核的晒单
	 *@param userId
	 *@param audit
	 *@return
	 * @author lvzf 2016年9月25日
	 */
	public long countByUserIdAndAudit(Long userId,boolean audit);

	/**
	 * 获取个人全部晒单
	 *@param userId
	 *@param audit
	 *@param pageable
	 *@return
	 * @author lvzf 2016年9月25日
	 */
	public Page<YgShareEntity> findByUserId(Long userId,Pageable pageable);
	/**
	 * 统计个人全部晒单
	 *@param userId
	 *@param audit
	 *@return
	 * @author lvzf 2016年9月25日
	 */
	public long countByUserId(Long userId);
	/**
	 * 获取某期云购商品的晒单
	 * 
	 * @param ygProductId
	 * @param audit
	 * @param pageable
	 * @return
	 * @author lvzf 2016年9月4日
	 */
	@Query("select DISTINCT a from YgShareEntity a ,UserYgEntity b where a.userId=b.buyUserId and b.ygProductId=?1 and a.audit=1")
	public Page<YgShareEntity> findByYgProductIdAndAudit(Long ygProductId, Pageable pageable);
	
	/** 不同期 一个商品的 审核通过幸运晒单
	 * 
	 *@param ygProductId
	 *@param pageable
	 *@return
	 * @author linwk 2016年10月19日
	 */
	@Query("select DISTINCT a from YgShareEntity a  where  a.productId=?1 and a.audit=1")
	public Page<YgShareEntity> findByProductIdAndAudit(Long productId, Pageable pageable);

	/**
	 * 根据标题搜索
	 * 
	 * @param string
	 * @param pageable
	 * @return
	 * @author linwk 2016年9月12日
	 */
	@Query("select t from YgShareEntity t where t.title like ?1 order by  id desc")
	public Page<YgShareEntity> findByTitle(String string, Pageable pageable);

}
