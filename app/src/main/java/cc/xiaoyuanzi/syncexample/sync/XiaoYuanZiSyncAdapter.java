package cc.xiaoyuanzi.syncexample.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.HttpConnection;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;

import cc.xiaoyuanzi.syncexample.R;
import cc.xiaoyuanzi.syncexample.provider.ProviderConstant;
import cc.xiaoyuanzi.syncexample.sync.data.MessageParser;

/**
 * Created by kotoshishang on 16/8/3.
 */
public class XiaoYuanZiSyncAdapter extends AbstractThreadedSyncAdapter {

    public XiaoYuanZiSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    public XiaoYuanZiSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {

        Log.d("eeee onPerformSync",bundle+" "+bundle+"  s:"+s);

        //TODO 1,post request and get the result
        String url = "http://123.56.18.84/GetBuiltyService/servlet/GetBuiltyUrlServlet?param1=param";

        HttpGet request = new HttpGet(url);
        Log.d("eeeeTest", MessageParser.getTest());
        try {

            DefaultHttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() == 200) {

                String result = EntityUtils.toString(response.getEntity());
                Log.d("eeeeResult", result);
                MessageParser parser = new MessageParser(getContext());
                parser.parseMessage(result);
            }
        }catch (ClientProtocolException e){
            Log.e("eeee","",e);
        }catch (IOException e) {
            Log.e("eeee","",e);
        }
        //TODO 2,parse string and start to download
       // String msg = MessageParser.getTest();

        //TODO 3.download finish insert to provider

//        Log.d("eeee",bundle+" "+bundle+"  s:"+s);
//        Uri uri = Uri.parse("content://"+getContext().getString(R.string.provider_authority));
//        ContentValues cv = new ContentValues();
//        cv.put(ProviderConstant.KEY_SYNC_CONTENT,Math.random()*100);
//        String date = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
//        cv.put(ProviderConstant.KEY_SYNC_TIME,date);
//        getContext().getContentResolver().insert(uri, cv);
    }
}
