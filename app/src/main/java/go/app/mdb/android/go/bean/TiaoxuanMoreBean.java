package go.app.mdb.android.go.bean;

import java.util.List;

/**
 * Created by Administrator on 2018-01-24.
 */

public class TiaoxuanMoreBean {

    /**
     * person : [{"StepPrice":28,"StockQuantity":7,"Specification":"1*24","ID":533,"Unit":"包","Name":"阿尔卑斯棒棒糖混合口味"}]
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
         * StepPrice : 28.0
         * StockQuantity : 7.0
         * Specification : 1*24
         * ID : 533
         * Unit : 包
         * Name : 阿尔卑斯棒棒糖混合口味
         */

        private double StepPrice;
        private double StockQuantity;
        private String Specification;
        private int ID;
        private String Unit;
        private String Name;

        public double getStepPrice() {
            return StepPrice;
        }

        public void setStepPrice(double StepPrice) {
            this.StepPrice = StepPrice;
        }

        public double getStockQuantity() {
            return StockQuantity;
        }

        public void setStockQuantity(double StockQuantity) {
            this.StockQuantity = StockQuantity;
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

        public String getUnit() {
            return Unit;
        }

        public void setUnit(String Unit) {
            this.Unit = Unit;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }
    }
}
