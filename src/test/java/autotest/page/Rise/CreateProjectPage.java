package autotest.page.Rise;

import autocom.common.CommonPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;

public class CreateProjectPage extends CommonPage {
	CreatProject_Task add;
	WebDriver driver;
	WebDriverWait wait;
	LoginPageRise login;

	public CreateProjectPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 10);
		add = new CreatProject_Task(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@title = 'Add project']")
	WebElement addProjectButton;

//	@FindBy(xpath = "//a[@title = 'Edit project']")
//    WebElement editProjectButton;
//	
//	@FindBy(xpath = "//a[@title = 'Delete project']")
//    WebElement deleteProjectButton;

	@FindBy(xpath = "//button[@class='btn btn-primary']")
	WebElement saveButton;

	@FindBy(xpath = "//button[@class='btn btn-default']")
	WebElement closeButton;

	@FindBy(xpath = "//span[@id='title-error']")
	WebElement titleErrorMessage;

	@FindBy(xpath = "//div[@class='app-alert-message']")
	WebElement warningMessage;
	
	@FindBy(xpath = "//div[@class = 'container-fluid']")
	WebElement warningMessageTime;
	
	@FindBy(xpath = "//div[@class = 'app-alert-message']")
	WebElement deleteSuccessMessage;

	public void openProjectForm() {
		wait.until(ExpectedConditions.elementToBeClickable(addProjectButton)).click();
	}

//    public void fillProjectForm(String title, boolean includeTitle) {
//    	if (includeTitle) {
//            WebElement titleInput = driver.findElement(By.id("title"));
//            titleInput.clear(); 
//            titleInput.sendKeys(title);
//        }
//		add.selectFromDropdown("s2id_project-type-dropdown", "Client Project");  	
//		add.selectFromSelect2Dropdown("Client", "Zoila Hauck");  	
//		add.writeDescription("Quis quisquam cumque quia aut nesciunt quia. Omnis omnis rerum nulla ut quisquam sit. Qui est necessitatibus sit eius omnis.");
//		add.writeInput("Start date","25-05-2025");
//        driver.findElement(By.id("start_date")).sendKeys(Keys.ENTER);
//		add.writeInput("Deadline","30-05-2025");
//        driver.findElement(By.id("deadline")).sendKeys(Keys.ENTER);
// 		add.writeInput("Price","1000");
//		add.selectFromDropdowns("s2id_project_labels", "Urgent");
//    }
	public void fillProjectForm(ProjectData projectData) {
		if (projectData.getTitle() != null) {
			WebElement titleInput = driver.findElement(By.id("title"));
			titleInput.clear();
			titleInput.sendKeys(projectData.getTitle());
		}

		if (projectData.getProjectType() != null) {
			add.selectFromDropdown("s2id_project-type-dropdown", projectData.getProjectType());
		}

		if (projectData.getClient() != null && !projectData.getClient().isEmpty()) {
			add.selectFromSelect2Dropdown("Client", projectData.getClient());
		}

		if (projectData.getDescription() != null) {
			add.writeDescription(projectData.getDescription());
		}

		if (projectData.getStartDate() != null) {
			add.writeInput("Start date", projectData.getStartDate());
			driver.findElement(By.id("start_date")).sendKeys(Keys.ENTER);
		}

		if (projectData.getDeadline() != null) {
			add.writeInput("Deadline", projectData.getDeadline());
			driver.findElement(By.id("deadline")).sendKeys(Keys.ENTER);
		}

		if (projectData.getPrice() != null) {
			add.writeInput("Price", projectData.getPrice());
		}

		if (projectData.getLabel() != null) {
			add.selectFromDropdowns("s2id_project_labels", projectData.getLabel());
		}
		if (projectData.getStatus() != null && !projectData.getStatus().isEmpty()) {
			add.selectFromSelect2Dropdown("Status", projectData.getStatus());
		}
	}

	public void clickSave() {
		saveButton.click();
	}

	public void clickClose() {
		closeButton.click();
	}

	public void searchProject(String titlle) {
		WebElement searchInput = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#project-table_filter input")));
		searchInput.clear();
		searchInput.sendKeys(titlle);
		searchInput.sendKeys(Keys.ENTER);
		 wait.until(ExpectedConditions.invisibilityOfElementLocated(
			        By.cssSelector(".dataTables_processing")));
			}
	public void editProjectByExactTitle(String title) {
	    searchProject(title);
	    
	    try {
	        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
	            By.xpath("//table[@id='project-table']//tbody//tr"), 0));
	            
	        String xpath = String.format(
	            "//table[@id='project-table']//tbody//tr[.//a[normalize-space()='%s']]",
	            title);
	            
	        WebElement targetRow = wait.until(ExpectedConditions.presenceOfElementLocated(
	            By.xpath(xpath)));
	            
	        WebElement editBtn = targetRow.findElement(
	            By.xpath(".//a[@title='Edit project']"));
	            
	        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
	        
	    } catch (TimeoutException e) {
	        throw new NoSuchElementException("Không tìm thấy project với tiêu đề: " + title);
	    }
	}
	public void deleteProjectByExactTitle(String title) {
	    searchProject(title);
	    
	    try {
	        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
	            By.xpath("//table[@id='project-table']//tbody//tr"), 0));
	            
	        String xpath = String.format(
	            "//table[@id='project-table']//tbody//tr[.//a[normalize-space()='%s']]",
	            title);
	            
	        WebElement targetRow = wait.until(ExpectedConditions.presenceOfElementLocated(
	            By.xpath(xpath)));
	            
	        WebElement editBtn = targetRow.findElement(
	            By.xpath(".//a[@title='Delete project']"));
	            
	        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
	        
	    } catch (TimeoutException e) {
	        throw new NoSuchElementException("Không tìm thấy project với tiêu đề: " + title);
	    }
	}

	public String getTitleError() {
		return wait.until(ExpectedConditions.visibilityOf(titleErrorMessage)).getText().trim();
	}

	public String getWarningMessage() {
		return wait.until(ExpectedConditions.visibilityOf(warningMessage)).getText().trim();
	}
	public String getDeleteSuccessMessage() {
		return wait.until(ExpectedConditions.visibilityOf(deleteSuccessMessage)).getText().trim();

	}

	
	public String getWarningIfPresent() {
	    try {
	        WebDriverWait shortWait = new WebDriverWait(driver, 2);
	        WebElement warning = shortWait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//div[contains(@class, 'col-md-9 form-group has-error')]")));
	        return warning.getText().trim();
	    } catch (TimeoutException e) {
	        return driver.findElement(By.xpath("//div[@class='container-fluid']")).getText();
	    }
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
					} catch (Exception ignored) {
					}

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
	public boolean isProjectExist(String title) {
	    return getClientInfo(title) != null;
	}

}
