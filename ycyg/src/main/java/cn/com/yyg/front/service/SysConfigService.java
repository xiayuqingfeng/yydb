package cn.com.yyg.front.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.easy.utils.FastJSONUtils;
import cn.com.yyg.base.em.UserScoreTypeEnum;
import cn.com.yyg.base.entity.SysConfigEntity;
import cn.com.yyg.front.dao.SysConfigDao;
import cn.com.yyg.front.dto.ScoreConfigDto;
/**
 * 系统配置项业务
 * @author lvzf	2016年10月12日
 *
 */
@Service
public class SysConfigService { 
	@Autowired
	private SysConfigDao sysConfigDao;  
	public SysConfigEntity getById(Long id){
		return sysConfigDao.findOne(id);
	}
	public SysConfigEntity save(SysConfigEntity config){
		return sysConfigDao.save(config);
	}
	/**
	 * 获取积分规则map列表
	 *@return key：积分类型枚举值
	 * @author lvzf 2016年10月12日
	 */
/*	public Map<Integer,ScoreConfigDto> getScoreRuleMap(){
		Map<Integer,ScoreConfigDto> map=new LinkedHashMap<Integer,ScoreConfigDto>();
		SysConfigEntity config=sysConfigDao.findOne(SysConfigEntity.ID_SCORE);
		if(config!=null && StringUtils.isNoneBlank(config.getData())){			
			List<ScoreConfigDto> list=FastJSONUtils.toArray(config.getData(), ScoreConfigDto.class);
			for(ScoreConfigDto s:list){
				map.put(s.getType(), s);
			}
		}
		return map;
	}*/
	/**
	 * 获取规则积分
	 */
	public ScoreConfigDto getScoreRule(){
		SysConfigEntity config=sysConfigDao.findOne(SysConfigEntity.ID_SCORE);
		if(config!=null && StringUtils.isNoneBlank(config.getData())){			
			ScoreConfigDto scoreRule=FastJSONUtils.toObject(config.getData(), ScoreConfigDto.class);
			return scoreRule;
		}
		return new ScoreConfigDto();
	}
	/**
	 * 获取规则积分
	 *@param type
	 *@return
	 * @author lvzf 2016年10月13日
	 */
	public int getScore(UserScoreTypeEnum type){
		ScoreConfigDto config=this.getScoreRule();
		if(type.getValue()==UserScoreTypeEnum.Regist.getValue()){
			return config.getRegistScore();
		}else if(type.getValue()==UserScoreTypeEnum.BigWheelJoin.getValue()){
			return config.getBigWheelJoinScore();
		}else if(type.getValue()==UserScoreTypeEnum.BigWheelWin.getValue()){
			return config.getBigWheelWinScore();
		}else if(type.getValue()==UserScoreTypeEnum.Buy.getValue()){
			return config.getBuyScore();
		}else if(type.getValue()==UserScoreTypeEnum.Link.getValue()){
			return config.getLinkScore();
		}else if(type.getValue()==UserScoreTypeEnum.QianDao.getValue()){
			return config.getQianDaoScore();
		}else if(type.getValue()==UserScoreTypeEnum.Share.getValue()){
			return config.getShareScore();
		}else if(type.getValue()==UserScoreTypeEnum.Yaoqing.getValue()){
			return config.getYaoqingScore();
		}
		return 0;
	}
}
