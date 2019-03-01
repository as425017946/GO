package go.app.mdb.android.go.wode;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import go.app.mdb.android.go.R;
import go.app.mdb.android.go.ThirdFragment;
import go.app.mdb.android.go.bean.GaijiActivityBean;
import go.app.mdb.android.go.kucun.KuncunMyAdapater;
import go.app.mdb.android.go.tools.Api;
import okhttp3.Call;
import okhttp3.Response;

import static go.app.mdb.android.go.LogoActivity.logo_id;

public class GaijiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaiji);
        ButterKnife.bind(this);

        //initViews();
        fanhui();
        title();
        setListener();
        addxinxi();
        chaxun();
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
                GaijiActivity.this.finish();
            }
        });
    }
    /**
     * 修改title
     */
    @BindView(R.id.fenye_title)
    TextView tv_title;
    private void title() {
        tv_title.setText("更改店铺等级");
    }
    /**
     * 添加网络数据
     */
    GaijiMyAdapter gj_myadapter;
    ArrayList<Gaiji> gj_arrayList = new ArrayList<>();
    @BindView(R.id.gaiji_lt)
    ListView gj_lt;
    private void init(){
        OkGo.post(Api.dianpu)
                .tag(this)
                .params("laSalesmanID",logo_id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //Log.e("店铺",s);
                        //Log.e("logo_id",logo_id);
                            gj_arrayList.clear();
                            Gson gson = new Gson();
                            GaijiActivityBean gb = gson.fromJson(s,GaijiActivityBean.class);
                        //Log.e("********",gb.getPerson().get(0).getID()+"");
                            if(gb.getState().equals("1")){
                             //   totalCount = gb.getPerson().size();
                             //   Log.e("改级店铺数量",totalCount+"");
//                                if (xiala_coint>totalCount){
                                    for (int i =0;i<gb.getPerson().size();i++){
                                        Gaiji gj =new Gaiji();
                                        gj.setDianpuname(gb.getPerson().get(i).getName());
                                        gj.setJishu(gb.getPerson().get(i).getStepCode());
                                        gj.setId(gb.getPerson().get(i).getID()+"");
                                        gj_arrayList.add(gj);
                                    }

                                    gj_myadapter = new GaijiMyAdapter(GaijiActivity.this,gj_arrayList);
                                    gj_lt.setAdapter(gj_myadapter);


//                                    gj_lt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                        @Override
//                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                            Gaiji gj = gj_arrayList.get(i);
//                                            Log.e("setOnItemClickListener","Gaiji"+gj.getId());
//                                            Intent intent  = new Intent(GaijiActivity.this,GaijiMoreActivity.class);
//                                            intent.putExtra("name",gj.getDianpuname());
//                                            // intent.putExtra("dengji",gj.getJishu());
//                                            intent.putExtra("dp_id",gj.getId());
//                                            startActivity(intent);
//                                        }
//                                    });
//                                }else {
//                                    for (int i =0;i<xiala_coint;i++){
//                                        Gaiji gj =new Gaiji();
//                                        gj.setDianpuname(gb.getPerson().get(i).getName());
//                                        gj.setJishu(gb.getPerson().get(i).getStepCode());
//                                        gj.setId(gb.getPerson().get(i).getID()+"");
//                                        gj_arrayList.add(gj);
//                                    }
//
//                                    gj_myadapter = new GaijiMyAdapter(GaijiActivity.this,gj_arrayList);
//                                    gj_lt.setAdapter(gj_myadapter);
                                    // 给handler发消息更新UI，子线程不可以更新UI
//                                    Message message = new Message();
//                                    message.what = 0;
//                                    handler.sendMessage(message);

//                                    gj_lt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                        @Override
//                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                            Gaiji gj = gj_arrayList.get(i);
//                                            Log.e("setOnItemClickListener","Gaiji"+gj.getId());
//                                            Intent intent  = new Intent(GaijiActivity.this,GaijiMoreActivity.class);
//                                            intent.putExtra("name",gj.getDianpuname());
//                                            // intent.putExtra("dengji",gj.getJishu());
//                                            intent.putExtra("dp_id",gj.getId());
//                                            startActivity(intent);
//                                        }
//                                    });
//                                }



                            }else {
                                Toast.makeText(GaijiActivity.this,"暂无管理的店铺信息！",Toast.LENGTH_SHORT).show();
                            }
                    }
                });
    }
    private void init(final int start, final int end){
        OkGo.post(Api.dianpu)
                .tag(this)
                .params("laSalesmanID",logo_id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //Log.e("店铺",s);
                        Gson gson = new Gson();
                        GaijiActivityBean gb = gson.fromJson(s,GaijiActivityBean.class);
                        //Log.e("********",gb.getPerson().get(0).getID()+"");
                        if(gb.getState().equals("1")){
                            for (int i =start;i<end;i++){
                                Gaiji gj =new Gaiji();
                                gj.setDianpuname(gb.getPerson().get(i).getName());
                                gj.setJishu(gb.getPerson().get(i).getStepCode());
                                gj.setId(gb.getPerson().get(i).getID()+"");
                                gj_arrayList.add(gj);
                            }
//                            new Thread() {
//                                public void run() {
//                                    try {
//                                        Thread.sleep(2000);// 模拟获取数据时的耗时3s
//                                        // 给handler发消息更新UI，子线程不可以更新UI
//                                        Message message = new Message();
//                                        message.what = 1;
//                                        handler.sendMessage(message);
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                };
//                            }.start();


//                            gj_lt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                    Gaiji gj = gj_arrayList.get(i);
//                                    Log.e("setOnItemClickListener","Gaiji"+gj.getId());
//                                    Intent intent  = new Intent(GaijiActivity.this,GaijiMoreActivity.class);
//                                    intent.putExtra("name",gj.getDianpuname());
//                                    // intent.putExtra("dengji",gj.getJishu());
//                                    intent.putExtra("dp_id",gj.getId());
//                                    startActivity(intent);
//                                }
//                            });




                        }else {
                            Toast.makeText(GaijiActivity.this,"暂无管理的店铺信息！",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    /***
     * 先给类似combox的自定义控件加入数据信息
     */

    @BindView(R.id.ac_2)
    AutoCompleteTextView act2;
    private ArrayList<String> list_1;
    private ArrayAdapter<String> adapter_1;
    private void setListener() {
        act2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act2.showDropDown();//点击控件显示所有的选项
            }
        });
    }
    private void addxinxi(){
        setData();
        adapter_1 = new ArrayAdapter<String>(GaijiActivity.this, R.layout.item_ac, R.id.tv_1, list_1);
        act2.setAdapter(adapter_1);
    }
    private void setData() {//设置数据源，这里只用了单纯的文字；
        list_1 = new ArrayList<>();
        list_1.add("一级客户");
        list_1.add("二级客户");
        list_1.add("三级客户");
        list_1.add("四级客户");
        list_1.add("五级客户");

    }

    //点击listview的item事件



    /**
     * 点击查询按钮
     */
    @BindView(R.id.ac_1)
    AutoCompleteTextView act1;
    @BindView(R.id.search_ll3_chaxun)
    Button btn_chaxun;
    private void chaxun(){
        btn_chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (act1.getText().toString().equals("")&&act2.getText().toString().equals("")){
                  //  Toast.makeText(GaijiActivity.this,"请输入要查询的数据",Toast.LENGTH_SHORT).show();
                    init();
                }else{
                    String jishu = "";
                    if (act2.getText().toString().equals("一级客户")){
                        jishu = "1";
                    }else if (act2.getText().toString().equals("二级客户")){
                        jishu = "2";
                    }else if (act2.getText().toString().equals("三级客户")){
                        jishu = "3";
                    }else if (act2.getText().toString().equals("四级客户")){
                        jishu = "4";
                    }else if (act2.getText().toString().equals("五级客户")){
                        jishu = "5";
                    }
                    //直接显示搜索的订单信息
                    Log.e("搜索出来的店铺信息",""+logo_id+"****"+act1.getText().toString());
                    OkGo.post(Api.dp_chaxun)
                            .tag(this)
                            .params("laSalesmanID",logo_id)
                            .params("name",act1.getText().toString())
                            .params("stepCode",jishu)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    try {
                                        gj_arrayList.clear();
                                        JSONObject object = new JSONObject(s);
                                        String state = object.getString("state");
                                        if(state.equals("0")){
                                           Toast.makeText(GaijiActivity.this,"输入的店铺不属于你管理，或没有该等级的店铺",Toast.LENGTH_SHORT).show();
                                          //  gj_lt.removeFooterView(view_more);
                                        }else if(state.equals("1")){

                                            Gson gson = new Gson();
                                            GaijiActivityBean gb = gson.fromJson(s,GaijiActivityBean.class);
                                            for (int i =0;i<gb.getPerson().size();i++){
                                            Gaiji gj =new Gaiji();
                                            gj.setDianpuname(gb.getPerson().get(i).getName());
                                            gj.setJishu(gb.getPerson().get(i).getStepCode());
                                            gj.setId(gb.getPerson().get(i).getID()+"");
                                            gj_arrayList.add(gj);
                                        }
                                        //gj_lt.removeFooterView(view_more);
                                        gj_myadapter = new GaijiMyAdapter(GaijiActivity.this,gj_arrayList);
                                        gj_lt.setAdapter(gj_myadapter);

//                                        gj_lt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                            @Override
//                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                                Gaiji gj = gj_arrayList.get(i);
//                                                Log.e("setOnItemClickListener","Gaiji"+gj.getId());
//                                                Intent intent  = new Intent(GaijiActivity.this,GaijiMoreActivity.class);
//                                                intent.putExtra("name",gj.getDianpuname());
//                                                // intent.putExtra("dengji",gj.getJishu());
//                                                intent.putExtra("dp_id",gj.getId());
//                                                startActivity(intent);
//                                            }
//                                        });
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                  //  Log.e("********",s);

//                                    //Log.e("********",gb.getPerson().get(0).getID()+"");
//                                    if(gb.getState().equals("1")){
//                                        for (int i =0;i<gb.getPerson().size();i++){
//                                            Gaiji gj =new Gaiji();
//                                            gj.setDianpuname(gb.getPerson().get(i).getName());
//                                            gj.setJishu(gb.getPerson().get(i).getStepCode());
//                                            gj.setId(gb.getPerson().get(i).getID()+"");
//                                            gj_arrayList.add(gj);
//                                        }
//
//                                        gj_myadapter = new GaijiMyAdapter(GaijiActivity.this,gj_arrayList);
//                                        gj_lt.setAdapter(gj_myadapter);
//
//                                        gj_lt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                            @Override
//                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                                Gaiji gj = gj_arrayList.get(i);
//                                                Log.e("setOnItemClickListener","Gaiji"+gj.getId());
//                                                Intent intent  = new Intent(GaijiActivity.this,GaijiMoreActivity.class);
//                                                intent.putExtra("name",gj.getDianpuname());
//                                                // intent.putExtra("dengji",gj.getJishu());
//                                                intent.putExtra("dp_id",gj.getId());
//                                                startActivity(intent);
//                                            }
//                                        });
//
//                                    }else {
//                                        String[] empty = new String[]{""};
//                                        ArrayAdapter<String> emptyadapter = new ArrayAdapter<String>(GaijiActivity.this, R.layout.spinner_item2, empty);
//                                        gj_lt.setAdapter(emptyadapter);
//                                        Toast.makeText(GaijiActivity.this,"暂无搜索的店铺信息！",Toast.LENGTH_SHORT).show();
//                                    }

                                }
                            });
                }
            }
        });
    }

    //返回执行
    @Override
    protected void onResume() {
        super.onResume();
      //  Log.e("onResume","onResume");
        init();
    }

