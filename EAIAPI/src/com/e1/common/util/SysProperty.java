/**
 * @author JangJooHwi
 * @version 1.0, 2005/07/15
 */
package com.e1.common.util;

import java.net.UnknownHostException;

import com.e1.eai.excp.APIException;

public class SysProperty {
	/**
	 * SysProperty 생성자 주석.
	 */
	public SysProperty() {
		super();
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2001-06-20 오후 6:17:25)
	 * 
	 * @return boolean
	 */
	public static String getEaiHome() {
		java.util.Properties pp = new java.util.Properties();
		pp = System.getProperties();
		String EAI_HOME = pp.getProperty("EAI_HOME");
		return EAI_HOME;
	}

	public static long getPID() throws APIException {
		String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
		
		try {
			if (processName != null && processName.length() > 0) {
				return Long.parseLong(processName.split("@")[0]);
			} else {
				return 0;
			}
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static String getHostname() throws APIException {
		String methodName = "getHostname";
		
		String hostName = "";
		try {
			hostName = java.net.InetAddress.getLocalHost().getHostName();
		}catch(Exception e) {
			throw new APIException("API", "0030", "MQAPI " + methodName + " Error.", e); 
		}
		return hostName;
	}
}