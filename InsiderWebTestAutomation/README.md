Insider Web Test Automation
UI Test Automation Project using Selenium Java with TestNG For Insider Website

Installation
Clone the repository.
Change the browser parameter in config.properties file. Since the webdrivermanager library is used, we can run our tests on the browser we want without needing to install any drivers.
Install dependencies with ./mvnw clean install.
To run the project, execute ./mvnw clean test.
If the scenario fails, a screenshot of the step where the error occurred will be taken and added to the screenshots file in the project directory.
