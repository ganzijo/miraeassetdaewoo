/**
 * @(#)EAIException.java   1.0 2005/07/19
 */
package com.e1.eai.excp;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.ibm.mq.MQException;

import com.e1.common.BusinessException;

/**
 * @author JangJooHwi
 * @version 1.0, 2005/07/19
 * 
 *          EAI 시스템 예외<br>
 *          Chained Exception을 구현한 EAI 시스템 전반의 Business Exception 을 정의한다. EAI
 *          시스템내의 에러코드 및 에러메시지를 저장하여 예외 상황 정보를 전달한다.
 */
public class APIException extends BusinessException {

	/** 송신 처리시 MQ 자원 관련 오류 */
	public static final int ERR_MQ_PROC = 10;

	/** 송신 처리시 Time Out 발생 */
	public static final int ERR_SEND_TIME_OUT = 11;

	/** 송신데이타의 표준헤더 정보 미등록 오류 */
	public static final int ERR_HDRINFO_NOT_FOUND = 12;

	/** ICS(채널 정보 체크) 오류 */
	public static final int ERR_ICS_FAIL = 13;

	/** ICF 에 등록된 INTERFACE 가 아닙니다. */
	public static final int ERR_ICF_NOT_FOUND = 14;

	/** EAI 송신 ADAPTER 처리 오류 */
	public static final int ERR_SEND_ADT_PROC = 19;

	/** UNISYS 수신처리 프로그램 호출이후 TIME OUT */
	public static final int ERR_HUB_AFTER_CALL_RECV_PGM = 21;

	/** UNISYS 수신처리 프로그램 호출이전 TIME OUT */
	public static final int ERR_HUB_BEFORE_CALL_RECV_PGM = 22;

	/** UNISYS 수신처리 요청에대한 응답대기 시간초과 오류 */
	public static final int ERR_HUB_WAIT_TIMEOVER = 25;

	/** UNISYS 수신처리 프로그램 실행시간 부족 오류 */
	public static final int ERR_HUB_NOT_ENOUGH_TIME = 27;

	/** UNISYS 서버간 TIME 동기화 오류 */
	public static final int ERR_HUB_SERVER_TIME_SYNC = 28;

	/** UNISYS EAI ADAPTER 처리 오류 */
	public static final int ERR_HUB_RECV_ADT_PROC = 29;

	/** 수신처리 프로그램 호출 불가 오류 */
	public static final int ERR_CALL_RECV_PGM = 30;

	/** 수신처리 프로그램 호출이후 TIME OUT */
	public static final int ERR_AFTER_CALL_RECV_PGM = 31;

	/** 수신처리 프로그램 호출이전 TIME OUT */
	public static final int ERR_BEFORE_CALL_RECV_PGM = 32;

	/** 수신처리 프로그램 실행시간 부족 오류 */
	public static final int ERR_NOT_ENOUGH_TIME = 37;

	/** 서버간 TIME 동기화 오류 */
	public static final int ERR_SERVER_TIME_SYNC = 38;

	/** EAI ADAPTER 처리 오류 */
	public static final int ERR_RECV_ADT_PROC = 39;

	/** 수정가능한 컬럼이 아닙니다. */
	public static final int ERR_UPDATE_PROTECTED_COL = 90;

	/** BFH 의 필드키가 아닙니다. */
	public static final int ERR_INVALID_COLNO = 91;

	/** BFH를 수정할 값이 유요하지 않습니다. */
	public static final int ERR_INVALID_COLVAL = 92;

	/** 입력한 데이타의 길이가 BFH 의 길이보다 작습니다. */
	public static final int ERR_INVALID_EAI_DATA_LENGTH = 94;

	/** 송신데이타의 표준헤더 정보 미등록 오류 */
	public static final int ERR_INTERFACEID_NOT_FOUND = 95;

	/** Adaptor Config File 로드 에러 */
	public static final int ERR_ADAPTOR_CONFIG_LOAD_FAIL = 96;

	/** Adaptor Config File 로드 에러 */
	public static final int ERR_ADAPTOR_CONFIG_READ_FAIL = 97;

	/** RECV HDRINFO NOT FOUND */
	public static final int ERR_RECV_HDRINFO_NOT_FOUND = 98;

