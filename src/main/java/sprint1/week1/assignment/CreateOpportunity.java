package sprint1.week1.assignment;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class CreateOpportunity {
    ChromeDriver driver;

    @BeforeMethod
    public void Login() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
//        Login to https://login.salesforce.com

        driver.get("https://virtusa-2df-dev-ed.develop.my.salesforce.com/");
        driver.findElement(By.id("username")).sendKeys("shankar@sdet.com");
        driver.findElement(By.id("password")).sendKeys("Shkr@9497");
        driver.findElement(By.id("Login")).click();
    }

    @Test
    public void Test() throws InterruptedException {
//        3. Click view All and click Sales from App Launcher
        driver.findElement(By.xpath("//button[@title='App Launcher']")).click();
        driver.findElement(By.xpath("//button[@aria-label='View All Applications']")).click();

        driver.findElement(By.xpath("//p[text()='Sales']")).click();

//        4. Click on Opportunity tab
        WebElement element = driver.findElement(By.xpath("//a[@title='Opportunities']"));
        try {
            element.click();
        } catch (JavascriptException e) {
            driver.executeScript("arguments[0].click();", element);
        }
//        5. Click on New button
        driver.findElement(By.xpath("//a[@title='New']")).click();

//        6. Enter Opportunity name as 'Salesforce Automation by *Your Name*,Get the text and Store it
        WebElement txt_OpportunityName = driver.findElement(By.xpath("//input[@name='Name']"));
        txt_OpportunityName.sendKeys("Salesforce Automation by Sivashankar");
        String expectedText = txt_OpportunityName.getAttribute("value");



//        7. Choose close date as Today

        driver.findElement(By.xpath("//input[@name='CloseDate']")).click();
        driver.findElement(By.xpath("//button[@name='today']")).click();


//        8. Select 'Stage' as Need Analysis
        driver.findElement(By.xpath("//button[@aria-label='Stage']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//lightning-base-combobox-item/span/span[@title='Needs Analysis']")).click();
//        9. click Save and VerifyOppurtunity Name
        driver.findElement(By.xpath("//button[@name='SaveEdit']")).click();
        Thread.sleep(2000);
        String actualText = driver.findElement(By.cssSelector("div[id^='toast'] >span a>div")).getText();
        Assert.assertTrue(actualText.equals(expectedText));

//        Expected Result:New Opportunity should be created with name as  'Salesforce Automation by *Your Name*'


    }
}
