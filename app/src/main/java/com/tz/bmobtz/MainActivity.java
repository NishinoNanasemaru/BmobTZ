package com.tz.bmobtz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tz.bmobtz.been.FeedBack;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity {

    EditText memberName, memberNum;
    TextView content;
    Button send, demand, obtain;
    FeedBack mFeedBack;

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

    public void doClick(View view) {
        switch (view.getId()) {
            case R.id.bt_send:
                mFeedBack = new FeedBack();
                String name = memberName.getText().toString();
                String num = memberNum.getText().toString();
                if (name.equals(null) || num.equals(null)) {
                    return;
                }
                mFeedBack.setMemberName(name);
                mFeedBack.setMemberNum(num);
                mFeedBack.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            memberName.setText("");
                            memberNum.setText("");
                            Toast.makeText(MainActivity.this, "添加数据成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "添加数据失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.bt_demand:
                break;
            case R.id.bt_obtain:
                content.setText("");
                BmobQuery<FeedBack> query = new BmobQuery<>();
                query.findObjects(new FindListener<FeedBack>() {
                    @Override
                    public void done(List<FeedBack> list, BmobException e) {
                        String string = "";
                        if (e == null) {

                            for (FeedBack feedBack : list) {
                                string += feedBack.getMemberName() + ":"
                                        + feedBack.getMemberNum() + "\n";
                            }
                            content.setText(string);
                        }
                    }
                });


                break;
            default:
                break;
        }
    }
}
