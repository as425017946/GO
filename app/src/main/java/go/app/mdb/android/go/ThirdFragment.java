package go.app.mdb.android.go;

import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import go.app.mdb.android.go.bean.Fenlei;
import go.app.mdb.android.go.bean.Fenzu;
import go.app.mdb.android.go.bean.Guige;
import go.app.mdb.android.go.bean.Pinpai;
import go.app.mdb.android.go.bean.ThirdFragmentBean;
import go.app.mdb.android.go.dingdan.DdMyAdapter;
import go.app.mdb.android.go.kucun.Kucun;
import go.app.mdb.android.go.kucun.KuncunMyAdapater;
import go.app.mdb.android.go.tools.Api;
import okhttp3.Call;
import okhttp3.Response;

public class ThirdFragment extends Fragment implements AbsListView.OnScrollListener  {
    @Nullable
    Context context;
    KuncunMyAdapater kc_myadapter;
    ArrayList<Kucun> kc_array= new ArrayList<>();
    @BindView(R.id.search_ll1)
    LinearLayout ll_1;
    @BindView(R.id.search_ll2)
    LinearLayout ll_2;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context =context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fg3,container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ll_1.setVisibility(View.GONE);
        ll_2.setVisibility(View.VISIBLE);
        //combox点击事件和加载信息
        initViews();
        setListener();
        addxinxi();
        sousuo();
        onclick_spinner();

    }

    String dengji;
    //分级点击事件
    @BindView(R.id.kc_sp1)
    Spinner spinner;

    private void onclick_spinner(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               // Log.e("分级客户",""+i);
                init(i+1);
                dengji = (i+1) + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


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
    @BindView(R.id.spinner_5)
    Spinner spinner5;
    @BindView(R.id.spinner_6)
    Spinner spinner6;

    private ArrayList<String> list_1,list_2,list_3,list_4 ;//就一个string  你想干嘛  我给你录个视频看一下吧就是实现这个的
    //我跟你说啊  你这个集合里面只有一个string  肯定拿不到id的  很负责的告诉你  你这个实现不了  除非改

    private ArrayAdapter<String> adapter_1,adapter_2,adapter_3,adapter_4,stringArrayAdapter,stringArrayAdapter2;

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
        adapter_1 = new ArrayAdapter<String>(context, R.layout.item_ac, R.id.tv_1, list_1);
        act1.setAdapter(adapter_1);
        adapter_2 = new ArrayAdapter<String>(context, R.layout.item_ac, R.id.tv_1, list_2);
        act2.setAdapter(adapter_2);
        adapter_3 = new ArrayAdapter<String>(context, R.layout.item_ac, R.id.tv_1, list_3);
        act3.setAdapter(adapter_3);
        adapter_4 = new ArrayAdapter<String>(context,  R.layout.item_ac, R.id.tv_1, list_4);
        act4.setAdapter(adapter_4);

    }

    String[] name5,name5_id,name6,name6_id;
    private void setData() {//设置数据源，这里只用了单纯的文字；
        list_1 =new ArrayList<>();

        list_2 =new ArrayList<>();

        list_3 =new ArrayList<>();
        OkGo.post(Api.pinpai)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        Pinpai pp = gson.fromJson(s,Pinpai.class);
                        if(pp.getState().equals("1")){
                            for (int i = 0; i <pp.getPerson().size() ; i++) {
                                list_3.add(pp.getPerson().get(i).getBrand());
                            }

                        }else{
                            Toast.makeText(context,"获取网络数据失败，请稍后重试",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        list_4 =new ArrayList<>();
        OkGo.post(Api.guige)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        Guige gg = gson.fromJson(s,Guige.class);
                        if (gg.getState().equals("1")){
                            for (int i = 0; i <gg.getPerson().size() ; i++) {
                                list_4.add(gg.getPerson().get(i).getSpecification());
                            }
                        }else {
                            Toast.makeText(context,"获取网络数据失败，请稍后重试",Toast.LENGTH_SHORT).show();
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

    //分组显示数据
    String fenzhu_id = "";
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
    @BindView(R.id.search_ll2_chaxun)
    Button btn_chaxun;

    private void sousuo() {
        btn_chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //&&spinner6.getSelectedItem().toString().equals("")
//                Log.e("**-****",spinner6.getSelectedItem().toString());
                if (act1.getText().toString().equals("")&&act2.getText().toString().equals("")&&act3.getText().toString().equals("")&&
                        act4.getText().toString().equals("")&&spinner5.getSelectedItem().toString().equals("请选择")&&spinner6.getSelectedItem().toString().equals("")){
                    //Toast.makeText(context,"请输入要查询的数据",Toast.LENGTH_SHORT).show();
                    //用户不输入值,我直接返回所有信息
                    init(1);
                }
                else {
                        //直接显示搜索的订单信息
                        OkGo.post(Api.kc_chaxun)
                                .tag(this)
                                .params("state", dengji)
                                .params("name", act1.getText().toString())
                                .params("shortCode", act2.getText().toString())
                                .params("brand", act3.getText().toString())
                                .params("specification", act4.getText().toString())
                                .params("classID", fenzhu_id)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
                                        Log.e("**************", s);
                                        kc_array.clear();
                                        Gson gson = new Gson();
                                        ThirdFragmentBean tf_bean = gson.fromJson(s,ThirdFragmentBean.class);
                                        String state =tf_bean.getState();
                                        if (state.equals("1")){

                                            for (int i = 0; i < tf_bean.getPerson().size() ; i++) {
                                                Kucun kc = new Kucun();
                                                kc.setKucunnum(tf_bean.getPerson().get(i).getStockQuantity()+"");
                                                kc.setRukutime(tf_bean.getPerson().get(i).getCreatTime());
                                                kc.setPrice(tf_bean.getPerson().get(i).getStepPrice()+"");
                                                kc.setName(tf_bean.getPerson().get(i).getName());
                                                String youwu = tf_bean.getPerson().get(i).getAsStockQuantity();
                                                if (youwu.equals("true")){
                                                    youwu = "有存货" ;
                                                }else if (youwu.equals("false")){
                                                    youwu = "暂无存货";
                                                }
                                                kc.setYouwu(youwu);
                                                kc_array.add(kc);
                                            }
                                            kc_myadapter = new KuncunMyAdapater(context,kc_array);
                                            listView.setAdapter(kc_myadapter);
                                            listView.removeFooterView(view_more);
                                        }else{
                                            kc_array.clear();
                                            String[] empty = new String[]{""};
                                            ArrayAdapter<String> emptyadapter = new ArrayAdapter<String>(context, R.layout.spinner_item2, empty);
                                            listView.setAdapter(emptyadapter);
                                            Toast.makeText(context,"暂无查询数据！",Toast.LENGTH_SHORT).show();
                                            listView.removeFooterView(view_more);
                                        }

                                    }
                                });

                }

            }
        });
    }

    /**
     * 添加网络数据
     */
    @BindView(R.id.kc_ll1)
    LinearLayout ll1;
    @BindView(R.id.kc_ll2)
    LinearLayout ll2;
    @BindView(R.id.fg3_lt)
    ListView listView;
    private void init(int zhi) {
        OkGo.post(Api.kucun)
                .tag(this)
                .params("state",zhi)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                       // Log.e("库存",s);
                        kc_array.clear();
                        Gson gson = new Gson();
                        ThirdFragmentBean tf_bean = gson.fromJson(s,ThirdFragmentBean.class);
                        String state =tf_bean.getState();
                        if (state.equals("1")){
                            totalCount = tf_bean.getCount();
//                            if(xiala_coint>totalCount){
                                for (int i = 0; i < totalCount ; i++) {
                                    Kucun kc = new Kucun();
                                    kc.setKucunnum(tf_bean.getPerson().get(i).getStockQuantity()+"");
                                    kc.setRukutime(tf_bean.getPerson().get(i).getCreatTime());
                                    kc.setPrice(tf_bean.getPerson().get(i).getStepPrice()+"");
                                    kc.setName(tf_bean.getPerson().get(i).getName());
                                    String youwu = tf_bean.getPerson().get(i).getAsStockQuantity();
                                    if (youwu.equals("true")){
                                        youwu = "有存货" ;
                                    }else if (youwu.equals("false")){
                                        youwu = "暂无存货";
                                    }
                                    kc.setYouwu(youwu);
                                    kc_array.add(kc);
                                }
                                kc_myadapter = new KuncunMyAdapater(context,kc_array);
                                listView.setAdapter(kc_myadapter);
//                            }else {
//                                for (int i = 0; i < xiala_coint ; i++) {
//                                    Kucun kc = new Kucun();
//                                    kc.setKucunnum(tf_bean.getPerson().get(i).getStockQuantity()+"");
//                                    kc.setRukutime(tf_bean.getPerson().get(i).getCreatTime());
//                                    kc.setPrice(tf_bean.getPerson().get(i).getStepPrice()+"");
//                                    kc.setName(tf_bean.getPerson().get(i).getName());
//                                    String youwu = tf_bean.getPerson().get(i).getAsStockQuantity();
//                                    if (youwu.equals("true")){
//                                        youwu = "有存货" ;
//                                    }else if (youwu.equals("false")){
//                                        youwu = "暂无存货";
//                                    }
//                                    kc.setYouwu(youwu);
//                                    kc_array.add(kc);
//                                }
//                                kc_myadapter = new KuncunMyAdapater(context,kc_array);
//                                listView.setAdapter(kc_myadapter);
//                                // 给handler发消息更新UI，子线程不可以更新UI
//                                Message message = new Message();
//                                message.what = 0;
//                                handler.sendMessage(message);
//                            }

                        }else{
                            Toast.makeText(context,"暂无查询数据！",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void init(int zhi,final int start, final int end) {
        OkGo.post(Api.kucun)
                .tag(this)
                .params("state",zhi)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        // Log.e("库存",s);
                        Gson gson = new Gson();
                        ThirdFragmentBean tf_bean = gson.fromJson(s,ThirdFragmentBean.class);
                        String state =tf_bean.getState();
                        if (state.equals("1")){
                            for (int i = start; i < end ; i++) {
                                Kucun kc = new Kucun();
                                kc.setKucunnum(tf_bean.getPerson().get(i).getStockQuantity()+"");
                                kc.setRukutime(tf_bean.getPerson().get(i).getCreatTime());
                                kc.setPrice(tf_bean.getPerson().get(i).getStepPrice()+"");
                                kc.setName(tf_bean.getPerson().get(i).getName());
                                String youwu = tf_bean.getPerson().get(i).getAsStockQuantity();
                                if (youwu.equals("true")){
                                    youwu = "有存货" ;
                                }else if (youwu.equals("false")){
                                    youwu = "暂无存货";
                                }
                                kc.setYouwu(youwu);
                                kc_array.add(kc);
                            }
                            kc_myadapter.notifyDataSetChanged();
//                            new Thread() {
//                                public void run() {
//                                    try {
//                                        Thread.sleep(4000);// 模拟获取数据时的耗时3s
//                                        // 给handler发消息更新UI，子线程不可以更新UI
//                                        Message message = new Message();
//                                        message.what = 1;
//                                        handler.sendMessage(message);
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                };
//                            }.start();
                        }else{
                            Toast.makeText(context,"暂无查询数据！",Toast.LENGTH_SHORT).show();
                        }
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
                    kc_myadapter = new KuncunMyAdapater(context,kc_array);
                    listView.setAdapter(kc_myadapter);
                    // 添加底部加载布局
                    listView.addFooterView(view_more);
                   // Log.e("mengdebin","mengdebin");
                    // 设置监听
                    setListeners();
                    break;
                case 1:
                    kc_myadapter.notifyDataSetChanged();
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
                && lastVisibleIndex == kc_myadapter.getCount()) {
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
            listView.removeFooterView(view_more);
        }
    }
    int start,end;
    private void loadMoreData() {
        // 获取此时adapter中的总条目数
        int count = kc_myadapter.getCount();
        // 一次加载10条数据，即下拉加载的执行
        if (count + 10 < totalCount) {
            start = count;
            end = start + 10;
            init(1,start, end);// 模拟网络获取数据操作
        } else {// 数据不足15条直接加载到结束
            start = count;
            end = totalCount;
            init(1,start, end);// 模拟网络获取数据操作

        }

    }
    private void setListeners() {
        if (totalCount > 10) {
            // listView设置滑动简监听
            listView.setOnScrollListener(ThirdFragment.this);
        } else {
            // 假如数据总数少于等于10条，直接移除底部的加载布局，不需要再加载更多的数据
            listView.removeFooterView(view_more);
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
