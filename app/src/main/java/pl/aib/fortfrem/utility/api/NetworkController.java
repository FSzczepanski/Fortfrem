package pl.aib.fortfrem.utility.api;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.Volley;

public class NetworkController {
    private static NetworkController instance;

    public static NetworkController getInstance(Context context) {
        if(instance == null) {
            instance = new NetworkController(context);
        }
        return instance;
    }

    private RequestQueue requestQueue;

    private NetworkController(Context context) {
        this.requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public void addRequestToQueue(Request request) {
        this.requestQueue.add(request);
    }

}
