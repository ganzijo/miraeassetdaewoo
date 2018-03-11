/*
* @(#)MqAPI.java   1.0 2003/10/21
 */
package com.e1.eai.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.logging.log4j.Logger;

import com.e1.eai.conf.APIConf;
import com.e1.eai.excp.APIException;
import com.e1.eai.log.APILogConst;
import com.ibm.mq.MQC;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;

/**
 * @author JangJooHwi
 * @version 1.0, 2005/07/21
 *
 *          MQ 관련 Library<br>
 *          큐메니저에 Connect/Disconnect, 큐를 Open/Close, 큐에 메시지를 Put/Get, 메시지 옵션
 *          지정등의 기능을 지원하며 <code>MQQueueManager</code> 을 Wrapping 한 MQ 관련 Library
 *          이다.
 */
public class MqAPI {
	private static MqAPI mqApi = null;
	
	private String methodName = "";
	
	private MQQueueManager mqMgr = null; // QueueManager
	private MQQueue mqQueue = null; // Queue
	private MQGetMessageOptions gmo = new MQGetMessageOptions();
	private MQPutMessageOptions pmo = new MQPutMessageOptions();
	
	private Logger apiLogger;

	public MqAPI(Logger apiLogger) throws APIException {
		this.apiLogger = apiLogger;
	}
	
	/** getInstance **/
	public static MqAPI getInstance(Logger apiLogger) throws APIException {
		// 최초 MQ Load시 여부 Check
		if (mqApi == null) {
			synchronized (MqAPI.class) {
				// perform double-check to avoid race condition
				if (mqApi == null) {
					mqApi = new MqAPI(apiLogger);
					apiLogger.log(APILogConst.DEBUG_5, "[DEBUG] newInstance MqAPI");
				}
			}
		}else {
			apiLogger.log(APILogConst.DEBUG_5, "[DEBUG] getInstance MqAPI");
		}
		return mqApi;
	}

	public int mqConn(APIConf apiConf) throws APIException {
		methodName = "mqConn";
		try {
			if (this.mqMgr != null) {
				this.mqDisc();
			}
			// openConnection
			this.mqMgr = MQConnectionMgr.openConnection(apiConf);
		} catch (MQException mqe) {
			//throw new APIException("MQE", mqe.reasonCode, "Connect QMGR (" + apiConf.getQmgr() + ") Failed.[" + getMQErrorCodeToString(mqe.reasonCode) + "]", mqe);
		} catch (Exception e) {
			//throw new APIException("API", "9999", "Connect QMGR(" + apiConf.getQmgr() + ") Failed.", e);
		}
		if (this.mqMgr != null) {
			return 1;
		} else {
			return 0;
		}
	}

	public void mqDisc() throws APIException {
		methodName = "mqDisc";
		if (this.mqMgr != null) {
			try {
				this.mqMgr.close();
				this.mqMgr.disconnect();
			} catch (MQException mcpe) {
				throw new APIException(mcpe);
			} catch (Exception e) {
				throw new APIException("API", "0005", "MQAPI " + methodName + " Error.");
			} finally {
				// reset all member variable
				this.mqMgr = null;
				this.mqQueue = null;
				this.gmo = new MQGetMessageOptions();
				this.pmo = new MQPutMessageOptions();
			}
		}
	}

	/**
	 * Open Option을 사용하여 큐를 오픈한다.
	 *
	 * @param objectName
	 *            Queue Name
	 * @param openOptions
	 *            Open Option
	 * @throws APIException
	 */
	public void mqOpen(String objectName, int openOptions) throws APIException {
		methodName = "mqOpen";
		
		if (this.mqMgr == null) {
			throw new APIException("MQE", "2009", "MQ Manager Not Connected");
			//throw new APIException("API", "9999", "MQ Manager Not Connected");
		}
		try {
			if (this.mqQueue != null) {
				this.mqClose();
			}
			this.mqQueue = mqMgr.accessQueue(objectName, openOptions);
		} catch (MQException mqe) {
			throw new APIException("MQE", mqe.reasonCode, "accessQueue (" + objectName + ") Failed.[" + getMQErrorCodeToString(mqe.reasonCode) + "]", mqe);
		} catch (Exception e) {
			throw new APIException("API", "0005", "MQAPI " + methodName + " Error.");
		}
	}

	/**
	 * 큐 연결을 해제한다.
	 *
	 * @throws APIException
	 */
	public void mqClose() throws APIException {
		methodName = "mqClose";
		
		if (this.mqMgr != null && this.mqQueue != null) {
			try {
				this.mqQueue.close();
			} catch (MQException mqe) {
				throw new APIException("MQE", mqe.reasonCode,
						"Close MQ (" + this.mqQueue.name + ") Failed.[" + getMQErrorCodeToString(mqe.reasonCode) + "]",
						mqe);
			} catch (Exception e) {
				throw new APIException("API", "0005", "MQAPI " + methodName + " Error.");
			} finally {
				// reset member variable
				this.mqQueue = null;
				this.gmo = new MQGetMessageOptions();
				this.pmo = new MQPutMessageOptions();
			}
		}
	}

