package go.app.mdb.android.go.dingdan;

import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import go.app.mdb.android.go.LogoActivity;
import go.app.mdb.android.go.R;
import go.app.mdb.android.go.lixian.Lixianinfo_searchActivity;
import go.app.mdb.android.go.tools.Helptools;
import go.app.mdb.android.go.tools.Utils;

public class TiaoxuanMoreItemActivity extends AppCompatActivity {
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiaoxuan_more_item);
        ButterKnife.bind(this);
        fanhui();
        init();
        xiugai();
        shanchu();
    }
    //返回按钮和修改标题
    @BindView(R.id.fenye_fanhui)
    LinearLayout ll_fanhui;
    @BindView(R.id.fenye_title)
    TextView tv_title;
    private void fanhui(){
        //修改标题
        tv_title.setText("修改商品信息");
        //点击返回按钮
        ll_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TiaoxuanMoreItemActivity.this.finish();
            }
        });
    }
    //添加信息
    @BindView(R.id.more_item_goods)
    TextView tv_name;
    @BindView(R.id.more_item_guige)
    TextView tv_guige;
    @BindView(R.id.more_item_danwei)
    TextView tv_danwei;
    @BindView(R.id.more_item_jiage)
    TextView tv_jiage;
    @BindView(R.id.more_item_goumaishu)
    EditText ed_shuliang;
    @BindView(R.id.more_item_beizhu)
    EditText ed_beizhu;
    String xiugai_id,hezuodanweiname;
    private void init(){
        Bundle bundle = getIntent().getExtras();
        tv_name.setText(bundle.getString("name"));
        tv_guige.setText(bundle.getString("guige"));
        tv_danwei.setText(bundle.getString("danwei"));
        tv_jiage.setText(bundle.getString("jiage"));
        ed_shuliang.setText(bundle.getString("shuliang"));
        ed_shuliang.setSelection(ed_shuliang.getText().length());
        ed_beizhu.setText(bundle.getString("beizhu"));
        xiugai_id = bundle.getString("id");
        hezuodanweiname = bundle.getString("hzdwinfo");
        Log.e("合作单位名字",hezuodanweiname);
    }
    /**
     * 修改
     */
    @BindView(R.id.more_item_xiugai)
    Button btn_xiugai;
    private void xiugai(){
        btn_xiugai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isFastClick()) {
                    // 进行点击事件后的逻辑操作
                    db = Helptools.getDb(TiaoxuanMoreItemActivity.this);
                    String sql =  "update goods_moshi1 set num="+"'"+ed_shuliang.getText().toString()+"'"+" where id="+"'"+xiugai_id+"'"+"and hezuodanwei="+"'"+hezuodanweiname+"'" ;
                    String sq2 =  "update goods_moshi1 set beizhu="+"'"+ed_beizhu.getText().toString()+"'"+" where id="+"'"+xiugai_id+"'"+"and hezuodanwei="+"'"+hezuodanweiname+"'" ;
                    // Log.e("更新",sql);
                    db.execSQL(sql);
                    Log.e("新消息",sql);
                    db.execSQL(sq2);
                    db.close();
                    Toast.makeText(TiaoxuanMoreItemActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            TiaoxuanMoreItemActivity.this.finish();
                        }
                    },1000 );
                }


            }
        });
    }
    /**
     * 删除
     */
    @BindView(R.id.more_item_shanchu)
    Button btn_shanchu;
    private void shanchu(){
        btn_shanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Utils.isFastClick()) {
                    // 进行点击事件后的逻辑操作
                    db = Helptools.getDb(TiaoxuanMoreItemActivity.this);
                    String sql1 = "delete from goods_moshi1 where id="+"'"+xiugai_id+"'"+"and hezuodanwei="+"'"+hezuodanweiname+"'" ;
                    db.execSQL(sql1);
                    db.close();
                    Toast.makeText(TiaoxuanMoreItemActivity.this,"删除成功，等待跳转",Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            TiaoxuanMoreItemActivity.this.finish();

                        }
                    }, 1000);

                }

            }
        });

    }
}
