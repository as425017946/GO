package go.app.mdb.android.go.dingdan;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import go.app.mdb.android.go.LogoActivity;
import go.app.mdb.android.go.Pd_searchActivity;
import go.app.mdb.android.go.R;
import go.app.mdb.android.go.bean.Chuangjian2_hzdwBean;
import go.app.mdb.android.go.bean.ChuanjianBean;
import go.app.mdb.android.go.bean.Chuanjian_list_Bean;
import go.app.mdb.android.go.bean.Guige;
import go.app.mdb.android.go.bean.Hezuodanwei;
import go.app.mdb.android.go.bean.ThirdFragmentBean;
import go.app.mdb.android.go.bean.TiaoxingmaBean;
import go.app.mdb.android.go.bean.TiaoxuanMoreBean;
import go.app.mdb.android.go.bean.Unit;
import go.app.mdb.android.go.bean.UnitBean;
import go.app.mdb.android.go.lixian.Lixianinfo_searchActivity;
import go.app.mdb.android.go.tools.Api;
import go.app.mdb.android.go.tools.Helptools;
import okhttp3.Call;
import okhttp3.Response;

import static go.app.mdb.android.go.LogoActivity.logo_id;
import static go.app.mdb.android.go.LogoActivity.logo_name;

public class ChuanjianActivity extends AppCompatActivity {

    @BindView(R.id.chuangjian_ll_1)
    LinearLayout lin;
    @BindView(R.id.add_1)
    ImageView imageView_1;

    @BindView(R.id.ll_context)
    LinearLayout ll_context;

    @BindView(R.id.tv_ddhao)
    TextView tv_ddhao;


