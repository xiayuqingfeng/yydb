package cn.com.yyg.front.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.com.yyg.base.entity.ArticleCateEntity;

/**
 * 文章分类dao
 * 
 * @author lvzf
 * 
 */
public interface ArticleCateDao extends JpaRepository<ArticleCateEntity, Long>, JpaSpecificationExecutor<ArticleCateEntity> {
	/**
	 * 根据分类获取分类
	 * 
	 * @param type
	 * @return
	 * @author lvzf 2016年6月1日
	 */
	@Query("from ArticleCateEntity t where t.deleteStatus=0 and parentId=?1 order by t.seqNo")
	public List<ArticleCateEntity> findByParentId(Long parentId);
	/**
	 * 获取所有分类
	 */
	@Query("from ArticleCateEntity t where t.deleteStatus=0 order by t.seqNo")
	public List<ArticleCateEntity> findAll();
	/**
	 * 根据id获取分类
	 * 
	 * @param cateIds
	 * @return
	 * @author lvzf 2016年6月3日
	 */
	@Query("select t from ArticleCateEntity t where t.deleteStatus=0 and t.id in ?1 order by seqNo,id")
	public List<ArticleCateEntity> findByTypeAndCate( List<Long> cateIds);

	/**
	 * 统计父分类下的子分类数量
	 * 
	 * @param parentId
	 * @return
	 * @author nibili 2016年6月8日
	 */
	public int countByParentId(Long parentId);
}
