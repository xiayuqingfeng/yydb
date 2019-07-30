package cn.com.yyg.front.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.com.yyg.base.entity.AccessoryEntity;

/**
 * 附件（图片）dao
 * 
 * @author lvzf
 * 
 */
public interface AccessoryDao extends JpaRepository<AccessoryEntity, Long>, JpaSpecificationExecutor<AccessoryEntity> {
	  @Query("select t from AccessoryEntity t where t.userId=?1 order by  id desc")
	  public Page<AccessoryEntity> findListByUserId(Long userId, Pageable pageable);
	  @Query("select t from AccessoryEntity t where t.userId=?1 and albumId=?2 order by  id desc")
	  public Page<AccessoryEntity> findListByUserIdAndAlbumId(Long userId,Long albumId, Pageable pageable);

}
