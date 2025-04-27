package com.unity.ui.infra;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WebAction implements IWebAction {
    private WebDriver driver;

    public WebAction(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver getDriver() {
        return driver;
    }

    @Override
    public void click(WebElement element) {
        try {
            element.click();
        } catch (ElementNotInteractableException | StaleElementReferenceException e) {
            System.err.println("Error clicking element: " + e.getMessage());
        }
    }

    @Override
    public void click(WebElement element, String elementName) {
        try {
            element.click();
        } catch (ElementNotInteractableException | StaleElementReferenceException e) {
            System.err.println("Error clicking element [" + elementName + "]: " + e.getMessage());
        }
    }

    @Step("Going to click on button: {elementName}")
    @Override
    public void click(WebElement element, String elementName, int toDisappearTimeOut) {
        try {
            element.click();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(toDisappearTimeOut));
            wait.until(ExpectedConditions.invisibilityOf(element));
        } catch (ElementClickInterceptedException | TimeoutException | StaleElementReferenceException e) {
            System.err.println("Error clicking and waiting for element [" + elementName + "] to disappear: " + e.getMessage());
        }
    }

    @Step("Going to get url: {url}")
    @Override
    public void getUrl(String url) {
        try {
            driver.get(url);
        } catch (Exception e) {
            System.err.println("Error navigating to URL [" + url + "]: " + e.getMessage());
        }
    }

    @Override
    public WebElement findElement(By by) {
        try {
            return driver.findElement(by);
        } catch (NoSuchElementException e) {
            System.err.println("Element not found: " + by.toString());
            return null;
        }
    }

    @Override
    public List<WebElement> findElements(By by) {
        try {
            return driver.findElements(by);
        } catch (Exception e) {
            System.err.println("Error finding elements: " + by.toString());
            return null;
        }
    }

    @Override
    public void sendKeys(WebElement element, String value) {
        try {
            element.clear();
            element.sendKeys(value);
        } catch (InvalidElementStateException | StaleElementReferenceException e) {
            System.err.println("Error sending keys to element: " + e.getMessage());
        }
    }

    @Override
    public void sendKeys(WebElement element, String value, boolean shouldClearTextBox) {
        try {
            if (shouldClearTextBox) {
                element.clear();
            }
            element.sendKeys(value);
        } catch (InvalidElementStateException | StaleElementReferenceException e) {
            System.err.println("Error sending keys to element with clear option: " + e.getMessage());
        }
    }

    @Override
    public void isChildElementExist(WebElement element) {
        // No implementation provided
    }

    @Step("Going to check if element exist: {locator}")
    @Override
    public boolean isElementDisplayed(By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return element.isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            System.err.println("Element not displayed: " + locator.toString());
            return false;
        }
    }

    @Override
    public void quitDriver() {
        try {
            driver.quit();
        } catch (Exception e) {
            System.err.println("Error quitting driver: " + e.getMessage());
        }
    }

    @Override
    public String getTitle() {
        try {
            return driver.getTitle();
        } catch (Exception e) {
            System.err.println("Error getting page title: " + e.getMessage());
            return "";
        }
    }

    @Override
    public String getText(WebElement element) {
        try {
            return element.getText();
        } catch (Exception e) {
            System.err.println("Error getting text from element: " + e.getMessage());
            return "";
        }
    }

    @Override
    public void selectFromDropDown(WebElement element, String optionText) {
        try {
            Select select = new Select(element);
            select.selectByVisibleText(optionText);
        } catch (NoSuchElementException | UnexpectedTagNameException e) {
            System.err.println("Error selecting from dropdown: " + e.getMessage());
        }
    }

    @Override
    public boolean isChildElementExist(WebElement wrapperElement, By childBy, int timeoutInSeconds) {
        for (int i = 0; i < timeoutInSeconds; i++) {
            try {
                List<WebElement> elements = wrapperElement.findElements(childBy);
                if (!elements.isEmpty()) {
                    return true;
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread interrupted while waiting for child element.");
                return false;
            } catch (StaleElementReferenceException e) {
                System.err.println("Stale element while checking for child element.");
            }
        }
        return false;
    }

    @Override
    public boolean isTextFoundInTable(List<WebElement> elements, String textToSearch) {
        for (WebElement element : elements){
            if (element.getText().equals(textToSearch)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void sendKeysWithWait(By by, String value, int waitTimeout, boolean shouldClearTextBox) {
        try {
            if (!isElementDisplayed(by, waitTimeout)) {
                throw new TimeoutException("Element not displayed after waiting for " + waitTimeout + " seconds: " + by.toString());
            }

            WebElement element = this.findElement(by);
            if (shouldClearTextBox) {
                element.clear();
            }
            element.sendKeys(value);

        } catch (TimeoutException e) {
            System.err.println("Timeout while waiting for element: " + e.getMessage());
        } catch (InvalidElementStateException e) {
            System.err.println("Element is not in a state that can accept input: " + e.getMessage());
        } catch (StaleElementReferenceException e) {
            System.err.println("Element is no longer attached to the DOM: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error while sending keys to element: " + e.getMessage());
        }
    }

    public boolean setCheckboxState(WebElement checkbox, boolean shouldBeChecked) {
        try {
            if (checkbox.isSelected() != shouldBeChecked) {
                checkbox.click();
            }
            return checkbox.isSelected() == shouldBeChecked;
        } catch (Exception e) {
            System.out.println("Exception while setting checkbox state: " + e.getMessage());
            return false;
        }
    }
public boolean setCheckboxStateJavascript(WebElement checkbox, boolean shouldBeChecked) {
    try {
        if (checkbox.isSelected() != shouldBeChecked) {
            // Try normal click first
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkbox);
                new WebDriverWait(driver, Duration.ofSeconds(5))
                        .until(ExpectedConditions.elementToBeClickable(checkbox));
                checkbox.click();
            } catch (ElementClickInterceptedException e) {
                // Fallback to JS click if real click fails
                System.out.println("Normal click failed, using JavaScript click.");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
            }
        }
        return checkbox.isSelected() == shouldBeChecked;
    } catch (Exception e) {
        System.out.println("Exception while setting checkbox state: " + e.getMessage());
        return false;
    }
}
}
