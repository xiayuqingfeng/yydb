package cn.com.yyg.front.web.pub.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.easy.utils.PageRequest;
import cn.com.easy.utils.PageRequestUtils;
import cn.com.yyg.base.em.YgProductStatusEnum;
import cn.com.yyg.base.entity.YgProductEntity;
import cn.com.yyg.base.utils.PageUtils;
import cn.com.yyg.front.dao.YgProductDao;
import cn.com.yyg.front.service.common.CommonService;
/**
 * 最新揭晓
 * @author lvzf	2016年9月18日
 *
 */
@Controller
@RequestMapping("/win")
public class PubJiexiaoController {
	/** 前端公共服务 */
	@Autowired
	private CommonService frontCommonService;
	/** 云购 */
	@Autowired
	private YgProductDao ygProductDao;
	@RequestMapping
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) { 
		return this.list(modelMap, req, res, 1);
	}
	@RequestMapping("/{pageNo}")
	public String list(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res,@PathVariable Integer pageNo){
		
		PageRequest pageRequest = new PageRequest(pageNo, 30);
		pageRequest.setOrderBy("publishDate");
		pageRequest.setOrderDir("desc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		Page<YgProductEntity> springPage=ygProductDao.findByStatus(YgProductStatusEnum.END.getValue(), pb);
		cn.com.easy.utils.Page<YgProductEntity> page = PageUtils.getPage(springPage);
		 
		modelMap.addAttribute("page",page);
		return "/pub/jiexiao/jiexiao";
	}
}
