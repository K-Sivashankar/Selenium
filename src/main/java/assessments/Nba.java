package assessments;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Nba {
    String browserName = "chrome";
    RemoteWebDriver driver = null;
    ChromeOptions chromeOptions=null;
    EdgeOptions edgeOptions=null;
   /* @BeforeMethod
    public void setUpGrid() throws MalformedURLException {
        chromeOptions= new ChromeOptions();
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.setCapability("platformName", Platform.LINUX);
        chromeOptions.setCapability("browserVersion", "129.0");

        edgeOptions = new EdgeOptions();
        edgeOptions.addArguments("--disable-notifications");
        edgeOptions.setCapability("platformName", Platform.LINUX);
        edgeOptions.setCapability("browserVersion", "129.0");

        if (browserName.equals("chrome")) {
            driver = new RemoteWebDriver(new URL("http://20.40.48.160:4444/wd/hub"), chromeOptions);

        }else
        {
            driver = new RemoteWebDriver(new URL("http://20.40.48.160:4444/wd/hub"), edgeOptions);

        }


    }*/


    @Test
    public void test()
    {
        chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");  // Optional: Run in headless mode
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://www.nba.com/stats");

        try {
            WebElement element = getElement("//button[text()='I Accept']");
            if(element.isDisplayed())
            {
                element.click();
            }
        } catch (Exception e) {
            System.out.println("No Element :: I Accept is present");
        }

        click("//a[text()='See All Player Stats']");
        //select season
        WebElement eleSeason = getElement("//p[text()='Season']/following::select[1]");
        new Select(eleSeason).selectByVisibleText("2023-24");
        WebElement eleSeasonType = getElement("//p[text()='Season Type']/following::select[1]");
        new Select(eleSeasonType).selectByVisibleText("NBA Cup");
        WebElement elePerMode = getElement("//p[text()='Per Mode']/following::select[1]");
        new Select(elePerMode).selectByVisibleText("Per Game");
        WebElement eleSeasonSegment = getElement("//p[text()='Season Segment']/following::select[1]");
        new Select(eleSeasonSegment).selectByVisibleText("Last Game");

        List<String> ages = allElements("(//table)[last()]//tbody//tr/td[4]").stream().map(e -> e.getText().trim()).collect(Collectors.toList());
        Collections.sort(ages);
        String youngestAge = ages.get(0);

        WebElement playerName = getElement("(//table)[last()]//tbody//tr/td[text()='" + youngestAge + "']/preceding-sibling::td[2]/a");
        String textPlayername=playerName.getText().trim();
        playerName.click();

        //click on Profile
        click("(//a[text()='Profile'])[1]");

        String actualExperience = getElement("(//p[text()='EXPERIENCE']/following-sibling::p)[1]").getText();
        System.out.println("Actual Exp :: "+actualExperience);
        System.out.println("Youngest age "+youngestAge);
        System.out.println("Playername :: "+textPlayername);
        Assert.assertTrue(actualExperience.contains("1"));



    }
    public List<WebElement> allElements(String xpath)
    {
        elementPresent();

        return driver.findElements(By.xpath(xpath));
    }
    public WebElement getElement(String xpath)
    {
        elementPresent();
        WebElement element = driver.findElement(By.xpath(xpath));
        new WebDriverWait(driver,Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(element));
        return element;
    }
    public void click(String xpath)
    {
        WebElement eleToClick = driver.findElement(By.xpath(xpath));
        try {

            driver.executeScript("arguments[0].click();",eleToClick);
        } catch (StaleElementReferenceException e) {
            elementPresent();
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.stalenessOf(eleToClick));
            eleToClick = driver.findElement(By.xpath(xpath));
            driver.executeScript("arguments[0].click();",eleToClick);
        }
        catch(ElementClickInterceptedException e)
        {
            elementPresent();
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(eleToClick));
            driver.executeScript("arguments[0].click();",eleToClick);

        }catch(NoSuchElementException e)
        {
            elementPresent();
            new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.visibilityOf(eleToClick));
            System.out.println("Trying to click this element :: "+xpath);
            driver.executeScript("arguments[0].click();",eleToClick);
        }

    }

    public void elementPresent()
    {
        List<WebElement> ads = new ArrayList<>();
        String xpath1="//button[@data-click='close' and preceding-sibling::div[contains(text(),'Click to subscribe to League Pass')]]";
        String xpath2="(//button[preceding::div[text()='Click to sign up for NBA ID']])[1]";
        String xpath3="(//button[preceding::div[text()='Enter your email to create an NBA ID']])[1]";
        List<String> list = Arrays.asList(xpath1, xpath2, xpath3);
        for(String s: list)
        {
            try
            {
                ads.add(driver.findElement(By.xpath(s)));
            }
            catch (Exception e)
            {

            }

        }


        if(ads.size()>0)
        {
           for (WebElement element: ads)
           {
               element.click();
           }
        }
        ads.clear();
    }


}
