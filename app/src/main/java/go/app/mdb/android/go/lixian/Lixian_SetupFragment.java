package go.app.mdb.android.go.lixian;


import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import go.app.mdb.android.go.R;
import go.app.mdb.android.go.dingdan.ChuanjianActivity;
import go.app.mdb.android.go.tools.Helptools;

import static go.app.mdb.android.go.lixian.LixinaMainActivity.lxlogo_name;
import static go.app.mdb.android.go.lixian.LixinaMainActivity.tiaoxingma_zhi;


public class Lixian_SetupFragment extends Fragment {

    Context context;

    @BindView(R.id.chuangjian_ll_1)
    LinearLayout lin;
    @BindView(R.id.add_1)
    ImageView imageView_1;

    @BindView(R.id.ll_context)
    LinearLayout ll_context;

    @BindView(R.id.tv_ddhao)
    TextView tv_ddhao;


    String hezuodanwei,hezuodanwei_id,guige1,fenlei1,pinpai1;
    //用于qufen控件展示数据
    int b = 1,q = 0, w =0 ,c ,m;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.activity_lixian__setup_fragment,container, false);
        ButterKnife.bind(this,view);//在这个方法内  不许在写其他的东西  因为只有返回后你才能拿到当前的视图  你要是在return之前你去写点击 会报错
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getRandomValue();
        //Log.e("合作单位",""+hezuodanwei);

        adddingdan();
        bangdingshuju();
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
    //创建动态添加的商品控件
    @BindView(R.id.delete_1)
    ImageView img_delete;
    private void generateSingleLayout()
    {
        //第一次添加视图qufen控件让其隐藏，其余时候展示
        View v_item=LayoutInflater.from(context).inflate(R.layout.s_item,null);
        ll_context.addView(v_item);
        LinearLayout ll_tiaoxinamg = (LinearLayout)v_item.findViewById(R.id.ll_tiaoxingma);
        ll_tiaoxinamg.setVisibility(View.GONE);
        AutoCompleteTextView tv_name = (AutoCompleteTextView)v_item.findViewById(R.id.ac_sahngpin_name);
        tv_name.setEnabled(true);
        EditText edt_danjia = (EditText)v_item.findViewById(R.id.ed_price);
        edt_danjia.setEnabled(true);

        final AutoCompleteTextView ac_name = (AutoCompleteTextView)v_item.findViewById(R.id.ac_sahngpin_name);
        ac_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ac_name.showDropDown();//点击控件显示所有的选项
            }
        });
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