	/** RECV HDRINFO NOT AVAILABLE */
	public static final int ERR_RECV_HDRINFO_NOT_AVAILABLE = 99;

	/** RECV MSG IS NULL */
	public static final int ERR_RECV_MSG_IS_NULL = 100;

	/** SEND MSG NOT AVAILABLE */
	public static final int ERR_SEND_MSG_NOT_AVAILABLE = 101;

	/** RECV MSG IS NULL */
	public static final int ERR_QMANAGER_NOT_FOUND = 102;

	/** Header Parsing Error */
	public static final int ERR_HEADER_FORMAT = 201;

	/** When Inbound, Class not found Exception */
	public static final int CLASS_NOT_FOUND = 301;

	/** When Inbound, Method not found Exception */
	public static final int METHOD_NOT_FOUND = 302;

	/** When Inbound, ReplyQ not found Exception */
	public static final int REPLYQ_NOT_FOUND = 303;

	/** When Inbound, ReplyFlag not found Exception */
	public static final int REPLYFLAG_NOT_FOUND = 304;

	/** When Inbound, Header_Format_Exception */
	public static final int HEADER_FORMAT_EXCEPTION = 305;

	/** err Service Type **/
	private String service = "";

	/** err errorCd **/
	private String errorCd = "";
	
	/** err e.reaso **/
	private String errReasonCd = "";

	/** err e.Msg **/
	private String errorMessage = "";

	/** err APIMsg **/
	private String errorData = "";

	/**
	 * APIException Constructor 1
	 * 
	 * @param service (API/MQE)
	 * @param errorCd(String)
	 * @param errorMessage
	 * @param err
	 */
	public APIException(String service, String errReasonCd, String errorMessage, Exception err) {
		super(err);
		this.service = service;
		this.errReasonCd = errReasonCd;
		this.errorCd = this.service + errReasonCd;
		this.errorMessage = errorMessage;
	}

	/**
	 * APIException Constructor 2
	 * 
	 * @param service (API/MQE)
	 * @param errorCd(int)
	 * @param errorMessage
	 * @param err
	 */
	public APIException(String service, int errReasonCd, String errorMessage, Exception err) {
		super(err);
		this.service = service;
		this.errReasonCd = errReasonCd + "";
		this.errorCd = this.service + errReasonCd;
		this.errorMessage = errorMessage;
	}

	/**
	 * APIException Constructor 3
	 * 
	 * @param service (API/MQE)
	 * @param errorCd(String)
	 * @param errorMessage
	 */
	public APIException(String service, String errReasonCd, String errorMessage) {
		super(errorMessage);
		this.service = service;
		this.errReasonCd = errReasonCd;
		this.errorCd = this.service + errReasonCd;
		this.errorMessage = errorMessage;
	}

	/**
	 * APIException Constructor 4
	 * 
	 * @param service (API/MQE)
	 * @param errorCd(int)
	 * @param errorMessage
	 */
	public APIException(String service, int errReasonCd, String errorMessage) {
		super(errorMessage);
		this.service = service;
		this.errReasonCd = errReasonCd + "";
		this.errorCd = this.service + errReasonCd;
		this.errorMessage = errorMessage;
	}

	/**
	 * APIException Constructor 5
	 * 
	 * @param service (API/MQE)
	 * @param errorCd(String)
	 * @param errorMessage
	 * @param err
	 */
	public APIException(String service, String errReasonCd, String errorMessage, Throwable err) {
		super(err);
		this.service = service;
		this.errReasonCd = errReasonCd;
		this.errorCd = this.service + errReasonCd;
		this.errorMessage = errorMessage;
	}

	/**
	 * APIException Constructor 6
	 * 
	 * @param service (API/MQE)
	 * @param errorCd(int)
	 * @param errorMessage
	 * @param err
	 */
	public APIException(String service, int errReasonCd, String errorMessage, Throwable err) {
		super(err);
		this.service = service;
		this.errReasonCd = errReasonCd + "";
		this.errorCd = this.service + errReasonCd;
		this.errorMessage = errorMessage;
	}

