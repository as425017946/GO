package go.app.mdb.android.go;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import go.app.mdb.android.go.dingdan.ChuanjianActivity;
import go.app.mdb.android.go.dingdan.ChuanjianActivity2;
import go.app.mdb.android.go.dingdan.GouwucheActivity;
import go.app.mdb.android.go.mapplication.MyApplication;


public class Main2Activity extends AppCompatActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener {
    // 初始化顶部栏显示
    private ImageView titleLeftImv;
    private TextView titleTv;
    // 定义4个Fragment对象
    private FirstFragment fg1;
    private SecondFragment fg2;
    private ThirdFragment fg3;
    private FourthFragment fg4;
    // 帧布局对象，用来存放Fragment对象
    private FrameLayout frameLayout;
    // 定义每个选项中的相关控件
    private LinearLayout firstLayout;
    private LinearLayout secondLayout;
    private LinearLayout thirdLayout;
    private LinearLayout fourthLayout;
    private ImageView firstImage;
    private ImageView secondImage;
    private ImageView thirdImage;
    private ImageView fourthImage;
    private TextView firstText;
    private TextView secondText;
    private TextView thirdText;
    private TextView fourthText;
    // 定义几个颜色
    private int whirt = 0xFF06B193;
    private int gray = 0xFFFFFFFF;
    private int dark = 0xFFFDD704;
    // 定义FragmentManager对象管理器
    private FragmentManager fragmentManager;
    //
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle bd=getIntent().getExtras();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//
//        //控制侧滑菜单
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//       // toggle.syncState();
//
//        //设定NavigationView菜单的选择事件
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        initView(); // 初始化界面控件
        setChioceItem(1); // 初始化页面加载时显示第一个选项卡

        MyApplication.getInstance().addActivity(this);

    }

    /**
     * 初始化页面
     */
    private void initView() {
// 初始化底部导航栏的控件
        secondImage = (ImageView) findViewById(R.id.second_image);
        thirdImage = (ImageView) findViewById(R.id.third_image);
        fourthImage = (ImageView) findViewById(R.id.fourth_image);
        secondText = (TextView) findViewById(R.id.second_text);
        thirdText = (TextView) findViewById(R.id.third_text);
        fourthText = (TextView) findViewById(R.id.fourth_text);
        secondLayout = (LinearLayout) findViewById(R.id.second_layout);
        thirdLayout = (LinearLayout) findViewById(R.id.third_layout);
        fourthLayout = (LinearLayout) findViewById(R.id.fourth_layout);
        secondLayout.setOnClickListener(Main2Activity.this);
        thirdLayout.setOnClickListener(Main2Activity.this);
        fourthLayout.setOnClickListener(Main2Activity.this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.second_layout:
                setChioceItem(1);
                break;
            case R.id.third_layout:
                setChioceItem(2);
                break;
            case R.id.fourth_layout:
                setChioceItem(3);
                break;
            default:
                break;
        }
    }
    /**
     * 设置点击选项卡的事件处理
     *
     * @param index 选项卡的标号：0, 1, 2, 3
     */
    private void setChioceItem(int index) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        clearChioce(); // 清空, 重置选项, 隐藏所有Fragment
        hideFragments(fragmentTransaction);
        switch (index) {
            case 1:
// secondImage.setImageResource(R.drawable.XXXX);
                secondText.setTextColor(dark);
                secondImage.setImageDrawable(Main2Activity.this.getResources().getDrawable(R.mipmap.dingdan2));
                if (fg2 == null) {
                    fg2 = new SecondFragment();
                    fragmentTransaction.add(R.id.content, fg2);
                } else {
                    fragmentTransaction.show(fg2);
                }
                break;
            case 2:
// thirdImage.setImageResource(R.drawable.XXXX);
                thirdText.setTextColor(dark);
                thirdImage.setImageDrawable(Main2Activity.this.getResources().getDrawable(R.mipmap.kucun2));
                if (fg3 == null) {
                    fg3 = new ThirdFragment();
                    fragmentTransaction.add(R.id.content, fg3);
                } else {
                    fragmentTransaction.show(fg3);
                }
                break;
            case 3:
// fourthImage.setImageResource(R.drawable.XXXX);
                fourthText.setTextColor(dark);
                fourthImage.setImageDrawable(Main2Activity.this.getResources().getDrawable(R.mipmap.wode2));
                if (fg4 == null) {
                    fg4 = new FourthFragment();
                    fragmentTransaction.add(R.id.content, fg4);
                } else {
                    fragmentTransaction.show(fg4);
                }
                break;
        }
        fragmentTransaction.commit(); // 提交
    }
    /**
     * 当选中其中一个选项卡时，其他选项卡重置为默认
     */
    private void clearChioce() {

// secondImage.setImageResource(R.drawable.XXX);
        secondText.setTextColor(gray);
        secondLayout.setBackgroundColor(whirt);
        secondImage.setImageDrawable(Main2Activity.this.getResources().getDrawable(R.mipmap.dingdan1));
// thirdImage.setImageResource(R.drawable.XXX);
        thirdText.setTextColor(gray);
        thirdLayout.setBackgroundColor(whirt);
        thirdImage.setImageDrawable(Main2Activity.this.getResources().getDrawable(R.mipmap.kucun1));
// fourthImage.setImageResource(R.drawable.XXX);
        fourthText.setTextColor(gray);
        fourthLayout.setBackgroundColor(whirt);
        fourthImage.setImageDrawable(Main2Activity.this.getResources().getDrawable(R.mipmap.wode1));
    }
    /**
     * 隐藏Fragment
     *
     * @param fragmentTransaction
     */
    private void hideFragments(FragmentTransaction fragmentTransaction) {

        if (fg2 != null) {
            fragmentTransaction.hide(fg2);
        }
        if (fg3 != null) {
            fragmentTransaction.hide(fg3);
        }
        if (fg4 != null) {
            fragmentTransaction.hide(fg4);
        }
    }



    /**
     * 返回键触发的方法
     * */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            exit();
        }
    }
    private  long exitTime=0;
    public  void exit(){
        if ((System.currentTimeMillis() - exitTime) > 2000)
        {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else
        {
            this.finish();
        }

    }
    //创建右上角状态栏目
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    //点击右上角状态栏进行操作
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_cjdd1) {
            Intent intent = new Intent(Main2Activity.this, ChuanjianActivity2.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_cjdd2) {
            Intent intent = new Intent(Main2Activity.this, ChuanjianActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_gwc) {
            Intent intent = new Intent(Main2Activity.this, GouwucheActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Toast.makeText(this,"1",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }
        //关闭侧滑菜单
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}
