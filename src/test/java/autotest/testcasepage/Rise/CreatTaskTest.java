package autotest.testcasepage.Rise;

import autocom.common.CommonPage;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import autocom.common.CommonPage;
import autocom.constant.KeywordConstant;
import autotest.page.Rise.*;
import autotest.pages.ProjectListPage;
public class CreatTaskTest extends CommonPage{
	LoginPageRise login;
	CreatProject_Task add;
	JavaTest jss;
	WebDriverWait wait;
	ProjectListPage projectList;
	public CreatTaskTest() {
		// TODO Auto-generated constructor stub
	}
	 @BeforeTest

	public void startBrowser() {
		driver = this.startBrower(KeywordConstant.urlRise, KeywordConstant.BROWSER);
	    login = new LoginPageRise(driver);
	    add = new CreatProject_Task(driver);
        this.wait = new WebDriverWait(driver,10);  
        this.projectList = new ProjectListPage(driver);

	    jss = new JavaTest();
	    jss.driver = driver;
	    login.login(KeywordConstant.usernameRise, KeywordConstant.passwordRise);
 		String text = login.getTitle();
		Assert.assertEquals(text, "John Doe", "Đăng nhập tài khoản không đúng");
  	}
	
	@AfterTest
	public void closeBrowser() {
	    this.closeBrowser(driver);
 
	}
	
	private static final String PROJECT_TITLE = "Tạo một task";
    private static final String WARRING_BIRTHDAY = "Deadline must be equal or greater than Start date.";

	@Test(priority = 1, description = "Kiểm tra tạo task cảnh báo thời gian")
	public void addtaskError() {	
		clickByDataTitle("Add task");

		add.writeInput("Title",PROJECT_TITLE);
		add.writeDescription("Tạo một task và test xem có thành công không");
		pause(1);

		add.selectFromDropdown("s2id_task-context", "Client"); 
		pause(1);
		add.selectFromDropdown("s2id_client_id", "Demo Client");
		add.selectFromSelect2Dropdown("Points", "4 Points");
		add.selectFromDropdown("s2id_assigned_to", "John Doe");
 		add.selectFromDropdowns("s2id_collaborators", "Mark Thomas");
 		add.selectFromDropdowns("s2id_collaborators", "Michael Wood");//lựa chọn lặp
		add.selectFromDropdown("s2id_task_status_id	", "To do");
		add.selectFromDropdown("s2id_priority_id", "Major");
		add.selectFromDropdowns("s2id_project_labels", "Bug"); //lựa chọn lặp
		add.writeInput("Start date","30-04-2025");
		driver.findElement(By.id("start_date")).sendKeys(Keys.ENTER);
	    add.writeInput("Deadline","25-04-2025");
		driver.findElement(By.id("deadline")).sendKeys(Keys.ENTER);
	    driver.findElement(By.xpath("//button[contains(text(),'Save')]")).click(); 

		String expectedText = add.getValEmailMsg();
		System.out.println("Thông báo tìm thấy: "+ expectedText);
		Assert.assertEquals(expectedText, WARRING_BIRTHDAY);
		add.clickClose();
	}
	@Test(priority = 2, description = "Kiểm tra tạo task thành công")
	public void addtaskSucsess() {

		clickByDataTitle("Add task");

 		pause(1);
		jss.writeInputPureJS("Title","Create task dashboards and reports");
		add.writeDescription("Quis quisquam cumque quia aut nesciunt quia. Omnis omnis rerum nulla ut quisquam sit. Qui est necessitatibus sit eius omnis.");
		add.selectFromDropdown("s2id_task-context", "Project"); 
		pause(1);
		add.selectFromDropdown("s2id_project_id", "Video Animation and Editing");
		add.selectFromSelect2Dropdown("Points", "4 Points");
		add.selectFromDropdown("s2id_assigned_to", "John Doe");
  		add.selectFromDropdowns("s2id_collaborators", "Michael Wood");
  		add.selectFromDropdown("s2id_task_status_id", "In progress");
		add.selectFromDropdown("s2id_priority_id", "Major");
		add.selectFromDropdowns("s2id_project_labels", "Bug"); 
		add.writeInput("Start date","25-04-2025");
		driver.findElement(By.id("start_date")).sendKeys(Keys.ENTER);
	    add.writeInput("Deadline","12-05-2025");
		driver.findElement(By.id("deadline")).sendKeys(Keys.ENTER);
		WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
		    By.xpath("//button[@class='btn btn-primary']")));
		submitButton.click();
		pause(2);
		driver.navigate().refresh();
		Map<String, String> info = projectList.getClientInfo(PROJECT_TITLE);
	 	if (info == null) {
	 	    Assert.fail("Không tìm thấy task có tiêu đề: " + PROJECT_TITLE);
	 	} else {
	 	    Assert.assertEquals(info.get("title"), PROJECT_TITLE, "Tên không đúng");
	 	}

	     System.out.println("Đã tìm thấy khách hàng và có ID là: "+info.get("id"));

	}
	
}