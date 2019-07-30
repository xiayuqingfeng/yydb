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

import org.apache.commons.lang3.StringUtils;
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
import cn.com.easy.utils.PageUtils;
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.yyg.base.entity.PayRecordEntity;
import cn.com.yyg.front.dao.ExpressComDao;
import cn.com.yyg.front.dao.PayRecordDao;

import com.google.common.collect.Lists;

/**
 * 支付记录
 * 
 * @author linwk 2016年10月21日
 * 
 */
@Controller
@RequestMapping("/admin/payRecord")
public class AdminPayRecordController {

	private Logger logger = LoggerFactory.getLogger(AdminPayRecordController.class);

	/** 用户云购订单 */
	@Autowired
	private PayRecordDao payRecordDao;

	/** 快递公司 */
	@Autowired
	private ExpressComDao expressComDao;

	/**
	 * 支付记录管理首页
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @author linwk 2016年10月21日
	 */
	@RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		return "/admin/payRecord/payRecord";
	}

	/**
	 * 分页获取信息
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param pageReqeustParam
	 * @param order
	 * @author linwk 2016年10月21日
	 */
	@RequestMapping("/all.json")
	public void all(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, PageRequestParamDTO pageReqeustParam, PayRecordEntity orderT) {
		try {
			final String searchTextString = pageReqeustParam.getSearchText();
			final PayRecordEntity order = orderT;
			Page<PayRecordEntity> springPage = payRecordDao.findAll(new Specification<PayRecordEntity>() {
				public Predicate toPredicate(Root<PayRecordEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					Path<Date> createTime = root.get("createTime");
					List<Predicate> p = Lists.newArrayList();
					Path<String> userName = root.get("userName");
					Path<Integer> payStatus = root.get("payStatus");
					Path<Integer> payType = root.get("payType");
					if (StringUtils.isNoneBlank(searchTextString)) {
						p.add(cb.equal(userName, searchTextString));
					}
					if ((Object) order.getPayStatus() != null && StringUtils.isNoneBlank(((Object) order.getPayStatus()).toString())) {
						p.add(cb.equal(payStatus, order.getPayStatus()));
					}
					if ((Object) order.getPayType() != null && StringUtils.isNoneBlank(((Object) order.getPayType()).toString())) {
						p.add(cb.equal(payType, order.getPayType()));
					}
					// 订单状态
					if (p != null) {
						query.where(p.toArray(new Predicate[] {})); // 这里可以设置任意条查询条件
					}
					List<Order> orders = Lists.newArrayList();
					orders.add(cb.desc(createTime));
					query.orderBy(orders);
					return null;
				}
			}, pageReqeustParam.buildSpringDataPageRequest());
			cn.com.easy.utils.Page<PayRecordEntity> page = PageUtils.getPage(springPage);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, page));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "分页获取支付记录异常"));
		}
	}

}
