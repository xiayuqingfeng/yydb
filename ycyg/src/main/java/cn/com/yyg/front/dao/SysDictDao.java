package cn.com.yyg.front.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.com.yyg.base.entity.SysDictEntity;

/**
 * 公共下拉选项（字典）dao
 * 
 * @author lvzf
 * 
 */
public interface SysDictDao extends JpaRepository<SysDictEntity, Long>, JpaSpecificationExecutor<SysDictEntity> {
	/**
	 * 获取有效下级列表
	 * 
	 * @param type
	 * @return
	 * @author lvzf 2016年6月1日
	 */
	@Query("from SysDictEntity t where t.deleteStatus=0 and display=1 and t.parentId=?1 order by t.seqNo desc")
	public List<SysDictEntity> findByParentId(Long parentId);
	
	@Query("from SysDictEntity t where t.deleteStatus=0 and display=1 and t.parentId=?1")
	public List<SysDictEntity> findByParentId(Long parentId,Pageable pageable);

	/**
	 * 所有有效字典
	 */
	@Query("from SysDictEntity t where t.deleteStatus=0 and display=1  order by t.seqNo desc")
	public List<SysDictEntity> findAll();
 
	/**
	 * 根据id获取字典
	 * 
	 * @param type
	 * @param cateIds
	 * @return
	 * @author linwk 2016年8月9日
	 */
	//@Query("select t from SysDictEntity t where t.deleteStatus=0 and type=?1 and t.id in ?2 order by seqNo,id")
	//public List<SysDictEntity> findByTypeAndCate(int type, List<Long> cateIds);

	/**
	 * 统计父节点下的子分类数量
	 * 
	 * @param parentId
	 * @return
	 * @author linwk 2016年8月9日
	 */
	public int countByDeleteStatusAndParentId(boolean deleteStatus,Long parentId);

}
