package com.e1.eai.helper;

/**
 * @author maPark/wrJoo
 * @version 1.0, 2017/10
 */

import com.ibm.mq.MQC;
import com.ibm.mq.MQMessage;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import com.e1.common.util.SysProperty;
import com.e1.eai.conf.APIConf;
import com.e1.eai.conf.constant.ConfigConst;
import com.e1.eai.entity.InterfaceBO;
import com.e1.eai.excp.APIException;
import com.e1.eai.log.APILogConst;
import com.e1.eai.log.APILogger;

/** 메시지 송수신 서비스(Method) 정의 **/
public class ServiceAPI {
	private static final String className = "ServiceAPI";

	// Logging methodName
	private String methodName = null;
	private Logger apiLogger = null;
	
	private byte[] rcvCorrelationId = null;
	
	/** ServiceAPI default 생성자 <br> **/
	public ServiceAPI() {
		this.methodName = className + ".ServiceAPI";
	}

	/** 단방향 메시지 송신 **/
	public void mqMsgSnd(InterfaceBO ifConf) throws APIException {
		this.methodName = className + ".mqMsgSnd";

		// get logger
		apiLogger = APILogger.getLogger();

		// Msg Object 정의
		MqAPI mqAPI = null;
		MsgAPI sndAPIMsg = null;
		MQMessage sndMQMsg = null;

		// logging step
		int step = 1;

		try {
			// Msg Object 생성
			mqAPI = MqAPI.getInstance(apiLogger);
			sndAPIMsg = new MsgAPI(ifConf); // 데이터(API/RMS) 생성 및 관리 MsgAPI 생성

			// 1. Make send Message --------------------------------------------
			sndAPIMsg.setAPIMsg(ConfigConst.HOPCNT_01);
			sndMQMsg = sndAPIMsg.getAPIMQMsg();
			ifConf.setProdTM02();

			apiLogger.log(Level.DEBUG, "[STEP" + step++ + "] Made sndMsg");
			apiLogger.log(APILogConst.DEBUG_2, "        apiMsg[" + sndAPIMsg.getAPIMsgBO().getAPIFullMsg() + "]");

			// 2. Put Message to SendQueue ( SYNCPOINT ) -----------------------
			sndMQMsg.correlationId = setCorrId(ifConf);
			putMsg(mqAPI, sndMQMsg, ifConf.getPutQ());
			ifConf.setProdTM03();

			apiLogger.log(Level.DEBUG, "[STEP" + step++ + "] Put sndMsg to PutQ(" + ifConf.getPutQ() + ")");

			// 3. MQCommit -----------------------------------------------------
			mqAPI.mqCommit();
			apiLogger.log(Level.DEBUG, "[STEP" + step++ + "] MQ Commit");

			ifConf.setProdTM04();

			// 4. Put Success Message to TraceQ ----------------------------------------
			if (!"".equals(ifConf.getApiConf().getTraceLevel()) && !"99".equals(ifConf.getApiConf().getTraceLevel())) {
				putTraceLog(mqAPI, ifConf, sndAPIMsg);
			}
		} catch (Exception e) {
			// MQ 롤백
			mqAPI.mqBack();
			apiLogger.log(APILogConst.DEBUG_5, "[ERROR] Rollback Service(" + this.methodName + ")");

			// ErrMsg 전송
			putMsg(mqAPI, sndMQMsg, ifConf.getErrQ());
			mqAPI.mqCommit();
			apiLogger.log(APILogConst.DEBUG_5, "[ERROR] Put sndMsg to ErrQ(" + ifConf.getErrQ() + ")");

			ifConf.setProdTM04();

			APIException ae = checkException(e);

			// Put Fail Message to TraceQ
			if (!"".equals(ifConf.getApiConf().getTraceLevel()) && !"99".equals(ifConf.getApiConf().getTraceLevel())) {
				putTraceLog(mqAPI, ifConf, sndAPIMsg, ae);
			}
			throw ae;
		} finally {
			// Close queue & Disconnect MQ Manager
			mqAPI.mqClose();
			// mqAPI.mqDisc();
			apiLogger.log(APILogConst.DEBUG_5, "[STEP" + step++ + "] Closed MQ");
		}
	}
	