    String hezuodanwei,danweiid="a";
    //用于qufen控件展示数据
    int b = 1,c ,f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuanjian);
        ButterKnife.bind(this);
        bangdingshuju();
        fanhui();
        adddingdan();


        //先添加一个视图
        generateSingleLayout();
        //调用动态生成订单号
        tv_ddhao.setText(getRandomValue());
        //点击+号创建新的商品视图
        imageView_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                generateSingleLayout();
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
     * 给动态绑定下拉框架数据使用
     */
    ArrayAdapter<String> adapter1,adapter_guige;
    @BindView(R.id.sp_hezuodanwei)
    AutoCompleteTextView sp_hezuodanwei;
    String[] danwei,danwei_id;
    private void bangdingshuju(){
        //先获取单位名称
        OkGo.post(Api.hezuodanwei)
                .tag(this)
                .params("laSalesmanID",logo_id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                      //  Log.e("合作单位",s);
                        Gson gson =new Gson();
                        Hezuodanwei hzdw = gson.fromJson(s,Hezuodanwei.class);
                        if (hzdw.getState().equals("1")){
                            danwei = new String[hzdw.getPerson().size()];
                            danwei_id = new  String[hzdw.getPerson().size()];
                            list_1 = new ArrayList<>();
                            for (int j = 0; j < hzdw.getPerson().size() ; j++) {
                                danwei[j] = hzdw.getPerson().get(j).getName();
                                danwei_id[j] = hzdw.getPerson().get(j).getID()+"";
                                list_1.add(hzdw.getPerson().get(j).getName());
                               // Log.e("合作单位",danwei[j]);
                            }
                            add_hezuodanwei();
                        }else {
                            Handler handler =new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                   ChuanjianActivity.this.finish();
                                }
                            }, 1000);
                            Toast.makeText(ChuanjianActivity.this,"合作单位网络获取信息失败，请稍后重试！",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    //获取网络数据添加到合作单位控件
    private ArrayList<String> list_1;
    private ArrayAdapter<String> adapter_1;
    private void add_hezuodanwei(){

        sp_hezuodanwei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp_hezuodanwei.showDropDown();//点击控件显示所有的选项
            }
        });
        adapter_1 = new ArrayAdapter<String>(ChuanjianActivity.this, R.layout.item_ac, R.id.tv_1, list_1);
        sp_hezuodanwei.setAdapter(adapter_1);

        sp_hezuodanwei.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    // 此处为得到焦点时的处理内容
                }else {
                    // 此处为失去焦点时的处理内容
                    if (sp_hezuodanwei.getText().toString().equals("")){
                        Toast.makeText(ChuanjianActivity.this, "请先填写合作单位！", Toast.LENGTH_LONG).show();
                    }else {
                      //  Log.e("i的值",""+danwei.length);
                        for (int i = 0; i < danwei.length ; i++) {
                            if(sp_hezuodanwei.getText().toString().equals(danwei[i])){
                                danweiid = danwei_id[i];
                                break;
                            }
                        }
                        if (danweiid.equals("a")){
                            sp_hezuodanwei.setText("");
                            Toast.makeText(ChuanjianActivity.this,"输入单位名称的名称不存在，请联系管理员填入后重试！",Toast.LENGTH_LONG).show();

                        }else{

                        }
//                      Log.e("danweiid",danweiid);

                    }
                }
            }
        });

    }
    //获取商品名
    String[] goods_name,goods_id;
    ArrayAdapter<String> adapter_name;
    ArrayList<String> list_name;
    int state;
    private void add_goods(final AutoCompleteTextView ac_name,final EditText shuliang){
        //ac_name = view.findViewById(R.id.ac_sahngpin_name);
        OkGo.post(Api.mohu_gongsi)
                .tag(this)
                .params("name",sp_hezuodanwei.getText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        Chuangjian2_hzdwBean cj2 = gson.fromJson(s,Chuangjian2_hzdwBean.class);
                        state = cj2.getPerson().get(0).getStepCode();
                        OkGo.post(Api.kucun)
                                .tag(this)
                                .params("state",state)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
                                        Gson gson = new Gson();
                                        ThirdFragmentBean tf_bean = gson.fromJson(s,ThirdFragmentBean.class);
                                        String state =tf_bean.getState();
                                        //Log.e("合作单位",s);
                                        if (state.equals("1")){
                                            goods_name = new String[tf_bean.getPerson().size()];
                                            goods_id = new String[tf_bean.getPerson().size()];
                                            list_name = new ArrayList<>();
                                            ac_name.setSelection(ac_name.getText().length());
                                            for (int j = 0; j <tf_bean.getPerson().size() ; j++) {
                                                list_name.add(tf_bean.getPerson().get(j).getName());
                                                goods_name[j] = tf_bean.getPerson().get(j).getName();
                                                goods_id[j] = tf_bean.getPerson().get(j).getID()+"";
                                            }
                                            adapter_name = new ArrayAdapter<String>(ChuanjianActivity.this, R.layout.item_ac, R.id.tv_1, list_name);
                                            ac_name.setAdapter(adapter_name);
                                            ac_name.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    ac_name.showDropDown();//点击控件显示所有的选项
                                                    ac_name.setSelection(ac_name.getText().length());
                                                    Log.e("a","a");
                                                }
                                            });

                                            ac_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                                    Log.e("b","b");
                                                    if (!sp_hezuodanwei.getText().toString().equals("")){

                                                        for (int j = 0; j < goods_name.length  ; j++) {
                                                            if (ac_name.getText().toString().equals(goods_name[j])){
                                                                good_id_chaxun = goods_id[j];
                                                                break;
                                                            }
                                                        }
                                                        //     Log.e("商品名","商品名");
                                                        tishi();
                                                    }else {

                                                        //Toast.makeText(ChuanjianActivity.this, "请输入商品！", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });

                                            ac_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                                @Override
                                                public void onFocusChange(View view, boolean b) {
                                                    ac_name.setSelection(ac_name.getText().length());
                                                    if(b){

                                                    }else{
                                                        if (!ac_name.getText().toString().equals("")){
                                                            ac_name.setSelection(ac_name.getText().length());
                                                            for (int i = 0; i < goods_name.length  ; i++) {
                                                                if (ac_name.getText().toString().equals(goods_name[i])){
                                                                    good_id_chaxun = goods_id[i];
                                                                    break;
                                                                }
                                                            }
                                                            if (good_id_chaxun.equals("a")) {
                                                                ac_name.setText("");
                                                                Toast.makeText(ChuanjianActivity.this, "输入商品名不存在，请联系管理员填入后重试！", Toast.LENGTH_LONG).show();
                                                            }

                                                        }
                                                    }
                                                }
                                            });

                                        }else {

                                        }
                                    }
                                });
                    }
                });

    }

    /**
     * 规格
     * @param view
     */
    String[] guige,guige_id;
    String wodeguige,wodedanwei;
    private void add_guige(final Spinner sp_guige){
//        sp_guige = view.findViewById(R.id.sp_guige);
        OkGo.post(Api.danwei)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson =new Gson();
                        UnitBean ub = gson.fromJson(s,UnitBean.class);
                        if (ub.getState().equals("1")){
                            guige = new String[ub.getPerson().size()];
                            guige_id = new String[ub.getPerson().size()];
                            for (int j = 0; j <ub.getPerson().size() ; j++) {
                                guige[j] = ub.getPerson().get(j).getName();
                                guige_id[j] = ub.getPerson().get(j).getID()+"";
                               // Log.e("规格",guige[j]);
                            }
                            add_guige_info(sp_guige);
                        }else{

                        }
                    }
                });
    }
    //规格添加到控件
    String guigeid;
    private void add_guige_info(final Spinner sp_guige){
        adapter_guige = new ArrayAdapter<String>(ChuanjianActivity.this,R.layout.spinner_item,guige);
        adapter_guige.setDropDownViewResource(R.layout.spinner_zidingyi);
        sp_guige.setAdapter(adapter_guige);
        sp_guige.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

              //  guigeid = guige_id[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    //创建动态添加的商品控件
    @BindView(R.id.delete_1)
    ImageView img_delete;
    private void generateSingleLayout()
    {
        //第一次添加视图qufen控件让其隐藏，其余时候展示
        final View v_item=LayoutInflater.from(ChuanjianActivity.this).inflate(R.layout.s_item,null);
        Spinner sp_guige = v_item.findViewById(R.id.sp_guige);
        final AutoCompleteTextView ac_name = v_item.findViewById(R.id.ac_sahngpin_name);
        final EditText shuliang = v_item.findViewById(R.id.ed_num);
        ll_context.addView(v_item);
        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ll_context.removeViewAt(b-2);
                b--;
                Log.e("bbb",""+b);
                if (b==2){
                    img_delete.setVisibility(View.GONE);
                }
            }
        });
        //把视图中的条形码值添加到条形码控件中
        tiaoxingma(v_item);
        //添加网络商品信息
        add_goods(ac_name,shuliang);
        //添加网络规格信息
        add_guige(sp_guige);
        //查找到对应的下拉框控件，然后对其绑定数据
       // Log.e("触发几次",""+i);
        TextView tv_qufen = (TextView)v_item.findViewById(R.id.tv_qufen);
        if (b==1){
            tv_qufen.setVisibility(View.GONE);
            img_delete.setVisibility(View.GONE);
        }else{
            img_delete.setVisibility(View.VISIBLE);
            tv_qufen.setText("这是第"+b+"个要添加的商品信息");
            tv_qufen.setVisibility(View.VISIBLE);
        }

        b++;

