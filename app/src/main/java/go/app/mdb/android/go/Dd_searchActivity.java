package go.app.mdb.android.go;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import go.app.mdb.android.go.bean.DingdanHaoSearch;
import go.app.mdb.android.go.dingdan.DdSearch;
import go.app.mdb.android.go.dingdan.DdSrearchMyAdapter;
import go.app.mdb.android.go.dingdan.Gouwuche;
import go.app.mdb.android.go.lixian.Lixianinfo_searchActivity;
import go.app.mdb.android.go.tools.Api;
import go.app.mdb.android.go.tools.Helptools;
import go.app.mdb.android.go.tools.MyDialog;
import okhttp3.Call;
import okhttp3.Response;

public class Dd_searchActivity extends AppCompatActivity {

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
        addinfo_ddid();

    }
    String ddhao,dd_id;
    private void init() {
        tv_title.setText("订单详情");
        //填入信息
        Bundle bundle = getIntent().getExtras();
        ddhao = bundle.getString("ddhao");
        dd_id = bundle.getString("dd_id");
        String henzuoname = bundle.getString("hzdw");
        String settime = bundle.getString("creattime");
        String uptime = bundle.getString("uptime");
        String ddstate = bundle.getString("state");
        String scstate = bundle.getString("upstate");
        editText_ddhao.setText(ddhao);
        editText_hezuodanwei.setText(henzuoname);
        editText_settime.setText(settime);
        editText_uptime.setText(uptime);
        editText_ddstate.setText(ddstate);
        editText_upstate.setText(scstate);
//        Log.e("******id",dd_id);

    }

    DdSrearchMyAdapter ddSrearchMyAdapter;
    ArrayList<DdSearch> ddSearchArrayList = new ArrayList<>();
    @BindView(R.id.serch_lt)
    ListView listView;
    @BindView(R.id.huadong_tishi)
    TextView tv_huadong;
    @BindView(R.id.dd_sh_tv_hejizonge)
    TextView dd_zonge;
    SQLiteDatabase db;
    float sum= 0,shu,jiage;
    //增加网络信息
    private void addinfo_ddid(){
        //Log.e("网络数据***",ddhao);
        OkGo.post(Api.dd_idchaxun)
                .tag(this)
                .params("mainBillCode",ddhao)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("网络数据",s);
                        ddSearchArrayList.clear();
                        Gson gson = new Gson();
                        DingdanHaoSearch ddh =gson.fromJson(s,DingdanHaoSearch.class);
                        if (ddh.getState().equals("1")){

                            if (ddh.getPerson().size()>1){
                                tv_huadong.setVisibility(View.VISIBLE);
                            }else {
                                tv_huadong.setVisibility(View.GONE);
                            }
                            for (int i = 0; i <ddh.getPerson().size() ; i++) {
                                DdSearch ddSearch = new DdSearch();
                                ddSearch.setName(ddh.getPerson().get(i).getInventoryName());
                                ddSearch.setBeizhu(ddh.getPerson().get(i).getRemark());
                                ddSearch.setGuige(ddh.getPerson().get(i).getUnit());
                                ddSearch.setNum(ddh.getPerson().get(i).getQuantity()+"");
                                ddSearch.setPrice(ddh.getPerson().get(i).getSalePrice()+"");
                                ddSearchArrayList.add(ddSearch);
                            }
                            ddSrearchMyAdapter = new DdSrearchMyAdapter(Dd_searchActivity.this,ddSearchArrayList);
                            listView.setAdapter(ddSrearchMyAdapter);
                            //计算总额
                            for (int i = 0; i <ddSearchArrayList.size() ; i++) {
                                DdSearch ddSearch = ddSearchArrayList.get(i);
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
                            float  b   =  (float)(Math.round(sum*100))/100;
                            dd_zonge.setText(b+"元");

//                            LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(Dd_searchActivity.this)
//                                    .setMessage("加载中...")
//                                    .setCancelable(false)
//                                    .setCancelOutside(false);
//                            final LoadingDailog dialog=loadBuilder.create();
//                            dialog.show();
//                            Handler handler =new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    dialog.dismiss();
//                                }
//                            }, 500);

                        }else {
                            Toast.makeText(Dd_searchActivity.this,"此订单暂无商品信息！",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    /***
     * 增加返回键事件
     */
    private void fanhui() {
        linearLayout_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Dd_searchActivity.this.finish();
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
    String goodsid;



    /**
     * 修改
     */
    @BindView(R.id.dd_sh_tv_xiugai)
    TextView textView_xiugai;
    String state_dd = "0";

    private void xiugai(){
        textView_xiugai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = {"未完成", "已完成"};
                AlertDialog.Builder builder = new AlertDialog.Builder(Dd_searchActivity.this);
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
                        Log.e("4111111111111选择的值",""+state_dd+"***"+dd_id);

                        dialogInterface.dismiss();
                        OkGo.post(Api.dd_state)
                                .tag(this)
                                .params("id",dd_id)
                                .params("status",state_dd)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
                                     //  Log.e("修改++++",s);
                                        Toast.makeText(Dd_searchActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Dd_searchActivity.this.finish();
                                            }
                                        },1000 );
                                    }
                                });



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
                MyDialog.show(Dd_searchActivity.this,"确认删除此条信息吗？", new MyDialog.OnConfirmListener() {
                    @Override
                    public void onConfirmClick() {
                        //点击是删除此条信息
                        OkGo.post(Api.dd_state)
                                .tag(this)
                                .params("id",dd_id)
                                .params("status",2)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
                                        Toast.makeText(Dd_searchActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Dd_searchActivity.this.finish();
                                            }
                                        },1000 );
                                    }
                                });
                    }
                });

            }
        });

    }



}
