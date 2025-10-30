package autocom.common;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonPage {

	public WebDriver driver;
	
    public CommonPage(WebDriver driver2) {
		// TODO Auto-generated constructor stub
	}
    public CommonPage() {
		// TODO Auto-generated constructor stub
	}
	public WebDriver getDriver() {
        return this.driver;
    }

	protected int DEFAULT_TIMEOUT = 20000;
	protected int WAIT_INTERVAL = 1000;
	public int loopCount = 0;
	public final int ACTION_REPEAT = 5;
	public Actions action;
	public String DEVICE = "iPhone 8";
	public String PLATFORM = "iOS";
	public String PLATFORMVERSION = "12.1";
	// public String APP = "UAT-My Viettel.zip";
	public String APP;
	// public String UDID = "B596E6B8-8776-4E89-AD92-BFE7974A835A";
	public String UDID = "B596E6B8-8776-4E89-AD92-BFE7974A835A";
	public String baseUrl = "http://10.60.108.62:9750/SALE_WEB";

	/**
	 * switch to a frame
	 * 
	 * @param locator
	 * @param opParams
//	 */
//	public void switchToFrame(Object locator, Object... opParams) {
////        info("Switch to frame " + locator);
//		int notDisplay = (Integer) (opParams.length > 0 ? opParams[0] : 0);
//		try {
//			driver.switchTo().frame(getElementPresent(locator, DEFAULT_TIMEOUT, 1, notDisplay));
//		} catch (Exception e) {
//			switchToFrame(locator, notDisplay);
//		}
//	}

//	/**
//	 * back to main frame
//	 */
//	public void switchToParentFrame() {
//		try {
//			driver.switchTo().defaultContent();
//		} catch (Exception e) {
//			switchToParentFrame();
//		}
//	}

	/**
	 * init Driver
	 * 
	 * @param URL
	 */	    
	    public WebDriverWait getWait(int timeoutInSeconds) {
	        return new WebDriverWait(driver,timeoutInSeconds);
	    }

	    public void waitForElementVisible(By locator, int timeoutInSeconds) {
	        getWait(timeoutInSeconds).until(ExpectedConditions.visibilityOfElementLocated(locator));
	    }

	    public void waitForElementClickable(By locator, int timeoutInSeconds) {
	        getWait(timeoutInSeconds).until(ExpectedConditions.elementToBeClickable(locator));
	    }
	    
	    public void waitForElementVisible(By locator) {
	        waitForElementVisible(locator, DEFAULT_TIMEOUT);
	    }

	    public void waitForElementClickable(By locator) {
	        waitForElementClickable(locator, DEFAULT_TIMEOUT);
	    }

	    public boolean verifyElementVisible(By by) {
	        try {
	            WebElement element = driver.findElement(by);
	            return element.isDisplayed();
	        } catch (NoSuchElementException e) {
	            return false;
	        }
	    }

	    public boolean verifyElementClickable(By by) {
	        try {
	            getWait(5).until(ExpectedConditions.elementToBeClickable(by));
	            return true;
	        } catch (TimeoutException e) {
	            return false;
	        }
	    }
	public WebDriver startBrower(String url, String browser) {
		if ("chrome".equals(browser)) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver.exe");
			driver = new ChromeDriver();
		} else if ("edge".equals(browser)) {
			System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "/drivers/msedgedriver.exe");

			driver = new EdgeDriver();
		} else if ("safari".equals(browser)) {
			driver = new SafariDriver();
		} else {
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/drivers/geckodriver.exe");
			FirefoxOptions options = new FirefoxOptions();
			options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe"); 
			driver = new FirefoxDriver(options);
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.navigate().to(url);
		return driver;
	}
	
	
	//Truyền vào string = "Hóa đơn\Tạo hoá đơn"
	public void clickMenu(String strSelected) {	
		String txtMenu = "//span[text() = '%s']/ancestor::a";
		String[] menus = new String[2];
		menus = strSelected.split("/");	
		pause(1000);
		
		for(int i = 0; i < menus.length; i++) {
			if(!driver.findElement(By.xpath(String.format(txtMenu, menus[i]))).isDisplayed()) {
				pause(1000);
			}
			driver.findElement(By.xpath(String.format(txtMenu, menus[i]))).click();			
		}
	}	
	
