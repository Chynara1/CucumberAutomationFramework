package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pages.EstyAppMainPage;
import utilities.BrowserUtils;
import utilities.ConfigReader;
import utilities.Driver;

import javax.swing.*;
import java.util.List;

public class EtsyAppSteps {
    WebDriver driver = Driver.getDriver();
    EstyAppMainPage estyAppMainPage = new EstyAppMainPage();

    @Given("user navigates to the etsy application")
    public void user_navigates_to_the_etsy_application() {
        driver.get(ConfigReader.getProperty("etsyUrls"));
    }

    @When("user searches for keyword {string}")
    public void user_searches_for_keyword(String keyword) {
        estyAppMainPage.searchBar.sendKeys(keyword + Keys.ENTER);

    }

    @Then("user verifying the search result contains")
    public void user_verifying_the_search_result_contains(DataTable dataTable) {
        List<String> keywords = dataTable.asList();
        for (WebElement item : estyAppMainPage.items) {
            boolean isFound = false;
            String wordNotFound = "";
            String itemDescription = item.getText();
            for (int i = 0; i < keywords.size(); i++) {
                if (itemDescription.toLowerCase().contains(keywords.get(i))) {
                    isFound = true;
                    System.out.println(keywords.get(i));
                } else {
                    wordNotFound = keywords.get(i);
                }
            }
            Assert.assertTrue("[" + itemDescription + "] does not contain keyword: [" + wordNotFound + "]", isFound);
        }
    }

    @When("user selects price range over thousand dollar")
    public void user_selects_price_range_over_thousand_dollar() {
        estyAppMainPage.allFiltersButton.click();
        Actions actions = new Actions(driver);
        actions.moveToElement(estyAppMainPage.overThousandButton).click().perform();
        estyAppMainPage.overThousandButton.click();
        estyAppMainPage.showResultsButton.click();
    }


//    @Then("user validating price range for items over {double}")
//    public void user_validating_price_range_for_items_over(Double priceRange) {
//        for (WebElement itemPrice : estyAppMainPage.itemPrices) {
//            double priceDouble = Double.parseDouble(itemPrice.getText());
//            Assert.assertTrue(priceDouble >= priceRange);
//
//        }

//    }
}
