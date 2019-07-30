package cn.com.yyg.front.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.com.yyg.base.entity.AreaEntity;

/**
 * 地区dao
 * 
 * @author linwk 2016年8月10日
 * 
 */
public interface AreaDao extends JpaRepository<AreaEntity, Long>, JpaSpecificationExecutor<AreaEntity> {

	/**
	 * 获取一级列表
	 * 
	 * @return
	 * @author linwk 2016年8月10日
	 */
	@Query("from AreaEntity t where t.deleteStatus=0 and t.parentId IS NULL")
	public List<AreaEntity> findParent();

	/**
	 * 获取有效下级列表
	 * 
	 * @param parentId
	 * @return
	 * @author linwk 2016年8月10日
	 */
	@Query("from AreaEntity t where t.deleteStatus=0 and t.parentId=?1 order by t.seqNo")
	public List<AreaEntity> findByParentId(Long parentId);

	/**
	 * 所有有效字典
	 * 
	 */
	@Query("from AreaEntity t where t.deleteStatus=0 order by t.seqNo")
	public List<AreaEntity> findAll();

	/**
	 * 统计父节点下的子分类数量
	 * 
	 * @param parentId
	 * @return
	 * @author linwk 2016年8月10日
	 */
	@Query("select count(t.id) from AreaEntity t where t.parentId=?1 and t.deleteStatus=0")
	public int countByParentId(Long parentId);

	/**
	 * 按城市名称，查找城市
	 * 
	 * @param areaName
	 * @param level
	 * @return
	 * @author linwk 2016年8月10日
	 */
	@Query("select t.id from AreaEntity t where t.areaName like %?1% and t.level=?2")
	public Long findByLikeAreaNameAndLevel(String areaName, int level);

}