	/**
	 * APIException Constructor 7 (MQ)
	 * 
	 * @param mqe
	 */
	public APIException(MQException mqe) {
		super(mqe);
		this.service = "MQE";
		this.errReasonCd = mqe.reasonCode + "";
		this.errorCd = this.service + mqe.reasonCode;
		this.errorMessage = mqe.getLocalizedMessage() + " (" + mqe.completionCode + ")";
	}

	/**
	 * APIException Constructor 8 (ActiveMQ)
	 * 
	 * @param jmse
	 * @param vendor
	 */
	/*
	public APIException(JMSException jmse, String vendor) {
		super(jmse);

		// this.service = vendor.toUpperCase() + "-JMS";
		this.service = "MQ";
		if ("activemq".equals(vendor.toLowerCase()) && jmse.getErrorCode() == null) {
			// 에러코드체크.
			this.errorCd = getAMQErrCode(jmse, jmse.getLocalizedMessage());
			this.errorMessage = jmse.getLocalizedMessage() + " (" + this.errorCd + ")";

		} else {
			this.errorCd = "JMS-" + jmse.getErrorCode();
		}

		// this.errorCd = "JMS-" + jmse.getErrorCode();
		// this.errorMessage = jmse.getLocalizedMessage() + " (" + jmse.getErrorCode() +
		// ")";
	}
	*/

	/**
	 * ActiveMQ errorCode
	 * 
	 * @param jmse
	 * @param errMessage
	 * @return errCd
	 */
	/*
	public String getAMQErrCode(JMSException jmse, String errMessage) {
		errMessage = jmse.getLocalizedMessage();

		// 각 errMessage에 맞는 errorCd 선정
		if (errMessage.contains("Error")) {
			this.errorCd = "AMQ0001";
		} else if (errMessage.contains("User name")) {
			this.errorCd = "AMQ0002";
		} else if (errMessage.contains("The JMS connection has failed:")) {
			this.errorCd = "AMQ0003";
		} else if (errMessage.contains("Cannot send,")) {
			this.errorCd = "AMQ0004";
		} else if (errMessage.contains("Software")) {
			this.errorCd = "AMQ0006";
		} else if (errMessage.contains("The") && errMessage.contains("Consumer is closed")) {
			this.errorCd = "AMQ0007";
		} else if (errMessage.contains("is not authorized")) {
			this.errorCd = "AMQ0998";
		} else {
			this.errorCd = "AMQ0999";
		}
		return errorCd;
	}
	*/

	/**
	 * Error Service Type get Method
	 * 
	 * @return ServiceType
	 */
	public String getService() {
		return service;
	}

	/**
	 * Error Cd get Method
	 * 
	 * @return ErrorCd
	 */
	public String getErrorCd() {
		return errorCd;
	}

	/**
	 * Error Message get Method
	 * 
	 * @return ErrorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Error Service Set Method
	 * 
	 * @param service
	 */
	public void setService(String service) {
		this.service = service;
	}
	
	/**
	 * ErrorCD Set Method
	 * 
	 * @param errorCd
	 */
	public void setErrorCd(String errorCd) {
		this.errorCd = errorCd;
	}
	
	/**
	 * Error Message Set Method
	 * 
	 * @param errorMessage
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	/**
	 * @return the errorData
	 */
	public String getErrorData() {
		return errorData;
	}

	/**
	 * @param ErrReasonCd the ErrReasonCd to set
	 */
	public void setErrReasonCd(String errReasonCd) {
		this.errReasonCd = errReasonCd;
	}
	
	/**
	 * @return the errorData
	 */
	public String getErrReasonCd() {
		return errReasonCd;
	}

	/**
	 * @param errorData the errorData to set
	 */
	public void setErrorData(String errorData) {
		this.errorData = errorData;
	}

	
	/**
	 * Error Message 출력용 Method ( errq, log )
	 * 
	 * @return 출력용 ErrorMessage
	 */
	public String printErrorMessage() {
		return "Service : " + getService() + " | ErrorCode : " + getErrorCd() + " | ErrorMessage : " + getErrorMessage();
	}
	
	/**
	 * StackTrace 출력용 Method
	 * @return StackTrace 전문
	 */
	public String printStackTraceMessage(){
		StringWriter errors = new StringWriter();
		this.printStackTrace(new PrintWriter(errors));
		
		return errors.toString();
	}

