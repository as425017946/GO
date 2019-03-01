package go.app.mdb.android.go.dingdan;

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

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import go.app.mdb.android.go.R;
import go.app.mdb.android.go.bean.Chuangjian2_hzdwBean;
import go.app.mdb.android.go.bean.Hezuodanwei;
import go.app.mdb.android.go.tools.Api;
import okhttp3.Call;
import okhttp3.Response;

import static go.app.mdb.android.go.LogoActivity.logo_id;

public class ChuanjianActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuanjian2);
        ButterKnife.bind(this);
        fanhui();
        hezuoxinxi();
        chaxun();
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
                ChuanjianActivity2.this.finish();
            }
        });
    }

    @BindView(R.id.spinner_hzdw)
    AutoCompleteTextView act_hzdw;
    String[] hzdw_name,hzdw_id;
    private ArrayList<String> list_2;
    private ArrayAdapter<String> adapter_2;
    private void hezuoxinxi(){
        act_hzdw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_hzdw.showDropDown();//点击控件显示所有的选项
            }
        });

        list_2 =new ArrayList<>();
        OkGo.post(Api.hezuodanwei)
                .tag(this)
                .params("laSalesmanID",logo_id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
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
        adapter_2 = new ArrayAdapter<String>(ChuanjianActivity2.this,  R.layout.item_ac, R.id.tv_1, list_2);
        act_hzdw.setAdapter(adapter_2);

    }
    /**
     * 查询
     * */
    @BindView(R.id.search_ll2_chaxun)
    Button btn_chaxun;
    @BindView(R.id.list_hzdw)
    ListView list_hzdw;
    HezdwAdapter hzadaper;
    ArrayList<hzdw_dd> arrayList = new ArrayList<hzdw_dd>();
    public static String fenjiid,hezuodanweiname,hezuodanweiid; //保存分级id静态变量和合作单位名称和单位id
    private void chaxun(){
        btn_chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (act_hzdw.getText().toString().equals("")){
                       //Toast.makeText(ChuanjianActivity2.this,"暂无查询数据",Toast.LENGTH_SHORT).show();
                    }
                    OkGo.post(Api.mohu_gongsi)
                            .tag(this)
                            .params("laSalesmanID",logo_id)
                            .params("name",act_hzdw.getText().toString())
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    arrayList.clear();
                                    Gson gson = new Gson();
                                    Chuangjian2_hzdwBean cj2 = gson.fromJson(s,Chuangjian2_hzdwBean.class);
                              //      Log.e("7894",cj2.getPerson().get(0).getName());
                                    Log.e("单位名",s);
                                    if(cj2.getState().equals("1")){
                                        for (int i = 0; i <cj2.getPerson().size() ; i++) {
                                            hzdw_dd hz = new hzdw_dd();
                                            hz.setId(cj2.getPerson().get(i).getID()+"");
                                            hz.setName(cj2.getPerson().get(i).getName());
                                            hz.setDizhi(cj2.getPerson().get(i).getAddress());
                                            hz.setPriceid(cj2.getPerson().get(i).getStepCode()+"");
                                            arrayList.add(hz);
                                        }
                                        hzadaper = new HezdwAdapter(ChuanjianActivity2.this,arrayList);
                                        list_hzdw.setAdapter(hzadaper);
                                    }else {
                                        Toast.makeText(ChuanjianActivity2.this,"查询的商家不存在！",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
            }
        });
        list_hzdw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                hzdw_dd hz= arrayList.get(i);
//                Toast.makeText(ChuanjianActivity2.this,"id"+hz.getId(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChuanjianActivity2.this,TiaoxuanActivity.class);
              //  intent.putExtra("id",hz.getId());
                fenjiid = hz.getPriceid();
                hezuodanweiname = hz.getName();
                hezuodanweiid = hz.getId();
                startActivity(intent);
            }
        });

    }


}
