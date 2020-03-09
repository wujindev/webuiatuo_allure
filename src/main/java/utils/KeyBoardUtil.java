package utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class KeyBoardUtil {
	//按tab键的封装方法
	public static void pressTabKey(){
		Robot robot=null;
		try {
			robot=new Robot();
		} catch (AWTException e) {
			
			e.printStackTrace();
		}
		//调用KeyPress的方法实现按下tab键
		robot.keyPress(KeyEvent.VK_TAB);
		//调用KeyPress的方法实现释放tab键
		robot.keyRelease(KeyEvent.VK_TAB);
	}
	//按Enter键的封装方法
	public static void pressEnterKey(){
		Robot robot=null;
		try {
			robot=new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//调用KeyPress的方法实现按下Enter键
		robot.keyPress(KeyEvent.VK_ENTER);
		//调用KeyPress的方法实现释放Enter键
		robot.keyRelease(KeyEvent.VK_ENTER);
	}
	
	/*将指定的字符串设为剪切板的内容，然后执行黏贴操作
	 * 将页面焦点切换到输入框后，调用此函数可以将指定的字符串黏贴到输入框中
	 */
	public static void setAndCtrlVClipboardData(String string){
		StringSelection	stringSelection=new	StringSelection(string);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
		Robot robot=null;
		try {
			robot=new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//以下4行代码表示按下和释放Ctrl+V组合键
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
		
	}
	
	
}
