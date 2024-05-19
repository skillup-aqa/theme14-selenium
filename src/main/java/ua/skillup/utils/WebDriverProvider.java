package ua.skillup.utils;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebDriverProvider {
    private static final Map<Long, WebDriver> driverMap = new ConcurrentHashMap<>();

    public static WebDriver getDriver() {
        long threadId = Thread.currentThread().getId();
        System.out.println("Thread ID: " + threadId);
        return driverMap.computeIfAbsent(threadId, key -> createDriver());
    }

    public static void removeDriver() {
        long threadId = Thread.currentThread().getId();
        System.out.println("Thread ID: " + threadId);
        WebDriver driver = driverMap.remove(threadId);
        if (driver != null) {
            driver.quit();
        }
    }

    private static WebDriver createDriver() {
        WebDriver driver = switch (System.getProperty("browser", "chrome")) {
            case "chrome" -> createChromeDriver();
            case "firefox" -> createFirefoxDriver();
            default -> throw new IllegalArgumentException("Unsupported browser: " + System.getProperty("browser"));
        };

        Dimension windowSize = parseWindowSize(System.getProperty("windowSize", "1920x1080"));
        driver.manage().window().setSize(windowSize);

        return driver;
    }

    private static ChromeDriver createChromeDriver() {
        return new ChromeDriver();
    }

    private static FirefoxDriver createFirefoxDriver() {
        return new FirefoxDriver();
    }

    private static Dimension parseWindowSize(String windowSize) {
        String[] parts = windowSize.split("x");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid window size: " + windowSize);
        }
        return new Dimension(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }
}
