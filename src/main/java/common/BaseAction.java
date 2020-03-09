package common;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.poi.ss.usermodel.DataConsolidateFunction;
import org.testng.Assert;



import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import utils.CommonUtil;
import utils.KeyBoardUtil;
import utils.Log;
import utils.ObjectMap;
import utils.WaitUtil;

public class BaseAction{

	public static WebDriver driver;
	private static ObjectMap objectMap = new ObjectMap();
	static {
		DOMConfigurator.configure("log4j.xml");
	}

	public static WebDriver getDriver() {
		String browserType=CommonUtil.getPropValue(System.getProperty("user.dir")+"\\src\\main\\resources\\conf\\autotest.properties", "browserType");
		switch (browserType) {
		case "ie":
			//加载驱动器
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\src\\main\\resources\\driver\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			Log.info("IE浏览器实例已经声明");
			break;
		case "firefox":
			//加载驱动器
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\src\\main\\resources\\driver\\geckodriver.exe");
			driver = new FirefoxDriver();
			Log.info("火狐浏览器实例已经声明");
			break;
		case "chrome":
			//加载驱动器
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\main\\resources\\driver\\chromedriver.exe");
			driver = new ChromeDriver();
			Log.info("Chrome浏览器实例已经声明");
			break;
		}
		return driver;
	}

	public static void openUrl(String url) {	
		driver.get(url);
		WaitUtil.sleep(2);
		Log.info("浏览器访问网址" + url);
	}

