package cc.xiaoyuanzi.syncexample;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cc.xiaoyuanzi.syncexample.provider.ProviderConstant;
import cc.xiaoyuanzi.syncexample.sync.data.Utils;


public class MainActivity extends Activity implements SharedPreferences.OnSharedPreferenceChangeListener{

    //the interval for each AD Pic
    private static final int FLIP_INTERVAL = 5000;

    private static final String TAG = "ADPlayerActivity";
    private View mLoginView;
    private TextView mAccountInfoView;
    private TextView mSyncTimeView;
    private ViewFlipper mViewFlipper;
    private List<Bitmap> adBmps = new ArrayList<Bitmap>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoginView = findViewById(R.id.login);
        mAccountInfoView = (TextView)findViewById(R.id.account_info);
        mSyncTimeView = (TextView)findViewById(R.id.sync_time);
        updateViewStatus();

    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    private void updateViewStatus() {
        Account[] accounts = AccountManager.get(this).
                getAccountsByType(getString(R.string.account_type));
        if(accounts != null && accounts.length > 0) {
            //now we only use the first account
            mAccountInfoView.setText("Hello, "+accounts[0].name);
            mLoginView.setVisibility(View.GONE);
            mSyncTimeView.setText(getPreference().getString(ProviderConstant.KEY_TEST_PREFRENCE_DATA,""));
            initControl();
            getPreference().registerOnSharedPreferenceChangeListener(this);
        } else {
            mAccountInfoView.setText(R.string.no_account);
            mLoginView.setVisibility(View.VISIBLE);
            mLoginView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    private SharedPreferences getPreference() {
        return getSharedPreferences
                (ProviderConstant.KEY_PREFRENCE_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Log.d("eeee","onSharedPreferenceChanged");
        mSyncTimeView.setText(getPreference().getString(ProviderConstant.KEY_TEST_PREFRENCE_DATA,""));
        updateSyncContent();
    }

    @Override
    protected void onDestroy() {
        getPreference().unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }

    private void updateSyncContent() {
        mViewFlipper.stopFlipping();
        mViewFlipper.removeAllViews();
        recycleBitmaps();
        adBmps.clear();
        loadResources();
        mViewFlipper.startFlipping();
    }

    private void initControl() {
        mViewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
        //find the resources
        loadResources();
        //init the params
        mViewFlipper.setAutoStart(true);
        mViewFlipper.setFlipInterval(FLIP_INTERVAL);
        //in animation
        mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
                R.anim.push_left_in));
        //out animation
        mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                R.anim.push_left_out));


    }

    private void loadResources() {
        List<File> adResources = Utils.getADResources(this);
        Log.d(TAG, String.format("get the count of AD resources is %d", adResources.size()));
        for (File file : adResources) {
            String path = file.getAbsolutePath();
            Bitmap object = BitmapFactory.decodeFile(path);
            adBmps.add(object);
            mViewFlipper.addView(createADDrawable(object));
        }


    }

    private View createADDrawable(Bitmap bitmap) {
        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
        View adView = getLayoutInflater().inflate(R.layout.ad_drawable, null);
        adView.setBackgroundDrawable(drawable);
        return adView;
    }

    private synchronized void recycleBitmaps() {
        Log.d(TAG, "start recycle bitmaps");
        for (Bitmap bitmap : adBmps) {
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}
