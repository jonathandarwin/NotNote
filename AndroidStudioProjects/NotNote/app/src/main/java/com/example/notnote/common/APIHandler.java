package com.example.notnote.common;

import org.json.JSONObject;

public interface APIHandler {
    void onSuccess(JSONObject response);
    void onError(String error);
}