	public static String getCurrentPageUrl(){
		String currentPageUrl = null;
		try {
			currentPageUrl=driver.getCurrentUrl();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return currentPageUrl;
	}

	public static void input(String elementString,String inputString) {
		try {
			driver.findElement(objectMap.getLocator(elementString)).clear();
			Log.info("清除" + elementString+"输入框中的所有内容");
			driver.findElement(objectMap.getLocator(elementString)).sendKeys(inputString);
			Log.info("在" + elementString+"输入框中输入"+inputString);
		} catch (Exception e) {
			Log.info("在" + elementString+"输入框中输入"+inputString+"出现异常" + e.getMessage());
		}

	}

	public static void click(String elementString) {
		try {
			driver.findElement(objectMap.getLocator(elementString)).click();
			Log.info("单击" + elementString+"页面元素成功");
		} catch (Exception e) {
			Log.info("单击" + elementString+"页面元素失败"+ e.getMessage());
		}
	}
	
	public static void press_Tab(String elementString) {
		try {
			Thread.sleep(2000);
			// 调用KeyBoardUtil类的封装方法pressTabKey
			KeyBoardUtil.pressTabKey();
			Log.info("按Tab键成功");
		} catch (Exception e) {
			Log.info("按Tab键出现异常，具体异常信息" + e.getMessage());
		}
	}
	
	public static void press_enter(String elementString) {
		try {
			KeyBoardUtil.pressEnterKey();
			Log.info("按enter键成功");
		} catch (Exception e) {
			Log.info("按enter键出现异常，具体异常信息" + e.getMessage());
		}
	}
	
	public static WebElement findElement(String elementString) {

		WebElement element = null;
		try {
			element = driver.findElement(objectMap.getLocator(elementString));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return element;
	}
	
	public static List<WebElement> findElements(String elementString) {

		List<WebElement> elements = null;
		try {
			elements = driver.findElements(objectMap.getLocator(elementString));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return elements;
	}

	
	public static void Assert_PageSource(String assertString) {
		try {
			Assert.assertTrue(driver.getPageSource().contains(assertString));
			Log.info("成功断言关键字:" + assertString);
		} catch (AssertionError e) {
			Log.info("出现断言失败，具体异常信息" + e.getMessage());
		}
	}

	public static void closeBrowser() {
		try {
			Log.info("关闭浏览器窗口");
			driver.close();
		} catch (Exception e) {
			Log.info("关闭浏览器出现异常，具体异常信息" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void closeAllBrowser() {
		try {
			Log.info("关闭浏览器窗口");
			driver.quit();
		} catch (Exception e) {
			Log.info("关闭浏览器出现异常，具体异常信息" + e.getMessage());
			e.printStackTrace();
		}
	}
	public void BrowserWindowsMax(){
		driver.manage().window().maximize();
	}
	
	public void SetBrowserWindows(int x,int y){
		Dimension dimesion=new Dimension(x,y);
		driver.manage().window().setSize(dimesion);
	}
	
	public void FreshPage(){
		driver.navigate().refresh();
	}
	/*模拟浏览器的前进功能*/
	public void ForwardPage(){
		driver.navigate().forward();
	}
	/*模拟浏览器的后退功能*/
	public void BackPage(){
		driver.navigate().back();
	}
	
	public static void OpenLinkWindow(String elementString,String string){
		//将当前浏览器窗口的句柄存储到parentWindowHandle变量中
		String parentWindowHandle=driver.getWindowHandle();
		//找到页面上唯一的连接元素，存储到link变量中
		WebElement link;
		try {
			link = driver.findElement(objectMap.getLocator(elementString));
			//单击链接
			link.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//获取当前所有打开窗口的句柄，存储到一个set容器中
		Set<String> allWindowHandles=driver.getWindowHandles();
		//存储对象不为空，遍历容器中的所有对象
		if(!allWindowHandles.isEmpty()){
			for(String allWindowHandle:allWindowHandles){
				//获取页面的标题属性，判断属性值是否string几个关键字
				String title=driver.switchTo().window(allWindowHandle).getTitle();
				if(title.equals(string)){
					Log.info("判断成立"+string);
					break;
				}else{
					Log.info("判断不成立"+string);
					break;
				}
			}
		}
		//返回最开始打开的浏览器页面
		driver.switchTo().window(parentWindowHandle);
	}
	
	public static void getScreenshot(){
	      Date now = new Date();
	      SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh-mm-ss");
	 
	    
		//把当前的浏览器页面进行截图，保存到File对象中
		File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			//把File对象文件转换成一个png文件，保存到c盘中
			FileUtils.copyFile(srcFile,new File(System.getProperty("user.dir")+"\\screenshots\\"+ft.format(now)+"_report.png"));
			Log.info("保存图片文件成功");
		} catch (IOException e) {
			Log.info("保存图片文件失败");
			e.printStackTrace();
		}
	}
	
		public static void switchToFrame(String elementString){
		try{
			//获取嵌入式框架对象，进入框架区域
			WebElement iframe=driver.findElement(objectMap.getLocator(elementString));
			driver.switchTo().frame(iframe);
			System.out.println("在框架内");
			Log.info("进入"+elementString+"框架成功");
		}catch(Exception e){
			Log.info("进入"+elementString+"框架失败");
			e.printStackTrace();
		}
	}
	public static void switchToAlert(){
		try{
			//获取Alert对象
			Alert alert=driver.switchTo().alert();
			//单击确定按钮
			alert.accept();
			//单击取消按钮
			//alert.dismiss();
		}catch(Exception e){
			Log.info("尝试操作的失败");
			e.printStackTrace();
		}
	}

	public static void handleCookie(){
		Set<Cookie>cookies=driver.manage().getCookies();
		for(Cookie cookie:cookies){
			System.out.println("所在的域:"+cookie.getDomain());
			System.out.println("name:"+cookie.getName());
			System.out.println("value:"+cookie.getValue());
			System.out.println("有效日期:"+cookie.getExpiry());
			System.out.println("路径:"+cookie.getPath());
			//通过对象,删除Cookie
			Cookie newCookie=new Cookie(cookie.getName(),cookie.getValue());
			driver.manage().deleteCookie(newCookie);		
		}
		/*
		 * 删除Cookie方法1通过名字
		 * driver.manage().deleteCookieNamed("");
		 * 删除Cookie方法3删除所有
		 * driver.manage().deleteAllCookies();
		 */
		try {
			Thread.sleep(2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isElementExist(String elementString,WebElement string) {
		try {
			string=driver.findElement(objectMap.getLocator(elementString));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(string!=null){
			Log.info("操作的元素找到");
			return true;
		}else{
			Log.info("操作的元素没有找到");
			return false;
		}
	}

	public void assertWebElementText(String elementString,String string){
		//捕获对象
		WebElement text;
		try {
			text = driver.findElement(objectMap.getLocator(elementString));
			String contentText=text.getText();
			//完全匹配
			Assert.assertEquals(contentText, string);
			//文字内容是否包含String
			Assert.assertTrue(contentText.contains(string));
			//文字内容开始文字是否是String
			Assert.assertTrue(contentText.startsWith(string));
			//文字内容结束文字是否是String
			Assert.assertTrue(contentText.endsWith(string));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void WaitForElement(String elementString) {

		try {
			// 调用封装的waitWebElement函数显示等待页面元素是否出现
			WaitUtil.waitWebElement(driver, elementString);
			Log.info("显示等待元素出现成功，元素是" +elementString);
		} catch (Exception e) {
			Log.info("显示等待元素出现异常，具体异常信息" + e.getMessage());
			e.printStackTrace();
		}
	}

	public void CheckSlectText(String elementString,String[] string){
		try {
			Select dropList=new Select(driver.findElement(objectMap.getLocator(elementString)));
			//设定单选列表的期望值
			List<String>expect_option=new ArrayList<String>();
			expect_option.add(string.toString());
			//获取单选列表的实际值存储到list列表中
			List<String>actual_option=new ArrayList<String>();
			for(WebElement option:dropList.getOptions()){
				actual_option.add(option.getText());
				
			}
				//判断期望值是否与实际值一致
				if(expect_option.toArray().equals(actual_option.toArray())){
					Log.info("期望值与实际值一致"+expect_option.toArray());
					
				}else{
					Log.info("期望值与实际值不一致"+actual_option.toArray());
					
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	//选中复选框
	public void SelectCheckBox(String elementString,String string){
		//获取复选框元素对象
		WebElement checkbox;
		try {
			checkbox = driver.findElement(objectMap.getLocator(elementString));
			if(!checkbox.isSelected()){
				checkbox.click();
				Log.info("选中复选框");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//取消复选框
	public void NoSelectCheckBox(String elementString,String string){
		//获取复选框元素对象
		WebElement checkbox;
		try {
			checkbox = driver.findElement(objectMap.getLocator(elementString));
			if(checkbox.isSelected()){
				checkbox.click();
				Log.info("取消选中复选框");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//选中全部复选框
	public void AllSelectCheckBox(String elementString,String string){
		List<WebElement> checkboxs;
		try {
			checkboxs = driver.findElements(objectMap.getLocator(elementString));
			for(WebElement checkbox:checkboxs ){
				checkbox.click();
				//checkbox.click();取消全部选中的复选框
				Log.info("全部选中复选框");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void SelectRadio(String elementString) {
		WebElement radio;
		try {
			radio = driver.findElement(objectMap.getLocator(elementString));
			if (!radio.isSelected()) {
				radio.click();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//取消单选框
	public void NoSelectRadio(String elementString){
		WebElement radio;
		try {
			radio = driver.findElement(objectMap.getLocator(elementString));
			if (radio.isSelected()) {
				radio.click();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//选中全部复选框
	public void AllSelectCheckbox(String elementString){
		List<WebElement> checkboxs;
		try {
			checkboxs = driver.findElements(objectMap.getLocator(elementString));
			for(WebElement checkbox:checkboxs){
				if(!checkbox.isSelected()){
					checkbox.click();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void DropList(String elementString,String string){
		try {
			Select dropList=new Select(driver.findElement(objectMap.getLocator(elementString)));
			//判断下拉列表是否支持多选,支持多选返回true
			boolean flag=dropList.isMultiple();
			if(flag==true){
				dropList.selectByValue(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void NoDropList(String elementString,String string){
		try {
			Select dropList=new Select(driver.findElement(objectMap.getLocator(elementString)));
			//判断下拉列表是否支持多选,支持多选返回true
			boolean flag=dropList.isMultiple();
			if(flag==true){
				dropList.deselectByValue(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void OneDropList(String elementString,String string){
		try {
			Select dropList=new Select(driver.findElement(objectMap.getLocator(elementString)));
			boolean flag=dropList.isMultiple();
			if(flag==false){
				Log.info("进入单选下拉列表");
				dropList.selectByValue(string);
				/* 通过选项列表的下标值进行选中
				 * dropList.selectByIndex(0);
				 * 通过选项的文字进行选中
				 * dropList.selectByVisibleText(string);
				 */
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void DoubleClick(String elementString){
		WebElement doubleClick;
		try {
			//获取被操作的对象
			doubleClick=driver.findElement(objectMap.getLocator(elementString));
			//声明Actions对象
			Actions action=new Actions(driver);
			//双击元素对象
			action.doubleClick(doubleClick).build().perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean GetTitle(String Title){
		String getTitle=driver.getTitle();
		if(getTitle.contains(Title)){
			Log.info("跳转到页面"+getTitle);
			return true;
		}else{
			Log.info("没有跳转到页面"+getTitle);
			return false;
		}
	}

	public void LeftClickAndRelease(String elementString){
		try {
			//获取被鼠标操作对象
			WebElement leftmouse=driver.findElement(objectMap.getLocator(elementString));
			//声明Actions对象
			Actions action=new Actions(driver);
			//单击鼠标左键
			action.clickAndHold(leftmouse).perform();
			//暂停2秒释放鼠标左键
			WaitUtil.sleep(2000);
			action.release(leftmouse);
		} catch (Exception e) {	
			e.printStackTrace();
		}
	}
	
	public void RightClick(String elementString){
		try {
			//获取被鼠标操作对象
			WebElement rightmouse=driver.findElement(objectMap.getLocator(elementString));
			//声明Actions对象
			Actions action=new Actions(driver);
			//暂停3秒单击鼠标右键
			WaitUtil.sleep(3000);
			action.contextClick(rightmouse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void OverMouseLink(String elementString){
		//获取被鼠标操作对象
		try {
			WebElement mouselink=driver.findElement(objectMap.getLocator(elementString));
			//声明Actions对象
			Actions action=new Actions(driver);
			//鼠标在超链接上悬浮
			action.moveToElement(mouselink).perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    public static void scrollToElement(WebDriver driver, WebElement element) {
        WaitUtil.sleep(1000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", element);
 
    }

    public static boolean clickByJS(WebDriver driver, WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click()", element);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean isAlertPresent(WebDriver driver) {
        try {
            driver.switchTo().alert();
            return true;
        } catch (Exception Ex) {
            return false;
        }
    }

    public static void acceptAlert(WebDriver driver) {
        if (isAlertPresent(driver)) {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        }
    }

    public static void cancelAlert(WebDriver driver) {
        if (isAlertPresent(driver)) {
            Alert alert = driver.switchTo().alert();
            alert.dismiss();
        }
    }

}
