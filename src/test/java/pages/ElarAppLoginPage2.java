package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class ElarAppLoginPage2 {
    public  ElarAppLoginPage2(){
        WebDriver driver = Driver.getDriver();
        PageFactory.initElements(driver,this);
    }
    @FindBy(xpath = "//input[@name='login']")
    public WebElement username;

    @FindBy(id= "id_input_pass")
    public  WebElement Password;

    @FindBy(xpath = "//button[@class='btn-login']")
    public  WebElement loginButton;

    public  void  logIn(){
        username.sendKeys("student1@mindtekbootcamp.com");
        Password.sendKeys("mindtek109");
        loginButton.click();
    }
}
