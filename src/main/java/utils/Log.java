package utils;

import org.apache.log4j.Logger;


public class Log {
	
	//日志记录器
	public static final Logger logger=Logger.getLogger(Log.class.getName());
	// 定义测试用例开始执行的打印方法，在日志中打印测试用例开始执行的信息
	public static void startTestCase(String testCaseName) {
		logger.info("-------------------\"" + testCaseName + "\"开始执行---------------");
	}

	// 定义测试用例执行完毕后的打印方法，在日志中打印测试用例执行完毕的信息
	public static void endTestCase(String testCaseName) {
		logger.info("-------------------\"" + testCaseName + "\"执行结束---------------");
	}
	//定义打印info级别日志的方法
	public static void info(String message){
		logger.info(message);
	}
	//定义打印error级别日志的方法
	public static void error(String message){
		logger.error(message);
	}
	//定义打印debug级别日志的方法
	public static void debug(String message){
		logger.debug(message);
	}
}
