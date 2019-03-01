package go.app.mdb.android.go.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mdb on 2017-11-30.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    //类没有实例化,是不能用作父类构造器的参数,必须声明为静态
    private static final String name = "kanjiago.db"; //数据库名称
    private static final int version = 1; //数据库版本
    public DatabaseHelper(Context context) {
        //第三个参数CursorFactory指定在执行查询时获得一个游标实例的工厂类,设置为null,代表使用系统默认的工厂类
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建用户表
        db.execSQL("CREATE TABLE IF NOT EXISTS person (personid integer primary key autoincrement, name varchar(20),id varchar(50))");
        //创建订单表
        db.execSQL("CREATE TABLE IF NOT EXISTS orderform (ddid integer primary key autoincrement, hezuoname varchar(50)," +
                "hezuo_id varchar(50), personid varcha(20),settiem varchar(20),updatetiem varchar(20),ddstate varchar(20),scstate varchar(20)," +
                "goodsid varchar(50),ddhao varchar(15))");
        //创建商品表
        db.execSQL("CREATE TABLE IF NOT EXISTS goods (goodsid integer primary key autoincrement, name varchar(20), fenlei varchar(20)" +
                ",id varchar(50),pinpai varchar(20),guige varchar(20),guigeid varchar(20),num varchar(20),price varchar(20),beizhu varchar(200))");
        //创建商品信息表（从网上读取的）
        db.execSQL("CREATE TABLE IF NOT EXISTS goodinfos (goodsid integer primary key autoincrement, name varchar(20),id varchar(200),BarCode varchar(30))");
        //创建合作单位信息表（从网上读取的）
        db.execSQL("CREATE TABLE IF NOT EXISTS hzdwinfo (goodsid integer primary key autoincrement, name varchar(20),id varchar(200),StepCode varcjar(10))");
        //创建商品单位信息表（从网上读取的）
        db.execSQL("CREATE TABLE IF NOT EXISTS goodsdanwei (goodsid integer primary key autoincrement, name varchar(20),id varchar(200))");
        //创建商品价格信息表（从网上读取的）
        db.execSQL("CREATE TABLE IF NOT EXISTS goodsdanjia (goodsid integer primary key autoincrement, InventoryID varchar(20),id varchar(200),StepPrice1 varchar(10)," +
                "StepPrice2 varchar(10),StepPrice3 varchar(10),StepPrice4 varchar(10),StepPrice5 varchar(10),name varchar(50))");
        //创建商品表
        db.execSQL("CREATE TABLE IF NOT EXISTS goods_moshi1 (goodsid integer primary key autoincrement, name varchar(20), fenlei varchar(20)" +
                ",id varchar(50),pinpai varchar(20),guige varchar(20),guigeid varchar(20),num varchar(20),danwei varchar(20),price varchar(20),beizhu varchar(200),hezuodanwei varchar(20))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL(" ALTER TABLE person ADD phone VARCHAR(12) null "); //往表中增加一列
        // DROP TABLE IF EXISTS person 删除表
    }
}
