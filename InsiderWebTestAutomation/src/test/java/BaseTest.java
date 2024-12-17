import com.insider.DriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Properties;

public class BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    protected static WebDriver driver;

    @BeforeMethod
    void setupAll() throws IOException {
        initialize();
    }

    @AfterMethod
    void teardown() {
        if(driver != null){
            driver.quit();
        }
    }

    public void captureScreenshot(String methodName){
        String filePath = "screenshots/%s-%s.png".formatted(methodName, Instant.now());
        try {
            File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(file, new File(filePath));
        } catch (IOException e) {
            logger.error("Unable to locate file screenshot", e);
        }
    }

    public static void initialize() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/resources/config.properties"));
        String browser = properties.getProperty("browser");
        String url = properties.getProperty("url");
        DriverManager.setup(browser);
        driver = DriverManager.getDriver(browser);
        driver.get(url);
        driver.manage().window().maximize();
    }
}
