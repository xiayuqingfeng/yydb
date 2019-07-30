package cn.com.yyg.front.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.com.yyg.base.entity.UserYgEntity;

/**
 * 用户dao
 * 
 * @author lvzf
 * 
 */
public interface UserYgDao extends JpaRepository<UserYgEntity, Long>, JpaSpecificationExecutor<UserYgEntity> {

	/**
	 * 获取某购买时间前的记录
	 *@param buyDateLong
	 *@param pageable
	 *@return
	 * @author lvzf 2016年8月23日
	 */
	@Query("from UserYgEntity t where t.buyDateLong<=?1")
	public List<UserYgEntity> findByBuyDateLong(Long buyDateLong, Pageable pageable);
	/**
	 * 根据购买时间
	 *@param buyDateLong
	 *@return
	 * @author lvzf 2016年11月1日
	 */
	public List<UserYgEntity> findByBuyDateLong(Long buyDateLong);
	/**
	 * 查找某期号某号码的中奖纪录
	 *@param ygProductId
	 *@param buyNos
	 *@return
	 * @author lvzf 2016年8月29日
	 */
   // public List<UserYgEntity> findByYgProductIdAndBuyNosIn(Long ygProductId,List<Long> buyNo);
    public List<UserYgEntity> findByYgProductIdAndBuyNosLike(Long ygProductId,String buyNo);
    /**
     * 查看某期商品的云购记录
     *@param ygProductId
     *@param pageable
     *@return
     * @author lvzf 2016年9月4日
     */
    public Page<UserYgEntity> findByYgProductId(Long ygProductId,Pageable pageable);
    /**
     * 获取我本期参与的记录
     *@param ygProductId
     *@param buyUserId
     *@return
     * @author lvzf 2016年9月8日
     */
    public List<UserYgEntity> findByYgProductIdAndBuyUserId(Long ygProductId,Long buyUserId);
    /**
     * 我的云购记录
     *@param buyUserId
     *@return
     * @author lvzf 2016年9月25日
     */
    public Page<UserYgEntity> findByBuyUserId(Long buyUserId,Pageable pageable);
    /**
     * 我的中奖记录
     *@param buyUserId
     *@return
     * @author lvzf 2016年9月25日
     */
    public Page<UserYgEntity> findByBuyUserIdAndWin(Long buyUserId,boolean win,Pageable pageable);
    /**
     * 统计我本期的总购买份数
     *@param ygProductId
     *@param buyUserId
     *@return
     * @author lvzf 2016年9月20日
     */
	@Query("select sum(buyNum) from UserYgEntity t where t.ygProductId=?1 and t.buyUserId=?2")
    public Long totalBuyNums(Long ygProductId,Long buyUserId);

}
