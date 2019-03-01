package go.app.mdb.android.go.kucun;

/**
 * Created by Administrator on 2017-11-24.
 */

public class Kucun {
    /**
     * 商品名
     * 库存量
     * 入库时间
     * 保质期
     * 销售价格
     * 有无存货
     */
    private String name;
    private String kucunnum;
    private String rukutime;
    private String baozhiqi;
    private String price;
    private String youwu;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKucunnum() {
        return kucunnum;
    }

    public void setKucunnum(String kucunnum) {
        this.kucunnum = kucunnum;
    }

    public String getRukutime() {
        return rukutime;
    }

    public void setRukutime(String rukutime) {
        this.rukutime = rukutime;
    }

    public String getBaozhiqi() {
        return baozhiqi;
    }

    public void setBaozhiqi(String baozhiqi) {
        this.baozhiqi = baozhiqi;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getYouwu() {
        return youwu;
    }

    public void setYouwu(String youwu) {
        this.youwu = youwu;
    }
}
