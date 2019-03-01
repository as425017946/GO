package go.app.mdb.android.go;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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
import go.app.mdb.android.go.bean.GoodsdanjiaBean;
import go.app.mdb.android.go.bean.GoodsinfoBean;
import go.app.mdb.android.go.bean.UnitBean;
import go.app.mdb.android.go.bean.WanglaidanweiBean;
import go.app.mdb.android.go.tools.Api;
import go.app.mdb.android.go.tools.Helptools;
import go.app.mdb.android.go.wode.ChaoshiActivity;
import go.app.mdb.android.go.wode.GaijiActivity;
import go.app.mdb.android.go.wode.Wode_lixian;
import go.app.mdb.android.go.wode.XiugaiChuanActivity;
import okhttp3.Call;
import okhttp3.Response;

public class FourthFragment extends Fragment {
    @Nullable

    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fg4,container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dengji();
        lixian();
        xiazai();
        xiugaichuan();
        chaoshidingdan();
    }
    /**查看超市订单**/
    @BindView(R.id.wd_chakanchaoshidingdan)
    RelativeLayout chaoshi;
    private void chaoshidingdan(){
        chaoshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChaoshiActivity.class);
                startActivity(intent);
            }
        });
    }
    /**
     * 修改数据库连接地址
     */
    @BindView(R.id.wd_lianjiedizhi)
    RelativeLayout relativeLayout_chuan;
    private void xiugaichuan(){
        relativeLayout_chuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,XiugaiChuanActivity.class);
                startActivity(intent);
                onDestroy();
            }
        });
    }

    /***
     * 修改店铺等级
     */
    @BindView(R.id.wd_dengji)
    RelativeLayout relativeLayout_dengji;
    private void dengji(){
        relativeLayout_dengji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GaijiActivity.class);
                startActivity(intent);
            }
        });

    }

    /***
     * 离线订单
     */
    @BindView(R.id.wd_lixian)
    RelativeLayout relativeLayout_lixian;
    private void lixian(){
        relativeLayout_lixian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Wode_lixian.class);
                startActivity(intent);
            }
        });

    }

    /**
     * 下载离线数据
     */
    ProgressDialog prodialog;
    @BindView(R.id.wd_xiazaishuju)
    RelativeLayout relativeLayout_xiazai;
    private void xiazai(){
        relativeLayout_xiazai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //网络合作单位信息添加到离线库
                add_lixian_hzdw();
                add_lixian_goodsdanwei();
                add_lixian_goodsinfo();
                add_lixian_goodsdanjia();
                addP_lixian_person();
                LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(context)
                                    .setMessage("离线信息下载中")
                                    .setCancelable(false)
                                    .setCancelOutside(false);
                            final LoadingDailog dialog=loadBuilder.create();
                            dialog.show();
                            Handler handler =new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.dismiss();
                                    Toast.makeText(context,"下载成功",Toast.LENGTH_SHORT).show();
                                }
                            }, 5000);
            }
        });
    }


    SQLiteDatabase db;
    /**
     * 添加人员信息
     */
    private void addP_lixian_person(){

//        db=Helptools.getDb(this);
//        final Cursor cs =db.query("person",null,null,null,null,null,null,null);
//        name  =  new  String[cs.getCount()];
//        //如果是第一次进入app，且没有在有网络的情况下登录过app时提示
//        if (cs.getCount()==0){
        OkGo.post(Api.renyuan)
                .tag(this)
                .params("pager",1)
                .params("pagerSize",Integer.MAX_VALUE)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        // Log.e("分类网络请求",s);

                        JSONObject object = null;
                        try {
                            object = new JSONObject(s);
                            String state = object.getString("state");
//                            Log.e("state",state);
                            if (state.equals("1")){
                                JSONArray jsonArray = object.getJSONArray("person");

                                //给离线数据添加人员信息
                                db = Helptools.getDb(context);
                                Cursor cs = db.query("person",null,null,null,null,null,null);
                                int num =cs.getCount();
                                db = Helptools.wriDb(context);
                                ContentValues cv = new ContentValues();
                                for (int i = num; i <jsonArray.length();i++){
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    cv.put("name",""+obj.getString("UserName"));
                                    cv.put("id",""+obj.getString("ID"));
                                    db.insert("person",null,cv);
                                }
                            }else {
                                Toast.makeText(context,"网络请求人员信息打开失败，请稍后重试",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }



    /**
     * 给离线数据库添加合作单位信息
     */
    private void add_lixian_hzdw(){
        OkGo.post(Api.dw_fenji)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                       // Log.e("合作单位",s);
                        Gson gson =new Gson();
                        WanglaidanweiBean hzdw = gson.fromJson(s,WanglaidanweiBean.class);
                        if (hzdw.getState().equals("1")){
                            db = Helptools.getDb(context);
                            Cursor cs = db.query("hzdwinfo",null,null,null,null,null,null);
                            int num =cs.getCount();
                            db = Helptools.wriDb(context);
                            ContentValues cv = new ContentValues();
                            //判断离线数据里面是不是有值，如果没有，全部填入，如果有但是比网上的少，直接填入缺少的信息

                                for (int i = num; i <hzdw.getPerson().size();i++){
                                    cv.put("name",hzdw.getPerson().get(i).getName());
                                    cv.put("id",hzdw.getPerson().get(i).getID());
                                    cv.put("StepCode",hzdw.getPerson().get(i).getStepCode());
                                    db.insert("hzdwinfo",null,cv);
                                }

                            db.close();  //关闭数据库
                        }
                    }
                });
    }
    /**
     * 给离线数据库添加商品信息
     */
    private void add_lixian_goodsinfo(){
        OkGo.post(Api.goods_txm)
                .tag(this)
                .params("state",1)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        GoodsinfoBean tf_bean = gson.fromJson(s,GoodsinfoBean.class);
                        String state =tf_bean.getState();
                        //Log.e("合作单位",s);
                        if (state.equals("1")){
                            if (tf_bean.getState().equals("1")){
                                db = Helptools.getDb(context);
                                Cursor cs = db.query("goodinfos",null,null,null,null,null,null);
                                int num =cs.getCount();
                                db = Helptools.wriDb(context);
                                ContentValues cv = new ContentValues();
                                //判断离线数据里面是不是有值，如果没有，全部填入，如果有但是比网上的少，直接填入缺少的信息

                                    for (int i = num; i <tf_bean.getPerson().size();i++){
                                        cv.put("name",tf_bean.getPerson().get(i).getName());
                                        cv.put("id",tf_bean.getPerson().get(i).getID());
                                        cv.put("BarCode",tf_bean.getPerson().get(i).getBarCode());
                                        db.insert("goodinfos",null,cv);
                                    }

                                db.close();  //关闭数据库
                            }
                        }
                    }
                });
    }

    /**
     * 给离线数据库添加商品单位规格
     */
    private void add_lixian_goodsdanwei(){
        OkGo.post(Api.danwei)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson =new Gson();
                        UnitBean ub = gson.fromJson(s,UnitBean.class);
                        if (ub.getState().equals("1")){
                            db = Helptools.getDb(context);
                            Cursor cs = db.query("goodsdanwei",null,null,null,null,null,null);
                            int num =cs.getCount();
                            db = Helptools.wriDb(context);
                            ContentValues cv = new ContentValues();
                            //判断离线数据里面是不是有值，如果没有，全部填入，如果有但是比网上的少，直接填入缺少的信息

                                for (int i = num; i <ub.getPerson().size();i++){
                                    cv.put("name",ub.getPerson().get(i).getName());
                                    cv.put("id",ub.getPerson().get(i).getID());
                                    db.insert("goodsdanwei",null,cv);
                                }

                            db.close();  //关闭数据库
                        }
                    }
                });
    }
    /**
     * 给离线数据库添加商品单价
     */
    private void add_lixian_goodsdanjia(){
        OkGo.post(Api.goods_danjia)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson =new Gson();
                       // Log.e("单价数据",s);
                        GoodsdanjiaBean ub = gson.fromJson(s,GoodsdanjiaBean.class);
                        if (ub.getState().equals("1")){
                            db = Helptools.getDb(context);
                            Cursor cs = db.query("goodsdanjia",null,null,null,null,null,null);
                            int num =cs.getCount();
                            db = Helptools.wriDb(context);
                            ContentValues cv = new ContentValues();
                            //判断离线数据里面是不是有值，如果没有，全部填入，如果有但是比网上的少，直接填入缺少的信息

                                for (int i = num; i <ub.getPerson().size();i++){
                                    cv.put("InventoryID",ub.getPerson().get(i).getInventoryID());
                                    cv.put("id",ub.getPerson().get(i).getID());
                                    cv.put("StepPrice1",ub.getPerson().get(i).getStepPrice1());
                                    cv.put("StepPrice2",ub.getPerson().get(i).getStepPrice1());
                                    cv.put("StepPrice3",ub.getPerson().get(i).getStepPrice1());
                                    cv.put("StepPrice4",ub.getPerson().get(i).getStepPrice1());
                                    cv.put("StepPrice5",ub.getPerson().get(i).getStepPrice1());
//                                    cv.put("name",ub.getPerson().get(i).get); 需要加信息
                                    db.insert("goodsdanjia",null,cv);
                                }

                            db.close();  //关闭数据库
                        }
                    }
                });
    }



}
