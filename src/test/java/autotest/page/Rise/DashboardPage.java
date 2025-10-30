package autotest.page.Rise;
import org.openqa.selenium.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import autocom.common.CommonPage;

public class DashboardPage extends CommonPage {
	WebDriverWait wait;
	WebDriver driver;

	public DashboardPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 10);
		PageFactory.initElements(driver, this);
		}
	@FindBy(xpath = "(//div[@class = 'widget-details']//h1)[1]")
	WebElement totalTasksCount;
	
	@FindBy(xpath = "(//div[@class = 'widget-details']//h1)[3]")
	WebElement totalDue;
	//////

	@FindBy(xpath = "//div//span[text() = 'Open']/preceding-sibling::h4")
	WebElement totalOpen;
	
	@FindBy(xpath = "//div//span[text() = 'Completed']/preceding-sibling::h4")
	WebElement totalCompleted;
	
	@FindBy(xpath = "//div//span[text() = 'Hold']/preceding-sibling::h4")
	WebElement totalHold;
	//////

	
	@FindBy(xpath = "//div[contains(., 'Overdue')]/div[@class='w25p text-end']")
	WebElement totalOverdue;
	
	@FindBy(xpath = "//div[contains(., 'Not paid')]/div[@class='w25p text-end']")
	WebElement totalNotpaid;
	
	@FindBy(xpath = "//div[contains(., 'Partially paid')]/div[@class='w25p text-end']")
	WebElement totalPartiallypaid;
	
	@FindBy(xpath = "//div[contains(., 'Fully paid')]/div[@class='w25p text-end']")
	WebElement totalFullypaid;
	
	@FindBy(xpath = "//div[contains(., 'Draft')]/div[@class='w25p text-end']")
	WebElement totalDraft;
	
	//////
	@FindBy(xpath = "//div[contains(., 'To do')]/span")
	WebElement totalToDoTask;
	
	@FindBy(xpath = "//div[contains(., 'In progress')]/span")
	WebElement totalInProgressTask;
	
	@FindBy(xpath = "//div[contains(., 'Review')]/span")
	WebElement totalReviewTask;
	
	@FindBy(xpath = "//div[contains(., 'Done')]/span")
	WebElement totalDoneTask;
	
	@FindBy(xpath = "//div[contains(., 'Expired')]/span")
	WebElement totalExpiredTask;
	
	
	public int getTotal(WebElement element) {
	    return Integer.parseInt(element.getText().trim());
	}
	
	 public double getTotalInvoiced(WebElement element) {
	        return Double.parseDouble(element.getText().replace("$", "").replace(",", ""));
	    }
	
	public int getTotalOpenFromDashboard() {
	    return getTotal(totalOpen);
	}
	
	public int getTotalCompletedFromDashboard() {
	    return getTotal(totalCompleted);
	}
	
	public int getTotalHoldFromDashboard() {
	    return getTotal(totalHold);
	}
	
	public int getTotalTasksFromDashboard() {
	    return getTotal(totalTasksCount);
	}
	
    public double getTotalDueFromDashboard() {
        return getTotalInvoiced(totalDue);
    }
	
    public double getTotalOverDueFromDashboard() {
        return getTotalInvoiced(totalOverdue);
    }
    
    public double getTotalNotpaidFromDashboard() {
        return getTotalInvoiced(totalNotpaid);
    }
    
    public double getTotalPartiallypaidFromDashboard() {
        return getTotalInvoiced(totalPartiallypaid);
    }
    
    public double getTotalFullypaidFromDashboard() {
        return getTotalInvoiced(totalFullypaid);
    }
    
    public double getTotalDraftFromDashboard() {
        return getTotalInvoiced(totalDraft);
    }
    
    public int getTotalToDoFromDashboard() {
        return (int) getTotalInvoiced(totalToDoTask); 
    }

    public int getTotalReviewFromDashboard() {
        return (int) getTotalInvoiced(totalReviewTask); 
    }
    
    public int getTotalInProgressFromDashboard() {
        return (int) getTotalInvoiced(totalInProgressTask); 
    }
    
    public double getTotalDoneFromDashboard() {
        return getTotalInvoiced(totalDoneTask);
    }
    
    public double getTotalExpriedFromDashboard() {
        return getTotalInvoiced(totalExpiredTask);
    }
    
    
}
