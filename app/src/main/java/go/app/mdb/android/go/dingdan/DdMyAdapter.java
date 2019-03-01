package go.app.mdb.android.go.dingdan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.logging.Handler;

import go.app.mdb.android.go.R;

/**
 * Created by Administrator on 2017-11-21.
 */

public class DdMyAdapter extends BaseAdapter {
    Context context;
    ArrayList<Dingdan> dd_array;
    public  DdMyAdapter(Context context,ArrayList<Dingdan> dd_array){
         this.context = context;
         this.dd_array = dd_array;

    }

    @Override
    public int getCount() {
        return dd_array.size();
    }

    @Override
    public Object getItem(int i) {
        return dd_array.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh=null;
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.fg2_lt_item,viewGroup,false);
            view.setTag(new ViewHolder(view));
        }
        vh=(ViewHolder)view.getTag();
        Dingdan dd=dd_array.get(i);
        vh.tv_ddhao.setText(dd.getDdhao());
        vh.tv_hezuoname.setText(dd.getHezuoname());
        vh.tv_newtime.setText(dd.getNewtime());
        vh.tv_updatetime.setText(dd.getUpdatetime());
        vh.tv_ddstate.setText(dd.getDdstate());
        vh.tv_updatestate.setText(dd.getUpdatestate());
        vh.tv_xiaoshouyuan.setText(dd.getXiaoshouyuan());

        return view;
    }

    /**
     * 实例化类对象
     */
   public static class ViewHolder{
       private TextView tv_ddhao;
       private TextView tv_hezuoname;
       private TextView tv_newtime;
       private TextView tv_updatetime;
       private TextView tv_ddstate;
       private TextView tv_updatestate;
        private TextView tv_xiaoshouyuan;
        public ViewHolder(View view){
            tv_ddhao=(TextView) view.findViewById(R.id.dd_tv_ddhao);
            tv_hezuoname=(TextView)view.findViewById(R.id.dd_tv_hezuoname);
            tv_newtime=(TextView)view.findViewById(R.id.dd_tv_newtime);
            tv_updatetime=(TextView)view.findViewById(R.id.dd_tv_updatetiem);
            tv_ddstate=(TextView)view.findViewById(R.id.dd_tv_ddsatae);
            tv_updatestate=(TextView)view.findViewById(R.id.dd_tv_updatestate);
            tv_xiaoshouyuan=(TextView)view.findViewById(R.id.dd_tv_xiaoshouyuan);
        }
   }
}
