package go.app.mdb.android.go.wode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import go.app.mdb.android.go.R;
import go.app.mdb.android.go.tools.Api;
import okhttp3.Call;
import okhttp3.Response;

import static go.app.mdb.android.go.LogoActivity.logo_id;

public class GaijiMoreActivity extends AppCompatActivity {

    @BindView(R.id.gjmore_name)
    TextView tv_name;
    @BindView(R.id.gjmore_sp1)
    Spinner spinner;
    @BindView(R.id.gjmore_xiugai)
    Button btn_xiugai;

    int jishu;
    String name;
    String dengji;
    String dp_id;
    String sp_dengji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaiji_more);
        ButterKnife.bind(this);


        Bundle bd = getIntent().getExtras();
        name  = bd.getString("name");
        dp_id = bd.getString("dp_id");
        Log.e("***************",dp_id+"+++++"+name);
//        if (dengji.equals("一级客户")){
//            jishu = 0;
//        }else if (dengji.equals("二级客户")){
//            jishu = 1;
//        }else if (dengji.equals("三级客户")){
//            jishu = 2;
//        }else if (dengji.equals("四级客户")){
//            jishu = 3;
//        }else if (dengji.equals("五级客户")){
//            jishu = 4;
//        }

         tv_name.setText(name);
       // spinner.setSelection(jishu);
//        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                sp_dengji = spinner.getSelectedItem().toString();
//            }
//        });
        dianji();
        fanhui();
        title();
    }
    private  void  dianji(){

        btn_xiugai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp_dengji = spinner.getSelectedItem().toString();
                if (sp_dengji.equals("一级客户")){
                    jishu = 1;
                }else if (sp_dengji.equals("二级客户")){
                    jishu = 2;
                }else if (sp_dengji.equals("三级客户")){
                    jishu = 3;
                }else if (sp_dengji.equals("四级客户")){
                    jishu = 4;
                }else if (sp_dengji.equals("五级客户")){
                    jishu = 5;
                }
             //   Log.e("店铺等级信息***",logo_id+"*****"+jishu+"*****"+name+"*****"+dp_id);
                OkGo.post(Api.dp_xiugai)
                        .tag(this)
                        .params("id",dp_id)
                        .params("stepCode",jishu)
                        .params("name",name)
                        .params("laSalesmanID",logo_id)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                Log.e("d店铺信息",s);
                                Toast.makeText(GaijiMoreActivity.this,"修改成功！",Toast.LENGTH_SHORT).show();
                               android.os.Handler handler = new android.os.Handler();
                               handler.postDelayed(new Runnable() {
                                   @Override
                                   public void run() {
                                       GaijiMoreActivity.this.finish();
                                   }
                               }, 1000);

                            }
                        });
            }
        });
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
                GaijiMoreActivity.this.finish();
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
}
