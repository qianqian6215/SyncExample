package cc.xiaoyuanzi.syncexample;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import cc.xiaoyuanzi.syncexample.provider.ProviderConstant;


public class MainActivity extends Activity implements SharedPreferences.OnSharedPreferenceChangeListener{

    private View mLoginView;
    private TextView mAccountInfoView;
    private TextView mSyncContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoginView = findViewById(R.id.login);
        mAccountInfoView = (TextView)findViewById(R.id.account_info);
        mSyncContentView = (TextView)findViewById(R.id.sync_content);
    }

    @Override
    protected void onResume() {
        updateViewStatus();
        super.onResume();
    }

    private void updateViewStatus() {
        Account[] accounts = AccountManager.get(this).
                getAccountsByType(getString(R.string.account_type));
        if(accounts != null && accounts.length > 0) {
            //now we only use the first account
            mAccountInfoView.setText("Hello, "+accounts[0].name);
            mLoginView.setVisibility(View.GONE);
            updateSyncContent();
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
        updateSyncContent();
    }

    @Override
    protected void onDestroy() {
        getPreference().unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }

    private void updateSyncContent() {
        mSyncContentView.setText(getPreference().getString(ProviderConstant.KEY_TEST_PREFRENCE_DATA,""));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
