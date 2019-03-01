package go.app.mdb.android.go.pandian;

/**
 * Created by mdb on 2017-11-20.
 */

public class PanDian {
    /***
     * name 名称
     * fenlei 分类
     * pinpai 品牌
     * guige 规格
     * kucun 库存
     * zhujima 助记码
     * tiaoxingma 条形码
     * id
     * 备注
     */
    private String name;
    private String fenlei;
    private String pinpai;
    private String guige;
    private String kucun;
    private String zhujima;
    private String tiaoxingma;
    private String id;

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    private String beizhu;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFenlei() {
        return fenlei;
    }

    public void setFenlei(String fenlei) {
        this.fenlei = fenlei;
    }

    public String getPinpai() {
        return pinpai;
    }

    public void setPinpai(String pinpai) {
        this.pinpai = pinpai;
    }

    public String getGuige() {
        return guige;
    }

    public void setGuige(String guige) {
        this.guige = guige;
    }

    public String getKucun() {
        return kucun;
    }

    public void setKucun(String kucun) {
        this.kucun = kucun;
    }

    public String getZhujima() {
        return zhujima;
    }

    public void setZhujima(String zhujima) {
        this.zhujima = zhujima;
    }

    public String getTiaoxingma() {
        return tiaoxingma;
    }

    public void setTiaoxingma(String tiaoxingma) {
        this.tiaoxingma = tiaoxingma;
    }
}
