package junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import static org.assertj.core.api.Assertions.assertThat;

public class JUnitTests {

    WebDriver driver;

    @Before
    public void before() {
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
    }

    @Test
    public void findZipCodeByAddress() throws InterruptedException {
        openUspsPage();
        navigateToLookUpZip();
        clickFindByAddress();
        fillInRequiredFields();
        clickFindButton();
        verifyZipCode();
    }

    private void openUspsPage() {
        driver.get("https://www.usps.com");
    }

    /**
     * Navigate to Look up by zip code
     */
    private void navigateToLookUpZip() {
        new Actions(driver).moveToElement(driver.findElement(By.xpath("//*[@class='qt-nav menuheader']"))).perform();
        driver.findElement(By.xpath("//*[@alt='Zip Code™ Lookup Icon']")).click();
    }

    /**
     * Choose by address
     */
    private void clickFindByAddress() {
        driver.findElement(By.xpath("//*[@data-location='byaddress']")).click();
    }

    /**
     * Populate all required fields
     */
    private void fillInRequiredFields() {
        driver.findElement(By.xpath("//*[@id='tAddress']")).sendKeys("One Apple Park Way");
        driver.findElement(By.xpath("//*[@id='tCity']")).sendKeys("Cupertino");
        driver.findElement(By.xpath("//*[@id='tState']")).click();
        driver.findElement(By.xpath("//option[@value='CA']")).click();
    }

    /**
     * Click Find button
     */
    private void clickFindButton() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id='zip-by-address']")).click();
        Thread.sleep(500);
    }

    /**
     * Verify result
     */
    private void verifyZipCode() {
        String result = driver.findElement(By.xpath("//*[@class='zipcode-result-address']")).getText();
        System.out.println(result);
        assertThat(result).contains("95014-0642");
    }

    @After
    public void after() {
        driver.quit();
    }
}
