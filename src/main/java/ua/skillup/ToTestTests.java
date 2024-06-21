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
        inventoryPage.sortList(inventoryPage.sortListValue[1]);
        System.out.println("----------------");
        inventoryPage.printElementNames();
        inventoryPage.sortList(inventoryPage.sortListValue[2]);
        System.out.println("----------------");
        inventoryPage.printElementNames();
        inventoryPage.sortList(inventoryPage.sortListValue[3]);
        System.out.println("----------------");
        inventoryPage.printElementNames();




    }
}
