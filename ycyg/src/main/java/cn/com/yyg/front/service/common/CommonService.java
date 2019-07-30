package cn.com.yyg.front.service.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cn.com.yyg.base.entity.SysDictEntity;
import cn.com.yyg.front.dao.SysDictDao;
import cn.com.yyg.front.dao.YgProductDao;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;

/**
 * 网站公共服务
 * 
 * @author lvzf 2016年8月4日
 * 
 */
@Service
public class CommonService {
	/** 公共字典 */
	@Autowired
	private SysDictDao sysDictDao;
	/** 云购商品dao */
	@Autowired
	private YgProductDao ygProductDao;

	/**
	 * 所有有效字典
	 * 
	 * @return
	 * @author lvzf 2016年8月4日
	 */
	@Cacheable(key = "'_allDicts'", value = "defaultFastjson")
	public Table<Long, Long, SysDictEntity> getAllDicts() {
		Table<Long, Long, SysDictEntity> tables = HashBasedTable.create();
		List<SysDictEntity> list = sysDictDao.findAll();
		for (SysDictEntity entity : list) {
			if (entity.getParentId() == null
					|| entity.getParentId().longValue() == SysDictEntity.ROOT_ID) {
				tables.put(entity.getId(), SysDictEntity.ROOT_ID, entity);
			} else {
				tables.put(entity.getId(), entity.getParentId(), entity);
			}
		}
		return tables;
	}

	/**
	 * 清除所有有效字典
	 * 
	 * @author lvzf 2016年8月4日
	 */
	@CacheEvict(key = "'_allDicts'", value = "defaultFastjson")
	public void evictAllDicts() {

	}
	/**
	 * 获取直接下级分类
	 *@param tables
	 *@param parentId
	 *@return
	 * @author lvzf 2016年9月6日
	 */
	public List<SysDictEntity> getChilds(
			Table<Long, Long, SysDictEntity> tables, Long parentId) {
		List<SysDictEntity> childs = Lists.newArrayList(tables.column(parentId)
				.values());
		return childs;
	}
	/**
	 * 获取所有下级
	 *@param tables
	 *@param parentId
	 *@param col 从0开始
	 *@return
	 * @author lvzf 2016年9月6日
	 */
	public List<SysDictEntity> getAllChilds(
			Table<Long, Long, SysDictEntity> tables, Long parentId, int col) {
		List<SysDictEntity> results = Lists.newArrayList();
		List<SysDictEntity> childs = getChilds(tables, parentId); 
		String p = "";
		for (int i = 0; i < col; i++) {
			p += "&nbsp;&nbsp;&nbsp;";
		}
		for (int i = 0; i < childs.size(); i++) {
			SysDictEntity s = childs.get(i);
			if (i + 1 == childs.size()) {// 最后一个
				s.setDisplayName(p + ((col>0)?"└─ ":"" )+ s.getName());
			} else {
				s.setDisplayName(p + ((col>0)?"├─ ":"" )+ s.getName());
			} 
			results.add(s);
			if (this.getChilds(tables, s.getId()).size() > 0) {
				// 递归
				results.addAll(this.getAllChilds(tables, s.getId(), col + 1));

			}
		}

		return results;
	}

	/**
	 * 统计云购份数
	 * 
	 * @return
	 * @author lvzf 2016年8月20日
	 */
	public String totalBuyNums() {
		Long count = ygProductDao.countBuyNums();
		if (count == null) {
			count = 1L;
		}
		DecimalFormat df = new DecimalFormat("000000000");
		String val = df.format(BigDecimal.valueOf(count.longValue() * 27));
		return val;
	}
}
