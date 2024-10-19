package sprint1.week1.classroom;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import io.github.sukgu.Shadow;

public class ServiceNowMobileOrdering {

	@Test
	public  void runSN() throws InterruptedException, IOException {
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
//		DesiredCapabilities dc = new DesiredCapabilities();
//		dc.setPlatform(Platform.WIN10);
//		dc.setBrowserName("chrome");
//		dc.setVersion();
//		RemoteWebDriver driver = new RemoteWebDriver(new URL(" http://192.168.29.166:4444"), dc);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://dev120081.service-now.com/");
		driver.findElement(By.id("user_name")).sendKeys("admin");
		driver.findElement(By.id("user_password")).sendKeys("$AWWF0/a2afr");
		driver.findElement(By.id("sysverb_login")).click();
		Shadow shadow = new Shadow(driver);
		Thread.sleep(10000);
		shadow.setImplicitWait(5);
		shadow.findElementByXPath("//div[@aria-label='All']").click();
		shadow.findElementByXPath("//input[@id='filter']").sendKeys("Service Catalog");
		shadow.findElementByXPath("//input[@id='filter']").sendKeys(Keys.ENTER);
		WebElement frame = shadow.findElementByXPath("//iframe[@title='Main Content']");
		driver.switchTo().frame(frame);
		driver.findElement(By.xpath("//a[text()='Mobiles']")).click();
		driver.findElement(By.xpath("//strong[text()='Apple iPhone 13 pro']/ancestor::a")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@value='yes']/following-sibling::label")).click();
		driver.findElement(By.xpath("//input[contains(@class,'sc-content-pad')]")).sendKeys("99");
		WebElement dropDown = driver.findElement(By.xpath("//select[@class='form-control cat_item_option ']"));
		Select dd = new Select(dropDown);
		dd.selectByValue("unlimited");
		driver.findElement(By.xpath("//input[@value='sierra_blue']/following-sibling::label")).click();
		driver.findElement(By.xpath("//input[@value='512']/following-sibling::label")).click();
		driver.findElement(By.xpath("//button[@id='oi_order_now_button']")).click();
		String text = driver.findElement(By.xpath("//a[@id='requesturl']")).getText();
		System.out.println("Request Number: "+text);
		File source = driver.getScreenshotAs(OutputType.FILE);
		File desc = new File("./snap/img.png");
		FileUtils.copyFile(source, desc);
		driver.close();
	}

}
