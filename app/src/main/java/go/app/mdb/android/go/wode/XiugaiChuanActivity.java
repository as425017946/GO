package go.app.mdb.android.go.wode;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import go.app.mdb.android.go.R;
import go.app.mdb.android.go.mapplication.MyApplication;

public class XiugaiChuanActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiugai_chuan);
        ButterKnife.bind(this);
        fanhui();
        dizhi();
        MyApplication.getInstance().addActivity(this);
        preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        editor = preferences.edit();
    }
    //返回按钮和修改标题
    @BindView(R.id.fenye_fanhui)
    LinearLayout ll_fanhui;
    @BindView(R.id.fenye_title)
    TextView tv_title;
    private void fanhui(){
        //修改标题
        tv_title.setText("修改连接地址");
        //点击返回按钮
        ll_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XiugaiChuanActivity.this.finish();
            }
        });
    }
    @BindView(R.id.et_lianjiedizhi)
    EditText ed_lianjie;
    @BindView(R.id.tv_dizhitijiao)
    TextView tv_tijiao;
    String info;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private void dizhi(){
        tv_tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(ed_lianjie.getText().toString())){

                    Toast.makeText(XiugaiChuanActivity.this, "请输入新连接地址", Toast.LENGTH_SHORT).show();

                }else{
                    info = ed_lianjie.getText().toString();
                    //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                    AlertDialog.Builder builder = new AlertDialog.Builder(XiugaiChuanActivity.this);
                    //    设置Title的内容
                    builder.setTitle(info);
//                    //    设置Content来显示一个信息
//                    builder.setMessage(info);
                    //    设置一个PositiveButton
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            editor.remove("qingqiudizhi");
                            editor.putString("qingqiudizhi", info);
                            editor.commit();
                            Toast.makeText(XiugaiChuanActivity.this,"地址修改成功，请重新登录！",Toast.LENGTH_SHORT).show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    MyApplication.getInstance().exit();
                                    android.os.Process.killProcess(android.os.Process.myPid());

                                }
                            }, 2000);
                        }
                    });
                    //    设置一个NegativeButton
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {

                        }
                    });
                    builder.show();
                }

            }
        });
    }

}
