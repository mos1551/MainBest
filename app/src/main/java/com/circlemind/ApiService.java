package com.circlemind;


import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static com.circlemind.Constant.IP;


public class ApiService {
    private static final String TAG = "ApiService";
    private Context context;

    public ApiService(Context context) {
        this.context = context;
    }

    public void signupUser(JSONObject requestJsonObject, final OnSignUpComplete onSignUpComplete) {
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, IP+"/AddUser.php", requestJsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean success=response.getBoolean("success");
                    onSignUpComplete.onSignUp(success);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onSignUpComplete.onSignUp(false);
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }

    public interface OnSignUpComplete {
        void onSignUp(boolean success);
    }
}
