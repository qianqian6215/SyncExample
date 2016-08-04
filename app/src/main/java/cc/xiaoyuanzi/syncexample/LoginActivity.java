package cc.xiaoyuanzi.syncexample;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cc.xiaoyuanzi.syncexample.provider.ProviderConstant;


public class LoginActivity extends Activity{

    private TextView mNameTextView;
    private TextView mPasswordTextView;
    private TextView mSyncContentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mNameTextView = (TextView) findViewById(R.id.login_name);
        mPasswordTextView = (TextView) findViewById(R.id.password);
        mSyncContentTextView = (TextView)findViewById(R.id.sync_content);
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name =  mNameTextView.getText().toString();
                String password =  mPasswordTextView.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this,
                            R.string.login_content_error, Toast.LENGTH_SHORT);
                } else {
                    String type = LoginActivity.this.getString(R.string.account_type);
                    Account account = new Account(name, type);
                    Bundle bundle = new Bundle();
                    bundle.putString(AccountManager.KEY_ACCOUNT_NAME,name);
                    AccountManager.get(LoginActivity.this).
                            addAccountExplicitly(account, password, bundle);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    String authority = getString(R.string.provider_authority);
                    ContentResolver.setSyncAutomatically(account, authority, true);
                    ContentResolver.addPeriodicSync(account, authority, bundle, 60);
                    finish();
                }
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
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
