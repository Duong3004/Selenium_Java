package autotest.page.Rise;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import autocom.common.CommonPage;
import net.bytebuddy.asm.Advice.ArgumentHandler.Factory;
import autotest.page.Rise.*;

public class CreatProject_Task extends CommonPage{
	WebDriver driver;
    WebDriverWait wait;
	LoginPageRise login;

	public CreatProject_Task(WebDriver driver) {
		this.driver = driver;
		this.login = new LoginPageRise(driver);
        this.wait = new WebDriverWait(driver,10);  
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath = "//span[text() = 'Projects']") WebElement MenuProject; 
	@FindBy(xpath = "//a[@id = 'quick-add-icon']") WebElement ButtonAdd; 
	@FindBy(xpath = "//ul[contains(@class, 'dropdown-menu')]//a[@id='js-quick-add-task']") WebElement AddTask; 
	@FindBy(xpath = "//div[contains(@class, 'col-md-9 form-group has-error')]")WebElement ValEmailMsg;
	@FindBy(xpath = "//span[@id='title-error']") WebElement msgError; 
	@FindBy(xpath = "//div[@class='app-alert-message']") WebElement msgWarning; 
	@FindBy(xpath = "//a[@title = 'Add project']") WebElement AddProject; 
	@FindBy(xpath = "//button[@class='btn btn-primary']") WebElement saveButton; 
	@FindBy(xpath = "//button[@class='btn btn-default']") WebElement closeButton; 
	
	public void clickAddTask() {
//		MenuProject.click();
        wait.until(ExpectedConditions.visibilityOf(MenuProject)).click();
        wait.until(ExpectedConditions.visibilityOf(ButtonAdd)).click();
        wait.until(ExpectedConditions.visibilityOf(AddTask)).click();


	}

	 public void writeDescription(String text) {
		    driver.findElement(By.xpath("//div[@class = ' col-md-9']//textarea")).click();
	        WebElement labelElement = driver.findElement(By.xpath("//label[contains(text(), 'Description')]"));
	        WebElement inputField = labelElement.findElement(By.xpath("following-sibling::div//div[@class='note-editable']"));
	        inputField.clear();
	        inputField.sendKeys(text);
	    }
	 public void  writeInput(String lable,String text) {
	        WebElement labelElement = driver.findElement(By.xpath("//label[contains(text(), '"+ lable +"')]"));
	        WebElement inputField = labelElement.findElement(By.xpath("following-sibling::div//input"));
	        inputField.clear();
	        inputField.sendKeys(text);
	    }
	 public void selectFromDropdown(String dropdownLocator, String valueToSelect) {
		    try {
 		        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
		                By.xpath("//div[@id='" + dropdownLocator.trim() + "']")));
		        dropdown.click();
 		        if (valueToSelect != null && !valueToSelect.isEmpty()) {   
 		        	String optionXpath = "//ul[@role='listbox']//li//div[normalize-space()='" + valueToSelect + "']";
 		            WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(optionXpath)));
		            option.click();
		        } else {
		            System.out.println("Không thỏa điều kiện để chọn option.");
		        }
		    } catch (Exception e) {
		        System.err.println("Có lỗi xảy ra khi chọn dropdown.");
		        e.printStackTrace();
		    }
		}
	 public void selectFromDropdowns(String dropdownLocator, String valueToSelect) {
		    try {
		        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
		                By.xpath("//div[@id='" + dropdownLocator + "']")));
		        dropdown.click();

		        if (valueToSelect != null && !valueToSelect.isEmpty()) {   
		            String optionXpath = "//ul[@class='select2-results']//li//div[normalize-space()='" + valueToSelect + "']";
		            WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(optionXpath)));
		            option.click();
		        } else {
		            System.out.println("Không thỏa điều kiện để chọn option.");
		        }
		    } catch (Exception e) {
		        System.err.println("Có lỗi xảy ra khi chọn dropdown.");
		        e.printStackTrace();
		    }
		}
	 public void selectFromSelect2Dropdown(String labelText, String valueToSelect) {
		    try {
		        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
		            By.xpath("//label[normalize-space()='" + labelText + "']/following-sibling::div//div[contains(@class, 'select2-container')]")));

		        dropdown.click(); 

		        String optionXpath = "//ul[contains(@class, 'select2-results')]//div[normalize-space()='" + valueToSelect + "']";
		        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(optionXpath)));

		        option.click(); 
		    } catch (Exception e) {
		        System.err.println("Có lỗi xảy ra khi chọn dropdown Select2.");
		        e.printStackTrace();
		    }
		}

	 public String getValEmailMsg() {
	        try {
	            return  login.waitt(ValEmailMsg).getText().trim();
	        } catch (Exception e) {
	        	System.err.println("Không thể lấy thông tin lỗi: " + e.getMessage());
	            return "";  
	        }
	    }
