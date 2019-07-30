package cn.com.yyg.front.web.pub.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;

import cn.com.yyg.base.entity.ArticleCateEntity;
import cn.com.yyg.base.entity.ArticleEntity;
import cn.com.yyg.base.entity.ContentEntity;
import cn.com.yyg.front.dao.ArticleCateDao;
import cn.com.yyg.front.dao.ArticleDao;
import cn.com.yyg.front.dao.ContentDao;

/**
 * 文章
 * @author lvzf	2016年10月17日
 *
 */
@Controller
@RequestMapping
public class PubArticleController {
	/** 文章dao */
	@Autowired
	private ArticleDao articleDao;
	/** 文章分类dao */
	@Autowired
	private ArticleCateDao articleCateDao;

	/** 内容 Dao */
	@Autowired
	private ContentDao contentDao;
	/**
	 * 公告
	 *@param modelMap
	 *@param req
	 *@param res
	 *@param id
	 *@return
	 * @author lvzf 2016年10月18日
	 */
	@RequestMapping("/news")
	public String news(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) { 
		//公告
		 List<ArticleCateEntity>  cates=Lists.newArrayList();
		 cates.add(articleCateDao.findOne(6L)); 
		 cates.addAll(articleCateDao.findByParentId(6L)); 
		 List<ArticleEntity> articles=Lists.newArrayList();
		 for(ArticleCateEntity a:cates){ 
			 a.setArticles(articleDao.findByCate(a.getId()));
			 articles.addAll(a.getArticles());
		 }

		 if(articles.size()>0){
			 return this.news(modelMap, req, res, articles.get(0).getId());
		 }
		return "redirect:/";
	}
	@RequestMapping("/news/{id}")
	public String news(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res,@PathVariable Long id) { 
		ArticleEntity article=articleDao.findOne(id);
		ContentEntity content = contentDao.findOne(article.getContentId());
		if (content != null) {
			article.setContent(content.getContent());
		}
		modelMap.addAttribute("article",article);	
		//公告
		 List<ArticleCateEntity>  cates=Lists.newArrayList();
		 cates.add(articleCateDao.findOne(6L)); 
		 cates.addAll(articleCateDao.findByParentId(6L)); 
		 List<ArticleEntity> articles=Lists.newArrayList();
		 for(ArticleCateEntity a:cates){ 
			 a.setArticles(articleDao.findByCate(a.getId()));
			 articles.addAll(a.getArticles());
		 }

		modelMap.addAttribute("articles",articles);	
		return "/pub/article/news_detail";
	}
	@RequestMapping("/help")
	public String help(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		return this.help(modelMap, req, res, "what");
	}
	/**
	 * 查看帮助
	 *@param modelMap
	 *@param req
	 *@param res
	 *@param pinyin
	 *@return
	 * @author lvzf 2016年10月17日
	 */
	@RequestMapping("/help/{pinyin}")
	public String help(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res,@PathVariable String pinyin) {
		if(StringUtils.isNumeric(pinyin)){
			this.help2(modelMap, req, res, Long.valueOf(pinyin));
		}
		if(StringUtils.isNoneBlank(pinyin)){
			List<ArticleEntity> list= articleDao.findByPinyin(pinyin);
			if(list.size()>0){
				ArticleEntity article=list.get(0);
				return this.help2(modelMap, req, res, article.getId());
			}
		}
		return "redirect:/";
	}
	@RequestMapping("/help/id-{id:\\d+}")
	public String help2(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res,@PathVariable Long id) {
		ArticleEntity article=articleDao.findOne(id);
		ContentEntity content = contentDao.findOne(article.getContentId());
		if (content != null) {
			article.setContent(content.getContent());
		}
		modelMap.addAttribute("article",article);	
		//帮助中心
		 List<ArticleCateEntity>  helps=articleCateDao.findByParentId(1L);
		 for(ArticleCateEntity a:helps){
			 a.setArticles(articleDao.findByCate(a.getId()));
		 }
			modelMap.addAttribute("helps",helps);	
		return "/pub/article/article_detail";
	}
}
