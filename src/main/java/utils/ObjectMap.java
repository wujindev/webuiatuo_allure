package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;

public class ObjectMap {

	private static String elementPath=System.getProperty("user.dir")+"\\src\\main\\resources\\conf\\ObjectMap.properties";

	
	
	
	public static By getLocator(String elementName) throws Exception {
		String locator = CommonUtil.getPropValue(elementPath, elementName);
		// 将配置对象中的定位类型存储到locatorType变量中，将定位表达式的值存储到locatorValue中
		// 获取的键值字符串通过>分割
		String locatorType = locator.split("=>")[0];//定位类型
		String locatorValue = locator.split("=>")[1];//定位表达式
		locatorValue = new String(locatorValue.getBytes("ISO-8859-1"), "UTF-8");
		// 输出locatorType和locatorValue变量值，验证赋值是否正确
		System.out.println("获取的定位类型:" + locatorType + "\t获取的定位表达式:" + locatorValue);
		// 根据locatorType的变量值内容判断返回何种定位方式的by对象
		if (locatorType.toLowerCase().equals("id")) {
			return By.id(locatorValue);
		} else if (locatorType.toLowerCase().equals("name")) {
			return By.name(locatorValue);

		} else if (locatorType.toLowerCase().equals("classname") || locatorType.toLowerCase().equals("class")) {
			return By.className(locatorValue);

		} else if (locatorType.toLowerCase().equals("tagname") || locatorType.toLowerCase().equals("tag")) {
			return By.tagName(locatorValue);

		} else if (locatorType.toLowerCase().equals("linktext") || locatorType.toLowerCase().equals("link")) {
			return By.linkText(locatorValue);

		} else if (locatorType.toLowerCase().equals("partiallinktext")) {
			return By.partialLinkText(locatorValue);

		} else if (locatorType.toLowerCase().equals("cssselector") || locatorType.toLowerCase().equals("css")) {
			return By.cssSelector(locatorValue);

		} else if (locatorType.toLowerCase().equals("xpath")) {
			return By.xpath(locatorValue);

		} else
			throw new Exception("输入的locatorType未在程序中定义:" + locatorType);

	}
}