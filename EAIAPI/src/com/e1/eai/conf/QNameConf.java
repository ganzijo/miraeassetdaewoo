package com.e1.eai.conf;

/**
 * MQ QueueNamePattern 정보<br>
 * &lt;PRE&gt;&lt;Post&gt; &lt;SND&gt;SND&lt;/SND&gt; &lt;!-- Async Send --&gt;
 * &lt;RCV&gt;RCV&lt;/RCV&gt; &lt;!-- Async Receive --&gt;
 * &lt;RQS&gt;REQ&lt;/RQS&gt; &lt;!-- Sync Request Send(put) --&gt;
 * &lt;RQR&gt;REQ&lt;/RQR&gt; &lt;!-- Sync Request Receive(get) --&gt;
 * &lt;RPS&gt;RPL&lt;/RPS&gt; &lt;!-- Sync Reply Send(put) --&gt;
 * &lt;RPR&gt;RPL&lt;/RPR&gt; &lt;!-- Sync Reply Receive(get) --&gt;
 * &lt;ERR&gt;ERR&lt;/ERR&gt; &lt;!-- Error --&gt; &lt;/PRE&gt;&lt;/POST&gt;
 **/
public class QNameConf {

	private String preSndQPT;
	private String preRcvQPT;
	private String preRqsQPT;
	private String preRqrQPT;
	private String preRpsQPT;
	private String preRprQPT;
	private String preErrQPT;

	private String postSndQPT;
	private String postRcvQPT;
	private String postRqsQPT;
	private String postRqrQPT;
	private String postRpsQPT;
	private String postRprQPT;
	private String postErrQPT;

	public QNameConf() {
	}

	// Set Start
	/** 1. Set Snd PreQName Pattern **/
	public void setPreSndQPT(String preSndQPT) {
		this.preSndQPT = preSndQPT;
	}

	/** 2. Set Snd PreQName Pattern **/
	public void setPreRcvQPT(String preRcvQPT) {
		this.preRcvQPT = preRcvQPT;
	}

	/** 3. Set Request PreQName Pattern **/
	public void setPreRqsQPT(String preRqsQPT) {
		this.preRqsQPT = preRqsQPT;
	}

	/** 4. Set Reply PreQName Pattern **/
	public void setPreRqrQPT(String preRqrQPT) {
		this.preRqrQPT = preRqrQPT;
	}

	/** 5. Set Request PreQName Pattern **/
	public void setPreRpsQPT(String preRpsQPT) {
		this.preRpsQPT = preRpsQPT;
	}

	/** 6. Set Reply PreQName Pattern **/
	public void setPreRprQPT(String preRprQPT) {
		this.preRprQPT = preRprQPT;
	}

	/** 7. Set Err PreQName Pattern **/
	public void setPreErrQPT(String preErrQPT) {
		this.preErrQPT = preErrQPT;
	}
	
	/** 1. Set Snd PostQName Pattern **/
	public void setPostSndQPT(String postSndQPT) {
		this.postSndQPT = postSndQPT;
	}

	/** 2. Set Snd PostQName Pattern **/
	public void setPostRcvQPT(String postRcvQPT) {
		this.postRcvQPT = postRcvQPT;
	}

	/** 3. Set Request PostQName Pattern **/
	public void setPostRqsQPT(String postRqsQPT) {
		this.postRqsQPT = postRqsQPT;
	}

	/** 4. Set Reply PostQName Pattern **/
	public void setPostRqrQPT(String postRqrQPT) {
		this.postRqrQPT = postRqrQPT;
	}

	/** 5. Set Request PostQName Pattern **/
	public void setPostRpsQPT(String postRpsQPT) {
		this.postRpsQPT = postRpsQPT;
	}

	/** 6. Set Reply PostQName Pattern **/
	public void setPostRprQPT(String postRprQPT) {
		this.postRprQPT = postRprQPT;
	}

	/** 7. Set Err PostQName Pattern **/
	public void setPostErrQPT(String postErrQPT) {
		this.postErrQPT = postErrQPT;
	}
	// Set End

	// Get Start
	/** 1. Get SND PreQName Pattern **/
	public String getPreSndQPT() {
		return this.preSndQPT;
	}

	/** 2. Get RCV PreQName Pattern **/
	public String getPreRcvQPT() {
		return this.preRcvQPT;
	}

	/** 3. Get Request SND PreQName Pattern **/
	public String getPreRqsQPT() {
		return this.preRqsQPT;
	}

	/** 4. Get Request RCV PreQName Pattern **/
	public String getPreRqrQPT() {
		return this.preRqrQPT;
	}

	/** 5. Get Reply SND PreQName Pattern **/
	public String getPreRpsQPT() {
		return this.preRpsQPT;
	}

	/** 6. Get Reply RCV PreQName Pattern **/
	public String getPreRprQPT() {
		return this.preRprQPT;
	}

	/** 7. Get ERR PreQName Pattern **/
	public String getPreErrQPT() {
		return this.preErrQPT;
	}

	/** 1. Get SND PostQName Pattern **/
	public String getPostSndQPT() {
		return this.postSndQPT;
	}

	/** 2. Get RCV PostQName Pattern **/
	public String getPostRcvQPT() {
		return this.postRcvQPT;
	}

	/** 3. Get Request SND PostQName Pattern **/
	public String getPostRqsQPT() {
		return this.postRqsQPT;
	}

	/** 4. Get Request RCV PostQName Pattern **/
	public String getPostRqrQPT() {
		return this.postRqrQPT;
	}

	/** 5. Get Reply SND PostQName Pattern **/
	public String getPostRpsQPT() {
		return this.postRpsQPT;
	}

	/** 6. Get Reply RCV PostQName Pattern **/
	public String getPostRprQPT() {
		return this.postRprQPT;
	}

	/** 7. Get ERR PostQName Pattern **/
	public String getPostErrQPT() {
		return this.postErrQPT;
	}
	// Get End
}
