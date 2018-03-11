/*
* @(#)APIConf.java   1.0 2017/08/10
 */

package com.e1.eai.entity;

import com.e1.eai.conf.constant.ConfigConst;
import com.e1.eai.excp.APIException;

/**
 * APIMsg 데이터(header+Body)전문 생성<br>
 * - RAHeaderBO
 * 
 * @author maPark
 * @version 1.0 - 2017/08/10
 */
public class APIMsgBO {

	// 데이터 전문 RA_HEADER
	private InterfaceBO ifConf = null;
	private RAHeaderBO raHeader;
	private String apiBody = "";

	private String columnSep = ConfigConst.SEP_RA_COLUMN; // %**%
	private String headerBodySep = ConfigConst.SEP_RA_HEADERBODY; // %$$%

	public APIMsgBO(InterfaceBO ifConf) {
		this.raHeader = new RAHeaderBO();
		this.ifConf = ifConf;
	}

	/** putMsg Setting **/
	public void SetAPIHeader(String HopCont) throws APIException {
		/* 1 MessageId */
		// this.raHeader.setMsgId();
		/* 2 InterfaceId */ // this.raHeader.setInterfaceId(ifConf.getInterfaceId());
		/* 3 InterfaceSubid */ // this.raHeader.setInterfaceSubId();

		/* 1 + 2 + 3 CreateID */ this.raHeader.createId(this.ifConf);

		/* 4 HopCount */ this.raHeader.setHopCnt(HopCont);
		/* 5 TraceLevel */ this.raHeader.setTraceLevel(this.ifConf.getApiConf().getTraceLevel());
		/* 6 MessageSeq */ this.raHeader.setMsgSeq(String.valueOf(ConfigConst.RECORD_CNT));
		/* 7 SndSystemName */ // this.raHeader.setSndSystemName("");
		/* 8 RcvSystemName */ // this.raHeader.setRcvSystemName("");
		/* 9 RecordCount */ this.raHeader.setRecordCnt(String.valueOf(ConfigConst.RECORD_CNT));
		/* 10 RecordLength */ this.raHeader.setRecordLength(String.valueOf(this.ifConf.getDataStr().length()));
		/* 11 EndOfRecord */ this.raHeader.setEndOfRecord(String.valueOf(ConfigConst.RECORD_CNT));
		/* 12 FileName */ this.raHeader.setFileName(this.ifConf.getFilename());
		/* 13 ProcessId */ // this.raHeader.setProcessId("");
		/* 14 Timeout */ this.raHeader.setTimeoutInterval(String.valueOf(ifConf.getTimeoutInterval()));
		/* 15 ReplyFlag */ this.raHeader.setReplyFlag(ifConf.getReplyFlag());
		/* 16 FileFullSize */ // this.raHeader.setFileFullSize("");
		/* 17 FileCurrentSize */ // this.raHeader.setFileCurrentSize("");
		/* 18 FileSplitCnt */ // this.raHeader.setFileSplitCnt("");
		/* 19 FileCnt */ // this.raHeader.setFileCnt("");
		/* 20 FileEncodingType */ this.raHeader.setFileEncodingType(ConfigConst.ECD_TEXT);
		/* 21 EncryptType */ this.raHeader.setFileEncryptType(ConfigConst.ECT_NO);
		/* 22 FileHash */ // this.raHeader.setFileHash("");
		/* 23 FilePostCommand */ this.raHeader.setFilePostCommand(ifConf.getFilePostCommand());
		/* 24 RESERVED_1 */ this.raHeader.setRESERVED_1("");
	}

	/** getMessage Setting **/
	public void SetAPIHeader(String header, String hopCount) throws APIException {

		String[] temp = header.split(ConfigConst.SPLIT_RA_COLUMN, -1); // header devide

		/* 1 MessageId */ this.raHeader.setMsgId(temp[0]);
		/* 2 InterfaceId */ this.raHeader.setInterfaceId(temp[1]);
		/* 3 InterfaceSubid */ this.raHeader.setInterfaceSubId(temp[2]);
		/* 4 HopCount */ this.raHeader.setHopCnt(hopCount);
		/* 5 TraceLevel */ this.raHeader.setTraceLevel(ifConf.getApiConf().getTraceLevel());
		/* 6 MessageSeq */ this.raHeader.setMsgSeq(temp[5]);
		/* 7 SndSystemName */ this.raHeader.setSndSystemName(temp[6]);
		/* 8 RcvSystemName */ this.raHeader.setRcvSystemName(temp[7]);
		/* 9 RecordCount */ this.raHeader.setRecordCnt(temp[8]);
		/* 10 RecordLength */ this.raHeader.setRecordLength(String.valueOf(temp[9].length()));
		/* 11 EndOfRecord */ this.raHeader.setEndOfRecord(temp[10]);
		/* 12 FileName */ this.raHeader.setFileName(temp[11]);
		/* 13 ProcessId */ this.raHeader.setProcessId(temp[12]);
		/* 14 Timeout */ this.raHeader.setTimeoutInterval(temp[13]);
		/* 15 ReplyFlag */ this.raHeader.setReplyFlag(temp[14]);
		/* 16 FileFullSize */ this.raHeader.setFileFullSize(temp[15]);
		/* 17 FileCurrentSize */ this.raHeader.setFileCurrentSize(temp[16]);
		/* 18 FileSplitCnt */ this.raHeader.setFileSplitCnt(temp[17]);
		/* 19 FileCnt */ this.raHeader.setFileCnt(temp[18]);
		/* 20 FileEncodingType */ this.raHeader.setFileEncodingType(temp[19]);
		/* 21 EncryptType */ this.raHeader.setFileEncryptType(temp[20]);
		/* 22 FileHash */ this.raHeader.setFileHash(temp[21]);
		/* 23 FilePostCommand */ this.raHeader.setFilePostCommand(temp[22]);
		/* 24 RESERVED_1 */ this.raHeader.setRESERVED_1(temp[23]);
	}

