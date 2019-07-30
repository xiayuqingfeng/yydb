package cn.com.yyg.front.dao;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseTest;
import cn.com.easy.exception.BusinessException;
import cn.com.yyg.base.entity.PayRecordEntity;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.base.utils.DateUtil;
import cn.com.yyg.front.service.PayService;
import cn.com.yyg.front.service.YgBuyService;

public class UserDaoTest extends BaseTest {

	@Autowired
	private UserDao userDao;

	@Autowired
    private PayService payService;
	@Autowired
	private YgBuyService ygBuyService;
	
	@Test
	public void save() {
/*		UserEntity userEntity = userDao.findOne(1l);
		if(userEntity!=null){
		userEntity.setTrueName("aabbba");
		userDao.saveAndFlush(userEntity);
		}*/
		System.out.println("long:"+System.currentTimeMillis());
		System.out.println("date:"+DateUtil.getNowDateToString("yyyy-MM-dd HH:mm:ss.S"));
		System.out.println("date:"+DateUtil.getNowDateToString("HH:mm:ss.S"));
		System.out.println("date:"+DateUtil.getDateToString(new Date(System.currentTimeMillis()), "HH:mm:ss.S"));
		long x=93518819+1;
		while(1==1){
			if((x+93518819+56562)%809==705){
				break;
			}
			x++;
		}
		System.out.println("mm:"+x);
		
	}
	@Test
	public void win() throws BusinessException {
		PayRecordEntity pay=payService.getPayByPayNo("1477402911921");		
		pay.setPayStatus(PayRecordEntity.PAY_STATUS_SUCCESS);//付款
		//生成中奖号码
		UserEntity user =userDao.findOne(pay.getUserId());
		ygBuyService.createWinNo(user,pay);
	}
}