	/** 단방향 메시지 수신 **/
	public void mqMsgRcv(InterfaceBO ifConf) throws APIException {
		this.methodName = className + ".mqMsgRcv";
		
		// get logger
		apiLogger = APILogger.getLogger();

		// Msg Object 정의
		MqAPI mqAPI = null;
		MsgAPI rcvAPIMsg = null;
		MQMessage rcvMQMsg = null;

		// logging step
		int step = 1;
		
		String receiveMessage = "";

		try {
			// Msg Object 생성
			mqAPI = MqAPI.getInstance(apiLogger);
			rcvAPIMsg = new MsgAPI(ifConf);
			rcvMQMsg = new MQMessage();

			// 2. get Message from replyQ --------------------------------------
			receiveMessage = getMsg(ifConf, mqAPI, rcvMQMsg);			
			ifConf.setProdTM02();
			
			apiLogger.log(Level.DEBUG, "[STEP" + step++ + "] Get rcvMsg from GetQ(" + ifConf.getGetQ() + ")");
			
			// 3. set MsgAPI by input Msg --------------------------------------
			rcvAPIMsg.extractAPIMsg(receiveMessage, ConfigConst.HOPCNT_04);
			ifConf.setProdTM03();
			
			apiLogger.log(Level.DEBUG, "[STEP" + step++ + "] Extracted rcvMsg");
			apiLogger.log(APILogConst.DEBUG_2, "        apiMsg[" + rcvAPIMsg.getAPIMsgBO().getAPIFullMsg() + "]");
			
			ifConf.setProdTM04(); // PROC_TM_CUST_04 set

			// 5. Put Success Message to TraceQ ----------------------------------------
			if (!"".equals(ifConf.getApiConf().getTraceLevel()) && !"99".equals(ifConf.getApiConf().getTraceLevel())) {
				putTraceLog(mqAPI, ifConf, rcvAPIMsg);
			}
		} catch (Exception e) {
			// MQ 롤백
			mqAPI.mqBack();
			ifConf.setProdTM04(); // PROC_TM_CUST_04 set
			
			apiLogger.log(APILogConst.DEBUG_5, "[ERROR] Rollback Service(" + this.methodName + ")");

			// ErrMsg 전송
			// putMsg(mqAPI, rcvMQMsg, ifConf.getErrQ());
			// mqAPI.mqCommit();
			// apiLogger.log(APILogConst.DEBUG_5, "[ERROR] Put sndMsg to ErrQ(" +
			// ifConf.getErrQ() + ")");

			APIException ae = checkException(e);

			throw ae;
		} finally {
			// Close queue & Disconnect MQ Manager
			mqAPI.mqClose();
			// mqAPI.mqDisc();
			apiLogger.log(APILogConst.DEBUG_5, "[STEP" + step++ + "] Closed MQ");
		}
	}
	
	public void mqMsgGetComplete() throws APIException {
		this.methodName = className + ".mqMsgGetComplete";
		
		apiLogger = APILogger.getLogger();

		MqAPI mqAPI = null;
	
		int step = 1;

		try {
			mqAPI = MqAPI.getInstance(apiLogger);
			
			// 4. MQCommit -----------------------------------------------------
			mqAPI.mqCommit();
			apiLogger.log(Level.DEBUG, "[STEP" + step++ + "] MQ Commit");
		
		} catch (Exception e) {
			// MQ 롤백
			mqAPI.mqBack();
			apiLogger.log(APILogConst.DEBUG_5, "[ERROR] Rollback Service(" + this.methodName + ")");

			APIException ae = checkException(e);

			throw ae;
		} finally {
			// Close queue & Disconnect MQ Manager
			mqAPI.mqClose();
			// mqAPI.mqDisc();
			apiLogger.log(APILogConst.DEBUG_5, "[STEP" + step++ + "] Closed MQ");
		}
	}