//	 public void fillProjectForm(String title, boolean includeTitle) {
//		    if (includeTitle) {
//		        writeInput("Title", title);
//		    }
//
//		    selectFromDropdown("s2id_project-type-dropdown", "Client Project");  	
//		    selectFromSelect2Dropdown("Client", "Zoila Hauck");  	
//		    writeDescription("Quis quisquam cumque quia aut nesciunt quia. Omnis omnis rerum nulla ut quisquam sit. Qui est necessitatibus sit eius omnis.");
//		    writeInput("Start date", "25-05-2025");	
//		    driver.findElement(By.id("start_date")).sendKeys(Keys.ENTER);
//		    writeInput("Deadline", "30-05-2025");
//		    driver.findElement(By.id("deadline")).sendKeys(Keys.ENTER);
//		    writeInput("Price", "1000");
//		    selectFromDropdowns("s2id_project_labels", "Urgent");
//		}
	 public String getErrorMessage() {
	        return wait.until(ExpectedConditions.visibilityOf(msgError)).getText().trim();
	    }

	 public String getWarningMessage() {
	        return wait.until(ExpectedConditions.visibilityOf(msgWarning)).getText().trim();
	    }
	 public void openAddProjectForm() {
		    wait.until(ExpectedConditions.visibilityOf(AddProject)).click();
		}
	 public void clickSave() {
	        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
	    }

	    public void clickClose() {
	        wait.until(ExpectedConditions.elementToBeClickable(closeButton)).click();
	    }
	    public void fillProjectForm(ProjectData projectData) {
			if (projectData.getTitle() != null) {
				WebElement titleInput = driver.findElement(By.id("title"));
				titleInput.clear();
				titleInput.sendKeys(projectData.getTitle());
			}

			if (projectData.getProjectType() != null) {
				selectFromDropdown("s2id_project-type-dropdown", projectData.getProjectType());
			}

			if (projectData.getClient() != null && !projectData.getClient().isEmpty()) {
				selectFromSelect2Dropdown("Client", projectData.getClient());
			}

			if (projectData.getDescription() != null) {
				writeDescription(projectData.getDescription());
			}

			if (projectData.getStartDate() != null) {
				writeInput("Start date", projectData.getStartDate());
				driver.findElement(By.id("start_date")).sendKeys(Keys.ENTER);
			}

			if (projectData.getDeadline() != null) {
				writeInput("Deadline", projectData.getDeadline());
				driver.findElement(By.id("deadline")).sendKeys(Keys.ENTER);
			}

			if (projectData.getPrice() != null) {
				writeInput("Price", projectData.getPrice());
			}

			if (projectData.getLabel() != null) {
				selectFromDropdowns("s2id_project_labels", projectData.getLabel());
			}
			if (projectData.getStatus() != null && !projectData.getStatus().isEmpty()) {
				selectFromSelect2Dropdown("Status", projectData.getStatus());
			}
		}

}
