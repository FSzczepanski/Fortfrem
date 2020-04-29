package pl.aib.fortfrem.utility.api.listener;

import org.json.JSONObject;

import pl.aib.fortfrem.utility.exception.RequestException;

public interface RequestResultListener {
    void onSuccess(JSONObject response);
    void onFail(RequestException e);
}
