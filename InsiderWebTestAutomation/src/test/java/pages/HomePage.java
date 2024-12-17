package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class HomePage {
    private static final Logger logger = LoggerFactory.getLogger(HomePage.class);

    private final WebDriver driver;

    @FindBy(xpath = "//img[@alt='insider_logo']")
    private WebElement insiderLogo;
    @FindBy(xpath = "/html//div[@id='announce']")
    private WebElement announceBar;
    @FindBy(xpath = "//div[@id='navbarNavDropdown']/ul[@class='navbar-nav']//a[@href='https://useinsider.com/careers/']")
    private WebElement careersMenu;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void checkHomePage() {
        waitForVisibility(insiderLogo,5);
        Assert.assertTrue(insiderLogo.isDisplayed(), "Insider logo is not found.Insider home page is not opened.");
        logger.info("Insider logo is viewed.");
        Assert.assertTrue(announceBar.isDisplayed(), "Announce bar is not found. Insider home page is not opened.");
        logger.info("Announce bar is viewed.");
        logger.info("Homepage is opened.");
    }

    public void selectNavigationbarItem(String item) {
        List<WebElement> navbarItems = driver.findElements(By.xpath("//a[@id='navbarDropdownMenuLink']"));

        for (WebElement element : navbarItems)
            if (element.getText().contains(item)) {
                element.click();
                logger.info(item + "is clicked.");
                break;
            }
    }

    public CareersPage selectCareers() {
        careersMenu.click();
        logger.info("Careers menu is clicked.");
        return new CareersPage(driver);
    }

    private void waitForVisibility(WebElement element, long timeoutInSecond){
        WebDriverWait wait = new WebDriverWait(driver, Duration.of(timeoutInSecond, ChronoUnit.SECONDS ));
        wait.until(ExpectedConditions.visibilityOf(element));
    }
}