	/**
	 * APIMsg_Body 전문 셋팅
	 * 
	 * @return String
	 **/
	public void SetAPIBody(String raData) throws APIException {
		if (raData.equals("") || raData.length() == 0)
			this.apiBody = "";
		else
			this.apiBody = raData;
	}

	/**
	 * APIMsgData 전문 셋팅
	 * 
	 * @return String
	 **/
	public void setAPIMsg(InterfaceBO ifConf, String hopCount) throws APIException {
		SetAPIHeader(hopCount);
		SetAPIBody(ifConf.getDataStr());
	}

	/**
	 * Header에 설정된 데이터를 MQ 전송용 Data로 반환.
	 * 
	 * @return MQ Header Data
	 */
	public String getAPIHeader() {
		String headerData = "";

		headerData = /* 1 MessageId */ this.raHeader.getMsgId() + columnSep
		/* 2 InterfaceId */ + this.raHeader.getInterfaceId() + columnSep
		/* 3 InterfaceSubid */ + this.raHeader.getInterfaceSubId() + columnSep
		/* 4 HopCount */ + this.raHeader.getHopCnt() + columnSep
		/* 5 TraceLevel */ + this.raHeader.getTraceLevel() + columnSep
		/* 6 MessageSeq */ + this.raHeader.getMsgSeq() + columnSep
		/* 7 SndSystemName */ + this.raHeader.getSndSystemName() + columnSep
		/* 8 RcvSystemName */ + this.raHeader.getRcvSystemName() + columnSep
		/* 9 RecordCount */ + this.raHeader.getRecordCnt() + columnSep
		/* 10 RecordLength */ + this.raHeader.getRecordLength() + columnSep
		/* 11 EndOfRecord */ + this.raHeader.getEndOfRecord() + columnSep
		/* 12 FileName */ + this.raHeader.getFileName() + columnSep
		/* 13 ProcessId */ + this.raHeader.getProcessId() + columnSep
		/* 14 Timeout */ + this.raHeader.getTimeoutInterval() + columnSep
		/* 15 ReplyFlag */ + this.raHeader.getReplyFlag() + columnSep
		/* 16 FileFullSize */ + this.raHeader.getFileFullSize() + columnSep
		/* 17 FileCurrentSize */ + this.raHeader.getFileCurrentSize() + columnSep
		/* 18 FileSplitCnt */ + this.raHeader.getFileSplitCnt() + columnSep
		/* 19 FileCnt */ + this.raHeader.getFileCnt() + columnSep
		/* 20 FileEncodingType */ + this.raHeader.getFileEncodingType() + columnSep
		/* 21 EncryptType */ + this.raHeader.getFileEncryptType() + columnSep
		/* 22 FileHash */ + this.raHeader.getFileHash() + columnSep
		/* 23 FilePostCommand */ + this.raHeader.getFilePostCommand() + columnSep
		/* 24 RESERVED_1 */ + this.raHeader.getRESERVED_1();
		return headerData;
	}

	public String getAPIBody() {
		return this.apiBody;
	}

	/**
	 * APIMsg_Full 전문 반환
	 * 
	 * @return String
	 **/
	public String getAPIFullMsg() {
		StringBuffer buf = new StringBuffer();
		String fullMessage = "";

		buf.append(this.getAPIHeader());
		buf.append(headerBodySep);
		buf.append(this.apiBody);

		fullMessage = buf.toString();
		return fullMessage;
	}

	public String getHopCount() {
		return this.raHeader.getHopCnt();
	}

	public RAHeaderBO getRAHeaderBO() {
		return this.raHeader;
	}

}