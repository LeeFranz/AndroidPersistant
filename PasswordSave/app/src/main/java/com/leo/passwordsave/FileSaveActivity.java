package com.leo.passwordsave;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileSaveActivity extends AppCompatActivity {
    private static final String TAG = "FileSaveActivity";

    private Button mNext;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_save);
        mNext = (Button) findViewById(R.id.fileText);
        mEditText = (EditText) findViewById(R.id.editText);
        String input = load();
        if (!TextUtils.isEmpty(input)) {
            mEditText.setText(input);
            mEditText.setSelection(input.length());
        }
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.leo.action.sharedpref.ACTIVITY");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        String fileTextContent = mEditText.getText().toString();
        save(fileTextContent);
        super.onDestroy();
    }

    private void save(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput("filedata", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(text);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    Log.e(TAG, "some thing wrong while closing writer");
                }
            }
        }
    }

    private String load() {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = openFileInput("filedata");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    Log.e(TAG, "some thing wrong while closing reader");
                }
            }
        }
        return content.toString();
    }
}
