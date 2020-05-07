package pl.aib.fortfrem.utility.api;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pl.aib.fortfrem.utility.api.listener.RequestResultListener;
import pl.aib.fortfrem.utility.exception.RequestCreationException;
import pl.aib.fortfrem.utility.exception.RequestException;

public class ApiRequest implements Response.Listener<String>, Response.ErrorListener {

    public enum ContentType {
        JSON("application/json"),
        URLENCODED("application/x-www-form-urlencoded");

        private String value;

        ContentType(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    @NonNull
    private Context context;

    @NonNull
    private String url;

    @Nullable
    private RequestResultListener resultListener;

    private int method;

    @Nullable
    private ContentType contentType;

    @Nullable
    private String data;

    @Nullable
    private String authToken;

    public ApiRequest(@NonNull Context context, @NonNull String url, int method) {
        this.context = context;
        this.url = url;
        this.method = method;
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        //TODO Handle Null Pointer
        String responseString = new String(error.networkResponse.data);
        String errorTag;
        try {
            JSONObject response = new JSONObject(responseString);
            errorTag = response.has("error_tag") ? response.getString("error_tag") : "unknown";
        } catch (JSONException e) {
            errorTag = "unknown";
        }
        if(resultListener != null) {
            resultListener.onFail(new RequestException(errorTag));
        }
    }

    @Override
    public void onResponse(String response) {
        try {
            JSONObject json = new JSONObject(response);
            if(resultListener != null) {
                resultListener.onSuccess(json);
            }
        } catch (JSONException e) {
            if (resultListener != null) {
                resultListener.onFail(new RequestException("response.bad.format"));
            }
        }
    }

    @Nullable
    private Request buildRequest(@NonNull final String TAG, @NonNull final Request.Priority priority) throws RequestCreationException {
        if(this.method != Request.Method.GET && this.method != Request.Method.POST) {
            throw new RequestCreationException("method.invalid");
        }
        if(this.method == Request.Method.POST && this.contentType == null) {
            throw new RequestCreationException("content.type.not.set");
        }
        if(this.method == Request.Method.POST && this.data == null) {
            throw new RequestCreationException("request.data.not.set");
        }

        Request request = new StringRequest(this.method, this.url, this, this) {
            @Override
            public String getBodyContentType() {
                return contentType != null ? contentType.value : ContentType.URLENCODED.value;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return data != null ? data.getBytes() : new byte[]{};
            }

            @Override
            public Priority getPriority() {
                return priority;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>(super.getHeaders());
                headers.put("Accept", "application/json");
                if(authToken != null) {
                    headers.put("Authorization", authToken);
                }
                return headers;
            }
        };
        request.setTag(TAG);
        return request;
    }

    public void execute() throws RequestCreationException {
        String tag = this.getClass().getCanonicalName() != null ? this.getClass().getCanonicalName() : "unknown";
        this.execute(tag);
    }

    public void execute(@NonNull final String TAG) throws RequestCreationException {
        this.execute(TAG, Request.Priority.NORMAL);
    }

    public void execute(@NonNull final String TAG, @NonNull Request.Priority priority) throws RequestCreationException {
        Request request = this.buildRequest(TAG, priority);
        NetworkController.getInstance(this.context).addRequestToQueue(request);
    }

    public void setResultListener(@NonNull RequestResultListener resultListener) {
        this.resultListener = resultListener;
    }

    public void setContentType(@NonNull ContentType contentType) {
        this.contentType = contentType;
    }

    public void setData(@NonNull String data) {
        this.data = data;
    }

    public void setAuthToken(@NonNull String authToken) {
        this.authToken = authToken;
    }

}
