package cn.com.yyg.front.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.com.yyg.base.entity.UserPhotoAblumEntity;

/**
 * 相册dao
 * 
 * @author lvzf
 * 
 */
public interface UserPhotoAblumDao extends JpaRepository<UserPhotoAblumEntity, Long>, JpaSpecificationExecutor<UserPhotoAblumEntity> {
 
	  public List<UserPhotoAblumEntity> findListByUserId(Long userId);
	  
	  public List<UserPhotoAblumEntity> findListByUserIdAndParentId(Long userId,Long parentId);
	  public int countByDeleteStatusAndParentId(boolean deleteStatus,Long parentId);
}
