package ua.skillup;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;


public class SauceLabInventoryTest {
    WebDriver driver;
    InventoryPage inventoryPage;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        inventoryPage = new InventoryPage(driver);
        inventoryPage.open();
    }

    @Test
    public void onInventoryPage() {
        assertTrue(inventoryPage.isOnInventoryPage());
    }

    @DataProvider//(parallel = true)
    public Object[] filter() {
        return new Object[]{
                inventoryPage.sortListValue[0],
                inventoryPage.sortListValue[1],
                inventoryPage.sortListValue[2],
                inventoryPage.sortListValue[3]
        };
    }

    @Test(dataProvider = "filter")
    public void testFilters(String filter) {
        inventoryPage.sortList(filter);
        inventoryPage.printElementNames();
        System.out.println("----------------");
        assertTrue(inventoryPage.isSorted(filter));
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

}