	public void mqMsgReq(InterfaceBO ifConf) throws APIException {
		this.methodName = className + ".mqMsgReq";

		apiLogger = APILogger.getLogger();
		
		MqAPI mqAPI = null;
		MsgAPI sndAPIMsg = null;
		MsgAPI rcvAPIMsg = null;
		MQMessage sndMQMsg = null;
		MQMessage rcvMQMsg = null;
		
		byte[] corrid;
		byte[] msgid;
		
		String receiveMessage = "";

		// logging step
		int step = 1;
		
		// 오류타입 send = 1 , receive = 2
		int exceptionType = 1; 

		try {
			mqAPI = MqAPI.getInstance(apiLogger);
			
			sndAPIMsg = new MsgAPI(ifConf);
			sndMQMsg = new MQMessage();
			
			// 1. Make send Message --------------------------------------------
			sndAPIMsg.setAPIMsg(ConfigConst.HOPCNT_01);
			sndMQMsg = sndAPIMsg.getAPIMQMsg();
			ifConf.setProdTM02();
			
			apiLogger.log(Level.DEBUG, "[STEP" + step++ + "] Made sndMsg");

			// 3. Put Message to SendQueue ( SYNCPOINT ) -----------------------
			// request 수행을 위한 correlationId 셋팅
			corrid = setCorrId(ifConf);
			msgid = getMessageId();
			sndMQMsg.correlationId = corrid;
			sndMQMsg.messageId = msgid;
	
			putMsg(mqAPI, sndMQMsg, ifConf.getPutQ());
			ifConf.setProdTM03();
			
			apiLogger.log(Level.DEBUG, "[STEP" + step++ + "] Put sndMsg to PutQ(" + ifConf.getPutQ() + ")");

			// 4. MQCommit -----------------------------------------------------
			mqAPI.mqCommit();
			ifConf.setProdTM04();
			
			apiLogger.log(Level.DEBUG, "[STEP" + step++ + "] MQ Commit");
			
			// 5. Put Success Message to TraceQ ----------------------------------------
			if (!"".equals(ifConf.getApiConf().getTraceLevel()) && !"99".equals(ifConf.getApiConf().getTraceLevel())) {
				putTraceLog(mqAPI, ifConf, sndAPIMsg);
			}
			
			try {
				rcvAPIMsg = new MsgAPI(ifConf);
				rcvMQMsg = new MQMessage();
				
				// 6. get Message from replyQ --------------------------------------
				// request 수행을 위한 correlationId 셋팅(보낸 msg에 대한 결과만 처리)
				rcvMQMsg.correlationId = corrid;
				rcvMQMsg.messageId = msgid;
				//ifConf.setTriggerTime(); // PROC_TM_CUST_01 set
				receiveMessage = getMsg(ifConf, mqAPI, rcvMQMsg);
				ifConf.setProdTM02();
				
				apiLogger.log(Level.DEBUG, "[STEP" + step++ + "] Get rcvMsg from GetQ(" + ifConf.getGetQ() + ")");
				
				// 7. set MsgAPI by rplMsg -----------------------------------------
				rcvAPIMsg.extractAPIMsg(receiveMessage, ConfigConst.HOPCNT_04);
				ifConf.setProdTM03();
				
				apiLogger.log(Level.DEBUG, "[STEP" + step++ + "] Extracted rcvMsg");
				apiLogger.log(APILogConst.DEBUG_2, "        apiMsg[" + rcvAPIMsg.getAPIMsgBO().getAPIFullMsg() + "]");

				// 8. MQCommit -----------------------------------------------------
				mqAPI.mqCommit();
				ifConf.setProdTM04();
				
				apiLogger.log(Level.DEBUG, "[STEP" + step++ + "] MQ Commit");

				// 9. Put Success Message to TraceQ ----------------------------------------
				if (!"".equals(ifConf.getApiConf().getTraceLevel())
						&& !"99".equals(ifConf.getApiConf().getTraceLevel())) {
					putTraceLog(mqAPI, ifConf, rcvAPIMsg);
				}
			} catch (Exception e) {
				exceptionType = 2; // receive 오류
				throw e;
			}
		} catch (Exception e) {
			mqAPI.mqBack();
			ifConf.setProdTM04();
			
			// Send 오류
			if(exceptionType == 1) {
				apiLogger.log(APILogConst.DEBUG_5, "[ERROR] Rollback Service(" + this.methodName + ":put)");
				// ErrMsg 전송
				putMsg(mqAPI, sndMQMsg, ifConf.getErrQ());
				mqAPI.mqCommit();
				apiLogger.log(APILogConst.DEBUG_5, "[ERROR] Put sndMsg to ErrQ(" + ifConf.getErrQ() + ")");
			}else {
				apiLogger.log(APILogConst.DEBUG_5, "[ERROR] Rollback Service(" + this.methodName + ":get)");
			}
			
			APIException ae = checkException(e);

			// Put Fail Message to TraceQ
			if (!"".equals(ifConf.getApiConf().getTraceLevel()) && !"99".equals(ifConf.getApiConf().getTraceLevel())) {
				putTraceLog(mqAPI, ifConf, sndAPIMsg, ae);
			}
			throw ae;
		} finally {
			// Close queue & Disconnect MQ Manager
			mqAPI.mqClose();
			// mqAPI.mqDisc();
			apiLogger.log(APILogConst.DEBUG_5, "[STEP" + step++ + "] Closed MQ");
		}
	}
	
