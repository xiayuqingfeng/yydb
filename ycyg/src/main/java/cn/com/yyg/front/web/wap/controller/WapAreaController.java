package cn.com.yyg.front.web.wap.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.easy.dto.MessageDTO;
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.yyg.base.entity.AreaEntity;
import cn.com.yyg.front.dao.AreaDao;

import com.google.common.collect.Lists;

/**
 * 地区管理
 * 
 * @author linwk 2016年10月5日
 * 
 */
@Controller
@RequestMapping("/wap/area")
public class WapAreaController {
	private Logger logger = LoggerFactory.getLogger(WapAreaController.class);

	/** 地区dao */
	@Autowired
	private AreaDao areaDao;

	/**
	 * 获取区域列表
	 * 
	 * @param modelMap
	 * @param req
	 * @param response
	 * @param parentId
	 * @author linwk 2016年10月5日
	 */
	@RequestMapping("/getChilds.json")
	public void getChilds(ModelMap modelMap, HttpServletRequest req, HttpServletResponse response, Long parentId) {
		try {
			List<AreaEntity> list = Lists.newArrayList();
			if (parentId == null) {
				list = areaDao.findParent();
			} else {
				list = areaDao.findByParentId(parentId);
			}
			ResponseOutputUtils.renderJson(response, MessageDTO.newInstance("", true, list));
		} catch (Exception ex) {
			logger.error("获取区域异常!parent id：" + parentId, ex);
			ResponseOutputUtils.renderJson(response, MessageDTO.newInstance("", false, "获取区域地址异常，请稍后再试!"));
		}
	}

}
