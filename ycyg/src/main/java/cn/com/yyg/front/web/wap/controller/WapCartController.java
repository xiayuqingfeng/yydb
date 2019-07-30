package cn.com.yyg.front.web.wap.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.easy.dto.MessageDTO;
import cn.com.easy.exception.BusinessException;
import cn.com.easy.utils.FastJSONUtils;
import cn.com.easy.utils.IPUtils;
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.yyg.base.em.YgProductStatusEnum;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.base.entity.YgCartEntity;
import cn.com.yyg.base.entity.YgProductEntity;
import cn.com.yyg.front.dao.YgCartDao;
import cn.com.yyg.front.dao.YgProductDao;
import cn.com.yyg.front.service.YgBuyService;
import cn.com.yyg.front.utils.CookieUtils;
import cn.com.yyg.front.utils.RequestUtils;
import cn.com.yyg.front.web.pub.dto.CartDto;

import com.google.common.collect.Lists;

/**
 * 购物车
 * 
 * @author lvzf 2016年8月27日
 * 
 */
@Controller
@RequestMapping("/wap/cart")
public class WapCartController {
	private Logger logger = LoggerFactory.getLogger(WapCartController.class);
	private final static String CART_COOKIE_NAME = "CART_ID_COOKIE";
	/** 云购 */
	@Autowired
	private YgProductDao ygProductDao;
	/** 云购购物车 */
	@Autowired
	private YgCartDao ygCartDao;
	@Autowired
	private YgBuyService ygProductService;

