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
 * Created by Administrator on 2018-01-23.
 */

public class HezdwAdapter  extends BaseAdapter{
    Context context;
    ArrayList<hzdw_dd> hzdw_ddArrayList;
    public HezdwAdapter(Context context,ArrayList<hzdw_dd> hzdw_ddArrayList){
        this.context = context;
        this.hzdw_ddArrayList = hzdw_ddArrayList ;
    }
    @Override
    public int getCount() {
        return hzdw_ddArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return hzdw_ddArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder v;
        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.activity_chuanjian2_item,viewGroup,false);
            view.setTag(new ViewHolder(view));
        }
        v=(ViewHolder)view.getTag();
        hzdw_dd hzdw_dd = hzdw_ddArrayList.get(i);
        v.tv_name.setText(hzdw_dd.getName());
        v.tv_dizhi.setText(hzdw_dd.getDizhi());
        return view;
    }
    public static class ViewHolder{
        TextView tv_name;
        TextView tv_dizhi;
        public ViewHolder(View view){
            tv_name = (TextView) view.findViewById(R.id.cj2_name);
            tv_dizhi = (TextView)view.findViewById(R.id.cj2_dizhi);
        }
    }
}
