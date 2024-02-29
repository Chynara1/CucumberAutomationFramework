package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class ElarAppYardsPage2 {
    WebDriver driver;
    public ElarAppYardsPage2(){
        WebDriver driver = Driver.getDriver();
        PageFactory.initElements(driver,this);
    }
    @FindBy(xpath = "//a[@href='#/panel/yards/add']")
    public WebElement  addYardButton;
    @FindBy(xpath = "//input[@name='yard_location']")
    public WebElement name;

    @FindBy(id = "id_address")
    public WebElement address;

    @FindBy(id = "id_city")
    public WebElement city;

    @FindBy(xpath = "//select[@name=\"state\"]")
    public WebElement state;

    @FindBy(xpath = "//input[@name=\"zip_code\"]")
    public WebElement zipCode;

    @FindBy(xpath = "//input[@name='spots']")
    public WebElement spots;

    @FindBy(tagName = "button")
    public WebElement addButton;

    @FindBy(tagName = "h5")
    public WebElement successMessage;

    @FindBy(xpath = "//a[contains(text(), 'Go to the current profile yard')]")
    public  WebElement goToCurrentPageButton;

    @FindBy(xpath = "//a[@class='edit-profile']")
    public  WebElement editButton;

    @FindBy(xpath = "//button[@class='confirm-save']")
    public WebElement saveButton;

    }

