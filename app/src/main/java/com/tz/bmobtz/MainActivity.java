package com.tz.bmobtz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tz.bmobtz.been.FeedBack;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity {

    EditText memberName, memberNum;
    TextView content;
    Button send, demand, obtain, push;
    FeedBack mFeedBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "411c42edaac6a8926d3d635362090f22");
        BmobInstallation.getCurrentInstallation().save();
        BmobPush.startWork(this);

        initView();

    }

    private void initView() {
        memberName = (EditText) findViewById(R.id.et_name);
        memberNum = (EditText) findViewById(R.id.et_num);
        content = (TextView) findViewById(R.id.tv_content);
        send = (Button) findViewById(R.id.bt_send);
        demand = (Button) findViewById(R.id.bt_demand);
        obtain = (Button) findViewById(R.id.bt_obtain);
        push = (Button) findViewById(R.id.bt_push);
    }

    public void doClick(View view) {
        switch (view.getId()) {
            case R.id.bt_send:
                mFeedBack = new FeedBack();
                String name = memberName.getText().toString();
                String num = memberNum.getText().toString();
                if (name.equals("") || num.equals("")) {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else {
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
                }
                break;
            case R.id.bt_demand:
                String named = memberName.getText().toString();
                String numd = memberNum.getText().toString();
                if (named.equals("") & numd.equals("")) {
                    Toast.makeText(this, "输入框不能全为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                BmobQuery<FeedBack> q1 = new BmobQuery<FeedBack>();
                BmobQuery<FeedBack> q2 = new BmobQuery<FeedBack>();
                BmobQuery<FeedBack> query1;
                List<BmobQuery<FeedBack>> andQuery = new ArrayList<BmobQuery<FeedBack>>();
                if (!named.equals("") & !numd.equals("")) {
                    query1 = new BmobQuery<FeedBack>();
                    q1.addWhereEqualTo("memberName", named);
                    q2.addWhereEqualTo("memberNum", numd);
                    andQuery.add(q1);
                    andQuery.add(q2);
                    query1.and(andQuery);
                } else if (numd.equals("")) {
                    q2.addWhereEqualTo("memberName", named);
                    query1 = q2;
                } else {
                    q1.addWhereEqualTo("memberNum", numd);
                    query1 = q1;
                }
                query1.findObjects(new FindListener<FeedBack>() {
                    @Override
                    public void done(List<FeedBack> list, BmobException e) {
                        String string = "";
                        if (e == null) {
                            for (FeedBack feedBack : list) {
                                string += feedBack.getMemberName() + ":"
                                        + feedBack.getMemberNum() + "\n";
                            }
                            content.setText(string);
                        } else {
                            Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
            case R.id.bt_push:
                BmobPushManager push = new BmobPushManager();
                push.pushMessageAll("test");
                //Toast.makeText(this,"0",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
