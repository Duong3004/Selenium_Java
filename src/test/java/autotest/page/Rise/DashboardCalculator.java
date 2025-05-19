package autotest.page.Rise;
import java.util.List;

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
    /////////  

    @FindBy(xpath = "//a[@href='https://rise.fairsketch.com/index.php/projects/all_projects/1']")
    WebElement openProjectLink;
    
    @FindBy(xpath = "//a[@href='https://rise.fairsketch.com/index.php/projects/all_projects/2']")
    WebElement openComPLink;
    
    @FindBy(xpath = "//a[@href='https://rise.fairsketch.com/index.php/projects/all_projects/3']")
    WebElement openHoldLink;
    /////////  

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

    public void goToMyOpenTasks() {
        goToSection(openTasksLink, taskRows);
    }

    public void goToDue() {
        goToSection(openDueLink, invoicesRows);
    }

    public void goToOpenPro() {
        goToSection(openProjectLink, projectRows);
    }

    public void goToCompletedPro() {
        goToSection(openComPLink, projectRows);
    }

    public void goToHoldPro() {
        goToSection(openHoldLink, projectRows);
    }
    public void goToSection1(WebElement linkElement, By rowsLocator) {
        wait.until(ExpectedConditions.elementToBeClickable(linkElement));
        linkElement.click();
//        driver.findElement(By.xpath("//div[@id='invoice-list-table_processing']")).isDisplayed();
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(rowsLocator));
    }

    public void goToOverdueInv() {
    	pause(3);
    	
        goToSection1(openOverdueLink, By.xpath("//table[@id='invoice-list-table']//tbody/tr"));
    }

    public void goToNotpaidInv() {
        goToSection(openNotpaidLink, invoicesRows);
    }

    public void goToPartiallypaidInv() {
        goToSection(openPartiallypaidLink, invoicesRows);
    }

    public void goToFullypaidInv() {
        goToSection(openFullypaidLink, invoicesRows);
    }

    public void goToDraftInv() {
        goToSection(openDraftLink, invoicesRows);
    }

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

//
//    public double getDueFromSale() {
//        String xpath = "//tr//th[@data-all-page='10']";
//        try {
//            WebDriverWait wait = new WebDriverWait(driver,10);
//            WebElement totalDue = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
//
//            JavascriptExecutor js = (JavascriptExecutor) driver;
//            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", totalDue);
//            String dueText = totalDue.getText();
//
//            if (dueText == null || dueText.isEmpty()) {
//                dueText = (String) js.executeScript("return arguments[0].textContent;", totalDue);
//            }
//
//            dueText = dueText.replace("$", "").replace(",", "").trim();
//
//            if (dueText.isEmpty()) {
//                throw new RuntimeException("Giá trị Due là rỗng!");
//            }
//
//            return Double.parseDouble(dueText);
//        } catch (Exception e) {
//            throw new RuntimeException("Lỗi khi lấy giá trị Due: " + e.getMessage(), e);
//        }
//    }
    
    public double calculateActualDue() {
        By rowLocator = By.cssSelector("table#invoice-list-table tbody tr");
        By statusLocator = By.cssSelector("td:nth-child(9)");
        By dueLocator = By.cssSelector("td:nth-child(8)");
        By nextBtnLi = By.cssSelector("li.paginate_button.next");
        By taskModule = By.cssSelector("#invoice-list-table_length .select2-container");
        
        double totalDue = 0.0;
        int page = 1;
        
        wait.until(ExpectedConditions.elementToBeClickable(buttonInvoice));
     	buttonInvoice.click();
     	
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
    
    public double calculateActualDues() {
        By rowLocator = By.cssSelector("table#invoice-list-table tbody tr");
        By dueLocator = By.cssSelector("td:nth-child(8)");
        By nextBtnLi = By.cssSelector("li.paginate_button.next");
        By taskModule = By.cssSelector("#invoice-list-table_length .select2-container");


        double totalDue = 0.0;
        int page = 1;
        selectSizePage("50", taskModule);
        while (true) {
            List<WebElement> rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(rowLocator));
            
            if (rows.size() == 1 && rows.get(0).getText().contains("No record found")) {
                return 0.0;
            }


            int rowCount = rows.size();
            for (int i = 0; i < rowCount; i++) {
                try {
                    List<WebElement> currentRows = driver.findElements(rowLocator);
                    WebElement row = currentRows.get(i);

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



} 
