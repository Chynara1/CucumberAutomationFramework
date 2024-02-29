package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class ElarLOgisticMainPage {
    public ElarLOgisticMainPage(){
        WebDriver driver = Driver.getDriver();
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//input[@name='login']")
    public WebElement usernameInput;

    @FindBy(id= "id_input_pass")
    public  WebElement usernamePassword;

    @FindBy(xpath = "//button[@class='btn-login']")
    public  WebElement loginButton;

}
