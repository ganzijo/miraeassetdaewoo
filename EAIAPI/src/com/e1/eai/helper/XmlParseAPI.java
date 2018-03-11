package com.e1.eai.helper;

import java.io.File;
import java.io.IOException;

import com.e1.common.conf.Config;
import com.e1.eai.conf.APIConf;
import com.e1.eai.conf.constant.ConfigConst;
import com.e1.common.conf.XMLConfigurator;
import com.e1.common.util.SystemEnv;
import com.e1.eai.excp.APIException;
import com.e1.eai.log.APILogConst;

public class XmlParseAPI {

	private static File file;
	private static String fileName = "";
	private static long lastModified;
	private static XmlParseAPI confMgr;

	/** default apiConfigPath **/
	private String apiConfigPath = "";
	private String apiConfigName = "";

	private Config config;
	private static APIConf apiConf;

	private XmlParseAPI() throws APIException {
		this.init();
	}

	private void init() throws APIException {
		try {
			apiConf = new APIConf();
			// EAI_HOME 환경변수 get
			SystemEnv env = new SystemEnv();

			this.apiConfigPath = env.getEnv("EAI_HOME") + "/";
			this.apiConfigName = ConfigConst.API_CFG_NAME;

			fileName = this.apiConfigPath + this.apiConfigName;
			file = new File(fileName);

			config = new XMLConfigurator(file.getAbsolutePath());

			// apiConfig.XML Loading
			setAPIConf(config);
			
			// apiConfig 필수값 체크
			vaildChecker(apiConf);

		} catch (IOException ie) {
			throw new APIException("API", "0001", "Load Config(" + fileName + ") Failed. check environment variables", ie);
		} catch (APIException ae) {
			throw ae;
		} catch (Exception e) {
			throw new APIException("API", "0001", "Load Config(" + fileName + ") Failed.", e);
		}

		XmlParseAPI.lastModified = XmlParseAPI.file.lastModified();
	}

	public static APIConf getInstance() throws APIException {
		// new confMgr
		if (confMgr == null) {
			synchronized (XmlParseAPI.class) {
				// perform double-check to avoid race condition
				if (confMgr == null) {
					confMgr = new XmlParseAPI();
					//System.out.println(".............apiConfig.xml First Load !!");
				}
			}
		} else {
			// apiConfig.xml 파일의 변경이 있다면 reload.
			if (lastModified != file.lastModified()) {
				//System.out.println(".............changed apiConfig.xml !!");
				confMgr.reInitialize();
			} else {
				//System.out.println(".............Not changed apiConfig.xml !!");
			}
		}
		return apiConf;
	}

