package go.app.mdb.android.go.lixian;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import go.app.mdb.android.go.Dd_searchActivity;
import go.app.mdb.android.go.R;
import go.app.mdb.android.go.dingdan.DdMyAdapter;
import go.app.mdb.android.go.dingdan.Dingdan;
import go.app.mdb.android.go.tools.Helptools;
import static go.app.mdb.android.go.lixian.LixinaMainActivity.lxlogo_name;

public class Lixian_WodeFragment extends Fragment {

    Context context;
    DdMyAdapter ddMyAdapter;
    ArrayList<Dingdan> ddarray = new ArrayList<>();
    @BindView(R.id.lx_wode_lt1)
    ListView listView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.activity_lixian__wode,container, false);
        ButterKnife.bind(this,view);//在这个方法内  不许在写其他的东西  因为只有返回后你才能拿到当前的视图  你要是在return之前你去写点击 会报错
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      //  init();
        chaxun();
        setListener();
        addxinxi();

    }

    //查询离线订单
    @BindView(R.id.ac_1)
    AutoCompleteTextView ac1;
    @BindView(R.id.ac_2)
    AutoCompleteTextView ac2;
    @BindView(R.id.ac_3)
    AutoCompleteTextView ac3;
    @BindView(R.id.ac_4)
    AutoCompleteTextView ac4;
    @BindView(R.id.search_ll4_chaxun)
    Button btn_chaxun;
    String state_dd,state_sc,hezuoname_dd,uptime_dd;
    private void chaxun() {
        btn_chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ac1.getText().toString().equals("")&&ac2.getText().toString().equals("")&&
                   ac3.getText().toString().equals("")&&ac4.getText().toString().equals("")){
                    Toast.makeText(context,"请输入要查询的信息",Toast.LENGTH_SHORT).show();
                }else {

                    //判断是不是为空值，空值的话sql语句就不加了
                    db = Helptools.getDb(context);
                    //+"hezuoname Like "+"'%"+hezuoname_dd+"%'"+"  or  "+"settiem Like "+"'%"+uptime_dd+"%'"+"  or  "+"ddstate Like "+"'%"+state_dd+"%'"+"  or  "+"scstate Like "+"'%"+state_sc+"%'  )";
                    String sql = "select * from orderform  where " + "personid="+"'"+lxlogo_name+"'";

                    if (ac1.getText().toString().equals("")){
                        hezuoname_dd = "";
                    }else{
                        hezuoname_dd = ac1.getText().toString();
                        sql = sql +" and hezuoname Like "+"'%"+hezuoname_dd+"%'";

                    }

                    if (ac2.getText().toString().equals("")){
                        uptime_dd = "";
                    }else{
                        uptime_dd = ac2.getText().toString();
                        sql = sql + " and settiem Like "+"'%"+uptime_dd+"%'";

                    }

                    if (ac3.getText().toString().equals("")){
                        state_dd = "";
                    }else{
                        if(ac3.getText().toString().equals("未完成")){
                            state_dd = "0";
                        }else if(ac3.getText().toString().equals("已完成")){
                            state_dd = "1";
                        }else if(ac3.getText().toString().equals("作废")){
                            state_dd = "2";
                        }else {
                            state_dd = ac3.getText().toString();
                        }
                        sql = sql + " and ddstate Like "+"'%"+state_dd+"%'";
                    }

                    if (ac4.getText().toString().equals("")){
                        state_sc = "";
                    }else{
                        if(ac4.getText().toString().equals("未上传")){
                            state_sc = "0";
                        }else if(ac4.getText().toString().equals("已上传")){
                            state_sc = "1";
                        }else {
                            state_sc = ac4.getText().toString();
                        }
                        sql = sql +" and scstate Like "+"'%"+state_sc+"%'";

                    }

                   // Log.e("sql语句",sql);
                    Cursor cs = db.rawQuery(sql,null);
                    //执行
                    init(cs);
                }
            }
        });
    }

    private ArrayList<String> list_1,list_2,list_3 ;
    private ArrayAdapter<String> adapter_1,adapter_2,adapter_3;
    private void setListener(){
        ac1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ac1.showDropDown();
            }
        });
        ac3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ac3.showDropDown();//点击控件显示所有的选项
            }
        });
        ac4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ac4.showDropDown();//点击控件显示所有的选项
            }
        });
    }
    private void addxinxi() {
        setData();
        adapter_3 = new ArrayAdapter<String>(context, R.layout.item_ac, R.id.tv_1, list_3);
        ac1.setAdapter(adapter_3);
        adapter_1 = new ArrayAdapter<String>(context, R.layout.item_ac, R.id.tv_1, list_1);
        ac3.setAdapter(adapter_1);
        adapter_2 = new ArrayAdapter<String>(context, R.layout.item_ac, R.id.tv_1, list_2);
        ac4.setAdapter(adapter_2);
    }
    private void setData() {//设置数据源，这里只用了单纯的文字；
        list_1 =new ArrayList<>();
        list_1 .add("未完成");
        list_1 .add("已完成");
        list_1 .add("作废");

        list_2 =new ArrayList<>();
        list_2 .add("未上传");
        list_2 .add("已上传");

        list_3 =new ArrayList<>();
    }

    //添加本地数据库信息
    SQLiteDatabase db;
    private void init(Cursor cs) {
        Log.e("到底有多少条",""+ddarray.size()+"sql查询出多少"+cs.getCount());
        //先清空listview数据，然后在更新新数据
        if(ddarray.size()>0){
            ddarray.removeAll(ddarray);
            ddMyAdapter.notifyDataSetChanged();
            listView.setAdapter(ddMyAdapter);
        }

        //判断登陆的人是否有离线订单
        if (cs.getCount()==0){
            Toast.makeText(context, "您登陆的账号暂无此类离线订单",Toast.LENGTH_SHORT).show();
        }else {
            //有信息循环填入listview控件
            while (cs.moveToNext()) {
                String hezuoname = cs.getString(cs.getColumnIndex("hezuoname"));
                String person = cs.getString(cs.getColumnIndex("personid"));
                String ddhao = cs.getString(cs.getColumnIndex("ddhao"));
                String setuptime = cs.getString(cs.getColumnIndex("settiem"));
                String updatime = cs.getString(cs.getColumnIndex("updatetiem"));
                String ddstate = cs.getString(cs.getColumnIndex("ddstate"));
                String goodsid=cs.getString(cs.getColumnIndex("goodsid"));
                Log.e("订单状态",""+ddstate);
                if(ddstate.equals("0")){
                    ddstate = "未完成";
                }else if(ddstate.equals("1")){
                    ddstate = "已完成";
                }else if(ddstate.equals("2")){
                    ddstate = "作废";
                }
                String updatestate = cs.getString(cs.getColumnIndex("scstate"));
                if(updatestate.equals("0")){
                    updatestate = "未上传";
                }else if(updatestate.equals("1")){
                    updatestate = "已上传";
                }
                //获取一下信息没写
                //Log.e("信息信息  ",""+hezuoname+person+ddhao+setuptime+updatime+ddstate+updatestate);
                Dingdan dd=new Dingdan();
                dd.setDdhao(ddhao);
                dd.setHezuoname(hezuoname);
                dd.setNewtime(setuptime);
                dd.setUpdatetime(updatime);
                dd.setDdstate(ddstate);
                dd.setUpdatestate(updatestate);
                dd.setXiaoshouyuan(person);
                dd.setId(goodsid);
                ddarray.add(dd);
            }
            ddMyAdapter = new DdMyAdapter(context,ddarray);
            listView.setAdapter(ddMyAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Dingdan dd=ddarray.get(i);
                  //  Log.e("goodid我的值",""+dd.getId());//拿到对象

                    Intent intent = new Intent(context,Lixianinfo_searchActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ddhao",""+dd.getDdhao());
                    bundle.putString("henzuoname",""+dd.getHezuoname());
                    bundle.putString("settime",""+dd.getNewtime());
                    bundle.putString("uptime",""+dd.getUpdatetime());
                    bundle.putString("ddstate",""+dd.getDdstate());
                    bundle.putString("scstate",""+dd.getUpdatestate());
                    bundle.putString("goodsid",""+dd.getId());
                    bundle.putString("wodeOrlixian","0"); //判读是从我的页面进去的离线页面还是从离线页面进去的1wode 0lixian
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        db.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        db = Helptools.getDb(context);
        Cursor cs = db.query("orderform", null, "personid="+"'"+lxlogo_name+"'", null, null, null, null, null);
        init(cs);
    }


}
