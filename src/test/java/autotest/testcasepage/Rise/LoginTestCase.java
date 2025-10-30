package autotest.testcasepage.Rise;

import autocom.common.CommonPage;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
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
	    driver = this.startBrower(KeywordConstant.urlHD, KeywordConstant.BROWSER);
	    login = new LoginPageRise(driver);
	    add = new CreatProject_Task(driver);
	    driver.navigate().refresh();
 	}
	
	@AfterTest
	public void closeBrowser() {
	    this.closeBrowser(driver);
	    driver.quit();
	}
	
	@DataProvider(name = "loginData")
	public Object[][] loginData() {
	    return new Object[][] {
	        {"0312303803-999+1", "0312303803-999","No account found for username 0312303803-999+1"},
	        {"0312303803-999+3", "0312303803-999","No account found for username 0312303803-999+3"},
	        {"0312303803-999", "0312303803-999",""}


	    };
	}
	@Test (dataProvider = "loginData")
	public void loginsuccess(String username, String password, String expectedMessage) {
		driver.navigate().refresh();
		login.login(username,password);
		 if (!expectedMessage.isEmpty()) {
		        String actualError = login.getErrorMsg11();
		        Assert.assertEquals(actualError, expectedMessage);
		    } else {
		        String actualUsername = login.getInfr(); 
		        Assert.assertEquals(actualUsername, username);
		    }

//		pause(1);

	}
//	@Test(priority = 1, description = "Kiểm tra đăng nhập đúng")
//	public void loginsuccess(String username, String password) {
//		login.login(username,password);
//// 		String text = login.getTitle();
////		Assert.assertEquals(text, "John Doe", "Đăng nhập tài khoản không đúng");
////		pause(1);
//
//	}
//	@Test(priority = 3, description = "Kiểm tra đăng nhập sai định dạng email")
//	public void loginInvalidEmail() {
//		login.login(KeywordConstant.usernameRise+" ", KeywordConstant.passwordRise);
// 		String text = login.getValEmailMsg();
//		Assert.assertEquals(text, "Please enter a valid email address.");
//  		pause(1);
//
// 	}
//	@Test(priority = 2, description = "Kiểm tra đăng nhập sai tên")
//	public void loginWrongUser() {
//		login.login(KeywordConstant.usernameRise+"m", KeywordConstant.passwordRise);
// 		String text = login.getErrorMsg();
//		Assert.assertEquals(text, "Authentication failed!");
//  		pause(1);
//
// 	}
//	@Test(priority = 1, description = "Kiểm tra đăng nhập sai mật khẩu")
//	public void loginWrongpass() {
//		login.login(KeywordConstant.usernameRise, KeywordConstant.passwordRise+"m");
// 		String text = login.getErrorMsg();
//  		Assert.assertEquals(text,"Authentication failed!" );
//  		pause(1);
//	}
//	
}