	/**
	 * 에러코드에 해당하는 EAI 에러메시지를 반환한다.
	 *
	 * @param errorCodeValue
	 *            에러코드
	 * @return String 에러메시지
	 */
	public static String getEAIErrStr(int errorCodeValue) {
		String errorMsg = "NOT DEFINED";

		switch (errorCodeValue) {
		case 0:
			errorMsg = "OK";
			break;

		case ERR_MQ_PROC:
			errorMsg = "MQ Resource Error";
			break;

		case ERR_SEND_TIME_OUT:
			errorMsg = "Send Adapter Time Out Error";
			break;

		case ERR_HDRINFO_NOT_FOUND:
			errorMsg = "Send Data Std Header Error";
			break;

		case ERR_ICS_FAIL:
			errorMsg = "ICS(Channel Status Info) Check Error";
			break;

		case ERR_ICF_NOT_FOUND:
			errorMsg = "Interface is not in ICF file";
			break;

		case ERR_SEND_ADT_PROC:
			errorMsg = "EAI Send ADAPTER Error";
			break;

		case ERR_HUB_AFTER_CALL_RECV_PGM:
			errorMsg = "TIME OUT after UNISYS Recv Program Call";
			break;

		case ERR_HUB_BEFORE_CALL_RECV_PGM:
			errorMsg = "TIME OUT before UNISYS Recv Program Call";
			break;

		case ERR_HUB_WAIT_TIMEOVER:
			errorMsg = "TIME OUT of UNISYS Recv Process Require";
			break;

		case ERR_HUB_NOT_ENOUGH_TIME:
			errorMsg = "TIME OUT of UNISYS Recv Process Execute";
			break;

		case ERR_HUB_SERVER_TIME_SYNC:
			errorMsg = "UNISYS Time Sync Error Between Servers";
			break;

		case ERR_HUB_RECV_ADT_PROC:
			errorMsg = "EAI UNISYS ADAPTER Error";
			break;

		case ERR_CALL_RECV_PGM:
			errorMsg = "Recv Process Call Error";
			break;

		case ERR_AFTER_CALL_RECV_PGM:
			errorMsg = "TIME OUT after Recv Program Call";
			break;

		case ERR_BEFORE_CALL_RECV_PGM:
			errorMsg = "ERR_BEFORE_CALL_RECV_PGM";
			break;

		case ERR_NOT_ENOUGH_TIME:
			errorMsg = "TIME OUT before Recv Process Call";
			break;

		case ERR_SERVER_TIME_SYNC:
			errorMsg = "Time Sync Error Between Servers";
			break;

		case ERR_RECV_ADT_PROC:
			errorMsg = "EAI Recv ADAPTER Error";
			break;

		case ERR_UPDATE_PROTECTED_COL:
			errorMsg = "Can not alter field";
			break;

		case ERR_INVALID_COLNO:
			errorMsg = "FIELD Key Error";
			break;

		case ERR_INVALID_COLVAL:
			errorMsg = "FIELD Value Error";
			break;

		case ERR_INVALID_EAI_DATA_LENGTH:
			errorMsg = "ERR_INVALID_EAI_DATA_LENGTH";
			break;

		case ERR_INTERFACEID_NOT_FOUND:
			errorMsg = "INTERFACE ID NOT DEFINE";
			break;

		case ERR_ADAPTOR_CONFIG_LOAD_FAIL:
			errorMsg = "ADAPTOR CONFIG LOAD FAIL";
			break;

		case ERR_ADAPTOR_CONFIG_READ_FAIL:
			errorMsg = "ADAPTOR CONFIG READ FAIL";
			break;

		case ERR_RECV_HDRINFO_NOT_FOUND:
			errorMsg = "RECV HDRINFO NOT FOUND";
			break;

		case ERR_RECV_HDRINFO_NOT_AVAILABLE:
			errorMsg = "RECV HDRINFO NOT AVAILABLE";
			break;

		case ERR_RECV_MSG_IS_NULL:
			errorMsg = "RECV MSG IS NULL";
			break;

		case ERR_SEND_MSG_NOT_AVAILABLE:
			errorMsg = "SEND MSG NOT AVAILABLE";
			break;

		case ERR_QMANAGER_NOT_FOUND:
			errorMsg = "QMANAGER NOT FOUND";
			break;

		}
		return errorMsg;
	}
}