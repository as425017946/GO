package go.app.mdb.android.go.dingdan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import go.app.mdb.android.go.R;
import go.app.mdb.android.go.pandian.PanDian;

/**
 * Created by Administrator on 2018-01-23.
 */

public class TiaoxuanAdapter extends BaseAdapter {
    Context context;
    ArrayList<PanDian> searchArrayList;
    public TiaoxuanAdapter(Context context, ArrayList<PanDian> searchArrayList){
        this.context = context;
        this.searchArrayList = searchArrayList;
    }
    @Override
    public int getCount() {
        return searchArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return searchArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder v;
        if (view==null){
            view = LayoutInflater.from(context).inflate(R.layout.activity_tiaoxuan_item,viewGroup,false);
            view.setTag(new ViewHolder(view));
        }
        v=(ViewHolder)view.getTag();
        PanDian panDian  = searchArrayList.get(i);
        v.tv_mingcheng.setText(panDian.getName());
        v.tv_guige.setText(panDian.getGuige());
        v.tv_kucn.setText(panDian.getKucun());
        return view;
    }
    public static class ViewHolder{
        TextView tv_mingcheng;
        TextView tv_guige;
        TextView tv_kucn;
        public ViewHolder(View view){
            tv_mingcheng = view.findViewById(R.id.tx_goods);
            tv_guige = view.findViewById(R.id.tx_guige);
            tv_kucn = view.findViewById(R.id.tx_kucun);
        }
    }
}
