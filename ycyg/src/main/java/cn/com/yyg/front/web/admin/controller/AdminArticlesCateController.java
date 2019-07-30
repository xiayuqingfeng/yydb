package cn.com.yyg.front.web.admin.controller;

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
import cn.com.easy.dto.PageRequestParamDTO;
import cn.com.easy.exception.BusinessException;
import cn.com.easy.utils.Page;
import cn.com.easy.utils.ResponseOutputUtils;
import cn.com.yyg.base.entity.ArticleCateEntity;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.front.dao.AccessoryDao;
import cn.com.yyg.front.dao.ArticleCateDao;
import cn.com.yyg.front.dao.ArticleDao;
import cn.com.yyg.front.utils.RequestUtils;

/**
 * 文库分类管理
 * 
 * @author lvzf 2016年6月7日下午9:17:02
 * 
 */
@Controller
@RequestMapping("/admin/articles/cate")
public class AdminArticlesCateController {

	private Logger logger = LoggerFactory.getLogger(AdminArticlesCateController.class);

	/** 文章分类dao */
	@Autowired
	private ArticleCateDao articleCateDao;
	/** 文章dao */
	@Autowired
	private ArticleDao articleDao;
	/** 文件dao */
	@Autowired
	private AccessoryDao accessoryDao;

	/**
	 * 分类管理首页
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @return
	 * @auth lvzf 2016年6月7日 下午9:17:50
	 */
	@RequestMapping
	public String index(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res) {
		return "/admin/articles/articles_cate";
	}

	/**
	 * 分页获取分类信息
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param pageReqeustParam
	 * @author lvzf 2016年6月7日
	 */
	@RequestMapping("/all.json")
	public void all(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, PageRequestParamDTO pageReqeustParam) {
		try {
			Page<ArticleCateEntity> page = new Page<ArticleCateEntity>();
			List<ArticleCateEntity> list = articleCateDao.findAll();
			page.setResult(list);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, page));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "分页获取会员信息异常"));
		}
	}

	/**
	 * 异步删除分类
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param id
	 * @author lvzf 2016年6月7日
	 */
	@RequestMapping("/delete.json")
	public void delete(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, Long id) {
		try {
			if (id == null) {
				throw new BusinessException("id不能为空");
			}
			ArticleCateEntity articleCateEntity = articleCateDao.findOne(id);
			if (articleCateEntity == null) {
				throw new BusinessException("找不到该对象");
			}
			// 如果有文章引用，不能删除
			if (articleDao.countByArticleCateId(id) > 0) {
				throw new BusinessException("当前分类已被其他文章引用，不能删除");
			}
			// 如果文章有子分类不能删除
			if (articleCateDao.countByParentId(id) > 0) {
				throw new BusinessException("当前分类下有子分类，不能删除");
			}
			UserEntity userEntity = RequestUtils.getCurrentAdminUser(req);
			articleCateEntity.setLastModifyBy(userEntity.getUserName());
			articleCateEntity.setLastModifyById(userEntity.getId());
			articleCateEntity.setDeleteStatus(true);
			articleCateDao.save(articleCateEntity);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, ""));
		} catch (BusinessException ex) {
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, ex.getLocalizedMessage()));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "删除分类异常"));
		}
	}

	/**
	 * 异步保存分类
	 * 
	 * @param modelMap
	 * @param req
	 * @param res
	 * @param articleCateEntity
	 * @param type
	 * @author lvzf 2016年6月8日
	 */
	@RequestMapping("/save.json")
	public void save(ModelMap modelMap, HttpServletRequest req, HttpServletResponse res, ArticleCateEntity articleCateEntity) {
		try {
			UserEntity userEntity = RequestUtils.getCurrentAdminUser(req);
			ArticleCateEntity articleCateEntityTemp = null;
			if (articleCateEntity.getId() == null) {
				// 新增
				articleCateEntityTemp = articleCateEntity;
				articleCateEntityTemp.setLastModifyBy(userEntity.getUserName());
				articleCateEntityTemp.setLastModifyById(userEntity.getId());
			} else {
				// 更新
				articleCateEntityTemp = articleCateDao.findOne(articleCateEntity.getId());
				articleCateEntityTemp.setName(articleCateEntity.getName());
				articleCateEntityTemp.setParentId(articleCateEntity.getParentId());
				articleCateEntityTemp.setRemark(articleCateEntity.getRemark());
				articleCateEntityTemp.setSeqNo(articleCateEntity.getSeqNo());
				articleCateEntityTemp.setPinyin(articleCateEntity.getPinyin());
				articleCateEntityTemp.setLastModifyBy(userEntity.getUserName());
				articleCateEntityTemp.setLastModifyById(userEntity.getId());
			}
			articleCateDao.save(articleCateEntityTemp);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", true, ""));
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			ResponseOutputUtils.renderJson(res, MessageDTO.newInstance("", false, "异步保存分类异常"));
		}
	}

}