//        //把视图中的条心甘添加到条形码控件中
//        tiaoxingma(v_item);
        add_goods(ac_name,edt_danjia);
        add_danweo(v_item);
        //查找到对应的下拉框控件，然后对其绑定数据
        // Log.e("触发几次",""+i);
        TextView tv_qufen = (TextView)v_item.findViewById(R.id.tv_qufen);
        if (b==1){
            img_delete.setVisibility(View.GONE);
            tv_qufen.setVisibility(View.GONE);
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
//                if (!ac_name.getText().toString().equals("")){
//
//                    for (int i = 0; i < goods_name.length  ; i++) {
//                        if (ac_name.getText().toString().equals(goods_name[i])){
//                            goodsid = goods_id[i];
//                        }else {
//                            ac_name.setText("");
//                            Toast.makeText(context,"输入商品名不存在，请联系管理员填入后重试！",Toast.LENGTH_LONG).show();
//                        }
//                    }
//                }
//                return false;
//            }
//        });




    }

    //产生10位随机数字用于产生订单使用
    public static String getRandomValue() {
        String str = "";
//        for (int i = 0; i < 10; i++) {
//            char temp = 0;
//
//            temp = (char) (Math.random() * 10 + 48);//产生随机数字
//
//            str = str + temp;
//        }
        SimpleDateFormat formatter    =   new    SimpleDateFormat    ("yyyyMMddHHmmssSSS");
        Date curDate    =   new    Date(System.currentTimeMillis());//获取当前时间
        str = "SO-"+ formatter.format(curDate);
        return str;

    }

    /**
     * 给动态绑定下拉框架数据使用
     */
    ArrayAdapter<String> adapter1;
    int a=0;
    @BindView(R.id.sp_hezuodanwei)
    AutoCompleteTextView sp_hezuodanwei;
    String[] danwei,danwei_id,danwei_StepCode;
    String danweiid="a",danwei_stepcode;
    private void bangdingshuju(){
        a = 0 ;
        SQLiteDatabase db = Helptools.getDb(context);
        Cursor cs = db.query("hzdwinfo",null,null,null,null,null,null);
        int num =cs.getCount();
     //   Log.e("hzdwinfo",""+num);
        if(num==0){

        }else {
            danwei = new String[num];
            danwei_id = new String[num];
            danwei_StepCode = new String[num];
            while (cs.moveToNext()){
              //  Log.e("合作单位名称",cs.getString(cs.getColumnIndex("name")));
                danwei[a] =  cs.getString(cs.getColumnIndex("name"));
                danwei_id[a] = cs.getString(cs.getColumnIndex("id"));
                danwei_StepCode[a] = cs.getString(cs.getColumnIndex("StepCode"));
                list_1.add(cs.getString(cs.getColumnIndex("name")));
                a++;
            }
            add_hezuodanwei();
            db.close();
        }
    }
    //获取离线数据添加到合作单位控件
    private ArrayList<String> list_1 = new ArrayList<>();
    private ArrayAdapter<String> adapter_1;
    private void add_hezuodanwei(){

        sp_hezuodanwei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp_hezuodanwei.showDropDown();//点击控件显示所有的选项
            }
        });
        adapter_1 = new ArrayAdapter<String>(context, R.layout.item_ac, R.id.tv_1, list_1);
        sp_hezuodanwei.setAdapter(adapter_1);

        sp_hezuodanwei.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    // 此处为得到焦点时的处理内容
                }else {
                    // 此处为失去焦点时的处理内容
                    if (sp_hezuodanwei.getText().toString().equals("")){
                        Toast.makeText(context, "请先填写合作单位！", Toast.LENGTH_LONG).show();
                    }else {
                        //  Log.e("i的值",""+danwei.length);
                        for (int i = 0; i < danwei.length ; i++) {
                            if(sp_hezuodanwei.getText().toString().equals(danwei[i])){
                                danweiid = danwei_id[i];
                                danwei_stepcode = danwei_StepCode[i];
                                break;
                            }
                        }
                        if (danweiid.equals("a")){
                            sp_hezuodanwei.setText("");
                            Toast.makeText(context,"输入单位名称的名称不存在，请联系管理员填入后重试！",Toast.LENGTH_LONG).show();

                        }else{

                        }
//                      Log.e("danweiid",danweiid);

                    }
                }
            }
        });

    }
    /**
     * 商品名和id
     */
    String[] goods_name,goods_id,goods_BarCode,goods_danjia;
    String  goodsid;
    ArrayAdapter<String> adapter_acname;
    ArrayList<String> list_acname = new ArrayList<String>();
    private void add_goods(final AutoCompleteTextView ac_name,final EditText ed_danjia){
//        AutoCompleteTextView ac_name = view.findViewById(R.id.ac_sahngpin_name);
        q = 0 ;
       final SQLiteDatabase db = Helptools.getDb(context);
        Cursor cs = db.query("goodinfos",null,null,null,null,null,null);
        int num =cs.getCount();
        if (num==0){

        }else {
            goods_name = new String[num];
            goods_id = new String[num];
            goods_BarCode = new String[num];
            list_acname.clear();
            while (cs.moveToNext()){
                list_acname.add(cs.getString(cs.getColumnIndex("name")));
                goods_name[q] = cs.getString(cs.getColumnIndex("name"));
                goods_id[q] = cs.getString(cs.getColumnIndex("id"));
                goods_BarCode[q] = cs.getString(cs.getColumnIndex("BarCode"));
                q++;
            }
            adapter_acname = new ArrayAdapter<String>(context, R.layout.item_ac, R.id.tv_1, list_acname);
            ac_name.setAdapter(adapter_acname);

            ac_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    if (!sp_hezuodanwei.getText().toString().equals("")){

                        for (int j = 0; j < goods_name.length  ; j++) {
                            if (ac_name.getText().toString().equals(goods_name[j])){
                                goodsid = goods_id[j];
                                break;
                            }
                        }
                        //     Log.e("商品名","商品名");
//                        Cursor cs = db.query("goodinfos",null,null,null,null,null,null);
//                        int num =cs.getCount();
//
                          tishi(ac_name,ed_danjia);

                    }else {

                        //Toast.makeText(ChuanjianActivity.this, "请输入商品！", Toast.LENGTH_LONG).show();
                    }
                }
            });

            ac_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if(b){

                    }else{
                        if (!ac_name.getText().toString().equals("")){

                            for (int i = 0; i < goods_name.length  ; i++) {
                                if (ac_name.getText().toString().equals(goods_name[i])){
                                    goodsid = goods_id[i];
                                    break;
                                }
                            }
                            if (goodsid.equals("a")) {
                                ac_name.setText("");
                                Toast.makeText(context, "输入商品名不存在，请联系管理员填入后重试！", Toast.LENGTH_LONG).show();
                            }

                        }else {
                            Toast.makeText(context, "请输入商品！", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });

            db.close();
        }
    }
    int num1,danjiazhi=0;
    String[] danjia_name,danjia_jiage,danjiazonghe;

    private void tishi(final AutoCompleteTextView ac_name,final EditText ed_danjia) {
        danjiazhi = 0;
        SQLiteDatabase db = Helptools.getDb(context);
        switch (danwei_stepcode){
            case "1":
                Cursor cs = db.query("goodsdanjia",null,"InventoryID="+"'"+goodsid+"'",null,null,null,null);
                num1 =cs.getCount();
                if (num1==0){

                }else {
                    danjia_name = new String[num1];
                    danjia_jiage = new String[num1];
                    danjiazonghe = new String[num1];
                    while (cs.moveToNext()){
                        danjiazonghe[danjiazhi] =  ac_name.getText().toString()+"  "+cs.getString(cs.getColumnIndex("StepPrice1"))+"元";
                        danjia_name[danjiazhi] = ac_name.getText().toString();
                        danjia_jiage[danjiazhi] = cs.getString(cs.getColumnIndex("StepPrice1"));
                        danjiazhi++;
                    }
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(true);
                    builder.setTitle("选择一个商品");
                    //    指定下拉列表的显示数据
                    //    设置一个下拉的列表选择项
                    builder.setItems(danjiazonghe, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            ac_name.setText(danjia_name[which]);
                            ed_danjia .setText(danjia_jiage[which]);
                            //Toast.makeText(ChuanjianActivity.this, "选择的城市为：" + goods[which], Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                break;
            case "2":
                Cursor cs2 = db.query("goodsdanjia",null,"InventoryID="+"'"+goodsid+"'",null,null,null,null);
                num1 =cs2.getCount();
                if (num1==0){

                }else {
                    danjia_name = new String[num1];
                    danjia_jiage = new String[num1];
                    danjiazonghe = new String[num1];
                    while (cs2.moveToNext()){
                        danjiazonghe[danjiazhi] =  ac_name.getText().toString()+"  "+cs2.getString(cs2.getColumnIndex("StepPrice2"))+"元";
                        danjia_name[danjiazhi] = ac_name.getText().toString();
                        danjia_jiage[danjiazhi] = cs2.getString(cs2.getColumnIndex("StepPrice2"));
                        danjiazhi++;
                    }
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(true);
                    builder.setTitle("选择一个商品");
                    //    指定下拉列表的显示数据
                    //    设置一个下拉的列表选择项
                    builder.setItems(danjiazonghe, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            ac_name.setText(danjia_name[which]);
                            ed_danjia .setText(danjia_jiage[which]);
                            //Toast.makeText(ChuanjianActivity.this, "选择的城市为：" + goods[which], Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                break;
            case "3":
                Cursor cs3 = db.query("goodsdanjia",null,"InventoryID="+"'"+goodsid+"'",null,null,null,null);
                num1 =cs3.getCount();

                if (num1==0){

                }else {
                    danjia_name = new String[num1];
                    danjia_jiage = new String[num1];
                    danjiazonghe = new String[num1];
                    while (cs3.moveToNext()){
                        danjiazonghe[danjiazhi] =  ac_name.getText().toString()+"  "+cs3.getString(cs3.getColumnIndex("StepPrice3"))+"元";
                        danjia_name[danjiazhi] = ac_name.getText().toString();
                        danjia_jiage[danjiazhi] = cs3.getString(cs3.getColumnIndex("StepPrice3"));
                        danjiazhi++;
                    }
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(true);
                    builder.setTitle("选择一个商品");
                    //    指定下拉列表的显示数据
                    //    设置一个下拉的列表选择项
                    builder.setItems(danjiazonghe, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            ac_name.setText(danjia_name[which]);
                            ed_danjia .setText(danjia_jiage[which]);
                            //Toast.makeText(ChuanjianActivity.this, "选择的城市为：" + goods[which], Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                break;
            case "4":
                Cursor cs4 = db.query("goodsdanjia",null,"InventoryID="+"'"+goodsid+"'",null,null,null,null);
                num1 =cs4.getCount();
                if (num1==0){

                }else {
                    danjia_name = new String[num1];
                    danjia_jiage = new String[num1];
                    danjiazonghe = new String[num1];
                    while (cs4.moveToNext()){
                        danjiazonghe[danjiazhi] = ac_name.getText().toString()+"  "+cs4.getString(cs4.getColumnIndex("StepPrice4"))+"元";
                        danjia_name[danjiazhi] = ac_name.getText().toString();
                        danjia_jiage[danjiazhi] = cs4.getString(cs4.getColumnIndex("StepPrice4"));
                        danjiazhi++;
                    }
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(true);
                    builder.setTitle("选择一个商品");
                    //    指定下拉列表的显示数据
                    //    设置一个下拉的列表选择项
                    builder.setItems(danjiazonghe, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            ac_name.setText(danjia_name[which]);
                            ed_danjia .setText(danjia_jiage[which]);
                            //Toast.makeText(ChuanjianActivity.this, "选择的城市为：" + goods[which], Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                break;
            case "5":
                Cursor cs5 = db.query("goodsdanjia",null,"InventoryID="+"'"+goodsid+"'",null,null,null,null);
                num1 =cs5.getCount();
                if (num1==0){

                }else {
                    danjia_name = new String[num1];
                    danjia_jiage = new String[num1];
                    danjiazonghe = new String[num1];
                    while (cs5.moveToNext()){
                        danjiazonghe[danjiazhi] =  ac_name.getText().toString()+"  "+cs5.getString(cs5.getColumnIndex("StepPrice5"))+"元";
                        danjia_name[danjiazhi] = ac_name.getText().toString();
                        danjia_jiage[danjiazhi] = cs5.getString(cs5.getColumnIndex("StepPrice5"));
                        danjiazhi++;
                    }
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(true);
                    builder.setTitle("选择一个商品");
                    //    指定下拉列表的显示数据
                    //    设置一个下拉的列表选择项
                    builder.setItems(danjiazonghe, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            ac_name.setText(danjia_name[which]);
                            ed_danjia .setText(danjia_jiage[which]);
                            //Toast.makeText(ChuanjianActivity.this, "选择的城市为：" + goods[which], Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                break;

        }
//        if (danwei_stepcode.equals("1")){
//
//        }else  if (danwei_stepcode.equals("2")){
//
//
//        }else  if (danwei_stepcode.equals("3")){
//
//
//        }else  if (danwei_stepcode.equals("4")){
//
//
//        }else  if (danwei_stepcode.equals("5")){
//
//        }

        db.close();


    }

    /**
     * 商品单位
     */
    String[] guige,guige_id;
    String guigeid;
    ArrayAdapter<String> adapter_guige;
    private void add_danweo(View view){
        w = 0 ;
        Spinner sp_guige = view.findViewById(R.id.sp_guige);
        SQLiteDatabase db = Helptools.getDb(context);
        Cursor cs = db.query("goodsdanwei",null,null,null,null,null,null);
        int num =cs.getCount();
        if (num==0){

        }else {
            guige = new String[num];
            guige_id = new String[num];
            while (cs.moveToNext()){
                guige[w] = cs.getString(cs.getColumnIndex("name"));
                guige_id[w] = cs.getString(cs.getColumnIndex("id"));
                w++;
            }
            if (w==num){
                w = 0 ;
            }

            adapter_guige = new ArrayAdapter<String>(context,R.layout.spinner_item,guige);
            adapter_guige.setDropDownViewResource(R.layout.spinner_zidingyi);
            sp_guige.setAdapter(adapter_guige);
            sp_guige.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    guigeid = guige_id[i];
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }




    //点击创建订单按钮
    @BindView(R.id.chuanjian_tijiaodingdan)
    Button btn_dingdan;
    SQLiteDatabase db2;
    Cursor cs;
    String readid="" ; //获取刚创建商品表的id

    private static long lastClickTime = 0;
    private static long DIFF = 1000;



    private void adddingdan(){

        btn_dingdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Helptools.exit(context);
                for ( c = 0; c < ll_context.getChildCount(); c++) {
                    view=ll_context.getChildAt(c);
                    AutoCompleteTextView ac_name = view.findViewById(R.id.ac_sahngpin_name);
//                    ac_name.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            ac_name.showDropDown();//点击控件显示所有的选项
//                        }
//                    });
                    EditText ed_num = (EditText)view.findViewById(R.id.ed_num);
                    String num1= ed_num.getText().toString();
                    EditText ed_danjia = (EditText)view.findViewById(R.id.ed_price);
                    String danjia1 = ed_danjia.getText().toString();
                    EditText ed_zhujima = (EditText)view.findViewById(R.id.ed_zhujima);
                    String zhujima = ed_zhujima.getText().toString();
                    EditText ed_beizhu = (EditText)view.findViewById(R.id.ed_beizhu);
                    String beizhu = ed_beizhu.getText().toString();

                    final Spinner sp_guige = (Spinner)view.findViewById(R.id.sp_guige);
                    guige1=sp_guige.getSelectedItem().toString();


                    //Log.e("第几次？",c+"    规格："+guige1+"     分类："+fenlei1+"     品牌："+pinpai1+"    名称："+name+"    数量："+num1+"    单价："+danjia1);

                    if (sp_hezuodanwei.getText().toString().equals("请选择")){
                        Toast.makeText(context,"请选择合作单位",Toast.LENGTH_SHORT).show();
                        break;
                    }else{
                        if (ac_name.getText().toString().equals("")){
                            Toast.makeText(context,"请输入商品名",Toast.LENGTH_SHORT).show();
                            break;
                        }else{
                            if (num1.equals("")){
                                Toast.makeText(context,"请输入商品数量",Toast.LENGTH_SHORT).show();
                                break;
                            }else{
                                if (danjia1.equals("")){
                                    Toast.makeText(context,"请输入商品单价",Toast.LENGTH_SHORT).show();
                                    break;
                                }else{
                                    Log.e("都不为空了","");


                                }
                            }
                        }
                    }

                }

                if(c==ll_context.getChildCount()) {
                    //当上述信息都不为空了，填入信息
                    for (int j = 0; j < ll_context.getChildCount(); j++) {
                        view=ll_context.getChildAt(j);
                        AutoCompleteTextView et_sahngpin_name=((AutoCompleteTextView) view.findViewById(R.id.ac_sahngpin_name));
                        String name=et_sahngpin_name.getText().toString();

                        EditText ed_num = (EditText)view.findViewById(R.id.ed_num);
                        String num1= ed_num.getText().toString();
                        EditText ed_danjia = (EditText)view.findViewById(R.id.ed_price);
                        String danjia1 = ed_danjia.getText().toString();
                        EditText ed_zhujima = (EditText)view.findViewById(R.id.ed_zhujima);
                        String zhujima = ed_zhujima.getText().toString();
                        EditText ed_beizhu = (EditText)view.findViewById(R.id.ed_beizhu);
                        String beizhu = ed_beizhu.getText().toString();

                        final Spinner sp_guige = (Spinner)view.findViewById(R.id.sp_guige);
                        guige1=sp_guige.getSelectedItem().toString();

                        //写入数据库到商品表
                        SQLiteDatabase db = Helptools.wriDb(context);
                        ContentValues cv = new ContentValues();
                        cv.put("name", "" + name);
                        cv.put("id",goodsid);
                        cv.put("guige", "" + guige1);;
                        cv.put("guigeid", "" + guigeid);
                        cv.put("num", "" + num1);
                        cv.put("price", "" + danjia1);
                        cv.put("beizhu", "" + beizhu);
                        db.insert("goods", null, cv);
                        db.close();
                        Log.e("goodsid","+"+goodsid+"**guigeid:"+guigeid);

                        //读取商品id

                        db2 = Helptools.getDb(context);
                        cs = db2.query("goods", null, null, null, null, null, null, null);

                        if (ll_context.getChildCount()>1){
                            if (cs.moveToLast()) {
                                readid = readid+","+cs.getString(cs.getColumnIndex("goodsid")) ;
                                //Log.e("大于1》》",""+readid);
                            }
                        }else{
                            if (cs.moveToLast()) {
                                readid = cs.getString(cs.getColumnIndex("goodsid")) ;
                               // Log.e("等于1》》",""+readid+"        多少信息"+cs.getCount());
                            }
                        }
                    }
                    //获取系统当前时间
                    SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd");
                    Date curDate   =   new   Date(System.currentTimeMillis());//获取当前时间
                    String   settiem   =   formatter.format(curDate);
                    //Log.e("系统时间",""+settiem);
                    //写入数据库到订单表
                    ContentValues cv2 = new ContentValues();
                    cv2.put("hezuoname", "" + sp_hezuodanwei.getText().toString());
                    cv2.put("hezuo_id", "" + danweiid);
                    cv2.put("personid", lxlogo_name);
                    cv2.put("settiem", "" + settiem);
                    cv2.put("updatetiem", "" + settiem);
                    cv2.put("ddstate", "0");  //0、未完成  1、已完成 2、作废
                    cv2.put("scstate", "0");   //0、未上传 1、已上传
                    cv2.put("goodsid", "" + readid);
                    cv2.put("ddhao",""+tv_ddhao.getText().toString());
                    db2.insert("orderform", null, cv2);
                    db2.close();
                    Log.e("数据     ",""+readid);
//                    Log.e("hezuodanwei_id:",hezuodanwei_id);
                    //信息添加成功了，提示
                    Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();

                    //先清空所有布局控件信息文件，然后在动态添加控件
                    ll_context.removeAllViews();
                    b=1;
                    generateSingleLayout();
                     //不让提示信息出来，所以也需要还原
                    sp_hezuodanwei.setSelection(0); //让之前选择的合作单位还原
                    //重新调用动态生成订单号
                    tv_ddhao.setText(getRandomValue());
                }

                //Toast.makeText(ChuanjianActivity.this,"暂时无法提交到服务器",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //点击获取条形码
//    EditText ed_tiaoxingma;
//    TextView tv_tiaoxingma;
//    private void tiaoxingma(View view){
//        ed_tiaoxingma = (EditText)view.findViewById(R.id.ed_tiaoxingma);
//        tv_tiaoxingma = (TextView) view.findViewById(R.id.tv_tiaoxingma);
//        tv_tiaoxingma.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                    //因为这个是fragment页面，不是activity页面，所以要跳转到activity页面获取到值后在返回fragment页面
//                Intent intent = new Intent(context,EmptyActivity.class);
//                intent.putExtra("name",lxlogo_name);
//                startActivity(intent);
//            }
//        });
//
//        //Log.e("条形码：",""+tiaoxingma);
//
//    }

//    @Override
//    public void onResume() {
//        super.onResume();
//       // Log.e("66666666666666",""+tiaoxingma_zhi);
//        if (tiaoxingma_zhi.equals("")){
//            tv_tiaoxingma.setVisibility(View.VISIBLE);
//            ed_tiaoxingma.setVisibility(View.GONE);
//        }else {
//            tv_tiaoxingma.setVisibility(View.GONE);
//            ed_tiaoxingma.setVisibility(View.VISIBLE);
//            ed_tiaoxingma.setText(tiaoxingma_zhi);
//        }
//    }






}
