package ua.skillup;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ToTestTests {
    public static void main(String[] args) {
        WebDriver driver;
        InventoryPage inventoryPage;

        driver = new ChromeDriver();
        inventoryPage = new InventoryPage(driver);

        inventoryPage.open();
        inventoryPage.printElementNames();
        inventoryPage.sortList("Name (Z to A)");
        System.out.println("----------------");
        inventoryPage.printElementNames();
        inventoryPage.sortList("Price (low to high)");
        System.out.println("----------------");
        inventoryPage.printElementNames();
        inventoryPage.sortList("Price (high to low)");
        System.out.println("----------------");
        inventoryPage.printElementNames();




    }
}
