package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.SmartbearLoginPage;
import utilities.ConfigReader;
import utilities.Driver;

public class SmartbearSteps {
    SmartbearLoginPage smartbearLoginPage= new SmartbearLoginPage();

    WebDriver driver = Driver.getDriver();

    @Given("user navigates to the SmartBear application")
    public void user_navigates_to_the_smart_bear_application() {
        driver.get(ConfigReader.getProperty("smartbearUrl"));

    }
    @When("user logs in with user name {string} and password {string}")
    public void user_user_logs_in_with_user_name_and_password(String username, String password) {
        smartbearLoginPage.usernameInput.sendKeys(username);
        smartbearLoginPage.passwordInput.sendKeys(password);
        smartbearLoginPage.loginButton.click();

    }
    @Then("user is successfully logged in and title is {string}")
    public void user_is_successfully_logged_in_and_title_is(String title) {
        Assert.assertEquals(title,driver.getTitle());
}

    @Then("Verifying the invalid message {string}")
    public void verifying_the_invalid_message(String InvalidStatusMessage) {
        smartbearLoginPage.invalidLoginOrPassword.getText();
        Assert.assertEquals(InvalidStatusMessage,smartbearLoginPage.invalidLoginOrPassword.getAttribute("value"));

    }
}
