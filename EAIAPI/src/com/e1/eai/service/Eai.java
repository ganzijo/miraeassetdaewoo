/**
 * @author maPark,wrJoo
 * @version 1.0, 2017/10
 */

package com.e1.eai.service;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.status.StatusLogger;

import com.e1.eai.conf.APIConf;
import com.e1.eai.conf.constant.ConfigConst;
import com.e1.eai.entity.InterfaceBO;
import com.e1.eai.excp.APIException;
import com.e1.eai.helper.MqAPI;
import com.e1.eai.helper.ServiceAPI;
import com.e1.eai.helper.XmlParseAPI;
import com.e1.eai.log.APILogConst;
import com.e1.eai.log.APILogger;

/**
 * API Adapter MainClass<br>
 * Method ( send / receive / request / sndFileAsync / sndFileSync )
 **/
public class Eai {
	private String className = "Eai";
	private String methodName = "";

	private APIConf apiConf = null;
	private ServiceAPI serviceAPI = null;
	private Logger apiLogger = null;
	private MqAPI mqAPI = null;

	/**
	 * Eai default 생성자 <br>
	 * apiConfig.xml 파일 로딩<br>
	 * logger 생성 및 api서비스 객체 초기화
	 * 
	 * @throws APIException
	 */
	public Eai() throws APIException {
		try {
			// log4j2 default XML Load 오프
			StatusLogger.getLogger().setLevel(Level.OFF);

			// apiConfig.xml 최초 Load
			apiConf = XmlParseAPI.getInstance();

			// Logger 생성
			apiLogger = APILogger.getInstance(apiConf.getPId(), apiConf.getLogPath(), apiConf.getLogLevel());
			apiLogger.log(APILogConst.DEBUG_5, "[DEBUG] Logger getInstance OK.");

			// API 서비스 객체 초기화
			serviceAPI = new ServiceAPI();
			apiLogger.log(Level.INFO, "======== Eai() apiConfig.xml Load OK.");

		} catch (APIException e) {
			if (apiLogger != null && apiLogger.getName().equals("apiLog")) {
				apiLogger.log(Level.ERROR, "[ERROR]  APIService(" + methodName + ") initialize Fail.");
				apiLogger.log(Level.ERROR, "ErrCode : " + e.getErrorCd() + "\nErrMsg : " + e.getErrorMessage());
			}
			throw e;
		} catch (Exception e) {
			throw new APIException("API", "0005", "MQAPI " + methodName + " Error.", e);
		}
	}

	/** 단방향 메시지 송신 **/
	public String eaiSendAPAsync(String interfaceID, String data) throws APIException {
		this.methodName = this.className + ".sendAPAsync";

		// 성공코드 "API0000"
		String returnCode = ConfigConst.CD_SUCCESS;

		try {
			// apiConfig.xml 파일 변경이 있다면 Reload
			apiConf = XmlParseAPI.getInstance();

			// Logger loading
			apiLogger = APILogger.getInstance(apiConf.getPId(), interfaceID, apiConf.getLogPath(),
					apiConf.getLogLevel());
			apiLogger.log(APILogConst.DEBUG_5, "[DEBUG] Successful getInstance from Logger.");

			// 필수 inputData Check
			if (interfaceID == null || interfaceID.trim().equals("")) {
				throw new APIException("API", "0003", "interfaceID - null or blank is not supported.");
			} else if (data == null || data.trim().equals("")) {
				throw new APIException("API", "0003", "sndData     - null or blank is not supported.");
			}
			apiLogger.log(Level.DEBUG, "[STEP0] Checked compulsory element(InterfaceID, sendData)");

			// InterfaceBO 생성 및 셋팅
			InterfaceBO ifConf = new InterfaceBO(apiConf);
			ifConf.setIfConf_sendAsync(interfaceID, data); // PROC_TM_CUST_01 set

			// 단방향 송신 서비스 호출
			serviceAPI.mqMsgSnd(ifConf);
			apiLogger.log(Level.INFO, "======== Eai() Service(" + methodName + ") Complete.");

		} catch (APIException e) {
			if (apiLogger != null && apiLogger.getName().equals("apiLog")) {
				apiLogger.log(Level.ERROR, "[ERROR]  APIService(" + methodName + ") Fail.");
				apiLogger.log(Level.ERROR, "ErrCode : " + e.getErrorCd() + "\nErrMsg : " + e.getErrorMessage());
			}
			throw e;
		} catch (Exception e) {
			throw new APIException("API", "0005", "MQAPI " + methodName + " Error.", e);
		}
		return returnCode;
	}

