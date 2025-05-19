package autotest.testcasepage.Rise;
import org.openqa.selenium.By;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import autocom.common.CommonPage;
import autocom.constant.KeywordConstant;
import autotest.page.Rise.CreatProject_Task;
import autotest.page.Rise.LoginPageRise;
public class JavaTest extends CommonPage {
    WebDriver driver;
	LoginPageRise login;
	WebDriverWait wait;
	public JavaTest() {
 	}
	@Test
	public void jsExecutorDemo02()  {
	  driver = this.startBrower("https://rise.fairsketch.com/clients", KeywordConstant.BROWSER);
	  JavascriptExecutor js = (JavascriptExecutor) driver;
	  login = new LoginPageRise(driver);
      this.wait = new WebDriverWait(driver,10);  
	  login.login(KeywordConstant.usernameRise, KeywordConstant.passwordRise);
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[data-title='Add client']")));
	  js.executeScript("document.querySelector('a[data-title=\"Add client\"]').click();");
	  WebElement element =  driver.findElement(By.xpath("//input[@value='person']"));
	 if(!element.isSelected()) {
		 element.click();
	 }
	  Assert.assertTrue(element.isSelected(), "Radio button 'person' chưa được chọn!");

	  writeInputPureJS("Name", "Innovators Ltd.");
	  pause(1);
	  selectFromDropdown("s2id_owner_id","Michael Wood");
	  pause(2);

	  writeInputPureJS("Address", "456 Future Road, Silicon Valley");
	  writeInputPureJS("City", "San Francisco");
	  writeInputPureJS("State", "California");
	  writeInputPureJS("Zip", "94107");
	  writeInputPureJS("Country", "United States");

	  writeInputPureJS("Phone", "+1 123 456 7890");
	  writeInputPureJS("Currency Symbol", "$");

	  
	  js.executeScript("document.getElementsByClassName('btn btn-primary')[0].click();");

	   
	  driver.get("https://rise.fairsketch.com/clients/index/clients_list#all_clients");
	  pause(2);  
	  WebElement searchInput = driver.findElement(By.cssSelector("#client-table_filter input"));
	  searchInput.sendKeys("Innovators Ltd.");
	  searchInput.sendKeys(Keys.ENTER);
	 pause(2);  

 	 Map<String, String> info = getClientInfo("Innovators Ltd.");
 	 Assert.assertNotNull(info, "Không tìm thấy thông tin khách hàng!");
	 pause(2);  

     Assert.assertEquals(info.get("name"), "Innovators Ltd.","Tên không đúng");
     System.out.println("Đã tìm thấy khách hàng và có ID là: "+info.get("id"));
 	}
	public void writeInputPureJS(String labelText, String text) {
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript(
	        "const labels = Array.from(document.querySelectorAll('label'));" +
	        "for (const label of labels) {" +
	        "  if (label.textContent.includes(arguments[0])) {" +
	        "    const input = label.parentElement.querySelector('input');" +
	        "    if (input) {" +
	        "      input.value = arguments[1];" +
	        "      input.dispatchEvent(new Event('input'));" +
	        "      break;" +
	        "    }" +
	        "  }" +
	        "}",
	        labelText, text
	    );
	}
	public void writeDescription(String text) {
	    try {
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        
 	        js.executeScript("document.getElementById('description').click();");
	        
 	        WebElement editor = driver.findElement(
	            By.xpath("//label[contains(text(), 'Description')]/following-sibling::div//div[@class='note-editable']")
	        );
	        js.executeScript("arguments[0].innerHTML = arguments[1];", editor, text);
	        
	    } catch (Exception e) {
	        System.err.println("Lỗi khi thao tác với editor: " + e.getMessage());
	        throw e;
	    }
	}

	 public void selectFromDropdown(String dropdownLocator, String valueToSelect) {
		    try {
		        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
		                By.xpath("//div[@id='" + dropdownLocator + "']")));
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
	public Map<String, String> getClientInfo( String clientName) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        String script =
                "const rows = document.querySelectorAll('#client-table tbody tr');" +
                "for (let row of rows) {" +
                "  const cells = row.querySelectorAll('td');" +
                "  if (cells[1] && cells[1].innerText.trim() === '" + clientName + "') {" +  
                "    return {" +
                "      id: cells[0].textContent.trim()," +
                "      name: cells[1].textContent.trim()," +
                "      primaryContact: cells[2].textContent.trim()," +
                "      phone: cells[3].textContent.trim()," +
                "      clientGroups: cells[4].textContent.trim()," +
                "      labels: cells[5].textContent.trim()," +
                "      projects: cells[6].textContent.trim()," +
                "      totalInvoiced: cells[7].textContent.trim()," +
                "      paymentReceived: cells[8].textContent.trim()," +
                "      due: cells[9].textContent.trim()" +
                "    };" +
                "  }" +
                "}" +
                "return null;";
        return (Map<String, String>) js.executeScript(script);

    }
	}


