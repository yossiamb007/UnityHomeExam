package com.unity.ui.pageObject;

import com.unity.ui.infra.BasePageFactory;
import com.unity.ui.infra.WebAction;

import java.util.Map;

public class ProfilePage extends BasePageFactory implements IUnityEntity {
    protected ProfilePage(WebAction webAction) {
        super(webAction);
    }

    @Override
    public boolean createRecord(Map<String, String> data, boolean isNew) {
        return false;
    }
}
