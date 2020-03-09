package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class WaitUtil {
	public static void sleep(long time){
		try {
			Thread.sleep(time*1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void waitWebElement(WebDriver driver,String elementName) throws Exception{
		WebDriverWait wait=new WebDriverWait(driver,10);
	
		//调用ExpectedConditions的presenceofElementLocated方法判断页面是否出现
		wait.until(ExpectedConditions.presenceOfElementLocated(new ObjectMap().getLocator(elementName)));
		
	}
	//显示等待页面元素出现的封装方法，，参数为页面元素的By对象，此函数可以支持更多的定位表达式
	public static void waitWebElement(WebDriver driver,By by){
		WebDriverWait wait=new WebDriverWait(driver,10);
		//调用ExpectedConditions的presenceOfElementLocated方法判断页面元素是否出现
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}
}
