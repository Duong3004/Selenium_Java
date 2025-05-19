package autotest.testcasepage.Rise;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import autocom.common.CommonPage;
import autocom.constant.KeywordConstant;
import autotest.page.Rise.CreateProjectPage;
import autotest.page.Rise.LoginPageRise;
import autotest.page.Rise.ProjectData;

public class EditProjectTest extends CommonPage {

	public EditProjectTest() {
		// TODO Auto-generated constructor stub
	}
	WebDriver driver;
    WebDriverWait wait;
    LoginPageRise loginPage;
    CreateProjectPage createPage;
   
    @BeforeTest
    public void setup() {
        driver = this.startBrower("https://rise.fairsketch.com/projects/all_projects", KeywordConstant.BROWSER);
        wait = new WebDriverWait(driver, 10);
        loginPage = new LoginPageRise(driver);
        createPage = new CreateProjectPage(driver);
        loginPage.login(KeywordConstant.usernameRise, KeywordConstant.passwordRise);
        Assert.assertEquals(loginPage.getTitle(), "John Doe", "Đăng nhập thất bại");
    }

    @AfterTest
    public void tearDown() {
        this.closeBrowser(driver);
    }
    @Test(priority = 1, description = "Edit project thành công")
    public void testEditProjectSuccess() {
    	createPage.editProjectByExactTitle("Create task dashboards and reports");
    	ProjectData Data = new ProjectData.Builder("Create task dashboards and reports", "Client Project")
    			.withClient("Zoila Hauck")
    	        .withStatus("Hold")
    	        .build();
    	
        createPage.fillProjectForm(Data); 
        createPage.clickSave();
        
        createPage.searchProject(Data.getTitle()); 
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
            By.cssSelector("#project-table"), Data.getStatus()
        ));
        
        Map<String, String> info = createPage.getClientInfo(Data.getTitle());
        Assert.assertEquals(info.get("status"), Data.getStatus(), "Status không đúng");
        System.out.println("Edit thành công "+ info.get("title") + "và status khi edit "+ info.get("status"));
    	
    }
    
// vẻifi từng trường 
    
}
