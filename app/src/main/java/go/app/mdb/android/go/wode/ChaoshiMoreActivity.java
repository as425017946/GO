package go.app.mdb.android.go.wode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import go.app.mdb.android.go.Dd_searchActivity;
import go.app.mdb.android.go.R;
import go.app.mdb.android.go.bean.DingdanHaoSearch;
import go.app.mdb.android.go.dingdan.DdSearch;
import go.app.mdb.android.go.dingdan.DdSrearchMyAdapter;
import go.app.mdb.android.go.tools.Api;
import okhttp3.Call;
import okhttp3.Response;

import static go.app.mdb.android.go.LogoActivity.logo_name;

public class ChaoshiMoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaoshi_more);
        ButterKnife.bind(this);
        fanhui();
        tv_title.setText("店铺订单查询详情");
        init();
    }
    /**
     * 返回
     */
    //获取标题
    @BindView(R.id.fenye_title)
    TextView tv_title;
    @BindView(R.id.fenye_fanhui)
    LinearLayout linearLayout_fanhui;
    private void fanhui(){

        linearLayout_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChaoshiMoreActivity.this.finish();
            }
        });
    }
    /**展示网络信息**/
    DdSrearchMyAdapter ddSrearchMyAdapter;
    ArrayList<DdSearch> ddSearchArrayList = new ArrayList<>();
    @BindView(R.id.dd_sh_tv_ddhao)
    TextView editText_ddhao;
    @BindView(R.id.dd_sh_tv_hezuodanwei)
    TextView editText_hezuodanwei;
    @BindView(R.id.dd_sh_tv_hejizonge)
    TextView dd_zonge;
    String ddhao,dd_id;
    private void init(){
        //填入信息
        Bundle bundle = getIntent().getExtras();
        ddhao = bundle.getString("ddhao");
        dd_id = bundle.getString("dd_id");
        Log.e("超市详情id",dd_id);
        String henzuoname = bundle.getString("hzdw");
        editText_ddhao.setText(ddhao);
        editText_hezuodanwei.setText(henzuoname);
        addinfo_ddid();
    }
    //增加网络信息
    @BindView(R.id.serch_lt)
    ListView listView;
    @BindView(R.id.cs_more_tijiao)
    Button btn_tijiao;
    float sum= 0,shu,jiage;
    private void addinfo_ddid(){
        //Log.e("网络数据***",ddhao);
        OkGo.post(Api.dd_idchaxun)
                .tag(this)
                .params("mainBillCode",ddhao)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        // Log.e("网络数据",s);
                        ddSearchArrayList.clear();
                        Gson gson = new Gson();
                        DingdanHaoSearch ddh =gson.fromJson(s,DingdanHaoSearch.class);
                        if (ddh.getState().equals("1")){
//                            btn_tijiao.setVisibility(View.VISIBLE);
                            for (int i = 0; i <ddh.getPerson().size() ; i++) {
                                DdSearch ddSearch = new DdSearch();
                                ddSearch.setName(ddh.getPerson().get(i).getInventoryName());
                                ddSearch.setBeizhu(ddh.getPerson().get(i).getRemark());
                                ddSearch.setGuige(ddh.getPerson().get(i).getUnit());
                                ddSearch.setNum(ddh.getPerson().get(i).getQuantity()+"");
                                ddSearch.setPrice(ddh.getPerson().get(i).getSalePrice()+"");
                                ddSearchArrayList.add(ddSearch);
                            }
                            ddSrearchMyAdapter = new DdSrearchMyAdapter(ChaoshiMoreActivity.this,ddSearchArrayList);
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
//                            btn_tijiao.setVisibility(View.GONE);
                            Toast.makeText(ChaoshiMoreActivity.this,"此订单暂无商品信息！",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
//        btn_tijiao.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                OkGo.post(Api.chaoshilistmoretijiao)
//                        .tag(this)
//                        .params("salesmanName",logo_name)
//                        .params("orderId",dd_id)
//                        .execute(new StringCallback() {
//                            @Override
//                            public void onSuccess(String s, Call call, Response response) {
//                                    Log.e("超市提交成功",s);
//
//                            }
//                        });
//            }
//        });

    }
}
