package go.app.mdb.android.go.kucun;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import go.app.mdb.android.go.R;

/**
 * Created by Administrator on 2017-11-24.
 */

public class KuncunMyAdapater extends BaseAdapter {
    Context context;
    ArrayList<Kucun> kc_array;
    public KuncunMyAdapater(Context context,ArrayList<Kucun> kc_array){
        this.context = context;
        this.kc_array = kc_array;
    }
    @Override
    public int getCount() {
        return kc_array.size();
    }

    @Override
    public Object getItem(int i) {
        return kc_array.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.fg3_lt_item,viewGroup,false);
            view.setTag(new ViewHolder(view));
        }
        vh = (ViewHolder)view.getTag();
        Kucun kc= kc_array.get(i);
        vh.tv_name.setText(kc.getName());
        vh.tv_kucunnum.setText(kc.getKucunnum());
        vh.tv_rukutime.setText(kc.getRukutime());
        vh.tv_price.setText(kc.getPrice());
        vh.tv_youwu.setText(kc.getYouwu());
        return view;
    }
    public static class ViewHolder{
        private TextView tv_name;
        private TextView tv_kucunnum;
        private TextView tv_rukutime;
        private TextView tv_price;
        private TextView tv_youwu;
        public ViewHolder(View view){
            tv_name = (TextView) view.findViewById(R.id.fg3_tv_name);
            tv_kucunnum = (TextView) view.findViewById(R.id.fg3_tv_kucunnum);
            tv_rukutime = (TextView) view.findViewById(R.id.fg3_tv_rukutime);
            tv_price = (TextView) view.findViewById(R.id.fg3_tv_price);
            tv_youwu = (TextView) view.findViewById(R.id.fg3_tv_youwu);
        }
    }
}
