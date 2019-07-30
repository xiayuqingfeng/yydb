package cn.com.yyg.front.web.wap.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.easy.exception.BusinessException;
import cn.com.easy.utils.FastJSONUtils;
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.yyg.base.em.UserScoreTypeEnum;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.front.dto.MessageDto;
import cn.com.yyg.front.service.ScoreService;
import cn.com.yyg.front.utils.RequestUtils;

/**
 * 微信分享
 * 
 * @author linwk 2016年10月18日
 * 
 */
@Controller
@RequestMapping("/wap/member/weixin/share")
public class WapWeixinShareController {

	@Autowired
	private ScoreService scoreService;

	@RequestMapping("/{type}")
	public void list(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Integer type) {
		MessageDto messageDto = new MessageDto();
		try {
			UserEntity user = RequestUtils.getCurrentUser(req);
			scoreService.add(user, "+", UserScoreTypeEnum.Share, 1, "用户分享");
			messageDto.setError("0");
			messageDto.setMsg("分享成功");
		} catch (Exception e) {
			messageDto.setError("1");
			messageDto.setMsg("分享失败");
		}
		ResponseOutputUtils.renderJson(res, FastJSONUtils.toJsonString(messageDto));
	}
}
