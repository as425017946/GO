package go.app.mdb.android.go.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import go.app.mdb.android.go.LogoActivity;
import go.app.mdb.android.go.R;

/**
 * Created by Administrator on 2017-11-21.
 * 工具类
 */

public class Helptools {


    /**
     *
     * 静态方法的使用
     * */
    public static  void mother(){
        Log.e("Helptools","mother");
    }

    //单例模式
    public static DatabaseHelper databaseHelper=null;
    public static SQLiteDatabase db;

    //数据读读取时使用数据库
    public static SQLiteDatabase getDb(Context context){
        if (databaseHelper==null){
            databaseHelper = new DatabaseHelper(context);
        }

        db=databaseHelper.getReadableDatabase();
        return db;
    }
    //数据写入时使用
    public static SQLiteDatabase wriDb(Context context){
        db=databaseHelper.getWritableDatabase();
        return db;
    }


}
