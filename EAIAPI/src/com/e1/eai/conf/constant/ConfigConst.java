/**
 * 0. Project	: KT Rainbow Adapter 프로젝트
 * 1. FileName	: MQConfigConst.java
 * 2. Package	: com.enterprise1.config.util
 * 3. Comment	: MQ Config 설정파일의 상수
 * 4. 작성자		: 김한선
 * 5. 작성일		: 2015. 3. 2. 오전 10:44:41
 * 6. 변경이력		:
 *	  이름						:일자			:	변경내용
 *	------------------------------------------------------
 *	hsnkim@enterprise1.co.kr	:2015. 3. 2.	:	신규 개발
 *
 */

package com.e1.eai.conf.constant;

/**
 * API Config 설정파일의 상수 선언
 *
 */
public class ConfigConst{
	// Conf File Path
	public final static String API_CFG_PATH = "/cfg/";
	public final static String API_CFG_NAME = "apiConfig.xml";
	
	public final static String DEF_LOG_PATH = "/logs/";
	
	// XML Path constant ---------------------------------------------
	/** APIConfig Text() **/
	public final static String NODT_TEXT = "/text()";
	/** APIConfig 노드 **/
	public final static String NODE_APICONF = "//API_Config";
	
	/** APIConfig SUB노드 **/
	public final static String NODE_MQCONN = NODE_APICONF + "/MQ_CON_INFO";
	public final static String NODE_LOGGER = NODE_APICONF + "/Logger";
	public final static String NODE_QNP    = NODE_APICONF + "/QueueNamePattern";
 
	/** MQ_CON_INFO 노드 **/
	public final static String NODE_BASE = NODE_MQCONN + "/BASE";
	public final static String NODE_BACK = NODE_MQCONN + "/BACK";
	
	/** QueueNamePattern 노드 **/
	public final static String NODE_PRE = NODE_QNP + "/PRE";
	public final static String NODE_POST = NODE_QNP + "/POST";
	
	/** MQ_CON_INFO 상수 **/
	// ex) API_Config/MQ_CON_INFO/BACK/Host/text()
	public final static String HOST     = "/Host";
	public final static String PORT     = "/Port";
	public final static String CCSID    = "/CCSID";
	public final static String QMGRNAME = "/QMGRName";
	public final static String CHANNEL  = "/Channel";
	
	/** Logger 상수 **/
	public final static String LOGPATH    = "/LogPath";
	public final static String LOGLEVEL   = "/LogLevel";
	public final static String LOGTYPE    = "/LogType";
	public final static String TRACELEVEL = "/TraceLevel";
	public final static String TRACEQUEUE = "/TraceQueue";
	
	/** QueueNamePattern 상수 **/
	public final static String SND   = "/SND";
	public final static String RCV   = "/RCV";
	public final static String RQS   = "/RQS";
	public final static String RQR   = "/RQR";
	public final static String RPS   = "/RPS";
	public final static String RPR   = "/RPR";
	public final static String ERR   = "/ERR";

	// default constant ---------------------------------------------
	public final static    int RECORD_CNT = 1;
	
	/** MQ Connection 오류 재처리 Count **/
	public final static    int MQ_RETRY_CNT = 1;
	
	/** 성공코드 : 0000 **/
	public final static String CD_SUCCESS = "API0000";
	
	public final static String CD_Y = "Y";
	public final static String CD_N = "N";
	
	public final static    int MAX_BODY_LEN = 999;
	
	public final static String CHARSET_UTF8 = "UTF-8";
	public final static String DEF_CHARSET  = CHARSET_UTF8;
	public final static String DEF_LOGFILE_SIZE = "5MB";
	public final static    int DEF_LOGFILE_INDEX = 5;

	public final static String SEP_Q = ".";
	
	// RA & RMS constant---------------------------------------------
	/** RA COLUMN Delimiter **/
	public final static String SEP_RA_COLUMN = "@2C!";
	public final static String SEP_RA_ROW    = "@1R!";
	public final static String SEP_RA_HEADERBODY = "@0H!";
	
	public final static String SPLIT_RA_COLUMN = "@2C!";
	public final static String SPLIT_RA_ROW    = "@1R!";
	public final static String SPLIT_RA_HEADERBODY = "@0H!";
	
	/** RMS headerBody Delimiter **/
	public final static String SEP_RMS = ":^|";
	
	/** RMS Apdater Type **/
	public final static String ADT_TYPE = "01";
	
	/** RMS RTN_CD (S: 0, F: 2) **/
	public final static String RMS_SUCCESS = "0";
	public final static String RMS_FAIL = "2";
	
	/** **/
	public final static String ECD_TEXT   = "0";
	public final static String ECD_BINARY = "1";
	
	/** **/
	public final static String ECT_NO = "0";
	public final static String ECT_AEX128 = "1";
	
	/** Hop Count **/
	public final static String HOPCNT_01 = "01";
	public final static String HOPCNT_02 = "02";
	public final static String HOPCNT_03 = "03";
	public final static String HOPCNT_04 = "04";
	
	private ConfigConst() {
		
	}
}