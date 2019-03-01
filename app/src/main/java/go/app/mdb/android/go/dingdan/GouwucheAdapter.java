package go.app.mdb.android.go.dingdan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import go.app.mdb.android.go.R;

/**
 * Created by Administrator on 2018-01-24.
 */

public class GouwucheAdapter extends BaseAdapter{
    Context context;
    ArrayList<Gouwuche> gwc_array ;
    public GouwucheAdapter(Context context,ArrayList<Gouwuche> gwc_array ){
        this.context = context;
        this.gwc_array = gwc_array;
    }
    @Override
    public int getCount() {
        return gwc_array.size();
    }

    @Override
    public Object getItem(int i) {
        return gwc_array.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder v;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.activity_gouwuche_item,viewGroup,false);
            view.setTag( new ViewHolder(view));
        }
        v=(ViewHolder)view.getTag();
        Gouwuche gwc = gwc_array.get(i);
        v.tx_name.setText(gwc.getName());
        v.tx_danwei.setText(gwc.getDanwei());
        v.tx_danjia.setText(gwc.getJiage());
        v.tx_guige.setText(gwc.getGuige());
        v.tx_shuliang.setText(gwc.getShuliang());
        v.tv_beizhu.setText(gwc.getBeizhu());
        return view;
    }
    public static class ViewHolder{
        TextView tx_name;
        TextView tx_guige;
        TextView tx_danwei;
        TextView tx_danjia;
        TextView tx_shuliang;
        TextView tv_beizhu;
        public ViewHolder(View view){
            tx_name = (TextView) view.findViewById(R.id.gwcmore_goods);
            tx_guige = (TextView) view.findViewById(R.id.gwcmore_guige);
            tx_danwei = (TextView) view.findViewById(R.id.gwcmore_danwei);
            tx_danjia = (TextView) view.findViewById(R.id.gwcmore_jiage);
            tx_shuliang = (TextView) view.findViewById(R.id.gwcmore_goumaishu);
            tv_beizhu = (TextView) view.findViewById(R.id.gwcmore_beizhu);
        }

    }

}