	public void mqMsgRead(InterfaceBO ifConf) throws APIException {
		this.methodName = className + ".mqMsgRead";
		
		apiLogger = APILogger.getLogger();

		MqAPI mqAPI = null;
		MsgAPI rcvAPIMsg = null;
		MQMessage rcvMQMsg = null;

		int step = 1;
		String receiveMessage = "";

		try {
			mqAPI = MqAPI.getInstance(apiLogger);
			rcvAPIMsg = new MsgAPI(ifConf);
			rcvMQMsg = new MQMessage();

			// 1. get Message from requestRCVQ --------------------------------------
			receiveMessage = getMsg(ifConf, mqAPI, rcvMQMsg);
			rcvCorrelationId = rcvMQMsg.correlationId; // reply correlationId 저장
			ifConf.setProdTM02();
			
			apiLogger.log(Level.DEBUG, "[STEP" + step++ + "] Get rcvMsg from GetQ(" + ifConf.getGetQ() + ")");
			
			// 2. set MsgAPI by getMsg --------------------------------------
			rcvAPIMsg.extractAPIMsg(receiveMessage, ConfigConst.HOPCNT_02);
			ifConf.setProdTM03();
			
			apiLogger.log(Level.DEBUG, "[STEP" + step++ + "] Extracted rcvMsg");
			apiLogger.log(APILogConst.DEBUG_2, "        apiMsg[" + rcvAPIMsg.getAPIMsgBO().getAPIFullMsg() + "]");
			
			// 3. MQCommit --------------------------------------------------
			mqAPI.mqCommit();
			ifConf.setProdTM04();
			
			apiLogger.log(Level.DEBUG, "[STEP" + step++ + "] MQ Commit");
			
			// 3. Put Success Message to TraceQ ----------------------------------------
			if (!"".equals(ifConf.getApiConf().getTraceLevel()) && !"99".equals(ifConf.getApiConf().getTraceLevel())) {
				putTraceLog(mqAPI, ifConf, rcvAPIMsg);
			}
		} catch (Exception e) {
			// MQ 롤백
			mqAPI.mqBack();
			ifConf.setProdTM04();
			
			apiLogger.log(APILogConst.DEBUG_5, "[ERROR] Rollback Service(" + this.methodName + ")");

			// ErrMsg 전송
			// putMsg(mqAPI, rcvMQMsg, ifConf.getErrQ());
			// mqAPI.mqCommit();
			// apiLogger.log(APILogConst.DEBUG_5, "[ERROR] Put sndMsg to ErrQ(" +
			// ifConf.getErrQ() + ")");

			APIException ae = checkException(e);

			// Put Fail Message to TraceQ
			if (!"".equals(ifConf.getApiConf().getTraceLevel()) && !"99".equals(ifConf.getApiConf().getTraceLevel())) {
				putTraceLog(mqAPI, ifConf, rcvAPIMsg, ae);
			}
			throw ae;
		} finally {
			// Close queue & Disconnect MQ Manager
			mqAPI.mqClose();
			// mqAPI.mqDisc();
			apiLogger.log(APILogConst.DEBUG_5, "[STEP" + step++ + "] Closed MQ");
		}
	}
	
	public void mqMsgReply(InterfaceBO ifConf) throws APIException {
		this.methodName = className + ".mqMsgReply";

		apiLogger = APILogger.getLogger();
		
		MqAPI mqAPI = null;
		MsgAPI sndAPIMsg = null;
		MQMessage sndMQMsg = null;

		// logging step
		int step = 1;

		try {
			mqAPI = MqAPI.getInstance(apiLogger);
			
			sndAPIMsg = new MsgAPI(ifConf);
			sndMQMsg = new MQMessage();
			
			// 1. Make send Message --------------------------------------------
			sndAPIMsg.setAPIMsg(ConfigConst.HOPCNT_03);
			sndMQMsg = sndAPIMsg.getAPIMQMsg();
			ifConf.setProdTM02();
			
			apiLogger.log(Level.DEBUG, "[STEP" + step++ + "] Made sndMsg");

			// 3. Put Message to SendQueue ( SYNCPOINT ) -----------------------
			// request 수행을 위한 correlationId 셋팅
			sndMQMsg.correlationId = rcvCorrelationId;
			putMsg(mqAPI, sndMQMsg, ifConf.getPutQ());
			ifConf.setProdTM03();
			
			apiLogger.log(Level.DEBUG, "[STEP" + step++ + "] Put sndMsg to PutQ(" + ifConf.getPutQ() + ")");

			// 4. MQCommit -----------------------------------------------------
			mqAPI.mqCommit();
			ifConf.setProdTM04();
			
			apiLogger.log(Level.DEBUG, "[STEP" + step++ + "] MQ Commit");
						
			// 5. Put Success Message to TraceQ ----------------------------------------
			if (!"".equals(ifConf.getApiConf().getTraceLevel()) && !"99".equals(ifConf.getApiConf().getTraceLevel())) {
				putTraceLog(mqAPI, ifConf, sndAPIMsg);
			}
		} catch (Exception e) {
			mqAPI.mqBack();
			ifConf.setProdTM04();
			
			apiLogger.log(APILogConst.DEBUG_5, "[ERROR] Rollback Service(" + this.methodName + ")");
			
			// ErrMsg 전송
			putMsg(mqAPI, sndMQMsg, ifConf.getErrQ());
			mqAPI.mqCommit();
			apiLogger.log(APILogConst.DEBUG_5, "[ERROR] Put sndMsg to ErrQ(" + ifConf.getErrQ() + ")");

			APIException ae = checkException(e);

			// Put Fail Message to TraceQ
			if (!"".equals(ifConf.getApiConf().getTraceLevel()) && !"99".equals(ifConf.getApiConf().getTraceLevel())) {
				putTraceLog(mqAPI, ifConf, sndAPIMsg, ae);
			}
			throw ae;
		} finally {
			// Close queue & Disconnect MQ Manager
			mqAPI.mqClose();
			// mqAPI.mqDisc();
			apiLogger.log(APILogConst.DEBUG_5, "[STEP" + step++ + "] Closed MQ");
		}
	}

