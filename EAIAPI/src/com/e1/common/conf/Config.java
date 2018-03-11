/*
 * @(#)Config.java   1.0 2005/07/13
 */
package com.e1.common.conf;

/**
 * @author JangJooHwi
 * @version 1.0, 2005/07/13
 * 
 * 환경설정파일에서의 속성정보 추출 기능을 정의한 추상클래스.
 * configuration file로 부터 Property 를 추출한다.
 */
public abstract class Config {

  /**
   * key 에 해당되는 값을 String 로 return
   *
   * @param   key    Property의 key
   * @return  String
   */
  abstract public String getString(String key);

  /**
   * key 에 해당되는 값을 String Array 로 return
   *
   * @param   key      Property의 key
   * @return  String[]
   */
  abstract public String [] getStringArray(String key);

  /**
   * key 에 해당되는 값을 int 로 return
   *
   * @param   key      Property의 key
   * @return  int
   */
  public int getInt(String key) {
    return Integer.parseInt(getString(key));
  }

  /**
   * key 에 해당되는 값을 long 로 return
   *
   * @param   key      Property의 key
   * @return  long
   */
  public long getLong(String key) {
    return Long.parseLong(getString(key));
  }

  /**
   * key 에 해당되는 값을 float 로 return
   *
   * @param   key      Property의 key
   * @return  float
   */
  public float getFloat(String key) {
    return Float.parseFloat(getString(key));
  }

  /**
   * key 에 해당되는 값을 boolean 로 return
   *
   * @param   key      Property의 key
   * @return  boolean
   */
  public boolean getBoolean(String key) {
    return Boolean.getBoolean(getString(key));
  }
}