//        EditText ed_num = (EditText)v_item.findViewById(R.id.ed_num);
//        ed_num.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//
//                return false;
//            }
//        });
    }

    //
    private void tishi(){
        OkGo.post(Api.cha_txm)
                .tag(this)
                .params("id",good_id_chaxun)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        final TiaoxingmaBean txm = gson.fromJson(s,TiaoxingmaBean.class);
                        if (txm.getState().equals("1")){
                            OkGo.post(Api.txm_xiangsi)
                                    .tag(this)
                                    .params("id",danweiid)
                                    .params("barCode",txm.getPerson().get(0).getBarCode())
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
//                                Log.e("id",danweiid);
                               // Log.e("barCode",txm.getPerson().get(0).getBarCode());
                             //   Log.e("我的返回值s",s);
                                            Gson gson = new Gson();
                                            Chuanjian_list_Bean cj_list_bean = gson.fromJson(s,Chuanjian_list_Bean.class);
                                            if (cj_list_bean.getState().equals("1")){

                                                final AlertDialog.Builder builder = new AlertDialog.Builder(ChuanjianActivity.this);
                                                builder.setTitle("选择一个商品");
                                                //    指定下拉列表的显示数据
                                                final String[] goods,goodsname,goodsid,goodsdanjia;
                                                goods = new String[cj_list_bean.getPerson().size()];
                                                goodsname = new String[cj_list_bean.getPerson().size()];
                                                goodsid = new String[cj_list_bean.getPerson().size()];
                                                goodsdanjia = new String[cj_list_bean.getPerson().size()];
                                                for (int i = 0; i < goods.length ; i++) {
                                                    goods[i] = cj_list_bean.getPerson().get(i).getName()+"    "+cj_list_bean.getPerson().get(i).getStepPrice()+"元";
                                                    goodsname[i] = cj_list_bean.getPerson().get(i).getName();
                                                    goodsdanjia[i] = cj_list_bean.getPerson().get(i).getStepPrice()+"";
                                                    goodsid[i] = cj_list_bean.getPerson().get(i).getID()+"";
                                                }
                                                //    设置一个下拉的列表选择项
                                                builder.setItems(goods, new DialogInterface.OnClickListener()
                                                {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which)
                                                    {
                                                        good_id_chaxun = goodsid[which];
                                                        ac_name.setText(goodsname[which]);
                                                        ed_danjia .setText(goodsdanjia[which]);
                                                        //Toast.makeText(ChuanjianActivity.this, "选择的城市为：" + goods[which], Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                                builder.show();
                                            }else {
                                                Toast.makeText(ChuanjianActivity.this, "选择的商家暂无商品信息，请联系管理员！", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }else {
                            Toast.makeText(ChuanjianActivity.this,"查询价格失败，请稍后重试！",Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }

    //点击创建订单按钮
    SQLiteDatabase db;
    @BindView(R.id.chuanjian_tijiaodingdan)
    Button btn_dingdan;
    String tiaoxingma="",good_id_chaxun="a";
    ArrayList<Goods> goods_list = new ArrayList<>();
    private void adddingdan(){

        btn_dingdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < danwei.length ; i++) {
                    if(sp_hezuodanwei.getText().toString().equals(danwei[i])){
                        danweiid = danwei_id[i];
                        break;
                    }
                }


                for ( c = 0; c < ll_context.getChildCount(); c++) {
                        view=ll_context.getChildAt(c);

                    EditText ed_num = (EditText)view.findViewById(R.id.ed_num);
                    String num1= ed_num.getText().toString();
                    EditText ed_danjia = (EditText)view.findViewById(R.id.ed_price);
                    String danjia1 = ed_danjia.getText().toString();
                    EditText ed_zhujima = (EditText)view.findViewById(R.id.ed_zhujima);
                    String zhujima = ed_zhujima.getText().toString();
                    EditText ed_beizhu = (EditText)view.findViewById(R.id.ed_beizhu);
                    String beizhu = ed_beizhu.getText().toString();
                    AutoCompleteTextView ac_name = (AutoCompleteTextView)view.findViewById(R.id.ac_sahngpin_name);



                    //Log.e("第几次？",c+"    规格："+guige1+"     分类："+fenlei1+"     品牌："+pinpai1+"    名称："+name+"    数量："+num1+"    单价："+danjia1);
                  //  Log.e("ac_name.getText()",ac_name.getText().toString());
                    if (sp_hezuodanwei.getText().toString().equals("")){
                        Toast.makeText(ChuanjianActivity.this,"请选择合作单位",Toast.LENGTH_SHORT).show();
                        break;
                    }else{
                        if (ac_name.getText().toString().equals("")){
                            Toast.makeText(ChuanjianActivity.this,"请输入商品名",Toast.LENGTH_SHORT).show();
                            break;
                        }else{
                            if (num1.equals("")){
                                Toast.makeText(ChuanjianActivity.this,"请输入商品数量",Toast.LENGTH_SHORT).show();
                                break;
                            }else{
                                if (danjia1.equals("")){
                                    Toast.makeText(ChuanjianActivity.this,"请输入商品单价",Toast.LENGTH_SHORT).show();
                                    break;
                                }else{
                                   Log.e("good_id_chaxun",good_id_chaxun);
                                    Goods gd =new Goods();
                                    gd.setName(ac_name.getText().toString());
                                    gd.setName_id(good_id_chaxun);
                                    gd.setNum(num1);
                                    gd.setDanjia(danjia1);
                                    gd.setBeizhu(beizhu);
                                   // gd.setGuige(guigeid);
                                    goods_list.add(gd);
                                }
                            }
                        }
                    }

                }

                    for (f = 0; f <c ; f++) {
                       final Goods gds = goods_list.get(f);
                        OkGo.post(Api.mohu_gongsi)
                                .tag(this)
                                .params("name",sp_hezuodanwei.getText().toString())
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
                                        Gson gson = new Gson();
                                        Chuangjian2_hzdwBean cj2 = gson.fromJson(s,Chuangjian2_hzdwBean.class);
                                        Log.e("啦啦",cj2.getPerson().get(0).getStepCode()+"/////"+gds.getName_id());
                                        OkGo.post(Api.weiyi_goods)
                                                .tag(this)
                                                .params("state",cj2.getPerson().get(0).getStepCode())
                                                .params("id",gds.getName_id())
                                                .execute(new StringCallback() {
                                                    @Override
                                                    public void onSuccess(String s, Call call, Response response) {
                                                        Gson gson = new Gson();
                                                        TiaoxuanMoreBean tx_bean = gson.fromJson(s,TiaoxuanMoreBean.class);
                                                        wodeguige = tx_bean.getPerson().get(0).getSpecification();
                                                        wodedanwei = tx_bean.getPerson().get(0).getUnit();
                                                        OkGo.post(Api.danwei)
                                                                .tag(this)
                                                                .execute(new StringCallback() {
                                                                    @Override
                                                                    public void onSuccess(String s, Call call, Response response) {
                                                                        Gson gson1 =new Gson();
                                                                        Unit unit = gson1.fromJson(s,Unit.class);
                                                                        for (int i = 0; i <unit.getPerson().size() ; i++) {
                                                                            if (wodedanwei.equals(unit.getPerson().get(i).getName())){
                                                                                guigeid = unit.getPerson().get(i).getID()+"";
                                                                                Log.e("默默",wodeguige+"//////"+wodeguige);
                                                                                db  = Helptools.wriDb(ChuanjianActivity.this);
                                                                                ContentValues cv = new ContentValues();
                                                                                cv.put("name", "" + gds.getName());
                                                                                cv.put("id",gds.getName_id());
                                                                                cv.put("guige", wodeguige);
                                                                                cv.put("danwei",wodedanwei );
                                                                                cv.put("guigeid", guigeid);
                                                                                cv.put("num", "" + gds.getNum());
                                                                                cv.put("price", "" + gds.getDanjia());
                                                                                cv.put("beizhu", "" + gds.getBeizhu());
                                                                                cv.put("hezuodanwei", "" + sp_hezuodanwei.getText().toString());
                                                                                db.insert("goods_moshi1", null, cv);
                                                                                db.close();
                                                                            }
                                                                        }
                                                                    }
                                                                });




                                                    }
                                                });

                                    }
                                });

                    }

                if (f==c){
                    Toast.makeText(ChuanjianActivity.this,"创建成功",Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            ChuanjianActivity.this.finish();

                        }
                    }, 1000);
                }


//                if(c==ll_context.getChildCount()) {
//                    OkGo.post(Api.dd_chuangjian)
//                            .tag(this)
//                            .params("parterID",danweiid)
//                            .params("parterName",hezuodanwei)
//                            .params("salesmanName",logo_name)
//                            .params("isUpdate","1")
//                            .params("billCode",tv_ddhao.getText().toString())
//                            .execute(new StringCallback() {
//                                @Override
//                                public void onSuccess(String s, Call call, Response response) {
//                                        Log.e("添加订单",s);
//                                        Gson gson = new Gson();
//                                        ChuanjianBean cjbean = gson.fromJson(s,ChuanjianBean.class);
//                                        if (cjbean.getState().equals("1")){
//        //mainID订单id，mainBillCode订单编号，inventoryID商品id，inventoryName商品名称，quantity数量，unit单位，salePrice销售单价，remark备注
//                                            for (f = 0; f <c ; f++) {
//                                                Goods gds = goods_list.get(f);
//                                                OkGo.post(Api.dd_xiangqing)
//                                                        .tag(this)
//                                                        .params("mainID",cjbean.getPerson().get(0).getLastId()+"")
//                                                        .params("mainBillCode",tv_ddhao.getText().toString())
//                                                        .params("inventoryID",gds.getName_id())
//                                                        .params("inventoryName",gds.getName())
//                                                        .params("quantity",gds.getNum())
//                                                        .params("unitID",gds.getGuige())
//                                                        .params("salePrice",gds.getDanjia())
//                                                        .params("remark",gds.getBeizhu())
//                                                        .execute(new StringCallback() {
//                                                            @Override
//                                                            public void onSuccess(String s, Call call, Response response) {
//                                                                Log.e("销售详情",s);
//                                                            }
//                                                        });
//                                            }
//
//
//                                            if (f==c){
//                                                Toast.makeText(ChuanjianActivity.this,"创建成功",Toast.LENGTH_SHORT).show();
//                                                Handler handler = new Handler();
//                                                handler.postDelayed(new Runnable() {
//                                                    @Override
//                                                    public void run() {
//
//                                                        ChuanjianActivity.this.finish();
//
//                                                    }
//                                                }, 1000);
//                                            }
//
//                                        }
//
//
//                                }
//                            });
//
//                }

                //Toast.makeText(ChuanjianActivity.this,"暂时无法提交到服务器",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //点击获取条形码
    TextView tv_tiaoxingma;
    AutoCompleteTextView ac_name;
    EditText ed_danjia;
    private void tiaoxingma(View view){
        tv_tiaoxingma = (TextView) view.findViewById(R.id.tv_tiaoxingma);
        //商品名和单价
        ac_name = (AutoCompleteTextView)view.findViewById(R.id.ac_sahngpin_name);
        ed_danjia = (EditText) view.findViewById(R.id.ed_price);
        tv_tiaoxingma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                            if (sp_hezuodanwei.getText().toString().equals("")){
                                Toast.makeText(ChuanjianActivity.this, "扫码前请选择合作单位！", Toast.LENGTH_LONG).show();
                            }else {
                                for (int i = 0; i < danwei.length ; i++) {
                                    if(sp_hezuodanwei.getText().toString().equals(danwei[i])){
                                        danweiid = danwei_id[i];
                                        break;
                                    }
                                    if (i==danwei.length){
                                        if (danweiid.equals("")){
                                            sp_hezuodanwei.setText("");
                                            Toast.makeText(ChuanjianActivity.this,"输入合作单位不存在，请联系管理员填入后重试！",Toast.LENGTH_LONG).show();

                                        }
                                    }
                                }
                                Log.e("danweiid",danweiid);
                                IntentIntegrator integrator = new IntentIntegrator(ChuanjianActivity.this);
                                // 设置要扫描的条码类型，ONE_D_CODE_TYPES：一维码，QR_CODE_TYPES-二维码
                                integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                                integrator.setPrompt("扫描条形码");//底部的提示文字，设为""可以置空
                                integrator.setCameraId(0);//前置或者后置摄像头
                                integrator.setBeepEnabled(false);//扫描成功的「哔哔」声，默认开启
                                integrator.initiateScan();
                            }

            }
        });
        //Log.e("条形码：",""+tiaoxingma);

    }
    //扫描条形码后得到的值
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "扫码取消！", Toast.LENGTH_LONG).show();
            } else {
                tiaoxingma = result.getContents();
                //Toast.makeText(this, "扫描成功，条码值: " + result.getContents(), Toast.LENGTH_LONG).show();
                //进行网络接口的写入查询
                tv_tiaoxingma.setText(tiaoxingma);
                OkGo.post(Api.txm_xiangsi)
                        .tag(this)
                        .params("id",danweiid)
                        .params("barCode",tiaoxingma)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
//                                Log.e("id",danweiid);
//                                Log.e("barCode",tiaoxingma);
//                                Log.e("s",s);
                                Gson gson = new Gson();
                                Chuanjian_list_Bean cj_list_bean = gson.fromJson(s,Chuanjian_list_Bean.class);
                                if (cj_list_bean.getState().equals("1")){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ChuanjianActivity.this);
                                    builder.setCancelable(false);
                                    builder.setTitle("选择一个商品");
                                    //    指定下拉列表的显示数据
                                    final String[] goods,goodsname,goodsid,goodsdanjia;
                                    goods = new String[cj_list_bean.getPerson().size()];
                                    goodsname = new String[cj_list_bean.getPerson().size()];
                                    goodsid = new String[cj_list_bean.getPerson().size()];
                                    goodsdanjia = new String[cj_list_bean.getPerson().size()];
                                    for (int i = 0; i < goods.length ; i++) {
                                        goods[i] = cj_list_bean.getPerson().get(i).getName()+"    "+cj_list_bean.getPerson().get(i).getStepPrice()+"元";
                                        goodsname[i] = cj_list_bean.getPerson().get(i).getName();
                                        goodsdanjia[i] = cj_list_bean.getPerson().get(i).getStepPrice()+"";
                                        goodsid[i] = cj_list_bean.getPerson().get(i).getID()+"";
                                    }
                                    //    设置一个下拉的列表选择项
                                    builder.setItems(goods, new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which)
                                        {
                                            good_id_chaxun = goodsid[which];
                                            ac_name.setText(goodsname[which]);
                                            ed_danjia.setText(goodsdanjia[which]);
                                            //Toast.makeText(ChuanjianActivity.this, "选择的城市为：" + goods[which], Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    builder.show();
                                }else {
                                    tv_tiaoxingma.setText("");
                                    Toast.makeText(ChuanjianActivity.this, "扫描的商品库中不存在，请联系管理员添加后重试！", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    //返回按钮和修改标题
    @BindView(R.id.fenye_fanhui)
    LinearLayout ll_fanhui;
    @BindView(R.id.fenye_title)
    TextView tv_title;
    private void fanhui(){
        //修改标题
        tv_title.setText("创建订单");
        //点击返回按钮
        ll_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    ChuanjianActivity.this.finish();
            }
        });
    }
}