	public void setAPIConf(Config config) throws APIException{
		String value = null;

		// MQ_CON_INFO Set Start -----------------------------------------------------
		// BASE
		value = config.getString(ConfigConst.NODE_BASE + ConfigConst.HOST + ConfigConst.NODT_TEXT);
		if (value != null) {
			apiConf.getMQConf().setBaseHost(value.trim());
			value = null;
		}
		value = config.getString(ConfigConst.NODE_BASE + ConfigConst.PORT + ConfigConst.NODT_TEXT);
		if (value != null) {
			apiConf.getMQConf().setBasePort(value.trim());
			value = null;
		}
		value = config.getString(ConfigConst.NODE_BASE + ConfigConst.CCSID + ConfigConst.NODT_TEXT);
		if (value != null) {
			apiConf.getMQConf().setBaseCcsid(value.trim());
			value = null;
		}
		value = config.getString(ConfigConst.NODE_BASE + ConfigConst.QMGRNAME + ConfigConst.NODT_TEXT);
		if (value != null) {
			apiConf.getMQConf().setBaseQmgr(value.trim());
			value = null;
		}
		value = config.getString(ConfigConst.NODE_BASE + ConfigConst.CHANNEL + ConfigConst.NODT_TEXT);
		if (value != null) {
			apiConf.getMQConf().setBaseChl(value.trim());
			value = null;
		}

		// BACK
		value = config.getString(ConfigConst.NODE_BACK + ConfigConst.HOST + ConfigConst.NODT_TEXT);
		if (value != null) {
			apiConf.getMQConf().setBackHost(value.trim());
			value = null;
		}
		value = config.getString(ConfigConst.NODE_BACK + ConfigConst.PORT + ConfigConst.NODT_TEXT);
		if (value != null) {
			apiConf.getMQConf().setBackPort(value.trim());
			value = null;
		}
		value = config.getString(ConfigConst.NODE_BACK + ConfigConst.CCSID + ConfigConst.NODT_TEXT);
		if (value != null) {
			apiConf.getMQConf().setBackCcsid(value.trim());
			value = null;
		}
		value = config.getString(ConfigConst.NODE_BACK + ConfigConst.QMGRNAME + ConfigConst.NODT_TEXT);
		if (value != null) {
			apiConf.getMQConf().setBackQmgr(value.trim());
			value = null;
		}
		value = config.getString(ConfigConst.NODE_BACK + ConfigConst.CHANNEL + ConfigConst.NODT_TEXT);
		if (value != null) {
			apiConf.getMQConf().setBackChl(value.trim());
			value = null;
		}
		// MQ_CON_INFO Set End -----------------------------------------------------

		// LOGGER Set Start -----------------------------------------------------
		value = config.getString(ConfigConst.NODE_LOGGER + ConfigConst.LOGPATH + ConfigConst.NODT_TEXT);
		if (value != null) {
			apiConf.setLogPath(
					value.trim().length() == 0 && "".equals(value.trim()) ? APILogConst.DEF_LOG_PAHT : value.trim());
			value = null;
		}
		value = config.getString(ConfigConst.NODE_LOGGER + ConfigConst.LOGLEVEL + ConfigConst.NODT_TEXT);
		if (value != null) {
			apiConf.setLogLevel(value.trim().length() == 0 && "".equals(value.trim()) ? APILogConst.DEF_LOG_LEVEL
					: value.toUpperCase().trim());
			value = null;
		}
		value = config.getString(ConfigConst.NODE_LOGGER + ConfigConst.LOGTYPE + ConfigConst.NODT_TEXT);
		if (value != null) {
			apiConf.setLogType(
					value.trim().length() == 0 && "".equals(value.trim()) ? APILogConst.DEF_LOG_TYPE : value.trim());
			value = null;
		}
		value = config.getString(ConfigConst.NODE_LOGGER + ConfigConst.TRACELEVEL + ConfigConst.NODT_TEXT);
		if (value != null) {
			apiConf.setTraceLevel(
					value.trim().length() == 0 && "".equals(value.trim()) ? APILogConst.DEF_TRACELEVEL : value.trim());
			value = null;
		}
		value = config.getString(ConfigConst.NODE_LOGGER + ConfigConst.TRACEQUEUE + ConfigConst.NODT_TEXT);
		if (value != null) {
			apiConf.setTraceQueue(value.trim());
			value = null;
		}
		// LOGGER Set End -----------------------------------------------------

		// QueueNamePattern Set Start ------------------------------------------------
		value = config.getString(ConfigConst.NODE_PRE + ConfigConst.SND + ConfigConst.NODT_TEXT);
		if (value != null) {
			apiConf.getQNameConf().setPreSndQPT(value.trim());
			value = null;
		}
		value = config.getString(ConfigConst.NODE_PRE + ConfigConst.RCV + ConfigConst.NODT_TEXT);
		if (value != null) {
			apiConf.getQNameConf().setPreRcvQPT(value.trim());
			value = null;
		}
		value = config.getString(ConfigConst.NODE_PRE + ConfigConst.RQS+ ConfigConst.NODT_TEXT);
		if (value != null) {
			apiConf.getQNameConf().setPreRqsQPT(value.trim());
			value = null;
		}
		value = config.getString(ConfigConst.NODE_PRE + ConfigConst.RQR+ ConfigConst.NODT_TEXT);
		if (value != null) {
			apiConf.getQNameConf().setPreRqrQPT(value.trim());
			value = null;
		}
		value = config.getString(ConfigConst.NODE_PRE + ConfigConst.RPS+ ConfigConst.NODT_TEXT);
		if (value != null) {
			apiConf.getQNameConf().setPreRpsQPT(value.trim());
			value = null;
		}
		value = config.getString(ConfigConst.NODE_PRE + ConfigConst.RPR+ ConfigConst.NODT_TEXT);
		if (value != null) {
			apiConf.getQNameConf().setPreRprQPT(value.trim());
			value = null;
		}
		value = config.getString(ConfigConst.NODE_PRE + ConfigConst.ERR+ ConfigConst.NODT_TEXT);
		if (value != null) {
			apiConf.getQNameConf().setPreErrQPT(value.trim());
			value = null;
		}
		
		value = config.getString(ConfigConst.NODE_POST + ConfigConst.SND + ConfigConst.NODT_TEXT);
		if (value != null) {
			apiConf.getQNameConf().setPostSndQPT(value.trim());
			value = null;
		}
		value = config.getString(ConfigConst.NODE_POST + ConfigConst.RCV + ConfigConst.NODT_TEXT);
		if (value != null) {
			apiConf.getQNameConf().setPostRcvQPT(value.trim());
			value = null;
		}
		value = config.getString(ConfigConst.NODE_POST + ConfigConst.RQS+ ConfigConst.NODT_TEXT);
		if (value != null) {
			apiConf.getQNameConf().setPostRqsQPT(value.trim());
			value = null;
		}
		value = config.getString(ConfigConst.NODE_POST + ConfigConst.RQR+ ConfigConst.NODT_TEXT);
		if (value != null) {
			apiConf.getQNameConf().setPostRqrQPT(value.trim());
			value = null;
		}
		value = config.getString(ConfigConst.NODE_POST + ConfigConst.RPS+ ConfigConst.NODT_TEXT);
		if (value != null) {
			apiConf.getQNameConf().setPostRpsQPT(value.trim());
			value = null;
		}
		value = config.getString(ConfigConst.NODE_POST + ConfigConst.RPR+ ConfigConst.NODT_TEXT);
		if (value != null) {
			apiConf.getQNameConf().setPostRprQPT(value.trim());
			value = null;
		}
		value = config.getString(ConfigConst.NODE_POST + ConfigConst.ERR+ ConfigConst.NODT_TEXT);
		if (value != null) {
			apiConf.getQNameConf().setPostErrQPT(value.trim());
			value = null;
		}
		// QueueNamePattern Set End ------------------------------------------------
	}

