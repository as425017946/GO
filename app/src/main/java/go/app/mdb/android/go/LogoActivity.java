package go.app.mdb.android.go;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.tu.loadingdialog.LoadingDailog;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import go.app.mdb.android.go.bean.BanbengengxinBean;
import go.app.mdb.android.go.bean.GoodsdanjiaBean;
import go.app.mdb.android.go.bean.GoodsinfoBean;
import go.app.mdb.android.go.bean.Hezuodanwei;
import go.app.mdb.android.go.bean.PersonsBean;
import go.app.mdb.android.go.bean.ThirdFragmentBean;
import go.app.mdb.android.go.bean.Unit;
import go.app.mdb.android.go.bean.UnitBean;
import go.app.mdb.android.go.bean.WanglaidanweiBean;
import go.app.mdb.android.go.lhy.Lihuoyuan_Activity;
import go.app.mdb.android.go.lixian.LixianLogoActivity;
import go.app.mdb.android.go.mapplication.MyApplication;
import go.app.mdb.android.go.tools.Api;
import go.app.mdb.android.go.tools.Helptools;
import go.app.mdb.android.go.tools.MyDialog;
import go.app.mdb.android.go.tools.PermissionsChecker;
import go.app.mdb.android.go.tools.Utils;
import okhttp3.Call;
import okhttp3.Response;

import static go.app.mdb.android.go.tools.Utils.getPackageInfo;

public class LogoActivity extends AppCompatActivity {
    private Spinner spinner;

    //这两句话就代替了findviewbyid
    @BindView(R.id.et_inputpwd)
    EditText et_inoutpwd;
    @BindView(R.id.sp1)
    Spinner sp1;
    private SQLiteDatabase db;
    //公开的变量获取登录的人名、ID、权限
    public static String logo_name ,logo_id,logo_limit;
    /**
     * 调取摄像头权限
     * @param
     */
    private static final int REQUEST_CODE = 0; // 请求码
    private boolean isRequireCheck; // 是否需要系统权限检测
    //危险权限（运行时权限）
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    private PermissionsChecker mPermissionsChecker;//检查权限
    private static final int PERMISSION_REQUEST_CODE = 0;        // 系统权限返回码
    private static final String PACKAGE_URL_SCHEME = "package:";

    @Override
    protected void onResume() {
        super.onResume();
        if (isRequireCheck) {
            //权限没有授权，进入授权界面
            if(mPermissionsChecker.judgePermissions(PERMISSIONS)){
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);
            }
        }else{
            isRequireCheck = true;
        }
    }

    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过.
     * 如果权限缺失, 则提示Dialog.
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE && hasAllPermissionsGranted(grantResults)) {
            isRequireCheck = true;
        } else {
            isRequireCheck = false;
            showPermissionDialog();
        }
    }

    // 含有全部的权限
    private boolean hasAllPermissionsGranted( int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 提示对话框
     */
    private void showPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LogoActivity.this);
        builder.setTitle("帮助");
        builder.setMessage("当前应用缺少必要权限。请点击\"设置\"-打开所需权限。");
        // 拒绝, 退出应用
        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
//                setResult(PERMISSIONS_DENIED);
                finish();
            }
        });

        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                startAppSettings();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    // 启动应用的设置
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivity(intent);
    }


    public static String qingqiudizhi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        ButterKnife.bind(this);//这句话必须写  而且必须写在setContentView下面  且他们中间不允许写其他的 这句话的意思是引入快捷方式
        mPermissionsChecker = new PermissionsChecker(this);
        isRequireCheck = true;

        isNetworkConnected(LogoActivity.this);
        isWifiConnected(LogoActivity.this);
