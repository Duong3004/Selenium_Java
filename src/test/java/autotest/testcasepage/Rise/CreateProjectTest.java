package autotest.testcasepage.Rise;

import autocom.common.CommonPage;
import java.util.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import autocom.constant.KeywordConstant;
import autotest.page.Rise.*;

public class CreateProjectTest extends CommonPage {
	    WebDriver driver;
	    WebDriverWait wait;
	    LoginPageRise loginPage;
	    CreateProjectPage createPage;
	    public CreateProjectTest() {
	    	
	    }
	    @BeforeClass
	    public void setup() {
	        driver = this.startBrower("https://rise.fairsketch.com/projects/all_projects", KeywordConstant.BROWSER);
	        wait = new WebDriverWait(driver, 10);
	        loginPage = new LoginPageRise(driver);
	        createPage = new CreateProjectPage(driver);
	        loginPage.login(KeywordConstant.usernameRise, KeywordConstant.passwordRise);
	        Assert.assertEquals(loginPage.getTitle(), "John Doe", "Đăng nhập thất bại");
	    }

	    @AfterClass
	    public void tearDown() {
	        this.closeBrowser(driver);
	    }
	    @Test(priority = 1, description = "Tạo project thành công")
	    public void testCreateProjectSuccess() {
	    	ProjectData Data1 = new ProjectData.Builder("Create task dashboards and reports", "Client Project")
	    	        .withClient("Zoila Hauck")
	    	        .withDescription("Quis quisquam cumque quia aut nesciunt quia...")
	    	        .withStartDate("25-05-2025")
	    	        .withDeadline("30-05-2025")
	    	        .withPrice("1000")
	    	        .withLabel("Urgent")
	    	        .build();

	        createPage.openProjectForm();
	        createPage.fillProjectForm(Data1);
	        createPage.clickSave();
	        
	        createPage.searchProject("Create task");
	        
	        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("#project-table"), Data1.getTitle()));
	        Map<String, String> info = createPage.getClientInfo(Data1.getTitle());
	        Assert.assertNotNull(info, "Không tìm thấy project đã tạo");
	        Assert.assertEquals(info.get("title"), Data1.getTitle(), "Tên project không khớp");
	        System.out.println("Đã tìm thấy project với ID: " + info.get("id"));
	    }

	    @Test(priority = 2, description = "Không cho tạo project nếu thiếu tiêu đề")
	    public void testCreateProjectMissingTitle() {
	    	ProjectData Data = new ProjectData.Builder("", "Internal Project")
	    	        .withDescription("Quis quisquam cumque quia aut nesciunt quia...")
	    	        .withStartDate("25-05-2025")
	    	        .withDeadline("30-05-2025")
	    	        .withPrice("1000")
	    	        .withLabel("Urgent")
	    	        .build();

	        createPage.openProjectForm();
	        createPage.fillProjectForm(Data); 
	        createPage.clickSave();

	        String error = createPage.getTitleError();
	        Assert.assertEquals(error, "This field is required.");
	        createPage.clickClose();
	    }

	    @Test(priority = 3, description = "Hiển thị cảnh báo khi deadline nhỏ hơn start date")
	    public void testCreateProjectBlankTitle() {
	    	ProjectData Data = new ProjectData.Builder("Create task dashboards and reports", "Internal Project")
	    	        .withDescription("Quis quisquam cumque quia aut nesciunt quia...")
	    	        .withStartDate("25-06-2025")
	    	        .withDeadline("30-05-2025")
	    	        .withPrice("1000")
	    	        .withLabel("Urgent")
	    	        .build();
	        createPage.openProjectForm();
	        createPage.fillProjectForm(Data); 
	        createPage.clickSave();

	        String warning = createPage.getWarningIfPresent();
	        Assert.assertTrue(warning.contains("Deadline must be equal or greater than Start date."), "Thông báo không như mong đợi");
	        createPage.clickClose();
	    }
	    @Test(priority = 4, description = "Không cho phép tiêu đề chỉ chứa khoảng trắng")
	    public void testInvalidProjectTime() {
	    	ProjectData Data = new ProjectData.Builder(" ", "Internal Project")
	    	        .withDescription("Quis quisquam cumque quia aut nesciunt quia...")
	    	        .withStartDate("25-05-2025")
	    	        .withDeadline("30-05-2025")
	    	        .withPrice("1000")
	    	        .withLabel("Urgent")
	    	        .build();
	        createPage.openProjectForm();
	        createPage.fillProjectForm(Data); 
	        createPage.clickSave();

	        String warning = createPage.getWarningMessage();
	        Assert.assertTrue(warning.contains("Oops, something went wrong"), "Thông báo không như mong đợi");
	        createPage.clickClose();
	    }

}
