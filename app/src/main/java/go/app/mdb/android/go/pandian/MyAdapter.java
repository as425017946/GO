package go.app.mdb.android.go.pandian;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import go.app.mdb.android.go.R;
import go.app.mdb.android.go.tools.Api;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by mdb on 2017-11-20.
 */

public class MyAdapter extends BaseAdapter {
    Context context;
    ArrayList<PanDian> pdarray;
    View.OnClickListener onClickListener;
    public MyAdapter(Context context, ArrayList<PanDian> pdarray, View.OnClickListener onClickListener){
            this.context = context;
            this.pdarray = pdarray;
            this.onClickListener = onClickListener;
    }
    @Override
    public int getCount() {
        return pdarray.size();
    }

    @Override
    public Object getItem(int i) {
        return pdarray.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    ViewHolder vh=null;
    int num = -1;
    int num2 = -2;
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.fg1_lt_item,viewGroup,false);
            view.setTag(new ViewHolder(view));
        }
        vh=(ViewHolder)view.getTag();
        final PanDian pd=pdarray.get(i);
        vh.name.setText(pd.getName());
        vh.fenlei.setText(pd.getFenlei());
        vh.pinpai.setText(pd.getPinpai());
        vh.guige.setText(pd.getGuige());
        vh.kucun.setText(pd.getKucun());
        vh.zhujima.setText(pd.getZhujima());
        vh.tiaoxingma.setText(pd.getTiaoxingma());
        vh.tv_beizhu.setText(pd.getBeizhu());
        vh.btn_xiugai.setTag(i);
       // Log.e("我的数据",pd.getBeizhu());
//        vh.btn_wancheng.setTag(i);
//        vh.edt_beizhu.setText(pd.getBeizhu());
//        vh.edt_beizhu.setTag(i);
//        vh.tv_beizhu.setTag(i);


        //修改按钮
        vh.btn_xiugai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater factory = LayoutInflater.from(context);//提示框
                view = factory.inflate(R.layout.editext_dialog, null);//这里必须是final的
                final EditText edit=(EditText)view.findViewById(R.id.editText);//获得输入框对象
                edit.setText(pd.getBeizhu());
                edit.setSelection(edit.length());
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setView(view)
                        .setMessage("请输入备注信息")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               // Log.e("有值啊",edit.getText().toString());
                                OkGo.post(Api.pd_xiugai)
                                        .tag(this)
                                        .params("remark", edit.getText().toString())
                                        .params("id",pd.getId())
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onSuccess(String s, Call call, Response response) {
                                                //Log.e("添加备注",s+"***"+edit.getText().toString()+"++++"+pd.getId());
                                                try {
                                                    JSONObject jsonObject = new JSONObject(s);
                                                    if (jsonObject.getString("state").equals("1")){
                                                        pd.setBeizhu(edit.getText().toString());

                                                        Toast.makeText(context,"修改成功！",Toast.LENGTH_SHORT).show();
                                                       //通知adapter数据改变需要重新加载
                                                        notifyDataSetChanged(); //必须有的一步
                                                    }else{
                                                        Toast.makeText(context,"修改失败，请稍后重试",Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            }

                                        });
                            }
                        })
                        .setNegativeButton("取消",null)
                        .create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);



//                int tag = (Integer) view.getTag();
//                Log.e("有值啊",tag+"");
//                Log.e("有值啊num",num+"");
//
//                if (tag==num){
//                    num=-1;
//                }else{
//                    num=tag;
//                }
//                //通知adapter数据改变需要重新加载
//                notifyDataSetChanged(); //必须有的一步
            }
        });
