package go.app.mdb.android.go;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import go.app.mdb.android.go.bean.Fenlei;
import go.app.mdb.android.go.bean.Fenzu;
import go.app.mdb.android.go.bean.FirstFragmentBean;
import go.app.mdb.android.go.pandian.MyAdapter;
import go.app.mdb.android.go.pandian.PanDian;
import go.app.mdb.android.go.tools.Api;
import go.app.mdb.android.go.tools.MyListView;
import okhttp3.Call;
import okhttp3.Response;

public class FirstFragment extends Fragment  implements AbsListView.OnScrollListener{
    @Nullable
    MyAdapter myAdapter;
    //listview视图
    @BindView(R.id.fg1_lv1)
    ListView list;
    private ArrayList<PanDian> listenety=new ArrayList<>();//只会new一次  其他地方都不会new
   // Context context;
    @BindView(R.id.search_ll1)
    LinearLayout ll_1;
    @BindView(R.id.search_ll2)
    LinearLayout ll_2;
    Activity context;

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
            this.context=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fg1,container, false);
        ButterKnife.bind(this,view);//在这个方法内  不许在写其他的东西  因为只有返回后你才能拿到当前的视图  你要是在return之前你去写点击 会报错
        return view;
    }


    @BindView(R.id.pd_ll1)
    LinearLayout ll1;
    @BindView(R.id.pd_ll2)
    LinearLayout ll2;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //所有的逻辑在onViewCreated内写
        //这个view就是返回给你的  你的这个view可以执行findviewbyid
        super.onResume();
        initViews();
        init();
        sousuo();
        saomiao();
        ll_1.setVisibility(View.VISIBLE);
        ll_2.setVisibility(View.GONE);
        //combox点击事件和加载信息
        setListener();
        addxinxi();


    }

    @Override
    public void onPause() {
        Log.e("","");
        super.onPause();
        init();

    }

    /***
     * 加载网络数据
     * 定义一个下拉的值为10条数据
     */
    int xiala_coint = 10;

    private void init(){
//        int yeshu=0,tiaoshu=1;
//        yeshu = Integer.MAX_VALUE/tiaoshu;
//        Log.e("网络盘点数据yeshu",yeshu+"tiaoshuju"+tiaoshu);
        OkGo.post(Api.pandian)
                .tag(this)
                .params("pager",1)
                .params("pagerSize",Integer.MAX_VALUE)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                      //  Log.e("网络盘点数据",s);
                            listenety.clear();
                            Gson gson = new Gson();
                            FirstFragmentBean ffb = gson.fromJson(s,FirstFragmentBean.class);

                            String state = ffb.getState();
                            if(state.equals("1")){
                                ll2.setVisibility(View.GONE);
                                ll1.setVisibility(View.VISIBLE);
                                totalCount = ffb.getCount();
                                if (totalCount<xiala_coint){
                                    Log.e("数量",totalCount+"");
                                    for (int i = 0; i < totalCount; i++) {
                                        //Log.e("网络盘点",ffb.getPerson().get(i).getRemark());
                                        PanDian panDian=new PanDian();
                                        panDian.setName(ffb.getPerson().get(i).getName());
                                        panDian.setFenlei(ffb.getPerson().get(i).getClassID()+"");
                                        panDian.setPinpai(ffb.getPerson().get(i).getBrand());
                                        panDian.setGuige(ffb.getPerson().get(i).getSpecification());
                                        panDian.setKucun(ffb.getPerson().get(i).getStockQuantity()+"");
                                        panDian.setZhujima(ffb.getPerson().get(i).getShortCode());
                                        panDian.setTiaoxingma(ffb.getPerson().get(i).getBarCode());
                                        panDian.setId(ffb.getPerson().get(i).getID()+"");
                                        panDian.setBeizhu(ffb.getPerson().get(i).getRemark());
                                        listenety.add(panDian);
                                    }
                                    myAdapter = new MyAdapter(context,listenety,onClickListener);
                                    list.setAdapter(myAdapter);
                                }else{
                                    for (int i = 0; i < xiala_coint; i++) {
                                        //Log.e("网络盘点",ffb.getPerson().get(i).getRemark());
                                        PanDian panDian=new PanDian();
                                        panDian.setName(ffb.getPerson().get(i).getName());
                                        panDian.setFenlei(ffb.getPerson().get(i).getClassID()+"");
                                        panDian.setPinpai(ffb.getPerson().get(i).getBrand());
                                        panDian.setGuige(ffb.getPerson().get(i).getSpecification());
                                        panDian.setKucun(ffb.getPerson().get(i).getStockQuantity()+"");
                                        panDian.setZhujima(ffb.getPerson().get(i).getShortCode());
                                        panDian.setTiaoxingma(ffb.getPerson().get(i).getBarCode());
                                        panDian.setId(ffb.getPerson().get(i).getID()+"");
                                        panDian.setBeizhu(ffb.getPerson().get(i).getRemark());
                                        listenety.add(panDian);
                                    }
                                    myAdapter = new MyAdapter(context,listenety,onClickListener);
                                    list.setAdapter(myAdapter);
                                    // 给handler发消息更新UI，子线程不可以更新UI
                                    Message message = new Message();
                                    message.what = 0;
                                    handler.sendMessage(message);
                                }


                            }else{
                                //没有信息隐藏ll1控件，显示ll2控件的图
                                Toast.makeText(context,"暂无数据",Toast.LENGTH_SHORT).show();
                            }




                    }
                });
}

    private void init(final int start, final int end){
//        int yeshu=0,tiaoshu=1;
//        yeshu = Integer.MAX_VALUE/tiaoshu;
//        Log.e("网络盘点数据yeshu",yeshu+"tiaoshuju"+tiaoshu);
        OkGo.post(Api.pandian)
                .tag(this)
                .params("pager",1)
                .params("pagerSize",Integer.MAX_VALUE)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //  Log.e("网络盘点数据",s);
                        Gson gson = new Gson();
                        FirstFragmentBean ffb = gson.fromJson(s,FirstFragmentBean.class);

                        String state = ffb.getState();
                        if(state.equals("1")){
                            ll2.setVisibility(View.GONE);
                            ll1.setVisibility(View.VISIBLE);
                            totalCount = ffb.getCount();
                           // Log.e("数量",totalCount+"");
                            for (int i = start; i < end; i++) {
                                //Log.e("网络盘点",ffb.getPerson().get(i).getRemark());
                                PanDian panDian=new PanDian();
                                panDian.setName(ffb.getPerson().get(i).getName());
                                panDian.setFenlei(ffb.getPerson().get(i).getClassID()+"");
                                panDian.setPinpai(ffb.getPerson().get(i).getBrand());
                                panDian.setGuige(ffb.getPerson().get(i).getSpecification());
                                panDian.setKucun(ffb.getPerson().get(i).getStockQuantity()+"");
                                panDian.setZhujima(ffb.getPerson().get(i).getShortCode());
                                panDian.setTiaoxingma(ffb.getPerson().get(i).getBarCode());
                                panDian.setId(ffb.getPerson().get(i).getID()+"");
                                panDian.setBeizhu(ffb.getPerson().get(i).getRemark());
                                listenety.add(panDian);
                            }
//                            new Thread() {
//                                public void run() {
//                                    try {
//
//                                        myAdapter.notifyDataSetChanged();
//
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
                            myAdapter.notifyDataSetChanged();


                        }else{
                            //没有信息隐藏ll1控件，显示ll2控件的图
                            Toast.makeText(context,"暂无数据",Toast.LENGTH_SHORT).show();
                        }




                    }
                });
    }


    //获取备注LinearLayout控件
    LinearLayout linearLayout_beizhu;

    /**
     * 修改按钮点击事件
     */
    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            Button btn = (Button) view;
            int pos = (Integer) btn.getTag();
       //     Toast.makeText(context,"点击的是第"+pos,Toast.LENGTH_SHORT).show();
        }
    };


    /***
     * 先给类似combox的自定义控件加入数据信息
     */
    @BindView(R.id.ac_1)
    AutoCompleteTextView act1;
    @BindView(R.id.ac_2)
    AutoCompleteTextView act2;
    @BindView(R.id.ac_3)
    AutoCompleteTextView act3;
    @BindView(R.id.ac_4)
    AutoCompleteTextView act4;

    private ArrayList<String> list_1,list_2,list_3,list_4 ;
    private ArrayAdapter<String> adapter_1,adapter_2,adapter_3,adapter_4 ;

    private void setListener(){
        act1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act1.showDropDown();//点击控件显示所有的选项
            }
        });
        act2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act2.showDropDown();//点击控件显示所有的选项
            }
        });
        act3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act3.showDropDown();//点击控件显示所有的选项
            }
        });
        act4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act4.showDropDown();//点击控件显示所有的选项
            }
        });

    }
    private void addxinxi(){
        setData();
//        adapter_1 = new ArrayAdapter<String>(context, R.layout.item_ac, R.id.tv_1, list_1);
//        act1.setAdapter(adapter_1);
//        adapter_2 = new ArrayAdapter<String>(context, R.layout.item_ac, R.id.tv_1, list_2);
//        act2.setAdapter(adapter_2);
        adapter_3 = new ArrayAdapter<String>(context, R.layout.item_ac, R.id.tv_1, list_3);
        act3.setAdapter(adapter_3);
        adapter_4 = new ArrayAdapter<String>(context,  R.layout.item_ac, R.id.tv_1, list_4);
        act4.setAdapter(adapter_4);

    }

    String[] name5,name5_id,name6,name6_id;
    private void setData() {//设置数据源，这里只用了单纯的文字；
       // list_1 =new ArrayList<>();


//        list_2 =new ArrayList<>();
//        list_2 .add("111155");
//        list_2 .add("222666");

        list_3 =new ArrayList<>();
        OkGo.post(Api.pinpai)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                        //    Log.e("品牌",s);
                            String state = jsonObject.getString("state");
                            if(state.equals("1")){
                                JSONArray jsonArray = jsonObject.getJSONArray("person");
                                for (int i = 0; i < jsonArray.length() ; i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);

                                    list_3.add(obj.getString("Brand"));
                                }
                            }else{
                                // Toast.makeText(context,"获取网络数据失败，请稍后重试",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        list_4 =new ArrayList<>();
        OkGo.post(Api.guige)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                          //  Log.e("规格",s);
                            String state = jsonObject.getString("state");
                            if(state.equals("1")){
                                JSONArray jsonArray = jsonObject.getJSONArray("person");
                                for (int i = 0; i < jsonArray.length() ; i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);

                                    list_4.add(obj.getString("Specification"));
                                }
                            }else{
                                // Toast.makeText(context,"获取网络数据失败，请稍后重试",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        OkGo.post(Api.fenzhu)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        Gson gson = new Gson();
                        Fenzu fz = gson.fromJson(s,Fenzu.class);
                        if (fz.getState().equals("1")){
                            name5 = new String[fz.getPerson().size()+1];
                            name5_id = new String[fz.getPerson().size()+1] ;
                            name5[0] = "请选择";
                            name5_id[0] = "a" ;
                            for (int i = 0; i < fz.getPerson().size() ; i++){
                                name5[i+1] = fz.getPerson().get(i).getName();
                                name5_id[i+1] = fz.getPerson().get(i).getID()+"";
                                // Log.e("name5[]",name5[i]);
                            }

                            addinfo_spinner();
                        }else{
                            Toast.makeText(context,"获取网络数据失败，请稍后重试",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    //给显示人名的spinner控件动态绑定数据
    private ArrayAdapter<String> stringArrayAdapter,stringArrayAdapter2;
    @BindView(R.id.spinner_5)
    Spinner spinner5;
    @BindView(R.id.spinner_6)
    Spinner spinner6;

    private void addinfo_spinner(){
        //  Log.e("addinfo_spinner","name5="+name5.length);
        stringArrayAdapter = new ArrayAdapter<String>(context,R.layout.spinner_item2,name5);
        stringArrayAdapter.setDropDownViewResource(R.layout.spinner_zidingyi);
        spinner5.setAdapter(stringArrayAdapter);
        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //当spinner5为“请选择”时，后面的spinner6清空数据
                if (name5_id[i].equals("a")){
                    String[] empty = new String[]{""};
                    ArrayAdapter<String> emptyadapter = new ArrayAdapter<String>(context, R.layout.spinner_item2, empty);
                    spinner6.setAdapter(emptyadapter);
                    name6_id = new String[1];
                    name6_id[0] = "";
                }else {
                    OkGo.post(Api.fenlei)
                            .tag(this)
                            .params("groupID", name5_id[i])
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    //Log.e("++++++++++++",s);
                                    Gson gson = new Gson();
                                    Fenlei fl = gson.fromJson(s, Fenlei.class);
                                    if (fl.getState().equals("1")) {
                                        name6 = new String[fl.getPerson().size()];
                                        name6_id = new String[fl.getPerson().size()];
                                        for (int j = 0; j < fl.getPerson().size(); j++) {
                                            name6[j] = fl.getPerson().get(j).getName();
                                            name6_id[j] = fl.getPerson().get(j).getID() + "";
                                        }
                                        addinfo_spinner2();
                                    } else {
                                        Toast.makeText(context, "此分类暂无分组数据！", Toast.LENGTH_SHORT).show();
                                        String[] empty = new String[0];
                                        ArrayAdapter<String> emptyadapter = new ArrayAdapter<String>(context, R.layout.spinner_item2, empty);
                                        spinner6.setAdapter(emptyadapter);
                                        name6_id = new String[1];
                                        name6_id[0] = "";
                                    }


                                }
                            });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    String fenzhu_id = "";
    //分组显示数据
    private void addinfo_spinner2(){
        stringArrayAdapter2 = new ArrayAdapter<String>(context,R.layout.spinner_item2,name6);
        stringArrayAdapter2.setDropDownViewResource(R.layout.spinner_zidingyi);
        spinner6.setAdapter(stringArrayAdapter2);
        spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fenzhu_id = name6_id[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



    /****
     * 点击搜索
     */
    @BindView(R.id.search_chaxun)
    Button btn_chaxun;

    private void sousuo() {
        btn_chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (act1.getText().toString().equals("")&&act2.getText().toString().equals("")&&act3.getText().toString().equals("")&&
                    act4.getText().toString().equals("")&&spinner5.getSelectedItem().toString().equals("请选择")&&spinner6.getSelectedItem().toString().equals("")){
                       //不写信息查询所有
                        init();

                }else {
                    Log.e("fenzhu_id",fenzhu_id);

                    OkGo.post(Api.pd_chaxun)
                            .tag(this)
                            .params("name",act1.getText().toString())
                            .params("shortCode",act2.getText().toString())
                            .params("brand",act3.getText().toString())
                            .params("specification",act4.getText().toString())
                            .params("classID",fenzhu_id)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                   // Log.e("盘点",s);
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
                                        myAdapter = new MyAdapter(context,listenety,onClickListener);
                                        list.setAdapter(myAdapter);
                                        list.removeFooterView(view_more);
                                    }else{
                                            listenety.clear();
                                            String[] empty = new String[]{""};
                                            ArrayAdapter<String> emptyadapter = new ArrayAdapter<String>(context, R.layout.spinner_item2, empty);
                                            list.setAdapter(emptyadapter);
                                            list.removeFooterView(view_more);
                                            Toast.makeText(context,"暂无查询数据",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
//                    //有值跳转页面
//                    Intent intent = new Intent(context,Pd_searchActivity.class);
//                    intent.putExtra("id","0");
//                    startActivity(intent);
                }
            }
        });
    }
    /***
     * 扫描功能
     */
    @BindView(R.id.search_saomiao)
    Button btn_saomiao;
    private void saomiao() {
        btn_saomiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //转页面
                Intent intent = new Intent(context,Pd_searchActivity.class);
                intent.putExtra("id","1");
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

    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    myAdapter = new MyAdapter(context,listenety,onClickListener);
                    list.setAdapter(myAdapter);
                    // 添加底部加载布局
                    list.addFooterView(view_more);
                    // 设置监听
                    setListeners();
                    break;
                case 1:
                    myAdapter.notifyDataSetChanged();
                    break;
            }
        };
    };

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        // 滑到底部后自动加载，判断listView已经停止滚动并且最后可视的条目等于adapter的条目
        // 注意这里在listView设置好adpter后，加了一个底部加载布局。
        // 所以判断条件为：lastVisibleIndex == adapter.getCount()
        if (scrollState == SCROLL_STATE_IDLE
                && lastVisibleIndex == myAdapter.getCount()) {
            /**
             * 这里也要设置为可见，是因为当你真正从网络获取数据且获取失败的时候。
             * 我在失败的方法里面，隐藏了底部的加载布局并提示用户加载失败。所以再次监听的时候需要
             * 继续显示隐藏的控件。因为我模拟的获取数据，失败的情况这里不给出。实际中简单的加上几句代码就行了。
             */
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
        int count = myAdapter.getCount();
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
            list.setOnScrollListener(FirstFragment.this);
        } else {
            // 假如数据总数少于等于15条，直接移除底部的加载布局，不需要再加载更多的数据
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
