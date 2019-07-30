package cn.com.yyg.front.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.easy.exception.BusinessException;
import cn.com.yyg.base.entity.PayRecordEntity;
import cn.com.yyg.base.entity.YgCartEntity;
import cn.com.yyg.base.entity.YgProductEntity;
import cn.com.yyg.front.dao.PayRecordDao;
import cn.com.yyg.front.dao.UserYgDao;
import cn.com.yyg.front.dao.YgProductDao;
import cn.com.yyg.front.mybatis.PayMybatisDao;
/**
 * 支付
 * @author lvzf	2016年9月22日
 *
 */
@Service
public class PayService {
	//private Logger logger = LoggerFactory.getLogger(PayService.class);
	@Autowired
	private UserYgDao userYgDao; 
	/**云购产品dao*/
	@Autowired
	private YgProductDao ygProductDao;
	@Autowired
	private PayRecordDao payRecordDao;
	@Autowired
	private PayMybatisDao payMybatisDao;
	public PayRecordEntity getPayByPayNo(String payNo){
		return payRecordDao.findByPayNo(payNo);
	}
	@Transactional
	public synchronized void createPay(PayRecordEntity pay)throws BusinessException{
		payRecordDao.save(pay);
	}
	@Transactional
	public synchronized void updatePay(PayRecordEntity pay)throws BusinessException{
		payRecordDao.save(pay);
	}
	/**
	 * 删除过期的支付，并还原购买数量等
	 *
	 * @author lvzf 2016年10月8日
	 */
	@Transactional
	public synchronized void updateDuePayRecord(int min){
		List<Long> list=payMybatisDao.queryDueUnPay(min, PayRecordEntity.PAY_STATUS_INIT);
		for(Long t:list){
			PayRecordEntity p=payRecordDao.findOne(t);
			 List<YgCartEntity> cs=p.getCartList();
			 for(YgCartEntity c:cs){				 
				YgProductEntity yg=ygProductDao.findOne(c.getYgProductId());
				yg.setUsedNum(yg.getUsedNum()-c.getBuyNum());
				yg.setLeaveNum(yg.getTotalNum()-yg.getUsedNum());
				ygProductDao.save(yg);
			 }
			 p.setPayStatus(PayRecordEntity.PAY_STATUS_DUE_CLOSE);
			 payRecordDao.save(p);  
		}
	}
}
