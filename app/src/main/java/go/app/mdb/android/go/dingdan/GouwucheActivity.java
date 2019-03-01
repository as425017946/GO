package go.app.mdb.android.go.dingdan;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import go.app.mdb.android.go.LogoActivity;
import go.app.mdb.android.go.R;
import go.app.mdb.android.go.bean.Chuangjian2_hzdwBean;
import go.app.mdb.android.go.bean.ChuanjianBean;
import go.app.mdb.android.go.bean.GouwuchetijiaoBean;
import go.app.mdb.android.go.bean.Hezuodanwei;
import go.app.mdb.android.go.lixian.LixianLogoActivity;
import go.app.mdb.android.go.lixian.Lixianinfo_searchActivity;
import go.app.mdb.android.go.tools.Api;
import go.app.mdb.android.go.tools.Helptools;
import go.app.mdb.android.go.tools.MyDialog;
import go.app.mdb.android.go.tools.Utils;
import go.app.mdb.android.go.tools.Utils2;
import okhttp3.Call;
import okhttp3.Response;

import static go.app.mdb.android.go.LogoActivity.logo_id;
import static go.app.mdb.android.go.LogoActivity.logo_name;

public class GouwucheActivity extends AppCompatActivity {
    @BindView(R.id.gwc_ll_zonge)
    LinearLayout ll_zonge;
    @BindView(R.id.gwc_zonge)
    TextView tv_zonge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gouwuche);
        ButterKnife.bind(this);
        fanhui();
        hezuoxinxi();
        chaxun();
        tijiao();
     //   Toast.makeText(GouwucheActivity.this,"xinde！",Toast.LENGTH_SHORT).show();
    }
    //返回按钮和修改标题
    @BindView(R.id.fenye_fanhui)
    LinearLayout ll_fanhui;
    @BindView(R.id.fenye_title)
    TextView tv_title;
    private void fanhui(){
        //修改标题
        tv_title.setText("购物车");
        //点击返回按钮
        ll_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GouwucheActivity.this.finish();
            }
        });
    }

    /**
     * 给下拉框添加信息
     */
    @BindView(R.id.spinner_hzdw)
    AutoCompleteTextView act_hzdw;
    String[] hzdw_name,hzdw_id;
    private ArrayList<String> list_2;
    private ArrayAdapter<String> adapter_2;
    private void hezuoxinxi(){
        act_hzdw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_hzdw.showDropDown();//点击控件显示所有的选项
            }
        });

        list_2 =new ArrayList<>();
        OkGo.post(Api.hezuodanwei)
                .tag(this)
                .params("laSalesmanID",logo_id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        Hezuodanwei hzdw = gson.fromJson(s,Hezuodanwei.class);

                        //Log.e("hzdw.getPerson().size()",hzdw_name.length+"");
                        if (hzdw.getState().equals("1")){
                            hzdw_name = new String[hzdw.getPerson().size()+1];
                            hzdw_id = new String[hzdw.getPerson().size()+1];
                            for (int i = 0; i <hzdw.getPerson().size() ; i++) {
                                hzdw_name[i] = hzdw.getPerson().get(i).getName();
                                hzdw_id[i] = hzdw.getPerson().get(i).getID()+"";
                                list_2.add(hzdw.getPerson().get(i).getName());
                            }
                        }
                    }
                });
        adapter_2 = new ArrayAdapter<String>(this,  R.layout.item_ac, R.id.tv_1, list_2);
        act_hzdw.setAdapter(adapter_2);

    }
    /**
     * 查询
     * */
    float sum = 0,jiage,shu;
    SQLiteDatabase db;
    @BindView(R.id.search_ll2_chaxun)
    Button btn_chaxun;
    @BindView(R.id.gouwuche_list)
    ListView gouwuche_list;
    GouwucheAdapter gwc_adapter;
    ArrayList<Gouwuche> arrayList = new ArrayList<Gouwuche>();
    public static String fenjiid,hezuodanweiname="",hezuodanweiid; //保存分级id静态变量和合作单位名称和单位id
    private void chaxun(){
        btn_chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sum = 0;
                if (act_hzdw.getText().toString().equals("")){
                    Toast.makeText(GouwucheActivity.this,"请输入合作单位后查询！",Toast.LENGTH_SHORT).show();
                }else{

                    OkGo.post(Api.mohu_gongsi)
                            .tag(this)
                            .params("name",act_hzdw.getText().toString())
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    Log.e("测试",s);
                                    Gson gson = new Gson();
                                    Chuangjian2_hzdwBean cj2 = gson.fromJson(s,Chuangjian2_hzdwBean.class);
//                                    Log.e("7894",s);
                                    if(cj2.getState().equals("1")){
                                        for (int i = 0; i <cj2.getPerson().size(); i++) {
                                            if(cj2.getPerson().get(i).getName().equals(act_hzdw.getText().toString())){
                                                danweiid = cj2.getPerson().get(i).getID()+"";
                                            }
                                        }


                                    }
                                }
                            });
                    db = Helptools.getDb(GouwucheActivity.this);
                    Cursor cs = db.query("goods_moshi1", null, "hezuodanwei="+"'"+act_hzdw.getText().toString()+"'", null, null, null, null, null);
                    arrayList.clear();
                    if(cs.getCount()==0){
                        ll_zonge.setVisibility(View.GONE);
                        String[] empty = new String[0];
                        ArrayAdapter<String> emptyadapter = new ArrayAdapter<String>(GouwucheActivity.this, R.layout.spinner_item2, empty);
                        gouwuche_list.setAdapter(emptyadapter);
                        Toast.makeText(GouwucheActivity.this,"暂无此单位的查询数据！",Toast.LENGTH_SHORT).show();
                    }else {
                        ll_zonge.setVisibility(View.VISIBLE);
                        while (cs.moveToNext()){
                            Gouwuche gwc = new Gouwuche();
                            Log.e("bbbbb",cs.getString(cs.getColumnIndex("danwei")));
                            gwc.setDanwei(cs.getString(cs.getColumnIndex("danwei"))+"");
                            gwc.setGuige(cs.getString(cs.getColumnIndex("guige"))+"");
                            gwc.setJiage(cs.getString(cs.getColumnIndex("price"))+"");
                            gwc.setName(cs.getString(cs.getColumnIndex("name"))+"");
                            gwc.setShuliang(cs.getString(cs.getColumnIndex("num"))+"");
                            gwc.setId(cs.getString(cs.getColumnIndex("id"))+"");
                            gwc.setGuigeid(cs.getString(cs.getColumnIndex("guigeid"))+"");
                            gwc.setBeizhu(cs.getString(cs.getColumnIndex("beizhu"))+"");
                            arrayList.add(gwc);
                        }
                       // Log.e("arrayList",arrayList.size()+"");
                        gwc_adapter = new GouwucheAdapter(GouwucheActivity.this,arrayList);
                        gouwuche_list.setAdapter(gwc_adapter);
                        db.close();
                        //计算总额
                        for (int i = 0; i <arrayList.size() ; i++) {
                            Gouwuche gw = arrayList.get(i);

                            if (TextUtils.isEmpty(gw.getShuliang())){
                                shu = 0;
                            }else {
                                shu = Float.parseFloat(gw.getShuliang());
                            }
                             if (TextUtils.isEmpty(gw.getJiage())){
                                 jiage = 0;
                             }else {
                                 jiage = Float.parseFloat(gw.getJiage());
                             }

                            sum = (shu * jiage) + sum;
                        }
                        float  b   =  (float)(Math.round(sum*100))/100;
                        tv_zonge.setText(b+"元");

                    }


                }

            }
        });
        gouwuche_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//                Log.e("aaaaaaaaaaa","aaa");
