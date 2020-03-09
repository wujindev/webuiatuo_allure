package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CommonUtil {
	private static Properties properties;

	public static String getPropValue(String propertiesPath, String key) {
		properties = new Properties();
		FileInputStream in;
		try {
			in = new FileInputStream(propertiesPath);
			properties.load(in);
			in.close();
		} catch (IOException e) {

			System.out.println("读取文件对象出错");
			e.printStackTrace();
		}
		return properties.getProperty(key);
	}
}
