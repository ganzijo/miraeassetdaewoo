/**
 * 0. Project	: KT Rainbow Adapter 프로젝트
 * 1. FileName	: CalculationUtil.java
 * 2. Package	: com.enterprise1.common.util
 * 3. Commnet	: 산술연산 Util Class
 * 4. 작성자		: 김선아
 * 5. 작성일		: 2015. 3. 31. 오전 10:54:00
 * 6. 변경이력		:
 *	  이름						:일자			:	변경내용
 *	------------------------------------------------------
 *	sakim@enterprise1.co.kr		:2015. 2. 10.	:	신규 개발
 *
 */
package com.e1.eai.util;

/**
 * 산술 연산 Util Class
 */
public class CalculationUtil {
	
	/**
	 * 자릿수 지정 랜덤 값 생성 Method
	 * 
	 * @param lineNum 랜덤값 생성 자릿 수 
	 * @return 랜덤 값
	 */
	public static String randomValue(int lineNum){
		java.util.Random r = new java.util.Random(); //java.util.Random r = new Random(); 이라고 쓸 수 있다.         
        int num = r.nextInt((int)Math.pow(10,lineNum));
        String randomValue = String.format("%0"+lineNum+"d", num);
        return randomValue;
	}
	
	/**
	 * MSGID seq 값 생성
	 * @param seqLoopVal
	 * @param lineNum seq 값 생성 자릿 수 
	 * @return seq 값
	 */
	public static String lendingZero(int seqLoopVal, int lineNum){
		String lendingZero = String.format("%0"+lineNum+"d", seqLoopVal);
		
		return lendingZero;
	}
	
	/**
	 * 문자열 데이터가 숫자인지 체크 하는 메소드
	 * 
	 * @param data 체크 값
	 * @return
	 */
	public static boolean isStringDouble(String data) {
	    try {
	        Double.parseDouble(data);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	  }
	
	
}


