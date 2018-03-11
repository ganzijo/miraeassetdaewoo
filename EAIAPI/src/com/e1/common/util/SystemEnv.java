/*
* @(#)SystemEnv.java   1.0 2005/07/15
 */
package com.e1.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author JangJooHwi
 * @version 1.0, 2005/07/15
 * 
 * 시스템 환경 변수 관련 클래스.<br>
 * 시스템 환경 변수값을 가져오거나 설정하는 기능을 제공한다.
 */
public class SystemEnv {
	private static String osName = System.getProperty("os.name");
	private static Sys sys = null;

	/**
	 * 시스템 환경변수값을 가져온다.
	 *
	 * @param envName 환경변수명
	 * @return String
	 * @throws IOException
	 */
	public static String getEnv(String envName) throws IOException {

		//System.err.println(osName);
		if (osName.indexOf("Window") != -1) {
			sys = new Win2kSys();
		} else if(osName.indexOf("AIX") != -1) {
			sys = new UNIXSys();
		} else{
			sys = new SunSys();
		}
		//System.out.println("sys.getEnv( envName )" + sys.getEnv( envName ));
		return sys.getEnv(envName);
	}

	/**
	 * Inner Class 로 각 OS별 구현을 위한 추상클래스.<br>
	 * 각 OS별 구현을 위해서는 이 추상클래스를 extends 하여 구현하면된다.
	 */
	static abstract class Sys {
		abstract public String getEnv(String envName) throws IOException;
	}

	/**
	 * Sys를 extends한 Inner Class로 윈도우2000 이상 계열 구현클래스.
	 */
	static class Win2kSys extends Sys {
		public String getEnv(String envName) throws IOException {
			if (envName == null)
				return null;

			String envValue = null;

			try {
				// Windows 98 이하에선 cmd.exe -> command.exe
				String[] env = { "cmd.exe", "/c", "set", envName };

				Runtime rt = Runtime.getRuntime();

				Process proc_env = rt.exec(env, null);

				BufferedReader in =
					new BufferedReader(
						new InputStreamReader(proc_env.getInputStream()));

				envValue = in.readLine();

				if (envName != null
					&& envValue != null
					&& envName.length() < envValue.length()) {
					envValue = envValue.substring(envName.length() + 1);
				}
			} catch (IOException ioe) {
				throw ioe;
			}

			return envValue;
		}
	}

	/**
	 * Sys를 extends한 Inner Class로 UNIX 계열 구현클래스.
	 */
	static class UNIXSys extends Sys {
		public String getEnv(String envName) throws IOException {
			if (envName == null)
				return null;

			String envValue = null;

			try {
				String[] env = { "env", envName };

				Runtime rt = Runtime.getRuntime();

				Process proc_env = rt.exec(env, null);

				BufferedReader in =
					new BufferedReader(
						new InputStreamReader(proc_env.getInputStream()));

				envValue = in.readLine();
			} catch (IOException ioe) {
				throw ioe;
			}

			return envValue;
		}
	}

	static class SunSys extends Sys {
		public String getEnv(String envName) throws IOException {
			if (envName == null)
				return null;

			String envValue = null;

			try {
				//        String [] env = { "env", envName };
				String[] env = { "env" };

				Runtime rt = Runtime.getRuntime();

				Process proc_env = rt.exec(env, null);

				BufferedReader in =
					new BufferedReader(
						new InputStreamReader(proc_env.getInputStream()));

				//        envValue = in.readLine();
				while ((envValue = in.readLine()) != null) {
					if (envValue.indexOf(envName) != -1) {
						envValue = envValue.substring(envName.length() + 1);
						break;
					}
				}
			} catch (IOException ioe) {
				throw ioe;
			}

			return envValue;
		}
	}

	/**
	 * 모든 시스템 정보 및 환경변수 값을 출력한다.
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception 
	{
		// 모든 시스템 정보 및 환경변수 값을 출력한다.
		// 1. Get all system properties
		Properties props = System.getProperties();

		// Enumerate all system properties
		Enumeration enum1 = props.propertyNames();
		System.out.println("----------------- system properties ------------------");
		for (; enum1.hasMoreElements();) 
		{
			// Get property name
			String propName = (String) enum1.nextElement();
			System.out.print(propName + "=");

			// Get property value
			String propValue = (String) props.get(propName);
			System.out.print(propValue + "\n");
		}
		System.out.println("os.name=" + System.getProperty("os.name"));
		System.out.println("os.version=" + System.getProperty("os.version"));
		System.out.println("os.arch=" + System.getProperty("os.arch"));

		// 2. Get all system environment variable
		System.out.println("------------ system environment variable -------------");

		Runtime rt = Runtime.getRuntime();
		Process proc_env = null;
		if (System.getProperty("os.name").indexOf("Window") != -1) 
		{
			// Windows 98 이하에선 cmd.exe -> command.exe
			String[] env = { "cmd.exe", "/c", "set" };
			proc_env = rt.exec(env, null);
		} 
		else 
		{
			String[] env = { "set" };
			proc_env = rt.exec(env, null);
		}

		BufferedReader in =new BufferedReader(new InputStreamReader(proc_env.getInputStream()));

		//System.out.println(StringUtil.getStringFromReader(in));
	}
}
