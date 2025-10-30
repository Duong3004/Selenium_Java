package autotest.pages;
import java.util.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import autocom.common.CommonPage;
public class ProjectListPage extends CommonPage {
	    private WebDriver driver;
	    private WebDriverWait wait;
	    public ProjectListPage(WebDriver driver) {
	        super(driver);
	        this.driver = driver;
	        this.wait = new WebDriverWait(driver, 10);
	    }
	    public Map<String, String> getClientInfo(String title) {
	        Map<String, String> info = new HashMap<>();

	        try {
	            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table//tbody")));
	            List<WebElement> rows = driver.findElements(By.xpath("//tbody//tr"));

	            for (WebElement row : rows) {
	                List<WebElement> cells = row.findElements(By.tagName("td"));
	                if (cells.size() >= 8) {
	                    String taskTitle = "";
	                    try {
	                        taskTitle = cells.get(1).findElement(By.tagName("a")).getText().trim();
	                    } catch (Exception ignored) {}

	                    if (title.equals(taskTitle)) {
	                        info.put("id", cells.get(0).getText().trim());
	                        info.put("title", taskTitle);
	                        info.put("client", cells.get(2).getText().trim());
	                        info.put("price", cells.get(3).getText().trim());
	                        info.put("startDate", cells.get(4).getText().trim());
	                        info.put("dealine", cells.get(5).getText().trim());
	                        info.put("progress", cells.get(6).getText().trim());
	                        info.put("status", cells.get(7).getText().trim());
	                        return info;
	                    }
	                }
	            }
	        } catch (Exception e) {
	            System.err.println("Lỗi khi tìm client info:");
	            e.printStackTrace();
	        }

	        return null;
	    }
}
