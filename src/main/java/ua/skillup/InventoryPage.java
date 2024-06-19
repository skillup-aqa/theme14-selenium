package ua.skillup;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class InventoryPage {
    private final By ITEM_NAMES = By.xpath("//*[@data-test='inventory-item-name']");
    private final By PRICE_ITEMS = By.cssSelector("[data-test=\"inventory-item-price\"]");
    private final By SORT_LIST = By.xpath("//*[@data-test='product-sort-container']");
   // private List<WebElement> itemNames = ITEM_NAMES;

    private final WebDriver driver;

    InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open(){
      driver.get("https://www.saucedemo.com/");
      driver.manage().addCookie(new Cookie.Builder("session-username", "standard_user").build());
      driver.get("https://www.saucedemo.com/inventory.html");
    }

    public void printElementNames() {
        List<WebElement> elements = driver.findElements(ITEM_NAMES);
        List<WebElement> prices = driver.findElements(PRICE_ITEMS);
//        for (WebElement element : elements) {
//            System.out.println(element.getText());
//        }

        for (int i = 0; i < elements.size(); i++) {
            String name = elements.get(i).getText();
            String price = prices.get(i).getText().substring(1);
            System.out.println(name + ": " + price);
        }
       // String secondTxt = prices.get(1).getText();
    }

    public void sortList (String string) {
        WebElement sortList = driver.findElement(SORT_LIST);
        Select select = new Select(sortList);
        select.selectByVisibleText(string);

    }

}
