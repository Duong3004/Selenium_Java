package autotest.page.Rise;
import java.util.*;
import java.util.NoSuchElementException;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import autocom.common.CommonPage;
public class DashboardCalculator extends CommonPage {
	WebDriver driver;
    WebDriverWait wait;
	public DashboardCalculator(WebDriver driver) {
		this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
        PageFactory.initElements(driver, this);
        }
	
    @FindBy(xpath = "//table[@id='task-table']//tbody/tr")
    List<WebElement> taskRows;
    
    @FindBy(xpath = "//table[@id='invoice-list-table']//tbody/tr")
    List<WebElement> invoicesRows;
    
    @FindBy(xpath = "//table[@id='project-table']//tbody/tr")
    List<WebElement> projectRows;
    
    
    
    
    @FindBy(xpath = "//button[text()='Invoices']")
    WebElement buttonInvoice;
    
    
   /////////  
    @FindBy(xpath = "//a[@href='https://rise.fairsketch.com/index.php/tasks/all_tasks#my_open_tasks']")
    WebElement openTasksLink;
    /////////  

    @FindBy(xpath = "//a[@href='https://rise.fairsketch.com/index.php/invoices/index']")
    WebElement openDueLink;


    @FindBy(xpath = "//a[@href='https://rise.fairsketch.com/index.php/invoices/index/custom/overdue/USD']")
    WebElement openOverdueLink;
    
    @FindBy(xpath = "//a[@href='https://rise.fairsketch.com/index.php/invoices/index/custom/not_paid/USD']")
    WebElement openNotpaidLink;
    
    @FindBy(xpath = "//a[@href='https://rise.fairsketch.com/index.php/invoices/index/custom/partially_paid/USD']")
    WebElement openPartiallypaidLink;
    
    @FindBy(xpath = "//a[@href='https://rise.fairsketch.com/index.php/invoices/index/custom/fully_paid/USD']")
    WebElement openFullypaidLink;
    
    @FindBy(xpath = "//a[@href='https://rise.fairsketch.com/index.php/invoices/index/custom/draft/USD']")
    WebElement openDraftLink;
    ///////
    
    
    
    
    public void goToSection(WebElement linkElement, List<WebElement> expectedRows) {
        wait.until(ExpectedConditions.elementToBeClickable(linkElement));
        linkElement.click();
        wait.until(ExpectedConditions.visibilityOfAllElements(expectedRows));

    }
    public void goToModul(WebElement modul) {
        wait.until(ExpectedConditions.elementToBeClickable(modul));
        modul.click(); 
        }

    public void goToMyOpenTasks() {
        goToSection(openTasksLink, taskRows);
    }

    public void goToDue() {
        goToSection(openDueLink, invoicesRows);
    }


    public void goToSection1(WebElement linkElement, By rowsLocator) {
        wait.until(ExpectedConditions.elementToBeClickable(linkElement));
        linkElement.click();
//        driver.findElement(By.xpath("//div[@id='invoice-list-table_processing']")).isDisplayed();
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(rowsLocator));
    }
    private void waitForTableLoad(By processingLocator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(processingLocator));
    }

//    public void goToOverdueInv() {
//    	pause(3);
//    	
//        goToSection1(openOverdueLink, By.xpath("//table[@id='invoice-list-table']//tbody/tr"));
//    }

