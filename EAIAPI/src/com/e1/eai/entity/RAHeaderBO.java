/**
 * 0. Project	: KT Rainbow Adapter 프로젝트
 * 1. FileName	: RAHeaderBo.java
 * 2. Package	: com.e1.eai.entity
 * 3. Comment	: RA Header Value Object
 * 4. 작성자	: 김현수
 * 5. 작성일	: 2015. 2. 10. 오전 10:54:00
 * 6. 변경이력	:     
 *	  이름						:일자			:	변경내용
 *	------------------------------------------------------
 *	hskim@enterprise1.co.kr		:2015. 2. 10.	:	신규 개발
 * 	sakim@enterprise1.co.kr		:2015. 9. 16.	: 	주석 정리
 *  maPark@enterprise1.co.kr    :2017. 8. 08.   :   API Adapter 컬럼추가
 */
package com.e1.eai.entity;

public class RAHeaderBO {

	/** 1. RAHeader Message ID **/
	private String msgId;

	/** 2. RAHeader Interface ID **/
	private String interfaceId;

	/** 3. RAHeader Interface Sub ID **/
	private String interfaceSubId;

	/** 4. RAHeader HOPCNT **/
	private String hopCnt;

	/** 5. RAHeader TraceLevel **/
	private String traceLevel;

	/** 6. RAHeader Message Sequence **/
	private String msgSeq;

	/** 7. RAHeader SND System Name **/
	private String sndSystemName; // 15.06.10 add

	/** 8. RAHeader RCV System Name **/
	private String rcvSystemName; // 15.06.10 add

	/** 9. RAHeader Record Count **/
	private String recordCnt;

	/** 10. RAHeader Record Length **/
	private String recordLength;

	/** 11. RAHeader End Of Record Flag **/
	private String endOfRecord;

	/** 12. RAHeader FileName [ FileAdapter ] **/
	private String fileName;

	/** 13. RAHeader ProcessID **/
	private String processId;

	/** 14. RAHeader Time Out Interval **/
	private String timeoutInterval;

	/** 15. RAHeader Retry Flag **/
	private String replyFlag;

	/** 16. RAHeader fileFullSize **/
	private String fileFullSize;

	/** 17. RAHeader FileCurrentSize **/
	private String fileCurrentSize;

	/** 18. RAHeader FileSplitCnt **/
	private String fileSplitCnt;

	/** 19. RAHeader FileCnt **/
	private String fileCnt;

	/** 20. RAHeader FileEncodingType **/
	private String fileEncodingType;

	/** 21. RAHeader FileEncryptType **/
	private String fileEncryptType;

	/** 22. RAHeader FileHash **/
	private String fileHash;

	/** 23. RAHeader FilePostCommand **/
	private String filePostCommand;

	/** 24. RAHeader RESERVED_1 **/
	private String RESERVED_1;

	/**
	 * Constructor
	 * 
	 * @param interfaceId
	 *            Interface ID
	 */
	public void RAHeader(String interfaceId) {
		setInterfaceId(interfaceId);
	}

	/**
	 * Header에 설정된 데이터를 MQ 전송용 Data로 반환해주는 Method
	 * 
	 * @param columnSep
	 * @param headerBodySep
	 * @return MQ Header Data
	 */
	public String getHeaderDataToMQString(String columnSep, String headerBodySep) {
		// public String getHeaderDataToMQString(){
		// 15.05.13 column/headerBody 구분자 추가로 넘겨 받도록 변경

		String headerData = "";
		// 15.05.13 사용자 정의 구분자 사용에 따른 변경(ex) AgentConst.COLUMNSEP -> columnSep)
		// 15.06.10 getSndSystemName + getRcvSystemName 추가
		// 17.08.08 API Adapter 컬럼추가
		headerData = getMsgId() + columnSep // 1
				+ getInterfaceId() + columnSep // 2
				+ getInterfaceSubId() + columnSep // 3
				+ getHopCnt() + columnSep // 4
				+ getTraceLevel() + columnSep // 5
				+ getMsgSeq() + columnSep // 6
				+ getSndSystemName() + columnSep // 7
				+ getRcvSystemName() + columnSep // 8
				+ getRecordCnt() + columnSep // 9
				+ getRecordLength() + columnSep // 10
				+ getEndOfRecord() + columnSep // 11
				+ getFileName() + columnSep // 12
				+ getProcessId() + columnSep // 13
				+ getTimeoutInterval() + columnSep // 14
				+ getReplyFlag() + columnSep // 15
				+ getFileFullSize() + columnSep // 16
				+ getFileCurrentSize() + columnSep // 17
				+ getFileSplitCnt() + columnSep // 18
				+ getFileCnt() + columnSep // 19
				+ getFileEncodingType() + columnSep // 20
				+ getFileEncryptType() + columnSep // 21
				+ getFileHash() + columnSep // 22
				+ getFilePostCommand() + columnSep // 23
				+ getRESERVED_1() + columnSep // 24
				+ headerBodySep;

		return headerData;
	}

