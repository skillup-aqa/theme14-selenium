package ua.skillup.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class InventoryPage {
    @FindBy(id = "shopping_cart_container")
    private WebElement shoppingCartContainer;

    @FindBy(css = "select[data-test='product-sort-container']")
    private WebElement sortSelect;

    @FindBy(css = "span[data-test='active-option']")
    private WebElement sortActiveOption;

    @FindBy(css = "div[data-test='inventory-item']")
    private List<WebElement> products;

    @FindBy(css = "div[data-test='inventory-item-name']")
    private List<WebElement> productNames;

    @FindBy(css = "div[data-test='inventory-item-price']")
    private List<WebElement> productPrices;

    private final WebDriver driver;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isLoaded() {
        return shoppingCartContainer.isDisplayed();
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

    private boolean isSortedByName(String filter) {
        List<WebElement> productNames = driver.findElements(By.cssSelector("div.inventory_item_name"));

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
        List<WebElement> productPrices = driver.findElements(By.cssSelector("div.inventory_item_price"));

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

    public InventoryPage sortBy(String filter) {
        Select select = new Select(sortSelect);
        select.selectByVisibleText(filter);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .ignoring(StaleElementReferenceException.class)
                .until(driver -> sortActiveOption.getText().equals(filter));

        return this;
    }

}