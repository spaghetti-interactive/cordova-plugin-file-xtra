package it.spaghettiinteractive.cordova.filextra.FileXtra;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

public class CDVFileXtra extends CordovaPlugin {

    private static final String TAG = "CDVFileXtra";

    @Override
    public boolean execute(String action, JSONArray args,
            CallbackContext callbackContext) throws JSONException {

        if (action.equals("getFreeDiskSpace")) {
            getFreeDiskSpace(callbackContext);
            return true;
        }
        return super.execute(action, args, callbackContext);
    }

    private void getFreeDiskSpace(final CallbackContext callbackContext) {
    }
}