//	public void scrollToElement(String xpath) {
//		JavascriptExecutor jse6 = (JavascriptExecutor) driver;
//		((JavascriptExecutor) driver).executeScript(
//	            "arguments[0].scrollIntoView();", driver.findElement(By.xpath(xpath)));
//	}
	
	
	
	public void closeBrowser(WebDriver dr) {
		dr.close();
	}

	/**
	 * pause driver in timeInMillis
	 * 
	 * @param timeInMillis
	 */
	public void pause(long secondS) {
		try {
			Thread.sleep(secondS*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param locator
	 * @param opParams
	 * @return
	 */
//	public WebElement waitForElementNotPresent(Object locator, int... opParams) {
//		WebElement elem = null;
//		int timeout = opParams.length > 0 ? opParams[0] : DEFAULT_TIMEOUT;
//		int isAssert = opParams.length > 1 ? opParams[1] : 1;
//		int notDisplayE = opParams.length > 2 ? opParams[2] : 0;
//
//		for (int tick = 0; tick < timeout / WAIT_INTERVAL; tick++) {
//			if (notDisplayE == 2) {
//				elem = getElement(locator);
//			} else {
//				elem = getDisplayedElement(locator);
//			}
//			if (elem == null) {
//				return null;
//			}
//			pause(WAIT_INTERVAL);
//		}
//		if (isAssert == 1) {
//			assert false : ("Timeout after " + timeout + "ms waiting for element not present: " + locator);
//		}
//		return elem;
//	}

	/**
	 * @param locator
	 * @return
	 */
	public WebElement getElement(Object locator, WebDriver dr) {
		By by = locator instanceof By ? (By) locator : By.xpath(locator.toString());
		WebElement elem = null;
		try {
			elem = dr.findElement(by);
		} catch (NoSuchElementException e) {
		
			pause(WAIT_INTERVAL);
			getElement(locator, dr);
		} catch (StaleElementReferenceException e) {
		
			pause(WAIT_INTERVAL);
			getElement(locator, dr);
		} catch (WebDriverException e) {
		
			pause(WAIT_INTERVAL);
			getElement(locator, dr);
		}

		return elem;
	}

	/**
	 * 
	 * @param locator
	 * @return
	 */
	public List<WebElement> getListElement(Object locator) {
		By by = locator instanceof By ? (By) locator : By.xpath(locator.toString());
		List<WebElement> elementOptions;
		try {
			elementOptions = driver.findElements(by);
			return elementOptions;
		} catch (NoSuchElementException ex) {
			pause(WAIT_INTERVAL);
			getListElement(locator);
		} catch (StaleElementReferenceException ex) {
			pause(WAIT_INTERVAL);
			getListElement(locator);
		} finally {
			loopCount = 0;
		}
		return null;

	}

//	public WebElement getElementPresent(Object locator) {
//		WebElement elem = null;
//		
//		for (int tick = 0; tick < timeout / WAIT_INTERVAL; tick++) {
//			if (notDisplayE == 2) {
//				elem = getElement(locator);
//			} else {
//				elem = getDisplayedElement(locator);
//			}
//			if (null != elem)
//				return elem;
//			pause(WAIT_INTERVAL);
//		}
//		return null;
//	}
//
//	public WebElement getElementPresentNoAssert(Object locator, int... opParams) {
//		WebElement elem = null;
//		int timeout = opParams.length > 0 ? opParams[0] : DEFAULT_TIMEOUT;
//		By by = locator instanceof By ? (By) locator : By.xpath(locator.toString());
//		for (int tick = 0; tick < timeout / WAIT_INTERVAL; tick++) {
//			try {
//				elem = driver.findElement(by);
//				if (null != elem)
//					return elem;
//				pause(WAIT_INTERVAL);
//				info("Lap lai lan thu " + tick + 1);
//			} catch (NoSuchElementException ex) {
//				if (tick == timeout / WAIT_INTERVAL) {
//					return null;
//				}
//			} catch (WebDriverException e) {
//				if (tick == timeout / WAIT_INTERVAL) {
//					return null;
//				}
//			} catch (IllegalStateException e) {
//				if (tick == timeout / WAIT_INTERVAL) {
//					return null;
//				}
//			}
//		}
//		return elem;
//	}

	/**
	 * get a display element in web page
	 * 
	 * @param locator @ return
	 */
	public WebElement getDisplayedElement(Object locator, WebDriver dr) {
		By by = locator instanceof By ? (By) locator : By.xpath(locator.toString());	
		WebElement e = null;
		try {
			if (by != null)
				e = dr.findElement(by);
			if (e != null) {
				if (isDisplay(by, dr))
					return e;
			}
		} catch (NoSuchElementException ex) {
		
			pause(WAIT_INTERVAL);
//			getDisplayedElement(locator);
		} catch (StaleElementReferenceException ex) {
			pause(WAIT_INTERVAL);
//			getDisplayedElement(locator);
		} catch (WebDriverException ex) {
			pause(WAIT_INTERVAL);
//			getDisplayedElement(locator);
		} finally {
			loopCount = 0;
		}
		return null;
	}

	/**
	 * checking an element is displayed in web page
	 * 
	 * @param locator
	 * @return
	 */
	public boolean isDisplay(Object locator, WebDriver dr) {
		boolean bool = false;
		WebElement e = getElement(locator, dr);
		try {
			if (e != null)
				bool = e.isDisplayed();
		} catch (StaleElementReferenceException ex) {
			pause(WAIT_INTERVAL);
			isDisplay(locator, dr);
		} finally {
			loopCount = 0;
		}
		return bool;
	}

	

	/**
	 * click on an element
	 * 
	 * @param locator
	 * @param opParams
	 */
//	public void click(Object locator, Object... opParams) {
//		int notDisplay = (Integer) (opParams.length > 0 ? opParams[0] : 0);
//		try {
//			WebElement element = getElementPresent(locator, DEFAULT_TIMEOUT, 1, notDisplay);
//			if (element.isEnabled()) {
//				element.click();
//			} else {
//				info("Element is not enabled");
//				click(locator, notDisplay);
//			}
//		} catch (StaleElementReferenceException e) {
//			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
//			pause(WAIT_INTERVAL);
//			click(locator, notDisplay);
//		} catch (ElementNotVisibleException e) {
//			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
//			pause(WAIT_INTERVAL);
//			click(locator, notDisplay);
//		} catch (NoSuchElementException e) {
//			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
//			pause(WAIT_INTERVAL);
//			click(locator, notDisplay);
//		} finally {
//			loopCount = 0;
//		}
//	}
//
//	/**
//	 * Click on an element of list element
//	 * 
//	 * @param locator
//	 * @param index
//	 */
//	public void clickListElement(Object locator, int index) {
//		List<WebElement> list = getListElement(locator);
//		if (list.size() > 0) {
//			info("So luong element tim duoc: " + list.size());
//			list.get(index).click();
//		}
//	}
//
//	/**
//	 * input data to element
//	 * 
//	 * @param locator
//	 * @param value
//	 * @param validate
//	 */
//	public void type(Object locator, String value, boolean validate, boolean... clear) {
//		boolean clean = clear.length > 0 ? clear[0] : true;
//		try {
//			for (int loop = 1;; loop++) {
//				if (loop >= ACTION_REPEAT) {
//					Assert.fail("Qua thoi gian khi input du lieu: " + value + " vao doi tuong " + locator);
//				}
//				WebElement element = getElementPresent(locator, 1000, 0);
//				if (element != null) {
//					if (clean)
//						element.clear();
//					element.click();
//					element.sendKeys(value);
//					if (!validate || value.equals(getValue(locator))) {
//						break;
//					}
//				}
//				info("Lap lai tac dong input text lan thu " + loop);
//				pause(WAIT_INTERVAL);
//			}
//		} catch (StaleElementReferenceException e) {
//			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
//			pause(WAIT_INTERVAL);
//			type(locator, value, validate);
//		} catch (NoSuchElementException e) {
//			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
//			pause(WAIT_INTERVAL);
//			type(locator, value, validate);
//		} catch (ElementNotVisibleException e) {
//			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
//			pause(WAIT_INTERVAL);
//			type(locator, value, validate);
//		} finally {
//			loopCount = 0;
//		}
//		// driver.navigate().back();
//	}
//
//	public void typeHinput(Object locator, String text, boolean validate) {
//		if (text != null) {
//			if (getElementPresent(locator) != null) {
//				try {
//					WebElement e = getElementPresent(locator);
//					e.click();
//					e.sendKeys(Keys.CONTROL + "a");
//					e.sendKeys(text);
//					for (int i = 0; i < 5; i++) {
//						String am = getValue(locator, 2);
//						if (am != null) {
//							if (am.equalsIgnoreCase(text)) {
//								break;
//							} else {
//								e.sendKeys(Keys.CONTROL + "a");
//								e.sendKeys(text);
//							}
//						}
//					}
//				} catch (StaleElementReferenceException ex) {
//					typeHinput(locator, text, validate);
//				}
//			}
//		}
//	}
//
//	/**
//	 * get value of element in web page
//	 * 
//	 * @param locator
//	 * @return
//	 */
//	public String getValue(Object locator, Object... opParams) {
//		int notDisplay = (Integer) (opParams.length > 0 ? opParams[0] : 0);
//		try {
//			return getElementPresent(locator, DEFAULT_TIMEOUT, 1, notDisplay).getAttribute("text");
//		} catch (StaleElementReferenceException e) {
//			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
//			pause(WAIT_INTERVAL);
//			return getValue(locator);
//		} finally {
//			loopCount = 0;
//		}
//	}
//
//	/**
//	 * get text of element
//	 * 
//	 * @param locator
//	 * @return
//	 */
//	public String getText(Object locator) {
//		WebElement element = null;
//		try {
//			element = getElementPresent(locator);
//			return element.getText();
//		} catch (StaleElementReferenceException e) {
//			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
//			pause(WAIT_INTERVAL);
//			return getText(locator);
//		} finally {
//			loopCount = 0;
//		}
//	}
//
//	/**
//	 * get absolute path of file
//	 * 
//	 * @param relativeFilePath
//	 * @return
//	 */
//	public String getAbsoluteFilePath(String relativeFilePath) {
//		String curDir = System.getProperty("user.dir");
//		String absolutePath = curDir + relativeFilePath;
//		return absolutePath;
//	}
//
//	/**
//	 * compare 2 string
//	 * 
//	 * @param s1
//	 * @param s2
//	 */
//	public void verifyCompare(String s1, String s2) {
//		if (s1 != "" && s1 != null && s2 != null && s2 != "") {
//			Assert.assertFalse(!s1.equalsIgnoreCase(s2), "So sanh khong bang nhau: " + s1 + " va " + s2);
//		} else if ((s1 == "" || s1 == null) && (s2 == "" || s2 == null)) {
//			info("2 truong du lieu can so sanh deu null");
//		} else {
//			Assert.fail("Du lieu so sanh co 1 truong bi null");
//		}
//	}
//
//	/**
//	 * 
//	 * @param dateBefore
//	 * @param dateAfter
//	 */
//	public void compareDateBeforeDate(String dateBefore, String dateAfter) {
//		Boolean compare = false;
//		try {
//			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//			compare = sdf.parse(dateBefore).before(sdf.parse(dateAfter));
//			info("Ket qua so sanh thoi gian: " + compare);
//		} catch (Throwable e) {
//			info("Loi:" + e);
//		}
//		Assert.assertFalse(!compare, "Moc thoi gian " + dateAfter + " khong lon hơn " + dateBefore);
//	}
//
//	public void verifyContains(String s1, String s2) {
//		if (s1 != null && s2 != null && !s2.contains(s1)) {
//			info("Chuỗi " + s1 + " không nằm trong chuỗi " + s2);
//			Assert.assertFalse(false);
//		}
//	}
//
//	/**
//	 * scroll to text in screen
//	 * 
//	 * @param text
//	 */
//	public void scrollToText(String text) {
//		pause(1000);
//		try {
//			driver.scrollToExact(text);
//		} catch (NoSuchElementException e) {
//			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
//			pause(WAIT_INTERVAL);
//			scrollToText(text);
//		} finally {
//			loopCount = 0;
//		}
//		pause(1000);
//	}
//
//	public void swipeVertical(String parentId, String childClass, String childName) {
//		MobileElement element = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator(
//				"new UiScrollable(new UiSelector().resourceId(\"" + parentId + "\")).getChildByText("
//						+ "new UiSelector().className(\"" + childClass + "\"), \"" + childName + "\")"));
//	}
//
//	public void selectMonthFromDatePicker(Object locator, String selectedMonth) {
//		By by = locator instanceof By ? (By) locator : By.xpath(locator.toString());
//
//		WebElement curMonth = null;
//		do {
//			curMonth = driver.findElement(by);
//			((MobileElement) curMonth).tap(1, 2);
//		} while (!curMonth.getText().contains(selectedMonth));
//	}
//
//	public void typeNotClick(Object locator, String value, boolean validate, boolean... clear) {
//		boolean clean = clear.length > 0 ? clear[0] : true;
//		try {
//			for (int loop = 1;; loop++) {
//				if (loop >= ACTION_REPEAT) {
//					Assert.fail("Qua thoi gian khi input du lieu: " + value + " vao doi tuong " + locator);
//				}
//				WebElement element = getElementPresent(locator, 10000, 0);
//				if (element != null) {
//					if (clean)
//						element.clear();
//					element.sendKeys(value);
//					if (!validate || value.equals(getValue(locator))) {
//						break;
//					}
//				}
//				info("Lap lai tac dong input text lan thu " + loop);
//				pause(WAIT_INTERVAL);
//			}
//		} catch (StaleElementReferenceException e) {
//			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
//			pause(WAIT_INTERVAL);
//			typeNotClick(locator, value, validate);
//		} catch (NoSuchElementException e) {
//			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
//			pause(WAIT_INTERVAL);
//			typeNotClick(locator, value, validate);
//		} catch (ElementNotVisibleException e) {
//			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
//			pause(WAIT_INTERVAL);
//			typeNotClick(locator, value, validate);
//		} finally {
//			loopCount = 0;
//		}
//
//	}
//
//	/**
//	 * parse to object from a xpath contains option
//	 * 
//	 * @param xpathOption
//	 * @param option
//	 * @return
//	 */
//	public String parseStringToObject(String xpathOption, String option) {
//		return xpathOption.replaceAll("&option", option);
//	}
//
//	public String trimCharactor(String input, String trim) {
//		info("Xau can xu ly trim: " + input);
//		if (input != "" && input != null && trim != "") {
//			if (trim == ".") {
//				return input.replaceAll("\\.", "");
//			} else {
//				return input.replaceAll(trim, "");
//			}
//		} else
//			return "";
//	}
//
//	/**
//	 * get first day of month of next month
//	 * 
//	 * @param addMonth
//	 * @return
//	 */
//	public String getFirstDayOfMonth(int addMonth) {
//		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//		Calendar cal = Calendar.getInstance();
//		info("Current date: " + df.format(cal.getTime()));
//		cal.add(Calendar.MONTH, addMonth);
//		cal.set(Calendar.DAY_OF_MONTH, 1);
//		return df.format(cal.getTime());
//	}
//
//	public String getExpireDate(int addMonth, int index) {
//		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//		Calendar cal = Calendar.getInstance();
//		info("Current date: " + df.format(cal.getTime()));
//		cal.add(Calendar.MONTH, addMonth);
//		cal.set(Calendar.DAY_OF_MONTH, 1);
//
//		String dateStr = df.format(cal.getTime());
//
//		String[] expireDate = null;
//		expireDate = dateStr.split("/");
//		return expireDate[index];
//
//	}
//
//	/**
//	 * 
//	 * @param tbSearch
//	 * @param textSearch
//	 * @param xpath
//	 */
//	public void selectOptionFromComBoxSearch(Object tbSearch, String textSearch, String xpath) {
//		type(tbSearch, textSearch, false, false);
//		pause(500);
//		click(By.xpath(xpath.replaceAll("&option", textSearch)));
//		pause(1000);
//	}
//
//	public void selectOptionFromCombobox(String xpath, String option) {
//		if (option != null) {
//			String locator = xpath.replaceAll("&option", option);
//			click(locator);
//		}
//	}
//
//	/**
//	 * Open page at not loaded status, as clear cache
//	 * 
//	 * @param pageUrl
//	 * @param driver
//	 */
//	public void openPageNotLoad(String pageUrl, WebDriver driver) {
//		if (pageUrl != null) {
//			driver.get(pageUrl);
//			pause(2000);
//		}
//	}
//
//	/**
//	 * accept unexpected alert
//	 */
//	public void acceptAlert() {
//		try {
//			Alert alert = driver.switchTo().alert();
//			alert.accept();
//
//		} catch (NoAlertPresentException ex) {
//			info("No Alert present");
//			;
//		}
//	}
//
//	/**
//	 * 
//	 * @param urlText
//	 * @param data
//	 * @param column
//	 * @return
//	 */
//	public static String[] callWS(String urlText, String data, String... column) {
//		String output = "";
//		String[] da = new String[100];
//		try {
//			URL url = new URL(urlText);
////		    URLConnection connection = url.openConnection();
//			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
//			System.out.println("*************************//////////////////////////");
//
////		    connection.setDoOutput(true);
////		    connection.setRequestProperty("Acceptcharset", "en-us");
////		    connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
////		    connection.setRequestProperty("charset", "EN-US");
////		    connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
////		    connection.setRequestProperty("Content-Type", "text/xml");
////		    connection.setRequestProperty("Accept", "text/xml");
//
//			httpConnection.setDoOutput(true);
//			System.out.println("*************************" + httpConnection.getResponseCode() + "/////"
//					+ httpConnection.getHeaderFields());
//			if (httpConnection.getResponseCode() == 200) {
//				System.out.println("////////////////////**************The URL is not accessed sucessfully!");
//
//			} else {
//				OutputStreamWriter osw = new OutputStreamWriter(httpConnection.getOutputStream());
//				osw.write(data);
//				osw.flush();
//				osw.close();
//				InputStream in = httpConnection.getInputStream();
////			    output = read(in);
//				DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//				InputSource src = new InputSource();
//				src.setCharacterStream(new StringReader(output));
//				Document doc = builder.parse(src);
//				if (column.length > 0) {
//					for (int i = 0; i < column.length; i++) {
//						da[i] = doc.getElementsByTagName(column[i]).item(0).getTextContent();
//						info("Gia tri trong tag " + column[i] + "là " + da[i]);
//					}
//				}
//			}
//		} catch (IOException | ParserConfigurationException | SAXException e) {
//			e.printStackTrace();
//		}
//		return da;
//	}
//
//	public boolean objectIsNull(Object obj) {
//		if (getElementPresent(obj) != null)
//			return false;
//		return true;
//	}
//
//	/**
//	 * 
//	 * @param input
//	 * @return
//	 * @throws IOException
//	 */
//	public static String read(InputStream input) throws IOException {
//		try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
//			return buffer.lines().collect(Collectors.joining("\n"));
//		}
//	}
//
//	/**
//	 * get message from Toast object
//	 * 
//	 * @return
//	 */
//	public String getMessageToast() {
//		WebElement toast = getElementPresent(By.xpath("//android.widget.Toast[1]"), DEFAULT_TIMEOUT, 1, 2);
//		String message = toast.getAttribute("name");
//		info("message Toast is: " + message);
//		return message;
//	}
//
//	/**
//	 * 
//	 * @param elementStart
//	 * @param elementEnd
//	 */
//	public void scrollUsingTouchAction(Object elementStart, Object elementEnd) {
//		WebElement start = getElementPresent(elementStart);
//		WebElement end = getElementPresent(elementStart);
//		TouchAction actions = new TouchAction(driver);
//		actions.press(start).waitAction(WAIT_INTERVAL).moveTo(end).release().perform();
//	}
//
//	public void scrollToElementUsingTouchAction(Object element) {
//		int startX = (int) ((driver.manage().window().getSize().getWidth()) / 2);
//		int topY = (int) ((driver.manage().window().getSize().getHeight()) * 1 / 8);
//		int bottomY = (int) ((driver.manage().window().getSize().getHeight()) * 4 / 5);
//
//		TouchAction actions = new TouchAction(driver);
//		WebElement el = getElementPresentNoAssert(element, 5000);
//		info("Doi tuong can scroll den: " + el);
//		int i = 0;
//		while ((el == null) || (!el.isDisplayed())) {
//			i++;
//			actions.press(startX, bottomY).waitAction(2000).moveTo(startX, topY).release().perform();
//			if (i == 10) {
//				break;
//			}
//			el = getElementPresentNoAssert(element, 5000);
//		}
//	}
//
//	public void scrollOnUIPickerView(Object element, int... soLanScroll) {
//		int soLan = (Integer) (soLanScroll.length > 0 ? soLanScroll[0] : 10);
//		int startX = (int) ((driver.manage().window().getSize().getWidth()) / 2);
//		int topY = (int) ((driver.manage().window().getSize().getHeight()) * 0.75);
//		int bottomY = (int) ((driver.manage().window().getSize().getHeight()) * 4 / 5);
//
//		TouchAction actions = new TouchAction(driver);
//		WebElement el = getElementPresentNoAssert(element, 5000);
//		info("Doi tuong can scroll den: " + element);
//		int i = 0;
//		while (el == null) {
//			i++;
//			actions.press(startX, bottomY).waitAction(2000).moveTo(startX, topY).release().perform();
//			if (i == soLan) {
//				break;
//			}
//			el = getElementPresentNoAssert(element, 5000);
//		}
//	}
//
//	public void selectValueFromUIPickerWheel(Object uiPicker, String value) {
//		WebElement pick = getElementPresent(uiPicker);
//		pick.sendKeys(value);
//	}
//
//	public void scrollByTouchActionIOS(Object element) {
//		TouchActions action = new TouchActions(driver);
//		action.scroll(getElementPresent(element), 10, 100);
//		action.perform();
//	}
}