/**
 * 0. Project	: KT Rainbow Adapter 프로젝트
 * 1. FileName	: DataTimeUtil.java
 * 2. Package	: com.enterprise1.common.util
 * 3. Comment	: DateTime Util Class
 * 4. 작성자		: 김현수
 * 5. 작성일		: 2015. 2. 10. 오전 10:54:00
 * 6. 변경이력	:
 *	  이름						:일자			:	변경내용
 *	------------------------------------------------------
 *	hskim@enterprise1.co.kr		:2015. 2. 10.	:	신규 개발
 *
 */
package com.e1.eai.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
	
	/** YYYYMMDD */
	public final static String YYYYMMDD = "yyyyMMdd";
	/** YYYYMMDDHHMMSS */
	public final static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	/** YYYYMMDDHHMMSSSSS */
	public final static String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
	
	
	/**
	 * yyyyMMddHHmmss Format
	 */
	public static final String DATETIME = "yyyyMMddHHmmss";
	
	/**
	 * yyyyMMdd_HHmmss Format
	 */
	public static final String DATETIMEUNDER = "yyyyMMdd_HHmmss";
	
	/**
	 * yyyy-MM-dd HH:mm:ss Format
	 */
	public static final String DATETIMESOFT = "yyyy-MM-dd HH:mm:ss";
	/**
	 * yyyy-MM-dd_HH-mm-ss Format
	 */
	public static final String DATETIMESOFTUNDER = "yyyy-MM-dd_HH-mm-ss";


	/**
	 * yyyyMMddHHmmssSSS Format
	 */
	public static final String RA_DATETIMEMS = "yyyyMMddHHmmssSSS";
	public static final String RMS_DATETIMES = "yyyyMMddHHmmss";
	
	/**
	 * yyyy-MM-dd HH:mm:ss.SSS" Format
	 */
	public static final String DATETIMEMSSOFT = "yyyy-MM-dd HH:mm:ss.SSS";

	/**
	 * yyyyMMdd Format
	 */
	public static final String DATE = "yyyyMMdd";
	
	/**
	 * yyyyMMdd Format
	 */
	public static final String DATESOFT = "yyyy-MM-dd";
	
	/**
	 * HH_mm_ss Format
	 */
	public static final String TIME = "HH:mm:ss";

	/**
	 * 
	 * @param format DataTime Format
	 * @return 요청 시간 값
	 */
	public static final String getDateTime(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}
	
	/**
	 * 
	 * @param format DataTime Format
	 * @return 요청 시간 값
	 */
	public static final String getDateTime(String format, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

}