	/**
	 * 설정파일 변경시 재 초기화한다.
	 * 
	 * @exception APIException
	 */
	private void reInitialize() throws APIException {
		this.init();
	}

	private void vaildChecker(APIConf apiConf) throws APIException {
		String methodName = "vaildChecker";
				
		boolean errorCD = false;
		String compulsoryValue = "";
		
		/* apiConfi.xml 필수값 체크 */
		// <MQ_CON_INFO> <BASE>
		if (apiConf.getMQConf().getBaseHost().equals("") || apiConf.getMQConf().getBaseHost() == null) {
			errorCD = true; compulsoryValue = compulsoryValue +  "<MQ_CON_INFO>/<BASE>/<Host>\n"; }
		if (apiConf.getMQConf().getBasePort().equals("") || apiConf.getMQConf().getBasePort() == null) {
			errorCD = true; compulsoryValue = compulsoryValue +  "<MQ_CON_INFO>/<BASE>/<Port>\n"; }
		if (apiConf.getMQConf().getBaseCcsid().equals("") || apiConf.getMQConf().getBaseCcsid() == null) {
			errorCD = true; compulsoryValue = compulsoryValue +  "<MQ_CON_INFO>/<BASE>/<CCSID>\n"; }
		if (apiConf.getMQConf().getBaseQmgr().equals("") || apiConf.getMQConf().getBaseQmgr() == null) {
			errorCD = true; compulsoryValue = compulsoryValue +  "<MQ_CON_INFO>/<BASE>/<QMGRName>\n"; }
		if (apiConf.getMQConf().getBaseChl().equals("") || apiConf.getMQConf().getBaseChl() == null) {
			errorCD = true; compulsoryValue = compulsoryValue +  "<MQ_CON_INFO>/<BASE>/<Channel>\n"; }
		
		// <MQ_CON_INFO> <BACK>
		if (apiConf.getMQConf().getBackHost().equals("") || apiConf.getMQConf().getBackHost() == null) {
			errorCD = true; compulsoryValue = compulsoryValue +  "<MQ_CON_INFO>/<BACK>/<Host>\n"; }
		if (apiConf.getMQConf().getBackPort().equals("") || apiConf.getMQConf().getBackPort() == null) {
			errorCD = true; compulsoryValue = compulsoryValue +  "<MQ_CON_INFO>/<BACK>/<Port>\n"; }
		if (apiConf.getMQConf().getBackCcsid().equals("") || apiConf.getMQConf().getBackCcsid() == null) {
			errorCD = true; compulsoryValue = compulsoryValue +  "<MQ_CON_INFO>/<BACK>/<CCSID>\n"; }
		if (apiConf.getMQConf().getBackQmgr().equals("") || apiConf.getMQConf().getBackQmgr() == null) {
			errorCD = true; compulsoryValue = compulsoryValue +  "<MQ_CON_INFO>/<BACK>/<QMGRName>\n"; }
		if (apiConf.getMQConf().getBackChl().equals("") || apiConf.getMQConf().getBackChl() == null) {
			errorCD = true; compulsoryValue = compulsoryValue +  "<MQ_CON_INFO>/<BACK>/<Channel>\n"; }
		
		// QueueNamePattern
		//필요 시 wanning 적용
		
		/* default value setting */
		if(apiConf.getLogPath().equals("") || apiConf.getLogPath().length() == 0) {
				apiConf.setLogPath(apiConfigPath + ConfigConst.DEF_LOG_PATH);
		} else {
			if(!apiConf.getLogPath().substring(apiConf.getLogPath().length()-1).equals("/")) {
				apiConf.setLogPath(apiConf.getLogPath() + "/"); 		// 경로 마지막에 / 검증
			}
		}
		if(apiConf.getLogLevel().equals("") || apiConf.getLogLevel().length() == 0) {
			apiConf.setLogLevel("INFO");
		}
		if(apiConf.getTraceLevel().equals("") || apiConf.getTraceLevel().length() == 0) {
			apiConf.setTraceLevel("01");
		}
		if(apiConf.getTraceQueue().equals("") || apiConf.getTraceQueue().length() == 0) {
			apiConf.setTraceLevel("EAI.TRK");
		}

		// host, ccsid 숫자만 허용
		try {
			int base_host = Integer.parseInt(apiConf.getMQConf().getBackPort());
			int back_host = Integer.parseInt(apiConf.getMQConf().getBackPort());
			int base_ccsid = Integer.parseInt(apiConf.getMQConf().getBackPort());
			int back_ccsid = Integer.parseInt(apiConf.getMQConf().getBackPort());
		} catch (NumberFormatException nfe) {
			throw new APIException("API", "0001", "Please check apiConfix.xml <MQ_CONN_INFO> host or ccsid (\n" + compulsoryValue + ")");
		} catch (Exception e) {
			throw new APIException("API", "0005", "MQAPI " + methodName + " Error.");
		}
		
		// apiConfig.xml 필수값 누락
		if(errorCD) {
			throw new APIException("API", "0001", "Please check apiConfix.xml compulsory value(\n" + compulsoryValue + ")");
		}
	}
}
