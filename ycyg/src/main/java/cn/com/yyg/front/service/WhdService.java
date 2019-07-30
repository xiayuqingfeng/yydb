package cn.com.yyg.front.service;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.easy.exception.BusinessException;
import cn.com.yyg.base.entity.BigWheelInfoEntity;
import cn.com.yyg.base.entity.BigWheelJoinUserEntity;
import cn.com.yyg.front.dao.BigWheelInfoDao;
import cn.com.yyg.front.dao.BigWheelJoinUserDao;
import cn.com.yyg.front.dao.BigWheelPrizeDao;
import cn.com.yyg.front.dao.BigWheelUserPrizeDao;

/**
 * 微活动服务类
 * 
 * @author nibili 2016年6月3日
 * 
 */
@Service
public class WhdService {

	/** 大转盘dao */
	@Autowired
	private BigWheelInfoDao bigWheelInfoDao;
	/** 大转盘奖项dao */
	@Autowired
	private BigWheelPrizeDao bigWheelPrizeDao;
	/** 大转盘用户奖项dao */
	@Autowired
	private BigWheelUserPrizeDao bigWheelUserPrizeDao;
	/** 参与用户dao */
	@Autowired
	private BigWheelJoinUserDao bigWheelJoinUserDao;

	/**
	 * 删除大转盘选项
	 * 
	 * @param bigWheelId
	 * @param prizeId
	 * @param userId
	 * @author nibili 2016年6月3日
	 */
	@Transactional
	public void deleteBigwheelPrize(Long bigWheelId, Long prizeId, Long userId) {
		// 一、先修改被引用的奖项
		BigWheelInfoEntity bigWheelInfoEntity = bigWheelInfoDao.findByIdAndUserIdAndDeleteStatus(bigWheelId, userId, false);
		String[] arrays = StringUtils.split(bigWheelInfoEntity.getWheelCalibrations(), ",");
		if (ArrayUtils.isNotEmpty(arrays) == true) {
			String prizeIdString = String.valueOf(prizeId);
			for (int i = 0; i < arrays.length; i++) {
				String id = arrays[i];
				if (id.equals(prizeIdString) == true) {
					// 改为 "谢谢参与"
					arrays[i] = "-1";
				}
			}
			bigWheelInfoEntity.setWheelCalibrations(StringUtils.join(arrays, ","));
		}
		// 二、删除奖项
		bigWheelPrizeDao.deleteByIdAndUserId(prizeId, userId);
	}

	/**
	 * 收集用户信息
	 * 
	 * @param bigWheelId
	 * @param tel
	 * @param dto
	 * @throws BusinessException
	 * @author nibili 2016年6月6日
	 */
	@Transactional
	public void collectUserInfo(Long bigWheelId, String tel, BigWheelJoinUserEntity dto) throws BusinessException {

		BigWheelJoinUserEntity bigWheelJoinUserEntityTemp = bigWheelJoinUserDao.findByBigWheelIdAndTel(bigWheelId, tel);
		if (bigWheelJoinUserEntityTemp != null) {
			// 数据库里一定有记录
			bigWheelJoinUserEntityTemp.setAddress(dto.getAddress());
			bigWheelJoinUserEntityTemp.setBigWheelId(bigWheelId);
			bigWheelJoinUserEntityTemp.setEmail(dto.getEmail());
			bigWheelJoinUserEntityTemp.setIsUserInfoCommited(true);
			bigWheelJoinUserEntityTemp.setName(dto.getName());
			bigWheelJoinUserEntityTemp.setQq(dto.getQq());
			bigWheelJoinUserEntityTemp.setWeixin(dto.getWeixin());
			// 保存用户信息
			bigWheelJoinUserDao.save(bigWheelJoinUserEntityTemp);
			// 判断如果是抽奖后，收集用户信息，要去修改用户中奖表中的这个大转盘抽奖的该用户的中奖信息中的名字
			bigWheelUserPrizeDao.updateNameByBigWheelIdAndTel(dto.getName(), bigWheelId, tel);
		}
	}
}
