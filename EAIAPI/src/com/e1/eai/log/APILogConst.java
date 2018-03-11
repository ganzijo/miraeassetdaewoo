/**
 * 0. Project	: 미래에셋대우 API Adapter
 * 1. FileName	: APILogLevel.java
 * 2. Package	: com.e1.eai.log
 * 3. Commnet	: API Custorm Log Level Define Class (used log4j2)
 * 4. 작성자	: 박민아
 * 5. 작성일	: 2017. 8. 31.
 * 6. 변경이력	:
 *	  이름						:일자			:	변경내용
 *	------------------------------------------------------
 *
 **/
package com.e1.eai.log;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.spi.StandardLevel;

/**
 * API Custorm Log Level Define Class
 */
public class APILogConst {
	// Reference source : https://logging.apache.org/log4j/log4j-2.2/manual/customloglevels.html

	// LogPath -------------------------------------------------------
	public final static String TRAN_LOG_PATH = "tranlog_LogPath";
	public final static String ERR_LOG_PATH  = "error_LogPath";
	public final static String CUR_LOG_PATH  = "cur_LogPath";

	/** userlogLevel int **/
	private static final int DEBUG_2_INT = StandardLevel.DEBUG.intLevel() + 10;
	private static final int DEBUG_3_INT = StandardLevel.DEBUG.intLevel() + 20;
	private static final int DEBUG_4_INT = StandardLevel.DEBUG.intLevel() + 30;
	private static final int DEBUG_5_INT = StandardLevel.DEBUG.intLevel() + 40;

	// User Log lever
	public static final Level DEBUG_2 = Level.forName("DEBUG_2", DEBUG_2_INT);
	public static final Level DEBUG_3 = Level.forName("DEBUG_3", DEBUG_3_INT);
	public static final Level DEBUG_4 = Level.forName("DEBUG_4", DEBUG_4_INT);
	public static final Level DEBUG_5 = Level.forName("DEBUG_5", DEBUG_5_INT);

	// FATAL > ERROR > WARN > INFO > DEBUG > TRACE
	public static final String LEVEL_FATAL = "FATAL";
	public static final String LEVEL_ERROR = "ERROR";
	public static final String LEVEL_WARN = "WARN";
	public static final String LEVEL_INFO = "INFO";
	public static final String LEVEL_DEBUG = "DEBUG";
	public static final String LEVEL_DEBUG_2 = "DEBUG_2";
	public static final String LEVEL_DEBUG_3 = "DEBUG_3";
	public static final String LEVEL_DEBUG_4 = "DEBUG_4";
	public static final String LEVEL_DEBUG_5 = "DEBUG_5";

	/** Log Info **/
	public static final String LOG_PATH = "/logs"; // default log path
	public static final String LOG_PATTERN = "%d{yyyy-MM-dd HH:mm:ss.SSS} | %-7.7p | ::%-16.16M Line:%-5.5L | %msg%n";
	public static final String LOG_NAME = ".log"; // logfile name patten
	public static final String LOG_NAME_BACK = "_%d{yyyyMMddHH}_%i.log"; // backup logfile name pattern
	public static final String CONSOLE_APPENDER_NAME = "CONSOLE_APPENDER";
	public static final String ROLLING_APPENDER_NAME = "FILE_APPERNDER";
	public static final String LOG_ROLLOVER_STRATEGY = "5"; // rolling file index
	public static final String LOG_FILE_SIZE = "10KB"; // rolling file size

	/** Defualt Log Info **/
	public final static String DEF_MAINLOG_NAME = "apiAdapter";
	public final static String DEF_LOGGER_NAME = "apiLog";
	public final static String DEF_LOG_PAHT = "/logs";
	public final static String DEF_LOG_TYPE = ROLLING_APPENDER_NAME;
	public final static String DEF_LOG_LEVEL = "INFO";
	public final static String DEF_TRACELEVEL = "01";

}