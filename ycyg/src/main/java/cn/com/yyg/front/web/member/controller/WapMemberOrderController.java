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

import org.apache.commons.collections.CollectionUtils;
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
import cn.com.yyg.base.utils.DateUtil;
import cn.com.yyg.base.utils.PageUtils;
import cn.com.yyg.front.dao.AddressDao;
import cn.com.yyg.front.dao.UserDao;
import cn.com.yyg.front.dao.UserYgDao;
import cn.com.yyg.front.dao.YgProductDao;
import cn.com.yyg.front.dao.YgProductOrderDao;
import cn.com.yyg.front.dao.YgShareDao;
import cn.com.yyg.front.dto.ContentDto;
import cn.com.yyg.front.utils.RequestUtils;

import com.google.common.collect.Lists;

@Controller
@RequestMapping("/wap/member/order")
public class WapMemberOrderController {

	private Logger logger = LoggerFactory.getLogger(WapMemberOrderController.class);

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

	/** 用户晒单 */
	@Autowired
	private YgShareDao ygShareDao;

	@Autowired
	private WapMemberController memberController;
	
	/** 用户 */
	@Autowired
	private UserDao userDao;

	@RequestMapping
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, Long status) {
		UserEntity user = userDao.findOne(RequestUtils.getCurrentUser(req).getId());
		// 删除保存的分页页码信息
		// 未发货数量
		// 已发货数量
		// 已完成数量
		modelMap.addAttribute("payed", ygProductOrderDao.countByUserIdAndStatus(user.getId(), YgProductOrderStatusEnum.PAYED.getValue()));
		modelMap.addAttribute("sended", ygProductOrderDao.countByUserIdAndStatus(user.getId(), YgProductOrderStatusEnum.SENDED.getValue()));
		modelMap.addAttribute("accepted", ygProductOrderDao.countByUserIdAndStatus(user.getId(), YgProductOrderStatusEnum.ACCEPTED.getValue()));
		modelMap.addAttribute("status", status);
		return "/wap_member/order/order_list";
	}

	@RequestMapping("/orderAjax/{pageNo}")
	public void orderAjax(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Integer pageNo, Long status, Integer amount) {
		PageRequestParamDTO pageReqeustParam = new PageRequestParamDTO();
		pageReqeustParam.setPageNumber(pageNo);
		pageReqeustParam.setPageSize(amount);
		final UserEntity user = userDao.findOne(RequestUtils.getCurrentUser(req).getId());
		final Long finalStatusLong = status;
		Page<YgProductOrderEntity> springPage = ygProductOrderDao.findAll(new Specification<YgProductOrderEntity>() {
			public Predicate toPredicate(Root<YgProductOrderEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<Integer> t_status = root.get("status");
				Path<Date> createTime = root.get("createTime");
				List<Predicate> p = Lists.newArrayList();
				Path<Long> userId = root.get("userId");
				p.add(cb.equal(userId, user.getId()));
				// 订单状态
				if (finalStatusLong != null) {
					p.add(cb.equal(t_status, finalStatusLong));
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
		List<ContentDto> contents = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(page.getResult())) {
			for (YgProductOrderEntity item : page.getResult()) {
				String html = "";
				html += "<dt class=\"db-order color03\">";
				html += "    <div style=\"float:right\">";
				html += "        <span>订单总额：" + item.getYgProduct().getTotalNum() + "</span>";
				html += "            </div>";
				html += "    订单号：<span>" + item.getOrderId() + "</span><br>";
				html += "    下单时间：<span>" + DateUtil.getDateToString(item.getYgProduct().getCreateTime(), DateUtil.YMDhhmmss) + "</span><span style=\"color: #000\"></span>";
				html += "</dt>";
				html += "<dt class=\"ui-clr\" style=\"min-height:75px;\">";
				html += "    <div class=\"db-img\"><a href=\"/product/" + item.getYgProduct().getId() + "\" target=\"_blank\"><img src=\"" + item.getYgProduct().getLogoPath()
						+ "\" alt=\"" + item.getYgProduct().getName() + "\" width=\"90\" /></a></div>";
				html += "    <div class=\"db-txt\">";
				html += "        <p class=\"p1\"><a href=\"/product/" + item.getYgProduct().getId() + "\" target=\"_blank\">" + item.getYgProduct().getName() + "</a></p>";
				html += "                <div>";
				html += "            <span class=\"color01\">" + item.getStatusName() + "</span><br/>";
				if (item.getStatus() == YgProductOrderStatusEnum.PAYED.getValue()) {

				}
				if (item.getStatus() == YgProductOrderStatusEnum.SENDED.getValue()) {
					html += "                                <a href=\"javascript:;\" onclick=\"zz_confirm('您确认已经收到该订单商品？','/member/order/finish_order/" + item.getId()
							+ "')\" class=\"btn-small\">确认收货</a>";
				}
				// 判断是否收货了
				if (item.getStatus() == YgProductOrderStatusEnum.ACCEPTED.getValue()) {
					if (item.getShareId() != null) {
						html += "                        <a href=\"/share/" + item.getShareId() + "\" class=\"btn-small\">查看晒单</a>         </div>";
					} else {
						html += "                        <a href=\"/member/post_share/" + item.getId() + "\" class=\"btn-small\">晒单</a>         </div>";
					}
				}
				html += "                                </div>";
				html += "    </div>";
				html += "</dt>";
				html += "<dd>";
				html += "    <table cellpadding=\"0\" cellspacing=\"0\">";
				html += "        <tr>";
				html += "            <th>物流信息</th>";
				html += "            <td>";
				html += "                <div><span>" + item.getAddress().getTrueName() + "</span> (" + item.getAddress().getMobile() + ") " + item.getAddress().getProvinceName()
						+ item.getAddress().getCityName() + item.getAddress().getAreaName() + item.getAddress().getAreaInfo() + " </div>";
				html += "                            </td>";
				html += "        </tr>";
				html += "    </table>";
				html += "</dd>";

				ContentDto contentDto = new ContentDto();
				contentDto.setContent(html);
				contents.add(contentDto);
			}
		}
		ResponseOutputUtils.renderJson(res, FastJSONUtils.toJsonString(contents));
	}

	/**
	 * 准备领奖
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param userYgId
	 * @return
	 * @author lvzf 2016年10月9日
	 */
	@RequestMapping("/buy/{userYgId}")
	public String buy(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long userYgId) {
		UserEntity user = userDao.findOne(RequestUtils.getCurrentUser(req).getId());
		// 如果没填写收货地址，自动跳转到收货地址；填写完收货地址在自动返回过来
		List<AddressEntity> addrs = addressDao.findByUserId(user.getId());
		if (addrs.size() == 0) {
			modelMap.addAttribute("fromUrl", "/member/order/buy/" + userYgId);
			return memberController.address(modelMap, req, res);
		}
		UserYgEntity ygRecord = userYgDao.findOne(userYgId);
		if (ygRecord == null) {
			return "redirect:/member/";
		}
		if (!user.getId().equals(ygRecord.getBuyUserId())) {
			return "redirect:/member/";
		}
		modelMap.addAttribute("ygRecord", ygRecord);
		List<AddressEntity> addressList = addressDao.findByUserId(user.getId());
		modelMap.addAttribute("addressList", addressList);
		modelMap.addAttribute("userYgId", userYgId);
		return "/wap_member/order/order_buy";
	}

	/**
	 * 领奖
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param order
	 * @return
	 * @author lvzf 2016年10月9日
	 */
	@RequestMapping("/buy/save")
	public String buy(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, YgProductOrderEntity order, Long addrId) {
		UserEntity user = userDao.findOne(RequestUtils.getCurrentUser(req).getId());
		UserYgEntity ygRecord = userYgDao.findOne(order.getUserYgId());
		AddressEntity addr = addressDao.findOne(addrId);
		if (ygRecord == null || addr == null) {
			return "redirect:/member/";
		}
		if (!user.getId().equals(ygRecord.getBuyUserId())) {
			return "redirect:/member/";
		}
		if (ygRecord.getStatus() != 0) {
			return "redirect:/member/";
		}
		YgProductEntity ygProduct = ygProductDao.findOne(ygRecord.getYgProductId());
		if (ygProduct == null) {
			return "redirect:/member/";
		}
		ygRecord.setStatus(1);// 已领奖
		userYgDao.save(ygRecord);
		// 生成订单
		order.setUserId(user.getId());
		order.setAddrJson(FastJSONUtils.toJsonString(addr));
		order.setOrderId(System.currentTimeMillis() + "" + user.getId());
		order.setYgProductJson(FastJSONUtils.toJsonString(ygProduct));
		order.setStatus(YgProductOrderStatusEnum.PAYED.getValue());
		ygProductOrderDao.save(order);
		return this.index(modelMap, req, res, null);
	}

	/**
	 * 确认收货
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param orderId
	 * @author lvzf 2016年10月11日
	 */
	@RequestMapping("/finish_order/{orderId}")
	public String sureAccept(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Long orderId) {
		try {
			// 修改
			YgProductOrderEntity tempEntity = ygProductOrderDao.findOne(orderId);
			if (tempEntity.getStatus() != YgProductOrderStatusEnum.SENDED.getValue()) {
				throw new BusinessException("不是" + YgProductOrderStatusEnum.SENDED.getName() + "状态，不能收货");
			}
			tempEntity.setStatus(YgProductOrderStatusEnum.ACCEPTED.getValue());
			tempEntity.setAcceptTime(new Date());
			ygProductOrderDao.save(tempEntity);
			// ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("",
			// true, ""));

		} catch (BusinessException ex) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, ex.getLocalizedMessage()));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			// ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("",
			// false, "确认收货异常"));
		}
		// 到已完成页面
		return index(modelMap, req, res, 3l);
	}

}
