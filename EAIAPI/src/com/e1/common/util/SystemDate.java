/*
 * @(#)SystemDate.java   1.0 2005/07/15
 */
package com.e1.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author JangJooHwi
 * @version 1.0, 2005/07/15
 * 
 * 날짜 및 시간 관련 클래스.<br>
 * 현재 시간을 여러 유형으로 가져오거나 type 변환, 출력포맷팅 변환, 시간차이등을 구하는
 * 기능을 제공한다.
 */
public final class SystemDate {

  private final static TimeZone timeZone = TimeZone.getTimeZone( "GMT+09:00" );

  /**
   * 현재 날짜를 반환한다(Date type).
   *
   * @return 현재 날짜
   */
  public static Date getDate() {

    Calendar cal = Calendar.getInstance( timeZone, Locale.KOREAN );

    return cal.getTime();
  }

  /**
   * 현재 날짜에서 offset(sec)만큼 더한 날짜를 반환한다.
   * offset은 초(sec)단위이며 이전 날짜를 계산할 경우 음수 값도 가능하다.
   *
   * @param offset 현재 날짜에서의 offset
   * @return 현재 날짜
   */
  public static Date getDate( long offset ) {

    Calendar cal = Calendar.getInstance( timeZone, Locale.KOREAN );
    cal.setTime( new Date( cal.getTime().getTime() + (offset*1000) ) );

    return cal.getTime();
  }

  /**
   * 특정 날짜에서 offset(sec)만큼 더한 날짜를 반환한다.
   * offset은 초(sec)단위이며 이전 날짜를 계산할 경우 음수 값도 가능하다.
   *
   * @param date 특정 날짜
   * @param offset 현재 날짜에서의 offset
   * @return 현재 날짜
   */
  public static Date getDate( Date date, long offset ) {

    return new Date( date.getTime() + (offset*1000) );
  }

  /**
   * format에 맞추어 현재 날짜를 String으로 반환한다.
   * (예 : "yyyy-MM-dd HH:mm:ss:SSS")
   *
   * @param format 날짜의 format
   * @return       현재 날짜
   */
  public static String getDateString( String format ) {

    SimpleDateFormat simpleFormat = new SimpleDateFormat( format );
    simpleFormat.setTimeZone( timeZone );
    return simpleFormat.format( getDate() );
  }

  /**
   * "yyyy-MM-dd HH:mm:ss"형식의 현재 날짜를 String으로 반환한다.
   *
   * @return       현재 날짜
   */
  public static String getDateString() {
    return getDateString( "yyyy-MM-dd HH:mm:ss.SSS" );
  }

  /**
   * format에 맞추어 현재 날짜를 long 타입 숫자로 반환한다.
   *
   * @param  format 날짜 형식
   * @return long   long형태의 현재 날짜
   */
  public static long getDateLong( String format ) {

    SimpleDateFormat simpleFormat = new SimpleDateFormat( format );
    simpleFormat.setTimeZone( timeZone );
    return Long.parseLong( simpleFormat.format( getDate() ) );
  }

  /**
   * "yyyyMMddHHmmss"형식의 현재 날짜를 long으로 반환한다.
   *
   * @return long   long형태의 현재 날짜
   */
  public static long getDateLong() {
    return getDateLong( "yyyyMMddHHmmss" );
  }

  /**
   * 특정 날짜를 반환한다(Date type).
   *
   * @param year   년
   * @param month  월
   * @param day    일
   * @param hour   시
   * @param minute 분
   * @param second 초
   * @return 현재 날짜 object
   */
  public static Date getDate( int year, int month, int day, int hour, int minute, int second ) {

    GregorianCalendar cal = new GregorianCalendar( timeZone, Locale.KOREAN );
    cal.set( year, month-1, day, hour, minute, second );
    return cal.getTime();
  }

  /**
   * format에 맞추어 주어진 날짜를 String으로 반환한다.(예 : "yyyy-MM-dd HH:mm:ss:SSS")
   *
   * @param date   String으로 표현할 날짜
   * @param format 날짜의 format
   * @return       String으로 변환된 날짜
   */
  public static String dateToString( Date date, String format ) {

    SimpleDateFormat simpleFormat = new SimpleDateFormat( format );
    simpleFormat.setTimeZone( timeZone );
    return simpleFormat.format( date );
  }

  /**
   * format에 맞추어 주어진 String을 Date로 변환한다.
   *
   * @param dateString String으로 표현된 날짜
   * @param format     날짜의 format
   * @return           Date로 변환된 날짜
   * @exception ParseException Sring parsing 에러 발생시
   */
  public static Date stringToDate( String dateString, String format ) throws ParseException {

    SimpleDateFormat simpleFormat = new SimpleDateFormat( format );
    simpleFormat.setTimeZone( timeZone );
    return simpleFormat.parse( dateString );
  }

  /**
   * format에 맞추어 주어진 날짜를 long으로 반환한다.(예 : "yyyy-MM-dd HH:mm:ss:SSS")
   *
   * @param date   String으로 표현할 날짜
   * @param format 날짜의 format
   * @return       long으로 변환된 날짜
   */
  public static long dateToLong( Date date, String format ) {

    SimpleDateFormat simpleFormat = new SimpleDateFormat( format );
    simpleFormat.setTimeZone( timeZone );
    return Long.parseLong( simpleFormat.format( date ) );
  }

  /**
   * format에 맞추어 주어진 long 타입 숫자을 Date로 변환한다.
   *
   * @param     dateLong        Long으로 표현된 날짜
   * @param     format          날짜의 format
   * @return    Date            Date로 변환된 날짜
   * @exception ParseException  String parsing 에러 발생시
   */
  public static Date longToDate( long dateLong, String format ) throws ParseException {

    SimpleDateFormat simpleFormat = new SimpleDateFormat( format );
    simpleFormat.setTimeZone( timeZone );
    return simpleFormat.parse( Long.toString( dateLong ) );
  }

  /**
   * format에 맞추어 주어진 long 타입 날짜(yyyyMMddHHmmss)를 String으로 변환한다.
   *
   * @param dateLong            Long으로 표현된 날짜
   * @param format              날짜의 format
   * @return String             String로 변환된 날짜
   * @throws ParseException
   */
  public static String longToString( long dateLong, String format)
      throws ParseException {
    return dateToString( longToDate( dateLong, "yyyyMMddHHmmss"), format);
  }

  /**
   * 두 Date의 날짜 차이를 date1 - date2 방식으로 계산하여 날짜로 반환한다.
   *
   * @param date1 operand date 1
   * @param date2 operand date 2
   * @return      날짜 차이
   */
  public static int getAfterDays( Date date1, Date date2 ) {

    return (int)( ( date1.getTime()-date2.getTime() ) / 86400000L );
  }

  /**
   * 두 Date의 날짜 차이를 date1 - date2 방식으로 계산하여 초단위로 반환한다.
   *
   * @param date1 operand date 1
   * @param date2 operand date 2
   * @return      초 차이
   */
  public static long getAfterSeconds( Date date1, Date date2 ) {

    return ( date1.getTime()-date2.getTime() ) / 1000L;
  }
}
