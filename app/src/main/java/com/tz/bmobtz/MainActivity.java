package com.tz.bmobtz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.bmob.v3.Bmob;

public class MainActivity extends AppCompatActivity {

    EditText memberName, memberNum;
    TextView content;
    Button send, demand, obtain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "411c42edaac6a8926d3d635362090f22");

        initView();

    }
    private void initView() {
        memberName = (EditText) findViewById(R.id.et_name);
        memberNum = (EditText) findViewById(R.id.et_num);
        content = (TextView) findViewById(R.id.tv_content);
        send = (Button) findViewById(R.id.bt_send);
        demand = (Button) findViewById(R.id.bt_demand);
        obtain = (Button) findViewById(R.id.bt_obtain);
    }
}
