package ua.skillup;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class SauceLabInventoryTest {
    WebDriver driver;
    InventoryPage inventoryPage;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        inventoryPage = new InventoryPage(driver);
    }

    @Test
    public void open() {
        inventoryPage.open();
    }

    @Test
    public void printElementNames(){
        inventoryPage.printElementNames();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