	public void mqFileMsgPut(InterfaceBO ifConf) throws APIException {
		this.methodName = className + ".mqFileMsgPut";

		apiLogger = APILogger.getLogger();

		MqAPI mqAPI = null;
		MsgAPI sndAPIMsg = null;
		MQMessage sndMQMsg = null;

		int step = 1;

		try {
			mqAPI = MqAPI.getInstance(apiLogger);
			sndAPIMsg = new MsgAPI(ifConf);

			// 1. Make send Message --------------------------------------------
			sndAPIMsg.setAPIMsg(ConfigConst.HOPCNT_01);
			sndMQMsg = sndAPIMsg.getAPIMQMsg();
			ifConf.setProdTM02();
			
			apiLogger.log(Level.DEBUG, "[STEP" + step++ + "] Made sndMsg");
			
			// 3. Put Message to SendQueue ( SYNCPOINT ) -----------------------
			putMsg(mqAPI, sndMQMsg, ifConf.getPutQ());
			ifConf.setProdTM03();
			
			apiLogger.log(Level.DEBUG, "[STEP" + step++ + "] Put sndMsg to PutQ(" + ifConf.getPutQ() + ")");

			// 4. MQCommit -----------------------------------------------------
			mqAPI.mqCommit();
			ifConf.setProdTM04();
			
			apiLogger.log(Level.DEBUG, "[STEP" + step++ + "] MQ Commit");

			// 5. Put Success Message to TraceQ ----------------------------------------
			if (!"".equals(ifConf.getApiConf().getTraceLevel()) && !"99".equals(ifConf.getApiConf().getTraceLevel())) {
				putTraceLog(mqAPI, ifConf, sndAPIMsg);
			}

		} catch (Exception e) {
			mqAPI.mqBack();
			ifConf.setProdTM04();
			
			apiLogger.log(APILogConst.DEBUG_5, "[ERROR] Rollback Service(" + this.methodName + ")");

			// ErrMsg 전송
			putMsg(mqAPI, sndMQMsg, ifConf.getErrQ());
			mqAPI.mqCommit();
			apiLogger.log(APILogConst.DEBUG_5, "[ERROR] Put sndMsg to ErrQ(" + ifConf.getErrQ() + ")");

			APIException ae = checkException(e);

			// Put Fail Message to TraceQ
			if (!"".equals(ifConf.getApiConf().getTraceLevel()) && !"99".equals(ifConf.getApiConf().getTraceLevel())) {
				putTraceLog(mqAPI, ifConf, sndAPIMsg, ae);
			}
			throw ae;
		} finally {
			// Close queue & Disconnect MQ Manager
			mqAPI.mqClose();
			// mqAPI.mqDisc();
			apiLogger.log(APILogConst.DEBUG_5, "[STEP" + step++ + "] Closed MQ");
		}
	}

