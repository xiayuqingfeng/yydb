package cn.com.yyg.front.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.com.yyg.base.entity.YgProductEntity;

/**
 * 云购商品dao
 * 
 * @author lvzf
 * 
 */
public interface YgProductDao extends JpaRepository<YgProductEntity, Long>, JpaSpecificationExecutor<YgProductEntity> {
	/**
	 * 根据商品名称查找
	 * 
	 * @param name
	 * @param pageable
	 * @return
	 * @author linwk 2016年8月8日
	 */
	@Query("select t from YgProductEntity t where t.name like ?1 order by  id desc")
	public Page<YgProductEntity> findByName(String name, Pageable pageable);
	/**
	 * 获取已经揭晓和即将揭晓的商品
	 *@param pageable
	 *@return
	 * @author lvzf 2016年8月20日
	 */
    public Page<YgProductEntity> findByStatusIn(List<Integer> status,Pageable pageable);
	/**
	 * 根据状态获取
	 *@param pageable
	 *@return
	 * @author lvzf 2016年8月20日
	 */
    public Page<YgProductEntity> findByStatus(int status,Pageable pageable);
	/**
	 * 根据状态获取
	 *@param status
	 *@return
	 * @author lvzf 2016年8月29日
	 */
    public List<YgProductEntity> findByStatus(int status);
	/**
	 * 根据状态和分类获取
	 *@param cateId
	 *@param pageable
	 *@return
	 * @author lvzf 2016年8月20日
	 */ 
    public Page<YgProductEntity> findByStatusAndCateId(int status,String cateId,Pageable pageable);
    
    /**
     * 根据关键字查找
     *@param status
     *@param keyword
     *@param pageable
     *@return
     * @author lvzf 2016年9月8日
     */
    public Page<YgProductEntity> findByStatusAndNameLike(int status,String keyword,Pageable pageable);
	/**
	 * 统计参与云购份数
	 *@return
	 * @author lvzf 2016年8月20日
	 */
	@Query("select sum(usedNum) from YgProductEntity ")
	public Long countBuyNums();
	
	/**
	 * 获取所有期数
	 *@param productId
	 *@return
	 * @author lvzf 2016年8月22日
	 */
	@Query("select t from YgProductEntity t where t.productId=?1 order by period desc")
	public List<YgProductEntity> findAllByProductId(Long productId); 
	/**
	 * 期数分页
	 *@param productId
	 *@param pageable
	 *@return
	 * @author lvzf 2016年9月8日
	 */
	@Query("select t from YgProductEntity t where t.productId=?1 order by period desc")
	public Page<YgProductEntity> findAllByProductId(Long productId,Pageable pageable); 
	/**
	 * 获取已到时间要揭晓的
	 *@param status
	 *@return
	 * @author lvzf 2016年8月30日
	 */
	@Query("select t from YgProductEntity t where t.status=?1 and t.publishDate<=now()")
	public List<YgProductEntity> findAllJiexiao(int status); 
	
	/**
	 * 根据id
	 *@param ids
	 *@return
	 * @author lvzf 2016年8月25日
	 */ 
    public List<YgProductEntity> findByIdIn(List<Long> ids);
	/**
	 * 根据商品id和期数获取
	 *@return
	 * @author lvzf 2016年8月27日
	 */
	public YgProductEntity findByProductIdAndPeriod(Long productId,long period);
}