//        Log.e("我的值",isNetworkConnected(LogoActivity.this)+"*");
//        Log.e("我的值",  isWifiConnected(LogoActivity.this)+"-");
        db=Helptools.getDb(this);

        MyApplication.getInstance().addActivity(this);

        preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        editor = preferences.edit();
        String dizhi  = preferences.getString("qingqiudizhi",null);
        Log.e("dizhi",dizhi+"***--");
        if (TextUtils.isEmpty(dizhi)){
//            http://link.s29.csome.cn:20136  测试 http://chuanqing.s29.csome.cn:20200
            //        Log.e("dizhi",dizhi+"2--");
            qingqiudizhi = "http://47.94.98.214:8080";
        }else {
            //        Log.e("dizhi",dizhi+"3--");
            qingqiudizhi = dizhi;
        }
        //进行网络请求
        showpersoninfo();
        denglu_dianji();
        gengxintishi();

//        Toast.makeText(LogoActivity.this,"版本号"+Utils.getVersionName(LogoActivity.this),Toast.LENGTH_SHORT).show();
    }

    /**更新提示**/
    private void gengxintishi(){

        OkGo.post(Api.banbengengxin)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("版本信息",s);
                        Gson gson = new Gson();
                        final BanbengengxinBean banebn = gson.fromJson(s,BanbengengxinBean.class);
                        String apkname = banebn.getPerson().get(0).getUrl();
                        if (banebn.getState().equals("1")){
                            if(!banebn.getPerson().get(0).getVersion().equals(Utils.getVersionName(LogoActivity.this))){
                                MyDialog.show(LogoActivity.this, "发现新版本，是否下载体验？", new MyDialog.OnConfirmListener() {
                                    @Override
                                    public void onConfirmClick() {
                                        Toast.makeText(LogoActivity.this,"开始下载",Toast.LENGTH_SHORT).show();
                                        FileDownloader downloader = new FileDownloader(LogoActivity.this);
                                        downloader.downloadFile(banebn.getPerson().get(0).getUrl(),"kanmengo.apk");
                                        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(LogoActivity.this)
                                                .setMessage("下载中，请耐心等待...")
                                                .setCancelable(false)
                                                .setCancelOutside(false);
                                        final LoadingDailog dialog=loadBuilder.create();
                                        dialog.show();
                                        Handler handler =new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                            }
                                        }, 500);

                                    }
                                });
                            }
                        }
                    }
                });


    }

    //创建一个数组保存数据
    String[] name;
    //创建一个值用于添加到上面的数组中
    int i=0;
    //进行网络请求人员信息
    EditText ed_zhi;
    SharedPreferences preferences; //保存账号密码用
    SharedPreferences.Editor editor;//用于转换保存账号密码
    private void showpersoninfo(){
//        db=Helptools.getDb(this);
//        final Cursor cs =db.query("person",null,null,null,null,null,null,null);
//        name  =  new  String[cs.getCount()];
//        //如果是第一次进入app，且没有在有网络的情况下登录过app时提示
//        if (cs.getCount()==0){

        try{
            Log.e("renyuan",Api.renyuan);
            OkGo.post(Api.renyuan)
                    .tag(this)
                    .params("pager",1)
                    .params("pagerSize",Integer.MAX_VALUE)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            Log.e("分类网络请求",s);

                            JSONObject object = null;
                            try {
                                object = new JSONObject(s);
                                String state = object.getString("state");
//                            Log.e("state",state);
                                if (state.equals("1")){
                                    JSONArray jsonArray = object.getJSONArray("person");
                                    //实例化name数组
                                    name = new String[jsonArray.length()];
                                    for (int i = 0; i < jsonArray.length() ; i++) {
                                        JSONObject obj = jsonArray.getJSONObject(i);
                                        name[i] = obj.getString("UserName");
                                    }
                                    addinfo();


                                    //判断离线数据里面是不是有值，如果没有，全部填入，如果有但是比网上的少，直接填入缺少的信息
//                                    if (num==0){
//                                        for (int i = num; i <jsonArray.length();i++){
//                                            JSONObject obj = jsonArray.getJSONObject(i);
//                                            cv.put("name",""+obj.getString("UserName"));
//                                            cv.put("id",""+obj.getString("ID"));
//                                            db.insert("person",null,cv);
//                                        }
//                                    }else {
//                                        for (int i = num; i <jsonArray.length();i++){
//                                            JSONObject obj = jsonArray.getJSONObject(i+1);
//                                            cv.put("name",""+obj.getString("UserName"));
//                                            cv.put("id",""+obj.getString("ID"));
//                                            db.insert("person",null,cv);
//                                        }
//                                    }

//                                    db.close();  //关闭数据库


                                }else {
                                    Toast.makeText(LogoActivity.this,"网络请求人员信息打开失败，请稍后重试",Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
        }catch (IllegalArgumentException e){
            Toast.makeText(LogoActivity.this, "输入的连接地址不正确，请重新输入！", Toast.LENGTH_SHORT).show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LogoActivity.this);
                    builder.setTitle("请输入正确地址");
                    LayoutInflater inflater = LayoutInflater.from(LogoActivity.this);
                    final View DialogView = inflater .inflate ( R.layout.activity_input, null);//1、自定义布局
                    builder.setView(DialogView);
                    ed_zhi = (EditText)DialogView.findViewById(R.id.newaddress) ;
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Toast.makeText(LoginActivity.this, ed_zhi.getText().toString(), Toast.LENGTH_LONG).show();
                            editor.putString("qingqiudizhi", ed_zhi.getText().toString());
                            editor.commit();
                            Toast.makeText(LogoActivity.this,"地址修改成功，请重新登录！",Toast.LENGTH_SHORT).show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    MyApplication.getInstance().exit();
                                    android.os.Process.killProcess(android.os.Process.myPid());

                                }
                            }, 2000);
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }
            }, 2000);
        }

