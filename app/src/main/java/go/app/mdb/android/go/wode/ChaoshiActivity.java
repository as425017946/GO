package go.app.mdb.android.go.wode;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airsaid.pickerviewlibrary.TimePickerView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import go.app.mdb.android.go.R;
import go.app.mdb.android.go.bean.ChaoshiBean;
import go.app.mdb.android.go.bean.Hezuodanwei;
import go.app.mdb.android.go.dingdan.Dingdan;
import go.app.mdb.android.go.tools.Api;
import okhttp3.Call;
import okhttp3.Response;

import static go.app.mdb.android.go.LogoActivity.logo_id;

public class ChaoshiActivity extends AppCompatActivity {
    ArrayList<Dingdan> dd_arraylt = new ArrayList<>();
    ChaoshiAdapter csadapter ;
    Activity context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaoshi);
        ButterKnife.bind(this);
        fanhui();
        setListener();
        addxinxi();
        chaxun();
        init();
    }
    /**
     * 返回
     */
    @BindView(R.id.fenye_fanhui)
    LinearLayout linearLayout_fanhui;
    private void fanhui(){
        linearLayout_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChaoshiActivity.this.finish();
            }
        });
    }

    /**绑定合作单位和起始时间和结束时间**/
    @BindView(R.id.spinner_hzdw)
    AutoCompleteTextView act_hzdw;
    @BindView(R.id.star_item)
    TextView tv_star_item;
    @BindView(R.id.end_time)
    TextView tv_end_item;
    private void setListener(){
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
        TimePickerView mTimePickerView = new TimePickerView(ChaoshiActivity.this, TimePickerView.Type.YEAR_MONTH_DAY);// 四种选择模式，年月日时分，年月日，时分，月日时分
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
    private ArrayList<String> list_1;
    private ArrayAdapter<String> adapter_1;
    private void addxinxi(){
        setData();
        adapter_1 = new ArrayAdapter<String>(ChaoshiActivity.this,  R.layout.item_ac, R.id.tv_1, list_1);
        act_hzdw.setAdapter(adapter_1);
    }
    String[] hzdw_name,hzdw_id;
    private void setData() {
        list_1 =new ArrayList<>();
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
                                list_1.add(hzdw.getPerson().get(i).getName());
                            }
                        }
                    }
                });
    }

    /**查询按钮**/
    @BindView(R.id.search5_chaxun)
    Button chaxun;
    private void chaxun(){
        chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("搜索",act_hzdw.getText().toString());
                if (act_hzdw.getText().toString().equals("")&&tv_star_item.getText().toString().equals("")&&tv_end_item.getText().toString().equals("")){
                    init();
                }else{
                    if(!tv_star_item.getText().toString().equals("")){
                        if (tv_end_item.getText().toString().equals("")){
                            Toast.makeText(ChaoshiActivity.this,"起始时间填写了，结束时间也必须写入！",Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(ChaoshiActivity.this,"结束时间不能小于起始时间！",Toast.LENGTH_SHORT).show();
                                }else {
                                    mohuchaxun();
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }else{
                        mohuchaxun();
                    }
                }
            }
        });
    }
    /**模糊查询**/
    private void mohuchaxun(){
        OkGo.post(Api.chaoshilist)
                .tag(this)
                .params("parterName",act_hzdw.getText().toString())
                .params("startTime",tv_star_item.getText().toString())
                .params("endTime",tv_end_item.getText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("超市列表信息搜索",s);
                        dd_arraylt.clear();
                        Gson gson = new Gson();
                        ChaoshiBean  csbean =gson.fromJson(s,ChaoshiBean.class);
                        String stats = csbean.getState()+"";
                        String zhi = csbean.getData().size()+"";
                        Log.e("bbbb",stats);
                        if(stats.equals("1")){
                            if(zhi.equals("0")){
                                Toast.makeText(ChaoshiActivity.this,"查询的没有信息",Toast.LENGTH_SHORT).show();
                            }
                            for (int i = 0; i <csbean.getData().size() ; i++) {
                                Dingdan dd=new Dingdan();
                                Log.e("aaaa",csbean.getData().get(i).getBillCode());
                                dd.setDdhao(csbean.getData().get(i).getBillCode());
                                dd.setNewtime(csbean.getData().get(i).getCreatTime());
                                dd.setHezuoname(csbean.getData().get(i).getParterName());
                                dd.setId(csbean.getData().get(i).getOrderId()+"");
                                dd_arraylt.add(dd);
                            }
                            csadapter = new ChaoshiAdapter(ChaoshiActivity.this,dd_arraylt);
                            list.setAdapter(csadapter);
                        }

                    }
                });
    }
    /**添加网络数据**/
    @BindView(R.id.dd_lt1)
    ListView list;
    private void init(){
        OkGo.post(Api.chaoshilist)
                .tag(this)
                .params("ID",logo_id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                            Log.e("超市列表信息",s);
                            dd_arraylt.clear();
                            Gson gson = new Gson();
                            ChaoshiBean  csbean =gson.fromJson(s,ChaoshiBean.class);
                            String stats = csbean.getState()+"";
                            String zhi = csbean.getData().size()+"";
                            Log.e("bbbb",stats);
                            if(stats.equals("1")){
                                if(zhi.equals("0")){
                                    Toast.makeText(ChaoshiActivity.this,"查询的没有信息",Toast.LENGTH_SHORT).show();
                                }
                                for (int i = 0; i <csbean.getData().size() ; i++) {
                                    Dingdan dd=new Dingdan();
                                    Log.e("aaaa",csbean.getData().get(i).getBillCode());
                                    dd.setDdhao(csbean.getData().get(i).getBillCode());
                                    dd.setNewtime(csbean.getData().get(i).getCreatTime());
                                    dd.setHezuoname(csbean.getData().get(i).getParterName());
                                    dd.setId(csbean.getData().get(i).getOrderId()+"");
                                    dd_arraylt.add(dd);
                                }
                                csadapter = new ChaoshiAdapter(ChaoshiActivity.this,dd_arraylt);
                                list.setAdapter(csadapter);
                            }

                    }
                });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Dingdan dd = dd_arraylt.get(i);
                Intent intent = new Intent(ChaoshiActivity.this,ChaoshiMoreActivity.class);
                intent.putExtra("ddhao",dd.getDdhao());
                intent.putExtra("dd_id",dd.getId());
                intent.putExtra("hzdw",dd.getHezuoname());
                startActivity(intent);
            }
        });
    }

}
