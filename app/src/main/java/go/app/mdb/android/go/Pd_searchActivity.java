package go.app.mdb.android.go;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import go.app.mdb.android.go.bean.FirstFragmentBean;
import go.app.mdb.android.go.pandian.MyAdapter;
import go.app.mdb.android.go.pandian.PanDian;
import go.app.mdb.android.go.tools.Api;
import go.app.mdb.android.go.tools.PermissionsChecker;
import okhttp3.Call;
import okhttp3.Response;


public class Pd_searchActivity extends AppCompatActivity {

    /***
     * 实例化对象
     */
//    @BindView(R.id.fenye_fanhui)
//    LinearLayout linearLayout_fanhui;
//    @BindView(R.id.search_ll1)
//    LinearLayout ll_1;
//    @BindView(R.id.search_ll2)
//    LinearLayout ll_2;
    MyAdapter myAdapter;
    //listview视图
    @BindView(R.id.pd_listview2)
    ListView list;
    private ArrayList<PanDian> listenety=new ArrayList<>();//只会new一次  其他地方都不会new
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_pd_search);
        setContentView(R.layout.activity_pd_search);
        ButterKnife.bind(this);


//        setListener();
//        addxinxi();
//        sousuo();
        saomiao();
        Bundle bundle =getIntent().getExtras();
        if(bundle.getString("id").equals("1")){

            IntentIntegrator integrator = new  IntentIntegrator(Pd_searchActivity.this);
            // 设置要扫描的条码类型，ONE_D_CODE_TYPES：一维码，QR_CODE_TYPES-二维码
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
            integrator.setPrompt("扫描条形码");//底部的提示文字，设为""可以置空
            integrator.setCameraId(0);//前置或者后置摄像头
            integrator.setBeepEnabled(false);//扫描成功的「哔哔」声，默认开启
            integrator.initiateScan();
        }else {
            //进行网络接口查询
            Toast.makeText(this, "后续进行网络接口查询，输出结果", Toast.LENGTH_LONG).show();
        }

    }

   

    @OnClick({R.id.fenye_fanhui})
    void click(View v){
        int id = v.getId();
        switch (id){
            case R.id.fenye_fanhui:
                Pd_searchActivity.this.finish();
                break;
        }

    }
    /***
     * 扫描功能
     */
    @BindView(R.id.saomiao)
    Button btn_saomiao;
    private void saomiao() {
        btn_saomiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new  IntentIntegrator(Pd_searchActivity.this);
                // 设置要扫描的条码类型，ONE_D_CODE_TYPES：一维码，QR_CODE_TYPES-二维码
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                integrator.setPrompt("扫描条形码");//底部的提示文字，设为""可以置空
                integrator.setCameraId(0);//前置或者后置摄像头
                integrator.setBeepEnabled(false);//扫描成功的「哔哔」声，默认开启
                integrator.initiateScan();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "扫码取消！", Toast.LENGTH_LONG).show();
                Pd_searchActivity.this.finish();
            } else {
               // Toast.makeText(this, "扫描成功，条码值: " + result.getContents(), Toast.LENGTH_LONG).show();
                //进行网络接口的写入查询
                OkGo.post(Api.pd_chaxun)
                        .tag(this)
                        .params("barCode",result.getContents())
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                listenety.clear();
                                Gson gson = new Gson();
                                FirstFragmentBean ffb = gson.fromJson(s,FirstFragmentBean.class);
                                String state = ffb.getState();
                                if(state.equals("1")){
                                    for (int i = 0; i < ffb.getPerson().size(); i++) {
                                        PanDian panDian=new PanDian();
                                        panDian.setName(ffb.getPerson().get(i).getName());
                                        panDian.setFenlei(ffb.getPerson().get(i).getClassID()+"");
                                        panDian.setPinpai(ffb.getPerson().get(i).getBrand());
                                        panDian.setGuige(ffb.getPerson().get(i).getSpecification());
                                        panDian.setKucun(ffb.getPerson().get(i).getStockQuantity()+"");
                                        panDian.setZhujima(ffb.getPerson().get(i).getShortCode());
                                        panDian.setTiaoxingma(ffb.getPerson().get(i).getBarCode());
                                        panDian.setId(ffb.getPerson().get(i).getID()+"");
                                        listenety.add(panDian);
                                    }
                                    myAdapter = new MyAdapter(Pd_searchActivity.this,listenety,onClickListener);
                                    list.setAdapter(myAdapter);
                                }else{
                                    listenety.clear();
                                    String[] empty = new String[]{""};
                                    ArrayAdapter<String> emptyadapter = new ArrayAdapter<String>(Pd_searchActivity.this, R.layout.spinner_item2, empty);
                                    list.setAdapter(emptyadapter);
                                    Toast.makeText(Pd_searchActivity.this,"暂无查询的条形码数据",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    /**
     * 修改按钮点击事件
     */
    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            Button btn = (Button) view;
            int pos = (Integer) btn.getTag();
            Toast.makeText(Pd_searchActivity.this,"点击的是第"+pos,Toast.LENGTH_SHORT).show();
        }
    };

//    /***
//     * 先给类似combox的自定义控件加入数据信息
//     */
//    @BindView(R.id.ac_1)
//    AutoCompleteTextView act1;
//    @BindView(R.id.ac_2)
//    AutoCompleteTextView act2;
//    @BindView(R.id.ac_3)
//    AutoCompleteTextView act3;
//    @BindView(R.id.ac_4)
//    AutoCompleteTextView act4;
//    private ArrayList<String> list_1,list_2,list_3,list_4,list_5,list_6 ;
//    private ArrayAdapter<String> adapter_1,adapter_2,adapter_3,adapter_4,adapter_5,adapter_6 ;
//
//    private void setListener(){
//        act1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                act1.showDropDown();//点击控件显示所有的选项
//            }
//        });
//        act2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                act2.showDropDown();//点击控件显示所有的选项
//            }
//        });
//        act3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                act3.showDropDown();//点击控件显示所有的选项
//            }
//        });
//        act4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                act4.showDropDown();//点击控件显示所有的选项
//            }
//        });
//    }
//    private void addxinxi(){
//        setData();
//        adapter_1 = new ArrayAdapter<String>(Pd_searchActivity.this, R.layout.item_ac, R.id.tv_1, list_1);
//        act1.setAdapter(adapter_1);
//        adapter_2 = new ArrayAdapter<String>(Pd_searchActivity.this, R.layout.item_ac, R.id.tv_1, list_2);
//        act2.setAdapter(adapter_2);
//        adapter_3 = new ArrayAdapter<String>(Pd_searchActivity.this, R.layout.item_ac, R.id.tv_1, list_3);
//        act3.setAdapter(adapter_3);
//        adapter_4 = new ArrayAdapter<String>(Pd_searchActivity.this,  R.layout.item_ac, R.id.tv_1, list_4);
//        act4.setAdapter(adapter_4);
//    }
//    private void setData() {//设置数据源，这里只用了单纯的文字；
//        list_1 =new ArrayList<>();
//        list_1 .add("白酒");
//        list_1 .add("方便面");
//
//        list_2 =new ArrayList<>();
//        list_2 .add("111155");
//        list_2 .add("222666");
//
//        list_3 =new ArrayList<>();
//        list_3 .add("茅台");
//        list_3 .add("五粮液");
//
//        list_4 =new ArrayList<>();
//        list_4 .add("箱");
//        list_4 .add("瓶");
//
//        list_5 =new ArrayList<>();
//        list_5 .add("酒水");
//        list_5 .add("食品");
//
//        list_6 =new ArrayList<>();
//        list_6 .add("酒水");
//        list_6 .add("食品");
//
//
//    }
//    /****
//     * 点击搜索
//     */
//    @BindView(R.id.search_chaxun)
//    Button btn_chaxun;
//
//    private void sousuo() {
//        btn_chaxun.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (act1.getText().toString().equals("")&&act2.getText().toString().equals("")&&act3.getText().toString().equals("")&&
//                        act4.getText().toString().equals("")){
//                    Toast.makeText(Pd_searchActivity.this,"请输入要查询的数据",Toast.LENGTH_SHORT).show();
//                }else {
//                    //有值 进行网络搜索
//
//                }
//            }
//        });
//    }




}
