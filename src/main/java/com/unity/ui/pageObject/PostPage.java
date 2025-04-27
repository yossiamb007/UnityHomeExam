package com.unity.ui.pageObject;

import com.unity.ui.infra.BasePageFactory;
import com.unity.ui.infra.WebAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class PostPage extends BasePageFactory implements IUnityEntity {
    @FindBy(css = "[data-css='Post-new-button']")
    private WebElement createNewPostBtn;
    @FindBy(id = "title")
    private WebElement titleInputElement;
    @FindBy(id = "content")
    private WebElement contentInputElement;
    @FindBy(css = "section[data-css='Post-edit-status'] div.adminjs_Select")
    private WebElement statusSelectionDiv;
    @FindBy(css = "section[data-css='Post-edit-publisher'] div.adminjs_Select")
    private WebElement publisherSelectionDiv;
    @FindBy(xpath = "//div[contains(@id, '-option-') and text()='ACTIVE']")
    private WebElement statusActiveOptionElm;
    @FindBy(xpath = "//div[contains(@id, '-option-') and text()='REMOVED']")
    private WebElement statusRemovedOptionElm;
    @FindBy(css = "[data-css='Post-new-drawer-submit']")
    private WebElement savePublisherBtn;
    @FindBy(id = "published")
    private WebElement publishCheckBox;
    @FindBy(css = "section[data-css='Post-list-status'] span.adminjs_Badge")
    private WebElement postPageStatusElm;
    public PostPage(WebAction webAction) {
        super(webAction);
    }

    @Override
    public boolean createRecord(Map<String, String> data, boolean isNew) {
       if (isNew){
           webAction.click(createNewPostBtn);
       }
        webAction.sendKeys(titleInputElement,data.get("title"));
        webAction.sendKeys(contentInputElement,data.get("content"));
        webAction.click(statusSelectionDiv);
        webAction.click(webAction.findElement(By.xpath("//div[contains(@id, '-option-') and text()='" + data.get("status") + "']")));
        webAction.setCheckboxStateJavascript(publishCheckBox,true);
        webAction.click(publisherSelectionDiv);
        webAction.click(webAction.findElement(By.xpath("//div[contains(@id, '-option-') and text()='" + data.get("publisher") + "']")));
        return true;
    }
    public void publish(){
      webAction.setCheckboxState(publishCheckBox,true);
    }
    public void unPublish(){
        webAction.setCheckboxState(publishCheckBox,false);
    }
    public String saveEntity(String expectedTitle){
        webAction.click(savePublisherBtn);
        return getStatus(expectedTitle);
    }
    private String getStatus(String expectedTitle){
        // 1. Find all the rows
        List<WebElement> rows = webAction.findElements(By.cssSelector(".adminjs_Table>tbody tr"));
        String status = null;
        for (WebElement row : rows) {
            try {
                // 2. Get the first td (assuming email is in the first column)
                WebElement titleCell = row.findElement(By.cssSelector("section[data-css='Post-list-title']"));
                String actualTitle = titleCell.getText().trim();

                // 3. Get the status td (by data attribute)
                WebElement statusCell = row.findElement(By.cssSelector("section[data-css='Post-list-status'] span.adminjs_Badge"));
                status = statusCell.getText().trim();

                // 4. Check if actualTitle is expectedTitle
                if (expectedTitle.equalsIgnoreCase(actualTitle)) {
                    return status;
                }
            } catch (NoSuchElementException e) {
                System.out.println("Element not found due to: "+ e.getMessage());
            }
        }
        return status;
    }
}
