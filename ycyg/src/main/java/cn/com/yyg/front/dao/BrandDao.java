package cn.com.yyg.front.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.com.yyg.base.entity.BrandEntity;

/**
 * 品牌dao
 * 
 * @author linwk 2016年8月26日
 * 
 */
public interface BrandDao extends JpaRepository<BrandEntity, Long>, JpaSpecificationExecutor<BrandEntity> {

	/**
	 * 获取一级列表
	 * 
	 * @return
	 * @author linwk 2016年8月26日
	 */
	@Query("from BrandEntity t where t.deleteStatus=0 and t.parentId IS NULL")
	public List<BrandEntity> findParent();

	/**
	 * 获取有效下级列表
	 * 
	 * @param parentId
	 * @return
	 * @author linwk 2016年8月26日
	 */
	@Query("from BrandEntity t where t.deleteStatus=0 and t.parentId=?1 order by t.seqNo")
	public List<BrandEntity> findByParentId(Long parentId);

	/**
	 * 所有有效字典
	 * 
	 */
	@Query("from BrandEntity t where t.deleteStatus=0 order by t.seqNo")
	public List<BrandEntity> findAll();

	/**
	 * 统计父节点下的子分类数量
	 * 
	 * @param parentId
	 * @return
	 * @author linwk 2016年8月26日
	 */
	@Query("select count(t.id) from BrandEntity t where t.parentId=?1 and t.deleteStatus=0")
	public int countByParentId(Long parentId);

	/**
	 * 按父级名称，查找分类
	 * 
	 * @param brandName
	 * @param level
	 * @return
	 * @author linwk 2016年8月26日
	 */
	@Query("select t.id from BrandEntity t where t.brandName like %?1% and t.level=?2")
	public Long findByLikeBrandAndLevel(String brandName, int level);

}