	/**
	 * MQ 전송용 Data를 Header로 설정해주는 Method
	 * 
	 * @param interfaceConfig
	 */
	/*
	 * public void setHeaderDataForRAHeader(RAConfigBO interfaceConfig){
	 * setInterfaceId(interfaceConfig.getInterfaceID()); }
	 */

	/**
	 * MQ 전송용 Data를 Header로 설정해주는 Method
	 * 
	 * @param dataString
	 * @param columnSep
	 * @param headerBodySep
	 */
	// 15.05.13 column/headerBody 구분자 추가로 넘겨 받도록 변경
	public void setHeaderDataForRAHeader(String dataString, String columnSep, String headerBodySep) {

		// 15.05.13 사용자 정의 구분자 사용에 따른 변경(ex) AgentConst.HEADERBODYSEP -> headerBodySep)
		// 15.06.10 setSndSystemName + setRcvSystemName 추가

		String[] datatemp = dataString.split(headerBodySep);
		String[] data = datatemp[0].split(columnSep, 24);

		// String []data = dataString.split( AgentConst.HEADERSEP);
		// data = data[0].split(AgentConst.COLUMNSEP);

		/*  1        MessageId */ setMsgId(data[0]);
		/*  2      InterfaceId */ setInterfaceId(data[1]);
		/*  3   InterfaceSubid */ setInterfaceSubId(data[2]);
		/*  4         HopCount */ setHopCnt(data[3]);
		/*  5       TraceLevel */ setTraceLevel(data[4]);
		/*  6       MessageSeq */ setMsgSeq(data[5]);
		/*  7    SndSystemName */ setSndSystemName(data[6]);
		/*  8    RcvSystemName */ setRcvSystemName(data[7]);
		/*  9      RecordCount */ setRecordCnt(data[8]);
		/* 10     RecordLength */ setRecordLength(data[9]);
		/* 11      EndOfRecord */ setEndOfRecord(data[10]);
		/* 12         FileName */ setFileName(data[11]);
		/* 13        ProcessId */ setProcessId(data[12]);
		/* 14          Timeout */ setTimeoutInterval(data[13]);
		/* 15        ReplyFlag */ setReplyFlag(data[14]);
		/* 16     FileFullSize */ setFileFullSize(data[15]);
		/* 17  FileCurrentSize */ setFileCurrentSize(data[16]);
		/* 18     FileSplitCnt */ setFileSplitCnt(data[17]);
		/* 19          FileCnt */ setFileCnt(data[18]);
		/* 20 FileEncodingType */ setFileEncodingType(data[19]);
		/* 21      EncryptType */ setFileEncryptType(data[20]);
		/* 22         FileHash */ setFileHash(data[21]);
		/* 23  FilePostCommand */ setFilePostCommand(data[22]);
		/* 24       RESERVED_1 */ setRESERVED_1(data[23]);
	}

	/**
	 * MessageID 생성 메소드 - 생성규칙 인터페이스 ID + 송신일시(밀리세컨) 17자리 + 랜덤 값 4자리
	 * 
	 * @return MessageID
	 */
	public void createId(InterfaceBO ifConf) {
		String interfaceID = ifConf.getInterfaceId();
		this.setInterfaceId(interfaceID);
		this.setInterfaceSubId(interfaceID.substring(interfaceID.length()-2 , interfaceID.length()));
		this.setMsgId(ifConf.getMsgId());
	}
	
	/** Bring the value of 'Message ID' **/
	public String getMsgId() {
		if (msgId == null || msgId.length() == 0) {
			return "";
		}
		return this.msgId;
	}

	/** Setting the value of 'Message ID' **/
	public void setMsgId(String msgId) {
		this.msgId = msgId.trim();
	}

	/**
	 * Bring the value of 'Interface ID'
	 * 
	 * @return the interfaceID
	 */

	public String getInterfaceId() {
		if (interfaceId == null || interfaceId.length() == 0) {
			return "";
		}
		return interfaceId;
	}

