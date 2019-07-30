package cn.com.yyg.front.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.com.yyg.base.entity.ProductEntity;

/**
 * 商品dao
 * 
 * @author lvzf
 * 
 */
public interface ProductDao extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {

	/**
	 * 根据商品名称查找
	 * 
	 * @param name
	 * @param pageable
	 * @return
	 * @author linwk 2016年8月8日
	 */
	@Query("select t from ProductEntity t where t.name like ?1 order by  id desc")
	public Page<ProductEntity> findByName(String name, Pageable pageable);
	/**
	 * 根据类别获取推荐商品：首页调用
	 *@param cateId
	 *@param pageable
	 *@return
	 * @author lvzf 2016年8月16日
	 */
	@Query("select t from ProductEntity t where t.status="+ProductEntity.STATUS_UP+" and  t.cateId=?1")
    public List<ProductEntity> findByCateId(String cateId,Pageable pageable);
}
