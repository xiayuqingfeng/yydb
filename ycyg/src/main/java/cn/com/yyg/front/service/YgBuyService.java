package cn.com.yyg.front.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.easy.exception.BusinessException;
import cn.com.easy.utils.FastJSONUtils;
import cn.com.easy.utils.PageRequest;
import cn.com.easy.utils.PageRequestUtils;
import cn.com.yyg.base.em.YgProductStatusEnum;
import cn.com.yyg.base.entity.ContentEntity;
import cn.com.yyg.base.entity.PayRecordEntity;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.base.entity.UserYgEntity;
import cn.com.yyg.base.entity.YgCartEntity;
import cn.com.yyg.base.entity.YgProductEntity;
import cn.com.yyg.base.utils.DateUtil;
import cn.com.yyg.front.dao.ContentDao;
import cn.com.yyg.front.dao.UserDao;
import cn.com.yyg.front.dao.UserYgDao;
import cn.com.yyg.front.dao.YgCartDao;
import cn.com.yyg.front.dao.YgProductDao;
import cn.com.yyg.sms.SmsServcie;

import com.google.common.collect.Lists;
import com.taobao.api.ApiException;

/**
 * 用户购买记录
 * @author lvzf	2016年8月23日
 *
 */
@Service
public class YgBuyService {
	private Logger logger = LoggerFactory.getLogger(YgBuyService.class);
	@Autowired
	private UserDao userDao; 
	@Autowired
	private UserYgDao userYgDao; 
	/**云购产品dao*/
	@Autowired
	private YgProductDao ygProductDao;
	@Autowired
	private YgCartDao ygCartDao;
	@Autowired
	private ProductService productService;
	/** 大字段 */
	@Autowired
	private ContentDao contentDao;
	@Autowired
	private PayService payService;
	@Autowired
	private ScoreService scoreService;
	@Autowired
	private UserService userService;
	public static void main(String[] args) {
		long init=YgProductEntity.INIT_BUY_NO;
		//商品总份数
		long totalNums=33l;
		//总时间长
		long totaltime=4287868890L;
		//最后时间的毫秒3位数
		long l3w=639;
		//最后购买时间长
		long lastBuyDateLong=143836000;
		//指定中奖号码
		long zd_winNo=10000022L;
		long index=0;
		while(1==1){
			if(((totaltime-l3w+index)%totalNums+init+1)==zd_winNo){ 
				//最后下单时间
				System.out.println("t2:"+(lastBuyDateLong+index));  
				System.out.println("winno2:"+zd_winNo); 
				break;
			}
			index++;
		} 
		System.out.println("winno:"+(7325692979L%33+10000001) ); 
		String no="160718882";
		int last_H=Integer.parseInt(no.substring(0,2)); 
		int last_m=Integer.parseInt(no.substring(2,4));
		int last_s=Integer.parseInt(no.substring(4,6)); 
		int last_SSS=Integer.parseInt(no.substring(6));  
	    DecimalFormat   df   =   new   DecimalFormat("00");
	    System.out.println("format:"+df.format(1));
		 
	}
	/**
	 * 计算中奖号
	 *
	 * @author lvzf 2016年8月23日
	 */
	@Transactional
	public void calWinNo(YgProductEntity yg){
/*		1 奖品的最后一个号码分配完毕后，将公示该分配时间点前本站全部奖品的最后50个参与时间；
		2 将这50个时间的数值进行求和（得出数值A）（每个时间按时、分、秒、毫秒的顺序组合，如20:15:25.362则为201525362）；
		3 为保证公平公正公开，系统还会等待一小段时间，取最近下一期中国福利彩票“老时时彩”的开奖结果（一个五位数值B）；
		4 （数值A+数值B）除以该奖品总需人次得到的余数  + 原始数 10000001，得到最终幸运号码，拥有该幸运号码者，直接获得该奖品。
		注：最后一个号码分配时间距离“老时时彩”最近下一期开奖大于24小时或无法读取老时时彩下一期开奖结果时，默认“老时时彩”开奖结果为00000*/
		 
		 //指定中奖号,需修改最后一笔时间，所以最后一笔最好是自己人购买
		 if(yg.isZdWin() && yg.getWinNo()!=null){ 
				long init=YgProductEntity.INIT_BUY_NO;
				//商品总份数
				long totalNums=yg.getTotalNum(); 
			   //1)将这50个时间的数值进行求和（得出数值A）
				List<UserYgEntity> buyRecords= getBuyRecords(yg,50);
				//总时间长
				long totaltime=0;
				//最后时间
				int last_H=0; 
				int last_m=0; 
				int last_s=0; 
				int last_SSS=0; //毫秒
				String last_ymd=""; 
				long lasttime=0;
				for(int i=0;i<buyRecords.size();i++){
					String dl=DateUtil.getDateToString(new Date(buyRecords.get(i).getBuyDateLong().longValue()), "HHmmssSSS");
					logger.error("指定中奖号码时间明细："+dl+","+buyRecords.get(i).getBuyUserNickName());
					totaltime+=Long.parseLong(dl);
					if(i==0){
						 lasttime=Long.parseLong(dl);		
						 logger.error("指定中奖号码最后一笔时间old："+dl);
						 last_ymd=DateUtil.getDateToString(new Date(buyRecords.get(i).getBuyDateLong().longValue()), "yyyy-MM-dd");
						 last_H=Integer.parseInt(dl.substring(0,2)); 
						 last_m=Integer.parseInt( dl.substring(2,4));
						 last_s=Integer.parseInt( dl.substring(4,6)); 
						 last_SSS=Integer.parseInt( dl.substring(6));  
					} 
				}				
				logger.error("指定中奖号码old总时间："+totaltime);
			    long zd_winNo=yg.getWinNo(); 
			    DecimalFormat   df2   =   new   DecimalFormat("00");
			    DecimalFormat   df3   =   new   DecimalFormat("000");
			    String newTime="";
			    boolean calErr=false;
				while(true){
					newTime=df2.format(last_H)+df2.format(last_m)+df2.format(last_s)+df3.format(last_SSS);
					if(((totaltime-lasttime+Long.parseLong(newTime))%totalNums+init+1)==zd_winNo){ 		
						logger.error("指定中奖号码："+zd_winNo+",新总时间："+(totaltime-lasttime+Long.parseLong(newTime))+",最后一笔时间："+newTime);
						break;
					}

					last_SSS++;
					if(last_SSS>=1000){
						last_SSS=0;
						last_s++;
					}
					if(last_s>=60){
						last_s=0;
						last_m++;
					}
					if(last_m>=60){
						last_m=0;
						last_H++;
					}  
					//找不到就指定中奖
					if(last_H>=24){
						logger.error("指定中奖就算不出结果");
						calErr=true;
						break;
					}
				}
				if(calErr){
					yg.setZdWin(false);
					this.calWinNo(yg);
					return ;
				}
				//修改最后一笔购买时间
				logger.error("指定中奖号码最后一笔时间："+last_ymd+" "+newTime);
				Date ldate=DateUtil.getStringToDate(last_ymd+" "+newTime, "yyyy-MM-dd HHmmssSSS"); 
				 List<UserYgEntity> userYgs=userYgDao.findByBuyDateLong(yg.getLastUserBuyDateLong());
				 if(userYgs.size()>0){
						UserYgEntity uyg=userYgs.get(0);
						uyg.setBuyDateLong(ldate.getTime());						 
						userYgDao.save(uyg);
				}
				 yg.setLastUserBuyDateLong(ldate.getTime());
				yg.setWinDate(new Date(yg.getLastUserBuyDateLong()));
		 }else{
			   //1)将这50个时间的数值进行求和（得出数值A）
				List<UserYgEntity> buyRecords= getBuyRecords(yg,50);
				long totals=0;
				for(int i=0;i<buyRecords.size();i++){
					String dl=DateUtil.getDateToString(new Date(buyRecords.get(i).getBuyDateLong().longValue()), "HHmmssS");
					totals+=Long.parseLong(dl);
					
				}
				//2)数值A 除以该奖品总需人次得到的余数  + 原始数 10000001
				long winno=totals%yg.getTotalNum()+YgProductEntity.INIT_BUY_NO+1;
				logger.info("(第"+yg.getPeriod()+"期)"+yg.getName()+",中奖号码"+winno);
				yg.setWinNo(winno);
				yg.setWinDate(new Date()); 
				
		
		 }
		 //更新购买记录为中奖
		 List<UserYgEntity> userYgs=userYgDao.findByYgProductIdAndBuyNosLike(yg.getId(),"%"+yg.getWinNo()+"%"); 
		if(userYgs.size()>0){
			UserYgEntity uyg=userYgs.get(0);
			yg.setWinUserId(uyg.getBuyUserId());
			Long totalNums=userYgDao.totalBuyNums(yg.getId(),yg.getWinUserId());
			if(totalNums==null){
				totalNums=0L;
			}
			yg.setWinUserBuyNum(totalNums);
			yg.setWinUserNickName(uyg.getBuyUserNickName());
			yg.setWinUserBuyDateLong(uyg.getBuyDateLong());
			yg.setWinUserLogoPath(uyg.getBuyUserLogoPath());
			yg.setWinUserIpAddr(uyg.getIpAddr());
			UserYgEntity winUyg=userYgDao.findOne(userYgs.get(0).getId());
			winUyg.setWin(true);
			winUyg.setWinNo(yg.getWinNo());
			userYgDao.save(winUyg);
		}
		 yg.setStatus(YgProductStatusEnum.END.getValue());
		 ygProductDao.save(yg);
		 
		 //执行成功发送短信给用户
		 UserEntity userEntity=userDao.findOne(yg.getWinUserId());
		 try {
			SmsServcie.sendWin(userEntity.getMobile(), userEntity.getNickName(), "(第"+yg.getPeriod()+"期)"+yg.getName());
		} catch (ApiException e) {
			logger.error("中奖用户(UsedNum="+yg.getUsedNum()+")(WinUserId="+yg.getWinUserId()+")中奖信息,短信发送失败");
			logger.error(e.getMessage());
		}

	}
	public List<UserYgEntity> getBuyRecords(YgProductEntity yg,int top){
		PageRequest pageRequest = new PageRequest(1, top);
		pageRequest.setOrderBy("buyDateLong");
		pageRequest.setOrderDir("desc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		List<UserYgEntity> record= userYgDao.findByBuyDateLong(yg.getLastUserBuyDateLong(), pb);
		return record;
	}
	 
	@Transactional
	public void deteleCart(Long userId,List<Long> ygProductIds){
		ygCartDao.deleteByUserIdAndYgProductIds(userId, ygProductIds);
	}
	/**
	 * 生成支付流水:锁定商品数量(不锁定了）
	 *@param user
	 *@param payType
	 *@param ygProductIds
	 *@return
	 *@throws BusinessException
	 * @author lvzf 2016年9月22日
	 */
	@Transactional
	public synchronized PayRecordEntity createPayOrder(UserEntity user,int payType,List<Long> ygProductIds) throws BusinessException{

		if(ygProductIds.size()==0){
			throw new BusinessException("没有需要支付的商品");
		}	
		long totalPrice=0;
		List<YgCartEntity> list=ygCartDao.findByUserIdAndYgProductIdIn(user.getId(), ygProductIds);
		if(list.size()==0){
			throw new BusinessException("没有需要支付的商品");
		}	
		for(YgCartEntity c:list){
			//商品库存和状态判断
			YgProductEntity yg=ygProductDao.findOne(c.getYgProductId());
			if(yg.getStatus()!=YgProductStatusEnum.ING.getValue()){
				throw new BusinessException(yg.getName()+",已经购买结束");
			} 
			if( yg.getLeaveNum()<c.getBuyNum()){
				throw new BusinessException(yg.getName()+",剩于"+ yg.getLeaveNum()+"份");
			}

			totalPrice+=c.getBuyNum()*yg.getSinglePrice();
	/*		yg.setUsedNum(yg.getUsedNum()+c.getBuyNum());
			yg.setLeaveNum(yg.getTotalNum()-yg.getUsedNum());*/
	
			ygProductDao.save(yg);

			 
		}
		//删除已经购买的购物车
		this.deteleCart(user.getId(), ygProductIds);
		//生成支付流水 
		PayRecordEntity pay=new PayRecordEntity();
		pay.setUserId(user.getId());
		pay.setUserName(user.getUserName());
		pay.setPayType(payType);
		pay.setCarts(FastJSONUtils.toJsonString(list));
		//等支付后在更新金额 
		pay.setAmount(BigDecimal.ONE);
		pay.setPayType(payType);
		//流水号
		//pay.setPayNo(UUID.randomUUID().toString());
		pay.setPayNo(String.valueOf(System.currentTimeMillis()));
		pay.setPayStatus(PayRecordEntity.PAY_STATUS_INIT);
		pay.setAmount(BigDecimal.valueOf(totalPrice));
		payService.createPay(pay);
		return pay;
	}
	/**
	 * 支付完成生成中奖号码等
	 *@param userId
	 *@param ygProductIds
	 *@throws BusinessException
	 * @author lvzf 2016年8月27日
	 */
	@Transactional
	public synchronized void createWinNo(UserEntity user,PayRecordEntity pay) throws BusinessException{ 
	
		List<YgCartEntity> list=FastJSONUtils.toArray(pay.getCarts(), YgCartEntity.class) ;
		List<Long> ygProductIds=Lists.newArrayList();
		for(YgCartEntity c:list){
			ygProductIds.add(c.getYgProductId());
			long nowdate=System.currentTimeMillis();
			//商品库存和状态判断
			YgProductEntity yg=ygProductDao.findOne(c.getYgProductId());
			if(yg.getStatus()!=YgProductStatusEnum.ING.getValue()){
				continue;
			}
			//没库存，就买最新一期的
			if(yg.getLeaveNum()==0){
				yg=ygProductDao.findByProductIdAndPeriod(yg.getProductId(), yg.getPeriod());
			}
			//库存不足，全部买了，剩余存余额
			if(yg.getLeaveNum()<c.getBuyNum()){
/*				c.setBuyNum(yg.getLeaveNum());
				yg.setUsedNum(0);
				yg.setLeaveNum(yg.getTotalNum()-yg.getUsedNum());
				//剩余存余额
				 UserEntity userEntity=userDao.findOne(c.getUserId()); 
				 userService.addAmount(userEntity, BigDecimal.valueOf(c.getBuyNum()-yg.getLeaveNum()), "购买商品退回");*/
				long lnum=yg.getLeaveNum();
				yg.setUsedNum(yg.getTotalNum());
				yg.setLeaveNum(0);
				//剩余存余额
				 UserEntity userEntity=userDao.findOne(c.getUserId()); 
				 userService.addAmount(userEntity, BigDecimal.valueOf((c.getBuyNum()-lnum)*yg.getSinglePrice()), "购买商品["+yg.getName()+"]退回");
				 c.setBuyNum(lnum);
			}else{
				yg.setUsedNum(yg.getUsedNum()+c.getBuyNum());
				yg.setLeaveNum(yg.getTotalNum()-yg.getUsedNum());
			}
			//剩余号码
			ContentEntity leaveBuyNoValue = contentDao.findOne(yg.getLeaveBuyNosContentId());
			String leaveBuyNoStr=leaveBuyNoValue.getContent();
			List<String> leaveBuyNos=Lists.newArrayList(leaveBuyNoStr.split(","));
			


			//生成随机号码
			List<String> buynos=newRandoms(c.getBuyNum(),leaveBuyNos);		
			//更新剩余号码
			leaveBuyNoValue.setContent(StringUtils.join(leaveBuyNos, ","));
			contentDao.save(leaveBuyNoValue);
			
			//修改云购状态为“即将揭晓”
			if(leaveBuyNos.size()<=0){ 
				yg.setStatus(YgProductStatusEnum.PRE.getValue());
				//5分钟后开奖
				//yg.setPublishDate(DateUtil.add(new Date(nowdate), Calendar.MINUTE, 5));
				yg.setPublishDate(new Date(nowdate));
				yg.setLastUserBuyDateLong(nowdate);
			}
			ygProductDao.save(yg);
			
			//生成购买记录
			UserYgEntity userYg=new UserYgEntity();
			userYg.setBuyDateLong(nowdate);	
			userYg.setBuyNos(StringUtils.join(buynos, ","));
			userYg.setBuyNum(c.getBuyNum());
			userYg.setBuyUserId(user.getId());
			userYg.setBuyUserNickName(user.getNickName());
			userYg.setBuyUserLogoPath(user.getHeadPhotoPath());
			userYg.setCreateBy(user.getUserName());
			userYg.setCreateById(user.getId());
			userYg.setPeriod(yg.getPeriod());
			userYg.setProductId(yg.getProductId());
			userYg.setProductName(yg.getName());
			userYg.setSinglePrice(yg.getSinglePrice()); 
			userYg.setTotalNum(yg.getTotalNum());
			userYg.setYgProductId(yg.getId());
			userYg.setClientIp(c.getClientIp());
			userYg.setIpAddr(c.getIpAddr());
			userYgDao.save(userYg);
			//没有可买的份数时，生成下一期云购商品；
			if(leaveBuyNos.size()<=0){ 
				this.calWinNo(yg);//TODO 先马上计算出来中奖结果，不等彩票结果
				productService.createNewYgProduct(yg.getProductId());
			}
		}
		//删除已经购买的购物车
		this.deteleCart(user.getId(), ygProductIds);
		
		//更新支付流水（交易记录）
		pay.setPayTime(new Date() );
		pay.setPayStatus(PayRecordEntity.PAY_STATUS_SUCCESS);
		payService.updatePay(pay);
		//添加积分
		scoreService.addScore4buyProduct(user, pay.getAmount().intValue(), "购买商品，金额：￥"+pay.getAmount());

		//如果余额付款，要扣除余额
		if(pay.getPayType()==PayRecordEntity.PAY_TYPE_YUE){
			userService.addAmount(user, pay.getAmount().multiply(new BigDecimal(-1)), "购买商品，订单号："+pay.getPayNo());
		}
	}
	/**
	 * 批量揭晓状态转-计算状态
	 *
	 * @author lvzf 2016年8月29日
	 */
	@Transactional
	public synchronized void doJiexiao (){
		 List<YgProductEntity> list=ygProductDao.findAllJiexiao(YgProductStatusEnum.PRE.getValue());
		 for(YgProductEntity yg:list){
			 yg.setStatus(YgProductStatusEnum.DO.getValue());
		 }
		 ygProductDao.save(list);
	}
	/**
	 * 批量计算中奖号码
	 *
	 * @author lvzf 2016年8月29日
	 */
	@Transactional
	public synchronized void doWinNo (){
		 List<YgProductEntity> list=ygProductDao.findByStatus(YgProductStatusEnum.PRE.getValue());
		 for(YgProductEntity yg:list){
			 //TO 计算中奖号码
			 calWinNo(yg);
		 }
		 //ygProductDao.save(list);
	}
	private static List<String> newRandoms(long buyNum,List<String> oldRandomList){ 
		//购买的号码列表
		List<String> buyNos = new ArrayList<String>();
		for (long j = 0; j < buyNum; j++) {
			//n must be positive
			int buyNo = new Random().nextInt(oldRandomList.size());
			buyNos.add(String.valueOf(YgProductEntity.INIT_BUY_NO+Long.parseLong(oldRandomList.get(buyNo))));
			oldRandomList.remove(buyNo);
		}
		return buyNos;
	}
	
}
