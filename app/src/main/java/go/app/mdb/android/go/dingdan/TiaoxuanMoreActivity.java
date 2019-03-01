package go.app.mdb.android.go.dingdan;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import go.app.mdb.android.go.R;
import go.app.mdb.android.go.bean.TiaoxuanMoreBean;
import go.app.mdb.android.go.bean.Unit;
import go.app.mdb.android.go.tools.Api;
import go.app.mdb.android.go.tools.DatabaseHelper;
import go.app.mdb.android.go.tools.Helptools;
import go.app.mdb.android.go.tools.Utils;
import okhttp3.Call;
import okhttp3.Response;

import static go.app.mdb.android.go.LogoActivity.logo_name;
import static go.app.mdb.android.go.dingdan.ChuanjianActivity2.fenjiid;
import static go.app.mdb.android.go.dingdan.ChuanjianActivity2.hezuodanweiid;
import static go.app.mdb.android.go.dingdan.ChuanjianActivity2.hezuodanweiname;
import static go.app.mdb.android.go.dingdan.TiaoxuanActivity.goodsids;
import static go.app.mdb.android.go.lixian.LixinaMainActivity.lxlogo_name;

public class TiaoxuanMoreActivity extends AppCompatActivity {

    /**
     * 挑选商品详细
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiaoxuan_more);
        ButterKnife.bind(this);
        fanhui();
        init();
        tijiao();
    }
    //返回按钮和修改标题
    @BindView(R.id.fenye_fanhui)
    LinearLayout ll_fanhui;
    @BindView(R.id.fenye_title)
    TextView tv_title;
    private void fanhui(){
        //修改标题
        tv_title.setText("挑选商品");
        //点击返回按钮
        ll_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TiaoxuanMoreActivity.this.finish();
            }
        });
    }

    /**
     * 添加网络数据
     */
    @BindView(R.id.txmore_goods)
    TextView tx_name;
    @BindView(R.id.txmore_guige)
    TextView tx_guige;
    @BindView(R.id.txmore_danwei)
    TextView tx_danwei;
    @BindView(R.id.txmore_jiage)
    TextView tx_jiage;
    @BindView(R.id.txmore_kucun)
    TextView tx_kucun;
    @BindView(R.id.txmore_shuliang)
    EditText ed_shuliang;
    @BindView(R.id.txmore_beizhu)
    EditText ed_beizhu;
    String guigeid;
    private void init(){

      //  Log.e("分级",fenjiid+"****"+goodsids);
        OkGo.post(Api.weiyi_goods)
                .tag(this)
                .params("state",fenjiid)
                .params("id",goodsids)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                      //  Log.e("我的信息",s);
                        Gson gson = new Gson();
                        TiaoxuanMoreBean tx_bean = gson.fromJson(s,TiaoxuanMoreBean.class);
                        if (tx_bean.getState().equals("1")){
                            tx_name.setText(tx_bean.getPerson().get(0).getName());
                            tx_guige.setText(tx_bean.getPerson().get(0).getSpecification());
                            tx_danwei.setText(tx_bean.getPerson().get(0).getUnit());
                            tx_jiage.setText(tx_bean.getPerson().get(0).getStepPrice()+"");
                            tx_kucun.setText(tx_bean.getPerson().get(0).getStockQuantity()+"");
                            OkGo.post(Api.danwei)
                                    .tag(this)
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            Gson gson1 =new Gson();
                                            Unit unit = gson1.fromJson(s,Unit.class);
                                            for (int i = 0; i <unit.getPerson().size() ; i++) {
                                                if (tx_danwei.getText().toString().equals(unit.getPerson().get(i).getName())){
                                                    guigeid = unit.getPerson().get(i).getID()+"";
                                                }
                                            }
                                        }
                                    });

                        }else{
                            Toast.makeText(TiaoxuanMoreActivity.this,"暂无查询数据",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @BindView(R.id.txmore_tijiao)
    Button btn_tijiao;
    SQLiteDatabase db;
    private void tijiao(){
        btn_tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ed_shuliang.getText().toString().equals("")){
                    Toast.makeText(TiaoxuanMoreActivity.this, "请输入数量！", Toast.LENGTH_SHORT).show();

                }else {
                    if (Utils.isFastClick()) {
                        // 进行点击事件后的逻辑操作
                        db = Helptools.getDb(TiaoxuanMoreActivity.this);
                        Cursor cs = db.query("goods_moshi1", null, "hezuodanwei="+"'"+hezuodanweiname+"' and id ='"+goodsids+"'", null, null, null, null, null);

                        if(cs.getCount()>0){
                            Toast.makeText(TiaoxuanMoreActivity.this, "你已添加过该商品！", Toast.LENGTH_SHORT).show();
                            db.close();
                        }else {
                            db.close();
                            db  = Helptools.wriDb(TiaoxuanMoreActivity.this);
                            ContentValues cv = new ContentValues();
                            cv.put("name", "" + tx_name.getText().toString());
                            cv.put("id",goodsids);
                            cv.put("guige", "" + tx_guige.getText().toString()); //
                            cv.put("guigeid", "" + guigeid);
                            cv.put("danwei", "" + tx_danwei.getText().toString());
                            cv.put("num", "" + ed_shuliang.getText().toString());
                            cv.put("price", "" + tx_jiage.getText().toString());
                            cv.put("beizhu", "" + ed_beizhu.getText().toString());
                            cv.put("hezuodanwei", "" + hezuodanweiname);
                            db.insert("goods_moshi1", null, cv);
                            db.close();
                           // Log.e("goodsid","+"+goodsids+"**guigeid:"+guigeid);
                            //信息添加成功了，提示
                            Toast.makeText(TiaoxuanMoreActivity.this, "添加商品成功", Toast.LENGTH_SHORT).show();
                            TiaoxuanMoreActivity.this.finish();
                        }


                    }
                }


            }
        });
    }
}
