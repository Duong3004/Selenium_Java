package autotest.thuchanh;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import autocom.common.CommonPage;
import autocom.constant.KeywordConstant;

public class Buoi17TH extends CommonPage {
    WebDriverWait wait;

	public Buoi17TH() {
	}
//	@Test(priority=1)
	public void login1() {
	driver.findElement(By.xpath("//button[@class='btn btn-danger']")).click();
	String alerTexxt = driver.switchTo().alert().getText();
	System.out.println(alerTexxt);
    Assert.assertEquals(alerTexxt, "I am an alert box!");
    driver.switchTo().alert().accept();

 	}
//	@Test(priority=2)
	public void login2() {
	driver.findElement(By.xpath("//a[text()='Alert with OK & Cancel ']")).click();
	driver.findElement(By.xpath("//button[@class='btn btn-primary']")).click();
	pause(1);

	String alerTexxt = driver.switchTo().alert().getText();
	System.out.println(alerTexxt);
	
     Assert.assertEquals(alerTexxt, "Press a Button !");
     driver.switchTo().alert().accept();

 	}
//	@Test(priority=3)
	public void login3() {
	driver.findElement(By.xpath("//a[text()='Alert with Textbox ']")).click();
	driver.findElement(By.xpath("//button[@class='btn btn-info']")).click();
	String alerTexxt = driver.switchTo().alert().getText();
	System.out.println(alerTexxt);
    Assert.assertEquals(alerTexxt, "Please enter your name");
 	}
	@BeforeTest
	@Parameters("browser")
	public void startBrowser(@Optional("edge") String browser) {
//		driver = this.startBrower("https://demo.guru99.com/test/delete_customer.php", KeywordConstant.BROWSER);
//		driver = this.startBrower("https://demo.automationtesting.in/Alerts.html", KeywordConstant.BROWSER);
		driver = this.startBrower("https://demo.guru99.com/popup.php", browser);

	}
	@AfterTest
	public void closeBrowser() {
	    this.closeBrowser(driver);
 
	}
	@Test
	public void getWWindowHandles() {
 	    driver.findElement(By.linkText("Click Here")).click();
	    
 	    String parentWindow = driver.getWindowHandle();
	    
 	    Set<String> allWindows = driver.getWindowHandles();
	    
 	    for (String windowHandle : allWindows) {
 	        if (!windowHandle.equals(parentWindow)) {
	            driver.switchTo().window(windowHandle);
	            
 	            WebDriverWait wait = new WebDriverWait(driver, 10);
	            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='emailid']")));
	            
 	            emailField.sendKeys("test@example.com");
	            
 	            driver.findElement(By.name("btnLogin")).click();
	            
 	            WebElement heading = driver.findElement(By.xpath("//td[@colspan='2']"));
	            String headingText = heading.getText();
	            System.out.println("Heading text: " + headingText);
	            
 	            Assert.assertTrue(headingText.contains("Access details"), "Heading không chứa text mong đợi!");
	            
 	            driver.close();
	            
 	            driver.switchTo().window(parentWindow);
	        }
	    }
	}

}
	
 