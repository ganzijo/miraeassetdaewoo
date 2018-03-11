/*
* @(#)APIConf.java   1.0 2017/08/10
 */
package com.e1.eai.entity;

import com.e1.eai.conf.APIConf;
import com.e1.eai.conf.constant.ConfigConst;
import com.e1.eai.util.CalculationUtil;
import com.e1.eai.util.DateTimeUtil;

/**
 * API 설정 정보.<br>
 * APIConf를 저장하는 entity 객체이다.
 * 
 * @author maPark
 * @version 1.0 - 2017/08/10
 */
public class InterfaceBO {
	private APIConf apiConf;

	/** PT_DT **/
	private String triggerTime = null;
	
	/** makeMsgTime **/
	private String msgMakeTime = null;
	
	/** makeMsgTime **/
	private String msgPutTime = null;
	
	/** makeMsgTime **/
	private String msgFinTime = null;
	
	private String msgId = null;
	
	private String rand_4 = null;

	/** InterfaceID **/
	private String interfaceId;

	/** Data **/
	private String dataStr;

	/** TimeoutInterval **/
	private int timeoutInterval;

	/** FileName **/
	private String fileName;

	/** FilePostCommand **/
	private String filePostCommand;

	/** ReplyFlag **/
	private String replyFlag;

	/** Interface putQ name **/
	private String putQName;

	/** Interface getQ name **/
	private String getQName;

	/** Interface errQ name **/
	private String errQName;
		
	public InterfaceBO(APIConf apiConf) {
		this.interfaceId = "";
		this.rand_4 = CalculationUtil.randomValue(4);
		this.dataStr = "";
		this.timeoutInterval = 0;
		this.fileName = "";
		this.filePostCommand = "";

		this.replyFlag = "";
		this.putQName = "";
		this.getQName = "";
		this.errQName = "";
		this.apiConf = apiConf;
		setTriggerTime(); // PROC_TM_CUST_01 set
	}
	
	/** SND Async **/
	public void setIfConf_sendAsync(String interfaceId, String dataStr) {
		this.interfaceId = interfaceId;
		this.msgId = interfaceId + getTriggerTime() + getRand_4();
		this.replyFlag = "N";
		this.dataStr = dataStr;
		this.putQName = setQname(apiConf.getQNameConf().getPreSndQPT(), this.interfaceId, apiConf.getQNameConf().getPostSndQPT());
	}
	
	/** SND Sync **/
	public void setIfConf_sendSync(String interfaceId, String dataStr, int timeoutInterval) {
		this.interfaceId = interfaceId;
		this.msgId = interfaceId + getTriggerTime() + getRand_4();
		this.replyFlag = "Y";
		this.setTimeoutInterval(timeoutInterval);
		this.dataStr = dataStr;
		this.putQName = setQname(apiConf.getQNameConf().getPreRqsQPT(), this.interfaceId, apiConf.getQNameConf().getPostRqsQPT());
		this.getQName = setQname(apiConf.getQNameConf().getPreRprQPT(), this.interfaceId, apiConf.getQNameConf().getPostRprQPT());
	}
	
	/** RCV Async **/
	public void setIfConf_recvAsync(String interfaceId, int timeoutInterval) {
		this.interfaceId = interfaceId;
		this.msgId = interfaceId + getTriggerTime() + getRand_4();
		this.replyFlag = "N";
		this.setTimeoutInterval(timeoutInterval);
		this.getQName = setQname(apiConf.getQNameConf().getPreRcvQPT(),this.interfaceId,apiConf.getQNameConf().getPostRcvQPT());
	}
	
	/** RCV Sync (dummy) **/
	public void setIfConf_recvSyncEnd(String interfaceId, int timeoutInterval) {
		this.interfaceId = interfaceId;
		this.msgId = interfaceId + getTriggerTime() + getRand_4();
		this.replyFlag = "Y";
		this.setTimeoutInterval(timeoutInterval);
		this.getQName = setQname(apiConf.getQNameConf().getPreRqrQPT(),  this.interfaceId, apiConf.getQNameConf().getPostRqrQPT());
		this.putQName = setQname(apiConf.getQNameConf().getPreRpsQPT(),  this.interfaceId, apiConf.getQNameConf().getPostRpsQPT());
	}
	
	/** RCV Sync **/
	public void setIfConf_recvSync(String interfaceId, int timeoutInterval) {
		this.interfaceId = interfaceId;
		this.msgId = interfaceId + getTriggerTime() + getRand_4();
		this.replyFlag = "Y";
		this.setTimeoutInterval(timeoutInterval);
		this.getQName = setQname(apiConf.getQNameConf().getPreRqrQPT(),  this.interfaceId, apiConf.getQNameConf().getPostRqrQPT());
	}
	
	/** Reply **/
	public void setIfConf_Reply(String interfaceId, String dataStr) {
		this.interfaceId = interfaceId;
		this.msgId = interfaceId + getTriggerTime() + getRand_4();
		this.dataStr = dataStr;
		this.replyFlag = "Y";
		this.putQName = setQname(apiConf.getQNameConf().getPreRpsQPT(), this.interfaceId, apiConf.getQNameConf().getPostRpsQPT());
	}
	
	/** used : API Adapter FileInvoke 1way **/
	public void setAPIFileInvokeConf(String interfaceId, String fileName, String filePostCommand) {
		this.interfaceId = interfaceId;
		this.msgId = interfaceId + getTriggerTime() + getRand_4();
		this.fileName = fileName;
		this.filePostCommand = filePostCommand;
		this.replyFlag = "N";
		this.putQName = setQname(apiConf.getQNameConf().getPreSndQPT(), this.interfaceId, apiConf.getQNameConf().getPostSndQPT());
	}

