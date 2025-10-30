package autotest.testcasepage.Rise;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
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
import autocom.data.TestData;
import autotest.page.Rise.CreatProject_Task;
import autotest.page.Rise.LoginPageRise;
import autotest.pages.ProjectListPage;

public class AddProjectTest extends CommonPage {
	CreatProject_Task add;
	WebDriver driver;
    WebDriverWait wait;
	LoginPageRise login;
	ProjectListPage list;
	public AddProjectTest(){
		
	}
	@BeforeTest

	public void startBrowser() {
		driver = this.startBrower(KeywordConstant.urlRise, KeywordConstant.BROWSER);
	    login = new LoginPageRise(driver);
	    add = new CreatProject_Task(driver);
	    list = new ProjectListPage(driver);
        this.wait = new WebDriverWait(driver,10);  
	    login.login(KeywordConstant.usernameRise, KeywordConstant.passwordRise);
		PageFactory.initElements(driver, this);

  	}
	@AfterTest
	public void closeBrowser() {
	    this.closeBrowser(driver);
 
	}
	private static final String PROJECT_TITLE = "Create task dashboards and reports";
    private static final String REQUIRED_FIELD_MSG = "This field is required.";
    private static final String ERROR_MSG = "Oops, something went wrong!";
 
	@Test(priority = 1, description = "Kiểm tra tạo project thành công")
	public void addProjectSucsess() {
		
		add.openAddProjectForm();
		add.fillProjectForm(TestData.VALID_PROJECT_DATA);
	    add.clickSave();
		 
		 Map<String, String> info = list.getClientInfo(PROJECT_TITLE);
		    Assert.assertNotNull(info, "Không tìm thấy project: " + PROJECT_TITLE);
		    Assert.assertEquals(info.get("title"), PROJECT_TITLE, "Tên project không đúng");
		    System.out.println("Đã tìm thấy project và có ID là: " + info.get("id"));
		    pause(2);

	}

	@Test(priority = 2, description = "Kiểm tra tạo project không thành công khi không có titlle")
	public void addProjectError1() {
		
		add.openAddProjectForm();
	    add.fillProjectForm(TestData.EMPTY_TITLE_DATA); 
	    add.clickSave();
        Assert.assertEquals(add.getErrorMessage(), REQUIRED_FIELD_MSG);
	    add.clickClose();
	}
	@Test(priority = 3, description = "Kiểm tra tạo project không thành công với titlle là khoảng trắng")
	public void addProjectError2() {
		
		add.openAddProjectForm();
	    add.fillProjectForm(TestData.BLANK_TITLE_DATA);  
	    add.clickSave();
        Assert.assertEquals(add.getWarningMessage(), ERROR_MSG);
	    add.clickClose();
 	   
	}
	
 	    
}
