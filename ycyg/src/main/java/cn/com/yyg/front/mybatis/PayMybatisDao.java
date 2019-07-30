package cn.com.yyg.front.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;
/*
 * 支付MybatisDao
 */
public interface PayMybatisDao {
	/**
	 * 获取过期待支付
	 *@param min 分钟
	 *@param status 状态
	 *@param pageBounds
	 *@return
	 * @author lvzf 2016年10月8日
	 */
	public  List<Long> queryDueUnPay(@Param("minute") Integer minute,@Param("status") Integer status);

}
