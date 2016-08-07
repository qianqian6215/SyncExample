package cc.xiaoyuanzi.syncexample.sync.data;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by kotoshishang on 15/12/27.
 */
public class Utils {

    private static final int DEFAULT_BUFFER_SIZE = 1024;

    public static void writeToCached(InputStream inputStream, String fileName, File dir) throws IOException {
        File newFile = new File(dir, fileName);
        newFile.createNewFile();
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(newFile);
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int n = 0;
            while (-1 != (n = inputStream.read(buffer))) {
                out.write(buffer, 0, n);
            }
        } finally {
            if (out != null) {
                out.close();
            }
            if (newFile.exists()) {
                Log.d(DownloadService.TAG, "create new file " + newFile.getAbsolutePath());
            }
        }

    }

    public static String getCurrentDateString() {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        return formatter.format(currentTime);
    }

    public static void deleteFile(File dir) {
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                deleteFile(file);
            }

        }
        dir.delete();

    }

    public static List<File> getADResources(Context context) {
        List<File> files = new ArrayList<File>();
        File cacheDir = context.getCacheDir();
        for (File file : cacheDir.listFiles()) {
            if (file.isDirectory() && file.getName().
                    startsWith(DownloadService.PREFIX_PIC_FILE_NAME)) {
                files.addAll(Arrays.asList(file.listFiles()));
            }
        }
        return files;
    }
}
