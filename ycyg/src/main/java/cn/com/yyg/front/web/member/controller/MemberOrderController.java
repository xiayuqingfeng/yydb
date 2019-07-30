package cn.com.yyg.front.web.member.controller;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.easy.dto.MessageDTO;
import cn.com.easy.dto.PageRequestParamDTO;
import cn.com.easy.exception.BusinessException;
import cn.com.easy.utils.FastJSONUtils;
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.yyg.base.em.YgProductOrderStatusEnum;
import cn.com.yyg.base.entity.AddressEntity;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.base.entity.UserYgEntity;
import cn.com.yyg.base.entity.YgProductEntity;
import cn.com.yyg.base.entity.YgProductOrderEntity;
import cn.com.yyg.base.utils.PageUtils;
import cn.com.yyg.front.dao.AddressDao;
import cn.com.yyg.front.dao.UserYgDao;
import cn.com.yyg.front.dao.YgProductDao;
import cn.com.yyg.front.dao.YgProductOrderDao;
import cn.com.yyg.front.utils.RequestUtils;

import com.google.common.collect.Lists;
/**
 * 中奖订单：领奖、生成订单等
 * @author lvzf	2016年10月10日
 *
 */
@Controller
@RequestMapping("/member/order")
public class MemberOrderController {

	private Logger logger = LoggerFactory.getLogger(MemberOrderController.class);
	/** 云购 */
	@Autowired
	private YgProductDao ygProductDao;
	/** 用户云购 */
	@Autowired
	private UserYgDao userYgDao;

	/** 用户云购订单 */
	@Autowired
	private YgProductOrderDao ygProductOrderDao;
	
	/** 收货地址dao */
	@Autowired
	private AddressDao addressDao;
	
	@Autowired
	private MemberController memberController;
	/**
	 * 我的订单
	 *@param modelMap
	 *@param req
	 *@param res
	 *@param pageReqeustParam
	 *@param order
	 *@return
	 * @author lvzf 2016年10月14日
	 */
	@RequestMapping
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, final PageRequestParamDTO pageReqeustParam, final YgProductOrderEntity order){
		UserEntity user=RequestUtils.getCurrentUser(req);
		final Long userId=user.getId();
		Page<YgProductOrderEntity> springPage = ygProductOrderDao.findAll(new Specification<YgProductOrderEntity>() {

			public Predicate toPredicate(Root<YgProductOrderEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Path<Integer> t_user = root.get("userId"); 
				Path<Integer> t_status = root.get("status"); 
				Path<Date> createTime = root.get("createTime");
				List<Predicate> p = Lists.newArrayList(); 
				p.add(cb.equal(t_user,userId));
				//订单状态
				if(order.getStatus()!=null){
					p.add(cb.equal(t_status, order.getStatus()));
				} 
				if (p != null) {
					query.where(p.toArray(new Predicate[] {})); // 这里可以设置任意条查询条件
				}
				List<Order> orders = Lists.newArrayList();
				orders.add(cb.desc(createTime));
				query.orderBy(orders);
				return null;
			}
		}, pageReqeustParam.buildSpringDataPageRequest());
		cn.com.easy.utils.Page<YgProductOrderEntity> page = PageUtils.getPage(springPage);
		modelMap.addAttribute("page",page);
		return "/member/order/order_list";
	}
	/**
	 * 准备领奖
	 *@param modelMap
	 *@param req
	 *@param res
	 *@param userYgId
	 *@return
	 * @author lvzf 2016年10月9日
	 */
	@RequestMapping("/buy/{userYgId}")
	public String buy(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res,@PathVariable Long userYgId){
		UserEntity user=RequestUtils.getCurrentUser(req);
		//如果没填写收货地址，自动跳转到收货地址；填写完收货地址在自动返回过来
		List<AddressEntity> addrs=addressDao.findByUserId(user.getId());
		if(addrs.size()==0){
			modelMap.addAttribute("fromUrl","/member/order/buy/"+userYgId);
			return memberController.addr(modelMap, req, res);
		}
		UserYgEntity ygRecord=userYgDao.findOne(userYgId);
		if(ygRecord==null){
			return "redirect:/member/";
		}
		if(!user.getId().equals(ygRecord.getBuyUserId())){
			return "redirect:/member/";
		}
		//已领奖
		if(ygRecord.getStatus()!=0){
			return "redirect:/member/";
		}
		modelMap.addAttribute("ygRecord",ygRecord);
		List<AddressEntity> addressList = addressDao.findByUserId(user.getId());
		modelMap.addAttribute("addressList", addressList);
		return "/member/order/order_buy";
	}
	/**
	 * 领奖
	 *@param modelMap
	 *@param req
	 *@param res
	 *@param order
	 *@return
	 * @author lvzf 2016年10月9日
	 */
	@RequestMapping("/buy/save")
	public String buy(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res,YgProductOrderEntity order,Long addrId){	
		UserEntity user=RequestUtils.getCurrentUser(req);
		UserYgEntity ygRecord=userYgDao.findOne(order.getUserYgId());
		AddressEntity addr=addressDao.findOne(addrId);
		if(ygRecord==null || addr==null){
			return "redirect:/member/";
		}
		if(!user.getId().equals(ygRecord.getBuyUserId())){
			return "redirect:/member/";
		}
		if(ygRecord.getStatus()!=0){
			return "redirect:/member/";
		}
		YgProductEntity ygProduct=ygProductDao.findOne(ygRecord.getYgProductId());
		if(ygProduct==null){
			return "redirect:/member/";
		}
		ygRecord.setStatus(1);//已领奖
		userYgDao.save(ygRecord);
		//生成订单
		order.setUserId(user.getId());
		order.setAddrJson(FastJSONUtils.toJsonString(addr));
		order.setOrderId(System.currentTimeMillis()+""+user.getId());
		order.setYgProductJson(FastJSONUtils.toJsonString(ygProduct));
		order.setStatus(YgProductOrderStatusEnum.PAYED.getValue());
		ygProductOrderDao.save(order);
		return "redirect:/member/order/";
	}
	/**
	 * 确认收货
	 *@param modelMap
	 *@param req
	 *@param res
	 *@param orderId
	 * @author lvzf 2016年10月11日
	 */
	@RequestMapping("/sureAccept.json")
	public void sureAccept(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, Long orderId) {
		try { 
				// 修改
				YgProductOrderEntity tempEntity = ygProductOrderDao.findOne(orderId);
			 
				if(tempEntity.getStatus()!=YgProductOrderStatusEnum.SENDED.getValue()){
					throw new BusinessException("不是"+YgProductOrderStatusEnum.SENDED.getName()+"状态，不能收货");
				} 
				tempEntity.setStatus(YgProductOrderStatusEnum.ACCEPTED.getValue());
				tempEntity.setAcceptTime(new Date());
				ygProductOrderDao.save(tempEntity);
				ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, ""));
	 
		} catch (BusinessException ex) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, ex.getLocalizedMessage()));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "确认收货异常"));
		}
	}
}
