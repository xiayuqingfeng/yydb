package cn.com.yyg.front.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.com.yyg.base.entity.UserEntity;

/**
 * 用户dao
 * 
 * @author lvzf
 * 
 */
public interface UserDao extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

	/**
	 * 按关键字查找
	 * 
	 * @param searchText
	 * @param pageable
	 * @return
	 * @author nibili 2016年6月7日
	 */
	@Query("from UserEntity u where u.deleteStatus=false and u.userName like ?1 or u.trueName like ?1")
	public Page<UserEntity> findBySearchText(String searchText, Pageable pageable);

	/**
	 * 查找所有正常的记录
	 * 
	 * @param pageable
	 * @return
	 * @author nibili 2016年6月7日
	 */
	@Query("from UserEntity u where u.deleteStatus=false")
	public Page<UserEntity> findAllNotDelete(Pageable pageable);

	/**
	 * 用户名查找用户
	 * 
	 * @param userName
	 * @return
	 * @author nibili 2016年5月24日
	 */
	@Query("select t from UserEntity t where t.userName=?1 ")
	public UserEntity findUserByUserName(String userName);

	/**
	 * 电话查找用户
	 * 
	 * @param mobile
	 * @return
	 * @author nibili 2016年5月24日
	 */
	@Query("select t from UserEntity t where t.mobile=?1 ")
	public UserEntity findUserByMobile(String mobile);

	/**
	 * 邮件查找用户
	 * 
	 * @param email
	 * @return
	 * @author nibili 2016年5月24日
	 */
	@Query("select t from UserEntity t where t.email=?1 ")
	public UserEntity findUserByEmail(String email);

	/**
	 * 通过openId查找用户
	 * 
	 * @param openid
	 * @return
	 * @author linwk 2016年10月13日
	 */
	public UserEntity findByWeixinOpenId(String openid);
	/**
	 * 被邀请的用户列表
	 *@param yaoqingUserId
	 *@return
	 * @author lvzf 2016年10月14日
	 */
	public List<UserEntity> findByYaoqingUserId(Long yaoqingUserId);
}
