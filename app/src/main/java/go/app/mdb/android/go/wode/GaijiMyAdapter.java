package go.app.mdb.android.go.wode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import go.app.mdb.android.go.R;

/**
 * Created by mdb on 2017-11-24.
 */

public class GaijiMyAdapter extends BaseAdapter {
    Context context;
    ArrayList<Gaiji> gj_array;
    public GaijiMyAdapter(Context context,ArrayList<Gaiji> gj_array){
        this.context = context ;
        this.gj_array = gj_array;

    }
    @Override
    public int getCount() {
        return gj_array.size();
    }

    @Override
    public Object getItem(int i) {
        return gj_array.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh = null;
        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.gaiji_item,viewGroup,false);
            view.setTag(new ViewHolder(view));
        }
        vh = (ViewHolder) view.getTag();
        Gaiji gj = gj_array.get(i);
        vh.tv_name.setText(gj.getDianpuname());
        vh.tv_dengji.setText(gj.getJishu());
        return view;

    }
    public static class ViewHolder{
        private TextView tv_name;
        private TextView tv_dengji;
        public ViewHolder(View view){
            tv_dengji = (TextView)view.findViewById(R.id.gj_tv_dengji);
            tv_name = (TextView)view.findViewById(R.id.gj_tv_name);
        }
    }
}
