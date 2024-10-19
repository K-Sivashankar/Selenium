package sprint1.week1.assignment;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class EditCase_Sales {
    @Test
    public void testEditCase() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");

        ChromeDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
//        Login to https://login.salesforce.com

        driver.get("https://virtusa-2df-dev-ed.develop.my.salesforce.com/");
        driver.findElement(By.id("username")).sendKeys("shankar@sdet.com");
        driver.findElement(By.id("password")).sendKeys("Shkr@9497");
        driver.findElement(By.id("Login")).click();
//          Click on toggle menu button from the left corner
        driver.findElement(By.xpath("//button[@title='App Launcher']")).click();
//        Click view All and click Sales from App Launcher
        driver.findElement(By.xpath("//button[@aria-label='View All Applications']")).click();
        driver.findElement(By.xpath("//p[text()='Sales']")).click();

//       4. Click on Cases tab visible or select from more.
        Thread.sleep(2000);
        driver.findElement(By.xpath("//span[text()='More']")).click();
        try {
            driver.findElement(By.xpath("//a[contains(@href,'Case') and @role='menuitem']")).click();
        } catch (JavascriptException e) {
            WebElement CaseLink = driver.findElement(By.xpath("//a[contains(@href,'Case') and @role='menuitem']"));

            driver.executeScript("arguments[0].click();", CaseLink);
        }

        String caseID = driver.findElement(By.xpath("//tbody/tr/th[1]//a")).getText().trim();
//5. Click on the Dropdown icon and select Edit from the case you created by reffering 'case owner alias'
        driver.findElement(By.xpath("(//a/span/span[text()='Show Actions']/../..)[1]")).click();
        driver.findElement(By.xpath("//a[@title='Edit']")).click();

//6. Update Status as Working
        driver.findElement(By.xpath("//button[@aria-label='Status']")).click();
        driver.findElement(By.xpath("//div[@aria-label='Status']/*[@*='Working']")).click();
//7. Update Priority to low
        driver.findElement(By.xpath("//button[@aria-label='Priority']")).click();
        driver.findElement(By.xpath("//div[@aria-label='Priority']/*[@*='Low']")).click();
//8. Update Case Origin as Phone
        WebElement caseOrigin_Button = driver.findElement(By.xpath("//button[@aria-label='Case Origin']"));
        try {
            caseOrigin_Button.click();
        } catch (ElementClickInterceptedException e) {
            driver.executeScript("arguments[0].scrollIntoView(true);", caseOrigin_Button);
            caseOrigin_Button.click();
        }
       Thread.sleep(2000);
        WebElement PhoneOption_CaseOrigin = driver.findElement(By.xpath("//lightning-base-combobox-item[@data-value='Phone']"));

        try {
            PhoneOption_CaseOrigin.click();
        } catch (NoSuchElementException e) {
            caseOrigin_Button.click();
            Thread.sleep(2000);
            PhoneOption_CaseOrigin.click();


        }
//9. Update SLA violation to No
        WebElement SLAViolation_Button = driver.findElement(By.xpath("//button[@aria-label='SLA Violation']"));
        try {
            SLAViolation_Button.click();
        } catch (ElementClickInterceptedException e) {
            driver.executeScript("arguments[0].scrollIntoView(true);", SLAViolation_Button);
            SLAViolation_Button.click();
        }
        driver.findElement(By.xpath("//div[@aria-label='SLA Violation']/*[@data-value='No']")).click();
//10. Click on Save and Verify Status as Working
        driver.findElement(By.xpath("(//button[contains(text(),'Save')])[last()]")).click();
//"Expected result:
//Case is Edited Successfully  and Status is working"
        Thread.sleep(2000);
        String toastMsg = driver.findElement(By.xpath("//div[starts-with(@id,'toastDescription')]/span")).getText().trim();

        String expectedText = "Case \"" + caseID + "\" was saved.";
        Assert.assertTrue(toastMsg.contains(expectedText));
        String status = driver.findElement(By.xpath("//tbody/tr[1]/td[4]/span/span[1]")).getText().trim();
        Assert.assertTrue(status.equals("Working"));
    }
}