//    public void goToNotpaidInv() {
//        goToSection(openNotpaidLink, invoicesRows);
//    }
//
//    public void goToPartiallypaidInv() {
//        goToSection(openPartiallypaidLink, invoicesRows);
//    }
//
//    public void goToFullypaidInv() {
//        goToSection(openFullypaidLink, invoicesRows);
//    }
//
//    public void goToDraftInv() {
//        goToSection(openDraftLink, invoicesRows);
//    }

    public void selectSizePage(String sizeText, By moduleLocator) {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        try {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(moduleLocator));

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", dropdown);
            Thread.sleep(300); 
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);

            String xpath = String.format("//ul[contains(@class,'select2-results')]//li/div[text()='%s']", sizeText);
            WebElement item = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

            item.click();

        } catch (TimeoutException e) {
            System.out.println("⛔ Timeout: Không thể chọn số dòng " + sizeText);
        } catch (Exception e) {
            System.out.println("⛔ Lỗi khi chọn số dòng/trang: " + e.getMessage());
        }
    }


    public int countTableRowsWithPagination(By rowLocator, By nextPageButtonLocator) {
        int totalRows = 0;

        do {
            List<WebElement> currentRows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(rowLocator));

            if (currentRows.size() == 1) {
                WebElement onlyRow = currentRows.get(0);
                String rowText = onlyRow.getText().trim();
                if (!rowText.equalsIgnoreCase("No record found.")) {
                    totalRows += 1;
                }
            } else {
                totalRows += currentRows.size();
            }

            WebElement nextButton = driver.findElement(nextPageButtonLocator);
            boolean isLastPage = nextButton.getAttribute("class").contains("disabled");
            if (isLastPage) {
                break;
            }

            WebElement firstRowBeforeClick = currentRows.get(0);

            try {
                nextButton.click();
                wait.until(ExpectedConditions.stalenessOf(firstRowBeforeClick));
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(rowLocator));
            } catch (Exception e) {
                System.out.println("Không thể chuyển trang: " + e.getMessage());
                break;
            }

        } while (true);

        return totalRows;
    }


    public int getTasksFromTask_1() {
    	By taskRowLocator = By.cssSelector("#task-table tbody tr");
    	By taskNextButtonLocator = By.cssSelector("#task-table_next.page-item.next");
        By taskModule = By.cssSelector("#task-table_length .select2-container");

     	return countTableRowsWithPagination(taskRowLocator,taskNextButtonLocator);
    }

    public int getProject() {
    	By projectRowLocator = By.cssSelector("#project-table tbody tr");
    	By projectNextButtonLocator = By.cssSelector("#project-table_next.page-item.next");
    	return countTableRowsWithPagination(projectRowLocator,projectNextButtonLocator);
    }
    

    public Map<String, Integer> countStatusesGeneric(
            By rowLocator,
            By statusLocator,
            By nextBtnLi,
            By sizePageLocator,
            By processingLocator
    ) {
        Map<String, Integer> statusCounts = new HashMap<>();
        selectSizePage("100", sizePageLocator);
        waitForTableLoad(processingLocator);
        pause(1);

        int page = 1;

        while (true) {
            waitForTableLoad(processingLocator);

            List<WebElement> rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(rowLocator));

            for (int i = 0; i < rows.size(); i++) {
                try {
                    WebElement row = rows.get(i);
                    String status = row.findElement(statusLocator).getText().trim();
                    statusCounts.put(status, statusCounts.getOrDefault(status, 0) + 1);
                } catch (StaleElementReferenceException e) {
                    System.out.println("⚠ Row bị stale, bỏ qua hàng " + i);
                } catch (Exception e) {
                    System.out.println("‼️ Lỗi xử lý row " + i + ": " + e.getMessage());
                }
            }

            try {
                WebElement nextLi = driver.findElement(nextBtnLi);
                String classAttr = nextLi.getAttribute("class");

                if (classAttr.contains("disabled")) {
                    break; 
                }

                WebElement nextBtn = nextLi.findElement(By.cssSelector("a.page-link"));
                WebElement firstRowBefore = rows.get(0); // Để check staleness

                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", nextBtn);
                nextBtn.click();

                wait.until(ExpectedConditions.stalenessOf(firstRowBefore));
                waitForTableLoad(processingLocator);

                page++;
            } catch (Exception e) {
                System.out.println("🚫 Lỗi khi chuyển trang: " + e.getMessage());
                break;
            }
        }

        return statusCounts;
    }




    public Map<String, Integer> countAllProjectStatuses() {
        WebElement modulProject = driver.findElement(By.xpath("//li[contains(@class,'main')]//span[text()='Projects']"));      
        goToModul(modulProject);

        return countStatusesGeneric(
            By.cssSelector("#project-table tbody tr"),                   // row
            By.cssSelector("td:nth-child(8)"),                           // status
            By.cssSelector("#project-table_next.page-item.next"),        // next
            By.cssSelector("#project-table_length .select2-container"),  // size per page
            By.cssSelector("#project-table_processing")                  // processing indicator
        );
    }

    public Map<String, Integer> countAllTaskStatuses() {
        WebElement modulTasks = driver.findElement(By.xpath("//li[contains(@class,'main')]//span[text()='Tasks']"));      
        goToModul(modulTasks);
        driver.findElement(By.xpath("//button[text()='All tasks']")).click();

        return countStatusesGeneric(
            By.cssSelector("#task-table tbody tr"),                   // row
            By.cssSelector("td a[data-act='update-task-status']"),                           // status
            By.cssSelector("#task-table_next.page-item.next"),        // next
            By.cssSelector("#task-table_length .select2-container"),  // size per page
            By.cssSelector("#task-table_processing")                  // processing indicator
        );
    }


    
    public double calculateActualDue() {
        By rowLocator = By.cssSelector("table#invoice-list-table tbody tr");
        By statusLocator = By.cssSelector("td:nth-child(9)");
        By dueLocator = By.cssSelector("td:nth-child(8)");
        By nextBtnLi = By.cssSelector("li.paginate_button.next");
        By taskModule = By.cssSelector("#invoice-list-table_length .select2-container");
        
        WebElement sale = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Sales']")));
        sale.click();
        
        WebElement invoice = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li//span[text()='Invoices']")));
        invoice.click();
        
        WebElement invoicesButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Invoices']")));
        invoicesButton.click();
        
        double totalDue = 0.0;
        int page = 1;
        
    	selectSizePage("50",taskModule);

        while (true) {
            List<WebElement> rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(rowLocator));

            for (int i = 0; i < rows.size(); i++) {
                try {
                    WebElement row = rows.get(i);

                    String status = row.findElement(statusLocator).getText().trim().toLowerCase();
                    if (status.contains("draft") || status.contains("fully paid")) continue;

                    String dueTextRaw = row.findElement(dueLocator).getText();
                    String dueText = dueTextRaw
                            .replaceAll("[^\\d.,]", "")
                            .replace(",", ".")
                            .replaceAll("(\\.)(?=.*\\.)", "")
                            .trim();

                    if (!dueText.isEmpty()) {
                        double due = Double.parseDouble(dueText);
                        totalDue += due;
                    }
                } catch (StaleElementReferenceException e) {
                    System.out.println("⚠ Row bị stale, bỏ qua hàng " + i);
                } catch (NumberFormatException e) {
                    System.out.println("❌ Lỗi chuyển đổi số tiền: '" + e.getMessage() + "'");
                } catch (Exception e) {
                    System.out.println("‼️ Lỗi xử lý row " + i + ": " + e.getMessage());
                }
            }

            try {
                WebElement nextLi = driver.findElement(nextBtnLi);
                if (nextLi.getAttribute("class").contains("disabled")) break;

                WebElement nextBtn = nextLi.findElement(By.cssSelector("a.page-link"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", nextBtn);

                wait.until(ExpectedConditions.elementToBeClickable(nextBtn)).click();
                wait.until(ExpectedConditions.stalenessOf(rows.get(0))); 
                page++;
            } catch (NoSuchElementException e) {
                break;
            } catch (Exception e) {
                System.out.println("🚫 Lỗi khi chuyển trang: " + e.getMessage());
                break;
            }
        }

        return totalDue;
    }
    

    public Map<String, Double> calculateAmountPerInvoiceStatus() {
        Map<String, Double> amountByStatus = new HashMap<>();
        
     // Khởi tạo tất cả các key cần thiết
        amountByStatus.put("overdue", 0.0);
        amountByStatus.put("not paid", 0.0);
        amountByStatus.put("partially paid", 0.0);
        amountByStatus.put("fully paid", 0.0);
        amountByStatus.put("draft", 0.0);


        // locator setup
        By rowLocator = By.cssSelector("table#invoice-list-table tbody tr");
        By totalInvoicedLocator = By.cssSelector("td:nth-child(6)");
        By paymentReceivedLocator = By.cssSelector("td:nth-child(7)");
        By dueLocator = By.cssSelector("td:nth-child(8)");
        By statusLocator = By.cssSelector("td:nth-child(9)");
        By nextBtnLi = By.cssSelector("li.paginate_button.next");
        By taskModule = By.cssSelector("#invoice-list-table_length .select2-container");
        
        WebElement sale = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Sales']")));
        sale.click();
        
        WebElement invoice = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li//span[text()='Invoices']")));
        invoice.click();
        
        WebElement invoicesButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Invoices']")));
        invoicesButton.click();
        
        selectSizePage("50", taskModule);

        while (true) {
            List<WebElement> rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(rowLocator));

            if (rows.size() == 1 && rows.get(0).getText().contains("No record found")) {
                return amountByStatus;
            }

            for (WebElement row : rows) {
                try {
                    String totalText = row.findElement(totalInvoicedLocator).getText().replaceAll("[^\\d.,]", "").replace(",", "");
                    String receivedText = row.findElement(paymentReceivedLocator).getText().replaceAll("[^\\d.,]", "").replace(",", "");
                    String dueText = row.findElement(dueLocator).getText().replaceAll("[^\\d.,]", "").replace(",", "");
                    
                    String status = row.findElement(statusLocator).getText().trim().toLowerCase();
                    double total = totalText.isEmpty() ? 0 : Double.parseDouble(totalText);
                    double received = receivedText.isEmpty() ? 0 : Double.parseDouble(receivedText);
                    double due = dueText.isEmpty() ? 0 : Double.parseDouble(dueText);

                    switch (status) {
                        case "overdue":
                        	
                            amountByStatus.merge("overdue", due, Double::sum);

                            if (received == 0) {
                                amountByStatus.merge("not paid", due, Double::sum);
                            } else {
                                amountByStatus.merge("partially paid", total, Double::sum);
                            }
                            break;
                        case "not paid":
                            amountByStatus.merge("not paid", due, Double::sum);
                            break;
                        case "partially paid":
                            amountByStatus.merge("partially paid", total, Double::sum);
                            break;
                        case "fully paid":
                            amountByStatus.merge("fully paid", total, Double::sum);
                            break;
                        case "draft":
                            amountByStatus.merge("draft", due, Double::sum);
                            break;
                        default:
                            continue; // Bỏ qua các trạng thái không xác định
                    }

                } catch (NumberFormatException e) {
                    System.out.println("‼️ Lỗi định dạng số: " + e.getMessage());
                } catch (NoSuchElementException e) {
                    System.out.println("‼️ Không tìm thấy element: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("‼️ Lỗi khi xử lý dòng: " + e.getMessage());
                }
            }

            try {
                WebElement nextLi = driver.findElement(nextBtnLi);
                if (nextLi.getAttribute("class").contains("disabled")) break;

                WebElement nextBtn = nextLi.findElement(By.cssSelector("a.page-link"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", nextBtn);
                wait.until(ExpectedConditions.elementToBeClickable(nextBtn)).click();
                wait.until(ExpectedConditions.stalenessOf(rows.get(0)));
            } catch (NoSuchElementException e) {
                break;
            } catch (Exception e) {
                System.out.println("‼️ Lỗi khi chuyển trang: " + e.getMessage());
                break;
            }
        }
        return amountByStatus;
    }
    public void calculateSummaryValues(Map<String, Double> amountByStatus) {
        double totalInvoiced = amountByStatus.get("partially paid") 
                             + amountByStatus.get("fully paid") 
                             + amountByStatus.get("not paid");
        amountByStatus.put("total invoiced", totalInvoiced);
    } 
} 
