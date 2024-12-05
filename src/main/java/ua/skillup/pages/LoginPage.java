package ua.skillup.pages;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import ua.skillup.data.Users;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class LoginPage {
    @FindBy(id = "user-name")
    private WebElement userNameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "h3[data-test='error']")
    private WebElement errorMessage;

    private final WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public LoginPage open() {
        driver.get("https://www.saucedemo.com/");
        return this;
    }

    public InventoryPage login(Users user) {
        userNameField.sendKeys(user.getUsername());
        passwordField.sendKeys(user.getPassword());
        loginButton.click();
        return new InventoryPage(driver);
    }

    public InventoryPage loginWithApi(Users user) {
        driver.manage().addCookie(
                new Cookie.Builder("session-username", user.getUsername()).build());

        driver.get("https://www.saucedemo.com/inventory.html");
        return new InventoryPage(driver);
    }

    public String getErrorMessage() {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(visibilityOf(errorMessage))
                .getText();
    }
}