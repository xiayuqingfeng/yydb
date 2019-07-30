package cn.com.yyg.front.web.admin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.easy.dto.MessageDTO;
import cn.com.easy.exception.BusinessException;
import cn.com.easy.utils.FastJSONUtils;
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.yyg.base.entity.SysConfigEntity;
import cn.com.yyg.front.dto.ScoreConfigDto;
import cn.com.yyg.front.service.SysConfigService;
/**
 * 系统配置项目
 * @author lvzf	2016年10月13日
 *
 */
@Controller
@RequestMapping("/admin/config")
public class AdminConfigController {
	private Logger logger = LoggerFactory.getLogger(AdminConfigController.class);
	@Autowired
	private SysConfigService sysConfigService;  
	@RequestMapping("/{id}")
	public String config(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res,@PathVariable Long id) {
		SysConfigEntity config=sysConfigService.getById(id);
		if(config==null){
			modelMap.addAttribute("errorString", "配置项不存在，id="+id);
			return "/error/custom";
		}
		modelMap.addAttribute("config",config); 
		return "/admin/config/config_edit";
	}
	@RequestMapping("/save/{id}")
	public void save(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res,@PathVariable Long id,ScoreConfigDto scoreRule) {
		try {
			SysConfigEntity config = sysConfigService
					.getById(id);
			if (config == null) {
				 throw new BusinessException( "配置项不存在，id="+id);
			}
			Map<String,String> values=new HashMap<String,String>();
			for(String k:req.getParameterMap().keySet()){
				values.put(k, StringUtils.join(req.getParameterMap().get(k), ","));				
			}
			config.setData(FastJSONUtils.toJsonString(values));
			sysConfigService.save(config);
			req.getSession().setAttribute("sysConfig", config);
			ResponseOutputUtils.renderJson(res,
					MessageDTO.newInstance("", true, ""));
		} catch (BusinessException ex) {
			ResponseOutputUtils
					.renderJson(
							res,
							MessageDTO.newInstance("", false,
									ex.getLocalizedMessage()));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res,
					MessageDTO.newInstance("", false, "保存系统配置异常"));
		}
	}
	/**
	 * 积分规则配置
	 *@param modelMap
	 *@param req
	 *@param res
	 *@return
	 * @author lvzf 2016年10月13日
	 */
	@RequestMapping("/editScoreRule")
	public String editScoreRule(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) { 
		//modelMap.addAttribute("items",UserScoreTypeEnum.values());
		//modelMap.addAttribute("ruleMap",sysConfigService.getScoreRuleMap());
		SysConfigEntity config=sysConfigService.getById(SysConfigEntity.ID_SCORE);
		if(config!=null){ 
			ScoreConfigDto scoreRule=FastJSONUtils.toObject(config.getData(), ScoreConfigDto.class);
			modelMap.addAttribute("scoreRule",scoreRule);
		}
		return "/admin/config/config_score_rule";
	}
	@RequestMapping("/saveScoreRule")
	public void saveScoreRule(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res,ScoreConfigDto scoreRule) {
		try {
			SysConfigEntity config = sysConfigService
					.getById(SysConfigEntity.ID_SCORE);
			if (config == null) {
				config = new SysConfigEntity();
				config.setId(SysConfigEntity.ID_SCORE);
				config.setRemark("积分规则配置");
			}
			config.setData(FastJSONUtils.toJsonString(scoreRule));
			sysConfigService.save(config);
			ResponseOutputUtils.renderJson(res,
					MessageDTO.newInstance("", true, ""));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res,
					MessageDTO.newInstance("", false, "保存积分规则配置异常"));
		}
	}
}
