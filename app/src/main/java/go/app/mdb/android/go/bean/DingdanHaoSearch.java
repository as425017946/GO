package go.app.mdb.android.go.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 */

public class DingdanHaoSearch {

    /**
     * person : [{"InventoryName":"紫云","Quantity":32,"ID":3,"MainBillCode":"1","SalePrice":15,"Unit":"抽","Remark":"纸巾"}]
     * state : 1
     */

    private String state;
    private List<PersonBean> person;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<PersonBean> getPerson() {
        return person;
    }

    public void setPerson(List<PersonBean> person) {
        this.person = person;
    }

    public static class PersonBean {
        /**
         * InventoryName : 紫云
         * Quantity : 32
         * ID : 3
         * MainBillCode : 1
         * SalePrice : 15
         * Unit : 抽
         * Remark : 纸巾
         */

        private String InventoryName;
        private float Quantity;
        private int ID;
        private String MainBillCode;
        private float SalePrice;
        private String Unit;
        private String Remark;

        public String getInventoryName() {
            return InventoryName;
        }

        public void setInventoryName(String InventoryName) {
            this.InventoryName = InventoryName;
        }

        public float getQuantity() {
            return Quantity;
        }

        public void setQuantity(float Quantity) {
            this.Quantity = Quantity;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getMainBillCode() {
            return MainBillCode;
        }

        public void setMainBillCode(String MainBillCode) {
            this.MainBillCode = MainBillCode;
        }

        public float getSalePrice() {
            return SalePrice;
        }

        public void setSalePrice(float SalePrice) {
            this.SalePrice = SalePrice;
        }

        public String getUnit() {
            return Unit;
        }

        public void setUnit(String Unit) {
            this.Unit = Unit;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }
    }
}
