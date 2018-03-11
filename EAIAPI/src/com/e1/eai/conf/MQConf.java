package com.e1.eai.conf;

/** MQ Connection 정보<br>
&lt;MQ_CON_INFO&gt;<br>
&lt;BASE&gt;<br>
	&lt;Host&gt;192.168.1.189&lt;/Host&gt;<br>
	&lt;Port&gt;1414&lt;/Port&gt;<br>
	&lt;CCSID&gt;1208&lt;/CCSID&gt;<br>
	&lt;QmgrName&gt;TESTMQ&lt;/QmgrName&gt;<br>
&lt;/BASE&gt;<br>
&lt;/MQ_CON_INFO&gt;<br>
**/
public class MQConf {
	/* MQ Connection 정보
	<MQ_CON_INFO>
	<BASE> <!-- Active Qmgr -->
		<Host>192.168.1.189</Host>
		<Port>1414</Port>
		<CCSID>1208</CCSID>
		<QmgrName>TESTMQ</QmgrName>
	</BASE>
	<BACK> <!-- Standby Qmgr -->
		<Host>192.168.1.189</Host>
		<Port>1417</Port>
		<CCSID>1208</CCSID>
		<QmgrName>RMSQmgr</QmgrName>
	</BACK>
	</MQ_CON_INFO>
	*/
	
    // Base Qmgr Info //////////////////////////////
	/** Base Qmgr Host */
	private String baseHost;
	
	/** Base Qmgr Port */
	private String basePort;
	
	/** Base Qmgr CCSID */
	private String baseCcsid;
	
	/** Base Qmgr NAME */
	private String baseQmgr;
	
	/** Base Qmgr Channel */
	private String baseChl;
	
	// Back Qmgr Info //////////////////////////////
	/** Back Qmgr Host */
	private String backHost;
	
	/** Back Qmgr Port */
	private String backPort;
	
	/** Back Qmgr CCSID */
	private String backCcsid;
	
	/** Back Qmgr Name */
	private String backQmgr;
	
	/** Back Qmgr Channel */
	private String backChl;
	
	/** PoolYN  **/
	private String poolYN;
	
    public MQConf(){
    	// BaseQmgr
    	this.baseHost     = "";
    	this.basePort     = "";
    	this.baseCcsid    = "";
    	this.baseQmgr     = "";
    	this.baseChl      = "";
    	
    	// BackQmgr
    	this.backHost     = "";
    	this.backPort     = "";
    	this.backCcsid    = "";
    	this.backQmgr     = "";
    	this.backChl      = "";
    	
    	// connPool
    	this.poolYN       = "";
    }
	
	// Set Start ////////////////////////////////////////////////////////////////////
	/** 1_1. Set BaseHost **/
	public void setBaseHost(String baseHost) {
		this.baseHost = baseHost;
	}
	
	/** 1_2. Set BasePort **/
	public void setBasePort(String basePort) {
		this.basePort = basePort;
	}
	
	/** 1_3. Set BaseCcsid **/
	public void setBaseCcsid(String baseCcsid) {
		this.baseCcsid = baseCcsid;
	}
	
	/** 1_4. Set BaseQmgr **/
	public void setBaseQmgr(String baseQmgr) {
		this.baseQmgr = baseQmgr;
	}
	
	/** 1_5. Set BaseQmgr **/
	public void setBaseChl(String baseChl) {
		this.baseChl = baseChl;
	}
	
	/** 2-1. Set BackHost **/
	public void setBackHost(String backHost) {
		this.backHost = backHost;
	}
	
	/** 2-2. Set BackPort **/
	public void setBackPort(String backPort) {
		this.backPort = backPort;
	}
	
	/** 2-3. Set BackCcsid **/
	public void setBackCcsid(String backCcsid) {
		this.backCcsid = backCcsid;
	}
	
	/** 2-4. Set BackQmgr **/
	public void setBackQmgr(String backQmgr) {
		this.backQmgr = backQmgr;
	}
	
	/** 2_5. Set BackChl **/
	public void setBackChl(String backChl) {
		this.backChl = backChl;
	}
	
	/** Set poolYN **/
	public void setPoolYN(String poolYN) {
		this.poolYN = poolYN;
	}
	// Set End ////////////////////////////////////////////////////////////////////
	
	// Get Start ////////////////////////////////////////////////////////////////////
	/** 1-1. Get BaseHost 
	 * @return baseHost**/
	public String getBaseHost() {
		return this.baseHost;
	}
	
	/** 1-2. Get BasePort 
	 * @return basePort**/
	public String getBasePort() {
		return this.basePort;
	}
	
	/** 1-3. Get BaseCcsid 
	 * @return baseCcsid**/
	public String getBaseCcsid() {
		return this.baseCcsid;
	}
	
	/** 1-4. Get BaseQmgr 
	 * @return baseQmgr**/
	public String getBaseQmgr() {
		return this.baseQmgr;
	}
	
	/** 1-5. Get BaseChl 
	 * @return baseChl**/
	public String getBaseChl() {
		return this.baseChl;
	}

	/** 2-1. Get BackHost 
	 * @return backHost**/
	public String getBackHost() {
		return this.backHost;
	}
	
	/** 2-2. Get BackPort 
	 * @return backPort**/
	public String getBackPort() {
		return this.backPort;
	}
	
	/** 2-3. Get BackCcsid 
	 * @return backCcsid**/
	public String getBackCcsid() {
		return this.backCcsid;
	}
	
	/** 2-4. Get BackQmgr 
	 * @return backQmgr **/
	public String getBackQmgr() {
		return this.backQmgr;
	}
	
	/** 2-5. Get BackChl 
	 * @return baseChl**/
	public String getBackChl() {
		return this.backChl;
	}
	
	/** Get PoolYN 
	 * @return PoolYN**/
	public String getPoolYN() {
		//return this.backQmgr;
		return "N";
	}
}