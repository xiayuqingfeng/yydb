package cn.com.yyg.front.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.com.yyg.base.entity.SmsRecordEntity;

/**
 * 短信记录dao
 * 
 * @author lvzf
 * 
 */
public interface SmsRecordDao extends JpaRepository<SmsRecordEntity, Long>, JpaSpecificationExecutor<SmsRecordEntity> { 

}
