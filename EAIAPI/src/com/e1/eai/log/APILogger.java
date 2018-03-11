package com.e1.eai.log;

import java.nio.charset.Charset;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.TimeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

import com.e1.eai.excp.APIException;

public class APILogger {
	private static String mainLogName = "";

	private static Logger logger = null;
	private static LoggerContext context = null;
	private static Configuration config = null;
	private static LoggerConfig loggerConfig = null;

	private static String pid = "";
	private static String logPath = "";

	/** Logger **/
	public static Logger getInstance(long processID, String path, String logLevel) throws APIException {
		// Log 셋팅 path & name
		logPath = path;
		pid = "_" + processID;
		mainLogName = APILogConst.DEF_MAINLOG_NAME;

		// get Logger		
		logger = getLogger( mainLogName + pid, setLogLevel(logLevel));

		// 최초 로그 load시 필수값 check 하여 오류 발생시킴.
		if (logger == null || logger.getLevel() == null || logger.getName() != APILogConst.DEF_LOGGER_NAME) {
			throw new APIException("API", "0005", "Logger initialize Fail");
		}
		return logger;
	}

	public static Logger getLogger() {
		if (logger == null || logger.getLevel() == null || logger.getName() != APILogConst.DEF_LOGGER_NAME) {
			// 로그파일 오류 시
		}
		return logger;
	}
	
	public static Logger getInstance(long processID , String interfaceID, String path, String logLevel) {
		// Log 셋팅 path & name
		logPath = path;				
		pid = "_" + processID;
		String moduleName = interfaceID + pid;

		// get Logger
		logger = getLogger(moduleName, setLogLevel(logLevel));

		if (logger == null || logger.getLevel() == null || logger.getName() != APILogConst.DEF_LOGGER_NAME) {
			// 로그파일 오류 시
		}
		return logger;
	}

	public static Level setLogLevel(String logLevel) {
		Level level = null;

		/** **/
		if (APILogConst.LEVEL_DEBUG.contains(logLevel)) {
			level = Level.DEBUG;
		} else if (APILogConst.LEVEL_DEBUG_2.equals(logLevel)) {
			level = APILogConst.DEBUG_2;
		} else if (APILogConst.LEVEL_DEBUG_3.equals(logLevel)) {
			level = APILogConst.DEBUG_3;
		} else if (APILogConst.LEVEL_DEBUG_4.equals(logLevel)) {
			level = APILogConst.DEBUG_4;
		} else if (APILogConst.LEVEL_DEBUG_5.equals(logLevel)) {
			level = APILogConst.DEBUG_5;
		}

		/** **/
		else if (APILogConst.LEVEL_INFO.equals(logLevel)) {
			level = Level.INFO;
		} else if (APILogConst.LEVEL_WARN.equals(logLevel)) {
			level = Level.WARN;
		} else if (APILogConst.LEVEL_ERROR.equals(logLevel)) {
			level = Level.ERROR;
		} else if (APILogConst.LEVEL_FATAL.equals(logLevel)) {
			level = Level.FATAL;
		} else {
			level = Level.ERROR;
		}

		return level;
	}

	public static Logger getLogger(String moduleName, Level logLevel) {
		context = (LoggerContext) LogManager.getContext();

		if (config == null) {
			config = context.getConfiguration();
		} else {
			// 기존 log cfg 삭제
			config.removeLogger("apiLog");
		}

		PatternLayout layout = PatternLayout.createLayout(APILogConst.LOG_PATTERN, null, config, null,
				Charset.defaultCharset(), true, false, null, null);

		TimeBasedTriggeringPolicy timeTP = TimeBasedTriggeringPolicy.createPolicy("1", "false");
		SizeBasedTriggeringPolicy sizeTP = SizeBasedTriggeringPolicy.createPolicy(APILogConst.LOG_FILE_SIZE);

		DefaultRolloverStrategy strategy = DefaultRolloverStrategy.createStrategy(APILogConst.LOG_ROLLOVER_STRATEGY,
				"0", null, null, null, false, config);
		
		// create Appenders
		Appender fileAppender = RollingFileAppender.createAppender(
				logPath + moduleName +".log", // 현재 기록되는 log 파일명(필수)
				logPath + moduleName + APILogConst.LOG_NAME_BACK, // 백업할 파일 log 파일 패턴 (필수)
				"true", // isAppend? (default: true)
				APILogConst.ROLLING_APPENDER_NAME, // appender name (필수)
				"false", // buffer IO 사용 여부 (default: true)
				"512", // buffer size (default: 8192)
				"true", // 이벤트 발생 시 즉시 flush (default: true)
				sizeTP, // trigger policy (필수)
				strategy, // The rollover strategy. Defaults to DefaultRolloverStrategy.
				layout, // layout The layout to use (defaults to the default PatternLayout).
				null, // filter
				"true", // is exception ignore? (default: true), false: throws exception to caller
				null, // advertise
				null, // advertiseURI
				config // Configuration
		);

		//Appender consoleAppender = ConsoleAppender.createAppender(layout, null, null, APILogConst.CONSOLE_APPENDER_NAME, null, null);

		//consoleAppender.start();
		fileAppender.start();

		// Appender ref
		//AppenderRef consoleRef = AppenderRef.createAppenderRef(APILogConst.CONSOLE_APPENDER_NAME, Level.INFO, null);
		AppenderRef fileRef = AppenderRef.createAppenderRef(APILogConst.ROLLING_APPENDER_NAME, logLevel, null);

		//AppenderRef[] refs = new AppenderRef[] { consoleRef, fileRef };
		
		AppenderRef[] refs = new AppenderRef[] { fileRef };

		loggerConfig = LoggerConfig.createLogger("true", logLevel, LogManager.ROOT_LOGGER_NAME, "true", refs, null,
				config, null);

		//loggerConfig.addAppender(consoleAppender, Level.INFO, null);
		loggerConfig.addAppender(fileAppender, logLevel, null);

		config.addLogger("apiLog", loggerConfig);

		context.updateLoggers(config);
		logger = LogManager.getContext().getLogger(APILogConst.DEF_LOGGER_NAME);

		return logger;
	}
}