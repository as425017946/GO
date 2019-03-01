package go.app.mdb.android.go.dingdan;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
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

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import go.app.mdb.android.go.R;
import go.app.mdb.android.go.bean.FirstFragmentBean;
import go.app.mdb.android.go.pandian.MyAdapter;
import go.app.mdb.android.go.pandian.PanDian;
import go.app.mdb.android.go.tools.Api;
import okhttp3.Call;
import okhttp3.Response;

public class TiaoxuanActivity extends AppCompatActivity {

    String hz_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiaoxuan);
        ButterKnife.bind(this);
        init();
        fanhui();
        chaxun();
    }

    //返回按钮和修改标题
    @BindView(R.id.fenye_fanhui)
    LinearLayout ll_fanhui;
    @BindView(R.id.fenye_title)
    TextView tv_title;
    private void fanhui(){
        //修改标题
        tv_title.setText("挑选商品");
        //点击返回按钮
        ll_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TiaoxuanActivity.this.finish();
            }
        });
    }


    @BindView(R.id.ac_3)
    AutoCompleteTextView act3;
    private ArrayList<String>list_3 ;
    private ArrayAdapter<String> adapter_3;
    private void init(){
        act3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act3.showDropDown();//点击控件显示所有的选项
            }
        });
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
        adapter_3 = new ArrayAdapter<String>(TiaoxuanActivity.this, R.layout.item_ac, R.id.tv_1, list_3);
        act3.setAdapter(adapter_3);
    }
    /**
     * 查询
     */
    private ArrayList<PanDian> listenety=new ArrayList<>();//只会new一次  其他地方都不会new
    TiaoxuanAdapter myAdapter;
    @BindView(R.id.list_hzdw)
    ListView list;
    @BindView(R.id.tiaoxuan_chaxun)
    Button btn_chaxun;
    @BindView(R.id.ac_goods_tiaoxuan)
    AutoCompleteTextView ac_tx_goods;
    public static String goodsids;//静态保存商品id
    private void chaxun(){
        btn_chaxun.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(act3.getText().toString().equals("")&&ac_tx_goods.getText().toString().equals("")){
                    
                    Toast.makeText(TiaoxuanActivity.this,"请输入要查询的商品",Toast.LENGTH_SHORT).show();
                }else {
                    OkGo.post(Api.pd_chaxun)
                            .tag(this)
                            .params("brand",act3.getText().toString())
                            .params("name",ac_tx_goods.getText().toString())
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    Log.e("订单商品详情",s);
                                    listenety.clear();
                                    Gson gson = new Gson();
                                    FirstFragmentBean ffb = gson.fromJson(s,FirstFragmentBean.class);
                                    String state = ffb.getState();
                                    if(state.equals("1")){
                                        for (int i = 0; i < ffb.getPerson().size(); i++) {
                                            PanDian panDian=new PanDian();
                                            panDian.setName(ffb.getPerson().get(i).getName());
                                            panDian.setKucun(ffb.getPerson().get(i).getStockQuantity()+"");
                                            panDian.setGuige(ffb.getPerson().get(i).getSpecification());
                                            panDian.setId(ffb.getPerson().get(i).getID()+"");
                                            listenety.add(panDian);
                                        }
                                        myAdapter = new TiaoxuanAdapter(TiaoxuanActivity.this,listenety);
                                        list.setAdapter(myAdapter);
                                    }else{
                                        listenety.clear();
                                        String[] empty = new String[]{""};
                                        ArrayAdapter<String> emptyadapter = new ArrayAdapter<String>(TiaoxuanActivity.this, R.layout.spinner_item2, empty);
                                        list.setAdapter(emptyadapter);
                                        Toast.makeText(TiaoxuanActivity.this,"暂无查询数据",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PanDian pd = listenety.get(i);
                ac_tx_goods.setText("");
                Intent intent = new Intent(TiaoxuanActivity.this,TiaoxuanMoreActivity.class);
                goodsids = pd.getId();
                startActivity(intent);
             //   Toast.makeText(TiaoxuanActivity.this,"id为"+pd.getId(),Toast.LENGTH_SHORT).show();
            }
        });
    }


}
