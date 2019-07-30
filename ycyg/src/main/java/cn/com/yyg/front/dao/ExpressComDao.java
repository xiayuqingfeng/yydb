package cn.com.yyg.front.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.com.yyg.base.entity.ExpressComEntity;

/**
 *快递公司dao
 * 
 * @author lvzf
 * 
 */
public interface ExpressComDao extends JpaRepository<ExpressComEntity, Long>, JpaSpecificationExecutor<ExpressComEntity> {
 

}
