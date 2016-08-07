package cc.xiaoyuanzi.syncexample.sync.data;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kotoshishang on 15/12/28.
 */
public class MessageParser {

    public static final String URL = "URL";
    private Context mContext;
    private static final String TAG = "MessageParser";

    public MessageParser(Context context) {
        this.mContext = context;
    }

    public void parseMessage(String message) {
        Log.e(TAG, "start parse message:" + message);

        ArrayList<String> urls = new ArrayList<String>();
        JSONObject json = null;
        try {
            json = new JSONObject(message);
            JSONArray urlArray = json.optJSONArray(URL);
            for (int i = 0; i < urlArray.length(); i++) {
                urls.add(urlArray.getString(i));
            }

        } catch (JSONException e) {
            Log.e(TAG, "parse message as json object error", e);
            return;

        }
        Intent intent = new Intent();
        intent.putStringArrayListExtra(DownloadService.KEY_DOWNLOAD_PATHS, urls);
        intent.setAction(DownloadService.DOWNLOAD_ACTION);
        mContext.startService(intent);
    }

    public final static String getTest(){

        JSONObject root = new JSONObject();
        JSONArray array = new JSONArray();
        array.put("http://img4.duitang.com/uploads/item/201208/22/20120822111602_cNrMi.thumb.700_0.jpeg");
        array.put("http://img4.duitang.com/uploads/item/201604/18/20160418205011_GTdFW.jpeg");
        //array.put("c");
        try {
            root.put(URL, array);
            Log.d("eeee",root.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root.toString();
    }
}
