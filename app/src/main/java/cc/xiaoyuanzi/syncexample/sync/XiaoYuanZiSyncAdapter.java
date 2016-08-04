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

import java.text.DateFormat;
import java.util.Calendar;

import cc.xiaoyuanzi.syncexample.R;
import cc.xiaoyuanzi.syncexample.provider.ProviderConstant;

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

        Log.d("eeee",bundle+" "+bundle+"  s:"+s);
        Uri uri = Uri.parse("content://"+getContext().getString(R.string.provider_authority));
        ContentValues cv = new ContentValues();
        cv.put(ProviderConstant.KEY_SYNC_CONTENT,Math.random()*100);
        String date = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        cv.put(ProviderConstant.KEY_SYNC_TIME,date);
        getContext().getContentResolver().insert(uri,cv);
    }
}
