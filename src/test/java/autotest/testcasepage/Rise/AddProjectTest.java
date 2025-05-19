package autotest.testcasepage.Rise;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import autocom.common.CommonPage;
import autocom.constant.KeywordConstant;
import autotest.page.Rise.CreatProject_Task;
import autotest.page.Rise.LoginPageRise;

public class AddProjectTest extends CommonPage {
	CreatProject_Task add;
	WebDriver driver;
    WebDriverWait wait;
	LoginPageRise login;
	
	public AddProjectTest(){
		
	}
	@BeforeTest

	public void startBrowser() {
		driver = this.startBrower("https://rise.fairsketch.com/projects/all_projects", KeywordConstant.BROWSER);
	    login = new LoginPageRise(driver);
	    add = new CreatProject_Task(driver);
        this.wait = new WebDriverWait(driver,10);  
	    login.login(KeywordConstant.usernameRise, KeywordConstant.passwordRise);
 		String text = login.getTitle();
		Assert.assertEquals(text, "John Doe", "Đăng nhập tài khoản không đúng");
		PageFactory.initElements(driver, this);

  	}
	@AfterTest
	public void closeBrowser() {
	    this.closeBrowser(driver);
 
	}
	@FindBy(xpath = "//a[@title = 'Add project']") WebElement MenuProject; 
	@FindBy(xpath = "//button[@class='btn btn-primary']") WebElement Save; 
	@FindBy(xpath = "//button[@class='btn btn-default']") WebElement Close; 

	@FindBy(xpath = "//span[@id='title-error']") WebElement MesErr; 
	@FindBy(xpath = "//div[@class='app-alert-message']") WebElement MesWar; 

 
	@Test(priority = 1, description = "Kiểm tra tạo project thành công")
	public void addProjectSucsess() {
        wait.until(ExpectedConditions.visibilityOf(MenuProject)).click();
 		add.writeInput("Title","Create task dashboards and reports");
		add.selectFromDropdown("s2id_project-type-dropdown", "Client Project");  	
		add.selectFromSelect2Dropdown("Client", "Zoila Hauck");  	
		add.writeDescription("Quis quisquam cumque quia aut nesciunt quia. Omnis omnis rerum nulla ut quisquam sit. Qui est necessitatibus sit eius omnis.");
		add.writeInput("Start date","25-05-2025");
		
	    driver.findElement(By.tagName("body")).click();
		add.writeInput("Deadline","30-05-2025");
	    driver.findElement(By.tagName("body")).click();
 		add.writeInput("Price","1000");
		add.selectFromDropdowns("s2id_project_labels", "Urgent");
 	    Save.click();
 	    WebElement searchInput = driver.findElement(By.cssSelector("#project-table_filter input"));
 	    searchInput.sendKeys("creat");
 	    searchInput.sendKeys(Keys.ENTER);
 	    pause(2);

	 	 Map<String, String> info = getClientInfo(driver,"Create task dashboards and reports");
	 	if (info == null) {
	 	    Assert.fail("Không tìm thấy task có tiêu đề: Create task dashboards and reports");
	 	} else {
	 	    Assert.assertEquals(info.get("title"), "Create task dashboards and reports", "Tên không đúng");
	 	}
	     System.out.println("Đã tìm thấy project và có ID là: "+info.get("id"));
	     pause(2);

	}
	@Test(priority = 2, description = "Kiểm tra tạo project không thành công khi không có titlle")
	public void addProjectError1() {
        wait.until(ExpectedConditions.visibilityOf(MenuProject)).click();
		add.selectFromDropdown("s2id_project-type-dropdown", "Client Project");  	
		add.selectFromSelect2Dropdown("Client", "Zoila Hauck");  	
		add.writeDescription("Quis quisquam cumque quia aut nesciunt quia. Omnis omnis rerum nulla ut quisquam sit. Qui est necessitatibus sit eius omnis.");
		add.writeInput("Start date","25-05-2025");	
		driver.findElement(By.id("start_date")).sendKeys(Keys.ENTER);
		add.writeInput("Deadline","30-05-2025");
		driver.findElement(By.id("deadline")).sendKeys(Keys.ENTER);
 		add.writeInput("Price","1000");
		add.selectFromDropdowns("s2id_project_labels", "Urgent");
 	    Save.click();
 	    String expectedText =  wait.until(ExpectedConditions.visibilityOf(MesErr)).getText().trim();
		System.out.println("Thông báo tìm thấy: "+ expectedText);
		Assert.assertEquals(expectedText, "This field is required.");
 	    Close.click();
 	   
	}
	@Test(priority = 3, description = "Kiểm tra tạo project không thành công với titlle trống")
	public void addProjectError2() {
        wait.until(ExpectedConditions.visibilityOf(MenuProject)).click();
 		add.writeInput("Title"," ");
		add.selectFromDropdown("s2id_project-type-dropdown", "Client Project");  	
		add.selectFromSelect2Dropdown("Client", "Zoila Hauck");  	
		add.writeDescription("Quis quisquam cumque quia aut nesciunt quia. Omnis omnis rerum nulla ut quisquam sit. Qui est necessitatibus sit eius omnis.");
		add.writeInput("Start date","25-05-2025");	
		driver.findElement(By.id("start_date")).sendKeys(Keys.ENTER);
		add.writeInput("Deadline","30-05-2025");
		driver.findElement(By.id("deadline")).sendKeys(Keys.ENTER);
 		add.writeInput("Price","1000");
		add.selectFromDropdowns("s2id_project_labels", "Urgent");
 	    Save.click();
 	    String expectedText = "\"" + wait.until(ExpectedConditions.visibilityOf(MesWar)).getText().trim()+ "\"";
		System.out.println("Thông báo tìm thấy: "+ expectedText);
		Assert.assertEquals(expectedText, "Oops, something went wrong!");
 	    Close.click();
 	   
	}
	
 	    



	
	public Map<String, String> getClientInfo(WebDriver driver, String title) {
	    Map<String, String> info = new HashMap<>();
	    WebDriverWait wait = new WebDriverWait(driver,10);

	    try {
 	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table//tbody")));

 	        List<WebElement> rows = driver.findElements(By.xpath("//tbody//tr"));

	        for (WebElement row : rows) {
	            List<WebElement> cells = row.findElements(By.tagName("td"));
	            if (cells.size() >= 8) {
	                String taskTitle = "";
	                try {
	                    taskTitle = cells.get(1).findElement(By.tagName("a")).getText().trim();
	                } catch (Exception ignored) {}

	                if (title.equals(taskTitle)) {
	                    info.put("id", cells.get(0).getText().trim());
	                    info.put("title", taskTitle);
	                    info.put("client", cells.get(2).getText().trim());
	                    info.put("price", cells.get(3).getText().trim());
	                    info.put("startDate", cells.get(4).getText().trim());
	                    info.put("dealine", cells.get(5).getText().trim());
	                    info.put("progress", cells.get(6).getText().trim());
 	                    info.put("status", cells.get(7).getText().trim());
	                    return info; 
	                }
	            }
	        }
	    } catch (Exception e) {
	        System.err.println("Lỗi khi tìm client info:");
	        e.printStackTrace();
	    }

	    return null; 
	}
}
