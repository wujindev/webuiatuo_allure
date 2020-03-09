package pageobjects;

import org.openqa.selenium.WebDriver;

import common.BaseAction;
import utils.ObjectMap;
import utils.WaitUtil;

public class LoginPage {
	WebDriver driver;
	
    public   LoginPage(WebDriver driver) {
    	this.driver=driver;
    	
    }
    
//    public void login(String url,String username,String password) {
//    	BaseAction.openUrl(url);
//    	WaitUtil.sleep(1);
//    	BaseAction.findElement("login.usernameinput").clear();
//    	BaseAction.input("login.usernameinput", username);
//    	BaseAction.findElement("login.passwordinput").clear();
//    	BaseAction.input("login.passwordinput", password);
//    	BaseAction.click("login.loginbtn");
//    	WaitUtil.sleep(2);
//    }
    
    public void inputUsername(String username) {
    	BaseAction.findElement("login.usernameinput").clear();
    	BaseAction.input("login.usernameinput", username);
    }
    public void inputPassword(String password) {
    	BaseAction.findElement("login.passwordinput").clear();
    	BaseAction.input("login.passwordinput", password);
    }
    public void clickLoginbtn() {
    	BaseAction.click("login.loginbtn");
    }
    public void login(String url,String username,String password) {
    	WaitUtil.sleep(1);
    	BaseAction.openUrl(url);
    	WaitUtil.sleep(1);
    	inputUsername(username);
    	inputPassword(password);
    	clickLoginbtn();
    	WaitUtil.sleep(2);
    }
}
