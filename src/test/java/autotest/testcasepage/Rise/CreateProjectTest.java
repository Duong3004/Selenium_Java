package autotest.testcasepage.Rise;

import autocom.common.CommonPage;
import java.util.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import autocom.constant.KeywordConstant;
import autocom.data.TestData;
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
//	        Assert.assertEquals(loginPage.getTitle(), "John Doe", "Đăng nhập thất bại");
//	        
	        Cookie ciSession = new Cookie("ci_session", "9fc88f1125b3c5c5c9f316659a4ddc32");
	        Cookie csrfToken = new Cookie("rise_csrf_cookie", "acfc5e281bfbde07cf2f5a659ece4b3b");

	        driver.manage().addCookie(ciSession);
	        driver.manage().addCookie(csrfToken);
	        
	        driver.navigate().to("https://rise.fairsketch.com/projects/all_projects");
////	        driver.navigate().refresh();
	    }

	    @AfterClass
	    public void tearDown() {
	        this.closeBrowser(driver);
	    }
	    private static final String PROJECT_TITLE = "Create task dashboards and reports";
	    private static final String REQUIRED_FIELD_MSG = "This field is required.";
	    private static final String ERROR_MSG = "Oops, something went wrong!";
	    private static final String WARRING_BIRTHDAY = "Deadline must be equal or greater than Start date.";

	    @Test(priority = 1, description = "Tạo project thành công")
	    public void testCreateProjectSuccess() {
	        createPage.openProjectForm();
	        createPage.fillProjectForm(TestData.VALID_PROJECT_DATA);
	        createPage.clickSave();
	       
	        createPage.searchProject("Create task");
	        Map<String, String> info = createPage.getClientInfo(PROJECT_TITLE);
	        Assert.assertNotNull(info, "Không tìm thấy project đã tạo");
	        Assert.assertEquals(info.get("title"), PROJECT_TITLE, "Tên project không khớp");
	        System.out.println("Đã tìm thấy project với ID: " + info.get("id"));
	    }

	    @Test(priority = 2, description = "Kiểm tra tạo project không thành công khi không có titlle")
		public void addProjectError1() {
			
	        createPage.openProjectForm();
	        createPage.fillProjectForm(TestData.EMPTY_TITLE_DATA); 
	        createPage.clickSave();
	        Assert.assertEquals(createPage.getTitleError(), REQUIRED_FIELD_MSG);
	        createPage.clickClose();
		}

	    @Test(priority = 3, description = "Hiển thị cảnh báo khi deadline nhỏ hơn start date")
	    public void testCreateProjectBlankTitle() {
	        createPage.openProjectForm();
	        createPage.fillProjectForm(TestData.DEADLINE_BEFORE_START_DATE); 
	        createPage.clickSave();

	        String warning = createPage.getWarningIfPresent();
	        Assert.assertTrue(warning.contains(WARRING_BIRTHDAY), "Thông báo không như mong đợi");
	        createPage.clickClose();
	    }
	    @Test(priority = 4, description = "Không cho phép tiêu đề chỉ chứa khoảng trắng")
	    public void testInvalidProjectTime() {
	        createPage.openProjectForm();
	        createPage.fillProjectForm(TestData.EMPTY_TITLE_DATA); 
	        createPage.clickSave();
	        String warning = createPage.getWarningMessage();
	        Assert.assertTrue(warning.contains(ERROR_MSG), "Thông báo không như mong đợi");
	        createPage.clickClose();
	    }

}