	/**
	 * 단방향 메시지 수신<br>
	 * ※ 메시지 수신 완료 후 completeAPAsync 호출<br>
	 * waittimeout(ms) - 0 : 무한대기
	 **/
	public String eaiRecvAPAsync(String interfaceID, int waittimeout) throws APIException {
		this.methodName = this.className + ".recvAPAsync";

		String recvMessage = "";

		try {
			// apiConfig.xml 파일 변경이 있다면 Reload
			apiConf = XmlParseAPI.getInstance();

			// Logger loading
			apiLogger = APILogger.getInstance(apiConf.getPId(), interfaceID, apiConf.getLogPath(),
					apiConf.getLogLevel());
			apiLogger.log(APILogConst.DEBUG_5, "[DEBUG] Successful getInstance from Logger.");

			// 필수 inputData Check
			if (interfaceID == null || interfaceID.trim().equals("")) {
				throw new APIException("API", "0003", "interfaceID - null or blank is not supported.");
			}
			apiLogger.log(APILogConst.DEBUG_5, "[STEP0] Checked compulsory element element(InterfaceID)");

			// InterfaceBO 생성 및 셋팅
			InterfaceBO ifConf = new InterfaceBO(apiConf);
			ifConf.setIfConf_recvAsync(interfaceID, waittimeout);

			// 단방향 수신 서비스 호출
			serviceAPI.mqMsgRcv(ifConf);
			apiLogger.log(Level.INFO, "======== Eai() Service(" + methodName + ") Complete.");

			// 수신데이터 셋팅
			recvMessage = ifConf.getDataStr();

		} catch (APIException e) {
			if (apiLogger != null && apiLogger.getName().equals("apiLog")) {
				apiLogger.log(Level.ERROR, "[ERROR]  APIService(" + methodName + ") Fail.");
				apiLogger.log(Level.ERROR, "ErrCode : " + e.getErrorCd() + "\nErrMsg : " + e.getErrorMessage());
			}
			throw e;
		} catch (Exception e) {
			throw new APIException("API", "0005", "MQAPI " + methodName + " Error.", e);
		}
		return recvMessage;
	}

	/**
	 * 단방향 메시지 수신 완료 <br>
	 * ※ 단방향 수신 완료(recvAPAsync) 후 호출
	 **/
	public void eaiCompleteAPAsync(String interfaceID) throws APIException {
		this.methodName = this.className + ".completeAPAsync";

		try {
			// apiConfig.xml 파일 변경이 있다면 Reload
			apiConf = XmlParseAPI.getInstance();

			// Logger loading
			apiLogger = APILogger.getInstance(apiConf.getPId(), interfaceID, apiConf.getLogPath(),
					apiConf.getLogLevel());
			apiLogger.log(APILogConst.DEBUG_5, "[DEBUG] Successful getInstance from Logger.");

			// 단방향 수신 MQ commit
			serviceAPI.mqMsgGetComplete();
			apiLogger.log(Level.INFO, "======== Eai() Service(" + methodName + ") Complete.");

		} catch (APIException e) {
			if (apiLogger != null && apiLogger.getName().equals("apiLog")) {
				apiLogger.log(Level.ERROR, "[ERROR]  APIService(" + methodName + ") Fail.");
				apiLogger.log(Level.ERROR, "ErrCode : " + e.getErrorCd() + "\nErrMsg : " + e.getErrorMessage());
			}
			throw e;
		} catch (Exception e) {
			throw new APIException("API", "0005", "MQAPI " + methodName + " Error.", e);
		}
	}

