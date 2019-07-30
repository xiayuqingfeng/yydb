package cn.com.yyg.front.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.com.yyg.base.entity.ContentEntity;

/**
 * 内容（大字段）dao
 * 
 * @author lvzf
 * 
 */
public interface ContentDao extends JpaRepository<ContentEntity, Long>, JpaSpecificationExecutor<ContentEntity> {

}
