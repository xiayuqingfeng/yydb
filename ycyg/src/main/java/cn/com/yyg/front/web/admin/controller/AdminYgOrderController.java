package cn.com.yyg.front.web.admin.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.easy.dto.MessageDTO;
import cn.com.easy.dto.PageRequestParamDTO;
import cn.com.easy.exception.BusinessException;
import cn.com.easy.utils.FastJSONUtils;
import cn.com.easy.utils.PageUtils;
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.yyg.base.em.YgProductOrderStatusEnum;
import cn.com.yyg.base.entity.YgProductOrderEntity;
import cn.com.yyg.front.dao.ExpressComDao;
import cn.com.yyg.front.dao.YgProductOrderDao;

import com.google.common.collect.Lists;

/**
 * 云购订单管理
 * 
 * @author lvzf 2016年9月12日
 * 
 */
@Controller
@RequestMapping("/admin/ygOrder")
public class AdminYgOrderController {

	private Logger logger = LoggerFactory.getLogger(AdminYgOrderController.class);

	/** 用户云购订单 */
	@Autowired
	private YgProductOrderDao ygProductOrderDao;
	
	/** 快递公司 */
	@Autowired
	private ExpressComDao expressComDao;
	/**
	 * 订单管理首页
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author lvzf 2016年9月12日
	 */
	// @RequestMapping()
	@RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		modelMap.addAttribute("expressComs", expressComDao.findAll());
		return "/admin/order/order_list";
	}
 
	/**
	 * 分页获取订单信息
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param pageReqeustParam
	 * @author lvzf 2016年9月12日
	 */
	@RequestMapping("/all.json")
	public void all(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, PageRequestParamDTO pageReqeustParam, final YgProductOrderEntity order) {
		try {
			Page<YgProductOrderEntity> springPage = ygProductOrderDao.findAll(new Specification<YgProductOrderEntity>() {

				public Predicate toPredicate(Root<YgProductOrderEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

					Path<Integer> t_status = root.get("status"); 
					Path<Date> createTime = root.get("createTime");
					List<Predicate> p = Lists.newArrayList(); 
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
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, page));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "分页获取订单信息异常"));
		}
	}

	/**
	 * 确认发货
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param shareEntity
	 * @author lvzf 2016年9月12日
	 */
	@RequestMapping("/sureSend.json")
	public void sureSend(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, YgProductOrderEntity order,Long expComId) {
		try { 
				// 修改
				YgProductOrderEntity tempEntity = ygProductOrderDao.findOne(order.getId());
				//快递公司
				if(expComId==null){
					throw new BusinessException("请选择快递公司");
				}
				if(tempEntity.getStatus()!=YgProductOrderStatusEnum.PAYED.getValue()){
					throw new BusinessException("不是"+YgProductOrderStatusEnum.SENDED.getName()+"状态，不能发货");
				}
				if(expComId.longValue()!=0){
					tempEntity.setExpComJson(FastJSONUtils.toJsonString(expressComDao.findOne(expComId)));
				}
				//快递单号
				tempEntity.setExpNo(order.getExpNo());
				tempEntity.setStatus(YgProductOrderStatusEnum.SENDED.getValue());
				tempEntity.setSendTime(new Date());
				ygProductOrderDao.save(tempEntity);
				ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, ""));
	 
		} catch (BusinessException ex) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, ex.getLocalizedMessage()));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "确认发货异常"));
		}
	}

 

}
