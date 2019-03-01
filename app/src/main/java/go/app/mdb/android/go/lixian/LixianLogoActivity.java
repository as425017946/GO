package go.app.mdb.android.go.lixian;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import go.app.mdb.android.go.LogoActivity;
import go.app.mdb.android.go.R;
import go.app.mdb.android.go.tools.Helptools;

public class LixianLogoActivity extends AppCompatActivity {


    //创建一个数组保存数据
    String[] name;
    //创建一个值用于添加到上面的数组中
    int i=0;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lixianlogo);
        ButterKnife.bind(this);//这句话必须写  而且必须写在setContentView下面  且他们中间不允许写其他的 这句话的意思是引入快捷方式
        read();
        addinfo();
        //panduan();
        logo();

    }

    List<String > stringList;
    ArrayAdapter<String> stringArrayAdapter ;
    //从库读取数据
    private void read() {
        db=Helptools.getDb(this);
        Cursor cs =db.query("person",null,null,null,null,null,null,null);
        name  =  new  String[cs.getCount()];
        //如果是第一次进入app，且没有在有网络的情况下登录过app时提示
        if (cs.getCount()==0){
            Toast.makeText(LixianLogoActivity.this,"请先在有网络的情况下，下载网络数据。",Toast.LENGTH_LONG).show();
        }

        while (cs.moveToNext()){
            name[i] =  cs.getString(cs.getColumnIndex("name"));
            i++;
           // Log.e("我的值都有   ",""+cs.getString(cs.getColumnIndex("name")));
        }
       //Log.e("我的值都有   ",""+name[0]+name[1]+name[2]);
    }

    //给pinner动态添加数据
    @BindView(R.id.lixian_sp1)
    Spinner sp1;
    String  s = "" ;
    private void addinfo(){

        stringArrayAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item,name);
        stringArrayAdapter.setDropDownViewResource(R.layout.spinner_zidingyi);
        sp1.setAdapter(stringArrayAdapter);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                s = sp1.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    /**
     * 登录
     */
    @BindView(R.id.lixian_login)
    TextView tv_logo;
    private void logo(){

        tv_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Log.e("有值吗",s);
                if (s.equals("")){

                }else {
                    Intent intent = new Intent(LixianLogoActivity.this,LixinaMainActivity.class);
                    intent.putExtra("name",""+s);
                    startActivity(intent);
                    LixianLogoActivity.this.finish();
                }

            }
        });
    }

    /***
     * 按2次返回键退出app
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;
    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }
}
