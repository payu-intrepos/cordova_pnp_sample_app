package com.payu.plugin.pnp;

import android.content.Intent;
import android.util.Log;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * This class echoes a string called from JavaScript.
 */
public class PayUMoneyPNPPlugin extends CordovaPlugin {
    
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("initWithTxnParams")) {
            JSONObject map = args.getJSONObject(0);
            this.initWithTxnParams(map, callbackContext);
            return true;
        }
        return false;
    }
    
    private void initWithTxnParams(JSONObject map, CallbackContext callbackContext) {
        if (map != null) try {
            callbackContext.success("Success");
        } catch (Exception ex) {
            callbackContext.error(ex.getMessage());
        }
        else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    }
}

