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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.easy.dto.MessageDTO;
import cn.com.easy.dto.PageRequestParamDTO;
import cn.com.easy.exception.BusinessException;
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.yyg.base.entity.ArticleCateEntity;
import cn.com.yyg.base.entity.ArticleEntity;
import cn.com.yyg.base.entity.ContentEntity;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.base.utils.PageUtils;
import cn.com.yyg.front.dao.AccessoryDao;
import cn.com.yyg.front.dao.ArticleCateDao;
import cn.com.yyg.front.dao.ArticleDao;
import cn.com.yyg.front.dao.ContentDao;
import cn.com.yyg.front.utils.RequestUtils;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;

/**
 * 文库管理
 * 
 * @author nibili 2016年5月24日
 * 
 */
@Controller
@RequestMapping("/admin/articles")
public class AdminArticlesController {

	private Logger logger = LoggerFactory.getLogger(AdminArticlesController.class);

	/** 文章dao */
	@Autowired
	private ArticleDao articleDao;
	/** 大字段 */
	@Autowired
	private ContentDao contentDao;
	/** 文件dao */
	@Autowired
	private AccessoryDao accessoryDao;
	/** 文章分类dao */
	@Autowired
	private ArticleCateDao articleCateDao;

	/**
	 * 文库管理首页
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param type
	 * @return
	 * @author nibili 2016年6月8日
	 */
	@RequestMapping
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		// 设置分类list
		this.setCateList(modelMap);
		return "/admin/articles/articles";
	}

	/**
	 * 分页获取文章
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param pageReqeustParam
	 * @param type
	 * @author nibili 2016年6月8日
	 */
	@RequestMapping("/all.json")
	public void all(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res,final PageRequestParamDTO pageReqeustParam,final Long cateId ) {
		Page<ArticleEntity> springPage = articleDao.findAll(new Specification<ArticleEntity>() {

			public Predicate toPredicate(Root<ArticleEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Path<Integer> deleteStatus = root.get("deleteStatus");
				Path<String> t_cateId = root.get("articleCateId");
				Path<String> t_title = root.get("title");

				Path<Date> createTime = root.get("createTime");
				Path<Integer> seqNo = root.get("seqNo");
				List<Predicate> p = Lists.newArrayList();
				p.add(cb.equal(deleteStatus, false));
				//商品分类
				if(cateId!=null){
					p.add(cb.equal(t_cateId,cateId));
				}
				if (!StringUtils.isEmpty(pageReqeustParam.getSearchText())) {
					p.add(cb.like(t_title, "%" + pageReqeustParam.getSearchText() + "%"));
				}
				if (p != null) {
					query.where(p.toArray(new Predicate[] {})); // 这里可以设置任意条查询条件
				}
				List<Order> orders = Lists.newArrayList();
				orders.add(cb.asc(seqNo));
				orders.add(cb.desc(createTime));
				query.orderBy(orders);
				return null;
			}
		}, pageReqeustParam.buildSpringDataPageRequest());
		cn.com.easy.utils.Page<ArticleEntity> page = PageUtils.getPage(springPage);
		ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, page));
	}

	/**
	 * 添加文章页
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param type
	 * @return
	 * @author nibili 2016年6月8日
	 */
	@RequestMapping("/add")
	public String addArticle(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		// 分类
		this.setCateList(modelMap);
		return "/admin/articles/articles_add";
	}

	/**
	 * 编辑文章
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @return
	 * @author nibili 2016年6月8日
	 */
	@RequestMapping("/edit")
	public String editArticle(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, Long id) {
		// 分类
		this.setCateList(modelMap);
		ArticleEntity articleEntity = articleDao.findOne(id);
		ContentEntity contentValue = null;
		if (articleEntity.getContentId() != null) {
			contentValue = contentDao.findOne(articleEntity.getContentId());
			articleEntity.setContent(contentValue.getContent());
		}
		modelMap.addAttribute("content", articleEntity);
		return "/admin/articles/articles_add";
	}

	/**
	 * 保存文章
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param photoFile
	 * @param articleEntity
	 * @param contentString
	 * @auth nibili 2016年6月10日 上午2:04:59
	 */
	@RequestMapping("/save.json")
	public void itemContentEdit(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res,  ArticleEntity articleEntity) {
		try {
			UserEntity user = RequestUtils.getCurrentAdminUser(req);
			ArticleEntity articleEntityTemp = new ArticleEntity();
			if (articleEntity.getId() != null) {
				// 更新
				articleEntityTemp = articleDao.findOne(articleEntity.getId());
				if (articleEntityTemp == null) {
					throw new BusinessException("ID无效");
				}
			} else {
				// 新增
				articleEntityTemp = articleEntity;
				articleEntityTemp.setCreateBy(user.getUserName());
				articleEntityTemp.setCreateById(user.getId());
				articleEntityTemp.setUserId(user.getId());
			} 
			// 保存正文
			ContentEntity contentValue = null;
			if (articleEntityTemp.getContentId() != null) {
				contentValue = contentDao.findOne(articleEntityTemp.getContentId());
			}
			if (contentValue == null) {
				contentValue = new ContentEntity();
				contentValue.setCreateBy(user.getUserName());
			}
			//
			contentValue.setLastModifyBy(user.getUserName());
			contentValue.setContent(articleEntity.getContent());
			contentDao.save(contentValue);
			// 大字段 id
			articleEntityTemp.setContentId(contentValue.getId());
			
			articleEntityTemp.setTitle(articleEntity.getTitle());
			articleEntityTemp.setSummary(articleEntity.getSummary());
			articleEntityTemp.setLinkUrl(articleEntity.getLinkUrl());
			articleEntityTemp.setArticleCateId(articleEntity.getArticleCateId());
			if(articleEntity.getArticleCateId()!=null){
				ArticleCateEntity cate=	articleCateDao.findOne(articleEntity.getArticleCateId());
				if(cate!=null){
					articleEntityTemp.setParentCateName(cate.getName());
				}
			}
			articleEntityTemp.setPinyin(articleEntity.getPinyin());
			articleEntityTemp.setSeqNo(articleEntity.getSeqNo());
			articleDao.save(articleEntityTemp);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, " 保存文章成功"));
		} catch (BusinessException ex) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, ex.getMessage()));
		} catch (Exception ex) {
			logger.error(" 保存文章错误：", ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, " 保存文章错误"));
		}
	}

	/**
	 * 删除文章
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @auth nibili 2016年6月10日 上午2:15:14
	 */
	@RequestMapping("/delete.json")
	public void deleteArticle(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, long id) {
		try {
			ArticleEntity articleEntity = articleDao.findOne(id);
			articleEntity.setDeleteStatus(true);
			articleDao.save(articleEntity);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, ""));
		} catch (Exception ex) {
			logger.error("删除文章错误：", ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "删除文章错误"));
		}
	}

	/**
	 * 设置分类list到modelMap中
	 * 
	 * @param modelMap
	 * @param type
	 * @auth nibili 2016年6月10日 上午1:09:54
	 */
	private void setCateList(ModelMap modelMap) {
		// 分类
		List<ArticleCateEntity> cateList = articleCateDao.findAll();
		if (CollectionUtils.isNotEmpty(cateList) == true) {
			Table<Long, Long, ArticleCateEntity> tables = HashBasedTable.create();
			for (ArticleCateEntity articleCateEntity : cateList) {
				if (articleCateEntity.getParentId() == null) {
					tables.put(articleCateEntity.getId(), -1l, articleCateEntity);
				} else {
					tables.put(articleCateEntity.getId(), articleCateEntity.getParentId(), articleCateEntity);
				}
			}
			// 结果集，根节点
			List<ArticleCateEntity> resultList = Lists.newArrayList();
			List<ArticleCateEntity> rootList = Lists.newArrayList(tables.column(-1l).values());
			for (ArticleCateEntity articleCateEntity : rootList) {
				this.getTreeList("1", articleCateEntity, resultList, tables);
			}
			modelMap.addAttribute("cateList", resultList);
		}
	}

	/**
	 * 查找给定元素的子元素
	 * 
	 * @param articleCateEntity
	 * @param tables
	 * @return
	 * @auth nibili 2016年6月10日 上午12:02:25
	 */
	private void getTreeList(String nameStart, ArticleCateEntity articleCateEntity, List<ArticleCateEntity> result, Table<Long, Long, ArticleCateEntity> tables) {
		articleCateEntity.setName(nameStart + "-" + articleCateEntity.getName());
		result.add(articleCateEntity);
		// 查找子集
		List<ArticleCateEntity> list = Lists.newArrayList(tables.column(articleCateEntity.getId()).values());
		if (CollectionUtils.isNotEmpty(list) == true) {
			for (int i = 0, len = list.size(); i < len; i++) {
				ArticleCateEntity articleCateEntityTemp = list.get(i);
				getTreeList("　" + nameStart + "." + (i + 1), articleCateEntityTemp, result, tables);
			}
		}
	}

}
