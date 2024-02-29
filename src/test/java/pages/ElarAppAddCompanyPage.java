
package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class ElarAppAddCompanyPage {
    WebDriver driver;

    public ElarAppAddCompanyPage() {
        driver = Driver.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@name='company_name']")
    public WebElement nameField;
    @FindBy(xpath = "//input[@name='mc_number']")
    public WebElement mcNumberField;
    @FindBy(xpath = "//input[@name='dot_number']")
    public WebElement dotNumberField;
    @FindBy(xpath = "(//input[@class='input-phone input-form border disabled-company'])[1]")
    public WebElement phoneNumberField;
    @FindBy(id = "id_address")
    public WebElement streetField;
    @FindBy(id = "id_city")
    public WebElement cityField;
    @FindBy(xpath = "//select[@name='state']")
    public WebElement stateDropdown;
    @FindBy(xpath = "//input[@name='zip_code']")
    public WebElement zip;
    @FindBy(xpath = "//input[@class='input-form disabled-not-ext border disabled-company lowercase']")
    public WebElement email;
    @FindBy(xpath = "//input[@name='insurance']")
    public WebElement insurance;
    @FindBy(xpath = "(//span[@class='secret-number'])[2]")
    public WebElement policyExpDate;
    @FindBy(xpath = "//span[contains(text(),'22') and @class='date']")
    public WebElement feb22;
    @FindBy(xpath = "//input[@name='policy_number']")
    public WebElement policyNum;
    @FindBy(xpath = "//select[@name='other_licenses']")
    public WebElement otherLicenses;

//    @FindBy(xpath = "//button[@class='confirm-save']")
//    public WebElement addCompanyBtn;

//    @FindBy(linkText = "Go to the current profile company")
//    public WebElement goToCurrentProfileCompanyBtn;

    @FindBy(tagName = "h5")
    public WebElement actualSuccessAddedMsg;

    @FindBy(xpath = "//button[@class='confirm-save']")
    public WebElement saveBtn;

    @FindBy(xpath = "//a[@class='edit-profile']")
    public WebElement editBtn;

    @FindBy(xpath = "//button[@class='confirm-save']")
    public WebElement addCompanyButton;

    @FindBy(xpath = "//a[contains(text(), ' Go to the current profile company')]")
    public WebElement goToCurrentCompanyButton;

    @FindBy(xpath = "//a[@class='edit-profile']")
    public WebElement companyEditButton;


}