package listener;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

//新建一个Retry 类，implements IRetryAnalyzer接口，这个类里面确定重跑次数，以及分析每次失败是否需要重新运行
public class Retry implements IRetryAnalyzer {
	private static int maxRetryCount = 2;
	private int retryCount = 1;

	@Override
	public boolean retry(ITestResult result) {
//    	System.out.println(result);

		if (retryCount <= maxRetryCount) {

			String message = "Running retry for '" + result.getName() + "' on class " + this.getClass().getName()
					+ " Retrying " + retryCount + " times";
			Reporter.setCurrentTestResult(result);
			Reporter.log("RunCount=" + (retryCount + 1));
			retryCount++;
			return true;
		}
		return false;
	}
}
