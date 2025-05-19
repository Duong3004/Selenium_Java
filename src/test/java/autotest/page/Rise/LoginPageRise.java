package autotest.page.Rise;

import autocom.common.CommonPage;
import autocom.constant.KeywordConstant;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginPageRise extends CommonPage {
	WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;
	public LoginPageRise(WebDriver driver) {
		this.driver = driver;
        this.wait = new WebDriverWait(driver,5);  
        PageFactory.initElements(driver, this);	
        this.js = (JavascriptExecutor) driver;

        }
    @FindBy(id = "email")
    WebElement txtUserName;

    @FindBy(id = "password")
    WebElement txtPassword;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement btnLogin;

    @FindBy(xpath = "//div[contains(@class, 'alert alert-danger')]")
    WebElement msgError;
    
    @FindBy(xpath = "//a[@id='user-dropdown']//span[2]")
    WebElement msgInfor;
    
    @FindBy(xpath = "//div[contains(@class, 'form-group has-error')]")
    WebElement ValEmailMsg;
    
    public void login(String username, String password) {
    	 waitt(txtUserName).clear();
    	 waitt(txtUserName).sendKeys(username);
    	 pause(1);
    	 waitt(txtPassword).clear();
    	 waitt(txtPassword).sendKeys(password);
    	 pause(2);
    	 waitt(btnLogin).click();
  	   

    }
    public String getTitle() {
 		return msgInfor.getText();
 	}
    public String getErrorMsg() {
        try {
            return  waitt(msgError).getText().trim();
        } catch (Exception e) {
        	System.err.println("Không thể lấy thông tin lỗi: " + e.getMessage());
            return "";  
        }
    }
    public String getValEmailMsg() {
        try {
            return  waitt(ValEmailMsg).getText().trim();
        } catch (Exception e) {
        	System.err.println("Không thể lấy thông tin lỗi: " + e.getMessage());
            return "";  
        }
    }
    public WebElement waitt(WebElement element) {
         return wait.until(ExpectedConditions.visibilityOf(element));

    }
    
}
	
