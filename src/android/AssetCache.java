package it.spaghettiinteractive.cordova.filextra;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

import android.content.res.AssetManager;

public final class AssetCache {

    @SuppressWarnings("unused")
    private static final String TAG = "AssetCache";

    private static Object lock = new Object();
    private static Map<String, String[]> files;
    private static Map<String, Long> sizes;

    public AssetCache(AssetManager assets) {
        initialize(assets);
    }

    @SuppressWarnings("unchecked")
    private void initialize(AssetManager assets) {
        synchronized (lock) {
            if (files == null) {
                ObjectInputStream in = null;
                try {
                    in = new ObjectInputStream(assets.open("cdvasset.manifest"));
                    files = (Map<String, String[]>)in.readObject();
                    sizes = (Map<String, Long>)in.readObject();
                } catch (ClassNotFoundException e) {
                } catch (IOException e) {
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                        }
                    }
                }
            }
        }
    }

    public long getSize(String path) {
        if (sizes == null) {
            return -1;
        }

        long l = 0;

        for (Map.Entry<String, Long> entry : sizes.entrySet()) {
            String key = entry.getKey();
            if (key.startsWith(path)) {
                l += entry.getValue();
            }
        }

        return l;
    }
}
