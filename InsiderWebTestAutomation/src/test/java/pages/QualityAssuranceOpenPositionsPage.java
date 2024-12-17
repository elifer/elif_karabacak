package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import static org.testng.Assert.assertTrue;

public class QualityAssuranceOpenPositionsPage {
    private static final Logger logger = LogManager.getLogger(QualityAssuranceOpenPositionsPage.class);
    WebDriver driver;
    WebDriverWait wait;


    @FindBy(xpath = "//span[@id='select2-filter-by-location-container']")
    private WebElement filterByLocationContainer;
    @FindBy(xpath = "//span[@id='select2-filter-by-department-container']")
    private WebElement filterByDepartmentContainer;
    @FindBy(xpath = "//div[@id='jobs-list']/div[1]//a")
    private WebElement viewRoleButton;
    @FindBy(xpath = "//div[@id='jobs-list']/div[1]/div/p")
    private WebElement firstJobTitle;



    public QualityAssuranceOpenPositionsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.of(30, ChronoUnit.SECONDS));
        PageFactory.initElements(driver, this);
    }

    public void selectFilterLocation(String item) {
        By locationOptions = By.xpath("//select[@id='filter-by-location']/option[.='" + item + "']");
        wait.until(ExpectedConditions.presenceOfElementLocated(locationOptions));
        filterByLocationContainer.click();
        logger.info("Filter by Location is clicked.");
        List<WebElement> filterLocationList = driver.findElements(By.xpath("//ul[@id='select2-filter-by-location-results']/li"));

        for (WebElement element : filterLocationList) {
            logger.info("element {}", element.getText());
            if (element.getText().contains(item)) {
                element.click();
                logger.info(item + "is selected.");
                break;
            }
        }
    }

    public void selectFilterDepartment(String item) {
        filterByDepartmentContainer.click();
        logger.info("Filter by Department is clicked.");
        List<WebElement> filterDepartmentList = driver.findElements(By.xpath("//ul[@id='select2-filter-by-department-results']/li"));

        for (WebElement element : filterDepartmentList)
            if (element.getText().contains(item)) {
                element.click();
                logger.info(item + "is selected.");
                break;
            }
    }

    public void checkOpenPositions(String position, String department, String location, String departmentTag) {

        wait.until((driver) -> {
            List<WebElement> openPositionsContainerList = driver.findElements(By.xpath("//div[@id='jobs-list']/div"));
            if (openPositionsContainerList.isEmpty()) {
                return false;
            }
            return openPositionsContainerList.stream()
                    .allMatch(webElement -> departmentTag.equals(webElement.getAttribute("data-team")));
        });

        List<WebElement> openPositionsContainerList = driver.findElements(By.xpath("//div[@id='jobs-list']/div"));
        for (int i = 1; i <= openPositionsContainerList.size(); i++) {
            By positionXPath = By.xpath("//div[@id='jobs-list']/div[" + i + "]/div/p");
            By departmentXPath = By.xpath("//div[@id='jobs-list']/div[" + i + "]/div/span");
            By locationXPath = By.xpath("//div[@id='jobs-list']/div[" + i + "]/div/div");

            wait.until(visibilityOfElementLocated(positionXPath));
            wait.until(visibilityOfElementLocated(departmentXPath));
            wait.until(visibilityOfElementLocated(locationXPath));

            WebElement positionElement = driver.findElement(positionXPath);
            WebElement departmentElement = driver.findElement(departmentXPath);
            WebElement locationElement = driver.findElement(locationXPath);

            assertTrue(positionElement.getText().contains(position) || positionElement.getText().contains("QA"), position + " position text is not found.");
            assertTrue(departmentElement.getText().contains(department), department + " department text is not found.");
            assertTrue(locationElement.getText().contains(location), location + " location text is not found.");
        }
    }

    public void clickViewRole() {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", viewRoleButton);
        logger.info("View role button is click.");
    }

    public void switchNewWindow(String jobTitle) {
        Object[] windowHandles = driver.getWindowHandles().toArray();
        driver.switchTo().window((String) windowHandles[1]);
        String title = driver.getTitle();
        assertTrue(jobTitle.equals(title), "Selected Job Title and Lever Application Page title is not equal.");
    }

    public String getFirstJobTitle() {
        return firstJobTitle.getText();
    }
}
