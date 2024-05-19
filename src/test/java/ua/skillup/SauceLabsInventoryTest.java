package ua.skillup;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ua.skillup.pages.InventoryPage;
import ua.skillup.pages.LoginPage;
import ua.skillup.data.Users;
import ua.skillup.utils.WebDriverProvider;

import static org.testng.Assert.assertTrue;

public class SauceLabsInventoryTest {
    @BeforeMethod
    public void setUp() {
        InventoryPage inventoryPage = new LoginPage(WebDriverProvider.getDriver())
                .open()
                .loginWithApi(Users.STANDARD_USER);
        assertTrue(inventoryPage.isLoaded());
    }

    @DataProvider(parallel = true)
    public Object[]filters() {
        return new Object[]{
                "Price (low to high)",
                "Price (high to low)",
                "Name (A to Z)",
                "Name (Z to A)"
        };
    }

    @Test(dataProvider = "filters")
    public void testFilters(String filter) {
        InventoryPage inventoryPage = new InventoryPage(WebDriverProvider.getDriver());
        inventoryPage.sortBy(filter);
        assertTrue(inventoryPage.isSorted(filter));
    }

    @AfterMethod
    public void tearDown() {
        WebDriverProvider.removeDriver();
    }
}
