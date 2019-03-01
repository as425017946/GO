package go.app.mdb.android.go;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airsaid.pickerviewlibrary.TimePickerView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import go.app.mdb.android.go.bean.Hezuodanwei;
import go.app.mdb.android.go.bean.SecondFragmentBean;
import go.app.mdb.android.go.dingdan.ChuanjianActivity;
import go.app.mdb.android.go.dingdan.DdMyAdapter;
import go.app.mdb.android.go.dingdan.Dingdan;
import go.app.mdb.android.go.pandian.MyAdapter;
import go.app.mdb.android.go.tools.Api;
import okhttp3.Call;
import okhttp3.Response;

import static go.app.mdb.android.go.LogoActivity.logo_id;
import static go.app.mdb.android.go.LogoActivity.logo_limit;
import static go.app.mdb.android.go.LogoActivity.logo_name;
@SuppressLint("NewApi")
public class SecondFragment extends Fragment implements AbsListView.OnScrollListener {
    @Nullable

    DdMyAdapter ddMyAdapter;
    Context context;
    ArrayList<Dingdan> dd_arraylt = new ArrayList<>();
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fg2,container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    //调用实例化后的页面
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListener();
        addxinxi();
        chuangjian();
        sousuo();
        initViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Log.e("中","sdadas");
        init();
    }

    /***
     * 先给类似combox的自定义控件加入数据信息
     */
    @BindView(R.id.ac_1)
    AutoCompleteTextView act1;
    @BindView(R.id.ac_5)
    AutoCompleteTextView act5;
    @BindView(R.id.ac_6)
    AutoCompleteTextView act6;
    @BindView(R.id.spinner_hzdw)
    AutoCompleteTextView act_hzdw;
    @BindView(R.id.star_item)
    TextView tv_star_item;
    @BindView(R.id.end_time)
    TextView tv_end_item;

    private ArrayList<String> list_1,list_2,list_5,list_6 ;
    private ArrayAdapter<String> adapter_1,adapter_2,adapter_5,adapter_6 ;

    private void setListener(){
        act1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act1.showDropDown();//点击控件显示所有的选项
            }
        });
        act5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act5.showDropDown();//点击控件显示所有的选项
            }
        });
        act6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act6.showDropDown();//点击控件显示所有的选项
            }
        });
        act_hzdw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_hzdw.showDropDown();//点击控件显示所有的选项
            }
        });
        tv_star_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimer(tv_star_item);
            }
        });
        tv_end_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimer(tv_end_item);
            }
        });
    }
    //时间选择器
    private void showTimer(final TextView editText){
        //     TimePickerView 同样有上面设置样式的方法
        TimePickerView mTimePickerView = new TimePickerView(context, TimePickerView.Type.YEAR_MONTH_DAY);// 四种选择模式，年月日时分，年月日，时分，月日时分
        // 设置是否循环
        mTimePickerView.setCyclic(true);

        // 设置滚轮文字大小
        mTimePickerView.setTextSize(TimePickerView.TextSize.BIG);
        // 设置时间可选范围(结合 setTime 方法使用,必须在)
//        Calendar calendar = Calendar.getInstance();
//        mTimePickerView.setRange(calendar.get(Calendar.YEAR) - 100, calendar.get(Calendar.YEAR));
        // 设置选中时间
        mTimePickerView.setTime(new Date());//设置选中的时间  new date（）是今天的时间
        mTimePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
//                Toast.makeText(context, format.format(date), Toast.LENGTH_SHORT).show();
                editText.setText(format.format(date));
            }
        });
        mTimePickerView.show();
    }


    private void addxinxi(){
        setData();
        adapter_1 = new ArrayAdapter<String>(context, R.layout.item_ac, R.id.tv_1, list_1);
        act1.setAdapter(adapter_1);
        adapter_5 = new ArrayAdapter<String>(context,  R.layout.item_ac, R.id.tv_1, list_5);
        act5.setAdapter(adapter_5);
        adapter_6 = new ArrayAdapter<String>(context,  R.layout.item_ac, R.id.tv_1, list_6);
        act6.setAdapter(adapter_6);
        adapter_2 = new ArrayAdapter<String>(context,  R.layout.item_ac, R.id.tv_1, list_2);
        act_hzdw.setAdapter(adapter_2);
    }

    String[] hzdw_name,hzdw_id;
    private void setData() {//设置数据源，这里只用了单纯的文字；
        list_1 =new ArrayList<>();

        list_2 =new ArrayList<>();
        Log.e("id值",logo_id);
        OkGo.post(Api.hezuodanwei)
                .tag(this)
                .params("laSalesmanID",logo_id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("数据",s);
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

        list_5 =new ArrayList<>();
        list_5 .add("未完成");
        list_5 .add("配送中");
        list_5 .add("作废");
        list_5 .add("配送完成");

        list_6 =new ArrayList<>();
        list_6 .add("未上传");
        list_6 .add("已上传");


    }


    String hezuo_id;

    /****
     * 点击搜索
     */
    @BindView(R.id.search_ll2_chaxun)
    Button btn_chaxun;
    String state="",upstate="";
    private void sousuo() {
        btn_chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (act1.getText().toString().equals("")&&act_hzdw.getText().toString().equals("")&&tv_star_item.getText().toString().equals("")&&
                        tv_end_item.getText().toString().equals("")&&act5.getText().toString().equals("")&&act6.getText().toString().equals("")){
                        //无数据返回原来的查询
                       init();
                }else {

                    if(!tv_star_item.getText().toString().equals("")){
                        if (tv_end_item.getText().toString().equals("")){
                            Toast.makeText(context,"起始时间填写了，结束时间也必须写入！",Toast.LENGTH_SHORT).show();
                        }else {
                            SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd",
                                    Locale.CHINA);
                            Date date,date1;
                            try {
                                date = sdr.parse(tv_star_item.getText().toString());
                                long L1 = date.getTime();
                                date1 = sdr.parse(tv_end_item.getText().toString());
                                long L2 = date1.getTime();
                                //Log.e("比较时间",""+(L1-L2));
                                if ((L1-L2)>0){
                                    Toast.makeText(context,"结束时间不能小于起始时间！",Toast.LENGTH_SHORT).show();
                                }else {
                                    //Log.e("aaaaaaaaaaaaaa","bbbbbbbbbbbbbb");
                                    sousuojiazai();
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }else{
                       // Log.e("cccccccccccc","ccccccccccc");
                        sousuojiazai();
                    }


                }
            }
        });
    }

    String star1,end1;
    private void sousuojiazai(){
        for (int i = 0; i < hzdw_name.length ; i++) {
            if(act_hzdw.getText().toString().equals(hzdw_name[i])){
                hezuo_id = hzdw_id[i];
                break;
            }else {
                hezuo_id = "";
            }
        }
        if (act5.getText().toString().equals("未完成")){
            state = "0";
        }else if (act5.getText().toString().equals("配送中")){
            state = "1";
        }else if (act5.getText().toString().equals("作废")){
            state = "2";
        }else if (act5.getText().toString().equals("配送完成")){
            state = "3";
        }else {
            state=act5.getText().toString();
        }
        if(act6.getText().toString().equals("未上传")){
            upstate = "0" ;
        }else if (act6.getText().toString().equals("已上传")){
            upstate = "1";
        }else{
            upstate = act6.getText().toString();
        }

        Log.e("订单状态",state);
        Log.e("上传状态",upstate);
        if(!state.equals("0")&&!state.equals("1")&&!state.equals("2")&&!state.equals("")&&!state.equals("3")){
            act5.setText("");
            Toast.makeText(context,"输入的订单状态不正确！",Toast.LENGTH_SHORT).show();
        }
        if (!upstate.equals("0")&&!upstate.equals("1")&&!upstate.equals("")){
            act6.setText("");
            Toast.makeText(context,"输入的上传状态不正确！",Toast.LENGTH_SHORT).show();

        }

        //    Log.e("*********",hezuo_id+"lxlogo_name"+lxlogo_name+"act3.getText().toString()"+act3.getText().toString()+"act4.getText().toString()"+act4.getText().toString()+"state"+state+"upstate"+upstate);
        //   直接显示搜索的订单信息
        if (tv_star_item.getText().toString().equals("")){
            star1 = "";
        }else {
            star1 = tv_star_item.getText()+" 00:00:00";
        }
        if (tv_end_item.getText().toString().equals("")){
            end1 = "";
        }else {
            end1 = tv_end_item.getText()+" 00:00:00";
        }

        Log.e("star1",star1);
        Log.e("end1",end1);
        Log.e("state",state);
        Log.e("upstate",upstate);
        OkGo.post(Api.dd_chaxun)
                .tag(this)
                .params("salesmanName",name)
                .params("parterID",hezuo_id)
                .params("startTime",star1)
                .params("stopTime",end1)
                .params("status",state)
                .params("isUpdate",upstate)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("订单查看返回信息",s);
                        tv_end_item.setText("");
                        tv_star_item.setText("");

                        dd_arraylt.clear();
                        Gson gson = new Gson();
                        SecondFragmentBean secondFragmentBean = gson.fromJson(s,SecondFragmentBean.class);
                        String state = secondFragmentBean.getState();
                        // Log.e("State",state);
                        if (state.equals("1")){
                            for (int i = 0; i <secondFragmentBean.getPerson().size() ; i++) {
                                Dingdan dd=new Dingdan();
                                dd.setDdhao(secondFragmentBean.getPerson().get(i).getBillCode());
                                dd.setHezuoname(secondFragmentBean.getPerson().get(i).getParterID());
                                dd.setNewtime(secondFragmentBean.getPerson().get(i).getCreatTime());
                                dd.setUpdatetime(secondFragmentBean.getPerson().get(i).getUpdateTime());
                                //订单状态改文字
                                //0、未完成 1、已完成 2、作废
                                String ddstate = ""+secondFragmentBean.getPerson().get(i).getStatus();
                                if (ddstate.equals("0")){
                                    ddstate = "未完成" ;
                                }else if (ddstate.equals("1")){
                                    ddstate = "配送中" ;
                                }else if (ddstate.equals("2")){
                                    ddstate = "作废" ;
                                }else if (ddstate.equals("3")){
                                    ddstate = "配送完成" ;
                                }
                                dd.setDdstate(ddstate);
                                //上传状态改文字
                                //0、未上传 1、已上传
                                String upstate = secondFragmentBean.getPerson().get(i).getIsUpdate()+"";
                                if (upstate.equals("0")){
                                    upstate = "未上传";
                                }else if (upstate.equals("1")){
                                    upstate = "已上传";
                                }
                                dd.setUpdatestate(upstate);
                                dd.setXiaoshouyuan(logo_name);
                                dd.setId(secondFragmentBean.getPerson().get(i).getID()+"");
                                dd_arraylt.add(dd);
                            }
                            ddMyAdapter = new DdMyAdapter(context,dd_arraylt);
                            list.setAdapter(ddMyAdapter);
                            list.removeFooterView(view_more);

                        }else {
                            String[] empty = new String[0];
                            ArrayAdapter<String> emptyadapter = new ArrayAdapter<String>(context, R.layout.spinner_item2, empty);
                            list.setAdapter(emptyadapter);
                            list.removeFooterView(view_more);
                            Toast.makeText(context,"暂无查询的订单信息！",Toast.LENGTH_SHORT).show();

                        }



                    }
                });
    }


    //创建订单
    @BindView(R.id.search_ll2_chuangjian)
    Button btn_chuangjian;
    private void chuangjian(){
        btn_chuangjian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChuanjianActivity.class);
                startActivity(intent);
            }
        });
    }



