import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.CareersPage;
import pages.HomePage;
import pages.QualityAssuranceOpenPositionsPage;
import pages.QualityAssurancePage;

@Listeners(TestFailureListenerClass.class)
public class CareersTest extends BaseTest{

    @Test
    public void checkCareersPage() {
        HomePage homePage = new HomePage(driver);
        homePage.checkHomePage();
        homePage.selectNavigationbarItem("Company");
        CareersPage careersPage = homePage.selectCareers();
        careersPage.checkCareersPage();
        QualityAssurancePage qualityAssurancePage = careersPage.navigateToQualityAssurancePage("https://useinsider.com/careers/quality-assurance/");
        qualityAssurancePage.checkQualityAssurancePage();
        QualityAssuranceOpenPositionsPage qualityAssuranceOpenPositionsPage = qualityAssurancePage.clickSeeAllQAJobsButton();
        qualityAssuranceOpenPositionsPage.selectFilterLocation("Istanbul, Turkey");
        qualityAssuranceOpenPositionsPage.selectFilterDepartment("Quality Assurance");
        qualityAssuranceOpenPositionsPage.checkOpenPositions("Quality Assurance","Quality Assurance","Istanbul, Turkey","qualityassurance");
        String jobTitle = qualityAssuranceOpenPositionsPage.getFirstJobTitle();
        qualityAssuranceOpenPositionsPage.clickViewRole();
        qualityAssuranceOpenPositionsPage.switchNewWindow("Insider. - " + jobTitle);
    }
}
