package cn.com.yyg.base.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * 日期时间处理
 * @author lvzf
 * 2015年11月3日 下午7:39:55
 */
public class DateUtil {
	public static String  YMD="yyyy-MM-dd";
	public static String  YMDhhmm="yyyy-MM-dd HH:mm";
	public static String  YMDhhmmss="yyyy-MM-dd HH:mm:ss";
	public static String  Hhmmss="HH:mm:ss";
	/**
	 * 日期转字符串，返回指定格式yyyy-MM-dd
	 * @return
	 */
	public static String getDateToString(Date date,String format){
		if(date==null)return null;
		SimpleDateFormat fmt= new SimpleDateFormat(format);
		String d=null;
		try{
			d=fmt.format(date);
		}catch(Exception e){
			  e.printStackTrace();
	        d = null;
		}
		return d;
	}
	/**
	 * 字符串转日期，返回指定格式yyyy-MM-dd
	 * @return
	 */
	public static Date getStringToDate(String date,String format){
		if(date==null)return null;
		SimpleDateFormat fmt= new SimpleDateFormat(format);
		Date d=null;
		try{
			d=fmt.parse(date);
		}catch(Exception e){
			  e.printStackTrace();
	        d = null;
		}
		return d;
	}
	public static String getNowDateToString(String format){
		return getDateToString(new Date(),format);
	}
	public static Date getNowDate(String format){
		return getStringToDate(getDateToString(new Date(),format),format);
	}
	public static Date addDay(Date date, int amount) {
		return add(date, Calendar.DATE, amount);
	}
	public static Date add(Date date, int field, int amount) {
		if (date == null) {
			date = new Date();
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);

		return cal.getTime();
	}
	public static Date addMonth(Date date, int amount) {
		return add(date, Calendar.MONTH, amount);
	}
}
