package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import pages.ElarAppAddCompanyPage;
import pages.ElarLOgisticMainPage;
import pages.ElarPanelCasesLIstPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.JDBCUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ElarDBCompanySteps {
    WebDriver driver = Driver.getDriver();
    ElarLOgisticMainPage elarLOgisticMainPage = new ElarLOgisticMainPage();
    ElarPanelCasesLIstPage elarPanelCasesLIstPage = new ElarPanelCasesLIstPage();
    ElarAppAddCompanyPage elarAppAddCompanyPage = new ElarAppAddCompanyPage();
    //    ElarAppCompnaiesAddPage elarAppCompnaiesAddPage=new ElarAppCompnaiesAddPage();
    Map<String, Object> companyData;
    String expectedSuccessMessage;
    String companyName;
    Integer MCrandomGlobal;
    String editedCompanyName;

    Map<String, Object> newCompanyData;


    @Given("user navigates to the elar Logistic application")
    public void user_navigates_to_the_elar_logistic_application() throws InterruptedException {
        driver.get(ConfigReader.getProperty("elarAPP"));
        Thread.sleep(3000);

    }


    @When("user login with user name {string} and password {string}")
    public void user_login_with_user_name_and_password(String userInputID, String userInputPassword) {
        elarLOgisticMainPage.usernameInput.sendKeys(userInputID);
        elarLOgisticMainPage.usernamePassword.sendKeys(userInputPassword);
        elarLOgisticMainPage.loginButton.click();

    }

    @Then("user clicks on Bag Icon button")
    public void user_clicks_on_bag_icon_button() throws InterruptedException {
        elarPanelCasesLIstPage.bugIcon.click();
        Thread.sleep(5000);
    }

    @When("user clicks on + Add Company button")
    public void user_clicks_on_add_company_button() throws InterruptedException {
        elarPanelCasesLIstPage.addCompanyButton.click();
        Thread.sleep(5000);

    }

    @Then("user creates company with data")
    public void user_creates_company_with_data(io.cucumber.datatable.DataTable dataTable) throws InterruptedException {
        companyData = dataTable.asMap(String.class, Object.class);
        companyName = companyData.get("Name").toString() + new Random().nextInt(10000);
        elarAppAddCompanyPage.nameField.sendKeys(companyName);
        MCrandomGlobal = new Random().nextInt(66666666);
        elarAppAddCompanyPage.mcNumberField.sendKeys(MCrandomGlobal.toString());
        Thread.sleep(3000);
        Integer DOTRandomNumber = new Random().nextInt(66666666);
        elarAppAddCompanyPage.dotNumberField.sendKeys(DOTRandomNumber.toString());
        elarAppAddCompanyPage.phoneNumberField.sendKeys(companyData.get("Phone number").toString());
        elarAppAddCompanyPage.streetField.sendKeys(companyData.get("Street").toString());
        elarAppAddCompanyPage.cityField.sendKeys(companyData.get("City").toString());
        Select select = new Select(elarAppAddCompanyPage.stateDropdown);
        select.selectByValue(companyData.get("State").toString());
        elarAppAddCompanyPage.zip.sendKeys(companyData.get("Zip code").toString());
        elarAppAddCompanyPage.email.sendKeys(companyData.get("Email").toString());
        elarAppAddCompanyPage.insurance.sendKeys(companyData.get("Insurance(producer company name)").toString());
        elarAppAddCompanyPage.policyExpDate.click();
        elarAppAddCompanyPage.feb22.click();
        elarAppAddCompanyPage.policyExpDate.click();
        elarAppAddCompanyPage.policyNum.sendKeys(companyData.get("Policy number").toString());


    }

    @Then("The user clicks on + Add Company button")
    public void the_user_clicks_on_add_company_button() {
        elarAppAddCompanyPage.addCompanyButton.click();


    }

    @Then("The user validates success message  of creating {string}")
    public void the_user_validates_success_message(String expectedSuccessMessage) {
        Assert.assertEquals(companyName + "\n" + expectedSuccessMessage, elarAppAddCompanyPage.actualSuccessAddedMsg.getText());


    }

    @Then("user validates created company persists in DB")
    public void user_validates_created_company_persists_in_db() throws SQLException {
        JDBCUtils.establishDBBConnection(
                ConfigReader.getProperty("ElarDBURL"),
                ConfigReader.getProperty("ElarDBUsername"),
                ConfigReader.getProperty("ElarDBPassword")
        );
        String query = "select * from core_company where company_name = '";
        List<Map<String, Object>> data = JDBCUtils.executeQuery(query + companyName + "'");
        Assert.assertEquals(companyName, data.get(0).get("company_name"));
        Assert.assertEquals(MCrandomGlobal.toString(), data.get(0).get("mc_number")); // asserting feature file and database table data


    }

    @When("user clicks on button")
    public void user_clicks_on_button() throws InterruptedException {
        elarAppAddCompanyPage.goToCurrentCompanyButton.click();


    }

    @When("user clicks on edit button")
    public void user_clicks_on_edit_button() {
        elarAppAddCompanyPage.companyEditButton.click();
    }

    @When("user edits created company with data")
    public void user_edits_created_company_with_data(io.cucumber.datatable.DataTable updatedDatatable) {
        newCompanyData = updatedDatatable.asMap(String.class, Object.class);
        editedCompanyName = newCompanyData.get("Name").toString() + new Random().nextInt(1000);
        elarAppAddCompanyPage.nameField.clear();
        elarAppAddCompanyPage.nameField.sendKeys(editedCompanyName);
        elarAppAddCompanyPage.streetField.clear();
        elarAppAddCompanyPage.streetField.sendKeys(newCompanyData.get("Street").toString());


    }

    @Then("user validates success message of editing {string}")
    public void user_validates_success_message_of_editing(String expectedEditedSuccessMessage) {
        elarAppAddCompanyPage.saveBtn.click();
        Assert.assertEquals(editedCompanyName + "\n" + expectedEditedSuccessMessage, elarAppAddCompanyPage.actualSuccessAddedMsg.getText());


    }

    @Then("user validates edited company is updated in DB")
    public void user_validates_edited_company_is_updated_in_db() throws SQLException {
        JDBCUtils.establishDBBConnection(
                ConfigReader.getProperty("ElarDBURL"),
                ConfigReader.getProperty("ElarDBUsername"),
                ConfigReader.getProperty("ElarDBPassword")
        );
        String query = "select * from core_company where company_name = '";
        List<Map<String, Object>> editEddata = JDBCUtils.executeQuery(query + editedCompanyName + "'");
        Assert.assertEquals(editedCompanyName, editEddata.get(0).get("company_name"));
        Assert.assertEquals(newCompanyData.get("Street").toString(), editEddata.get(0).get("address").toString()); // asserting feature file and database table data


    }

}