	/**
	 * 양방향 수신<br>
	 * ※ 메시지 수신 완료 후 recvAPSyncReturn 호출<br>
	 * waittimeout(ms) 0 : 무한대기
	 **/
	public String eaiRecvAPSyncRead(String interfaceID, int waittimeout) throws APIException {
		this.methodName = this.className + ".recvAPSyncRead";

		String recvMessage = "";

		try {
			// apiConfig.xml 파일 변경이 있다면 Reload
			apiConf = XmlParseAPI.getInstance();

			// Logger loading
			apiLogger = APILogger.getInstance(apiConf.getPId(), interfaceID, apiConf.getLogPath(),
					apiConf.getLogLevel());
			apiLogger.log(APILogConst.DEBUG_5, "[DEBUG] Successful getInstance from Logger.");

			// 필수 inputData Check
			if (interfaceID == null || interfaceID.trim().equals("")) {
				throw new APIException("API", "0003", "interfaceID - null or blank is not supported.");
			}
			apiLogger.log(APILogConst.DEBUG_5, "[STEP0] Checked compulsory element element(InterfaceID)");

			// InterfaceBO 생성 및 셋팅
			InterfaceBO ifConf = new InterfaceBO(apiConf);
			ifConf.setIfConf_recvSync(interfaceID, waittimeout);

			// 양방향 데이터 수신 호출
			serviceAPI.mqMsgRead(ifConf);
			apiLogger.log(Level.INFO, "======== Eai() Service(" + methodName + ") Complete.");

			recvMessage = ifConf.getDataStr();

		} catch (APIException e) {
			if (apiLogger != null && apiLogger.getName().equals("apiLog")) {
				apiLogger.log(Level.ERROR, "[ERROR]  APIService(" + methodName + ") Fail.");
				apiLogger.log(Level.ERROR, "ErrCode : " + e.getErrorCd() + "\nErrMsg : " + e.getErrorMessage());
			}
			throw e;
		} catch (Exception e) {
			throw new APIException("API", "0005", "MQAPI " + methodName + " Error.", e);
		}
		return recvMessage;
	}

	/**
	 * 양방향 리턴 메시지 송신<br>
	 * ※ 양방향 메시지 수신(recvAPSyncRead) 완료 후 호출
	 **/
	public String eaiRecvAPSyncReturn(String interfaceID, String data) throws APIException {
		this.methodName = this.className + ".recvAPSyncReturn";

		// 성공코드 "API0000"
		String returnCode = ConfigConst.CD_SUCCESS;

		try {
			// apiConfig.xml 파일 변경이 있다면 Reload
			apiConf = XmlParseAPI.getInstance();

			// Logger loading
			apiLogger = APILogger.getInstance(apiConf.getPId(), interfaceID, apiConf.getLogPath(),
					apiConf.getLogLevel());
			apiLogger.log(APILogConst.DEBUG_5, "[DEBUG] Successful getInstance from Logger.");

			// 필수 inputData Check
			if (interfaceID == null || interfaceID.trim().equals("")) {
				throw new APIException("API", "0003", "interfaceID - null or blank is not supported.");
			} else if (data == null || data.trim().equals("")) {
				throw new APIException("API", "0003", "returnData  - null or blank is not supported.");
			}
			apiLogger.log(Level.DEBUG, "[STEP0] Checked compulsory element(InterfaceID, returnData)");

			// InterfaceBO 생성 및 셋팅
			InterfaceBO ifConf = new InterfaceBO(apiConf);
			ifConf.setIfConf_Reply(interfaceID, data); // PROC_TM_CUST_01 set

			// 양방향 송신 서비스 호출
			serviceAPI.mqMsgReply(ifConf);
			apiLogger.log(Level.INFO, "======== Eai() Service(" + methodName + ") Complete.");

		} catch (APIException e) {
			if (apiLogger != null && apiLogger.getName().equals("apiLog")) {
				apiLogger.log(Level.ERROR, "[ERROR]  APIService(" + methodName + ") Fail.");
				apiLogger.log(Level.ERROR, "ErrCode : " + e.getErrorCd() + "\nErrMsg : " + e.getErrorMessage());
			}
			throw e;
		} catch (Exception e) {
			throw new APIException("API", "0005", "MQAPI " + methodName + " Error.", e);
		}
		return returnCode;
	}

