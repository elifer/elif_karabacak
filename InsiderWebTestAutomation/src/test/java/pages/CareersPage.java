package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class CareersPage {
    private static final Logger logger = LogManager.getLogger(CareersPage.class);
    WebDriver driver;

    @FindBy(xpath = "//section[@id='career-our-location']//h3")
    private WebElement ourLocationsTitle;
    @FindBy(xpath = "//div[@class='elementor-widget-wrap elementor-element-populated e-swiper-container']//h2")
    private WebElement lifeatInsiderTitle;

    public CareersPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void checkCareersPage() {
        waitForVisibility(ourLocationsTitle,5);
        Assert.assertTrue(ourLocationsTitle.isDisplayed(), "Our Locations title is not found.");
        logger.info("Our locations title is viewed.");
        Assert.assertTrue(lifeatInsiderTitle.isDisplayed(), "Life at Insider title is not found.");
        logger.info("Life at Insider title is viewed.");
        logger.info("Careers page is opened.");
    }

    public QualityAssurancePage navigateToQualityAssurancePage(String url) {
        driver.navigate().to(url);
        return new QualityAssurancePage(driver);
    }

    private void waitForVisibility(WebElement element, long timeoutInSecond){
        WebDriverWait wait = new WebDriverWait(driver, Duration.of(timeoutInSecond, ChronoUnit.SECONDS ));
        wait.until(ExpectedConditions.visibilityOf(element));
    }
}
