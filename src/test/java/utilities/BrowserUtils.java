package utilities;

import io.cucumber.java.sl.In;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.UUID;

public class BrowserUtils {

    /**
     *
     */
    public static String getRandomEmail(){
        UUID uuid = UUID.randomUUID();
        return "username"+uuid+"@gmail.com";
    }


    public static void selectOptionByValue(WebElement target, String value) {
        Select select = new Select(target);
        select.selectByValue(value);
    }

    /**
     * This method waits for element to be clickable
     * @param element
     */
    public static void waitForElementToBeClickable(WebElement element){ //we create web element parameter
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(),15);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * This method waits for text to be present in the webelement.
     * @param target
     * @param text
     */
    public static void waitForTextToBePresent(WebElement target,String text){
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(),15);
        wait.until(ExpectedConditions.textToBePresentInElement(target,text));
    }
public  static void scrollByPixel(Integer pixels){
    JavascriptExecutor jse = ((JavascriptExecutor) Driver.getDriver());
    jse.executeScript("window.scrollBy(0,"+pixels+")");
}
}
