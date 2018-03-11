/*
 * @(#)EAIMessage.java   1.0 2017/08/10
 */
package com.e1.eai.helper;

import java.io.IOException;

import org.apache.logging.log4j.Logger;

import com.e1.eai.conf.constant.ConfigConst;
import com.e1.eai.entity.APIMsgBO;
import com.e1.eai.entity.InterfaceBO;
import com.e1.eai.entity.RMSLogBO;
import com.e1.eai.excp.APIException;
import com.e1.eai.log.APILogConst;
import com.e1.eai.log.APILogger;
import com.ibm.mq.MQC;
import com.ibm.mq.MQMessage;

/**
 * API Msg 생성 및 반환<br>
 * RMS Tracking Msg 생성 및 반환
 * @author maPark
 * @version 1.0 - 2017/08/10
 */
public class MsgAPI {
	private String methodName = null;
	
	private MQMessage mqMsg = null;
	private APIMsgBO apiMsg = null;
	private RMSLogBO rmsLogBO = null;

	private InterfaceBO ifConf;
	private Logger apiLogger;
	
    public MsgAPI(InterfaceBO ifConf){
    	this.apiLogger = APILogger.getLogger();
    	this.ifConf = ifConf;
    	
    	this.mqMsg      = new MQMessage();
    	this.apiMsg     = new APIMsgBO(ifConf);
    	this.rmsLogBO   = new RMSLogBO();
    }
    
    public MsgAPI(InterfaceBO ifConf, byte[] msgID){
    	this.apiLogger = APILogger.getLogger();
    	this.ifConf = ifConf;
    	
    	this.mqMsg      = new MQMessage();
    	this.apiMsg     = new APIMsgBO(ifConf);
    	this.rmsLogBO   = new RMSLogBO();
    }
    
    /** APIMsg 전문 셋팅 (RA Header + RA Body) **/
    public void setAPIMsg(String hopCount) throws APIException{
    	methodName = "setAPIMsg";
    	    	
    	// apiMsg 생성되지 않았다면 오류발생
    	if(apiMsg == null) {
    		throw new APIException("API", "0010", "make APIMsg Fail!");
    	}
    	apiMsg.setAPIMsg(ifConf, hopCount);
    	apiLogger.log(APILogConst.DEBUG_5,"[DEBUG] Set APIMsg.");
    }
    
    /** RMSMsg 트래킹 전문 셋팅 (성공) **/
    public void setRMSLog() throws APIException{
    	methodName = "setRMSLog_S";
    	
    	rmsLogBO.setRAHeader(ifConf, apiMsg);
    	apiLogger.log(APILogConst.DEBUG_5,"[DEBUG] Set RMSMsg(Success).");
    }
    
    /** RMSMsg 트래킹 전문 셋팅 (실패) **/
    public void setRMSLog(APIException e) throws APIException{
    	methodName = "setRMSLog_F";
    	
    	rmsLogBO.setRAHeader(ifConf, apiMsg);
    	rmsLogBO.setRTN_CD(ConfigConst.RMS_FAIL);
    	rmsLogBO.setERR_CD(String.valueOf(e.getErrorCd()));
    	rmsLogBO.setERR_MSG(e.getErrorMessage());
    	rmsLogBO.setERR_DETAIL(e.getErrorData().length() > 999 ? e.getErrorData().substring(0,ConfigConst.MAX_BODY_LEN) : e.getErrorData() );
    	rmsLogBO.setRCOL_2("0");
    	apiLogger.log(APILogConst.DEBUG_5,"[DEBUG] Set RMSMsg(Fail).");
    }

    /** APIMsg MQ 전문 반환 (RA Header + RA Body)
 	* @return MQMessage **/
	public MQMessage getAPIMQMsg() throws APIException {
		methodName = "getAPIMQMsg";
		
    	mqMsg.format = MQC.MQFMT_STRING;
    	mqMsg.characterSet = 1208;
    	try {
    			mqMsg.clearMessage();
    			// MQ 메시지 생성
    			mqMsg.writeString(apiMsg.getAPIFullMsg());
    	}catch(IOException ioe) {
            throw new APIException("API", "0012", "make MQMessage(" + methodName + ") Fail!", ioe);
        } catch (Exception e) {
			throw new APIException("API", "0005", "MQAPI " + methodName + " Error.");
		}
        return mqMsg;
    }
    
	/** RMSMsg MQ 전문 반환
	* @return MQMessage **/
	public MQMessage getRMSMQMsg() throws APIException {
		methodName = "getRMSMQMsg";
		
    	mqMsg.format = MQC.MQFMT_STRING;
    	try {
    			mqMsg.clearMessage();
    			mqMsg.writeString(rmsLogBO.getRMSLog());
    	} catch(IOException ioe) {
    		throw new APIException("API", "0012", "make MQMessage(" + methodName + ") Fail!", ioe);
        } catch (Exception e) {
			throw new APIException("API", "0005", "MQAPI " + methodName + " Error.");
		}
        return mqMsg;
    }
	
	public void extractAPIMsg(String rcvMsg, String hopCount) throws APIException{	
		methodName = "extractAPIMsg";
	
		String[] temp = rcvMsg.split(ConfigConst.SPLIT_RA_HEADERBODY,-1); // Header-Body 구분
		
		// return 전문 정합 check
		if(temp.length != 2){
			if(rcvMsg.length() < 1000){
				throw new APIException("API", "0013", "Please check the received data. [Receive data = " + rcvMsg + "]");
			}else{
				throw new APIException("API", "0013", "Please check the received data. [Receive data = " + rcvMsg + "]");
				//throw new APIException("API", "0013", "Please check the received data. [Receive data = " + rcvMsg.substring(0, ConfigConst.MAX_BODY_LEN) + "]");
			}
		}
		
		try {
			// RMS의 Msg 영역을 위해 apiMSG 셋팅
			String t_Header = temp[0];
			String t_Body = temp[1];
			
			apiMsg.SetAPIHeader(t_Header, hopCount);
			apiMsg.SetAPIBody(t_Body);
			
			// return 수신 메시지 셋팅
			ifConf.setDataStr(apiMsg.getAPIBody());
		}catch(Exception e) {
			throw new APIException("API", "0013", "Please check the received data. [Receive data = " + rcvMsg + "]");
		}
	}
	
	public RMSLogBO getRmsLogBO() {
		return this.rmsLogBO;
	}
	
	public APIMsgBO getAPIMsgBO() {
		return this.apiMsg;
	}
}