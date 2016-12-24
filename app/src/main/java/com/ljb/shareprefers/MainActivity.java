package com.ljb.shareprefers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ljb.shareprefers.helper.MainHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTextContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextContent = (TextView) findViewById(R.id.tv_main_content);
        findViewById(R.id.btn_get_float).setOnClickListener(this);
        findViewById(R.id.btn_save_float).setOnClickListener(this);
        findViewById(R.id.btn_get_string).setOnClickListener(this);
        findViewById(R.id.btn_save_string).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save_float:
                MainHelper.saveUserAge(11.3f);
                break;
            case R.id.btn_save_string:
                MainHelper.saveUserName("liu");
                break;
            case R.id.btn_get_float:
                mTextContent.setText(String.valueOf(MainHelper.getUserAge()));
                break;
            case R.id.btn_get_string:
                mTextContent.setText(MainHelper.getUserName());
                break;
        }
    }
}