	public void mqFileMsgReq(InterfaceBO ifConf) throws APIException {
		this.methodName = className + ".mqFileMsgReq";

		apiLogger = APILogger.getLogger();
		
		MqAPI mqAPI = null;
		MsgAPI sndAPIMsg = null;
		MsgAPI rcvAPIMsg = null;
		MQMessage sndMQMsg = null;
		MQMessage rcvMQMsg = null;
		
		byte[] corrid;
		byte[] msgid;

		String receiveMessage = "";

		// logging step
		int step = 1;
		
		// 오류타입 send = 1 , receive = 2
		int exceptionType = 1; 

		try {
			mqAPI = MqAPI.getInstance(apiLogger);
			sndAPIMsg = new MsgAPI(ifConf);
			sndMQMsg = new MQMessage();

			// 1. Make send Message --------------------------------------------
			sndAPIMsg.setAPIMsg(ConfigConst.HOPCNT_01);
			sndMQMsg = sndAPIMsg.getAPIMQMsg();
			ifConf.setProdTM02();
			
			apiLogger.log(Level.DEBUG, "[STEP" + step++ + "] Made sndMsg");

			// 3. Put Message to SendQueue ( SYNCPOINT ) -----------------------
			// request 수행을 위한 correlationId 셋팅
			corrid = setCorrId(ifConf);
			msgid = getMessageId();
			sndMQMsg.correlationId = corrid;
			sndMQMsg.messageId = msgid;
			putMsg(mqAPI, sndMQMsg, ifConf.getPutQ());
			ifConf.setProdTM03();
			
			apiLogger.log(Level.DEBUG, "[STEP" + step++ + "] Put sndMsg to PutQ(" + ifConf.getPutQ() + ")");

			// 4. MQCommit -----------------------------------------------------
			mqAPI.mqCommit();
			ifConf.setProdTM04();
			
			apiLogger.log(Level.DEBUG, "[STEP" + step++ + "] MQ Commit");

			// 5. Put Success Message to TraceQ ----------------------------------------
			if (!"".equals(ifConf.getApiConf().getTraceLevel()) && !"99".equals(ifConf.getApiConf().getTraceLevel())) {
				putTraceLog(mqAPI, ifConf, sndAPIMsg);
			}

			try {
				rcvAPIMsg = new MsgAPI(ifConf);
				rcvMQMsg = new MQMessage();
				
				// 6. get Message from replyQ --------------------------------------
				// request 수행을 위한 correlationId 셋팅(보낸 msg에 대한 결과만 처리)
				rcvMQMsg.correlationId = corrid;
				rcvMQMsg.messageId = msgid;
				
				//ifConf.setTriggerTime(); // PROC_TM_CUST_01 set
				
				receiveMessage = getMsg(ifConf, mqAPI, rcvMQMsg);
				ifConf.setProdTM02();
				
				apiLogger.log(Level.DEBUG, "[STEP" + step++ + "] Get rcvMsg from GetQ(" + ifConf.getGetQ() + ")");

				// 7. set MsgAPI by rplMsg -----------------------------------------
				rcvAPIMsg.extractAPIMsg(receiveMessage, ConfigConst.HOPCNT_04);
				ifConf.setProdTM03();
				
				apiLogger.log(Level.DEBUG, "[STEP" + step++ + "] Extracted rcvMsg");
				apiLogger.log(APILogConst.DEBUG_2, "        apiMsg[" + rcvAPIMsg.getAPIMsgBO().getAPIFullMsg() + "]");

				// 8. MQCommit -----------------------------------------------------
				mqAPI.mqCommit();
				ifConf.setProdTM04();
				
				apiLogger.log(Level.DEBUG, "[STEP" + step++ + "] MQ Commit");

				// 9. Put Success Message to TraceQ ----------------------------------------
				if (!"".equals(ifConf.getApiConf().getTraceLevel())
						&& !"99".equals(ifConf.getApiConf().getTraceLevel())) {
					putTraceLog(mqAPI, ifConf, rcvAPIMsg);
				}
			} catch (Exception e) {
				exceptionType = 2; // receive 오류
				throw e;
			}
		} catch (Exception e) {
			mqAPI.mqBack();
			ifConf.setProdTM04();
			
			// ErrMsg 전송
			if(exceptionType==1) {
				apiLogger.log(APILogConst.DEBUG_5, "[ERROR] Rollback Service(" + this.methodName + ":put)");
				putMsg(mqAPI, sndMQMsg, ifConf.getErrQ());
				mqAPI.mqCommit();
				apiLogger.log(APILogConst.DEBUG_5, "[ERROR] Put sndMsg to ErrQ(" + ifConf.getErrQ() + ")");
			}
			else {
				apiLogger.log(APILogConst.DEBUG_5, "[ERROR] Rollback Service(" + this.methodName + ":get)");
			}
			APIException ae = checkException(e);

			// Put Fail Message to TraceQ
			if (!"".equals(ifConf.getApiConf().getTraceLevel()) && !"99".equals(ifConf.getApiConf().getTraceLevel())) {
				putTraceLog(mqAPI, ifConf, sndAPIMsg, ae);
			}
			throw ae;
		} finally {
			// Close queue & Disconnect MQ Manager
			mqAPI.mqClose();
			// mqAPI.mqDisc();
			apiLogger.log(APILogConst.DEBUG_5, "[STEP" + step++ + "] Closed MQ");
		}
	}

