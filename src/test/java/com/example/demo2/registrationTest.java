package com.example.demo2;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class registrationTest {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {

        ChromeOptions options = new ChromeOptions();

        // Headless mode only for GitHub Actions
        if ("true".equalsIgnoreCase(System.getenv("CI"))) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        }

        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        String chromeBinary = System.getenv("CHROME_BIN");

        if (chromeBinary != null && !chromeBinary.isEmpty()) {
            options.setBinary(chromeBinary);
        }

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();

        // Locate HTML file
        String projectPath = System.getProperty("user.dir");

        File htmlFile = new File(
            projectPath,
            "registrationform/reg.html"
        );

        System.out.println("Project Path: " + projectPath);
        System.out.println("HTML File Path: " +
                           htmlFile.getAbsolutePath());
        System.out.println("HTML File Exists: " + htmlFile.exists());

        Assert.assertTrue(
            htmlFile.exists(),
            "reg.html file not found!"
        );

        // Open HTML page
        driver.get(htmlFile.toURI().toString());

        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page Title: " + driver.getTitle());

        // Verify login box
        Assert.assertTrue(
            driver.findElement(By.id("loginBox")).isDisplayed(),
            "Login box not found!"
        );

        System.out.println("Registration page opened successfully!");
    }

    @Test
    public void validLoginTest() {

        driver.findElement(By.id("username")).sendKeys("student");

        driver.findElement(By.id("password")).sendKeys("12345");

        driver.findElement(By.id("loginButton")).click();

        String message = driver.findElement(By.id("message")).getText();

        System.out.println("Message: " + message);

        Assert.assertEquals(message, "Login Successful");
    }

    @Test
    public void invalidLoginTest() {

        driver.findElement(By.id("username")).sendKeys("wrong");

        driver.findElement(By.id("password")).sendKeys("wrong");

        driver.findElement(By.id("loginButton")).click();

        String message = driver.findElement(By.id("message")).getText();

        System.out.println("Message: " + message);

        Assert.assertEquals(
            message,
            "Invalid Username or Password"
        );
    }

    @Test
    public void checkBackgroundColor() {

        String bgColor = driver.findElement(By.id("loginBox"))
                .getCssValue("background-color");

        System.out.println("Background Color: " + bgColor);

        Assert.assertEquals(
            bgColor,
            "rgba(173, 216, 230, 1)"
        );
    }

    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}