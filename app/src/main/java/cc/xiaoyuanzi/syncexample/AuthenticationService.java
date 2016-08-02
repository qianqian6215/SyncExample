package cc.xiaoyuanzi.syncexample;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import java.io.FileDescriptor;

public class AuthenticationService extends Service {

    private CCAthenticator mAuthenticator;
    public AuthenticationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        if(mAuthenticator == null) {
            mAuthenticator = new CCAthenticator(AuthenticationService.this);
        }
        return mAuthenticator.getIBinder();
    }

    private class CCAthenticator extends AbstractAccountAuthenticator {

        public CCAthenticator(Context context) {
            super(context);
        }

        @Override
        public Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse, String s) {
            return null;
        }

        @Override
        public Bundle addAccount(AccountAuthenticatorResponse accountAuthenticatorResponse, String s, String s2, String[] strings, Bundle bundle) throws NetworkErrorException {

//            String name = "cici";
//            String type = "xiaoyuanzi.cc.AccountType";
//            String password = "123456";
//            Account account = new Account(name,type);
//            AccountManager.get(AuthenticationService.this).addAccountExplicitly(account,password,null);
            Log.d("eee", (bundle==null?"empty":bundle.toString()));
            Bundle ret = new Bundle();
            Intent intent=new Intent(AuthenticationService.this,LoginActivity.class);
            //intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
            ret.putParcelable(AccountManager.KEY_INTENT, intent);
            return ret;
        }

        @Override
        public Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, Bundle bundle) throws NetworkErrorException {
            return null;
        }

        @Override
        public Bundle getAuthToken(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String s, Bundle bundle) throws NetworkErrorException {
            return null;
        }

        @Override
        public String getAuthTokenLabel(String s) {
            return null;
        }

        @Override
        public Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String s, Bundle bundle) throws NetworkErrorException {
            return null;
        }

        @Override
        public Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String[] strings) throws NetworkErrorException {
            return null;
        }

    }
}
