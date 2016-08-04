package cc.xiaoyuanzi.syncexample.sync;

import android.app.Service;
import android.content.AbstractThreadedSyncAdapter;
import android.content.Intent;
import android.os.IBinder;

public class XiaoYuanZiSyncService extends Service {

    private AbstractThreadedSyncAdapter mSyncAdapter;

    public XiaoYuanZiSyncService() {
    }


    @Override
    public IBinder onBind(Intent intent) {

        if (mSyncAdapter == null) {
            mSyncAdapter = new XiaoYuanZiSyncAdapter(this, true);
        }
        return mSyncAdapter.getSyncAdapterBinder();
    }


}
