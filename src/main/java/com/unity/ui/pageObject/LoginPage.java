package com.unity.ui.pageObject;

import com.unity.ui.infra.BasePageFactory;
import com.unity.ui.infra.WebAction;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePageFactory {

    @FindBy(name = "email")
    private WebElement usernameInput;

    @FindBy(name = "password")
    private WebElement passwordInput;

    @FindBy(css = "button.adminjs_Button")
    private WebElement loginButton;

    public LoginPage(WebAction webAction) {
        super(webAction);
    }

    public String login(String username, String password) {
        webAction.sendKeys(usernameInput,username,true);
        webAction.sendKeys(passwordInput,password,true);
        webAction.click(loginButton);
        return webAction.getTitle();
    }
}