	/** connected QMGR (retry connect BackQmgr, MQ_RETRY_CNT(1) ) **/
	public void connectQmgr(InterfaceBO ifConf, MqAPI mqAPI) throws APIException {
		int ret = 0;

		ifConf.getApiConf().setBaseMQ(); // Set APIConf : BaseQmgr

		// MQ Connection
		ret = mqAPI.mqConn(ifConf.getApiConf());

		// BaseQmgr Connection Fail
		if (ret == 0) {
			ifConf.getApiConf().setBackMQ(); // Set APIConf : switch BackQmgr
			apiLogger.log(Level.WARN, " [WARN] BaseQMGR Connection Failed. -> Retry Connection for BackQMGR.");

			// Connect BackQmgr
			for (int i = 0; i < ConfigConst.MQ_RETRY_CNT; i++) {
				if (i == 0) {
					if (ifConf.getApiConf().getQmgr() == null) {
						apiLogger.log(Level.WARN, " [WARN] Not Found QMGRName. (QMGR is null)");
						continue;
					}
				}
				if (mqAPI.mqConn(ifConf.getApiConf()) == 1) {
					apiLogger.log(Level.WARN, " [WARN] BackQmgr Connection OK.");
					break;
				} else {
					throw new APIException("MQE", "2018", "Connect QMGR Failed");
					// throw new APIException("API", "9999", "Connect QMGR Failed");
				}
			}
		}
	}

	/** connected QMGR (retry connect BackQmgr, MQ_RETRY_CNT(1) ) **/
	public void connectQmgr(APIConf apiConf, MqAPI mqAPI, Logger apiLogger) throws APIException {
		int ret = 0;

		apiConf.setBaseMQ(); // Set APIConf : BaseQmgr

		// MQ Connection
		ret = mqAPI.mqConn(apiConf);

		// BaseQmgr Connection Fail
		if (ret == 0) {
			apiConf.setBackMQ(); // Set APIConf : switch BackQmgr
			apiLogger.log(Level.WARN, " [WARN] BaseQMGR Connection Failed. -> Retry Connection for BackQMGR.");

			// Connect BackQmgr
			for (int i = 0; i < ConfigConst.MQ_RETRY_CNT; i++) {
				if (i == 0) {
					if (apiConf.getQmgr() == null) {
						apiLogger.log(Level.WARN, " [WARN] Not Found QMGRName. (QMGR is null)");
						continue;
					}
				}
				if (mqAPI.mqConn(apiConf) == 1) {
					apiLogger.log(Level.WARN, " [WARN] BackQmgr Connection OK.");
					break;
				} else {
					throw new APIException("MQE", "2018", "Connect QMGR Failed");
				}
			}
		}
	}

	/** put Message to putQ **/
	public void putMsg(MqAPI mqAPI, MQMessage sndMQMsg, String putQName) throws APIException {
		mqAPI.mqOpenToPut(putQName);
		mqAPI.setPutOption(MQC.MQPMO_SYNCPOINT);
		mqAPI.mqPut(sndMQMsg);
	}

	/** get Message from getQ **/
	public String getMsg(InterfaceBO ifConf, MqAPI mqAPI, MQMessage rcvMQMsg) throws APIException {
		// MQ Option 셋팅(응답대기시간)
		if (ifConf.getTimeoutInterval() == 0) {
			mqAPI.mqOpenToGet(ifConf.getGetQ());
			apiLogger.log(APILogConst.DEBUG_5, "[DEBUG] Unlimited Waiting for replyQ.");
		} else {
			mqAPI.mqOpenToGet(ifConf.getGetQ(), ifConf.getTimeoutInterval());
			apiLogger.log(APILogConst.DEBUG_5,
					"[DEBUG] Waiting for replyQ during " + ifConf.getTimeoutInterval() + " ms.");
		}

		// get Msg from ReplyQ -------------------------------------------
		mqAPI.setGetOption(MQC.MQGMO_SYNCPOINT);
		return mqAPI.mqGet(rcvMQMsg);
	}

