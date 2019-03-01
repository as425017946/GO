package go.app.mdb.android.go.bean;

import java.util.List;

public class ChaoshiBean {

    /**
     * data : [{"parterId":9,"salesmanName":"admin","creatTime":"2018-05-11 15:03:04","orderId":750,"parterName":"聚鑫源烟酒超市","billCode":"SO-20180511150316083","status":0},{"parterId":9,"salesmanName":"admin","creatTime":"2018-05-11 15:04:19","orderId":751,"parterName":"聚鑫源烟酒超市","billCode":"SO-20180511150430538","status":0},{"parterId":9,"salesmanName":"admin","creatTime":"2018-05-11 15:07:42","orderId":752,"parterName":"聚鑫源烟酒超市","billCode":"SO-20180511150754251","status":0},{"parterId":9,"salesmanName":"admin","creatTime":"2018-05-11 15:57:05","orderId":753,"parterName":"聚鑫源烟酒超市","billCode":"SO-20180511155717354","status":0},{"parterId":9,"salesmanName":"admin","creatTime":"2018-05-11 15:58:29","orderId":754,"parterName":"聚鑫源烟酒超市","billCode":"SO-20180511155841406","status":0},{"parterId":9,"salesmanName":"admin","creatTime":"2018-05-15 16:55:48","orderId":755,"parterName":"聚鑫源烟酒超市","billCode":"SO-20180515165604269","status":0},{"parterId":9,"salesmanName":"admin","creatTime":"2018-06-01 14:22:20","orderId":824,"parterName":"聚鑫源烟酒超市","billCode":"SO-20180601142306844","status":0},{"parterId":9,"salesmanName":"admin","creatTime":"2018-06-01 16:15:56","orderId":825,"parterName":"聚鑫源烟酒超市","billCode":"SO-20180601161642911","status":0}]
     * message : 请求成功
     * state : 1
     */

    private String message;
    private int state;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * parterId : 9
         * salesmanName : admin
         * creatTime : 2018-05-11 15:03:04
         * orderId : 750
         * parterName : 聚鑫源烟酒超市
         * billCode : SO-20180511150316083
         * status : 0
         */

        private int parterId;
        private String salesmanName;
        private String creatTime;
        private int orderId;
        private String parterName;
        private String billCode;
        private String status;

        public int getParterId() {
            return parterId;
        }

        public void setParterId(int parterId) {
            this.parterId = parterId;
        }

        public String getSalesmanName() {
            return salesmanName;
        }

        public void setSalesmanName(String salesmanName) {
            this.salesmanName = salesmanName;
        }

        public String getCreatTime() {
            return creatTime;
        }

        public void setCreatTime(String creatTime) {
            this.creatTime = creatTime;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getParterName() {
            return parterName;
        }

        public void setParterName(String parterName) {
            this.parterName = parterName;
        }

        public String getBillCode() {
            return billCode;
        }

        public void setBillCode(String billCode) {
            this.billCode = billCode;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
