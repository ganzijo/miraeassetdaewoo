package com.e1.eai.conf;

import com.e1.common.util.SysProperty;
import com.e1.eai.excp.APIException;

public class APIConf {
	// API Object
	private MQConf mqConf       = null;
	private LogConf logConf     = null;
	private QNameConf qNameConf = null;
	
	/** Qmgr Host */
	private String host;
	
	/** Qmgr Port */
	private String port;
	
	/** Qmgr CCSID */
	private String ccsid;
	
	/** Qmgr NAME */
	private String qmgr;
	
	/** Qmgr Channel */
	private String channel;
	
	/** PoolYN  **/
	private String poolYN;
	
	/** AP_ProcessId **/
	private long pId;

	public APIConf() throws APIException{
		this.mqConf    = new MQConf();		
		this.logConf   = new LogConf();
		this.qNameConf = new QNameConf();
		
		// Used QmgrInfo (Base or Back)
		this.host     = "";
    	this.port     = "";
    	this.ccsid    = "";
    	this.qmgr     = "";
    	this.channel  = "";

    	this.poolYN   = "";
    	
    	this.pId      = SysProperty.getPID();
    	
    	if(mqConf == null || logConf == null || qNameConf == null) {
    		throw new APIException("API", "0005","APIConfig initialize Fail.");
    	}
	}
	
	public MQConf getMQConf() {
		return this.mqConf;
	}
	
	public LogConf getLogConf() {
		return this.logConf;
	}
	
	public QNameConf getQNameConf() {
		return this.qNameConf;
	}
	
	// Set MQConf Start  ////////////////////////////////////////////////////////
	/** Set BaseMQ **/
	public void setBaseMQ() {
		this.host    = mqConf.getBaseHost();
		this.port    = mqConf.getBasePort();
		this.ccsid   = mqConf.getBaseCcsid();
		this.qmgr    = mqConf.getBaseQmgr();
		this.channel = mqConf.getBaseChl();
		
		//System.out.println("set Qmgr(Base) complete.");
	}
	
	/** Set BaseMQ **/
	public void setBackMQ() {
		this.host    = mqConf.getBackHost();
		this.port    = mqConf.getBackPort();
		this.ccsid   = mqConf.getBackCcsid();
		this.qmgr    = mqConf.getBackQmgr();
		this.channel = mqConf.getBackChl();
		
		//System.out.println("set Qmgr(Back) complete.");
	}

	/** Set poolYN **/
	public void setPoolYN(String poolYN) {
		this.poolYN = poolYN;
	}
	// Set MQConf End    ////////////////////////////////////////////////////////
	
	// Get MQConf Start  ////////////////////////////////////////////////////////
	/** 1. Get Host 
	 * @return Host**/
	public String getHost() {
		return this.host;
	}
	
	/** 2. Get Port 
	 * @return Port **/
	public String getPort() {
		return this.port;
	}
	
	/** 3. Get Ccsid 
	 * @return Ccsid **/
	public String getCcsid() {
		return this.ccsid;
	}
	
	/** 4. Get Qmgr 
	 * @return Qmgr **/
	public String getQmgr() {
		return this.qmgr;
	}
	
	/** 5. Get Channel 
	 * @return Channel**/
	public String getChannel() {
		return this.channel;
	}

	/** poolYN 값을 반환한다.
	* @return ReplyFlag **/
	public String getPoolYN() { 
		return mqConf.getPoolYN(); 
	}
	// Get MQConf End    ////////////////////////////////////////////////////////

	// Set LogConf Start ////////////////////////////////////////////////////////
	/** 1. Set LogPath **/
	public void setLogPath(String logPath) {
		logConf.setLogPath(logPath);
	}
	
	/** 2. Set LogLevel **/
	public void setLogLevel(String logLevel) {
		logConf.setLogLevel(logLevel);
	}
	
	/** 3. Set LogType **/
	public void setLogType(String logType) {
		logConf.setLogType(logType);
	}
	
	/** 4. Set TrackLevel **/
	public void setTraceLevel(String traceLevel) {
		logConf.setTraceLevel(traceLevel);
	}
	
	/** 5. Set TraceQueue **/
	public void setTraceQueue(String traceQueue) {
		logConf.setTraceQueue(traceQueue);
	}
	// Set LogConf End   ////////////////////////////////////////////////////////
	
	// Get LogConf Start ////////////////////////////////////////////////////////
	/** 1. Get LogPath 
	 * @return logPath**/
	public String getLogPath() {
		return logConf.getLogPath();
	}
	
	/** 2. Get LogLevel 
	 * @return logLevel**/
	public String getLogLevel() {
		return logConf.getLogLevel();
	}
	
	/** 3. Get LogType 
	 * @return logType**/
	public String getLogType() {
		return logConf.getLogType();
	}
	
	/** 4. Get TrackLevel
	 * @return  trackLevel **/
	public String getTraceLevel() {
		return logConf.getTraceLevel();
	}
	
	/** 5. Get TraceQueue 
	 * @return traceQueue **/
	public String getTraceQueue() {
		return logConf.getTraceQueue();
	}
	// Get LogConf End   ////////////////////////////////////////////////////////

	public long getPId() {
		return this.pId;
	}
}
