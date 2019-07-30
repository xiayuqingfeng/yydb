package cn.com.yyg.front.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.com.yyg.base.entity.AddressEntity;

/**
 * 收货地址dao
 * 
 * @author linwk 2016年10月5日
 * 
 */
public interface AddressDao extends JpaRepository<AddressEntity, Long>, JpaSpecificationExecutor<AddressEntity> {
	/**
	 * 获取用户地址列表
	 * 
	 * @param userId
	 * @return
	 * @author linwk 2016年10月8日
	 */
	// isDefaultAddress 排序 id 降序
	@Query("select a from AddressEntity a where a.userId=?1 order by a.isDefaultAddress desc,a.lastModifyTime desc,a.id desc")
	List<AddressEntity> findByUserId(Long userId);

	int countByUserId(Long userId);
}
