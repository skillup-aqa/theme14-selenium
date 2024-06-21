package ua.skillup;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

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
    public Object[] filters() {
        return new Object[]{
                inventoryPage.sortListValue[0],
                inventoryPage.sortListValue[1],
                inventoryPage.sortListValue[2],
                inventoryPage.sortListValue[3]
        };
    }

    @Test(dataProvider = "filters")
    public void testFilters(String filters) {
        inventoryPage.sortList(filters);
        assertTrue(inventoryPage.isSorted(filters));
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
