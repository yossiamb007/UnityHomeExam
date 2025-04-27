package com.unity.ui.pageObject;

import com.unity.ui.infra.BasePageFactory;
import com.unity.ui.infra.WebAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Map;

public class PublisherPage extends BasePageFactory implements IUnityEntity {

    @FindBy(css = "[data-css='Publisher-new-button']")
    private WebElement createNewPublisherBtn;
    @FindBy(id = "name")
    private WebElement nameInputElement;

    @FindBy(id = "email")
    private WebElement emailInputElement;

    @FindBy(css = "[data-css='Publisher-new-drawer-submit']")
    private WebElement savePublisherBtn;
    @FindBy(css = "[data-testid='property-list-email']")
    private List<WebElement> publisherEmailListElm;

    public PublisherPage(WebAction webAction) {
        super(webAction);
    }

    @Override
    public boolean createRecord(Map<String, String> data, boolean isNew) {
        webAction.click(createNewPublisherBtn);
        webAction.sendKeys(nameInputElement,data.get("name"));
        webAction.sendKeys(emailInputElement,data.get("email"));
        webAction.click(savePublisherBtn);
        return webAction.isTextFoundInTable(publisherEmailListElm,data.get("email"));
    }
}
