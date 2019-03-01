package go.app.mdb.android.go.dingdan;

/**
 * Created by mdb on 2017-11-21.
 */

public class Dingdan {
    /***
     * 订单号
     * 合作单位
     * 创建时间
     * 更新时间
     * 订单状态
     * 上传状态
     * 销售员
     * 备注
     */
    private String ddhao;
    private String hezuoname;
    private String newtime;
    private String updatetime;
    private String ddstate;
    private String updatestate;
    private String xiaoshouyuan;
    private String id;

    public String getXiaoshouyuan() {
        return xiaoshouyuan;
    }

    public void setXiaoshouyuan(String xiaoshouyuan) {
        this.xiaoshouyuan = xiaoshouyuan;
    }

    public String getDdhao() {
        return ddhao;
    }

    public void setDdhao(String ddhao) {
        this.ddhao = ddhao;
    }

    public String getHezuoname() {
        return hezuoname;
    }

    public void setHezuoname(String hezuoname) {
        this.hezuoname = hezuoname;
    }

    public String getNewtime() {
        return newtime;
    }

    public void setNewtime(String newtime) {
        this.newtime = newtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getDdstate() {
        return ddstate;
    }

    public void setDdstate(String ddstate) {
        this.ddstate = ddstate;
    }

    public String getUpdatestate() {
        return updatestate;
    }

    public void setUpdatestate(String updatestate) {
        this.updatestate = updatestate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
