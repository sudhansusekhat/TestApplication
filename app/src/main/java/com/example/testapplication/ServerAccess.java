package com.fydo.Config;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fydo.BuildConfig;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.fydo.Config.CommonUtilities.FydoURL;
import static com.fydo.Config.CommonUtilities.Pref_DeviceReg_TokenKey;
import static com.fydo.Config.CommonUtilities.Pref_HGUID;
import static com.fydo.Config.CommonUtilities.Pref_Login_TokenKey;
import static com.fydo.Config.CommonUtilities.Pref_isLogin;

public class
ServerAccess {


    public static void getResponse(final Context context, String method, String tag_json_obj, JSONObject params, boolean progress, final VolleyCallback callback) {

        final Dialog dialog;
        dialog = new Dialog(context);
        if (progress) {
            if (!dialog.isShowing())
                dialog.setCancelable(false);
            dialog.show();
        }
        CommonUtilities.log("URL :: " + FydoURL + method);
        CommonUtilities.log("params :: " + params.toString());
        JsonObjectRequest stringRequest = new JsonObjectRequest(FydoURL + method, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        CommonUtilities.log("response :: " + response.toString());
                        if (dialog.isShowing())
                            dialog.dismiss();
                        callback.onSuccess(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CommonUtilities.log("Error: " + error.getMessage());
                dialog.dismiss();
                callback.onError(error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json; charset=utf-8");
                header.put("Accept", "application/json");
                header.put("DID", CommonUtilities.getDeviceId(context));
                header.put("AppVer", BuildConfig.VERSION_NAME);

                if (CommonUtilities.getPreference(context, Pref_isLogin).equals("1")) {
                    header.put("TK", CommonUtilities.getTokenKey(context, Pref_Login_TokenKey));
                } else {
                    header.put("TK", CommonUtilities.getTokenKey(context, Pref_DeviceReg_TokenKey));
                }
                header.put("HGUID", CommonUtilities.getTokenKey(context, Pref_HGUID));
                header.put("DT", "2");
                header.put("UID", CommonUtilities.getPreference(context, CommonUtilities.Pref_User_Id));
                CommonUtilities.log("Headers :: " + header.toString());

                return header;
            }
        };

        if (CommonUtilities.isConnectingToInternet(context)) {
            AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
        } else {
            if (dialog.isShowing())
                dialog.dismiss();
            CommonUtilities.alertdialog(context, "Please check your Internet Connection");
        }
    }

    public interface VolleyCallback {
        void onSuccess(String result);

        void onError(String error);

    }
}
