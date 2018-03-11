package com.e1.eai.conf;

/** apiConfig 의 Log 정보<br>
&lt;Logger&gt;<br>
	&lt;logPath&gt;&lt;/logPath&gt; &lt;!-- 절대경로 기준 --&gt;<br>
	&lt;logLevel>info&lt;/logLevel&gt; &lt;!-- File 로그레벨 (info/debug) --&gt;<br>
	&lt;trackLevel>01&lt;/trackLevel&gt; &lt;!-- 트래킹 로그레벨 --&gt;<br>
	&lt;traceQueue>TRK.RQ&lt;/traceQueue&gt; <br>
&lt;/Logger&gt;
 **/
public class LogConf {
	
	/* apiConfig 의 Log 정보<br>
	<Logger>
		<logPath></logPath> <!-- 절대경로 기준 -->
		<logLevel>info</logLevel> <!-- File 로그레벨 (info/debug) -->
		<trackLevel>01</trackLevel> <!-- 트래킹 로그레벨 -->
		<traceQueue>TRK.RQ</traceQueue>
	</Logger>
	 */
	
	/** FILE Log 경로 **/
	private String logPath;
	
	/** FILE Log 레벨 **/
	private String logLevel;
	
	private String logType;
	
	/** 트래킹 Log 레벨 **/
	private String traceLevel;
	
	/** 트래킹 Log 전송큐 **/
	private String traceQueue;
	
    public LogConf() {
    	this.logPath = "";
    	this.logLevel = "";
    	this.logType = "";
    	this.traceLevel = "";
    	this.traceQueue = "";
    }
	
    
    // Set Start ////////////////////////////////////////////////////////////////////
    /** 1. Set LogPath **/
	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}
	
	/** 2. Set LogLevel **/
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	
	/** 3. Set LogType **/
	public void setLogType(String logType) {
		this.logType = logType;
	}
	
	/** 4. Set TrackLevel **/
	public void setTraceLevel(String traceLevel) {
		this.traceLevel = traceLevel;
	}
	
	/** 5. Set TraceQueue **/
	public void setTraceQueue(String traceQueue) {
		this.traceQueue = traceQueue;
	}
	// Set End ////////////////////////////////////////////////////////////////////
	
	// Get Start ////////////////////////////////////////////////////////////////////
	/** 1. Get LogPath 
	 * @return logPath**/
	public String getLogPath() {
		return this.logPath;
	}
	
	/** 2. Get LogLevel 
	 * @return logLevel**/
	public String getLogLevel() {
		return this.logLevel;
	}
	
	/** 3. Get LogType 
	 * @return logType**/
	public String getLogType() {
		return this.logType;
	}
	
	/** 4. Get TrackLevel
	 * @return  trackLevel **/
	public String getTraceLevel() {
		return this.traceLevel;
	}
	
	/** 5. Get TraceQueue 
	 * @return traceQueue **/
	public String getTraceQueue() {
		return this.traceQueue;
	}
	// Get End ////////////////////////////////////////////////////////////////////
}
