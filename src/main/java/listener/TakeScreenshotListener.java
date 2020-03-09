package listener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import common.BaseAction;
import io.qameta.allure.Attachment;
import net.bytebuddy.implementation.bind.annotation.Super;
import utils.Log;

public class TakeScreenshotListener extends TestListenerAdapter {
	// 此处为获取当前的driver，可以写一个静态方法来获取当前driver，然后来调用
	WebDriver driver = BaseAction.getDriver();
	 String filePath;
	@Override
	public void onTestFailure(ITestResult tr) {
		// TODO Auto-generated method stub
		super.onTestFailure(tr);
		// 类名为全类名，包含包名：com.testcases.LoginTest
		//String classname = tr.getTestClass().getName();
		// 方法名为执行的方法：testWrongPasswordLogin
		//String methodname = tr.getMethod().getMethodName();

		takeScreenshotData(tr);
		Log.info("运行失败");
		
	}

	@Override
	public void onTestStart(ITestResult tr) {
		super.onTestStart(tr);
		Log.info("运行开始");

	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		super.onTestSuccess(tr);
		Log.info("运行成功");
		
	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		super.onTestSkipped(tr);
		Log.info("运行跳过");
		
	}
	 public void takeScreenShot(String classname, String methodname) {
	        // 获取截图file
	        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	        try {
	            // 将图片移动到指定位置
	            FileUtils.copyFile(file, new File(getFilePath(classname, methodname)));
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
	    @Attachment(value = "screenshot",type = "image/png")
	    public byte[] takeScreenshotData(ITestResult result){
	        byte[] screenshotAs = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
	        String[] classNameArr = result.getTestClass().getName().split("\\.");
	        String className = classNameArr[classNameArr.length-1];
	        String methodName = result.getName();


	       
	       
	        try (FileOutputStream out = new FileOutputStream(getFilePath(className, methodName))){
	            out.write(screenshotAs);
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return screenshotAs;
	    }

	    public String getFilePath(String classname, String methodname) {
	        // 创建储存图片的路径，不存在则创建
	        File dir = new File(System.getProperty("user.dir")+"\\screenshots\\");
	        if (!dir.exists()) {
	            dir.mkdirs();
	        }
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
	        String dateStr = dateFormat.format(new Date());
	        // 获取新的文件名，包含时间，类名，方法名
	        String fileName = dateStr + "_" + classname + "_" + methodname + ".png";
	        // 获取文件路径
	        String filePath = dir.getAbsolutePath() + "/" + fileName;
	        return filePath;

	    }
}