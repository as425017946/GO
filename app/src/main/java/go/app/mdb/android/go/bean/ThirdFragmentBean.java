package go.app.mdb.android.go.bean;

import java.util.List;

/**
 * Created by Administrator on 2017-12-22.
 */

public class ThirdFragmentBean {
    /**
     * person : [{"CreatTime":"2017-12-14 14:01:38","asStockQuantity":"true","StockQuantity":32,"CostValue":320,"StepPrice":1990,"ID":1,"Name":"心心相印"}]
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
         * CreatTime : 2017-12-14 14:01:38
         * asStockQuantity : true
         * StockQuantity : 32
         * CostValue : 320
         * StepPrice : 1990
         * ID : 1
         * Name : 心心相印
         */

        private String CreatTime;
        private String asStockQuantity;
        private float StockQuantity;
        private float CostValue;
        private float StepPrice;
        private int ID;
        private String Name;

        public String getCreatTime() {
            return CreatTime;
        }

        public void setCreatTime(String CreatTime) {
            this.CreatTime = CreatTime;
        }

        public String getAsStockQuantity() {
            return asStockQuantity;
        }

        public void setAsStockQuantity(String asStockQuantity) {
            this.asStockQuantity = asStockQuantity;
        }

        public float getStockQuantity() {
            return StockQuantity;
        }

        public void setStockQuantity(float StockQuantity) {
            this.StockQuantity = StockQuantity;
        }

        public float getCostValue() {
            return CostValue;
        }

        public void setCostValue(float CostValue) {
            this.CostValue = CostValue;
        }

        public float getStepPrice() {
            return StepPrice;
        }

        public void setStepPrice(float StepPrice) {
            this.StepPrice = StepPrice;
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
    }
}
