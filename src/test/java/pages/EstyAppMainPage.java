package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;

public class EstyAppMainPage {
    WebDriver driver;
    public EstyAppMainPage(){
        driver = Driver.getDriver();
        PageFactory.initElements(driver,this);
    }
    @FindBy(id = "global-enhancements-search-query")
    public WebElement searchBar;

    @FindBy(xpath = "/div[@class='wt-grid wt-pl-xs-0 wt-pr-xs-0 search-listings-group']//h3")
    public List<WebElement> items;

    @FindBy(id = "search-filter-button")
    public  WebElement allFiltersButton;

    @FindBy(id = "price-input-4")
    public WebElement overThousandButton;

    @FindBy(xpath = "//button[@aria-label=\"Show results (1,000+ items)\"]")
    public WebElement showResultsButton;

    @FindBy(xpath = "//ol[@data-results-grid-container]//p[1]//span[@class='currency-value']")
    public WebElement itemPrices;
}