	/**
	 * 큐를 오픈한다.
	 *
	 * @param func
	 *            1:get용, 2:put용
	 * @param objectName
	 *            Queue Name
	 * @throws APIException
	 */
	public void mqOpen(int func, String objectName) throws APIException {
		if (func == 1) { // open to get
			this.mqOpen(objectName, MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_FAIL_IF_QUIESCING);
			apiLogger.log(APILogConst.DEBUG_5, "[DEBUG] Open GetQ Success.");
		} else { // open to put
			this.mqOpen(objectName, MQC.MQOO_OUTPUT | MQC.MQOO_FAIL_IF_QUIESCING);
			apiLogger.log(APILogConst.DEBUG_5, "[DEBUG] Open PutQ Success.");
		}
	}

	/**
	 * 큐에 메시지를 Put 하기 위해 큐를 오픈한다.
	 *
	 * @param objectName
	 *            Queue Name
	 * @throws APIException
	 */
	public void mqOpenToPut(String objectName) throws APIException {
		this.mqOpen(2, objectName);
	}

	/**
	 * 큐에서 메시지를 Get 하기 위해 큐를 오픈한다.
	 *
	 * @param objectName
	 *            Queue Name
	 * @throws APIException
	 */
	public void mqOpenToGet(String objectName) throws APIException {
		this.mqOpen(1, objectName);

		this.gmo.options = MQC.MQGMO_WAIT;
		this.gmo.waitInterval = MQC.MQWI_UNLIMITED;
		apiLogger.log(APILogConst.DEBUG_5, "[DEBUG] Set Waiting Time to Unlimited");
	}

	/**
	 * 큐에서 메시지를 Get 하기 위해 큐를 오픈한다.
	 *
	 * @param objectName
	 *            Queue Name
	 * @param waitTime
	 *            Wating Time(in milliseconds)
	 * @throws APIException
	 */
	public void mqOpenToGet(String objectName, int waitTime) throws APIException {
		this.mqOpen(1, objectName);

		// this.gmo.options = MQC.MQGMO_NO_WAIT;
		this.gmo.options = MQC.MQGMO_WAIT;
		this.gmo.waitInterval = waitTime;
		apiLogger.log(APILogConst.DEBUG_5, "[DEBUG] Set Waiting Time to " + this.gmo.waitInterval);
	}

	/**
	 * 큐에서 메시지를 Get 하기 위한 옵션을 설정한다.
	 *
	 * @param getOptions
	 *            Get Message Options
	 */
	public void setGetOption(int getOptions) {
		this.gmo.options = this.gmo.options | getOptions;
		apiLogger.log(APILogConst.DEBUG_5, "[DEBUG] Set GetQ Option.");
	}

	/**
	 * 큐에 메시지를 Put 하기 위한 옵션을 설정한다.
	 *
	 * @param putOptions
	 *            Get Message Options
	 */
	public void setPutOption(int putOptions) {
		this.pmo.options = this.pmo.options | putOptions;
		apiLogger.log(APILogConst.DEBUG_5, "[DEBUG] Set PutQ Option.");
	}

	/**
	 * 큐에서 메시지를 가져온다.
	 *
	 * @param mqMsg
	 *            MQMD값이 설정된 MQMessage
	 * @throws APIException
	 */
	public String mqGet(MQMessage mqMsg) throws APIException {
		String returnMsg = "";

		if (this.mqQueue == null) {
			throw new APIException("MQE", "9998", "MQ Object Not Opened.");
			//throw new APIException("MQE", "9998", "MQ Object Not Opened.");
		}
		try {
			this.mqQueue.get(mqMsg, this.gmo);
			byte[] b_rcvMsg = new byte[mqMsg.getMessageLength()];
			mqMsg.readFully(b_rcvMsg);
			returnMsg = new String(b_rcvMsg);
		} catch (Exception e) {
			if (e instanceof MQException) {
				MQException mqe = (MQException) e;
				if (mqe.reasonCode == MQException.MQRC_NO_MSG_AVAILABLE) {
					throw new APIException("MQE", mqe.reasonCode,
							"Recv MQ MSG From MQ (" + this.mqQueue.name.trim() + ") Failed.["
									+ getMQErrorCodeToString(mqe.reasonCode) + "] :Recv Wait Time["
									+ this.gmo.waitInterval / 1000 + "sec] Out Occured",
							mqe);
				} else {
					throw new APIException("MQE", mqe.reasonCode, "Recv MQ MSG From MQ (" + this.mqQueue.name.trim()
							+ ") Failed.[" + getMQErrorCodeToString(mqe.reasonCode) + "]", mqe);
				}
			} else {
				throw new APIException("MQE", "9996",
						"Get MQ MSG From MQ (" + this.mqQueue.name + ") Failed.[" + e.getMessage() + "]", e);
			}

		} finally {
			this.gmo = new MQGetMessageOptions();
		}
		return returnMsg;
	}

