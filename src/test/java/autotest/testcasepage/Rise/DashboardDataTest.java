package autotest.testcasepage.Rise;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.openqa.selenium.Cookie; 
import autocom.common.CommonPage;
import autocom.constant.KeywordConstant;
import autotest.page.Rise.*;

public class DashboardDataTest extends CommonPage {

	public DashboardDataTest() {
		// TODO Auto-generated constructor stub
	}
	WebDriver driver;
    WebDriverWait wait;
    LoginPageRise loginPage;
	DashboardCalculator dsk;
	DashboardPage ds;
    @BeforeTest
    public void setup() {
        driver = this.startBrower("https://rise.fairsketch.com", KeywordConstant.BROWSER);
        wait = new WebDriverWait(driver, 10);
        loginPage = new LoginPageRise(driver);
        dsk = new DashboardCalculator(driver);
        ds = new DashboardPage(driver);
//
//        loginPage.login(KeywordConstant.usernameRise, KeywordConstant.passwordRise);
//        Assert.assertEquals(loginPage.getTitle(), "John Doe", "Đăng nhập thất bại");
        
        Cookie ciSession = new Cookie("ci_session", "e6be49fa848967c605c937f19511c742");
        Cookie csrfToken = new Cookie("rise_csrf_cookie", "8fdb015b1bd7ec620a3b2ff74e4aaff1");

        driver.manage().addCookie(ciSession);
        driver.manage().addCookie(csrfToken);
        
        driver.navigate().to("https://rise.fairsketch.com/index.php/dashboard");


    }
    
    @AfterTest
    public void tearDown() {
        this.closeBrowser(driver);
    }
//   @Test(priority = 1, description = "Kiểm tra tổng task ở Dashbaoard")
    public void testMyOpenTasks() {
    	int dashboardTaskCount = ds.getTotalTasksFromDashboard();
    	dsk.goToMyOpenTasks();
    	int actualCount = dsk.getTasksFromTask_1();
        Assert.assertEquals(actualCount, dashboardTaskCount, "Số lượng task không khớp giữa Dashboard và danh sách");
    	driver.navigate().back();

         	
    }
//   @Test(priority = 2, description = "Kiểm tra tổng Due ở Dashbaoard")
    public void testDue() {
    	double dashboardDue = ds.getTotalDueFromDashboard();
    	dsk.goToDue();
    	double actualCount = dsk.calculateActualDue();
    	driver.navigate().back();
        Assert.assertEquals(actualCount, dashboardDue, "Tổng Due không khớp giữa Dashboard và danh sách");

         	
    }
    
//   @Test(priority = 3, description = "Kiểm tra thành phần ở Projects Overview")
    public void testProjects_Overview() {
    	double openDs = ds.getTotalOpenFromDashboard();
    	double completedDs = ds.getTotalCompletedFromDashboard();
    	double holdDs = ds.getTotalHoldFromDashboard();

    	dsk.goToOpenPro();;
    	double open_actualCount = dsk.getProject();
    	driver.navigate().back();
    	
    	dsk.goToCompletedPro();
    	double compeleted_actualCount = dsk.getProject();
    	driver.navigate().back();
    	
    	dsk.goToHoldPro();
    	double hold_actualCount = dsk.getProject();
    	driver.navigate().back();

        Assert.assertEquals(openDs, open_actualCount, " Open Project không khớp giữa Dashboard và danh sách");
        Assert.assertEquals(completedDs, compeleted_actualCount, "Completed Project không khớp giữa Dashboard và danh sách");
        Assert.assertEquals(holdDs, hold_actualCount, "Hold Project không khớp giữa Dashboard và danh sách");   	
    }
    
    @Test(priority = 4, description = "Kiểm tra thành phần ở Invoice Overview")
    public void testInvoice_Overview() {
        SoftAssert softAssert = new SoftAssert();

        double overdueDs = ds.getTotalOverDueFromDashboard();
        double notPaidDs = ds.getTotalNotpaidFromDashboard();
        double partially_paidDs = ds.getTotalPartiallypaidFromDashboard();
        double fully_paidDs = ds.getTotalFullypaidFromDashboard();
        double draftDs = ds.getTotalHoldFromDashboard();

        dsk.goToOverdueInv();
        double over_actualCount = dsk.calculateActualDues();
        driver.navigate().back();

        dsk.goToNotpaidInv();
        double notpaid_actualCount = dsk.calculateActualDues();
        driver.navigate().back();

        dsk.goToPartiallypaidInv();
        double partiallypaid_actualCount = dsk.calculateActualDues();
        driver.navigate().back();

        dsk.goToFullypaidInv();
        double fullypaid_actualCount = dsk.calculateActualDues();
        driver.navigate().back();

        dsk.goToDraftInv();
        double draft_actualCount = dsk.calculateActualDues();
        driver.navigate().back();

        softAssert.assertEquals(overdueDs, over_actualCount, "overdueDs không khớp giữa Dashboard và danh sách");
        softAssert.assertEquals(notPaidDs, notpaid_actualCount, "notPaidDs không khớp giữa Dashboard và danh sách"); 
        softAssert.assertEquals(partially_paidDs, partiallypaid_actualCount, "partially_paidDs không khớp giữa Dashboard và danh sách");
        softAssert.assertEquals(fully_paidDs, fullypaid_actualCount, "fully_paidDs không khớp giữa Dashboard và danh sách");  
        softAssert.assertEquals(draftDs, draft_actualCount, "draftDs không khớp giữa Dashboard và danh sách");

        softAssert.assertAll();
    }
    
//    @Test(priority = 5, description = "Kiểm tra thành phần ở All Tasks Overview")
    public void testAllTasks_Overview() {
        SoftAssert softAssert = new SoftAssert();

        double todoDs = ds.getTotalToDoFromDashboard();
        double inprogressDs = ds.getTotalInProgressFromDashboard();
        double reviewDs = ds.getTotalReviewFromDashboard();
        double doneDs = ds.getTotalDoneFromDashboard();
        double expriedDs = ds.getTotalExpriedFromDashboard();
///
        dsk.goToOverdueInv();
        double over_actualCount = dsk.calculateActualDues();
        driver.navigate().back();

        dsk.goToNotpaidInv();
        double notpaid_actualCount = dsk.calculateActualDues();
        driver.navigate().back();

        dsk.goToPartiallypaidInv();
        double partiallypaid_actualCount = dsk.calculateActualDues();
        driver.navigate().back();

        dsk.goToFullypaidInv();
        double fullypaid_actualCount = dsk.calculateActualDues();
        driver.navigate().back();

        dsk.goToDraftInv();
        double draft_actualCount = dsk.calculateActualDues();
        driver.navigate().back();
        
//        softAssert.assertEquals(overdueDs, over_actualCount, "overdueDs không khớp giữa Dashboard và danh sách");
//        softAssert.assertEquals(notPaidDs, notpaid_actualCount, "notPaidDs không khớp giữa Dashboard và danh sách"); 
//        softAssert.assertEquals(partially_paidDs, partiallypaid_actualCount, "partially_paidDs không khớp giữa Dashboard và danh sách");
//        softAssert.assertEquals(fully_paidDs, fullypaid_actualCount, "fully_paidDs không khớp giữa Dashboard và danh sách");  
//        softAssert.assertEquals(draftDs, draft_actualCount, "draftDs không khớp giữa Dashboard và danh sách");

        softAssert.assertAll();
    }
    
    
}
