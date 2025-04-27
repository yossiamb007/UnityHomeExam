package com.unity.ui.pageObject;

import com.unity.ui.infra.BasePageFactory;
import com.unity.ui.infra.WebAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePageFactory {
    @FindBy(css = "section>ul")
    protected WebElement happyFolderElement;
    @FindBy(xpath = "//div[text()='Publisher']")
    WebElement publisherElement;
    @FindBy(xpath = "//div[text()='Profile']")
    WebElement profileElement;
    @FindBy(xpath = "//div[text()='Post']")
    WebElement postElement;
    private final String CREATE_NEW_RECORD_BUTTON_LOCATOR = "[data-css='Publisher-new-button']";
    public HomePage(WebAction webAction) {
        super(webAction);
    }
    public boolean clickOnHappyFolder(){
        //if (!isHappyFolderClicked()){
            webAction.click(happyFolderElement);
        //}
        return webAction.isChildElementExist(happyFolderElement,By.tagName("ul"),3);
    }
    private boolean isHappyFolderClicked(){
        return webAction.isChildElementExist(happyFolderElement,By.tagName("ul"),1);
    }
    public boolean clickOnPublisherTab(){
        webAction.click(publisherElement);
        return webAction.isElementDisplayed(By.cssSelector(CREATE_NEW_RECORD_BUTTON_LOCATOR),2);
    }
    public boolean clickOnProfileTab(){
        webAction.click(profileElement);
        return webAction.isElementDisplayed(By.cssSelector(CREATE_NEW_RECORD_BUTTON_LOCATOR),2);
    }
    public boolean clickOnPostTab(){
        webAction.click(postElement);
        return webAction.isElementDisplayed(By.cssSelector(CREATE_NEW_RECORD_BUTTON_LOCATOR),2);
    }
}
