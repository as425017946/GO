package go.app.mdb.android.go.tools;


import static go.app.mdb.android.go.LogoActivity.qingqiudizhi;

/**
 * Created by Administrator on 2017-12-19.
 * 接口请求地址
 */

public class Api {
//    public static String qingqiudizhi = "http://link.s29.csome.cn:20136";  正式
//    public static String qingqiudizhi = "http://chuanqing.s29.csome.cn:20200"; 测试
    //登录
    public static String logo = qingqiudizhi+"/kanjiaGo/user/login";
    //人员名称
    public static String renyuan = qingqiudizhi+"/kanjiaGo/user/getUsers";
    //分组
    public static String fenzhu  = qingqiudizhi+"/kanjiaGo/classGroup/groupNames";
    //分类
    public static String fenlei = qingqiudizhi+"/kanjiaGo/classGroup/getInventiryClass";
    //规格
    public static String guige = qingqiudizhi+"/kanjiaGo/classGroup/Specification";
    //单位
    public static String danwei = qingqiudizhi+"/kanjiaGo/order/getUnit";
    //品牌
    public static String pinpai = qingqiudizhi+"/kanjiaGo/classGroup/Brand";
    //合作单位
    public static String hezuodanwei = qingqiudizhi+"/kanjiaGo/partner/getPartner";
    //盘点统计
    public static String pandian = qingqiudizhi+"/kanjiaGo/classGroup/stock";
    //盘点查询
    public static String pd_chaxun = qingqiudizhi+"/kanjiaGo/classGroup/getDetails" ;
    //盘点修改
    public static String pd_xiugai = qingqiudizhi+"/kanjiaGo/classGroup/upRemark" ;
    //订单统计
    public static String dingdan = qingqiudizhi+"/kanjiaGo/order/details";
    //订单查询
    public static String dd_chaxun = qingqiudizhi+"/kanjiaGo/order/getOrder" ;
    //根据订单id查询
    public static String dd_idchaxun = qingqiudizhi+"/kanjiaGo/order/getOrderDetails";
    //订单修改上传状态
    public static String dd_upstate = qingqiudizhi+"/kanjiaGo/order/updataIsUpdate";
    //订单修改订单状态
    public static String dd_state = qingqiudizhi+"/kanjiaGo/order/updataStatus" ;
    //创建订单详情
    public static String dd_xiangqing = qingqiudizhi+"/kanjiaGo/order/addOrderDetail" ;
    //创建订单
    public static String dd_chuangjian  = qingqiudizhi+"/kanjiaGo/order/addOrder";
    //库存分级查询
    public static String kucun = qingqiudizhi+"/kanjiaGo/inventory/getInventoryList";
    //库存查询
    public static String kc_chaxun = qingqiudizhi+"/kanjiaGo/inventory/getInventoryList" ;
    //店铺信息
    public static String dianpu = qingqiudizhi+"/kanjiaGo/partner/getIdPartner";
    //店铺信息查询
    public static String dp_chaxun = qingqiudizhi+"/kanjiaGo/partnerStep/getPartnerStep";
    //修改店铺等级
    public static String dp_xiugai = qingqiudizhi+"/kanjiaGo/partnerStep/upStepCode";
    //条形码扫描出相类似信息
    public static String txm_xiangsi = qingqiudizhi+"/kanjiaGo/classGroup/getBrand" ;
    //根据商品id查询条形码
    public static String cha_txm = qingqiudizhi+"/kanjiaGo/classGroup/getShopId";
    //往来单位带分级
    public static String dw_fenji = qingqiudizhi+"/kanjiaGo/partner/getAllPartner" ;
    //所有商品带条形码
    public static String goods_txm = qingqiudizhi+"/kanjiaGo/inventory/getAllInventory";
    //所有商品价格
    public static String goods_danjia = qingqiudizhi+"/kanjiaGo/inventory/getAllInventoryPrice" ;
    //模糊查询公司
    public static String mohu_gongsi = qingqiudizhi+"/kanjiaGo/partner/getPartnerDetails" ;
    //查询商品列表
    public static String weiyi_goods = qingqiudizhi+"/kanjiaGo/inventory/selectInventoryStateList";
    //版本更新
    public static String banbengengxin = qingqiudizhi + "/kanjiaGo/user/getVersion";

    //超市列表查询
    public static String chaoshilist = "/YunyixiaoE/goods/getPartnerIdBySalesman";
    //超市列表详情查询
    public static String chaoshilistmore = "/YunyixiaoClient/goods/getOrderListDetail";
    //超市列表详情提交
    public static String chaoshilistmoretijiao = "/YunyixiaoE/goods/updateOrderStatus";
//    http://39.107.70.80:8080

}
