package cc.xiaoyuanzi.syncexample.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.Set;

public class XiaoYuanZiContentProvider extends ContentProvider {


    public XiaoYuanZiContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {


        //throw new UnsupportedOperationException("Not yet implemented");
        SharedPreferences xiaoyuanzi_test = getContext().
                getSharedPreferences(ProviderConstant.KEY_PREFRENCE_NAME, Context.MODE_PRIVATE);
        String newString = values.getAsString(ProviderConstant.KEY_SYNC_TIME);
        SharedPreferences.Editor edit = xiaoyuanzi_test.edit();
        edit.putString(ProviderConstant.KEY_TEST_PREFRENCE_DATA, newString);
        Log.d("eeee", "insert new string " + newString);
        edit.apply();

        return null;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
