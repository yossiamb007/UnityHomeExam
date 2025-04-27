package com.unity.ui.infra;

import org.openqa.selenium.support.PageFactory;

public class BasePageFactory {
    protected WebAction webAction;
    protected BasePageFactory(WebAction webAction){
        this.webAction = webAction;
        PageFactory.initElements(this.webAction.getDriver(), this);
    }
}