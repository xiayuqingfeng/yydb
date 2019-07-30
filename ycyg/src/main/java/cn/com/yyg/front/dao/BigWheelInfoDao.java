package cn.com.yyg.front.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.com.yyg.base.entity.BigWheelInfoEntity;

/**
 * 大转盘实体dao
 * 
 * @author nibili 2016年6月1日
 * 
 */
public interface BigWheelInfoDao extends JpaRepository<BigWheelInfoEntity, Long>, JpaSpecificationExecutor<BigWheelInfoEntity> {

	/**
	 * 按用户id查找转盘
	 * 
	 * @param userId
	 * @param deleteStatus
	 * @param pageable
	 * @return
	 * @author nibili 2016年6月3日
	 */
	public Page<BigWheelInfoEntity> findByUserIdAndDeleteStatus(Long userId, Boolean deleteStatus, Pageable pageable);

	/**
	 * 按id和userId查找实体
	 * 
	 * @param id
	 * @param userId
	 * @param deleteStatus
	 * @return
	 * @author nibili 2016年6月3日
	 */
	public BigWheelInfoEntity findByIdAndUserIdAndDeleteStatus(Long id, Long userId, Boolean deleteStatus);


}
