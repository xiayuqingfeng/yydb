package cn.com.yyg.front.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.easy.utils.PageRequest;
import cn.com.easy.utils.PageRequestUtils;
import cn.com.yyg.base.em.YgProductStatusEnum;
import cn.com.yyg.base.entity.AdvItemEntity;
import cn.com.yyg.base.entity.SysDictEntity;
import cn.com.yyg.base.entity.YgProductEntity;
import cn.com.yyg.base.entity.YgShareEntity;
import cn.com.yyg.front.dao.AdvItemDao;
import cn.com.yyg.front.dao.SysDictDao;
import cn.com.yyg.front.dao.YgProductDao;
import cn.com.yyg.front.dao.YgShareDao;

import com.google.common.collect.Lists;
/**
 * 系统首页
 * @author lvzf	2016年8月20日
 *
 */
@Service
public class FrontIndexService {
	/**字典dao*/
	@Autowired
	private SysDictDao sysDictDao;
	/** 广告dao*/
	@Autowired
	private AdvItemDao advItemDao;
	/**云购商品dao*/
	@Autowired
	private YgProductDao ygProductDao;
	/**晒单分享dao*/
	@Autowired
	private YgShareDao ygShareDao;
	/**
	 * 获取商品分类
	 *@return
	 * @author lvzf 2016年8月19日
	 */
	public List<SysDictEntity> getProductCates(int top){
		PageRequest pageRequest = new PageRequest(1, top);
		pageRequest.setOrderBy("seqNo,createTime");
		pageRequest.setOrderDir("desc,asc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		return sysDictDao.findByParentId(SysDictEntity.ROOT_PRODUCT,pb);
	}
	/**
	 * 获取首页轮播图
	 *@return
	 * @author lvzf 2016年8月19日
	 */
	public List<AdvItemEntity> getIndexSlides(int top){
		PageRequest pageRequest = new PageRequest(1, top);
		pageRequest.setOrderBy("seqNo,createTime");
		pageRequest.setOrderDir("desc,desc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		return advItemDao.findByAdvId(AdvItemEntity.ADV_INDEX_SLIDE,pb).getContent();
	}
	/**
	 * 获取m站首页轮播图
	 *@param top
	 *@return
	 * @author linwk 2016年9月15日
	 */
	public List<AdvItemEntity> getWapIndexSlides(int top){
		PageRequest pageRequest = new PageRequest(1, top);
		pageRequest.setOrderBy("seqNo,createTime");
		pageRequest.setOrderDir("desc,desc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		return advItemDao.findByAdvId(AdvItemEntity.ADV_WAP_INDEX_SLIDE,pb).getContent();
	}
	/**
	 * 获取推荐云购商品
	 *@param top
	 *@return
	 * @author lvzf 2016年8月20日
	 */
	public List<YgProductEntity> getRecommendYgProduct(int top){
		PageRequest pageRequest = new PageRequest(1, top);
		pageRequest.setOrderBy("recommend,seqNo,createTime");
		pageRequest.setOrderDir("desc,desc,desc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		List<YgProductEntity> list=Lists.newArrayList(ygProductDao.findByStatus(YgProductStatusEnum.ING.getValue(), pb).getContent());
		return list;
	}
	/**
	 * 获取最新已揭晓
	 *@param top
	 *@return
	 * @author lvzf 2016年8月20日
	 */
	public List<YgProductEntity> getNewJiexiaoed(int top){
		PageRequest pageRequest = new PageRequest(1, top);
		pageRequest.setOrderBy("publishDate");
		pageRequest.setOrderDir("desc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		List<Integer> status=Lists.newArrayList();
		status.add(YgProductStatusEnum.END.getValue());
		List<YgProductEntity> list=Lists.newArrayList(ygProductDao.findByStatusIn(status, pb).getContent());
 
		return list;
	}
	/**
	 * 获取最新准备揭晓
	 *@param top
	 *@return
	 * @author lvzf 2016年8月20日
	 */
	public List<YgProductEntity> getNewJiexiaoPre(int top){
		PageRequest pageRequest = new PageRequest(1, top);
		pageRequest.setOrderBy("publishDate");
		pageRequest.setOrderDir("desc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		List<Integer> status=Lists.newArrayList();
		status.add(YgProductStatusEnum.PRE.getValue());
		status.add(YgProductStatusEnum.DO.getValue());
		List<YgProductEntity> list=Lists.newArrayList(ygProductDao.findByStatusIn(status, pb).getContent());
 
		return list;
	}
	/**
	 * 获取最新云购商品
	 *@param top
	 *@return
	 * @author lvzf 2016年8月20日
	 */
	public List<YgProductEntity> getNewYgProduct(int top){
		PageRequest pageRequest = new PageRequest(1, top);
		pageRequest.setOrderBy("createTime");
		pageRequest.setOrderDir("desc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		List<YgProductEntity> list=Lists.newArrayList(ygProductDao.findByStatus(YgProductStatusEnum.ING.getValue(), pb).getContent());

		return list;
	}
	/**
	 * 按分类获取云购商品
	 *@param cateId
	 *@param top
	 *@return
	 * @author lvzf 2016年8月20日
	 */
	public List<YgProductEntity> getYgProductByCateId(String cateId,int top){
		PageRequest pageRequest = new PageRequest(1, top);
		pageRequest.setOrderBy("seqNo,createTime");
		pageRequest.setOrderDir("desc,desc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		List<YgProductEntity> list=Lists.newArrayList(ygProductDao.findByStatusAndCateId(YgProductStatusEnum.ING.getValue(),cateId, pb).getContent());

		return list;
	}
	/**
	 * 最新晒单分享
	 *@param top
	 *@return
	 * @author lvzf 2016年8月22日
	 */
	public List<YgShareEntity> getNewYgShare(int top){
		PageRequest pageRequest = new PageRequest(1, top);
		pageRequest.setOrderBy("createTime");
		pageRequest.setOrderDir("desc");
		org.springframework.data.domain.PageRequest pb = PageRequestUtils.buildSpringDataPageRequest(pageRequest);
		List<YgShareEntity> list=ygShareDao.findByAudit(true, pb).getContent();
		return list;
	}
}
