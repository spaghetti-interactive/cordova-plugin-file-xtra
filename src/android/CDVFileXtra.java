package it.spaghettiinteractive.cordova.filextra;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.res.AssetManager;
import android.os.Environment;
import android.os.StatFs;

public class CDVFileXtra extends CordovaPlugin {

    @SuppressWarnings("unused")
    private static final String TAG = "CDVFileXtra";

    private interface FileOp {
        void run(JSONArray args) throws Exception;
    }

    @Override
    public boolean execute(String action, String rawArgs,
            final CallbackContext callbackContext) throws JSONException {

        if (action.equals("getFreeDiskSpace")) {
            runOnThread(new FileOp() {

                @Override
                public void run(JSONArray args) throws Exception {
                    long l = getFreeDiskSpace();
                    callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, l));
                }
            }, rawArgs, callbackContext);
        } else if (action.equals("getSize")) {
            runOnThread(new FileOp() {

                @Override
                public void run(JSONArray args) throws Exception {
                    String path = args.getString(0);
                    long l = -1;
                    if (path.startsWith("file:///android_asset/")) {
                        AssetManager assets = cordova.getActivity().getApplicationContext().getAssets();
                        AssetCache cache = new AssetCache(assets);
                        l = getSize(path.replace("file:///android_asset/", ""), cache);
                    }
                    callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, l));
                }
            }, rawArgs, callbackContext);
        } else {
            return false;
        }

        return true;
    }

    private static long getFreeDiskSpace() {
        String status = Environment.getExternalStorageState();
        long l = 0;

        if (status.equals(Environment.MEDIA_MOUNTED)) {
            l = fn(Environment.getExternalStorageDirectory().getPath());
        } else {
            l = -1;
        }

        return l;
    }

    private static long getSize(String path, AssetCache cache) {
        return cache.getSize(path);
    }

    @SuppressWarnings("deprecation")
    private static long fn(String path) {
        StatFs stat = new StatFs(path);
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize / 1024;
    }

    private void runOnThread(final FileOp f, final String rawArgs, final CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {

            @Override
            public void run() {
                try {
                    JSONArray args = new JSONArray(rawArgs);
                    f.run(args);
                } catch (Exception e) {
                    e.printStackTrace();
                    callbackContext.error(0);
                }
            }
        });
    }
}
