package autotest.testcasepage.Rise;

import autocom.common.CommonPage;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import autocom.common.CommonPage;
import autocom.constant.KeywordConstant;
import autotest.page.Rise.*;
import autotest.page.Rise.CreatProject_Task;
public class LoginTestCase extends CommonPage{
	LoginPageRise login;
	CreatProject_Task add;
	public LoginTestCase() {
		// TODO Auto-generated constructor stub
	}
	 @BeforeTest

	public void startBrowser() {
	    driver = this.startBrower(KeywordConstant.urlRise, KeywordConstant.BROWSER);
	    login = new LoginPageRise(driver);
	    add = new CreatProject_Task(driver);
	    driver.navigate().refresh();
 	}
	
	@AfterTest
	public void closeBrowser() {
	    this.closeBrowser(driver);
	    driver.quit();
	}
	@Test(priority = 4, description = "Kiểm tra đăng nhập thành công")
	public void loginsuccess() {
		login.login(KeywordConstant.usernameRise, KeywordConstant.passwordRise);
 		String text = login.getTitle();
		Assert.assertEquals(text, "John Doe", "Đăng nhập tài khoản không đúng");
 //		add.clickAddTask();
//		pause(1);

	}
	@Test(priority = 3, description = "Kiểm tra đăng nhập sai định dạng email")
	public void loginInvalidEmail() {
		login.login(KeywordConstant.usernameRise+" ", KeywordConstant.passwordRise);
 		String text = login.getValEmailMsg();
		Assert.assertEquals(text, "Please enter a valid email address.");
  		pause(1);

 	}
	@Test(priority = 2, description = "Kiểm tra đăng nhập sai tên")
	public void loginWrongUser() {
		login.login(KeywordConstant.usernameRise+"m", KeywordConstant.passwordRise);
 		String text = login.getErrorMsg();
		Assert.assertEquals(text, "Authentication failed!");
  		pause(1);

 	}
	@Test(priority = 1, description = "Kiểm tra đăng nhập sai mật khẩu")
	public void loginWrongpass() {
		login.login(KeywordConstant.usernameRise, KeywordConstant.passwordRise+"m");
 		String text = login.getErrorMsg();
  		Assert.assertEquals(text,"Authentication failed!" );
  		pause(1);
	}
	
}