	/**
	 * Setting the value of 'InterfaceID'
	 * 
	 * @param interfaceId
	 *            the interfaceID to set
	 */
	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId.trim();
	}

	/**
	 * Bring the value of 'InterfaceSubID'
	 * 
	 * @return the interfaceSubID
	 */
	public String getInterfaceSubId() {
		if (interfaceSubId == null || interfaceSubId.length() == 0) {
			return "";
		}
		return interfaceSubId;
	}

	/**
	 * Setting the value of 'InterfaceSubID'
	 * 
	 * @param interfaceSubID
	 *            the interfacSubID to set
	 */
	public void setInterfaceSubId(String interfaceSubId) {
		this.interfaceSubId = interfaceSubId.trim();
	}

	/**
	 * Bring the value of 'HopCnt'
	 * 
	 * @return the hopCnt
	 */
	public String getHopCnt() {
		if (hopCnt == null || hopCnt.length() == 0) {
			return "01";
		}
		return hopCnt;
	}

	/**
	 * Setting the value of 'HopCnt'
	 * 
	 * @param hopCnt
	 *            the hopCnt to set
	 */
	public void setHopCnt(String hopCnt) {
		this.hopCnt = hopCnt.trim();
	}

	/**
	 * Bring the value of 'TraceLevel'
	 * 
	 * @return the traceLevel
	 */
	public String getTraceLevel() {
		if (traceLevel == null || traceLevel.length() == 0) {
			return "00";
		}
		return traceLevel;
	}

	/**
	 * Setting the value of 'TraceLevel'
	 * 
	 * @param traceLevel
	 *            the traceLevel to set
	 */
	public void setTraceLevel(String traceLevel) {
		this.traceLevel = traceLevel.trim();
	}

	/**
	 * Bring the value of 'Message Sequence'
	 * 
	 * @return the messageSeq
	 */
	public String getMsgSeq() {
		if (msgSeq == null || msgSeq.length() == 0) {
			return "0";
		}
		return msgSeq;
	}

	/**
	 * Setting the value of 'Message Sequence'
	 * 
	 * @param msgSeq
	 *            the messageSeq to set
	 */
	public void setMsgSeq(String msgSeq) {
		this.msgSeq = msgSeq.trim();
	}

	/**
	 * Bring the value of 'SND System Name'
	 * 
	 * @return the SndSystemName
	 */
	public String getSndSystemName() {
		if (sndSystemName == null || sndSystemName.length() == 0) {
			return "";
		}
		return sndSystemName;
	}

	/**
	 * Setting the value of 'SND System Name'
	 * 
	 * @param sndSystemName
	 *            the sndSystemName to set
	 */
	public void setSndSystemName(String sndSystemName) {
		this.sndSystemName = sndSystemName.trim();
	}

	/**
	 * Bring the value of 'RCV System Name'
	 * 
	 * @return the rcvSystemName
	 */
	public String getRcvSystemName() {
		if (rcvSystemName == null || rcvSystemName.length() == 0) {
			return "";
		}
		return rcvSystemName;
	}

	/**
	 * Setting the value of 'RCV System Name'
	 * 
	 * @param rcvSystemName
	 *            the rcvSystemName to set
	 */
	public void setRcvSystemName(String rcvSystemName) {
		this.rcvSystemName = rcvSystemName.trim();
	}

	/**
	 * Bring the value of 'Record Count'
	 * 
	 * @return the recordCnt
	 */
	public String getRecordCnt() {
		if (recordCnt == null || recordCnt.length() == 0) {
			return "0";
		}
		return recordCnt;
	}

	/**
	 * Setting the value of 'Record Count'
	 * 
	 * @param recordCnt
	 *            the recordCnt to set
	 */
	public void setRecordCnt(String recordCnt) {
		this.recordCnt = recordCnt.trim();
	}

	/**
	 * Bring the value of 'Record Length'
	 * 
	 * @return the recordLength
	 */
	public String getRecordLength() {
		if (recordLength == null || recordLength.length() == 0) {
			return "0";
		}
		return recordLength;
	}

	/**
	 * Setting the value of 'Record Length'
	 * 
	 * @param recordLength
	 *            the recordLength to set
	 */
	public void setRecordLength(String recordLength) {
		this.recordLength = recordLength.trim();
	}

	/**
	 * Bring the value of 'EndOfRecord'
	 * 
	 * @return the endOfRecord
	 */
	public String getEndOfRecord() {
		if (endOfRecord == null || endOfRecord.length() == 0) {
			return "";
		}
		return endOfRecord;
	}

	/**
	 * Setting the value of 'EndOfRecord'
	 * 
	 * @param endOfRecord
	 *            the endOfRecord to set
	 */
	public void setEndOfRecord(String endOfRecord) {
		this.endOfRecord = endOfRecord.trim();
	}

	/**
	 * Bring the value of 'FileName'
	 * 
	 * @return the fileName
	 */
	public String getFileName() {
		if (fileName == null || fileName.length() == 0) {
			return "";
		}
		return fileName;
	}

	/**
	 * Setting the value of 'FileName'
	 * 
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName.trim();
	}

	/**
	 * Bring the value of 'ProcessID'
	 * 
	 * @return the processID
	 */
	public String getProcessId() {
		if (processId == null || processId.length() == 0) {
			return "";
		}
		return processId;
	}

	/**
	 * Setting the value of 'ProcessID'
	 * 
	 * @param processID
	 *            the processID to set
	 */
	public void setProcessId(String processId) {
		this.processId = processId.trim();
	}

	/**
	 * Bring the value of 'TimeoutInterval'
	 * 
	 * @return the timeoutInterval
	 */
	public String getTimeoutInterval() {
		if (timeoutInterval == null || timeoutInterval.length() == 0) {
			return "0";
		}
		return timeoutInterval;
	}

	/**
	 * Setting the value of 'TimeoutInterval'
	 * 
	 * @param dueTime
	 *            the timeoutInterval to set
	 */
	public void setTimeoutInterval(String timeoutInterval) {
		this.timeoutInterval = timeoutInterval;
	}

	/**
	 * Bring the value of 'ReplyFlag'
	 * 
	 * @return the replyFlag
	 */
	public String getReplyFlag() {
		if (replyFlag == null || replyFlag.length() == 0) {
			return "N";
		}
		return this.replyFlag;
	}

	/**
	 * Setting the value of 'ReplyFlag'
	 * 
	 * @param replyFlag
	 *            the dueTime to set
	 */
	public void setReplyFlag(String replyFlag) {
		this.replyFlag = replyFlag.trim();
	}

	/**
	 * @return the fileFullSize
	 */
	public String getFileFullSize() {
		if (fileFullSize == null || fileFullSize.length() == 0) {
			return "";
		}
		return fileFullSize;
	}

	/**
	 * @param fileFullSize
	 *            the fileFullSize to set
	 */
	public void setFileFullSize(String fileFullSize) {
		this.fileFullSize = fileFullSize;
	}

	/**
	 * @return the fileCurrentSize
	 */
	public String getFileCurrentSize() {
		if (fileCurrentSize == null || fileCurrentSize.length() == 0) {
			return "";
		}
		return fileCurrentSize;
	}

	/**
	 * @param fileCurrentSize
	 *            the fileCurrentSize to set
	 */
	public void setFileCurrentSize(String fileCurrentSize) {
		this.fileCurrentSize = fileCurrentSize;
	}

	/**
	 * @return the fileSplitCnt
	 */
	public String getFileSplitCnt() {
		if (fileSplitCnt == null || fileSplitCnt.length() == 0) {
			return "";
		}
		return fileSplitCnt;
	}

	/**
	 * @param fileSplitCnt
	 *            the fileSplitCnt to set
	 */
	public void setFileSplitCnt(String fileSplitCnt) {
		this.fileSplitCnt = fileSplitCnt;
	}

	/**
	 * @return the fileCnt
	 */
	public String getFileCnt() {
		if (fileCnt == null || fileCnt.length() == 0) {
			return "";
		}
		return fileCnt;
	}

	/**
	 * @param fileCnt
	 *            the fileCnt to set
	 */
	public void setFileCnt(String fileCnt) {
		this.fileCnt = fileCnt;
	}

	/**
	 * @return the fileEncodingType
	 */
	public String getFileEncodingType() {
		return fileEncodingType;
	}

	/**
	 * @param fileEncodingType
	 *            the fileEncodingType to set
	 */
	public void setFileEncodingType(String fileEncodingType) {
		this.fileEncodingType = fileEncodingType;
	}

	/**
	 * @return the fileEncryptType
	 */
	public String getFileEncryptType() {
		return fileEncryptType;
	}

	/**
	 * @param fileEncryptType
	 *            the fileEncryptType to set
	 */
	public void setFileEncryptType(String fileEncryptType) {
		this.fileEncryptType = fileEncryptType;
	}

	/**
	 * @return the fileHash
	 */
	public String getFileHash() {
		if (fileHash == null || fileHash.length() == 0) {
			return "";
		}
		return fileHash;
	}

	/**
	 * @param fileHash
	 *            the fileHash to set
	 */
	public void setFileHash(String fileHash) {
		this.fileHash = fileHash;
	}

	/**
	 * Bring the value of 'FilePostCommand'
	 * 
	 * @return the filePostCommand
	 */
	public String getFilePostCommand() {
		if (filePostCommand == null || filePostCommand.length() == 0) {
			return "";
		}
		return filePostCommand;
	}

	/**
	 * Setting the value of 'FilePostCommand'
	 * 
	 * @param filePostCommand
	 *            the filePostCommand to set
	 */
	public void setFilePostCommand(String filePostCommand) {
		this.filePostCommand = filePostCommand.trim();
	}

	/**
	 * @return the RESERVED_1
	 */
	public String getRESERVED_1() {
		return RESERVED_1;
	}

	/**
	 * @param RESERVED_1
	 *            the RESERVED_1 to set
	 */
	public void setRESERVED_1(String rESERVED_1) {
		RESERVED_1 = rESERVED_1;
	}
}
