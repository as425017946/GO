package go.app.mdb.android.go.bean;

import java.util.List;

/**
 * Created by Administrator on 2017-12-21.
 */

public class FirstFragmentBean {

    /**
     * person : [{"Brand":"心心相印","ShortCode":"xinxinxiangyin","StockQuantity":32,"BarCode":"1234567890123","ClassID":5,"Specification":"1*5","ID":1,"Name":"心心相印","Remark":"真假"},{"Brand":"烟","ShortCode":"xinxinxiangyin","StockQuantity":32,"BarCode":"1234567890123","ClassID":5,"Specification":"1*10","ID":2,"Name":"天下名楼","Remark":"真假难辨"},{"Brand":"烟","ShortCode":"xinxinxiangyin","StockQuantity":32,"BarCode":"1234567890123","ClassID":5,"Specification":"1*10","ID":3,"Name":"紫云","Remark":"真假"},{"Brand":"烟","ShortCode":"xinxinxiangyin","StockQuantity":32,"BarCode":"1234567890123","ClassID":5,"Specification":"1*10","ID":4,"Name":"煊赫门","Remark":"正品"}]
     * state : 1
     */
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

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
         * Brand : 心心相印
         * ShortCode : xinxinxiangyin
         * StockQuantity : 32
         * BarCode : 1234567890123
         * ClassID : 5
         * Specification : 1*5
         * ID : 1
         * Name : 心心相印
         * Remark : 真假
         */

        private String Brand;
        private String ShortCode;
        private float StockQuantity;
        private String BarCode;
        private int ClassID;
        private String Specification;
        private int ID;
        private String Name;
        private String Remark;

        public String getBrand() {
            return Brand;
        }

        public void setBrand(String Brand) {
            this.Brand = Brand;
        }

        public String getShortCode() {
            return ShortCode;
        }

        public void setShortCode(String ShortCode) {
            this.ShortCode = ShortCode;
        }

        public float getStockQuantity() {
            return StockQuantity;
        }

        public void setStockQuantity(float StockQuantity) {
            this.StockQuantity = StockQuantity;
        }

        public String getBarCode() {
            return BarCode;
        }

        public void setBarCode(String BarCode) {
            this.BarCode = BarCode;
        }

        public int getClassID() {
            return ClassID;
        }

        public void setClassID(int ClassID) {
            this.ClassID = ClassID;
        }

        public String getSpecification() {
            return Specification;
        }

        public void setSpecification(String Specification) {
            this.Specification = Specification;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }
    }
}