//        }
// else {
//            OkGo.post(Api.renyuan)
//                    .tag(this)
//                    .params("pager",1)
//                    .params("pagerSize",Integer.MAX_VALUE)
//                    .execute(new StringCallback() {
//                        @Override
//                        public void onSuccess(String s, Call call, Response response) {
//                            Gson gson = new Gson();
//                            PersonsBean psb = gson.fromJson(s,PersonsBean.class);
//                            if (psb.getState().equals("1")){
//                                if(cs.getCount()<psb.getPerson().size()){
//                                    //给离线数据添加人员信息
//                                    db = Helptools.getDb(LogoActivity.this);
//                                    Cursor cs = db.query("person",null,null,null,null,null,null);
//                                    int num =cs.getCount();
//                                    db = Helptools.wriDb(LogoActivity.this);
//                                    ContentValues cv = new ContentValues();
//                                    for (int i = num; i <psb.getPerson().size();i++){
//                                        cv.put("name",""+psb.getPerson().get(i).getUserName());
//                                        cv.put("id",""+psb.getPerson().get(i).getUserName());
//                                        db.insert("person",null,cv);
//
//                                    }
//
//                                    while (cs.moveToNext()){
//                                        name[i] =  cs.getString(cs.getColumnIndex("name"));
//                                        i++;
//                                        // Log.e("我的值都有   ",""+cs.getString(cs.getColumnIndex("name")));
//                                    }
//                                    if (i==cs.getCount()){
//                                        addinfo();
//                                    }
//
//                                    db.close();  //关闭数据库
//                                }
//                            }
//                        }
//                    });
//
//        }






    }

    //给显示人名的spinner控件动态绑定数据
    ArrayAdapter<String> stringArrayAdapter ;
    private void addinfo(){

        stringArrayAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item,name);
        stringArrayAdapter.setDropDownViewResource(R.layout.spinner_zidingyi);
        sp1.setAdapter(stringArrayAdapter);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

