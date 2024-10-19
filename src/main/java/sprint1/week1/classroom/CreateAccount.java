package sprint1.week1.classroom;

import java.time.Duration;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.annotations.Test;

public class CreateAccount {

    @Test
    public void test() {

        ChromeDriver driver = new ChromeDriver();
        driver.get("https://login.salesforce.com");
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        driver.findElement(By.id("username")).sendKeys("gokul.sekar@testleaf.com");
        driver.findElement(By.id("password")).sendKeys("Leaf$321");
        driver.findElement(By.id("Login")).click();

//		Click on toggle menu button from the left corner
        driver.findElement(By.xpath("//button[@title='App Launcher']")).click();

//		Click view All and click Sales from App Launcher

        driver.findElement(By.xpath("//button[@aria-label='View All Applications']")).click();

        driver.findElement(By.xpath("//p[text()='Sales']")).click();
//		Click on Accounts tab
//		driver.findElement(By.xpath("//a[@title='Accounts']")).click();-->Exception
        WebElement element = driver.findElement(By.xpath("//a[@title='Accounts']"));
        driver.executeScript("arguments[0].click();", element);
//		Click on New button
        driver.findElement(By.xpath("//a[@title='New']")).click();
//		Enter 'your name' as account name
        driver.findElement(By.xpath("//label[contains(text(),'Account Name')]/following::input[1]")).sendKeys("Siva");
//		Select Ownership as Public  

        WebElement element2 = driver.findElement(By.xpath("//label[contains(text(),'Ownership')]/following::button[1]"));
        driver.executeScript("arguments[0].scrollIntoView(true);", element2);
        element2.click();
//driver.findElement(By.xpath("//button[@aria-label='Ownership']")).click();

        driver.findElement(By.xpath("//span[@title='Public']")).click();
//		Click save and verify Account name 
        driver.findElement(By.xpath("//button[@name='SaveEdit']")).click();
//		Expected Result:
//		Account should be created Successfully

        String text = driver.findElement(By.xpath("//div[starts-with(@id,'toastDescription')]/span")).getText();
        System.out.println(text);


    }

}
