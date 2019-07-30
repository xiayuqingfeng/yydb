package cn.com.yyg.front.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.com.yyg.base.entity.ArticleEntity;

/**
 * 文章dao
 * 
 * @author lvzf
 * 
 */
public interface ArticleDao extends JpaRepository<ArticleEntity, Long>, JpaSpecificationExecutor<ArticleEntity> {
 
	/**
	 * 根据类型 分类获取文章
	 * 
	 * @param type
	 * @param cateId
	 * @return
	 * @author lvzf 2016年6月3日
	 */
	@Query("select t from ArticleEntity t where t.deleteStatus=0 and articleCateId=?1 order by seqNo asc,createTime desc")
	public List<ArticleEntity> findByCate(Long cateId);

	
	public List<ArticleEntity> findByPinyin(String pinyin);

	/**
	 * 统计分类被引用的数量
	 * 
	 * @param cateId
	 * @return
	 * @author nibili 2016年6月8日
	 */
	public int countByArticleCateId(Long cateId); 
 
}