//                Log.e("aaaaaaaaaaa",act_hzdw.getText().toString());
                Gouwuche gwc = arrayList.get(i);
                Intent intent = new Intent(GouwucheActivity.this,TiaoxuanMoreItemActivity.class);
                intent.putExtra("name",gwc.getName());
                intent.putExtra("guige",gwc.getGuige());
                intent.putExtra("danwei",gwc.getDanwei());
                intent.putExtra("jiage",gwc.getJiage());
                intent.putExtra("shuliang",gwc.getShuliang());
                intent.putExtra("beizhu",gwc.getBeizhu());
                intent.putExtra("id",gwc.getId());
                intent.putExtra("hzdwinfo",act_hzdw.getText().toString());
                startActivity(intent);
            }
        });

    }
    //产生订单号
    public static String getRandomValue() {
        String str = "";
        SimpleDateFormat formatter    =   new    SimpleDateFormat    ("yyyyMMddHHmmssSSS");
        Date curDate    =   new    Date(System.currentTimeMillis());//获取当前时间
        str = "SO-"+ formatter.format(curDate);
        return str;

    }
    /**
     * 提交
     */
    String danweiid,ddbianhao,cuowuxinxi="";
    int f=0;
    @BindView(R.id.txmore_tijiao)
    Button btn_tijiao;
    Handler handler1 =new Handler();
    private void tijiao(){
        btn_tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("单位id",danweiid);
                if (Utils2.isFastClick()) {
                    // 进行点击事件后的逻辑操作
                    ddbianhao = getRandomValue();
                    if(arrayList.size()==0){
                        Toast.makeText(GouwucheActivity.this,"请查询要提交的合作单位的商品信息！",Toast.LENGTH_SHORT).show();
                    }else {
                        int bb = 0,aa;
                        for (aa = 0; aa < arrayList.size() ; aa++) {
                            Gouwuche gwc = arrayList.get(aa);
                            if (TextUtils.isEmpty(gwc.getId()) || TextUtils.isEmpty(gwc.getName()) || TextUtils.isEmpty(gwc.getShuliang()) || TextUtils.isEmpty(gwc.getGuigeid()) || TextUtils.isEmpty(gwc.getJiage())|| TextUtils.isEmpty(gwc.getDanwei())||TextUtils.isEmpty(gwc.getGuige())) {
                                Toast.makeText(GouwucheActivity.this,"订单中有商品没有正确存入，请删除后在重新添加问题商品！",Toast.LENGTH_LONG).show();
                                bb = 0;
                                break;
                            }else{
                                bb=1;
                            }
                        }


                        if (bb==1 && aa==arrayList.size()){

//                            Log.e("数据","parterID"+danweiid+"-parterName"+act_hzdw.getText().toString()+"-salesmanName"+logo_name+"-billCode"+ddbianhao);
                            OkGo.post(Api.dd_chuangjian)
                                    .tag(this)
                                    .params("parterID",danweiid)
                                    .params("parterName",act_hzdw.getText().toString())
                                    .params("salesmanName",logo_name)
                                    .params("isUpdate","1")
                                    .params("billCode",ddbianhao)
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            Log.e("sad",arrayList.size()+"");
                                            Gson gson = new Gson();
                                            ChuanjianBean cjbean = gson.fromJson(s,ChuanjianBean.class);
                                            String danhaoid = cjbean.getPerson().get(0).getLastId()+"";
                                            if (cjbean.getState().equals("1")){
                                                requestSendUp(danhaoid);
                                            }


                                        }
                                    });
                        }


                    }

                }else{
                    Toast.makeText(GouwucheActivity.this,"您点击速度过瘾频繁，请等待4秒钟后在进行提交订单！",Toast.LENGTH_SHORT).show();
                }




            }
        });


    }
    private void requestSendUp(final String danhaoid ){
        int number=sousuozhanshixinxi2();
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(GouwucheActivity.this)
                .setMessage("提交信息中...")
                .setCancelable(false)
                .setCancelOutside(false);
        final LoadingDailog dialog=loadBuilder.create();
        dialog.show();
        if (number!=0){
//            f++;
            final   Gouwuche gwc = arrayList.get(0);
            OkGo.post(Api.dd_xiangqing)
                    .tag(this)
                    .params("mainID",danhaoid+"")
                    .params("mainBillCode",ddbianhao+"")
                    .params("inventoryID",gwc.getId()+"")
                    .params("inventoryName",gwc.getName()+"")
                    .params("quantity",gwc.getShuliang()+"")
                    .params("unitID",gwc.getGuigeid()+"")
                    .params("salePrice",gwc.getJiage()+"")
                    .params("remark",gwc.getBeizhu()+"")
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            Log.e("销售详情",gwc.getName()+"下标"+f+s);
                            Gson gson = new Gson();
                            GouwuchetijiaoBean gwtj_bean = gson.fromJson(s,GouwuchetijiaoBean.class);
                            if(gwtj_bean.getState().equals("1")){
                                db = Helptools.getDb(GouwucheActivity.this);
                                String sql =  "delete from goods_moshi1 where hezuodanwei="+"'"+act_hzdw.getText().toString()+"'"+"and name="+"'"+gwc.getName()+"'" ;
                                db.execSQL(sql);
                                db.close();
                                requestSendUp(danhaoid);
                            }else{
                                cuowuxinxi +=gwc.getName()+"、";
                            }

                        }
                    });


        }else{
            dialog.dismiss();
            Toast.makeText(GouwucheActivity.this,"订单提交成功！",Toast.LENGTH_SHORT).show();
            GouwucheActivity.this.finish();
        }

    }

    //判断是否有网络连接
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }else{
                 Toast.makeText(GouwucheActivity.this, "网络中断，请重新上传订单！",Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }
    //判断WIFI网络是否可用
    public boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }else{
                Toast.makeText(GouwucheActivity.this, "网络中断，请重新上传订单！",Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }




    @Override
    protected void onResume() {
        super.onResume();
        if (act_hzdw.getText().toString().equals("")){

        }else {
            sousuozhanshixinxi();
        }

    }
    private int sousuozhanshixinxi(){

        hezuodanweiname=act_hzdw.getText().toString();
        sum = 0;
        OkGo.post(Api.mohu_gongsi)
                .tag(this)
                .params("name",act_hzdw.getText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        Chuangjian2_hzdwBean cj2 = gson.fromJson(s,Chuangjian2_hzdwBean.class);
                        //      Log.e("7894",cj2.getPerson().get(0).getName());
//                        Log.e("测试",s);
                        if(cj2.getState().equals("1")){
                            danweiid = cj2.getPerson().get(0).getID()+"";
                        }
                    }
                });
        db = Helptools.getDb(GouwucheActivity.this);
        Cursor cs = db.query("goods_moshi1", null, "hezuodanwei="+"'"+act_hzdw.getText().toString()+"'", null, null, null, null, null);
        arrayList.clear();
        if(cs.getCount()==0){
            ll_zonge.setVisibility(View.GONE);
            String[] empty = new String[0];
            ArrayAdapter<String> emptyadapter = new ArrayAdapter<String>(GouwucheActivity.this, R.layout.spinner_item2, empty);
            gouwuche_list.setAdapter(emptyadapter);
            Toast.makeText(GouwucheActivity.this,"暂无此单位的查询数据！",Toast.LENGTH_SHORT).show();
        }else {
            ll_zonge.setVisibility(View.VISIBLE);
            while (cs.moveToNext()){
                Gouwuche gwc = new Gouwuche();
                gwc.setDanwei(cs.getString(cs.getColumnIndex("danwei"))+"");
                gwc.setGuige(cs.getString(cs.getColumnIndex("guige"))+"");
                gwc.setJiage(cs.getString(cs.getColumnIndex("price"))+"");
                gwc.setName(cs.getString(cs.getColumnIndex("name"))+"");
                gwc.setShuliang(cs.getString(cs.getColumnIndex("num"))+"");
                gwc.setId(cs.getString(cs.getColumnIndex("id"))+"");
                gwc.setGuigeid(cs.getString(cs.getColumnIndex("guigeid"))+"");
                gwc.setBeizhu(cs.getString(cs.getColumnIndex("beizhu"))+"");
                //      Log.e("更新后",cs.getString(cs.getColumnIndex("num")));
                arrayList.add(gwc);
            }
            // Log.e("arrayList",arrayList.size()+"");
            gwc_adapter = new GouwucheAdapter(GouwucheActivity.this,arrayList);
            gouwuche_list.setAdapter(gwc_adapter);
            db.close();
            //计算总额
            for (int i = 0; i <arrayList.size() ; i++) {
                Gouwuche gw = arrayList.get(i);

                if (TextUtils.isEmpty(gw.getShuliang())){
                    shu = 0;
                }else {
                    shu = Float.parseFloat(gw.getShuliang());
                }
                if (TextUtils.isEmpty(gw.getJiage())){
                    jiage = 0;
                }else {
                    jiage = Float.parseFloat(gw.getJiage());
                }

                sum = (shu * jiage) + sum;
            }
            float  b   =  (float)(Math.round(sum*100))/100;
//                Log.e("有值吗",b+"");
            tv_zonge.setText(b+"元");

        }
        return arrayList.size();
    }
    private int sousuozhanshixinxi2(){

        hezuodanweiname=act_hzdw.getText().toString();
        sum = 0;
        OkGo.post(Api.mohu_gongsi)
                .tag(this)
                .params("name",act_hzdw.getText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        Chuangjian2_hzdwBean cj2 = gson.fromJson(s,Chuangjian2_hzdwBean.class);
                        //      Log.e("7894",cj2.getPerson().get(0).getName());
                        Log.e("测试",s);
                        if(cj2.getState().equals("1")){
                            danweiid = cj2.getPerson().get(0).getID()+"";
                        }
                    }
                });
        db = Helptools.getDb(GouwucheActivity.this);
        Cursor cs = db.query("goods_moshi1", null, "hezuodanwei="+"'"+act_hzdw.getText().toString()+"'", null, null, null, null, null);
        arrayList.clear();
        if(cs.getCount()==0){
//            ll_zonge.setVisibility(View.GONE);
//            String[] empty = new String[0];
//            ArrayAdapter<String> emptyadapter = new ArrayAdapter<String>(GouwucheActivity.this, R.layout.spinner_item2, empty);
//            gouwuche_list.setAdapter(emptyadapter);
//            Toast.makeText(GouwucheActivity.this,"暂无此单位的查询数据！",Toast.LENGTH_SHORT).show();
        }else {
            ll_zonge.setVisibility(View.VISIBLE);
            while (cs.moveToNext()){
                Gouwuche gwc = new Gouwuche();
                gwc.setDanwei(cs.getString(cs.getColumnIndex("danwei"))+"");
                gwc.setGuige(cs.getString(cs.getColumnIndex("guige"))+"");
                gwc.setJiage(cs.getString(cs.getColumnIndex("price"))+"");
                gwc.setName(cs.getString(cs.getColumnIndex("name"))+"");
                gwc.setShuliang(cs.getString(cs.getColumnIndex("num"))+"");
                gwc.setId(cs.getString(cs.getColumnIndex("id"))+"");
                gwc.setGuigeid(cs.getString(cs.getColumnIndex("guigeid"))+"");
                gwc.setBeizhu(cs.getString(cs.getColumnIndex("beizhu"))+"");
                //      Log.e("更新后",cs.getString(cs.getColumnIndex("num")));
                arrayList.add(gwc);
            }
            // Log.e("arrayList",arrayList.size()+"");
            gwc_adapter = new GouwucheAdapter(GouwucheActivity.this,arrayList);
            gouwuche_list.setAdapter(gwc_adapter);
            db.close();
            //计算总额
            for (int i = 0; i <arrayList.size() ; i++) {
                Gouwuche gw = arrayList.get(i);

                if (TextUtils.isEmpty(gw.getShuliang())){
                    shu = 0;
                }else {
                    shu = Float.parseFloat(gw.getShuliang());
                }
                if (TextUtils.isEmpty(gw.getJiage())){
                    jiage = 0;
                }else {
                    jiage = Float.parseFloat(gw.getJiage());
                }

                sum = (shu * jiage) + sum;
            }
            float  b   =  (float)(Math.round(sum*100))/100;
//                Log.e("有值吗",b+"");
            tv_zonge.setText(b+"元");

        }
        return arrayList.size();
    }
}
