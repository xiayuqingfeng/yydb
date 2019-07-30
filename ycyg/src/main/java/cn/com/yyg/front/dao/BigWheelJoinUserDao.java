package cn.com.yyg.front.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.com.yyg.base.entity.BigWheelJoinUserEntity;

/**
 * 大转盘参与用户
 * 
 * @author nibili 2016年6月3日
 * 
 */
public interface BigWheelJoinUserDao extends JpaRepository<BigWheelJoinUserEntity, Long>, JpaSpecificationExecutor<BigWheelJoinUserEntity> {

	/**
	 * 按大转盘id和电话查找参与用户信息
	 * 
	 * @param bigWheelId
	 * @param tel
	 * @return
	 * @author nibili 2016年6月4日
	 */
	public BigWheelJoinUserEntity findByBigWheelIdAndTel(Long bigWheelId, String tel);

	/**
	 * 统计大转盘参与人数
	 * @param bigWheelId
	 * @return
	 * @author nibili 2016年6月6日
	 */
	public Integer countByBigWheelId(Long bigWheelId);
}
