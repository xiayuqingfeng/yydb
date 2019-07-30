package cn.com.yyg.front.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.com.yyg.base.entity.AdvItemEntity;

/**
 * 广告dao
 * 
 * @author lvzf 
 */
public interface AdvItemDao extends JpaRepository<AdvItemEntity, Long>,
		JpaSpecificationExecutor<AdvItemEntity> { 
	/**广告列表*/
	@Query("select a from AdvItemEntity a where a.advId=?1 order by a.seqNo desc,a.id desc")
	public List<AdvItemEntity> findByAdvId(Long advId);
	/**广告列表*/
	public Page<AdvItemEntity> findByAdvId(Long advId, Pageable pageable);
}
