package autotest.testcasepage.Rise;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Map;

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
        
        Cookie ciSession = new Cookie("ci_session", "81ac440162b8f9fc72186b9ca9665911");
        Cookie csrfToken = new Cookie("rise_csrf_cookie", "cca74465329e7549dc0600276295008c");

        driver.manage().addCookie(ciSession);
        driver.manage().addCookie(csrfToken);
        
        driver.navigate().to("https://rise.fairsketch.com/dashboard");
        driver.navigate().refresh();


    }
    
//    @AfterTest
//    public void tearDown() {
//        this.closeBrowser(driver);
//    }
   @Test(priority = 1, description = "Kiểm tra tổng task ở Dashbaoard")
    public void testMyOpenTasks() {
    	int dashboardTaskCount = ds.getTotalTasksFromDashboard();
    	dsk.goToMyOpenTasks();
    	int actualCount = dsk.getTasksFromTask_1();
    	driver.navigate().back();
        Assert.assertEquals(actualCount, dashboardTaskCount, "Số lượng task không khớp giữa Dashboard và danh sách");

         	
    }
   @Test(priority = 2, description = "Kiểm tra tổng Due ở Dashbaoard")
    public void testTotalDue() {
    	double dashboardDue = ds.getTotalDueFromDashboard();
    	double actualCount = dsk.calculateActualDue();
    	driver.navigate().back();
        Assert.assertEquals(actualCount, dashboardDue, "Tổng Due không khớp giữa Dashboard và danh sách");
         	
    }
    
   @Test(priority = 3, description = "Kiểm tra thành phần ở Projects Overview")
    public void testProjects_Overview() {
    	double openDs = ds.getTotalOpenFromDashboard();
    	double completedDs = ds.getTotalCompletedFromDashboard();
    	double holdDs = ds.getTotalHoldFromDashboard();

    	Map<String, Integer> statusCounts =dsk.countAllProjectStatuses();

    	int openCount = statusCounts.getOrDefault("Open", 0);
    	int completedCount = statusCounts.getOrDefault("Completed", 0);
    	int holdCount = statusCounts.getOrDefault("Hold", 0);

        Assert.assertEquals(openDs, openCount, " Open Project không khớp giữa Dashboard và danh sách");
        Assert.assertEquals(completedDs, completedCount, "Completed Project không khớp giữa Dashboard và danh sách");
        Assert.assertEquals(holdDs, holdCount, "Hold Project không khớp giữa Dashboard và danh sách"); 
        
    	driver.navigate().back();

        
    }
    
    @Test(priority = 4, description = "Kiểm tra thành phần ở Invoice Overview")
    public void testInvoice_Overview() {
        SoftAssert softAssert = new SoftAssert(); 	
        
        Map<String, Double> statusAmounts = dsk.calculateAmountPerInvoiceStatus();

        double over_actualCount = statusAmounts.getOrDefault("overdue", 0.0);
        double notpaid_actualCount = statusAmounts.getOrDefault("not paid", 0.0);
        double partiallypaid_actualCount = statusAmounts.getOrDefault("partially paid", 0.0); 
        double draft_actualCount = statusAmounts.getOrDefault("draft", 0.0);
        double fullypaid_actualCount = statusAmounts.getOrDefault("fully paid", 0.0);
  
        driver.navigate().back();
        
        double overdueDs = ds.getTotalOverDueFromDashboard();
        double notPaidDs = ds.getTotalNotpaidFromDashboard();
        double partially_paidDs = ds.getTotalPartiallypaidFromDashboard();
        double fully_paidDs = ds.getTotalFullypaidFromDashboard();
        double draftDs = ds.getTotalDraftFromDashboard();

        softAssert.assertEquals(overdueDs, over_actualCount, "overdueDs không khớp giữa Dashboard và danh sách");
        softAssert.assertEquals(notPaidDs, notpaid_actualCount, "notPaidDs không khớp giữa Dashboard và danh sách"); 
        softAssert.assertEquals(partially_paidDs, partiallypaid_actualCount, "partially_paidDs không khớp giữa Dashboard và danh sách");
        softAssert.assertEquals(fully_paidDs, fullypaid_actualCount, "fully_paidDs không khớp giữa Dashboard và danh sách");  
        softAssert.assertEquals(draftDs, draft_actualCount, "draftDs không khớp giữa Dashboard và danh sách");

        softAssert.assertAll();
    }
    
    @Test(priority = 5, description = "Kiểm tra thành phần ở All Tasks Overview")
    public void testAllTasks_Overview() {
        SoftAssert softAssert = new SoftAssert();

        int todoDs = ds.getTotalToDoFromDashboard();
        int inprogressDs = ds.getTotalInProgressFromDashboard();
        int reviewDs = ds.getTotalReviewFromDashboard();
        double doneDs = ds.getTotalDoneFromDashboard();
        double expriedDs = ds.getTotalExpriedFromDashboard();

        Map<String, Integer> statusCounts =dsk.countAllTaskStatuses();

    	for (Map.Entry<String, Integer> entry : statusCounts.entrySet()) {
    	} 
    	int to_doCount = statusCounts.getOrDefault("To do", 0);
    	int in_progressCount = statusCounts.getOrDefault("In progress", 0);
    	int reviewCount = statusCounts.getOrDefault("Review", 0);
    	int doneCount = statusCounts.getOrDefault("Done", 0);
    	int expiredCount = statusCounts.getOrDefault("Expired", 0);

    	driver.navigate().back();

        softAssert.assertEquals(reviewDs, reviewCount, "reviewDs không khớp giữa Dashboard và danh sách");

        softAssert.assertEquals(todoDs, to_doCount, "todoDs không khớp giữa Dashboard và danh sách");
        softAssert.assertEquals(inprogressDs, in_progressCount, "inprogressDs không khớp giữa Dashboard và danh sách"); 
//        softAssert.assertEquals(doneDs, doneCount, "doneDs không khớp giữa Dashboard và danh sách");  
//        softAssert.assertEquals(expriedDs, expiredCount, "expriedDs không khớp giữa Dashboard và danh sách");

        softAssert.assertAll();

    }
    
    
}
