import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.junit.Rule;


public class TestJunit {

	static WebDriver driver;
	 @Rule public TestName name = new TestName();

	@Before
	public  void launchingApplication() throws InterruptedException{
		System.setProperty("webdriver.chrome.driver", ".\\src\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.navigate().to("https://todomvc.com/examples/angular2/");
		driver.manage().window().maximize();
		Thread.sleep(2000);
	}

		
	@Test
	public void testLoadingHomepage() {
		assertEquals("Angular2 • TodoMVC", driver.getTitle().trim());
		WebElement txtTodos = driver.findElement(By.xpath("//h1[text()='todos']"));
		assertEquals("todos", txtTodos.getText().trim());
		try {
			takeSnapShot(driver, "C://Reports//"+name.getMethodName()+".png");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void enterTodos(String item) {
		WebElement inputTodos = driver.findElement(By.xpath("//input"));
		inputTodos.clear();
		inputTodos.sendKeys(item.trim());
		inputTodos.sendKeys(Keys.ENTER);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void checkedTodos(String item) {
		WebElement checkDoneTodos = driver.findElement(By.xpath("//div[label='"+item+"']//preceding-sibling::input[@type='checkbox']"));
		checkDoneTodos.click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void removingTodos() {
		WebElement clearTodos = driver.findElement(By.xpath("//button[text()='Clear completed']"));
		clearTodos.click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAddingItems() {
	String [] items = {"Grocery","Movies","Travel","Meeting"};
	for (int i = 0; i < items.length; i++) {
		enterTodos(items[i]);
	}
	List<WebElement> itemList = driver.findElements(By.tagName("label"));
	assertEquals(items.length, itemList.size());
	try {
		takeSnapShot(driver, "C://Reports//"+name.getMethodName()+".png");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	@Test
	public void testCheckingItems() {
		String [] items = {"Grocery","Movies","Travel","Meeting"};
		for (int i = 0; i < items.length; i++) {
			enterTodos(items[i]);
		}
		List<WebElement> itemList = driver.findElements(By.tagName("label"));
		WebElement todoCount = driver.findElement(By.xpath("//*[@class='todo-count']/strong"));
		int totalitemsLeft = Integer.parseInt(todoCount.getText());
		assertEquals(totalitemsLeft, itemList.size());
		/// Assuming added only 4 to do which mentioned in previous test
		checkedTodos("Grocery");
		checkedTodos("Movies");
		try {
			takeSnapShot(driver, "C://Reports//"+name.getMethodName()+".png");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRemovingItems() {
		String [] items = {"Grocery","Movies","Travel","Meeting"};
		for (int i = 0; i < items.length; i++) {
			enterTodos(items[i]);
		}
		checkedTodos("Grocery");
		checkedTodos("Movies");
		removingTodos();
		WebElement todoCountAfterRemove = driver.findElement(By.xpath("//*[@class='todo-count']/strong"));
		int totalitemsLeftAfterRemove = Integer.parseInt(todoCountAfterRemove.getText());
		assertEquals(2,totalitemsLeftAfterRemove);
		try {
			takeSnapShot(driver, "C://Reports//"+name.getMethodName()+".png");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 
	
	//// Negative tests
	
	@Test
	public void testLoadingHomepage_negative() {
		assertNotEquals("Angular2", driver.getTitle().trim());
		WebElement txtTodos = driver.findElement(By.xpath("//h1[text()='todos']"));
		assertNotEquals("todo", txtTodos.getText().trim());
	}
	
	@Test
	public void negative_testAddingItems() {
	String [] items = {"Grocery","Movies","Meeting"};
	for (int i = 0; i < items.length; i++) {
		enterTodos(items[i]);
	}
	List<WebElement> itemList = driver.findElements(By.tagName("label"));
	assertNotEquals(items.length+1, itemList.size());
	try {
		takeSnapShot(driver, "C://Reports//"+name.getMethodName()+".png");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}

	@Test
	public void negative_testCheckingItems() {
		String [] items = {"Grocery","Movies","Meeting"};
		for (int i = 0; i < items.length; i++) {
			enterTodos(items[i]);
		}
		List<WebElement> itemList = driver.findElements(By.tagName("label"));
		WebElement todoCount = driver.findElement(By.xpath("//*[@class='todo-count']/strong"));
		int totalitemsLeft = Integer.parseInt(todoCount.getText());
		assertNotEquals(totalitemsLeft+1, itemList.size());
		/// Assuming added only 4 to do which mentioned in previous test
		checkedTodos("Grocery");
		checkedTodos("Movies");
		try {
			takeSnapShot(driver, "C://Reports//"+name.getMethodName()+".png");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void negative_testRemovingItems() {
		String [] items = {"Grocery","Movies","Meeting","Flight"};
		for (int i = 0; i < items.length; i++) {
			enterTodos(items[i]);
		}
		checkedTodos("Grocery");
		removingTodos();
		WebElement todoCountAfterRemove = driver.findElement(By.xpath("//*[@class='todo-count']/strong"));
		int totalitemsLeftAfterRemove = Integer.parseInt(todoCountAfterRemove.getText());
		assertNotEquals(2,totalitemsLeftAfterRemove);
		try {
			takeSnapShot(driver, "C://Reports//"+name.getMethodName()+".png");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@After
	public  void quitingBrowser() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.quit();
	}
	
	 public static void takeSnapShot(WebDriver webdriver,String fileWithPath) throws Exception{
	        TakesScreenshot scrShot =((TakesScreenshot)webdriver);
	                File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
	                File DestFile=new File(fileWithPath);
	                FileUtils.copyFile(SrcFile, DestFile);
	    }
	}
