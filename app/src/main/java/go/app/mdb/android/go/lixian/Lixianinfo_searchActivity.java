package go.app.mdb.android.go.lixian;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import go.app.mdb.android.go.R;
import go.app.mdb.android.go.bean.ChuanjianBean;
import go.app.mdb.android.go.bean.Hezuodanwei;
import go.app.mdb.android.go.dingdan.ChuanjianActivity;
import go.app.mdb.android.go.dingdan.DdSearch;
import go.app.mdb.android.go.dingdan.DdSrearchMyAdapter;
import go.app.mdb.android.go.tools.Api;
import go.app.mdb.android.go.tools.Helptools;
import go.app.mdb.android.go.tools.MyDialog;
import go.app.mdb.android.go.wode.Wode_lixian;
import okhttp3.Call;
import okhttp3.Response;

import static go.app.mdb.android.go.lixian.LixinaMainActivity.lxlogo_name;


public class Lixianinfo_searchActivity extends AppCompatActivity {

    //获取标题
    @BindView(R.id.fenye_title)
    TextView tv_title;
    //返回整体按钮
    @BindView(R.id.fenye_fanhui)
    LinearLayout linearLayout_fanhui;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dd_search);
        ButterKnife.bind(this);
        init();
        fanhui();
        xiugai();
        delete();
        addshuju();
        up_wenjian();
    }

    /***
     * 增加返回键事件
     */
    private void fanhui() {
        linearLayout_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Lixianinfo_searchActivity.this.finish();
            }
        });
    }

    /**
     * 修改标题
     * 添加传过来的数据
     */
    @BindView(R.id.dd_sh_tv_ddhao)
    TextView editText_ddhao;
    @BindView(R.id.dd_sh_tv_hezuodanwei)
    TextView editText_hezuodanwei;
    @BindView(R.id.dd_sh_tv_settime)
    EditText editText_settime;
    @BindView(R.id.dd_sh_tv_uptime)
    EditText editText_uptime;
    @BindView(R.id.dd_sh_tv_ddstate)
    EditText editText_ddstate;
    @BindView(R.id.dd_sh_tv_upstate)
    EditText editText_upstate;
    String goodsid,ddhao;
    String[] shuzu_goodsid;
    @BindView(R.id.huadong_tishi)
    TextView tv_tishi;
    @BindView(R.id.dd_sh_tv_hejizonge)
    TextView dd_zonge;
    float sum = 0 ,shu,jiage;
    String henzuoname;
    private void init() {
        tv_title.setText("订单详情");
        //先让Editext控件不能编辑
        editText_ddhao.setEnabled(false);
        editText_hezuodanwei.setEnabled(false);
        editText_settime.setEnabled(false);
        editText_uptime.setEnabled(false);
        editText_ddstate.setEnabled(false);
        editText_upstate.setEnabled(false);
        Bundle bundle = getIntent().getExtras();
        ddhao = bundle.getString("ddhao");
        henzuoname = bundle.getString("henzuoname");
        String settime = bundle.getString("settime");
        String uptime = bundle.getString("uptime");
        String ddstate = bundle.getString("ddstate");
        String scstate = bundle.getString("scstate");
        goodsid = bundle.getString("goodsid");
        editText_ddhao.setText(ddhao);
        editText_hezuodanwei.setText(henzuoname);
        editText_settime.setText(settime);
        editText_uptime.setText(uptime);
        editText_ddstate.setText(ddstate);
        editText_upstate.setText(scstate);
//        Log.e("离线详细页面",goodsid);
        shuzu_goodsid = goodsid.split(",");
        Log.e("shuzu_goodsid",shuzu_goodsid.length+"");
        if(shuzu_goodsid.length>1){
            tv_tishi.setVisibility(View.VISIBLE);
        }
        String wodeOrlixian = bundle.getString("wodeOrlixian");
        if(wodeOrlixian.equals("1")){
            textView_xiugai.setVisibility(View.GONE);
            tv_up.setVisibility(View.VISIBLE);

        }else if((wodeOrlixian.equals("0"))){
            textView_xiugai.setVisibility(View.VISIBLE);
            tv_up.setVisibility(View.GONE);

        }

//        Log.e("离线详细页面goodsid长度",""+shuzu_goodsid.length);
//        for (int i = 0; i <shuzu_goodsid.length ; i++) {
//            Log.e("离线详细页面goodsid",shuzu_goodsid[i]);
//        }


    }

    /**
     * 上传
     */
    @BindView(R.id.dd_sh_tv_shangchuan)
    TextView tv_up;
    int a;
    private void up_wenjian(){
        tv_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = Helptools.getDb(Lixianinfo_searchActivity.this);
                Log.e("有网页面的离线订单的人名",""+lxlogo_name);
                Cursor cs = db.query("orderform", null, "ddhao="+"'"+ddhao+"'", null, null, null, null, null);

                while (cs.moveToNext()){
//            Log.e("orderform",cs.getString(cs.getColumnIndex("hezuoname")));
                    if(cs.getString(cs.getColumnIndex("scstate")).equals("1")){
                        Toast.makeText(Lixianinfo_searchActivity.this,"已上传，无需在上传订单！",Toast.LENGTH_LONG).show();
                    }else {
                        OkGo.post(Api.dd_chuangjian)
                                .tag(this)
                                .params("parterID", cs.getString(cs.getColumnIndex("hezuo_id")))
                                .params("parterName", cs.getString(cs.getColumnIndex("hezuoname")))
                                .params("salesmanName", lxlogo_name)
                                .params("isUpdate", "1")
                                .params("billCode", cs.getString(cs.getColumnIndex("ddhao")))
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
                                        Gson gson = new Gson();
                                        ChuanjianBean cjbean = gson.fromJson(s, ChuanjianBean.class);
                                        Log.e("返回值", cjbean.getPerson().get(0).getLastId() + "");
                                        for (a = 0; a < shuzu_goodsid.length; a++) {
                                            Cursor cs = db.query("goods", null, "goodsid=" + "'" + shuzu_goodsid[a] + "'", null, null, null, null);
                                            while (cs.moveToNext()){
                                                OkGo.post(Api.dd_xiangqing)
                                                        .tag(this)
                                                        .params("mainID", cjbean.getPerson().get(0).getLastId() + "")
                                                        .params("mainBillCode", ddhao)
                                                        .params("inventoryID", cs.getString(cs.getColumnIndex("id")))
                                                        .params("inventoryName", cs.getString(cs.getColumnIndex("name")))
                                                        .params("quantity", cs.getString(cs.getColumnIndex("num")))
                                                        .params("unitID", cs.getString(cs.getColumnIndex("guigeid")))
                                                        .params("salePrice", cs.getString(cs.getColumnIndex("price")))
                                                        .params("remark", cs.getString(cs.getColumnIndex("beizhu")))
                                                        .execute(new StringCallback() {
                                                            @Override
                                                            public void onSuccess(String s, Call call, Response response) {
                                                                Log.e("销售详情", s);
                                                            }
                                                        });
                                            }

                                        }
                                        if (a == shuzu_goodsid.length) {
                                            String sql = "update orderform set scstate=1 where goodsid=" + "'" + goodsid + "'";
                                            db.execSQL(sql);
                                            db.close();
                                            Toast.makeText(Lixianinfo_searchActivity.this,"创建成功",Toast.LENGTH_SHORT).show();
                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {

                                                    Lixianinfo_searchActivity.this.finish();

                                                }
                                            }, 1000);
                                        }
                                        db.close();


                                    }
                                });
                    }
                }
            }
        });


    }


    /**
     * 修改
     */
    @BindView(R.id.dd_sh_tv_xiugai)
    TextView textView_xiugai;
    String state_dd="0";
    private void xiugai(){
        textView_xiugai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    db = Helptools.getDb(Lixianinfo_searchActivity.this);
                    final CharSequence[] items = {"未完成", "已完成", "作废"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(Lixianinfo_searchActivity.this);
                    builder.setTitle("请选择订单状态");
                    builder.setCancelable(false);
                    builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            state_dd = items[i].toString();
                            if(state_dd.equals("未完成")){
                                state_dd = "0";
                            }else if(state_dd.equals("已完成")){
                                state_dd = "1";
                            }
                            //0、未完成  1、已完成 2、作废

                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
//                            Log.e("4111111111111选择的值",""+state_dd);
                            //插入修改语句
                            String sql = "update orderform set ddstate="+"'"+state_dd+"'"+" where goodsid="+"'"+goodsid+"'" +"and hezuodanwei="+"'"+henzuoname+"'" ;
                            db.execSQL(sql);
                            db.close();
                            dialogInterface.dismiss();
                            Toast.makeText(Lixianinfo_searchActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Lixianinfo_searchActivity.this.finish();
                                }
                            },1000 );

                        }
                    });
                    builder.show();



            }

        });

    }

    /**
     * 删除
     */
    @BindView(R.id.dd_sh_tv_delete)
    TextView textView_delete;
    private void delete(){
        textView_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialog.show(Lixianinfo_searchActivity.this,"确认删除这个整条订单信息吗？", new MyDialog.OnConfirmListener() {
                    @Override
                    public void onConfirmClick() {
                        //点击是删除此条信息
                        db = Helptools.getDb(Lixianinfo_searchActivity.this);
                        String sql1 = "delete from goods where goodsid="+"'"+goodsid+"'" +"and hezuodanwei="+"'"+henzuoname+"'" ;
                        String sql2 = "delete from orderform where goodsid="+"'"+goodsid+"'" +"and hezuodanwei="+"'"+henzuoname+"'" ;
                        db.execSQL(sql1);
                        db.execSQL(sql2);
                        db.close();
                        Toast.makeText(Lixianinfo_searchActivity.this,"删除成功，等待跳转",Toast.LENGTH_SHORT).show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Lixianinfo_searchActivity.this.finish();

                            }
                        }, 1000);

                    }
                });

            }
        });

    }

    /**
     * 添加本地数据库的数据
     */
    DdSrearchMyAdapter ddSrearchMyAdapter;
    ArrayList<DdSearch> ddSearchArrayList = new ArrayList<>();
    @BindView(R.id.serch_lt)
    ListView listView;
    SQLiteDatabase db;
    private void addshuju(){
        for (int i = 0 ; i < shuzu_goodsid.length ; i++){
            db = Helptools.getDb(Lixianinfo_searchActivity.this);
            Cursor cs=db.query("goods",null,"goodsid="+"'"+shuzu_goodsid[i]+"' order by goodsid desc",null,null,null,null);

            while (cs.moveToNext()){
                //填入信息
                DdSearch ddSearch = new DdSearch();
                ddSearch.setName(cs.getString(cs.getColumnIndex("name")));
                ddSearch.setBeizhu(cs.getString(cs.getColumnIndex("beizhu")));
                ddSearch.setGuige(cs.getString(cs.getColumnIndex("guige")));
                ddSearch.setNum(cs.getString(cs.getColumnIndex("num")));
                ddSearch.setPrice(cs.getString(cs.getColumnIndex("price")));
                ddSearchArrayList.add(ddSearch);
            }

            ddSrearchMyAdapter = new DdSrearchMyAdapter(this,ddSearchArrayList);
            listView.setAdapter(ddSrearchMyAdapter);
            //计算总额
            for (int a = 0; a <ddSearchArrayList.size() ; a++) {
                DdSearch ddSearch = ddSearchArrayList.get(a);
//                 shu = Float.parseFloat(ddSearch.getNum());
//                 jiage =Float.parseFloat(ddSearch.getPrice());
                if (TextUtils.isEmpty(ddSearch.getNum())){
                    shu = 0;
                }else {
                    shu = Float.parseFloat(ddSearch.getNum());
                }
                if (TextUtils.isEmpty(ddSearch.getPrice())){
                    jiage = 0;
                }else {
                    jiage = Float.parseFloat(ddSearch.getPrice());
                }

                sum = (shu * jiage) + sum;
            }
            dd_zonge.setText(sum+"元");
            db.close();
        }

    }


}
