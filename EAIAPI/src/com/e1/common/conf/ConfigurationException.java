/*
 * @(#)ConfigurationException.java   1.0 2005/07/13 
 */
package com.e1.common.conf;

import com.e1.common.BusinessException;

/**
 * @author JangJooHwi
 * @version 1.0, 2005/07/13 
 * 환경설정 파일 파싱, 설정시 발생하는 예외.<br>
 * BusinessException 을 상속받았고, 환경설정 파일의 파싱, 설정 과정에서 발생하는 예외를 저장한다.
 */
public class ConfigurationException extends BusinessException {
  public ConfigurationException() {}

  public ConfigurationException(String s) {
    super(s);
  }

  public ConfigurationException(Exception e) {
    super(e);
  }

  public ConfigurationException(String s, Exception e) {
    super(s, e);
  }
}