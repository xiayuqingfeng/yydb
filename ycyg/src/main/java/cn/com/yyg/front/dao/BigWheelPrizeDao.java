package cn.com.yyg.front.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.com.yyg.base.entity.BigWheelPrizeEntity;

/**
 * 大转盘奖项实体dao
 * 
 * @author nibili 2016年6月1日
 * 
 */
public interface BigWheelPrizeDao extends JpaRepository<BigWheelPrizeEntity, Long>, JpaSpecificationExecutor<BigWheelPrizeEntity> {

	/**
	 * 按大转盘id 和 用户id获取奖项
	 * 
	 * @param bigWheelId
	 * @param userId
	 * @return
	 * @author nibili 2016年6月2日
	 */
	public Page<BigWheelPrizeEntity> findByBigWheelIdAndUserId(Long bigWheelId, Long userId, Pageable pageable);

	/**
	 * 按大转盘id 和 用户id获取奖项
	 * 
	 * @param bigWheelId
	 * @param userId
	 * @return
	 * @author nibili 2016年6月2日
	 */
	public List<BigWheelPrizeEntity> findByBigWheelIdAndUserId(Long bigWheelId, Long userId);

	/**
	 * 按大转盘id查找奖品
	 * @param bigWheelId
	 * @return
	 * @author nibili 2016年6月4日
	 */
	public List<BigWheelPrizeEntity> findByBigWheelId(Long bigWheelId);

	/**
	 * 按id和用户id 查找实体
	 * 
	 * @param id
	 * @param userId
	 * @return
	 * @author nibili 2016年6月2日
	 */
	public BigWheelPrizeEntity findByIdAndUserId(Long id, Long userId);

	/**
	 * 按id和用户id删除
	 * 
	 * @param id
	 * @param userId
	 * @return
	 * @author nibili 2016年6月3日
	 */
	@Modifying
	public Integer deleteByIdAndUserId(Long id, Long userId);

	/**
	 * 计算总概率
	 * 
	 * @param bigWhealId
	 * @return
	 * @author nibili 2016年6月2日
	 */
	@Query("select sum(b.prizeRate) from BigWheelPrizeEntity b where b.bigWheelId=?1")
	public Integer sumPrizeRateByBigWheelId(long bigWheelId);
}
