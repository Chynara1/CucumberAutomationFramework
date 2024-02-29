//package steps;
//
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import org.junit.Assert;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.WebDriver;
//import pages.ElarLOgisticMainPage;
//import pages.ElarPanelCasesLIstPage;
//import utilities.ConfigReader;
//import utilities.Driver;
//
//public class ElarLogisticAppSteps {
//
//    WebDriver driver = Driver.getDriver();
//    ElarLOgisticMainPage elarLOgisticMainPage = new ElarLOgisticMainPage();
//    ElarPanelCasesLIstPage elarPanelCasesLIstPage = new ElarPanelCasesLIstPage();
////    ElarAppCompnaiesAddPage elarAppCompnaiesAddPage = new ElarAppCompnaiesAddPage();
//
//    @Given("user navigates to the elar Logistic application")
//    public void user_navigates_to_the_elar_logistic_application() {
//        driver.get(ConfigReader.getProperty("elarAPP"));
//
//    }
//
//
//    @When("user login with user name {string} and password {string}")
//    public void user_login_with_user_name_and_password(String userInputID, String userInputPassword) {
//        elarLOgisticMainPage.usernameInput.sendKeys(userInputID);
//        elarLOgisticMainPage.usernamePassword.sendKeys(userInputPassword);
//        elarLOgisticMainPage.loginButton.click();
//
//    }
//    @Then("user is successfully logged in to {string}")
//    public void user_is_successfully_logged_in_to(String string) {
//
//        }
//    @Then("user clicks on Bag Icon button")
//    public void user_clicks_on_bag_icon_button() throws InterruptedException {
//        elarPanelCasesLIstPage.bugIcon.click();
//        Thread.sleep(5000);
//    }
//
//    @When("user clicks on + Add Company button")
//    public void user_clicks_on_add_company_button() throws InterruptedException {
//        elarPanelCasesLIstPage.addCompanyButton.click();
//        Thread.sleep(5000);
//
//    }
//
//    @When("user inputs {int} digits")
//    public void user_inputs_digits(Integer digits) {
//        elarAppCompnaiesAddPage.MCInputFiled.sendKeys(digits.toString());
//    }
//
//
//    @Then("user validating digits is {int}")
//    public void user_validating_digits_is(Integer expectedDigits) {
//        Assert.assertEquals(expectedDigits.toString(), elarAppCompnaiesAddPage.MCInputFiled.getAttribute("value"));
//    }
//
//    @Then("user validating the error message is {string}")
//    public void user_validating_the_error_message_is(String expectedErrorMessage) {
//   Assert.assertEquals(elarAppCompnaiesAddPage.ErrorMessage.getText(),expectedErrorMessage);
//
//    }
//
//    @When("user clears the field")
//    public void user_clears_the_field() throws InterruptedException {
//        Thread.sleep(2000);
//      elarAppCompnaiesAddPage.MCInputFiled.sendKeys(Keys.BACK_SPACE);
//
//    }
//    @When("user inputs letters {string}")
//    public void user_inputs_letters(String lettersInput) {
//     elarAppCompnaiesAddPage.MCInputFiled.sendKeys(lettersInput);
//
//    }
//
//
//
//}
//
//
