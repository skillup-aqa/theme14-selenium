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
    private final By SHOPPING_CART = By.cssSelector("[data-test=\"inventory-item-price\"]");

    public String[] sortListValue = {"Name (A to Z)", "Name (Z to A)", "Price (low to high)", "Price (high to low)"};

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

    public boolean isOnInventoryPage() {
        WebElement shoppingCart = driver.findElement(SHOPPING_CART);
        return shoppingCart.isDisplayed();
    }

    public void printElementNames() {
        List<WebElement> elements = driver.findElements(ITEM_NAMES);
        List<WebElement> prices = driver.findElements(PRICE_ITEMS);


        for (int i = 0; i < elements.size(); i++) {
            String name = elements.get(i).getText();
            String price = prices.get(i).getText().substring(1);
            System.out.println(name + ": " + price);
        }
    }

    public void sortList (String string) {
        WebElement sortList = driver.findElement(SORT_LIST);
        Select select = new Select(sortList);
        select.selectByVisibleText(string);
    }

    private boolean isSortedByName(String filter) {
        List<WebElement> productNames = driver.findElements(ITEM_NAMES);
        //List<WebElement> productNames = driver.findElements(By.cssSelector("div.inventory_item_name"));

        for (int i = 0; i < productNames.size() - 1; i++) {
            String name1 = productNames.get(i).getText();
            String name2 = productNames.get(i + 1).getText();
            if (filter.contains("A to Z")) {
                if (name1.compareTo(name2) > 0) {
                    return false;
                }
            } else if (filter.contains("Z to A")) {
                if (name1.compareTo(name2) < 0) {
                    return false;
                }
            } else {
                throw new IllegalArgumentException("Unsupported filter: " + filter);
            }
        }
        return true;
    }

    private boolean isSortedByPrice(String filter) {
        List<WebElement> productPrices = driver.findElements(PRICE_ITEMS);
        //List<WebElement> productPrices = driver.findElements(By.cssSelector("div.inventory_item_price"));

        for (int i = 0; i < productPrices.size() - 1; i++) {
            String price1 = productPrices.get(i).getText();
            String price2 = productPrices.get(i + 1).getText();
            if (filter.contains("low to high")) {
                if (Double.parseDouble(price1.substring(1)) > Double.parseDouble(price2.substring(1))) {
                    return false;
                }
            } else if (filter.contains("high to low")) {
                if (Double.parseDouble(price1.substring(1)) < Double.parseDouble(price2.substring(1))) {
                    return false;
                }
            } else {
                throw new IllegalArgumentException("Unsupported filter: " + filter);
            }
        }
        return true;
    }

    public boolean isSorted(String filter) {
        if (filter.contains("Name")) {
            return isSortedByName(filter);
        } else if (filter.contains("Price")) {
            return isSortedByPrice(filter);
        } else {
            throw new IllegalArgumentException("Unsupported filter: " + filter);
        }
    }
}