//        //提交按钮
//        vh.btn_wancheng.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Toast.makeText(context,"您点击的是"+tag,Toast.LENGTH_SHORT).show();
//                int tag = (Integer) view.getTag();
//                //num = -1 ;
//                if (tag==num2){
//                    num2=-2;
//                }else{
//                    num2=tag;
//
//                    // Log.e("修改信息的内容是：",""+vh.tv_beizhu.getText().toString()+"::::"+pd.getId());
//                    OkGo.post(Api.pd_xiugai)
//                            .tag(this)
//                            .params("remark", String.valueOf(vh.edt_beizhu.getText()))
//                            .params("id",pd.getId())
//                            .execute(new StringCallback() {
//                                @Override
//                                public void onSuccess(String s, Call call, Response response) {
//                                    Log.e("添加备注",s+"***"+vh.edt_beizhu.getText()+"++++"+pd.getId());
//                                }
//                            });
//                    Toast.makeText(context,"提交成功",Toast.LENGTH_SHORT).show();
//                }
//                //通知adapter数据改变需要重新加载
//                notifyDataSetChanged(); //必须有的一步
//            }
//        });

//        if (num == i) {
//            Log.e("if",num+"****"+i);
//            vh.edt_beizhu.setVisibility(View.VISIBLE);
//            vh.edt_beizhu.requestFocus();
//            vh.edt_beizhu.setSelection(vh.edt_beizhu.getText().length());
//            vh.tv_beizhu.setVisibility(View.GONE);
//            //  Log.e("tv_xiugai", String.valueOf(vh.edt_beizhu.getText()));
//            vh.ry_wancheng.setVisibility(View.VISIBLE);
//            vh.ry_xiugai.setVisibility(View.GONE);
//
//        } else {
//            Log.e("else",num+"++++"+i);
//            vh.edt_beizhu.setVisibility(View.GONE);
//            vh.tv_beizhu.setVisibility(View.VISIBLE);
//            vh.ry_wancheng.setVisibility(View.GONE);
//            vh.ry_xiugai.setVisibility(View.VISIBLE);
//        }
//
//        if (num2==i){
//            vh.edt_beizhu.setVisibility(View.GONE);
//            vh.tv_beizhu.setVisibility(View.VISIBLE);
//            //vh.tv_xiugai.setFocusable(false);
//            vh.ry_wancheng.setVisibility(View.GONE);
//            vh.ry_xiugai.setVisibility(View.VISIBLE);
//        }else{
////            vh.ry_wancheng.setVisibility(View.VISIBLE);
////            vh.ry_xiugai.setVisibility(View.GONE);
//        }

        return view;
    }
    /**
     * 实例化类
     * */
    public static class ViewHolder{
        private TextView name;
        private TextView fenlei;
        private TextView pinpai;
        private TextView guige;
        private TextView kucun;
        private TextView zhujima;
        private TextView tiaoxingma;
        private Button btn_xiugai;
        private Button btn_wancheng;
        private LinearLayout linearLayout_beizhu;
        private RelativeLayout ry_xiugai;
        private RelativeLayout ry_wancheng;
        private EditText edt_beizhu;
        private TextView tv_beizhu;
        public ViewHolder(View view){
            name=(TextView)view.findViewById(R.id.fg1_name);
            fenlei=(TextView)view.findViewById(R.id.fg1_fenlei);
            pinpai=(TextView)view.findViewById(R.id.fg1_pinpai);
            guige=(TextView)view.findViewById(R.id.fg1_guige);
            kucun=(TextView)view.findViewById(R.id.fg1_kucun);
            zhujima=(TextView)view.findViewById(R.id.fg1_zhujima);
            tiaoxingma=(TextView)view.findViewById(R.id.fg1_tiaoxingma);
            btn_xiugai = (Button)view.findViewById(R.id.pd_xiugia);
            btn_wancheng = (Button) view.findViewById(R.id.pd_wancheng);
            linearLayout_beizhu = (LinearLayout)view.findViewById(R.id.fg1_ll_beizhu);
            ry_xiugai =(RelativeLayout)view.findViewById(R.id.rl_xiugai);
            ry_wancheng =(RelativeLayout)view.findViewById(R.id.rl_wancheng);
            tv_beizhu = (TextView) view.findViewById(R.id.fg1_beizhu);

        }
    }

}