	/**
	 * 큐에 메시지를 넣는다.
	 *
	 * @param mqMsg
	 *            메시지
	 * @throws APIException
	 */
	public void mqPut(MQMessage mqMsg) throws APIException {
		if (this.mqQueue == null) {
			throw new APIException("API", "9998", "MQ Queue Not Opened.");
		}

		try {
			mqMsg.feedback = MQC.MQFB_NONE;
			mqMsg.messageType = MQC.MQMT_DATAGRAM;
			mqMsg.persistence = MQC.MQPER_PERSISTENT;

			this.mqQueue.put(mqMsg, this.pmo);
		} catch (MQException mqe) {
			throw new APIException("MQE", mqe.reasonCode, "mqPut (" + this.mqQueue.name + ") Failed.[" + getMQErrorCodeToString(mqe.reasonCode) + "]", mqe);
		} catch (Exception e) {
			throw new APIException("API", "0005", "MQAPI " + methodName + " Error.");
		}
	}

	/**
	 * MQC.MQPMO_SYNCPOINT 지정된 메시지를 Rollback 한다.
	 *
	 * @throws APIException
	 */
	public void mqBack() throws APIException {
		if (this.mqMgr != null) {
			try {
				this.mqMgr.backout();
			} catch (MQException mqe) {
				throw new APIException("MQE", mqe.reasonCode, "MQBack MQ MGR(" + this.mqMgr.name + ") Failed.["
						+ getMQErrorCodeToString(mqe.reasonCode) + "]", mqe);
			} catch (Exception e) {
				throw new APIException("API", "0005", "MQAPI " + methodName + " Error.");
			}
		}
	}

	/**
	 * MQC.MQPMO_SYNCPOINT 지정된 메시지를 Commit 을 실행한다.
	 *
	 * @throws APIException
	 */
	public void mqCommit() throws APIException {
		if (this.mqMgr != null) {
			try {
				this.mqMgr.commit();
			} catch (MQException mqe) {
				throw new APIException("MQE", mqe.reasonCode, "MQCommit (" + this.mqMgr.name + ") Failed.["
						+ getMQErrorCodeToString(mqe.reasonCode) + "]", mqe);
			} catch (Exception e) {
				throw new APIException("API", "0005", "MQAPI " + methodName + " Error.");
			}
		}
	}

	/**
	 * Queue Depth 를 반환한다.
	 *
	 * @param objectName
	 *            Queue Name
	 * @return int
	 * @throws APIException
	 */
	public int getMqDepth(String objectName) throws APIException {
		int queueDepth;

		try {
			this.mqOpen(objectName, MQC.MQOO_INQUIRE);
			queueDepth = this.mqQueue.getCurrentDepth();
		} catch (MQException mqe) {
			throw new APIException("MQE", mqe.reasonCode,
					"GET MQ Depth MQ(" + objectName + ") Failed.[" + getMQErrorCodeToString(mqe.reasonCode) + "]", mqe);
		} catch (Exception e) {
			throw new APIException("API", "0005", "MQAPI " + methodName + " Error.");
		} finally {
			this.mqClose();
		}

		return queueDepth;
	}

	public String getMQErrorCodeToString(int reasonCode) {
		if (reasonCode <= 0)
			return "";

		String osName = System.getProperty("os.name");
		StringBuffer sb = new StringBuffer();

		try {
			String[] mqrc = null;
			if (osName.indexOf("Window") != -1) {
				// Windows 98 이하에선 cmd.exe -> command.exe
				String[] cmd = { "cmd.exe", "/c", "mqrc", String.valueOf(reasonCode) };
				mqrc = cmd;
			} else {
				String[] cmd = { "mqrc", String.valueOf(reasonCode) };
				mqrc = cmd;
			}

			Runtime rt = Runtime.getRuntime();

			Process proc_mqrc = rt.exec(mqrc, null);

			BufferedReader in = new BufferedReader(new InputStreamReader(proc_mqrc.getInputStream()));

			String bf = null;
			
			while ((bf = in.readLine()) != null) {
				sb.append(bf);
			}
		} catch (IOException ioe) {
			// ignore
		}
		//apiLogger.log(APILogConst.DEBUG_5, "[DEBUG] MQ Error Code Return");

		return sb.toString().trim();
	}
}