package testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import common.BaseAction;
import listener.TakeScreenshotListener;
import listener.TestListener;
import listener.RetryListener;
import pageobjects.LoginPage;
import utils.ExcelUtil;

//@Listeners({RetryListener.class,TestListener.class,TakeScreenshotListener.class})
public class OpenHome {


//	@BeforeClass
//	public void setUp() throws Exception {
//		 driver=BaseAction.getDriver();
//		
//	}
	@Test // (retryAnalyzer=listener.Retry.class)
	public void openHome() {
		// loginPage=new LoginPage(driver);
		WebDriver driver = BaseAction.getDriver();
		LoginPage loginPage = new LoginPage(driver);
		
		System.out.println(System.getProperty("user.dir") + "\\src\\main\\resources\\testdata\\testData.xlsx");
		System.out.print("ss" + ExcelUtil.getTestData("CarrierICS"));
		System.out.println(ExcelUtil.getTestData("CarrierICS").get("loginUrl"));

		loginPage.login(ExcelUtil.getTestData("CarrierICS").get("loginUrl"),
				ExcelUtil.getTestData("CarrierICS").get("username"),
				ExcelUtil.getTestData("CarrierICS").get("password"));
		BaseAction.openUrl(ExcelUtil.getTestData("CarrierICS").get("portalUrl"));
		BaseAction.Assert_PageSource("FusionStorage_8.0.0");
		// BaseAction.getScreenshot();

		
			Assert.assertTrue(false);
		
			driver.quit();
	}

//  @AfterClass
//  public void tearDown() {
//	  driver.quit();
//	  
//  }
}
