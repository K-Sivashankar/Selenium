package sprint1.week1.classroom;

/*
 * https://www.cheapoair.com/
 *1. Select one way trip
 *2. Select Destination
 *3. Select Travel date
 *4. Select 2 adults & Click on Done
 *5. Click search Flights
 *6. Write a logic to find the lowest price and Select the lowest price flight
 * 
 */

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class CheapOAir {

	public static void main(String[] args) throws InterruptedException {
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.get("https://www.cheapoair.com/");

		WebElement onewayTrip = driver.findElement(By.id("onewayTrip"));
	try	{
			onewayTrip.click();
		}catch(ElementClickInterceptedException e)
	{
		driver.executeScript("arguments[0].click()",onewayTrip);
	}

		WebElement from = driver.findElement(By.id("from0"));
		from.sendKeys(Keys.CONTROL+"a");
		from.sendKeys(Keys.DELETE);
		Thread.sleep(2000);
		from.sendKeys("MAA",Keys.TAB);
		Thread.sleep(2000);

		WebElement textBox=driver.findElement(By.id("to0"));
//		textBox.sendKeys(Keys.CONTROL+"a"+Keys.DELETE);
		new Actions(driver).click(textBox).sendKeys(Keys.END).keyDown(Keys.SHIFT).sendKeys(Keys.HOME)
				.keyUp(Keys.SHIFT).sendKeys(Keys.BACK_SPACE).build().perform();
//		textBox.sendKeys(Keys.DELETE);
		Thread.sleep(2000);
		driver.findElement(By.id("to0")).sendKeys("BLR",Keys.ENTER);
		driver.findElement(By.id("to0")).sendKeys(Keys.TAB);
		driver.findElement(By.id("cal0")).click();
		driver.findElement(By.xpath("//a[contains(@class,'today')]")).click();
		driver.findElement(By.xpath("//div[text()='Traveler']")).click();
		driver.findElement(By.id("addadults")).click();
		driver.findElement(By.id("closeDialog")).click();
		driver.findElement(By.id("searchNow")).click();
		Thread.sleep(4000);
		List<WebElement> allElements = driver.findElements(By.xpath("//header[@data-test='selectContract']//span[@title]"));
		List<Double> allPrice = new ArrayList<Double>();
		for (WebElement eachEle : allElements) {
			String priceInString = eachEle.getAttribute("title");
			double priceInDouble = Double.parseDouble(priceInString);
			allPrice.add(priceInDouble);
		}

		Collections.sort(allPrice); // Write a logic to find the least price
		Double least = allPrice.get(0);
		System.out.println(allPrice);
		System.out.println("--------------------------");
		allPrice.forEach(d-> System.out.println(d));

		WebElement element = driver.findElement(By.xpath("//span[contains(@title,'" + least + "')]/following::span[text()='Select']"));
        try {
            element
                    .click();
        } catch (Exception e) {
            driver.executeScript("arguments[0].click()",element);
        }

    }

}

