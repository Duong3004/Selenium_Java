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

	@FindBy(xpath = "(//div[@class = 'pt-3 pb10 text-center']//h4)[1]")
	WebElement totalOpen;
	
	@FindBy(xpath = "(//div[@class = 'pt-3 pb10 text-center']//h4)[2]")
	WebElement totalCompleted;
	
	@FindBy(xpath = "(//div[@class = 'pt-3 pb10 text-center']//h4)[3]")
	WebElement totalHold;
	//////

	
	@FindBy(xpath = "(//div[@id = 'invoice-overview-container']//div[@class='w25p text-end'])[1]")
	WebElement totalOverdue;
	
	@FindBy(xpath = "(//div[@id = 'invoice-overview-container']//div[@class='w25p text-end'])[2]")
	WebElement totalNotpaid;
	
	@FindBy(xpath = "(//div[@id = 'invoice-overview-container']//div[@class='w25p text-end'])[3]")
	WebElement totalPartiallypaid;
	
	@FindBy(xpath = "(//div[@id = 'invoice-overview-container']//div[@class='w25p text-end'])[4]")
	WebElement totalFullypaid;
	
	@FindBy(xpath = "(//div[@id = 'invoice-overview-container']//div[@class='w25p text-end'])[5]")
	WebElement totalDraft;
	
	//////
	@FindBy(xpath = "(//div[@class='pb-2']//span)[1]")
	WebElement totalToDoTask;
	
	@FindBy(xpath = "(//div[@class='pb-2']//span)[2]")
	WebElement totalInProgressTask;
	
	@FindBy(xpath = "(//div[@class='pb-2']//span)[3]")
	WebElement totalReviewTask;
	
	@FindBy(xpath = "(//div[@class='pb-2']//span)[4]")
	WebElement totalDoneTask;
	
	@FindBy(xpath = "(//div[@class='pb-2']//span)[5]")
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
    
    public double getTotalToDoFromDashboard() {
        return getTotalInvoiced(totalToDoTask);
    }
    
    public double getTotalInProgressFromDashboard() {
        return getTotalInvoiced(totalInProgressTask);
    }
    
    public double getTotalReviewFromDashboard() {
        return getTotalInvoiced(totalReviewTask);
    }
    
    public double getTotalDoneFromDashboard() {
        return getTotalInvoiced(totalDoneTask);
    }
    
    public double getTotalExpriedFromDashboard() {
        return getTotalInvoiced(totalExpiredTask);
    }
    
    
}
