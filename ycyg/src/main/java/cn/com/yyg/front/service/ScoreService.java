package cn.com.yyg.front.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.easy.exception.BusinessException;
import cn.com.easy.utils.DateUtils;
import cn.com.yyg.base.em.UserScoreTypeEnum;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.base.entity.UserScoreEntity;
import cn.com.yyg.front.dao.UserScoreDao;
/**
 * 积分
 * @author lvzf	2016年10月14日
 *
 */
@Service
public class ScoreService {
	@Autowired
	private UserScoreDao userScoreDao;

	@Autowired
	private SysConfigService sysConfigService;

	/**
	 * 添加积分
	 * 
	 * @param user
	 * @param score
	 * @param type
	 * @return
	 * @author lvzf 2016年10月12日
	 */
	public void addScore(UserEntity user, UserScoreTypeEnum type, String remark) throws BusinessException {
		 this.add(user, "+",type, 1, remark); 
	}
	/**
	 * 添加购买商品积分
	 */
	public void addScore4buyProduct(UserEntity user,int num,String remark) throws BusinessException{
		 this.add(user, "+", UserScoreTypeEnum.Buy, num, remark); 
	}
	/**
	 * 添加签到积分：一天一次签到
	 */
	public boolean addScore4qiandao(UserEntity user,String remark) throws BusinessException{
	 
		boolean qd=todayQiandao(user);
		//一天一次签到,连续签到积分翻倍，7天后重新计算
		if(qd==false){  
			int dd=getLianxuQiandaoDays(user)+1;
			//最多算7天
			if(dd>7){
				dd=7;
			}
			if(dd>1){
				remark+=",连续"+dd+"天";
			}
			this.add(user, "+", UserScoreTypeEnum.QianDao,dd, remark);
			return true;
		} 
		return false;
	}
	/**
	 * 大转盘中奖
	 *@param user
	 *@param score
	 *@param remark
	 *@throws BusinessException
	 * @author lvzf 2016年10月17日
	 */
	public void addScore4BigWheel(UserEntity user,int score,String remark) throws BusinessException{
		this.addByScore(user, "+", UserScoreTypeEnum.BigWheelWin,score, remark);
	}
	/**
	 * 今天是否签到了
	 *@param user
	 *@return
	 * @author lvzf 2016年10月14日
	 */
	public boolean todayQiandao(UserEntity user){
		Date now=new Date();
		List<UserScoreEntity> list=userScoreDao.findByUserIdAndScoreTypeAndYyyymmdd(user.getId(),UserScoreTypeEnum.QianDao.getValue(), DateUtils.formatDate(now, "yyyyMMdd"));
		if(list.size()==0){  
			return false;
		}
		return true;
	}
	/**
	 * 连续签到次数
	 *@param user
	 *@param date
	 *@param dd
	 *@return
	 * @author lvzf 2016年10月14日
	 */
	public int getLianxuQiandaoDays(UserEntity user){  
		Date now=new Date();
		boolean qd=todayQiandao(user);
		int dd=0;
		
		if(qd){//今天已签到
			dd=this.getLianxuQiandaoDays(user,DateUtils.addDays(now, -1),1);
		}else{//今天没签到
			dd=this.getLianxuQiandaoDays(user,DateUtils.addDays(now, -1),0);
		}
		return dd;
	}
	private int getLianxuQiandaoDays(UserEntity user,Date date,int dd){  
		String strday= DateUtils.formatDate(date, "yyyyMMdd");
		List<UserScoreEntity> list=userScoreDao.findByUserIdAndScoreTypeAndYyyymmdd(user.getId(),UserScoreTypeEnum.QianDao.getValue(),strday);
		if(list.size()>0){		
			dd+=1;
			return getLianxuQiandaoDays(user,DateUtils.addDays(date, -1),dd);
		}
		return dd;
	}
	/**
	 * 扣除积分
	 * 
	 * @param user
	 * @param score
	 * @param type
	 * @return
	 * @author lvzf 2016年10月12日
	 */
	public void reduceScore(UserEntity user, UserScoreTypeEnum type, String remark) throws BusinessException {
		 this.add(user, "-", type, 1, remark); 
	}
	public void add(UserEntity user,String direct, UserScoreTypeEnum type, int num,String remark){
		UserScoreEntity scoreEntity = new UserScoreEntity();
		scoreEntity.setCreateBy(user.getUserName());
		scoreEntity.setUserId(user.getId());
		scoreEntity.setDirection(direct);
		scoreEntity.setScoreType(type.getValue());
		scoreEntity.setYyyymmdd(DateUtils.formatDate(new Date(), "yyyyMMdd"));
		if(direct.equals("-")){
			scoreEntity.setScore(-sysConfigService.getScore(type)*num);
		}else{
			scoreEntity.setScore(sysConfigService.getScore(type)*num);
		}
		scoreEntity.setRemark(remark);
		userScoreDao.save(scoreEntity); 
	}
	private void addByScore(UserEntity user,String direct, UserScoreTypeEnum type, int score,String remark){
		UserScoreEntity scoreEntity = new UserScoreEntity();
		scoreEntity.setCreateBy(user.getUserName());
		scoreEntity.setUserId(user.getId());
		scoreEntity.setDirection(direct);
		scoreEntity.setScoreType(type.getValue());
		scoreEntity.setYyyymmdd(DateUtils.formatDate(new Date(), "yyyyMMdd"));
		if(direct.equals("-")){
			scoreEntity.setScore(-score);
		}else{
			scoreEntity.setScore(score);
		}
		scoreEntity.setRemark(remark);
		userScoreDao.save(scoreEntity); 
	}
	
}
