package cc.xiaoyuanzi.syncexample.sync.data;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kotoshishang on 15/12/27.
 */
public class DownLoadImp {

    public static final int TIMEOUT_MILLIS = 5000;
    public static final int RETRY_TIMES = 3;

    public static InputStream downloadURL(String path) throws IOException {
        URL url = new URL(path);
        int currentCount = 1;
        while (currentCount <= RETRY_TIMES) {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(TIMEOUT_MILLIS);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                Log.d(DownloadService.TAG, "download success");
                InputStream inputStream = conn.getInputStream();
                if (inputStream != null) {
                    return inputStream;
                }
            }
            Log.e(DownloadService.TAG, "download url fail, retry currentCount");
            currentCount++;
            conn.disconnect();

        }
        Log.e(DownloadService.TAG, String.format("download url fail", path));
        return null;
    }
}
