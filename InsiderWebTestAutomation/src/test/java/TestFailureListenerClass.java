import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestFailureListenerClass extends BaseTest implements ITestListener {
    private static final Logger logger = LoggerFactory.getLogger(TestFailureListenerClass.class);

    public void onTestFailure(ITestResult result){
        try{
            captureScreenshot(result.getName());
        }catch (Exception e){
            logger.error("Capturing screenshot failed", e);
        }
    }
}