	/**
	 * 양방향 메시지 송신<br>
	 * 메시지 송신 후 결과(응답)값 수신<br>
	 * waittimeout(ms) 0 : 무한대기
	 **/
	public String eaiSendAPSync(String interfaceID, String data, int waittimeout) throws APIException {
		this.methodName = this.className + ".sendAPSync";

		String recvMessage = null;

		try {
			// apiConfig.xml 파일 변경이 있다면 Reload
			apiConf = XmlParseAPI.getInstance();

			// Logger loading
			apiLogger = APILogger.getInstance(apiConf.getPId(), interfaceID, apiConf.getLogPath(),
					apiConf.getLogLevel());
			apiLogger.log(APILogConst.DEBUG_5, "[DEBUG] Successful getInstance from Logger.");

			// 필수 inputData Check
			if (interfaceID == null || interfaceID.trim().equals("")) {
				throw new APIException("API", "0003", "interfaceID - null or blank is not supported.");
			} else if (data == null || data.trim().equals("")) {
				throw new APIException("API", "0003", "sndData     - null or blank is not supported.");
			}
			apiLogger.log(APILogConst.DEBUG_5, "[STEP0] Checked compulsory element(InterfaceID, sendData)");

			// InterfaceBO 생성 및 셋팅
			InterfaceBO ifConf = new InterfaceBO(apiConf);
			ifConf.setIfConf_sendSync(interfaceID, data, waittimeout);

			serviceAPI.mqMsgReq(ifConf);
			apiLogger.log(Level.INFO, "======== Eai() Service(" + methodName + ") Complete.");

			recvMessage = ifConf.getDataStr();
		} catch (APIException e) {
			if (apiLogger != null && apiLogger.getName().equals("apiLog")) {
				apiLogger.log(Level.ERROR, "[ERROR]  APIService(" + methodName + ") Fail.");
				apiLogger.log(Level.ERROR, "ErrCode : " + e.getErrorCd() + "\nErrMsg : " + e.getErrorMessage());
			}
			throw e;
		} catch (Exception e) {
			throw new APIException("API", "0005", "MQAPI " + methodName + " Error.", e);
		}
		return recvMessage;
	}

	/** 단방향 File정보 송신 **/
	public String eaiSendFIAsync(String interfaceID, String filename, String filePostCommand) throws APIException {
		this.methodName = this.className + ".sendFIAsync";
		String returnCode = ConfigConst.CD_SUCCESS;

		try {
			/// apiConfig.xml 파일 변경이 있다면 Reload
			apiConf = XmlParseAPI.getInstance();

			// Logger loading
			apiLogger = APILogger.getInstance(apiConf.getPId(), interfaceID, apiConf.getLogPath(),
					apiConf.getLogLevel());
			apiLogger.log(APILogConst.DEBUG_5, "[DEBUG] Successful getInstance from Logger.");

			// 필수 inputData Check
			if (interfaceID == null || interfaceID.trim().equals("")) {
				throw new APIException("API", "0003", "interfaceID - null or blank is not supported.");
			} else if (filename == null || filename.trim().equals("")) {
				throw new APIException("API", "0003", "fileName    - null or blank is not supported.");
			} else if (filePostCommand == null || filePostCommand.trim().equals("")) {
				throw new APIException("API", "0003", "filePostCommand - null or blank is not supported.");
			}
			apiLogger.log(APILogConst.DEBUG_5,
					"[STEP0] Checked compulsory element(interfaceID, fileName, filePostCommand)");

			// InterfaceBO 생성 및 셋팅
			InterfaceBO ifConf = new InterfaceBO(apiConf);
			ifConf.setAPIFileInvokeConf(interfaceID, filename, filePostCommand);

			serviceAPI.mqFileMsgPut(ifConf);
			apiLogger.log(Level.INFO, "======== Eai() Service(" + methodName + ") Complete.");

		} catch (APIException e) {
			if (apiLogger != null && apiLogger.getName().equals("apiLog")) {
				apiLogger.log(Level.ERROR, "[ERROR]  APIService(" + methodName + ") Fail.");
				apiLogger.log(Level.ERROR, "ErrCode : " + e.getErrorCd() + "\nErrMsg : " + e.getErrorMessage());
			}
			throw e;
		} catch (Exception e) {
			throw new APIException("API", "0005", "MQAPI " + methodName + " Error.", e);
		}
		return returnCode;
	}

