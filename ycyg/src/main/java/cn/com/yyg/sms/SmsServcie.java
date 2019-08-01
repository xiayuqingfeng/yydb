package cn.com.yyg.sms;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.com.easy.utils.FastJSONUtils;
import cn.com.easy.utils.SpringContextHolder;
import cn.com.yyg.base.entity.SmsRecordEntity;
import cn.com.yyg.front.dao.SmsRecordDao;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * 短信服务
 * 
 * @author nibili 2016年6月29日
 * 
 */
@Service
public class SmsServcie {

	private static Logger logger = LoggerFactory.getLogger(SmsServcie.class); 
	/**短信key*/
	public static final String APP_KEY="23561272";
	/**短信密钥*/
	public static final String APP_SECRET="0b11bf145fc4fbf4bc3a3c767434aa1a";
	/**阿里签名：公司名称*/
	public static final String SIGN_COM="郑州中原视界";
	/**产品名称*/
	public static final String SIGN_PRODUCT="幸运一点网";
	/**验证码：登录模板*/
	public static final String TPL_CODE_LOGIN="SMS_33545330";
	/**验证码：注册模板*/
	public static final String TPL_CODE_REG="SMS_33545328";
	/**验证码：修改密码模板*/
	public static final String TPL_CODE_FPWD="SMS_33545326";
	/**中奖短信模板*/
	public static final String TPL_WIN="SMS_33640424";
	@Value("${develop.mode}")
	private int develop;
	public static void main(String[] args) {
		try { 
			//SmsServcie.sendRegistCode("13666076525", "8888");
			//SmsServcie.sendLoginCode("13666076525", "8888");
			//SmsServcie.sendFindPasswdCode("13666076525", "8888");
			SmsServcie.sendWin("15805930886", "你真富", "(第14期) 移动20元充值");
			//System.out.println("短信发送成功");
		} catch (ApiException e) { 
			e.printStackTrace();
		}
	}
	/**
	 * 发送注册，验证码
	 *@param mobile
	 *@param code
	 *@throws ApiException
	 * @author lvzf 2016年9月20日
	 */
	public static void sendRegistCode(String mobile, String code) throws ApiException {
		SmsServcie.sendMms(TPL_CODE_REG, mobile, "{\"code\":\"" + code + "\",\"product\":\""+SIGN_PRODUCT+"\"}");
	}
	/**
	 * 发送登录，验证码
	 *@param mobile
	 *@param code
	 *@throws ApiException
	 * @author lvzf 2016年9月20日
	 */
	public static void sendLoginCode(String mobile, String code) throws ApiException {
		SmsServcie.sendMms(TPL_CODE_LOGIN, mobile, "{\"code\":\"" + code + "\",\"product\":\""+SIGN_PRODUCT+"\"}");
	}

	/**
	 * 发送 找回密码，验证码
	 *@param mobile
	 *@param code
	 *@throws ApiException
	 * @author lvzf 2016年9月20日
	 */
	public static void sendFindPasswdCode(String mobile, String code) throws ApiException {
		SmsServcie.sendMms(TPL_CODE_FPWD, mobile, "{\"code\":\"" + code + "\",\"product\":\""+SIGN_PRODUCT+"\"}");
	}


 

	/**
	 * 中奖短信
	 *@param mobile
	 *@throws ApiException
	 * @author lvzf 2016年9月20日
	 */
	public static void sendWin(String mobile,String userName,String goodName) throws ApiException { 
		SmsServcie.sendMms(TPL_WIN, mobile, "{\"name\":\"" + userName + "\",\"goodName\":\""+goodName + "\",\"product\":\""+SIGN_PRODUCT+"\"}");
	}

	/**
	 * 发送短信
	 * 参考链连:https://api.alidayu.com/doc2/apiDetail.htm?spm=a3142.7629065.4.7
	 * .9e3kYF&apiId=25450
	 * 
	 * @param templateCode
	 *            模板id
	 * @param mobile
	 * @param param
	 *            短信模板变量，传参规则{"key":"value"}，key的名字须和申请模板中的变量名一致，多个变量之间以逗号隔开。
	 * @author nibili 2016年6月29日
	 * @throws ApiException
	 * @throws Exception
	 */
	@Transactional
	private static void sendMms(String templateCode, String mobile, String param) throws ApiException {
		
		if(StringUtils.isBlank(mobile)){
			return;
		}	
		SmsServcie smsServcie=null;
		try{
			 smsServcie = SpringContextHolder.getBean(SmsServcie.class);		
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		//开发模式不发短信 
		if(smsServcie==null || smsServcie.develop==0){
			return;
		}
		TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest",APP_KEY, APP_SECRET);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		// 公共回传参数，在“消息返回”中会透传回该参数；举例：用户可以传入自己下级的会员ID，在消息返回时，该会员ID会包含在内，用户可以根据该会员ID识别是哪位会员使用了你的应用
		//req.setExtend("123456");
		// 短信类型，传入值请填写normal
		req.setSmsType("normal");
		// 短信签名，传入的短信签名必须是在阿里大鱼“管理中心-短信签名管理”中的可用签名。如“阿里大鱼”已在短信签名管理中通过审核，则可传入”阿里大鱼“（传参时去掉引号）作为短信签名。短信效果示例：【阿里大鱼】欢迎使用阿里大鱼服务。
		req.setSmsFreeSignName(SIGN_COM);
		// 短信模板变量，传参规则{"key":"value"}，key的名字须和申请模板中的变量名一致，多个变量之间以逗号隔开。示例：针对模板“验证码${code}，您正在进行${product}身份验证，打死不要告诉别人哦！”，传参时需传入{"code":"1234","product":"alidayu"}
		req.setSmsParamString(param);
		// 知信接收号码
		req.setRecNum(mobile);
		// 短信模板ID，传入的模板必须是在阿里大鱼“管理中心-短信模板管理”中的可用模板。示例：SMS_585014
		req.setSmsTemplateCode(templateCode);
		AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
		//插入短信发送记录
		SmsRecordEntity record=new SmsRecordEntity();
		record.setBody(FastJSONUtils.toJsonString(rsp));
		record.setMobile(mobile);
		record.setContent(param);
		SmsRecordDao smsRecordDao=SpringContextHolder.getBean(SmsRecordDao.class);
		smsRecordDao.save(record);
		logger.info("发送短信,tel:" + mobile + ";" + rsp.getBody());
	}
}
