package com.leo.passwordsave;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class SharedPrefActivity extends AppCompatActivity {
    private static final String TAG = "SharedPrefActivity";

    private EditText mUserName;
    private EditText mPassword;
    private CheckBox mRememberPass;
    private Button mLogin;
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shared_pref);
        mUserName = (EditText) findViewById(R.id.user_name);
        mPassword = (EditText) findViewById(R.id.password);
        mRememberPass = (CheckBox) findViewById(R.id.remember_password);
        mLogin = (Button) findViewById(R.id.login);
        //mPref = getSharedPreferences("loging_data", MODE_PRIVATE);
        mPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = mPref.getBoolean("remember_pass", false);
        if (isRemember) {
            mUserName.setText(mPref.getString("username", ""));
            mPassword.setText(mPref.getString("password", ""));
            mRememberPass.setChecked(true);
        }
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditor = mPref.edit();
                mEditor.clear();
                mEditor.putBoolean("remember_pass", mRememberPass.isChecked());
                mEditor.putString("username", mUserName.getText().toString());
                mEditor.putString("password", mPassword.getText().toString());
                mEditor.apply();
                Log.d(TAG, "saving pass");
            }
        });
    }
}