//    /***
//     * 下拉功能
//     */
//    private int totalCount;// 数据总条数
//    private View view_more;
//    private ProgressBar pb;
//    private TextView tvLoad;
//    private int lastVisibleIndex;
//    int xiala_coint = 20;
//
//    private Handler handler = new Handler() {
//
//        public void handleMessage(android.os.Message msg) {
//            switch (msg.what) {
//                case 0:
//                    gj_myadapter = new GaijiMyAdapter(GaijiActivity.this,gj_arrayList);
//                    gj_lt.setAdapter(gj_myadapter);
//
//                    // 添加底部加载布局
//                    gj_lt.addFooterView(view_more);
//                    // Log.e("mengdebin","mengdebin");
//                    // 设置监听
//                    setListeners();
//                    break;
//                case 1:
//                    gj_myadapter.notifyDataSetChanged();
//                    break;
//            }
//        };
//    };
//
//    @Override
//    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
////        Log.e("TAG", "lastVisibleIndex = " + lastVisibleIndex);
////        Log.e("TAG", "adapter.getCount() = " + ddMyAdapter.getCount());
//        // 滑到底部后自动加载，判断listView已经停止滚动并且最后可视的条目等于adapter的条目
//        // 注意这里在listView设置好adpter后，加了一个底部加载布局。
//        // 所以判断条件为：lastVisibleIndex == adapter.getCount()
//        if (scrollState == SCROLL_STATE_IDLE
//                && lastVisibleIndex == gj_myadapter.getCount()) {
//            /**
//             * 这里也要设置为可见，是因为当你真正从网络获取数据且获取失败的时候。
//             * 我在失败的方法里面，隐藏了底部的加载布局并提示用户加载失败。所以再次监听的时候需要
//             * 继续显示隐藏的控件。因为我模拟的获取数据，失败的情况这里不给出。实际中简单的加上几句代码就行了。
//             */
////            Log.e("TAG", "scrollState =" + scrollState);
//            pb.setVisibility(View.VISIBLE);
//            tvLoad.setVisibility(View.VISIBLE);
//            loadMoreData();// 加载更多数据
//        }
//    }
//
//    /**
//     * 监听listView的滑动
//     */
//    @Override
//    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
//        // 计算最后可见条目的索引
//        lastVisibleIndex = i + i1 - 1;
//        // 当adapter中的所有条目数已经和要加载的数据总条数相等时，则移除底部的View
//        if (i2 == totalCount + 1) {
//            // 移除底部的加载布局
//            gj_lt.removeFooterView(view_more);
//        }
//    }
//    int start,end;
//    private void loadMoreData() {
//        // 获取此时adapter中的总条目数
//        int count = gj_myadapter.getCount();
//        // 一次加载10条数据，即下拉加载的执行
//        if (count + xiala_coint < totalCount) {
//            start = count;
//            end = start + xiala_coint;
//            init(start, end);// 模拟网络获取数据操作
//        } else {// 数据不足15条直接加载到结束
//            start = count;
//            end = totalCount;
//            init(start, end);// 模拟网络获取数据操作
//
//        }
//
//    }
//    private void setListeners() {
//        if (totalCount > xiala_coint) {
//            // listView设置滑动简监听
//            gj_lt.setOnScrollListener(this);
//        } else {
//            // 假如数据总数少于等于10条，直接移除底部的加载布局，不需要再加载更多的数据
//            gj_lt.removeFooterView(view_more);
//        }
//    }
//
//    private void initViews() {
//        // 构建底部加载布局
//        view_more = (View) getLayoutInflater()
//                .inflate(R.layout.view_more, null);
//        // 进度条
//        pb = (ProgressBar) view_more.findViewById(R.id.progressBar);
//        // “正在加载...”文本控件
//        tvLoad = (TextView) view_more.findViewById(R.id.tv_Load);
//    }


}
