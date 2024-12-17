package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class QualityAssurancePage {
    private static final Logger logger = LogManager.getLogger(QualityAssurancePage.class);
    WebDriver driver;

    @FindBy(xpath = "//section[@id='page-head']/div[@class='container']//h1")
    private WebElement qualityAssuranceTitle;
    @FindBy(xpath = "//section[@id='page-head']/div[@class='container']//a")
    private WebElement seeAllQAJobsButton;

    public QualityAssurancePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void checkQualityAssurancePage() {
        Assert.assertTrue(qualityAssuranceTitle.isDisplayed(), "Quality assurance title is not found.");
        logger.info("Quality assurance title is viewed.");
        Assert.assertTrue(seeAllQAJobsButton.isDisplayed(), "See all QA jobs button is not found.");
        logger.info("See all QA jobs button is viewed.");
    }

    public QualityAssuranceOpenPositionsPage clickSeeAllQAJobsButton() {
        seeAllQAJobsButton.click();
        logger.info("See all QA jobs button is clicked.");
        return new QualityAssuranceOpenPositionsPage(driver);
    }
}
