package cn.com.yyg.front.web.wap.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.easy.utils.FastJSONUtils;
import cn.com.easy.utils.PageRequest;
import cn.com.easy.utils.PageRequestUtils;
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.yyg.base.em.YgProductStatusEnum;
import cn.com.yyg.base.entity.YgProductEntity;
import cn.com.yyg.base.utils.PageUtils;
import cn.com.yyg.front.dao.YgProductDao;
import cn.com.yyg.front.dto.ContentDto;
import cn.com.yyg.front.service.common.CommonService;

import com.google.common.collect.Lists;

/**
 * 最新揭晓
 * 
 * @author linwk 2016年9月20日
 * 
 */
@Controller
@RequestMapping("/wap/win")
public class WapJiexiaoController {

	/** 前端公共服务 */
	@Autowired
	private CommonService frontCommonService;

	/** 云购 */
	@Autowired
	private YgProductDao ygProductDao;

	@RequestMapping
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		// 删除保存的信息
		return "/wap/jiexiao/jiexiao";
	}

	@RequestMapping("/{pageNo}")
	public void list(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, @PathVariable Integer pageNo, Integer amount) {
		PageRequest pageRequest = new PageRequest(pageNo, amount);
		pageRequest.setOrderBy("publishDate");
		pageRequest.setOrderDir("desc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		Page<YgProductEntity> springPage = ygProductDao.findByStatus(YgProductStatusEnum.END.getValue(), pb);
		cn.com.easy.utils.Page<YgProductEntity> page = PageUtils.getPage(springPage);
		List<ContentDto> contents = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(page.getResult())) {
			for (YgProductEntity ygProductEntity : page.getResult()) {
				String html = "<div class=\"pic\">";
				html += " <a href=\"/product/" + ygProductEntity.getId() + ".html\"><img src=\"" + ygProductEntity.getLogoPath() + "\"></a>";
				html += " </div>";
				html += "  <div class=\"info\">";
				html += "   <p>";
				html += "     来自：<span>" + ygProductEntity.getWinUserIpAddr()+ "</span>";
				html += "    </p>";
				html += "   <p>";
				html += "    获得者：<a href=\"/user/" + ygProductEntity.getWinUserId() + "\" class=\"color\">" + ygProductEntity.getWinUserNickName() + "</a>";
				html += "   </p>";
				html += "  <p>";
				html += "  本期参与：<span class=\"color\">" + ygProductEntity.getWinUserBuyNum() + "</span>人次";
				html += " </p>";
				html += " <p>";
				html += "  幸运号码：<span class=\"color\">" + ygProductEntity.getWinNo() + "</span>";
				html += "  </p>";
				html += "   <p>";
				html += "     揭晓时间：<span title=\"ygProductEntity.getWinDate()\">" + ygProductEntity.getWinDate() + "</span>";
				html += "    </p>";
				html += "  </div>";
				ContentDto contentDto = new ContentDto();
				contentDto.setContent(html);
				contents.add(contentDto);
			}
		}
		ResponseOutputUtils.renderJson(res, FastJSONUtils.toJsonString(contents));
	}
}
