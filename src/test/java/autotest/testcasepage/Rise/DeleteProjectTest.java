package autotest.testcasepage.Rise;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.helpers.Reporter;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import autocom.common.CommonPage;
import autocom.constant.KeywordConstant;
import autotest.page.Rise.CreateProjectPage;
import autotest.page.Rise.LoginPageRise;
import autotest.page.Rise.ProjectData;

public class DeleteProjectTest extends CommonPage {

	public DeleteProjectTest() {
		// TODO Auto-generated constructor stub
	}
	WebDriver driver;
    WebDriverWait wait;
    LoginPageRise loginPage;
    CreateProjectPage createPage;
   
    @BeforeTest
    public void setup() {
    	driver = this.startBrower("https://rise.fairsketch.com", KeywordConstant.BROWSER);
        wait = new WebDriverWait(driver, 10);
        loginPage = new LoginPageRise(driver);
        createPage = new CreateProjectPage(driver);
//        loginPage.login(KeywordConstant.usernameRise, KeywordConstant.passwordRise);
//        Assert.assertEquals(loginPage.getTitle(), "John Doe", "Đăng nhập thất bại");
        
        Cookie ciSession = new Cookie("ci_session", "81ac440162b8f9fc72186b9ca9665911");
        Cookie csrfToken = new Cookie("rise_csrf_cookie", "cca74465329e7549dc0600276295008c");

        driver.manage().addCookie(ciSession);
        driver.manage().addCookie(csrfToken);
        
        driver.navigate().to("https://rise.fairsketch.com/projects/all_projects");
//        driver.navigate().refresh();
    }

    @AfterTest
    public void tearDown() {
        this.closeBrowser(driver);
    }
    
    @Test(priority = 1, description = "Hủy xoá project, project vẫn còn trong danh sách")
    public void testCancelDeleteProject() {
        final String Project_name = "Product Packaging Design";
    	createPage.deleteProjectByExactTitle(Project_name);
    	
    	String alerTexxt = driver.findElement(By.xpath("//div[@class='modal-body']//div")).getText();
        Assert.assertEquals(alerTexxt, "Are you sure? You won't be able to undo this action!");

    	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='modal-footer clearfix']//button[@class='btn btn-default']"))).click();
    	
    	createPage.searchProject(Project_name); 
        boolean isStillVisible = createPage.isProjectExist(Project_name);

    	Assert.assertTrue(isStillVisible , "Project không còn khi nhấn cancel");
    	
    }
    @Test(priority = 2, description = "Xoá project thành công và kiểm tra không còn hiển thị")
    public void testDeleteProjectSuccess() {
        final String Project_name = "Create task dashboards and reports";
    	createPage.deleteProjectByExactTitle(Project_name);
    	
    	String alerTexxt = driver.findElement(By.xpath("//div[@class='modal-body']//div")).getText();
        Assert.assertEquals(alerTexxt, "Are you sure? You won't be able to undo this action!");

    	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='confirmDeleteButton']"))).click();
    	
    	String deleteSuccessText = createPage.getDeleteSuccessMessage();
    	Assert.assertEquals(deleteSuccessText, "The record has been deleted.","Thông báo không đúng");
    	
    	createPage.searchProject(Project_name); 
        boolean isStillVisible = createPage.isProjectExist(Project_name);

    	Assert.assertFalse(isStillVisible , "Project vẫn còn xuất hiện sau khi xoá");
    	System.out.println("Xoá thành công " + Project_name);
    	
    }
   
    

}
