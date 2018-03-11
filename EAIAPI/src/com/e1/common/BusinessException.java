/*
 * @(#)BusinessException.java   1.0 2005/07/12
 */
package com.e1.common;
 
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * @author JangJooHwi
 * @version 1.0, 2005/07/12
 * 
 * Chained Exception을 구현한 시스템 전반의 Base Business Exception.<br>
 * 업무시스템에서 발생하는 최상위 Exception 으로 이 클래스를 extends 하여
 * 해당 업무별 예외를 정의한다.
 */
public class BusinessException extends Exception {

  /** reference for previous exception */
  private Throwable previousThrowable = null;

  /**
  * this constructor is used to create the root exception
  */
  public BusinessException() {}

  public BusinessException(String pMsg) {
    super(pMsg);
  }

  /**
  * @param pEx previous exception
  */
  public BusinessException(Throwable pEx) {
    this.previousThrowable = pEx;
  }

  public BusinessException(String pMsg,	Throwable pEx) {
    super(pMsg);
    this.previousThrowable = pEx;
  }

  /**
  * print it's own stack trace, then invoke the
  * corresponding printStackTrace() of the previous exception
  */
  public void printStackTrace() {
    super.printStackTrace();
    if (this.previousThrowable != null) {
      this.previousThrowable.printStackTrace();
    }
  }

  public void printStackTrace(PrintStream pPS) {
    super.printStackTrace(pPS);
    if (this.previousThrowable != null) {
      this.previousThrowable.printStackTrace(pPS);
    }
  }

  public void printStackTrace(PrintWriter pPW) {
    super.printStackTrace(pPW);
    if (this.previousThrowable != null) {
      this.previousThrowable.printStackTrace(pPW);
    }
  }
}