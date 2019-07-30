package cn.com.yyg.front.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.com.yyg.base.entity.BigWheelUserPrizeEntity;

/**
 * 大转盘用户奖项dao
 * 
 * @author nibili 2016年6月3日
 * 
 */
public interface BigWheelUserPrizeDao extends JpaRepository<BigWheelUserPrizeEntity, Long>, JpaSpecificationExecutor<BigWheelUserPrizeEntity> {

	/**
	 * 按大转盘id,查找参与用户中奖信息
	 * 
	 * @param bigWheelId
	 * @param pageable
	 * @return
	 * @author nibili 2016年6月3日
	 */
	public Page<BigWheelUserPrizeEntity> findByBigWheelId(Long bigWheelId, Pageable pageable);

	/**
	 * 查找中奖记录
	 * 
	 * @param bigWheelId
	 * @param pageable
	 * @return
	 * @author nibili 2016年6月3日
	 */
	@Query(" from BigWheelUserPrizeEntity b where b.bigWheelId=?1 and b.prizeId is not null order by id desc ")
	public Page<BigWheelUserPrizeEntity> findWinnings(Long bigWheelId, Pageable pageable);

	/**
	 * 按大转盘id和电话，统计用户总的参与次数
	 * 
	 * @param bigWheelId
	 * @param tel
	 * @param pageable
	 * @return
	 * @author nibili 2016年6月4日
	 */
	public int countByBigWheelIdAndTel(Long bigWheelId, Long tel);

	/**
	 * 查找某个大转盘的用户中奖信息
	 * 
	 * @param bigWheelId
	 * @param tel
	 * @return
	 * @author nibili 2016年6月6日
	 */
	public List<BigWheelUserPrizeEntity> findByBigWheelIdAndTel(Long bigWheelId, Long tel);

	/**
	 * 查询当天抽奖次数
	 * 
	 * @param bigWheelId
	 * @param tel
	 * @return
	 * @author nibili 2016年6月4日
	 */
	@Query("select count(1) from BigWheelUserPrizeEntity b where b.bigWheelId=?1 and b.tel=?2 and date(b.createTime) = curdate()")
	public int countTodayTimesByBigWheelIdAndTel(Long bigWheelId, String tel);

	/**
	 * 查询用户中奖记录
	 * 
	 * @param bigWheelId
	 * @param tel
	 * @param pageable
	 * @return
	 * @author nibili 2016年6月4日
	 */
	@Query(" from BigWheelUserPrizeEntity b where b.bigWheelId=?1 and b.tel=?2 and b.prizeId is not null ")
	public Page<BigWheelUserPrizeEntity> findUserWinnings(Long bigWheelId, String tel, Pageable pageable);

	/**
	 * 按奖项id获取用户获取记录
	 * 
	 * @param prizeId
	 * @return
	 * @auth nibili 2016年6月6日 上午1:19:00
	 */
	public List<BigWheelUserPrizeEntity> findByPrizeId(Long prizeId);

	/**
	 * 按大转盘id和用户电话，更新用户名字
	 * 
	 * @param bigWheelId
	 * @param tel
	 * @return
	 * @author nibili 2016年6月6日
	 */
	@Modifying
	@Query("update BigWheelUserPrizeEntity b set b.name=?1 where b.bigWheelId=?2 and b.tel=?3")
	public int updateNameByBigWheelIdAndTel(String name, Long bigWheelId, String tel);
}