	/** used : API Adapter FileInvoke 2way **/
	public void setAPIFileInvokeConf(String interfaceId, String fileName, String filePostCommand, int timeoutInterval) {
		this.interfaceId = interfaceId;
		this.msgId = interfaceId + getTriggerTime() + getRand_4();
		this.fileName = fileName;
		this.filePostCommand = filePostCommand;
		this.timeoutInterval = timeoutInterval;
		this.replyFlag = "Y";
		this.putQName = setQname(apiConf.getQNameConf().getPreRqsQPT(), this.interfaceId, apiConf.getQNameConf().getPostRqsQPT());
		this.getQName = setQname(apiConf.getQNameConf().getPreRprQPT(), this.interfaceId, apiConf.getQNameConf().getPostRprQPT());
	}

	// Set Start ////////////////////////////////////////////////////////
	/** set value : triggerTime(서비스 요청시간) **/
	public void setTriggerTime() {
		this.triggerTime = DateTimeUtil.getDateTime(DateTimeUtil.RA_DATETIMEMS);
		//System.out.println("triggerTime :" + triggerTime);
	}
	
	/** set value : msgMakeTime(메시지 생성시간) **/
	public void setProdTM02() {
		this.msgMakeTime = DateTimeUtil.getDateTime(DateTimeUtil.RA_DATETIMEMS);
		//System.out.println("msgMakeTime :" + msgMakeTime);
	}
	
	/** set value : msgPutTime(큐 put 시간) **/
	public void setProdTM03() {
		this.msgPutTime = DateTimeUtil.getDateTime(DateTimeUtil.RA_DATETIMEMS);
		//System.out.println("msgPutTime :" + msgPutTime);
	}
	
	/** set value : msgFinTime(서비스 종료시간) **/
	public void setProdTM04() {
		this.msgFinTime = DateTimeUtil.getDateTime(DateTimeUtil.RA_DATETIMEMS);
		//System.out.println("msgFinTime :" + msgFinTime);
	}
		
	/** set value : InterfaceId **/
	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	/** set value : dataStr **/
	public void setDataStr(String dataStr) {
		this.dataStr = dataStr;
	}

	/** set value : timeoutInterval **/
	public void setTimeoutInterval(int timeoutInterval) {
		this.timeoutInterval = timeoutInterval;
	}

	/** set value : FileName **/
	public void setFimename(String fileName) {
		this.fileName = fileName;
	}

	/** set value : filePostCommand **/
	public void setFilePostCommand(String filePostCommand) {
		this.filePostCommand = filePostCommand;
	}

	/** set value : replyFlag **/
	public void setReplyFlag(String replyFlag) {
		this.replyFlag = replyFlag;
	}

	// Get Start ////////////////////////////////////////////////////////
	/**
	 * get value : filePostCommand
	 * 
	 * @return InterfaceID
	 **/
	public String getInterfaceId() {
		return interfaceId;
	}

	/**
	 * get value : data(String)
	 * 
	 * @return DataStr
	 **/
	public String getDataStr() {
		return dataStr;
	}

	/**
	 * get value : TimeoutInterval
	 * 
	 * @return TimeoutInterval
	 **/
	public int getTimeoutInterval() {
		return timeoutInterval;
	}

	/**
	 * get value : FileName
	 * 
	 * @return Filename
	 **/
	public String getFilename() {
		return fileName;
	}

	/**
	 * get value : filePostCommand
	 * 
	 * @return PostCommand
	 **/
	public String getFilePostCommand() {
		return filePostCommand;
	}

	/**
	 * get value : replyFlag
	 * 
	 * @return ReplyFlag
	 **/
	public String getReplyFlag() {
		return replyFlag;
	}

	/** get REQName **/
	public String getPutQ() {
		return this.putQName;
	}

	/** get RPLQName **/
	public String getGetQ() {
		return this.getQName;
	}

	/** get ERRQName **/
	public String getErrQ() {
		return this.errQName;
	}

	public String getPT_DT() {
		// TriggerTime 에서 8자리 추출
		return this.triggerTime.substring(0, 8);
	}
	
	public String getTriggerTime() {
		//System.out.println("triggerTime : " + triggerTime);
		return this.triggerTime;
	}
	
	public String getMsgMakeTime() {
		//System.out.println("msgMakeTime : " + msgMakeTime);
		return this.msgMakeTime;
	}
	
	public String getMsgPutTime() {
		//System.out.println("msgPutTime : " + msgPutTime);
		return this.msgPutTime;
	}
	
	public String getMsgFinTime() {
		//System.out.println("msgFinTime : " +  msgFinTime);
		return this.msgFinTime;
	}
	
	public String getRecordLengt() {
		return  String.valueOf(this.dataStr.length());
	}
	
	public String getMsgId(){
		return this.msgId;
	}
	
	public String getRand_4(){
		return this.rand_4;
	}
	
	public APIConf getApiConf() {
		return this.apiConf;
	}
	
	public String setQname(String preQPT, String ifid, String postQPT) {
		String qName = "";
		if(preQPT!=null) {
			qName = preQPT;
		}
		
		qName = qName + ifid;
		
		if(postQPT!=null) {
			qName = qName + postQPT;
		}
		return qName;
	}
}