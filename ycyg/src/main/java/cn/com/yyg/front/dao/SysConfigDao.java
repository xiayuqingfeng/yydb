package cn.com.yyg.front.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.com.yyg.base.entity.SysConfigEntity;

/**
 * 系统配置项dao
 * 
 * @author lvzf
 * 
 */
public interface SysConfigDao extends JpaRepository<SysConfigEntity, Long>, JpaSpecificationExecutor<SysConfigEntity> {
 

}
