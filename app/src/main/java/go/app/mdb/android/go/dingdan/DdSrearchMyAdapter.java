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
 * Created by Administrator on 2017-11-24.
 */

public class DdSrearchMyAdapter extends BaseAdapter {
    Context context;
    ArrayList<DdSearch> searchArrayList;
    public DdSrearchMyAdapter(Context context,ArrayList<DdSearch> searchArrayList){
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
        ViewHolder vh=null;
        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.dd_search_item,viewGroup,false);
            view.setTag(new ViewHolder(view));
        }
        vh=(ViewHolder)view.getTag();
        DdSearch ddSearch  = searchArrayList.get(i);
        vh.tv_name.setText(ddSearch.getName());
        vh.tv_guige.setText(ddSearch.getGuige());
        vh.tv_num.setText(ddSearch.getNum());
        vh.tv_price.setText(ddSearch.getPrice());
        vh.tv_beizhu.setText(ddSearch.getBeizhu());
        return view;
    }
    /**
     * 实例化类对象
     */
    public static class ViewHolder{
        private TextView tv_name;
        private TextView tv_num;
        private TextView tv_guige;
        private TextView tv_price;
        private TextView tv_beizhu;
        public ViewHolder(View view){
            tv_name=(TextView) view.findViewById(R.id.dd_sh_tv_goodsname);
            tv_num=(TextView)view.findViewById(R.id.dd_sh_tv_num);
            tv_guige=(TextView)view.findViewById(R.id.dd_sh_tv_unit);
            tv_price=(TextView)view.findViewById(R.id.dd_sh_tv_sellingprice);
            tv_beizhu=(TextView)view.findViewById(R.id.dd_sh_tv_beizhu);

        }
    }
}
