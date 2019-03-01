package go.app.mdb.android.go.lixian;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import go.app.mdb.android.go.FirstFragment;
import go.app.mdb.android.go.FourthFragment;
import go.app.mdb.android.go.MainActivity;
import go.app.mdb.android.go.R;
import go.app.mdb.android.go.SecondFragment;
import go.app.mdb.android.go.ThirdFragment;
// a
public class LixinaMainActivity extends AppCompatActivity implements View.OnClickListener {
    // 定义FragmentManager对象管理器
    private FragmentManager fragmentManager;
    //实例化对象
//    private Lixian_SetupFragment setupFragment;
//    private Lixian_WodeFragment wodeFragment;
    //实例化几个颜色
    private int whirt = 0xFF06B193;
    private int gray = 0xFFFFFFFF;
    private int dark = 0xFFFDD704;
    //公开的变量获取离线登录的人名
    public static String lxlogo_name;

    //扫描条形码后得到的值
    public static String tiaoxingma_zhi = "";

    //获取是不是从查询详细页面返回到这个页面的  0不是 1是
    String lxdelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lixina);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        Bundle bd=getIntent().getExtras();
        lxlogo_name = bd.getString("name");
        //Log.e("离线名称",""+lxlogo_name);
        initView();
//        setChioceItem(0);


            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Lixian_SetupFragment setupFragment = new Lixian_SetupFragment();
            fragmentTransaction.add(R.id.lixian_content, setupFragment);
            fragmentTransaction.commit(); // 提交
            firstChioce();

    }

    //实例化对象
    @BindView(R.id.lx_first_image)
    ImageView imageView_1;
    @BindView(R.id.lx_second_image)
    ImageView imageView_2;
    @BindView(R.id.lx_first_text)
    TextView tv_1;
    @BindView(R.id.lx_second_text)
    TextView tv_2;
    @BindView(R.id.lx_first_layout)
    LinearLayout linearLayout_1;
    @BindView(R.id.lx_second_layout)
    LinearLayout linearLayout_2;
    @BindView(R.id.lixian_fenye_title)
    TextView tv_title;
    /**
     * 初始化页面
     */
    private void initView(){

        linearLayout_1.setOnClickListener(LixinaMainActivity.this);
        linearLayout_2.setOnClickListener(LixinaMainActivity.this);
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.lx_first_layout:
                setChioceItem(0);
                break;
            case R.id.lx_second_layout:
                setChioceItem(1);
                break;
            default:
                break;
        }
    }
    /**
     * 设置点击选项卡的事件处理
     *
     * @param index 选项卡的标号：0, 1
     */
    private void setChioceItem(int index) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        clearChioce(); // 清空, 重置选项, 隐藏所有Fragment
//        hideFragments(fragmentTransaction);
        switch (index) {
            case 0:
// firstImage.setImageResource(R.drawable.XXXX); 需要的话自行修改
                tv_1.setTextColor(dark);
                tv_title.setText("创建离线订单");
                imageView_1.setImageDrawable(LixinaMainActivity.this.getResources().getDrawable(R.mipmap.chuangjian2));
// 如果fg1为空，则创建一个并添加到界面上
//                if (setupFragment == null) {
                Lixian_SetupFragment  setupFragment = new Lixian_SetupFragment();
                fragmentTransaction.replace(R.id.lixian_content,setupFragment);
//                } else {
//// 如果不为空，则直接将它显示出来
//                    fragmentTransaction.show(setupFragment);
//                }

                break;
            case 1:
// secondImage.setImageResource(R.drawable.XXXX);
                tv_2.setTextColor(dark);
                tv_title.setText("我的离线订单");
                imageView_2.setImageDrawable(LixinaMainActivity.this.getResources().getDrawable(R.mipmap.dingdan2));
//                if (wodeFragment == null) {
                Lixian_WodeFragment wodeFragment = new Lixian_WodeFragment();
                fragmentTransaction.replace(R.id.lixian_content, wodeFragment);
//                } else {
//                    fragmentTransaction.show(wodeFragment);
//                }
                break;

        }
        fragmentTransaction.commit(); // 提交
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("onRestart","onRestart");//这里加载数据
       // setChioceItem(1);
    }

    /**
     * 先默认第一个选择
     */
    private void firstChioce() {
// firstImage.setImageResource(R.drawable.XXX);
        tv_1.setTextColor(dark);
        tv_title.setText("创建离线订单");
        imageView_1.setImageDrawable(LixinaMainActivity.this.getResources().getDrawable(R.mipmap.chuangjian2));
// secondImage.setImageResource(R.drawable.XXX);
        tv_2.setTextColor(gray);
        tv_title.setText("我的离线订单");
        imageView_2.setImageDrawable(LixinaMainActivity.this.getResources().getDrawable(R.mipmap.dingdan1));

    }
    /**
     * 先默认第二个选择
     */
    private void seondChioce() {
// firstImage.setImageResource(R.drawable.XXX);
        tv_1.setTextColor(gray);
        tv_title.setText("创建离线订单");
        imageView_1.setImageDrawable(LixinaMainActivity.this.getResources().getDrawable(R.mipmap.chuangjian1));
// secondImage.setImageResource(R.drawable.XXX);
        tv_2.setTextColor(dark);
        tv_title.setText("我的离线订单");
        imageView_2.setImageDrawable(LixinaMainActivity.this.getResources().getDrawable(R.mipmap.dingdan2));

    }


    /**
     * 当选中其中一个选项卡时，其他选项卡重置为默认
     */
    private void clearChioce() {
// firstImage.setImageResource(R.drawable.XXX);
        tv_1.setTextColor(gray);
        linearLayout_1.setBackgroundColor(whirt);
        imageView_1.setImageDrawable(LixinaMainActivity.this.getResources().getDrawable(R.mipmap.chuangjian1));
// secondImage.setImageResource(R.drawable.XXX);
        tv_2.setTextColor(gray);
        linearLayout_2.setBackgroundColor(whirt);
        imageView_2.setImageDrawable(LixinaMainActivity.this.getResources().getDrawable(R.mipmap.dingdan1));

    }
    /**
     * 隐藏Fragment
     *
     * @param fragmentTransaction
     */
//    private void hideFragments(FragmentTransaction fragmentTransaction) {
//        if (setupFragment != null) {
//            fragmentTransaction.hide(setupFragment);
//        }
//        if (wodeFragment != null) {
//            fragmentTransaction.hide(wodeFragment);
//        }
//
//    }
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