//    /**
//     * 根据时间戳转换成正常显示时间 标题显示时间
//     * */
//
//    private  String transformTimeDay(int data, long time1){
//        SimpleDateFormat sdfTwo = null;
//        if (1==data){
////            sdfTwo= new SimpleDateFormat("yyyy年MM月dd日",Locale.getDefault());
//            sdfTwo= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//        }else if (2==data){
////            sdfTwo= new SimpleDateFormat("MM月dd日",Locale.getDefault());
//            sdfTwo= new SimpleDateFormat("MM-dd",Locale.getDefault());
//        }else if (3==data){
////            sdfTwo = new SimpleDateFormat("HH:mm",Locale.getDefault());
//            sdfTwo = new SimpleDateFormat("HH:mm",Locale.getDefault());
//        }else if(4==data){
////            sdfTwo = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss",Locale.getDefault());
//            sdfTwo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());
//        }
//
//
//
//        String time11 = sdfTwo.format(time1);
//        return time11;
//    }


    @BindView(R.id.dd_lt1)
    ListView list ;

    /**
     * 添加网络数据
     */
    String name;
    private void init() {

        if (logo_limit.equals("2")){
            name = "";
        }else {
            name = logo_name;
        }
        Log.e("名字",""+name+"****"+logo_name);
        OkGo.post(Api.dingdan)
                .tag(this)
                .params("salesmanName",name)
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("State",s);
                        dd_arraylt.clear();
                        Gson gson = new Gson();
                        SecondFragmentBean secondFragmentBean = gson.fromJson(s,SecondFragmentBean.class);
                        String state = secondFragmentBean.getState();
                        totalCount = secondFragmentBean.getCount();
                       // Log.e("totalCount",totalCount+"");
                        if (state.equals("1")){
//                            if (xiala_coint>totalCount){
                                for (int i = 0; i <totalCount ; i++) {
                                    Dingdan dd=new Dingdan();
                                    dd.setDdhao(secondFragmentBean.getPerson().get(i).getBillCode());
                                    dd.setHezuoname(secondFragmentBean.getPerson().get(i).getParterID());
                                    //时间戳转时间
                                    dd.setNewtime(String.valueOf(secondFragmentBean.getPerson().get(i).getCreatTime()));
                                    dd.setUpdatetime(String.valueOf(secondFragmentBean.getPerson().get(i).getUpdateTime()));
                                    //订单状态改文字
                                    //0、未完成 1、已完成 2、作废
                                    String ddstate = ""+secondFragmentBean.getPerson().get(i).getStatus();
                                    if (ddstate.equals("0")){
                                        ddstate = "未完成" ;
                                    }else if (ddstate.equals("1")){
                                        ddstate = "配送中" ;
                                    }else if (ddstate.equals("2")){
                                        ddstate = "作废" ;
                                    }else if (ddstate.equals("3")){
                                        ddstate = "配送完成" ;
                                    }
                                    dd.setDdstate(ddstate);
                                    //上传状态改文字
                                    //0、未上传 1、已上传
                                    String upstate = secondFragmentBean.getPerson().get(i).getIsUpdate()+"";
                                    if (upstate.equals("0")){
                                        upstate = "未上传";
                                    }else if (upstate.equals("1")){
                                        upstate = "已上传";
                                    }
                                    dd.setUpdatestate(upstate);
                                    dd.setXiaoshouyuan(secondFragmentBean.getPerson().get(i).getSalesmanName());
                                    //  Log.e("销售员名字",""+secondFragmentBean.getPerson().get(i).getSalesmanName());
                                    dd.setId(secondFragmentBean.getPerson().get(i).getID()+"");
                                    dd_arraylt.add(dd);
                                }
                                ddMyAdapter = new DdMyAdapter(context,dd_arraylt);
                                list.setAdapter(ddMyAdapter);
//                            }else{
//                                //Log.e("mengdebin","mengdebin");
//                                for (int i = 0; i <xiala_coint ; i++) {
//                                    Dingdan dd=new Dingdan();
//                                    dd.setDdhao(secondFragmentBean.getPerson().get(i).getBillCode());
//                                    dd.setHezuoname(secondFragmentBean.getPerson().get(i).getParterID());
//                                    //时间戳转时间
//                                    dd.setNewtime(String.valueOf(secondFragmentBean.getPerson().get(i).getCreatTime()));
//                                    dd.setUpdatetime(String.valueOf(secondFragmentBean.getPerson().get(i).getUpdateTime()));
//                                    //订单状态改文字
//                                    //0、未完成 1、已完成 2、作废
//                                    String ddstate = ""+secondFragmentBean.getPerson().get(i).getStatus();
//                                    if (ddstate.equals("0")){
//                                        ddstate = "未完成" ;
//                                    }else if (ddstate.equals("1")){
//                                        ddstate = "配送中" ;
//                                    }else if (ddstate.equals("2")){
//                                        ddstate = "作废" ;
//                                    }else if (ddstate.equals("3")){
//                                        ddstate = "配送完成" ;
//                                    }
//                                    dd.setDdstate(ddstate);
//                                    //上传状态改文字
//                                    //0、未上传 1、已上传
//                                    String upstate = secondFragmentBean.getPerson().get(i).getIsUpdate()+"";
//                                    if (upstate.equals("0")){
//                                        upstate = "未上传";
//                                    }else if (upstate.equals("1")){
//                                        upstate = "已上传";
//                                    }
//                                    dd.setUpdatestate(upstate);
//                                    dd.setXiaoshouyuan(secondFragmentBean.getPerson().get(i).getSalesmanName());
//                                    //  Log.e("销售员名字",""+secondFragmentBean.getPerson().get(i).getSalesmanName());
//                                    dd.setId(secondFragmentBean.getPerson().get(i).getID()+"");
//                                    dd_arraylt.add(dd);
//                                }
//                                ddMyAdapter = new DdMyAdapter(context,dd_arraylt);
//                                list.setAdapter(ddMyAdapter);
//                                // 给handler发消息更新UI，子线程不可以更新UI
//                                Message message = new Message();
//                                message.what = 0;
//                                handler.sendMessage(message);
//                            }


                        }else {
                            Toast.makeText(context,"当前登录的账号暂无订单信息！",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Dingdan dd = dd_arraylt.get(i);
                //Log.e("dd_id",dd.getId());
                Intent intent = new Intent(context,Dd_searchActivity.class);
                intent.putExtra("ddhao",dd.getDdhao());
                intent.putExtra("dd_id",dd.getId());
                intent.putExtra("hzdw",dd.getHezuoname());
                intent.putExtra("creattime",dd.getNewtime());
                intent.putExtra("uptime",dd.getUpdatetime());
                intent.putExtra("state",dd.getDdstate());
                intent.putExtra("upstate",dd.getUpdatestate());
                startActivity(intent);

            }
        });


    }
    private void init(final int start, final int end) {

        if (logo_limit.equals("2")){
            name = "";
        }else {
            name = logo_name;
        }
        //Log.e("名字",""+name+"****"+logo_name);
        OkGo.post(Api.dingdan)
                .tag(this)
                .params("salesmanName",name)
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("State",s);
                        Gson gson = new Gson();
                        SecondFragmentBean secondFragmentBean = gson.fromJson(s,SecondFragmentBean.class);
                        String state = secondFragmentBean.getState();
                        Log.e("State",state);
                        if (state.equals("1")){
                            for (int i = start; i <end ; i++) {
                                Dingdan dd=new Dingdan();
                                dd.setDdhao(secondFragmentBean.getPerson().get(i).getBillCode());
                                dd.setHezuoname(secondFragmentBean.getPerson().get(i).getParterID());
                                //时间戳转时间
                                dd.setNewtime(String.valueOf(secondFragmentBean.getPerson().get(i).getCreatTime()));
                                dd.setUpdatetime(String.valueOf(secondFragmentBean.getPerson().get(i).getUpdateTime()));
                                //订单状态改文字
                                //0、未完成 1、已完成 2、作废
                                String ddstate = ""+secondFragmentBean.getPerson().get(i).getStatus();
                                if (ddstate.equals("0")){
                                    ddstate = "未完成" ;
                                }else if (ddstate.equals("1")){
                                    ddstate = "配送中" ;
                                }else if (ddstate.equals("2")){
                                    ddstate = "作废" ;
                                }else if (ddstate.equals("3")){
                                    ddstate = "配送完成" ;
                                }
                                dd.setDdstate(ddstate);
                                //上传状态改文字
                                //0、未上传 1、已上传
                                String upstate = secondFragmentBean.getPerson().get(i).getIsUpdate()+"";
                                if (upstate.equals("0")){
                                    upstate = "未上传";
                                }else if (upstate.equals("1")){
                                    upstate = "已上传";
                                }
                                dd.setUpdatestate(upstate);
                                dd.setXiaoshouyuan(secondFragmentBean.getPerson().get(i).getSalesmanName());
                                //  Log.e("销售员名字",""+secondFragmentBean.getPerson().get(i).getSalesmanName());
                                dd.setId(secondFragmentBean.getPerson().get(i).getID()+"");
                                dd_arraylt.add(dd);
                            }
                            ddMyAdapter.notifyDataSetChanged();
//                            new Thread() {
//                                public void run() {
//                                    try {
//                                        Thread.sleep(4000);// 模拟获取数据时的耗时3s
//
//                                        // 给handler发消息更新UI，子线程不可以更新UI
//                                        Message message = new Message();
//                                        message.what = 1;
//                                        handler.sendMessage(message);
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                };
//                            }.start();


                        }else {
                            Toast.makeText(context,"当前登录的账号暂无订单信息！",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Dingdan dd = dd_arraylt.get(i);
                //Log.e("dd_id",dd.getId());
                Intent intent = new Intent(context,Dd_searchActivity.class);
                intent.putExtra("ddhao",dd.getDdhao());
                intent.putExtra("dd_id",dd.getId());
                intent.putExtra("hzdw",dd.getHezuoname());
                intent.putExtra("creattime",dd.getNewtime());
                intent.putExtra("uptime",dd.getUpdatetime());
                intent.putExtra("state",dd.getDdstate());
                intent.putExtra("upstate",dd.getUpdatestate());
                startActivity(intent);

            }
        });


    }

    /***
     * 下拉功能
     */
    private int totalCount;// 数据总条数
    private View view_more;
    private ProgressBar pb;
    private TextView tvLoad;
    private int lastVisibleIndex;
    int xiala_coint = 10;

    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    ddMyAdapter = new DdMyAdapter(context,dd_arraylt);
                    list.setAdapter(ddMyAdapter);
                    // 添加底部加载布局
                    list.addFooterView(view_more);
                   // Log.e("mengdebin","mengdebin");
                    // 设置监听
                    setListeners();
                    break;
                case 1:
                    ddMyAdapter.notifyDataSetChanged();
                    break;
            }
        };
    };

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
//        Log.e("TAG", "lastVisibleIndex = " + lastVisibleIndex);
//        Log.e("TAG", "adapter.getCount() = " + ddMyAdapter.getCount());
        // 滑到底部后自动加载，判断listView已经停止滚动并且最后可视的条目等于adapter的条目
        // 注意这里在listView设置好adpter后，加了一个底部加载布局。
        // 所以判断条件为：lastVisibleIndex == adapter.getCount()
        if (scrollState == SCROLL_STATE_IDLE
                && lastVisibleIndex == ddMyAdapter.getCount()) {
            /**
             * 这里也要设置为可见，是因为当你真正从网络获取数据且获取失败的时候。
             * 我在失败的方法里面，隐藏了底部的加载布局并提示用户加载失败。所以再次监听的时候需要
             * 继续显示隐藏的控件。因为我模拟的获取数据，失败的情况这里不给出。实际中简单的加上几句代码就行了。
             */
//            Log.e("TAG", "scrollState =" + scrollState);
            pb.setVisibility(View.VISIBLE);
            tvLoad.setVisibility(View.VISIBLE);
            loadMoreData();// 加载更多数据
        }
    }

    /**
     * 监听listView的滑动
     */
    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        // 计算最后可见条目的索引
        lastVisibleIndex = i + i1 - 1;
        // 当adapter中的所有条目数已经和要加载的数据总条数相等时，则移除底部的View
        if (i2 == totalCount + 1) {
            // 移除底部的加载布局
            list.removeFooterView(view_more);
        }
    }
    int start,end;
    private void loadMoreData() {
        // 获取此时adapter中的总条目数
        int count = ddMyAdapter.getCount();
        // 一次加载10条数据，即下拉加载的执行
        if (count + 10 < totalCount) {
            start = count;
            end = start + 10;
            init(start, end);// 模拟网络获取数据操作
        } else {// 数据不足15条直接加载到结束
            start = count;
            end = totalCount;
            init(start, end);// 模拟网络获取数据曹祖

        }

    }
    private void setListeners() {
        if (totalCount > 10) {
            // listView设置滑动简监听
            list.setOnScrollListener(SecondFragment.this);
        } else {
            // 假如数据总数少于等于10条，直接移除底部的加载布局，不需要再加载更多的数据
            list.removeFooterView(view_more);
        }
    }

    private void initViews() {
        // 构建底部加载布局
        view_more = (View) getLayoutInflater()
                .inflate(R.layout.view_more, null);
        // 进度条
        pb = (ProgressBar) view_more.findViewById(R.id.progressBar);
        // “正在加载...”文本控件
        tvLoad = (TextView) view_more.findViewById(R.id.tv_Load);
    }


}
