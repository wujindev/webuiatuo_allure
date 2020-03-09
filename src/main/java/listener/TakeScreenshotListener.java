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
	// �˴�Ϊ��ȡ��ǰ��driver������дһ����̬��������ȡ��ǰdriver��Ȼ��������
	WebDriver driver = BaseAction.getDriver();
	 String filePath;
	@Override
	public void onTestFailure(ITestResult tr) {
		// TODO Auto-generated method stub
		super.onTestFailure(tr);
		// ����Ϊȫ����������������com.testcases.LoginTest
		//String classname = tr.getTestClass().getName();
		// ������Ϊִ�еķ�����testWrongPasswordLogin
		//String methodname = tr.getMethod().getMethodName();

		takeScreenshotData(tr);
		Log.info("����ʧ��");
		
	}

	@Override
	public void onTestStart(ITestResult tr) {
		super.onTestStart(tr);
		Log.info("���п�ʼ");

	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		super.onTestSuccess(tr);
		Log.info("���гɹ�");
		
	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		super.onTestSkipped(tr);
		Log.info("��������");
		
	}
	 public void takeScreenShot(String classname, String methodname) {
	        // ��ȡ��ͼfile
	        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	        try {
	            // ��ͼƬ�ƶ���ָ��λ��
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
	        // ��������ͼƬ��·�����������򴴽�
	        File dir = new File(System.getProperty("user.dir")+"\\screenshots\\");
	        if (!dir.exists()) {
	            dir.mkdirs();
	        }
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
	        String dateStr = dateFormat.format(new Date());
	        // ��ȡ�µ��ļ���������ʱ�䣬������������
	        String fileName = dateStr + "_" + classname + "_" + methodname + ".png";
	        // ��ȡ�ļ�·��
	        String filePath = dir.getAbsolutePath() + "/" + fileName;
	        return filePath;

	    }
}