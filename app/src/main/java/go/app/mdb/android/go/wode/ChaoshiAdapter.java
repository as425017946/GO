package go.app.mdb.android.go.wode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import go.app.mdb.android.go.R;
import go.app.mdb.android.go.dingdan.Dingdan;

public class ChaoshiAdapter extends BaseAdapter{
    ArrayList<Dingdan> dd_array;
    Context context;
    public ChaoshiAdapter(Context context,ArrayList<Dingdan> dd_array){
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
            view= LayoutInflater.from(context).inflate(R.layout.chaoshi_list_item,viewGroup,false);
            view.setTag(new ViewHolder(view));
        }
        vh=(ViewHolder)view.getTag();
        Dingdan dd=dd_array.get(i);
        vh.tv_ddhao.setText(dd.getDdhao());
        vh.tv_hezuoname.setText(dd.getHezuoname());
        vh.tv_newtime.setText(dd.getNewtime());
        return view;
    }

    /**
     * 实例化类对象
     */
    public static class ViewHolder{
        private TextView tv_ddhao;
        private TextView tv_hezuoname;
        private TextView tv_newtime;
        public ViewHolder(View view){
            tv_ddhao=(TextView) view.findViewById(R.id.dd_tv_ddhao);
            tv_hezuoname=(TextView)view.findViewById(R.id.dd_tv_hezuoname);
            tv_newtime=(TextView)view.findViewById(R.id.dd_tv_newtime);
        }
    }
}
