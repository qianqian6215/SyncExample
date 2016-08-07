package cc.xiaoyuanzi.syncexample.sync.data;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import cc.xiaoyuanzi.syncexample.R;
import cc.xiaoyuanzi.syncexample.provider.ProviderConstant;

public class DownloadService extends IntentService {

    public static final String TAG = "AD.DownloadService";
    public static final String KEY_DOWNLOAD_PATHS = "DownLoaded path key";
    public static final String DOWNLOAD_ACTION = "com.dw.android.intent.DOWNLOAD";
    public static final String RESOURCES_CHANGE_ACTION = "com.dw.android.intent.RESOURCES_CHANGE";
    public static final String PREFIX_PIC_FILE_NAME = "pictures_";
    private List<String> downloadPaths;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DownloadService(String name) {
        super(name);
    }

    public DownloadService() {
        this("");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        List<String> paths = intent.getStringArrayListExtra(KEY_DOWNLOAD_PATHS);
        //generate the dir
        File cacheDir = getCacheDir();
        File destDir = new File(cacheDir, PREFIX_PIC_FILE_NAME + Utils.getCurrentDateString());
        Log.d(TAG, String.format("prepare to download resource ,the count is %d ", paths.size()));
        destDir.mkdir();
        Log.d(TAG, String.format("create destination dir %s", destDir.getPath()));
        InputStream inputStream = null;
        int currentCount = 0;
        int successCount = 0;
        //start to download the urls
        for (String path : paths) {
            try {
                Log.d(TAG, String.format("start download URL %s", path));
                inputStream = DownLoadImp.downloadURL(path);
                currentCount++;
                if (inputStream == null) {
                    continue;
                }
                Utils.writeToCached(inputStream, currentCount + "", destDir);

            } catch (IOException e) {
                Log.e(TAG, String.format("download error url:%s", path), e);
                continue;
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {

                    }
                }
            }
            successCount++;
            Log.d(TAG, String.format("download success url: %s", path));
        }
        Log.d(TAG, String.format("save %d resources to path %s", successCount, destDir));
        //remove the old data
        if (successCount > 0) {
            Log.d(TAG, "remove old resources");
            for (File file : cacheDir.listFiles()) {
                if (file.isDirectory() && !file.getName().equals(destDir.getName())
                        && file.getName().startsWith(PREFIX_PIC_FILE_NAME)) {
                    Log.d(TAG, String.format("remove old dir %s", file.getPath()));
                    Utils.deleteFile(file);
                }
            }

            Uri uri = Uri.parse("content://"+getString(R.string.provider_authority));
            ContentValues cv = new ContentValues();
            cv.put(ProviderConstant.KEY_SYNC_CONTENT,Math.random()*100);
            String date = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
            cv.put(ProviderConstant.KEY_SYNC_TIME,date);
            getContentResolver().insert(uri, cv);

//            PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
//            if (!powerManager.isScreenOn()) {
//                Log.d(TAG, "the screen is on saver status, start AD player");
//            } else {
//                Log.d(TAG, "resource change ,notify the ad plaer");
//                sendBroadcast(new Intent(RESOURCES_CHANGE_ACTION));
//            }

        } else {
            Log.d(TAG, String.format("save none resources ,remove dir %s", destDir.getPath()));
            Utils.deleteFile(destDir);
        }

    }

}
