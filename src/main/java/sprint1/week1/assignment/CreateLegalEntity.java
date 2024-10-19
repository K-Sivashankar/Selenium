package sprint1.week1.assignment;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import java.time.Duration;

public class CreateLegalEntity {


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
    public void test() throws InterruptedException {
        driver.findElement(By.xpath("//button[@title='App Launcher']")).click();
        driver.findElement(By.xpath("//button[@aria-label='View All Applications']")).click();
        Thread.sleep(3000);
        WebElement LegalEntities = driver.findElement(By.xpath("//p[.='Legal Entities']"));
        try {
            LegalEntities.click();
        } catch (Exception e) {
            driver.executeScript("arguments[0].click()", LegalEntities);
        }

        Thread.sleep(2000);
        WebElement icon = driver.findElement(By.xpath("//a[@title='Legal Entities']//following::lightning-icon[1]"));

        driver.executeScript("arguments[0].click();",icon);

        WebElement NewLegalEntity_Link = driver.findElement(By.xpath("//span[.='New Legal Entity']//lightning-primitive-icon"));
        try {
            NewLegalEntity_Link.click();
        } catch (Exception e) {
            driver.executeScript("arguments[0].click()",NewLegalEntity_Link);
        }
        String LegalEntityName = "Salesforce automation by Sivashankar";
        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@name='Name']")).sendKeys(LegalEntityName);
        driver.findElement(By.xpath("//button[@name='SaveEdit']")).click();

        Thread.sleep(2000);
        String toastMsgText = driver.findElement(By.xpath("//div[starts-with(@id,'toast')]/span")).getText();
        boolean verification = toastMsgText.contains(LegalEntityName);
        Assert.assertTrue(verification);


    }
}
