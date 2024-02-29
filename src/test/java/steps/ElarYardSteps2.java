package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import pages.ElarAppHomePage2;
import pages.ElarAppLoginPage2;
import pages.ElarAppYardsPage2;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.JDBCUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ElarYardSteps2 {
    ElarAppLoginPage2 elarAppLoginPage2 = new ElarAppLoginPage2();
    ElarAppYardsPage2 elarAppYardsPage2 = new ElarAppYardsPage2();
    Map<String, Object> yardData;
    String yardName;
    WebDriver driver = Driver.getDriver(); //global

    @Given("user navigates to the elar  application")
    public void user_navigates_to_the_elar_application() {
        driver.get("https://elarbridgelogisticsmindtek.space/");

    }

    @When("user log in to Elar app")
    public void user_log_in_to_elar_app() {
        elarAppLoginPage2.logIn();

    }

    @When("user clicks on Yard tab")
    public void user_clicks_on_yard_tab() {
        ElarAppHomePage2 elarAppHomePage2 = new ElarAppHomePage2();
        elarAppHomePage2.yardsTab.click();
    }

    @When("user clicks om add yard button")
    public void user_clicks_om_add_yard_button() {
        elarAppYardsPage2.addYardButton.click();

    }

    @When("user creates yard with data")
    public void user_creates_yard_with_data(io.cucumber.datatable.DataTable dataTable) {
        yardData = dataTable.asMap(String.class, Object.class);
        yardName = yardData.get("Name").toString()+new Random().nextInt(10000);
        elarAppYardsPage2.name.sendKeys(yardName);
        elarAppYardsPage2.address.sendKeys(yardData.get("Street").toString());
        elarAppYardsPage2.city.sendKeys(yardData.get("City").toString());
        Select select = new Select(elarAppYardsPage2.state);
        select.selectByValue(yardData.get("State").toString());
        elarAppYardsPage2.zipCode.sendKeys(yardData.get("Zip code").toString());
        elarAppYardsPage2.spots.sendKeys(yardData.get("spots").toString());
        elarAppYardsPage2.addButton.click();

    }

    @Then("user validates success message {string}")
    public void user_validates_success_message(String successMessage) {
        Assert.assertEquals(yardName +"\n"+ successMessage, elarAppYardsPage2.successMessage.getText());

    }

    @Then("user validates yard is persisted in BD")
    public void user_validates_yard_is_persisted_in_bd() throws SQLException {
        JDBCUtils.establishDBBConnection(
                ConfigReader.getProperty("ElarDBURL"),
                ConfigReader.getProperty("ElarDBUsername"),
                ConfigReader.getProperty("ElarDBPassword")
        );
        List<Map<String, Object>> data = JDBCUtils.executeQuery("select * from core_yard where location = '"+yardName+"'");
        Assert.assertEquals(yardName, data.get(0).get("location"));
        Assert.assertEquals(yardData.get("Street").toString(), data.get(0).get("address"));
        Assert.assertEquals(yardData.get("State").toString(), data.get(0).get("state"));
        Assert.assertEquals(yardData.get("Zip code").toString(), data.get(0).get("zip_code"));
        Assert.assertEquals(yardData.get("spots").toString(), data.get(0).get("spots"));
    }

    @When("user goes to created  yard page and clicks edit button")
    public void user_goes_to_created_yard_page_and_clicks_edit_button() {
        elarAppYardsPage2.goToCurrentPageButton.click();
        elarAppYardsPage2.editButton.click();

    }

    @When("user updates name with {string}")
    public void user_updates_name_with(String name) throws InterruptedException {
        Thread.sleep(5000);
        yardName=name+new Random().nextInt(100000); //generates random name
        elarAppYardsPage2.name.clear();
        elarAppYardsPage2.name.sendKeys(yardName);
        elarAppYardsPage2.saveButton.click();

    }

}
