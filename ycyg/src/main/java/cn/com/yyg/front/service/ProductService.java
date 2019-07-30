package cn.com.yyg.front.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.easy.exception.BusinessException;
import cn.com.yyg.base.em.YgProductStatusEnum;
import cn.com.yyg.base.entity.ContentEntity;
import cn.com.yyg.base.entity.ProductEntity;
import cn.com.yyg.base.entity.UserEntity;
import cn.com.yyg.base.entity.UserPhotoAblumEntity;
import cn.com.yyg.base.entity.YgProductEntity;
import cn.com.yyg.front.dao.ContentDao;
import cn.com.yyg.front.dao.ProductDao;
import cn.com.yyg.front.dao.UserPhotoAblumDao;
import cn.com.yyg.front.dao.YgProductDao;

import com.google.common.collect.Lists;

@Service
public class ProductService {
	@Autowired
	private ProductDao productDao;
	/** 大字段 */
	@Autowired
	private ContentDao contentDao;
	/** 云购 云购商品 */
	@Autowired
	private YgProductDao ygProductDao;
	/** 相册*/
	@Autowired
	private UserPhotoAblumDao userPhotoAblumDao;
	@Transactional
	public void save(UserEntity user, ProductEntity productEntity) throws BusinessException { 
		ProductEntity product = new ProductEntity();
		if (productEntity.getId() != null) {
			// 更新
			product = productDao.findOne(productEntity.getId());
			if (product == null) {
				throw new BusinessException("ID无效");
			}
		} else {
			// 新增
			product.setCreateBy(user.getUserName());
			product.setCreateById(user.getId()); 
		}
		product.setLastModifyBy(user.getUserName());
		product.setName(productEntity.getName());
		product.setTitle(productEntity.getTitle());
		product.setCateId(productEntity.getCateId());
		product.setBrandId(productEntity.getBrandId());
		product.setLogoPath(productEntity.getLogoPath());
		product.setPhotos(productEntity.getPhotos());
		product.setRecommend(productEntity.isRecommend());
		product.setSeqNo(productEntity.getSeqNo());
		product.setOrigPrice(productEntity.getOrigPrice()); 
		product.setSinglePrice(productEntity.getSinglePrice());
		//总份数
		if(productEntity.getTotalNum()>YgProductEntity.MAX_BUY_NUM){
			throw new BusinessException("总份数不能超过："+YgProductEntity.MAX_BUY_NUM);
		}
		product.setTotalNum(productEntity.getTotalNum());
		product.setSeoDesc(productEntity.getSeoDesc());
		product.setSeoKey(productEntity.getSeoKey());
		product.setSeoTitle(productEntity.getSeoTitle());
		product.setStatus(productEntity.getStatus());
		product.setLogoPath(productEntity.getLogoPath());
		product.setLimitPeriods(productEntity.getLimitPeriods());
		// 商品详情
		ContentEntity contentValue = null;
		if (product.getContentId() != null) {
			contentValue = contentDao.findOne(product.getContentId());
		}
		if (contentValue == null) {
			contentValue = new ContentEntity();
			contentValue.setCreateBy(user.getUserName());
		}
		contentValue.setLastModifyBy(user.getUserName());
		contentValue.setContent(productEntity.getContent());
		contentDao.save(contentValue);
		product.setContentId(contentValue.getId());
		product = productDao.save(product);
		//生成第一期
		if(ygProductDao.findByProductIdAndPeriod(product.getId(), 1)==null){
			createNewYgProduct(product.getId());
		}

	}

	/**
	 * 生成新的云购期商品
	 *@param productEntity
	 * @author lvzf 2016年8月27日
	 */
	public void createNewYgProduct(Long pid){
		ProductEntity productEntity=productDao.findOne(pid);
		if(productEntity!=null && productEntity.getStatus()==ProductEntity.STATUS_UP
				&& (productEntity.getLimitPeriods().longValue()==0 || productEntity.getLimitPeriods().longValue()>productEntity.getPeriod())){ 
				YgProductEntity yg=new YgProductEntity();
				BeanUtils.copyProperties(productEntity, yg, "id");
				yg.setLeaveNum(yg.getTotalNum());
				yg.setStatus(YgProductStatusEnum.ING.getValue());
				yg.setProductId(pid);
				//云购号码列表
				long totalNum=yg.getTotalNum();
				List<Long> leaveBuyNos=Lists.newArrayList();
				for(long i=1;i<=totalNum;i++){
					leaveBuyNos.add(i);
				}
				ContentEntity contentValue =new ContentEntity();
				contentValue.setContent(StringUtils.join(leaveBuyNos, ","));
				contentDao.save(contentValue);
				yg.setLeaveBuyNosContentId(contentValue.getId());
				yg.setPeriod(productEntity.getPeriod()+1);
				//上一期云购商品
				YgProductEntity preYg=ygProductDao.findByProductIdAndPeriod(pid, productEntity.getPeriod());
				if(preYg!=null){
					yg.setPreYgProductId(preYg.getId());
				}
				ygProductDao.save(yg); 
				productEntity.setPeriod(yg.getPeriod());
				productDao.save(productEntity); 

		}
		
	}
	/**
	 * 获取商品相册的所有下级
	 *@param tables
	 *@param parentId
	 *@param col 从0开始
	 *@return
	 * @author lvzf 2016年9月6日
	 */
	public List<UserPhotoAblumEntity> getAblumAllChilds(Long userId,Long parentId, int col) {
		List<UserPhotoAblumEntity> results = Lists.newArrayList();
		List<UserPhotoAblumEntity> childs = userPhotoAblumDao.findListByUserIdAndParentId(userId, parentId); 
		String p = "";
		for (int i = 0; i < col; i++) {
			p += "&nbsp;&nbsp;&nbsp;";
		}
		for (int i = 0; i < childs.size(); i++) {
			UserPhotoAblumEntity s = childs.get(i);
			if (i + 1 == childs.size()) {// 最后一个
				s.setDisplayName(p + ((col>0)?"└─ ":"" )+ s.getName());
			} else {
				s.setDisplayName(p + ((col>0)?"├─ ":"" )+ s.getName());
			} 
			results.add(s);
			if (userPhotoAblumDao.findListByUserIdAndParentId(userId,s.getId()).size() > 0) {
				// 递归
				results.addAll(this.getAblumAllChilds(userId,s.getId(), col + 1));

			}
		}
		return results;
	}
}
