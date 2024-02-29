package steps;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import utilities.Driver;

public class Hooks {

    WebDriver driver;
    @Before
    public void setUp(Scenario scenario){
        if (scenario.getSourceTagNames().contains("ui"))
        driver= Driver.getDriver();
        System.out.println("Before Scenario Method");
    }

    @After
    public void tearDown(Scenario scenario){
        if (scenario.getSourceTagNames().contains("ui"))
        driver.quit();
        System.out.println("After Scenario Method");
    }
}
