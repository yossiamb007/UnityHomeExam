package com.unity.ui.pageObject;

import java.util.Map;

public interface IUnityEntity {
    boolean createRecord(Map<String, String> data, boolean isNew);
}