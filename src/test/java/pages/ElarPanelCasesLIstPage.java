package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class ElarPanelCasesLIstPage {
    public ElarPanelCasesLIstPage(){
        WebDriver driver = Driver.getDriver();
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//a[@href='#/panel/companies/list']")
    public WebElement bugIcon;

    @FindBy(xpath = "//a[@href='#/panel/companies/add']")
    public  WebElement addCompanyButton;
}