//    //读取信息后写入本地数据库preson表
//    private void addperson() throws IOException {
//        db = Helptools.getDb(this);
//        Cursor cs = db.query("person",null,null,null,null,null,null);
//        int num =cs.getCount();
//        //判断这个person表是否有值，如果有不在添加,没有就添加
//        if(num==0){
//            db = Helptools.wriDb(this);
//            ContentValues cv = new ContentValues();
//            cv.put("name","张三");
//            db.insert("person",null,cv);
//            cv.put("name","李四");
//            db.insert("person",null,cv);
//            cv.put("name","王五");
//            db.insert("person",null,cv);
//            db.close();
//        }
//
//    }
    //判断是否有网络连接
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }else{
                MyDialog.show(LogoActivity.this, "无网络服务，是否进入离线模式？", new MyDialog.OnConfirmListener() {
                    @Override
                    public void onConfirmClick() {
                            //点击是进入离线页面，点击否，则取消
                            Intent intent = new Intent(LogoActivity.this, LixianLogoActivity.class);
                            startActivity(intent);
                            LogoActivity.this.finish();

                    }
                });
//                Toast.makeText(LogoActivity.this, "当前无可用的网络服务",Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }
    //判断WIFI网络是否可用
    public boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }else{
                Toast.makeText(LogoActivity.this, "当前WIFI网络不可用",Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }

    @BindView(R.id.tv_login)
    TextView tv_denglu;
    private void denglu_dianji(){
        tv_denglu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isFastClick()) {
                    // 进行点击事件后的逻辑操作
                    addshuju();
                }
            }
        });
    }

//    @OnClick({R.id.tv_login})
//    void click(View v){
//        int id=v.getId();
//        switch (id){
//            case R.id.tv_login://登陆
//                addshuju();
//                break;
//        }
//
//    }
    private void addshuju() {
        final String  ss  =  sp1.getSelectedItem().toString();
//        db.insert()//赠
//        db.delete()//删
//        db.update()//改
//        db.execSQL();//查
        if(ss.equals("请选择账号")){
            Toast.makeText(LogoActivity.this, "请先选择账号",Toast.LENGTH_SHORT).show();

        }else{
            //Log.e("tag","输出："+et_inoutpwd.getText().toString());
            if(et_inoutpwd.getText().toString().equals("")){
                Toast.makeText(LogoActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            }else{
                //进行网络接口判断
                OkGo.post(Api.logo)
                        .tag(this)
                        .params("userName",ss)
                        .params("passWord",et_inoutpwd.getText().toString())
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(final String s, Call call, Response response) {
                                try {
                                  //  Log.e("数据",s);
                                    JSONObject object = new JSONObject(s);
                                    String state = object.getString("state");
                                    if (state.equals("1")){
                                        JSONArray jsonArray  = object.getJSONArray("person");
                                        final String id = jsonArray.getJSONObject(0).getString("ID");
                                        final String limit = jsonArray.getJSONObject(0).getString("Limit");
                                        Log.e("权限",limit);
                                        Log.e("名称",ss);
                                        Log.e("id",id);
                                        logo_name = ss;
                                        logo_limit = limit;
                                        logo_id = id;
                                        Handler handler = new Handler();
                                        if (limit.equals("0")){
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Intent intent = new Intent(LogoActivity.this,Main2Activity.class);
                                                    intent.putExtra("name",ss);
                                                    intent.putExtra("id",id);
                                                    intent.putExtra("limit",1);
                                                    startActivity(intent);
                                                    LogoActivity.this.finish();
                                                }
                                            }, 2000);
                                        }else if (limit.equals("1")){
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Intent intent = new Intent(LogoActivity.this,Lihuoyuan_Activity.class);
                                                    startActivity(intent);
                                                    LogoActivity.this.finish();
                                                }
                                            }, 2000);
                                        }else if (limit.equals("2")){
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Intent intent = new Intent(LogoActivity.this,MainActivity.class);
                                                    intent.putExtra("name",ss);
                                                    intent.putExtra("id",id);
                                                    intent.putExtra("limit",2);
                                                    startActivity(intent);
                                                    LogoActivity.this.finish();
                                                }
                                            }, 2000);
                                        }


                                        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(LogoActivity.this)
                                                .setMessage("登录成功...")
                                                .setCancelable(false)
                                                .setCancelOutside(false);
                                        LoadingDailog dialog=loadBuilder.create();
                                        dialog.show();

                                    }else {
                                        Toast.makeText(LogoActivity.this, "输入的密码不正确!", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });





            }
        }

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