	/**
	 * 我的购物车明细列表
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @param buyNum
	 * @return
	 * @author lvzf 2016年8月25日
	 * @throws IOException
	 */
	@RequestMapping
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, Long id) throws IOException {
		//设置返回地址
		RequestUtils.setBackUrl(req);
		UserEntity user = RequestUtils.getCurrentUser(req);
		// 需用户登录
		if (user == null) {
			res.sendRedirect("/login");
			return null;
		}
		List<YgCartEntity> list = ygCartDao.findByUserId(user.getId());
		Map<Long, CartDto> cartMap = new LinkedHashMap<Long, CartDto>();
		for (YgCartEntity y : list) {
			CartDto dto = new CartDto(y.getYgProductId(), y.getBuyNum());
			dto.setUpdateDate(y.getLastModifyTime().getTime());
			cartMap.put(y.getYgProductId(), dto);
		}
		List<CartDto> results = getCartList(modelMap, req, res,cartMap, false);
		modelMap.addAttribute("results", results);

		return "/wap/buy/cart_index";
	}

	/**
	 * 添加购物车
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @param num
	 * @author lvzf 2016年8月25日
	 */

	@RequestMapping("/addCart.json")
	public void addCart(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, Long id, long buyNum) {
		try {
			if (id == null) {
				throw new BusinessException("id为空");
			}
			YgProductEntity product = ygProductDao.findOne(id);
			if (product == null) {
				throw new BusinessException("id无效");
			}
			if (product.getStatus() != YgProductStatusEnum.ING.getValue()) {
				throw new BusinessException("非进行中的商品不能加入购物车");
			}
			if (product.getLeaveNum() < buyNum) {
				throw new BusinessException("该期商品剩余" + product.getLeaveNum() + "份");
			}
			UserEntity user = RequestUtils.getCurrentUser(req);
			// 用户未登录，保存到cookie
			if (user == null) {
				String cookieVal = CookieUtils.getCookieValue(req, CART_COOKIE_NAME);
				List<CartDto> carts = Lists.newArrayList();
				if (StringUtils.isNoneBlank(cookieVal)) {
					// cartsMap.get(id)
					carts = FastJSONUtils.toArray(cookieVal, CartDto.class);
					CartDto haveCart = null;
					for (CartDto c : carts) {
						// 修改
						if (c.getId() != null && c.getId().equals(id)) {
							c.setBuyNum(c.getBuyNum() + buyNum);
							c.setUpdateDate(System.currentTimeMillis());
							haveCart = c;
							break;
						}
					}
					// 添加
					if (haveCart == null) {
						carts.add(new CartDto(id, buyNum));
					}
				} else {
					carts.add(new CartDto(id, buyNum));
				}
				CookieUtils.setCookie(req, res, CART_COOKIE_NAME, FastJSONUtils.toJsonString(carts));
			} else {
				// 添加到数据库
				extracted(req, id, buyNum, user);
			}
			this.loadCart(modelMap, req, res, false);
		} catch (BusinessException e) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, e.getMessage()));
		} catch (Exception e) {
			logger.error("添加购物车错误：", e);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "添加购物车错误"));
		}
	}

	private synchronized void extracted(HttpServletRequest req, Long id, long buyNum, UserEntity user) {
		List<YgCartEntity> list = ygCartDao.findByUserIdAndYgProductId(user.getId(), id);
		// 修改
		if (list.size() > 0) {
			YgCartEntity cart = list.get(0);
			cart.setBuyNum(cart.getBuyNum() + buyNum);
			ygCartDao.save(cart);
		} else {
			YgCartEntity cart = new YgCartEntity();
			cart.setBuyNum(buyNum);
			cart.setYgProductId(id);
			cart.setUserId(user.getId());
			String ip = IPUtils.getRealIP(req);
			cart.setClientIp(ip);
			String cityName = "";
			try {
				cityName = IPUtils.getCityNameByIP(ip, 2000, 2000);
			} catch (Exception ex) {
				logger.error("读取ip地址错误：", ex);
			}
			cart.setIpAddr(cityName);
			ygCartDao.save(cart);
		}
	}

	/**
	 * 加载购物车
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @author lvzf 2016年8月25日
	 */
	@RequestMapping("/total.json")
	public void tatal(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, boolean disLoadProduct) {
		loadCart(modelMap, req, res, true);
	}

	/**
	 * 加载购物车
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @author lvzf 2016年8月25日
	 */
	@RequestMapping("/loadCart.json")
	public void loadCart(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, boolean disLoadProduct) {
		try {
			UserEntity user = RequestUtils.getCurrentUser(req);
			List<CartDto> carts = Lists.newArrayList();
			// 用户未登录，从cookie获取
			String cookieVal = CookieUtils.getCookieValue(req, CART_COOKIE_NAME);
			if (StringUtils.isNoneBlank(cookieVal)) {
				carts = FastJSONUtils.toArray(cookieVal, CartDto.class);
			}
			// 用户登录 把cookie添加到数据库，并清空cookie
			if (user != null) {
				for (CartDto c : carts) {
					List<YgCartEntity> list = ygCartDao.findByUserIdAndYgProductId(user.getId(), c.getId());
					// 修改
					if (list.size() > 0) {
						YgCartEntity cart = list.get(0);
						cart.setBuyNum(cart.getBuyNum() + c.getBuyNum());
						ygCartDao.save(cart);
					} else {// 增加
						YgCartEntity cart = new YgCartEntity();
						cart.setBuyNum(c.getBuyNum());
						cart.setYgProductId(c.getId());
						cart.setUserId(user.getId());
						String ip = IPUtils.getRealIP(req);
						cart.setClientIp(ip);
						String cityName = "";
						try {
							cityName = IPUtils.getCityNameByIP(ip, 2000, 2000);
						} catch (Exception ex) {
							logger.error("读取ip地址错误：", ex);
						}
						cart.setIpAddr(cityName);
						ygCartDao.save(cart);
					}
				}
				CookieUtils.setCookie(req, res, CART_COOKIE_NAME, "");
				// 重新从购物车数据库中取
				List<YgCartEntity> list = ygCartDao.findByUserId(user.getId());
				carts = Lists.newArrayList();
				for (YgCartEntity ygcart : list) {
					CartDto dto = new CartDto(ygcart.getYgProductId(), ygcart.getBuyNum());
					dto.setUpdateDate(ygcart.getLastModifyTime().getTime());
					carts.add(dto);
				}
			}
			Map<Long, CartDto> cartMap = new LinkedHashMap<Long, CartDto>();
			for (CartDto c : carts) {
				if (c.getId() != null) {
					cartMap.put(c.getId(), c);
				}
			}
			List<CartDto> results = getCartList(modelMap, req, res,cartMap, disLoadProduct);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("购物车", true, results));
		} catch (Exception e) {
			logger.error("加载购物车错误：", e);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "加载购物车错误"));
			try {
				CookieUtils.setCookie(req, res, CART_COOKIE_NAME, null);
			} catch (Exception e1) {
				logger.error("加载购物车错误：", e);
			}
		}
	}

	/**
	 * 获取购物车列表页面显示对象
	 * 
	 * @param cartMap
	 *            <云购商品id,购物车对象>
	 * @return
	 * @author lvzf 2016年8月26日
	 */
	public List<CartDto> getCartList(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res,Map<Long, CartDto> cartMap, boolean disLoadProduct) {
		List<CartDto> results = Lists.newArrayList();
		UserEntity user = RequestUtils.getCurrentUser(req);
		if (cartMap.size() > 0) {
			List<Long> ygProductIds = Lists.newArrayList(cartMap.keySet());
			List<YgProductEntity> list = ygProductDao.findByIdIn(ygProductIds);
			for (YgProductEntity p : list) {
				//更新库存
				List<YgCartEntity> carts = ygCartDao.findByUserIdAndYgProductId(user.getId(), p.getId());
				CartDto dto = new CartDto();
				if (disLoadProduct==false) {
					dto.setProduct(p);
				}else {
					YgProductEntity productEntity=new YgProductEntity();
					productEntity.setId(p.getId());
					productEntity.setSinglePrice(p.getSinglePrice());
					dto.setProduct(productEntity);
				}
				CartDto c = cartMap.get(p.getId());
				if (c != null) {
					dto.setBuyNum(c.getBuyNum());
					dto.setUpdateDate(c.getUpdateDate());
					dto.setId(c.getId());
				}
				if (p.getStatus() != YgProductStatusEnum.ING.getValue() || p.getLeaveNum() <= 0) {
					dto.setSuccess(false);
					dto.setRemark("该期商品已经结束");
					if (carts.size() > 0) {
						YgCartEntity cart = carts.get(0); 
						ygCartDao.delete(cart);
						dto.setSuccess(true);
					}
					continue;
				}
				if (p.getLeaveNum() < c.getBuyNum()) {
					dto.setSuccess(false);
					dto.setRemark("该期商品剩余" + p.getLeaveNum() + "份");					 
					if (carts.size() > 0) {
						YgCartEntity cart = carts.get(0);
						cart.setBuyNum(p.getLeaveNum());
						ygCartDao.save(cart);
						dto.setSuccess(true);
					}
				} else {
					dto.setSuccess(true);
				}
				results.add(dto);
			}
		}
		// 更新时间倒序
		Collections.sort(results, new Comparator<CartDto>() {
			public int compare(CartDto obj1, CartDto obj2) {
				if (obj1.getUpdateDate() == obj2.getUpdateDate()) {
					return 0;
				}
				return obj1.getUpdateDate() - obj2.getUpdateDate() > 0 ? 1 : -1;
			}
		});
		return results;
	}

	/**
	 * 删除购物车明细
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @author lvzf 2016年8月26日
	 */
	@RequestMapping("/deleteCart.json")
	public void deleteCart(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, Long[] ids) {
		try {
			UserEntity user = RequestUtils.getCurrentUser(req);
			// 用户未登录，保存到cookie
			if (user == null) {
				String cookieVal = CookieUtils.getCookieValue(req, CART_COOKIE_NAME);
				List<CartDto> carts = Lists.newArrayList();
				if (cookieVal != null) {
					// cartsMap.get(id)
					carts = FastJSONUtils.toArray(cookieVal, CartDto.class);
					for (Long id : ids) {
						Iterator<CartDto> iterator = carts.iterator();
						while (iterator.hasNext()) {
							CartDto c = iterator.next();
							if (c.getId() != null && c.getId().equals(id)) {
								iterator.remove();
							}
						}
					}
				}
				CookieUtils.setCookie(req, res, CART_COOKIE_NAME, FastJSONUtils.toJsonString(carts));
			} else {
				// 从数据库删除
				List<Long> ygProductIds = Lists.newArrayList(ids);
				ygProductService.deteleCart(user.getId(), ygProductIds);
			}
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, ""));
		} catch (Exception e) {
			logger.error("删除购物车错误：", e);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "删除购物车错误"));
		}
	}

	@RequestMapping("/updateCartNum.json")
	public void updateCartNum(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, Long id, long buyNum) {
		try {
			UserEntity user = RequestUtils.getCurrentUser(req);
			List<YgCartEntity> carts = ygCartDao.findByUserIdAndYgProductId(user.getId(), id);
			if (carts.size() > 0) {
				YgCartEntity cart = carts.get(0);
				cart.setBuyNum(buyNum);
				ygCartDao.save(cart);
			}
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, ""));
		} catch (Exception e) {
			logger.error("更新购物车商品数量错误：", e);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "更新购物车商品数量错误"));
		}

	}
}