	/**
	 * 양방향 File정보 송신<br>
	 * 메시지 송신 후 결과(응답)코드 수신<br>
	 * waittimeout(ms) 0 : 무한대기
	 **/
	public String eaiSendFISync(String interfaceID, String filename, String filePostCommand, int waittimeout)
			throws APIException {
		this.methodName = this.className + ".sendFISync";

		// 성공코드 "API0000"
		String returnCode = ConfigConst.CD_SUCCESS;

		try {
			// apiConfig.xml 파일 변경이 있다면 Reload
			apiConf = XmlParseAPI.getInstance();

			// Logger loading
			apiLogger = APILogger.getInstance(apiConf.getPId(), interfaceID, apiConf.getLogPath(),
					apiConf.getLogLevel());
			apiLogger.log(APILogConst.DEBUG_5, "[DEBUG] Successful getInstance from Logger.");

			// 필수 inputData Check
			if (interfaceID == null || interfaceID.trim().equals("")) {
				throw new APIException("API", "0003", "interfaceID - null or blank is not supported.");
			} else if (filename == null || filename.trim().equals("")) {
				throw new APIException("API", "0003", "filename    - null or blank is not supported.");
			} else if (filePostCommand == null || filePostCommand.trim().equals("")) {
				throw new APIException("API", "0003", "filePostCommand     - null or blank is not supported.");
			}
			apiLogger.log(APILogConst.DEBUG_5,
					"[STEP0] Checked compulsory element(InterfaceID, filename, filePostCommand)");

			// InterfaceBO 생성 및 셋팅
			InterfaceBO ifConf = new InterfaceBO(apiConf);
			ifConf.setAPIFileInvokeConf(interfaceID, filename, filePostCommand, waittimeout);

			serviceAPI.mqFileMsgReq(ifConf);
			apiLogger.log(Level.INFO, "======== Eai() Service(" + methodName + ") Complete.");

		} catch (APIException e) {
			if (apiLogger != null && apiLogger.getName().equals("apiLog")) {
				apiLogger.log(Level.ERROR, "[ERROR]  APIService(" + methodName + ") Fail.");
				apiLogger.log(Level.ERROR, "ErrCode : " + e.getErrorCd() + "\nErrMsg : " + e.getErrorMessage());
			}
			throw e;
		} catch (Exception e) {
			throw new APIException("API", "0005", "MQAPI " + methodName + " Error.", e);
		}
		return returnCode;
	}

	/**
	 * MQ Connection - Qmgr 연결<br>
	 * ※ 서비스 호출 전 MQ Connection init
	 */
	public void eaiInit() throws APIException {
		// Logger loading
		apiLogger = APILogger.getInstance(apiConf.getPId(), apiConf.getLogPath(), apiConf.getLogLevel());

		if (mqAPI != null) {
			throw new APIException("API", "0004", "Please check! init() Already exists."); 
		} else {
			// MQ 객체 초기화
			mqAPI = MqAPI.getInstance(apiLogger);

			serviceAPI.connectQmgr(apiConf, mqAPI, apiLogger);
			apiLogger.log(Level.INFO, "======== Eai() initInterface complete.");
		}
	}

	/**
	 * MQ Connection - Qmgr 연결 종료 서비스 호출 완료 후 MQ Connection Destroy
	 */
	public void eaiDestroy() throws APIException {
		apiLogger = APILogger.getInstance(apiConf.getPId(), apiConf.getLogPath(), apiConf.getLogLevel());
		mqAPI.mqDisc();
		apiLogger.log(APILogConst.DEBUG_5, "[DEBUG] Closed QMGR");
		apiLogger.log(Level.INFO, "======== Eai() destoryInterface complete.");
	}
}