package cn.com.yyg.front.dto;

public class MessageDto {
	private String error;
	/** 内容 **/
	private String msg;

	/**
	 * get msg
	 * 
	 * @return
	 * @author linwk 2016年10月14日
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * set msg
	 * 
	 * @param msg
	 * @author linwk 2016年10月14日
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * get error
	 * 
	 * @return
	 * @author linwk 2016年10月14日
	 */
	public String getError() {
		return error;
	}

	/**
	 * set error
	 * 
	 * @param error
	 * @author linwk 2016年10月14日
	 */
	public void setError(String error) {
		this.error = error;
	}
}