	/**
	 * make TraceMsg & put traceQ <br>
	 * API Success
	 **/
	public void putTraceLog(MqAPI mqAPI, InterfaceBO ifConf, MsgAPI apiMsg) {
		int i = 1;
		apiLogger = APILogger.getLogger();

		while (i == 1) {
			i = i + 1;
			try {
				// Put TraceMsg(RmsMsg) TraceLevel 이 "99" 이면 로그 남기지 않는다.
				if (!"".equals(ifConf.getApiConf().getTraceLevel())
						&& !"99".equals(ifConf.getApiConf().getTraceLevel())) {
					// Make traceMsg
					apiMsg.setRMSLog();
					apiMsg.getRmsLogBO().setGUID(apiMsg.getAPIMsgBO().getRAHeaderBO().getMsgId());
					
					mqAPI.mqOpenToPut(ifConf.getApiConf().getTraceQueue());
					mqAPI.setPutOption(MQC.MQPMO_SYNCPOINT);
					mqAPI.mqPut(apiMsg.getRMSMQMsg());
					mqAPI.mqCommit();
					apiLogger.log(APILogConst.DEBUG_3, "[DEBUG] Put RMSMsg to TraceQueue");
				}
			} catch (Exception te) {
				apiLogger.log(Level.WARN, " [WARN] put RMSMsg Fail.");
				te.printStackTrace();
				break;
			}
		}
	}

	/**
	 * make TraceMsg & put traceQ <br>
	 * API Fail
	 **/
	public void putTraceLog(MqAPI mqAPI, InterfaceBO ifConf, MsgAPI apiMsg, APIException e) {
		int i = 1;
		apiLogger = APILogger.getLogger();

		while (i == 1) {
			i = i + 1;
			try {
				// Make traceMsg
				apiMsg.setRMSLog(e);
				apiMsg.getRmsLogBO().setGUID(apiMsg.getAPIMsgBO().getRAHeaderBO().getMsgId());
				
				// TraceMsg Q Put (TraceLevel '00' is skip).
				if (!"".equals(ifConf.getApiConf().getTraceLevel())
						&& !"99".equals(ifConf.getApiConf().getTraceLevel())) {
					mqAPI.mqOpenToPut(ifConf.getApiConf().getTraceQueue());
					mqAPI.setPutOption(MQC.MQPMO_SYNCPOINT);
					mqAPI.mqPut(apiMsg.getRMSMQMsg());
					mqAPI.mqCommit();
					apiLogger.log(APILogConst.DEBUG_3, "[DEBUG] Put RMSMsg to TraceQueue");
				}
			} catch (Exception te) {
				apiLogger.log(Level.WARN, " [WARN] put RMSMsg Fail.");
				te.printStackTrace();
				break;
			}

		}
	}

	public APIException checkException(Exception e) {
		APIException ae = null;
		if (e instanceof APIException) {
			ae = (APIException) e;
		} else {
			ae = new APIException("API", "0005", "MQAPI " + methodName + " Error.", e);
		}
		return ae;
	}
	
	public byte[] setCorrId(InterfaceBO ifConf) throws APIException{
		String s_corrId = "";
		String pid = String.valueOf(ifConf.getApiConf().getPId());
		String hostname = SysProperty.getHostname();
		String dateTime = ifConf.getTriggerTime();
		
		// hostname 셋팅(max 10자리)
		if(hostname.length() > 10) {
			hostname = hostname.substring(hostname.length() - 10, hostname.length());
		}	
				
		// processId 셋팅(4자리 맞춤)
		if(pid.length() != 4){
			if(pid.length() > 4) {
				pid = pid.substring(0,4);
			}
			else {
				if(pid.length()== 3) {
					pid = "0" + pid;
				} else if(pid.length()==2) {
					pid = "00" + pid;
				} else if(pid.length()==1) {
					pid = "000" + pid;
				}
			}
		}
		
		s_corrId = hostname + pid + dateTime;
		
		if(s_corrId.length() > 24){
			s_corrId = s_corrId.substring(0,24);
		}
		
		//System.out.println("@@@@@@ corrId = [" + s_corrId +"]" );
		
		return s_corrId.getBytes();
	}
	
	public byte[] getCorrId() {
		return this.rcvCorrelationId;
	}
	
	public byte[] getMessageId() {
		byte[] messageId;
		
		MQMessage mqtemp = new MQMessage();
		messageId = mqtemp.messageId;
		
		return messageId;
	}

}